package test;

import client.IClientCli;
import org.junit.Test;
import proxy.IProxyCli;
import server.IFileServerCli;
import util.ComponentFactory;

import static org.junit.Assert.assertTrue;


/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 10/17/13
 * Time: 7:42 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCrashTest {
    static ComponentFactory componentFactory = new ComponentFactory();
    IProxyCli proxy;
    IFileServerCli server;
    IFileServerCli server2;
    IClientCli client;
    IClientCli client2;

    @Test
    public void proxyCrash_login() throws Exception{
        String answer = client.login("alice", "12345").toString();
        proxy.exit();
        client.login("alice", "12345");

        server.exit();
        server2.exit();
        client.exit();
        client2.exit();

    }

    @Test
    public void proxyCrash_credits() throws Exception{
        String answer = client.login("alice", "12345").toString();
        String creditsAnswer = client.credits().toString();
        proxy.exit();
        client.credits();
        server.exit();
        server2.exit();
        client.exit();
        client2.exit();
    }

    @Test
    public void proxyCrash_list() throws Exception{
        String answer = client.login("alice", "12345").toString();
        String listAnswer2 = client.list().toString();
        proxy.exit();
        client.list();

        server.exit();
        server2.exit();
        client.exit();
        client2.exit();
    }

    @Test
    public void proxyCrash_buy() throws Exception{
        String answer = client.login("alice", "12345").toString();
        String listAnswer2 = client.buy(12312312).toString();
        proxy.exit();
        client.buy(123);

        server.exit();
        server2.exit();
        client.exit();
        client2.exit();
    }

    @Test
    public void proxyCrash_download() throws  Exception{
        String answer = client.login("alice", "12345").toString();
        String listAnswer2 = client.download("small.txt").toString();
        proxy.exit();
        client.download("long.txt");

        server.exit();
        server2.exit();
        client.exit();
        client2.exit();
    }

    @Test
    public void proxyCrash_logout() throws Exception{
        String answer = client.login("alice", "12345").toString();
        String listAnswer2 = client.logout().toString();
        proxy.exit();
        client.logout();

        server.exit();
        server2.exit();
        client.exit();
        client2.exit();
    }

    @Test
    public void fsCrash_List() throws Exception{
        String answer = client.login("alice", "12345").toString();
        //exit servers
        server.exit();
        server2.exit();
        //make request
        client.list();
        client.exit();
        client2.exit();
        proxy.exit();
    }
    @Test
    public void fsCrash_Download() throws  Exception{
        String answer = client.login("alice", "12345").toString();
        //exit servers
        server.exit();
        server2.exit();
        //make request
        client.download("lol.txt");
        client.exit();
        client2.exit();
        proxy.exit();


    }
    @Test
    public void fsCrash_Upload() throws Exception{
        String answer = client.login("alice", "12345").toString();
        //exit servers
        server.exit();
        server2.exit();
        //make request
        client.upload("upload.txt");
        client.exit();
        client2.exit();
        proxy.exit();

    }

    @Test
    public void crashTest_easyChiller() throws Exception {

        String actual = client.login("alice", "12345").toString();
        String expected = "success";
        assertTrue(String.format("Response must contain '%s' but was '%s'", expected, actual), actual.contains(expected));

        String actual2 = client2.login("bill", "23456").toString();
        String expected2 = "success";
        assertTrue(String.format("Response must contain '%s' but was '%s'", expected2, actual2), actual.contains(expected));
        server2.exit();
        /*actual = client.upload("upload.txt").toString();
        expected = "success";
        assertTrue(String.format("Response must contain '%s' but was '%s'", expected, actual), actual.contains(expected));
        System.out.println(proxy.fileservers());*/
        server.exit();
        actual = client.download("upload.txt").toString();
        System.out.println(proxy.fileservers());
        expected = "Could not connect to a fileserver";
        assertTrue(String.format("Response must start with '%s' but was '%s'", expected, actual), actual.startsWith(expected));
    }

}
