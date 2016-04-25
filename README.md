
# metasfresh-procurement-webui

This is a standalone mobile web application that a communicates with metasfresh and allows vendors to report supplies.

Those supply reports are transferred to metasfresh, are transformed into procurement candidates and then a metasfresh user can create purchase orders based on them.

The webui leans on the following components to do its job

* vaadin
* spring-boot
* cxf, using activeMQ for transport
