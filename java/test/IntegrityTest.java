package test;


import cli.Shell;
import cli.TestInputStream;
import cli.TestOutputStream;
import client.IClientCli;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import proxy.IProxyCli;
import server.IFileServerCli;
import util.ComponentFactory;
import util.Config;
import util.Util;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 10/15/13
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class IntegrityTest extends AbstractIntegrityTest {

    @Before
    public void before() throws Exception {
        proxy = componentFactory.startProxy(new Config("proxy"), new Shell("proxy", new TestOutputStream(System.out), new TestInputStream()));
        Thread.sleep(Util.WAIT_FOR_COMPONENT_STARTUP);

        server = componentFactory.startFileServer(new Config("fs1"), new Shell("fs1", new TestOutputStream(System.out), new TestInputStream()));
        Thread.sleep(Util.WAIT_FOR_COMPONENT_STARTUP);

        client = componentFactory.startClient(new Config("client"), new Shell("client", new TestOutputStream(System.out), new TestInputStream()));
        Thread.sleep(Util.WAIT_FOR_COMPONENT_STARTUP);

        client2 = componentFactory.startClient(new Config("client2"), new Shell("client2", new TestOutputStream(System.out), new TestInputStream()));
        Thread.sleep(Util.WAIT_FOR_COMPONENT_STARTUP);

        server2 = componentFactory.startFileServer(new Config("fs2"), new Shell("fs2", new TestOutputStream(System.out), new TestInputStream()));
        Thread.sleep(Util.WAIT_FOR_COMPONENT_STARTUP);
    }
    @After
    public void after() throws Exception {
        try {
            proxy.exit();
        } catch (Exception e) {
            // This should not happen. In case it does, output the stack trace for easier trouble shooting.
            System.err.println("This should not happen");
            e.printStackTrace();
        }
        try {
            server.exit();
        } catch (IOException e) {
            // This should not happen. In case it does, output the stack trace for easier trouble shooting.
            System.err.println("This should not happen");
            e.printStackTrace();
        }
        try {
            client.exit();
        } catch (IOException e) {
            // This should not happen. In case it does, output the stack trace for easier trouble shooting.
            System.err.println("This should not happen");
            e.printStackTrace();
        }
        try {
            client2.exit();
        } catch (IOException e) {
            // This should not happen. In case it does, output the stack trace for easier trouble shooting.
            System.err.println("This should not happen");
            e.printStackTrace();
        }
    }

}
