package com.hhpc.wechat.domain


/**
 * Created by Administrator on 2014/9/30.
 */
/*
场所信息
 */
import grails.persistence.*

@Entity
class PosInfo {

    transient domainModifyLogService
    String name;//场所名称
    String alias;//别名、简称
    String posCode;//场所编码
    String address //地址
//    ManagerType managerType  //场所经营类型
//    ScaleType scaleType //规模类型
//    GradeType gradeType //档次类型
//    CooperationType cooperationType //合作方式
//    PosLevelType levelType;//行业地位
    User agenter; //责任业代
    String phone  //总机电话
    String fax          //传真
    String servicePhone //服务台电话
    String bossName //终端联系人
    String bossPhone //联系人电话
    List yearPlans

    User sellerLeader //促销组长

    Date createDate;  //  创建时间
    User createUser;  //创建者

    static belongsTo = [dealer: Dealer, subArea: SubArea]  //经销商        片区
    static hasMany = [emulants: Brand, sellers: Seller, rooms: RoomInfo, subs: SubPosInfo]   //场所有多个经销商和多个竞争品牌

    String code;//场所编码

    TDIf isSeller //  是否上我方促销
    TDIf isRun //  是否经营
    int chainNum=0//连锁店间数
    String remark // 备注


    //备用字段
  //  BrandCorp mainBrand;//主要竞品
    int mainCount = 0;//  主要竞品销量
  ///  BrandCorp secBrand;//次要品牌
    int secCount = 0;//  次要品牌销量
    int othCount = 0; //其他竞品销量
    int ourCount = 0 //我方销量


    public PosInfo() {
      //  managerType = new ManagerType();
     //   scaleType = new ScaleType()  //规模类型
      //  gradeType = new GradeType() //档次类型
        //dealer=new  Dealer();
        //agenter=new User();
        createDate = new Date();//创建时间
    }

    static constraints = {
        name(nullable: false)
        subArea(nullable: false)

        emulants(nullable: true)
        agenter(nullable: true)
        posCode(nullable: true)

      //  mainBrand(nullable: true)
        //  mainCount(nullable:true)
       // secBrand(nullable: true)
        //  secCount(nullable:true)
        //  othCount(nullable:true)
        bossName(nullable: true)
        bossPhone(nullable: true)
        name(nullable: true)
        code(nullable: true)
        dealer(nullable: true)
        address(nullable: true)
       // managerType(nullable: true)
      //  scaleType(nullable: true)
      //  gradeType(nullable: true)
      //  dealer(nullable: true)
      //  cooperationType(nullable: true)
        phone(nullable: true)
        fax(nullable: true)
        servicePhone(nullable: true)
        isRun(nullable: true)
        sellerLeader(nullable: true)
        isSeller(nullable: true)
        remark(nullable: true)
        createDate(nullable: true)
        createUser(nullable: true)
       // levelType(nullable: true)

    }
    def afterUpdate() {

        //  domainModifyLogService.recordDirty(this)

    }

    String toString() {
        name
    }

}
