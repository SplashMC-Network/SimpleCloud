package eu.thesimplecloud.lib.network.packets.service

import eu.thesimplecloud.clientserverapi.lib.connection.IConnection
import eu.thesimplecloud.clientserverapi.lib.packet.IPacket
import eu.thesimplecloud.clientserverapi.lib.packet.packettype.ObjectPacket
import eu.thesimplecloud.clientserverapi.lib.promise.ICommunicationPromise
import eu.thesimplecloud.lib.CloudLib

class PacketIOStopCloudService() : ObjectPacket<String>() {

    constructor(name: String) : this() {
        this.value = name
    }

    override suspend fun handle(connection: IConnection): ICommunicationPromise<Unit> {
        val name = this.value ?: return contentException("value")
        val cloudService = CloudLib.instance.getCloudServiceManger().getCloudService(name) ?: return failure(NoSuchElementException("Serve"))
        cloudService.shutdown()
        return unit()
    }
}