package com.xinsteel.sgs.controller;


import com.xinsteel.sgs.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-23
 */
@RestController
@RequestMapping("/sgs/order-info")
public class OrderInfoController {

    @Autowired
    OrderInfoService orderInfoService;

    @GetMapping("/abc")
    public boolean saveOrderInfo(){
        boolean bool = orderInfoService.saveOrderInfo();
        return bool;
    }


}

