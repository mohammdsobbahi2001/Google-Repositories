package com.sample.googlerepositories.ui.repositoriesdetails

import android.os.Bundle
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sample.googlerepositories.R
import com.sample.googlerepositories.core.BaseFragment
import com.sample.googlerepositories.databinding.FragmentRepositoryDetailsBinding

/**
 * Fragment class for the repository details screen.
 */
class RepositoryDetailsFragment :
    BaseFragment<FragmentRepositoryDetailsBinding, RepositoryDetailsVM>(R.layout.fragment_repository_details) {

    override val viewModel: RepositoryDetailsVM by viewModels()

    private val args: RepositoryDetailsFragmentArgs by navArgs()

    override fun onFragmentCreated(savedInstanceState: Bundle?) {

        // handle back press
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }

        // handle imgBack press
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()

        }

        // set the data for binding and load image
        binding.repository = args.repository
        Glide.with(binding.root.context).load(args.repository.owner?.avatarUrl)
            .into(binding.imgAvatar)

    }

}