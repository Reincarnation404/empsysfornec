package com.example.empsysfornec.viewModel


import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.empsysfornec.R
import com.example.empsysfornec.SQLiteHelper
import com.example.empsysfornec.model.Emp
import java.util.Locale.filter

/**
 * 員工列表資料處理
 */
class DataViewModel: ViewModel() {

    // 原始員工列表
    var emplist = mutableListOf<Emp>()
    // 受監控的LiveData，一旦指派新值就會更新員工列表畫面
    val emps: MutableLiveData<List<Emp>> by lazy { MutableLiveData<List<Emp>>() }



    fun dataFilter(selectedDept: String?){
        if (selectedDept == "全部") {
            emps.value = emplist
        } else {
            val filteredEmpList = mutableListOf<Emp>()
            emplist.forEach { emps ->
                if (emps.dept == selectedDept) {
                    filteredEmpList.add(emps)
                }
            }
            emps.value = filteredEmpList
        }
    }
}