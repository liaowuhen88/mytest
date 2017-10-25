package bio_nio;

import bio_nio.bio.service.ServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public final class Calculator {
    private final static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
    private static Logger log = LoggerFactory.getLogger(ServerHandler.class);

    public static Object cal(String expression) throws ScriptException {
        return jse.eval(expression);
    }
}
