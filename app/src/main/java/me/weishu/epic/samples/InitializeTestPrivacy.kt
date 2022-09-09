package me.weishu.epic.samples

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import androidx.startup.Initializer
import de.robv.android.xposed.DexposedBridge
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import java.net.NetworkInterface


/**
 * 隐私相关同意后的第三方初始化
 */
class InitializeTestPrivacy : Initializer<Unit> {
    @SuppressLint("PrivateApi")
    override fun create(context: Context) {
        XposedBridge.log("初始化检测：${System.currentTimeMillis()}")
        initPackage() //软件安装列表读取检测
        initDeviceId()//检测设备方法
        initPosition()//检测定位方法
        initWifi() //网络等mac
        XposedBridge.log("初始化检测结束：${System.currentTimeMillis()}")
    }

    private fun initPackage() {
        DexposedBridge.findAndHookMethod(
            Class.forName("android.app.ApplicationPackageManager"),
            "getInstalledPackages",
            Int::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    XposedBridge.log("调用getInstalledPackages(int)获取了软件安装列表读取检测")
                }

                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    XposedBridge.log(getMethodStack())
                    super.afterHookedMethod(param)
                }
            }
        )
    }

    private fun initPosition() {
        DexposedBridge.findAndHookMethod(
            LocationManager::class.java,
            "getLastKnownLocation",
            String::class.java,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    XposedBridge.log("调用getLastKnownLocation获取了GPS地址")
                }

                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    XposedBridge.log(getMethodStack())
                    super.afterHookedMethod(param)
                }
            }
        )
    }

    private fun initDeviceId() {
        //检测获取设备信息方法
        DexposedBridge.findAndHookMethod(
            TelephonyManager::class.java,
            "getDeviceId",
            Int::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    XposedBridge.log("调用getDeviceId(int)获取了imei")
                }

                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    XposedBridge.log(getMethodStack())
                    super.afterHookedMethod(param)
                }
            }
        )

        //检测获取设备信息方法
        DexposedBridge.findAndHookMethod(
            TelephonyManager::class.java,
            "getDeviceId",
            Int::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    XposedBridge.log("调用getDeviceId(int)获取了imei")
                }

                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    XposedBridge.log(getMethodStack())
                    super.afterHookedMethod(param)
                }
            }
        )
        //检测imsi获取方法
        DexposedBridge.findAndHookMethod(
            TelephonyManager::class.java,
            "getSubscriberId",
            Int::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    XposedBridge.log("调用getSubscriberId获取了imsi")
                }

                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    XposedBridge.log(getMethodStack())
                    super.afterHookedMethod(param)
                }
            }
        )
    }

    private fun initWifi() {


        //检测低版本系统获取mac地方方法
        XposedHelpers.findAndHookMethod(
            WifiInfo::class.java.name,
            this.javaClass.classLoader,
            "getMacAddress",
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    XposedBridge.log("调用getMacAddress()获取了mac地址")
                }

                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    XposedBridge.log(getMethodStack())
                    super.afterHookedMethod(param)
                }
            }
        )
//
//        DexposedBridge.findAndHookMethod(NetworkInterface::class.java, "getHardwareAddress",   object : XC_MethodHook() {
//            override fun beforeHookedMethod(param: MethodHookParam) {
//                XposedBridge.log("调用NetworkInterface:${param.method.name}")
//            }
//
//            @Throws(Throwable::class)
//            override fun afterHookedMethod(param: MethodHookParam) {
//                XposedBridge.log(getMethodStack())
//                super.afterHookedMethod(param)
//            }
//        })


//        //检测获取mac地址方法
//        DexposedBridge.findAndHookMethod(
//            NetworkInterface::class.java,
//            "getHardwareAddress",
//            object : XC_MethodHook() {
//                override fun beforeHookedMethod(param: MethodHookParam) {
//                    XposedBridge.log("调用getHardwareAddress()获取了mac地址")
//                }
//
//                @Throws(Throwable::class)
//                override fun afterHookedMethod(param: MethodHookParam) {
//                    XposedBridge.log(getMethodStack())
//                    super.afterHookedMethod(param)
//                }
//            }
//        )

//
//        // MAC
//        DexposedBridge.hookAllMethods(WifiManager::class.java, "getMacAddress", object : XC_MethodHook() {
//            override fun beforeHookedMethod(param: MethodHookParam) {
//                XposedBridge.log("调用WifiManager${param.method.name}")
//            }
//
//            @Throws(Throwable::class)
//            override fun afterHookedMethod(param: MethodHookParam) {
//                XposedBridge.log(getMethodStack())
//                super.afterHookedMethod(param)
//            }
//        })
//
//
//        // wifiName
//        DexposedBridge.findAndHookMethod(
//            WifiInfo::class.java,
//            "getWifiName",
//            object : XC_MethodHook() {
//                override fun beforeHookedMethod(param: MethodHookParam) {
//                    XposedBridge.log("调用getWifiName()获取了wifi地址")
//                }
//
//                @Throws(Throwable::class)
//                override fun afterHookedMethod(param: MethodHookParam) {
//                    XposedBridge.log(getMethodStack())
//                    super.afterHookedMethod(param)
//                }
//            }
//        )

//        //SSID
//        XposedHelpers.findAndHookMethod(
//            WifiInfo::class.java,
//            "getSSID",
//            object : XC_MethodHook() {
//                override fun beforeHookedMethod(param: MethodHookParam) {
//                    XposedBridge.log("调用getSSID()获取了wifi地址")
//                }
//
//                @Throws(Throwable::class)
//                override fun afterHookedMethod(param: MethodHookParam) {
//                    XposedBridge.log(getMethodStack())
//                    super.afterHookedMethod(param)
//                }
//            }
//        )

    }

    private fun getMethodStack(): String {
        val stackTraceElements = Thread.currentThread().stackTrace
        val stringBuilder = StringBuilder()
        for (temp in stackTraceElements) {
            stringBuilder.append(
                """
                $temp
                
                """.trimIndent()
            )
        }
        return stringBuilder.toString()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}