create table backup.c_invoice_candidate_bkp_gh14569 as select * from c_invoice_candidate;

WITH ol AS
         (
             SELECT COALESCE(ol.c_paymentterm_override_id, o.c_paymentterm_id) AS c_paymentterm_id, o.c_order_id, ol.c_orderline_id
             FROM c_orderline ol
                      JOIN c_order o ON ol.c_order_id = o.c_order_id
         )
UPDATE c_invoice_candidate
SET c_paymentterm_id = ol.c_paymentterm_id
FROM ol
WHERE c_invoice_candidate.c_paymentterm_id IS NULL
  AND c_invoice_candidate.record_id = ol.c_orderline_id
  AND c_invoice_candidate.ad_table_id = 260
;
