package wechat

import grails.transaction.Transactional

import com.hhpc.wechat.domain.*

/**
 * Created by Administrator on 2014/9/30.
 */


@Transactional
class ParseSmsService {

//    boolean transactional = true
//    def sessionFactory
//    def dataSource


    def parseSms(MergerSms mergerSms){

        //  def msg="0322精舞门;青5间纯156/欢180/冰240;百5间支180/罐240;珠3间支120;嘉3间支120/罐60;张5间优赤25/特赤15/优解15;华夏3间优赤15.丰收2间优赤9.芝1间优赤3;轩V2间优赤9;黑牌1间优赤3;白酒2间优赤6;青9卡纯320/欢460/冰72;百1台罐24;珠1卡支72;青27台纯360/欢660/冰72;边润华#"
        //def msg="0322精舞门;青岛6间:纯生168支,冰爽120支;百威6间:支装48,罐装108;珠江6间:支装336;张裕3间:优赤18;青岛10卡:欢动600;边润华#"
        if((mergerSms.status.id==21)) {

            if( !mergerSms.message.contains('#')   )  {
                 throw new RuntimeException ('X短信没以#结尾,信息不完整或只收到前半截:');
             }
        }
        //==5   合并成功
        if(!(mergerSms.status.id==5)) return;


        def msg= mergerSms.message.trim();
       //替换中文符号
        msg=msg.replaceAll('，',',')
        msg=msg.replaceAll('＃','#')
        msg=msg.replaceAll(',,',',')
        msg=msg.replaceAll('//','/')
        msg=msg.replaceAll('/,',',')
        mergerSms.message=msg



        // 提出备注
        def remark //短信备注
        def findRemark= msg =~/.*(\[.*\])#$/  // ***[***]#  结尾正确
        if(findRemark.matches()){

            remark=findRemark[0][1]
            if(remark)                {
                msg=msg[0..msg.indexOf(remark)-1]+'#'
                if(remark.size()>2){
                    remark=remark[1..-2]
                }else  remark=null
            }
        }else{
            findRemark= msg =~/.*(\[.*\]).*#$/  //***[***]***# 错误格式
            if(findRemark.matches())
                throw new RuntimeException ('B短信格式错误！"以姓名[备注信息]#"结尾才准确。');
        }

        def finder = msg =~/^([A-L,N-Z]?[A-Z])(\d{4})([^,\s]+),(([\u4e00-\u9fa5]+\d+[大|中|小]?[\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5]+\d+(\/[a-zA-Z0-9\u4e00-\u9fa5]+\d+)*,)+)([\u4e00-\u9fa5]+)#$/
        //  ^[A-Z]  以大写字母开头 ；d{4}  4 位表示日期数字 ;  [^,\s]+, 场所名称以逗号结束，场所名称不能有逗号和空白 ； [\u4e00-\u9fa5] +\d+[大|中|小]?[\u4e00-\u9fa5]  表示品牌开房或卡数，品牌（汉字）后面数字再后面可以有[大|中|小] 也可没有后面是房间类型（汉字）； [a-zA-Z0-9\u4e00-\u9fa5]+\d+  第一个品种量，品种（汉字字母和数字）后面是数字，(\/[a-zA-Z0-9\u4e00-\u9fa5]+\d+)*  可以有多个品种，多品种必须以斜杠分隔 ； [\u4e00-\u9fa5]+#  促销名(汉字)[备注]# 结束。

        //   if(!finder.matches())
        //        throw new RuntimeException ('B短信格式错误！请认真检查:');

        String sellerName    //促销名字
        def firstFlag    //编码
        def dates  //日期
        def posName    //终端名字

        def marketFlag=false; //商超标识
        //解析夜场短信
        if(finder.find()){
            firstFlag= finder.group(1)
            dates=finder.group(2)
            posName=finder.group(3)
            msg=finder.group(4)
            sellerName=finder.group(finder.groupCount())

        }else{
            println "begin mark..."
            //解析商超短信
            finder = msg =~/^(M[A-Z])(\d{4})([^,\s]+),(([a-zA-Z0-9\u4e00-\u9fa5]+\d+,)+)([\u4e00-\u9fa5]+)#$/
            //  ^[A-Z]  以大写字母开头 ；d{4}  4 位表示日期数字 ;  [^,\s]+, 场所名称以逗号结束，场所名称不能有逗号和空白 ；
            if(finder.find()){
                marketFlag=true;
                firstFlag= finder.group(1)
                dates=finder.group(2)
                posName=finder.group(3)
                msg=finder.group(4)
                sellerName=finder.group(finder.groupCount())

            }
             else throw new RuntimeException ('B短信格式错误！！请认真检查...');

        }
        if(!sellerName)
              throw new RuntimeException ('B解析报数人员姓名错误，请检查短信格式');



        def lists= msg.split(",").toList();




        def posInfo=SubPosInfo.find("from com.hhpc.wechat.domain.SubPosInfo a where a.name='${posName}' and a.posInfo.subArea.area.id=${mergerSms.area.id}")
        if(!posInfo){

             if(posName==~ /^\d\D+/)throw new RuntimeException ('B不存在的场所名或者格式错误，请仔细检查：'+posName);
             throw new RuntimeException ('K不存在的场所名或者格式错误，请联系促销组长。'+posName);

         }


        def simpleDateFormat =  new java.text. SimpleDateFormat("yyyy-MM-dd 00:00:00")
        def sellDate  =  new Date();
        try{

            int month=dates[0..1].toInteger()
            int day=   dates[2..-1].toInteger()
            int year= sellDate.getYear()+1900;
            if("1231".equals(dates)) year=year-1

            if(month<1||month>12)
                  throw new RuntimeException ('B月份错误：'+dates+".日期用4位数表示,前两位是月份后位号数。");
            if(day<1||day>31)
                  throw new RuntimeException ('B号数错误：'+dates+".日期用4位数表示,前两位是月份后位号数。");

            sellDate=  simpleDateFormat.parse(""+year+"-"+month+"-"+day+" 00:00:00")

        }catch(Exception e){
              throw new RuntimeException ('B日期错误：'+dates);
        }

        if(!("1231".equals(dates))){// 一年的最后一天不用次规则
            Date now = new Date()
            // Integer year = now.year + 1900
            Integer month = now.month + 1
            Integer day = now.getAt(Calendar.DAY_OF_MONTH) // inconsistent!
            Integer hour = now.hours

            //def simpleDateFormat =  new java.text. SimpleDateFormat("yyyy-MM-dd 00:00:00")
            // now=simpleDateFormat.parse(simpleDateFormat.format(now))



            if((sellDate.month + 1)>month){// 销售月份不能大于当前月份
                  throw new RuntimeException ('B日期错误月份过大，请勿发送还未发生销量的信息：'+dates);
            }
            if((sellDate.month + 1)>=month&&sellDate.getAt(Calendar.DAY_OF_MONTH)>day){// 销售号数不能大于当前号数
                throw new RuntimeException ('B日期错误号数过大，请勿发送还未发生销量的信息：'+dates);

            }
            //   println ""+simpleDateFormat.format(sellDate)+simpleDateFormat.format(now.plus(-6))+"::"+sellDate.after(now.plus(-6))
            if(sellDate.before(now.plus(-30))){// 销售号数不能小于 5(-6) 天之前
                  throw new RuntimeException ('B日期错误号数过期，请勿发送25天之前的销量：'+dates);

            }

            if(!marketFlag&&((sellDate.month + 1)>=month && sellDate.getAt(Calendar.DAY_OF_MONTH)==day)){// 1 8 点之前不能发送当天的销售信息
                if(hour<18)
                     throw new RuntimeException ('B日期错误，请勿发送今天销量的信息：'+dates);

            }
        }


        def prePosSell= PosSell.findAll("from com.hhpc.wechat.domain.PosSell as b where b.posInfo.id=${posInfo.id} and b.sellDate=timestamp(\'${simpleDateFormat.format(sellDate)}\')")
        // 删除已解析信息：必须是有同日期的已经解析才删除，即使新短信没有解析成功原短信也可能被删除
        //  println "DDDDDD::::"+remark
        if(remark&&remark.indexOf('T')>-1){//
            if(prePosSell.size()>0){
                println  "delete mergerSms.id:"+mergerSms.id
                def lastSms=prePosSell[0].sms
                def sql = new groovy.sql.Sql(dataSource)
                prePosSell.each{posSell->
                    sql.execute("delete from SELL_ITEM where POS_SELL_ID = ?" , [posSell.id])
                }
                def delSql="delete from Pos_Sell b where b.pos_Info_id=? and b.sell_Date=timestamp(\'${simpleDateFormat.format(sellDate)}\')"
                sql.execute(delSql,[posInfo.id])
                def fStatus= SmsStatus.findByName("报数废弃短信");
                if(lastSms){
                    lastSms.status=fStatus
                    lastSms.infos='报数废弃短信'
                }
            }


            mergerSms.message=mergerSms.message.replace("[T","[")

        }

        else if(prePosSell.size()>0)  {
            mergerSms.status=SmsStatus.findByFlagId(10);
              throw new RuntimeException (dates+posName+"的信息已经解析。");
        }

        def seller
        def sellers=Seller.executeQuery("select  distinct a from com.hhpc.wechat.domain.Seller a ,com.hhpc.wechat.domain.PosInfo b where a.name='${sellerName}' and b.subArea.area.id=${posInfo.posInfo.subArea.area.id} and a in elements(b.sellers)")
        if(sellers==null||sellers.size()==0){
            seller=new Seller();
            seller.name=sellerName

            seller.posInfos=[posInfo.posInfo]
            seller.mobile=mergerSms.number
            seller.status=TDIf.get(1)
            seller.lastSms=mergerSms

            if(posInfo.posInfo.sellers==null)posInfo.posInfo.sellers=[]
            posInfo.posInfo.sellers.add(seller)

            if (!seller.hasErrors() && seller.save()) {
                //println "save ok!"
            }
            else {
                seller.errors.allErrors.each {
                    println "save seller error:"+it
                }
            }

        }else{//seller 存在
            seller=sellers.find{
                it.posInfos.contains(posInfo.posInfo)
            }
            if(!seller){//没找到对应
                seller=sellers[0]
                seller.posInfos.add(posInfo.posInfo)

                if(posInfo.posInfo.sellers==null)posInfo.posInfo.sellers=[]
                posInfo.posInfo.sellers.add(seller)

            }

            seller.lastSms=mergerSms
        }
        if(!mergerSms.number.equals(seller.mobile)){
            seller.mobile=mergerSms.number
        }

        if(lists.size()<1)
              throw new RuntimeException ('B短信格式错误！请仔细检查');

        def roomTypes= RoomType.list();


        if(marketFlag){

            println "商超报数短信..."

            def marketMap=[:]
            lists.each{breedListStr->//每个list为一个品种的数据
                SellItem item=new SellItem();
                item.gifts=[]

                breedListStr=breedListStr.replaceAll('返现','送返现')
                def breedLists=breedListStr.split("送")
                def idx=0
                breedLists.each{breeds->
                    def breedMatch= breeds =~/(.*\D)(\d*)/ //品种短称
                    breedMatch.each{breedShort->

                        def breedShortName=breedShort[1]
                        def breed=Breed.find("from com.hhpc.wechat.domain.Breed b where b.shortName='${breedShortName}' and b.brand.ourFlag='我方'");
                        def breedNum=breedShort[2].toInteger()
                        if(breed==null)throw new RuntimeException ('B品种不存在或格式错误：'+breedShortName);
                        if(breedNum<0)throw new RuntimeException ('数量出错：'+breedShortName);
                        if(idx==0){//卖品
                            idx++
                            item.breed=breed
                            item.sellNum=breedNum
                            def brandList=marketMap["${item.breed.brand.id}"]
                            if(!brandList) brandList=[]
                            brandList.add(item)

                            marketMap["${item.breed.brand.id}"]=brandList

                        }else{//赠品

                            SellItemGift gift=new SellItemGift();
                            //我方所有产品的简称不能重复
                            gift.breed=breed
                            gift.sellNum=breedNum
                            gift.sellItem=item
                            item.gifts.add(gift)

                        }


                    }

                }

            }
            marketMap.each{brandId,breedItems->

                def posSell=new PosSell();
                posSell.posInfo=posInfo
                posSell.seller=seller
                posSell.sellDate=sellDate
                posSell.brand=Brand.get(brandId)
                posSell.roomType=RoomType.get(1);
                posSell.roomNum=0
                posSell.sms=mergerSms

                posSell.items=[]

                breedItems.each{sellItem->
                    posSell.items.add(sellItem);
                    sellItem.posSell=posSell;

                }



                if(!posSell.hasErrors() && posSell.save()){

                }
                else  {
                    posSell.errors.allErrors.each {println it }
                    throw new RuntimeException ('ServiceException: posSell.save()...');
                }

            }


        }
        else{//解析夜场
            lists.each{list->//每个list为一个品牌的数据
                def posSell=new PosSell();
                posSell.posInfo=posInfo
                posSell.seller=seller

                posSell.sellDate=sellDate

                posSell.items=[]
                //品牌
                def sizeOf=0
                //青9卡纯320/欢460/冰72

                finder = list =~ /^\D+\d+[大|中|小]?\D/ //房间类型以品牌+房数+房间类型(一个字：即间、卡、台)

                finder.each{
                    sizeOf=  it.size()
                    def     brand = it =~ /^\D+/ //首字以品牌短称开始(brand.shortName)
                    brand.each{brandshortName->

                        posSell.brand=Brand.findByShortName(brandshortName)
                    }
                    if(!posSell.brand)
                         throw new RuntimeException ('B品牌有误：'+it);
                    def     roomType = it =~ /[大|中|小]?\D$/ //首字以品牌短称开始(brand.shortName)
                    //开房类型
                    roomType.each{roomTypeName->
                        roomTypes.each{roomTypeA->
                            if(roomTypeA.name.equals(roomTypeName.replace("间","房")))
                                posSell.roomType=roomTypeA
                        }
                    }
                    if(!posSell.roomType)
                         throw new RuntimeException ('B开房(台)类型错误：'+it);

                    def num = it =~ /\d+///中间数字即为房间数目
                    num.each{ posSell.roomNum=it.toInteger() }
                    if(posSell.roomNum==null||posSell.roomNum<0){
                        throw new RuntimeException ('B开房(台)数目错误：'+it);
                    }
                }
                def breeds=list[sizeOf..-1].split("/")  //所有品种
                breeds.each{
                    def   sellItem=new SellItem();
                    def breed= it =~/.*\D/ //品种短称

                    breed.each{
                        // println "it.toUpperCase():"+it.toUpperCase()+"posSell.brand:"+posSell.brand
                        def breedShort=it.toUpperCase()
                        def bandShortName=posSell.brand.shortName
                        if(posSell.brand.shortName.equals("青")&&breedShort.equals("精"))
                            breedShort="银" // 青岛 精品纯生是银纯
                        else if(posSell.brand.shortName.equals("青")&&breedShort.equals("金"))
                            breedShort="至" // 青岛 金纯是至尊
                        else if((bandShortName.equals("科")||bandShortName.equals("喜")||bandShortName.equals("蓝")||bandShortName.equals("虎"))&&(breedShort.equals("支")||breedShort.equals("罐")))   //||bandShortName.equals("力")
                            breedShort=bandShortName // 这几个品牌不用分支和罐


                        sellItem.breed=Breed.findByShortNameAndBrand(breedShort,posSell.brand);
                    }
                    if(sellItem.breed==null)throw new RuntimeException ('B品种不存在或格式错误：'+list);
                    def count= it =~/\d+$/  //品种开瓶数
                    count.each{
                        sellItem.sellNum=it.toInteger()
                    }
                    println "sellItem count:"+sellItem.sellNum
                    if(sellItem.sellNum==null||sellItem.sellNum<0){
                        throw new RuntimeException ('B开瓶数错误：'+it);
                    }
                    if(sellItem.sellNum>0){
                        sellItem.posSell=posSell;
                        posSell.items.add(sellItem)
                    }
                }
                if(posSell.roomNum>0){
                    if(posSell.items.size()<1)
                        throw new RuntimeException ("B${posSell.brand.name}${posSell.roomNum}${posSell.roomType.name}后面的品种数目不能为0");
                    posSell.sms=mergerSms
                    PosSell.get(-1)
                    if(!posSell.hasErrors() && posSell.save()){
                          println posSell.errors.allErrors.each {
                              println "error:"+it
                          }
                    }
                    else  {
                        posSell.errors.allErrors.each {println it }
                        throw new RuntimeException ('ServiceException: posSell.save()...');
                    }
                }

                //println "******:"+list+"breeds:"+breeds+"::::"+list.size()
            }
        }
        def status=SmsStatus.findByName("解析成功");
        mergerSms.status=status
        mergerSms.infos= "解析成功"
         mergerSms.remark=remark // 备注

        if(!posInfo.posInfo?.isRun){
            posInfo.posInfo.isRun=TDIf.get(1);
        }else if(posInfo.posInfo?.isRun?.id==2){
            posInfo.posInfo.isRun=TDIf.get(1);
        }


        if(!posInfo.posInfo?.isSeller){
            posInfo.posInfo.isSeller=TDIf.get(1);
        }else if(posInfo.posInfo?.isSeller?.id==2){
            posInfo.posInfo.isSeller=TDIf.get(1);
        }

        if(!mergerSms.hasErrors() && mergerSms.save()){
           return "解析成功!!!"
        }
        else  {
            mergerSms.errors.allErrors.each {println "mergerSms.save():"+it }
            throw new RuntimeException ('ServiceException: mergerSms.save()...');
        }

    }
}
