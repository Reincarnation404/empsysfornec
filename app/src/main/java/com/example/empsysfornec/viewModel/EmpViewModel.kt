package com.example.empsysfornec.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.empsysfornec.model.Emp

/**
 * 單一員工資料處理
 */
class EmpViewModel: ViewModel() {
    val emp: MutableLiveData<Emp> by lazy { MutableLiveData<Emp>() }
    val detailVisible: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }

    // 點擊RecyclerView選項後改變 顯示/隱藏 狀態
    fun onClick() {
        detailVisible.value = !detailVisible.value!!
    }

    //轉換jobTitle成String以顯示於view上
    fun titleToString():String? {
        if (emp.value?.jobTitle == 1){
            return "部長"
        }
        if (emp.value?.jobTitle == 2){
            return "經理"
        }
        if (emp.value?.jobTitle == 3){
            return "員工"
        }
        return null
    }

}