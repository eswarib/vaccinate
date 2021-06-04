public enum RequestType {
    GET_REQUEST_START(0),
    GET_STATES(1),
    GET_DISTRICTS(2),
    GET_VACCINE_CALENDER_BY_DISTRICT(3),
    POST_REQUEST_START(4),
    POST_GENERATE_OTP(5);

    private final int value;

    RequestType(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}

