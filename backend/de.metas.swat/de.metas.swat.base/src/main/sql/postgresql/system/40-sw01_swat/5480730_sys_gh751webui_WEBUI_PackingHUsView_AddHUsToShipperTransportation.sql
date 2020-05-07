-- 2017-12-15T13:30:28.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,EntityType,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','WEBUI_PackingHUsView_AddHUsToShipperTransportation','3','Y','N','N','N',540899,'N','Y','N','Java','N','N',0,0,'Add to Transportation Order','de.metas.ui.web.pickingslotsClearing.process.WEBUI_PackingHUsView_AddHUsToShipperTransportation','de.metas.picking',100,TO_TIMESTAMP('2017-12-15 13:30:27','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-15 13:30:27','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-15T13:30:28.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540899 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-12-15T13:31:17.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,AD_Val_Rule_ID,IsCentrallyMaintained,AD_Element_ID,IsEncrypted,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,EntityType,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540899,30,'N',541252,540248,'Y',540089,'N','M_ShipperTransportation_ID','Y','N',0,'Transport Auftrag','METAS_SHIPPING',100,TO_TIMESTAMP('2017-12-15 13:31:17','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-15 13:31:17','YYYY-MM-DD HH24:MI:SS'),0,10)
;

-- 2017-12-15T13:31:17.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541252 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

