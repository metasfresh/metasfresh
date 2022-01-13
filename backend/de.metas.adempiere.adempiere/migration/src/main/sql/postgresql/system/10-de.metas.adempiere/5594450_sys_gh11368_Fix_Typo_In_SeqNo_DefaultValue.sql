
-- 2021-06-24T07:40:42.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo), 0) + 10 AS DefaultValue FROM WEBUI_DashboardItem WHERE WEBUI_Dashboard_ID=@WEBUI_Dashboard_ID/-1@
',Updated=TO_TIMESTAMP('2021-06-24 10:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555448
;
