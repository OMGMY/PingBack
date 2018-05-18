package com.pp.cloud.pingback;

import android.os.Message;

/**
 * Created by hanzhang on 2018/5/10.
 */

public class Pingback {


    /*package*/ static final int FLAG_IN_USE = 1 << 0;

    /** If set message is asynchronous */
    /*package*/ static final int FLAG_ASYNCHRONOUS = 1 << 1;

    /** Flags to clear in the copyFrom method */
    /*package*/ static final int FLAGS_TO_CLEAR_ON_COPY_FROM = FLAG_IN_USE;

    /*package*/ Pingback next;

    private static final Object sPoolSync = new Object();
    private static Pingback sPool;
    private static int sPoolSize = 0;

    private static final int MAX_POOL_SIZE = 20;

    private static boolean gCheckRecycle = true;

    /*package*/ int flags;


    /**
     * Return a new Message instance from the global pool. Allows us to
     * avoid allocating new objects in many cases.
     */
    public static Pingback obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                Pingback m = sPool;
                sPool = m.next;
                m.next = null;
                m.flags = 0; // clear in-use flag
                sPoolSize--;
                return m;
            }
        }
        return new Pingback();
    }

    /**
     * Return a Message instance to the global pool.
     * <p>
     * You MUST NOT touch the Message after calling this function because it has
     * effectively been freed.  It is an error to recycle a message that is currently
     * enqueued or that is in the process of being delivered to a Handler.
     * </p>
     */
    public void recycle() {
        if (isInUse()) {
            if (gCheckRecycle) {
                throw new IllegalStateException("This message cannot be recycled because it "
                        + "is still in use.");
            }
            return;
        }
        recycleUnchecked();
    }

    /**
     * Recycles a Message that may be in-use.
     * Used internally by the MessageQueue and Looper when disposing of queued Messages.
     */
    void recycleUnchecked() {
        // Mark the message as in use while it remains in the recycled object pool.
        // Clear out all other details.
        flags = FLAG_IN_USE;
       /* what = 0;
        arg1 = 0;
        arg2 = 0;
        obj = null;
        replyTo = null;
        sendingUid = -1;
        when = 0;
        target = null;
        callback = null;
        data = null;*/

        synchronized (sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

    /*package*/ boolean isInUse() {
        return ((flags & FLAG_IN_USE) == FLAG_IN_USE);
    }
}
