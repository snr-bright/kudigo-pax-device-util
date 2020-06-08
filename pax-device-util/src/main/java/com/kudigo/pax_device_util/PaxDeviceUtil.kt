package com.kudigo.pax_device_util

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.ContextCompat
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.emv.pax.services.POSDeviceImpl
import com.interswitchng.smartpos.shared.models.core.POSConfig

class PaxDeviceUtil {


    //MARK: set up device for payment
    /**
     * @author, Bright Ahedor
     * @param application the application instance
     * @param configItem the object holding all configuration items
     * @param printableLogo a resource to be printed on the receipt
     * @param projectHasRealm does the project have realm, default is False
     */
    fun setUpDeviceForPayment(
        application: Application,
        configItem: ConfigItem,
        printableLogo: Int? = null,
        projectHasRealm: Boolean = false
    ) {
        val configBase = POSConfig(
            configItem.alias,
            configItem.clientId,
            configItem.clientSecret,
            configItem.merchantCode,
            configItem.phoneNumber
        )
        val device = POSDeviceImpl.create(application)
        printableLogo?.let {
            val logo = ContextCompat.getDrawable(application, it)!!
            device.setCompanyLogo(drawableToBitmap(logo))
        }

        //initialize POSConfig
        if (!isPaymentDevice()) {
            IswPos.setupTerminal(application, MockPosDevice(), configBase, projectHasRealm)
        } else {
            IswPos.setupTerminal(application, device, configBase, projectHasRealm)
        }
    }

    //MARK: convert drawable to bitmap
    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    //MARK: get the active device
    /**
     * @return, return if the device is kudiGo configured payment
     */
    private fun isPrintingDevice(): Boolean {
        return when (getDeviceName()) {
            "PAX A930" -> true
            "PAX A920" -> true
            else -> false
        }
    }

    //MARK: is device configurable for payment
    private fun isPaymentDevice(): Boolean {
        return when (getDeviceName()) {
            "PAX A930" -> true
            "PAX A920" -> true
            else -> false
        }
    }

    //MARK: get the device name
    private fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else capitalize(manufacturer) + " " + model
    }

    private fun capitalize(s: String?): String {
        if (s == null || s.isEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first) + s.substring(1)
        }
    }
}