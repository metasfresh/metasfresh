UPDATE c_doc_outbound_log
SET POReference = i.poreference, Updated=TO_TIMESTAMP('2024-09-05 13:32:35.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
FROM c_doc_outbound_log l
         INNER JOIN c_invoice i ON l.record_id = i.c_invoice_id AND l.ad_table_id = get_Table_ID('C_Invoice')
WHERE l.c_doc_outbound_log_id = c_doc_outbound_log.c_doc_outbound_log_id
  AND i.poreference IS NOT NULL
;

UPDATE c_doc_outbound_log
SET POReference = ship.poreference, Updated=TO_TIMESTAMP('2024-09-05 13:32:35.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
FROM c_doc_outbound_log l
         INNER JOIN m_inout ship ON l.record_id = ship.m_inout_id AND l.ad_table_id = get_Table_ID('M_InOut')
WHERE l.c_doc_outbound_log_id = c_doc_outbound_log.c_doc_outbound_log_id
  AND ship.poreference IS NOT NULL
;
