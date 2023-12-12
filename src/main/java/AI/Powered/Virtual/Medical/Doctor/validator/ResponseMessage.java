package AI.Powered.Virtual.Medical.Doctor.validator;


public class ResponseMessage {


    private String Result_Code;

    private String Message;

    public ResponseMessage(String code, String message){
        this.Result_Code = code;
        this.Message = message;
    }

    public String getResult_Code() {
        return Result_Code;
    }

    public void setResult_Code(String result_Code) {
        Result_Code = result_Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


}
