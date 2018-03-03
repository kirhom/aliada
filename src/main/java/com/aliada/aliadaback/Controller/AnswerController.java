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

    @RequestMapping(path="{updateId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSearch (@PathVariable String userId, @PathVariable Long updateId, @RequestBody Answer answer) {
        this.validateUser(userId);
        Answer currentAnswer = answerRepository.findOne(updateId);
        currentAnswer.setStatus(answer.getStatus());
        answerRepository.saveAndFlush(currentAnswer);
        return new ResponseEntity<>(currentAnswer, HttpStatus.OK);
    }


    private User validateUser(String userId) {
        User user = this.userRepository.findByName(userId).orElseThrow(
                () -> new RuntimeException("Could not find user '" + userId + "'."));
        return user;
    }
}