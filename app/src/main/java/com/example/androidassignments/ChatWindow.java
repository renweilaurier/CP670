package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.utils.ChatDatabaseHelper;

public class ChatWindow extends AppCompatActivity {

    private EditText inputEditText;
    private ListView messageListView;
    private Button sendButton;
    private FrameLayout messageDetailsFrameLayout;
//    private MessageFragment messageFragment;

    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private List<MessageEntity> messageList;
    ArrayAdapter<MessageEntity> messageAdapter;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        messageList = new ArrayList<>();
        dbHelper = new ChatDatabaseHelper(ChatWindow.this);
        database = dbHelper.getWritableDatabase();

        inputEditText = findViewById(R.id.editTextInput);
        messageAdapter =
                new ChatAdapter(this, android.R.layout.simple_list_item_1, messageList);
        messageListView = (ListView) findViewById(R.id.listViewMessage);
        messageListView.setAdapter(messageAdapter);
        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ChatWindow", "ID: " + messageList.get(position).getMessageId());
                Log.i("ChatWindow", "MESSAGE: " + messageList.get(position).getMessage());

                if (findViewById(R.id.frameLayoutMessageDetails) == null) {
                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    intent.putExtra("message", messageList.get(position));
                    startActivityForResult(intent, 10);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("message", messageList.get(position));
                    MessageFragment messageFragment = new MessageFragment();
                    messageFragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayoutMessageDetails, messageFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        sendButton = findViewById(R.id.buttonSend);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = inputEditText.getText().toString();
                if (message.matches("")) {
                    Toast.makeText(ChatWindow.this, getString(R.string.EmptyMessage), Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues values = new ContentValues();
                values.put(ChatDatabaseHelper.KEY_MESSAGE, message);
                long messageId = database.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
                messageList.add(new MessageEntity(messageId, message));
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()
                inputEditText.setText("");
            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<MessageEntity> {

        public ChatAdapter(@NonNull Context context, int resource, @NonNull List<MessageEntity> objects) {
            super(context, resource, objects);
        }

        public ChatAdapter(@NonNull Context context, int resource, @NonNull MessageEntity[] objects) {
            super(context, resource, objects);
        }

        public ChatAdapter(@NonNull Context ctx) {
            super(ctx, 0);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                Log.i("ChatWindow", "chat_row_incoming");
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
                Log.i("ChatWindow", "chat_row_outgoing");
            }
            TextView messageTextView = (TextView) result.findViewById(R.id.textViewMessage);
            messageTextView.setText(((MessageEntity) getItem(position)).getMessage());
            return result;
        }
    }

    @SuppressLint("Range")
    @Override
    protected void onResume() {
        Log.i("ChatWindow", "onResume");
        super.onResume();
        messageList.clear();
        Cursor cursor = database.query(true,
                ChatDatabaseHelper.TABLE_NAME, null, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
            int messageId = cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));
            Log.i("ChatWindow", "SQL MESSAGE: " + message);
            Log.i("ChatWindow", "Cursor's column count = " + cursor.getColumnCount());
            messageList.add(new MessageEntity(messageId, message));
            cursor.moveToNext();
        }
        cursor.close();
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        Log.i("ChatWindow", "onCreate");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i("ChatWindow", "onCreate");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("ChatWindow", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("ChatWindow", "onDestroy");
        super.onDestroy();
        database.close();
        dbHelper.close();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i("ChatWindow", "onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i("ChatWindow", "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == RESULT_FIRST_USER) {
                long messageId = data.getLongExtra("id", 0);
                database.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID + " = " + messageId, null);
            }
        }
    }

    public void deleteMessage(MessageEntity message) {
        database.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID + " = " + message.getMessageId(), null);
        messageList.remove(message);
        messageAdapter.notifyDataSetChanged();
    }
}

