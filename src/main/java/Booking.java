

import java.io.*;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.concurrent.Callable;
import java.util.stream.Stream;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import javax.ws.rs.core.UriBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.codehaus.jackson.map.ObjectMapper;





public  class Booking {
    private static HashMap<String,String> config = new HashMap<>();
    private final String[] uriRepo={"https://cdn-api.co-vin.in/api",
            "/v2/admin/location/states",
        "/v2/admin/location/districts/",
        "/v2/appointment/sessions/public/calendarByDistrict",
        "Dummy",
        "/v2/auth/public/generateOTP"
    };
    private static Booking instance;

    private Booking(){}

    public static Booking getInstance(){
        try {
            if (instance == null) {
                instance = new Booking();
            }
        } catch(Exception e) {
            System.out.println("Exception occurred while instantiating Booking");
            e.printStackTrace();
        }
        return instance;
    }

//    private final var getOptions = {
//            headers: {
//                'accept'          : 'application/json, text/plain, */*',
//                'origin'          : 'https://selfregistration.cowin.gov.in',
//                'referer'         : 'https://selfregistration.cowin.gov.in/',
//                'user-agent'      : 'Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36',
//                'content-type'    : 'application/json',
//                'pragma'          : 'no-cache',
//                'cache-control'   : 'no-cache',
//                'sec-ch-ua'       : '" Not A;Brand";v="99", "Chromium";v="90", "Google Chrome";v="90"',
//                'sec-ch-ua-mobile': '?0',
//                'sec-fetch-site'  : 'cross-site',
//                'sec-fetch-mode'  : 'cors',
//                'sec-fetch-dest'  : 'empty',
//                'accept-language' : 'en-IN,en;q=0.9,ta-IN;q=0.8,ta;q=0.7,en-GB;q=0.6,en-US;q=0.5.'
//    }
//}

    public void getConfiguration(){
        String filePath="/Users/eswari/Documents/Sample/src/main/java/config.txt";
        File configFile = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(configFile));
            String line;
            while ((line = br.readLine()) != null){
                line.strip();
                String[] param = line.split("=");
                config.put(param[0],param[1]);
            }
        }
        catch(FileNotFoundException e){ // file exceptions
            System.out.println("FileNotFoundException occurred " + filePath);
        }
        catch(IOException e){
            System.out.println("IOException occurred");
        }
    }


    private URI getRequestURI(RequestType requestType) throws URISyntaxException
    {
        String uriString = uriRepo[0] + uriRepo[requestType.getValue()];
        URIBuilder urib = new URIBuilder(uriString);
        URI uri;
        if(requestType == RequestType.GET_VACCINE_CALENDER_BY_DISTRICT) {
            //need to add district id and date in the uri
            uri = urib.addParameter("district_id", "294")
                    .addParameter("date", "03-06-2021").build();
        } else {
            uri = urib.build();
        }
        return uri;
    }


    public String sendRequest(RequestType requestType){
        HttpRequest request;
        String resp = null;

        //lets initiate a REST API
        // create a client
        var client = HttpClient.newHttpClient();
        try {
            //based on the request it could be HTTP GET/POST
            // create a request
            URI uri =   getRequestURI(requestType);

            if(requestType.getValue() > RequestType.GET_REQUEST_START.getValue() && requestType.getValue() < RequestType.POST_REQUEST_START.getValue() ) {
                request = HttpRequest.newBuilder(uri)
                        //URI.create(getRequestURI(requestType)))
                        .header("user-agent","Mozilla/5.0")
                        .header("Accept-Language","en_US")
                        .GET()
                        .build();
            } else {
                var values = new HashMap<String, String>() {{
                    put("mobile", config.get("mobile"));

                }};

                var objectMapper = new ObjectMapper();
                String requestBody = objectMapper
                        .writeValueAsString(values);

                request = HttpRequest.newBuilder(uri)
                        //URI.create(getRequestURI(requestType)))
                        .header("accept", "application/json")
                        .header("user-agent","Mozilla/5.0")
                        .POST(BodyPublishers.ofString(requestBody))
                        .build();
            }
            System.out.println(request);
        // use the client to send the request
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // the response:
            resp = response.body();
            //System.out.println(response.body().get().title);
            //System.out.println(resp);
        }
        catch(InterruptedException | URISyntaxException | IOException e){
            System.out.println("Exception occurred");
            e.printStackTrace();
        }
        return resp;
    }

}
