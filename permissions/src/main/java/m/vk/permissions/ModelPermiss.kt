package m.vk.permissions

import android.os.Parcel
import android.os.Parcelable

data class ModelPermiss(
    val requestCode: Int,
    val grantResults: Int,
    val permissions: String?
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(requestCode)
        parcel.writeInt(grantResults)
        parcel.writeString(permissions)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelPermiss> {
        override fun createFromParcel(parcel: Parcel): ModelPermiss {
            return ModelPermiss(parcel)
        }

        override fun newArray(size: Int): Array<ModelPermiss?> {
            return arrayOfNulls(size)
        }
    }

}