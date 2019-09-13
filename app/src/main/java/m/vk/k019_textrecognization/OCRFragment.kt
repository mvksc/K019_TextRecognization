package m.vk.k019_textrecognization

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import m.vk.k019_textrecognization.databinding.FragmentOcrBinding
import m.vk.permissions.ModelPermiss
import m.vk.permissions.RunTimePermission


class OCRFragment : RunTimePermission() {

    private var RESULT_CAMERA_REQUEST = 100
    private lateinit var binding : FragmentOcrBinding
    private lateinit var textRecognizer : TextRecognizer
    lateinit var mCameraSource : CameraSource
    override fun onPermissionsGranted(requestCode: Int, status: Int, lisPermisses: List<ModelPermiss>) {
        if (requestCode == RESULT_CAMERA_REQUEST && status == STATUS_GRANTED){
            mCameraSource.start(binding.sfvCamera.holder)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_ocr,container , false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        startCameraSource()
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            OCRFragment().apply {

            }
    }

    private fun startCameraSource() {
        //  Create text Recognizer
        textRecognizer = TextRecognizer.Builder(activity).build()
        if (!textRecognizer.isOperational) {
            Log.e("Checkkkk","55555")
            return
        }

        //  Init camera source to use high resolution and auto focus
        mCameraSource = CameraSource.Builder(activity, textRecognizer)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedPreviewSize(1280, 1024)
            .setAutoFocusEnabled(true)
            .setRequestedFps(2.0f)
            .build()

        binding.sfvCamera.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

            }

            override fun surfaceDestroyed(p0: SurfaceHolder?) {
                mCameraSource.stop()
            }
            override fun surfaceCreated(p0: SurfaceHolder?) {
                requestAppPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.CAMERA),
                    RESULT_CAMERA_REQUEST)
            }
        })

        textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
                val items = detections.detectedItems

                if (items.size() <= 0) {
                    return
                }

                binding.tvResult.post {
                    val stringBuilder = StringBuilder()
                    for (i in 0 until items.size()) {
                        val item = items.valueAt(i)
                        stringBuilder.append(item.value)
                        stringBuilder.append("\n")
                    }
                    binding.tvResult.text = stringBuilder.toString()
                }
            }
        })
    }
}
