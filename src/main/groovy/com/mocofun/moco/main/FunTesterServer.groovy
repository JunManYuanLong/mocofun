package com.mocofun.moco.main

import com.funtester.base.bean.Result
import com.mocofun.moco.MocoResponse
import com.mocofun.moco.MocoServer

import static com.github.dreamhead.moco.Moco.and

/**
 * moco API演示类
 */
class FunTesterServer extends MocoServer{

    static void main(String[] args) {
        def server = getServer(getLogMonitor("${getMark()}.log"))
        server.get(urlOnly("/fun/tester")).response(obRes(Result.success("恭喜你进入测试服务了!")))
        server.get(and(urlContain("/funtester"),existArgs("fun"))).response(random("测试随机0","测试随机1","测试随机2","测试随机3"))
        server.get(urlStartsWith("/limit")).response(limit(textRes("dsfsd"), textRes("432432"), 1000))
        server.get(urlStartsWith("/random")).response(random(getJson("random=0"), getJson("random=1"), getJson("random=2"), getJson("random=3")))
        server.post(and(urlOnly("/post"), eqForm("a", "a"))).response(textRes("恭喜完成POST请求表单传参,单独参数验证"))
        server.get(urlOnly("/redirect")).redirectTo("https://mp.weixin.qq.com/s/s_FvGZTC42GEjaWzroz1eA")




        server.get(urlStartsWith("/test/qps")).response(qps(textRes("恭喜到达QPS!"),50))




        server.response(textRes("迷路的羔羊,我来收留你了!"))

        def nolog = getServerNoLog(12344)
        nolog.response("这是一个没有日志的服务!")
        def ser = getServer(54321, "1.log")
        ser.response("恭喜发现隐藏服务!")
        def run = run(server,ser)
        waitForKey("fan")
        run.stop()


    }


}
