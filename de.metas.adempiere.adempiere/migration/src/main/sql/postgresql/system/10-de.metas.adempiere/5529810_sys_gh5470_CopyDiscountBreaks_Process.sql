-- 2019-09-03T11:52:43.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541188,'Y','de.metas.ui.web.pricing.process.M_DiscountSchemaBreak_CopyToOtherSchema_Product','N',TO_TIMESTAMP('2019-09-03 14:52:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Y',0,'Rabatte Zeilen kopieren mit Produktänderung','Y','N','Java',TO_TIMESTAMP('2019-09-03 14:52:43','YYYY-MM-DD HH24:MI:SS'),100,'M_DiscountSchemaBreak_CopyToOtherSchema_Product')
;

-- 2019-09-03T11:52:43.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541188 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-09-03T11:53:19.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1714,0,541188,541577,30,'M_DiscountSchema_ID',TO_TIMESTAMP('2019-09-03 14:53:19','YYYY-MM-DD HH24:MI:SS'),100,'Schema um den prozentualen Rabatt zu berechnen','D',0,'Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.','Y','N','Y','N','Y','N','Rabatt Schema',10,TO_TIMESTAMP('2019-09-03 14:53:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-03T11:53:19.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541577 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-09-03T11:53:54.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,541188,541578,30,'M_Product_ID',TO_TIMESTAMP('2019-09-03 14:53:54','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','D',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','Y','N','Produkt','1=1',20,TO_TIMESTAMP('2019-09-03 14:53:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-03T11:53:54.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541578 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-09-03T11:55:46.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541188,476,540737,540612,TO_TIMESTAMP('2019-09-03 14:55:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2019-09-03 14:55:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2019-09-03T11:55:52.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2019-09-03 14:55:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540737
;

-- 2019-09-03T13:14:43.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2019-09-03 16:14:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541578
;

-- 2019-09-03T13:14:57.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ReadOnlyLogic='@M_Product_ID@ > 0',Updated=TO_TIMESTAMP('2019-09-03 16:14:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541578
;







-- 2019-09-03T14:38:25.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2019-09-03 17:38:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541578
;








-- 2019-09-03T14:59:54.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=30, ReadOnlyLogic='',Updated=TO_TIMESTAMP('2019-09-03 17:59:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541578
;


