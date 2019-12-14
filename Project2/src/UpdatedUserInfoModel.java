public class UpdatedUserInfoModel {

    public String mUsername, mOldPassword, mNewPassword, mNewFullname;

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append("\"").append(mUsername).append("\"").append(",");
        sb.append("\"").append(mOldPassword).append("\"").append(",");
        sb.append("\"").append(mNewPassword).append("\"").append(",");
        sb.append("\"").append(mNewFullname).append("\"").append(",");

        return sb.toString();
    }

}