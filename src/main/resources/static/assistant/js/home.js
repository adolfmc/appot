var base_url="https://www.yushangcc.com" ;
var base_url="http://127.0.0.1" ;

var storage=window.localStorage;

function create_calendar1(result,which_){
    var info = new Array();
    var info_ = new Array();

    var month_week=0;
    for(var i =0 ;i<result.length ;i++){
        info.push(result[i] )
        
        if(info.length==7){
            info_[month_week++] = info
            info = new Array();
        }
    }

    var tr_="" ;
    var data_m= ""
    var today_datee = new Date().Format("yyyy-MM-dd");
    for(var i=0;i<info_.length;i++){
        var _css_tag='color: #20ca6a'
        var _css_td_today=""
        var _css_datee='';
        var _css_a ='' ;
        var tdd="" ;

        
        for(var j=0;j<7;j++){
            var datee_day = info_[i][j][1];
            var datee_month =datee_day.toString().split('-')[1];
            
            data_m= info_[i][j][0] 

            //当月日期
            if( datee_month==data_m  ){
                _css_tag='color: #20ca6a'
                _css_datee='';
                _css_td_today="";
                _css_a ="style='pointer-events:'; " ;
            //非当月日期
            }else{
                _css_tag='color: #1d1d1d;opacity: 0.2' 
                _css_datee='opacity: 0.2';
                _css_a ="style='pointer-events:none'; " ;
                _css_td_today = '';

                //判断 日历正行是否是当月 且生效日期，如果整行都不是当月日期，则直接退出循环
                // if(  Number(info_[i][j][1].split('-')[2])>=1  && Number(info_[i][j][1].split('-')[2])<=14  ){
                //     continue;
                // }
            } 
            
            //设置当天日期
            if(datee_day ==today_datee && datee_month==data_m  ){
                _css_tag='color:#FFFFFF'
                _css_datee='color:#FFFFFF';
                _css_td_today="bgcolor='#10AEFF'";
                _css_a ="style='pointer-events:'; " ;
            }

            var datee=info_[i][j][1]
            tdd = tdd
            +"<td class='hometable_td'  "+_css_td_today+">"
                +"<a onclick=\"choose_site('"+datee+"')\"  "+_css_a+">"
                    +"<p style='"+_css_datee+"'> "+(info_[i][j][1].split('-')[2])+"</p> "
                    +"<p style='"+_css_tag+"'>充足</p>"
                +"</a> "
            +"</td> "

            

        }
        tr_ = tr_ +"<tr>"+tdd+"</tr>"
    }
    
    $("#cmon1").html(data_m+'月');

    var calendar_ = ""
                +   "<div class='hometable_th'>"+data_m+"月</div>"
                +   "<table  width='90%'  style='background-color: white;background-image:url(./assistant/images/tgb_"+data_m+".png);background-size: 100% 100%;margin-left: 1.5rem;'>"
                +       "<tr >"
                +       "<th class='hometable_th'><a >周一</a></th>"
                +       "<th class='hometable_th'><a >周二</a></th>"
                +       "<th class='hometable_th'><a >周三</a></th>"
                +       "<th class='hometable_th'><a >周四</a></th>"
                +       "<th class='hometable_th'><a >周五</a></th>"
                +       "<th class='hometable_th'><a >周六</a></th>"
                +       "<th class='hometable_th'><a >周日</a></th>"
                +       "</tr>"
                +       "<tbody >"+tr_+"</tbody>"
                +   "</table>"

    $('#'+which_).append(calendar_) ;
}

function choose_site(datee){

}

function create_calendar2(result,which_){
    var info = new Array();

    var info_ = new Array();

    var month_week=1;
    for(var i =0 ;i<result.length ;i++){
        info.push(result[i] )
        
        if(info.length==7){
            info_[month_week++] = info
            info = new Array();
        }
    }

    var tr_="" ;
    var data_m="";
    var today_datee = new Date().Format("yyyy-MM-dd");
    for(var i=1;i<info_.length;i++){
        var _css_tag='color: #20ca6a'
        var _css_td_today=""
        var _css_datee='';
        var _css_a ='' ;
        var tdd="" ;

        for(var j=0;j<7;j++){
            var datee_day = info_[i][j][1];
            var datee_month =datee_day.toString().split('-')[1];

            data_m= info_[i][j][0] 

            //当月日期
            if( datee_month==data_m  ){
                _css_tag='color: #20ca6a'
                _css_datee='';
                _css_td_today="";
                _css_a ="style='pointer-events:'; " ;
            //非当月日期
            }else{
                _css_tag='color: #1d1d1d;opacity: 0.2' 
                _css_datee='opacity: 0.2';
                _css_a ="style='pointer-events:none'; " ;
                _css_td_today = '';
            } 
            
            //设置当天日期
            if(datee_day ==today_datee ){
                _css_tag='color:#FFFFFF'
                _css_datee='color:#FFFFFF';
                _css_td_today="bgcolor='#10AEFF'";
                _css_a ="style='pointer-events:'; " ;
            }

            var datee=info_[i][j][1]
            tdd = tdd
            +"<td class='hometable_td'  "+_css_td_today+">"
                +"<a onclick=\"choose_site('"+datee+"')\" class='js-dialog-close' "+_css_a+">"
                    +"<p style='"+_css_datee+"'> "+(info_[i][j][1].split('-')[2] )+"</p> "
                +"</a> "
            +"</td> "
        }
        tr_ = tr_ +"<tr>"+tdd+"</tr>"
    }
    
    var calendar_ = ""
                +   "<div class='hometable_th3'>"+data_m+"月</div>"
                +   "<table  width='90%'  style='background-color: white;background-image:url(./assistant/images/tgb_"+data_m+".png);background-size: 100% 100%;margin-left: 1.5rem;'>"
                +       "<tr >"
                +       "<th class='hometable_th2'><a >周日</a></th>"
                +       "<th class='hometable_th2'><a >周一</a></th>"
                +       "<th class='hometable_th2'><a >周二</a></th>"
                +       "<th class='hometable_th2'><a >周三</a></th>"
                +       "<th class='hometable_th2'><a >周四</a></th>"
                +       "<th class='hometable_th2'><a >周五</a></th>"
                +       "<th class='hometable_th2'><a >周六</a></th>"
                +       "</tr>"
                +       "<tbody id='c1'>"+tr_+"</tbody>"
                +   "</table>"

    $('#'+which_).append(calendar_) ;
}


function get_calendar(which_){
    //
    $.ajax({type : "POST",contentType: "text/html; charset=utf-8",url : base_url+"/getCalendar",
        success : function(result) {
            result = $.parseJSON( result );
            //首页日历
            for(var i=0;i<result.length;i++){
                create_calendar1(result[i],'calendar')
            }
            //弹窗日历
            for(var i=0;i<result.length;i++){
                create_calendar2(result[i],'calendar2')
            }
        },
        error : function(e){
            console.log(e.status);
            console.log(e.responseText);
        }
    });
}

