package com.unicenta.pos.util;

import com.unicenta.basic.BasicException;
import com.unicenta.data.gui.MessageInf;
import com.unicenta.format.Formats;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ValidateBuilder {

    public static int IS_NOT_EMPTY = 1;
    public static int IS_INT = 2;
    public static int IS_DOUBLE = 3;
    public static int IS_CURRENCY = 4;
    public static int IS_DOUBLE_NULL = 5 ;

    private List<String> listMessage = new ArrayList<>();
    private Component parent;

    public ValidateBuilder(Component parent) {
        this.parent = parent;
    }

    public void setValidate(Object value , int tipe ,  String message){
        if (value==null){
                listMessage.add(message);
        }
    }

    public void setValidate(String value, int type, String message) {
        if (type == IS_NOT_EMPTY) {
            if (value.isEmpty()) {
                listMessage.add(message);
            }
        } else if (type == IS_INT) {
            if (!isInt(value)) {
                listMessage.add(message);
            }
        } else if (type == IS_DOUBLE) {
            if (!isDouble(value)) {
                listMessage.add(message);
            }
        } else if (type == IS_CURRENCY) {
            if (!isCurrency(value)) {
                listMessage.add(message);
            }
        } else if (type == IS_DOUBLE_NULL) {
            if (!isDoubleNull(value)) {
                listMessage.add(message);
            }
        }
    }

    private void showMessage(){
        StringBuilder sb = new StringBuilder();
        sb.append("<br>");
        for (String message : listMessage){
            sb.append(message+"<br>");
        }
            MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE,sb.toString() , null);
            msg.show(parent);

    }

    public boolean  getValid(){
        boolean isValid = true;
        if (!listMessage.isEmpty()) {
            isValid = false;
            showMessage();
        }
        return isValid;
    }

    private  boolean isInt(String n) {
            try {
            Optional<Integer> a = Optional.ofNullable(Formats.INT.parseValue(n)).map(Integer.class::cast);
            return a.isPresent();
        } catch (BasicException ex) {
            return false;
        }
    }


    private  boolean isDouble(String n) {
            try {
            Optional<Double> a = Optional.ofNullable(Formats.DOUBLE.parseValue(n)).map(Double.class::cast);
            return a.isPresent();
        } catch (BasicException ex) {
            return false;
        }
    }



      private  boolean isDoubleNull(String n) {
            try {
            Formats.DOUBLE.parseValue(n);
             return true;
        } catch (BasicException ex) {
            return false;
        }
    }


    private boolean isCurrency(String n){
        try {
            Optional<Double> a = Optional.ofNullable(Formats.CURRENCY.parseValue(n)).map(Double.class::cast);
            return a.isPresent();
        } catch (BasicException ex) {
            return false;
        }
    }
}
