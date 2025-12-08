-- 2025-03-13T19:16:47.167Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583531,0,TO_TIMESTAMP('2025-03-13 20:16:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','VP','VP',TO_TIMESTAMP('2025-03-13 20:16:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-13T19:16:47.232Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583531 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-03-13T19:17:27.203Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583532,0,TO_TIMESTAMP('2025-03-13 20:17:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','VPName','VPName',TO_TIMESTAMP('2025-03-13 20:17:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-13T19:17:27.254Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583532 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-03-13T19:18:22.703Z
UPDATE AD_Element SET ColumnName='VP',Updated=TO_TIMESTAMP('2025-03-13 20:18:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583531
;

-- 2025-03-13T19:18:22.763Z
UPDATE AD_Column SET ColumnName='VP' WHERE AD_Element_ID=583531
;

-- 2025-03-13T19:18:22.813Z
UPDATE AD_Process_Para SET ColumnName='VP' WHERE AD_Element_ID=583531
;

-- 2025-03-13T19:18:23.034Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583531,'de_DE') 
;

-- 2025-03-13T19:18:42.605Z
UPDATE AD_Element SET ColumnName='VPName',Updated=TO_TIMESTAMP('2025-03-13 20:18:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583532
;

-- 2025-03-13T19:18:42.757Z
UPDATE AD_Column SET ColumnName='VPName' WHERE AD_Element_ID=583532
;

-- 2025-03-13T19:18:42.809Z
UPDATE AD_Process_Para SET ColumnName='VPName' WHERE AD_Element_ID=583532
;

-- 2025-03-13T19:18:43.162Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583532,'de_DE') 
;

-- 2025-03-13T19:21:24.627Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583533,0,'CustomerValue',TO_TIMESTAMP('2025-03-13 20:21:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','KdNr','KdNr',TO_TIMESTAMP('2025-03-13 20:21:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-13T19:21:24.680Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583533 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-03-13T19:21:59.430Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583534,0,'Customer',TO_TIMESTAMP('2025-03-13 20:21:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kunde','Kunde',TO_TIMESTAMP('2025-03-13 20:21:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-13T19:21:59.483Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583534 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-03-13T19:27:08.072Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583535,0,'ArticleValue',TO_TIMESTAMP('2025-03-13 20:27:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ArtikelNr','ArtikelNr',TO_TIMESTAMP('2025-03-13 20:27:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-13T19:27:08.126Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583535 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-03-13T19:27:51.435Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583536,0,'ArticleName',TO_TIMESTAMP('2025-03-13 20:27:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ArtikelName','ArtikelName',TO_TIMESTAMP('2025-03-13 20:27:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-13T19:27:51.488Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583536 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: UOMSymbol
-- 2025-03-13T19:30:14.130Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Maßeinheit', PrintName='Maßeinheit',Updated=TO_TIMESTAMP('2025-03-13 20:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=602 AND AD_Language='de_DE'
;

-- 2025-03-13T19:30:14.233Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(602,'de_DE') 
;

-- 2025-03-13T19:30:14.285Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(602,'de_DE') 
;

-- 2025-03-13T19:31:51.639Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583537,0,'CommissionRate',TO_TIMESTAMP('2025-03-13 20:31:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Provisionssatz','Provisionssatz',TO_TIMESTAMP('2025-03-13 20:31:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-13T19:31:51.693Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583537 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-03-13T19:35:32.999Z
UPDATE AD_Element SET ColumnName='Provision',Updated=TO_TIMESTAMP('2025-03-13 20:35:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574231
;

-- 2025-03-13T19:35:33.061Z
UPDATE AD_Column SET ColumnName='Provision' WHERE AD_Element_ID=574231
;

-- 2025-03-13T19:35:33.112Z
UPDATE AD_Process_Para SET ColumnName='Provision' WHERE AD_Element_ID=574231
;

-- 2025-03-13T19:35:33.318Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574231,'de_DE') 
;

