package m.vk.permissions

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.squareup.otto.Subscribe

open class StatedFragment : Fragment() {

    private var savedState: Bundle? = null

    private val mActivityResultSubscriber = object : Any() {
        @Subscribe
        fun onActivityResultReceived(event: ActivityResultEvent) {
            val requestCode = event.requestCode
            val resultCode = event.resultCode
            val data = event.data
            onActivityResult(requestCode, resultCode, data)
        }
    }

    init {
        if (arguments == null)
            arguments = Bundle()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Restore State Here
        if (!restoreStateFromArguments()) {
            // First Time, Initialize something here
            onFirstTimeLaunched()
        }
    }

    /**
     * Called when the fragment is launched for the first time.
     * In the other words, fragment is now recreated.
     */

    protected fun onFirstTimeLaunched() {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save State Here
        saveStateToArguments()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Save State Here
        saveStateToArguments()
    }

    ////////////////////
    // Don't Touch !!
    ////////////////////

    private fun saveStateToArguments() {
        if (view != null)
            savedState = saveState()
        if (savedState != null) {
            val b = arguments
            b?.putBundle("internalSavedViewState8954201239547", savedState)
        }
    }

    ////////////////////
    // Don't Touch !!
    ////////////////////

    private fun restoreStateFromArguments(): Boolean {
        val b = arguments
        if (b != null) {
            savedState = b.getBundle("internalSavedViewState8954201239547")
            if (savedState != null) {
                restoreState()
                return true
            }
        }
        return false
    }

    /////////////////////////////////
    // Restore Instance State Here
    /////////////////////////////////

    private fun restoreState() {
        savedState?.let { onRestoreState(it) }
    }


    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  This is called after [.onCreateView]
     * and before [.onViewStateRestored].
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */

    private fun onRestoreState(savedInstanceState: Bundle) {

    }

    //////////////////////////////
    // Save Instance State Here
    //////////////////////////////

    private fun saveState(): Bundle {
        val state = Bundle()
        // For Example
        //state.putString("text", tv1.getText().toString());
        onSaveState(state)
        return state
    }

    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to [.onRestoreState].
     *
     *
     * This corresponds to [ Activity.onSaveInstanceState(Bundle)][Activity.onSaveInstanceState] and most of the discussion there
     * applies here as well.  Note however: *this method may be called
     * at any time before [.onDestroy]*.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */

    private fun onSaveState(outState: Bundle) {

    }

    override fun onStart() {
        super.onStart()
        ActivityResultBus.getInstance().register(mActivityResultSubscriber)
    }

    override fun onStop() {
        super.onStop()
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber)
    }

}
