package me.weishu.epic.samples

import android.content.Context
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.TypedValue
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.startup.AppInitializer
import java.net.NetworkInterface
import me.weishu.epic.samples.databinding.ActivityPrivacyBinding

class PrivacyActivity : AppCompatActivity() {

    @RequiresPermission(allOf = ["android.permission.READ_PHONE_STATE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.LOCAL_MAC_ADDRESS"])
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppInitializer.getInstance(this).initializeComponent(InitializeTestPrivacy::class.java)
        val binding = ActivityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mLocationManager =  getSystemService(Context.LOCATION_SERVICE) as LocationManager;
        val wifiMan =
            applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val outValue = TypedValue()
        theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        ContextCompat.getDrawable(this, outValue.resourceId)

        binding.checkPackage.setOnClickListener {
            kotlin.runCatching {
                //软件安装列表读取检测测试
               packageManager?.getInstalledPackages(0)
            }
        }

        binding.checkDevice.setOnClickListener {
            println(getDeviceId(this))

        }

        binding.checkMac.setOnClickListener {
            val wifiInf = wifiMan.connectionInfo
            val macAddr = wifiInf.macAddress
            println( wifiInf.ssid)
            println(macAddr)

            Class.forName("android.net.wifi.WifiInfo")

            val enm = NetworkInterface.getNetworkInterfaces() ?: return@setOnClickListener
            while (enm.hasMoreElements()) {
                val network: NetworkInterface = enm.nextElement()
                network.name
                network.hardwareAddress
            }
        }


        binding.checkPosition.setOnClickListener {
            kotlin.runCatching {
                val lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                println(lastKnownLocation.toString())
            }
        }
    }

    fun getDeviceId(context: Context): String? {
        val deviceId: String
        deviceId = if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } else {
            val mTelephony = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            if (mTelephony.deviceId != null) {
                mTelephony.deviceId
            } else {
                Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            }
        }
        return deviceId
    }
}