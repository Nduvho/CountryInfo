package org.countryinfo.listcurrency;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Currencies", namespace = "http://www.oorsprong.org/websamples.countryinfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Currencies {

    @XmlElement(name = "sISOCode", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    public String sISOCode;
    @XmlElement(name = "sName", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    public String sName;

    public String getsISOCode() {
        return sISOCode;
    }

    public void setsISOCode(String sISOCode) {
        this.sISOCode = sISOCode;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }
}
