-- Run mode: SWING_CLIENT

-- Column: C_Doc_Outbound_Log.EMailCount
-- Column SQL (old): (select COUNT(*) from C_Doc_Outbound_Log_Line where  C_Doc_Outbound_Log_Line.Action = 'eMail'   AND C_Doc_Outbound_Log_Line.C_Doc_Outbound_Log_ID =    C_Doc_Outbound_Log.C_Doc_Outbound_Log_ID AND    C_Doc_Outbound_Log_Line.Status in ('Message sent.','Mitteilung versendet.'))
-- 2025-10-30T17:31:53.951Z
UPDATE AD_Column SET ColumnSQL='(select COUNT(*) from C_Doc_Outbound_Log_Line where  C_Doc_Outbound_Log_Line.Action = ''eMail''   AND C_Doc_Outbound_Log_Line.C_Doc_Outbound_Log_ID =    C_Doc_Outbound_Log.C_Doc_Outbound_Log_ID AND    C_Doc_Outbound_Log_Line.Status = ''Email_Success'')',Updated=TO_TIMESTAMP('2025-10-30 17:31:53.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=548163
;
