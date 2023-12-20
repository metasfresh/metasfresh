-- Value: role_access_add_menu_recursively
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2023-05-31T19:52:06.250392300Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('6',0,0,585270,'Y','de.metas.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2023-05-31 20:52:05.87','YYYY-MM-DD HH24:MI:SS.US'),100,'Zugang zum Menü rekursiv hinzufügen','D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Zugang zum Menü rekursiv hinzufügen','json','N','N','xls','SELECT role_access_add_menu_recursively(@AD_Role_ID@,@AD_Menu_ID@,''@IsReadWrite@'');','SQL',TO_TIMESTAMP('2023-05-31 20:52:05.87','YYYY-MM-DD HH24:MI:SS.US'),100,'role_access_add_menu_recursively')
;

-- 2023-05-31T19:52:06.255827700Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585270 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: role_access_add_menu_recursively(de.metas.process.ExecuteUpdateSQL)
-- 2023-05-31T19:52:39.118304Z
UPDATE AD_Process_Trl SET Description='Add access to menu recursively', IsTranslated='Y', Name='Add access to menu recursively',Updated=TO_TIMESTAMP('2023-05-31 20:52:39.118','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585270
;

-- Process: role_access_add_menu_recursively(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: AD_Role_ID
-- 2023-05-31T19:55:17.618519800Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,123,0,585270,542634,22,'AD_Role_ID',TO_TIMESTAMP('2023-05-31 20:55:17.329','YYYY-MM-DD HH24:MI:SS.US'),100,'@AD_Role_ID@','Responsibility Role','U',0,'The Role determines security and access a user who has this Role will have in the System.','Y','N','Y','N','Y','N','Rolle',10,TO_TIMESTAMP('2023-05-31 20:55:17.329','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-31T19:55:17.621119600Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542634 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-05-31T19:55:17.643983Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(123) 
;

-- Process: role_access_add_menu_recursively(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: AD_Menu_ID
-- 2023-05-31T19:56:53.979088300Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,110,0,585270,542635,30,540722,'AD_Menu_ID',TO_TIMESTAMP('2023-05-31 20:56:53.714','YYYY-MM-DD HH24:MI:SS.US'),100,' Bezeichnet ein Menü','U',0,'"Menü" bezeichnet ein einzelnes Menü. Menüs kontrollieren die Anzeige der Oberflächen, auf die ein Nutzer Zugriff hat.','Y','N','Y','N','Y','N','Menü',20,TO_TIMESTAMP('2023-05-31 20:56:53.714','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-31T19:56:53.980680Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542635 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-05-31T19:56:53.982265100Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(110) 
;

-- Process: role_access_add_menu_recursively(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: IsReadWrite
-- 2023-05-31T19:57:23.724141900Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,406,0,585270,542636,20,'IsReadWrite',TO_TIMESTAMP('2023-05-31 20:57:23.096','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Feld / Eintrag / Bereich kann gelesen und verändert werden','U',0,'"Lesen und Schreiben" zeigt an, dass hier gelesen und aktualisiert werden kann.','Y','N','Y','N','Y','N','Lesen und Schreiben',30,TO_TIMESTAMP('2023-05-31 20:57:23.096','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-31T19:57:23.725757300Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542636 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-05-31T19:57:23.727355900Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(406) 
;

-- Process: role_access_add_menu_recursively(de.metas.process.ExecuteUpdateSQL)
-- Table: AD_Role
-- EntityType: D
-- 2023-05-31T19:58:08.843538100Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585270,156,541385,TO_TIMESTAMP('2023-05-31 20:58:08.586','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2023-05-31 20:58:08.586','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Process: role_access_add_menu_recursively(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: AD_Menu_ID
-- 2023-05-31T19:59:53.981938800Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2023-05-31 20:59:53.981','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542635
;

-- Element: IsReadWrite
-- 2023-05-31T20:01:09.513708900Z
UPDATE AD_Element_Trl SET Name='ReadWrite',Updated=TO_TIMESTAMP('2023-05-31 21:01:09.513','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=406 AND AD_Language='en_US'
;

-- 2023-05-31T20:01:09.523521400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(406,'en_US') 
;

-- Process: role_access_add_menu_recursively(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: AD_Role_ID
-- 2023-05-31T20:01:44.901052500Z
UPDATE AD_Process_Para SET DisplayLogic='1=2',Updated=TO_TIMESTAMP('2023-05-31 21:01:44.901','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542634
;

-- Element: IsReadWrite
-- 2023-05-31T20:02:14.050703800Z
UPDATE AD_Element_Trl SET Name='Read Write',Updated=TO_TIMESTAMP('2023-05-31 21:02:14.05','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=406 AND AD_Language='en_US'
;

-- 2023-05-31T20:02:14.052316200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(406,'en_US') 
;

