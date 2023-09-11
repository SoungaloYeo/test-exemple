package com.testexample.service;

import com.testexample.controller.dto.SubscriberDTO;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {
    public static final Long SUBSCRIBER_ID = 25L;
    @InjectMocks
    private SubscriberService subscriberService;

    @Mock
    private SubscriberRepository subscriberRepository;
    @Mock
    private SubscriberMapper subscriberMapper;

    private Subscriber subscriberWithCard;
    private Subscriber subscriberWithCash;


    @BeforeEach
    void setUp() {
        subscriberWithCard = Subscriber.builder().id(1L).email("one@gmail.com").type(Type.CARD).name("Karl").build();
        subscriberWithCash = Subscriber.builder().id(2L).email("two@gmail.com").type(Type.CASH).name("Max").build();

    }

    @Test
    @DisplayName("trying to read all CARD subscribers it's  Ok and return One subscriber")
    void when_Get_cards_Subscriber_is_ok() {

        when(subscriberRepository.findAll()).thenReturn(List.of(subscriberWithCard, subscriberWithCash));
        when(subscriberMapper.subscriberListToSubscriberDTOList(Arrays.asList(subscriberWithCard, subscriberWithCash))).thenReturn(
                List.of(
                        SubscriberDTO.builder().id(subscriberWithCard.getId()).name(subscriberWithCard.getName()).email(subscriberWithCard.getEmail()).type(subscriberWithCard.getType()).build(),
                        SubscriberDTO.builder().id(subscriberWithCash.getId()).name(subscriberWithCash.getName()).email(subscriberWithCash.getEmail()).type(subscriberWithCash.getType()).build()
                ));
        var subscribers = this.subscriberService.getCardsSubscriber();

        Assertions.assertAll(()-> assertEquals(2, subscribers.size()));
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