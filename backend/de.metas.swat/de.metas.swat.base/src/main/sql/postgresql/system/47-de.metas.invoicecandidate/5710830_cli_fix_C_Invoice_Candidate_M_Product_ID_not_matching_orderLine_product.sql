DROP TABLE IF EXISTS tmp_invoice_candidates_to_update
;

CREATE TEMPORARY TABLE tmp_invoice_candidates_to_update AS
SELECT (SELECT p.value || '_' || p.name FROM m_product p WHERE p.m_product_id = ic.m_product_id) AS ic_product,
       (SELECT p.value || '_' || p.name FROM m_product p WHERE p.m_product_id = ol.m_product_id) AS order_product,
       o.documentno                                                                              AS ic_order,
       o.c_order_id                                                                              AS ic_order_id,
       ic.c_orderline_id                                                                         AS ic_orderline_id,
       ic.c_invoice_candidate_id,
       ol.m_product_id                                                                           AS ol_product_id,
       ic.m_product_id                                                                           AS ic_product_id,
       ic.created                                                                                AS ic_created,
       ic.updated                                                                                AS ic_updated
FROM c_invoice_candidate ic
         LEFT OUTER JOIN c_orderline ol ON ol.c_orderline_id = ic.c_orderline_id
         LEFT OUTER JOIN c_order o ON o.c_order_id = ol.c_order_id
WHERE TRUE
  AND (ol.m_product_id IS NOT NULL AND ic.m_product_id != ol.m_product_id)
  AND ic.processed = 'N' -- don't change processed ones
  AND COALESCE(ic.qtyinvoiced, 0) = 0
;

-- delete from tmp_invoice_candidates_to_update where ic_order='A240976-teotest';
-- select * from tmp_invoice_candidates_to_update;

SELECT backup_table('c_invoice_candidate')
;

UPDATE c_invoice_candidate ic
SET m_product_id=t.ol_product_id
FROM tmp_invoice_candidates_to_update t
WHERE ic.c_invoice_candidate_id = t.c_invoice_candidate_id
;
