-- Value: M_ShipmentSchedule_EnqueueSelection
-- Classname: de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection
-- 2023-01-23T20:12:06.521Z
UPDATE AD_Process SET Classname='de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection',Updated=TO_TIMESTAMP('2023-01-23 22:12:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540458
;

-- Process: M_ShipmentSchedule_EnqueueSelection(de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection)
-- ParameterName: IsFEC
-- 2023-01-23T20:12:33.644Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581947,0,540458,542462,20,'IsFEC',TO_TIMESTAMP('2023-01-23 22:12:33','YYYY-MM-DD HH24:MI:SS'),100,'N','1=2','de.metas.handlingunits',0,'Y','N','Y','N','Y','N','FEC','1=1',40,TO_TIMESTAMP('2023-01-23 22:12:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-23T20:12:33.648Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542462 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ShipmentSchedule_EnqueueSelection(de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection)
-- ParameterName: C_ForeignExchangeContract_ID
-- 2023-01-23T20:12:54.498Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581935,0,540458,542463,30,'C_ForeignExchangeContract_ID',TO_TIMESTAMP('2023-01-23 22:12:54','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','de.metas.handlingunits',0,'Y','N','Y','N','N','N','Foreign Exchange Contract',50,TO_TIMESTAMP('2023-01-23 22:12:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-23T20:12:54.499Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542463 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

