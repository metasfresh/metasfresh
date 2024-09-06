-- Column: C_Doc_Outbound_Log.POReference
-- Column SQL (old): (SELECT getDocumentPOReference(C_Doc_Outbound_Log.AD_Table_ID, C_Doc_Outbound_Log.Record_ID))
-- Column: C_Doc_Outbound_Log.POReference
-- Column SQL (old): (SELECT getDocumentPOReference(C_Doc_Outbound_Log.AD_Table_ID, C_Doc_Outbound_Log.Record_ID))
-- 2024-09-05T13:32:35.958Z
UPDATE AD_Column SET ColumnSQL='', IsLazyLoading='N',Updated=TO_TIMESTAMP('2024-09-05 13:32:35.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551964
;

-- 2024-09-05T13:32:42.395Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Log','ALTER TABLE public.C_Doc_Outbound_Log ADD COLUMN POReference VARCHAR(40)')
;

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
