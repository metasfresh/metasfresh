-- 2019-12-11T15:32:07.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,540318,318,540764,167,TO_TIMESTAMP('2019-12-11 16:32:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2019-12-11 16:32:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2019-12-11T15:32:50.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, ReadOnlyLogic='',Updated=TO_TIMESTAMP('2019-12-11 16:32:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548468
;

-- 2019-12-11T15:33:08.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-12-11 16:33:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551554
;

-- 2019-12-11T15:33:46.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='(@EDI_ExportStatus/''X''@=''E'' | @EDI_ExportStatus/''X''@=''I'') & @IsEdiEnabled/''X''@=''Y''',Updated=TO_TIMESTAMP('2019-12-11 16:33:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551574
;

