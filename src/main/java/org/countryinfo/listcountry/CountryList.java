package org.countryinfo.listcountry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "CountryList", namespace = "http://www.oorsprong.org/websamples.countryinfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryList {

    @XmlElement(name = "tCountryCodeAndName", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    List<Countries> countries;

    public List<Countries> getCountryNames() {
        return countries;
    }

    public void setCountryNames(List<Countries> countries) {
        this.countries = countries;
    }
}
