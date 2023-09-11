package com.testexample.controller;

import com.testexample.controller.dto.SubscriberDTO;
import com.testexample.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/subscribers")
@RequiredArgsConstructor
public class SubscriberController {

    private final SubscriberService subscriberService;

    @GetMapping
    public ResponseEntity<List<SubscriberDTO>> getCardsSubscriber() {
        return ResponseEntity.ok(subscriberService.getCardsSubscriber());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriberDTO> getSubscriber(@PathVariable Long id) {
        return ResponseEntity.ok(subscriberService.getSubscriberById(id).orElseThrow(EntityNotFoundException::new));
    }


    @PostMapping
    public ResponseEntity<SubscriberDTO> addSubscriber(@Valid @RequestBody SubscriberDTO subscriber) {
        return new ResponseEntity(subscriberService.saveSubscriber(subscriber), HttpStatus.CREATED);
    }


    @DeleteMapping(path = "{id}")
    public void deleteSubscriber(
            @PathVariable("id") Long id) {
        subscriberService.deleteSubscriber(id);
    }
}
