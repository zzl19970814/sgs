package com.xinsteel.sgs.service.impl;

import com.xinsteel.sgs.entity.OrderInfo;
import com.xinsteel.sgs.mapper.OrderInfoMapper;
import com.xinsteel.sgs.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinsteel.sgs.utils.HttpClientUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-23
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Override
    public boolean saveOrderInfo() {

        return false;
    }

    @Override
    public List selectAllOrderIds() {
        return null;
    }

    @Override
    public boolean existOrderInfo(String orderId) {

        OrderInfo orderInfo = baseMapper.selectById(orderId);
        if (orderInfo == null){
            return false;
        }

        return true;
    }

    private Map<String, Object> selectOrderInfoByOrderId(String orderId){
        Map<String, Object> data = new HashMap<>();
        return data;
    }
}
