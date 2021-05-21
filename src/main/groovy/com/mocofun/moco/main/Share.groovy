package com.mocofun.moco.main

import com.mocofun.moco.MocoServer

class Share extends MocoServer{

    static void main(String[] args) {
        def server = getServer(12345)
        server.get(urlStartsWith("/test/qps")).response(qps(textRes("恭喜到达QPS!"),50))
        server.response("到底了,没啥响应了")
        def run = run(server)
        waitForKey("fan")
        run.stop()
    }
}
