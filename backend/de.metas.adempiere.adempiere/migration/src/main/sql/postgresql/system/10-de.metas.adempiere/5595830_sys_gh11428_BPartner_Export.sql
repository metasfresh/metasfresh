-- 2021-06-29T15:09:13.564Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541731,'N',TO_TIMESTAMP('2021-06-29 18:09:11','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','N','N','N','N','N','Y',0,'Partner Export','NP','L','C_BPartner_Export','DTI',TO_TIMESTAMP('2021-06-29 18:09:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T15:09:13.698Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541731 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-06-29T15:09:36.273Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579402,0,'C_BPartner_Export_ID',TO_TIMESTAMP('2021-06-29 18:09:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Partner Export','Partner Export',TO_TIMESTAMP('2021-06-29 18:09:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T15:09:36.310Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579402 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-29T15:09:38.742Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574780,579402,0,13,541731,'C_BPartner_Export_ID',TO_TIMESTAMP('2021-06-29 18:09:35','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','N','N','N','N','N','Partner Export',TO_TIMESTAMP('2021-06-29 18:09:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:38.774Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574780 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:40.637Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574781,113,0,30,541731,'AD_Org_ID',TO_TIMESTAMP('2021-06-29 18:09:40','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-06-29 18:09:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:40.670Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574781 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:41.692Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574782,52017,0,14,541731,'Category',TO_TIMESTAMP('2021-06-29 18:09:41','YYYY-MM-DD HH24:MI:SS'),100,'D',2147483647,'Y','Y','N','N','N','N','N','N','N','N','N','Kategorie',TO_TIMESTAMP('2021-06-29 18:09:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:41.722Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574782 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:42.717Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574783,1876,0,10,541731,'BPValue',TO_TIMESTAMP('2021-06-29 18:09:42','YYYY-MM-DD HH24:MI:SS'),100,'Sponsor-Nr.','D',40,'Suchschlüssel für den Geschäftspartner','Y','Y','N','N','N','N','N','N','N','N','N','Nr.',TO_TIMESTAMP('2021-06-29 18:09:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:42.748Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574783 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:43.718Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574784,2510,0,10,541731,'BPName',TO_TIMESTAMP('2021-06-29 18:09:43','YYYY-MM-DD HH24:MI:SS'),100,'Name des Sponsors.','D',60,'Y','Y','N','N','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2021-06-29 18:09:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:43.749Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574784 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:44.765Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574785,1159,0,30,541731,'C_Greeting_ID',TO_TIMESTAMP('2021-06-29 18:09:44','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz','D',10,'Anrede, die beim Druck auf Korrespondenz verwendet werden soll.','Y','Y','N','N','N','N','N','N','N','N','N','Anrede (ID)',TO_TIMESTAMP('2021-06-29 18:09:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:44.796Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574785 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:45.804Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574786,1171,0,10,541731,'Greeting',TO_TIMESTAMP('2021-06-29 18:09:45','YYYY-MM-DD HH24:MI:SS'),100,'Für Briefe - z.B. "Sehr geehrter {0}" oder "Sehr geehrter Herr {0}" - Zur Laufzeit wird  "{0}" durch den Namen ersetzt','D',60,'Anrede definiert, was auf einem Brief an einen Geschäftspartner gedruckt wird.','Y','Y','N','N','N','N','N','N','N','N','N','Anrede',TO_TIMESTAMP('2021-06-29 18:09:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:45.837Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574786 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:46.816Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574787,542055,0,10,541731,'Letter_Salutation',TO_TIMESTAMP('2021-06-29 18:09:46','YYYY-MM-DD HH24:MI:SS'),100,'D',60,'Y','Y','N','N','N','N','N','N','N','N','N','Briefanrede',TO_TIMESTAMP('2021-06-29 18:09:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:46.848Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574787 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:47.869Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574788,540398,0,10,541731,'Firstname',TO_TIMESTAMP('2021-06-29 18:09:47','YYYY-MM-DD HH24:MI:SS'),100,'Vorname','D',255,'Y','Y','N','N','N','N','N','N','N','N','N','Vorname',TO_TIMESTAMP('2021-06-29 18:09:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:47.902Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574788 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:48.925Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574789,540399,0,10,541731,'Lastname',TO_TIMESTAMP('2021-06-29 18:09:48','YYYY-MM-DD HH24:MI:SS'),100,'D',255,'Y','Y','N','N','N','N','N','N','N','N','N','Nachname',TO_TIMESTAMP('2021-06-29 18:09:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:48.969Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574789 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:50.250Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574790,1891,0,16,541731,'Birthday',TO_TIMESTAMP('2021-06-29 18:09:49','YYYY-MM-DD HH24:MI:SS'),100,'Geburtstag oder Jahrestag','D',29,'Geburtstag oder Jahrestag','Y','Y','N','N','N','N','N','N','N','N','N','Geburtstag',TO_TIMESTAMP('2021-06-29 18:09:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:50.290Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574790 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:51.681Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574791,156,0,10,541731,'Address1',TO_TIMESTAMP('2021-06-29 18:09:51','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 1 für diesen Standort','D',100,'"Adresszeile 1" gibt die Anschrift für diesen Standort an.','Y','Y','N','N','N','N','N','N','N','N','N','Straße und Nr.',TO_TIMESTAMP('2021-06-29 18:09:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:51.713Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574791 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:52.818Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574792,157,0,10,541731,'Address2',TO_TIMESTAMP('2021-06-29 18:09:52','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 2 für diesen Standort','D',100,'"Adresszeile 2" bietet weitere Adressangaben für diesen Standort. Z.B. Gebäudenummer, Stockwerk, Raumnummer o.ä.','Y','Y','N','N','N','N','N','N','N','N','N','Adresszusatz',TO_TIMESTAMP('2021-06-29 18:09:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:52.849Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574792 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:54.035Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574793,2555,0,10,541731,'Address3',TO_TIMESTAMP('2021-06-29 18:09:53','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeilee 3 für diesen Standort','D',100,'"Adresszeile 2" bietet weitere Adressangaben für diesen Standort. Z.B. Gebäudenummer, Stockwerk, Raumnummer o.ä.','Y','Y','N','N','N','N','N','N','N','N','N','Adresszeile 3',TO_TIMESTAMP('2021-06-29 18:09:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:54.068Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574793 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:55.279Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574794,2556,0,10,541731,'Address4',TO_TIMESTAMP('2021-06-29 18:09:54','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 4 für diesen Standort','D',100,'The Address 4 provides additional address information for an entity. Z.B. Gebäudenummer, Stockwerk, Raumnummer o.ä.','Y','Y','N','N','N','N','N','N','N','N','N','Adresszusatz',TO_TIMESTAMP('2021-06-29 18:09:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:55.369Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574794 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:56.788Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574795,512,0,10,541731,'Postal',TO_TIMESTAMP('2021-06-29 18:09:56','YYYY-MM-DD HH24:MI:SS'),100,'Postleitzahl','D',10,'"PLZ" bezeichnet die Postleitzahl für diese Adresse oder dieses Postfach.','Y','Y','N','N','N','N','N','N','N','N','N','PLZ',TO_TIMESTAMP('2021-06-29 18:09:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:56.820Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574795 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:58.022Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574796,225,0,10,541731,'City',TO_TIMESTAMP('2021-06-29 18:09:57','YYYY-MM-DD HH24:MI:SS'),100,'Name des Ortes','D',60,'Bezeichnet einen einzelnen Ort in diesem Land oder dieser Region.','Y','Y','N','N','N','N','N','N','N','N','N','Ort',TO_TIMESTAMP('2021-06-29 18:09:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:58.057Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574796 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:09:59.537Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574797,192,0,30,541731,'C_Country_ID',TO_TIMESTAMP('2021-06-29 18:09:58','YYYY-MM-DD HH24:MI:SS'),100,'Land','D',10,'"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','Y','N','N','N','N','N','N','N','N','N','Land',TO_TIMESTAMP('2021-06-29 18:09:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:09:59.573Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574797 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:10:01.131Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574798,1896,0,10,541731,'EMailUser',TO_TIMESTAMP('2021-06-29 18:10:00','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer-Name/Konto (ID) im EMail-System','D',60,'The user name in the mail system is usually the string before the @ of your email address. Notwendig, wenn der EMail-SErver eine Anmeldung vor dem Versenden von EMails verlangt.','Y','Y','N','N','N','N','N','N','N','N','N','EMail Nutzer-ID',TO_TIMESTAMP('2021-06-29 18:10:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:10:01.213Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574798 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:10:02.242Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574799,543413,0,16,541731,'MasterStartDate',TO_TIMESTAMP('2021-06-29 18:10:01','YYYY-MM-DD HH24:MI:SS'),100,'D',29,'Y','Y','N','N','N','N','N','N','N','N','N','Master Start Date',TO_TIMESTAMP('2021-06-29 18:10:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:10:02.272Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574799 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:10:03.229Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574800,543414,0,16,541731,'MasterEndDate',TO_TIMESTAMP('2021-06-29 18:10:02','YYYY-MM-DD HH24:MI:SS'),100,'D',29,'Y','Y','N','N','N','N','N','N','N','N','N','Master End Date',TO_TIMESTAMP('2021-06-29 18:10:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:10:03.262Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574800 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T15:10:04.217Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,574801,543465,0,10,541731,'TerminationReason',TO_TIMESTAMP('2021-06-29 18:10:03','YYYY-MM-DD HH24:MI:SS'),100,'D',3,'Y','Y','N','N','N','N','N','N','N','N','N','Termination Reason',TO_TIMESTAMP('2021-06-29 18:10:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T15:10:04.249Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574801 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;













-- 2021-06-29T15:29:52.495Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,579402,0,541174,TO_TIMESTAMP('2021-06-29 18:29:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Partner Export','N',TO_TIMESTAMP('2021-06-29 18:29:50','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2021-06-29T15:29:52.626Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541174 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2021-06-29T15:29:52.727Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(579402) 
;

-- 2021-06-29T15:29:52.780Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541174
;

-- 2021-06-29T15:29:52.815Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541174)
;

-- 2021-06-29T15:30:28.720Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579402,0,544118,541731,541174,'Y',TO_TIMESTAMP('2021-06-29 18:30:28','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','C_BPartner_Export','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Partner Export','N',10,0,TO_TIMESTAMP('2021-06-29 18:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T15:30:28.742Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=544118 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-06-29T15:30:28.773Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579402) 
;

-- 2021-06-29T15:30:28.804Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544118)
;

-- 2021-06-29T16:26:20.499Z
-- URL zum Konzept
UPDATE AD_Table SET IsView='N',Updated=TO_TIMESTAMP('2021-06-29 19:26:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541731
;

-- 2021-06-29T16:30:01.954Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574780,649857,0,544118,TO_TIMESTAMP('2021-06-29 19:30:01','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Partner Export',TO_TIMESTAMP('2021-06-29 19:30:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:01.991Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649857 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:02.027Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579402) 
;

-- 2021-06-29T16:30:02.070Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649857
;

-- 2021-06-29T16:30:02.105Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649857)
;

-- 2021-06-29T16:30:02.579Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574781,649858,0,544118,TO_TIMESTAMP('2021-06-29 19:30:02','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-06-29 19:30:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:02.613Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649858 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:02.644Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-06-29T16:30:03.008Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649858
;

-- 2021-06-29T16:30:03.040Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649858)
;

-- 2021-06-29T16:30:03.508Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574782,649859,0,544118,TO_TIMESTAMP('2021-06-29 19:30:03','YYYY-MM-DD HH24:MI:SS'),100,2147483647,'D','Y','N','N','N','N','N','N','N','Kategorie',TO_TIMESTAMP('2021-06-29 19:30:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:03.539Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649859 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:03.570Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(52017) 
;

-- 2021-06-29T16:30:03.609Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649859
;

-- 2021-06-29T16:30:03.640Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649859)
;

-- 2021-06-29T16:30:04.106Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574783,649860,0,544118,TO_TIMESTAMP('2021-06-29 19:30:03','YYYY-MM-DD HH24:MI:SS'),100,'Sponsor-Nr.',40,'D','Suchschlüssel für den Geschäftspartner','Y','N','N','N','N','N','N','N','Nr.',TO_TIMESTAMP('2021-06-29 19:30:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:04.139Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649860 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:04.171Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1876) 
;

-- 2021-06-29T16:30:04.208Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649860
;

-- 2021-06-29T16:30:04.240Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649860)
;

-- 2021-06-29T16:30:04.732Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574784,649861,0,544118,TO_TIMESTAMP('2021-06-29 19:30:04','YYYY-MM-DD HH24:MI:SS'),100,'Name des Sponsors.',60,'D','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2021-06-29 19:30:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:04.764Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649861 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:04.798Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2510) 
;

-- 2021-06-29T16:30:04.840Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649861
;

-- 2021-06-29T16:30:04.870Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649861)
;

-- 2021-06-29T16:30:05.339Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574785,649862,0,544118,TO_TIMESTAMP('2021-06-29 19:30:04','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz',10,'D','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.','Y','N','N','N','N','N','N','N','Anrede (ID)',TO_TIMESTAMP('2021-06-29 19:30:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:05.371Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649862 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:05.406Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1159) 
;

-- 2021-06-29T16:30:05.465Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649862
;

-- 2021-06-29T16:30:05.498Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649862)
;

-- 2021-06-29T16:30:05.966Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574786,649863,0,544118,TO_TIMESTAMP('2021-06-29 19:30:05','YYYY-MM-DD HH24:MI:SS'),100,'Für Briefe - z.B. "Sehr geehrter {0}" oder "Sehr geehrter Herr {0}" - Zur Laufzeit wird  "{0}" durch den Namen ersetzt',60,'D','Anrede definiert, was auf einem Brief an einen Geschäftspartner gedruckt wird.','Y','N','N','N','N','N','N','N','Anrede',TO_TIMESTAMP('2021-06-29 19:30:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:05.997Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649863 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:06.032Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1171) 
;

-- 2021-06-29T16:30:06.069Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649863
;

-- 2021-06-29T16:30:06.099Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649863)
;

-- 2021-06-29T16:30:06.564Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574787,649864,0,544118,TO_TIMESTAMP('2021-06-29 19:30:06','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','N','N','N','N','N','N','N','Briefanrede',TO_TIMESTAMP('2021-06-29 19:30:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:06.597Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649864 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:06.630Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542055) 
;

-- 2021-06-29T16:30:06.666Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649864
;

-- 2021-06-29T16:30:06.698Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649864)
;

-- 2021-06-29T16:30:07.177Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574788,649865,0,544118,TO_TIMESTAMP('2021-06-29 19:30:06','YYYY-MM-DD HH24:MI:SS'),100,'Vorname',255,'D','Y','N','N','N','N','N','N','N','Vorname',TO_TIMESTAMP('2021-06-29 19:30:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:07.211Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649865 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:07.244Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540398) 
;

-- 2021-06-29T16:30:07.279Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649865
;

-- 2021-06-29T16:30:07.312Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649865)
;

-- 2021-06-29T16:30:07.829Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574789,649866,0,544118,TO_TIMESTAMP('2021-06-29 19:30:07','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Nachname',TO_TIMESTAMP('2021-06-29 19:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:07.861Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649866 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:07.893Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540399) 
;

-- 2021-06-29T16:30:07.928Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649866
;

-- 2021-06-29T16:30:07.959Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649866)
;

-- 2021-06-29T16:30:08.437Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574790,649867,0,544118,TO_TIMESTAMP('2021-06-29 19:30:08','YYYY-MM-DD HH24:MI:SS'),100,'Geburtstag oder Jahrestag',29,'D','Geburtstag oder Jahrestag','Y','N','N','N','N','N','N','N','Geburtstag',TO_TIMESTAMP('2021-06-29 19:30:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:08.469Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649867 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:08.501Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1891) 
;

-- 2021-06-29T16:30:08.535Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649867
;

-- 2021-06-29T16:30:08.570Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649867)
;

-- 2021-06-29T16:30:09.052Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574791,649868,0,544118,TO_TIMESTAMP('2021-06-29 19:30:08','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 1 für diesen Standort',100,'D','"Adresszeile 1" gibt die Anschrift für diesen Standort an.','Y','N','N','N','N','N','N','N','Straße und Nr.',TO_TIMESTAMP('2021-06-29 19:30:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:09.083Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:09.114Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(156) 
;

-- 2021-06-29T16:30:09.148Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649868
;

-- 2021-06-29T16:30:09.181Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649868)
;

-- 2021-06-29T16:30:09.640Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574792,649869,0,544118,TO_TIMESTAMP('2021-06-29 19:30:09','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 2 für diesen Standort',100,'D','"Adresszeile 2" bietet weitere Adressangaben für diesen Standort. Z.B. Gebäudenummer, Stockwerk, Raumnummer o.ä.','Y','N','N','N','N','N','N','N','Adresszusatz',TO_TIMESTAMP('2021-06-29 19:30:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:09.672Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:09.706Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(157) 
;

-- 2021-06-29T16:30:09.772Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649869
;

-- 2021-06-29T16:30:09.804Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649869)
;

-- 2021-06-29T16:30:10.282Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574793,649870,0,544118,TO_TIMESTAMP('2021-06-29 19:30:09','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeilee 3 für diesen Standort',100,'D','"Adresszeile 2" bietet weitere Adressangaben für diesen Standort. Z.B. Gebäudenummer, Stockwerk, Raumnummer o.ä.','Y','N','N','N','N','N','N','N','Adresszeile 3',TO_TIMESTAMP('2021-06-29 19:30:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:10.315Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:10.348Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2555) 
;

-- 2021-06-29T16:30:10.383Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649870
;

-- 2021-06-29T16:30:10.414Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649870)
;

-- 2021-06-29T16:30:10.887Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574794,649871,0,544118,TO_TIMESTAMP('2021-06-29 19:30:10','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 4 für diesen Standort',100,'D','The Address 4 provides additional address information for an entity. Z.B. Gebäudenummer, Stockwerk, Raumnummer o.ä.','Y','N','N','N','N','N','N','N','Adresszusatz',TO_TIMESTAMP('2021-06-29 19:30:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:10.925Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:10.958Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2556) 
;

-- 2021-06-29T16:30:10.990Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649871
;

-- 2021-06-29T16:30:11.023Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649871)
;

-- 2021-06-29T16:30:11.469Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574795,649872,0,544118,TO_TIMESTAMP('2021-06-29 19:30:11','YYYY-MM-DD HH24:MI:SS'),100,'Postleitzahl',10,'D','"PLZ" bezeichnet die Postleitzahl für diese Adresse oder dieses Postfach.','Y','N','N','N','N','N','N','N','PLZ',TO_TIMESTAMP('2021-06-29 19:30:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:11.500Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:11.532Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(512) 
;

-- 2021-06-29T16:30:11.566Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649872
;

-- 2021-06-29T16:30:11.598Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649872)
;

-- 2021-06-29T16:30:12.061Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574796,649873,0,544118,TO_TIMESTAMP('2021-06-29 19:30:11','YYYY-MM-DD HH24:MI:SS'),100,'Name des Ortes',60,'D','Bezeichnet einen einzelnen Ort in diesem Land oder dieser Region.','Y','N','N','N','N','N','N','N','Ort',TO_TIMESTAMP('2021-06-29 19:30:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:12.093Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:12.124Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(225) 
;

-- 2021-06-29T16:30:12.160Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649873
;

-- 2021-06-29T16:30:12.194Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649873)
;

-- 2021-06-29T16:30:12.667Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574797,649874,0,544118,TO_TIMESTAMP('2021-06-29 19:30:12','YYYY-MM-DD HH24:MI:SS'),100,'Land',10,'D','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','N','N','N','N','N','Land',TO_TIMESTAMP('2021-06-29 19:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:12.699Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649874 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:12.733Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(192) 
;

-- 2021-06-29T16:30:12.774Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649874
;

-- 2021-06-29T16:30:12.805Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649874)
;

-- 2021-06-29T16:30:13.263Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574798,649875,0,544118,TO_TIMESTAMP('2021-06-29 19:30:12','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer-Name/Konto (ID) im EMail-System',60,'D','The user name in the mail system is usually the string before the @ of your email address. Notwendig, wenn der EMail-SErver eine Anmeldung vor dem Versenden von EMails verlangt.','Y','N','N','N','N','N','N','N','EMail Nutzer-ID',TO_TIMESTAMP('2021-06-29 19:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:13.293Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:13.325Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1896) 
;

-- 2021-06-29T16:30:13.357Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649875
;

-- 2021-06-29T16:30:13.388Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649875)
;

-- 2021-06-29T16:30:13.859Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574799,649876,0,544118,TO_TIMESTAMP('2021-06-29 19:30:13','YYYY-MM-DD HH24:MI:SS'),100,29,'D','Y','N','N','N','N','N','N','N','Master Start Date',TO_TIMESTAMP('2021-06-29 19:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:13.897Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649876 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:13.932Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543413) 
;

-- 2021-06-29T16:30:13.972Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649876
;

-- 2021-06-29T16:30:14.003Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649876)
;

-- 2021-06-29T16:30:14.473Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574800,649877,0,544118,TO_TIMESTAMP('2021-06-29 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,29,'D','Y','N','N','N','N','N','N','N','Master End Date',TO_TIMESTAMP('2021-06-29 19:30:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:14.510Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649877 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:14.543Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543414) 
;

-- 2021-06-29T16:30:14.581Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649877
;

-- 2021-06-29T16:30:14.615Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649877)
;

-- 2021-06-29T16:30:15.079Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574801,649878,0,544118,TO_TIMESTAMP('2021-06-29 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,3,'D','Y','N','N','N','N','N','N','N','Termination Reason',TO_TIMESTAMP('2021-06-29 19:30:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:30:15.117Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649878 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:30:15.150Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543465) 
;

-- 2021-06-29T16:30:15.186Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649878
;

-- 2021-06-29T16:30:15.221Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649878)
;

-- 2021-06-29T16:38:39.156Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649858
;

-- 2021-06-29T16:38:40.544Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649859
;

-- 2021-06-29T16:38:41.897Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649860
;

-- 2021-06-29T16:38:43.200Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649861
;

-- 2021-06-29T16:38:44.437Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649862
;

-- 2021-06-29T16:38:45.739Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649863
;

-- 2021-06-29T16:38:47.053Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649864
;

-- 2021-06-29T16:38:48.367Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649865
;

-- 2021-06-29T16:38:49.670Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649866
;

-- 2021-06-29T16:38:50.975Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649867
;

-- 2021-06-29T16:38:52.375Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649868
;

-- 2021-06-29T16:38:53.685Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649869
;

-- 2021-06-29T16:38:54.950Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649870
;

-- 2021-06-29T16:38:56.246Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649871
;

-- 2021-06-29T16:38:57.495Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649872
;

-- 2021-06-29T16:38:58.756Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649873
;

-- 2021-06-29T16:39:00.003Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:38:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649874
;

-- 2021-06-29T16:39:01.371Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:39:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649875
;

-- 2021-06-29T16:39:02.705Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:39:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649876
;

-- 2021-06-29T16:39:04.061Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:39:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649877
;

-- 2021-06-29T16:39:09.578Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-29 19:39:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649878
;

-- 2021-06-29T16:39:30.688Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544118,543259,TO_TIMESTAMP('2021-06-29 19:39:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-06-29 19:39:30','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-06-29T16:39:30.704Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543259 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-06-29T16:39:31.121Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544078,543259,TO_TIMESTAMP('2021-06-29 19:39:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-06-29 19:39:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:31.452Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544079,543259,TO_TIMESTAMP('2021-06-29 19:39:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-06-29 19:39:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:31.869Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,544078,546163,TO_TIMESTAMP('2021-06-29 19:39:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2021-06-29 19:39:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:32.770Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649858,0,544118,546163,586885,'F',TO_TIMESTAMP('2021-06-29 19:39:32','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2021-06-29 19:39:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:33.175Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649859,0,544118,546163,586886,'F',TO_TIMESTAMP('2021-06-29 19:39:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kategorie',20,0,0,TO_TIMESTAMP('2021-06-29 19:39:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:33.578Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649860,0,544118,546163,586887,'F',TO_TIMESTAMP('2021-06-29 19:39:33','YYYY-MM-DD HH24:MI:SS'),100,'Sponsor-Nr.','Suchschlüssel für den Geschäftspartner','Y','N','Y','N','N','Nr.',30,0,0,TO_TIMESTAMP('2021-06-29 19:39:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:33.964Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649861,0,544118,546163,586888,'F',TO_TIMESTAMP('2021-06-29 19:39:33','YYYY-MM-DD HH24:MI:SS'),100,'Name des Sponsors.','Y','N','Y','N','N','Name',40,0,0,TO_TIMESTAMP('2021-06-29 19:39:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:34.397Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649862,0,544118,546163,586889,'F',TO_TIMESTAMP('2021-06-29 19:39:34','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.','Y','N','Y','N','N','Anrede (ID)',50,0,0,TO_TIMESTAMP('2021-06-29 19:39:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:34.813Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649863,0,544118,546163,586890,'F',TO_TIMESTAMP('2021-06-29 19:39:34','YYYY-MM-DD HH24:MI:SS'),100,'Für Briefe - z.B. "Sehr geehrter {0}" oder "Sehr geehrter Herr {0}" - Zur Laufzeit wird  "{0}" durch den Namen ersetzt','Anrede definiert, was auf einem Brief an einen Geschäftspartner gedruckt wird.','Y','N','Y','N','N','Anrede',60,0,0,TO_TIMESTAMP('2021-06-29 19:39:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:35.214Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649864,0,544118,546163,586891,'F',TO_TIMESTAMP('2021-06-29 19:39:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Briefanrede',70,0,0,TO_TIMESTAMP('2021-06-29 19:39:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:35.631Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649865,0,544118,546163,586892,'F',TO_TIMESTAMP('2021-06-29 19:39:35','YYYY-MM-DD HH24:MI:SS'),100,'Vorname','Y','N','Y','N','N','Vorname',80,0,0,TO_TIMESTAMP('2021-06-29 19:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:36.049Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649866,0,544118,546163,586893,'F',TO_TIMESTAMP('2021-06-29 19:39:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Nachname',90,0,0,TO_TIMESTAMP('2021-06-29 19:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:36.450Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649867,0,544118,546163,586894,'F',TO_TIMESTAMP('2021-06-29 19:39:36','YYYY-MM-DD HH24:MI:SS'),100,'Geburtstag oder Jahrestag','Geburtstag oder Jahrestag','Y','N','Y','N','N','Geburtstag',100,0,0,TO_TIMESTAMP('2021-06-29 19:39:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:36.904Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649868,0,544118,546163,586895,'F',TO_TIMESTAMP('2021-06-29 19:39:36','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 1 für diesen Standort','"Adresszeile 1" gibt die Anschrift für diesen Standort an.','Y','N','Y','N','N','Straße und Nr.',110,0,0,TO_TIMESTAMP('2021-06-29 19:39:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:37.306Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649869,0,544118,546163,586896,'F',TO_TIMESTAMP('2021-06-29 19:39:36','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 2 für diesen Standort','"Adresszeile 2" bietet weitere Adressangaben für diesen Standort. Z.B. Gebäudenummer, Stockwerk, Raumnummer o.ä.','Y','N','Y','N','N','Adresszusatz',120,0,0,TO_TIMESTAMP('2021-06-29 19:39:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:37.707Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649870,0,544118,546163,586897,'F',TO_TIMESTAMP('2021-06-29 19:39:37','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeilee 3 für diesen Standort','"Adresszeile 2" bietet weitere Adressangaben für diesen Standort. Z.B. Gebäudenummer, Stockwerk, Raumnummer o.ä.','Y','N','Y','N','N','Adresszeile 3',130,0,0,TO_TIMESTAMP('2021-06-29 19:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:38.152Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649871,0,544118,546163,586898,'F',TO_TIMESTAMP('2021-06-29 19:39:37','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 4 für diesen Standort','The Address 4 provides additional address information for an entity. Z.B. Gebäudenummer, Stockwerk, Raumnummer o.ä.','Y','N','Y','N','N','Adresszusatz',140,0,0,TO_TIMESTAMP('2021-06-29 19:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:38.540Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649872,0,544118,546163,586899,'F',TO_TIMESTAMP('2021-06-29 19:39:38','YYYY-MM-DD HH24:MI:SS'),100,'Postleitzahl','"PLZ" bezeichnet die Postleitzahl für diese Adresse oder dieses Postfach.','Y','N','Y','N','N','PLZ',150,0,0,TO_TIMESTAMP('2021-06-29 19:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:38.989Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649873,0,544118,546163,586900,'F',TO_TIMESTAMP('2021-06-29 19:39:38','YYYY-MM-DD HH24:MI:SS'),100,'Name des Ortes','Bezeichnet einen einzelnen Ort in diesem Land oder dieser Region.','Y','N','Y','N','N','Ort',160,0,0,TO_TIMESTAMP('2021-06-29 19:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:39.405Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649874,0,544118,546163,586901,'F',TO_TIMESTAMP('2021-06-29 19:39:39','YYYY-MM-DD HH24:MI:SS'),100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','Y','N','N','Land',170,0,0,TO_TIMESTAMP('2021-06-29 19:39:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:39.807Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649875,0,544118,546163,586902,'F',TO_TIMESTAMP('2021-06-29 19:39:39','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer-Name/Konto (ID) im EMail-System','The user name in the mail system is usually the string before the @ of your email address. Notwendig, wenn der EMail-SErver eine Anmeldung vor dem Versenden von EMails verlangt.','Y','N','Y','N','N','EMail Nutzer-ID',180,0,0,TO_TIMESTAMP('2021-06-29 19:39:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:40.208Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649876,0,544118,546163,586903,'F',TO_TIMESTAMP('2021-06-29 19:39:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Master Start Date',190,0,0,TO_TIMESTAMP('2021-06-29 19:39:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:40.615Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649877,0,544118,546163,586904,'F',TO_TIMESTAMP('2021-06-29 19:39:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Master End Date',200,0,0,TO_TIMESTAMP('2021-06-29 19:39:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:39:41.031Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649878,0,544118,546163,586905,'F',TO_TIMESTAMP('2021-06-29 19:39:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Termination Reason',210,0,0,TO_TIMESTAMP('2021-06-29 19:39:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:42:02.641Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-06-29 19:42:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586885
;

-- 2021-06-29T16:42:02.804Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-06-29 19:42:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586886
;

-- 2021-06-29T16:42:02.942Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-06-29 19:42:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586887
;

-- 2021-06-29T16:42:03.073Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-06-29 19:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586888
;

-- 2021-06-29T16:42:03.237Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-06-29 19:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586889
;

-- 2021-06-29T16:42:03.358Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-06-29 19:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586890
;

-- 2021-06-29T16:42:03.490Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-06-29 19:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586891
;

-- 2021-06-29T16:42:03.622Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-06-29 19:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586893
;

-- 2021-06-29T16:42:03.760Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-06-29 19:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586892
;

-- 2021-06-29T16:42:03.891Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-06-29 19:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586894
;

-- 2021-06-29T16:42:04.039Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2021-06-29 19:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586895
;

-- 2021-06-29T16:42:04.177Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2021-06-29 19:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586896
;

-- 2021-06-29T16:42:04.309Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2021-06-29 19:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586897
;

-- 2021-06-29T16:42:04.447Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2021-06-29 19:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586898
;

-- 2021-06-29T16:42:04.578Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2021-06-29 19:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586899
;

-- 2021-06-29T16:42:04.717Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2021-06-29 19:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586900
;

-- 2021-06-29T16:42:04.841Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2021-06-29 19:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586901
;

-- 2021-06-29T16:42:04.971Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2021-06-29 19:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586902
;

-- 2021-06-29T16:42:05.095Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2021-06-29 19:42:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586903
;

-- 2021-06-29T16:42:05.223Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2021-06-29 19:42:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586904
;

-- 2021-06-29T16:42:05.349Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2021-06-29 19:42:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586905
;

-- 2021-06-29T16:43:13.385Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_bpartner_export','Address1','VARCHAR(100)',null,null)
;

-- 2021-06-29T16:44:34.897Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574811,102,0,19,541731,'AD_Client_ID',TO_TIMESTAMP('2021-06-29 19:44:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','N','Mandant',0,TO_TIMESTAMP('2021-06-29 19:44:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T16:44:34.944Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574811 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T16:44:35.009Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-06-29T16:44:38.507Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574812,126,0,19,541731,'AD_Table_ID',TO_TIMESTAMP('2021-06-29 19:44:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Database Table information','D',10,'The Database Table provides the information of the table definition','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','DB-Tabelle',0,TO_TIMESTAMP('2021-06-29 19:44:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T16:44:38.553Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574812 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T16:44:38.606Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(126) 
;

-- 2021-06-29T16:44:42.032Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574813,2978,0,19,541731,'CM_Template_ID',TO_TIMESTAMP('2021-06-29 19:44:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Template defines how content is displayed','D',10,'A template describes how content should get displayed, it contains layout and maybe also scripts on how to handle the content','Y','Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Vorlage',0,TO_TIMESTAMP('2021-06-29 19:44:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T16:44:42.081Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574813 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T16:44:42.144Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2978) 
;

-- 2021-06-29T16:44:44.748Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574814,245,0,16,541731,'Created',TO_TIMESTAMP('2021-06-29 19:44:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',7,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Erstellt',0,TO_TIMESTAMP('2021-06-29 19:44:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T16:44:44.785Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574814 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T16:44:44.847Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-06-29T16:44:47.255Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574815,246,0,18,110,541731,'CreatedBy',TO_TIMESTAMP('2021-06-29 19:44:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-06-29 19:44:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T16:44:47.294Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574815 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T16:44:47.360Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-06-29T16:44:49.590Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574816,275,0,10,541731,'Description',TO_TIMESTAMP('2021-06-29 19:44:49','YYYY-MM-DD HH24:MI:SS'),100,'N','D',255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Beschreibung',0,TO_TIMESTAMP('2021-06-29 19:44:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T16:44:49.624Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574816 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T16:44:49.685Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- 2021-06-29T16:44:51.698Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574817,348,0,20,541731,'IsActive',TO_TIMESTAMP('2021-06-29 19:44:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','Aktiv',0,TO_TIMESTAMP('2021-06-29 19:44:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T16:44:51.728Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574817 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T16:44:51.806Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-06-29T16:44:53.786Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574818,469,0,10,541731,'Name',TO_TIMESTAMP('2021-06-29 19:44:53','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',120,'','Y','Y','N','N','N','N','Y','N','Y','N','Y','N','N','Y','Name',1,TO_TIMESTAMP('2021-06-29 19:44:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T16:44:53.825Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574818 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T16:44:53.887Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2021-06-29T16:44:55.661Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574819,2642,0,14,541731,'OtherClause',TO_TIMESTAMP('2021-06-29 19:44:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Other SQL Clause','D',2000,'Any other complete clause like GROUP BY, HAVING, ORDER BY, etc. after WHERE clause.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Other SQL Clause',0,TO_TIMESTAMP('2021-06-29 19:44:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T16:44:55.693Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574819 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T16:44:55.761Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2642) 
;

-- 2021-06-29T16:44:57.638Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574820,607,0,16,541731,'Updated',TO_TIMESTAMP('2021-06-29 19:44:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',7,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-06-29 19:44:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T16:44:57.682Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574820 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T16:44:57.739Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-06-29T16:44:59.674Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574821,608,0,18,110,541731,'UpdatedBy',TO_TIMESTAMP('2021-06-29 19:44:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-06-29 19:44:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T16:44:59.712Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574821 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T16:44:59.777Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-06-29T16:45:01.702Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574822,630,0,14,541731,'WhereClause',TO_TIMESTAMP('2021-06-29 19:45:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Fully qualified SQL WHERE clause','D',2000,'The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Sql WHERE',0,TO_TIMESTAMP('2021-06-29 19:45:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T16:45:01.733Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574822 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T16:45:01.818Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(630) 
;

-- 2021-06-29T16:46:18.449Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574813
;

-- 2021-06-29T16:46:18.658Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=574813
;

-- 2021-06-29T16:46:29.225Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574822
;

-- 2021-06-29T16:46:29.413Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=574822
;

CREATE TABLE public.C_BPartner_Export
(
    AD_Client_ID         NUMERIC(10)                            NOT NULL,
    Address1             VARCHAR(100),
    Address2             VARCHAR(100),
    Address3             VARCHAR(100),
    Address4             VARCHAR(100),
    AD_Org_ID            NUMERIC(10),
    Birthday             TIMESTAMP WITH TIME ZONE,
    BPName               VARCHAR(60),
    BPValue              VARCHAR(40),
    Category             TEXT,
    C_BPartner_Export_ID NUMERIC(10),
    C_Country_ID         NUMERIC(10),
    C_Greeting_ID        NUMERIC(10),
    City                 VARCHAR(60),
    Created              TIMESTAMP WITH TIME ZONE               NOT NULL,
    CreatedBy            NUMERIC(10)                            NOT NULL,

    EMailUser            VARCHAR(60),
    Firstname            VARCHAR(255),
    Greeting             VARCHAR(60),
    IsActive             CHAR(1) CHECK (IsActive IN ('Y', 'N')) NOT NULL,
    Lastname             VARCHAR(255),
    Letter_Salutation    VARCHAR(60),
    MasterEndDate        TIMESTAMP WITH TIME ZONE,
    MasterStartDate      TIMESTAMP WITH TIME ZONE,

    Postal               VARCHAR(10),
    TerminationReason    VARCHAR(3),
    Updated              TIMESTAMP WITH TIME ZONE               NOT NULL,
    UpdatedBy            NUMERIC(10)                            NOT NULL,
    CONSTRAINT C_BPartner_Export_Key PRIMARY KEY (C_BPartner_Export_ID),
    CONSTRAINT CCountry_CBPartnerExport FOREIGN KEY (C_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CGreeting_CBPartnerExport FOREIGN KEY (C_Greeting_ID) REFERENCES public.C_Greeting DEFERRABLE INITIALLY DEFERRED
)
;

-- 2021-06-30T06:56:15.891Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,579402,541728,0,541174,TO_TIMESTAMP('2021-06-30 09:56:15','YYYY-MM-DD HH24:MI:SS'),100,'D','C_BPartner_Export','Y','N','Y','N','N','Partner Export',TO_TIMESTAMP('2021-06-30 09:56:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T06:56:15.918Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541728 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-06-30T06:56:15.949Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541728, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541728)
;

-- 2021-06-30T06:56:16.018Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(579402) 
;

-- 2021-06-30T06:56:18.409Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53043, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53047 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:18.442Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53043, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53045 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:18.474Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53043, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53046 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:18.505Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53043, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53044 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:18.537Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53043, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541728 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.075Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.106Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541234 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.144Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000021 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.173Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541578 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.205Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541728 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.236Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.275Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.307Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.322Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.360Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.391Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.422Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541217 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.460Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541218 AND AD_Tree_ID=10
;

-- 2021-06-30T06:56:32.507Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541219 AND AD_Tree_ID=10
;

-- 2021-06-30T06:58:08.144Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-06-30 09:58:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574818
;

-- 2021-06-30T06:58:09.897Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2021-06-30 09:58:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574781
;

-- 2021-06-30T06:58:11.488Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2021-06-30 09:58:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574782
;

-- 2021-06-30T06:58:12.996Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2021-06-30 09:58:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574795
;

-- 2021-06-30T06:59:24.620Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='B',Updated=TO_TIMESTAMP('2021-06-30 09:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574795
;

-- 2021-06-30T06:59:31.879Z
-- URL zum Konzept
UPDATE AD_Column SET IsFacetFilter='Y',Updated=TO_TIMESTAMP('2021-06-30 09:59:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574795
;

-- 2021-06-30T07:10:28.706Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2021-06-30 10:10:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574799
;

-- 2021-06-30T07:10:30.126Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2021-06-30 10:10:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574800
;

-- 2021-06-30T07:10:52.589Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='B',Updated=TO_TIMESTAMP('2021-06-30 10:10:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574799
;

-- 2021-06-30T07:11:06.587Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='B',Updated=TO_TIMESTAMP('2021-06-30 10:11:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574800
;

-- 2021-06-30T07:16:42.059Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574819
;

-- 2021-06-30T07:16:42.258Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=574819
;

-- 2021-06-30T07:16:49.572Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574816
;

-- 2021-06-30T07:16:49.767Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=574816
;

-- 2021-06-30T07:17:08.130Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574818
;

-- 2021-06-30T07:17:08.332Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=574818
;

-- 2021-06-30T07:17:27.421Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574812
;

-- 2021-06-30T07:17:27.617Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=574812
;

-- 2021-06-30T07:32:50.614Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E',Updated=TO_TIMESTAMP('2021-06-30 10:32:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574795
;

-- 2021-06-30T07:35:25.026Z
-- URL zum Konzept
UPDATE AD_Column SET MaxFacetsToFetch=50,Updated=TO_TIMESTAMP('2021-06-30 10:35:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574795
;

-- 2021-06-30T07:45:56.688Z
-- URL zum Konzept
INSERT INTO AD_UserQuery (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_User_ID,AD_UserQuery_ID,Created,CreatedBy,IsActive,IsManadatoryParams,IsShowAllParams,Name,Updated,UpdatedBy) VALUES (0,0,544118,541731,100,540107,TO_TIMESTAMP('2021-06-30 10:45:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','C_BPartner_Export_Postal1',TO_TIMESTAMP('2021-06-30 10:45:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T08:02:04.813Z
-- URL zum Konzept
UPDATE AD_UserQuery SET Code='<^>Postal<^> BETWEEN <^><^>''',Updated=TO_TIMESTAMP('2021-06-30 11:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UserQuery_ID=540107
;

-- 2021-06-30T08:02:26.017Z
-- URL zum Konzept
UPDATE AD_UserQuery SET Code='AND<^>Postal<^> BETWEEN <^><^>',Updated=TO_TIMESTAMP('2021-06-30 11:02:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UserQuery_ID=540107
;

-- 2021-06-30T08:56:38.587Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574823,579361,0,20,541731,'ExcludeFromPromotions',TO_TIMESTAMP('2021-06-30 11:56:38','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Keine Werbung',0,0,TO_TIMESTAMP('2021-06-30 11:56:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-30T08:56:38.617Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574823 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-30T08:56:38.717Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579361) 
;

-- 2021-06-30T08:56:44.415Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Export','ALTER TABLE public.C_BPartner_Export ADD COLUMN ExcludeFromPromotions CHAR(1) DEFAULT ''N'' CHECK (ExcludeFromPromotions IN (''Y'',''N'')) NOT NULL')
;

-- 2021-06-30T08:57:06.296Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574824,541436,0,19,541731,'C_Postal_ID',TO_TIMESTAMP('2021-06-30 11:57:05','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Postal codes',0,0,TO_TIMESTAMP('2021-06-30 11:57:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-30T08:57:06.325Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574824 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-30T08:57:06.399Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(541436) 
;

-- 2021-06-30T08:57:13.251Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Export','ALTER TABLE public.C_BPartner_Export ADD COLUMN C_Postal_ID NUMERIC(10)')
;

-- 2021-06-30T08:57:13.294Z
-- URL zum Konzept
ALTER TABLE C_BPartner_Export ADD CONSTRAINT CPostal_CBPartnerExport FOREIGN KEY (C_Postal_ID) REFERENCES public.C_Postal DEFERRABLE INITIALLY DEFERRED
;

-- 2021-06-30T08:57:19.915Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2021-06-30 11:57:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574824
;

-- 2021-06-30T08:57:31.354Z
-- URL zum Konzept
UPDATE AD_Column SET IsFacetFilter='Y',Updated=TO_TIMESTAMP('2021-06-30 11:57:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574824
;

-- 2021-06-30T08:59:58.940Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574811,649880,0,544118,TO_TIMESTAMP('2021-06-30 11:59:58','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-06-30 11:59:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T08:59:58.971Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649880 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-30T08:59:59.003Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-06-30T08:59:59.304Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649880
;

-- 2021-06-30T08:59:59.342Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649880)
;

-- 2021-06-30T08:59:59.813Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574817,649881,0,544118,TO_TIMESTAMP('2021-06-30 11:59:59','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-06-30 11:59:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T08:59:59.844Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-30T08:59:59.876Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-06-30T09:00:00.124Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649881
;

-- 2021-06-30T09:00:00.157Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649881)
;

-- 2021-06-30T09:00:00.632Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574823,649882,0,544118,TO_TIMESTAMP('2021-06-30 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Keine Werbung',TO_TIMESTAMP('2021-06-30 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T09:00:00.670Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649882 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-30T09:00:00.704Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579361) 
;

-- 2021-06-30T09:00:00.733Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649882
;

-- 2021-06-30T09:00:00.764Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649882)
;

-- 2021-06-30T09:00:01.223Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574824,649883,0,544118,TO_TIMESTAMP('2021-06-30 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Postal codes',TO_TIMESTAMP('2021-06-30 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T09:00:01.251Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649883 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-30T09:00:01.297Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541436) 
;

-- 2021-06-30T09:00:01.323Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649883
;

-- 2021-06-30T09:00:01.351Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649883)
;

-- 2021-06-30T09:00:50.207Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649883,0,544118,546163,586907,'F',TO_TIMESTAMP('2021-06-30 12:00:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Postal codes',220,0,0,TO_TIMESTAMP('2021-06-30 12:00:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T09:01:21.521Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649882,0,544118,546163,586908,'F',TO_TIMESTAMP('2021-06-30 12:01:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Keine Werbung',230,0,0,TO_TIMESTAMP('2021-06-30 12:01:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T09:08:59.838Z
-- URL zum Konzept
UPDATE AD_Column SET FacetFilterSeqNo=0, IsFacetFilter='N',Updated=TO_TIMESTAMP('2021-06-30 12:08:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574795
;

-- 2021-06-30T09:09:02.617Z
-- URL zum Konzept
UPDATE AD_Column SET FacetFilterSeqNo=10, IsFacetFilter='Y',Updated=TO_TIMESTAMP('2021-06-30 12:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574824
;

-- 2021-06-30T09:13:46.861Z
-- URL zum Konzept
UPDATE AD_Column SET MaxFacetsToFetch=20,Updated=TO_TIMESTAMP('2021-06-30 12:13:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574824
;

-- 2021-06-30T09:18:20.356Z
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2021-06-30 12:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545746
;

-- 2021-06-30T09:20:33.949Z
-- URL zum Konzept
UPDATE AD_Column SET SeqNo=2,Updated=TO_TIMESTAMP('2021-06-30 12:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545746
;

-- 2021-06-30T09:21:34.617Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_postal','Postal','VARCHAR(10)',null,null)
;

-- 2021-06-30T09:23:46.170Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='B',Updated=TO_TIMESTAMP('2021-06-30 12:23:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574795
;

-- 2021-06-30T09:27:44.072Z
-- URL zum Konzept
UPDATE AD_Column SET IsShowFilterIncrementButtons='Y',Updated=TO_TIMESTAMP('2021-06-30 12:27:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574795
;

-- 2021-06-30T09:31:41.700Z
-- URL zum Konzept
UPDATE AD_UserQuery SET Code='AND^>MasterEndDate^> BETWEEN ^>^>',Updated=TO_TIMESTAMP('2021-06-30 12:31:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UserQuery_ID=540107
;

-- 2021-06-30T09:33:08.742Z
-- URL zum Konzept
UPDATE AD_UserQuery SET Code='AND^>MasterEndDate^> BETWEEN <^>^>', IsActive='Y',Updated=TO_TIMESTAMP('2021-06-30 12:33:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UserQuery_ID=540107
;

-- 2021-06-30T09:34:11.592Z
-- URL zum Konzept
UPDATE AD_UserQuery SET Code='AND<^>Created<^> BETWEEN <^><^>', IsActive='Y',Updated=TO_TIMESTAMP('2021-06-30 12:34:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UserQuery_ID=540107
;

-- 2021-06-30T10:26:36.134Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='B',Updated=TO_TIMESTAMP('2021-06-30 13:26:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545746
;

-- 2021-06-30T10:39:55.403Z
-- URL zum Konzept
UPDATE AD_Table SET IsHighVolume='Y',Updated=TO_TIMESTAMP('2021-06-30 13:39:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=259
;












-- 2021-06-30T11:47:51.764Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E',Updated=TO_TIMESTAMP('2021-06-30 14:47:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574782
;

-- 2021-06-30T13:01:39.246Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsShowFilterIncrementButtons='N',Updated=TO_TIMESTAMP('2021-06-30 16:01:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574795
;

-- 2021-06-30T13:08:13.756Z
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540608,0,541731,TO_TIMESTAMP('2021-06-30 16:08:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BPartner_Export_BPName','N',TO_TIMESTAMP('2021-06-30 16:08:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T13:08:13.792Z
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540608 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-06-30T13:08:27.976Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,574784,541116,540608,0,TO_TIMESTAMP('2021-06-30 16:08:27','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',10,TO_TIMESTAMP('2021-06-30 16:08:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T13:08:33.721Z
-- URL zum Konzept
UPDATE AD_Index_Column SET EntityType='D',Updated=TO_TIMESTAMP('2021-06-30 16:08:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=541116
;

-- 2021-06-30T13:08:50.947Z
-- URL zum Konzept
CREATE INDEX C_BPartner_Export_BPName ON C_BPartner_Export (BPName)
;








-- 2021-06-30T15:10:03.377Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541358,TO_TIMESTAMP('2021-06-30 18:10:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BPartner_Export_Category',TO_TIMESTAMP('2021-06-30 18:10:03','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-06-30T15:10:03.407Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541358 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-06-30T15:10:39.244Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541358,542686,TO_TIMESTAMP('2021-06-30 18:10:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mitglied',TO_TIMESTAMP('2021-06-30 18:10:38','YYYY-MM-DD HH24:MI:SS'),100,'Mitglied','Mitglied')
;

-- 2021-06-30T15:10:39.284Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542686 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-30T15:10:59.042Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541358,542687,TO_TIMESTAMP('2021-06-30 18:10:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kunde',TO_TIMESTAMP('2021-06-30 18:10:58','YYYY-MM-DD HH24:MI:SS'),100,'Kunde','Kunde')
;

-- 2021-06-30T15:10:59.074Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542687 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-30T15:11:08.683Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541358,542688,TO_TIMESTAMP('2021-06-30 18:11:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abonnent',TO_TIMESTAMP('2021-06-30 18:11:08','YYYY-MM-DD HH24:MI:SS'),100,'Abonnent','Abonnent')
;

-- 2021-06-30T15:11:08.705Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542688 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-30T15:11:42.081Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541358,Updated=TO_TIMESTAMP('2021-06-30 18:11:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574782
;

-- 2021-06-30T15:12:04.800Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2021-06-30 18:12:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574782
;

-- 2021-06-30T15:12:15.456Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_bpartner_export','Category','VARCHAR(255)',null,null)
;

-- 2021-06-30T15:17:41.893Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574874,2159,0,19,541731,'AD_Language_ID',TO_TIMESTAMP('2021-06-30 18:17:41','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sprache',0,0,TO_TIMESTAMP('2021-06-30 18:17:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-30T15:17:41.924Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574874 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-30T15:17:41.990Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2159) 
;

-- 2021-06-30T15:19:39.067Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2021-06-30 18:19:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574874
;

-- 2021-06-30T15:42:03.322Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=109, AD_Reference_ID=10, ColumnName='AD_Language', Description='Sprache für diesen Eintrag', Help='Definiert die Sprache für Anzeige und Aufbereitung', IsExcludeFromZoomTargets='Y', Name='Sprache',Updated=TO_TIMESTAMP('2021-06-30 18:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574874
;

-- 2021-06-30T15:42:03.356Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Sprache', Description='Sprache für diesen Eintrag', Help='Definiert die Sprache für Anzeige und Aufbereitung' WHERE AD_Column_ID=574874
;

-- 2021-06-30T15:42:03.413Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(109) 
;

-- 2021-06-30T15:42:08.060Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Export','ALTER TABLE public.C_BPartner_Export ADD COLUMN AD_Language VARCHAR(10)')
;

-- 2021-06-30T16:24:11.900Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574875,540648,0,10,541731,'Companyname',TO_TIMESTAMP('2021-06-30 19:24:11','YYYY-MM-DD HH24:MI:SS'),100,'N','U',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Firmenname',0,0,TO_TIMESTAMP('2021-06-30 19:24:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-30T16:24:11.936Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574875 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-30T16:24:12.006Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(540648) 
;

-- 2021-06-30T16:24:18.112Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Export','ALTER TABLE public.C_BPartner_Export ADD COLUMN Companyname VARCHAR(255)')
;

-- 2021-06-30T16:24:26.149Z
-- URL zum Konzept
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2021-06-30 19:24:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574875
;

-- 2021-06-30T16:24:30.678Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_bpartner_export','Companyname','VARCHAR(255)',null,null)
;

-- 2021-06-30T16:25:02.170Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574876,540009,0,17,540918,541731,'ContractStatus',TO_TIMESTAMP('2021-06-30 19:25:01','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vertrags-Status',0,0,TO_TIMESTAMP('2021-06-30 19:25:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-30T16:25:02.202Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574876 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-30T16:25:02.271Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(540009) 
;





-- 2021-06-30T16:39:20.740Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='N', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-06-30 19:39:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574798
;

-- 2021-06-30T16:50:15.508Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579407,0,'HasDifferentBillpartner',TO_TIMESTAMP('2021-06-30 19:50:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abweichende Rechnungsaddresse','Abweichende Rechnungsaddresse',TO_TIMESTAMP('2021-06-30 19:50:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T16:50:15.539Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579407 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-30T16:50:27.588Z
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='HasDifferentBillPartner',Updated=TO_TIMESTAMP('2021-06-30 19:50:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579407
;

-- 2021-06-30T16:50:27.714Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='HasDifferentBillPartner', Name='Abweichende Rechnungsaddresse', Description=NULL, Help=NULL WHERE AD_Element_ID=579407
;

-- 2021-06-30T16:50:27.745Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='HasDifferentBillPartner', Name='Abweichende Rechnungsaddresse', Description=NULL, Help=NULL, AD_Element_ID=579407 WHERE UPPER(ColumnName)='HASDIFFERENTBILLPARTNER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-30T16:50:27.780Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='HasDifferentBillPartner', Name='Abweichende Rechnungsaddresse', Description=NULL, Help=NULL WHERE AD_Element_ID=579407 AND IsCentrallyMaintained='Y'
;

-- 2021-06-30T16:50:54.868Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574877,579407,0,20,541731,'HasDifferentBillPartner',TO_TIMESTAMP('2021-06-30 19:50:54','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Abweichende Rechnungsaddresse',0,0,TO_TIMESTAMP('2021-06-30 19:50:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-30T16:50:54.898Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574877 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-30T16:50:54.959Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579407) 
;

-- 2021-06-30T16:51:00.058Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Export','ALTER TABLE public.C_BPartner_Export ADD COLUMN HasDifferentBillPartner CHAR(1) DEFAULT ''N'' CHECK (HasDifferentBillPartner IN (''Y'',''N'')) NOT NULL')
;

-- 2021-06-30T16:51:17.870Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='N', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-06-30 19:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574877
;

-- 2021-06-30T16:51:29.193Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E',Updated=TO_TIMESTAMP('2021-06-30 19:51:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574877
;

-- 2021-06-30T16:51:55.215Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574878,543889,0,30,541731,'C_CompensationGroup_Schema_ID',TO_TIMESTAMP('2021-06-30 19:51:54','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.order',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kompensationsgruppe Schema',0,0,TO_TIMESTAMP('2021-06-30 19:51:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-30T16:51:55.245Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574878 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-30T16:51:55.309Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(543889) 
;

-- 2021-06-30T16:52:06.458Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-06-30 19:52:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574878
;

-- 2021-06-30T16:57:04.466Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574879,544034,0,30,541731,'MKTG_Campaign_ID',TO_TIMESTAMP('2021-06-30 19:57:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.marketing.base',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'MKTG_Campaign',0,0,TO_TIMESTAMP('2021-06-30 19:57:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-30T16:57:04.497Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574879 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-30T16:57:04.561Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(544034) 
;

-- 2021-06-30T16:57:10.976Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Export','ALTER TABLE public.C_BPartner_Export ADD COLUMN MKTG_Campaign_ID NUMERIC(10)')
;

-- 2021-06-30T16:57:11.024Z
-- URL zum Konzept
ALTER TABLE C_BPartner_Export ADD CONSTRAINT MKTGCampaign_CBPartnerExport FOREIGN KEY (MKTG_Campaign_ID) REFERENCES public.MKTG_Campaign DEFERRABLE INITIALLY DEFERRED
;

-- 2021-06-30T16:57:18.522Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-06-30 19:57:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574879
;

-- 2021-06-30T17:03:17.365Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Export','ALTER TABLE public.C_BPartner_Export ADD COLUMN C_CompensationGroup_Schema_ID NUMERIC(10)')
;

-- 2021-06-30T17:03:17.402Z
-- URL zum Konzept
ALTER TABLE C_BPartner_Export ADD CONSTRAINT CCompensationGroupSchema_CBPartnerExport FOREIGN KEY (C_CompensationGroup_Schema_ID) REFERENCES public.C_CompensationGroup_Schema DEFERRABLE INITIALLY DEFERRED
;











-- 2021-06-30T17:04:36.948Z
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=541174,Updated=TO_TIMESTAMP('2021-06-30 20:04:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541731
;

-- 2021-06-30T17:07:56.308Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574874,649888,0,544118,TO_TIMESTAMP('2021-06-30 20:07:55','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag',10,'D','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','N','N','N','N','N','N','Sprache',TO_TIMESTAMP('2021-06-30 20:07:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T17:07:56.342Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649888 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-30T17:07:56.377Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(109) 
;

-- 2021-06-30T17:07:56.505Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649888
;

-- 2021-06-30T17:07:56.543Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649888)
;

-- 2021-06-30T17:07:57.004Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574875,649889,0,544118,TO_TIMESTAMP('2021-06-30 20:07:56','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Firmenname',TO_TIMESTAMP('2021-06-30 20:07:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T17:07:57.035Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649889 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-30T17:07:57.069Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540648) 
;

-- 2021-06-30T17:07:57.118Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649889
;

-- 2021-06-30T17:07:57.149Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649889)
;

-- 2021-06-30T17:07:57.625Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574876,649890,0,544118,TO_TIMESTAMP('2021-06-30 20:07:57','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','N','N','N','N','N','N','N','Vertrags-Status',TO_TIMESTAMP('2021-06-30 20:07:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T17:07:57.660Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649890 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-30T17:07:57.695Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540009) 
;

-- 2021-06-30T17:07:57.756Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649890
;

-- 2021-06-30T17:07:57.790Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649890)
;

-- 2021-06-30T17:07:58.278Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574877,649891,0,544118,TO_TIMESTAMP('2021-06-30 20:07:57','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Abweichende Rechnungsaddresse',TO_TIMESTAMP('2021-06-30 20:07:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T17:07:58.313Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649891 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-30T17:07:58.346Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579407) 
;

-- 2021-06-30T17:07:58.383Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649891
;

-- 2021-06-30T17:07:58.418Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649891)
;

-- 2021-06-30T17:07:58.880Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574878,649892,0,544118,TO_TIMESTAMP('2021-06-30 20:07:58','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Kompensationsgruppe Schema',TO_TIMESTAMP('2021-06-30 20:07:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T17:07:58.911Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649892 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-30T17:07:58.942Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543889) 
;

-- 2021-06-30T17:07:59.003Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649892
;

-- 2021-06-30T17:07:59.034Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649892)
;

-- 2021-06-30T17:07:59.525Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574879,649893,0,544118,TO_TIMESTAMP('2021-06-30 20:07:59','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','MKTG_Campaign',TO_TIMESTAMP('2021-06-30 20:07:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T17:07:59.558Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649893 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-30T17:07:59.592Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544034) 
;

-- 2021-06-30T17:07:59.628Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649893
;

-- 2021-06-30T17:07:59.659Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649893)
;

-- 2021-06-30T17:08:54.757Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649889,0,544118,546163,586916,'F',TO_TIMESTAMP('2021-06-30 20:08:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Firmenname',240,0,0,TO_TIMESTAMP('2021-06-30 20:08:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T17:09:22.922Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-06-30 20:09:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586916
;

-- 2021-06-30T17:09:23.049Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-06-30 20:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586888
;

-- 2021-06-30T17:09:23.193Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-06-30 20:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586889
;

-- 2021-06-30T17:09:23.319Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-06-30 20:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586890
;

-- 2021-06-30T17:09:23.450Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-06-30 20:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586891
;

-- 2021-06-30T17:09:23.584Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-06-30 20:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586893
;

-- 2021-06-30T17:09:23.707Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-06-30 20:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586892
;

-- 2021-06-30T17:09:23.834Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2021-06-30 20:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586894
;

-- 2021-06-30T17:09:23.967Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2021-06-30 20:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586895
;

-- 2021-06-30T17:09:24.095Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2021-06-30 20:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586896
;

-- 2021-06-30T17:09:24.224Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2021-06-30 20:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586897
;

-- 2021-06-30T17:09:24.346Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2021-06-30 20:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586898
;

-- 2021-06-30T17:09:24.470Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2021-06-30 20:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586899
;

-- 2021-06-30T17:09:24.595Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2021-06-30 20:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586900
;

-- 2021-06-30T17:09:24.722Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2021-06-30 20:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586901
;

-- 2021-06-30T17:09:24.867Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2021-06-30 20:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586902
;

-- 2021-06-30T17:09:25.024Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2021-06-30 20:09:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586903
;

-- 2021-06-30T17:09:25.152Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2021-06-30 20:09:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586904
;

-- 2021-06-30T17:09:25.280Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2021-06-30 20:09:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586905
;

-- 2021-06-30T17:24:35.656Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574880,541447,0,30,541731,'C_Flatrate_Term_ID',TO_TIMESTAMP('2021-06-30 20:24:34','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Pauschale - Vertragsperiode',0,0,TO_TIMESTAMP('2021-06-30 20:24:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-30T17:24:35.688Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574880 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-30T17:24:35.756Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(541447) 
;

-- 2021-06-30T17:24:41.989Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Export','ALTER TABLE public.C_BPartner_Export ADD COLUMN C_Flatrate_Term_ID NUMERIC(10)')
;

-- 2021-06-30T17:24:42.062Z
-- URL zum Konzept
ALTER TABLE C_BPartner_Export ADD CONSTRAINT CFlatrateTerm_CBPartnerExport FOREIGN KEY (C_Flatrate_Term_ID) REFERENCES public.C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED
;

-- 2021-06-30T17:58:00.255Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574881,187,0,19,541731,'C_BPartner_ID',TO_TIMESTAMP('2021-06-30 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','D',0,10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartner',0,0,TO_TIMESTAMP('2021-06-30 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-30T17:58:00.288Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574881 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-30T17:58:00.355Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- 2021-06-30T17:58:12.922Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2021-06-30 20:58:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574881
;

-- 2021-06-30T17:58:19.793Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Export','ALTER TABLE public.C_BPartner_Export ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2021-06-30T17:58:19.832Z
-- URL zum Konzept
ALTER TABLE C_BPartner_Export ADD CONSTRAINT CBPartner_CBPartnerExport FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2021-06-30T17:58:42.632Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574882,558,0,30,541731,'C_Order_ID',TO_TIMESTAMP('2021-06-30 20:58:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftrag','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftrag',0,0,TO_TIMESTAMP('2021-06-30 20:58:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-30T17:58:42.663Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574882 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-30T17:58:42.750Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(558) 
;

-- 2021-06-30T17:58:48.764Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Export','ALTER TABLE public.C_BPartner_Export ADD COLUMN C_Order_ID NUMERIC(10)')
;

-- 2021-06-30T17:58:48.801Z
-- URL zum Konzept
ALTER TABLE C_BPartner_Export ADD CONSTRAINT COrder_CBPartnerExport FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- 2021-06-30T18:02:38.913Z
-- URL zum Konzept
UPDATE AD_Column SET IsFacetFilter='N',Updated=TO_TIMESTAMP('2021-06-30 21:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574824
;

-- 2021-06-30T18:07:29.473Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574880,649894,0,544118,TO_TIMESTAMP('2021-06-30 21:07:29','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Pauschale - Vertragsperiode',TO_TIMESTAMP('2021-06-30 21:07:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T18:07:29.511Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649894 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-30T18:07:29.543Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541447) 
;

-- 2021-06-30T18:07:29.589Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649894
;

-- 2021-06-30T18:07:29.612Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649894)
;

-- 2021-06-30T18:07:30.097Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574881,649895,0,544118,TO_TIMESTAMP('2021-06-30 21:07:29','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2021-06-30 21:07:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T18:07:30.128Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649895 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-30T18:07:30.160Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2021-06-30T18:07:30.254Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649895
;

-- 2021-06-30T18:07:30.285Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649895)
;

-- 2021-06-30T18:07:30.747Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574882,649896,0,544118,TO_TIMESTAMP('2021-06-30 21:07:30','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2021-06-30 21:07:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T18:07:30.778Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649896 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-30T18:07:30.815Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2021-06-30T18:07:30.899Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649896
;

-- 2021-06-30T18:07:30.946Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649896)
;

-- 2021-06-30T18:08:31.273Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544079,546166,TO_TIMESTAMP('2021-06-30 21:08:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2021-06-30 21:08:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T18:08:40.524Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544079,546167,TO_TIMESTAMP('2021-06-30 21:08:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','details',20,TO_TIMESTAMP('2021-06-30 21:08:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T18:08:46.805Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544079,546168,TO_TIMESTAMP('2021-06-30 21:08:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2021-06-30 21:08:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T18:09:28.380Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=546168, SeqNo=10,Updated=TO_TIMESTAMP('2021-06-30 21:09:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586885
;

-- 2021-06-30T18:09:50.245Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649880,0,544118,546168,586917,'F',TO_TIMESTAMP('2021-06-30 21:09:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2021-06-30 21:09:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T18:10:35.953Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2021-06-30 21:10:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586888
;

-- 2021-06-30T18:11:00.137Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-06-30 21:11:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586916
;

-- 2021-06-30T18:11:11.892Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2021-06-30 21:11:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586889
;

-- 2021-06-30T18:11:30.529Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2021-06-30 21:11:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586886
;

-- 2021-06-30T18:12:34.317Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649881,0,544118,546166,586918,'F',TO_TIMESTAMP('2021-06-30 21:12:33','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2021-06-30 21:12:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T18:12:58.024Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649891,0,544118,546166,586919,'F',TO_TIMESTAMP('2021-06-30 21:12:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Abweichende Rechnungsaddresse',20,0,0,TO_TIMESTAMP('2021-06-30 21:12:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T18:13:27.632Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=546167, SeqNo=10,Updated=TO_TIMESTAMP('2021-06-30 21:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586903
;

-- 2021-06-30T18:13:40.653Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=546167, SeqNo=20,Updated=TO_TIMESTAMP('2021-06-30 21:13:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586904
;

-- 2021-06-30T18:16:00.402Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649890,0,544118,546163,586920,'F',TO_TIMESTAMP('2021-06-30 21:15:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vertrags-Status',240,0,0,TO_TIMESTAMP('2021-06-30 21:15:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T18:16:22.903Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=546167, SeqNo=30,Updated=TO_TIMESTAMP('2021-06-30 21:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586920
;

-- 2021-06-30T18:19:49.748Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540761,Updated=TO_TIMESTAMP('2021-06-30 21:19:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574801
;

-- 2021-06-30T18:19:56.315Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_bpartner_export','TerminationReason','VARCHAR(3)',null,null)
;

-- 2021-06-30T18:20:27.135Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=546167, SeqNo=40,Updated=TO_TIMESTAMP('2021-06-30 21:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586905
;

-- 2021-06-30T18:20:45.906Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=546167, SeqNo=50,Updated=TO_TIMESTAMP('2021-06-30 21:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586886
;

-- 2021-06-30T18:21:09.502Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=546166, SeqNo=30,Updated=TO_TIMESTAMP('2021-06-30 21:21:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586908
;

-- 2021-06-30T18:21:53.194Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2021-06-30 21:21:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586887
;

-- 2021-06-30T18:21:56.530Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-06-30 21:21:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586888
;

-- 2021-06-30T18:21:59.862Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2021-06-30 21:21:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586916
;

-- 2021-06-30T18:22:05.926Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2021-06-30 21:22:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586889
;

-- 2021-06-30T18:22:12.564Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2021-06-30 21:22:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586890
;

-- 2021-06-30T18:22:18.154Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2021-06-30 21:22:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586891
;

-- 2021-06-30T18:22:23.742Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2021-06-30 21:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586892
;

-- 2021-06-30T18:22:27.814Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2021-06-30 21:22:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586893
;

-- 2021-06-30T18:22:31.782Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2021-06-30 21:22:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586894
;

-- 2021-06-30T18:22:40.751Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2021-06-30 21:22:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586895
;

-- 2021-06-30T18:22:44.155Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2021-06-30 21:22:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586896
;

-- 2021-06-30T18:22:47.697Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2021-06-30 21:22:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586897
;

-- 2021-06-30T18:22:51.880Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2021-06-30 21:22:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586898
;

-- 2021-06-30T18:23:20.783Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2021-06-30 21:23:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586899
;

-- 2021-06-30T18:23:26.270Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2021-06-30 21:23:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586907
;

-- 2021-06-30T18:23:30.080Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2021-06-30 21:23:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586899
;

-- 2021-06-30T18:24:54.037Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=95,Updated=TO_TIMESTAMP('2021-06-30 21:24:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586902
;

-- 2021-06-30T18:26:10.512Z
-- URL zum Konzept
UPDATE AD_Window SET WindowType='T',Updated=TO_TIMESTAMP('2021-06-30 21:26:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541174
;

-- 2021-06-30T18:27:57.334Z
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2021-06-30 21:27:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544118
;

-- 2021-06-30T18:43:13.392Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649896,0,544118,546167,586921,'F',TO_TIMESTAMP('2021-06-30 21:43:12','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','Y','N','N','N',0,'Auftrag',60,0,0,TO_TIMESTAMP('2021-06-30 21:43:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T18:43:40.397Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649894,0,544118,546167,586922,'F',TO_TIMESTAMP('2021-06-30 21:43:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Pauschale - Vertragsperiode',70,0,0,TO_TIMESTAMP('2021-06-30 21:43:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T18:45:17.497Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649895,0,544118,546167,586923,'F',TO_TIMESTAMP('2021-06-30 21:45:17','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',80,0,0,TO_TIMESTAMP('2021-06-30 21:45:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T18:45:45.491Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2021-06-30 21:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586923
;



















-- 2021-06-30T18:52:59.741Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:52:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649878
;

-- 2021-06-30T18:53:01.050Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649858
;

-- 2021-06-30T18:53:02.300Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649859
;

-- 2021-06-30T18:53:03.579Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649860
;

-- 2021-06-30T18:53:04.955Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649861
;

-- 2021-06-30T18:53:06.191Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649862
;

-- 2021-06-30T18:53:07.447Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649863
;

-- 2021-06-30T18:53:08.735Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649864
;

-- 2021-06-30T18:53:10.140Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649865
;

-- 2021-06-30T18:53:11.391Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649866
;

-- 2021-06-30T18:53:12.681Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649867
;

-- 2021-06-30T18:53:13.970Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649868
;

-- 2021-06-30T18:53:15.190Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649869
;

-- 2021-06-30T18:53:16.492Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649870
;

-- 2021-06-30T18:53:17.748Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649871
;

-- 2021-06-30T18:53:19.105Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649872
;

-- 2021-06-30T18:53:20.430Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649873
;

-- 2021-06-30T18:53:21.783Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649874
;

-- 2021-06-30T18:53:23.035Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649875
;

-- 2021-06-30T18:53:24.295Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649876
;

-- 2021-06-30T18:53:25.617Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649877
;

-- 2021-06-30T18:53:26.893Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649896
;

-- 2021-06-30T18:53:28.249Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649881
;

-- 2021-06-30T18:53:29.590Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649882
;

-- 2021-06-30T18:53:30.902Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649883
;

-- 2021-06-30T18:53:32.201Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649888
;

-- 2021-06-30T18:53:33.614Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649889
;

-- 2021-06-30T18:53:34.904Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649890
;

-- 2021-06-30T18:53:36.233Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649891
;

-- 2021-06-30T18:53:37.527Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649892
;

-- 2021-06-30T18:53:38.819Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649893
;

-- 2021-06-30T18:53:40.145Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649894
;

-- 2021-06-30T18:53:41.401Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649895
;

-- 2021-06-30T18:53:57.148Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-06-30 21:53:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649857
;

-- 2021-06-30T18:55:57.587Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2021-06-30 21:55:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586889
;

-- 2021-06-30T18:55:57.718Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-06-30 21:55:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586920
;

-- 2021-06-30T18:55:57.848Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-06-30 21:55:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586887
;

-- 2021-06-30T18:55:57.972Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-06-30 21:55:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586916
;

-- 2021-06-30T18:55:58.104Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-06-30 21:55:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586888
;

-- 2021-06-30T18:58:25.515Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2021-06-30 21:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574878
;

-- 2021-06-30T18:58:27.653Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2021-06-30 21:58:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574877
;

-- 2021-06-30T18:58:29.387Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2021-06-30 21:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574879
;

-- 2021-06-30T18:58:31.211Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2021-06-30 21:58:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574782
;

-- 2021-06-30T18:58:32.652Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2021-06-30 21:58:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574795
;

-- 2021-06-30T18:58:34.121Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2021-06-30 21:58:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574799
;

-- 2021-06-30T18:58:35.440Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2021-06-30 21:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574800
;

-- 2021-06-30T18:58:36.643Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2021-06-30 21:58:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574798
;

-- 2021-06-30T18:58:47.034Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-06-30 21:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574878
;

-- 2021-06-30T18:58:48.593Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-06-30 21:58:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574782
;

-- 2021-06-30T18:58:50.130Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-06-30 21:58:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574798
;

-- 2021-06-30T18:58:52.190Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-06-30 21:58:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574877
;

-- 2021-06-30T18:58:53.608Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-06-30 21:58:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574879
;

-- 2021-06-30T18:58:55.150Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-06-30 21:58:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574800
;

-- 2021-06-30T18:58:56.684Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-06-30 21:58:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574799
;

-- 2021-06-30T18:58:58.135Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-06-30 21:58:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574795
;

-- 2021-06-30T19:00:34.573Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2021-06-30 22:00:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574782
;

-- 2021-06-30T19:00:36.162Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2021-06-30 22:00:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574876
;

-- 2021-06-30T19:00:38.900Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2021-06-30 22:00:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574795
;

-- 2021-06-30T19:00:41.303Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2021-06-30 22:00:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574874
;

-- 2021-06-30T19:00:43.354Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2021-06-30 22:00:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574798
;

-- 2021-06-30T19:00:45.329Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2021-06-30 22:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574823
;

-- 2021-06-30T19:00:47.790Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2021-06-30 22:00:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574877
;

-- 2021-06-30T19:00:49.565Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2021-06-30 22:00:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574878
;

-- 2021-06-30T19:00:51.216Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2021-06-30 22:00:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574880
;

-- 2021-06-30T19:00:52.834Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2021-06-30 22:00:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574799
;

-- 2021-06-30T19:00:54.370Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=120,Updated=TO_TIMESTAMP('2021-06-30 22:00:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574800
;

-- 2021-06-30T19:00:55.812Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=130,Updated=TO_TIMESTAMP('2021-06-30 22:00:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574879
;

-- 2021-06-30T19:33:01.739Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649892,0,544118,546167,586924,'F',TO_TIMESTAMP('2021-06-30 22:33:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Kompensationsgruppe Schema',80,0,0,TO_TIMESTAMP('2021-06-30 22:33:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-30T19:36:13.505Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=572551, Description=NULL, Help=NULL, Name='Mail',Updated=TO_TIMESTAMP('2021-06-30 22:36:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649875
;

-- 2021-06-30T19:36:13.546Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(572551) 
;

-- 2021-06-30T19:36:13.589Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649875
;

-- 2021-06-30T19:36:13.624Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649875)
;



-- 2021-06-30T20:00:38.737Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=540000, FilterOperator='E',Updated=TO_TIMESTAMP('2021-06-30 23:00:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574876
;

-- 2021-06-30T22:08:01.366Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=327, FilterOperator='E',Updated=TO_TIMESTAMP('2021-07-01 01:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574874
;

-- 2021-06-30T22:08:06.037Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_bpartner_export','AD_Language','VARCHAR(10)',null,null)
;


