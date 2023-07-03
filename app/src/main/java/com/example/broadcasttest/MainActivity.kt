package com.example.broadcasttest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

//动态注册
class MainActivity : AppCompatActivity() {

    private lateinit var timeChangeReceiver: TimeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
//            发送自定义广播
            val intent = Intent("com.example.broadcasttest.MY_BROADCAST")
            intent.setPackage(packageName)
//            发送标准广播
//            sendBroadcast(intent, null)
//            发送有序广播
            sendOrderedBroadcast(intent, null)
        }
        val intentFilter = IntentFilter()
//        BroadcastReceiver 想监听什么，就在这里添加 action。当系统时间发生变化时，发出的就是下面这个广播
        intentFilter.addAction("android.intent.action.TIME_TICK")
        timeChangeReceiver = TimeChangeReceiver()
//        注册
        registerReceiver(timeChangeReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
//        动态注册的 BroadcastReceiver 一定要取消注册
        unregisterReceiver(timeChangeReceiver)
    }

    //    TimeChangeReceiver 实现了 BroadcastReceiver 的 onReceive 方法，这样
    //    当系统时间每分钟发生变化时，该方法会被执行
    inner class TimeChangeReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context, "Time has changed", Toast.LENGTH_SHORT).show()
        }

    }

}
