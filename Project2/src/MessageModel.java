public class MessageModel {

    public static final int LOGIN = 0;
    public static final int LOGOUT = 1;

    public static final int GET_USER = 10;
    public static final int CHECK_USER = 100;
    public static final int PUT_USER = 11;

    public static final int GET_PRODUCT = 20;
    public static final int PUT_PRODUCT = 21;

    public static final int GET_CUSTOMER = 30;
    public static final int PUT_CUSTOMER = 31;

    //NEW
    public static final int GET_PURCHASE = 40;
    public static final int PUT_PURCHASE = 41;
    //

    public static final int GET_PURCHASE_LIST = 500;    // NEED CUSTOMERID
    // NEW
    public static final int PUT_PURCHASE_LIST = 500;
    // NEED MORE

    public static final int SEARCH_PRODUCT = 600;

    public static final int UPDATE_USER_PASSWORD = 700;
    public static final int UPDATE_USER_FULLNAME = 701;
    public static final int UPDATE_USER_TYPE = 702;



    public static final int OPERATION_OK = 1000; // server responses!
    public static final int OPERATION_FAILED = 1001;



    public int code;    // OPERATION
    public int ssid;
    public String data; // ADDITIONAL INFORMATION

    public MessageModel() {
        code = 0;
        data = null;
    }

    public MessageModel(int code, String data) {
        this.code = code;
        this.data = data;
    }
    public MessageModel(int code, int ssid, String data) {
        this.code = code;
        this.ssid = code;
        this.data = data;
    }
}
