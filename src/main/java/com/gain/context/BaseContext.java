package com.gain.context;

public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置当前用户id
     * @param id
     */
    public static void setCurrentId(Long id) {
        if (id != null) {threadLocal.set(id);}
    }
    /**
     * 获取当前用户 ID
     * @return 当前登录用户 ID，若未获取到则返回 null
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }
    /**
     * 清理当前线程存储的信息
     * 【非常重要】防止线程池环境下的内存泄漏和数据污染
     */
    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
