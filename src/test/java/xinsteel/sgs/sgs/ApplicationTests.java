package xinsteel.sgs.sgs;

import com.xinsteel.sgs.Application;
import com.xinsteel.sgs.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest(classes = {Application.class})
class ApplicationTests {


    @Test
    void contextLoads() {
        String billId = "20201225001";
        String substring = billId.substring(0, 8);
        System.out.println(substring);

        // 4天内的订单
        String formatDate = DateUtil.formatDate(DateUtil.addDays(new Date(), -4));
        String replace = formatDate.replace("-", "");
        System.out.println(replace);

        // 判断是否是在4天内
        if (Long.parseLong(substring) >= Long.parseLong(replace)){
            System.out.println("sdasfasdfasedfsadsd");

        }
    }

}
