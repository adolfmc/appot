var flag=true;
var time="";
var timeFlag=true;
$(function(){
    if(flag){
        $(".signal_venues").hide();
        $("#signal_Img").show();
    }
    var obj=$(".cur");
    time=$(".cur").attr("time");
    timeChoose(obj,time);
    $(".toDo_pay").css("background","#cccccc");
    $(".toDo_pay").removeAttr("onclick");
})
var infoStr="";
var array=new Array();
var price ="";
var Chooseflag=true;
function chooseSite(obj,id,beginTime,endTime,siteName,price){
    var html="";
    $(".toDo_pay").css("background","#009de2");
    $(".toDo_pay").attr("onclick","toPendingOrder()");
    var len=$('#haveChoose').children('span').length;

    if($("dd[class='on']").size()>3){
        if(Chooseflag){
            Chooseflag=false;
            layer.msg("最多只能选4个");
            return;
        }else{
            if(obj.getAttribute("class")=="on"){
                Chooseflag=true;
                obj.className="vv_sel";
                var id=obj.getAttribute("id");
                $('#haveChoose').find('span').each(function(){
                    var t = $(this).attr('id');
                    if(t == id){
                        $(this).remove();
                    }
                });
            }
        }
    }else{
        if(obj.getAttribute("class")=="on"){
            obj.className="vv_sel";
            var id=obj.getAttribute("id");
            $('#haveChoose').find('span').each(function(){
                var t = $(this).attr('id');
                if(t == id){
                    $(this).remove();
                }
            });
            if($('.on').length==0){
                $("#signal_Img").show();
                $(".signal_venues").hide();
                $(".toDo_pay").css("background","#cccccc");
                $(".toDo_pay").removeAttr("onclick");
            }
        }else{
            obj.setAttribute("class","on");
            html+="<span id='"+siteName+endTime+"'>";
            html+="<p>"+beginTime.substring(10,16)+"-"+endTime.substring(10,16)+"</p>";
            html+="<p>"+siteName+"</p>"
                +"<input type='hidden' name='siteNo' value='"+id+"'>"
                +"<input type='hidden' name='stime' value='"+beginTime+"'>"
                +"<input type='hidden' name='etime' value='"+endTime+"'>"
                +"<input type='hidden' name='price' value='"+price+"'>"
                +"<input type='hidden' name='siteName' value='"+siteName+"'>"
            html+="</span>";
            $(".signal_venues").show();
            $("#signal_Img").hide();
            $('#haveChoose').append(html);
        }
    }
}

function isHalf(objs){
    $(objs).addClass("on").siblings().removeClass("on");
    var obj=$(".cur");
    var time=$(".cur").attr("time")
    $("#isHalf").val($(".on").attr("data-half"))
    timeChoose(obj,time);

}

