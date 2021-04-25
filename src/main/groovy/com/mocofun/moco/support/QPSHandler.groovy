package com.mocofun.moco.support

import com.funtester.config.Constant
import com.funtester.frame.Output
import com.funtester.frame.SourceCode
import com.funtester.utils.Time
import com.github.dreamhead.moco.ResponseHandler
import com.github.dreamhead.moco.handler.AbstractResponseHandler
import com.github.dreamhead.moco.internal.SessionContext
import com.github.dreamhead.moco.util.Idles
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import io.netty.handler.codec.http.HttpConstants

import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

import static com.google.common.base.Preconditions.checkArgument

/**
 * 固定QPS的接口实现类
 */
@SuppressFBWarnings("LI_LAZY_INIT_STATIC")
class QPSHandler extends AbstractResponseHandler {

    private static volatile Semaphore semaphore = new Semaphore(1, true);
    /**
     * 访问间隔,使用微秒控制
     */
    private int gap

    private ResponseHandler handler

    /**
     * 用于记录收到第一个请求的时间
     */
    private long start

    /**
     * 用于统计已处理请求的总次数,因为用了流量控制,所以不适用安全类
     */
    private int times = 0

    /**
     * 用于统计实际的请求数和预期请求数直接的差距,由于在真实场景下预期的QPS总是大于实际QPS,所以只处理diff为正值的情况
     */
    private AtomicInteger diff = new AtomicInteger(0)

    /**
     * 重建handle
     * @param handler
     * @param gap 毫秒单位,内部转成微秒
     */
    private QPSHandler(ResponseHandler handler, int gap) {
        this.gap = gap * 1000
        this.handler = handler
    }

    public static ResponseHandler newSeq(final ResponseHandler handler, int gap) {
        com.google.common.base.Preconditions.checkArgument(handler != null, "responsehandler 不能为空!");
        def handler1 = new QPSHandler(handler, gap)
        return handler1;
    }


    /**
     * 具体实现,这里采用微秒,实验证明微秒更准确
     * @param context
     */
    @Override
    void writeToResponse(SessionContext context) {
        if (start == 0) {
            this.thread.start()
            start = SourceCode.getNanoMark()
        }
        semaphore.acquire()
        if (diff.getAndDecrement() <= 0) Idles.idle(gap, TimeUnit.MICROSECONDS)
        times++
        semaphore.release()
        handler.writeToResponse(context)
    }

    /**
     * 用于定时计算实际处理请求与预期处理请求数量差距,补偿缺失部分的多线程
     */
    private Thread thread = new Thread(new Runnable() {

        @Override
        void run() {
            while (true) {
                SourceCode.sleep(3.0)
                long present = SourceCode.getNanoMark()
                def t0 = (present - start) / 1000
                def t1 = times * gap
                if (t0 - t1 > gap) diff.getAndSet((int) ((t0 - t1) / gap))
            }
        }
    })

}
