-- 2022-10-25T14:44:46.235Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581607,0,'Legal_form',TO_TIMESTAMP('2022-10-25 16:44:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Rechtsform','Rechtsform',TO_TIMESTAMP('2022-10-25 16:44:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:44:46.240Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581607 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:45:42.381Z
UPDATE AD_Element_Trl SET Name='Legal Form', PrintName='Legal Form',Updated=TO_TIMESTAMP('2022-10-25 16:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581607 AND AD_Language='en_US'
;

-- 2022-10-25T14:45:42.397Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581607,'en_US') 
;

-- 2022-10-25T14:46:59.178Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581608,0,'Number_employees',TO_TIMESTAMP('2022-10-25 16:46:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Anz. Mitarbeiter','Anz. Mitarbeiter',TO_TIMESTAMP('2022-10-25 16:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:46:59.179Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581608 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:47:22.383Z
UPDATE AD_Element_Trl SET Name='Number of Employees', PrintName='Number of Employees',Updated=TO_TIMESTAMP('2022-10-25 16:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581608 AND AD_Language='en_US'
;

-- 2022-10-25T14:47:22.385Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581608,'en_US') 
;

-- 2022-10-25T14:47:27.447Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 16:47:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581608 AND AD_Language='de_DE'
;

-- 2022-10-25T14:47:27.449Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581608,'de_DE') 
;

-- 2022-10-25T14:47:27.461Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581608,'de_DE') 
;

-- 2022-10-25T14:48:11.471Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581609,0,'Industrial_sector',TO_TIMESTAMP('2022-10-25 16:48:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Branche','Branche',TO_TIMESTAMP('2022-10-25 16:48:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:48:11.472Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581609 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:48:43.178Z
UPDATE AD_Element_Trl SET Name='Industrial Sector', PrintName='Industrial Sector',Updated=TO_TIMESTAMP('2022-10-25 16:48:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581609 AND AD_Language='en_US'
;

-- 2022-10-25T14:48:43.179Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581609,'en_US') 
;

-- 2022-10-25T14:48:52.770Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 16:48:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581609 AND AD_Language='de_DE'
;

-- 2022-10-25T14:48:52.771Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581609,'de_DE') 
;

-- 2022-10-25T14:48:52.779Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581609,'de_DE') 
;

-- 2022-10-25T14:49:31.743Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581610,0,'Affiliate',TO_TIMESTAMP('2022-10-25 16:49:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Verbundenes Unternehmen','Verbundenes Unternehmen',TO_TIMESTAMP('2022-10-25 16:49:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:49:31.744Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581610 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:49:44.709Z
UPDATE AD_Element_Trl SET Name='Affiliate', PrintName='Affiliate',Updated=TO_TIMESTAMP('2022-10-25 16:49:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581610 AND AD_Language='en_US'
;

-- 2022-10-25T14:49:44.710Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581610,'en_US') 
;

-- 2022-10-25T14:49:51.878Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 16:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581610 AND AD_Language='de_DE'
;

-- 2022-10-25T14:49:51.879Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581610,'de_DE') 
;

-- 2022-10-25T14:49:51.885Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581610,'de_DE') 
;

-- 2022-10-25T14:50:07.967Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581611,0,'Facebook',TO_TIMESTAMP('2022-10-25 16:50:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Facebook','Facebook',TO_TIMESTAMP('2022-10-25 16:50:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:50:07.968Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581611 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:50:16.585Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 16:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581611 AND AD_Language='de_DE'
;

-- 2022-10-25T14:50:16.586Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581611,'de_DE') 
;

-- 2022-10-25T14:50:16.594Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581611,'de_DE') 
;

-- 2022-10-25T14:50:38.769Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581612,0,'Twitter',TO_TIMESTAMP('2022-10-25 16:50:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Twitter','Twitter',TO_TIMESTAMP('2022-10-25 16:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:50:38.770Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581612 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:50:42.908Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 16:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581612 AND AD_Language='de_DE'
;

-- 2022-10-25T14:50:42.909Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581612,'de_DE') 
;

-- 2022-10-25T14:50:42.916Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581612,'de_DE') 
;

-- 2022-10-25T14:50:56.867Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581613,0,'LinkedIn',TO_TIMESTAMP('2022-10-25 16:50:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','LinkedIn','LinkedIn',TO_TIMESTAMP('2022-10-25 16:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:50:56.867Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581613 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:51:00.168Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 16:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581613 AND AD_Language='de_DE'
;

-- 2022-10-25T14:51:00.169Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581613,'de_DE') 
;

-- 2022-10-25T14:51:00.179Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581613,'de_DE') 
;

-- 2022-10-25T14:51:09.785Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581614,0,'Xing',TO_TIMESTAMP('2022-10-25 16:51:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Xing','Xing',TO_TIMESTAMP('2022-10-25 16:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:51:09.787Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581614 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:51:36.383Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581615,0,'Social_media_5',TO_TIMESTAMP('2022-10-25 16:51:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Social Media 5','Social Media 5',TO_TIMESTAMP('2022-10-25 16:51:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:51:36.384Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581615 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:52:31.733Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581616,0,'Preferred_import',TO_TIMESTAMP('2022-10-25 16:52:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bevorzugter Import','Bevorzugter Import',TO_TIMESTAMP('2022-10-25 16:52:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:52:31.733Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581616 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:52:42.398Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 16:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581616 AND AD_Language='de_DE'
;

-- 2022-10-25T14:52:42.399Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581616,'de_DE') 
;

-- 2022-10-25T14:52:42.405Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581616,'de_DE') 
;

-- 2022-10-25T14:52:50.733Z
UPDATE AD_Element_Trl SET Name='Preferred Import', PrintName='Preferred Import',Updated=TO_TIMESTAMP('2022-10-25 16:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581616 AND AD_Language='en_US'
;

-- 2022-10-25T14:52:50.734Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581616,'en_US') 
;

-- 2022-10-25T14:53:23.476Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581617,0,'MRN_ABN',TO_TIMESTAMP('2022-10-25 16:53:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','MRN/ABN','MRN/ABN',TO_TIMESTAMP('2022-10-25 16:53:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:53:23.477Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581617 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:53:41.748Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 16:53:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581617 AND AD_Language='de_DE'
;

-- 2022-10-25T14:53:41.748Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581617,'de_DE') 
;

-- 2022-10-25T14:53:41.760Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581617,'de_DE') 
;

-- 2022-10-25T14:53:49.965Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581618,0,'ABC',TO_TIMESTAMP('2022-10-25 16:53:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ABC','ABC',TO_TIMESTAMP('2022-10-25 16:53:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:53:49.966Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581618 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:53:52.602Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 16:53:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581618 AND AD_Language='de_DE'
;

-- 2022-10-25T14:53:52.603Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581618,'de_DE') 
;

-- 2022-10-25T14:53:52.608Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581618,'de_DE') 
;

-- 2022-10-25T14:54:11.489Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581619,0,'Photo',TO_TIMESTAMP('2022-10-25 16:54:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Foto','Foto',TO_TIMESTAMP('2022-10-25 16:54:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:54:11.491Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581619 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:54:20.532Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 16:54:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581619 AND AD_Language='de_DE'
;

-- 2022-10-25T14:54:20.533Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581619,'de_DE') 
;

-- 2022-10-25T14:54:20.539Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581619,'de_DE') 
;

-- 2022-10-25T14:54:25.703Z
UPDATE AD_Element_Trl SET Name='Photo', PrintName='Photo',Updated=TO_TIMESTAMP('2022-10-25 16:54:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581619 AND AD_Language='en_US'
;

-- 2022-10-25T14:54:25.704Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581619,'en_US') 
;

-- 2022-10-25T14:54:51.748Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581620,0,'Email_private',TO_TIMESTAMP('2022-10-25 16:54:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','eMail Private','eMail Private',TO_TIMESTAMP('2022-10-25 16:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:54:51.749Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581620 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:55:19.997Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='eMail Privat', PrintName='eMail Privat',Updated=TO_TIMESTAMP('2022-10-25 16:55:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581620 AND AD_Language='de_DE'
;

-- 2022-10-25T14:55:19.998Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581620,'de_DE') 
;

-- 2022-10-25T14:55:20.004Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581620,'de_DE') 
;

-- 2022-10-25T14:55:20.005Z
UPDATE AD_Column SET ColumnName='Email_private', Name='eMail Privat', Description=NULL, Help=NULL WHERE AD_Element_ID=581620
;

-- 2022-10-25T14:55:20.005Z
UPDATE AD_Process_Para SET ColumnName='Email_private', Name='eMail Privat', Description=NULL, Help=NULL, AD_Element_ID=581620 WHERE UPPER(ColumnName)='EMAIL_PRIVATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-10-25T14:55:20.008Z
UPDATE AD_Process_Para SET ColumnName='Email_private', Name='eMail Privat', Description=NULL, Help=NULL WHERE AD_Element_ID=581620 AND IsCentrallyMaintained='Y'
;

-- 2022-10-25T14:55:20.008Z
UPDATE AD_Field SET Name='eMail Privat', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581620) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581620)
;

-- 2022-10-25T14:55:20.019Z
UPDATE AD_PrintFormatItem pi SET PrintName='eMail Privat', Name='eMail Privat' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581620)
;

-- 2022-10-25T14:55:20.020Z
UPDATE AD_Tab SET Name='eMail Privat', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581620
;

-- 2022-10-25T14:55:20.021Z
UPDATE AD_WINDOW SET Name='eMail Privat', Description=NULL, Help=NULL WHERE AD_Element_ID = 581620
;

-- 2022-10-25T14:55:20.022Z
UPDATE AD_Menu SET   Name = 'eMail Privat', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581620
;

-- 2022-10-25T14:55:48.807Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581621,0,'Previous_company',TO_TIMESTAMP('2022-10-25 16:55:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Vorherige Arbeitsstelle','Vorherige Arbeitsstelle',TO_TIMESTAMP('2022-10-25 16:55:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:55:48.808Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581621 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:55:51.558Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 16:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581621 AND AD_Language='de_DE'
;

-- 2022-10-25T14:55:51.559Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581621,'de_DE') 
;

-- 2022-10-25T14:55:51.565Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581621,'de_DE') 
;

-- 2022-10-25T14:56:03.387Z
UPDATE AD_Element_Trl SET Name='Previous Company', PrintName='Previous Company',Updated=TO_TIMESTAMP('2022-10-25 16:56:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581621 AND AD_Language='en_US'
;

-- 2022-10-25T14:56:03.388Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581621,'en_US') 
;

-- 2022-10-25T14:57:05.652Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581622,0,'Hobbies_interests',TO_TIMESTAMP('2022-10-25 16:57:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hobbies/ Interessen','Hobbies/ Interessen',TO_TIMESTAMP('2022-10-25 16:57:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T14:57:05.653Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581622 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T14:57:08.017Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 16:57:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581622 AND AD_Language='en_US'
;

-- 2022-10-25T14:57:08.018Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581622,'en_US') 
;

-- 2022-10-25T14:57:18.328Z
UPDATE AD_Element_Trl SET Name='Hobbies/ Interests', PrintName='Hobbies/ Interests',Updated=TO_TIMESTAMP('2022-10-25 16:57:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581622 AND AD_Language='en_US'
;

-- 2022-10-25T14:57:18.328Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581622,'en_US') 
;

-- 2022-10-25T14:57:28.128Z
UPDATE AD_Element_Trl SET Name='Hobbies/Interests', PrintName='Hobbies/Interests',Updated=TO_TIMESTAMP('2022-10-25 16:57:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581622 AND AD_Language='en_US'
;

-- 2022-10-25T14:57:28.128Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581622,'en_US') 
;

-- Name: Legal Form List
-- 2022-10-25T14:59:27.335Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541684,TO_TIMESTAMP('2022-10-25 16:59:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Legal Form List',TO_TIMESTAMP('2022-10-25 16:59:27','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-10-25T14:59:27.337Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541684 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Legal Form List
-- Value: LF1
-- Name: Legal Form 1
-- 2022-10-25T15:01:20.130Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541684,543319,TO_TIMESTAMP('2022-10-25 17:01:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Legal Form 1',TO_TIMESTAMP('2022-10-25 17:01:20','YYYY-MM-DD HH24:MI:SS'),100,'LF1','Legal Form 1')
;

-- 2022-10-25T15:01:20.131Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543319 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Legal Form List
-- Value: LF2
-- Name: Legal Form 2
-- 2022-10-25T15:01:36.577Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541684,543320,TO_TIMESTAMP('2022-10-25 17:01:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Legal Form 2',TO_TIMESTAMP('2022-10-25 17:01:36','YYYY-MM-DD HH24:MI:SS'),100,'LF2','Legal Form 2')
;

-- 2022-10-25T15:01:36.578Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543320 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Name: Legal Form Reference List
-- 2022-10-25T15:01:49.838Z
UPDATE AD_Reference SET Name='Legal Form Reference List',Updated=TO_TIMESTAMP('2022-10-25 17:01:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541684
;

-- Column: C_BPartner.Legal_form
-- 2022-10-25T15:02:19.004Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584807,581607,0,17,541684,291,'Legal_form',TO_TIMESTAMP('2022-10-25 17:02:18','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,3,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechtsform',0,0,TO_TIMESTAMP('2022-10-25 17:02:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:02:19.005Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584807 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:02:19.007Z
/* DDL */  select update_Column_Translation_From_AD_Element(581607) 
;

-- Column: C_BPartner.Legal_form
-- 2022-10-25T15:02:59.386Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-10-25 17:02:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584807
;

-- 2022-10-25T15:03:40.271Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Legal_form VARCHAR(3)')
;

-- Name: Number Employees Reference List
-- 2022-10-25T15:05:02.600Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541685,TO_TIMESTAMP('2022-10-25 17:05:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Number Employees Reference List',TO_TIMESTAMP('2022-10-25 17:05:02','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-10-25T15:05:02.601Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541685 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Number Employees Reference List
-- Value: 1-10
-- Name: 1-10
-- 2022-10-25T15:05:22.728Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541685,543321,TO_TIMESTAMP('2022-10-25 17:05:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','1-10',TO_TIMESTAMP('2022-10-25 17:05:22','YYYY-MM-DD HH24:MI:SS'),100,'1-10','1-10')
;

-- 2022-10-25T15:05:22.729Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543321 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Number Employees Reference List
-- Value: 10-20
-- Name: 10-20
-- 2022-10-25T15:05:47.693Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541685,543322,TO_TIMESTAMP('2022-10-25 17:05:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','10-20',TO_TIMESTAMP('2022-10-25 17:05:47','YYYY-MM-DD HH24:MI:SS'),100,'10-20','10-20')
;

-- 2022-10-25T15:05:47.694Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543322 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Number Employees Reference List
-- Value: 1
-- Name: 10-20
-- 2022-10-25T15:07:01.213Z
UPDATE AD_Ref_List SET Value='1',Updated=TO_TIMESTAMP('2022-10-25 17:07:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543322
;

-- Reference: Number Employees Reference List
-- Value: 2
-- Name: 1-10
-- 2022-10-25T15:07:06.598Z
UPDATE AD_Ref_List SET Value='2',Updated=TO_TIMESTAMP('2022-10-25 17:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543321
;

-- Column: C_BPartner.Number_employees
-- 2022-10-25T15:07:12.305Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584808,581608,0,17,541685,291,'Number_employees',TO_TIMESTAMP('2022-10-25 17:07:12','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Anz. Mitarbeiter',0,0,TO_TIMESTAMP('2022-10-25 17:07:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:07:12.307Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584808 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:07:12.308Z
/* DDL */  select update_Column_Translation_From_AD_Element(581608) 
;

-- 2022-10-25T15:07:26.165Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Number_employees VARCHAR(2)')
;

-- Name: Industrial Sector
-- 2022-10-25T15:09:48.588Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541686,TO_TIMESTAMP('2022-10-25 17:09:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Industrial Sector',TO_TIMESTAMP('2022-10-25 17:09:48','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-10-25T15:09:48.589Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541686 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Industrial Sector
-- Value: TI
-- Name: Transport industry.
-- 2022-10-25T15:10:56.303Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541686,543323,TO_TIMESTAMP('2022-10-25 17:10:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Transport industry.',TO_TIMESTAMP('2022-10-25 17:10:56','YYYY-MM-DD HH24:MI:SS'),100,'TI','Transport industry.')
;

-- 2022-10-25T15:10:56.304Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543323 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Industrial Sector
-- Value: TI
-- Name: Transport industry
-- 2022-10-25T15:11:09.328Z
UPDATE AD_Ref_List SET Name='Transport industry',Updated=TO_TIMESTAMP('2022-10-25 17:11:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543323
;

-- Reference: Industrial Sector
-- Value: TI
-- Name: Transport industry
-- 2022-10-25T15:11:15.483Z
UPDATE AD_Ref_List SET ValueName='Transport industry',Updated=TO_TIMESTAMP('2022-10-25 17:11:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543323
;

-- Reference: Industrial Sector
-- Value: AI
-- Name: Agriculture industry
-- 2022-10-25T15:11:37.798Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541686,543324,TO_TIMESTAMP('2022-10-25 17:11:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Agriculture industry',TO_TIMESTAMP('2022-10-25 17:11:37','YYYY-MM-DD HH24:MI:SS'),100,'AI','Agriculture industry')
;

-- 2022-10-25T15:11:37.799Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543324 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Industrial Sector
-- Value: TE
-- Name: Telecommunication industry
-- 2022-10-25T15:12:32.436Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541686,543326,TO_TIMESTAMP('2022-10-25 17:12:32','YYYY-MM-DD HH24:MI:SS'),100,'Telecommunication industry','D','Y','Telecommunication industry',TO_TIMESTAMP('2022-10-25 17:12:32','YYYY-MM-DD HH24:MI:SS'),100,'TE','Telecommunication industry')
;

-- 2022-10-25T15:12:32.437Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543326 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_BPartner.Industrial_sector
-- 2022-10-25T15:12:53.757Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584809,581609,0,17,541686,291,'Industrial_sector',TO_TIMESTAMP('2022-10-25 17:12:53','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Branche',0,0,TO_TIMESTAMP('2022-10-25 17:12:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:12:53.758Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584809 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:12:53.760Z
/* DDL */  select update_Column_Translation_From_AD_Element(581609) 
;

-- 2022-10-25T15:13:32.741Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Industrial_sector VARCHAR(2)')
;

-- Column: C_BPartner.Affiliate
-- 2022-10-25T15:14:08.449Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584810,581610,0,20,291,'Affiliate',TO_TIMESTAMP('2022-10-25 17:14:08','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verbundenes Unternehmen',0,0,TO_TIMESTAMP('2022-10-25 17:14:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:14:08.450Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584810 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:14:08.452Z
/* DDL */  select update_Column_Translation_From_AD_Element(581610) 
;

-- 2022-10-25T15:14:17.498Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Affiliate CHAR(1) DEFAULT ''N'' CHECK (Affiliate IN (''Y'',''N'')) NOT NULL')
;

-- Column: C_BPartner.Facebook
-- 2022-10-25T15:14:55.956Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584811,581611,0,40,291,'Facebook',TO_TIMESTAMP('2022-10-25 17:14:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Facebook',0,0,TO_TIMESTAMP('2022-10-25 17:14:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:14:55.957Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584811 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:14:55.959Z
/* DDL */  select update_Column_Translation_From_AD_Element(581611) 
;

-- 2022-10-25T15:15:00.905Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Facebook VARCHAR(255)')
;

-- Column: C_BPartner.Twitter
-- 2022-10-25T15:15:17.870Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584813,581612,0,40,291,'Twitter',TO_TIMESTAMP('2022-10-25 17:15:17','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Twitter',0,0,TO_TIMESTAMP('2022-10-25 17:15:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:15:17.871Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584813 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:15:17.872Z
/* DDL */  select update_Column_Translation_From_AD_Element(581612) 
;

-- 2022-10-25T15:15:19.736Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Twitter VARCHAR(255)')
;

-- Column: C_BPartner.LinkedIn
-- 2022-10-25T15:15:37.426Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584814,581613,0,40,291,'LinkedIn',TO_TIMESTAMP('2022-10-25 17:15:37','YYYY-MM-DD HH24:MI:SS'),100,'N','	','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'LinkedIn',0,0,TO_TIMESTAMP('2022-10-25 17:15:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:15:37.427Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584814 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:15:37.429Z
/* DDL */  select update_Column_Translation_From_AD_Element(581613) 
;

-- 2022-10-25T15:15:38.853Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN LinkedIn VARCHAR(255) DEFAULT ''	''')
;

-- Column: C_BPartner.Xing
-- 2022-10-25T15:15:56.286Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584815,581614,0,40,291,'Xing',TO_TIMESTAMP('2022-10-25 17:15:56','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Xing',0,0,TO_TIMESTAMP('2022-10-25 17:15:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:15:56.287Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584815 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:15:56.289Z
/* DDL */  select update_Column_Translation_From_AD_Element(581614) 
;

-- 2022-10-25T15:15:57.926Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Xing VARCHAR(255)')
;

-- Column: C_BPartner.Social_media_5
-- 2022-10-25T15:16:16.124Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584816,581615,0,40,291,'Social_media_5',TO_TIMESTAMP('2022-10-25 17:16:16','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Social Media 5',0,0,TO_TIMESTAMP('2022-10-25 17:16:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:16:16.125Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584816 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:16:16.127Z
/* DDL */  select update_Column_Translation_From_AD_Element(581615) 
;

-- 2022-10-25T15:16:17.148Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Social_media_5 VARCHAR(255)')
;

-- Column: C_BPartner.Preferred_import
-- 2022-10-25T15:16:36.444Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584817,581616,0,10,291,'Preferred_import',TO_TIMESTAMP('2022-10-25 17:16:36','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bevorzugter Import',0,0,TO_TIMESTAMP('2022-10-25 17:16:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:16:36.445Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584817 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:16:36.446Z
/* DDL */  select update_Column_Translation_From_AD_Element(581616) 
;

-- 2022-10-25T15:16:44.532Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Preferred_import VARCHAR(255)')
;

-- Column: C_BPartner.MRN_ABN
-- 2022-10-25T15:19:50.376Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584818,581617,0,10,291,'MRN_ABN',TO_TIMESTAMP('2022-10-25 17:19:50','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'MRN/ABN',0,0,TO_TIMESTAMP('2022-10-25 17:19:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:19:50.378Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584818 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:19:50.380Z
/* DDL */  select update_Column_Translation_From_AD_Element(581617) 
;

-- 2022-10-25T15:20:00.977Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN MRN_ABN VARCHAR(255)')
;

-- Column: C_BPartner.ABC
-- 2022-10-25T15:20:21.707Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584819,581618,0,10,291,'ABC',TO_TIMESTAMP('2022-10-25 17:20:21','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ABC',0,0,TO_TIMESTAMP('2022-10-25 17:20:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:20:21.708Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584819 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:20:21.709Z
/* DDL */  select update_Column_Translation_From_AD_Element(581618) 
;

-- Column: C_BPartner.ABC
-- 2022-10-25T15:20:29.118Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-10-25 17:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584819
;

-- 2022-10-25T15:20:31.705Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN ABC VARCHAR(255)')
;

-- Column: C_BPartner.Photo
-- 2022-10-25T15:20:54.734Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584820,581619,0,32,291,'Photo',TO_TIMESTAMP('2022-10-25 17:20:54','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Foto',0,0,TO_TIMESTAMP('2022-10-25 17:20:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:20:54.735Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584820 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:20:54.737Z
/* DDL */  select update_Column_Translation_From_AD_Element(581619) 
;

-- Column: C_BPartner.Email_private
-- 2022-10-25T15:23:35.996Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584821,581620,0,10,291,'Email_private',TO_TIMESTAMP('2022-10-25 17:23:35','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'eMail Privat',0,0,TO_TIMESTAMP('2022-10-25 17:23:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:23:35.997Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584821 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:23:35.999Z
/* DDL */  select update_Column_Translation_From_AD_Element(581620) 
;

-- 2022-10-25T15:23:37.372Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Email_private VARCHAR(255)')
;

-- Column: C_BPartner.Previous_company
-- 2022-10-25T15:23:54.925Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584822,581621,0,10,291,'Previous_company',TO_TIMESTAMP('2022-10-25 17:23:54','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vorherige Arbeitsstelle',0,0,TO_TIMESTAMP('2022-10-25 17:23:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:23:54.926Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584822 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:23:54.927Z
/* DDL */  select update_Column_Translation_From_AD_Element(581621) 
;

-- 2022-10-25T15:23:56.141Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Previous_company VARCHAR(255)')
;

-- Column: C_BPartner.Hobbies_interests
-- 2022-10-25T15:24:12.084Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584823,581622,0,10,291,'Hobbies_interests',TO_TIMESTAMP('2022-10-25 17:24:12','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Hobbies/ Interessen',0,0,TO_TIMESTAMP('2022-10-25 17:24:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:24:12.085Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584823 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:24:12.087Z
/* DDL */  select update_Column_Translation_From_AD_Element(581622) 
;

-- 2022-10-25T15:24:13.496Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Hobbies_interests VARCHAR(255)')
;

-- 2022-10-25T15:28:32.768Z
UPDATE AD_Element SET ColumnName='Photo_ID',Updated=TO_TIMESTAMP('2022-10-25 17:28:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581619
;

-- 2022-10-25T15:28:32.771Z
UPDATE AD_Column SET ColumnName='Photo_ID', Name='Foto', Description=NULL, Help=NULL WHERE AD_Element_ID=581619
;

-- 2022-10-25T15:28:32.772Z
UPDATE AD_Process_Para SET ColumnName='Photo_ID', Name='Foto', Description=NULL, Help=NULL, AD_Element_ID=581619 WHERE UPPER(ColumnName)='PHOTO_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-10-25T15:28:32.773Z
UPDATE AD_Process_Para SET ColumnName='Photo_ID', Name='Foto', Description=NULL, Help=NULL WHERE AD_Element_ID=581619 AND IsCentrallyMaintained='Y'
;

-- 2022-10-25T15:28:50.421Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Photo_ID NUMERIC(10)')
;

-- 2022-10-25T15:28:51.511Z
ALTER TABLE C_BPartner ADD CONSTRAINT Photo_CBPartner FOREIGN KEY (Photo_ID) REFERENCES public.AD_Image DEFERRABLE INITIALLY DEFERRED
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> Rechtsform
-- Column: C_BPartner.Legal_form
-- 2022-10-25T15:41:14.831Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584807,707863,0,220,0,TO_TIMESTAMP('2022-10-25 17:41:14','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Rechtsform',0,410,0,1,1,TO_TIMESTAMP('2022-10-25 17:41:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:41:14.832Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707863 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:41:14.834Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581607) 
;

-- 2022-10-25T15:41:14.841Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707863
;

-- 2022-10-25T15:41:14.843Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707863)
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> Anz. Mitarbeiter
-- Column: C_BPartner.Number_employees
-- 2022-10-25T15:41:35.918Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584808,707864,0,220,0,TO_TIMESTAMP('2022-10-25 17:41:35','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Anz. Mitarbeiter',0,420,0,1,1,TO_TIMESTAMP('2022-10-25 17:41:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:41:35.919Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707864 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:41:35.920Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581608) 
;

-- 2022-10-25T15:41:35.922Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707864
;

-- 2022-10-25T15:41:35.922Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707864)
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> Branche
-- Column: C_BPartner.Industrial_sector
-- 2022-10-25T15:41:52.309Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584809,707865,0,220,0,TO_TIMESTAMP('2022-10-25 17:41:52','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Branche',0,430,0,1,1,TO_TIMESTAMP('2022-10-25 17:41:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:41:52.310Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707865 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:41:52.311Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581609) 
;

-- 2022-10-25T15:41:52.315Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707865
;

-- 2022-10-25T15:41:52.315Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707865)
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> Verbundenes Unternehmen
-- Column: C_BPartner.Affiliate
-- 2022-10-25T15:42:03.909Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584810,707866,0,220,0,TO_TIMESTAMP('2022-10-25 17:42:03','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Verbundenes Unternehmen',0,440,0,1,1,TO_TIMESTAMP('2022-10-25 17:42:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:42:03.910Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707866 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:42:03.911Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581610) 
;

-- 2022-10-25T15:42:03.913Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707866
;

-- 2022-10-25T15:42:03.913Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707866)
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> Facebook
-- Column: C_BPartner.Facebook
-- 2022-10-25T15:42:16.393Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584811,707867,0,220,0,TO_TIMESTAMP('2022-10-25 17:42:16','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Facebook',0,450,0,1,1,TO_TIMESTAMP('2022-10-25 17:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:42:16.394Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707867 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:42:16.395Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581611) 
;

-- 2022-10-25T15:42:16.397Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707867
;

-- 2022-10-25T15:42:16.398Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707867)
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> Twitter
-- Column: C_BPartner.Twitter
-- 2022-10-25T15:42:27.531Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584813,707868,0,220,0,TO_TIMESTAMP('2022-10-25 17:42:27','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Twitter',0,460,0,1,1,TO_TIMESTAMP('2022-10-25 17:42:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:42:27.531Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:42:27.532Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581612) 
;

-- 2022-10-25T15:42:27.535Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707868
;

-- 2022-10-25T15:42:27.535Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707868)
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> LinkedIn
-- Column: C_BPartner.LinkedIn
-- 2022-10-25T15:42:40.153Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584814,707869,0,220,0,TO_TIMESTAMP('2022-10-25 17:42:40','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','LinkedIn',0,470,0,1,1,TO_TIMESTAMP('2022-10-25 17:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:42:40.153Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:42:40.154Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581613) 
;

-- 2022-10-25T15:42:40.158Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707869
;

-- 2022-10-25T15:42:40.158Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707869)
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> Xing
-- Column: C_BPartner.Xing
-- 2022-10-25T15:42:50.419Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584815,707870,0,220,0,TO_TIMESTAMP('2022-10-25 17:42:50','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Xing',0,480,0,1,1,TO_TIMESTAMP('2022-10-25 17:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:42:50.420Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:42:50.421Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581614) 
;

-- 2022-10-25T15:42:50.422Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707870
;

-- 2022-10-25T15:42:50.423Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707870)
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> Social Media 5
-- Column: C_BPartner.Social_media_5
-- 2022-10-25T15:43:04.558Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584816,707871,0,220,0,TO_TIMESTAMP('2022-10-25 17:43:04','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Social Media 5',0,490,0,1,1,TO_TIMESTAMP('2022-10-25 17:43:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:43:04.558Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:43:04.559Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581615) 
;

-- 2022-10-25T15:43:04.561Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707871
;

-- 2022-10-25T15:43:04.562Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707871)
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> Bevorzugter Import
-- Column: C_BPartner.Preferred_import
-- 2022-10-25T15:43:15.922Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584817,707872,0,220,0,TO_TIMESTAMP('2022-10-25 17:43:15','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Bevorzugter Import',0,500,0,1,1,TO_TIMESTAMP('2022-10-25 17:43:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:43:15.923Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:43:15.924Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581616) 
;

-- 2022-10-25T15:43:15.926Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707872
;

-- 2022-10-25T15:43:15.926Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707872)
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> MRN/ABN
-- Column: C_BPartner.MRN_ABN
-- 2022-10-25T15:43:27.069Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584818,707873,0,220,0,TO_TIMESTAMP('2022-10-25 17:43:27','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','MRN/ABN',0,510,0,1,1,TO_TIMESTAMP('2022-10-25 17:43:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:43:27.070Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:43:27.071Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581617) 
;

-- 2022-10-25T15:43:27.073Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707873
;

-- 2022-10-25T15:43:27.073Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707873)
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> ABC
-- Column: C_BPartner.ABC
-- 2022-10-25T15:43:37.603Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584819,707874,0,220,0,TO_TIMESTAMP('2022-10-25 17:43:37','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','ABC',0,520,0,1,1,TO_TIMESTAMP('2022-10-25 17:43:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:43:37.604Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707874 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:43:37.605Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581618) 
;

-- 2022-10-25T15:43:37.606Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707874
;

-- 2022-10-25T15:43:37.606Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707874)
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> Foto
-- Column: C_BPartner.Photo_ID
-- 2022-10-25T15:43:52.697Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584820,707875,0,220,0,TO_TIMESTAMP('2022-10-25 17:43:52','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Foto',0,530,0,1,1,TO_TIMESTAMP('2022-10-25 17:43:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:43:52.697Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:43:52.698Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581619) 
;

-- 2022-10-25T15:43:52.700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707875
;

-- 2022-10-25T15:43:52.700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707875)
;

-- 2022-10-25T16:39:19.244Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707875
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> Foto
-- Column: C_BPartner.Photo_ID
-- 2022-10-25T16:39:19.247Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707875
;

-- 2022-10-25T16:39:19.250Z
DELETE FROM AD_Field WHERE AD_Field_ID=707875
;

-- 2022-10-25T16:39:44.231Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707874
;

-- Field: Geschäftspartner_OLD -> Geschäftspartner -> ABC
-- Column: C_BPartner.ABC
-- 2022-10-25T16:39:44.232Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707874
;

-- 2022-10-25T16:39:44.233Z
DELETE FROM AD_Field WHERE AD_Field_ID=707874
;

-- Column: C_BPartner.Photo_ID
-- 2022-10-25T16:40:41.900Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584820
;

-- 2022-10-25T16:40:41.902Z
DELETE FROM AD_Column WHERE AD_Column_ID=584820
;

-- Column: C_BPartner.Email_private
-- 2022-10-25T16:41:27.656Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584821
;

-- 2022-10-25T16:41:27.657Z
DELETE FROM AD_Column WHERE AD_Column_ID=584821
;

-- Column: C_BPartner.Previous_company
-- 2022-10-25T16:41:39.318Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584822
;

-- 2022-10-25T16:41:39.321Z
DELETE FROM AD_Column WHERE AD_Column_ID=584822
;

-- Column: C_BPartner.Hobbies_interests
-- 2022-10-25T16:41:50.841Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584823
;

-- 2022-10-25T16:41:50.842Z
DELETE FROM AD_Column WHERE AD_Column_ID=584823
;

-- Name: ABC Reference List
-- 2022-10-25T16:42:21.542Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541687,TO_TIMESTAMP('2022-10-25 18:42:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','ABC Reference List',TO_TIMESTAMP('2022-10-25 18:42:21','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-10-25T16:42:21.543Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541687 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ABC Reference List
-- Value: A1
-- Name: A1
-- 2022-10-25T16:42:55.723Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541687,543327,TO_TIMESTAMP('2022-10-25 18:42:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','A1',TO_TIMESTAMP('2022-10-25 18:42:55','YYYY-MM-DD HH24:MI:SS'),100,'A1','A1')
;

-- 2022-10-25T16:42:55.723Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543327 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: ABC Reference List
-- Value: A2
-- Name: A2
-- 2022-10-25T16:43:06.457Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541687,543328,TO_TIMESTAMP('2022-10-25 18:43:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','A2',TO_TIMESTAMP('2022-10-25 18:43:06','YYYY-MM-DD HH24:MI:SS'),100,'A2','A2')
;

-- 2022-10-25T16:43:06.457Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543328 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_BPartner.ABC
-- 2022-10-25T16:43:21.118Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541687, FieldLength=2,Updated=TO_TIMESTAMP('2022-10-25 18:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584819
;

-- 2022-10-25T16:43:27.552Z
INSERT INTO t_alter_column values('c_bpartner','ABC','VARCHAR(2)',null,null)
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.Rechtsform
-- Column: C_BPartner.Legal_form
-- 2022-10-25T16:47:41.294Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707863,0,220,540671,613305,'F',TO_TIMESTAMP('2022-10-25 18:47:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Rechtsform',170,0,0,TO_TIMESTAMP('2022-10-25 18:47:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.Anz. Mitarbeiter
-- Column: C_BPartner.Number_employees
-- 2022-10-25T16:47:53.602Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707864,0,220,540671,613306,'F',TO_TIMESTAMP('2022-10-25 18:47:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Anz. Mitarbeiter',180,0,0,TO_TIMESTAMP('2022-10-25 18:47:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.Branche
-- Column: C_BPartner.Industrial_sector
-- 2022-10-25T16:48:00.694Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707865,0,220,540671,613307,'F',TO_TIMESTAMP('2022-10-25 18:48:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Branche',190,0,0,TO_TIMESTAMP('2022-10-25 18:48:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.Branche
-- Column: C_BPartner.Industrial_sector
-- 2022-10-25T16:48:02.510Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-10-25 18:48:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613307
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.Verbundenes Unternehmen
-- Column: C_BPartner.Affiliate
-- 2022-10-25T16:48:12.912Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707866,0,220,540671,613308,'F',TO_TIMESTAMP('2022-10-25 18:48:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Verbundenes Unternehmen',200,0,0,TO_TIMESTAMP('2022-10-25 18:48:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.Verbundenes Unternehmen
-- Column: C_BPartner.Affiliate
-- 2022-10-25T16:48:14.682Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-10-25 18:48:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613308
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.Verbundenes Unternehmen
-- Column: C_BPartner.Affiliate
-- 2022-10-25T16:48:18.830Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-10-25 18:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613308
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.Verbundenes Unternehmen
-- Column: C_BPartner.Affiliate
-- 2022-10-25T16:48:27.139Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-10-25 18:48:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613308
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.Facebook
-- Column: C_BPartner.Facebook
-- 2022-10-25T16:48:55.722Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707867,0,220,540671,613309,'F',TO_TIMESTAMP('2022-10-25 18:48:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Facebook',210,0,0,TO_TIMESTAMP('2022-10-25 18:48:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.Twitter
-- Column: C_BPartner.Twitter
-- 2022-10-25T16:49:02.935Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707868,0,220,540671,613310,'F',TO_TIMESTAMP('2022-10-25 18:49:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Twitter',220,0,0,TO_TIMESTAMP('2022-10-25 18:49:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.LinkedIn
-- Column: C_BPartner.LinkedIn
-- 2022-10-25T16:49:14.469Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707869,0,220,540671,613311,'F',TO_TIMESTAMP('2022-10-25 18:49:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'LinkedIn',230,0,0,TO_TIMESTAMP('2022-10-25 18:49:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.Xing
-- Column: C_BPartner.Xing
-- 2022-10-25T16:49:22.247Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707870,0,220,540671,613312,'F',TO_TIMESTAMP('2022-10-25 18:49:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Xing',240,0,0,TO_TIMESTAMP('2022-10-25 18:49:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.Social Media 5
-- Column: C_BPartner.Social_media_5
-- 2022-10-25T16:49:33.688Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707871,0,220,540671,613313,'F',TO_TIMESTAMP('2022-10-25 18:49:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Social Media 5',250,0,0,TO_TIMESTAMP('2022-10-25 18:49:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.Bevorzugter Import
-- Column: C_BPartner.Preferred_import
-- 2022-10-25T16:49:45.236Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707872,0,220,540671,613314,'F',TO_TIMESTAMP('2022-10-25 18:49:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Bevorzugter Import',260,0,0,TO_TIMESTAMP('2022-10-25 18:49:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Geschäftspartner.MRN/ABN
-- Column: C_BPartner.MRN_ABN
-- 2022-10-25T16:49:59.396Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707873,0,220,540671,613315,'F',TO_TIMESTAMP('2022-10-25 18:49:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'MRN/ABN',270,0,0,TO_TIMESTAMP('2022-10-25 18:49:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Geschäftspartner_OLD -> Kunde -> ABC
-- Column: C_BPartner.ABC
-- 2022-10-25T16:50:37.434Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584819,707876,0,223,0,TO_TIMESTAMP('2022-10-25 18:50:37','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','N','N','N','N','N','N','ABC',0,350,0,1,1,TO_TIMESTAMP('2022-10-25 18:50:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T16:50:37.435Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707876 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T16:50:37.436Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581618) 
;

-- 2022-10-25T16:50:37.440Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707876
;

-- 2022-10-25T16:50:37.441Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707876)
;

-- UI Element: Geschäftspartner_OLD -> Kunde.ABC
-- Column: C_BPartner.ABC
-- 2022-10-25T16:51:21.704Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707876,0,223,540672,613316,'F',TO_TIMESTAMP('2022-10-25 18:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ABC',330,0,0,TO_TIMESTAMP('2022-10-25 18:51:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: AD_User.Hobbies_interests
-- 2022-10-25T17:06:59.387Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584824,581622,0,10,114,'Hobbies_interests',TO_TIMESTAMP('2022-10-25 19:06:59','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Hobbies/ Interessen',0,0,TO_TIMESTAMP('2022-10-25 19:06:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T17:06:59.388Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584824 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T17:06:59.389Z
/* DDL */  select update_Column_Translation_From_AD_Element(581622) 
;

COMMIT; -- without this commit we sometimes get
-- psql:/opt/metasfresh/dist/sql/20-de.metas.business/5661990_sys_gh_add_sales_fields_to_partner.sql:1347: ERROR:  deadlock detected
-- app_1       |   DETAIL:  Process 292 waits for AccessExclusiveLock on relation 20055 of database 16385; blocked by process 299.
-- app_1       |   Process 299 waits for AccessShareLock on relation 18250 of database 16385; blocked by process 292.

-- 2022-10-25T17:07:01.802Z
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN Hobbies_interests VARCHAR(255)')
;

-- Column: AD_User.Previous_company
-- 2022-10-25T17:07:14.664Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584825,581621,0,10,114,'Previous_company',TO_TIMESTAMP('2022-10-25 19:07:14','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vorherige Arbeitsstelle',0,0,TO_TIMESTAMP('2022-10-25 19:07:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T17:07:14.665Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584825 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T17:07:14.666Z
/* DDL */  select update_Column_Translation_From_AD_Element(581621) 
;

-- Column: AD_User.Email_private
-- 2022-10-25T17:07:27.038Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584826,581620,0,10,114,'Email_private',TO_TIMESTAMP('2022-10-25 19:07:26','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'eMail Privat',0,0,TO_TIMESTAMP('2022-10-25 19:07:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T17:07:27.039Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584826 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T17:07:27.040Z
/* DDL */  select update_Column_Translation_From_AD_Element(581620) 
;

-- Column: AD_User.Photo_ID
-- 2022-10-25T17:07:45.208Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584827,581619,0,32,114,'Photo_ID',TO_TIMESTAMP('2022-10-25 19:07:45','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Foto',0,0,TO_TIMESTAMP('2022-10-25 19:07:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T17:07:45.210Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584827 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T17:07:45.211Z
/* DDL */  select update_Column_Translation_From_AD_Element(581619) 
;

-- 2022-10-25T17:07:48.983Z
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN Photo_ID NUMERIC(10)')
;

-- 2022-10-25T17:07:49.294Z
ALTER TABLE AD_User ADD CONSTRAINT Photo_ADUser FOREIGN KEY (Photo_ID) REFERENCES public.AD_Image DEFERRABLE INITIALLY DEFERRED
;

-- 2022-10-25T17:07:54.057Z
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN Previous_company VARCHAR(255)')
;

-- 2022-10-25T17:08:09.052Z
INSERT INTO t_alter_column values('ad_user','Previous_company','VARCHAR(255)',null,null)
;

-- 2022-10-25T17:08:21.806Z
INSERT INTO t_alter_column values('ad_user','Hobbies_interests','VARCHAR(255)',null,null)
;

-- Field: Geschäftspartner_OLD -> Nutzer / Kontakt -> Foto
-- Column: AD_User.Photo_ID
-- 2022-10-25T17:09:05.388Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584827,707877,0,496,0,TO_TIMESTAMP('2022-10-25 19:09:05','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','N','N','N','N','N','N','Foto',0,320,0,1,1,TO_TIMESTAMP('2022-10-25 19:09:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T17:09:05.389Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707877 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T17:09:05.390Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581619) 
;

-- 2022-10-25T17:09:05.393Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707877
;

-- 2022-10-25T17:09:05.394Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707877)
;

-- Field: Geschäftspartner_OLD -> Nutzer / Kontakt -> eMail Privat
-- Column: AD_User.Email_private
-- 2022-10-25T17:09:17.348Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584826,707878,0,496,0,TO_TIMESTAMP('2022-10-25 19:09:17','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','N','N','N','N','N','N','eMail Privat',0,330,0,1,1,TO_TIMESTAMP('2022-10-25 19:09:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T17:09:17.348Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707878 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T17:09:17.350Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581620) 
;

-- 2022-10-25T17:09:17.351Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707878
;

-- 2022-10-25T17:09:17.352Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707878)
;

-- Field: Geschäftspartner_OLD -> Nutzer / Kontakt -> Vorherige Arbeitsstelle
-- Column: AD_User.Previous_company
-- 2022-10-25T17:09:26.571Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584825,707879,0,496,0,TO_TIMESTAMP('2022-10-25 19:09:26','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','N','N','N','N','N','N','Vorherige Arbeitsstelle',0,340,0,1,1,TO_TIMESTAMP('2022-10-25 19:09:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T17:09:26.572Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707879 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T17:09:26.573Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581621) 
;

-- 2022-10-25T17:09:26.576Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707879
;

-- 2022-10-25T17:09:26.576Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707879)
;

-- Field: Geschäftspartner_OLD -> Nutzer / Kontakt -> Hobbies/ Interessen
-- Column: AD_User.Hobbies_interests
-- 2022-10-25T17:09:36.319Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584824,707880,0,496,0,TO_TIMESTAMP('2022-10-25 19:09:36','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','N','N','N','N','N','N','Hobbies/ Interessen',0,350,0,1,1,TO_TIMESTAMP('2022-10-25 19:09:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T17:09:36.319Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707880 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T17:09:36.320Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581622) 
;

-- 2022-10-25T17:09:36.322Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707880
;

-- 2022-10-25T17:09:36.322Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707880)
;

-- UI Element: Geschäftspartner_OLD -> Nutzer / Kontakt.Foto
-- Column: AD_User.Photo_ID
-- 2022-10-25T17:11:55.211Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707877,0,496,540578,613317,'F',TO_TIMESTAMP('2022-10-25 19:11:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Foto',260,0,0,TO_TIMESTAMP('2022-10-25 19:11:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Nutzer / Kontakt.eMail Privat
-- Column: AD_User.Email_private
-- 2022-10-25T17:12:09.308Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707878,0,496,540578,613318,'F',TO_TIMESTAMP('2022-10-25 19:12:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'eMail Privat',270,0,0,TO_TIMESTAMP('2022-10-25 19:12:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Nutzer / Kontakt.Vorherige Arbeitsstelle
-- Column: AD_User.Previous_company
-- 2022-10-25T17:12:17.307Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707879,0,496,540578,613319,'F',TO_TIMESTAMP('2022-10-25 19:12:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vorherige Arbeitsstelle',280,0,0,TO_TIMESTAMP('2022-10-25 19:12:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Nutzer / Kontakt.Hobbies/ Interessen
-- Column: AD_User.Hobbies_interests
-- 2022-10-25T17:12:25.740Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707880,0,496,540578,613320,'F',TO_TIMESTAMP('2022-10-25 19:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Hobbies/ Interessen',290,0,0,TO_TIMESTAMP('2022-10-25 19:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T17:15:53.170Z
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN Email_private VARCHAR(255)')
;

ALTER TABLE c_bpartner
    DROP COLUMN photo_id
;

ALTER TABLE c_bpartner
    DROP COLUMN hobbies_interests
;

ALTER TABLE c_bpartner
    DROP COLUMN previous_company
;

ALTER TABLE c_bpartner
    DROP COLUMN email_private
;
