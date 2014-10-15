package chanjarster.weixin.bean.result;

import chanjarster.weixin.util.json.WxGsonBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 微信用户信息
 * @author chanjarster
 *
 */
@Entity
@Table(name="Wx_user")
public class  WxUser {

  protected boolean subscribe;
  protected String openid;
  protected String nickname;
  protected String sex;
  protected String language;
  protected String city;
  protected String province;
  protected String country;
  protected String headimgurl;
  protected long subscribe_time;
  protected String unionid;
    /*******************/
    public String realName;//真实姓名
    public String phoneNum ;//手机号码
    public Date regionDate ;//注册日期
    public Date createDate;//创建日期
    public String postName; //岗位
    public String deptName ;//部门


    /**********/

    @Id
    @GeneratedValue
    Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
  
  public boolean isSubscribe() {
    return subscribe;
  }
  public void setSubscribe(boolean subscribe) {
    this.subscribe = subscribe;
  }
  public String getOpenid() {
    return openid;
  }
  public void setOpenid(String openid) {
    this.openid = openid;
  }
  public String getNickname() {
    return nickname;
  }
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
  public String getSex() {
    return sex;
  }
  public void setSex(String sex) {
    this.sex = sex;
  }
  public String getLanguage() {
    return language;
  }
  public void setLanguage(String language) {
    this.language = language;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getProvince() {
    return province;
  }
  public void setProvince(String province) {
    this.province = province;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getHeadimgurl() {
    return headimgurl;
  }
  public void setHeadimgurl(String headimgurl) {
    this.headimgurl = headimgurl;
  }
  public long getSubscribe_time() {
    return subscribe_time;
  }
  public void setSubscribe_time(long subscribe_time) {
    this.subscribe_time = subscribe_time;
  }
  public String getUnionid() {
    return unionid;
  }
  public void setUnionid(String unionid) {
    this.unionid = unionid;
  }
  
  public static WxUser fromJson(String json) {
    return WxGsonBuilder.INSTANCE.create().fromJson(json, WxUser.class);
  }

    public String getPhoneNum() {
        return phoneNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getRegionDate() {
        return regionDate;
    }

    public void setRegionDate(Date regionDate) {
        this.regionDate = regionDate;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
