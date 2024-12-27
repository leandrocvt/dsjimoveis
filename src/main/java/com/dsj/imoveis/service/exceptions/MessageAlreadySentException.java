package com.dsj.imoveis.service.exceptions;

public class MessageAlreadySentException extends RuntimeException{

    public MessageAlreadySentException(String msg){
        super(msg);
    }
}
