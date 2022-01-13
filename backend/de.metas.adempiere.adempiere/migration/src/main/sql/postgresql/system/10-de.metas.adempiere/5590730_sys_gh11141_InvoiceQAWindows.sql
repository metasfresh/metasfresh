-- 2021-05-24T09:55:38.992Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579232,0,TO_TIMESTAMP('2021-05-24 12:55:37','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Verification Set','D','Y','Invoice Verification Set','Invoice Verification Set',TO_TIMESTAMP('2021-05-24 12:55:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T09:55:39.537Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579232 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T09:56:10.928Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', IsTranslated='Y', Name='Rechnung-Überprüfungssatz', PrintName='Rechnung-Überprüfungssatz',Updated=TO_TIMESTAMP('2021-05-24 12:56:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579232 AND AD_Language='de_CH'
;

-- 2021-05-24T09:56:11.054Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579232,'de_CH')
;

-- 2021-05-24T09:56:28.727Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', IsTranslated='Y', Name='Rechnung-Überprüfungssatz', PrintName='Rechnung-Überprüfungssatz',Updated=TO_TIMESTAMP('2021-05-24 12:56:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579232 AND AD_Language='de_DE'
;

-- 2021-05-24T09:56:28.769Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579232,'de_DE')
;

-- 2021-05-24T09:56:28.880Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579232,'de_DE')
;

-- 2021-05-24T09:56:28.922Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help=NULL WHERE AD_Element_ID=579232
;

-- 2021-05-24T09:56:28.960Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help=NULL WHERE AD_Element_ID=579232 AND IsCentrallyMaintained='Y'
;

-- 2021-05-24T09:56:28.997Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579232) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579232)
;

-- 2021-05-24T09:56:29.083Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung-Überprüfungssatz', Name='Rechnung-Überprüfungssatz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579232)
;

-- 2021-05-24T09:56:29.128Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579232
;

-- 2021-05-24T09:56:29.168Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help=NULL WHERE AD_Element_ID = 579232
;

-- 2021-05-24T09:56:29.207Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungssatz', Description = 'Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579232
;

-- 2021-05-24T09:56:43.350Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='A verification set contains a selection of existing invoice lines to be verified against possibly altered master data or business logic.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 12:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579232 AND AD_Language='en_US'
;

-- 2021-05-24T09:56:43.392Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579232,'en_US')
;

-- 2021-05-24T09:57:05.397Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Name='Rechnung-Überprüfungssatz', PrintName='Rechnung-Überprüfungssatz',Updated=TO_TIMESTAMP('2021-05-24 12:57:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579232 AND AD_Language='nl_NL'
;

-- 2021-05-24T09:57:05.437Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579232,'nl_NL')
;

-- 2021-05-24T09:58:35.700Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541662,'N',TO_TIMESTAMP('2021-05-24 12:58:33','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Invoice Verification Set','NP','L','C_Invoice_Verification_Set','DTI',TO_TIMESTAMP('2021-05-24 12:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T09:58:35.779Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541662 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-05-24T09:58:36.244Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555405,TO_TIMESTAMP('2021-05-24 12:58:35','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Invoice_Verification_Set',1,'Y','N','Y','Y','C_Invoice_Verification_Set','N',1000000,TO_TIMESTAMP('2021-05-24 12:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T09:58:36.415Z
-- URL zum Konzept
CREATE SEQUENCE C_INVOICE_VERIFICATION_SET_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-05-24T09:59:18.391Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574033,102,0,19,541662,'AD_Client_ID',TO_TIMESTAMP('2021-05-24 12:59:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-05-24 12:59:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T09:59:18.640Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574033 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T09:59:18.720Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- 2021-05-24T09:59:21.037Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574034,113,0,30,541662,'AD_Org_ID',TO_TIMESTAMP('2021-05-24 12:59:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Organisation',0,TO_TIMESTAMP('2021-05-24 12:59:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T09:59:21.234Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574034 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T09:59:21.319Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- 2021-05-24T09:59:23.814Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574035,245,0,16,541662,'Created',TO_TIMESTAMP('2021-05-24 12:59:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-05-24 12:59:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T09:59:24.005Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574035 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T09:59:24.083Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- 2021-05-24T09:59:26.378Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574036,246,0,18,110,541662,'CreatedBy',TO_TIMESTAMP('2021-05-24 12:59:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-05-24 12:59:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T09:59:26.588Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574036 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T09:59:26.662Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- 2021-05-24T09:59:28.783Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574037,348,0,20,541662,'IsActive',TO_TIMESTAMP('2021-05-24 12:59:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-05-24 12:59:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T09:59:29.001Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574037 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T09:59:29.080Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- 2021-05-24T09:59:31.285Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574038,607,0,16,541662,'Updated',TO_TIMESTAMP('2021-05-24 12:59:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-05-24 12:59:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T09:59:31.490Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574038 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T09:59:31.571Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- 2021-05-24T09:59:34.017Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574039,608,0,18,110,541662,'UpdatedBy',TO_TIMESTAMP('2021-05-24 12:59:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-05-24 12:59:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T09:59:34.211Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574039 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T09:59:34.288Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2021-05-24T09:59:36.284Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579233,0,'C_Invoice_Verification_Set_ID',TO_TIMESTAMP('2021-05-24 12:59:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice Verification Set','Invoice Verification Set',TO_TIMESTAMP('2021-05-24 12:59:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T09:59:36.480Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579233 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T09:59:38.085Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574040,579233,0,13,541662,'C_Invoice_Verification_Set_ID',TO_TIMESTAMP('2021-05-24 12:59:35','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','Invoice Verification Set',0,TO_TIMESTAMP('2021-05-24 12:59:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T09:59:38.172Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574040 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T09:59:38.251Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579233)
;

-- 2021-05-24T10:02:17.464Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574041,469,0,10,541662,'Name',TO_TIMESTAMP('2021-05-24 13:02:16','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,60,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2021-05-24 13:02:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T10:02:17.717Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574041 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T10:02:17.798Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(469)
;

-- 2021-05-24T10:03:00.765Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574042,275,0,36,541662,'Description',TO_TIMESTAMP('2021-05-24 13:03:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2021-05-24 13:03:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T10:03:00.963Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574042 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T10:03:01.050Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;


-- 2021-05-24T10:08:50.048Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,579233,0,541145,TO_TIMESTAMP('2021-05-24 13:08:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Invoice Verification Set','N',TO_TIMESTAMP('2021-05-24 13:08:38','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2021-05-24T10:08:50.242Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541145 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2021-05-24T10:08:50.321Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(579233)
;

-- 2021-05-24T10:08:50.394Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541145
;

-- 2021-05-24T10:08:50.445Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541145)
;

-- 2021-05-24T10:10:09.408Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- 2021-05-24T10:14:43.869Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579234,0,TO_TIMESTAMP('2021-05-24 13:14:43','YYYY-MM-DD HH24:MI:SS'),100,'D','A verification set contains a selection of existing invoice lines to be verified against possibly altered master data or business logic.','Y','Invoice Verification Set','Invoice Verification Set',TO_TIMESTAMP('2021-05-24 13:14:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:14:43.946Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579234 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T10:15:03.447Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungssatz', PrintName='Rechnung-Überprüfungssatz',Updated=TO_TIMESTAMP('2021-05-24 13:15:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='de_CH'
;

-- 2021-05-24T10:15:03.485Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'de_CH')
;

-- 2021-05-24T10:15:08.434Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungssatz', PrintName='Rechnung-Überprüfungssatz',Updated=TO_TIMESTAMP('2021-05-24 13:15:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='de_DE'
;

-- 2021-05-24T10:15:08.475Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'de_DE')
;

-- 2021-05-24T10:15:08.585Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579234,'de_DE')
;

-- 2021-05-24T10:15:08.624Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Rechnung-Überprüfungssatz', Description=NULL, Help='A verification set contains a selection of existing invoice lines to be verified against possibly altered master data or business logic.' WHERE AD_Element_ID=579234
;

-- 2021-05-24T10:15:08.662Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rechnung-Überprüfungssatz', Description=NULL, Help='A verification set contains a selection of existing invoice lines to be verified against possibly altered master data or business logic.' WHERE AD_Element_ID=579234 AND IsCentrallyMaintained='Y'
;

-- 2021-05-24T10:15:08.700Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-Überprüfungssatz', Description=NULL, Help='A verification set contains a selection of existing invoice lines to be verified against possibly altered master data or business logic.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579234) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579234)
;

-- 2021-05-24T10:15:08.774Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung-Überprüfungssatz', Name='Rechnung-Überprüfungssatz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579234)
;

-- 2021-05-24T10:15:08.816Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-Überprüfungssatz', Description=NULL, Help='A verification set contains a selection of existing invoice lines to be verified against possibly altered master data or business logic.', CommitWarning = NULL WHERE AD_Element_ID = 579234
;

-- 2021-05-24T10:15:08.859Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-Überprüfungssatz', Description=NULL, Help='A verification set contains a selection of existing invoice lines to be verified against possibly altered master data or business logic.' WHERE AD_Element_ID = 579234
;

-- 2021-05-24T10:15:08.896Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungssatz', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579234
;

-- 2021-05-24T10:15:19.277Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 13:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='en_US'
;

-- 2021-05-24T10:15:19.314Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'en_US')
;

-- 2021-05-24T10:15:27.495Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rechnung-Überprüfungssatz', PrintName='Rechnung-Überprüfungssatz',Updated=TO_TIMESTAMP('2021-05-24 13:15:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='nl_NL'
;

-- 2021-05-24T10:15:27.534Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'nl_NL')
;

-- 2021-05-24T10:15:37.634Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.',Updated=TO_TIMESTAMP('2021-05-24 13:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='nl_NL'
;

-- 2021-05-24T10:15:37.673Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'nl_NL')
;

-- 2021-05-24T10:15:51.204Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.',Updated=TO_TIMESTAMP('2021-05-24 13:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='de_DE'
;

-- 2021-05-24T10:15:51.249Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'de_DE')
;

-- 2021-05-24T10:15:51.327Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579234,'de_DE')
;

-- 2021-05-24T10:15:51.364Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Rechnung-Überprüfungssatz', Description=NULL, Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.' WHERE AD_Element_ID=579234
;

-- 2021-05-24T10:15:51.400Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rechnung-Überprüfungssatz', Description=NULL, Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.' WHERE AD_Element_ID=579234 AND IsCentrallyMaintained='Y'
;

-- 2021-05-24T10:15:51.437Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-Überprüfungssatz', Description=NULL, Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579234) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579234)
;

-- 2021-05-24T10:15:51.481Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-Überprüfungssatz', Description=NULL, Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', CommitWarning = NULL WHERE AD_Element_ID = 579234
;

-- 2021-05-24T10:15:51.518Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-Überprüfungssatz', Description=NULL, Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.' WHERE AD_Element_ID = 579234
;

-- 2021-05-24T10:15:51.554Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungssatz', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579234
;

-- 2021-05-24T10:16:00.781Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.',Updated=TO_TIMESTAMP('2021-05-24 13:16:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='de_CH'
;

-- 2021-05-24T10:16:00.820Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'de_CH')
;

-- 2021-05-24T10:16:05.613Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.',Updated=TO_TIMESTAMP('2021-05-24 13:16:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='de_DE'
;

-- 2021-05-24T10:16:05.651Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'de_DE')
;

-- 2021-05-24T10:16:05.730Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579234,'de_DE')
;

-- 2021-05-24T10:16:05.767Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.' WHERE AD_Element_ID=579234
;

-- 2021-05-24T10:16:05.810Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.' WHERE AD_Element_ID=579234 AND IsCentrallyMaintained='Y'
;

-- 2021-05-24T10:16:05.845Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579234) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579234)
;

-- 2021-05-24T10:16:05.940Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', CommitWarning = NULL WHERE AD_Element_ID = 579234
;

-- 2021-05-24T10:16:06.016Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-Überprüfungssatz', Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.' WHERE AD_Element_ID = 579234
;

-- 2021-05-24T10:16:06.064Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungssatz', Description = 'Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579234
;

-- 2021-05-24T10:16:12.476Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='A verification set contains a selection of existing invoice lines to be verified against possibly altered master data or business logic.',Updated=TO_TIMESTAMP('2021-05-24 13:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='en_US'
;

-- 2021-05-24T10:16:12.514Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'en_US')
;

-- 2021-05-24T10:16:19.308Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.',Updated=TO_TIMESTAMP('2021-05-24 13:16:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='nl_NL'
;

-- 2021-05-24T10:16:19.347Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'nl_NL')
;

-- 2021-05-24T10:17:34.316Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=579234, Description='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Help='Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', Name='Rechnung-Überprüfungssatz',Updated=TO_TIMESTAMP('2021-05-24 13:17:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541145
;

-- 2021-05-24T10:17:34.529Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(579234)
;

-- 2021-05-24T10:17:34.570Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541145
;

-- 2021-05-24T10:17:34.607Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541145)
;

-- 2021-05-24T10:20:30.568Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungssatz', PrintName='Rechnung-Überprüfungssatz',Updated=TO_TIMESTAMP('2021-05-24 13:20:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579233 AND AD_Language='de_CH'
;

-- 2021-05-24T10:20:30.609Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579233,'de_CH')
;

-- 2021-05-24T10:20:36.473Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungssatz', PrintName='Rechnung-Überprüfungssatz',Updated=TO_TIMESTAMP('2021-05-24 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579233 AND AD_Language='de_DE'
;

-- 2021-05-24T10:20:36.514Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579233,'de_DE')
;

-- 2021-05-24T10:20:36.598Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579233,'de_DE')
;

-- 2021-05-24T10:20:36.637Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Invoice_Verification_Set_ID', Name='Rechnung-Überprüfungssatz', Description=NULL, Help=NULL WHERE AD_Element_ID=579233
;

-- 2021-05-24T10:20:36.675Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Verification_Set_ID', Name='Rechnung-Überprüfungssatz', Description=NULL, Help=NULL, AD_Element_ID=579233 WHERE UPPER(ColumnName)='C_INVOICE_VERIFICATION_SET_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-24T10:20:36.714Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Verification_Set_ID', Name='Rechnung-Überprüfungssatz', Description=NULL, Help=NULL WHERE AD_Element_ID=579233 AND IsCentrallyMaintained='Y'
;

-- 2021-05-24T10:20:36.750Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-Überprüfungssatz', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579233) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579233)
;

-- 2021-05-24T10:20:36.797Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung-Überprüfungssatz', Name='Rechnung-Überprüfungssatz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579233)
;

-- 2021-05-24T10:20:36.836Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-Überprüfungssatz', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579233
;

-- 2021-05-24T10:20:36.892Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-Überprüfungssatz', Description=NULL, Help=NULL WHERE AD_Element_ID = 579233
;

-- 2021-05-24T10:20:36.931Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungssatz', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579233
;

-- 2021-05-24T10:20:41.172Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 13:20:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579233 AND AD_Language='en_US'
;

-- 2021-05-24T10:20:41.211Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579233,'en_US')
;

-- 2021-05-24T10:20:54.060Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rechnung-Überprüfungssatz', PrintName='Rechnung-Überprüfungssatz',Updated=TO_TIMESTAMP('2021-05-24 13:20:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579233 AND AD_Language='nl_NL'
;

-- 2021-05-24T10:20:54.124Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579233,'nl_NL')
;

