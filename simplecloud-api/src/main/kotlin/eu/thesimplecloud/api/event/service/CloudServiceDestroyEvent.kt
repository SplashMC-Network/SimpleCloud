package eu.thesimplecloud.api.event.service

import eu.thesimplecloud.api.service.ICloudService

class CloudServiceDestroyEvent(cloudService: ICloudService): CloudServiceEvent(cloudService)