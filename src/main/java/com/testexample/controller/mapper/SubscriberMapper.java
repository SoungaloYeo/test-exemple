package com.testexample.controller.mapper;

import com.testexample.service.dto.SubscriberDTO;
import com.testexample.domain.Subscriber;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriberMapper {

    Subscriber  subscriberDtoToSubscriber(SubscriberDTO dto);

    SubscriberDTO subscriberToSubscriberDTO(Subscriber subscriber);
}
