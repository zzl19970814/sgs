package com.xinsteel.sgs.service;

import com.xinsteel.sgs.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-23
 */
public interface OrderInfoService extends IService<OrderInfo> {

    boolean saveOrderInfo();

    List selectAllOrderIds();


    boolean existOrderInfo(String orderId);
}
