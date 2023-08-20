package com.example.empsysfornec.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.empsysfornec.model.Emp

class DataAddViewModel : ViewModel() {
    val emp: MutableLiveData<Emp> by lazy { MutableLiveData<Emp>(Emp()) }


}