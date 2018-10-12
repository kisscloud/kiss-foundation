package utils;


import status.Code;
import output.ResultOutput;

public class ResultOutputUtil {

    /**
     * 操作成功
     *
     * @param data Object
     * @return ResultOutput
     */
    public static ResultOutput success(Object data) {


        ResultOutput resultOutput = new ResultOutput();
        resultOutput.setCode(Code.SUCCESS);

        resultOutput.setData(data);

        return resultOutput;
    }

    public static ResultOutput success() {

        ResultOutput resultOutput = new ResultOutput();
        resultOutput.setCode(Code.SUCCESS);

        return resultOutput;
    }

    public static ResultOutput error(int code, String message, Object data) {
        ResultOutput resultOutput = new ResultOutput();
        resultOutput.setCode(code);
        resultOutput.setMessage(message);
        resultOutput.setData(data);

        return resultOutput;
    }

    /**
     * 数据校验错误
     *
     * @param data Object
     * @return ResultOutput
     */
    public static ResultOutput validateError(Object data) {

        ResultOutput resultOutput = new ResultOutput();
        resultOutput.setCode(Code.VALIDATE_ERROR);

        resultOutput.setData(data);

        return resultOutput;
    }


    public static ResultOutput lackParamsError() {

        ResultOutput resultOutput = new ResultOutput();
        resultOutput.setCode(Code.PARAMETER_ERROR);

        return resultOutput;
    }


}
