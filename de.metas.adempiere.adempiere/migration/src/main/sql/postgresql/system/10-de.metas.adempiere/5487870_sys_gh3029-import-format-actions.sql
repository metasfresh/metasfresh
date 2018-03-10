-- 2018-03-10T12:12:31.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2018-03-10 12:12:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4703
;

-- 2018-03-10T12:13:25.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,135,381,TO_TIMESTAMP('2018-03-10 12:13:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2018-03-10 12:13:25','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2018-03-10T12:19:29.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Erstelle Import Format Zeilen',Updated=TO_TIMESTAMP('2018-03-10 12:19:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540896
;

-- 2018-03-10T12:19:35.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-10 12:19:35','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540896 AND AD_Language='en_US'
;

