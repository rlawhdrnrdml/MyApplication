package hw.myapplication;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    myDBHelper myHelper;
    EditText edtName, edtNum, editResult, editNum;
    Button btnInit, btnEnter, btnEdit, btnDelete, btnState;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("가수 그룹 관리 DB");

        edtName = (EditText) findViewById(R.id.edtName);
        edtNum = (EditText) findViewById(R.id.edtNum);
        editResult = (EditText) findViewById(R.id.editResult);
        editNum = (EditText) findViewById(R.id.editNum);
        btnInit = (Button) findViewById(R.id.btnInit);
        btnEnter = (Button) findViewById(R.id.btnEnter);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnState = (Button) findViewById(R.id.btnState);

        myHelper = new myDBHelper(this);

        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
            }
        });

        btnEnter.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO groupTBL VALUES ('" + edtName.getText().toString() + "'," + edtNum.getText().toString() + ");");
                sqlDB.close();
                btnState.callOnClick();

                Toast.makeText(getApplicationContext(),"입력됨",Toast.LENGTH_SHORT).show();

            }
        });
        btnState.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null);

                String strNames = "그룹 이름" + "\r\n"+"-------" + "\r\n";
                String strNumbers = "인원"+"\r\n"+"-------" +"\r\n";

                while (cursor.moveToNext()){
                    strNames += cursor.getString(0)+"\r\n";
                    strNumbers +=cursor.getString(1)+"\r\n";
                }

                editResult.setText(strNames);
                editNum.setText(strNumbers);

                cursor.close();
                sqlDB.close();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("UPDATE groupTBL SET gNumber = " + edtNum.getText().toString() + " WHERE gName = '" + edtName.getText().toString() + "';");
                sqlDB.close();
                btnState.callOnClick();
                Toast.makeText(getApplicationContext(), "수정됨", Toast.LENGTH_SHORT).show();
                            }
                     });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("DELETE FROM groupTBL WHERE gName = '" + edtName.getText().toString() + "';");
                sqlDB.close();
                btnState.callOnClick();
                Toast.makeText(getApplicationContext(), "삭제됨", Toast.LENGTH_SHORT).show();
                             }
                     });
            }

    public class myDBHelper extends SQLiteOpenHelper{
        public myDBHelper(Context context){
            super(context,"groupDB",null,1);
        }
        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE groupTBL (gName CHAR(20) PRIMARY KEY,gNumber INTEGER);");
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS groupTBL");
            onCreate(db);
        }
    }


}