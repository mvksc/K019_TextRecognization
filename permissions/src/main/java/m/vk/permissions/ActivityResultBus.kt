package m.vk.permissions

import android.os.Handler
import android.os.Looper

import com.squareup.otto.Bus

class ActivityResultBus : Bus() {

    private val mHandler = Handler(Looper.getMainLooper())

    fun postQueue(obj: Any) {
        mHandler.post { ActivityResultBus.getInstance().post(obj) }
    }

    companion object {

        private var instance: ActivityResultBus? = null

        fun getInstance(): ActivityResultBus {
            if (instance == null)
                instance = ActivityResultBus()
            return instance as ActivityResultBus
        }
    }

}
