import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class StoreServer {
    static String dbfile = "/Users/ziyantian/Desktop/SoftwareArchitecture/Project/Project2/data/store.db";

    public static void main(String[] args) {

        HashMap<Integer, UserModel> activeUsers = new HashMap<Integer, UserModel>();

        int totalActiveUsers = 0;

        int port = 1000;

        if (args.length > 0) {
            System.out.println("Running arguments: ");
            for (String arg : args)
                System.out.println(arg);
            port = Integer.parseInt(args[0]);
            dbfile = args[1];
        }

        try {
            SQLiteDataAdapter adapter = new SQLiteDataAdapter();
            Gson gson = new Gson();
            adapter.connect(dbfile);

            ServerSocket server = new ServerSocket(port);

            System.out.println("Server is listening at port = " + port);


            // WATITING FOR THE MESSAGE FROM CLIENT
            while (true) {
                Socket pipe = server.accept();
                PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);
                Scanner in = new Scanner(pipe.getInputStream());

                MessageModel msg = gson.fromJson(in.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.GET_PRODUCT) {
                    System.out.println("GET product with id = " + msg.data);
                    ProductModel p = adapter.loadProduct(Integer.parseInt(msg.data));
                    if (p == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(p);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.GET_CUSTOMER) {
                    System.out.println("GET customer with id = " + msg.data);
                    CustomerModel p = adapter.loadCustomer(Integer.parseInt(msg.data));
                    if (p == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(p);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.PUT_PRODUCT) { // STORE OR UPDATE INFORMATION OF A PRODUCT
                    ProductModel p = gson.fromJson(msg.data, ProductModel.class);
                    System.out.println("PUT command with Product = " + p);
                    int res = adapter.saveProduct(p);
                    if (res == IDataAdapter.PRODUCT_SAVE_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.PUT_PURCHASE) { // STORE OR UPDATE INFORMATION OF A PRODUCT
                    PurchaseModel p = gson.fromJson(msg.data, PurchaseModel.class);
                    System.out.println("PUT command with Product = " + p);
                    int res = adapter.savePurchase(p);
                    if (res == IDataAdapter.PURCHASE_SAVE_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.PUT_CUSTOMER) { // STORE OR UPDATE INFORMATION OF A PRODUCT
                    CustomerModel c = gson.fromJson(msg.data, CustomerModel.class);
                    System.out.println("PUT command with Customer = " + c);
                    int res = adapter.saveCustomer(c);
                    if (res == IDataAdapter.CUSTOMER_SAVE_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.PUT_USER) { // STORE OR UPDATE INFORMATION OF A PRODUCT
                    UserModel u = gson.fromJson(msg.data, UserModel.class);
                    System.out.println("PUT command with User = " + u);
                    int res = adapter.saveUser(u);
                    if (res == IDataAdapter.USER_SAVE_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }


                if (msg.code == MessageModel.LOGIN) {
                    UserModel u = gson.fromJson(msg.data, UserModel.class);
                    System.out.println("LOGIN command with User = " + u);
                    UserModel user = adapter.loadUser(u.mUsername);

                    // VERIFY USER INFORMATION
                    if (user != null && user.mPassword.equals(u.mPassword)) {
                        msg.code = MessageModel.OPERATION_OK;
                        totalActiveUsers++;
                        int accessToken = totalActiveUsers;
                        msg.ssid = accessToken;
                        msg.data = gson.toJson(user, UserModel.class);  // SEND THE WHOLE USER INFORMATION, INCLUDEING USER TYPE, BACK TO THE CLIENT
                        activeUsers.put(accessToken, user);
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));  // answer login command!
                }

                // ONLY CHECK IF THE USER IS IN THE DATABASE OR NOT
                if(msg.code == MessageModel.CHECK_USER){
                    UpdatedUserInfoModel i = gson.fromJson(msg.data, UpdatedUserInfoModel.class);
                    System.out.println("CHECK command with Info = " + i);
                    UserModel user = adapter.loadUser(i.mUsername);

                    if(user != null && user.mPassword.equals(i.mOldPassword)){
                        if(i.mNewPassword != null) {
                            int res = adapter.updateUserPassword(i);
                            if(res == adapter.USERINFO_UPDATE_OK) {
                                msg.code = MessageModel.OPERATION_OK;
                                msg.data = gson.toJson(res);
                                out.println(gson.toJson(msg));
                            }
                        }
                        if(i.mNewFullname != null){
                            int res = adapter.updateUserFullname(i);
                            if(res == adapter.USERINFO_UPDATE_OK) {
                                msg.code = MessageModel.OPERATION_OK;
                                msg.data = gson.toJson(res);
                                out.println(gson.toJson(msg));
                            }
                        }
                    }
                    else{
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.UPDATE_USER_TYPE) { // STORE OR UPDATE INFORMATION OF A PRODUCT
                    UserModel u = gson.fromJson(msg.data, UserModel.class);
                    System.out.println("UPDATE command with User = " + u);
                    int res = adapter.updateUserType(u);
                    if (res == IDataAdapter.USERTYPE_UPDATE_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }


                //**** SUMMARY PURCHASE IS SUMMERY TO THIS
                if (msg.code == MessageModel.GET_PURCHASE_LIST) {  // ONLY NEED TO DEFINE A NEW LIST MODEL TO CONVERT A LIST OF OBJECTS
                    int id = Integer.parseInt(msg.data);
                    PurchaseListModel res = adapter.loadPurchaseHistory(id);
                    msg.code = MessageModel.OPERATION_OK;
                    msg.data = gson.toJson(res);
                    out.println(gson.toJson(msg));  // answer get purchase history!!!
                }

                if (msg.code == MessageModel.SEARCH_PRODUCT) {
                    //**** NEED TO CHANGE THE VALUES TO USER INPUT

                    // NEW
                    ProductSearchModel model = new ProductSearchModel();
                    model = gson.fromJson(msg.data, ProductSearchModel.class);
                    //String name = "Apple";
                    //double min = 0, max = 10000;
                   // ProductListModel res = adapter.searchProduct(name, min, max);
                    ProductListModel res = adapter.searchProduct(model);
                    //

                    msg.code = MessageModel.OPERATION_OK;
                    msg.data = gson.toJson(res);
                    out.println(gson.toJson(msg));  // answer get purchase history!!!
                }


                // add responding to GET_USER, PUT_USER,...
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}