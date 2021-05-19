package com.mocofun.moco.main

import com.funtester.utils.ArgsUtil
import com.github.dreamhead.moco.MocoMount
import com.mocofun.moco.MocoServer

class Blog extends MocoServer {

    public static void main(String[] args) {
        def util = new ArgsUtil(args)
        def monitor = getHitMonitor()
        def server = getServer(util.getIntOrdefault(0, 8080), "blog.log", monitor)
        server.get(urlOnly("/")).redirectTo("/blog/home.html")
        server.mount("/blog/", MocoMount.to("/blog"))
        server.response("FunTester到底啦!")
        def run = run(server)

        waitForKey("fan")

        run.stop()
    }
}
