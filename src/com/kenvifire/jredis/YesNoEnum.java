package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/5/15.
 */
public enum YesNoEnum {
    YES("yes",1),
    NO("no",0);


    private String value;
    private int code;

    private YesNoEnum(String value,int code){
       this.value = value;
    }

    public static YesNoEnum parse(String value){
        for(YesNoEnum enums : values()){
            if(enums.value.equals(value)){
                return enums;
            }
        }
        return null;
    }

    public int getCode(){
        return this.code;
    }


}
