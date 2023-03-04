package com.sample.googlerepositories.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

/**
 * abstract fragment class, [D] is the binding responsible for [layoutId], [V] is view model
 *
 * @param layoutId is the fragment layoutId
 */
abstract class BaseFragment<D : ViewDataBinding, V : BaseVM>(@LayoutRes private val layoutId: Int) :
    Fragment() {

    protected lateinit var binding: D private set

    protected abstract val viewModel: V

    private val baseActivity by lazy { requireActivity() as BaseActivity<*, *> }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentCreated(savedInstanceState)
    }

    /**
     * handles fragment creation, should be overridden
     */
    abstract fun onFragmentCreated(savedInstanceState: Bundle?)

    /**
     *  navigates from fragment to other based on the direction wanted
     *
     *  @param action is the directions to move to
     */
    protected fun navigateTo(action: NavDirections) = findNavController().navigate(action)

}