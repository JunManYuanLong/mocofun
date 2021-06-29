package com.mocofun.moco.main


import com.alibaba.fastjson.JSONObject
import com.funtester.base.bean.Result
import com.funtester.utils.RWUtil
import com.mocofun.moco.MocoServer

class DcsServer3 extends MocoServer {

    public static void main(String[] args) {
        def server = getServer(12345)
        def res = new JSONObject()
        res.script = RWUtil.readTxtByString("/Users/oker/IdeaProjects/funtester/src/test/groovy/com/funtest/groovytest/Share.groovy")
        output(res.script)
        server.get(urlStartsWith("/m")).response(obRes(Result.success(res)))
        def run = run(server)
        waitForKey("fun")
        run.stop()
    }
}
