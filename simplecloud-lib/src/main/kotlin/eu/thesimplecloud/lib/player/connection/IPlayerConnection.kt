package eu.thesimplecloud.lib.player.connection

import java.net.InetAddress
import java.util.*

interface IPlayerConnection {

    /**
     * Returns the address of this player.
     */
    fun getAddress(): InetAddress

    /**
     * Returns the unique id of this player
     */
    fun getUniqueId(): UUID

    /**
     * Returns the username of this player
     */
    fun getName(): String

    /**
     * Returns whether the player's connection is in online mode.
     */
    fun isOnlineMode(): Boolean

    /**
     * Get the numerical client version of the player attempting to log in.
     *
     * @return the protocol version of the client
     */
    fun getVersion(): Int

}