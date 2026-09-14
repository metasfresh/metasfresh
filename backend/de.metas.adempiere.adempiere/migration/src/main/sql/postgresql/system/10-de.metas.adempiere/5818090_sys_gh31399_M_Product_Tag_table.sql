-- Product ↔ Tag assignment table (M_Product_Tag)
-- Many-to-many link table between M_Product and M_Tag. Uniqueness enforced on
-- (AD_Client_ID, M_Product_ID, M_Tag_ID) so a product can be tagged with a
-- given tag at most once per client.
--
-- IDs allocated from idserver.metas.de:
--   AD_Table 542637
--   AD_Element: 585162 (M_Product_Tag_ID key column)
--   Reused existing AD_Elements: 102 AD_Client_ID, 113 AD_Org_ID, 245 Created, 246 CreatedBy,
--     348 IsActive, 454 M_Product_ID, 607 Updated, 608 UpdatedBy, 585159 M_Tag_ID (from 5818080)
--   AD_Columns 593121..593130 (593121 = M_Tag_ID FK, inserted last in file but allocated earlier from server block; std cols + PK + M_Product_ID = 593122..593130)

-- AD_Table -------------------------------------------------------------------
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('4',0,0,0,542637 /*From ID Server*/,'N',TO_TIMESTAMP('2026-08-10 15:25:00','YYYY-MM-DD HH24:MI:SS'),100,'Produkt-Label Zuordnung','D','N','Y','N','N','Y','N','N','N','N','N',0,'Product Tag Assignment','NP','L','M_Product_Tag',TO_TIMESTAMP('2026-08-10 15:25:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=542637 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- Key column element (M_Product_Tag_ID) ------------------------------------
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585162 /*From ID Server*/,0,'M_Product_Tag_ID',TO_TIMESTAMP('2026-08-10 15:25:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produkt-Label Zuordnung','Produkt-Label Zuordnung',TO_TIMESTAMP('2026-08-10 15:25:01','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585162 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Product Tag Assignment', PrintName='Product Tag Assignment',Updated=TO_TIMESTAMP('2026-08-10 15:25:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585162 AND AD_Language='en_US'
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produkt-Label Zuordnung', PrintName='Produkt-Label Zuordnung',Updated=TO_TIMESTAMP('2026-08-10 15:25:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585162 AND AD_Language='de_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585162,'en_US')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585162,'de_CH')
;

-- Standard columns -----------------------------------------------------------
-- AD_Client_ID
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593122 /*From ID Server*/,102,0,19,542637,'AD_Client_ID',TO_TIMESTAMP('2026-08-10 15:25:03','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person.','Y','Y','N','N','N','N','Y','N','N','Y','N','N','Mandant','NP',0,TO_TIMESTAMP('2026-08-10 15:25:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593122 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- AD_Org_ID
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593123 /*From ID Server*/,113,0,30,542637,'AD_Org_ID',TO_TIMESTAMP('2026-08-10 15:25:04','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten.','Y','Y','N','N','N','N','Y','N','Y','Y','N','N','Sektion','NP',0,TO_TIMESTAMP('2026-08-10 15:25:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593123 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Created
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593124 /*From ID Server*/,245,0,16,542637,'Created',TO_TIMESTAMP('2026-08-10 15:25:05','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt','NP',0,TO_TIMESTAMP('2026-08-10 15:25:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593124 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- CreatedBy
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593125 /*From ID Server*/,246,0,18,110,542637,'CreatedBy',TO_TIMESTAMP('2026-08-10 15:25:06','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch','NP',0,TO_TIMESTAMP('2026-08-10 15:25:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593125 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- IsActive
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593126 /*From ID Server*/,348,0,20,542637,'IsActive',TO_TIMESTAMP('2026-08-10 15:25:07','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren.','Y','Y','N','N','N','N','Y','N','N','Y','N','Y','Aktiv','NP',0,TO_TIMESTAMP('2026-08-10 15:25:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593126 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Updated
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593127 /*From ID Server*/,607,0,16,542637,'Updated',TO_TIMESTAMP('2026-08-10 15:25:08','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert','NP',0,TO_TIMESTAMP('2026-08-10 15:25:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593127 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- UpdatedBy
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593128 /*From ID Server*/,608,0,18,110,542637,'UpdatedBy',TO_TIMESTAMP('2026-08-10 15:25:09','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch','NP',0,TO_TIMESTAMP('2026-08-10 15:25:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593128 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Key column: M_Product_Tag_ID (ID, reference 13) --------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593129 /*From ID Server*/,585162,0,13,542637,'M_Product_Tag_ID',TO_TIMESTAMP('2026-08-10 15:25:10','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','N','N','N','N','Y','Y','N','N','Y','N','N','Produkt-Label Zuordnung','NP',0,TO_TIMESTAMP('2026-08-10 15:25:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593129 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Parent: M_Product_ID (TableDir 19, IsParent='Y') ; reuse element 454 -----
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593130 /*From ID Server*/,454,0,19,542637,'M_Product_ID',TO_TIMESTAMP('2026-08-10 15:25:11','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','Y','Y','N','Y','N','N','Produkt','NP',0,TO_TIMESTAMP('2026-08-10 15:25:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593130 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Value: M_Tag_ID (TableDir 19, IsSelectionColumn='Y') ; reuse element 585159 (from 5818080)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593121 /*From ID Server*/,585159,0,19,542637,'M_Tag_ID',TO_TIMESTAMP('2026-08-10 15:25:12','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','Y','N','Y','N','Y','Y','N','Y','Produkt-Label','NP',0,TO_TIMESTAMP('2026-08-10 15:25:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593121 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Physical table -------------------------------------------------------------
/* DDL */ CREATE TABLE public.M_Product_Tag (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_ID NUMERIC(10) NOT NULL, M_Product_Tag_ID NUMERIC(10) NOT NULL, M_Tag_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT M_Product_Tag_Key PRIMARY KEY (M_Product_Tag_ID), CONSTRAINT MProduct_MProductTag FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MTag_MProductTag FOREIGN KEY (M_Tag_ID) REFERENCES public.M_Tag DEFERRABLE INITIALLY DEFERRED)
;

-- Unique index: one row per (Product, Tag) per Client, active only ---------
/* DDL */ CREATE UNIQUE INDEX M_Product_Tag_uc ON public.M_Product_Tag (AD_Client_ID, M_Product_ID, M_Tag_ID) WHERE IsActive='Y'
;

