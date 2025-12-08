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
