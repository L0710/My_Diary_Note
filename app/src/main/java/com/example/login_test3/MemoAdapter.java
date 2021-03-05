package com.example.login_test3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    private ArrayList<Memodata> mData;
    private Context mContext;

    long now = System.currentTimeMillis(); // 현재 시간 가져오기.
    String getTime; //가져온 현재 시간을 String에 넣을 변수.


    // 생성자에서 데이터 리스트 객체를 전달받음.
    public MemoAdapter(ArrayList<Memodata> list) {
        mData = list;
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.memo_item, parent, false);
        MemoAdapter.ViewHolder vh = new MemoAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull MemoAdapter.ViewHolder holder, int position) {

        Memodata item = mData.get(position);

        holder.memo_title.setText(item.getMemoStr());
        holder.memo_time.setText(item.getTimeStr());



    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        ImageView icon;
        TextView memo_title;
        TextView memo_time;
        public View mView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;

            memo_title = itemView.findViewById(R.id.textview_line1);
            memo_time = itemView.findViewById(R.id.textview_line2);

            itemView.setOnCreateContextMenuListener(this);

            // 아이템 클릭 이벤트 처리.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO : process click event.
                    // int pos = getAdapterPosition();

                    // if (pos != RecyclerView.NO_POSITION) {
                    //     Memodata item = mData.get(pos);


                    //    notifyItemChanged(pos);
                    //    if (mListener != null) {
                    //        mListener.onItemClick(v, pos);

                    //    }
                    //  }

                    //Date 생성하기
                    final Date date = new Date(now);
                    //가져오고 싶은 형식으로 가져오기
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    getTime = sdf.format(date);

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                    // 다이얼로그를 보여주기 위해 edit_box.xml 파일을 사용합니다.

                    View view = LayoutInflater.from(mContext)
                            .inflate(R.layout.promise_edit_box, null, false);
                    builder.setView(view);
                    final Button ButtonSubmit = (Button) view.findViewById(R.id.box_set_btn);
                    /*final EditText editTextID = (EditText) view.findViewById(R.id.edittext_dialog_id);*/
                    final Button box_cancel = (Button) view.findViewById(R.id.box_cancel);
                    final EditText editTextEnglish = (EditText) view.findViewById(R.id.box_set_edtext);
                    /*final EditText editTextKorean = (EditText) view.findViewById(R.id.edittext_dialog_korean);*/


                    // 6. 해당 줄에 입력되어 있던 데이터를 불러와서 다이얼로그에 보여줍니다.
                    /*editTextID.setText(mData.get(getAdapterPosition()).getIcon().toString());*/
                    editTextEnglish.setText(mData.get(getAdapterPosition()).getMemoStr());
                    /*editTextKorean.setText(mData.get(getAdapterPosition()).getDesc());*/

                    final AlertDialog dialog = builder.create();
                    ButtonSubmit.setOnClickListener(new View.OnClickListener() {

                        // 7. 수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로

                        public void onClick(View v) {
                            /*String strID = editTextID.getText().toString();*/
                            String strEnglish = editTextEnglish.getText().toString();
                            /*String strKorean = editTextKorean.getText().toString();*/

                            //쉐어드 연결
                            SharedPreferences pref = mContext.getSharedPreferences("memo", MODE_PRIVATE);
                            SharedPreferences pref1 = mContext.getSharedPreferences("memo_count", MODE_PRIVATE);

                            SharedPreferences.Editor editor = pref.edit();
                            SharedPreferences.Editor editor1 = pref1.edit();


                            String strKorean = mData.get(getAdapterPosition()).getTimeStr();

                            String fixdata;

                            int count = 0;
                            count = pref1.getInt("key1", 0);
                            for (int i = 0; count > i; i++) {
                                fixdata = pref.getString("key" + i, " ");
                                if (!fixdata.equals(" ")) {
                                    String[] array = fixdata.split("#");

                                    if (array.length == 3) {
                                        String key = array[0];
                                        int keynumbercheck = Integer.parseInt(key);
                                        String title = array[1];
                                        String date = array[2];
                                        int keynumnum = mData.get(getAdapterPosition()).getKeynumber();
                                        //가져온 키값과 담긴 키값이 일치한다면
                                        if (keynumnum == keynumbercheck) {

                                            //editor.remove("key" + i);
                                            //editor.commit();

                                            editor.putString("key"+i, key + "#" + strEnglish + "#" + getTime);
                                            editor.commit();

                                        }

                                    }

                                }

                            }


                            Memodata dict = new Memodata(strEnglish, getTime);


                            // 8. ListArray에 있는 데이터를 변경하고
                            mData.set(getAdapterPosition(), dict);


                            // 9. 어댑터에서 RecyclerView에 반영하도록 합니다.

                            notifyItemChanged(getAdapterPosition());

                            dialog.dismiss();
                        }
                    });

                    box_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }
            });

            //memo_title = itemView.findViewById(R.id.textview_line1);
            //memo_time = itemView.findViewById(R.id.textview_line2);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // 3. 컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록해줍니다. ID 1001, 1002로 어떤 메뉴를 선택했는지 리스너에서 구분하게 됩니다.

            //MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "수정");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            //Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);

            //Date 생성하기
            final Date date = new Date(now);
            //가져오고 싶은 형식으로 가져오기
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            getTime = sdf.format(date);

        }

        // 4. 컨텍스트 메뉴에서 항목 클릭시 동작을 설정합니다.
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    //  case 1001:  // 5. 편집 항목을 선택시


                    //     AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                    // 다이얼로그를 보여주기 위해 edit_box.xml 파일을 사용합니다.

                    //     View view = LayoutInflater.from(mContext)
                    //             .inflate(R.layout.promise_edit_box, null, false);
                    //     builder.setView(view);
                    //      final Button ButtonSubmit = (Button) view.findViewById(R.id.box_set_btn);
                    /*final EditText editTextID = (EditText) view.findViewById(R.id.edittext_dialog_id);*/
                    //     final Button box_cancel = (Button) view.findViewById(R.id.box_cancel);
                    //     final EditText editTextEnglish = (EditText) view.findViewById(R.id.box_set_edtext);
                    /*final EditText editTextKorean = (EditText) view.findViewById(R.id.edittext_dialog_korean);*/


                    // 6. 해당 줄에 입력되어 있던 데이터를 불러와서 다이얼로그에 보여줍니다.
                    /*editTextID.setText(mData.get(getAdapterPosition()).getIcon().toString());*/
                    //     editTextEnglish.setText(mData.get(getAdapterPosition()).getMemoStr());
                    /*editTextKorean.setText(mData.get(getAdapterPosition()).getDesc());*/


                    //     final AlertDialog dialog = builder.create();
                    //     ButtonSubmit.setOnClickListener(new View.OnClickListener() {

                    // 7. 수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로

                    //    public void onClick(View v) {
                    /*String strID = editTextID.getText().toString();*/
                    //       String strEnglish = editTextEnglish.getText().toString();
                    /*String strKorean = editTextKorean.getText().toString();*/

                    //쉐어드 연결
                    //       SharedPreferences pref = mContext.getSharedPreferences("memo", MODE_PRIVATE);
                    //       SharedPreferences pref1 = mContext.getSharedPreferences("memo_count", MODE_PRIVATE);

                    //       SharedPreferences.Editor editor = pref.edit();
                    //       SharedPreferences.Editor editor1 = pref1.edit();


                    //       String strKorean = mData.get(getAdapterPosition()).getTimeStr();

                    //       String fixdata;

                    //       int count = 0;
                    //       count = pref1.getInt("key1", 0);
                    //       for (int i = 0; count > i; i++) {
                    //           fixdata = pref.getString("key" + i, " ");
                    //            if (!fixdata.equals(" ")) {
                    //                String[] array = fixdata.split("#");

                    //                if (array.length == 3) {
                    //                    String key = array[0];
                    //                    int keynumbercheck = Integer.parseInt(key);
                    //                    String title = array[1];
                    //                    String date = array[2];
                    //                    int keynumnum = mData.get(getAdapterPosition()).getKeynumber();
                    //                    //가져온 키값과 담긴 키값이 일치한다면
                    //                    if (keynumnum == keynumbercheck) {

                    //editor.remove("key" + i);
                    //editor.commit();

                    //                        editor.putString("key"+i, key + "#" + strEnglish + "#" + getTime);
                    //                        editor.commit();

                    //                     }

                    //                 }

                    //             }

                    //         }


                    //         Memodata dict = new Memodata(strEnglish, getTime);


                    // 8. ListArray에 있는 데이터를 변경하고
                    //          mData.set(getAdapterPosition(), dict);


                    // 9. 어댑터에서 RecyclerView에 반영하도록 합니다.

                    //           notifyItemChanged(getAdapterPosition());

                    //          dialog.dismiss();
                    //       }
                    //   });

                    //   box_cancel.setOnClickListener(new View.OnClickListener() {
                    //       @Override
                    //       public void onClick(View v) {
                    //           dialog.dismiss();
                    //       }
                    //    });

                    //   dialog.show();

                    //   break;


                    case 1002:

                        //쉐어드 연결
                        SharedPreferences pref = mContext.getSharedPreferences("memo", MODE_PRIVATE);
                        SharedPreferences pref1 = mContext.getSharedPreferences("memo_count", MODE_PRIVATE);

                        SharedPreferences.Editor editor = pref.edit();
                        SharedPreferences.Editor editor1 = pref1.edit();

                        Memodata diaryRecyclerItem = mData.get(getAdapterPosition());

                        int number = diaryRecyclerItem.getKeynumber();


                        //쉐어드 정보를 저장할 변수
                        String deletedata = null;


                        int count = 0;
                        count = pref1.getInt("key1", 0);
                        for (int i = 0; count > i; i++) {
                            deletedata = pref.getString("key" + i, " ");
                            if (!deletedata.equals(" ")) {
                                String[] array = deletedata.split("#");

                                if (array.length == 3) {
                                    String key = array[0];
                                    int keynumbercheck = Integer.parseInt(key);
                                    String title = array[1];
                                    String date = array[2];
                                    int keynumnum = mData.get(getAdapterPosition()).getKeynumber();
                                    //가져온 키값과 담긴 키값이 일치한다면
                                    if (keynumnum == keynumbercheck) {

                                        editor.remove("key" + i);
                                        editor.commit();

                                    }

                                }

                            }

                        }


                        //해당 데이터를 삭제하고
                        mData.remove(getAdapterPosition());
                        //어댑터에서 RecyclerView에 반영하도록 한다.
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), mData.size());

                        break;

                }

                return true;
            }
        };



    }

    public MemoAdapter(Context context, ArrayList<Memodata> list) {
        mData = list;
        mContext = context;

    }

    //-------------------------------------------------------------

    //어댑터 내에서 커스텀 리스너 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    /// 코드 계속 ...


    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }


    /// ... 코드 계속.

}
