package org.countryinfo;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
    public static final String URL = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";
    public static final String TYPE="Content-Type";
    public static final String HEADER_VALUE ="text/xml; charset=utf-8";

    public static void getCountryList(){

        try {
            URL object = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty(TYPE,HEADER_VALUE);
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                    "  <soap12:Body>\n" +
                    "    <ListOfCountryNamesByName xmlns=\"http://www.oorsprong.org/websamples.countryinfo\">\n" +
                    "    </ListOfCountryNamesByName>\n" +
                    "  </soap12:Body>\n" +
                    "</soap12:Envelope>";
          String response =  apiRequest(con, xml);
          //System.out.println("response:" + response);
            System.out.println("Data");

            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document parse = newDocumentBuilder.parse(new ByteArrayInputStream(response.getBytes()));
            for(int i = 0;i<parse.toString().length();i++){
              //  System.out.println("code" +parse.getFirstChild());
                System.out.println(parse.getFirstChild().getTextContent());
            }
        }catch (Exception e){
            System.out.println( "Invalid");
        }
    }

    public static void getCurrencyList(){
        try {
            URL object = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty(TYPE,HEADER_VALUE);
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                    "  <soap12:Body>\n" +
                    "    <ListOfCurrenciesByName xmlns=\"http://www.oorsprong.org/websamples.countryinfo\">\n" +
                    "    </ListOfCurrenciesByName>\n" +
                    "  </soap12:Body>\n" +
                    "</soap12:Envelope>";
         String response = apiRequest(con, xml);

            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document parse = newDocumentBuilder.parse(new ByteArrayInputStream(response.getBytes()));
            for(int i = 0;i<parse.toString().length();i++){
                //  System.out.println("code" +parse.getFirstChild());
                System.out.println(parse.getFirstChild().getTextContent());
            }
        }catch (Exception e){
            System.out.println("Invalid");
        }
    }

    private static String apiRequest(HttpURLConnection con, String xml) throws IOException {
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(xml);
        wr.flush();
        wr.close();
        String responseStatus = con.getResponseMessage();
        System.out.println(responseStatus);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
