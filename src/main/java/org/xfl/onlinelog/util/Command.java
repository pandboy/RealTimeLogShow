package org.xfl.onlinelog.util;

/**
 * Created by bl04559 on 2016/8/19.
 */
public enum Command {
    LOG(0), //测试日志打印
    TEST_DEPLOY(1), //测试部署
    MVN_PACKAGE(2), //打包
    PROD_DEPLOY(3), //生产部署
    PROD_DEPLOY_START(4), //生产部署开始
    PROD_DEPLOYING(6), //部署中
    PROD_ROLLBACK(5), //生产回滚部署
    START_FILE_SERVER(7), //启动文件服务器
    START_FILE_SERVER_FINISH(9), //启动文件服务器完毕
    FILE_SAVED(11), //文件保存完毕
    EXCEPT(13);// (13); //异常终止
    private int value;

    private Command(int value) {
        this.value = value;
    }

    public int getVal() {
        return this.value;
    }

    public static Command getByValue(int value) {
        Command[] types = values();
        for (Command typeEnum : types) {
            if (value == typeEnum.getVal()) {
                return typeEnum;
            }
        }
        throw new NullPointerException("not find");
    }
}
