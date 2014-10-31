package me.chanjar.weixin.mp.bean;

import me.chanjar.weixin.common.util.xml.AdapterCDATA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by hujun on 14/10/31.
 */
@XmlRootElement(name = "ScanCodeInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="Wx_ScanCode_Info")
public  class ScanCodeInfo {

    @Id
    @GeneratedValue
    Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "ScanType")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String scanType;

    @XmlElement(name = "ScanResult")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String scanResult;

    /**
     * 扫描类型，一般是qrcode
     * @return
     */
    public String getScanType() {

        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    /**
     * 扫描结果，即二维码对应的字符串信息
     * @return
     */
    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }

}