function timeChoose(obj,times){
    var objs;
    if(obj[0]!=null){
        objs=obj[0];
    }else{
        objs=obj;
    }
    time=times;
    timeFlag=false;
    var venueId=$("#venueId").val();
    var siteId=$("#siteId").val();
    $(objs).addClass("cur").siblings().removeClass("cur");
    var half=$("#isHalf").val();

    $.ajax({
        type : "get",
        data : {"site_id":siteId,"venue_id":venueId,"datee":time,"sports_type":half},
        dataType : "json",
        url : base_url+"/getInfoByDateFromDCYumaoqiu", 

        beforeSend:function() {
            $(".spinner").show();
        },
        success : function (json) {
            document.getElementById("spinnerid").style.display="none"
            if(objs.getAttribute("class")!="cur"){
                objs.setAttribute("class","cur");
            }
            var html = "";
            var info=json.body;
            var code=json.code;
            if(code!=0){
                if(info!=null){
                    var sTime = Date.parse((info.stime).replace(/-/g,"/"));
                    var eTime =  Date.parse((info.etime).replace(/-/g,"/"));
                    console.log(sTime+"--"+eTime)
                    var site=info.siteInfos;
                    if(info.minHour==1){
                        for(var i=0;i<site.length;i++){
                            html+="<dl>";
                            html+=' <dt id="changdi" >'+site[i].siteName+'</dt>';
                            var book=site[i].bookPriceInfos;
                            var j=0;
                            var t=sTime;
                            var comTime=0;
                            for(t;t<eTime;t+=3600000){
                                comTime=t;

                                if( j<book.length){
                                    var sDate = Date.parse((book[j].beginTime).replace(/-/g,"/"));
                                    var eDate =  Date.parse((book[j].endTime).replace(/-/g,"/"));
                                    if( Date.parse((book[j].beginTime).replace(/-/g,"/"))==comTime){
                                        if(parseInt(book[j].bookStatus)==1){
                                            if((eDate-sDate)/3600000 ==2){
                                                t=t+1*3600000;
                                                html+='<dd class="vv_sel" style="height:79px;line-height：80px;" id="'+site[i].siteName+book[j].endTime+'" onclick="chooseSite(this,\''+site[i].siteNo+'\',\''+book[j].beginTime +'\',\''+book[j].endTime+'\',\''+site[i].siteName+'\',\''+book[j].salePrice+'\')">￥'+book[j].salePrice+'</dd>';
                                            }else if((eDate-sDate)/3600000  ==3){
                                                t=t+2*3600000;
                                                html+='<dd class="vv_sel" style="height:120px;line-height:120px;" id="'+site[i].siteName+book[j].endTime+'" onclick="chooseSite(this,\''+site[i].siteNo+'\',\''+book[j].beginTime +'\',\''+book[j].endTime+'\',\''+site[i].siteName+'\',\''+book[j].salePrice+'\')">￥'+book[j].salePrice+'</dd>';
                                            }else if((eDate-sDate)/3600000  ==4){
                                                t=t+3*3600000;
                                                html+='<dd class="vv_sel" style="height:161px;line-height:161px;" id="'+site[i].siteName+book[j].endTime+'" onclick="chooseSite(this,\''+site[i].siteNo+'\',\''+book[j].beginTime +'\',\''+book[j].endTime+'\',\''+site[i].siteName+'\',\''+book[j].salePrice+'\')">￥'+book[j].salePrice+'</dd>';
                                            }else{
                                                html+='<dd class="vv_sel"  id="'+site[i].siteName+book[j].endTime+'" onclick="chooseSite(this,\''+site[i].siteNo+'\',\''+book[j].beginTime +'\',\''+book[j].endTime+'\',\''+site[i].siteName+'\',\''+book[j].salePrice+'\')">￥'+book[j].salePrice+'</dd>';
                                            }
                                            j++;

                                        }else{

                                            if((eDate-sDate)/3600000  ==2){
                                                t=t+1*3600000;
                                                html+='<dd class="vv_sel" style="background-color:#eee;height:79px"></dd>';
                                            }else if((eDate-sDate)/3600000  ==3){
                                                t=t+2*3600000;
                                                html+='<dd class="vv_sel" style="background-color:#eee;height:120px"></dd>';
                                            }else if((eDate-sDate)/3600000  ==4){
                                                t=t+3*3600000;
                                                html+='<dd class="vv_sel" style="background-color:#eee;height:161px"></dd>';
                                            }else{
                                                html+='<dd class="vv_sel" style="background-color:#eee"></dd>';
                                            }
                                            j++;
                                        }
                                    }else{
                                        html+='<dd class="vv_sel" style="background-color:#eee"></dd>';
                                    }
                                }else{
                                    html+='<dd class="vv_sel" style="background-color:#eee"></dd>';
                                }
                            }
                            html+="</dl>";
                        }
                    }else{//半小时场地
                        for(var i=0;i<site.length;i++){
                            html+="<dl>";
                            html+=' <dt id="changdi" >'+site[i].siteName+'</dt>';
                            var book=site[i].bookPriceInfos;
                            var j=0;
                            var t=sTime;
                            var comHarfTime=0;
                            //------------------半小时时间循环------------------------------------
                            for(t;t<eTime;t+=1800000){
                                comHarfTime=t;

                                if( j<book.length){
                                    var sDate=Date.parse(book[j].beginTime.replace(/-/g,"/"));
                                    var eDate=Date.parse(book[j].endTime.replace(/-/g,"/"));
                                    // console.log(Date.parse((book[j].beginTime).replace(/-/g,"/"))==comHarfTime)
                                    if(Date.parse((book[j].beginTime).replace(/-/g,"/"))==comHarfTime){
                                        if(parseInt(book[j].bookStatus)==0){
                                            if(((eDate-sDate)/1800000)==2){
                                                t=t+1*1800000;
                                                html+='<dd class="vv_sel" style="background-color:#eee;height:79px"></dd>';
                                                j++;
                                            }else if(((eDate-sDate)/1800000) ==3){
                                                t=t+2*1800000;
                                                html+='<dd class="vv_sel" style="background-color:#eee;height:120px"></dd>';
                                                j++;
                                            }else if(((eDate-sDate)/1800000) ==4){
                                                t=t+3*1800000;
                                                html+='<dd class="vv_sel" style="background-color:#eee;height:160px"></dd>';
                                                j++;
                                            }else if(((eDate-sDate)/1800000) ==5){
                                                t=t+4*1800000;
                                                html+='<dd class="vv_sel" style="background-color:#eee;height:200px"></dd>';
                                                j++;
                                            }else if(((eDate-sDate)/1800000) ==6){
                                                t=t+5*1800000;
                                                html+='<dd class="vv_sel" style="background-color:#eee;height:240px"></dd>';
                                                j++;
                                            }else{
                                                html+='<dd class="vv_sel" style="background-color:#eee"></dd>';
                                                j++;
                                            }
                                        }else{
                                            if(((eDate-sDate)/1800000) ==2){
                                                t=t+1*1800000;
                                                html+='<dd class="vv_sel" style="height:79px" id="'+site[i].siteName+book[j].endTime+'" onclick="chooseSite(this,\''+site[i].siteNo+'\',\''+book[j].beginTime +'\',\''+book[j].endTime+'\',\''+site[i].siteName+'\',\''+book[j].salePrice+'\')">￥'+book[j].salePrice+'</dd>';
                                                j++;
                                            }else if(((eDate-sDate)/1800000) ==3){
                                                t=t+2*1800000;
                                                html+='<dd class="vv_sel" style="height:120px" id="'+site[i].siteName+book[j].endTime+'" onclick="chooseSite(this,\''+site[i].siteNo+'\',\''+book[j].beginTime +'\',\''+book[j].endTime+'\',\''+site[i].siteName+'\',\''+book[j].salePrice+'\')">￥'+book[j].salePrice+'</dd>';
                                                j++;
                                            }else if(((eDate-sDate)/1800000)  ==4){
                                                t=t+3*1800000;
                                                html+='<dd class="vv_sel" style="height:160px" id="'+site[i].siteName+book[j].endTime+'" onclick="chooseSite(this,\''+site[i].siteNo+'\',\''+book[j].beginTime +'\',\''+book[j].endTime+'\',\''+site[i].siteName+'\',\''+book[j].salePrice+'\')">￥'+book[j].salePrice+'</dd>';
                                                j++;
                                            }else if(((eDate-sDate)/1800000)  ==5){
                                                t=t+4*1800000;
                                                html+='<dd class="vv_sel" style="height:200px" id="'+site[i].siteName+book[j].endTime+'" onclick="chooseSite(this,\''+site[i].siteNo+'\',\''+book[j].beginTime +'\',\''+book[j].endTime+'\',\''+site[i].siteName+'\',\''+book[j].salePrice+'\')">￥'+book[j].salePrice+'</dd>';
                                                j++;
                                            }else if(((eDate-sDate)/1800000)  ==6){
                                                t=t+5*1800000;
                                                html+='<dd class="vv_sel" style="height:240px" id="'+site[i].siteName+book[j].endTime+'" onclick="chooseSite(this,\''+site[i].siteNo+'\',\''+book[j].beginTime +'\',\''+book[j].endTime+'\',\''+site[i].siteName+'\',\''+book[j].salePrice+'\')">￥'+book[j].salePrice+'</dd>';
                                                j++;
                                            }else{
                                                html+='<dd class="vv_sel"  id="'+site[i].siteName+book[j].endTime+'" onclick="chooseSite(this,\''+site[i].siteNo+'\',\''+book[j].beginTime +'\',\''+book[j].endTime+'\',\''+site[i].siteName+'\',\''+book[j].salePrice+'\')">￥'+book[j].salePrice+'</dd>';
                                                j++;
                                            }
                                        }
                                    }else{
                                        html+='<dd class="vv_sel" style="background-color:#eee"></dd>';
                                    }
                                }else{
         
                                    html+='<dd class="vv_sel" style="background-color:#eee"></dd>';
                                }
                            }
                            html+="</dl>";
                        }
                    }
                    var timehtml="<div style='height: 49px;' > <a  onclick=Metro.dialog.open('#demoDialog1') > <span class='mif-calendar mif-2x' style='margin-left: 1.2rem;margin-top: 0.6rem;'></span> </a></div>";
                    if(info.minHour==1){//一小时
                        for(var i=Date.parse((info.stime).replace(/-/g,"/"));i<=Date.parse((info.etime).replace(/-/g,"/"));i+=3600000){
                            var time=new Date(i);
                            timehtml+="<em>"+(time.getHours()<10?("0"+time.getHours()):time.getHours())+":00</em>"
                        }
                    }else{//半小时
                        for(var i=Date.parse((info.stime).replace(/-/g,"/"));i<=Date.parse((info.etime).replace(/-/g,"/"));i+=1800000){
                            var time=new Date(i);
                            timehtml+="<em>"+(time.getHours()<10?("0"+time.getHours()):time.getHours())+
                                ":"+(time.getMinutes()<10?("00"):time.getMinutes())+"</em>"
                        }

                    }
                    $(".selectnom").html(timehtml);
                    $("#siteItem").html(html);
                }
            }else{
                $(".selectnom").html("");
                $("#siteItem").html("");
            }
            $(".signal_venues").hide();
            $("#haveChoose").html("");
            $("#signal_Img").show();
            $(".spinner").hide();
        }
    });
}


