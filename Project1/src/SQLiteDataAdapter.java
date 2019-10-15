import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.PreparedStatement;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAccess {
    Connection conn = null;
    int errorCode = 0;

    public boolean connect(String path) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + path;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = CONNECTION_OPEN_FAILED;
            return false;
        }

    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        switch (errorCode) {
            case CONNECTION_OPEN_FAILED: return "Connection is not opened!";
            case PRODUCT_LOAD_FAILED: return "Cannot load the product!";
        };
        return "OK";
    }

    public ProductModel loadProduct(int productID) {
        try {
            ProductModel product = new ProductModel();

            String sql = "SELECT ProductId, Name, Price, Quantity FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");
            return product;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = PRODUCT_LOAD_FAILED;
            return null;
        }
    }

    public CustomerModel loadCustomer(int id) {
        try {
            CustomerModel c = new CustomerModel();

            String sql = "SELECT * FROM Customers WHERE CustomerID = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            c.mCustomerID = rs.getInt("CustomerID");
            c.mName = rs.getString("Name");
            c.mPhone = rs.getString("Phone");
            c.mAddress = rs.getString("Address");
            return c;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = CUSTOMER_LOAD_FAILED;
            return null;
        }
    }

    public PurchaseModel loadPurchase(int id) {
        try {
            PurchaseModel p = new PurchaseModel();

            String sql = "SELECT * FROM Purchases WHERE PurchaseID = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            p.mPurchaseID = rs.getInt("PurchaseID");
            p.mCustomerID = rs.getInt("CustomerID");
            p.mProductID = rs.getInt("ProductID");
            p.mCost = rs.getDouble("Cost");
            p.mTax = rs.getDouble("Tax");
            p.mTotalCost = rs.getDouble("TotalCost");
            p.mPrice = rs.getDouble("Price");
            p.mQuantity = rs.getDouble("Quantity");
            p.mDate = rs.getString("Data");
            return p;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = CUSTOMER_LOAD_FAILED;
            return null;
        }
    }


    public boolean saveProduct(ProductModel product) {
        String sql = "INSERT INTO Products(ProductID, Name, Price, Quantity) VALUES(?,?,?,?)";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.mProductID);
            pstmt.setString(2, product.mName);
            pstmt.setDouble(3, product.mPrice);
            pstmt.setDouble(4, product.mQuantity);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public boolean saveCustomer(CustomerModel customer) {
        String sql = "INSERT INTO Customers(CustomerID, Name, Address, Phone) VALUES(?,?,?,?)";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customer.mCustomerID);
            pstmt.setString(2, customer.mName);
            pstmt.setString(3, customer.mAddress);
            pstmt.setString(4, customer.mPhone);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public boolean savePurchase(PurchaseModel purchase) {
        String sql = "INSERT INTO Purchase(OrderID, CustomerID, ProductID, Quantity, TotalCost, Price, Cost, Tax, Date) VALUES(?,?,?,?,?,?,?,?,?)";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, purchase.mPurchaseID);
            pstmt.setInt(2, purchase.mCustomerID);
            pstmt.setInt(3, purchase.mProductID);
            pstmt.setDouble(4, purchase.mQuantity);
            pstmt.setDouble(5, purchase.mTotalCost);
            pstmt.setDouble(6, purchase.mPrice);
            pstmt.setDouble(7, purchase.mCost);
            pstmt.setDouble(8, purchase.mTax);
            pstmt.setString(9, purchase.mDate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

}
