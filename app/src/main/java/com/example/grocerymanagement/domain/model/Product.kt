package com.example.grocerymanagement.domain.model

import android.os.Parcel
import android.os.Parcelable


data class Product(
    val id: Int,
    val name: String,
    val barcode: String,
    val img: String, // Đường dẫn ảnh
    val description: String,
    val quantity: Int,
    val note: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(barcode)
        parcel.writeString(img)
        parcel.writeString(description)
        parcel.writeInt(quantity)
        parcel.writeString(note)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
