-- New child table MobileUI_MFG_Config_Attribute (parent + M_Attribute_ID + SeqNo + IsActive).
-- Presence of an active row = editable attribute at the mobile manufacturing receive dialog.
-- Modeled on the existing HU-Manager display-config child table MobileUI_HUManager_Attribute.
-- AD_Tab/AD_Field/AD_UI_Element for the admin child tab are a separate follow-up (out of scope here).
--
-- IDs allocated from idserver.metas.de on 2026-09-01:
--   AD_Table   542643 (MobileUI_MFG_Config_Attribute)
--   AD_Element 585399 (MobileUI_MFG_Config_Attribute_ID — new PK element)
--   AD_Column  593447 (AD_Client_ID), 593448 (AD_Org_ID), 593449 (Created), 593450 (CreatedBy),
--              593451 (IsActive), 593452 (Updated), 593453 (UpdatedBy),
--              593454 (MobileUI_MFG_Config_Attribute_ID), 593455 (MobileUI_MFG_Config_ID),
--              593456 (M_Attribute_ID), 593457 (SeqNo)
-- Reused existing AD_Elements: 102 (AD_Client_ID), 113 (AD_Org_ID), 245 (Created), 246 (CreatedBy),
--   348 (IsActive), 607 (Updated), 608 (UpdatedBy), 583019 (MobileUI_MFG_Config_ID — the parent's own
--   PK element), 2015 (M_Attribute_ID), 566 (SeqNo).
--
-- IDs allocated from idserver.metas.de on 2026-09-01 (unique-constraint fix, mirroring
-- MobileUI_HUManager_Attribute's precedent index at
-- backend/de.metas.handlingunits.base/src/main/sql/postgresql/system/70-de.metas.handlingunits/5728210_sys_gh18417_MobileUI_HU_Manager.sql:945-963):
--   AD_Index_Table  540873 (IDX_unique_MobileUI_MFG_Config_Attribute)
--   AD_Index_Column 541544 (MobileUI_MFG_Config_ID, SeqNo 10), 541545 (M_Attribute_ID, SeqNo 20)

-- ============================================================================
-- Table: MobileUI_MFG_Config_Attribute
-- ============================================================================

