package com.example.javademo.m5_jvm.gc;

/**
 * 2022/2/13
 */
public class FinalizeEscapeGc {

    public static FinalizeEscapeGc SAVE_HOOK = null;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed");
        FinalizeEscapeGc.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGc();

        // 第一次自救
        escape();

        // 再来一次
        escape();
    }

    private static void escape() throws InterruptedException {
        SAVE_HOOK = null;
        System.gc();

        // finalize优先级低 这里先暂停一下
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            System.out.println("yes i am alive");
        } else {
            System.out.println("no i am dead");
        }
    }
}
