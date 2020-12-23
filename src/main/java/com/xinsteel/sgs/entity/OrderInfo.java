package com.xinsteel.sgs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2020-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OrderInfo对象", description="")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单号")
    @TableId(value = "order_form_id", type = IdType.ID_WORKER_STR)
    private String orderFormId;

    @ApiModelProperty(value = "订单类型，1 客户自主下单2代下单")
    private String orderFormType;

    @ApiModelProperty(value = "下单时间")
    private String orderFormCreatetime;

    @ApiModelProperty(value = "订单完成时间")
    private String orderFormEndTime;

    @ApiModelProperty(value = "起点")
    private String startThing;

    @ApiModelProperty(value = "起点纬度")
    private String startThingLat;

    @ApiModelProperty(value = "起点经度")
    private String startThingLng;

    @ApiModelProperty(value = "终点")
    private String endThing;

    @ApiModelProperty(value = "终点纬度")
    private String endThingLat;

    @ApiModelProperty(value = "终点经度")
    private String endThingLng;

    @ApiModelProperty(value = "订单状态")
    private String orderFormHandle;

    @ApiModelProperty(value = "预计到达时间")
    private String orderFormEstimate;

    @ApiModelProperty(value = "订单评价描述")
    private String orderFormEvaluation;

    @ApiModelProperty(value = "商品名称")
    private String materialName;

    @ApiModelProperty(value = "商品数量")
    private String materialNumber;

    @ApiModelProperty(value = "客户名称")
    private String customerUserName;

    @ApiModelProperty(value = "客户邮箱")
    private String customerUserEmail;

    @ApiModelProperty(value = "客户手机号")
    private String customerUserMobile;

    @ApiModelProperty(value = "承运商名称")
    private String carrierUserName;

    @ApiModelProperty(value = "承运商邮箱")
    private String carrierUserEmail;

    @ApiModelProperty(value = "承运商手机号")
    private String carrierUserMobile;


}
