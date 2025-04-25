-- Run mode: SWING_CLIENT

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account2_ID
-- 2025-04-24T20:21:05.459Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,148,0,585341,542946,30,540666,'Account2_ID',TO_TIMESTAMP('2025-04-24 23:21:05.187','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','@Account_ID/-1@>0','U',0,'Das verwendete (Standard-) Konto','Y','N','Y','N','N','N','Konto',50,TO_TIMESTAMP('2025-04-24 23:21:05.187','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:21:05.486Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542946 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-04-24T20:21:05.530Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(148)
;

-- 2025-04-24T20:22:33.567Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583592,0,'Account2_ID',TO_TIMESTAMP('2025-04-24 23:22:33.427','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','D','Das verwendete (Standard-) Konto','Y','Konto','Konto',TO_TIMESTAMP('2025-04-24 23:22:33.427','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:22:33.576Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583592 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Account2_ID
-- 2025-04-24T20:22:52.335Z
UPDATE AD_Element_Trl SET Description='Account used', Help='The (natural) account used', Name='Account', PrintName='Account',Updated=TO_TIMESTAMP('2025-04-24 23:22:52.335','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583592 AND AD_Language='en_US'
;

-- 2025-04-24T20:22:52.338Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583592,'en_US')
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account2_ID
-- 2025-04-24T20:23:10.207Z
UPDATE AD_Process_Para SET AD_Element_ID=583592,Updated=TO_TIMESTAMP('2025-04-24 23:23:10.207','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542946
;

-- 2025-04-24T20:23:10.210Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583592)
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account2_ID
-- 2025-04-24T20:26:55.984Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=331,Updated=TO_TIMESTAMP('2025-04-24 23:26:55.984','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542946
;

-- Element: Account2_ID
-- 2025-04-24T20:27:31.845Z
UPDATE AD_Element_Trl SET Name='Konto 2', PrintName='Konto 2',Updated=TO_TIMESTAMP('2025-04-24 23:27:31.845','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583592 AND AD_Language='de_CH'
;

-- 2025-04-24T20:27:31.848Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583592,'de_CH')
;

-- Element: Account2_ID
-- 2025-04-24T20:27:34.191Z
UPDATE AD_Element_Trl SET Name='Konto 2', PrintName='Konto 2',Updated=TO_TIMESTAMP('2025-04-24 23:27:34.191','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583592 AND AD_Language='de_DE'
;

-- 2025-04-24T20:27:34.194Z
UPDATE AD_Element SET Name='Konto 2', PrintName='Konto 2' WHERE AD_Element_ID=583592
;

-- 2025-04-24T20:27:34.687Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583592,'de_DE')
;

-- 2025-04-24T20:27:34.696Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583592,'de_DE')
;

-- Element: Account2_ID
-- 2025-04-24T20:27:37.028Z
UPDATE AD_Element_Trl SET Name='Account 2', PrintName='Account 2',Updated=TO_TIMESTAMP('2025-04-24 23:27:37.028','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583592 AND AD_Language='en_US'
;

-- 2025-04-24T20:27:37.029Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583592,'en_US')
;

-- Run mode: SWING_CLIENT

-- 2025-04-24T20:29:24.522Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583593,0,'Account3_ID',TO_TIMESTAMP('2025-04-24 23:29:24.39','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','D','Das verwendete (Standard-) Konto','Y','Konto 3','Konto 3',TO_TIMESTAMP('2025-04-24 23:29:24.39','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:29:24.525Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583593 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Account3_ID
-- 2025-04-24T20:29:43.665Z
UPDATE AD_Element_Trl SET Description='Account used', Help='The (natural) account used', Name='Account 3', PrintName='Account 3',Updated=TO_TIMESTAMP('2025-04-24 23:29:43.665','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583593 AND AD_Language='en_US'
;

-- 2025-04-24T20:29:43.666Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583593,'en_US')
;

-- 2025-04-24T20:30:06.069Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583594,0,'Account4_ID',TO_TIMESTAMP('2025-04-24 23:30:05.935','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','D','Das verwendete (Standard-) Konto','Y','Konto 4','Konto 4',TO_TIMESTAMP('2025-04-24 23:30:05.935','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:30:06.071Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583594 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Account4_ID
-- 2025-04-24T20:30:21.938Z
UPDATE AD_Element_Trl SET Description='Account used', Help='The (natural) account used', Name='Account 4', PrintName='Account 4',Updated=TO_TIMESTAMP('2025-04-24 23:30:21.938','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583594 AND AD_Language='en_US'
;

-- 2025-04-24T20:30:21.940Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583594,'en_US')
;

-- 2025-04-24T20:30:46.972Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583595,0,'Account5_ID',TO_TIMESTAMP('2025-04-24 23:30:46.833','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','D','Das verwendete (Standard-) Konto','Y','Konto 5','Konto 5',TO_TIMESTAMP('2025-04-24 23:30:46.833','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:30:46.973Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583595 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Account5_ID
-- 2025-04-24T20:31:02.357Z
UPDATE AD_Element_Trl SET Description='Account used', Help='The (natural) account used', Name='Account 5', PrintName='Account 5',Updated=TO_TIMESTAMP('2025-04-24 23:31:02.357','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583595 AND AD_Language='en_US'
;

-- 2025-04-24T20:31:02.359Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583595,'en_US')
;

-- 2025-04-24T20:31:21.200Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583596,0,'Account6_ID',TO_TIMESTAMP('2025-04-24 23:31:21.052','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','D','Das verwendete (Standard-) Konto','Y','Konto 6','Konto 6',TO_TIMESTAMP('2025-04-24 23:31:21.052','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:31:21.202Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583596 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Account6_ID
-- 2025-04-24T20:31:41.420Z
UPDATE AD_Element_Trl SET Description='Account used', Help='The (natural) account used', Name='Account 6', PrintName='Account 6',Updated=TO_TIMESTAMP('2025-04-24 23:31:41.42','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583596 AND AD_Language='en_US'
;

-- 2025-04-24T20:31:41.421Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583596,'en_US')
;

-- 2025-04-24T20:32:03.477Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583597,0,'Account7_ID',TO_TIMESTAMP('2025-04-24 23:32:03.333','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','D','Das verwendete (Standard-) Konto','Y','Konto 7','Konto 7',TO_TIMESTAMP('2025-04-24 23:32:03.333','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:32:03.479Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583597 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Account7_ID
-- 2025-04-24T20:32:19.697Z
UPDATE AD_Element_Trl SET Description='Account used', Help='The (natural) account used', Name='Account 7', PrintName='Account 7',Updated=TO_TIMESTAMP('2025-04-24 23:32:19.697','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583597 AND AD_Language='en_US'
;

-- 2025-04-24T20:32:19.698Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583597,'en_US')
;

-- 2025-04-24T20:32:41.452Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583598,0,'Account8_ID',TO_TIMESTAMP('2025-04-24 23:32:41.307','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','D','Das verwendete (Standard-) Konto','Y','Konto 8','Konto 8',TO_TIMESTAMP('2025-04-24 23:32:41.307','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:32:41.454Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583598 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Account8_ID
-- 2025-04-24T20:32:55.061Z
UPDATE AD_Element_Trl SET Description='Account used', Help='The (natural) account used', Name='Account 8', PrintName='Account 8',Updated=TO_TIMESTAMP('2025-04-24 23:32:55.061','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583598 AND AD_Language='de_DE'
;

-- 2025-04-24T20:32:55.062Z
UPDATE AD_Element SET Description='Account used', Help='The (natural) account used', Name='Account 8', PrintName='Account 8' WHERE AD_Element_ID=583598
;

-- 2025-04-24T20:32:55.734Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583598,'de_DE')
;

-- 2025-04-24T20:32:55.735Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583598,'de_DE')
;

-- Run mode: SWING_CLIENT

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account2_ID
-- 2025-04-24T20:33:20.252Z
UPDATE AD_Process_Para SET SeqNo=21,Updated=TO_TIMESTAMP('2025-04-24 23:33:20.252','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542946
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account3_ID
-- 2025-04-24T20:34:38.014Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583593,0,585341,542947,30,331,540666,'Account3_ID',TO_TIMESTAMP('2025-04-24 23:34:37.89','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','@Account2_ID/-1@>0','U',0,'Das verwendete (Standard-) Konto','Y','N','Y','N','N','N','Konto 3',22,TO_TIMESTAMP('2025-04-24 23:34:37.89','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:34:38.016Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542947 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-04-24T20:34:38.018Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583593)
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account2_ID
-- 2025-04-24T20:37:53.315Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2025-04-24 23:37:53.315','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542946
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account_ID
-- 2025-04-24T20:39:05.933Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=NULL, ColumnName='Account_ID',Updated=TO_TIMESTAMP('2025-04-24 23:39:05.933','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542946
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account_ID
-- 2025-04-24T20:46:19.685Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=182, AD_Val_Rule_ID=540666,Updated=TO_TIMESTAMP('2025-04-24 23:46:19.685','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542946
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account2_ID
-- 2025-04-24T20:46:42.427Z
UPDATE AD_Process_Para SET ColumnName='Account2_ID',Updated=TO_TIMESTAMP('2025-04-24 23:46:42.427','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542946
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account3_ID
-- 2025-04-24T20:51:18.399Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=182,Updated=TO_TIMESTAMP('2025-04-24 23:51:18.399','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542947
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account4_ID
-- 2025-04-24T20:52:14.965Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583594,0,585341,542948,30,182,540666,'Account4_ID',TO_TIMESTAMP('2025-04-24 23:52:14.806','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','@Account3_ID/-1@>0','U',0,'Das verwendete (Standard-) Konto','Y','N','Y','N','N','N','Konto 4',23,TO_TIMESTAMP('2025-04-24 23:52:14.806','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:52:14.968Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542948 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-04-24T20:52:14.971Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583594)
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account5_ID
-- 2025-04-24T20:53:37.725Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583595,0,585341,542949,30,182,540666,'Account5_ID',TO_TIMESTAMP('2025-04-24 23:53:37.598','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','@Account4_ID/-1@>0','U',0,'Das verwendete (Standard-) Konto','Y','N','Y','N','N','N','Konto 5',24,TO_TIMESTAMP('2025-04-24 23:53:37.598','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:53:37.726Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542949 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-04-24T20:53:37.729Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583595)
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account6_ID
-- 2025-04-24T20:54:27.028Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583596,0,585341,542950,30,182,540666,'Account6_ID',TO_TIMESTAMP('2025-04-24 23:54:26.902','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','@Account5_ID/-1@>0','U',0,'Das verwendete (Standard-) Konto','Y','N','Y','N','N','N','Konto 6',25,TO_TIMESTAMP('2025-04-24 23:54:26.902','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:54:27.028Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542950 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-04-24T20:54:27.029Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583596)
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account_ID
-- 2025-04-24T20:55:43.478Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=182,Updated=TO_TIMESTAMP('2025-04-24 23:55:43.478','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542758
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account7_ID
-- 2025-04-24T20:56:48.320Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583597,0,585341,542951,30,182,540666,'Account7_ID',TO_TIMESTAMP('2025-04-24 23:56:48.196','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','@Account6_ID/-1@>0','U',0,'Das verwendete (Standard-) Konto','Y','N','Y','N','N','N','Konto 7',26,TO_TIMESTAMP('2025-04-24 23:56:48.196','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:56:48.322Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542951 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-04-24T20:56:48.324Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583597)
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account8_ID
-- 2025-04-24T20:57:45.115Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583598,0,585341,542952,30,182,540666,'Account8_ID',TO_TIMESTAMP('2025-04-24 23:57:44.977','YYYY-MM-DD HH24:MI:SS.US'),100,'Account used','@Account7_ID/-1@>0','U',0,'The (natural) account used','Y','N','Y','N','N','N','Account 8',50,TO_TIMESTAMP('2025-04-24 23:57:44.977','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-24T20:57:45.117Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542952 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-04-24T20:57:45.119Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583598)
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account8_ID
-- 2025-04-24T20:57:55.698Z
UPDATE AD_Process_Para SET SeqNo=27,Updated=TO_TIMESTAMP('2025-04-24 23:57:55.698','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542952
;

-- Element: Account8_ID
-- 2025-04-24T20:58:56.771Z
UPDATE AD_Element_Trl SET Name='Konto 8', PrintName='Konto 8',Updated=TO_TIMESTAMP('2025-04-24 23:58:56.771','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583598 AND AD_Language='de_DE'
;

-- 2025-04-24T20:58:56.772Z
UPDATE AD_Element SET Name='Konto 8', PrintName='Konto 8' WHERE AD_Element_ID=583598
;

-- 2025-04-24T20:58:57.365Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583598,'de_DE')
;

-- 2025-04-24T20:58:57.367Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583598,'de_DE')
;

-- Element: Account8_ID
-- 2025-04-24T20:59:01.217Z
UPDATE AD_Element_Trl SET Description='Verwendetes Konto',Updated=TO_TIMESTAMP('2025-04-24 23:59:01.217','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583598 AND AD_Language='de_DE'
;

-- 2025-04-24T20:59:01.217Z
UPDATE AD_Element SET Description='Verwendetes Konto' WHERE AD_Element_ID=583598
;

-- 2025-04-24T20:59:01.656Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583598,'de_DE')
;

-- 2025-04-24T20:59:01.657Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583598,'de_DE')
;

-- Element: Account8_ID
-- 2025-04-24T20:59:04.659Z
UPDATE AD_Element_Trl SET Help='Das verwendete (Standard-) Konto',Updated=TO_TIMESTAMP('2025-04-24 23:59:04.659','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583598 AND AD_Language='de_DE'
;

-- 2025-04-24T20:59:04.660Z
UPDATE AD_Element SET Help='Das verwendete (Standard-) Konto' WHERE AD_Element_ID=583598
;

-- 2025-04-24T20:59:05.128Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583598,'de_DE')
;

-- 2025-04-24T20:59:05.129Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583598,'de_DE')
;

-- Element: Account8_ID
-- 2025-04-24T20:59:32.261Z
UPDATE AD_Element_Trl SET Description='Account used', Help='The (natural) account used', Name='Account 8', PrintName='Account 8',Updated=TO_TIMESTAMP('2025-04-24 23:59:32.261','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583598 AND AD_Language='en_US'
;

-- 2025-04-24T20:59:32.263Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583598,'en_US')
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account_ID
-- 2025-04-24T21:06:10.521Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2025-04-25 00:06:10.521','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542758
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account_ID
-- 2025-04-24T21:11:16.055Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=182,Updated=TO_TIMESTAMP('2025-04-25 00:11:16.055','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542758
;
