package com.example.empsysfornec.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.empsysfornec.R
import com.example.empsysfornec.databinding.FragmentDataItemBinding
import com.example.empsysfornec.model.Emp
import com.example.empsysfornec.viewModel.DataViewModel
import com.example.empsysfornec.viewModel.EmpViewModel

class DataAdapter (private var emps: List<Emp>): RecyclerView.Adapter<DataAdapter.DataViewHolder>(){

    /**
     * 更新資料列表內容
     * @param emps 新的資料列表
     */
    fun updateEmps(emps: List<Emp>) {
        this.emps = emps
        notifyDataSetChanged()
    }

    class DataViewHolder(val itemViewBinding: FragmentDataItemBinding):
        RecyclerView.ViewHolder(itemViewBinding.root)

    //設定layout跟viewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemViewBinding = FragmentDataItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        itemViewBinding.viewModel = EmpViewModel()
        // 設定lifecycleOwner方能監控LiveData資料變化，layout檔案的view才會更新顯示
        itemViewBinding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return DataViewHolder(itemViewBinding)
    }

    //資料總數
    override fun getItemCount(): Int {
        return emps.size
    }

    //滾動recyclerView時被調用
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        //根據位置從資料源中獲取對應的資料
        val emp = emps[position]
        with(holder){
            // 將欲顯示的emp物件指派給LiveData，就會自動更新layout檔案的view顯示
            itemViewBinding.viewModel?.emp?.value = emp

            // 設置背景顏色
            // 要有else 不然畫面被回收使用的時候底色會出錯
            if (itemViewBinding.viewModel?.emp?.value!!.jobTitle == 1) {
                itemViewBinding.DataList.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.yellow))
            }else{
                itemViewBinding.DataList.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
            }

        }
    }



}