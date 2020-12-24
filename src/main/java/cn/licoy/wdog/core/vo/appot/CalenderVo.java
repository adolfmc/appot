package cn.licoy.wdog.core.vo.appot;

import java.util.ArrayList;
import java.util.List;

public class CalenderVo {
    public String month;
    public List<Week> weeks=new ArrayList<Week>();

    public class Week{
        public List<Day> days=new ArrayList<Day>();
    }

    public class Day{
        public String lDay;
        public String sDay;
        public String tag;
        public String isCurMonthDay;
        public String isPastDay;
    }
}
