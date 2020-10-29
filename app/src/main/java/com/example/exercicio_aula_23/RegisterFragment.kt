package com.example.exercicio_aula_23

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_user_infos.*

class RegisterFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    var isNameOk = false
    var isEmailOk = false
    var isPhoneOk = false
    var isPasswordOk = false
    var isPasswordEqualOk = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(it).get(MainViewModel::class.java)
        }


        initComponents()
    }

    fun initComponents() {
        var allFilled = true
        tietPhone.addTextChangedListener(MaskWatcher(tietPhone, getString(R.string.phoneMask)))

        btRegisterUser.isEnabled = false;
        tilName.editText?.addTextChangedListener {
            val name = tilName.editText?.text.toString()
            if(name.isBlank()) {
                tilName.error = getString(R.string.errorMessage, getString(R.string.textInputName))
                isNameOk = false;
                isActivated()
            }else {
                tilName.isErrorEnabled = false
                isNameOk = true;
                isActivated()
            }
        }

        tilEmail.editText?.addTextChangedListener{
            val email = tilEmail.editText?.text.toString()
            if(email.isBlank()) {
                tilEmail.error = getString(R.string.errorMessage, getString(R.string.textInputEmail))
                isEmailOk = false;
                isActivated()
            }else {
                tilEmail.isErrorEnabled = false
                isEmailOk = true;
                isActivated()
            }
        }

        tilPhone.editText?.addTextChangedListener {
            val phone = tilPhone.editText?.text.toString()
            if(phone.isBlank()) {
                tilPhone.error = getString(R.string.errorMessage, getString(R.string.textInputPhone))
                isPhoneOk = false
                isActivated()
            }else {
                tilPhone.isErrorEnabled = false
                isPhoneOk = true
                isActivated()
            }
        }
        tilPassword.editText?.addTextChangedListener {
            val password = tilPassword.editText?.text.toString()
            if(password.isBlank()) {
                tilPassword.error = getString(R.string.errorMessage, getString(R.string.textInputPassword))
                isPasswordOk = false
                isActivated()
            }else {
                tilPassword.isErrorEnabled = false
                isPasswordOk = true
                isActivated()
            }
        }

        tilConfirmPassword.editText?.addTextChangedListener {
            val confirmPassword = tilConfirmPassword.editText?.text.toString()
            val password = tilPassword.editText?.text.toString()
            if (confirmPassword != password) {
                tilConfirmPassword.error = getString(R.string.confirmPasswordError)
                isPasswordEqualOk = false
                isActivated()
            }else if(confirmPassword.isBlank()) {
                tilConfirmPassword.error = getString(R.string.errorMessage, getString(R.string.textInputConfirmPassword))
                isPasswordEqualOk = false
                isActivated()
            } else {
                tilConfirmPassword.isErrorEnabled = false
                isPasswordEqualOk = true
                isActivated()
            }
        }


        btRegisterUser.setOnClickListener {
            val name = tilName.editText?.text.toString()
            val email = tilEmail.editText?.text.toString()
            val phone = tilPhone.editText?.text.toString()
            val password = tilPassword.editText?.text.toString()
            val confirmPassword = tilConfirmPassword.editText?.text.toString()

            viewModel.setNewPartner(Partner(
                name = name,
                email = email,
                phone = phone))
            activity?.tlBar?.getTabAt(1)?.select()

        }
    }

//    fun textChangedDefault(editText: EditText, textInput: TextInputLayout, errorString: Int, xpto: Boolean): Boolean {
//        var test = xpto
//        editText.addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//            override fun afterTextChanged(p0: Editable?) {}
//
//            override fun onTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
//                if(text.toString().isBlank()) {
//                    textInput.error = getString(R.string.errorMessage, getString(errorString))
//                }else {
//                    textInput.isErrorEnabled = false
//                    isActivated()
//                    test = true
//                }
//            }
//        })
//        return test
//    }

//    fun confirmPassword(editText1: EditText, editText2: EditText?): Boolean {
//        var isTrue = false
//        editText1.addTextChangedListener(object: TextWatcher {
//            val password = editText2?.text.toString()
//
//            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//            override fun afterTextChanged(p0: Editable?) {}
//
//            override fun onTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
//                if(text.toString() == password) {
//                    isActivated()
//                }
//            }
//        })
//        return isTrue
//    }
    fun isActivated() {
        btRegisterUser.isEnabled = isNameOk && isEmailOk && isPasswordOk && isPhoneOk && isPasswordEqualOk
    }
}