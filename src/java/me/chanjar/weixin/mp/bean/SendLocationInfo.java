package me.chanjar.weixin.mp.bean;

import me.chanjar.weixin.common.util.xml.AdapterCDATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by hujun on 14/10/31.
 */

@XmlRootElement(name = "SendLocationInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public  class SendLocationInfo {

    @XmlElement(name = "Location_X")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String locationX;

    @XmlElement(name = "Location_Y")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String locationY;

    @XmlElement(name = "Scale")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String scale;

    @XmlElement(name = "Label")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String label;

    @XmlElement(name = "Poiname")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String poiname;

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPoiname() {
        return poiname;
    }

    public void setPoiname(String poiname) {
        this.poiname = poiname;
    }
}