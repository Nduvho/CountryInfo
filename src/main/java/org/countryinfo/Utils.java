package org.countryinfo;

import okhttp3.*;
import org.countryinfo.listcountry.CountryResponse;
import org.countryinfo.listcountry.Countries;
import org.countryinfo.listcurrency.Currencies;
import org.countryinfo.listcurrency.CurrencyResponse;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Logger;


public class Utils {

    public static final String BASEURL = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";
    public static final String TYPE = "text/xml; charset=utf-8";

    static final Logger logger = Logger.getLogger(Utils.class.getSimpleName());

    protected static String marshallObject(Object object){
        StringWriter sw = new StringWriter();
        String objXMLString = "";
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(object, sw);
            objXMLString = sw.toString();
          //  logger.info(object.getClass().getSimpleName().toString()+" XML:\n"+objXMLString);
        } catch (JAXBException e) {
            logger.info("Error marshalling: "+object.getClass().getSimpleName()+"\n" + e.getMessage());
            return null;
        }

        return objXMLString;
    }

    protected static Object unMarshallObject(String responseString, Class responseClass){
        Object responseObject;
        try {
            SOAPMessage message = MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(responseString.getBytes())); //Create soap message from api response
            Unmarshaller unmarshaller = JAXBContext.newInstance(responseClass).createUnmarshaller(); //Create CreateXMTransactionResponse unmarshaller
            responseObject = unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument()); //Map to CreateXMTransactionResponse object
        }
        catch(Exception e){
            logger.info("Error unmarshalling: "+responseClass.getSimpleName()+"Response String: \n" + e.getMessage());
            return null;
        }

        return responseObject;
    }

    protected static String xmlCountry(String requestBody, String url, String mediaType) {

        requestBody="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <ListOfCountryNamesByName xmlns=\"http://www.oorsprong.org/websamples.countryinfo\">\n" +
                "    </ListOfCountryNamesByName>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";

        return apiRequest(requestBody, url, mediaType);

    }


    protected static String xmlCurrency(String requestBody, String url, String mediaType) {

        requestBody="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <ListOfCurrenciesByName xmlns=\"http://www.oorsprong.org/websamples.countryinfo\">\n" +
                "    </ListOfCurrenciesByName>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";

        return apiRequest(requestBody, url, mediaType);
    }

    @Nullable
    private static String apiRequest(String requestBody, String url, String mediaType) {
        final MediaType SOAP_MEDIA_TYPE = MediaType.parse(mediaType);
        RequestBody body = RequestBody.create(SOAP_MEDIA_TYPE, requestBody);

        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-type","text/xml; charset=UTF-8")
                .build();
        OkHttpClient client = new OkHttpClient();
        try {
            Response resp = client.newCall(request).execute();
            return resp.body().string();
        } catch (IOException e) {
            // logger.info("API Request Exception Message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a generic soap string
     *
     * @param xmlString
     * @return
     */
    protected String createGenericSoapString(String xmlString) {
        String soapMessageFromString = "";
        try {
            soapMessageFromString = getSoapMessageFromString(xmlString);
        } catch (SOAPException | IOException e) {
            e.printStackTrace();
        }
        return soapMessageFromString;
    }

    /**
     * Return string soap envelope
     *
     * @param xml
     * @return
     * @throws SOAPException
     * @throws IOException
     */
    private String getSoapMessageFromString(String xml) throws SOAPException, IOException {
        SOAPMessage soapEnvelope = createSoapEnvelope(xml);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapEnvelope.writeTo(out);

        return new String(out.toByteArray());
    }

    /**
     * Adds the soap body to envelope
     *
     * @param xml
     * @return
     * @throws SOAPException
     */
    private static SOAPMessage createSoapEnvelope(String xml) throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();

        SOAPBody soapBody = envelope.getBody();
        Document document = null;
        try {
            document = loadXMLFromString(xml);
        } catch (Exception e) {
            logger.info("createSoapEnvelope Exception: " + e.getMessage());
        }
        soapBody.addDocument(document);
        return soapMessage;
    }

    /**
     * Convert string to document
     *
     * @param xml
     * @return
     * @throws Exception
     */
    private static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }

    public static void getCountryList()  {

        Countries listOfCountry = new Countries();

        String object = Utils.marshallObject(listOfCountry);
        String stringObject = new Utils().createGenericSoapString(object);
        String response = Utils.xmlCountry(stringObject, BASEURL, TYPE );

        CountryResponse countries = (CountryResponse) Utils.unMarshallObject(response, CountryResponse.class);

        for (int i = 0; i < countries.getListCountry().getCountryNames().size(); i++)
        {
            String Name = countries.getListCountry().getCountryNames().get(i).getsName();
            String Code = countries.getListCountry().getCountryNames().get(i).getsISOCode();

            System.out.println("Name: " + Name);
            System.out.println("Code: " + Code);
            System.out.println("\t");
        }

    }

    public static void getCurrencyList(){
        Currencies listOfCurrency = new Currencies();

        String object = Utils.marshallObject(listOfCurrency);
        String stringObject = new Utils().createGenericSoapString(object);
        String response = Utils.xmlCurrency(stringObject, BASEURL, TYPE );

        CurrencyResponse currency = (CurrencyResponse) Utils.unMarshallObject(response, CurrencyResponse.class);

        for (int i = 0; i < currency.getCurrencyList().getCurrencies().size(); i++)
        {
            String Name = currency.getCurrencyList().getCurrencies().get(i).getsName();
            String Code = currency.getCurrencyList().getCurrencies().get(i).getsISOCode();

            System.out.println("Name: " + Name);
            System.out.println("Code: " + Code);
            System.out.println("\t");
        }
    }
}
