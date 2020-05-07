package com.flowable.springboot.util;

public class CmsException extends RuntimeException {
    public CmsException(String msg)
    {
        super(msg);
    }

    public CmsException(Throwable t) {
        super(t);
    }

    public CmsException(String msg, Throwable t) {
        super(msg, t);
    }
}
