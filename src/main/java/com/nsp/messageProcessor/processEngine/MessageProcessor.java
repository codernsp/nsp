package com.nsp.messageProcessor.processEngine;

import com.nsp.messageProcessor.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MessageProcessor {

    private static List<Message> messageList ;

    private Message message;

    @Autowired
    public MessageProcessor(Message message){
        if(messageList == null){
            messageList = new ArrayList<Message>();
        }
        this.message = message;
        addMessage(message);
    }

    public static void addMessage(Message message){
        if(message != null){
            messageList.add(message);
        }
    }


}
