package com.sj.modules.sys;

import org.junit.Test;

import java.util.*;

/**
 * Created by yangrd on 2018/2/6
 **/
public class Module {

    private static void println(Object o){
        System.out.println(o);
    }

    static class A{
        public A() {
            println(A.class);
        }
    }

    static class B extends A{
        public B() {
            println(B.class);
        }
    }

    static class C extends B{
        public C(){
            println(C.class);
        }
    }

    @Test
    public void t(){
        int a = 5; int b = 10;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        println(a);
        println(b);
    }

    public static void main(String[] args) {
        List<? extends B> aList = new HolderList<>();
//        aList.add(new Object());
        B c = aList.get(0);
        List<? super B> sList= new HolderList<>();
        sList.add(new C());
        sList.add(new B());
        List<B> bList = new HolderList<>();
        bList.add(new C());
        A c1 = bList.get(0);
    }

    static class HolderList<T> extends ArrayList<T> implements List<T>  {

//        @Override
        public <S extends T> S  get0(int index) {
            return (S)super.get(index);
        }
    }

}