-- 2021-05-24T10:22:55.246Z
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=541145,Updated=TO_TIMESTAMP('2021-05-24 13:22:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541662
;

-- 2021-05-24T10:23:11.257Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- 2021-05-24T10:24:59.264Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579233,0,543956,541662,541145,'Y',TO_TIMESTAMP('2021-05-24 13:24:58','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','C_Invoice_Verification_Set','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Rechnung-Überprüfungssatz','N',10,1,TO_TIMESTAMP('2021-05-24 13:24:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:24:59.453Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543956 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-05-24T10:24:59.492Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579233)
;

-- 2021-05-24T10:24:59.528Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543956)
;

-- 2021-05-24T10:25:16.018Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543956,543109,TO_TIMESTAMP('2021-05-24 13:25:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-05-24 13:25:15','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-05-24T10:25:16.092Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543109 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-05-24T10:25:16.551Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543909,543109,TO_TIMESTAMP('2021-05-24 13:25:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-05-24 13:25:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:25:16.999Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543910,543109,TO_TIMESTAMP('2021-05-24 13:25:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-05-24 13:25:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:25:17.493Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,543909,545888,TO_TIMESTAMP('2021-05-24 13:25:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2021-05-24 13:25:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:25:44.370Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574033,646780,0,543956,TO_TIMESTAMP('2021-05-24 13:25:43','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-05-24 13:25:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:25:44.493Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646780 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T10:25:44.532Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2021-05-24T10:25:44.771Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646780
;

-- 2021-05-24T10:25:44.810Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646780)
;

-- 2021-05-24T10:25:45.363Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574034,646781,0,543956,TO_TIMESTAMP('2021-05-24 13:25:44','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2021-05-24 13:25:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:25:45.446Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646781 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T10:25:45.485Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2021-05-24T10:25:45.634Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646781
;

-- 2021-05-24T10:25:45.671Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646781)
;

-- 2021-05-24T10:25:46.215Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574037,646782,0,543956,TO_TIMESTAMP('2021-05-24 13:25:45','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-05-24 13:25:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:25:46.297Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646782 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T10:25:46.333Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2021-05-24T10:25:46.509Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646782
;

-- 2021-05-24T10:25:46.546Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646782)
;

-- 2021-05-24T10:25:47.121Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574040,646783,0,543956,TO_TIMESTAMP('2021-05-24 13:25:46','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Rechnung-Überprüfungssatz',TO_TIMESTAMP('2021-05-24 13:25:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:25:47.203Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646783 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T10:25:47.244Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579233)
;

-- 2021-05-24T10:25:47.287Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646783
;

-- 2021-05-24T10:25:47.334Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646783)
;

-- 2021-05-24T10:25:47.876Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574041,646784,0,543956,TO_TIMESTAMP('2021-05-24 13:25:47','YYYY-MM-DD HH24:MI:SS'),100,'',60,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2021-05-24 13:25:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:25:47.950Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646784 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T10:25:47.987Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2021-05-24T10:25:48.060Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646784
;

-- 2021-05-24T10:25:48.097Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646784)
;

-- 2021-05-24T10:25:48.717Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574042,646785,0,543956,TO_TIMESTAMP('2021-05-24 13:25:48','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2021-05-24 13:25:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:25:48.814Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646785 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T10:25:48.855Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2021-05-24T10:25:48.984Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646785
;

-- 2021-05-24T10:25:49.020Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646785)
;

-- 2021-05-24T10:26:43.881Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646784,0,543956,545888,585234,'F',TO_TIMESTAMP('2021-05-24 13:26:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2021-05-24 13:26:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:27:06.954Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543909,545889,TO_TIMESTAMP('2021-05-24 13:27:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','descrirption',20,TO_TIMESTAMP('2021-05-24 13:27:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:27:51.495Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646785,0,543956,545889,585235,'F',TO_TIMESTAMP('2021-05-24 13:27:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',10,0,0,TO_TIMESTAMP('2021-05-24 13:27:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:28:09.866Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543910,545890,TO_TIMESTAMP('2021-05-24 13:28:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2021-05-24 13:28:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:28:37.129Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646782,0,543956,545890,585236,'F',TO_TIMESTAMP('2021-05-24 13:28:36','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2021-05-24 13:28:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:28:52.640Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543910,545891,TO_TIMESTAMP('2021-05-24 13:28:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2021-05-24 13:28:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:29:09.035Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646781,0,543956,545891,585237,'F',TO_TIMESTAMP('2021-05-24 13:29:08','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2021-05-24 13:29:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:29:23.938Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646780,0,543956,545891,585238,'F',TO_TIMESTAMP('2021-05-24 13:29:23','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2021-05-24 13:29:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T10:30:04Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-05-24 13:30:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585236
;

-- 2021-05-24T10:30:04.189Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-05-24 13:30:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585234
;

-- 2021-05-24T10:30:04.433Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-05-24 13:30:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585235
;

-- 2021-05-24T10:30:04.653Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-05-24 13:30:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585237
;

-- 2021-05-24T10:34:19.454Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy,WEBUI_NameBrowse,WEBUI_NameNewBreadcrumb) VALUES (0,579235,0,TO_TIMESTAMP('2021-05-24 13:34:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice verifcation','Invoice verifcation',TO_TIMESTAMP('2021-05-24 13:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Invoice verifcation','Invoice verifcation')
;

-- 2021-05-24T10:34:19.570Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579235 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T10:34:39.211Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfung', PrintName='Rechnung-Überprüfung', WEBUI_NameBrowse='Rechnung-Überprüfung', WEBUI_NameNewBreadcrumb='Rechnung-Überprüfung',Updated=TO_TIMESTAMP('2021-05-24 13:34:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579235 AND AD_Language='de_CH'
;

-- 2021-05-24T10:34:39.251Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579235,'de_CH')
;

-- 2021-05-24T10:34:50.334Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfung', PrintName='Rechnung-Überprüfung', WEBUI_NameBrowse='Rechnung-Überprüfung', WEBUI_NameNewBreadcrumb='Rechnung-Überprüfung',Updated=TO_TIMESTAMP('2021-05-24 13:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579235 AND AD_Language='de_DE'
;

-- 2021-05-24T10:34:50.375Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579235,'de_DE')
;

-- 2021-05-24T10:34:50.451Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579235,'de_DE')
;

-- 2021-05-24T10:34:50.490Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Rechnung-Überprüfung', Description=NULL, Help=NULL WHERE AD_Element_ID=579235
;

-- 2021-05-24T10:34:50.528Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rechnung-Überprüfung', Description=NULL, Help=NULL WHERE AD_Element_ID=579235 AND IsCentrallyMaintained='Y'
;

-- 2021-05-24T10:34:50.564Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-Überprüfung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579235) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579235)
;

-- 2021-05-24T10:34:50.626Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung-Überprüfung', Name='Rechnung-Überprüfung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579235)
;

-- 2021-05-24T10:34:50.660Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-Überprüfung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579235
;

-- 2021-05-24T10:34:50.697Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-Überprüfung', Description=NULL, Help=NULL WHERE AD_Element_ID = 579235
;

-- 2021-05-24T10:34:50.748Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfung', Description = NULL, WEBUI_NameBrowse = 'Rechnung-Überprüfung', WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = 'Rechnung-Überprüfung' WHERE AD_Element_ID = 579235
;

-- 2021-05-24T10:34:55.076Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 13:34:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579235 AND AD_Language='en_US'
;

-- 2021-05-24T10:34:55.114Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579235,'en_US')
;

-- 2021-05-24T10:35:03.952Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rechnung-Überprüfung', PrintName='Rechnung-Überprüfung', WEBUI_NameBrowse='Rechnung-Überprüfung', WEBUI_NameNewBreadcrumb='Rechnung-Überprüfung',Updated=TO_TIMESTAMP('2021-05-24 13:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579235 AND AD_Language='nl_NL'
;

-- 2021-05-24T10:35:03.995Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579235,'nl_NL')
;

-- 2021-05-24T10:36:07.612Z
-- URL zum Konzept
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse,WEBUI_NameNewBreadcrumb) VALUES (0,579235,541712,0,TO_TIMESTAMP('2021-05-24 13:36:07','YYYY-MM-DD HH24:MI:SS'),100,'D','_InvoiceVerification','Y','N','N','N','N','Rechnung-Überprüfung',TO_TIMESTAMP('2021-05-24 13:36:07','YYYY-MM-DD HH24:MI:SS'),100,'Rechnung-Überprüfung','Rechnung-Überprüfung')
;

-- 2021-05-24T10:36:07.811Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541712 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-05-24T10:36:07.850Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541712, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541712)
;

-- 2021-05-24T10:36:07.916Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(579235)
;

-- 2021-05-24T10:36:11.817Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53324 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:11.860Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=241 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:11.901Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=288 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:11.958Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=432 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:11.999Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=243 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.052Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=413 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.098Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=538 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.134Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=462 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.170Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=505 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.205Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=235 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.242Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=511 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.279Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=245 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.312Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=251 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.354Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=246 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.391Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=509 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.426Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=510 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.462Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=496 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.498Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=304 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.536Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=255 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.582Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=286 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.623Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=287 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.661Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=438 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.698Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=234 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.738Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=244 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.778Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53190 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.826Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53187 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.867Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540642 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.908Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540651 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.946Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000103 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:12.983Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541712 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:36.113Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541712 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:36.150Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000104 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:36.263Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541537 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:36.358Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000085 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:36.435Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000086 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:36.473Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541271 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:36.521Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541418 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:36.559Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000059 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:36.597Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000067 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:36.636Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000077 AND AD_Tree_ID=10
;

-- 2021-05-24T10:36:53.592Z
-- URL zum Konzept
UPDATE AD_Menu SET IsSummary='Y',Updated=TO_TIMESTAMP('2021-05-24 13:36:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541712
;

-- 2021-05-24T10:40:34.921Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Rechnung-Überprüfungssatz',Updated=TO_TIMESTAMP('2021-05-24 13:40:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='de_CH'
;

-- 2021-05-24T10:40:34.957Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'de_CH')
;

-- 2021-05-24T10:40:40.261Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Rechnung-Überprüfungssatz',Updated=TO_TIMESTAMP('2021-05-24 13:40:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='de_DE'
;

-- 2021-05-24T10:40:40.300Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'de_DE')
;

-- 2021-05-24T10:40:40.403Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579234,'de_DE')
;

-- 2021-05-24T10:40:40.441Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungssatz', Description = 'Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.', WEBUI_NameBrowse = 'Rechnung-Überprüfungssatz', WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579234
;

-- 2021-05-24T10:40:46.018Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Rechnung-Überprüfungssatz',Updated=TO_TIMESTAMP('2021-05-24 13:40:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='nl_NL'
;

-- 2021-05-24T10:40:46.057Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'nl_NL')
;

-- 2021-05-24T10:40:56.158Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Invoice Verification Set',Updated=TO_TIMESTAMP('2021-05-24 13:40:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579234 AND AD_Language='en_US'
;

-- 2021-05-24T10:40:56.197Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579234,'en_US')
;

-- 2021-05-24T10:42:05.205Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,579234,541713,0,541145,TO_TIMESTAMP('2021-05-24 13:42:04','YYYY-MM-DD HH24:MI:SS'),100,'Ein Überprüfungssatz enthält existierende Rechnungszeilen, anhand derer Änderungen an Stammdaten oder Business-Logik überprüft werden können.','D','_InvoiceVerificationSet','Y','N','N','N','N','Rechnung-Überprüfungssatz',TO_TIMESTAMP('2021-05-24 13:42:04','YYYY-MM-DD HH24:MI:SS'),100,'Rechnung-Überprüfungssatz')
;

-- 2021-05-24T10:42:05.283Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541713 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-05-24T10:42:05.322Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541713, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541713)
;

-- 2021-05-24T10:42:05.360Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(579234)
;

-- 2021-05-24T10:42:16.279Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541712, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541713 AND AD_Tree_ID=10
;

-- 2021-05-24T10:53:58.436Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-05-24 13:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574041
;

-- 2021-05-24T10:54:06.406Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_invoice_verification_set','Name','VARCHAR(60)',null,null)
;


-- Start Creation for C_Invoice_Verification_SetLine


