package com.example.empsysfornec.controller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.empsysfornec.MainActivity
import com.example.empsysfornec.R
import com.example.empsysfornec.SQLiteHelper
import com.example.empsysfornec.adapter.DataAdapter
import com.example.empsysfornec.databinding.FragmentDataBinding
import com.example.empsysfornec.model.Emp
import com.example.empsysfornec.viewModel.DataViewModel


class DataFragment : Fragment() {
    private lateinit var binding: FragmentDataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().title = "資料列表"
        binding = FragmentDataBinding.inflate(inflater, container, false)
        val viewModel: DataViewModel by viewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding){
            getData()
            rvEmpData.layoutManager = LinearLayoutManager(requireContext())
            viewModel?.emps?.value.run {
                println("all : ${viewModel?.emps?.value.toString()}")
                viewModel?.emps?.observe(viewLifecycleOwner) { emps ->
                    // adapter為null要建立新的adapter；之後只要呼叫updateEmps(emps)即可
                    if (rvEmpData.adapter == null) {
                        rvEmpData.adapter = DataAdapter(emps)
                        println("all 2 : ${viewModel?.emps?.value.toString()}")
                    }else {
                        (rvEmpData.adapter as DataAdapter).updateEmps(emps)
                        println("this : ${viewModel?.emps?.value.toString()}")
                    }
                }
            }

            val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedDept = parent?.getItemAtPosition(position).toString()
                    viewModel?.dataFilter(selectedDept)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
            /* 註冊OnItemSelectedListener監聽器之前先呼叫setSelection()，
                不僅可以設定一開始預選項目，還可避免Spinner一開始就執行
                OnItemSelectedListener.onItemClick()的問題 */
            spinnerDept.setSelection(0, true)

            spinnerDept.onItemSelectedListener = onItemSelectedListener

            fabDataAdd.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.dataAddFragment)
            }

        }
    }

    private fun getData() {
        with(binding.viewModel){
            this?.emps?.value =  (requireActivity() as MainActivity).dbHelper!!.getAllData()
            this?.emplist = this?.emps?.value?.toMutableList()!!
        }

    }

}