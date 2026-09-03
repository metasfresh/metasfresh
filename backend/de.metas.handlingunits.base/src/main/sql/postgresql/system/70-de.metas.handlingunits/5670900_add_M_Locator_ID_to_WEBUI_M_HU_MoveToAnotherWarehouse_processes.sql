-- Process: WEBUI_M_HU_MoveToAnotherWarehouse(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_MoveToAnotherWarehouse_ExclQuarantined)
-- ParameterName: M_Locator_ID
-- 2023-01-09T15:19:02.194Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,448,0,540820,542437,31,127,'M_Locator_ID',TO_TIMESTAMP('2023-01-09 17:19:01','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort im Lager','de.metas.handlingunits',0,'"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','Y','N','Y','N','Lagerort',20,TO_TIMESTAMP('2023-01-09 17:19:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T15:19:02.197Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542437 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_M_HU_MoveToAnotherWarehouse_QA(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_MoveToAnotherWarehouse_InclQuarantined)
-- ParameterName: M_Locator_ID
-- 2023-01-09T15:52:51.089Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,448,0,541006,542438,31,127,'M_Locator_ID',TO_TIMESTAMP('2023-01-09 17:52:50','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort im Lager','de.metas.handlingunits',0,'"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','Y','N','Y','N','Lagerort',20,TO_TIMESTAMP('2023-01-09 17:52:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T15:52:51.094Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542438 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

