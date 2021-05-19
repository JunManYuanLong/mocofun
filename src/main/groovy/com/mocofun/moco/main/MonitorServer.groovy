package com.mocofun.moco.main

import com.funtester.base.bean.Result
import com.github.dreamhead.moco.Moco
import com.github.dreamhead.moco.MocoRequestHit
import com.github.dreamhead.moco.RequestHit
import com.mocofun.moco.MocoServer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import static com.github.dreamhead.moco.MocoRequestHit.*;

class MonitorServer extends MocoServer {

    private static final Logger logger = LogManager.getLogger(MonitorServer.class)

    public static void main(String[] args) {

        def monitor = getHitMonitor()
        def server = getServer(12345, "${getMark()}.log", monitor)
        server.get(urlStartsWith("/m")).response(obRes(Result.success("FunTester监控")))
        server.get(urlStartsWith("/t")).response(obRes(Result.success("FunTester测试")))
        server.get(urlStartsWith("/a")).response(obRes(Result.success(getJson("w4323=3423"))))
        server.get(urlStartsWith("/b")).response(obRes(Result.success("fda;jfkd s")))

        def fun = run(server)
        waitForKey("fun")
        try {
            monitor.verify(urlStartsWith("/m"), never())
            monitor.verify(urlMatcher("/t"), once())
            monitor.verify(urlOnly("/a"), times(1))
            monitor.verify(urlEndWith("/b"), atLeast(2))
            monitor.verify(urlContain("/"), atMost(10))
            monitor.verify(urlEndWith("/"), between(12, 22))
            monitor.verify(unexpected(), never())
        } catch (e) {
            logger.warn(e)
        }
        fun.stop()
    }
}
