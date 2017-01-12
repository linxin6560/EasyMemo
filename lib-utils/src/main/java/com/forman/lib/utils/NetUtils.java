package com.forman.lib.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class NetUtils {
    // 是否有网络连接
    public static boolean isConnected(Context context) {
        try {
            if (context != null) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // WIFI网络是否可用
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * @param context
     * @return 是否正在使用移动网络流量
     */
    public static boolean isUsingMobileNetwork(Context context) {
        boolean ret = false;
        try {
            ConnectivityManager connetManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] infos = connetManager.getAllNetworkInfo();
            for (int i = 0; i < infos.length && infos[i] != null; i++) {
                if (isMobileType(infos[i].getType()) &&
                        infos[i].isConnected() && infos[i].isAvailable()) {
                    ret = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * @param type
     * @return
     */
    private static boolean isMobileType(int type) {
        switch (type) {
            case ConnectivityManager.TYPE_MOBILE:
            case ConnectivityManager.TYPE_MOBILE_DUN:
            case ConnectivityManager.TYPE_MOBILE_HIPRI:
            case ConnectivityManager.TYPE_MOBILE_MMS:
            case ConnectivityManager.TYPE_MOBILE_SUPL:
                return true;
        }

        return false;
    }

    public static boolean isWifiNet(Context context) {
        if ("wifi".equals(getNetWorkType(context))) {
            return true;
        }
        return false;
    }

    public static boolean isMobileNet(Context context) {
        if ("2G".equals(getNetWorkType(context)) || "3G".equals(getNetWorkType(context)) || "4G".equals(getNetWorkType(context))) {
            return true;
        }
        return false;
    }

    // 获取当前网络连接的类型信息
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 功能：网络类型
     *
     * @return
     * @author: by Fooyou 2014年2月12日 下午4:38:21
     */
    public static String getNetWorkType(Context context) {
        try {
            NetworkInfo netInfo = getNetInfo(context);
            if (netInfo != null) {
                if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return "wifi";
                } else {
                    int type = netInfo.getSubtype();
                    switch (type) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                            return "2G";
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                            return "3G";
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return "4G";
                        default:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NON";
    }

    /**
     * 功能：获取网络信息
     *
     * @param
     * @return
     * @author: by Fooyou 2014年2月6日 下午2:51:09
     */
    private static NetworkInfo getNetInfo(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        return netInfo;
    }

    public static String getNetworkCarrier(Context context) {
        String networkCarrier = "";
        try {
            TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            /** 获取SIM卡的IMSI码 
             * SIM卡唯一标识：IMSI 国际移动用户识别码（IMSI：International Mobile Subscriber Identification Number）是区别移动用户的标志，
             * 储存在SIM卡中，可用于区别移动用户的有效信息。IMSI由MCC、MNC、MSIN组成，其中MCC为移动国家号码，由3位数字组成，
             * 唯一地识别移动客户所属的国家，我国为460；MNC为网络id，由2位数字组成， 
             * 用于识别移动客户所归属的移动网络，中国移动为00，中国联通为01,中国电信为03；
             MSIN为移动客户识别码，采用等长11位数字构成。 
             *唯一地识别国内GSM移动通信网中移动客户。所以要区分是移动还是联通，只需取得SIM卡中的MNC字段即可

             */
            String imsi = telManager.getSubscriberId();
            if (imsi != null) {
                if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                    // 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号 //中国移动
                    networkCarrier = "CMCC";
                } else if (imsi.startsWith("46001")) {
                    // 中国联通
                    networkCarrier = "CUCC";
                } else if (imsi.startsWith("46003")) {
                    // 中国电信
                    networkCarrier = "CTCC";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return networkCarrier;
    }


    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static boolean isGpsOPen(Context context) {
        if (isConnected(context)) {
            return true;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    /**
     * 功能：运营商信息
     */
    public static String getMobileType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telephonyManager.getSimOperator();
        if (operator.length() > 0) {
            if (operator.equals("46000") || operator.equals("46002")) {// 中国移动
                return "cm";
            } else if (operator.equals("46001")) {// 中国联通
                return "cu";
            } else if (operator.equals("46003")) {// 中国电信
                return "ct";
            }
            return operator;
        }
        return "unknow";
    }


    // 获取网络状态 network网络状态，接收1和2 1=WLAN 2=WIFI
    public static int getAPNType(Context context) {

        int netType = 1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }

        int nType = networkInfo.getType();

        if (nType == ConnectivityManager.TYPE_MOBILE) {
            netType = 1;
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 2;
        }
        return netType;
    }

    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }
}
