package com.sample.googlerepositories.models

import com.google.gson.annotations.SerializedName
import com.sample.googlerepositories.utils.constants.ParameterKeys
import java.io.Serializable

/**
 * acts as the owner for [Repository]
 *
 * @param id is owner Id
 * @param avatarUrl os the url for avatar i.e image Url
 */
data class Owner(
    @SerializedName(ParameterKeys.id) val id: Int? = null,
    @SerializedName(ParameterKeys.avatarUrl) val avatarUrl: String? = null,
) : Serializable {

    /**
     *  checks if 2 [Owner] are equals or not
     *
     *  @param other is the Object comparing with
     *  @return true if same attributes and same class type,false otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Owner
        if (id != other.id) return false
        if (avatarUrl != other.avatarUrl) return false

        return true
    }

    /**
     *  transforms [Owner] to string
     *
     *  @return [Repository] as string
     */
    override fun toString(): String {
        return "Owner(id=$id, avatarUrl=$avatarUrl)"
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (avatarUrl?.hashCode() ?: 0)
        return result
    }

}