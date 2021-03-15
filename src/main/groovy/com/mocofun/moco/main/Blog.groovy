package com.mocofun.moco.main

import com.funtester.utils.ArgsUtil
import com.github.dreamhead.moco.MocoMount
import com.mocofun.moco.MocoServer

class Blog extends MocoServer {
    public static void main(String[] args) {
        def util = new ArgsUtil(args)
        def server = getServer(util.getIntOrdefault(0,8080), "blog", getHitMonitor())
        server.get(urlOnly("/")).redirectTo("/blog/home.html")
        server.mount("/blog/", MocoMount.to("/blog"))
        def server1 = run(server)
        waitForKey("fan")
        server1.stop()
    }
}
