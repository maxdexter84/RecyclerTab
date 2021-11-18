import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoomEvent(
    val id: Long,
    val startTime: String,
    val endTime: String,
    val colorRoom: Int,
    val haveEvent: Boolean = false

) : Parcelable {
    fun minuteToDp(): Int {
        val (startHour, startMinute) = startTime.split(":")
        val (endHour, endMinute) = endTime.split(":")
        val h = endHour.toInt() - startHour.toInt()
        var res = 0
        res = if (h > 0) {
            h * 60 - startMinute.toInt() + endMinute.toInt()
        } else endMinute.toInt() - startMinute.toInt()
        return res * 2
    }
}