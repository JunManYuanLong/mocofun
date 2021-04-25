package com.mocofun.moco.main

import com.mocofun.moco.MocoServer

class TestDemo extends MocoServer{

    static void main(String[] args) {
        def log = getServerNoLog(12345)

        log.response("我是测试服务的,测试接口,23上出炯炯分散反;J房贷就发积分赛")
        def run = run(log)
        waitForKey("fan")
        run.stop()


    }
}
