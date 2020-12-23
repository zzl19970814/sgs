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
@ApiModel(value="TransportInfo对象", description="")
public class TransportInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "运单号")
    @TableId(value = "waybill_id", type = IdType.ID_WORKER_STR)
    private String waybillId;

    @ApiModelProperty(value = "订单号")
    private String oId;

    @ApiModelProperty(value = "皮重")
    private String tare;

    @ApiModelProperty(value = "毛重")
    private String netWeight;

    @ApiModelProperty(value = "净重")
    private String grossWeight;

    @ApiModelProperty(value = "运单开始时间")
    private String waybillStartTime;

    @ApiModelProperty(value = "运单结束时间")
    private String waybillEndTime;

    @ApiModelProperty(value = "运单状态")
    private String waybillHandle;

    @ApiModelProperty(value = "封铅号")
    private String leadseal;

    @ApiModelProperty(value = "车牌号")
    private String wholeName;

    @ApiModelProperty(value = "司机名称")
    private String driverUserName;

    @ApiModelProperty(value = "司机邮箱")
    private String driverUserEmail;

    @ApiModelProperty(value = "司机手机号")
    private String driverUserMobile;


}
