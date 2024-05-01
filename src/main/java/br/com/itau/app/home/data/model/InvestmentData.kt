package br.com.itau.app.home.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InvestmentData(
    val name: String,
    val code: String,
    val quantity: Float,
    @SerializedName("unit_price") val unitPrice: Double,
    val type: String
) : Parcelable
