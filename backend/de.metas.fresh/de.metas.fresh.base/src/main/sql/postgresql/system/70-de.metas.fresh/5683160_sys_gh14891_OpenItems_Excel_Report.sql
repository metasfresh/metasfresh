-- 2023-03-29T10:20:33.389Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582184,0,'Kunden_Nr',TO_TIMESTAMP('2023-03-29 11:20:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kunden Nr.','Kunden Nr.',TO_TIMESTAMP('2023-03-29 11:20:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T10:20:33.504Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582184 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-29T10:21:27.542Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Customer No.', PrintName='Customer No.',Updated=TO_TIMESTAMP('2023-03-29 11:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582184 AND AD_Language='en_US'
;

-- 2023-03-29T10:21:27.693Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582184,'en_US') 
;

-- 2023-03-29T10:22:46.367Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582185,0,'Kunden_Name',TO_TIMESTAMP('2023-03-29 11:22:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kunden Name','Kunden Name',TO_TIMESTAMP('2023-03-29 11:22:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T10:22:46.484Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582185 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-29T10:23:12.174Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Customer Name', PrintName='Customer Name',Updated=TO_TIMESTAMP('2023-03-29 11:23:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582185 AND AD_Language='en_US'
;

-- 2023-03-29T10:23:12.296Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582185,'en_US') 
;

-- 2023-03-29T10:26:50.720Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Gesamt',Updated=TO_TIMESTAMP('2023-03-29 11:26:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=316 AND AD_Language='en_GB'
;

-- 2023-03-29T10:26:50.835Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(316,'en_GB') 
;

-- 2023-03-29T10:27:04.338Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Gesamt',Updated=TO_TIMESTAMP('2023-03-29 11:27:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=316 AND AD_Language='fr_CH'
;

-- 2023-03-29T10:27:04.451Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(316,'fr_CH') 
;

-- 2023-03-29T10:27:08.803Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Gesamt',Updated=TO_TIMESTAMP('2023-03-29 11:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=316 AND AD_Language='it_CH'
;

-- 2023-03-29T10:27:08.940Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(316,'it_CH') 
;

-- 2023-03-29T10:27:20.417Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Gesamt',Updated=TO_TIMESTAMP('2023-03-29 11:27:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=316 AND AD_Language='de_CH'
;

-- 2023-03-29T10:27:20.536Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(316,'de_CH') 
;

-- 2023-03-29T10:27:31.780Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Gesamt',Updated=TO_TIMESTAMP('2023-03-29 11:27:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=316 AND AD_Language='nl_NL'
;

-- 2023-03-29T10:27:31.902Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(316,'nl_NL') 
;

-- 2023-03-29T10:27:58.569Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Gesamt',Updated=TO_TIMESTAMP('2023-03-29 11:27:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=316 AND AD_Language='de_DE'
;

-- 2023-03-29T10:27:58.676Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(316,'de_DE') 
;

-- 2023-03-29T10:27:58.895Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(316,'de_DE') 
;

-- 2023-03-29T10:27:59.002Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Gesamt', Name='Summe Gesamt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=316)
;

-- 2023-03-29T10:34:12.049Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582186,0,'Gesamt',TO_TIMESTAMP('2023-03-29 11:34:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Gesamt','Gesamt',TO_TIMESTAMP('2023-03-29 11:34:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T10:34:12.166Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582186 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-29T10:34:55.814Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Total', PrintName='Total',Updated=TO_TIMESTAMP('2023-03-29 11:34:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582186 AND AD_Language='en_US'
;

-- 2023-03-29T10:34:55.952Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582186,'en_US') 
;

-- 2023-03-29T10:42:14.437Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582187,0,'OpenCurrency',TO_TIMESTAMP('2023-03-29 11:42:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','OffenWährun','OffenWährun',TO_TIMESTAMP('2023-03-29 11:42:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T10:42:14.562Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582187 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-29T10:42:35.072Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Open Currency', PrintName='Open Currency',Updated=TO_TIMESTAMP('2023-03-29 11:42:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582187 AND AD_Language='en_US'
;

-- 2023-03-29T10:42:35.184Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582187,'en_US') 
;

-- 2023-03-29T10:52:57.122Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582188,0,'Discount_until',TO_TIMESTAMP('2023-03-29 11:52:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Skonto bis','Skonto bis',TO_TIMESTAMP('2023-03-29 11:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T10:52:57.245Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582188 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-29T10:53:16.784Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Discount until', PrintName='Discount until',Updated=TO_TIMESTAMP('2023-03-29 11:53:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582188 AND AD_Language='en_US'
;

-- 2023-03-29T10:53:16.915Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582188,'en_US') 
;

-- 2023-03-29T10:54:22.276Z
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='Customer_No',Updated=TO_TIMESTAMP('2023-03-29 11:54:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582184
;

-- 2023-03-29T10:54:22.718Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Customer_No', Name='Kunden Nr.', Description=NULL, Help=NULL WHERE AD_Element_ID=582184
;

-- 2023-03-29T10:54:22.846Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Customer_No', Name='Kunden Nr.', Description=NULL, Help=NULL, AD_Element_ID=582184 WHERE UPPER(ColumnName)='CUSTOMER_NO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-29T10:54:22.964Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Customer_No', Name='Kunden Nr.', Description=NULL, Help=NULL WHERE AD_Element_ID=582184 AND IsCentrallyMaintained='Y'
;

-- 2023-03-29T10:55:13.482Z
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='Customer_Name',Updated=TO_TIMESTAMP('2023-03-29 11:55:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582185
;

-- 2023-03-29T10:55:13.925Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Customer_Name', Name='Kunden Name', Description=NULL, Help=NULL WHERE AD_Element_ID=582185
;

-- 2023-03-29T10:55:14.039Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Customer_Name', Name='Kunden Name', Description=NULL, Help=NULL, AD_Element_ID=582185 WHERE UPPER(ColumnName)='CUSTOMER_NAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-29T10:55:14.153Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Customer_Name', Name='Kunden Name', Description=NULL, Help=NULL WHERE AD_Element_ID=582185 AND IsCentrallyMaintained='Y'
;

-- 2023-03-29T10:55:51.025Z
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='Total',Updated=TO_TIMESTAMP('2023-03-29 11:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582186
;

-- 2023-03-29T10:55:51.492Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Total', Name='Gesamt', Description=NULL, Help=NULL WHERE AD_Element_ID=582186
;

-- 2023-03-29T10:55:51.619Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Total', Name='Gesamt', Description=NULL, Help=NULL, AD_Element_ID=582186 WHERE UPPER(ColumnName)='TOTAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-29T10:55:51.744Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Total', Name='Gesamt', Description=NULL, Help=NULL WHERE AD_Element_ID=582186 AND IsCentrallyMaintained='Y'
;

-- 2023-03-29T11:02:08.430Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582190,0,'Net_until',TO_TIMESTAMP('2023-03-29 12:02:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Netto bis','Netto bis',TO_TIMESTAMP('2023-03-29 12:02:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T11:02:08.544Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582190 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