-- 2021-05-24T11:25:42.846Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541663,'N',TO_TIMESTAMP('2021-05-24 14:25:42','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Invoice Verification Element','NP','L','C_Invoice_Verification_SetLine','DTI',TO_TIMESTAMP('2021-05-24 14:25:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:25:43.099Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541663 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-05-24T11:25:43.522Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555406,TO_TIMESTAMP('2021-05-24 14:25:43','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Invoice_Verification_SetLine',1,'Y','N','Y','Y','C_Invoice_Verification_SetLine','N',1000000,TO_TIMESTAMP('2021-05-24 14:25:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:25:43.691Z
-- URL zum Konzept
CREATE SEQUENCE C_INVOICE_VERIFICATION_SETLINE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-05-24T11:26:53.267Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574043,102,0,19,541663,'AD_Client_ID',TO_TIMESTAMP('2021-05-24 14:26:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-05-24 14:26:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:26:53.506Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574043 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:26:53.587Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- 2021-05-24T11:26:56.381Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574044,113,0,30,541663,'AD_Org_ID',TO_TIMESTAMP('2021-05-24 14:26:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Organisation',0,TO_TIMESTAMP('2021-05-24 14:26:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:26:56.575Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574044 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:26:56.652Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- 2021-05-24T11:26:59.052Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574045,245,0,16,541663,'Created',TO_TIMESTAMP('2021-05-24 14:26:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-05-24 14:26:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:26:59.256Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574045 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:26:59.333Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- 2021-05-24T11:27:01.409Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574046,246,0,18,110,541663,'CreatedBy',TO_TIMESTAMP('2021-05-24 14:27:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-05-24 14:27:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:27:01.701Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574046 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:27:01.778Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- 2021-05-24T11:27:03.807Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574047,348,0,20,541663,'IsActive',TO_TIMESTAMP('2021-05-24 14:27:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-05-24 14:27:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:27:04.037Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574047 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:27:04.116Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- 2021-05-24T11:27:06.126Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574048,607,0,16,541663,'Updated',TO_TIMESTAMP('2021-05-24 14:27:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-05-24 14:27:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:27:06.338Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574048 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:27:06.412Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- 2021-05-24T11:27:08.332Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574049,608,0,18,110,541663,'UpdatedBy',TO_TIMESTAMP('2021-05-24 14:27:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-05-24 14:27:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:27:08.537Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574049 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:27:08.614Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2021-05-24T11:27:10.643Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579236,0,'C_Invoice_Verification_SetLine_ID',TO_TIMESTAMP('2021-05-24 14:27:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice Verification Element','Invoice Verification Element',TO_TIMESTAMP('2021-05-24 14:27:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:27:10.845Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579236 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T11:27:12.162Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574050,579236,0,13,541663,'C_Invoice_Verification_SetLine_ID',TO_TIMESTAMP('2021-05-24 14:27:10','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','Invoice Verification Element',0,TO_TIMESTAMP('2021-05-24 14:27:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:27:12.241Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574050 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:27:12.317Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579236)
;

-- 2021-05-24T11:30:23.353Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574051,579233,0,19,541663,'C_Invoice_Verification_Set_ID',TO_TIMESTAMP('2021-05-24 14:30:22','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnung-Überprüfungssatz',0,10,TO_TIMESTAMP('2021-05-24 14:30:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:30:23.544Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574051 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:30:23.624Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579233)
;

-- 2021-05-24T11:31:31.122Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574052,1008,0,19,541663,'C_Invoice_ID',TO_TIMESTAMP('2021-05-24 14:31:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Invoice Identifier','D',0,10,'E','The Invoice Document.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Rechnung',0,0,TO_TIMESTAMP('2021-05-24 14:31:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:31:31.327Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574052 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:31:31.404Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1008)
;

-- 2021-05-24T11:32:23.037Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574053,196,0,19,541663,'C_DocType_ID',TO_TIMESTAMP('2021-05-24 14:32:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Belegart oder Verarbeitungsvorgaben','D',0,10,'E','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N',0,'Belegart',0,0,TO_TIMESTAMP('2021-05-24 14:32:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:32:23.256Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574053 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:32:23.331Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(196)
;

-- 2021-05-24T11:32:53.017Z
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2021-05-24 14:32:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574051
;

-- 2021-05-24T11:33:14.219Z
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2021-05-24 14:33:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574052
;

-- 2021-05-24T11:34:17.753Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574054,1076,0,19,541663,'C_InvoiceLine_ID',TO_TIMESTAMP('2021-05-24 14:34:16','YYYY-MM-DD HH24:MI:SS'),100,'N','Rechnungszeile','D',0,10,'Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungsposition',0,20,TO_TIMESTAMP('2021-05-24 14:34:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:34:18.010Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574054 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:34:18.086Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1076)
;

-- 2021-05-24T11:35:35.501Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579237,0,'RelevantDate',TO_TIMESTAMP('2021-05-24 14:35:34','YYYY-MM-DD HH24:MI:SS'),100,'Date by which the invoice''s tax was selected.','D','Y','Relevant Date','Relevant Date',TO_TIMESTAMP('2021-05-24 14:35:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:35:35.691Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579237 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T11:35:59.130Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Relevantes Datum', PrintName='Relevantes Datum',Updated=TO_TIMESTAMP('2021-05-24 14:35:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579237 AND AD_Language='de_CH'
;

-- 2021-05-24T11:35:59.170Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579237,'de_CH')
;

-- 2021-05-24T11:36:02.334Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 14:36:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579237 AND AD_Language='de_CH'
;

-- 2021-05-24T11:36:02.374Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579237,'de_CH')
;

-- 2021-05-24T11:36:17.338Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Relevantes Datum Date', PrintName='Relevantes Datum Date',Updated=TO_TIMESTAMP('2021-05-24 14:36:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579237 AND AD_Language='de_DE'
;

-- 2021-05-24T11:36:17.380Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579237,'de_DE')
;

-- 2021-05-24T11:36:17.491Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579237,'de_DE')
;

-- 2021-05-24T11:36:17.533Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='RelevantDate', Name='Relevantes Datum Date', Description='Date by which the invoice''s tax was selected.', Help=NULL WHERE AD_Element_ID=579237
;

-- 2021-05-24T11:36:17.573Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='RelevantDate', Name='Relevantes Datum Date', Description='Date by which the invoice''s tax was selected.', Help=NULL, AD_Element_ID=579237 WHERE UPPER(ColumnName)='RELEVANTDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-24T11:36:17.619Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='RelevantDate', Name='Relevantes Datum Date', Description='Date by which the invoice''s tax was selected.', Help=NULL WHERE AD_Element_ID=579237 AND IsCentrallyMaintained='Y'
;

-- 2021-05-24T11:36:17.658Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Relevantes Datum Date', Description='Date by which the invoice''s tax was selected.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579237) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579237)
;

-- 2021-05-24T11:36:17.736Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Relevantes Datum Date', Name='Relevantes Datum Date' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579237)
;

-- 2021-05-24T11:36:17.779Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Relevantes Datum Date', Description='Date by which the invoice''s tax was selected.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579237
;

-- 2021-05-24T11:36:17.821Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Relevantes Datum Date', Description='Date by which the invoice''s tax was selected.', Help=NULL WHERE AD_Element_ID = 579237
;

-- 2021-05-24T11:36:17.862Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Relevantes Datum Date', Description = 'Date by which the invoice''s tax was selected.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579237
;

-- 2021-05-24T11:36:22.745Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 14:36:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579237 AND AD_Language='en_US'
;

-- 2021-05-24T11:36:22.786Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579237,'en_US')
;

-- 2021-05-24T11:36:37.413Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Datum anhand dessen der Steuersatz ermittelt wurde.', Name='Relevantes Datum', PrintName='Relevantes Datum',Updated=TO_TIMESTAMP('2021-05-24 14:36:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579237 AND AD_Language='nl_NL'
;

-- 2021-05-24T11:36:37.452Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579237,'nl_NL')
;

-- 2021-05-24T11:36:43.930Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Datum anhand dessen der Steuersatz ermittelt wurde.',Updated=TO_TIMESTAMP('2021-05-24 14:36:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579237 AND AD_Language='de_DE'
;

-- 2021-05-24T11:36:43.969Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579237,'de_DE')
;

-- 2021-05-24T11:36:44.064Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579237,'de_DE')
;

-- 2021-05-24T11:36:44.102Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='RelevantDate', Name='Relevantes Datum Date', Description='Datum anhand dessen der Steuersatz ermittelt wurde.', Help=NULL WHERE AD_Element_ID=579237
;

-- 2021-05-24T11:36:44.141Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='RelevantDate', Name='Relevantes Datum Date', Description='Datum anhand dessen der Steuersatz ermittelt wurde.', Help=NULL, AD_Element_ID=579237 WHERE UPPER(ColumnName)='RELEVANTDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-24T11:36:44.180Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='RelevantDate', Name='Relevantes Datum Date', Description='Datum anhand dessen der Steuersatz ermittelt wurde.', Help=NULL WHERE AD_Element_ID=579237 AND IsCentrallyMaintained='Y'
;

-- 2021-05-24T11:36:44.218Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Relevantes Datum Date', Description='Datum anhand dessen der Steuersatz ermittelt wurde.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579237) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579237)
;

-- 2021-05-24T11:36:44.261Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Relevantes Datum Date', Description='Datum anhand dessen der Steuersatz ermittelt wurde.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579237
;

-- 2021-05-24T11:36:44.303Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Relevantes Datum Date', Description='Datum anhand dessen der Steuersatz ermittelt wurde.', Help=NULL WHERE AD_Element_ID = 579237
;

-- 2021-05-24T11:36:44.341Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Relevantes Datum Date', Description = 'Datum anhand dessen der Steuersatz ermittelt wurde.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579237
;

-- 2021-05-24T11:36:50.363Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Datum anhand dessen der Steuersatz ermittelt wurde.',Updated=TO_TIMESTAMP('2021-05-24 14:36:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579237 AND AD_Language='de_CH'
;

-- 2021-05-24T11:36:50.403Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579237,'de_CH')
;

-- 2021-05-24T11:37:35.785Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574055,579237,0,15,541663,'RelevantDate',TO_TIMESTAMP('2021-05-24 14:37:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum anhand dessen der Steuersatz ermittelt wurde.','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Relevantes Datum Date',0,0,TO_TIMESTAMP('2021-05-24 14:37:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:37:35.912Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574055 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:37:35.989Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579237)
;

-- 2021-05-24T11:37:45.833Z
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2021-05-24 14:37:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574055
;

-- 2021-05-24T11:42:30.694Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579238,0,'C_InvoiceLine_Tax_ID',TO_TIMESTAMP('2021-05-24 14:42:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice Line TaxId','Invoice Line TaxId',TO_TIMESTAMP('2021-05-24 14:42:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:42:31.256Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579238 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T11:43:34.447Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574056,579238,0,19,541663,'C_InvoiceLine_Tax_ID',TO_TIMESTAMP('2021-05-24 14:43:33','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Invoice Line TaxId',0,0,TO_TIMESTAMP('2021-05-24 14:43:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T11:43:34.523Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574056 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T11:43:34.595Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579238)
;

-- 2021-05-24T11:44:26.174Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=10, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-05-24 14:44:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574056
;


-- 2021-05-24T11:49:28.713Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES (0,579239,0,TO_TIMESTAMP('2021-05-24 14:49:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice Verification Set Line','Invoice Verification Set Line',TO_TIMESTAMP('2021-05-24 14:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Verification Set Line')
;

-- 2021-05-24T11:49:28.907Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579239 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T11:49:55.020Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungszeile', PrintName='Rechnung-Überprüfungszeile', WEBUI_NameBrowse='Rechnung-Überprüfungszeile',Updated=TO_TIMESTAMP('2021-05-24 14:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579239 AND AD_Language='de_CH'
;

-- 2021-05-24T11:49:55.056Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579239,'de_CH')
;

-- 2021-05-24T11:50:06.528Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungszeile', PrintName='Rechnung-Überprüfungszeile', WEBUI_NameBrowse='Rechnung-Überprüfungszeile',Updated=TO_TIMESTAMP('2021-05-24 14:50:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579239 AND AD_Language='de_DE'
;

-- 2021-05-24T11:50:06.567Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579239,'de_DE')
;

-- 2021-05-24T11:50:06.662Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579239,'de_DE')
;

-- 2021-05-24T11:50:06.699Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=579239
;

-- 2021-05-24T11:50:06.737Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=579239 AND IsCentrallyMaintained='Y'
;

-- 2021-05-24T11:50:06.773Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579239) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579239)
;

-- 2021-05-24T11:50:06.851Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung-Überprüfungszeile', Name='Rechnung-Überprüfungszeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579239)
;

-- 2021-05-24T11:50:06.886Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579239
;

-- 2021-05-24T11:50:06.921Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL WHERE AD_Element_ID = 579239
;

-- 2021-05-24T11:50:06.955Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungszeile', Description = NULL, WEBUI_NameBrowse = 'Rechnung-Überprüfungszeile', WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579239
;

-- 2021-05-24T11:50:12.502Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 14:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579239 AND AD_Language='en_US'
;

-- 2021-05-24T11:50:12.538Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579239,'en_US')
;

-- 2021-05-24T11:50:21.726Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rechnung-Überprüfungszeile', PrintName='Rechnung-Überprüfungszeile', WEBUI_NameBrowse='Rechnung-Überprüfungszeile',Updated=TO_TIMESTAMP('2021-05-24 14:50:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579239 AND AD_Language='nl_NL'
;

-- 2021-05-24T11:50:21.762Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579239,'nl_NL')
;

-- 2021-05-24T11:51:12.024Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,579239,0,541146,TO_TIMESTAMP('2021-05-24 14:51:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Rechnung-Überprüfungszeile','N',TO_TIMESTAMP('2021-05-24 14:51:11','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2021-05-24T11:51:12.105Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541146 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2021-05-24T11:51:12.178Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(579239)
;

-- 2021-05-24T11:51:12.215Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541146
;

-- 2021-05-24T11:51:12.255Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541146)
;

-- 2021-05-24T11:52:18.080Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579236,0,543957,541663,541146,'Y',TO_TIMESTAMP('2021-05-24 14:52:17','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','C_Invoice_Verification_SetLine','Y','N','Y','N','N','N','N','Y','N','N','N','Y','Y','N','N','N',0,'Invoice Verification Element','N',10,1,TO_TIMESTAMP('2021-05-24 14:52:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:52:18.214Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543957 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-05-24T11:52:18.254Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579236)
;

-- 2021-05-24T11:52:18.291Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543957)
;

-- 2021-05-24T11:52:35.514Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574043,646786,0,543957,TO_TIMESTAMP('2021-05-24 14:52:35','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-05-24 14:52:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:52:35.586Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646786 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T11:52:35.626Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2021-05-24T11:52:36.035Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646786
;

-- 2021-05-24T11:52:36.068Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646786)
;

-- 2021-05-24T11:52:36.585Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574044,646787,0,543957,TO_TIMESTAMP('2021-05-24 14:52:36','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2021-05-24 14:52:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:52:36.712Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646787 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T11:52:36.754Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2021-05-24T11:52:36.951Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646787
;

-- 2021-05-24T11:52:36.988Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646787)
;

-- 2021-05-24T11:52:37.563Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574047,646788,0,543957,TO_TIMESTAMP('2021-05-24 14:52:37','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-05-24 14:52:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:52:37.650Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646788 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T11:52:37.689Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2021-05-24T11:52:37.912Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646788
;

-- 2021-05-24T11:52:37.949Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646788)
;

-- 2021-05-24T11:52:38.497Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574050,646789,0,543957,TO_TIMESTAMP('2021-05-24 14:52:38','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Invoice Verification Element',TO_TIMESTAMP('2021-05-24 14:52:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:52:38.583Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646789 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T11:52:38.620Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579236)
;

-- 2021-05-24T11:52:38.658Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646789
;

-- 2021-05-24T11:52:38.699Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646789)
;

-- 2021-05-24T11:52:39.224Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574051,646790,0,543957,TO_TIMESTAMP('2021-05-24 14:52:38','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Rechnung-Überprüfungssatz',TO_TIMESTAMP('2021-05-24 14:52:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:52:39.318Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646790 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T11:52:39.357Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579233)
;

-- 2021-05-24T11:52:39.402Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646790
;

-- 2021-05-24T11:52:39.444Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646790)
;

-- 2021-05-24T11:52:39.972Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574052,646791,0,543957,TO_TIMESTAMP('2021-05-24 14:52:39','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier',10,'D','The Invoice Document.','Y','N','N','N','N','N','N','N','Rechnung',TO_TIMESTAMP('2021-05-24 14:52:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:52:40.050Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646791 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T11:52:40.087Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008)
;

-- 2021-05-24T11:52:40.145Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646791
;

-- 2021-05-24T11:52:40.181Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646791)
;

-- 2021-05-24T11:52:40.738Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574053,646792,0,543957,TO_TIMESTAMP('2021-05-24 14:52:40','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben',10,'D','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2021-05-24 14:52:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:52:40.829Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646792 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T11:52:40.867Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196)
;

-- 2021-05-24T11:52:40.917Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646792
;

-- 2021-05-24T11:52:40.950Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646792)
;

-- 2021-05-24T11:52:41.471Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574054,646793,0,543957,TO_TIMESTAMP('2021-05-24 14:52:41','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungszeile',10,'D','Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.','Y','N','N','N','N','N','N','N','Rechnungsposition',TO_TIMESTAMP('2021-05-24 14:52:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:52:41.552Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646793 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T11:52:41.590Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1076)
;

-- 2021-05-24T11:52:41.629Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646793
;

-- 2021-05-24T11:52:41.666Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646793)
;

-- 2021-05-24T11:52:42.210Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574055,646794,0,543957,TO_TIMESTAMP('2021-05-24 14:52:41','YYYY-MM-DD HH24:MI:SS'),100,'Datum anhand dessen der Steuersatz ermittelt wurde.',7,'D','Y','N','N','N','N','N','N','N','Relevantes Datum Date',TO_TIMESTAMP('2021-05-24 14:52:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:52:42.321Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646794 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T11:52:42.373Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579237)
;

-- 2021-05-24T11:52:42.422Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646794
;

-- 2021-05-24T11:52:42.471Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646794)
;

-- 2021-05-24T11:52:43.017Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574056,646795,0,543957,TO_TIMESTAMP('2021-05-24 14:52:42','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Invoice Line TaxId',TO_TIMESTAMP('2021-05-24 14:52:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:52:43.101Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646795 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T11:52:43.143Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579238)
;

-- 2021-05-24T11:52:43.180Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646795
;

-- 2021-05-24T11:52:43.220Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646795)
;

-- 2021-05-24T11:53:06.370Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543957,543110,TO_TIMESTAMP('2021-05-24 14:53:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-05-24 14:53:06','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-05-24T11:53:06.445Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543110 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-05-24T11:53:06.832Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543911,543110,TO_TIMESTAMP('2021-05-24 14:53:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-05-24 14:53:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:53:07.251Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543912,543110,TO_TIMESTAMP('2021-05-24 14:53:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-05-24 14:53:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:53:07.666Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,543911,545892,TO_TIMESTAMP('2021-05-24 14:53:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2021-05-24 14:53:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:53:14.962Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- 2021-05-24T11:56:35.355Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646790,0,543957,545892,585239,'F',TO_TIMESTAMP('2021-05-24 14:56:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnung-Überprüfungssatz',10,0,0,TO_TIMESTAMP('2021-05-24 14:56:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:56:54.269Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646791,0,543957,545892,585240,'F',TO_TIMESTAMP('2021-05-24 14:56:53','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier','The Invoice Document.','Y','N','N','Y','N','N','N',0,'Rechnung',20,0,0,TO_TIMESTAMP('2021-05-24 14:56:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:57:27.977Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646793,0,543957,545892,585241,'F',TO_TIMESTAMP('2021-05-24 14:57:27','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungszeile','Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.','Y','N','N','Y','N','N','N',0,'Rechnungsposition',30,0,0,TO_TIMESTAMP('2021-05-24 14:57:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:57:52.664Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646795,0,543957,545892,585242,'F',TO_TIMESTAMP('2021-05-24 14:57:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Invoice Line TaxId',40,0,0,TO_TIMESTAMP('2021-05-24 14:57:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:58:30.056Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543912,545893,TO_TIMESTAMP('2021-05-24 14:58:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','flag',10,TO_TIMESTAMP('2021-05-24 14:58:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:58:47.495Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543912,545894,TO_TIMESTAMP('2021-05-24 14:58:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','doc',20,TO_TIMESTAMP('2021-05-24 14:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:58:56.035Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543912,545895,TO_TIMESTAMP('2021-05-24 14:58:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',30,TO_TIMESTAMP('2021-05-24 14:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:59:03.509Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543912,545896,TO_TIMESTAMP('2021-05-24 14:59:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',40,TO_TIMESTAMP('2021-05-24 14:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:59:25.390Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646788,0,543957,545893,585243,'F',TO_TIMESTAMP('2021-05-24 14:59:24','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2021-05-24 14:59:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T11:59:52.773Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646792,0,543957,545894,585244,'F',TO_TIMESTAMP('2021-05-24 14:59:52','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','Y','N','N','N',0,'Belegart',10,0,0,TO_TIMESTAMP('2021-05-24 14:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:00:26.534Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646794,0,543957,545895,585245,'F',TO_TIMESTAMP('2021-05-24 15:00:26','YYYY-MM-DD HH24:MI:SS'),100,'Datum anhand dessen der Steuersatz ermittelt wurde.','Y','N','N','Y','N','N','N',0,'Relevantes Datum Date',10,0,0,TO_TIMESTAMP('2021-05-24 15:00:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:01:01.973Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646787,0,543957,545896,585247,'F',TO_TIMESTAMP('2021-05-24 15:01:01','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2021-05-24 15:01:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:01:18.899Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646786,0,543957,545896,585248,'F',TO_TIMESTAMP('2021-05-24 15:01:18','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2021-05-24 15:01:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:02:46.649Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-05-24 15:02:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585239
;

-- 2021-05-24T12:02:46.840Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-05-24 15:02:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585240
;

-- 2021-05-24T12:02:47.025Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-05-24 15:02:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585244
;

-- 2021-05-24T12:02:47.206Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-05-24 15:02:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585241
;

-- 2021-05-24T12:02:47.392Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-05-24 15:02:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585245
;

-- 2021-05-24T12:02:47.587Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-05-24 15:02:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585242
;

-- 2021-05-24T12:02:47.837Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-05-24 15:02:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585247
;

-- 2021-05-24T12:05:33.557Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,579239,541714,0,541146,TO_TIMESTAMP('2021-05-24 15:05:33','YYYY-MM-DD HH24:MI:SS'),100,'D','_Invoice Verification Element','Y','N','N','N','N','Rechnung-Überprüfungszeile',TO_TIMESTAMP('2021-05-24 15:05:33','YYYY-MM-DD HH24:MI:SS'),100,'Rechnung-Überprüfungszeile')
;

-- 2021-05-24T12:05:33.639Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541714 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-05-24T12:05:33.682Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541714, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541714)
;

-- 2021-05-24T12:05:33.733Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(579239)
;

-- 2021-05-24T12:05:44.090Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541712, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541713 AND AD_Tree_ID=10
;

-- 2021-05-24T12:05:44.128Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541712, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541714 AND AD_Tree_ID=10
;


-- 2021-05-24T12:23:51.981Z
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540592,0,541663,TO_TIMESTAMP('2021-05-24 15:23:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','C_Invoice_Verification_Set_ID_Unique','N',TO_TIMESTAMP('2021-05-24 15:23:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:23:52.135Z
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540592 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-05-24T12:24:56.893Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,574051,541102,540592,0,TO_TIMESTAMP('2021-05-24 15:24:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-05-24 15:24:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:25:39.245Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,574044,541103,540592,0,TO_TIMESTAMP('2021-05-24 15:25:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2021-05-24 15:25:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:26:36.849Z
-- URL zum Konzept
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540592
;

-- 2021-05-24T12:26:37.078Z
-- URL zum Konzept
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540592
;

-- 2021-05-24T12:27:03.032Z
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540593,0,541663,TO_TIMESTAMP('2021-05-24 15:27:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Invoice_Verification_Set_ID_Index','N',TO_TIMESTAMP('2021-05-24 15:27:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:27:03.071Z
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540593 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-05-24T12:27:29.307Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,574051,541104,540593,0,TO_TIMESTAMP('2021-05-24 15:27:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-05-24 15:27:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:27:34.299Z
-- URL zum Konzept
CREATE INDEX C_Invoice_Verification_Set_ID_Index ON C_Invoice_Verification_SetLine (C_Invoice_Verification_Set_ID)
;

-- 2021-05-24T12:28:08.183Z
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540594,0,541663,TO_TIMESTAMP('2021-05-24 15:28:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Invoice_ID_Index','N',TO_TIMESTAMP('2021-05-24 15:28:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:28:08.224Z
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540594 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-05-24T12:28:25.227Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,574052,541105,540594,0,TO_TIMESTAMP('2021-05-24 15:28:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-05-24 15:28:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:28:36.794Z
-- URL zum Konzept
CREATE INDEX C_Invoice_ID_Index ON C_Invoice_Verification_SetLine (C_Invoice_ID)
;

-- Creation for C_Invoice_Verification_Run



-- 2021-05-24T12:46:26.005Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541664,'N',TO_TIMESTAMP('2021-05-24 15:46:25','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Invoice Verification Run','NP','L','C_Invoice_Verification_Run','DTI',TO_TIMESTAMP('2021-05-24 15:46:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:46:26.347Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541664 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-05-24T12:46:26.767Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555407,TO_TIMESTAMP('2021-05-24 15:46:26','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Invoice_Verification_Run',1,'Y','N','Y','Y','C_Invoice_Verification_Run','N',1000000,TO_TIMESTAMP('2021-05-24 15:46:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:46:26.941Z
-- URL zum Konzept
CREATE SEQUENCE C_INVOICE_VERIFICATION_RUN_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-05-24T12:46:54.475Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574058,102,0,19,541664,'AD_Client_ID',TO_TIMESTAMP('2021-05-24 15:46:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-05-24 15:46:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:46:54.714Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574058 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:46:54.791Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- 2021-05-24T12:46:57.806Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574059,113,0,30,541664,'AD_Org_ID',TO_TIMESTAMP('2021-05-24 15:46:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Organisation',0,TO_TIMESTAMP('2021-05-24 15:46:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:46:58.032Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574059 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:46:58.112Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- 2021-05-24T12:47:00.546Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574060,245,0,16,541664,'Created',TO_TIMESTAMP('2021-05-24 15:47:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-05-24 15:47:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:47:00.745Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574060 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:47:00.821Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- 2021-05-24T12:47:03.495Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574061,246,0,18,110,541664,'CreatedBy',TO_TIMESTAMP('2021-05-24 15:47:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-05-24 15:47:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:47:03.695Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574061 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:47:03.776Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- 2021-05-24T12:47:05.910Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574062,348,0,20,541664,'IsActive',TO_TIMESTAMP('2021-05-24 15:47:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-05-24 15:47:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:47:06.119Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574062 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:47:06.193Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- 2021-05-24T12:47:08.240Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574063,607,0,16,541664,'Updated',TO_TIMESTAMP('2021-05-24 15:47:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-05-24 15:47:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:47:08.427Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574063 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:47:08.501Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- 2021-05-24T12:47:10.441Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574064,608,0,18,110,541664,'UpdatedBy',TO_TIMESTAMP('2021-05-24 15:47:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-05-24 15:47:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:47:10.655Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574064 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:47:10.736Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2021-05-24T12:47:12.714Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579241,0,'C_Invoice_Verification_Run_ID',TO_TIMESTAMP('2021-05-24 15:47:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice Verification Run','Invoice Verification Run',TO_TIMESTAMP('2021-05-24 15:47:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:47:12.912Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579241 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T12:47:14.411Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574065,579241,0,13,541664,'C_Invoice_Verification_Run_ID',TO_TIMESTAMP('2021-05-24 15:47:12','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','Invoice Verification Run',0,TO_TIMESTAMP('2021-05-24 15:47:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:47:14.493Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574065 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:47:14.567Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579241)
;

-- 2021-05-24T12:47:56.392Z
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2021-05-24 15:47:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574065
;

-- 2021-05-24T12:48:50.125Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574066,579233,0,19,541664,'C_Invoice_Verification_Set_ID',TO_TIMESTAMP('2021-05-24 15:48:49','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Rechnung-Überprüfungssatz',0,20,TO_TIMESTAMP('2021-05-24 15:48:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:48:50.365Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574066 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:48:50.449Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579233)
;

-- 2021-05-24T12:49:35.541Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579242,0,'MovementDate_Override',TO_TIMESTAMP('2021-05-24 15:49:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Override Movement Date','Override Movement Date',TO_TIMESTAMP('2021-05-24 15:49:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:49:35.744Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579242 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T12:49:58.964Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Overriding movement date to be used when the verification set''s invoice lines are verified. Leave empty to use the invoice''s original date.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 15:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579242 AND AD_Language='en_US'
;

-- 2021-05-24T12:49:59.003Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579242,'en_US')
;

-- 2021-05-24T12:50:11.457Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.',Updated=TO_TIMESTAMP('2021-05-24 15:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579242 AND AD_Language='nl_NL'
;

-- 2021-05-24T12:50:11.496Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579242,'nl_NL')
;

-- 2021-05-24T12:50:19.365Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.',Updated=TO_TIMESTAMP('2021-05-24 15:50:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579242 AND AD_Language='de_DE'
;

-- 2021-05-24T12:50:19.404Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579242,'de_DE')
;

-- 2021-05-24T12:50:19.497Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579242,'de_DE')
;

-- 2021-05-24T12:50:19.536Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='MovementDate_Override', Name='Override Movement Date', Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', Help=NULL WHERE AD_Element_ID=579242
;

-- 2021-05-24T12:50:19.573Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='MovementDate_Override', Name='Override Movement Date', Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', Help=NULL, AD_Element_ID=579242 WHERE UPPER(ColumnName)='MOVEMENTDATE_OVERRIDE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-24T12:50:19.616Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='MovementDate_Override', Name='Override Movement Date', Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', Help=NULL WHERE AD_Element_ID=579242 AND IsCentrallyMaintained='Y'
;

-- 2021-05-24T12:50:19.653Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Override Movement Date', Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579242) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579242)
;

-- 2021-05-24T12:50:19.732Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Override Movement Date', Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579242
;

-- 2021-05-24T12:50:19.773Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Override Movement Date', Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', Help=NULL WHERE AD_Element_ID = 579242
;

-- 2021-05-24T12:50:19.812Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Override Movement Date', Description = 'Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579242
;

-- 2021-05-24T12:50:26.047Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.',Updated=TO_TIMESTAMP('2021-05-24 15:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579242 AND AD_Language='de_CH'
;

-- 2021-05-24T12:50:26.083Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579242,'de_CH')
;

-- 2021-05-24T12:50:36.736Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abw. Bewegungsdatum', PrintName='Abw. Bewegungsdatum',Updated=TO_TIMESTAMP('2021-05-24 15:50:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579242 AND AD_Language='de_CH'
;

-- 2021-05-24T12:50:36.776Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579242,'de_CH')
;

-- 2021-05-24T12:50:43.234Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abw. Bewegungsdatum', PrintName='Abw. Bewegungsdatum',Updated=TO_TIMESTAMP('2021-05-24 15:50:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579242 AND AD_Language='de_DE'
;

-- 2021-05-24T12:50:43.275Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579242,'de_DE')
;

-- 2021-05-24T12:50:43.360Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579242,'de_DE')
;

-- 2021-05-24T12:50:43.399Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='MovementDate_Override', Name='Abw. Bewegungsdatum', Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', Help=NULL WHERE AD_Element_ID=579242
;

-- 2021-05-24T12:50:43.439Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='MovementDate_Override', Name='Abw. Bewegungsdatum', Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', Help=NULL, AD_Element_ID=579242 WHERE UPPER(ColumnName)='MOVEMENTDATE_OVERRIDE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-24T12:50:43.477Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='MovementDate_Override', Name='Abw. Bewegungsdatum', Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', Help=NULL WHERE AD_Element_ID=579242 AND IsCentrallyMaintained='Y'
;

-- 2021-05-24T12:50:43.517Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Abw. Bewegungsdatum', Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579242) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579242)
;

-- 2021-05-24T12:50:43.580Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Abw. Bewegungsdatum', Name='Abw. Bewegungsdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579242)
;

-- 2021-05-24T12:50:43.619Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Abw. Bewegungsdatum', Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579242
;

-- 2021-05-24T12:50:43.658Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Abw. Bewegungsdatum', Description='Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', Help=NULL WHERE AD_Element_ID = 579242
;

-- 2021-05-24T12:50:43.694Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Abw. Bewegungsdatum', Description = 'Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579242
;

-- 2021-05-24T12:50:50.728Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Abw. Bewegungsdatum', PrintName='Abw. Bewegungsdatum',Updated=TO_TIMESTAMP('2021-05-24 15:50:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579242 AND AD_Language='nl_NL'
;

-- 2021-05-24T12:50:50.768Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579242,'nl_NL')
;

-- 2021-05-24T12:51:20.217Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574067,579242,0,15,541664,'MovementDate_Override',TO_TIMESTAMP('2021-05-24 15:51:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Abw. Bewegungsdatum',0,0,TO_TIMESTAMP('2021-05-24 15:51:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:51:20.314Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574067 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:51:20.394Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579242)
;

-- 2021-05-24T12:52:03.281Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574068,53280,0,15,541664,'DateStart',TO_TIMESTAMP('2021-05-24 15:52:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N',0,'DateStart',0,30,TO_TIMESTAMP('2021-05-24 15:52:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:52:03.488Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574068 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:52:03.560Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(53280)
;

-- 2021-05-24T12:52:30.909Z
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2021-05-24 15:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574068
;

-- 2021-05-24T12:54:33.049Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579243,0,'DateEnd',TO_TIMESTAMP('2021-05-24 15:54:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Date End','Date End',TO_TIMESTAMP('2021-05-24 15:54:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T12:54:33.295Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579243 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T12:55:02.822Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574069,579243,0,15,541664,'DateEnd',TO_TIMESTAMP('2021-05-24 15:55:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Date End',0,0,TO_TIMESTAMP('2021-05-24 15:55:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:55:02.921Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574069 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:55:03.008Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579243)
;

-- 2021-05-24T12:55:42.502Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574070,114,0,19,541664,'AD_PInstance_ID',TO_TIMESTAMP('2021-05-24 15:55:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Instanz eines Prozesses','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Prozess-Instanz',0,0,TO_TIMESTAMP('2021-05-24 15:55:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:55:42.759Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574070 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:55:42.834Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(114)
;

-- 2021-05-24T12:56:27.735Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574071,1115,0,36,541664,'Note',TO_TIMESTAMP('2021-05-24 15:56:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Optional weitere Information','D',0,255,'Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Notiz',0,0,TO_TIMESTAMP('2021-05-24 15:56:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T12:56:27.946Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574071 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T12:56:28.020Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1115)
;

-- 2021-05-24T12:57:53.736Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541324,TO_TIMESTAMP('2021-05-24 15:57:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Invoice Verification Run Status',TO_TIMESTAMP('2021-05-24 15:57:53','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-05-24T12:57:53.938Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541324 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-05-24T12:59:00.675Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541324,542613,TO_TIMESTAMP('2021-05-24 15:59:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Geplant',TO_TIMESTAMP('2021-05-24 15:59:00','YYYY-MM-DD HH24:MI:SS'),100,'P','Planned')
;

-- 2021-05-24T12:59:00.767Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542613 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-24T12:59:08.034Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 15:59:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542613
;

-- 2021-05-24T12:59:12.854Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 15:59:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542613
;

-- 2021-05-24T12:59:26.111Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Planned',Updated=TO_TIMESTAMP('2021-05-24 15:59:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542613
;

-- 2021-05-24T12:59:59.032Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541324,542614,TO_TIMESTAMP('2021-05-24 15:59:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Läuft',TO_TIMESTAMP('2021-05-24 15:59:58','YYYY-MM-DD HH24:MI:SS'),100,'R','Running')
;

-- 2021-05-24T12:59:59.112Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542614 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-24T13:00:10.120Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Running',Updated=TO_TIMESTAMP('2021-05-24 16:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542614
;

-- 2021-05-24T13:00:14.220Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 16:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542614
;

-- 2021-05-24T13:00:18.090Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 16:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542614
;

-- 2021-05-24T13:00:44.590Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541324,542615,TO_TIMESTAMP('2021-05-24 16:00:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Beendet',TO_TIMESTAMP('2021-05-24 16:00:44','YYYY-MM-DD HH24:MI:SS'),100,'F','Finished')
;

-- 2021-05-24T13:00:44.671Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542615 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-24T13:00:52.359Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 16:00:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542615
;

-- 2021-05-24T13:00:57.488Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 16:00:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542615
;

-- 2021-05-24T13:01:08.764Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Finished',Updated=TO_TIMESTAMP('2021-05-24 16:01:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542615
;

-- 2021-05-24T13:02:29.958Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574072,3020,0,17,541324,541664,'Status',TO_TIMESTAMP('2021-05-24 16:02:29','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,1,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Status',0,0,TO_TIMESTAMP('2021-05-24 16:02:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:02:30.039Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574072 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:02:30.116Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(3020)
;

-- 2021-05-24T13:02:56.369Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-05-24 16:02:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574072
;


-- 2021-05-24T13:05:14.523Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,579241,0,541147,TO_TIMESTAMP('2021-05-24 16:05:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Invoice Verification Run','N',TO_TIMESTAMP('2021-05-24 16:05:13','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2021-05-24T13:05:14.762Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541147 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2021-05-24T13:05:14.839Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(579241)
;

-- 2021-05-24T13:05:14.884Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541147
;

-- 2021-05-24T13:05:14.926Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541147)
;

-- 2021-05-24T13:06:11.843Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579241,0,543958,541664,541147,'Y',TO_TIMESTAMP('2021-05-24 16:06:11','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','C_Invoice_Verification_Run','Y','N','Y','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Invoice Verification Run','N',10,1,TO_TIMESTAMP('2021-05-24 16:06:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:06:11.969Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543958 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-05-24T13:06:12.007Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579241)
;

-- 2021-05-24T13:06:12.043Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543958)
;

-- 2021-05-24T13:06:45.986Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungslauf', PrintName='Rechnung-Überprüfungslauf',Updated=TO_TIMESTAMP('2021-05-24 16:06:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579241 AND AD_Language='de_CH'
;

-- 2021-05-24T13:06:46.026Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579241,'de_CH')
;

-- 2021-05-24T13:06:53.530Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungslauf', PrintName='Rechnung-Überprüfungslauf',Updated=TO_TIMESTAMP('2021-05-24 16:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579241 AND AD_Language='de_DE'
;

-- 2021-05-24T13:06:53.568Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579241,'de_DE')
;

-- 2021-05-24T13:06:53.646Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579241,'de_DE')
;

-- 2021-05-24T13:06:53.685Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Invoice_Verification_Run_ID', Name='Rechnung-Überprüfungslauf', Description=NULL, Help=NULL WHERE AD_Element_ID=579241
;

-- 2021-05-24T13:06:53.724Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Verification_Run_ID', Name='Rechnung-Überprüfungslauf', Description=NULL, Help=NULL, AD_Element_ID=579241 WHERE UPPER(ColumnName)='C_INVOICE_VERIFICATION_RUN_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-24T13:06:53.763Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Verification_Run_ID', Name='Rechnung-Überprüfungslauf', Description=NULL, Help=NULL WHERE AD_Element_ID=579241 AND IsCentrallyMaintained='Y'
;

-- 2021-05-24T13:06:53.799Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-Überprüfungslauf', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579241) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579241)
;

-- 2021-05-24T13:06:53.889Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung-Überprüfungslauf', Name='Rechnung-Überprüfungslauf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579241)
;

-- 2021-05-24T13:06:53.927Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-Überprüfungslauf', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579241
;

-- 2021-05-24T13:06:53.970Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-Überprüfungslauf', Description=NULL, Help=NULL WHERE AD_Element_ID = 579241
;

-- 2021-05-24T13:06:54.010Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungslauf', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579241
;

-- 2021-05-24T13:07:00.270Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 16:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579241 AND AD_Language='en_US'
;

-- 2021-05-24T13:07:00.308Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579241,'en_US')
;

-- 2021-05-24T13:07:09.249Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rechnung-Überprüfungslauf', PrintName='Rechnung-Überprüfungslauf', WEBUI_NameBrowse='Rechnung-Überprüfungslauf',Updated=TO_TIMESTAMP('2021-05-24 16:07:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579241 AND AD_Language='nl_NL'
;

-- 2021-05-24T13:07:09.287Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579241,'nl_NL')
;

-- 2021-05-24T13:07:17.410Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Rechnung-Überprüfungslauf',Updated=TO_TIMESTAMP('2021-05-24 16:07:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579241 AND AD_Language='de_DE'
;

-- 2021-05-24T13:07:17.447Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579241,'de_DE')
;

-- 2021-05-24T13:07:17.526Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579241,'de_DE')
;

-- 2021-05-24T13:07:17.563Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungslauf', Description = NULL, WEBUI_NameBrowse = 'Rechnung-Überprüfungslauf', WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579241
;

-- 2021-05-24T13:07:22.212Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Rechnung-Überprüfungslauf',Updated=TO_TIMESTAMP('2021-05-24 16:07:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579241 AND AD_Language='de_CH'
;

-- 2021-05-24T13:07:22.250Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579241,'de_CH')
;

-- 2021-05-24T13:07:32.653Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Invoice Verification Run',Updated=TO_TIMESTAMP('2021-05-24 16:07:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579241 AND AD_Language='en_US'
;

-- 2021-05-24T13:07:32.692Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579241,'en_US')
;

-- 2021-05-24T13:07:49.252Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574058,646797,0,543958,TO_TIMESTAMP('2021-05-24 16:07:48','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-05-24 16:07:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:07:49.340Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646797 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T13:07:49.381Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2021-05-24T13:07:49.778Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646797
;

-- 2021-05-24T13:07:49.812Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646797)
;

-- 2021-05-24T13:07:50.364Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574059,646798,0,543958,TO_TIMESTAMP('2021-05-24 16:07:49','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2021-05-24 16:07:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:07:50.485Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646798 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T13:07:50.525Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2021-05-24T13:07:50.695Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646798
;

-- 2021-05-24T13:07:50.731Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646798)
;

-- 2021-05-24T13:07:51.257Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574062,646799,0,543958,TO_TIMESTAMP('2021-05-24 16:07:50','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-05-24 16:07:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:07:51.335Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646799 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T13:07:51.372Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2021-05-24T13:07:51.579Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646799
;

-- 2021-05-24T13:07:51.616Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646799)
;

-- 2021-05-24T13:07:52.133Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574065,646800,0,543958,TO_TIMESTAMP('2021-05-24 16:07:51','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Rechnung-Überprüfungslauf',TO_TIMESTAMP('2021-05-24 16:07:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:07:52.216Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646800 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T13:07:52.255Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579241)
;

-- 2021-05-24T13:07:52.292Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646800
;

-- 2021-05-24T13:07:52.328Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646800)
;

-- 2021-05-24T13:07:52.849Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574066,646801,0,543958,TO_TIMESTAMP('2021-05-24 16:07:52','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Rechnung-Überprüfungssatz',TO_TIMESTAMP('2021-05-24 16:07:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:07:52.932Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646801 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T13:07:52.974Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579233)
;

-- 2021-05-24T13:07:53.017Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646801
;

-- 2021-05-24T13:07:53.054Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646801)
;

-- 2021-05-24T13:07:53.585Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574067,646802,0,543958,TO_TIMESTAMP('2021-05-24 16:07:53','YYYY-MM-DD HH24:MI:SS'),100,'Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.',7,'D','Y','N','N','N','N','N','N','N','Abw. Bewegungsdatum',TO_TIMESTAMP('2021-05-24 16:07:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:07:53.680Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646802 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T13:07:53.718Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579242)
;

-- 2021-05-24T13:07:53.755Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646802
;

-- 2021-05-24T13:07:53.790Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646802)
;

-- 2021-05-24T13:07:54.327Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574068,646803,0,543958,TO_TIMESTAMP('2021-05-24 16:07:53','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','DateStart',TO_TIMESTAMP('2021-05-24 16:07:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:07:54.423Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646803 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T13:07:54.462Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53280)
;

-- 2021-05-24T13:07:54.500Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646803
;

-- 2021-05-24T13:07:54.538Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646803)
;

-- 2021-05-24T13:07:55.069Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574069,646804,0,543958,TO_TIMESTAMP('2021-05-24 16:07:54','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Date End',TO_TIMESTAMP('2021-05-24 16:07:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:07:55.145Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T13:07:55.183Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579243)
;

-- 2021-05-24T13:07:55.220Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646804
;

-- 2021-05-24T13:07:55.254Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646804)
;

-- 2021-05-24T13:07:55.773Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574070,646805,0,543958,TO_TIMESTAMP('2021-05-24 16:07:55','YYYY-MM-DD HH24:MI:SS'),100,'Instanz eines Prozesses',10,'D','Y','N','N','N','N','N','N','N','Prozess-Instanz',TO_TIMESTAMP('2021-05-24 16:07:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:07:55.854Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T13:07:55.893Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(114)
;

-- 2021-05-24T13:07:55.937Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646805
;

-- 2021-05-24T13:07:55.972Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646805)
;

-- 2021-05-24T13:07:56.519Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574071,646806,0,543958,TO_TIMESTAMP('2021-05-24 16:07:56','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information',255,'D','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','N','N','N','N','N','Notiz',TO_TIMESTAMP('2021-05-24 16:07:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:07:56.595Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646806 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T13:07:56.631Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115)
;

-- 2021-05-24T13:07:56.670Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646806
;

-- 2021-05-24T13:07:56.705Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646806)
;

-- 2021-05-24T13:07:57.226Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574072,646807,0,543958,TO_TIMESTAMP('2021-05-24 16:07:56','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','','Y','N','N','N','N','N','N','N','Status',TO_TIMESTAMP('2021-05-24 16:07:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:07:57.303Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646807 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T13:07:57.343Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3020)
;

-- 2021-05-24T13:07:57.380Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646807
;

-- 2021-05-24T13:07:57.417Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646807)
;

-- 2021-05-24T13:08:13.436Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543958,543111,TO_TIMESTAMP('2021-05-24 16:08:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-05-24 16:08:13','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-05-24T13:08:13.510Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543111 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-05-24T13:08:13.884Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543913,543111,TO_TIMESTAMP('2021-05-24 16:08:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-05-24 16:08:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:08:14.301Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543914,543111,TO_TIMESTAMP('2021-05-24 16:08:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-05-24 16:08:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:08:14.727Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,543913,545897,TO_TIMESTAMP('2021-05-24 16:08:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2021-05-24 16:08:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:10:01.426Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646801,0,543958,545897,585249,'F',TO_TIMESTAMP('2021-05-24 16:10:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnung-Überprüfungssatz',10,0,0,TO_TIMESTAMP('2021-05-24 16:10:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:10:41.286Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646802,0,543958,545897,585250,'F',TO_TIMESTAMP('2021-05-24 16:10:40','YYYY-MM-DD HH24:MI:SS'),100,'Abweichendes Bewegungsdatum das benutzt wird, wenn die Rechnungszeilen überprüft werden. Wenn leer wird das jeweilige Datum der Rechnung benutzt.','Y','N','N','Y','N','N','N',0,'Abw. Bewegungsdatum',20,0,0,TO_TIMESTAMP('2021-05-24 16:10:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:11:22.800Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646807,0,543958,545897,585251,'F',TO_TIMESTAMP('2021-05-24 16:11:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Status',30,0,0,TO_TIMESTAMP('2021-05-24 16:11:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:11:52.263Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543913,545898,TO_TIMESTAMP('2021-05-24 16:11:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','note',20,TO_TIMESTAMP('2021-05-24 16:11:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:12:09.031Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646806,0,543958,545898,585252,'F',TO_TIMESTAMP('2021-05-24 16:12:08','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','Y','N','N','N',0,'Notiz',10,0,0,TO_TIMESTAMP('2021-05-24 16:12:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:12:22.182Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543914,545899,TO_TIMESTAMP('2021-05-24 16:12:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','flag',10,TO_TIMESTAMP('2021-05-24 16:12:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:12:38.258Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646799,0,543958,545899,585253,'F',TO_TIMESTAMP('2021-05-24 16:12:37','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2021-05-24 16:12:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:12:48.571Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543914,545900,TO_TIMESTAMP('2021-05-24 16:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',20,TO_TIMESTAMP('2021-05-24 16:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:13:07.572Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646803,0,543958,545900,585254,'F',TO_TIMESTAMP('2021-05-24 16:13:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DateStart',10,0,0,TO_TIMESTAMP('2021-05-24 16:13:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:13:25.653Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646804,0,543958,545900,585255,'F',TO_TIMESTAMP('2021-05-24 16:13:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Date End',20,0,0,TO_TIMESTAMP('2021-05-24 16:13:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:13:39.446Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543914,545901,TO_TIMESTAMP('2021-05-24 16:13:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2021-05-24 16:13:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:14:01.087Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646798,0,543958,545901,585256,'F',TO_TIMESTAMP('2021-05-24 16:14:00','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2021-05-24 16:14:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:14:19.633Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646797,0,543958,545901,585257,'F',TO_TIMESTAMP('2021-05-24 16:14:19','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2021-05-24 16:14:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:15:10.984Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-05-24 16:15:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585249
;

-- 2021-05-24T13:15:11.176Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-05-24 16:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585250
;

-- 2021-05-24T13:15:11.360Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-05-24 16:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585255
;

-- 2021-05-24T13:15:11.555Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-05-24 16:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585254
;

-- 2021-05-24T13:15:11.739Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-05-24 16:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585251
;

-- 2021-05-24T13:15:11.927Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-05-24 16:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585256
;

-- 2021-05-24T13:16:28.184Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,579241,541715,0,541147,TO_TIMESTAMP('2021-05-24 16:16:27','YYYY-MM-DD HH24:MI:SS'),100,'D','_InvoiceVerificationRun','Y','N','N','N','N','Rechnung-Überprüfungslauf',TO_TIMESTAMP('2021-05-24 16:16:27','YYYY-MM-DD HH24:MI:SS'),100,'Rechnung-Überprüfungslauf')
;

-- 2021-05-24T13:16:28.385Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541715 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-05-24T13:16:28.425Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541715, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541715)
;

-- 2021-05-24T13:16:28.462Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(579241)
;

-- 2021-05-24T13:16:38.908Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541712, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541713 AND AD_Tree_ID=10
;

-- 2021-05-24T13:16:38.945Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541712, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541714 AND AD_Tree_ID=10
;

-- 2021-05-24T13:16:38.981Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541712, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541715 AND AD_Tree_ID=10
;

-- 2021-05-24T13:22:17.142Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-24 16:22:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646804
;

-- 2021-05-24T13:24:16.965Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Enddatum', PrintName='Enddatum',Updated=TO_TIMESTAMP('2021-05-24 16:24:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579243 AND AD_Language='de_CH'
;

-- 2021-05-24T13:24:17.003Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579243,'de_CH')
;

-- 2021-05-24T13:24:23.896Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Enddatum', PrintName='Enddatum',Updated=TO_TIMESTAMP('2021-05-24 16:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579243 AND AD_Language='de_DE'
;

-- 2021-05-24T13:24:23.935Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579243,'de_DE')
;

-- 2021-05-24T13:24:24.032Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579243,'de_DE')
;

-- 2021-05-24T13:24:24.072Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DateEnd', Name='Enddatum', Description=NULL, Help=NULL WHERE AD_Element_ID=579243
;

-- 2021-05-24T13:24:24.111Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DateEnd', Name='Enddatum', Description=NULL, Help=NULL, AD_Element_ID=579243 WHERE UPPER(ColumnName)='DATEEND' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-24T13:24:24.149Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DateEnd', Name='Enddatum', Description=NULL, Help=NULL WHERE AD_Element_ID=579243 AND IsCentrallyMaintained='Y'
;

-- 2021-05-24T13:24:24.186Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Enddatum', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579243) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579243)
;

-- 2021-05-24T13:24:24.240Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Enddatum', Name='Enddatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579243)
;

-- 2021-05-24T13:24:24.278Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Enddatum', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579243
;

-- 2021-05-24T13:24:24.315Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Enddatum', Description=NULL, Help=NULL WHERE AD_Element_ID = 579243
;

-- 2021-05-24T13:24:24.352Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Enddatum', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579243
;

-- 2021-05-24T13:24:31.001Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 16:24:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579243 AND AD_Language='en_US'
;

-- 2021-05-24T13:24:31.040Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579243,'en_US')
;

-- 2021-05-24T13:24:37.771Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Enddatum', PrintName='Enddatum',Updated=TO_TIMESTAMP('2021-05-24 16:24:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579243 AND AD_Language='nl_NL'
;

-- 2021-05-24T13:24:37.811Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579243,'nl_NL')
;

-- 2021-05-24T13:25:22.625Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Startdatum', PrintName='Startdatum',Updated=TO_TIMESTAMP('2021-05-24 16:25:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53280 AND AD_Language='fr_CH'
;

-- 2021-05-24T13:25:22.663Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53280,'fr_CH')
;

-- 2021-05-24T13:25:33.772Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Date Start', PrintName='Date Start',Updated=TO_TIMESTAMP('2021-05-24 16:25:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53280 AND AD_Language='en_GB'
;

-- 2021-05-24T13:25:33.811Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53280,'en_GB')
;

-- 2021-05-24T13:25:40.005Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Startdatum', PrintName='Startdatum',Updated=TO_TIMESTAMP('2021-05-24 16:25:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53280 AND AD_Language='de_CH'
;

-- 2021-05-24T13:25:40.043Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53280,'de_CH')
;

-- 2021-05-24T13:25:54.213Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Startdatum', PrintName='Startdatum',Updated=TO_TIMESTAMP('2021-05-24 16:25:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53280 AND AD_Language='nl_NL'
;

-- 2021-05-24T13:25:54.254Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53280,'nl_NL')
;

-- 2021-05-24T13:26:19.196Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-24 16:26:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646805
;

-- 2021-05-24T13:26:25.401Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-24 16:26:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646807
;

-- 2021-05-24T13:26:34.426Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-24 16:26:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646803
;

-- Set fields from C_Invoice_Verification_SetLine window

-- 2021-05-24T13:33:26.936Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-24 16:33:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646787
;

-- 2021-05-24T13:33:28.816Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-24 16:33:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646788
;

-- 2021-05-24T13:33:31.851Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-24 16:33:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646789
;

-- 2021-05-24T13:33:33.895Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-24 16:33:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646790
;

-- 2021-05-24T13:33:35.913Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-24 16:33:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646791
;

-- 2021-05-24T13:33:37.750Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-24 16:33:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646792
;

-- 2021-05-24T13:33:42.964Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-24 16:33:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646793
;

-- 2021-05-24T13:33:46.351Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2021-05-24 16:33:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646789
;

-- 2021-05-24T13:33:48.859Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-24 16:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646794
;

-- 2021-05-24T13:33:53.349Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-24 16:33:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646795
;



-- 2021-05-25T06:00:04.421Z
-- URL zum Konzept
UPDATE AD_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2021-05-25 09:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574041
;

-- 2021-05-25T06:08:27.204Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-05-25 09:08:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574056
;

-- 2021-05-25T06:09:15.561Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579256,0,TO_TIMESTAMP('2021-05-25 09:09:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice line','Invoice line',TO_TIMESTAMP('2021-05-25 09:09:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-25T06:09:15.786Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579256 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-25T06:09:33.399Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnungzeile', PrintName='Rechnungzeile',Updated=TO_TIMESTAMP('2021-05-25 09:09:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579256 AND AD_Language='de_CH'
;

-- 2021-05-25T06:09:33.437Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579256,'de_CH')
;

-- 2021-05-25T06:09:41.068Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnungzeile', PrintName='Rechnungzeile',Updated=TO_TIMESTAMP('2021-05-25 09:09:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579256 AND AD_Language='de_DE'
;

-- 2021-05-25T06:09:41.127Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579256,'de_DE')
;

-- 2021-05-25T06:09:41.207Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579256,'de_DE')
;

-- 2021-05-25T06:09:41.242Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Rechnungzeile', Description=NULL, Help=NULL WHERE AD_Element_ID=579256
;

-- 2021-05-25T06:09:41.279Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rechnungzeile', Description=NULL, Help=NULL WHERE AD_Element_ID=579256 AND IsCentrallyMaintained='Y'
;

-- 2021-05-25T06:09:41.315Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnungzeile', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579256) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579256)
;

-- 2021-05-25T06:09:41.367Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnungzeile', Name='Rechnungzeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579256)
;

-- 2021-05-25T06:09:41.408Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnungzeile', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579256
;

-- 2021-05-25T06:09:41.445Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnungzeile', Description=NULL, Help=NULL WHERE AD_Element_ID = 579256
;

-- 2021-05-25T06:09:41.481Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnungzeile', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579256
;

-- 2021-05-25T06:09:47.092Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-25 09:09:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579256 AND AD_Language='en_US'
;

-- 2021-05-25T06:09:47.129Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579256,'en_US')
;

-- 2021-05-25T06:09:53.706Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rechnungzeile', PrintName='Rechnungzeile',Updated=TO_TIMESTAMP('2021-05-25 09:09:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579256 AND AD_Language='nl_NL'
;

-- 2021-05-25T06:09:53.744Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579256,'nl_NL')
;

-- 2021-05-25T06:10:47.824Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=579256, Description=NULL, Help=NULL, Name='Rechnungzeile',Updated=TO_TIMESTAMP('2021-05-25 09:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646793
;

-- 2021-05-25T06:10:47.902Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579256)
;

-- 2021-05-25T06:10:47.940Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646793
;

-- 2021-05-25T06:10:47.978Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646793)
;

-- 2021-05-25T06:32:40.137Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Startdatum', PrintName='Startdatum',Updated=TO_TIMESTAMP('2021-05-25 09:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53280 AND AD_Language='de_DE'
;

-- 2021-05-25T06:32:40.177Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53280,'de_DE')
;

-- 2021-05-25T06:32:40.306Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(53280,'de_DE')
;

-- 2021-05-25T06:32:40.342Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DateStart', Name='Startdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=53280
;

-- 2021-05-25T06:32:40.381Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DateStart', Name='Startdatum', Description=NULL, Help=NULL, AD_Element_ID=53280 WHERE UPPER(ColumnName)='DATESTART' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-25T06:32:40.419Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DateStart', Name='Startdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=53280 AND IsCentrallyMaintained='Y'
;

-- 2021-05-25T06:32:40.455Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Startdatum', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53280) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 53280)
;

-- 2021-05-25T06:32:40.504Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Startdatum', Name='Startdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53280)
;

-- 2021-05-25T06:32:40.547Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Startdatum', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 53280
;

-- 2021-05-25T06:32:40.593Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Startdatum', Description=NULL, Help=NULL WHERE AD_Element_ID = 53280
;

-- 2021-05-25T06:32:40.630Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Startdatum', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 53280
;

-- Start Creation of Table and Window C_Invoice_Verification_RunLine


-- 2021-05-24T13:42:53.779Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541665,'N',TO_TIMESTAMP('2021-05-24 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Invoice Verification RunLine','NP','L','C_Invoice_Verification_RunLine','DTI',TO_TIMESTAMP('2021-05-24 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:42:54.005Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541665 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-05-24T13:42:54.425Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555408,TO_TIMESTAMP('2021-05-24 16:42:54','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Invoice_Verification_RunLine',1,'Y','N','Y','Y','C_Invoice_Verification_RunLine','N',1000000,TO_TIMESTAMP('2021-05-24 16:42:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:42:54.597Z
-- URL zum Konzept
CREATE SEQUENCE C_INVOICE_VERIFICATION_RUNLINE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-05-24T13:43:24.153Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574074,102,0,19,541665,'AD_Client_ID',TO_TIMESTAMP('2021-05-24 16:43:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-05-24 16:43:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:43:24.416Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574074 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:43:24.497Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- 2021-05-24T13:43:26.692Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574075,113,0,30,541665,'AD_Org_ID',TO_TIMESTAMP('2021-05-24 16:43:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Organisation',0,TO_TIMESTAMP('2021-05-24 16:43:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:43:26.888Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574075 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:43:26.968Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- 2021-05-24T13:43:29.252Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574076,245,0,16,541665,'Created',TO_TIMESTAMP('2021-05-24 16:43:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-05-24 16:43:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:43:29.444Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574076 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:43:29.523Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- 2021-05-24T13:43:31.720Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574077,246,0,18,110,541665,'CreatedBy',TO_TIMESTAMP('2021-05-24 16:43:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-05-24 16:43:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:43:31.919Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574077 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:43:31.995Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- 2021-05-24T13:43:34.216Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574078,348,0,20,541665,'IsActive',TO_TIMESTAMP('2021-05-24 16:43:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-05-24 16:43:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:43:34.438Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574078 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:43:34.520Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- 2021-05-24T13:43:37.052Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574079,607,0,16,541665,'Updated',TO_TIMESTAMP('2021-05-24 16:43:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-05-24 16:43:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:43:37.262Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574079 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:43:37.338Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- 2021-05-24T13:43:39.575Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574080,608,0,18,110,541665,'UpdatedBy',TO_TIMESTAMP('2021-05-24 16:43:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-05-24 16:43:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:43:39.776Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574080 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:43:39.855Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2021-05-24T13:43:42.651Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579245,0,'C_Invoice_Verification_RunLine_ID',TO_TIMESTAMP('2021-05-24 16:43:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice Verification RunLine','Invoice Verification RunLine',TO_TIMESTAMP('2021-05-24 16:43:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:43:42.899Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579245 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T13:43:44.629Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574081,579245,0,13,541665,'C_Invoice_Verification_RunLine_ID',TO_TIMESTAMP('2021-05-24 16:43:42','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','Invoice Verification RunLine',0,TO_TIMESTAMP('2021-05-24 16:43:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:43:44.715Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574081 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:43:44.795Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579245)
;

-- 2021-05-24T13:45:54.350Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574082,579241,0,19,541665,'C_Invoice_Verification_Run_ID',TO_TIMESTAMP('2021-05-24 16:45:53','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnung-Überprüfungslauf',0,0,TO_TIMESTAMP('2021-05-24 16:45:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:45:54.905Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574082 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:45:54.992Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579241)
;

-- 2021-05-24T13:46:23.812Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-05-24 16:46:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574082
;

-- 2021-05-24T13:47:33.666Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574083,579233,0,19,541665,'C_Invoice_Verification_Set_ID',TO_TIMESTAMP('2021-05-24 16:47:33','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Rechnung-Überprüfungssatz',0,0,TO_TIMESTAMP('2021-05-24 16:47:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:47:33.857Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574083 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:47:33.935Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579233)
;

-- 2021-05-24T13:52:11.806Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574084,579236,0,19,541665,'C_Invoice_Verification_SetLine_ID',TO_TIMESTAMP('2021-05-24 16:52:09','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Invoice Verification Element',0,0,TO_TIMESTAMP('2021-05-24 16:52:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:52:12.013Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574084 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:52:12.089Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579236)
;

-- 2021-05-24T13:52:58.905Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579246,0,'Run_Tax_ID',TO_TIMESTAMP('2021-05-24 16:52:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Run Tax ID','Run Tax ID',TO_TIMESTAMP('2021-05-24 16:52:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:52:59.140Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579246 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T13:53:33.614Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574085,579246,0,19,541665,'Run_Tax_ID',TO_TIMESTAMP('2021-05-24 16:53:33','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Run Tax ID',0,0,TO_TIMESTAMP('2021-05-24 16:53:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:53:33.686Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574085 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:53:33.757Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579246)
;

-- 2021-05-24T13:54:19.217Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579247,0,'IsTaxIdMatch',TO_TIMESTAMP('2021-05-24 16:54:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IsTaxIdMatch','IsTaxIdMatch',TO_TIMESTAMP('2021-05-24 16:54:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:54:19.415Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579247 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T13:56:39.203Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574086,579247,0,20,541665,'IsTaxIdMatch',TO_TIMESTAMP('2021-05-24 16:56:38','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','Y','N',0,'IsTaxIdMatch',0,0,TO_TIMESTAMP('2021-05-24 16:56:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:56:39.303Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574086 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:56:39.381Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579247)
;

-- 2021-05-24T13:57:47.083Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579248,0,'IsTaxRateMatch',TO_TIMESTAMP('2021-05-24 16:57:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Is Tax Rate Match','Is Tax Rate Match',TO_TIMESTAMP('2021-05-24 16:57:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:57:47.266Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579248 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T13:58:24.216Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574087,579248,0,20,541665,'IsTaxRateMatch',TO_TIMESTAMP('2021-05-24 16:58:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','Y','N',0,'Is Tax Rate Match',0,0,TO_TIMESTAMP('2021-05-24 16:58:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:58:24.309Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574087 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:58:24.385Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579248)
;

-- 2021-05-24T13:59:07.692Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579249,0,'IsTaxBoilerPlateMatch',TO_TIMESTAMP('2021-05-24 16:59:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Is Tax Boiler Plate Match ','Is Tax Boiler Plate Match ',TO_TIMESTAMP('2021-05-24 16:59:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:59:07.892Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579249 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T14:00:13.450Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574088,579249,0,20,541665,'IsTaxBoilerPlateMatch',TO_TIMESTAMP('2021-05-24 17:00:12','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','Y','N',0,'Is Tax Boiler Plate Match ',0,0,TO_TIMESTAMP('2021-05-24 17:00:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T14:00:13.528Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574088 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T14:00:13.602Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579249)
;


-- 2021-05-25T07:16:50.733Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuer', PrintName='Steuer',Updated=TO_TIMESTAMP('2021-05-25 10:16:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579238 AND AD_Language='de_CH'
;

-- 2021-05-25T07:16:50.779Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579238,'de_CH')
;

-- 2021-05-25T07:17:14.687Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuer', PrintName='Steuer',Updated=TO_TIMESTAMP('2021-05-25 10:17:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579238 AND AD_Language='de_DE'
;

-- 2021-05-25T07:17:14.726Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579238,'de_DE')
;

-- 2021-05-25T07:17:14.808Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579238,'de_DE')
;

-- 2021-05-25T07:17:14.854Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_InvoiceLine_Tax_ID', Name='Steuer', Description=NULL, Help=NULL WHERE AD_Element_ID=579238
;

-- 2021-05-25T07:17:14.891Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_InvoiceLine_Tax_ID', Name='Steuer', Description=NULL, Help=NULL, AD_Element_ID=579238 WHERE UPPER(ColumnName)='C_INVOICELINE_TAX_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-25T07:17:14.931Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_InvoiceLine_Tax_ID', Name='Steuer', Description=NULL, Help=NULL WHERE AD_Element_ID=579238 AND IsCentrallyMaintained='Y'
;

-- 2021-05-25T07:17:14.967Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Steuer', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579238) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579238)
;

-- 2021-05-25T07:17:15.023Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Steuer', Name='Steuer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579238)
;

-- 2021-05-25T07:17:15.060Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Steuer', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579238
;

-- 2021-05-25T07:17:15.100Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Steuer', Description=NULL, Help=NULL WHERE AD_Element_ID = 579238
;

-- 2021-05-25T07:17:15.138Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Steuer', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579238
;

-- 2021-05-25T07:17:28.335Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Tax', PrintName='Tax',Updated=TO_TIMESTAMP('2021-05-25 10:17:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579238 AND AD_Language='en_US'
;

-- 2021-05-25T07:17:28.372Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579238,'en_US')
;

-- 2021-05-25T07:17:36.916Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Steuer', PrintName='Steuer',Updated=TO_TIMESTAMP('2021-05-25 10:17:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579238 AND AD_Language='nl_NL'
;

-- 2021-05-25T07:17:36.955Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579238,'nl_NL')
;

-- 2021-05-25T07:18:43.779Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=158,Updated=TO_TIMESTAMP('2021-05-25 10:18:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574056
;

-- 2021-05-25T07:19:57.745Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=60,Updated=TO_TIMESTAMP('2021-05-25 10:19:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574056
;

-- 2021-05-25T07:20:53.583Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2021-05-25 10:20:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574056
;

-- 2021-05-25T07:22:27.119Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2021-05-25 10:22:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574056
;

-- 2021-05-25T07:24:14.669Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=60,Updated=TO_TIMESTAMP('2021-05-25 10:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574056
;

-- 2021-05-25T07:25:54.037Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, FieldLength=10,Updated=TO_TIMESTAMP('2021-05-25 10:25:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574056
;

-- 2021-05-25T07:28:59.908Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=585242
;

-- 2021-05-25T07:29:19.462Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646795
;

-- 2021-05-25T07:29:19.499Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=646795
;

-- 2021-05-25T07:29:19.716Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=646795
;

-- 2021-05-25T07:29:31.961Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574056
;

-- 2021-05-25T07:29:32.208Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=574056
;

-- 2021-05-25T07:31:32.743Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574108,579238,0,18,158,541663,'C_InvoiceLine_Tax_ID',TO_TIMESTAMP('2021-05-25 10:31:32','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Steuer',0,0,TO_TIMESTAMP('2021-05-25 10:31:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-25T07:31:32.956Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574108 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-25T07:31:33.034Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579238)
;

-- 2021-05-25T07:32:02.872Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-05-25 10:32:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574108
;

-- 2021-05-25T07:32:50.069Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=19, IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2021-05-25 10:32:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574108
;

-- 2021-05-25T07:34:24.521Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574108
;

-- 2021-05-25T07:34:24.743Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=574108
;

-- 2021-05-25T07:34:53.473Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_invoice_verification_setline','C_Invoice_Verification_Set_ID','NUMERIC(10)',null,null)
;

-- 2021-05-25T07:36:00.063Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574109,579238,0,30,541663,'C_InvoiceLine_Tax_ID',TO_TIMESTAMP('2021-05-25 10:35:59','YYYY-MM-DD HH24:MI:SS'),100,'U',10,'Y','Y','N','N','N','N','N','N','N','N','Y','Steuer',TO_TIMESTAMP('2021-05-25 10:35:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-25T07:36:00.254Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574109 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-25T07:36:00.342Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579238)
;

-- 2021-05-25T07:36:48.137Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574109
;

-- 2021-05-25T07:36:48.354Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=574109
;

-- 2021-05-25T07:38:36.285Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574110,579238,0,19,541663,'C_InvoiceLine_Tax_ID',TO_TIMESTAMP('2021-05-25 10:38:35','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Steuer',0,0,TO_TIMESTAMP('2021-05-25 10:38:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-25T07:38:36.489Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574110 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-25T07:38:36.565Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579238)
;

-- 2021-05-25T07:39:11.772Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=158, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-05-25 10:39:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574110
;

-- 2021-05-25T07:39:21.945Z
-- URL zum Konzept
ALTER TABLE C_Invoice_Verification_SetLine ADD CONSTRAINT CInvoiceLineTax_CInvoiceVerificationSetLine FOREIGN KEY (C_InvoiceLine_Tax_ID) REFERENCES public.C_Tax DEFERRABLE INITIALLY DEFERRED
;

-- 2021-05-25T07:40:53.759Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574110,646821,0,543957,0,TO_TIMESTAMP('2021-05-25 10:40:53','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Steuer',10,10,0,1,1,TO_TIMESTAMP('2021-05-25 10:40:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-25T07:40:53.967Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646821 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-25T07:40:54.009Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579238)
;

-- 2021-05-25T07:40:54.048Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646821
;

-- 2021-05-25T07:40:54.084Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646821)
;

-- 2021-05-25T07:42:00.822Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646821,0,543957,545892,585270,'F',TO_TIMESTAMP('2021-05-25 10:42:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Steuer',40,0,0,TO_TIMESTAMP('2021-05-25 10:42:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-25T07:42:33.571Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-05-25 10:42:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585270
;

-- 2021-05-25T07:53:11.344Z
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='Y',Updated=TO_TIMESTAMP('2021-05-25 10:53:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543957
;

-- 2021-05-25T07:54:56.108Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2021-05-25 10:54:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646790
;

-- 2021-05-25T07:55:09.667Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2021-05-25 10:55:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646792
;

-- 2021-05-25T07:55:27.202Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2021-05-25 10:55:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646793
;

-- 2021-05-25T07:55:39.393Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2021-05-25 10:55:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646794
;

-- 2021-05-25T07:55:48.680Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2021-05-25 10:55:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646791
;

-- 2021-05-25T07:58:00.027Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=336, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-05-25 10:57:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574052
;

-- 2021-05-25T07:58:12.014Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_invoice_verification_setline','C_Invoice_ID','NUMERIC(10)',null,null)
;

-- 2021-05-25T07:58:57.953Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540191, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-05-25 10:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574054
;

-- 2021-05-25T07:59:13.436Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_invoice_verification_setline','C_InvoiceLine_ID','NUMERIC(10)',null,null)
;

-- 2021-05-25T08:10:10.715Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-25 11:10:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646821
;

-- 2021-05-25T08:10:13.550Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-25 11:10:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646789
;

-- 2021-05-25T08:10:15.632Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-25 11:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646790
;

-- 2021-05-25T08:10:17.855Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-25 11:10:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646792
;

-- 2021-05-25T08:10:19.790Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-25 11:10:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646793
;

-- 2021-05-25T08:10:21.803Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-25 11:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646794
;

-- 2021-05-25T08:10:25.110Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-25 11:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646791
;

-- 2021-05-25T08:10:34.626Z
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2021-05-25 11:10:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543957
;

-- 2021-05-25T06:58:27.275Z
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540595,0,541665,TO_TIMESTAMP('2021-05-25 09:58:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Invoice_Verification_Run_ID_Index','N',TO_TIMESTAMP('2021-05-25 09:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-25T06:58:27.322Z
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540595 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-05-25T06:58:55.857Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,574082,541106,540595,0,TO_TIMESTAMP('2021-05-25 09:58:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-05-25 09:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Add Trl for yes/no fields
-- 2021-06-08T06:38:52.551Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zugeordnete Steuer', PrintName='Zugeordnete Steuer',Updated=TO_TIMESTAMP('2021-06-08 08:38:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579246 AND AD_Language='de_CH'
;

-- 2021-06-08T06:38:52.571Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579246,'de_CH')
;

-- 2021-06-08T06:38:59.038Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zugeordnete Steuer', PrintName='Zugeordnete Steuer',Updated=TO_TIMESTAMP('2021-06-08 08:38:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579246 AND AD_Language='de_DE'
;

-- 2021-06-08T06:38:59.040Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579246,'de_DE')
;

-- 2021-06-08T06:38:59.058Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579246,'de_DE')
;

-- 2021-06-08T06:38:59.065Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Run_Tax_ID', Name='Zugeordnete Steuer', Description=NULL, Help=NULL WHERE AD_Element_ID=579246
;

-- 2021-06-08T06:38:59.067Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Run_Tax_ID', Name='Zugeordnete Steuer', Description=NULL, Help=NULL, AD_Element_ID=579246 WHERE UPPER(ColumnName)='RUN_TAX_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-08T06:38:59.072Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Run_Tax_ID', Name='Zugeordnete Steuer', Description=NULL, Help=NULL WHERE AD_Element_ID=579246 AND IsCentrallyMaintained='Y'
;

-- 2021-06-08T06:38:59.073Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Zugeordnete Steuer', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579246) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579246)
;

-- 2021-06-08T06:38:59.106Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zugeordnete Steuer', Name='Zugeordnete Steuer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579246)
;

-- 2021-06-08T06:38:59.107Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Zugeordnete Steuer', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579246
;

-- 2021-06-08T06:38:59.109Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Zugeordnete Steuer', Description=NULL, Help=NULL WHERE AD_Element_ID = 579246
;

-- 2021-06-08T06:38:59.109Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Zugeordnete Steuer', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579246
;

-- 2021-06-08T06:40:01.935Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Tax that was assigned to the invoice line during the verifiycation run.', IsTranslated='Y', Name='Assigned tax', PrintName='Assigned tax',Updated=TO_TIMESTAMP('2021-06-08 08:40:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579246 AND AD_Language='en_US'
;

-- 2021-06-08T06:40:01.935Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579246,'en_US')
;

-- 2021-06-08T06:40:09.578Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zugeordnete Steuer', PrintName='Zugeordnete Steuer',Updated=TO_TIMESTAMP('2021-06-08 08:40:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579246 AND AD_Language='nl_NL'
;

-- 2021-06-08T06:40:09.580Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579246,'nl_NL')
;

-- 2021-06-08T06:40:27.484Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.',Updated=TO_TIMESTAMP('2021-06-08 08:40:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579246 AND AD_Language='nl_NL'
;

-- 2021-06-08T06:40:27.485Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579246,'nl_NL')
;

-- 2021-06-08T06:40:34.081Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.',Updated=TO_TIMESTAMP('2021-06-08 08:40:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579246 AND AD_Language='de_DE'
;

-- 2021-06-08T06:40:34.086Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579246,'de_DE')
;

-- 2021-06-08T06:40:34.106Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579246,'de_DE')
;

-- 2021-06-08T06:40:34.107Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Run_Tax_ID', Name='Zugeordnete Steuer', Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', Help=NULL WHERE AD_Element_ID=579246
;

-- 2021-06-08T06:40:34.108Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Run_Tax_ID', Name='Zugeordnete Steuer', Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', Help=NULL, AD_Element_ID=579246 WHERE UPPER(ColumnName)='RUN_TAX_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-08T06:40:34.109Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Run_Tax_ID', Name='Zugeordnete Steuer', Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', Help=NULL WHERE AD_Element_ID=579246 AND IsCentrallyMaintained='Y'
;

-- 2021-06-08T06:40:34.110Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Zugeordnete Steuer', Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579246) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579246)
;

-- 2021-06-08T06:40:34.130Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Zugeordnete Steuer', Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579246
;

-- 2021-06-08T06:40:34.132Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Zugeordnete Steuer', Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', Help=NULL WHERE AD_Element_ID = 579246
;

-- 2021-06-08T06:40:34.133Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Zugeordnete Steuer', Description = 'Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579246
;

-- 2021-06-08T06:40:40.085Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.',Updated=TO_TIMESTAMP('2021-06-08 08:40:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579246 AND AD_Language='de_CH'
;

-- 2021-06-08T06:40:40.086Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579246,'de_CH')
;

-- 2021-06-08T06:43:52.371Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuer OK', PrintName='Steuer OK',Updated=TO_TIMESTAMP('2021-06-08 08:43:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='de_CH'
;

-- 2021-06-08T06:43:52.372Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'de_CH')
;

-- 2021-06-08T06:44:00.556Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuer OK', PrintName='Steuer OK',Updated=TO_TIMESTAMP('2021-06-08 08:44:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='de_DE'
;

-- 2021-06-08T06:44:00.557Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'de_DE')
;

-- 2021-06-08T06:44:00.570Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579247,'de_DE')
;

-- 2021-06-08T06:44:00.573Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsTaxIdMatch', Name='Steuer OK', Description=NULL, Help=NULL WHERE AD_Element_ID=579247
;

-- 2021-06-08T06:44:00.574Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxIdMatch', Name='Steuer OK', Description=NULL, Help=NULL, AD_Element_ID=579247 WHERE UPPER(ColumnName)='ISTAXIDMATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-08T06:44:00.576Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxIdMatch', Name='Steuer OK', Description=NULL, Help=NULL WHERE AD_Element_ID=579247 AND IsCentrallyMaintained='Y'
;

-- 2021-06-08T06:44:00.576Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Steuer OK', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579247) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579247)
;

-- 2021-06-08T06:44:00.600Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Steuer OK', Name='Steuer OK' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579247)
;

-- 2021-06-08T06:44:00.601Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Steuer OK', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579247
;

-- 2021-06-08T06:44:00.603Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Steuer OK', Description=NULL, Help=NULL WHERE AD_Element_ID = 579247
;

-- 2021-06-08T06:44:00.604Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Steuer OK', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579247
;

-- 2021-06-08T06:44:35.536Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuer OK', PrintName='Steuer OK',Updated=TO_TIMESTAMP('2021-06-08 08:44:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='nl_NL'
;

-- 2021-06-08T06:44:35.538Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'nl_NL')
;

-- 2021-06-08T06:45:33.701Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Indicates if the tax assigned during the run is the same that the invoice line has.', IsTranslated='Y', Name='Tax OK', PrintName='Tax OK',Updated=TO_TIMESTAMP('2021-06-08 08:45:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='en_US'
;

-- 2021-06-08T06:45:33.704Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'en_US')
;

-- 2021-06-08T06:46:52.346Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Reichnungszeile hat',Updated=TO_TIMESTAMP('2021-06-08 08:46:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='de_DE'
;

-- 2021-06-08T06:46:52.347Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'de_DE')
;

-- 2021-06-08T06:46:52.361Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579247,'de_DE')
;

-- 2021-06-08T06:46:52.363Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsTaxIdMatch', Name='Steuer OK', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Reichnungszeile hat.', Help=NULL WHERE AD_Element_ID=579247
;

-- 2021-06-08T06:46:52.364Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxIdMatch', Name='Steuer OK', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Reichnungszeile hat.', Help=NULL, AD_Element_ID=579247 WHERE UPPER(ColumnName)='ISTAXIDMATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-08T06:46:52.365Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxIdMatch', Name='Steuer OK', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Reichnungszeile hat.', Help=NULL WHERE AD_Element_ID=579247 AND IsCentrallyMaintained='Y'
;

-- 2021-06-08T06:46:52.366Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Steuer OK', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Reichnungszeile hat.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579247) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579247)
;

-- 2021-06-08T06:46:52.387Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Steuer OK', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Reichnungszeile hat.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579247
;

-- 2021-06-08T06:46:52.389Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Steuer OK', Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Reichnungszeile hat.', Help=NULL WHERE AD_Element_ID = 579247
;

-- 2021-06-08T06:46:52.389Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Steuer OK', Description = 'Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Reichnungszeile hat.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579247
;

-- 2021-06-08T06:46:59.352Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Reichnungszeile hat.',Updated=TO_TIMESTAMP('2021-06-08 08:46:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='de_CH'
;

-- 2021-06-08T06:46:59.355Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'de_CH')
;

-- 2021-06-08T06:47:04.164Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf die selbe Steuer zugeordnet wurde, die die Reichnungszeile hat.',Updated=TO_TIMESTAMP('2021-06-08 08:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579247 AND AD_Language='nl_NL'
;

-- 2021-06-08T06:47:04.167Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579247,'nl_NL')
;

-- 2021-06-08T06:55:20.158Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Indicates if the rate of the tax assigned during the run is the rate same that the invoice line''s tax has.', IsTranslated='Y', Name='Tax rate OK', PrintName='Tax rate OK',Updated=TO_TIMESTAMP('2021-06-08 08:55:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579248 AND AD_Language='en_US'
;

-- 2021-06-08T06:55:20.159Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579248,'en_US')
;

-- 2021-06-08T06:56:39.361Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', IsTranslated='Y', Name='Steuersatz OK', PrintName='Steuersatz OK',Updated=TO_TIMESTAMP('2021-06-08 08:56:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579248 AND AD_Language='nl_NL'
;

-- 2021-06-08T06:56:39.362Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579248,'nl_NL')
;

-- 2021-06-08T06:56:45.636Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat',Updated=TO_TIMESTAMP('2021-06-08 08:56:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579248 AND AD_Language='de_DE'
;

-- 2021-06-08T06:56:45.637Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579248,'de_DE')
;

-- 2021-06-08T06:56:45.654Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579248,'de_DE')
;

-- 2021-06-08T06:56:45.655Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsTaxRateMatch', Name='Is Tax Rate Match', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', Help=NULL WHERE AD_Element_ID=579248
;

-- 2021-06-08T06:56:45.656Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxRateMatch', Name='Is Tax Rate Match', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', Help=NULL, AD_Element_ID=579248 WHERE UPPER(ColumnName)='ISTAXRATEMATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-08T06:56:45.658Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxRateMatch', Name='Is Tax Rate Match', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', Help=NULL WHERE AD_Element_ID=579248 AND IsCentrallyMaintained='Y'
;

-- 2021-06-08T06:56:45.658Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Is Tax Rate Match', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579248) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579248)
;

-- 2021-06-08T06:56:45.681Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Is Tax Rate Match', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579248
;

-- 2021-06-08T06:56:45.683Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Is Tax Rate Match', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', Help=NULL WHERE AD_Element_ID = 579248
;

-- 2021-06-08T06:56:45.684Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Is Tax Rate Match', Description = 'Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579248
;

-- 2021-06-08T06:56:56.741Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuersatz OK', PrintName='Steuersatz OK',Updated=TO_TIMESTAMP('2021-06-08 08:56:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579248 AND AD_Language='de_DE'
;

-- 2021-06-08T06:56:56.742Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579248,'de_DE')
;

-- 2021-06-08T06:56:56.747Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579248,'de_DE')
;

-- 2021-06-08T06:56:56.748Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsTaxRateMatch', Name='Steuersatz OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', Help=NULL WHERE AD_Element_ID=579248
;

-- 2021-06-08T06:56:56.748Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxRateMatch', Name='Steuersatz OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', Help=NULL, AD_Element_ID=579248 WHERE UPPER(ColumnName)='ISTAXRATEMATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-08T06:56:56.749Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxRateMatch', Name='Steuersatz OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', Help=NULL WHERE AD_Element_ID=579248 AND IsCentrallyMaintained='Y'
;

-- 2021-06-08T06:56:56.749Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Steuersatz OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579248) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579248)
;

-- 2021-06-08T06:56:56.760Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Steuersatz OK', Name='Steuersatz OK' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579248)
;

-- 2021-06-08T06:56:56.761Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Steuersatz OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579248
;

-- 2021-06-08T06:56:56.762Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Steuersatz OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', Help=NULL WHERE AD_Element_ID = 579248
;

-- 2021-06-08T06:56:56.762Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Steuersatz OK', Description = 'Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579248
;

-- 2021-06-08T06:57:02.308Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuersatz OK', PrintName='Steuersatz OK',Updated=TO_TIMESTAMP('2021-06-08 08:57:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579248 AND AD_Language='de_CH'
;

-- 2021-06-08T06:57:02.309Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579248,'de_CH')
;

-- 2021-06-08T06:57:11.298Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben %-Steuersatz zugeordnet wurde, den die Reichnungszeile hat',Updated=TO_TIMESTAMP('2021-06-08 08:57:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579248 AND AD_Language='de_CH'
;

-- 2021-06-08T06:57:11.298Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579248,'de_CH')
;

-- 2021-06-08T07:13:19.349Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Reichnungszeile hat', IsTranslated='Y', Name='Textbaustein OK', PrintName='Textbaustein OK',Updated=TO_TIMESTAMP('2021-06-08 09:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579249 AND AD_Language='de_CH'
;

-- 2021-06-08T07:13:19.350Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579249,'de_CH')
;

-- 2021-06-08T07:13:40.136Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Reichnungszeile hat', IsTranslated='Y', Name='Textbaustein OK', PrintName='Textbaustein OK',Updated=TO_TIMESTAMP('2021-06-08 09:13:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579249 AND AD_Language='de_DE'
;

-- 2021-06-08T07:13:40.137Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579249,'de_DE')
;

-- 2021-06-08T07:13:40.143Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579249,'de_DE')
;

-- 2021-06-08T07:13:40.144Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsTaxBoilerPlateMatch', Name='Textbaustein OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Reichnungszeile hat', Help=NULL WHERE AD_Element_ID=579249
;

-- 2021-06-08T07:13:40.145Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxBoilerPlateMatch', Name='Textbaustein OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Reichnungszeile hat', Help=NULL, AD_Element_ID=579249 WHERE UPPER(ColumnName)='ISTAXBOILERPLATEMATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-08T07:13:40.148Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsTaxBoilerPlateMatch', Name='Textbaustein OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Reichnungszeile hat', Help=NULL WHERE AD_Element_ID=579249 AND IsCentrallyMaintained='Y'
;

-- 2021-06-08T07:13:40.148Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Textbaustein OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Reichnungszeile hat', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579249) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579249)
;

-- 2021-06-08T07:13:40.166Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Textbaustein OK', Name='Textbaustein OK' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579249)
;

-- 2021-06-08T07:13:40.167Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Textbaustein OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Reichnungszeile hat', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579249
;

-- 2021-06-08T07:13:40.169Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Textbaustein OK', Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Reichnungszeile hat', Help=NULL WHERE AD_Element_ID = 579249
;

-- 2021-06-08T07:13:40.170Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Textbaustein OK', Description = 'Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Reichnungszeile hat', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579249
;

-- 2021-06-08T07:14:00.340Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Sagt aus, ob beim Überprüfungs-Lauf ein Steuer-Datensatz mit dem selben Textbaustein zugeordnet wurde, den die Reichnungszeile hat', IsTranslated='Y', Name='Textbaustein OK', PrintName='Textbaustein OK',Updated=TO_TIMESTAMP('2021-06-08 09:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579249 AND AD_Language='nl_NL'
;

-- 2021-06-08T07:14:00.341Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579249,'nl_NL')
;

-- 2021-06-08T07:17:12.542Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Indicates if the text snippet of the tax assigned during the run is the rate same that the invoice line''s tax has.', IsTranslated='Y', Name='Text snippet OK', PrintName='Text snippet OK',Updated=TO_TIMESTAMP('2021-06-08 09:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579249 AND AD_Language='en_US'
;

-- 2021-06-08T07:17:12.543Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579249,'en_US')
;

-- 2021-06-08T07:22:23.629Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579305,0,'Run_Tax_Lookup_Log',TO_TIMESTAMP('2021-06-08 09:22:23','YYYY-MM-DD HH24:MI:SS'),100,'Log-Messages written by metasfresh during the assignment of the tax.','D','Y','Tax log','Tax log',TO_TIMESTAMP('2021-06-08 09:22:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T07:22:23.652Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579305 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-08T07:22:34.485Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-08 09:22:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579305 AND AD_Language='en_US'
;

-- 2021-06-08T07:22:34.486Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579305,'en_US')
;

-- 2021-06-08T07:23:58.023Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', IsTranslated='Y', Name='Steuer-Log', PrintName='Steuer-Log',Updated=TO_TIMESTAMP('2021-06-08 09:23:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579305 AND AD_Language='de_CH'
;

-- 2021-06-08T07:23:58.026Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579305,'de_CH')
;

-- 2021-06-08T07:24:06.148Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-08 09:24:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579305 AND AD_Language='de_DE'
;

-- 2021-06-08T07:24:06.150Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579305,'de_DE')
;

-- 2021-06-08T07:24:06.174Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579305,'de_DE')
;

-- 2021-06-08T07:24:06.175Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Run_Tax_Lookup_Log', Name='Tax log', Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', Help=NULL WHERE AD_Element_ID=579305
;

-- 2021-06-08T07:24:06.176Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Run_Tax_Lookup_Log', Name='Tax log', Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', Help=NULL, AD_Element_ID=579305 WHERE UPPER(ColumnName)='RUN_TAX_LOOKUP_LOG' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-08T07:24:06.178Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Run_Tax_Lookup_Log', Name='Tax log', Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', Help=NULL WHERE AD_Element_ID=579305 AND IsCentrallyMaintained='Y'
;

-- 2021-06-08T07:24:06.178Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Tax log', Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579305) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579305)
;

-- 2021-06-08T07:24:06.194Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Tax log', Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579305
;

-- 2021-06-08T07:24:06.196Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Tax log', Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', Help=NULL WHERE AD_Element_ID = 579305
;

-- 2021-06-08T07:24:06.197Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Tax log', Description = 'Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579305
;

-- 2021-06-08T07:24:15.403Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-08 09:24:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579305 AND AD_Language='nl_NL'
;

-- 2021-06-08T07:24:15.405Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579305,'nl_NL')
;

-- 2021-06-08T07:24:30.874Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Steuer-Log', PrintName='Steuer-Log',Updated=TO_TIMESTAMP('2021-06-08 09:24:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579305 AND AD_Language='de_DE'
;

-- 2021-06-08T07:24:30.875Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579305,'de_DE')
;

-- 2021-06-08T07:24:30.882Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579305,'de_DE')
;

-- 2021-06-08T07:24:30.885Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Run_Tax_Lookup_Log', Name='Steuer-Log', Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', Help=NULL WHERE AD_Element_ID=579305
;

-- 2021-06-08T07:24:30.885Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Run_Tax_Lookup_Log', Name='Steuer-Log', Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', Help=NULL, AD_Element_ID=579305 WHERE UPPER(ColumnName)='RUN_TAX_LOOKUP_LOG' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-08T07:24:30.886Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Run_Tax_Lookup_Log', Name='Steuer-Log', Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', Help=NULL WHERE AD_Element_ID=579305 AND IsCentrallyMaintained='Y'
;

-- 2021-06-08T07:24:30.886Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Steuer-Log', Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579305) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579305)
;

-- 2021-06-08T07:24:30.892Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Steuer-Log', Name='Steuer-Log' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579305)
;

-- 2021-06-08T07:24:30.892Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Steuer-Log', Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579305
;

-- 2021-06-08T07:24:30.893Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Steuer-Log', Description='Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', Help=NULL WHERE AD_Element_ID = 579305
;

-- 2021-06-08T07:24:30.894Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Steuer-Log', Description = 'Log-Meldungen, sie von metasfresh beim zuordnen des Steuer-Datensatzres ausgegeben wurden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579305
;

-- 2021-06-08T07:24:36.410Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Steuer-Log', PrintName='Steuer-Log',Updated=TO_TIMESTAMP('2021-06-08 09:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579305 AND AD_Language='nl_NL'
;

-- 2021-06-08T07:24:36.412Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579305,'nl_NL')
;

-- 2021-06-08T07:53:02.750Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574244,579305,0,36,541665,'Run_Tax_Lookup_Log',TO_TIMESTAMP('2021-06-08 09:53:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Log-Meldungen, sie von metasfresh beim zuorden des Steuer-Datensatzers ausgegeben wuren.','D',0,1000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Steuer-Log',0,0,TO_TIMESTAMP('2021-06-08 09:53:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-08T07:53:02.785Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574244 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-08T07:53:02.790Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579305)
;

-- 2021-06-08T08:20:46.991Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574245,1008,0,19,541665,'C_Invoice_ID','(select C_Invoice_ID from C_Invoice_Verification_SetLine ivsl where ivsl.C_Invoice_Verification_SetLine_ID = C_Invoice_Verification_SetLine.C_Invoice_Verification_SetLine_ID)',TO_TIMESTAMP('2021-06-08 10:20:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Invoice Identifier','D',0,10,'E','The Invoice Document.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N',0,'Rechnung',0,0,TO_TIMESTAMP('2021-06-08 10:20:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-08T08:20:47.002Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574245 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-08T08:20:47.004Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1008)
;

-- 2021-06-08T08:21:51.787Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574246,1076,0,19,541665,'C_InvoiceLine_ID','(select C_InvoiceLine_ID from C_Invoice_Verification_SetLine ivsl where ivsl.C_Invoice_Verification_SetLine_ID = C_Invoice_Verification_SetLine.C_Invoice_Verification_SetLine_ID)',TO_TIMESTAMP('2021-06-08 10:21:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Rechnungszeile','D',0,10,'Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Rechnungsposition',0,0,TO_TIMESTAMP('2021-06-08 10:21:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-08T08:21:51.826Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574246 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-08T08:21:51.833Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1076)
;

-- 2021-06-08T08:23:04.053Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574247,579238,0,19,541665,'C_InvoiceLine_Tax_ID','(select C_InvoiceLine_Tax_ID from C_Invoice_Verification_SetLine ivsl where ivsl.C_Invoice_Verification_SetLine_ID = C_Invoice_Verification_SetLine.C_Invoice_Verification_SetLine_ID)',TO_TIMESTAMP('2021-06-08 10:23:03','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N',0,'Steuer',0,0,TO_TIMESTAMP('2021-06-08 10:23:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-08T08:23:04.060Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574247 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-08T08:23:04.060Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579238)
;


-- 2021-06-08T08:25:31.171Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574247
;

-- 2021-06-08T08:25:31.178Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=574247
;

-- 2021-06-08T08:26:17.949Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-06-08 10:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574082
;


-- 2021-06-08T08:28:08.890Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=158, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-06-08 10:28:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574085
;


-- 2021-06-08T08:30:16.121Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574248,579238,0,19,541665,'C_InvoiceLine_Tax_ID','(select C_InvoiceLine_Tax_ID from C_Invoice_Verification_SetLine ivsl where ivsl.C_Invoice_Verification_SetLine_ID = C_Invoice_Verification_SetLine.C_Invoice_Verification_SetLine_ID)',TO_TIMESTAMP('2021-06-08 10:30:16','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N',0,'Steuer',0,0,TO_TIMESTAMP('2021-06-08 10:30:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-08T08:30:16.127Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574248 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-08T08:30:16.128Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579238)
;

-- 2021-06-08T08:31:05.977Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=158, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-06-08 10:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574248
;

-- 2021-06-08T09:01:38.502Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=19, AD_Reference_Value_ID=NULL, IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2021-06-08 11:01:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574248
;

-- 2021-06-08T09:03:26.275Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=40, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-06-08 11:03:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574248
;

-- 2021-06-08T09:08:18.022Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_invoice_verification_setline','C_InvoiceLine_Tax_ID','NUMERIC(10)',null,null)
;

-- Create Indexes for Table C_Invoice_Verification_RunLine

-- 2021-06-08T09:10:01.541Z
-- URL zum Konzept
CREATE INDEX C_Invoice_Verification_Run_ID_Index ON C_Invoice_Verification_RunLine (C_Invoice_Verification_Run_ID)
;

-- 2021-06-08T09:10:08.480Z
-- URL zum Konzept
CREATE INDEX C_Invoice_Verification_RunLine_Set_ID_Index ON C_Invoice_Verification_RunLine (C_Invoice_Verification_SetLine_ID)
;

-- 2021-06-08T09:10:14.533Z
-- URL zum Konzept
CREATE INDEX C_Invoice_VerificationRunLine_SetLine_ID ON C_Invoice_Verification_RunLine (C_Invoice_Verification_SetLine_ID)
;

--Create Window C_Invoice_Verification_RunLine
-- 2021-06-08T09:17:30.606Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579309,0,TO_TIMESTAMP('2021-06-08 11:17:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Rechnung-Überprüfungszeile','Rechnung-Überprüfungszeile',TO_TIMESTAMP('2021-06-08 11:17:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:17:30.625Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579309 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-08T09:17:38.681Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-08 11:17:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579309 AND AD_Language='de_CH'
;

-- 2021-06-08T09:17:38.681Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579309,'de_CH')
;

-- 2021-06-08T09:17:42.320Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-08 11:17:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579309 AND AD_Language='de_DE'
;

-- 2021-06-08T09:17:42.321Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579309,'de_DE')
;

-- 2021-06-08T09:17:42.331Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579309,'de_DE')
;

-- 2021-06-08T09:18:05.027Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Invoice Verification Run Line', PrintName='Invoice Verification Run Line',Updated=TO_TIMESTAMP('2021-06-08 11:18:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579309 AND AD_Language='en_US'
;

-- 2021-06-08T09:18:05.029Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579309,'en_US')
;

-- 2021-06-08T09:18:09.220Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-08 11:18:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579309 AND AD_Language='nl_NL'
;

-- 2021-06-08T09:18:09.222Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579309,'nl_NL')
;

-- 2021-06-08T09:22:40.888Z
-- URL zum Konzept
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=579309
;

-- 2021-06-08T09:22:40.898Z
-- URL zum Konzept
DELETE FROM AD_Element WHERE AD_Element_ID=579309
;

-- 2021-06-08T09:36:00.762Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES (0,579310,0,TO_TIMESTAMP('2021-06-08 11:36:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice Verification Run Line','Invoice Verification Run Line',TO_TIMESTAMP('2021-06-08 11:36:00','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Verification Run Line')
;

-- 2021-06-08T09:36:00.767Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579310 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-08T09:36:09.937Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-08 11:36:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579310 AND AD_Language='en_US'
;

-- 2021-06-08T09:36:09.938Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579310,'en_US')
;

-- 2021-06-08T09:36:48.042Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,579310,0,541156,TO_TIMESTAMP('2021-06-08 11:36:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Invoice Verification Run Line','N',TO_TIMESTAMP('2021-06-08 11:36:47','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2021-06-08T09:36:48.045Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541156 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2021-06-08T09:36:48.047Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(579310)
;

-- 2021-06-08T09:36:48.051Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541156
;

-- 2021-06-08T09:36:48.052Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541156)
;

-- 2021-06-08T09:39:30.885Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579245,0,544004,541665,541156,'Y',TO_TIMESTAMP('2021-06-08 11:39:30','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','C_Invoice_Verification_RunLine','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Invoice Verification RunLine','N',10,0,TO_TIMESTAMP('2021-06-08 11:39:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:30.898Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=544004 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-06-08T09:39:30.902Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579245)
;

-- 2021-06-08T09:39:30.909Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544004)
;

-- 2021-06-08T09:39:36.940Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574074,647452,0,544004,TO_TIMESTAMP('2021-06-08 11:39:36','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-06-08 11:39:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:36.943Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647452 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:36.944Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2021-06-08T09:39:37.244Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647452
;

-- 2021-06-08T09:39:37.244Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647452)
;

-- 2021-06-08T09:39:37.304Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574075,647453,0,544004,TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:37.306Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647453 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:37.307Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2021-06-08T09:39:37.432Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647453
;

-- 2021-06-08T09:39:37.432Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647453)
;

-- 2021-06-08T09:39:37.503Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574078,647454,0,544004,TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:37.506Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647454 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:37.508Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2021-06-08T09:39:37.657Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647454
;

-- 2021-06-08T09:39:37.657Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647454)
;

-- 2021-06-08T09:39:37.734Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574081,647455,0,544004,TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Invoice Verification RunLine',TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:37.736Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647455 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:37.737Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579245)
;

-- 2021-06-08T09:39:37.739Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647455
;

-- 2021-06-08T09:39:37.739Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647455)
;

-- 2021-06-08T09:39:37.796Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574082,647456,0,544004,TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Rechnung-Überprüfungslauf',TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:37.798Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647456 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:37.798Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579241)
;

-- 2021-06-08T09:39:37.800Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647456
;

-- 2021-06-08T09:39:37.800Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647456)
;

-- 2021-06-08T09:39:37.847Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574083,647457,0,544004,TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Rechnung-Überprüfungssatz',TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:37.849Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647457 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:37.849Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579233)
;

-- 2021-06-08T09:39:37.851Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647457
;

-- 2021-06-08T09:39:37.851Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647457)
;

-- 2021-06-08T09:39:37.895Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574084,647458,0,544004,TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Invoice Verification Element',TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:37.896Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647458 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:37.897Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579236)
;

-- 2021-06-08T09:39:37.898Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647458
;

-- 2021-06-08T09:39:37.898Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647458)
;

-- 2021-06-08T09:39:37.941Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574085,647459,0,544004,TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100,'Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.',10,'D','Y','N','N','N','N','N','N','N','Zugeordnete Steuer',TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:37.950Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647459 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:37.953Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579246)
;

-- 2021-06-08T09:39:37.954Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647459
;

-- 2021-06-08T09:39:37.955Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647459)
;

-- 2021-06-08T09:39:38.037Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574086,647460,0,544004,TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100,'Sagt aus, ob beim Uberpunfungs-Lauf die selbe Steur zugerdnet wurde, die die Reichnungzeile hat.',1,'D','Y','N','N','N','N','N','N','N','Steuer OK',TO_TIMESTAMP('2021-06-08 11:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:38.062Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647460 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:38.069Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579247)
;

-- 2021-06-08T09:39:38.075Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647460
;

-- 2021-06-08T09:39:38.081Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647460)
;

-- 2021-06-08T09:39:38.154Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574087,647461,0,544004,TO_TIMESTAMP('2021-06-08 11:39:38','YYYY-MM-DD HH24:MI:SS'),100,'Sag aus, ob baim Uber..',1,'D','Y','N','N','N','N','N','N','N','Steuersatz OK',TO_TIMESTAMP('2021-06-08 11:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:38.156Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647461 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:38.156Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579248)
;

-- 2021-06-08T09:39:38.157Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647461
;

-- 2021-06-08T09:39:38.157Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647461)
;

-- 2021-06-08T09:39:38.200Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574088,647462,0,544004,TO_TIMESTAMP('2021-06-08 11:39:38','YYYY-MM-DD HH24:MI:SS'),100,'Sagt aus, ob beim ...',1,'D','Y','N','N','N','N','N','N','N','Textbaustein OK',TO_TIMESTAMP('2021-06-08 11:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:38.202Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647462 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:38.203Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579249)
;

-- 2021-06-08T09:39:38.204Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647462
;

-- 2021-06-08T09:39:38.204Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647462)
;

-- 2021-06-08T09:39:38.245Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574244,647463,0,544004,TO_TIMESTAMP('2021-06-08 11:39:38','YYYY-MM-DD HH24:MI:SS'),100,'Log-Meldungen, sie von metasfresh beim zuorden des Steuer-Datensatzers ausgegeben wuren.',1000,'D','Y','N','N','N','N','N','N','N','Steuer-Log',TO_TIMESTAMP('2021-06-08 11:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:38.248Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647463 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:38.249Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579305)
;

-- 2021-06-08T09:39:38.250Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647463
;

-- 2021-06-08T09:39:38.250Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647463)
;

-- 2021-06-08T09:39:38.299Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574245,647464,0,544004,TO_TIMESTAMP('2021-06-08 11:39:38','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier',10,'D','The Invoice Document.','Y','N','N','N','N','N','N','N','Rechnung',TO_TIMESTAMP('2021-06-08 11:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:38.304Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647464 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:38.306Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008)
;

-- 2021-06-08T09:39:38.353Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647464
;

-- 2021-06-08T09:39:38.353Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647464)
;

-- 2021-06-08T09:39:38.415Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574246,647465,0,544004,TO_TIMESTAMP('2021-06-08 11:39:38','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungszeile',10,'D','Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.','Y','N','N','N','N','N','N','N','Rechnungsposition',TO_TIMESTAMP('2021-06-08 11:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:38.424Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647465 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:38.426Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1076)
;

-- 2021-06-08T09:39:38.434Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647465
;

-- 2021-06-08T09:39:38.435Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647465)
;

-- 2021-06-08T09:39:38.498Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574248,647466,0,544004,TO_TIMESTAMP('2021-06-08 11:39:38','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','N','N','N','N','N','N','N','Steuer',TO_TIMESTAMP('2021-06-08 11:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:39:38.504Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647466 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T09:39:38.506Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579238)
;

-- 2021-06-08T09:39:38.507Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647466
;

-- 2021-06-08T09:39:38.507Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647466)
;

-- 2021-06-08T09:42:44.811Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,579310,541723,0,541156,TO_TIMESTAMP('2021-06-08 11:42:44','YYYY-MM-DD HH24:MI:SS'),100,'D','_InvoiceVerificationRunLine','Y','N','N','N','N','Invoice Verification Run Line',TO_TIMESTAMP('2021-06-08 11:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Verification Run Line')
;

-- 2021-06-08T09:42:44.813Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541723 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-06-08T09:42:44.814Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541723, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541723)
;

-- 2021-06-08T09:42:44.818Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(579310)
;

-- 2021-06-08T09:42:52.856Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541712, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541713 AND AD_Tree_ID=10
;

-- 2021-06-08T09:42:52.857Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541712, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541714 AND AD_Tree_ID=10
;

-- 2021-06-08T09:42:52.857Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541712, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541715 AND AD_Tree_ID=10
;

-- 2021-06-08T09:42:52.857Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541712, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541723 AND AD_Tree_ID=10
;

-- 2021-06-08T09:44:50.792Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544004,543153,TO_TIMESTAMP('2021-06-08 11:44:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2021-06-08 11:44:50','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-06-08T09:44:50.795Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543153 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-06-08T09:44:56.058Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543962,543153,TO_TIMESTAMP('2021-06-08 11:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-06-08 11:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:45:28.557Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,543962,545973,TO_TIMESTAMP('2021-06-08 11:45:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2021-06-08 11:45:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:46:05.653Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647456,0,544004,545973,585676,'F',TO_TIMESTAMP('2021-06-08 11:46:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnung-Überprüfungslauf',10,0,0,TO_TIMESTAMP('2021-06-08 11:46:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:46:21.600Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647457,0,544004,545973,585677,'F',TO_TIMESTAMP('2021-06-08 11:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnung-Überprüfungssatz',20,0,0,TO_TIMESTAMP('2021-06-08 11:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:46:39.647Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647458,0,544004,545973,585678,'F',TO_TIMESTAMP('2021-06-08 11:46:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Invoice Verification Element',30,0,0,TO_TIMESTAMP('2021-06-08 11:46:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:47:25.301Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543962,545974,TO_TIMESTAMP('2021-06-08 11:47:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','invoice',20,TO_TIMESTAMP('2021-06-08 11:47:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:47:48.014Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647464,0,544004,545974,585679,'F',TO_TIMESTAMP('2021-06-08 11:47:47','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier','The Invoice Document.','Y','N','N','Y','N','N','N',0,'Rechnung',10,0,0,TO_TIMESTAMP('2021-06-08 11:47:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:48:05.037Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647465,0,544004,545974,585680,'F',TO_TIMESTAMP('2021-06-08 11:48:04','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungszeile','Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.','Y','N','N','Y','N','N','N',0,'Rechnungsposition',20,0,0,TO_TIMESTAMP('2021-06-08 11:48:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:48:37.052Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647466,0,544004,545974,585681,'F',TO_TIMESTAMP('2021-06-08 11:48:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Steuer',30,0,0,TO_TIMESTAMP('2021-06-08 11:48:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:49:04.524Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647459,0,544004,545974,585682,'F',TO_TIMESTAMP('2021-06-08 11:49:04','YYYY-MM-DD HH24:MI:SS'),100,'Steuer-Datensatz, der der Rechnungszeile beim Überprüfungs-Lauf zugeordnet wurde.','Y','N','N','Y','N','N','N',0,'Zugeordnete Steuer',40,0,0,TO_TIMESTAMP('2021-06-08 11:49:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:49:20.530Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647463,0,544004,545974,585683,'F',TO_TIMESTAMP('2021-06-08 11:49:20','YYYY-MM-DD HH24:MI:SS'),100,'Log-Meldungen, sie von metasfresh beim zuorden des Steuer-Datensatzers ausgegeben wuren.','Y','N','N','Y','N','N','N',0,'Steuer-Log',50,0,0,TO_TIMESTAMP('2021-06-08 11:49:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:49:33.717Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543963,543153,TO_TIMESTAMP('2021-06-08 11:49:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-06-08 11:49:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:49:42.072Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543963,545975,TO_TIMESTAMP('2021-06-08 11:49:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2021-06-08 11:49:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:50:06.190Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647460,0,544004,545975,585684,'F',TO_TIMESTAMP('2021-06-08 11:50:06','YYYY-MM-DD HH24:MI:SS'),100,'Sagt aus, ob beim Uberpunfungs-Lauf die selbe Steur zugerdnet wurde, die die Reichnungzeile hat.','Y','N','N','Y','N','N','N',0,'Steuer OK',10,0,0,TO_TIMESTAMP('2021-06-08 11:50:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:50:33.508Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647461,0,544004,545975,585685,'F',TO_TIMESTAMP('2021-06-08 11:50:33','YYYY-MM-DD HH24:MI:SS'),100,'Sag aus, ob baim Uber..','Y','N','N','Y','N','N','N',0,'Steuersatz OK',20,0,0,TO_TIMESTAMP('2021-06-08 11:50:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:50:45.153Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647462,0,544004,545975,585686,'F',TO_TIMESTAMP('2021-06-08 11:50:45','YYYY-MM-DD HH24:MI:SS'),100,'Sagt aus, ob beim ...','Y','N','N','Y','N','N','N',0,'Textbaustein OK',30,0,0,TO_TIMESTAMP('2021-06-08 11:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:51:07.262Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543963,545976,TO_TIMESTAMP('2021-06-08 11:51:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2021-06-08 11:51:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:51:16.335Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647453,0,544004,545976,585687,'F',TO_TIMESTAMP('2021-06-08 11:51:16','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2021-06-08 11:51:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:51:22.805Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647452,0,544004,545976,585688,'F',TO_TIMESTAMP('2021-06-08 11:51:22','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2021-06-08 11:51:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T09:53:31.840Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-06-08 11:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585676
;

-- 2021-06-08T09:53:31.849Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-06-08 11:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585677
;

-- 2021-06-08T09:53:31.857Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-06-08 11:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585678
;

-- 2021-06-08T09:53:31.866Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-06-08 11:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585679
;

-- 2021-06-08T09:53:31.874Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-06-08 11:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585680
;

-- 2021-06-08T09:53:31.882Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-06-08 11:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585681
;

-- 2021-06-08T09:53:31.890Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-06-08 11:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585682
;

-- 2021-06-08T09:53:31.898Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-06-08 11:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585684
;

-- 2021-06-08T09:53:31.905Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-06-08 11:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585685
;

-- 2021-06-08T09:53:31.910Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-06-08 11:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585686
;

-- 2021-06-08T09:53:58.950Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-06-08 11:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585684
;

-- 2021-06-08T09:53:58.955Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-06-08 11:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585685
;

-- 2021-06-08T09:53:58.961Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-06-08 11:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585686
;

-- 2021-06-08T09:53:58.966Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-06-08 11:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585676
;

-- 2021-06-08T09:53:58.971Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-06-08 11:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585677
;

-- 2021-06-08T09:53:58.978Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-06-08 11:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585678
;

-- 2021-06-08T09:53:58.983Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-06-08 11:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585679
;

-- 2021-06-08T09:53:58.988Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-06-08 11:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585680
;

-- 2021-06-08T09:53:58.993Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-06-08 11:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585681
;

-- 2021-06-08T09:53:58.999Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-06-08 11:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585682
;

-- 2021-06-08T09:53:59.004Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2021-06-08 11:53:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585687
;

-- 2021-06-08T09:55:43.838Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rechnung-Überprüfungselement', PrintName='Rechnung-Überprüfungselement',Updated=TO_TIMESTAMP('2021-06-08 11:55:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579239 AND AD_Language='de_CH'
;

-- 2021-06-08T09:55:43.841Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579239,'de_CH')
;

-- 2021-06-08T09:55:54.988Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Rechnung-Überprüfungselement',Updated=TO_TIMESTAMP('2021-06-08 11:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579239 AND AD_Language='de_CH'
;

-- 2021-06-08T09:55:54.989Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579239,'de_CH')
;

-- 2021-06-08T09:56:25.936Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rechnung-Überprüfungselement', PrintName='Rechnung-Überprüfungselement', WEBUI_NameBrowse='Rechnung-Überprüfungselement',Updated=TO_TIMESTAMP('2021-06-08 11:56:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579239 AND AD_Language='de_DE'
;

-- 2021-06-08T09:56:25.938Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579239,'de_DE')
;

-- 2021-06-08T09:56:25.963Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579239,'de_DE')
;

-- 2021-06-08T09:56:25.966Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Rechnung-Überprüfungselement', Description=NULL, Help=NULL WHERE AD_Element_ID=579239
;

-- 2021-06-08T09:56:25.968Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rechnung-Überprüfungselement', Description=NULL, Help=NULL WHERE AD_Element_ID=579239 AND IsCentrallyMaintained='Y'
;

-- 2021-06-08T09:56:25.969Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-Überprüfungselement', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579239) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579239)
;

-- 2021-06-08T09:56:25.988Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung-Überprüfungselement', Name='Rechnung-Überprüfungselement' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579239)
;

-- 2021-06-08T09:56:25.990Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-Überprüfungselement', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579239
;

-- 2021-06-08T09:56:25.992Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-Überprüfungselement', Description=NULL, Help=NULL WHERE AD_Element_ID = 579239
;

-- 2021-06-08T09:56:25.993Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungselement', Description = NULL, WEBUI_NameBrowse = 'Rechnung-Überprüfungselement', WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579239
;

-- 2021-06-08T09:57:02.440Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Invoice Verification Element', PrintName='Invoice Verification Element', WEBUI_NameBrowse='Invoice Verification Element',Updated=TO_TIMESTAMP('2021-06-08 11:57:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579239 AND AD_Language='en_US'
;

-- 2021-06-08T09:57:02.441Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579239,'en_US')
;

-- 2021-06-08T09:57:26.555Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rechnung-Überprüfungselement', PrintName='Rechnung-Überprüfungselement', WEBUI_NameBrowse='Rechnung-Überprüfungselement',Updated=TO_TIMESTAMP('2021-06-08 11:57:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579239 AND AD_Language='nl_NL'
;

-- 2021-06-08T09:57:26.557Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579239,'nl_NL')
;

-- 2021-06-08T09:58:14.050Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungszeile', PrintName='Rechnung-Überprüfungszeile',Updated=TO_TIMESTAMP('2021-06-08 11:58:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579310 AND AD_Language='de_CH'
;

-- 2021-06-08T09:58:14.050Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579310,'de_CH')
;

-- 2021-06-08T09:58:20.545Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungszeile', PrintName='Rechnung-Überprüfungszeile',Updated=TO_TIMESTAMP('2021-06-08 11:58:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579310 AND AD_Language='de_DE'
;

-- 2021-06-08T09:58:20.547Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579310,'de_DE')
;

-- 2021-06-08T09:58:20.580Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579310,'de_DE')
;

-- 2021-06-08T09:58:20.581Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=579310
;

-- 2021-06-08T09:58:20.582Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=579310 AND IsCentrallyMaintained='Y'
;

-- 2021-06-08T09:58:20.583Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579310) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579310)
;

-- 2021-06-08T09:58:20.593Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung-Überprüfungszeile', Name='Rechnung-Überprüfungszeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579310)
;

-- 2021-06-08T09:58:20.594Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579310
;

-- 2021-06-08T09:58:20.596Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-Überprüfungszeile', Description=NULL, Help=NULL WHERE AD_Element_ID = 579310
;

-- 2021-06-08T09:58:20.597Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungszeile', Description = NULL, WEBUI_NameBrowse = 'Invoice Verification Run Line', WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579310
;

-- 2021-06-08T09:58:32.487Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungszeile', PrintName='Rechnung-Überprüfungszeile',Updated=TO_TIMESTAMP('2021-06-08 11:58:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579310 AND AD_Language='nl_NL'
;

-- 2021-06-08T09:58:32.489Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579310,'nl_NL')
;

-- 2021-06-08T09:58:38.735Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Rechnung-Überprüfungszeile',Updated=TO_TIMESTAMP('2021-06-08 11:58:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579310 AND AD_Language='nl_NL'
;

-- 2021-06-08T09:58:38.737Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579310,'nl_NL')
;

-- 2021-06-08T09:58:44.551Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Rechnung-Überprüfungszeile',Updated=TO_TIMESTAMP('2021-06-08 11:58:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579310 AND AD_Language='de_DE'
;

-- 2021-06-08T09:58:44.551Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579310,'de_DE')
;

-- 2021-06-08T09:58:44.555Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579310,'de_DE')
;

-- 2021-06-08T09:58:44.555Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungszeile', Description = NULL, WEBUI_NameBrowse = 'Rechnung-Überprüfungszeile', WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579310
;

-- 2021-06-08T09:58:49.072Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Rechnung-Überprüfungszeile',Updated=TO_TIMESTAMP('2021-06-08 11:58:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579310 AND AD_Language='de_CH'
;

-- 2021-06-08T09:58:49.074Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579310,'de_CH')
;

-- 2021-06-08T10:02:25.040Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select C_Invoice_ID from C_Invoice_Verification_SetLine ivsl where ivsl.C_Invoice_Verification_SetLine_ID = C_Invoice_Verification_RunLine.C_Invoice_Verification_SetLine_ID)',Updated=TO_TIMESTAMP('2021-06-08 12:02:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574245
;

-- 2021-06-08T10:04:11.423Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select C_InvoiceLine_ID from C_Invoice_Verification_SetLine ivsl where ivsl.C_Invoice_Verification_SetLine_ID = C_Invoice_Verification_RunLine.C_Invoice_Verification_SetLine_ID)',Updated=TO_TIMESTAMP('2021-06-08 12:04:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574246
;

-- 2021-06-08T10:04:31.369Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select C_InvoiceLine_Tax_ID from C_Invoice_Verification_SetLine ivsl where ivsl.C_Invoice_Verification_SetLine_ID = C_Invoice_Verification_RunLine.C_Invoice_Verification_SetLine_ID)',Updated=TO_TIMESTAMP('2021-06-08 12:04:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574248
;

-- 2021-06-08T10:26:51.172Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-06-08 12:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585684
;

-- 2021-06-08T10:27:01.188Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-06-08 12:27:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585685
;

-- 2021-06-08T10:27:10.890Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-06-08 12:27:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585686
;

-- 2021-06-08T10:29:10.219Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-06-08 12:29:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585681
;

-- 2021-06-08T10:29:17.798Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-06-08 12:29:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585682
;

-- Fix Zoom
-- 2021-06-08T10:35:13.443Z
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=541156,Updated=TO_TIMESTAMP('2021-06-08 12:35:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541665
;

-- 2021-06-08T10:35:50.234Z
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=541147,Updated=TO_TIMESTAMP('2021-06-08 12:35:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541664
;

-- 2021-06-08T10:36:32.713Z
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=541146,Updated=TO_TIMESTAMP('2021-06-08 12:36:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541663
;

-- 2021-06-08T10:45:57.191Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=19, FieldLength=10, IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2021-06-08 12:45:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574248
;

-- 2021-06-08T10:49:06.725Z
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=0,Updated=TO_TIMESTAMP('2021-06-08 12:49:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543958
;

-- 2021-06-08T10:49:21.125Z
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=0,Updated=TO_TIMESTAMP('2021-06-08 12:49:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543956
;

-- 2021-06-08T10:49:34.291Z
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=0,Updated=TO_TIMESTAMP('2021-06-08 12:49:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543957
;

-- 2021-06-08T11:08:48.267Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647453
;

-- 2021-06-08T11:08:49.003Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647454
;

-- 2021-06-08T11:08:50.107Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647455
;

-- 2021-06-08T11:08:50.850Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647456
;

-- 2021-06-08T11:08:51.497Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647457
;

-- 2021-06-08T11:08:52.180Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647458
;

-- 2021-06-08T11:08:52.763Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647459
;

-- 2021-06-08T11:08:53.474Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647460
;

-- 2021-06-08T11:08:54.090Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647461
;

-- 2021-06-08T11:08:54.964Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647463
;

-- 2021-06-08T11:08:55.684Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647462
;

-- 2021-06-08T11:08:56.246Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647464
;

-- 2021-06-08T11:08:56.774Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647465
;

-- 2021-06-08T11:08:58.070Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-08 13:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647466
;

-- 2021-06-08T11:09:44.851Z
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2021-06-08 13:09:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544004
;

-- 2021-06-08T11:16:40.914Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=158, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-06-08 13:16:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574248
;


-- 2021-06-08T11:19:04.330Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-06-08 13:19:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574084
;


-- 2021-06-08T11:20:10.831Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2021-06-08 13:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574084
;

-- Add missing TRL
-- 2021-06-08T11:34:31.420Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-ÃœberprÃ¼fungselement', PrintName='Rechnung-ÃœberprÃ¼fungselement',Updated=TO_TIMESTAMP('2021-06-08 13:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579236 AND AD_Language='de_CH'
;

-- 2021-06-08T11:34:31.420Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579236,'de_CH')
;

-- 2021-06-08T11:34:37.076Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-ÃœberprÃ¼fungselement', PrintName='Rechnung-ÃœberprÃ¼fungselement',Updated=TO_TIMESTAMP('2021-06-08 13:34:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579236 AND AD_Language='de_DE'
;

-- 2021-06-08T11:34:37.076Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579236,'de_DE')
;

-- 2021-06-08T11:34:37.082Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579236,'de_DE')
;

-- 2021-06-08T11:34:37.083Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Invoice_Verification_SetLine_ID', Name='Rechnung-ÃœberprÃ¼fungselement', Description=NULL, Help=NULL WHERE AD_Element_ID=579236
;

-- 2021-06-08T11:34:37.083Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Verification_SetLine_ID', Name='Rechnung-ÃœberprÃ¼fungselement', Description=NULL, Help=NULL, AD_Element_ID=579236 WHERE UPPER(ColumnName)='C_INVOICE_VERIFICATION_SETLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-08T11:34:37.086Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Verification_SetLine_ID', Name='Rechnung-ÃœberprÃ¼fungselement', Description=NULL, Help=NULL WHERE AD_Element_ID=579236 AND IsCentrallyMaintained='Y'
;

-- 2021-06-08T11:34:37.086Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-ÃœberprÃ¼fungselement', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579236) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579236)
;

-- 2021-06-08T11:34:37.113Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung-ÃœberprÃ¼fungselement', Name='Rechnung-ÃœberprÃ¼fungselement' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579236)
;

-- 2021-06-08T11:34:37.114Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-ÃœberprÃ¼fungselement', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579236
;

-- 2021-06-08T11:34:37.115Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-ÃœberprÃ¼fungselement', Description=NULL, Help=NULL WHERE AD_Element_ID = 579236
;

-- 2021-06-08T11:34:37.116Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-ÃœberprÃ¼fungselement', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579236
;

-- 2021-06-08T11:34:48.751Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-08 13:34:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579236 AND AD_Language='en_US'
;

-- 2021-06-08T11:34:48.751Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579236,'en_US')
;

-- 2021-06-08T11:34:55.404Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-ÃœberprÃ¼fungselement', PrintName='Rechnung-ÃœberprÃ¼fungselement',Updated=TO_TIMESTAMP('2021-06-08 13:34:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579236 AND AD_Language='nl_NL'
;

-- 2021-06-08T11:34:55.404Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579236,'nl_NL')
;

-- 2021-06-08T12:03:04.578Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=336, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-06-08 14:03:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574245
;

-- 2021-06-08T12:06:46.499Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-06-08 14:06:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574052
;


