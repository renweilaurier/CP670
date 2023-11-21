package com.example.androidassignments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.app.Activity.RESULT_FIRST_USER;

public class MessageFragment extends Fragment {

    private TextView messageContentTextView;
    private TextView messageIdTextView;
    private Button deleteMessageButton;

    private MessageEntity message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().getSerializable("message") != null) {
            message = (MessageEntity) getArguments().getSerializable("message");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_message_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        messageContentTextView = view.findViewById(R.id.textViewMessageContent);
        messageIdTextView = view.findViewById(R.id.textViewMessageId);
        deleteMessageButton = view.findViewById(R.id.buttonDeleteMessage);
        deleteMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", message.getMessageId());
                getActivity().setResult(RESULT_FIRST_USER, intent);
                ((ChatWindow)getActivity()).deleteMessage(message);
                messageContentTextView.setText("");
                messageIdTextView.setText("");
                deleteMessageButton.setEnabled(false);
            }
        });
        if (message != null) {
            messageContentTextView.setText(message.getMessage());
            messageIdTextView.setText("ID=" + message.getMessageId());
            deleteMessageButton.setEnabled(true);
        } else {
            deleteMessageButton.setEnabled(false);
        }
    }
}