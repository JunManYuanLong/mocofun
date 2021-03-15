package com.mocofun.moco.main

import com.funtester.base.bean.Result
import com.mocofun.moco.MocoResponse
import com.mocofun.moco.MocoServer

import static com.github.dreamhead.moco.Moco.and
import static com.github.dreamhead.moco.Moco.from

/**
 * moco API的应用
 */
class HttpServer extends MocoServer {

    static void main(String[] args) {
        def server = getServer(getLogMonitor("1.log"))
        server.get(urlOnly("/test")).response(obRes(Result.success(getJson("324324=32432"))))
        server.get(urlOnly("/ss")).response(limit(textRes("dsfsd"), textRes("432432"), 1000))

        server.get(urlOnly("/find")).response(random("43242=342", "e343=fdsf", "3242"))
        server.get(urlOnly("/qq")).response(cycle(getJson("3234=32423"), getJson("ew=32")))
        server.post(and(urlOnly("/post"), eqForm("a", "a"))).response(textRes("234"))
        server.response(MocoResponse.textRes("hello word"))
        def run = run(server)

        server.get(urlOnly("/test00")).redirectTo("https://mp.weixin.qq.com/s?__biz=MzU4MTE2NDEyMQ==&mid=2247483864&idx=3&sn=8e20005abfe783422e81ee9a9aa606bd&chksm=fd4a8ceeca3d05f894c90f14a418010ac8d36f1ec954c1363a2a9c4233ac92a7e67308bb83b3&token=438960288&lang=zh_CN#rd")
        waitForKey("fan")
        run.stop()
    }

    static proxyNetdata(HttpServer server) {
        server.proxy(from("/").to("http://10.10.6.3:19999"))
    }
}
