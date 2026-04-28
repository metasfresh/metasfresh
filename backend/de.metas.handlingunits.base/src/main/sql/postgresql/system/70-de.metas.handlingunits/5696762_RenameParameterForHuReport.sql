-- 2023-07-26T09:52:10.124Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582572,0,'IsPrintPreview',TO_TIMESTAMP('2023-07-26 12:52:09','YYYY-MM-DD HH24:MI:SS'),100,'Generate PDF without printing.','U','Generate PDF without printing.','Y','Print Preview','Print Preview',TO_TIMESTAMP('2023-07-26 12:52:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-26T09:52:10.132Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582572 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-07-26T09:52:20.866Z
UPDATE AD_Element SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2023-07-26 12:52:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582572
;

-- 2023-07-26T09:52:20.892Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582572,'de_DE') 
;

-- Element: IsPrintPreview
-- 2023-07-26T09:52:32.560Z
UPDATE AD_Element_Trl SET Description='Generieren Sie PDFs ohne zu drucken.', Help='Generieren Sie PDFs ohne zu drucken.', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-26 12:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582572 AND AD_Language='de_CH'
;

-- 2023-07-26T09:52:32.563Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582572,'de_CH') 
;

-- Element: IsPrintPreview
-- 2023-07-26T09:52:54.151Z
UPDATE AD_Element_Trl SET Description='Generieren Sie PDFs ohne zu drucken.', Help='Generieren Sie PDFs ohne zu drucken.', IsTranslated='Y', Name='Druckvorschau', PrintName='Druckvorschau',Updated=TO_TIMESTAMP('2023-07-26 12:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582572 AND AD_Language='de_DE'
;

-- 2023-07-26T09:52:54.154Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582572,'de_DE') 
;

-- 2023-07-26T09:52:54.156Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582572,'de_DE') 
;

-- Element: IsPrintPreview
-- 2023-07-26T09:52:58.584Z
UPDATE AD_Element_Trl SET Name='Druckvorschau', PrintName='Druckvorschau',Updated=TO_TIMESTAMP('2023-07-26 12:52:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582572 AND AD_Language='de_CH'
;

-- 2023-07-26T09:52:58.587Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582572,'de_CH') 
;

-- Process: M_HU_Report_QRCode(de.metas.handlingunits.process.M_HU_Report_QRCode)
-- ParameterName: IsPrintPreview
-- 2023-07-26T09:55:10.764Z
UPDATE AD_Process_Para SET AD_Element_ID=582572, ColumnName='IsPrintPreview',Updated=TO_TIMESTAMP('2023-07-26 12:55:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542664
;

-- Process: M_HU_Report_QRCode(de.metas.handlingunits.process.M_HU_Report_QRCode)
-- ParameterName: IsPrintPreview
-- 2023-07-26T09:55:27.532Z
UPDATE AD_Process_Para SET IsCentrallyMaintained='Y',Updated=TO_TIMESTAMP('2023-07-26 12:55:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542664
;

-- Process: M_HU_Report_QRCode(de.metas.handlingunits.process.M_HU_Report_QRCode)
-- ParameterName: IsPrintPreview
-- 2023-07-26T09:55:45.268Z
UPDATE AD_Process_Para SET Description='Generieren Sie PDFs ohne zu drucken.', Help='Generieren Sie PDFs ohne zu drucken.', Name='Druckvorschau',Updated=TO_TIMESTAMP('2023-07-26 12:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542664
;

