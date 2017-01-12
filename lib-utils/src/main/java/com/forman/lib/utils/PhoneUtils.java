package com.forman.lib.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * 手机基础信息
 */
public class PhoneUtils {

    public static boolean isInstall(Context context, String packageName) {
        boolean isInstall = false;
        try {
            PackageInfo packageInfo;
            try {
                packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                packageInfo = null;
                e.printStackTrace();
            }
            if (packageInfo == null) {
                isInstall = false;
            } else {
                isInstall = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isInstall;
    }

    public static void copy(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }
    /**
     * 获取手机mac地址
     *
     * @return
     */
    public static String getMacAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            return wifiInfo.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // 手机型号
    public static String getPhoneModel() {
        try {
            return android.os.Build.MODEL;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //手机品牌
    public static String getPhoneBrand() {
        try {
            return android.os.Build.BRAND;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static int getSDK_INT() {
        try {
            return android.os.Build.VERSION.SDK_INT;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getSdkVersion() {
        try {
            return android.os.Build.VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // 获取网络运营商名称
    public static String getNetworkOperatorName(Context context) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return manager.getNetworkOperatorName();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getIMSI(Context context) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imsi = manager.getSubscriberId();
            if (TextUtils.isEmpty(imsi)) {
                return "";
            }
            return imsi;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getIMEI(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = manager.getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            return getLocalMacAddressFromIp();
        } else {
            return deviceId;
        }
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            String versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取没有点号的版本名
     *
     * @return
     */
    public static String getVersionNameWithoutDot(Context context) {
        try {
            String versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            String[] versionArr = versionName.split("\\.");
            if (versionArr.length == 2) {//1.2-->0120 || 2.0-->0200
                if (versionArr[0].length() == 2) {
                    versionName = versionArr[0] + versionArr[1] + "0";
                } else {
                    versionName = "0" + versionArr[0] + versionArr[1] + "0";
                }
            } else if (versionArr.length == 3 || versionArr.length == 4) {//1.2.1-->0121 || 1.4.5.5-->0145
                if (versionArr[0].length() == 2) {
                    versionName = versionArr[0] + versionArr[1] + versionArr[2];
                } else {
                    versionName = "0" + versionArr[0] + versionArr[1] + versionArr[2];
                }
            }
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 得到当前的手机网络类型
     *
     * @param context
     * @return
     */
    public static String getCurrentNetType(Context context) {
        String type = "";
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                type = "null";
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                type = "wifi";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                int subType = info.getSubtype();
                if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                        || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                    type = "2g";
                } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                    type = "3g";
                } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                    type = "4g";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

    /**
     * 获取手机上前100个非系统应用APP
     *
     * @return
     */
    public static String getAllApp(Context context) {
        try {
            StringBuffer result = new StringBuffer("");
            List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
            PackageInfo packageInfo;
            String appName = "";
            String packageName = "";
            int size = 0;
            for (int i = 0; packages != null && i < packages.size(); i++) {
                packageInfo = packages.get(i);
                appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                packageName = packageInfo.applicationInfo.packageName;
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    size++;
                    result.append(appName + "|" + packageName + ",");
                }
                if (size == 100) {
                    break;
                }
            }
            if (!TextUtils.isEmpty(result)) {
                result.substring(0, result.length() - 1);
            }
            return result.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private static String getLocalMacAddressFromIp() {
        try {
            byte[] mac;
            NetworkInterface ne = NetworkInterface.getByInetAddress(InetAddress.getByName(getLocalIpAddress()));
            mac = ne.getHardwareAddress();
            return byte2hex(mac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer(b.length);
        String stmp = "";
        int len = b.length;
        for (int n = 0; n < len; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs.append("0").append(stmp);
            else {
                hs = hs.append(stmp);
            }
        }
        return String.valueOf(hs);
    }

    private static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "";
    }

}
