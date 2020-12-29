import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author wangnan
 * @Date 2019/9/23/023 2019-09 17:05
 * @Param []
 * @return
 **/
public class quitTimeTest {

    public static String quitTimeCheck(String index, String value) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        Date quitDate = null;
        try {
            nowDate = sdf.parse(sdf.format(new Date()));
            quitDate = sdf.parse(value);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (nowDate.compareTo(quitDate) < 0) {
            return "错误行号：" + index + "，错误信息：" + "离职时间不能大于当前时间" + " ";
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(quitTimeCheck("A1","2019-09-24"));
    }
}
