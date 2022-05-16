package org.countryinfo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {

    public static void getCountryList(){
        try {
            String url = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";
            URL object = new URL(url);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","text/xml; charset=utf-8");
            // String countryCode="Canada";
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                    "  <soap12:Body>\n" +
                    "    <ListOfCountryNamesByName xmlns=\"http://www.oorsprong.org/websamples.countryinfo\">\n" +
                    "    </ListOfCountryNamesByName>\n" +
                    "  </soap12:Body>\n" +
                    "</soap12:Envelope>";
            connection(con, xml);

        }catch (Exception e){
            System.out.println( e + "Invalid");
        }

    }

    public static void getCurrencyList(){
        try {
            String url = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";
            URL object = new URL(url);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","text/xml; charset=utf-8");
            // String countryCode="Canada";
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                    "  <soap12:Body>\n" +
                    "    <ListOfCurrenciesByName xmlns=\"http://www.oorsprong.org/websamples.countryinfo\">\n" +
                    "    </ListOfCurrenciesByName>\n" +
                    "  </soap12:Body>\n" +
                    "</soap12:Envelope>";
            connection(con, xml);

        }catch (Exception e){
            System.out.println( e + "Invalid");
        }

    }

    private static void connection(HttpURLConnection con, String xml) throws IOException {
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
        System.out.println("response:" + response);
    }
}
