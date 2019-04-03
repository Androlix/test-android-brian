package com.androlix.macare.data

import android.os.Parcel
import android.os.Parcelable
import java.util.*


/**
 * Created by Brian on 4/2/2019.
 */

class GlycemicLevelEntry(
    var id: String = "",
    var level: Float = 0f,
    var year: Int = Calendar.getInstance().get(Calendar.YEAR),
    var month: Int = Calendar.getInstance().get(Calendar.MONTH),
    var day: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    var hour: Int = Calendar.getInstance().get(Calendar.HOUR),
    var minutes: Int = Calendar.getInstance().get(Calendar.MINUTE),
    var timestamp: Long = System.currentTimeMillis()

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeFloat(level)
        parcel.writeInt(year)
        parcel.writeInt(month)
        parcel.writeInt(day)
        parcel.writeInt(hour)
        parcel.writeInt(minutes)
        parcel.writeLong(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GlycemicLevelEntry> {

        fun newInstance(): GlycemicLevelEntry {
            return GlycemicLevelEntry()
        }

        override fun createFromParcel(parcel: Parcel): GlycemicLevelEntry {
            return GlycemicLevelEntry(parcel)
        }

        override fun newArray(size: Int): Array<GlycemicLevelEntry?> {
            return arrayOfNulls(size)
        }
    }
}
