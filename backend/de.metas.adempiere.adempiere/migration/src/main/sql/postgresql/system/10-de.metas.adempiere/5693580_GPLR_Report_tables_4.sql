DROP INDEX IF EXISTS GPLR_Report_SalesInvoice
;

CREATE UNIQUE INDEX GPLR_Report_SalesInvoice ON GPLR_Report (Sales_Invoice_ID)
;

DROP INDEX IF EXISTS GPLR_Report_PurchaseInvoice
;

CREATE INDEX GPLR_Report_PurchaseInvoice ON GPLR_Report (Purchase_Invoice_ID)
;

