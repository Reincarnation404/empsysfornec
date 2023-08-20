package com.example.empsysfornec.controller

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.empsysfornec.R
import com.example.empsysfornec.SQLiteHelper
import com.example.empsysfornec.databinding.FragmentDataAddBinding
import com.example.empsysfornec.viewModel.DataAddViewModel

class DataAddFragment : Fragment() {

    private lateinit var binding: FragmentDataAddBinding
    private lateinit var dbHelper: SQLiteHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().title = "新增資料"
        binding = FragmentDataAddBinding.inflate(inflater, container, false)
        val viewModel: DataAddViewModel by viewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding){
            dbHelper = SQLiteHelper(requireContext())

            tietDataAddDept.setOnClickListener {
                showDept()
            }

            tietDataAddJobTitle.setOnClickListener {
                showJobTitle()
            }

            btDataAddSave.setOnClickListener {
                viewModel?.emp?.value.run {

                    if (!inputValid()) {
                        Toast.makeText(requireContext(), "請確認資料格式", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    //將職稱轉成Int存回db
                    val ch_jobtitle = tietDataAddJobTitle.text.toString()
                    val jobtitle_id: Int = when (ch_jobtitle) {
                        "部長" -> 1
                        "經理" -> 2
                        "員工" -> 3
                        else ->
                            return@setOnClickListener
                    }
                    this?.jobTitle = jobtitle_id

                    //抓textview的文字 存回資料庫
                    val ch_dept = tietDataAddDept.text.toString()
                    this?.dept = ch_dept


                    if (this != null) {
                        dbHelper.insertData(this)
                        Toast.makeText(requireContext(), "資料新增成功", Toast.LENGTH_LONG).show()
                        println(this.toString())
                        Navigation.findNavController(it).navigate(R.id.dataFragment)
                    }
                }

            }

            btDataAddCancel.setOnClickListener {
                Navigation.findNavController(it).popBackStack()
            }
        }

    }

    private fun inputValid(): Boolean {
        var valid = true
        with(binding) {
            val e = viewModel?.emp?.value

            val eName = e?.name?.trim()
            val eExNum = e?.exNum?.trim()
            val eDept = tietDataAddDept.text.toString()
            val eJobTitle = tietDataAddJobTitle.text.toString()


            val exNumRegex = Regex("^\\d{4}\$")


            if (eExNum != null) {
                if (!eExNum.matches(exNumRegex)) {
                    tietDataAddExNumber.error = getString(R.string.errDataError)
                    valid = false
                }
            }else{
                tietDataAddExNumber.error = getString(R.string.errDataEmpty)
                valid = false
            }

            if (eName == null || eName.isEmpty()) {
                tietDataAddName.error = getString(R.string.errDataEmpty)
                valid = false
            }

            if (eDept.isEmpty()){
                tietDataAddDept.error = getString(R.string.errDataEmpty)
                valid = false
            }
            if (eJobTitle.isEmpty()){
                tietDataAddJobTitle.error = getString(R.string.errDataEmpty)
                valid = false
            }
        }
        return valid
    }

    private fun showJobTitle() {
        var choice = arrayOf("部長","經理","員工")
        var selectItem = -1


        AlertDialog.Builder(view?.context)
            // 設定標題文字
            .setTitle("選擇職位")
            .setSingleChoiceItems(choice, selectItem) { _, position ->
                selectItem = position
            }
            .setPositiveButton(R.string.save) { _, _ ->
                if (selectItem != -1) {
                    val selectedJobTitle = choice[selectItem]
                    binding.tietDataAddJobTitle.text = selectedJobTitle
                }
            }
            // false代表要點擊按鈕方能關閉，預設為true
            .setCancelable(true)
            .show()
    }

    private fun showDept() {
        var choice = arrayOf("財務部","技術部","人力資源部")
        var selectItem = -1


        AlertDialog.Builder(view?.context)
            // 設定標題文字
            .setTitle("選擇部門")
            .setSingleChoiceItems(choice, selectItem) { _, position ->
                selectItem = position
            }
            .setPositiveButton(R.string.save) { _, _ ->
                if (selectItem != -1) {
                    val selectedDept = choice[selectItem]
                    binding.tietDataAddDept.text = selectedDept
                }
            }
            // false代表要點擊按鈕方能關閉，預設為true
            .setCancelable(true)
            .show()
    }


}