-- 2017-12-19T10:06:28.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,EntityType,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','WEBUI_PackingHUsView_AddHUsToShipperTransportationShipAndInvoice','3','Y','N','N','N',540904,'N','Y','N','Java','N','N',0,0,'Add to Transportation Order, Ship and Invoice','de.metas.ui.web.pickingslotsClearing.process.WEBUI_PackingHUsView_AddHUsToShipperTransportationShipAndInvoice','de.metas.picking',100,TO_TIMESTAMP('2017-12-19 10:06:27','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-19 10:06:27','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-19T10:06:28.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540904 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-12-19T10:07:12.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AD_Client_ID=0, IsActive='Y', ProcedureName=NULL, IsReport='N', IsDirectPrint='N', AD_ReportView_ID=NULL, AccessLevel='3', ShowHelp='Y', WorkflowValue=NULL, AD_Workflow_ID=NULL, IsBetaFunctionality='N', IsServerProcess='N', AD_Form_ID=NULL, CopyFromProcess='N', AD_PrintFormat_ID=NULL, Help=NULL, JasperReport=NULL, SQLStatement=NULL, JasperReport_Tabular=NULL, AllowProcessReRun='N', IsUseBPartnerLanguage='Y', IsApplySecuritySettings='N', Description=NULL, Type='Java', RefreshAllAfterExecution='N', IsOneInstanceOnly='N', LockWaitTimeout=0, AD_Org_ID=0, Classname='de.metas.ui.web.pickingslotsClearing.process.WEBUI_PackingHUsView_AddHUsToShipperTransportation', EntityType='de.metas.picking',Updated=TO_TIMESTAMP('2017-12-19 10:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540904
;

-- 2017-12-19T10:07:12.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,AD_Val_Rule_ID,IsCentrallyMaintained,AD_Element_ID,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,EntityType,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540904,30,'N',541256,540248,'Y',540089,'M_ShipperTransportation_ID','Y','N',0,'Transport Auftrag','de.metas.picking',100,TO_TIMESTAMP('2017-12-19 10:07:12','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-19 10:07:12','YYYY-MM-DD HH24:MI:SS'),0,10)
;

-- 2017-12-19T10:07:12.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541256 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-12-19T10:07:12.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541256
;

-- 2017-12-19T10:07:12.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT AD_Process_Para_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541256
;

-- 2017-12-19T10:07:28.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.pickingslotsClearing.process.WEBUI_PackingHUsView_AddHUsToShipperTransportationShipAndInvoice',Updated=TO_TIMESTAMP('2017-12-19 10:07:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540904
;