-- 2026-09-01 10:00:00
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('2',0,0,0,542643 /*From ID Server*/,'A','N',TO_TIMESTAMP('2026-09-01 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'A','D','N','Y','N','N','Y','N','N','N','N','N',0,'Merkmale','NP','L','MobileUI_MFG_Config_Attribute','DTI',TO_TIMESTAMP('2026-09-01 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2026-09-01 10:00:01 (seed _Trl for all system/base languages)
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542643 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2026-09-01 10:00:02 (en_US override)
UPDATE AD_Table_Trl SET Name='Attributes', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Table_ID=542643 AND AD_Language='en_US'
;

-- ============================================================================
-- Standard audit columns (reuse existing shared AD_Elements)
-- ============================================================================

-- Column: MobileUI_MFG_Config_Attribute.AD_Client_ID
-- 2026-09-01 10:01:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593447 /*From ID Server*/,102,0,19,542643,'AD_Client_ID',TO_TIMESTAMP('2026-09-01 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant','NP',0,0,TO_TIMESTAMP('2026-09-01 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593447 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: MobileUI_MFG_Config_Attribute.AD_Org_ID
-- 2026-09-01 10:01:10
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593448 /*From ID Server*/,113,0,30,542643,'AD_Org_ID',TO_TIMESTAMP('2026-09-01 10:01:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion','NP',10,0,TO_TIMESTAMP('2026-09-01 10:01:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593448 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: MobileUI_MFG_Config_Attribute.Created
-- 2026-09-01 10:01:20
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593449 /*From ID Server*/,245,0,16,542643,'Created',TO_TIMESTAMP('2026-09-01 10:01:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt','NP',0,0,TO_TIMESTAMP('2026-09-01 10:01:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593449 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: MobileUI_MFG_Config_Attribute.CreatedBy
-- 2026-09-01 10:01:30
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593450 /*From ID Server*/,246,0,18,110,542643,'CreatedBy',TO_TIMESTAMP('2026-09-01 10:01:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch','NP',0,0,TO_TIMESTAMP('2026-09-01 10:01:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593450 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: MobileUI_MFG_Config_Attribute.IsActive
-- 2026-09-01 10:01:40
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593451 /*From ID Server*/,348,0,20,542643,'IsActive',TO_TIMESTAMP('2026-09-01 10:01:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv','NP',0,0,TO_TIMESTAMP('2026-09-01 10:01:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593451 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: MobileUI_MFG_Config_Attribute.Updated
-- 2026-09-01 10:01:50
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593452 /*From ID Server*/,607,0,16,542643,'Updated',TO_TIMESTAMP('2026-09-01 10:01:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert','NP',0,0,TO_TIMESTAMP('2026-09-01 10:01:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593452 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: MobileUI_MFG_Config_Attribute.UpdatedBy
-- 2026-09-01 10:02:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593453 /*From ID Server*/,608,0,18,110,542643,'UpdatedBy',TO_TIMESTAMP('2026-09-01 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch','NP',0,0,TO_TIMESTAMP('2026-09-01 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593453 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- ============================================================================
-- PK column: MobileUI_MFG_Config_Attribute_ID (new AD_Element)
-- ============================================================================

-- 2026-09-01 10:03:00
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585399 /*From ID Server*/,0,'MobileUI_MFG_Config_Attribute_ID',TO_TIMESTAMP('2026-09-01 10:03:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Merkmale','Merkmale',TO_TIMESTAMP('2026-09-01 10:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585399 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-09-01 10:03:12 (en_US override)
UPDATE AD_Element_Trl SET Name='Attributes', PrintName='Attributes', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 10:03:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585399 AND AD_Language='en_US'
;

-- Column: MobileUI_MFG_Config_Attribute.MobileUI_MFG_Config_Attribute_ID
-- 2026-09-01 10:04:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593454 /*From ID Server*/,585399,0,13,542643,'MobileUI_MFG_Config_Attribute_ID',TO_TIMESTAMP('2026-09-01 10:04:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Merkmale','NP',0,0,TO_TIMESTAMP('2026-09-01 10:04:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593454 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */  select update_Column_Translation_From_AD_Element(585399)
;

-- ============================================================================
-- Physical table (new table — direct CREATE TABLE, not t_alter_column)
-- ============================================================================

-- 2026-09-01 10:05:00
/* DDL */ CREATE TABLE public.MobileUI_MFG_Config_Attribute (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MobileUI_MFG_Config_Attribute_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MobileUI_MFG_Config_Attribute_Key PRIMARY KEY (MobileUI_MFG_Config_Attribute_ID))
;

-- Native PK sequence for this table. A direct CREATE TABLE does NOT create the <table>_seq
-- (the app server otherwise creates it lazily at startup, which masks the omission on dev DBs).
-- A data migration that allocates IDs via nextval('mobileui_mfg_config_attribute_seq') therefore
-- fails on a fresh DB applied before any app start (e.g. CI db-apply-migrations). Create it
-- explicitly, per the convention used by other new-table migrations.
CREATE SEQUENCE MobileUI_MFG_Config_Attribute_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2026-09-01 10:05:10
INSERT INTO t_alter_column values('mobileui_mfg_config_attribute','AD_Org_ID','NUMERIC(10)',null,null)
;
INSERT INTO t_alter_column values('mobileui_mfg_config_attribute','Created','TIMESTAMP WITH TIME ZONE',null,null)
;
INSERT INTO t_alter_column values('mobileui_mfg_config_attribute','CreatedBy','NUMERIC(10)',null,null)
;
INSERT INTO t_alter_column values('mobileui_mfg_config_attribute','IsActive','CHAR(1)',null,null)
;
INSERT INTO t_alter_column values('mobileui_mfg_config_attribute','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;
INSERT INTO t_alter_column values('mobileui_mfg_config_attribute','UpdatedBy','NUMERIC(10)',null,null)
;
INSERT INTO t_alter_column values('mobileui_mfg_config_attribute','MobileUI_MFG_Config_Attribute_ID','NUMERIC(10)',null,null)
;

-- ============================================================================
-- Column: MobileUI_MFG_Config_ID (parent FK — reuses the parent table's own PK element)
-- ============================================================================

-- 2026-09-01 10:06:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593455 /*From ID Server*/,583019,0,19,542643,'XX','MobileUI_MFG_Config_ID',TO_TIMESTAMP('2026-09-01 10:06:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'MobileUI Manufacturing Configuration','NP',0,0,TO_TIMESTAMP('2026-09-01 10:06:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593455 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */  select update_Column_Translation_From_AD_Element(583019)
;

-- 2026-09-01 10:06:20
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config_Attribute','ALTER TABLE public.MobileUI_MFG_Config_Attribute ADD COLUMN MobileUI_MFG_Config_ID NUMERIC(10) NOT NULL')
;

-- 2026-09-01 10:06:30
ALTER TABLE MobileUI_MFG_Config_Attribute ADD CONSTRAINT MobileUIMFGConfig_MobileUIMFGConfigAttr FOREIGN KEY (MobileUI_MFG_Config_ID) REFERENCES public.MobileUI_MFG_Config DEFERRABLE INITIALLY DEFERRED
;

-- ============================================================================
-- Column: M_Attribute_ID (reuses the existing shared attribute element)
-- ============================================================================

-- 2026-09-01 10:07:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593456 /*From ID Server*/,2015,0,30,542643,'XX','M_Attribute_ID',TO_TIMESTAMP('2026-09-01 10:07:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt-Merkmal','D',0,22,'Product Attribute like Color, Size','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Merkmal','NP',0,2,TO_TIMESTAMP('2026-09-01 10:07:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593456 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */  select update_Column_Translation_From_AD_Element(2015)
;

-- 2026-09-01 10:07:20
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config_Attribute','ALTER TABLE public.MobileUI_MFG_Config_Attribute ADD COLUMN M_Attribute_ID NUMERIC(10) NOT NULL')
;

-- 2026-09-01 10:07:30
ALTER TABLE MobileUI_MFG_Config_Attribute ADD CONSTRAINT MAttribute_MobileUIMFGConfigAttr FOREIGN KEY (M_Attribute_ID) REFERENCES public.M_Attribute DEFERRABLE INITIALLY DEFERRED
;

-- ============================================================================
-- Column: SeqNo (auto-increment default, scoped to the parent config row — mirrors the HU-Manager precedent)
-- ============================================================================

-- 2026-09-01 10:08:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593457 /*From ID Server*/,566,0,11,542643,'XX','SeqNo',TO_TIMESTAMP('2026-09-01 10:08:00','YYYY-MM-DD HH24:MI:SS'),100,'N','@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM MobileUI_MFG_Config_Attribute WHERE MobileUI_MFG_Config_ID=@MobileUI_MFG_Config_ID@','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D',0,22,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge','NP',0,0,TO_TIMESTAMP('2026-09-01 10:08:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=593457 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */  select update_Column_Translation_From_AD_Element(566)
;

-- 2026-09-01 10:08:20
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config_Attribute','ALTER TABLE public.MobileUI_MFG_Config_Attribute ADD COLUMN SeqNo NUMERIC(10) NOT NULL')
;

-- ============================================================================
-- Unique constraint (MobileUI_MFG_Config_ID, M_Attribute_ID) — one row per attribute per config.
-- Mirrors the MobileUI_HUManager_Attribute precedent (see header comment for the source lines).
-- ============================================================================

-- 2026-09-01 10:09:00
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540873 /*From ID Server*/,0,542643,TO_TIMESTAMP('2026-09-01 10:09:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','IDX_unique_MobileUI_MFG_Config_Attribute','N',TO_TIMESTAMP('2026-09-01 10:09:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-09-01 10:09:10
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540873 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2026-09-01 10:09:20
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,593455,541544 /*From ID Server*/,540873,0,TO_TIMESTAMP('2026-09-01 10:09:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2026-09-01 10:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-09-01 10:09:30
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,593456,541545 /*From ID Server*/,540873,0,TO_TIMESTAMP('2026-09-01 10:09:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2026-09-01 10:09:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-09-01 10:09:40
CREATE UNIQUE INDEX IDX_unique_MobileUI_MFG_Config_Attribute ON MobileUI_MFG_Config_Attribute (MobileUI_MFG_Config_ID, M_Attribute_ID)
;
