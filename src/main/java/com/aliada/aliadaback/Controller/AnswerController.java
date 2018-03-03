package com.aliada.aliadaback.Controller;

import com.aliada.aliadaback.model.Search;
import com.aliada.aliadaback.model.SearchRepository;
import com.aliada.aliadaback.model.User;
import com.aliada.aliadaback.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController    // This means that this class is a Controller
@RequestMapping(path="/{userId}/search") // This means URL's start with /demo (after Application path)
public class SearchController {
    @Autowired // This means to get the bean called searchRepository. // Which is auto-generated by Spring, we will use it to handle the data
    private SearchRepository searchRepository;

    @Autowired
    private UserRepository userRepository;


    @RequestMapping(method = RequestMethod.POST) // Map ONLY POST Requests
    public ResponseEntity<?> addNewSearch (@PathVariable String userId, @RequestBody Search search) {
        User user = this.validateUser(userId);
        this.cancelOtherSearches(user);
        search.setCreator(user);
        search.setStatus(Search.AWAITING_RESPONSE);
        searchRepository.save(search);
        return new ResponseEntity<>(search, HttpStatus.OK);
    }

    @RequestMapping(path="active", method = RequestMethod.GET)
    public ResponseEntity<?> getActiveSearches (@PathVariable String userId) {
        User user = this.validateUser(userId);
        Set<Search> searches = searchRepository.findByStatusAndUser(user, Search.AWAITING_RESPONSE);
        if (searches.size() > 0) {
            Search search = (Search) searchRepository.findByStatusAndUser(user, Search.AWAITING_RESPONSE).toArray()[0];
            return new ResponseEntity<>(search, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(path="{searchId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSearch (@PathVariable String userId, @PathVariable Long searchId, @RequestBody Search search) {
        this.validateUser(userId);
        Search currentSearch = searchRepository.findOne(searchId);
        currentSearch.setStatus(search.getStatus());
        currentSearch.setReason(search.getReason());
        searchRepository.saveAndFlush(currentSearch);
        return new ResponseEntity<>(currentSearch, HttpStatus.OK);
    }

    private void cancelOtherSearches(User user) {
        Set<Search> searches = searchRepository.findByStatusAndUser(user, Search.AWAITING_RESPONSE);
        for (Search search: searches) {
            search.setStatus(Search.CANCELLED);
            searchRepository.save(search);
        }
    }

    private User validateUser(String userId) {
        User user = this.userRepository.findByName(userId).orElseThrow(
                () -> new RuntimeException("Could not find user '" + userId + "'."));
        return user;
    }
}