package org.countryinfo.listcountry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ListOfCountryNamesByNameResponse", namespace = "http://www.oorsprong.org/websamples.countryinfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryResponse {

    @XmlElement(name = "ListOfCountryNamesByNameResult", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    protected CountryList countryList;

    public CountryList getListCountry() {
        return countryList;
    }

    public void setListCountry(CountryList countryList) {
        this.countryList = countryList;
    }
}
