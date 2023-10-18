package com.safe.study.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import com.safe.study.R
import com.safe.study.utils.StringUtils
import kotlinx.android.synthetic.main.activity_date_picker_example.*

class DatePickerActivity : AppCompatActivity() {

    private var maxDate: Long = 0
    private var minDate: Long = 0
    private var defaultDate: Long = 0

    private lateinit var context: Context

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_picker_example)
        context = this

        tvMaxDate.setOnClickListener {
            CardDatePickerDialog.builder(this)
                .setTitle("SET MAX DATE")
                .setDefaultTime(maxDate)
                .setOnChoose {
                    maxDate = it
                    tvMaxDate.text = StringUtils.conversionTime(it, "yyyy-MM-dd HH:mm:ss")
                }.build().show()
        }


        btnClearMax.setOnClickListener {
            maxDate = 0L
            tvMaxDate.text = ""
        }

        tvMinDate.setOnClickListener {
            CardDatePickerDialog.builder(this)
                .setTitle("SET MIN DATE")
                .setDefaultTime(minDate)
                .setOnChoose {
                    minDate = it
                    tvMinDate.text = StringUtils.conversionTime(it, "yyyy-MM-dd HH:mm:ss")
                }.build().show()
        }

        btnClearMin.setOnClickListener {
            minDate = 0L
            tvMinDate.text = ""
        }

        tvDefaultDate.setOnClickListener {
            CardDatePickerDialog.builder(this)
                .setTitle("SET DEFAULT DATE")
                .setDefaultTime(defaultDate)
                .setOnChoose {
                    defaultDate = it
                    tvDefaultDate.text =
                        StringUtils.conversionTime(it, "yyyy-MM-dd HH:mm:ss")
                }.build().show()
        }

        btnClearDefault.setOnClickListener {
            defaultDate = 0L
            tvDefaultDate.text = ""
        }

        rgPickerLayout.setOnCheckedChangeListener { group, checkedId ->
            //自定义布局下防止页面出现不必要显示异常，禁止变更显示选择项
            checkYear.isEnabled = checkedId == R.id.radioPickerDefault
            checkMonth.isEnabled = checkedId == R.id.radioPickerDefault
            checkDay.isEnabled = checkedId == R.id.radioPickerDefault
            checkHour.isEnabled = checkedId == R.id.radioPickerDefault
            checkMin.isEnabled = checkedId == R.id.radioPickerDefault
            checkSecond.isEnabled = checkedId == R.id.radioPickerDefault
        }

        btnCardDialogShow.setOnClickListener {
            var displayList: MutableList<Int>? = mutableListOf()
            if (checkYear.isChecked)
                displayList?.add(DateTimeConfig.YEAR)
            if (checkMonth.isChecked)
                displayList?.add(DateTimeConfig.MONTH)
            if (checkDay.isChecked)
                displayList?.add(DateTimeConfig.DAY)
            if (checkHour.isChecked)
                displayList?.add(DateTimeConfig.HOUR)
            if (checkMin.isChecked)
                displayList?.add(DateTimeConfig.MIN)
            if (checkSecond.isChecked)
                displayList?.add(DateTimeConfig.SECOND)


            var model = CardDatePickerDialog.CARD
            if (radioModelCard.isChecked)
                model = CardDatePickerDialog.CARD
            if (radioModelCube.isChecked)
                model = CardDatePickerDialog.CUBE
            if (radioModelStack.isChecked)
                model = CardDatePickerDialog.STACK
            if (radioModelCustom.isChecked)
                model = R.drawable.shape_bg_dialog_custom

            var pickerLayout = 0
            if (radioPickerDefault.isChecked)
                pickerLayout = 0
            if (radioPickerSegmentation.isChecked) {
                displayList = null
                pickerLayout = R.layout.layout_date_picker_segmentation
            }
            if (radioPickerGrid.isChecked) {
                displayList = null
                pickerLayout = R.layout.layout_date_picker_grid
            }


            var dialog = CardDatePickerDialog.builder(context)
                .setTitle("DATE&TIME PICKER")
                .setDisplayType(displayList)
                .setBackGroundModel(model)
//                .setBackGroundModel(if(isDark) R.drawable.shape_bg_dialog_dark else R.drawable.shape_bg_dialog_light)
                .showBackNow(true)
                .setMaxTime(maxDate)
                .setPickerLayout(pickerLayout)
                .setMinTime(minDate)
                .setDefaultTime(defaultDate)
                .setTouchHideable(true)
                .setChooseDateModel(DateTimeConfig.DATE_LUNAR)
                .setWrapSelectorWheel(false)
                .setThemeColor(if (model == R.drawable.shape_bg_dialog_custom) Color.parseColor("#FF8000") else 0)
//                .setAssistColor(Color.parseColor("#DDFFFFFF"))
//                .setDividerColor(Color.parseColor("#222222"))
                .showDateLabel(true)//显示单位
                .showFocusDateInfo(true)//显示日期信息
                .setOnChoose("选择") {
                    btnCardDialogShow.text = "${
                        StringUtils.conversionTime(
                            it,
                            "yyyy-MM-dd HH:mm:ss"
                        )
                    }    ${StringUtils.getWeek(it)}"
                }
                .setOnCancel("关闭") {
                }.build()
            dialog.show()
            //重点 需要在dialog show 方法后
            //得到 BottomSheetDialog 实体，设置其 isHideable 为 fasle
            (dialog as BottomSheetDialog).behavior.isHideable = false
        }
    }
}
