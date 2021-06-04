public class MyBooking {
    public static void main(String[] args){
        //get booking instance
        Booking bi = Booking.getInstance();

        //lets read all the parameters from a file and store locally
        //parameters are VaccineType, Dose, FeeType,AgeCategory,MobileNumber,DisctrictId
        bi.getConfiguration();

        //lets get availability districtwise
        String response = bi.sendRequest(RequestType.GET_VACCINE_CALENDER_BY_DISTRICT);
        System.out.println(response);

        //lets try to send otp authentication and getback access_token
        response = bi.sendRequest(RequestType.POST_GENERATE_OTP);
        System.out.println(response);
    }
}
