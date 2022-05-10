package com.pim.feature.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.pim.R
import com.pim.databinding.FragmentLoginBinding
import com.pim.feature.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var mBinding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    val compositeDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.submit.setOnClickListener {
            val user = mBinding.edtUser.text.toString()
            val pass = mBinding.edtPass.text.toString()
            val clientCode = mBinding.edtClientUser.text.toString()
            if (user.trim() == "" || user.length <= 3) {
                Snackbar.make(it, resources.getText(R.string.user_is_empty), Snackbar.LENGTH_LONG)
                    .setBackgroundTint(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.purple_700
                        )
                    )
                    .show()
            } else if (pass.trim() == "" || pass.length <= 3) {
                Snackbar.make(it, resources.getText(R.string.pass_is_empty), Snackbar.LENGTH_LONG)
                    .setBackgroundTint(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.purple_700
                        )
                    )
                    .show()
            } else if (clientCode.trim() == "" || clientCode.length <= 3) {
                Snackbar.make(
                    it,
                    resources.getText(R.string.clientcode_is_empty),
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.purple_700
                    )
                )
                    .show()
            } else {
                mBinding.progressCircular.visibility = View.VISIBLE
                viewModel.login(user, pass, clientCode)
                    .doFinally { mBinding.progressCircular.visibility = View.INVISIBLE }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : CompletableObserver {
                        override fun onSubscribe(d: Disposable) {
                            compositeDisposable.add(d)
                        }

                        override fun onComplete() {
                            requireActivity().startActivity(HomeActivity.newInstance(requireActivity()))
                            requireActivity().finish()
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                            Log.e("test", e.message.toString())
                        }
                    })
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {

            }
    }
}