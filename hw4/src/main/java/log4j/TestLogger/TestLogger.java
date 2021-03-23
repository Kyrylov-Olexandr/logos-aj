package log4j.TestLogger;

import org.apache.log4j.Logger;

public class TestLogger {
    private static final Logger LOG = Logger.getLogger(TestLogger.class);

    public static void main(String[] args) {
        LOG.error("error");
        LOG.info("info");
        LOG.debug("debug");
        LOG.warn("warn");
    }
}
