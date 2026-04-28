DROP INDEX IF EXISTS C_Doc_Outbound_Log_POReference_Index
;

-- 2024-09-09T07:53:31.950Z
CREATE INDEX C_Doc_Outbound_Log_POReference_Index ON C_Doc_Outbound_Log (
                                                                         unaccent_string(POReference, 1)
    )
;

-- Column: C_Doc_Outbound_Log.POReference
-- Column: C_Doc_Outbound_Log.POReference
-- 2024-08-30T16:42:55.306Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-08-30 16:42:55.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551964
;
