package com.mayezi.util;

/**
 * Created by Mayezi
 * Date: 2017-04-18
 * Time: 14:51
 * All rights reserved.
 */
public class PageItem {
    private int number;
    private boolean current;
    public PageItem(int number, boolean current){
        this.number = number;
        this.current = current;
    }

    public int getNumber(){
        return this.number;
    }

    public boolean isCurrent(){
        return this.current;
    }
}
