-- Product Tag registry (M_Tag)
-- General product-tag registry — a Name / unique Value / Description entity
-- that a product can be tagged with via the M_Product_Tag many-to-many table.
-- Complements the existing single-text M_Product.CustomerLabelName field.
--
-- IDs allocated from idserver.metas.de:
--   AD_Table 542636
--   AD_Elements: 585159 (M_Tag_ID key column). 585160 (originally for Value) skipped —
--     AD_Element.ColumnName has a unique constraint and element 620 already covers 'Value'
--     (Name='Suchschlüssel', en_US='Search Key'), so we reuse it instead.
--   Reused existing AD_Elements: 102 AD_Client_ID, 113 AD_Org_ID, 245 Created, 246 CreatedBy,
--     275 Description, 348 IsActive, 469 Name, 607 Updated, 608 UpdatedBy, 620 Value
--   AD_Columns 593110..593120

-- AD_Table -------------------------------------------------------------------
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('4',0,0,0,542636 /*From ID Server*/,'N',TO_TIMESTAMP('2026-08-10 15:20:00','YYYY-MM-DD HH24:MI:SS'),100,'Produkt-Label Registry','D','N','Y','Y','N','Y','N','N','N','N','N',0,'Product Tag','NP','L','M_Tag',TO_TIMESTAMP('2026-08-10 15:20:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=542636 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

INSERT INTO AD_Sequence (CurrentNext,IsAudited,IsActive,IsTableID,Created,CreatedBy,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,Updated,UpdatedBy,AD_Sequence_ID,AD_Client_ID,Name,AD_Org_ID,Description) VALUES (1000000,'N','Y','Y',TO_TIMESTAMP('2026-08-10 15:20:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000000,1,50000,TO_TIMESTAMP('2026-08-10 15:20:00','YYYY-MM-DD HH24:MI:SS'),100,581987,0,'M_Tag',0,'Table M_Tag')
;

-- Key column element (M_Tag_ID) --------------------------------------------
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585159 /*From ID Server*/,0,'M_Tag_ID',TO_TIMESTAMP('2026-08-10 15:20:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produkt-Label','Produkt-Label',TO_TIMESTAMP('2026-08-10 15:20:01','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585159 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
-- English via en_US override
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Product Tag', PrintName='Product Tag',Updated=TO_TIMESTAMP('2026-08-10 15:20:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585159 AND AD_Language='en_US'
;
-- Swiss German (de_CH) explicit override with Swiss conventions (already same as de_DE — no ß)
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produkt-Label', PrintName='Produkt-Label',Updated=TO_TIMESTAMP('2026-08-10 15:20:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585159 AND AD_Language='de_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585159,'en_US')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585159,'de_CH')
;

-- Value column reuses existing AD_Element 620 (ColumnName='Value', Name='Suchschlüssel', en='Search Key').
-- AD_Element.ColumnName has a unique constraint, so we MUST reuse.

-- Standard columns -----------------------------------------------------------
-- AD_Client_ID
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593110 /*From ID Server*/,102,0,19,542636,'AD_Client_ID',TO_TIMESTAMP('2026-08-10 15:20:05','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person.','Y','Y','N','N','N','N','Y','N','N','Y','N','N','Mandant','NP',0,TO_TIMESTAMP('2026-08-10 15:20:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593110 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- AD_Org_ID
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593111 /*From ID Server*/,113,0,30,542636,'AD_Org_ID',TO_TIMESTAMP('2026-08-10 15:20:06','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten.','Y','Y','N','N','N','N','Y','N','Y','Y','N','N','Sektion','NP',0,TO_TIMESTAMP('2026-08-10 15:20:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593111 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Created
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593112 /*From ID Server*/,245,0,16,542636,'Created',TO_TIMESTAMP('2026-08-10 15:20:07','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt','NP',0,TO_TIMESTAMP('2026-08-10 15:20:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593112 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- CreatedBy
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593113 /*From ID Server*/,246,0,18,110,542636,'CreatedBy',TO_TIMESTAMP('2026-08-10 15:20:08','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch','NP',0,TO_TIMESTAMP('2026-08-10 15:20:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593113 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- IsActive
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593114 /*From ID Server*/,348,0,20,542636,'IsActive',TO_TIMESTAMP('2026-08-10 15:20:09','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren.','Y','Y','N','N','N','N','Y','N','N','Y','N','Y','Aktiv','NP',0,TO_TIMESTAMP('2026-08-10 15:20:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593114 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Updated
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593115 /*From ID Server*/,607,0,16,542636,'Updated',TO_TIMESTAMP('2026-08-10 15:20:10','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert','NP',0,TO_TIMESTAMP('2026-08-10 15:20:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593115 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- UpdatedBy
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593116 /*From ID Server*/,608,0,18,110,542636,'UpdatedBy',TO_TIMESTAMP('2026-08-10 15:20:11','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch','NP',0,TO_TIMESTAMP('2026-08-10 15:20:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593116 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Key column: M_Tag_ID (ID, reference 13) ---------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593117 /*From ID Server*/,585159,0,13,542636,'M_Tag_ID',TO_TIMESTAMP('2026-08-10 15:20:12','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','N','N','N','N','Y','Y','N','N','Y','N','N','Produkt-Label','NP',0,TO_TIMESTAMP('2026-08-10 15:20:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593117 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Business column: Name (String, reference 10) ; reuse element 469 --------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593118 /*From ID Server*/,469,0,10,542636,'Name',TO_TIMESTAMP('2026-08-10 15:20:13','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumerischer Identifikator dieses Eintrags','D',60,'Der Name des Eintrags wird zusätzlich zur Ordnungszahl bzw. dem Suchschlüssel verwendet.','Y','Y','N','N','Y','N','Y','N','Y','Y','N','Y','Name','NP',0,TO_TIMESTAMP('2026-08-10 15:20:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593118 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Business column: Value (String, reference 10, unique per client) — reuses existing AD_Element 620
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593119 /*From ID Server*/,620,0,10,542636,'Value',TO_TIMESTAMP('2026-08-10 15:20:14','YYYY-MM-DD HH24:MI:SS'),100,'D',60,'Y','Y','N','N','Y','N','Y','N','Y','Y','N','Y','Suchschlüssel','NP',0,TO_TIMESTAMP('2026-08-10 15:20:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593119 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Business column: Description (String, reference 14 text) ; reuse element 275
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593120 /*From ID Server*/,275,0,14,542636,'Description',TO_TIMESTAMP('2026-08-10 15:20:15','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche kurze Beschreibung','D',255,'Eine Beschreibung ist auf 255 Zeichen begrenzt.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','Beschreibung','NP',0,TO_TIMESTAMP('2026-08-10 15:20:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=593120 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Physical table -------------------------------------------------------------
/* DDL */ CREATE TABLE public.M_Tag (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Tag_ID NUMERIC(10) NOT NULL, Name VARCHAR(60) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, Value VARCHAR(60) NOT NULL, CONSTRAINT M_Tag_Key PRIMARY KEY (M_Tag_ID))
;

-- Unique index on Value per client, active rows only -------------------------
/* DDL */ CREATE UNIQUE INDEX M_Tag_Value_uc ON public.M_Tag (AD_Client_ID, LOWER(Value)) WHERE IsActive='Y'
;
