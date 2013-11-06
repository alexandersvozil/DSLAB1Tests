package test;

import cli.Shell;
import cli.TestInputStream;
import cli.TestOutputStream;
import client.IClientCli;
import message.response.FileServerInfoResponse;
import message.response.UserInfoResponse;
import model.FileServerInfo;
import model.UserInfo;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import proxy.IProxyCli;
import server.IFileServerCli;
import util.ComponentFactory;
import util.Config;
import util.Util;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 10/15/13
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractIntegrityTest {
    static ComponentFactory componentFactory = new ComponentFactory();
    IProxyCli proxy;
    IFileServerCli server;
    IFileServerCli server2;
    IClientCli client;
    IClientCli client2;

    @Test
    public void test_clientLoggedOutTest() throws Exception {
        String actual = client.login("alice", "12345").toString();
        String expected = "success";
        assertTrue(String.format("Response must contain '%s' but was '%s'", expected, actual), actual.contains(expected));
        client.exit();
        UserInfoResponse userInfoResponse = (UserInfoResponse) proxy.users();
        List<UserInfo> uinfoList = userInfoResponse.getUserInfo();
        for(UserInfo info: uinfoList){
            if(info.getName().equals("alice")){
                assertThat(info.isOnline(),is(false));
            }
        }
    }

    @Test
    public void tes_prescribed() throws Exception {
        String actual = client.login("alice", "12345").toString();
        String expected = "success";
        assertTrue(String.format("Response must contain '%s' but was '%s'", expected, actual), actual.contains(expected));

        actual = client.credits().toString();
        expected = "200";
        assertTrue(String.format("Response must contain '%s' but was '%s'", expected, actual), actual.contains(expected));

        // client.list().toString();
        actual = client.download("short.txt").toString();
        expected = "!data dslab13";
        assertTrue(String.format("Response must start with '%s' but was '%s'", expected, actual), actual.startsWith(expected));

        actual = client.credits().toString();
        expected = "192";
        assertTrue(String.format("Response must contain '%s' but was '%s'", expected, actual), actual.contains(expected));

        actual = client.upload("upload.txt").toString();
        expected = "success";
        assertTrue(String.format("Response must contain '%s' but was '%s'", expected, actual), actual.contains(expected));

        actual = client.credits().toString();
        expected = "292";
        assertTrue(String.format("Response must contain '%s' but was '%s'", expected, actual), actual.contains(expected));

        actual = client.logout().toString();
        expected = "Successfully logged out.";
        assertTrue(String.format("Response must contain '%s' but was '%s'", expected, actual), actual.contains(expected));
    }

    @Test
    public void test_usageSwitch() throws Exception{
        String actual = client.login("alice", "12345").toString();
        String expected = "success";
        assertTrue(String.format("Response must contain '%s' but was '%s'", expected, actual), actual.contains(expected));

        // client.list().toString();
        actual = client.download("short.txt").toString();
        expected = "!data dslab13";
        assertTrue(String.format("Response must start with '%s' but was '%s'", expected, actual), actual.startsWith(expected));

        // client.list().toString();
        actual = client.download("short.txt").toString();
        expected = "!data dslab13";
        assertTrue(String.format("Response must start with '%s' but was '%s'", expected, actual), actual.startsWith(expected));

        System.out.println(proxy.fileservers());
        for(FileServerInfo e: ((FileServerInfoResponse)proxy.fileservers()).getFileServerInfo()){
            assertThat(e.getUsage(), is(equalTo(new Long(8))));

        }
    }



}
