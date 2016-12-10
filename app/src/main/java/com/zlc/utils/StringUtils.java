package com.zlc.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/7/21.
 */
public class StringUtils {


    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
		移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		170卡:
		总结起来就是第一位必定为1，第二位必定为3或5或8或7，其他位置的可以为0-9
		*/
        boolean flag = false;
        try{
            Pattern p = Pattern.compile("^1[34578][0-9]{9}$");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }


    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }



    /**
     * 验证身份证号
     * @param idNum
     * @return
     */
    public static boolean checkIdCard(String idNum) {
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        Matcher matcher = idNumPattern.matcher(idNum);
        return matcher.matches();
    }


    /**
     * 验证车牌号码
     * 车牌号格式：汉字 + A-Z + 位A-Z或-
     * 只包括了普通车牌号，教练车和部分部队车等车牌号不包括在内
     * @param carnumber
     * @return
     */
    public static boolean isCarnumberNO(String carnumber) {
        String carnumRegex = "[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
        if (isEmpty(carnumber)) return false;
        else return carnumber.matches(carnumRegex);
    }

    /**
     * 验证价格
     * 理工学科
     * 条件一：格式 0232 错误，023.232错误，0.23正确，202.02正确（错误的0开头为错误）
     * 条件二：整数最多10位，小数至多2位 条
     * 件三：成员当然不能为非数字，23.a错误 23.0a错误等
     * 条件四：带小数点必须要有小数位，233. 错误
     */

    public static boolean isPrice(String price){
        String carnumRegex = "^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$";
        if (isEmpty(price)) return false;
        else return price.matches(carnumRegex);
    }

    public static void main(String[] args){
        System.out.println(isPrice("01.22222"));
        System.out.println(isPrice("1.22222"));
        System.out.println(isPrice("0.22"));
        System.out.println(isPrice("1."));
        System.out.println(isPrice("1"));
    }
}
