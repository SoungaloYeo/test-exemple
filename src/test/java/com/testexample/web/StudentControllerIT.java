package com.testexample.web;

import com.testexample.controller.SubscriberController;
import com.testexample.domain.Subscriber;
import com.testexample.domain.enumeration.Type;
import com.testexample.repository.SubscriberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubscriberControllerIT extends PostgresSqlDbTestBase{

    public static final String HTTP_LOCALHOST = "http://localhost:";
    public static final String BASE_URI = "/api/v1/subscribers";
    @LocalServerPort
    private int port;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SubscriberController subscriberController;

    @Autowired
    private TestRestTemplate restTemplate;

    private Subscriber subscriberWithCard;
    private Subscriber subscriberWithCash;


    @BeforeEach
    void setUp() {
        subscriberWithCard = Subscriber.builder().email("male@gmail.com").type(Type.CARD).name("Karl").build();
        subscriberWithCash = Subscriber.builder().email("female@gmail.com").type(Type.CASH).name("Max").build();

        subscriberRepository.save(subscriberWithCard);
        subscriberRepository.save(subscriberWithCash);

    }

    @Test
    @DisplayName("trying to read all MALE subscriber it's Ok and return One subscriber")
    void when_Get_cards_subscriber_is_ok() {
        var responseEntity = restTemplate.getForEntity(HTTP_LOCALHOST + port + BASE_URI, Subscriber[].class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().length).isEqualTo(1);

        var subscribers = responseEntity.getBody();
        assertThat(Arrays.stream(subscribers).findFirst().get())
                .usingRecursiveComparison()
                .isEqualTo(this.subscriberWithCard);
    }

    @Test
    @DisplayName("Create a new subscriber with already existing email")
    void should_fail_creating_subscriber_with_existing_email() {
        var responseEntity = restTemplate.postForEntity(HTTP_LOCALHOST + port + BASE_URI, subscriberWithCash, Subscriber.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
//        assertThat(responseEntity.getBody().getMessage()).isEqualTo("errors");
    }
//
//    @Test
//    @DisplayName("when deleting a none existing subscriber")
//    void whenDeletingANoneExistingSubscriber() {
//
//    }


}