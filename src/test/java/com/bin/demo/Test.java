package com.bin.demo;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        final ThreadLocal tl = new MyThreadLocal();
        Integer num = Integer.valueOf(1);
        String str = new String("aaa");
        tl.set(new My50MB());

        System.out.println("Full GC");
        System.gc();
        new Thread(){
            @Override
            public void run() {
                System.out.println("inner-class:"+tl.get());
            }
        }.start();
        System.out.println(tl.get());
    }

    public static class MyThreadLocal extends ThreadLocal {
        private byte[] a = new byte[1024*1024*1];

        public MyThreadLocal(){
            System.out.println("MyThreadLocal constructor");
        }
        @Override
        public void finalize() {
            System.out.println("My threadlocal 1 MB finalized.");
        }
    }

    public static class My50MB {
        private byte[] a = new byte[1024*1024*50];

        public My50MB(){
            System.out.println("My50MB constructor");
        }

        @Override
        public void finalize() {
            System.out.println("My 50 MB finalized.");
        }

        @Override
        public String toString() {
            return "this is My50MB class";
        }
    }

}