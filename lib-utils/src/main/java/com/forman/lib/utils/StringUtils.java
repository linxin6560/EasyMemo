package com.forman.lib.utils;

import android.graphics.Color;
import android.net.Uri;

import com.forman.lib.utils.log.Log;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/******************************************************
 * @author wtj
 * @version 1.0.1 2013-08-26修改电话号码的验证规则：开放所有18(0-9)的号码
 ****************************************************/
public class StringUtils {

    /**
     * 去除图片?后缀
     *
     * @param url
     * @return
     */
    public static String subImgUrl(String url) {
        if (url == null || url.length() == 0) {
            return "";
        } else {
            if (url.contains("?")) {
                String[] split = url.split("\\?");
                return split[0];
            } else {
                return url;
            }
        }
    }

    // 判断一个字符串是否都为数字
    public static boolean isDigit(String strNum) {
        return strNum.matches("[0-9]{1,}");
    }

    //过滤特殊字符
    public static String specialFilter(String content) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(content);
        return m.replaceAll("").trim();
    }

    /**
     * 获取非null的字符串
     */
    public static String get(String text) {
        if (StringUtils.isEmpty(text)) {
            return "";
        }
        return text;
    }


    public static String HtmlText(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

			/* 空格 —— */
            // p_html = Pattern.compile("\\ ", Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            // htmlStr = htmlStr.replaceAll(" ","");

            textStr = htmlStr;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0 || s.equals("null");
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }


    /**
     * 判断是不是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断含有多少个数字
     *
     * @param str
     * @return
     */
    public static int haveNumeric(String str) {
        Matcher match = Pattern.compile("\\d").matcher(str);
        int count = 0;
        while(match.find())
            count++;
        return count;
    }

    /**
     * 判断是不是浮点数字
     *
     * @param str
     * @return
     */
    public static boolean isDecimal(String str) {
        if (str == null || "".equals(str))
            return false;
        Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static String map2StringStat(Map<String, String> map) {
        String splitstr = "&";
        if (map == null || map.isEmpty())
            return "";

        String str = "";
        for (Entry<String, String> entry : map.entrySet()) {
            str += entry.getKey() + "=" + entry.getValue() + splitstr;
        }
        return str.substring(0, str.length() - splitstr.length());
    }

    public static HashMap<String, String> string2MapStat(String statStr) {
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            if (isEmpty(statStr)) {
                return map;
            }
            String[] strs = statStr.split("&");
            for (String s : strs) {
                String[] mapstr = s.split("=");
                if (mapstr.length != 2)
                    continue;
                mapstr[1] = URLDecoder.decode(mapstr[1]);
                map.put(mapstr[0], mapstr[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String map2StringBySplit(Map<String, String> map, String splitstr) {
        if (map == null || map.isEmpty())
            return "";

        String str = "";
        for (Entry<String, String> entry : map.entrySet()) {
            str += entry.getKey().toString() + splitstr;
        }
        return str.substring(0, str.length() - splitstr.length());
    }

    /**
     * 是否是email格式
     *
     * @param email
     * @return true 正确的格式 false 错误的格式
     */
    public static boolean isEmail(String email) {
//		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 是否是固话或者手机
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        String str = "^((1\\d{10})|(0(10|2[0-5789]|\\d{3})\\d{7,8}))$";// 手机号
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 是否是手机号
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNum(String phoneNumber) {
        boolean isValid = false;
        CharSequence inputStr = phoneNumber;
        //正则表达式
        String phone = "^1[34578]\\d{9}$";

        Pattern pattern = Pattern.compile(phone);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 是否是身份证号
     *
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        boolean isValid = false;
        CharSequence inputStr = idCard;
        //正则表达式
        String str = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";

        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 判断是否有特殊字符
     *
     * @param s
     * @return false 包含有特殊字符 true 不包含特殊字符
     */
    public static boolean isSpecialString(String s) {
        String str = "^[a-zA-Z0-9_\u0391-\uFFE5]+$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 计算字符的长度 支持中文
     *
     * @param value
     * @return string长度
     */
    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * g根据限定的长度获取字符串
     *
     * @param value       字符串
     * @param limitLength 限定的长度。 (字符长度，中文2，字母1）
     * @return
     */
    public static String getLimitStringlength(String value, int limitLength) {
        if (length(value) <= limitLength) {
            return value;
        }
        try {
            int valueLength = 0;
            String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
            for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
                String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
                if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                    valueLength += 2;
                } else {
                /* 其他字符长度为1 */
                    valueLength += 1;
                }
                Log.e("valueLength=" + valueLength);
                if (valueLength >= limitLength) {
                    return value.substring(0, i + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value.substring(0, limitLength / 2);
    }

    public static String getDoubleString(double d) {
        DecimalFormat df = new DecimalFormat("########0.00");
        return df.format(d);
    }

    public static double getRound(double dSource) {
        // BigDecimal的构造函数参数类型是double
        double round = 0;
        try {
            BigDecimal deSource = new BigDecimal(dSource);
            round = deSource.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return round;
    }

    public static String getVolume(int volume) {
        if (volume >= 10000) {
            double result = 0;
            double dSource = (double) volume / 10000;
            try {
                BigDecimal deSource = new BigDecimal(dSource);
                result = deSource.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result + "万";
        } else {
            return volume + "";
        }
    }

    public static String getVolumeOfW(int volume) {
        if (volume >= 10000) {
            double result = 0;
            double dSource = (double) volume / 10000;
            try {
                BigDecimal deSource = new BigDecimal(dSource);
                result = deSource.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result + "W";
        } else {
            return volume + "";
        }
    }

    public static HashMap<String, String> url2Map(String url) {
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            if (isEmpty(url)) {
                return map;
            }
            String[] strs = url.split("&");
            for (String s : strs) {
                String[] mapstr = s.split("=");
                if (mapstr.length != 2)
                    continue;
                mapstr[1] = URLDecoder.decode(mapstr[1]);
                map.put(mapstr[0], mapstr[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String map2Url(HashMap<String, String> map) {
        StringBuffer sb = new StringBuffer();
        for (Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (sb.length() != 0) {
            return sb.substring(0, sb.length() - 1);
        }
        return sb.toString();
    }

    private static String revStr(String str) {
        char[] keyChars = str.toCharArray();
        char[] realChars = new char[keyChars.length];
        for (int i = 0; i < keyChars.length; i++) {
            realChars[keyChars.length - i - 1] = keyChars[i];

        }
        return String.valueOf(realChars);
    }

    /**
     * String转换成Int，如果无法转换则返回0
     *
     * @param s
     * @return
     */
    public static int stringToInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取字符串的MD5加密
     *
     * @param str
     * @return
     */
    public static String changeStringToMD5(String str) {
        return entryMd5OrSha(str, "MD5");
    }

    /**
     * 对密码进行加密，先获取字符串的MD5加密，再进行SHA1加密
     *
     * @param str
     * @return
     */
    public static String entryMD5ThenSHA1(String str) {
        String md5Str = entryMd5OrSha(str, "MD5");
        return entryMd5OrSha(md5Str, "SHA1");
    }

    /**
     * 对密码进行加密，先获取字符串的MD5加密，再进行SHA1加密
     *
     * @param str
     * @return
     */
    public static String entryPassword(String str) {
        String md5Str = entryMd5OrSha(str, "MD5");
        return entryMd5OrSha(md5Str, "SHA1");
    }

    /**
     * 加密
     *
     * @param str
     * @param algorithm 只能传MD5 和SHA字段
     * @return
     */
    private static String entryMd5OrSha(String str, String algorithm) {
        String hash = "";
        try {
            MessageDigest messagedigest = MessageDigest.getInstance(algorithm);
            messagedigest.update(str.getBytes());
            hash = byte2digest(messagedigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    private static String byte2digest(byte abyte0[]) {
        StringBuffer stringbuffer = new StringBuffer(abyte0.length * 2);
        for (int i = 0; i < abyte0.length; i++) {
            if ((abyte0[i] & 255) < 16)
                stringbuffer.append("0");
            stringbuffer.append(Long.toString(abyte0[i] & 255, 16));
        }
        return stringbuffer.toString();
    }

    public static boolean isUrl(String str) {
        if (StringUtils.isNotEmpty(str)) {
            return str.startsWith("http://") || str.startsWith("https://");
        }
        return false;
    }

    public static boolean isUri(String str) {
        try {
            URI uri = new URI(str);
        } catch (URISyntaxException e) {
            return false;
        }
        return true;
    }

    public static String getHostUrl(String imageUrl, String imgHost) {
        if (!isUrl(imageUrl) && isNotEmpty(imgHost)) {
            imageUrl = imgHost + imageUrl;
        }
        return imageUrl;
    }

    public static int getColor(String colorStr) {
        int color = -1;
        try {
            if (!StringUtils.isEmpty(colorStr)) {
                if (colorStr.startsWith("#"))
                    return Color.parseColor(colorStr);
                color = Color.parseColor("#" + colorStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }

    public static String getEditHideRandomText() {
        Random random = new Random();
        int num = random.nextInt(11);
        String text = "他趣清清，口下留情（≧∇≦）";
        switch (num) {
            case 0:
                text = "他趣清清，口下留情（≧∇≦）";
                break;
            case 1:
                text = "贴小广告会被警察叔叔抓走哦╥﹏╥";
                break;
            case 2:
                text = "来卖萌，别来卖货(╯ω╰)";
                break;
            case 3:
                text = "不要让我看到少儿不宜(＾ｰ^)ノ";
                break;
            case 4:
                text = "我可爱，因为你文明（＾ν＾）";
                break;
            case 5:
                text = "凶我我会哭的呦∑(ﾟДﾟ）";
                break;
            case 6:
                text = "我有玻璃的心，最怕锋利的贱(つД`)ノ";
                break;
            case 7:
                text = "再刺激我我就要爆啦(´Д` )";
                break;
            case 8:
                text = "撸是你的事，脏就是你的错啦ಠ_ಠ";
                break;
            case 9:
                text = "有美文，有美女，还有文明的你( ^ω^ )";
                break;
            case 10:
                text = "随口一句我也可能哭出声音(╥﹏╥)";
                break;
        }
        return text;
    }

    /**
     * 数字格式化 已废弃;
     *
     * @param count
     * @return
     * @see #getFormatNumFromDigit
     */
    @Deprecated
    public static String getFormatReadCount(String count) {
        int realCount = Integer.parseInt(count);
        if (realCount < 1000) {
            return count;
        } else {
            double result = Math.round(realCount / 100.0) / 10.0;
            return String.valueOf(result) + "k";
        }
    }

    /**
     * 数字格式化 带小数点后两位四舍五入
     *
     * @param tobeFormat 需要格式化 "1111"
     * @param digit      需要的精度 1000f
     * @param units      后面的单位 k
     * @return "1.1k"
     */
    public static String getFormatNumFromDigit(String tobeFormat, float digit, String units) {
        try {
            String count = tobeFormat.trim().replace(",", "");
            int num = Integer.parseInt(count);
            float temp = digit / 10;
            if (num < digit) {
                return count;
            } else {
                double result = Math.round(num / temp) / 10.0;
                return String.valueOf(result) + units;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return tobeFormat;
        }
    }

    /**
     * 判断是否是json
     *
     * @param str 内容
     * @return true:是，false:否
     */
    public static boolean isJson(String str) {
        final char[] strChar = str.substring(0, 1).toCharArray();
        final char firstChar = strChar[0];

        if (firstChar == '{') {
            return true;
        }
        return false;
    }

    /**
     * @param uriData
     * @return
     * @author wangtingjie 2014年10月21日 下午4:46:57
     * @Description: 获取schmem路径
     */

    public static String getEncodedPath(Uri uriData) {
        /*
        getEncodedQuery和getPathSegments()的区别：
		getEncodedQuery获取url地址?后面的参数,并且是urlencode状态
		getPathSegments获取scheme和host后面/符号后的参数，并且自动urldecode，如果后面是?符号，则获取不到数据

		eg:
		地址为 ushengsheng.xjb://splash/m=forum&a=list&cid=1&user_could_post=1
		getEncodedQuery返回null
		getPathSegments返回list对象[m=forum&a=list&cid=1&user_could_post=1]

		地址为 ushengsheng.xjb://splash?m=forum&a=list&cid=1&user_could_post=1
		getEncodedQuery返回m=forum&a=list&cid=1&user_could_post=1
		getPathSegments返回空list对象[]

		强烈建议使用getEncodedQuery方法
		getPathSegments方法会自动urldecode，会导致后面解析出错
		*/
        String paramData = null;
        try {
            if (uriData != null) {
                paramData = uriData.getEncodedQuery();
                if (paramData == null) {
                    paramData = uriData.getEncodedPath();
                    if (paramData != null && paramData.indexOf("/") == 0) {
                        paramData = paramData.replaceFirst("/", "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramData;
    }
}