function toPendingOrder(){
    var siteNo=new  Array();

    var mobile = storage['mobile'];
    if(mobile == undefined || mobile == ''){
        window.location=base_url+'/static/login.html' ;
    }


    var flag=false;
    var venueName=$("#venueName").val();
    $("input[name='siteNo']").each(function(){
        var siteno=$(this).val();
        if(siteno != undefined || siteno == ''){
            siteNo.push(siteno);

        }
    });
    var stime=new  Array();
    $("input[name='stime']").each(function(){
        var stime1=$(this).val();
        if(stime1 != undefined || stime1 == ''){
            stime.push(stime1.substring(10,16));
            flag=true;
        }
    });
    var etime=new  Array();
    $("input[name='etime']").each(function(){
        var etime1=$(this).val();
        if(etime1 != undefined || etime1 == ''){
            etime.push(etime1.substring(10,16));
            flag=true;
        }
    });
    var price=new  Array();
    $("input[name='price']").each(function(){
        var peice1=$(this).val();
        if(peice1 != undefined || peice1 == ''){
            price.push(peice1);
            flag=true;
        }
    });

    var siteName=new  Array();
    $("input[name='siteName']").each(function(){
        var siteName1=$(this).val();
        if(siteName1 != undefined || siteName1 == ''){
            siteName.push(siteName1);
            flag=true;
        }
    });


 


    if(flag){
        // window.location=base_url+"/sendOrder?siteNo="+siteNo.join(',')+"&stime="+stime.join(',')
        //     +"&etime="+etime.join(',')+"&price="+price.join(',')
        //     +"&time="+time+"&venueId="+venueId+"&siteName="+siteName.join(',')
        //     +"&sportType="+sportType+"&siteId="+siteId+"&productId="+productId
        //     +"&isHalf="+ishalf+"&openId="+$.cookie('openId')+"&mobile="+mobile;
        var amount_=0;
        var li_str='';
        var siteNos = siteNo.join(',');
        var stimes = stime.join(',');
        var etimes = etime.join(',');
        var prices = price.join(',');
        var siteNames = siteName.join(',');

        var appotDate = $('#choose_date').val();
        var appotDate_mm = appotDate.substring(5,7);
        var appotDate_dd = appotDate.substring(8,11);
            appotDate = appotDate_mm+'月'+appotDate_dd+'日';

        for(var i=0;i<siteNos.split(',').length;i++){
            var siteName = siteNames.split(',')[i];
            var sitePrice = prices.split(',')[i];
            var stime = stimes.split(',')[i];
            var etime = etimes.split(',')[i];
            li_str = li_str + '<li> '+siteName+' &nbsp;&nbsp;&nbsp; '+appotDate+' &nbsp;&nbsp;&nbsp; '+stime+'-'+etime+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+sitePrice+'元 </li>'
            amount_ = Number(amount_) + Number(sitePrice) ; 
        }

        //设置订单查询页面 手机号
        // $("#tel").val(storage['mobile']);
        // $("#amount_").val(amount_);
        // $("#span_amount_").html('￥'+amount_) ;
        // $("#confrom_amount_").html('确认支付'+amount_+'元') ;
        // // // $("#venue_name").val(amount_); TODO
        // $("#order_venue_list").empty();
        // $('#order_venue_list').append(li_str);
        // $('#siteNos').val(siteNos);
        // $('#stimes').val(stimes);
        // $('#etimes').val(etimes);
        // $('#prices').val(prices);
        // $('#siteNames').val(siteNames);


        var datee=getUrlParam('datee');
        var venueId=getUrlParam('venueId');
        var siteId=getUrlParam('siteId');
        var productId=getUrlParam('productId');
        var isHalf=getUrlParam('isHalf');
        var sportType=getUrlParam('sportType');


        window.location.href="./order.html?datee="+datee
        +"&venueId="+venueId
        +"&siteId="+siteId
        +"&productId="+productId
        +"&isHalf="+isHalf
        +"&sportType="+sportType
        +"&amount_="+amount_
        +"&appotDate="+ encodeURIComponent(encodeURIComponent(appotDate))
        +"&siteNos="+encodeURIComponent(siteNos)
        +"&stimes="+encodeURIComponent(stimes)
        +"&etimes="+encodeURIComponent(etimes)
        +"&prices="+encodeURIComponent(prices)
        +"&siteNames="+ encodeURIComponent( encodeURIComponent(siteNames) );
    }else{
        layer.msg("请选择场地！");
    }
}