package m.vk.k019_textrecognization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private var TAG_OCRFragment : String = "TagOCRFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,
            OCRFragment.newInstance(),
            TAG_OCRFragment)/*.addToBackStack(TAG_CameraFragment)*/.commit()
    }
}
