package com.cloudbees.assessment.domain.mapper;

import com.cloudbees.assessment.domain.dto.TrainDto;
import com.cloudbees.assessment.domain.entity.Train;

public class TrainMapper {

    public static Train fromDto(Long id, TrainDto trainDto, Train train) {
        Train updateTrain = new Train();
        updateTrain.setId(id);
        updateTrain.setFrom(trainDto.getFrom() != null ? trainDto.getFrom() : train.getFrom());
        updateTrain.setTo(trainDto.getTo() != null ? trainDto.getTo() : train.getTo());
        updateTrain.setPrice(trainDto.getPrice() == 0.0 ? trainDto.getPrice() : train.getPrice());
        return updateTrain;
    }
}
