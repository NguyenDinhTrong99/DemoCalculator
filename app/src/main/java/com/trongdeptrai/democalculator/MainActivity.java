package com.trongdeptrai.democalculator;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_One, tv_Two, tv_Result;
    private double mVarOne, mVarTwo, mResult;
    private String mTemp = "0"; // biến tạm để lưu số cần tính toán cho biến 1 và biến 2
    private String mCalculation = ""; // biến lưu phép tính
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hàm ánh xạ
        Controlls();
    }

    // hàmn này sử dụng để bắt event khi nhấn mỗi button
    // hàm này dược xml sử dụng với giá trị là onClick
    public void OnButtonsClick(View v){
        switch (v.getId()){
            case R.id.btn_0:
                getNumberForButton("0");
                break;
            case R.id.btn_1:
                getNumberForButton("1");
                break;
            case R.id.btn_2:
                getNumberForButton("2");
                break;
            case R.id.btn_3:
                getNumberForButton("3");
                break;
            case R.id.btn_4:
                getNumberForButton("4");
                break;
            case R.id.btn_5:
                getNumberForButton("5");
                break;
            case R.id.btn_6:
                getNumberForButton("6");
                break;
            case R.id.btn_7:
                getNumberForButton("7");
                break;
            case R.id.btn_8:
                getNumberForButton("8");
                break;
            case R.id.btn_9:
                getNumberForButton("9");
                break;
            case R.id.btn_dot:
                getNumberForButton(".");
                break;
            case R.id.btn_negative:
                convertToNumber();
                break;
            case R.id.btn_result:
                setResult();
                break;
            case R.id.btn_sub:
                getMath("-");
                break;
            case R.id.btn_add:
                getMath("+");
                break;
            case R.id.btn_multi:
                getMath("*");
                break;
            case R.id.btn_division:
                getMath("/");
                break;
            case R.id.btn_div:
                getMath("%");
                break;
            case R.id.btn_delete:
                deleteNumber();
                break;
            case R.id.btn_clear_all:
                clearAll();
                break;
        }
    }
    //hàm ánh xạ
    private void Controlls(){
        tv_One = findViewById(R.id.text_one);
        tv_Two = findViewById(R.id.text_two);
        tv_Result = findViewById(R.id.text_result);
        tv_Two.setText("0");
    }
    //hàm lấy số
    private void getNumberForButton(String number){
        // khi nhấn vào 1 button thì lấy button đó và công vào biến chuổi
       if(mTemp.equals("0")) {
           //nếu biến tạm = 0
           //gắn biến tạm = ""
           mTemp = "";
           // cộng dồn biến tạm với số nhập
           // vd: 1, sau đó nhập 2 thì thành 12
           mTemp += number;
           //đổ dữ liệu lên textview thứ 2
           if(!DoubleToInt(Double.parseDouble(mTemp)).equals("NA")){
               tv_Two.setText(DoubleToInt(Double.parseDouble(mTemp)));
           }else tv_Two.setText(mTemp);

       } else {
           //ngược lại thì tương tự
           mTemp += number;
           if(!DoubleToInt(Double.parseDouble(mTemp)).equals("NA")){
               tv_Two.setText(String.valueOf(DoubleToInt(Double.parseDouble(mTemp))));
           }else tv_Two.setText(mTemp);

       }
       if(!mCalculation.equals("")){
           // nêu đã nhập phép toán
           // đổ dữ liệu lên textview thứ 2 với format: phép tính + số thứ 2
           //vd: 55 + 55
           tv_Two.setText(mCalculation + "\t       " + mTemp);
           // kiểm tra để tránh lỗi khiến dừng ct
           if(!mTemp.equals("-")){
               mVarTwo = Double.parseDouble(mTemp);
           }
           //thực hiện tính toán và đổi màu chữ textview result sau đó tiến hành đổi dư liệu
           Calculating();
           tv_Result.setTextColor(getResources().getColor(R.color.colorResultHint));

           if(!DoubleToInt(mResult).equals("NA")){
               tv_Result.setText("=  " +String.valueOf(mResult));
           }else tv_Result.setText("= " + mResult);

       }

    }

    private void convertToNumber(){
        if(mTemp.equals("0")){
            //nếu ng dùng chưa nhập liệu mà nhấn - trước thì thêm - vào biến tạm
            mTemp = "-";
        }
        if(!mCalculation.equals("")){
            //nếu có phép tính và biến 1
            if( !mTemp.equals("")){
                //nếu biến tạm khác rỗng nghĩa là chưa chuyển đổi sang kiểu số
                // 1 + chuỗi =
                // tiến hành lấy số vừa nhập đổi sang số và nhân với -1
                mTemp = String.valueOf(Double.parseDouble(mTemp) * -1) ;
                //tiến hành set lên textview thứ 2 với phép tính + số vừa nhập
                tv_Two.setText(mCalculation + "    " +mTemp);
                if(!mTemp.equals("-")){
                    // kiểm tra nếu chỉ nhập môi - thì không chuyển sang số cho biến thứ 2
                    mVarTwo = Double.parseDouble(mTemp);
                }
                // có 2 biến 1 và 2 rồi  thì thực hiện tính toán và gắn vào biến mResult;
                Calculating();
                //đổi màu textview result - màu xám
                tv_Result.setTextColor(getResources().getColor(R.color.colorResultHint));
                // gắn kết quả lên textview result
                tv_Result.setText("= " + mResult);
            }else {
                // nếu chưa nhập biến tam cho biến số 2 thì thêm dâu - ở trước
                mTemp = "-";
                //tiến hành set lên textview thứ 2 với phép tính + số vừa nhập
                tv_Two.setText(mCalculation + "    " +mTemp);
                if(!mTemp.equals("-")){
                    // kiểm tra nếu chỉ nhập môi - thì không chuyển sang số cho biến thứ 2
                    mVarTwo = Double.parseDouble(mTemp);
                }
                // có 2 biến 1 và 2 rồi  thì thực hiện tính toán và gắn vào biến mResult;
                Calculating();
                //đổi màu textview result - màu xám
                tv_Result.setTextColor(getResources().getColor(R.color.colorResultHint));
                // gắn kết quả lên textview result
                tv_Result.setText("= " + mResult);
            }
        }else if( mVarOne == 0){
            // nếu biến 1 chưa có(luc đầu khi khởi động ct lên
            if(!mTemp.equals("-")){
                // tương tự không chuyển đổi sang số khi chỉ mới nhập là - để khỏi sinh lỗi break ct
                // nhân số mới nhập với -1
                mTemp = String.valueOf(Double.parseDouble(mTemp) * -1) ;
                // gắn sô mới nhập lên textview 2
                tv_Two.setText(mTemp);
            }
        }

    }
    /* hàm cho các phép toán khi nhấn vào 1 phép toán thì sẽ chuyển biến tạm thành số
    tiếp theo là lấy phép toán gắn vào biến phép toán
     */
    private void getMath(String calculation){
        if(!mTemp.equals("") && mVarOne == 0){
            //nếu đã nhập liệu cho biến số 1
            // chuyển đổi biến tạm sang kiểu số
            mVarOne = Double.parseDouble(mTemp);
            if(!DoubleToInt(mVarOne).equals("NA")){
                tv_One.setText(String.valueOf(DoubleToInt(mVarOne)));
            }else tv_One.setText(String.valueOf(mVarOne));
            // gắn dữ liệu cho biến phép tính (biến toàn cục)
            mCalculation = calculation;
            //gắn phép tính vừa nhập cho textview thứ 2
            tv_Two.setText(mCalculation);
            // reset lại biến tạm cho biến thứ 2
            mTemp = "";
        }else if(!mTemp.equals("") && mVarOne != 0 && !mCalculation.equals("")) {
            //nếu đã có biến số 1 và nhập liệu cho biến thứ 2
            // nghĩa là đã nhập số thứ 2  nhưng chưa chuyển sang dạng số  để tính toán
            //gọi hàm tính toán
            Calculating();
            //gắn biến số 1 = kết quả vừa tính tiếp để tiếp tục thưc hiện phép tính
            mVarOne = mResult;
            if(!DoubleToInt(mVarOne).equals("NA")){
                tv_One.setText(String.valueOf(DoubleToInt(mVarOne)));
            }else tv_One.setText(String.valueOf(mVarOne));
            // tiếp tục set giá trị cho biến phép tính
            mCalculation = calculation;
            //reset dữ liệu
            mTemp = "";
            // đổ dữ liệu phép tính vừa nhập được cho textview thứ 2
            tv_Two.setText(mCalculation);
        }else if(mTemp.equals("") && mVarOne != 0 && !mCalculation.equals("")) {
            //nếu chưa nhập dữ liệu cho biến thứ 2
            // gắn biến thứ 2 bằng = 0 phòng trường hợp nhấn các phép toán thì sẽ lấy biến thứ 2 trước đó để thực hiện
            mVarTwo = 0;
            //gọi hàm tính toán
            Calculating();
            // set biến số 1 = kết quả tính được
            mVarOne = mResult;
            //tiến hành set dữ liệu lên textview thứ 1
            tv_One.setText(String.valueOf(mVarOne));
            //gắn lại giá trị cho biên tính tpoán
            mCalculation = calculation;
            //reset lại dữ liệu
            mTemp = "";
            // đổ dữ liệu phép toàn vừa nhập được cho texview thứ 2
            tv_Two.setText(mCalculation);
        }else if(!mTemp.equals("") && mVarOne != 0){

            tv_One.setText(String.valueOf(mVarOne));
            mCalculation = calculation;
            tv_Two.setText(mCalculation);
        }

    }
    //hàm xóa các số
    private void deleteNumber(){
        try{
            // hàm này sẽ lấy biến number sau đó tiến hành cắt chuỗi
            // cắt phần tử cuối cùng và trả lại kết quả
            if(!mCalculation.equals("") && mTemp.equals("0")){
                //nếu có phép tính và chưa nhập liệu
                // gắn phép tính = biến tạm để xóa phép tính
                mTemp += mCalculation;
                // gắn giá trị null cho biến tính toán
                mCalculation = "";
            }
            if(mTemp.length() > 1){
                //nếu nhập liệu
                //tiến hành xóa số cuối cùng của chuôix
                mTemp = mTemp.substring(0, mTemp.length() -1);
                //đổ lại dư liệu lên textview thứ 2 sau khi xóa
                tv_Two.setText(mCalculation + "    "  + mTemp);
                // kiểm tra nếu biến tạm chứa mỗi í tự - thì không chuyển đổi
                if(!mTemp.equals("-")){
                    mVarTwo = Double.parseDouble(mTemp);
                }
                // gọi hàm tính toán
                Calculating();
                // set text color cho textview result (màu xám)
                tv_Result.setTextColor(getResources().getColor(R.color.colorResultHint));
                //đổi dữ liệu cho textview result
                tv_Result.setText("= " + mResult);
            }else if(mTemp.length() == 1 && !mCalculation.equals("")){
                //nếu xóa còn 1 kí tự
                // gắn biến tạm = 0
                mTemp = "0";
                //đổi dữ liệu lên textview thứ 2
                tv_Two.setText(mCalculation + "    " + mTemp);
                // nếu biến tạm cho biến số 2 chỉ có mỗi í tự - thì không chuyển đổi
                // tránh lỗi khiến dừng ct
                if(!mTemp.equals("-")){
                    mVarTwo = Double.parseDouble(mTemp);
                }
                // thực hiện tính toán và đổi màu textview sau đó gắn dữ liệu lên textview kết quả
                Calculating();
                tv_Result.setTextColor(getResources().getColor(R.color.colorResultHint));
                tv_Result.setText("= " + mResult);
            }
        }catch (AndroidRuntimeException e){
            // bắt lỗi
            Log.e("ERRO_DELETE", e.getMessage());
        }
    }
    //hàm xóa tất cả
    private void clearAll(){
        // sẽ reset tất cả các  biến, cac textview
        mTemp = "0";
        mCalculation = "";
        tv_Two.setText("0");
        tv_One.setText("");
        tv_Result.setText("");
        mVarOne = 0;
        mVarTwo=0;
        mResult =0;
    }
    //hàm thực hiện tính toán
    private void Calculating(){
        // thực hiên tính toán khi đã dữ liệu cho 2 biến
        // sử dụng switch để phân loại và tiến hành tính toán
        switch (mCalculation){
            case "+":
                mResult = mVarOne + mVarTwo;
                break;
            case "-":
                mResult = mVarOne - mVarTwo;
                break;
            case "*":
                mResult = mVarOne * mVarTwo;
                break;
            case "/":
                mResult = mVarOne / mVarTwo;
                break;
            case "%":
                mResult = mVarOne % mVarTwo;
                break;
        }
    }
    /* xử lí khi nhấn button = đổi màu textview sau đó gắn bằng mVarOne để tính tiếp
    */
    private void setResult(){
        // đổi màu textview result(màu đen) và đổ dữ liệu lên textview result
        // cuói cùng là reset biên kết quả để những kết quả sau khi bị dồn
        tv_Result.setTextColor(getResources().getColor(R.color.colorTextNumber));
        mTemp = String.valueOf(mResult);
        mResult = 0;
    }

    //hàm chuyển đổi từ double sang int
    // sử dụng vòng for và cắt chuổi
    private String DoubleToInt(double number){
        //tạo biến để chuyển số sang chuỗi
        String temp = String.valueOf(number).toString();
        //dùng vòng for để thực hiện
        // nêu sau dấu . là số 0 thì trả về kiểu int còn không thì giữ nguyên
        // phải dùng for mới chính xách để lấy vị rrí để cắt
        //ví dụ 705.55
        //thì . sẽ có index = 3
        // sau đó cắt chuỗi ở index thứ 3->4
        if(temp.substring(1,2).equals("0")){
            return temp.substring(0,1);
        }else {
            return "NA";
        }

    }
}
