package com.example.test_ubi.data.mapper

import com.example.test_ubi.data.entity.SpeedEntity
import com.example.test_ubi.data.model.SpeedModel

fun speedEntityToModel(entity: SpeedEntity): SpeedModel {
    return SpeedModel(entity.speed, entity.timestamp)
}