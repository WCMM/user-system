package com.example.demo.dto.excel;

/**


/**
 * @ClassName: CourtReportDto
 * @Description: 场馆经营汇总表
 * @author: 罗伟涛
 * @date: 2019/3/5 10:34
 * @version: 1.0
 */
public class CourtReportDto {

    private String couponName;

    private String orderType;

    private String count;

    private String  money;


    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
