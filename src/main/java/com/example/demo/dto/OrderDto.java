package com.example.demo.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author wangnan
 * @Date 2020/5/22/022 2020-05 15:54
 * @Param []
 * @return
 **/
public class OrderDto {
    private Date deliveryDate;

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "OrderDto{" +
                "deliveryDate=" + simpleDateFormat.format(deliveryDate) +
                '}';
    }
}
