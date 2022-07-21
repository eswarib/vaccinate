import java.util.Scanner;

public class MyBooking {
    public static void main(String[] args){
        //get booking instance
        Scanner s = new Scanner(System.in);
        Booking bi = Booking.getInstance();

        //lets read all the parameters from a file and store locally
        //parameters are VaccineType, Dose, FeeType,AgeCategory,MobileNumber,DisctrictId
        bi.getConfiguration();

        TaskManager tm = TaskManager.getInstance();

            Runnable sendRequest = () -> {
                //lets get availability districtwise
                String response = bi.sendRequest(RequestType.GET_VACCINE_CALENDER_BY_DISTRICT);
                System.out.println(response);
            };
            tm.getExecutor().execute(sendRequest);

        //lets try to send otp authentication and getback access_token
        String response = bi.sendRequest(RequestType.POST_GENERATE_OTP);
        System.out.println(response);

        //get the OTP and fire the confirm otp api
        s.nextInt();

        response = bi.sendRequest(RequestType.POST_CONFIRM_OTP);


    }
}
