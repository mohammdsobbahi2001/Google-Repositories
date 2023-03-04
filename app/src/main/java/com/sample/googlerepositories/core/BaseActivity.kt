package com.sample.googlerepositories.core

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.sample.googlerepositories.utils.extensions.hideKeyboard

/**
 * abstract activity class, [D] is the binding responsible for [layoutId], [V] is view model
 *
 * @param layoutId is the activity layoutId
 */
abstract class BaseActivity<D : ViewDataBinding, V : BaseVM>(@LayoutRes private val layoutId: Int) :
    AppCompatActivity() {

    protected lateinit var binding: D private set

    protected abstract val viewModel: V

    private lateinit var alertProgressBarDialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        onActivityCreated(savedInstanceState, intent.extras)
    }

    /**
     * handles activity creation, should be overridden
     */
    abstract fun onActivityCreated(savedInstanceState: Bundle?, extras: Bundle?)

    /**
     *  remove focus and hide keyboard when touching parent of a TextInputEditText or TextInputLayout
     *  or EditText when they have focus
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (currentFocus != null && (currentFocus is SearchView.SearchAutoComplete || currentFocus is EditText)) {
                val visibleRectangle = Rect()
                currentFocus!!.getGlobalVisibleRect(visibleRectangle)
                val rawX = event.rawX.toInt()
                val rawY = event.rawY.toInt()
                if (!visibleRectangle.contains(rawX, rawY)) {
                    currentFocus!!.clearFocus()
                    binding.root.hideKeyboard()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    /**
     *  navigates from fragment to other using [navController] and the direction wanted
     *
     *  @param navController is the navigation controller used
     *  @param action is the directions to move to
     */
    internal fun navigateTo(navController: NavController, action: NavDirections) =
        navController.navigate(action)

}