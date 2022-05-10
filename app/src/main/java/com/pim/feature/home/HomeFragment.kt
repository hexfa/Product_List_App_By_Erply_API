package com.pim.feature.home

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pim.R
import com.pim.data.InfoContainer
import com.pim.databinding.FragmentHomeBinding
import com.pim.feature.PaginationScrollListener
import com.pim.feature.login.LoginActivity
import com.pim.feature.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var mBinding: FragmentHomeBinding
    private val loginViewModel by viewModels<LoginViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()
    private val pageStart: Int = 0
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var currentPage: Int = pageStart
    private var isSearch: Boolean = false
    private val adapter = ProductAdapter()
    private val searchAdapter = SearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        fetchData()
    }

    private fun init() {
        mBinding.logout.setOnClickListener {
            loginViewModel.bakeToLogin()
            requireActivity().startActivity(LoginActivity.newInstance(requireActivity()))
            requireActivity().finish()
        }


        when {
            requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT -> {
                mBinding.recyclerSearch.layoutManager = GridLayoutManager(
                    context, 2, GridLayoutManager.VERTICAL, false
                )
                mBinding.recycler.layoutManager = GridLayoutManager(
                    context, 2, GridLayoutManager.VERTICAL, false
                )

            }
            homeViewModel.isTablet(requireContext()) -> {
                mBinding.recyclerSearch.layoutManager = GridLayoutManager(
                    context, 4, GridLayoutManager.VERTICAL, false
                )
                mBinding.recycler.layoutManager = GridLayoutManager(
                    context, 4, GridLayoutManager.VERTICAL, false
                )
            }
            else -> {
                mBinding.recyclerSearch.layoutManager = GridLayoutManager(
                    context, 3, GridLayoutManager.VERTICAL, false
                )
                mBinding.recycler.layoutManager = GridLayoutManager(
                    context, 3, GridLayoutManager.VERTICAL, false
                )
            }
        }

        mBinding.recyclerSearch.adapter = searchAdapter
        mBinding.recycler.adapter = adapter
        mBinding.recycler.addOnScrollListener(object :
            PaginationScrollListener(mBinding.recycler.layoutManager as GridLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 10
                InfoContainer.token?.let {

                    homeViewModel.getProductList(
                        it,
                        currentPage.toString(),
                        "10"
                    )
                }
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
        mBinding.icSearch.setOnClickListener {
            search(it)
        }
        mBinding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchWithIme()
            }
            true
        }
    }

    private fun searchWithIme() {
        homeViewModel.searchArray.clear()
        val search = mBinding.editTextSearch.text.toString()
        Log.d("ime",search)

        mBinding.icSearch.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_close_24
            )
        )
        isSearch = true
        mBinding.recycler.visibility = View.GONE
        mBinding.recyclerSearch.visibility = View.VISIBLE
        if (search.toIntOrNull() == null) {
            homeViewModel.getProductListSearch(
                "[[\"name.en\", \"contains\", \"${search}\" ]]",
                InfoContainer.token.toString(),
                "0",
                "60"
            )
        } else {
            homeViewModel.getProductListSearch(
                "[[\"name.en\", \"contains\", \"${search}\" ]]",
                InfoContainer.token.toString(),
                "0",
                "60"
            )
            homeViewModel.getProductListSearch(
                "[[\"code2\", \"=\", \"${search}\" ]]",
                InfoContainer.token.toString(),
                "0",
                "60"
            )
            homeViewModel.getProductListSearch(
                "[[\"code\", \"=\", \"${search}\" ]]",
                InfoContainer.token.toString(),
                "0",
                "60"
            )
        }
    }

    private fun search(it: View) {
        if (isSearch) {
            mBinding.editTextSearch.setText("")
            mBinding.recycler.visibility = View.VISIBLE
            mBinding.recyclerSearch.visibility = View.GONE
            mBinding.icSearch.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_search_24
                )
            )
            homeViewModel.searchArray.clear()
            isSearch = false
        } else {
            homeViewModel.searchArray.clear()
            val search = mBinding.editTextSearch.text.toString()
            if (search.trim() == "" || search.length < 2) {
                Snackbar.make(
                    it,
                    resources.getText(R.string.nothing_searched),
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.purple_700
                    )
                )
                    .show()
            } else {
                mBinding.icSearch.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_close_24
                    )
                )
                isSearch = true
                mBinding.recycler.visibility = View.GONE
                mBinding.recyclerSearch.visibility = View.VISIBLE
                if (search.toIntOrNull() == null) {
                    homeViewModel.getProductListSearch(
                        "[[\"name.en\", \"contains\", \"${search}\" ]]",
                        InfoContainer.token.toString(),
                        "0",
                        "60"
                    )
                } else {
                    homeViewModel.getProductListSearch(
                        "[[\"name.en\", \"contains\", \"${search}\" ]]",
                        InfoContainer.token.toString(),
                        "0",
                        "60"
                    )
                    homeViewModel.getProductListSearch(
                        "[[\"code2\", \"=\", \"${search}\" ]]",
                        InfoContainer.token.toString(),
                        "0",
                        "60"
                    )
                    homeViewModel.getProductListSearch(
                        "[[\"code\", \"=\", \"${search}\" ]]",
                        InfoContainer.token.toString(),
                        "0",
                        "60"
                    )
                }
            }
        }
    }

    private fun fetchData() {
        homeViewModel.firstPageList.observe(viewLifecycleOwner) {
            mBinding.progressBar.visibility = View.GONE
            adapter.addAll(it)
            adapter.addLoadingFooter()
        }
        homeViewModel.nextPageList.observe(viewLifecycleOwner) {
            adapter.removeLoadingFooter()
            isLoading = false
            adapter.addAll(it)
            adapter.addLoadingFooter()
        }
        homeViewModel.searchList.observe(viewLifecycleOwner) {
            searchAdapter.setData(homeViewModel.searchArray)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }

}