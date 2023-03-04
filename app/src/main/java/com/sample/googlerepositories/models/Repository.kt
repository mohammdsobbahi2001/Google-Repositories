package com.sample.googlerepositories.models

import com.google.gson.annotations.SerializedName
import com.sample.googlerepositories.utils.constants.ParameterKeys
import com.sample.googlerepositories.utils.extensions.formatGMTDateStringAsDDMMYYYYString
import java.io.Serializable

/**
 * repository class act to model a Google repository
 *
 * @param id is repository Id
 * @param name is repository name
 * @param fullName is repository full Name
 * @param createdAt is repository creation date
 * @param stargazersCount is repository stargazers Count
 * @param owner is repository owner
 */
data class Repository(
    @SerializedName(ParameterKeys.id) val id: Int? = null,
    @SerializedName(ParameterKeys.name) val name: String? = null,
    @SerializedName(ParameterKeys.fullName) val fullName: String? = null,
    @SerializedName(ParameterKeys.createdAt) val createdAt: String? = null,
    @SerializedName(ParameterKeys.stargazersCount) val stargazersCount: Int? = null,
    @SerializedName(ParameterKeys.owner) val owner: Owner? = Owner(),
) : Serializable {


    /**
     * used to format the date retrieved and rerun it as a string
     *
     * @return String is the data formated
     */
    fun formatDate(): String {
        return createdAt?.formatGMTDateStringAsDDMMYYYYString() ?: createdAt ?: ""
    }


    /**
     *  checks if 2 [Repository] are equals or not
     *
     *  @param other is the Object comparing with
     *  @return true if same attributes and same class type,false otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Repository
        if (id != other.id) return false
        if (name != other.name) return false
        if (fullName != other.fullName) return false
        if (createdAt != other.createdAt) return false
        if (stargazersCount != other.stargazersCount) return false
        if (owner != other.owner) return false
        return true
    }

    /**
     *  transforms [Repository] to string
     *
     *  @return [Repository] as string
     */
    override fun toString(): String {
        return "Repository(id=$id, name=$name, fullName=$fullName, createdAt=$createdAt, stargazersCount=$stargazersCount, owner=$owner)"
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (fullName?.hashCode() ?: 0)
        result = 31 * result + (createdAt?.hashCode() ?: 0)
        result = 31 * result + (stargazersCount ?: 0)
        result = 31 * result + (owner?.hashCode() ?: 0)
        return result
    }

}