package com.testexample.controller.mapper;

import com.testexample.controller.dto.SubscriberDTO;
import com.testexample.domain.Subscriber;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriberMapper {

    Subscriber  subscriberDtoToSubscriber(SubscriberDTO dto);

    SubscriberDTO subscriberToSubscriberDTO(Subscriber subscriber);
    List<SubscriberDTO> subscriberListToSubscriberDTOList(List<Subscriber> subscriber);
}
