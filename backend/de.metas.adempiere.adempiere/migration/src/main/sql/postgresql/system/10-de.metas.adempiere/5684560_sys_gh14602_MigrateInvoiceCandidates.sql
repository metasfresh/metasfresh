-- Column: C_Invoice_Candidate.C_PaymentTerm_ID
-- 2023-04-07T10:20:34.845Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-04-07 13:20:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558578
;


-- backup
CREATE TABLE backup.c_invoice_candidate_payment_term AS
SELECT *
FROM c_invoice_candidate
;


-- update
UPDATE c_invoice_candidate
SET c_paymentterm_id=o.c_paymentterm_id
FROM c_orderLine ol
         JOIN c_order o ON ol.c_order_id = ol.c_order_id
WHERE c_invoice_candidate.ad_table_id = 260
  AND c_invoice_candidate.record_id = ol.c_orderLine_ID
  AND c_invoice_candidate.c_paymentterm_id IS NULL
;


-- make it mandatory


-- 2023-04-07T10:25:52.683Z
INSERT INTO t_alter_column values('c_invoice_candidate','C_PaymentTerm_ID','NUMERIC(10)',null,null)
;

-- 2023-04-07T10:25:52.693Z
INSERT INTO t_alter_column values('c_invoice_candidate','C_PaymentTerm_ID',null,'NOT NULL',null)
;


--- drop unneeded parameter
-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: SupplementMissingPaymentTermIds
-- 2023-04-07T12:19:10.617Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=541281
;

-- 2023-04-07T12:19:10.628Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=541281
;

