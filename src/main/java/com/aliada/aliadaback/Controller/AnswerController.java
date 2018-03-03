package com.aliada.aliadaback.Controller;

import com.aliada.aliadaback.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping(path="/{userId}/answer")
public class AnswerController {
    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addNewAnswer (@PathVariable String userId, @RequestBody Answer answer) {
        User user = this.validateUser(userId);
        answer.setStatus(Answer.AWAITING_RESPONSE);
        answer.setUser(user);
        answerRepository.save(answer);
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

//    @RequestMapping(path="active", method = RequestMethod.GET)
//    public ResponseEntity<?> getActiveSearches (@PathVariable String userId) {
//        User user = this.validateUser(userId);
//        Set<Search> searches = searchRepository.findByStatusAndUser(user, Search.AWAITING_RESPONSE);
//        if (searches.size() > 0) {
//            Search search = (Search) searchRepository.findByStatusAndUser(user, Search.AWAITING_RESPONSE).toArray()[0];
//            return new ResponseEntity<>(search, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }
//
//    @RequestMapping(path="{searchId}", method = RequestMethod.PUT)
//    public ResponseEntity<?> updateSearch (@PathVariable String userId, @PathVariable Long searchId, @RequestBody Search search) {
//        this.validateUser(userId);
//        Search currentSearch = searchRepository.findOne(searchId);
//        currentSearch.setStatus(search.getStatus());
//        currentSearch.setReason(search.getReason());
//        searchRepository.saveAndFlush(currentSearch);
//        return new ResponseEntity<>(currentSearch, HttpStatus.OK);
//    }


    private User validateUser(String userId) {
        User user = this.userRepository.findByName(userId).orElseThrow(
                () -> new RuntimeException("Could not find user '" + userId + "'."));
        return user;
    }
}