package com.example.demo.dto.Emum;

/**
 * @ClassName: ComEnum
 * @Description: 枚举类
 * @author: wangnan
 * @date: 2019/01/16 15:48
 * @version: 1.0
 */
public final class ComEnum {

    public enum SEX {
        MAN(1, "男"),
        GIRL(2, "女"),
        CHILE(3, "小孩");

        //键
        private Integer code;
        //值
        private String value;

        //取得枚举区分
        public String getEnumName() {
            return "SEX";
        }

        //构造函数
        private SEX(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        //getcode
        public Integer getCode() {
            return code;
        }

        //getvalue
        public String getValue() {
            return value;
        }

        //根据code获取去value
        public static String getValueByCode(Integer code) {
            for (SEX obj : SEX.values()) {
                if (code.equals(obj.getCode())) {
                    return obj.getValue();
                }
            }
            return null;
        }
    }

    public enum REPORT_TYPE{
        COURT_REPROT(0,"用户信息报表");

        /** 键 */
        private Integer code;
        /** 值 */
        private String value;

        /**
         * 取得枚举区分
         * @return String
         */
        public String getEnumName() {
            return "REPORT_TYPE";
        }
        /**
         * 构造函数
         * @param value
         * @param code
         */
        private REPORT_TYPE(Integer code, String value) {
            this.code = code;
            this.value = value;
        }
        /**
         * getvalue
         * @return String
         */
        public String getValue() {
            return value;
        }
        /**
         * getcode
         * @return String
         */
        public Integer getCode() {
            return code;
        }

        //根据code获取去value
        public static String getValueByCode(Integer code) {
            for (REPORT_TYPE obj : REPORT_TYPE.values()) {
                if (code.equals(obj.getCode())) {
                    return obj.getValue();
                }
            }
            return null;
        }
    }
}
