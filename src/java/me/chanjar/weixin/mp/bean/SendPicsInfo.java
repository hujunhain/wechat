package me.chanjar.weixin.mp.bean;

import me.chanjar.weixin.common.util.xml.AdapterCDATA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujun on 14/10/31.
 */
@XmlRootElement(name = "SendPicsInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="Wx_SendPics_Info")
public   class SendPicsInfo {

    @Id
    @GeneratedValue
    Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "Count")
    private Long count;

    @XmlElementWrapper(name="PicList")
    @XmlElement(name = "item")
    protected final List<Item> picList = new ArrayList<Item>();

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<Item> getPicList() {
        return picList;
    }

    @XmlRootElement(name = "item")
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "WxXmlMessage.SendPicsInfo.Item")
    public static class Item {

        @XmlElement(name = "PicMd5Sum")
        @XmlJavaTypeAdapter(AdapterCDATA.class)
        private String PicMd5Sum;

        public String getPicMd5Sum() {
            return PicMd5Sum;
        }

        public void setPicMd5Sum(String picMd5Sum) {
            PicMd5Sum = picMd5Sum;
        }
    }
}