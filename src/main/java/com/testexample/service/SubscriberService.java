package com.testexample.service;

import com.testexample.controller.dto.SubscriberDTO;
import com.testexample.controller.exception.AlreadyExistsException;
import com.testexample.controller.exception.ErrorConstants;
import com.testexample.controller.mapper.SubscriberMapper;
import com.testexample.domain.Subscriber;
import com.testexample.domain.enumeration.Type;
import com.testexample.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final SubscriberMapper subscriberMapper;

    @Transactional(readOnly = true)
    public List<SubscriberDTO> getCardsSubscriber() {
        var repositoryAll = subscriberRepository.findAll();

        return subscriberMapper.subscriberListToSubscriberDTOList(repositoryAll);
    }


    public SubscriberDTO saveSubscriber(SubscriberDTO subscriber) {

        if (subscriberRepository.existsByEmailIgnoreCase(subscriber.getEmail())) {
            throw new AlreadyExistsException(String.format(ErrorConstants.E_MAIL_EXIST, subscriber.getEmail()));
        }

        return subscriberMapper.subscriberToSubscriberDTO(
                subscriberRepository.save(
                        subscriberMapper.subscriberDtoToSubscriber(subscriber)));

    }

    @Transactional(readOnly = true)
    public Optional<SubscriberDTO> getSubscriberById(Long id) {
        log.debug("Request to get Subscriber : {}", id);
        return subscriberRepository.findById(id).map(subscriberMapper::subscriberToSubscriberDTO);
    }

    public void deleteSubscriber(Long id) {

        if (!subscriberRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Subscriber with id " + id + " does not exists");
        }
        subscriberRepository.deleteById(id);
    }
}

