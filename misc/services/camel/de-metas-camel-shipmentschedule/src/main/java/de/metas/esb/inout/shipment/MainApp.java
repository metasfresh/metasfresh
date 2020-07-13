package de.metas.esb.inout.shipment;

import org.apache.camel.main.Main;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {

        final Main main = new Main();
        main.configure().addRoutesBuilder(new JsonToXmlRouteBuilder());
        main.run(args);
    }

}

