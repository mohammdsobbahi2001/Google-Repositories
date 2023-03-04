package com.sample.googlerepositories.core.result

/**
 * Sealed class that specify the API different errors with the possibility to have a message
 *
 * @param message is the message attached with the error
 */
sealed class RemoteError(private val message: String? = null) {

    /**
     * Occurs due to general error not found in [RemoteError]
     *
     * @param msg is the message of the error
     */
    class GenericError(msg: String? = null) : RemoteError(msg)

    /**
     * Occurs due to bad request parameters
     *
     * @param msg is the message of the error
     */
    class BadRequest(msg: String? = null) : RemoteError(msg)

    /**
     * Occurs due to a server error
     *
     * @param msg is the message of the error
     */
    class ServerError(msg: String? = null) : RemoteError(msg)
}