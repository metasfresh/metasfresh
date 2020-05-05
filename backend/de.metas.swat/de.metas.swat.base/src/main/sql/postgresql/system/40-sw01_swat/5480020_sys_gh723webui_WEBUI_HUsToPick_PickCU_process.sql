-- 2017-12-08T15:53:29.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,EntityType,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','WEBUI_HUsToPick_PickCU','3','Y','N','N','N',540894,'N','Y','N','Java','N','N',0,0,'Pick CUs','de.metas.ui.web.picking.husToPick.process.WEBUI_HUsToPick_PickCU','de.metas.picking',100,TO_TIMESTAMP('2017-12-08 15:53:28','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-08 15:53:28','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-08T15:53:29.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540894 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-12-08T15:54:17.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,IsCentrallyMaintained,AD_Element_ID,IsEncrypted,Description,Help,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,EntityType,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540894,30,'N',541244,'Y',454,'N','Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','M_Product_ID','Y','N',0,'Produkt','de.metas.picking',100,TO_TIMESTAMP('2017-12-08 15:54:17','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-08 15:54:17','YYYY-MM-DD HH24:MI:SS'),0,10)
;

-- 2017-12-08T15:54:17.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541244 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-12-08T15:54:43.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,ValueMin,IsCentrallyMaintained,AD_Element_ID,IsEncrypted,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,EntityType,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540894,29,'N',541245,'0','Y',542492,'N','QtyCU','Y','N',0,'Menge CU','de.metas.handlingunits',100,TO_TIMESTAMP('2017-12-08 15:54:42','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-08 15:54:42','YYYY-MM-DD HH24:MI:SS'),0,20)
;

-- 2017-12-08T15:54:43.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541245 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-12-08T16:28:21.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=544600
;

-- 2017-12-08T16:28:21.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=544600
;

-- 2017-12-08T16:52:38.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,AD_Message_ID,MsgText,AD_Org_ID,EntityType,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'de.metas.ui.web.picking.husToPick.process.WEBUI_HUsToPick_PickCU.InvalidProduct',544601,'You scanned the wrong product: {0}.
Product {1} was expected.
',0,'de.metas.picking',100,TO_TIMESTAMP('2017-12-08 16:52:38','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-08 16:52:38','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-08T16:52:38.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544601 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

