-- 2019-03-01T10:14:03.631
-- #298 changing anz. stellen
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576164,0,'IsInsertOnly',TO_TIMESTAMP('2019-03-01 10:14:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IsInsertOnly','IsInsertOnly',TO_TIMESTAMP('2019-03-01 10:14:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-01T10:14:03.631
-- #298 changing anz. stellen
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576164 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-01T10:14:55.368
-- #298 changing anz. stellen
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,576164,0,194,541377,20,'IsInsertOnly',TO_TIMESTAMP('2019-03-01 10:14:55','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','N','N','IsInsertOnly',40,TO_TIMESTAMP('2019-03-01 10:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-01T10:14:55.378
-- #298 changing anz. stellen
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541377 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;



-- 2019-03-01T11:36:06.188
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET Name='Nur Hinzufügen', PrintName='Nur Hinzufügen',Updated=TO_TIMESTAMP('2019-03-01 11:36:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576164 AND AD_Language='de_DE'
;

-- 2019-03-01T11:36:06.294
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576164,'de_DE') 
;

-- 2019-03-01T11:36:06.321
-- #298 changing anz. stellen
/* DDL */  select update_ad_element_on_ad_element_trl_update(576164,'de_DE') 
;

-- 2019-03-01T11:36:06.327
-- #298 changing anz. stellen
UPDATE AD_Column SET ColumnName='IsInsertOnly', Name='Nur Hinzufügen', Description=NULL, Help=NULL WHERE AD_Element_ID=576164
;

-- 2019-03-01T11:36:06.329
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='IsInsertOnly', Name='Nur Hinzufügen', Description=NULL, Help=NULL, AD_Element_ID=576164 WHERE UPPER(ColumnName)='ISINSERTONLY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-01T11:36:06.331
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='IsInsertOnly', Name='Nur Hinzufügen', Description=NULL, Help=NULL WHERE AD_Element_ID=576164 AND IsCentrallyMaintained='Y'
;

-- 2019-03-01T11:36:06.333
-- #298 changing anz. stellen
UPDATE AD_Field SET Name='Nur Hinzufügen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576164) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576164)
;

-- 2019-03-01T11:36:06.381
-- #298 changing anz. stellen
UPDATE AD_PrintFormatItem pi SET PrintName='Nur Hinzufügen', Name='Nur Hinzufügen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576164)
;

-- 2019-03-01T11:36:06.383
-- #298 changing anz. stellen
UPDATE AD_Tab SET Name='Nur Hinzufügen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576164
;

-- 2019-03-01T11:36:06.385
-- #298 changing anz. stellen
UPDATE AD_WINDOW SET Name='Nur Hinzufügen', Description=NULL, Help=NULL WHERE AD_Element_ID = 576164
;

-- 2019-03-01T11:36:06.387
-- #298 changing anz. stellen
UPDATE AD_Menu SET   Name = 'Nur Hinzufügen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576164
;

-- 2019-03-01T11:36:25.315
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET Description='Dieser Prozess fügt neue Einträge hinzu. Schon vorhandene Einträge werden nicht aktualisiert.',Updated=TO_TIMESTAMP('2019-03-01 11:36:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576164 AND AD_Language='de_DE'
;

-- 2019-03-01T11:36:25.316
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576164,'de_DE') 
;

-- 2019-03-01T11:36:25.324
-- #298 changing anz. stellen
/* DDL */  select update_ad_element_on_ad_element_trl_update(576164,'de_DE') 
;

-- 2019-03-01T11:36:25.326
-- #298 changing anz. stellen
UPDATE AD_Column SET ColumnName='IsInsertOnly', Name='Nur Hinzufügen', Description='Dieser Prozess fügt neue Einträge hinzu. Schon vorhandene Einträge werden nicht aktualisiert.', Help=NULL WHERE AD_Element_ID=576164
;

-- 2019-03-01T11:36:25.328
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='IsInsertOnly', Name='Nur Hinzufügen', Description='Dieser Prozess fügt neue Einträge hinzu. Schon vorhandene Einträge werden nicht aktualisiert.', Help=NULL, AD_Element_ID=576164 WHERE UPPER(ColumnName)='ISINSERTONLY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-01T11:36:25.331
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='IsInsertOnly', Name='Nur Hinzufügen', Description='Dieser Prozess fügt neue Einträge hinzu. Schon vorhandene Einträge werden nicht aktualisiert.', Help=NULL WHERE AD_Element_ID=576164 AND IsCentrallyMaintained='Y'
;

-- 2019-03-01T11:36:25.342
-- #298 changing anz. stellen
UPDATE AD_Field SET Name='Nur Hinzufügen', Description='Dieser Prozess fügt neue Einträge hinzu. Schon vorhandene Einträge werden nicht aktualisiert.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576164) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576164)
;

-- 2019-03-01T11:36:25.360
-- #298 changing anz. stellen
UPDATE AD_Tab SET Name='Nur Hinzufügen', Description='Dieser Prozess fügt neue Einträge hinzu. Schon vorhandene Einträge werden nicht aktualisiert.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576164
;

-- 2019-03-01T11:36:25.363
-- #298 changing anz. stellen
UPDATE AD_WINDOW SET Name='Nur Hinzufügen', Description='Dieser Prozess fügt neue Einträge hinzu. Schon vorhandene Einträge werden nicht aktualisiert.', Help=NULL WHERE AD_Element_ID = 576164
;

-- 2019-03-01T11:36:25.369
-- #298 changing anz. stellen
UPDATE AD_Menu SET   Name = 'Nur Hinzufügen', Description = 'Dieser Prozess fügt neue Einträge hinzu. Schon vorhandene Einträge werden nicht aktualisiert.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576164
;



-- 2019-03-01T11:37:27.376
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-01 11:37:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576164 AND AD_Language='de_DE'
;

-- 2019-03-01T11:37:27.380
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576164,'de_DE') 
;

-- 2019-03-01T11:37:27.403
-- #298 changing anz. stellen
/* DDL */  select update_ad_element_on_ad_element_trl_update(576164,'de_DE') 
;

-- 2019-03-01T11:37:47.716
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET Description='This process only inserts new entries. It does not update existing ones.', Name='Insert Only', PrintName='Insert Only',Updated=TO_TIMESTAMP('2019-03-01 11:37:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576164 AND AD_Language='en_US'
;

-- 2019-03-01T11:37:47.719
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576164,'en_US') 
;




