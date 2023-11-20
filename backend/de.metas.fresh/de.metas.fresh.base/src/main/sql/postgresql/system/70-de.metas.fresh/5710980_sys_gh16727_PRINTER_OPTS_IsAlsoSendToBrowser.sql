-- Run mode: SWING_CLIENT

-- 2023-11-19T22:15:59.921Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582810,0,'PRINTER_OPTS_IsAlsoSendToBrowser',TO_TIMESTAMP('2023-11-19 23:15:59.572','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Send To Browser','Send To Browser',TO_TIMESTAMP('2023-11-19 23:15:59.572','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-19T22:15:59.932Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582810 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: Lieferschein (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: PRINTER_OPTS_IsAlsoSendToBrowser
-- 2023-11-19T22:16:15.642Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582810,0,500008,542738,20,'PRINTER_OPTS_IsAlsoSendToBrowser',TO_TIMESTAMP('2023-11-19 23:16:15.427','YYYY-MM-DD HH24:MI:SS.US'),100,'N','U',0,'Y','N','Y','N','Y','N','Send To Browser',20,TO_TIMESTAMP('2023-11-19 23:16:15.427','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-19T22:16:15.644Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542738 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-11-19T22:16:15.668Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582810)
;

