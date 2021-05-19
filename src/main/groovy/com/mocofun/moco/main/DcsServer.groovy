package com.mocofun.moco.main

import com.alibaba.fastjson.JSONObject
import com.funtester.base.bean.Result
import com.funtester.httpclient.FunLibrary
import com.funtester.httpclient.FunRequest
import com.mocofun.moco.MocoServer
import com.sun.deploy.ui.FancyButton
import org.apache.http.client.methods.HttpGet

class DcsServer extends MocoServer {

    public static void main(String[] args) {
        def server = getServer(12345)
        def res = new JSONObject()
        res.times = 1000
        res.thread = 20
        res.mode = "ftt"
        res.desc = "FunTester分布式测试Demo"
        res.runup = 10
        String url = "http://192.168.80.169:12345/m"
        def get = FunLibrary.getHttpGet(url)
        def request = FunRequest.initFromRequest(get)
        res.request = request
        output(res)
        def success = Result.success(res)
        output(success.toJson())
        server.get(urlStartsWith("/m")).response(obRes(success))

        def run = run(server)

        waitForKey("fun")

        run.stop()

    }
}
