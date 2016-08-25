package org.xfl.onlinelog.factory;

import org.xfl.onlinelog.util.Command;

/**
 * Created by bl04559 on 2016/8/17.
 */
public class ProcessFactory {
    private static ProcessFactory instance = new ProcessFactory();
    public static ProcessFactory getInstance() {
        return instance;
    }
    public IProcess createProcess(Command command) {
        IProcess process = null;
        switch (command) {
            case LOG:
                process =  new TailProcess();
                break;
            case TEST_DEPLOY:
                process = new DeployTestProcess();
                break;
            case MVN_PACKAGE:
                //process = new DeployTestProcess();
                break;
            case PROD_DEPLOY:
                process = new DeployProdProcess();
                break;
            case PROD_ROLLBACK:
                process = new DeployRollbackProcess();
                break;
            default:
                break;
        }
        return process;
    }
}
