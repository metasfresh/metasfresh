-- Run mode: SWING_CLIENT

-- Column: C_Doc_Outbound_Log.POReference
-- 2025-02-05T08:15:28.738Z
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=40,Updated=TO_TIMESTAMP('2025-02-05 08:15:28.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551964
;

SELECT backup_table('C_Doc_Outbound_Log')
;

UPDATE c_doc_outbound_log
SET poreference =SUBSTRING(poreference FOR 40)
WHERE LENGTH(poreference) > 40
;

-- 2025-02-05T08:15:50.364Z
INSERT INTO t_alter_column values('c_doc_outbound_log','POReference','VARCHAR(40)',null,null)
;

