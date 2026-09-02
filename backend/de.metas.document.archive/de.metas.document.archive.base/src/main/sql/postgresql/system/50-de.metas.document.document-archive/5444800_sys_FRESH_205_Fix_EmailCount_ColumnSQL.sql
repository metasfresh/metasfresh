-- 19.04.2016 11:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select COUNT(*) from C_Doc_Outbound_Log_Line where  C_Doc_Outbound_Log_Line.Action = ''eMail''   AND C_Doc_Outbound_Log_Line.C_Doc_Outbound_Log_ID =    C_Doc_Outbound_Log.C_Doc_Outbound_Log_ID AND    C_Doc_Outbound_Log_Line.Status in (''MessageSent'',''Mitteilung versendet.''))',Updated=TO_TIMESTAMP('2016-04-19 11:27:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548163
;