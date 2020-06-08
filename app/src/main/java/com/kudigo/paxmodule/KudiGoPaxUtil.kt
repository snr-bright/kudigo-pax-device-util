package com.kudigo.paxmodule

import android.app.Application
import com.kudigo.pax_device_util.ConfigItem
import com.kudigo.pax_device_util.PaxDeviceUtil

class KudiGoPaxUtil : Application() {

    override fun onCreate() {
        super.onCreate()


        //MARK: set up for payment
        val configItem = ConfigItem("", "", "", "", "")
        PaxDeviceUtil().setUpDeviceForPayment(this, configItem)
    }

}