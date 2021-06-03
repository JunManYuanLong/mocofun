package com.mocofun.moco.main

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.funtester.base.bean.Result
import com.funtester.httpclient.FunLibrary
import com.funtester.httpclient.FunRequest
import com.mocofun.moco.MocoServer

class DcsServer2 extends MocoServer {

    public static void main(String[] args) {
        def server = getServer(12345)
        def res = new JSONObject()
        res.path = "com.funtester.main.DcsCase.main"
        res.paramsType = ["java.lang.Integer", 10, "java.lang.Integer", 100, "java.lang.Integer", 2]
        def success = Result.success(res)
        output(success.toJson())
        def res2 = new JSONObject()
        res2.path = "com.funtester.main.DcsCase.main"
        res2.paramsType = ["java.lang.Integer", 10, "java.lang.Integer", 100, "java.lang.Integer", 2, "java.lang.String", "http://192.168.80.169:12345/m"]
        def success2 = Result.success(res2)
        def res3 = new JSONObject()
        res3.path = "com.funtester.main.DcsCase.main"
        res.paramsType = ["java.lang.String", "10,100,1"]
        def success3 = Result.success(res3)
        server.get(urlStartsWith("/m")).response(obRes(success))
        server.get(urlStartsWith("/t")).response(obRes(success2))
        server.get(urlStartsWith("/f")).response(obRes(success3))
        def run = run(server)
        waitForKey("fun")
        run.stop()
    }
}
