-- unrelated fix
-- 2018-09-25T11:42:31.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET EntityType='de.metas.dunning',Updated=TO_TIMESTAMP('2018-09-25 11:42:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=159
;

-- set the C_DunningDoc (Mahnbrief Jasper) so the print action is available from te dunning do window
-- 2018-09-25T11:42:48.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Process_ID=1000000,Updated=TO_TIMESTAMP('2018-09-25 11:42:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540430
;

-- change the value of "Mahnbrief (Jasper)" so that it's near the other dunning doc process
-- 2018-09-25T11:44:30.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='C_DunningDoc',Updated=TO_TIMESTAMP('2018-09-25 11:44:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=1000000
;

-- remove "Mahnbrief (Jasper)" from beeing a quickaction, because it's now availabe  via ALT+P etc
-- 2018-09-25T11:46:04.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Table_ID=540401 AND AD_Process_ID=1000000
;

