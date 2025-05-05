-- Element: DatePromised
-- 2023-02-08T17:33:51.212Z
UPDATE AD_Element_Trl SET Name='Date Promised From', PrintName='Date Promised From',Updated=TO_TIMESTAMP('2023-02-08 19:33:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=269 AND AD_Language='en_US'
;

-- 2023-02-08T17:33:51.238Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(269,'en_US') 
;

-- 2023-02-09T09:52:15.061Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582044,0,'DatePromised_From',TO_TIMESTAMP('2023-02-09 11:52:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zugesagter Termin von','Zugesagter Termin von',TO_TIMESTAMP('2023-02-09 11:52:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-09T09:52:15.063Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582044 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: DatePromised_From
-- 2023-02-09T09:52:31.204Z
UPDATE AD_Element_Trl SET Name='Date Promised from', PrintName='Date Promised from',Updated=TO_TIMESTAMP('2023-02-09 11:52:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582044 AND AD_Language='en_US'
;

-- 2023-02-09T09:52:31.206Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582044,'en_US') 
;

-- 2023-02-09T09:52:56.140Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582045,0,'DatePromised_To',TO_TIMESTAMP('2023-02-09 11:52:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zugesagter Termin bis','Zugesagter Termin bis',TO_TIMESTAMP('2023-02-09 11:52:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-09T09:52:56.141Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582045 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: DatePromised_To
-- 2023-02-09T09:53:09.794Z
UPDATE AD_Element_Trl SET Name='Date Promised through', PrintName='Date Promised through',Updated=TO_TIMESTAMP('2023-02-09 11:53:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582045 AND AD_Language='en_US'
;

-- 2023-02-09T09:53:09.796Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582045,'en_US') 
;

-- Process: C_Order_CreatePOFromSOs(de.metas.order.process.C_Order_CreatePOFromSOs)
-- ParameterName: DatePromised_From
-- 2023-02-09T09:53:56.591Z
UPDATE AD_Process_Para SET AD_Element_ID=582044,Updated=TO_TIMESTAMP('2023-02-09 11:53:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=254
;

-- Process: C_Order_CreatePOFromSOs(de.metas.order.process.C_Order_CreatePOFromSOs)
-- ParameterName: DatePromised_To
-- 2023-02-09T09:54:13.802Z
UPDATE AD_Process_Para SET AD_Element_ID=582045,Updated=TO_TIMESTAMP('2023-02-09 11:54:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540625
;

