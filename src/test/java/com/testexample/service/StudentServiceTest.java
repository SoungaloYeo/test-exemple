package com.testexample.service;

import com.testexample.controller.mapper.SubscriberMapper;
import com.testexample.domain.Subscriber;
import com.testexample.domain.enumeration.Type;
import com.testexample.repository.SubscriberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {
    public static final Long SUBSCRIBER_ID = 25L;
    private SubscriberService subscriberService;

    @Mock
    private SubscriberRepository subscriberRepository;
    @Autowired
    private SubscriberMapper subscriberMapper;

    private Subscriber subscriberWithCard;
    private Subscriber subscriberWithCash;


    @BeforeEach
    void setUp() {
        subscriberWithCard = Subscriber.builder().email("one@gmail.com").type(Type.CARD).name("Karl").build();
        subscriberWithCash = Subscriber.builder().email("two@gmail.com").type(Type.CASH).name("Max").build();

        subscriberService = new SubscriberService(subscriberRepository, subscriberMapper);
    }

    @Test
    @DisplayName("trying to read all CARD subscribers it's  Ok and return One subscriber")
    void when_Get_cards_Subscriber_is_ok() {

        when(subscriberRepository.findAll()).thenReturn(Arrays.asList(subscriberWithCard, subscriberWithCash));
        var subscribers = this.subscriberService.getCardsSubscriber();

        Assertions.assertAll(
                ()-> assertEquals(1, subscribers.size()),
                ()-> Assertions.assertTrue(subscribers.stream().findFirst().isPresent()),
                ()-> Assertions.assertEquals(subscribers.stream().findFirst().get().getEmail(), subscriberWithCard.getEmail())
        );
    }

    @Test
    @DisplayName("when deleting a existing subscriber")
    void whenDeletingAExistingSubscriber() {

        subscriberWithCard.setId(SUBSCRIBER_ID);
        when(subscriberRepository.existsById(SUBSCRIBER_ID)).thenReturn(Boolean.TRUE);

        ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);
        doNothing().when(subscriberRepository).deleteById(valueCapture.capture());

        subscriberService.deleteSubscriber(SUBSCRIBER_ID);
        verify(subscriberRepository, times(1)).deleteById(SUBSCRIBER_ID);
        verify(subscriberRepository, times(1)).existsById(SUBSCRIBER_ID);

        assertEquals(SUBSCRIBER_ID, valueCapture.getValue());
    }

    @Test
    @DisplayName("when deleting a none existing subscriber")
    void whenDeletingANoneExistingSubscriber() {

        subscriberWithCard.setId(SUBSCRIBER_ID);
        when(subscriberRepository.existsById(SUBSCRIBER_ID)).thenReturn(Boolean.FALSE);


        assertThrows(EntityNotFoundException.class, () -> {
            subscriberService.deleteSubscriber(SUBSCRIBER_ID);
        });

        verify(subscriberRepository, times(0)).deleteById(SUBSCRIBER_ID);
    }


}