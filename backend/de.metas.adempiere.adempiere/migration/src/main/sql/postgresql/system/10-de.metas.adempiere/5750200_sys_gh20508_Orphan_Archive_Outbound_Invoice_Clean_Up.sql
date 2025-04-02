CREATE TABLE backup.c_doc_outbound_log_line_04022025
AS
SELECT *
FROM c_doc_outbound_log_line
;

DELETE
FROM c_doc_outbound_log_line
WHERE ad_table_id = 318
  AND NOT EXISTS(
        SELECT 1
        FROM C_Invoice
        WHERE C_Invoice.c_invoice_id = c_doc_outbound_log_line.record_id
    )
;

CREATE TABLE backup.AD_Archive_04022025
AS
SELECT *
FROM AD_Archive
;

DELETE
FROM AD_Archive
WHERE ad_table_id = 318
  AND NOT EXISTS(
        SELECT 1
        FROM C_Invoice
        WHERE C_Invoice.c_invoice_id = AD_Archive.record_id
    )
;

CREATE TABLE backup.c_doc_outbound_log_04022025
AS
SELECT *
FROM c_doc_outbound_log
;

DELETE
FROM c_doc_outbound_log
WHERE ad_table_id = 318
  AND NOT EXISTS(
        SELECT 1
        FROM C_Invoice
        WHERE C_Invoice.c_invoice_id = c_doc_outbound_log.record_id
    )
;
