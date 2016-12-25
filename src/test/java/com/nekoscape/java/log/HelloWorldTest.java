package com.nekoscape.java.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class HelloWorldTest {

    @Mock
    private Appender mockAppender;

    @Captor
    private ArgumentCaptor<LogEvent> logCaptor;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        Mockito.reset(mockAppender);
        Mockito.when(mockAppender.getName()).thenReturn("MockAppender");
        Mockito.when(mockAppender.isStarted()).thenReturn(true);
        Mockito.when(mockAppender.isStopped()).thenReturn(false);

        setLogLevel(Level.INFO);
    }

    @Test
    public void outputLogMessage() {
        String name = "World";

        HelloWorld helloWorld = new HelloWorld();
        helloWorld.sayHello(name);

        Mockito.verify(mockAppender, Mockito.times(1)).append(logCaptor.capture());

        String message = logCaptor.getValue().getMessage().getFormattedMessage();
        Level level = logCaptor.getValue().getLevel();
        assertThat(message, is("Hello " + name));
        assertThat(level, is(Level.INFO));
    }

    private void setLogLevel(Level level) {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();

        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.removeAppender("MockAppender");

        loggerConfig.setLevel(level);
        loggerConfig.addAppender(mockAppender, level, null);
        ctx.updateLoggers();
    }
}
