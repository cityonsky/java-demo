package com.bigcrab.java.demo.lang.pattern;

import com.bigcrab.java.demo.lang.function.CoRHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sky
 */
public class ChainOfResponsibility {

    private static class Event {

        private Map<String, String> map;

        private String id;

        public Map<String, String> getMap() {
            return map;
        }

        public void setMap(Map<String, String> map) {
            this.map = map;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    private static boolean find(Event event, String field) {
        if (event.getMap().containsKey(field)) {
            event.setId(event.getMap().get(field));
            return true;
        } else {
            return false;
        }
    }

    private static CoRHandler<Event> handler = (event) -> false;

    static {
        handler = handler.or((event) -> find(event, "k1"));
        handler = handler.or((event) -> find(event, "k2"));
        handler = handler.or((event) -> find(event, "k3"));
        handler = handler.or((event) -> {
            event.setId("default_value"); return true;
        });
    }

    private static String run() {
        Map<String, String> req = new HashMap<>();
        req.put("k2", "v2");
        req.put("k4", "v4");
        Event event = new Event();
        event.setMap(req);
        handler.handle(event);
        return event.getId();
    }

    public static void main(String[] args) {
        System.out.println(run());
    }
}
