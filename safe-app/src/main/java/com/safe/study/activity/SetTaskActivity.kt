package com.safe.study.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import com.safe.study.R
import com.safe.study.base.BaseSimpleActivity
import com.safe.study.service.MyIntentService
import com.safe.study.service.MyService
import com.safe.study.task.TaskAdapter
import com.safe.study.task.TaskInfo
import com.safe.study.utils.StringUtils
import kotlinx.android.synthetic.main.activity_date_picker_example.*

class SetTaskActivity : BaseSimpleActivity() {

    private var maxDate: Long = 0
    private var minDate: Long = 0
    private var defaultDate: Long = 0

    private lateinit var context: Context
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: TaskAdapter? = null
    private val mUrls: MutableList<TaskInfo> = mutableListOf()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_task)
        context = this
        mRecyclerView = findViewById(R.id.recyclerView)

        mAdapter = TaskAdapter(mUrls)
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        mRecyclerView!!.adapter = mAdapter

        btnCardDialogShow.setOnClickListener {
            var displayList: MutableList<Int>? = mutableListOf()
            displayList?.add(DateTimeConfig.YEAR)
            displayList?.add(DateTimeConfig.MONTH)
            displayList?.add(DateTimeConfig.DAY)
            displayList?.add(DateTimeConfig.HOUR)
            displayList?.add(DateTimeConfig.MIN)
            displayList?.add(DateTimeConfig.SECOND)

            var model = CardDatePickerDialog.CARD
//            model = CardDatePickerDialog.CUBE
//            model = CardDatePickerDialog.STACK
//            model = R.drawable.shape_bg_dialog_custom

            var pickerLayout = 0
//            if (radioPickerSegmentation.isChecked) {
//                displayList = null
//                pickerLayout = R.layout.layout_date_picker_segmentation
//            }
//            if (radioPickerGrid.isChecked) {
//                displayList = null
//                pickerLayout = R.layout.layout_date_picker_grid
//            }

            var dialog = CardDatePickerDialog.builder(context)
//                .setTitle("DATE&TIME PICKER")
                .setDisplayType(displayList)
                .setBackGroundModel(model)
//                .setBackGroundModel(if(isDark) R.drawable.shape_bg_dialog_dark else R.drawable.shape_bg_dialog_light)
                .showBackNow(false)//回到此刻
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
                .setDisplayType(mutableListOf<Int>(DateTimeConfig.DAY,DateTimeConfig.HOUR,DateTimeConfig.MIN))
                .setOnChoose("选择") {
                    Log.d("SetTaskActivity", "onChooseListener: time is = " + it)
                    var intent = Intent(this, MyService::class.java)
                    intent.putExtra("task_time", it)
                    startService(intent)
//                    btnCardDialogShow.text = "${StringUtils.conversionTime(it,"yyyy-MM-dd HH:mm:ss")}${StringUtils.getWeek(it)}"
                    mUrls.add(
                        TaskInfo(
                            "2222",
                            it,
                            "${StringUtils.conversionTime(it,"yyyy-MM-dd HH:mm:ss")}  ${StringUtils.getWeek(it)}",
                            "关机"
                        )
                    )
                    mAdapter!!.setData(mUrls)
                    mAdapter!!.notifyDataSetChanged()
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
