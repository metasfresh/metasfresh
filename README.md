#metasfresh-webui

This repo contains the totally prototypical and experimental beginnings of an awesome webui.

Please stay tuned...

![stay tuned...](https://upload.wikimedia.org/wikipedia/commons/0/0b/FuBK_wide.jpg)
(image source: https://commons.wikimedia.org/wiki/File%3AFuBK_wide.jpg)


#Some notes for developers:

* one can run it from eclipse
* by default, it listens on port 8080
* on http://localhost:8080/ you can find a login screen (which currently doesn't work, see exception stacktrace)
* the "main" code is under de.metas.ui.web.vaadin.window.prototype.order. A lot of the other stuff is obsolete.
* To open a concrete window (sales order, in this case), try http://localhost:8080/window/143
** Note: if you get a weird error "java.lang.RuntimeException: Property DocumentNo not found", then first change if you have any sales orders in the DB
** Also note: while in testing mode (see _Application.isTesting()_ ), you can add the following URL parameters:
*** AD_Language
*** AD_Client_ID
*** AD_Role_ID
