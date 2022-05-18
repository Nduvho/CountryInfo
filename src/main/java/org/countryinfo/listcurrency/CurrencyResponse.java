package org.countryinfo.listcurrency;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ListOfCurrenciesByNameResponse", namespace = "http://www.oorsprong.org/websamples.countryinfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyResponse {

    @XmlElement(name = "ListOfCurrenciesByNameResult", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    protected CurrencyList currencyList;

    public CurrencyList getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(CurrencyList currencyList) {
        this.currencyList = currencyList;
    }
}
