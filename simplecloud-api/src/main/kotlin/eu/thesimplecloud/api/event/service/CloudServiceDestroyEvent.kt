package eu.thesimplecloud.api.event.service

import eu.thesimplecloud.api.eventapi.IEvent
import eu.thesimplecloud.api.service.ICloudService

open class CloudServiceDestroyEvent(val cloudService: ICloudService): IEvent