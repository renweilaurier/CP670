package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.utils.ChatDatabaseHelper;

public class MessageDetails extends AppCompatActivity {

    private TextView messageContentTextView;
    private TextView messageIdTextView;
    private Button deleteMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        MessageEntity message = (MessageEntity) getIntent().getSerializableExtra("message");
        messageContentTextView = findViewById(R.id.textViewMessageContent);
        messageContentTextView.setText(message.getMessage());
        messageIdTextView = findViewById(R.id.textViewMessageId);
        messageIdTextView.setText("ID=" + message.getMessageId());
        deleteMessageButton = findViewById(R.id.buttonDeleteMessage);
        deleteMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", message.getMessageId());
                setResult(RESULT_FIRST_USER, intent);
                finish();
            }
        });
    }
}