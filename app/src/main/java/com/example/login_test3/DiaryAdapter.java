package com.example.login_test3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
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


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {

    private ArrayList<Diarydata> mData;
    private Context mContext;


    //이미지 변수
    private static final int REQUEST_CODE = 0;
    private int PICK_IMAGE_REQUEST = 1;
    Uri uri = null;

    long now = System.currentTimeMillis(); // 현재 시간 가져오기.
    String getTime; //가져온 현재 시간을 String에 넣을 변수.


    // 생성자에서 데이터 리스트 객체를 전달받음.
    public DiaryAdapter(ArrayList<Diarydata> list) {
        mData = list;
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public DiaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.diary_item, parent, false);
        DiaryAdapter.ViewHolder vh = new DiaryAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull DiaryAdapter.ViewHolder holder, int position) {

        Diarydata item = mData.get(position);

        holder.diary_title.setText(item.getDiarytext());
        holder.diary_time.setText(item.getTime());
        holder.icon.setImageURI(item.getIconmuri());




        // holder.mView.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //   public void onClick(View v) {


        //   }
        //});

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        ImageView icon;
        TextView diary_title;
        TextView diary_time;
        public View mView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;

            diary_title = itemView.findViewById(R.id.diarytext_line1);
            diary_time = itemView.findViewById(R.id.diarytext_line2);
            icon = itemView.findViewById(R.id.diaryimage_line);

            itemView.setOnCreateContextMenuListener(this);

            // 아이템 클릭 이벤트 처리.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //쉐어드 연결
                    SharedPreferences pref = mContext.getSharedPreferences("diary", MODE_PRIVATE);
                    SharedPreferences pref1 = mContext.getSharedPreferences("diary_count", MODE_PRIVATE);

                    SharedPreferences.Editor editor = pref.edit();
                    SharedPreferences.Editor editor1 = pref1.edit();

                    Diarydata diaryRecyclerItem = mData.get(getAdapterPosition());

                    int number = diaryRecyclerItem.getKeynumber();


                    //쉐어드 정보를 저장할 변수
                    String deletedata = null;


                    int count = 0;
                    count = pref1.getInt("key1", 0);
                    for (int i = 0; count > i; i++) {
                        deletedata = pref.getString("key" + i, " ");
                        if (!deletedata.equals(" ")) {
                            String[] array = deletedata.split("#");

                            if (array.length == 4) {
                                String key = array[0];
                                int keynumbercheck = Integer.parseInt(key);
                                String title = array[1];
                                String date = array[2];
                                String uri = array[3];
                                Log.e("가나다라마","가나다라바: "+uri);
                                int keynumnum = mData.get(getAdapterPosition()).getKeynumber();
                                //가져온 키값과 담긴 키값이 일치한다면
                                if (keynumnum == keynumbercheck) {
                                    Intent intent  = new Intent(mContext, Diary_see.class);
                                    intent.putExtra("key",keynumnum);
                                    mContext.startActivity(intent);

                                    //editor.remove("key" + i);
                                    //editor.commit();

                                }

                            }

                        }

                    }

                }
            });



        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // 3. 컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록해줍니다. ID 1001, 1002로 어떤 메뉴를 선택했는지 리스너에서 구분하게 됩니다.

            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);


        }

        // 4. 컨텍스트 메뉴에서 항목 클릭시 동작을 설정합니다.
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case 1001:
                        //쉐어드 연결
                        SharedPreferences pref = mContext.getSharedPreferences("diary", MODE_PRIVATE);
                        SharedPreferences pref1 = mContext.getSharedPreferences("diary_count", MODE_PRIVATE);

                        SharedPreferences.Editor editor = pref.edit();
                        SharedPreferences.Editor editor1 = pref1.edit();

                        Diarydata diaryRecyclerItem = mData.get(getAdapterPosition());

                        int number = diaryRecyclerItem.getKeynumber();


                        //쉐어드 정보를 저장할 변수
                        String deletedata = null;


                        int count = 0;
                        count = pref1.getInt("key1", 0);
                        for (int i = 0; count > i; i++) {
                            deletedata = pref.getString("key" + i, " ");
                            if (!deletedata.equals(" ")) {
                                String[] array = deletedata.split("#");

                                if (array.length == 4) {
                                    String key = array[0];
                                    int keynumbercheck = Integer.parseInt(key);
                                    String title = array[1];
                                    String date = array[2];
                                    String uri = array[3];
                                    int keynumnum = mData.get(getAdapterPosition()).getKeynumber();
                                    //가져온 키값과 담긴 키값이 일치한다면
                                    if (keynumnum == keynumbercheck) {
                                        Intent intent  = new Intent(mContext, Diary_edit_box.class);
                                        intent.putExtra("key",keynumnum);
                                        mContext.startActivity(intent);

                                        //editor.remove("key" + i);
                                        //editor.commit();

                                    }

                                }

                            }

                        }
                        //해당 데이터를 삭제하고
                        // mData.remove(getAdapterPosition());
                        //어댑터에서 RecyclerView에 반영하도록 한다.
                        //notifyItemRemoved(getAdapterPosition());
                        //notifyItemRangeChanged(getAdapterPosition(), mData.size());

                        //Intent intent  = new Intent(mContext, Diary_edit.class);
                        // mContext.startActivity(intent);

                        break;


                    case 1002:

                        //쉐어드 연결
                        SharedPreferences pref12 = mContext.getSharedPreferences("diary", MODE_PRIVATE);
                        SharedPreferences pref112 = mContext.getSharedPreferences("diary_count", MODE_PRIVATE);

                        SharedPreferences.Editor editor12 = pref12.edit();
                        SharedPreferences.Editor editor112 = pref112.edit();

                        Diarydata diaryRecyclerItem12 = mData.get(getAdapterPosition());

                        int number12 = diaryRecyclerItem12.getKeynumber();


                        //쉐어드 정보를 저장할 변수
                        String deletedata12 = null;


                        int count12 = 0;
                        count = pref112.getInt("key1", 0);
                        for (int i = 0; count > i; i++) {
                            deletedata12 = pref12.getString("key" + i, " ");
                            if (!deletedata12.equals(" ")) {
                                String[] array = deletedata12.split("#");

                                if (array.length == 4) {
                                    String key = array[0];
                                    int keynumbercheck = Integer.parseInt(key);
                                    String title = array[1];
                                    String date = array[2];
                                    String uri = array[3];
                                    int keynumnum = mData.get(getAdapterPosition()).getKeynumber();
                                    //가져온 키값과 담긴 키값이 일치한다면
                                    if (keynumnum == keynumbercheck) {

                                        editor12.remove("key" + i);
                                        editor12.commit();

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

    public DiaryAdapter(Context context, ArrayList<Diarydata> list) {
        mData = list;
        mContext = context;
    }

    //-------------------------------------------------------------

    //어댑터 내에서 커스텀 리스너 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemClick(int position);
    }

    /// 코드 계속 ...


    // 리스너 객체 참조를 저장하는 변수
    private DiaryAdapter.OnItemClickListener mListener = null;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(DiaryAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }


    /// ... 코드 계속.



}
