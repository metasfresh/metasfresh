-- Run mode: SWING_CLIENT

-- 2024-01-09T19:12:44.233Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582885,0,'ShipmentDate',TO_TIMESTAMP('2024-01-09 21:12:43.597','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Lieferdatum','Lieferdatum',TO_TIMESTAMP('2024-01-09 21:12:43.597','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-09T19:12:44.251Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582885 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ShipmentDate
-- 2024-01-09T19:12:57.945Z
UPDATE AD_Element_Trl SET Name='Shipment Date', PrintName='Shipment Date',Updated=TO_TIMESTAMP('2024-01-09 21:12:57.945','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582885 AND AD_Language='en_US'
;

-- 2024-01-09T19:12:57.998Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582885,'en_US')
;

-- Run mode: SWING_CLIENT

-- Process: M_ShipmentSchedule_EnqueueSelection(de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection)
-- ParameterName: ShipmentDate
-- 2024-01-09T19:16:25.056Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582885,0,540458,542765,15,'ShipmentDate',TO_TIMESTAMP('2024-01-09 21:16:24.888','YYYY-MM-DD HH24:MI:SS.US'),100,'@DeliveryDate@','@IsShipToday@=N','de.metas.handlingunits',7,'Y','N','Y','N','N','N','Lieferdatum',35,TO_TIMESTAMP('2024-01-09 21:16:24.888','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-09T19:16:25.060Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542765 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-01-09T19:16:25.067Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582885)
;

