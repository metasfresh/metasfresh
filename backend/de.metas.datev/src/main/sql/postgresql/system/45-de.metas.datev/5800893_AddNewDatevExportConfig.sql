
----------------------------------
----------------------------------
----------------------------------
----------------------------------



-- Table: DATEV_Export_Config
-- 2026-05-05T15:41:53.444Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WhenChildCloningStrategy) VALUES ('3',0,0,0,542597,'A','N',TO_TIMESTAMP('2026-05-05 15:41:52.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','de.metas.datev','N','Y','N','Y','Y','N','N','N','N','N',0,'DATEV Export Config','NP','L','DATEV_Export_Config','DTI',TO_TIMESTAMP('2026-05-05 15:41:52.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A')
;

-- 2026-05-05T15:41:53.450Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542597 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2026-05-05T15:41:53.560Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556596,TO_TIMESTAMP('2026-05-05 15:41:53.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table DATEV_Export_Config',1,'Y','N','Y','Y','DATEV_Export_Config',1000000,TO_TIMESTAMP('2026-05-05 15:41:53.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:41:53.571Z
CREATE SEQUENCE DATEV_EXPORT_CONFIG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: DATEV_Export_Config.AD_Client_ID
-- 2026-05-05T15:42:01.696Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592480,102,0,19,542597,'AD_Client_ID',TO_TIMESTAMP('2026-05-05 15:42:01.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mandant für diese Installation.','de.metas.datev',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2026-05-05 15:42:01.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T15:42:01.702Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592480 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T15:42:01.706Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: DATEV_Export_Config.AD_Org_ID
-- 2026-05-05T15:42:02.549Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592481,113,0,30,542597,'AD_Org_ID',TO_TIMESTAMP('2026-05-05 15:42:02.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Organisatorische Einheit des Mandanten','de.metas.datev',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2026-05-05 15:42:02.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T15:42:02.553Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592481 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T15:42:02.555Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: DATEV_Export_Config.Created
-- 2026-05-05T15:42:02.981Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592482,245,0,16,542597,'Created',TO_TIMESTAMP('2026-05-05 15:42:02.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.datev',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2026-05-05 15:42:02.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T15:42:02.984Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592482 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T15:42:02.987Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: DATEV_Export_Config.CreatedBy
-- 2026-05-05T15:42:03.440Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592483,246,0,18,110,542597,'CreatedBy',TO_TIMESTAMP('2026-05-05 15:42:03.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.datev',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2026-05-05 15:42:03.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T15:42:03.442Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592483 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T15:42:03.446Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: DATEV_Export_Config.IsActive
-- 2026-05-05T15:42:03.860Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592484,348,0,20,542597,'IsActive',TO_TIMESTAMP('2026-05-05 15:42:03.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Der Eintrag ist im System aktiv','de.metas.datev',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2026-05-05 15:42:03.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T15:42:03.862Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592484 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T15:42:03.866Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: DATEV_Export_Config.Updated
-- 2026-05-05T15:42:04.280Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592485,607,0,16,542597,'Updated',TO_TIMESTAMP('2026-05-05 15:42:04.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.datev',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2026-05-05 15:42:04.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T15:42:04.282Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592485 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T15:42:04.284Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: DATEV_Export_Config.UpdatedBy
-- 2026-05-05T15:42:05.020Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592486,608,0,18,110,542597,'UpdatedBy',TO_TIMESTAMP('2026-05-05 15:42:04.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.datev',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2026-05-05 15:42:04.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T15:42:05.022Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592486 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T15:42:05.025Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2026-05-05T15:42:05.380Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584826,0,'DATEV_Export_Config_ID',TO_TIMESTAMP('2026-05-05 15:42:05.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.datev','Y','DATEV Export Config','DATEV Export Config',TO_TIMESTAMP('2026-05-05 15:42:05.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:42:05.385Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584826 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: DATEV_Export_Config.DATEV_Export_Config_ID
-- 2026-05-05T15:42:05.748Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592487,584826,0,13,542597,'DATEV_Export_Config_ID',TO_TIMESTAMP('2026-05-05 15:42:05.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.datev',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','DATEV Export Config',0,0,TO_TIMESTAMP('2026-05-05 15:42:05.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T15:42:05.753Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592487 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T15:42:05.757Z
/* DDL */  select update_Column_Translation_From_AD_Element(584826)
;

-- 2026-05-05T15:43:19.819Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584827,0,'AdvisorNumber',TO_TIMESTAMP('2026-05-05 15:43:19.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.datev','Y','Beraternummer','Beraternummer',TO_TIMESTAMP('2026-05-05 15:43:19.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:43:19.824Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584827 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: AdvisorNumber
-- 2026-05-05T15:43:42.849Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-05 15:43:42.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584827 AND AD_Language in ('de_CH', 'de_DE')
;

-- 2026-05-05T15:43:42.854Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584827,'de_CH')
;

-- 2026-05-05T15:43:42.854Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584827,'de_DE')
;
-- Element: AdvisorNumber
-- 2026-05-05T15:45:05.096Z
UPDATE AD_Element_Trl SET Description='Bereich 1001-9999999',Updated=TO_TIMESTAMP('2026-05-05 15:45:05.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584827 AND AD_Language='de_CH'
;

-- 2026-05-05T15:45:05.097Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-05T15:45:05.294Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584827,'de_CH')
;

-- Column: DATEV_Export_Config.AdvisorNumber
-- 2026-05-05T15:45:27.623Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592488,584827,0,22,542597,'XX','AdvisorNumber',TO_TIMESTAMP('2026-05-05 15:45:27.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.datev',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N',0,'Beraternummer',0,0,TO_TIMESTAMP('2026-05-05 15:45:27.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T15:45:27.628Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592488 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T15:45:27.633Z
/* DDL */  select update_Column_Translation_From_AD_Element(584827)
;

-- Column: DATEV_Export_Config.AdvisorNumber
-- 2026-05-05T15:46:00.242Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2026-05-05 15:46:00.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592488
;

-- 2026-05-05T15:46:27.470Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584828,0,'ClientNumber',TO_TIMESTAMP('2026-05-05 15:46:27.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bereich 1-99999','U','Y','Mandantennummer','Mandantennummer',TO_TIMESTAMP('2026-05-05 15:46:27.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:46:27.480Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584828 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-05-05T15:46:43.098Z
UPDATE AD_Element SET EntityType='de.metas.datev',Updated=TO_TIMESTAMP('2026-05-05 15:46:43.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584828
;

-- 2026-05-05T15:46:43.103Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584828,'de_DE')
;

-- Element: ClientNumber
-- 2026-05-05T15:46:47.291Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-05 15:46:47.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584828 AND AD_Language='de_CH'
;

-- 2026-05-05T15:46:47.293Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584828,'de_CH')
;

-- Element: ClientNumber
-- 2026-05-05T15:46:53.288Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='ClientNumber', PrintName='ClientNumber',Updated=TO_TIMESTAMP('2026-05-05 15:46:53.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584828 AND AD_Language='en_US'
;

-- 2026-05-05T15:46:53.289Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-05T15:46:53.472Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584828,'en_US')
;

-- Column: DATEV_Export_Config.ClientNumber
-- 2026-05-05T15:47:06.725Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592489,584828,0,10,542597,'XX','ClientNumber',TO_TIMESTAMP('2026-05-05 15:47:06.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Bereich 1-99999','de.metas.datev',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N',0,'Mandantennummer',0,0,TO_TIMESTAMP('2026-05-05 15:47:06.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T15:47:06.729Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592489 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T15:47:06.799Z
/* DDL */  select update_Column_Translation_From_AD_Element(584828)
;

-- 2026-05-05T15:50:13.562Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584829,0,'ChartOfAccounts',TO_TIMESTAMP('2026-05-05 15:50:13.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sachkontenrahmen, der für die Bewegungsdaten verwendet wurde','de.metas.datev','Y','Sachkontenrahmen','Sachkontenrahmen',TO_TIMESTAMP('2026-05-05 15:50:13.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:50:13.566Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584829 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: DATEV_Export_Config.ChartOfAccounts
-- 2026-05-05T15:50:38.182Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592490,584829,0,10,542597,'XX','ChartOfAccounts',TO_TIMESTAMP('2026-05-05 15:50:38.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Sachkontenrahmen, der für die Bewegungsdaten verwendet wurde','de.metas.datev',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sachkontenrahmen',0,0,TO_TIMESTAMP('2026-05-05 15:50:38.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T15:50:38.185Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592490 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T15:50:38.258Z
/* DDL */  select update_Column_Translation_From_AD_Element(584829)
;

-- Column: DATEV_Export_Config.ChartOfAccounts
-- 2026-05-05T15:50:40.953Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-05-05 15:50:40.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592490
;

-- 2026-05-05T15:53:06.401Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584830,0,'ChartOfAccountsNumberLength',TO_TIMESTAMP('2026-05-05 15:53:06.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.datev','Y','Sachkontenlänge','Sachkontenlänge',TO_TIMESTAMP('2026-05-05 15:53:06.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:53:06.407Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584830 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-05-05T15:53:19.012Z
UPDATE AD_Element SET Description='Nummernlänge der Sachkonten. Wert muss beim Import mit der Konfiguration des Mandats in der DATEV App übereinstimmen.',Updated=TO_TIMESTAMP('2026-05-05 15:53:19.010000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584830
;

-- 2026-05-05T15:53:19.013Z
UPDATE AD_Element_Trl trl SET Description='Nummernlänge der Sachkonten. Wert muss beim Import mit der Konfiguration des Mandats in der DATEV App übereinstimmen.' WHERE AD_Element_ID=584830 AND AD_Language='de_DE'
;

-- 2026-05-05T15:53:19.016Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584830,'de_DE')
;

-- Element: ChartOfAccountsNumberLength
-- 2026-05-05T15:53:21.873Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-05 15:53:21.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584830 AND AD_Language='de_CH'
;

-- 2026-05-05T15:53:21.878Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584830,'de_CH')
;

-- Column: DATEV_Export_Config.ChartOfAccountsNumberLength
-- 2026-05-05T15:53:56.402Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592491,584830,0,22,542597,'XX','ChartOfAccountsNumberLength',TO_TIMESTAMP('2026-05-05 15:53:56.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nummernlänge der Sachkonten. Wert muss beim Import mit der Konfiguration des Mandats in der DATEV App übereinstimmen.','de.metas.datev',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sachkontenlänge',0,0,TO_TIMESTAMP('2026-05-05 15:53:56.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T15:53:56.404Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592491 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T15:53:56.406Z
/* DDL */  select update_Column_Translation_From_AD_Element(584830)
;

-- Column: DATEV_Export_Config.ChartOfAccountsNumberLength
-- 2026-05-05T15:54:04.997Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-05-05 15:54:04.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592491
;

-- 2026-05-05T15:54:26.241Z
/* DDL */ CREATE TABLE public.DATEV_Export_Config (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AdvisorNumber VARCHAR(10) NOT NULL, ChartOfAccounts VARCHAR(10) NOT NULL, ChartOfAccountsNumberLength NUMERIC NOT NULL, ClientNumber VARCHAR(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DATEV_Export_Config_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT DATEV_Export_Config_Key PRIMARY KEY (DATEV_Export_Config_ID))
;

ALTER TABLE DATEV_Export_Config ADD CONSTRAINT DATEV_Config_OneDefault_UQ UNIQUE (AD_Org_ID);

CREATE INDEX DATEV_Config_Org_IDX ON DATEV_Export_Config (AD_Org_ID);

-- Element: DATEV_Export_Config_ID
-- 2026-05-05T15:57:21.275Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='DATEV-Konfiguration', PrintName='DATEV-Konfiguration',Updated=TO_TIMESTAMP('2026-05-05 15:57:21.275000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584826 AND AD_Language='de_CH'
;

-- 2026-05-05T15:57:21.276Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language in ('de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-05T15:57:21.468Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584826,'de_CH')
;

-- 2026-05-05T15:57:21.468Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584826,'de_DE')
;

-- Window: DATEV Export Config, InternalName=null
-- 2026-05-05T15:57:58.075Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584826,0,542142,TO_TIMESTAMP('2026-05-05 15:57:57.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.datev','Y','N','N','N','N','N','N','Y','DATEV Export Config','N',TO_TIMESTAMP('2026-05-05 15:57:57.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2026-05-05T15:57:58.078Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542142 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2026-05-05T15:57:58.083Z
/* DDL */  select update_window_translation_from_ad_element(584826)
;

-- 2026-05-05T15:57:58.091Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=542142
;

-- 2026-05-05T15:57:58.099Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(542142)
;

-- Tab: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config
-- Table: DATEV_Export_Config
-- 2026-05-05T15:58:15.031Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584826,0,549216,542597,542142,'Y',TO_TIMESTAMP('2026-05-05 15:58:14.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.datev','N','N','A','DATEV_Export_Config','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'DATEV Export Config','N',10,0,TO_TIMESTAMP('2026-05-05 15:58:14.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:58:15.036Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549216 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-05-05T15:58:15.040Z
/* DDL */  select update_tab_translation_from_ad_element(584826)
;

-- 2026-05-05T15:58:15.044Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549216)
;

-- Field: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> Mandant
-- Column: DATEV_Export_Config.AD_Client_ID
-- 2026-05-05T15:58:19.112Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592480,778048,0,549216,TO_TIMESTAMP('2026-05-05 15:58:18.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.datev','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2026-05-05 15:58:18.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:58:19.116Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=778048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-05T15:58:19.122Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-05-05T15:58:20.054Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=778048
;

-- 2026-05-05T15:58:20.056Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(778048)
;

-- Field: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> Sektion
-- Column: DATEV_Export_Config.AD_Org_ID
-- 2026-05-05T15:58:20.161Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592481,778049,0,549216,TO_TIMESTAMP('2026-05-05 15:58:20.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.datev','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2026-05-05 15:58:20.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:58:20.164Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=778049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-05T15:58:20.166Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-05-05T15:58:20.463Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=778049
;

-- 2026-05-05T15:58:20.465Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(778049)
;

-- Field: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> Aktiv
-- Column: DATEV_Export_Config.IsActive
-- 2026-05-05T15:58:20.563Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592484,778050,0,549216,TO_TIMESTAMP('2026-05-05 15:58:20.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.datev','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2026-05-05 15:58:20.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:58:20.569Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=778050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-05T15:58:20.572Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-05-05T15:58:20.872Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=778050
;

-- 2026-05-05T15:58:20.874Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(778050)
;

-- Field: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> DATEV Export Config
-- Column: DATEV_Export_Config.DATEV_Export_Config_ID
-- 2026-05-05T15:58:20.983Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592487,778051,0,549216,TO_TIMESTAMP('2026-05-05 15:58:20.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.datev','Y','N','N','N','N','N','N','N','DATEV Export Config',TO_TIMESTAMP('2026-05-05 15:58:20.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:58:20.986Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=778051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-05T15:58:20.988Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584826)
;

-- 2026-05-05T15:58:20.989Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=778051
;

-- 2026-05-05T15:58:20.990Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(778051)
;

-- Field: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> Beraternummer
-- Column: DATEV_Export_Config.AdvisorNumber
-- 2026-05-05T15:58:21.091Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592488,778052,0,549216,TO_TIMESTAMP('2026-05-05 15:58:20.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.datev','Y','N','N','N','N','N','N','N','Beraternummer',TO_TIMESTAMP('2026-05-05 15:58:20.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:58:21.094Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=778052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-05T15:58:21.095Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584827)
;

-- 2026-05-05T15:58:21.097Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=778052
;

-- 2026-05-05T15:58:21.098Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(778052)
;

-- Field: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> Mandantennummer
-- Column: DATEV_Export_Config.ClientNumber
-- 2026-05-05T15:58:21.212Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592489,778053,0,549216,TO_TIMESTAMP('2026-05-05 15:58:21.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bereich 1-99999',10,'de.metas.datev','Y','N','N','N','N','N','N','N','Mandantennummer',TO_TIMESTAMP('2026-05-05 15:58:21.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:58:21.215Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=778053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-05T15:58:21.217Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584828)
;

-- 2026-05-05T15:58:21.222Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=778053
;

-- 2026-05-05T15:58:21.223Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(778053)
;

-- Field: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> Sachkontenrahmen
-- Column: DATEV_Export_Config.ChartOfAccounts
-- 2026-05-05T15:58:21.335Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592490,778054,0,549216,TO_TIMESTAMP('2026-05-05 15:58:21.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sachkontenrahmen, der für die Bewegungsdaten verwendet wurde',10,'de.metas.datev','Y','N','N','N','N','N','N','N','Sachkontenrahmen',TO_TIMESTAMP('2026-05-05 15:58:21.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:58:21.340Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=778054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-05T15:58:21.342Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584829)
;

-- 2026-05-05T15:58:21.344Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=778054
;

-- 2026-05-05T15:58:21.345Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(778054)
;

-- Field: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> Sachkontenlänge
-- Column: DATEV_Export_Config.ChartOfAccountsNumberLength
-- 2026-05-05T15:58:21.439Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592491,778055,0,549216,TO_TIMESTAMP('2026-05-05 15:58:21.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nummernlänge der Sachkonten. Wert muss beim Import mit der Konfiguration des Mandats in der DATEV App übereinstimmen.',1,'de.metas.datev','Y','N','N','N','N','N','N','N','Sachkontenlänge',TO_TIMESTAMP('2026-05-05 15:58:21.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T15:58:21.442Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=778055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-05T15:58:21.444Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584830)
;

-- 2026-05-05T15:58:21.446Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=778055
;

-- 2026-05-05T15:58:21.447Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(778055)
;

-- Tab: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev)
-- UI Section: main
-- 2026-05-05T15:58:33.131Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,549216,547731,TO_TIMESTAMP('2026-05-05 15:58:32.980000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2026-05-05 15:58:32.980000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-05-05T15:58:33.135Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547731 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> main
-- UI Column: 10
-- 2026-05-05T16:32:12.386Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549438,547731,TO_TIMESTAMP('2026-05-05 16:32:12.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-05-05 16:32:12.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> main -> 10
-- UI Element Group: main
-- 2026-05-05T16:32:27.264Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549438,555228,TO_TIMESTAMP('2026-05-05 16:32:27.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2026-05-05 16:32:27.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> main -> 10 -> main.Beraternummer
-- Column: DATEV_Export_Config.AdvisorNumber
-- 2026-05-05T16:32:46.298Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,778052,0,549216,555228,650494,'F',TO_TIMESTAMP('2026-05-05 16:32:46.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Beraternummer',10,0,0,TO_TIMESTAMP('2026-05-05 16:32:46.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> main -> 10 -> main.Mandantennummer
-- Column: DATEV_Export_Config.ClientNumber
-- 2026-05-05T16:32:53.482Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,778053,0,549216,555228,650495,'F',TO_TIMESTAMP('2026-05-05 16:32:53.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bereich 1-99999','Y','N','Y','N','N','Mandantennummer',20,0,0,TO_TIMESTAMP('2026-05-05 16:32:53.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> main -> 10 -> main.Sachkontenrahmen
-- Column: DATEV_Export_Config.ChartOfAccounts
-- 2026-05-05T16:33:04.895Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,778054,0,549216,555228,650496,'F',TO_TIMESTAMP('2026-05-05 16:33:04.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sachkontenrahmen, der für die Bewegungsdaten verwendet wurde','Y','N','Y','N','N','Sachkontenrahmen',30,0,0,TO_TIMESTAMP('2026-05-05 16:33:04.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> main -> 10 -> main.Sachkontenlänge
-- Column: DATEV_Export_Config.ChartOfAccountsNumberLength
-- 2026-05-05T16:33:10.442Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,778055,0,549216,555228,650497,'F',TO_TIMESTAMP('2026-05-05 16:33:10.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nummernlänge der Sachkonten. Wert muss beim Import mit der Konfiguration des Mandats in der DATEV App übereinstimmen.','Y','N','Y','N','N','Sachkontenlänge',40,0,0,TO_TIMESTAMP('2026-05-05 16:33:10.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> main
-- UI Column: 20
-- 2026-05-05T16:33:16.400Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549439,547731,TO_TIMESTAMP('2026-05-05 16:33:16.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-05-05 16:33:16.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> main -> 20
-- UI Element Group: all
-- 2026-05-05T16:33:22.282Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549439,555229,TO_TIMESTAMP('2026-05-05 16:33:22.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','all',10,TO_TIMESTAMP('2026-05-05 16:33:22.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> main -> 20 -> all.Aktiv
-- Column: DATEV_Export_Config.IsActive
-- 2026-05-05T16:33:29.570Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,778050,0,549216,555229,650498,'F',TO_TIMESTAMP('2026-05-05 16:33:29.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2026-05-05 16:33:29.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: DATEV Export Config(542142,de.metas.datev) -> DATEV Export Config(549216,de.metas.datev) -> main -> 20 -> all.Sektion
-- Column: DATEV_Export_Config.AD_Org_ID
-- 2026-05-05T16:33:34.487Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,778049,0,549216,555229,650499,'F',TO_TIMESTAMP('2026-05-05 16:33:34.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',20,0,0,TO_TIMESTAMP('2026-05-05 16:33:34.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: DATEV Export Config
-- Action Type: null
-- 2026-05-05T16:34:16.278Z
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,584826,542318,0,TO_TIMESTAMP('2026-05-05 16:34:16.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.datev','DATEV Export Config','Y','N','N','N','N','DATEV Export Config',TO_TIMESTAMP('2026-05-05 16:34:16.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T16:34:16.282Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542318 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2026-05-05T16:34:16.285Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542318, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542318)
;

-- 2026-05-05T16:34:16.294Z
/* DDL */  select update_menu_translation_from_ad_element(584826)
;

-- Reordering children of `Requisition-to-Invoice`
-- Node name: `RfQ Topic (C_RfQ_Topic)`
-- 2026-05-05T16:34:16.880Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=452 AND AD_Tree_ID=10
;

-- Node name: `RfQ (C_RfQ)`
-- 2026-05-05T16:34:16.884Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=454 AND AD_Tree_ID=10
;

-- Node name: `RfQ Response`
-- 2026-05-05T16:34:16.885Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=466 AND AD_Tree_ID=10
;

-- Node name: `Ausschreibung-Antwort Position (C_RfQResponseLine)`
-- 2026-05-05T16:34:16.886Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540716 AND AD_Tree_ID=10
;

-- Node name: `RfQ Unanswered`
-- 2026-05-05T16:34:16.887Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=468 AND AD_Tree_ID=10
;

-- Node name: `RfQ Response`
-- 2026-05-05T16:34:16.887Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=467 AND AD_Tree_ID=10
;

-- Node name: `Requisition (M_Requisition)`
-- 2026-05-05T16:34:16.888Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=463 AND AD_Tree_ID=10
;

-- Node name: `Create PO from Requisition (org.compiere.process.RequisitionPOCreate)`
-- 2026-05-05T16:34:16.889Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=549 AND AD_Tree_ID=10
;

-- Node name: `Open Requisitions`
-- 2026-05-05T16:34:16.890Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=471 AND AD_Tree_ID=10
;

-- Node name: `Purchase Order (C_Order)`
-- 2026-05-05T16:34:16.890Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=205 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt (M_InOut)`
-- 2026-05-05T16:34:16.891Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=204 AND AD_Tree_ID=10
;

-- Node name: `Expense Invoice (Alpha) (C_Invoice)`
-- 2026-05-05T16:34:16.892Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=360 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt Details`
-- 2026-05-05T16:34:16.893Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=493 AND AD_Tree_ID=10
;

-- Node name: `Invoice (Vendor) (C_Invoice)`
-- 2026-05-05T16:34:16.894Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=206 AND AD_Tree_ID=10
;

-- Node name: `Invoice Batch (C_Invoice)`
-- 2026-05-05T16:34:16.895Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=516 AND AD_Tree_ID=10
;

-- Node name: `Wareneingangsdisposition (M_ReceiptSchedule)`
-- 2026-05-05T16:34:16.896Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540495 AND AD_Tree_ID=10
;

-- Node name: `Procurement`
-- 2026-05-05T16:34:16.897Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540688 AND AD_Tree_ID=10
;

-- Node name: `DATEV Export Format (DATEV_ExportFormat)`
-- 2026-05-05T16:34:16.898Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541039 AND AD_Tree_ID=10
;

-- Node name: `DATEV Export Config`
-- 2026-05-05T16:34:16.898Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542318 AND AD_Tree_ID=10
;

-- Reordering children of `Finance`
-- Node name: `Payments`
-- 2026-05-05T16:34:45.021Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542310 AND AD_Tree_ID=10
;

-- Node name: `Lexware`
-- 2026-05-05T16:34:45.023Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542265 AND AD_Tree_ID=10
;

-- Node name: `Kontenauszug (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-05-05T16:34:45.024Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542131 AND AD_Tree_ID=10
;

-- Node name: `DATEV Export Config`
-- 2026-05-05T16:34:45.025Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542318 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2026-05-05T16:34:45.026Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2026-05-05T16:34:45.028Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP) (SAP_GLJournal)`
-- 2026-05-05T16:34:45.029Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `GL Distribution (GL_Distribution)`
-- 2026-05-05T16:34:45.030Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2026-05-05T16:34:45.031Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2026-05-05T16:34:45.032Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2026-05-05T16:34:45.033Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2026-05-05T16:34:45.034Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2026-05-05T16:34:45.034Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2026-05-05T16:34:45.035Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2026-05-05T16:34:45.036Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2026-05-05T16:34:45.037Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Cost Element (M_CostElement)`
-- 2026-05-05T16:34:45.037Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542067 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2026-05-05T16:34:45.038Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-05-05T16:34:45.039Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-05-05T16:34:45.041Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2026-05-05T16:34:45.042Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2026-05-05T16:34:45.044Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Type (C_Cost_Type)`
-- 2026-05-05T16:34:45.045Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation (M_CostRevaluation)`
-- 2026-05-05T16:34:45.046Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2026-05-05T16:34:45.047Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2026-05-05T16:34:45.049Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2026-05-05T16:34:45.050Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2026-05-05T16:34:45.051Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2026-05-05T16:34:45.052Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2026-05-05T16:34:45.053Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2026-05-05T16:34:45.054Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2026-05-05T16:34:45.055Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2026-05-05T16:34:45.056Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2026-05-05T16:34:45.057Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2026-05-05T16:34:45.058Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides (C_Invoice_Acct)`
-- 2026-05-05T16:34:45.059Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Document Accounting Log (Document_Acct_Log)`
-- 2026-05-05T16:34:45.060Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542238 AND AD_Tree_ID=10
;

-- Node name: `INTRASTAT RTIC File (AT) (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-05-05T16:34:45.060Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542261 AND AD_Tree_ID=10
;

-- Node name: `Kostenartengruppe Übersetzung (C_CostClassification_Category_Trl)`
-- 2026-05-05T16:34:45.061Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542285 AND AD_Tree_ID=10
;

-- Node name: `Cost Classification Category (C_CostClassification_Category)`
-- 2026-05-05T16:34:45.062Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542284 AND AD_Tree_ID=10
;

-- Node name: `Cost Classification (C_CostClassification)`
-- 2026-05-05T16:34:45.063Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542287 AND AD_Tree_ID=10
;

-- Node name: `Kostenklassifizierung Übersetzung (C_CostClassification_Trl)`
-- 2026-05-05T16:34:45.064Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542288 AND AD_Tree_ID=10
;

-- Name: DATEV Export Config
-- Action Type: W
-- Window: DATEV Export Config(542142,de.metas.datev)
-- 2026-05-05T16:34:58.220Z
UPDATE AD_Menu SET Action='W', AD_Window_ID=542142,Updated=TO_TIMESTAMP('2026-05-05 16:34:58.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=542318
;

-- Reordering children of `Finance`
-- Node name: `Payments`
-- 2026-05-05T16:35:02.756Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542310 AND AD_Tree_ID=10
;

-- Node name: `Lexware`
-- 2026-05-05T16:35:02.758Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542265 AND AD_Tree_ID=10
;

-- Node name: `Kontenauszug (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-05-05T16:35:02.759Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542131 AND AD_Tree_ID=10
;

-- Node name: `DATEV Export Config`
-- 2026-05-05T16:35:02.762Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542318 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2026-05-05T16:35:02.764Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2026-05-05T16:35:02.766Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP) (SAP_GLJournal)`
-- 2026-05-05T16:35:02.768Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `GL Distribution (GL_Distribution)`
-- 2026-05-05T16:35:02.770Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2026-05-05T16:35:02.772Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2026-05-05T16:35:02.774Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2026-05-05T16:35:02.776Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2026-05-05T16:35:02.777Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2026-05-05T16:35:02.779Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2026-05-05T16:35:02.781Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2026-05-05T16:35:02.783Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2026-05-05T16:35:02.784Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Cost Element (M_CostElement)`
-- 2026-05-05T16:35:02.786Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542067 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2026-05-05T16:35:02.787Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-05-05T16:35:02.790Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-05-05T16:35:02.792Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2026-05-05T16:35:02.795Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2026-05-05T16:35:02.797Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Type (C_Cost_Type)`
-- 2026-05-05T16:35:02.799Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation (M_CostRevaluation)`
-- 2026-05-05T16:35:02.800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2026-05-05T16:35:02.802Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2026-05-05T16:35:02.804Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2026-05-05T16:35:02.805Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2026-05-05T16:35:02.807Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2026-05-05T16:35:02.809Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2026-05-05T16:35:02.812Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2026-05-05T16:35:02.813Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2026-05-05T16:35:02.815Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2026-05-05T16:35:02.817Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2026-05-05T16:35:02.819Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2026-05-05T16:35:02.821Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides (C_Invoice_Acct)`
-- 2026-05-05T16:35:02.823Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Document Accounting Log (Document_Acct_Log)`
-- 2026-05-05T16:35:02.825Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542238 AND AD_Tree_ID=10
;

-- Node name: `INTRASTAT RTIC File (AT) (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-05-05T16:35:02.827Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542261 AND AD_Tree_ID=10
;

-- Node name: `Kostenartengruppe Übersetzung (C_CostClassification_Category_Trl)`
-- 2026-05-05T16:35:02.828Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542285 AND AD_Tree_ID=10
;

-- Node name: `Cost Classification Category (C_CostClassification_Category)`
-- 2026-05-05T16:35:02.830Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542284 AND AD_Tree_ID=10
;

-- Node name: `Cost Classification (C_CostClassification)`
-- 2026-05-05T16:35:02.832Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542287 AND AD_Tree_ID=10
;

-- Node name: `Kostenklassifizierung Übersetzung (C_CostClassification_Trl)`
-- 2026-05-05T16:35:02.833Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542288 AND AD_Tree_ID=10
;

-- Column: DATEV_Export_Config.AdvisorNumber
-- 2026-05-05T16:36:44.443Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-05-05 16:36:44.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592488
;

-- Column: DATEV_Export_Config.ChartOfAccounts
-- 2026-05-05T16:36:50.473Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-05-05 16:36:50.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592490
;

-- Column: DATEV_Export_Config.AdvisorNumber
-- 2026-05-05T16:36:57.917Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2026-05-05 16:36:57.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592488
;

-- Column: DATEV_Export_Config.ClientNumber
-- 2026-05-05T16:37:06.072Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=2,Updated=TO_TIMESTAMP('2026-05-05 16:37:06.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592489
;

-- Column: DATEV_Export.DATEV_Export_Config_ID
-- 2026-05-05T16:37:33.662Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592492,584826,0,19,540934,'XX','DATEV_Export_Config_ID',TO_TIMESTAMP('2026-05-05 16:37:33.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.datev',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'DATEV Export Config',0,0,TO_TIMESTAMP('2026-05-05 16:37:33.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T16:37:33.667Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592492 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T16:37:33.674Z
/* DDL */  select update_Column_Translation_From_AD_Element(584826)
;

-- 2026-05-05T16:37:36.948Z
/* DDL */ SELECT public.db_alter_table('DATEV_Export','ALTER TABLE public.DATEV_Export ADD COLUMN DATEV_Export_Config_ID NUMERIC(10)')
;

-- 2026-05-05T16:37:36.964Z
ALTER TABLE DATEV_Export ADD CONSTRAINT DATEVExportConfig_DATEVExport FOREIGN KEY (DATEV_Export_Config_ID) REFERENCES public.DATEV_Export_Config DEFERRABLE INITIALLY DEFERRED
;

-- Column: DATEV_Export.AdvisorNumber
-- 2026-05-05T16:38:27.450Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592493,584827,0,10,540934,'XX','AdvisorNumber',TO_TIMESTAMP('2026-05-05 16:38:27.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.datev',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Beraternummer',0,0,TO_TIMESTAMP('2026-05-05 16:38:27.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T16:38:27.452Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592493 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T16:38:27.529Z
/* DDL */  select update_Column_Translation_From_AD_Element(584827)
;

-- Column: DATEV_Export.ChartOfAccounts
-- 2026-05-05T16:38:38.736Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592494,584829,0,10,540934,'XX','ChartOfAccounts',TO_TIMESTAMP('2026-05-05 16:38:38.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Sachkontenrahmen, der für die Bewegungsdaten verwendet wurde','de.metas.datev',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Sachkontenrahmen',0,0,TO_TIMESTAMP('2026-05-05 16:38:38.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T16:38:38.738Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592494 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T16:38:38.809Z
/* DDL */  select update_Column_Translation_From_AD_Element(584829)
;

-- 2026-05-05T16:38:39.920Z
/* DDL */ SELECT public.db_alter_table('DATEV_Export','ALTER TABLE public.DATEV_Export ADD COLUMN ChartOfAccounts VARCHAR(10)')
;

-- Column: DATEV_Export.ChartOfAccountsNumberLength
-- 2026-05-05T16:39:08.364Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592495,584830,0,22,540934,'XX','ChartOfAccountsNumberLength',TO_TIMESTAMP('2026-05-05 16:39:08.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nummernlänge der Sachkonten. Wert muss beim Import mit der Konfiguration des Mandats in der DATEV App übereinstimmen.','de.metas.datev',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sachkontenlänge',0,0,TO_TIMESTAMP('2026-05-05 16:39:08.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T16:39:08.366Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592495 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T16:39:08.433Z
/* DDL */  select update_Column_Translation_From_AD_Element(584830)
;

-- 2026-05-05T16:39:09.319Z
/* DDL */ SELECT public.db_alter_table('DATEV_Export','ALTER TABLE public.DATEV_Export ADD COLUMN ChartOfAccountsNumberLength NUMERIC')
;

-- Column: DATEV_Export.ClientNumber
-- 2026-05-05T16:39:29.285Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592496,584828,0,10,540934,'XX','ClientNumber',TO_TIMESTAMP('2026-05-05 16:39:29.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Bereich 1-99999','de.metas.datev',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Mandantennummer',0,0,TO_TIMESTAMP('2026-05-05 16:39:29.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-05T16:39:29.285Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592496 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-05T16:39:29.365Z
/* DDL */  select update_Column_Translation_From_AD_Element(584828)
;

-- Column: DATEV_Export.ClientNumber
-- 2026-05-05T16:39:31.566Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2026-05-05 16:39:31.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592496
;

-- Field: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> DATEV Export Config
-- Column: DATEV_Export.DATEV_Export_Config_ID
-- 2026-05-05T16:39:51.891Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592492,778056,0,541036,TO_TIMESTAMP('2026-05-05 16:39:51.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.datev','Y','N','N','N','N','N','N','N','DATEV Export Config',TO_TIMESTAMP('2026-05-05 16:39:51.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T16:39:51.895Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=778056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-05T16:39:51.898Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584826)
;

-- 2026-05-05T16:39:51.901Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=778056
;

-- 2026-05-05T16:39:51.904Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(778056)
;

-- Field: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Beraternummer
-- Column: DATEV_Export.AdvisorNumber
-- 2026-05-05T16:39:52.032Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592493,778057,0,541036,TO_TIMESTAMP('2026-05-05 16:39:51.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.datev','Y','N','N','N','N','N','N','N','Beraternummer',TO_TIMESTAMP('2026-05-05 16:39:51.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T16:39:52.034Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=778057 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-05T16:39:52.037Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584827)
;

-- 2026-05-05T16:39:52.039Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=778057
;

-- 2026-05-05T16:39:52.040Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(778057)
;

-- Field: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Sachkontenrahmen
-- Column: DATEV_Export.ChartOfAccounts
-- 2026-05-05T16:39:52.140Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592494,778058,0,541036,TO_TIMESTAMP('2026-05-05 16:39:52.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sachkontenrahmen, der für die Bewegungsdaten verwendet wurde',10,'de.metas.datev','Y','N','N','N','N','N','N','N','Sachkontenrahmen',TO_TIMESTAMP('2026-05-05 16:39:52.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T16:39:52.143Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=778058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-05T16:39:52.144Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584829)
;

-- 2026-05-05T16:39:52.146Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=778058
;

-- 2026-05-05T16:39:52.148Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(778058)
;

-- Field: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Sachkontenlänge
-- Column: DATEV_Export.ChartOfAccountsNumberLength
-- 2026-05-05T16:39:52.250Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592495,778059,0,541036,TO_TIMESTAMP('2026-05-05 16:39:52.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nummernlänge der Sachkonten. Wert muss beim Import mit der Konfiguration des Mandats in der DATEV App übereinstimmen.',14,'de.metas.datev','Y','N','N','N','N','N','N','N','Sachkontenlänge',TO_TIMESTAMP('2026-05-05 16:39:52.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T16:39:52.255Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=778059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-05T16:39:52.256Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584830)
;

-- 2026-05-05T16:39:52.259Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=778059
;

-- 2026-05-05T16:39:52.260Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(778059)
;

-- Field: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Mandantennummer
-- Column: DATEV_Export.ClientNumber
-- 2026-05-05T16:39:52.353Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592496,778060,0,541036,TO_TIMESTAMP('2026-05-05 16:39:52.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bereich 1-99999',10,'de.metas.datev','Y','N','N','N','N','N','N','N','Mandantennummer',TO_TIMESTAMP('2026-05-05 16:39:52.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-05T16:39:52.357Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=778060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-05T16:39:52.359Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584828)
;

-- 2026-05-05T16:39:52.362Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=778060
;

-- 2026-05-05T16:39:52.362Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(778060)
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 10 -> description.Beraternummer
-- Column: DATEV_Export.AdvisorNumber
-- 2026-05-05T16:40:11.081Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,778057,0,541036,541480,650500,'F',TO_TIMESTAMP('2026-05-05 16:40:10.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Beraternummer',20,0,0,TO_TIMESTAMP('2026-05-05 16:40:10.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 10 -> description.Mandantennummer
-- Column: DATEV_Export.ClientNumber
-- 2026-05-05T16:40:21.354Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,778060,0,541036,541480,650501,'F',TO_TIMESTAMP('2026-05-05 16:40:21.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bereich 1-99999','Y','N','N','Y','N','N','N',0,'Mandantennummer',30,0,0,TO_TIMESTAMP('2026-05-05 16:40:21.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 10 -> description.Sachkontenrahmen
-- Column: DATEV_Export.ChartOfAccounts
-- 2026-05-05T16:40:35.479Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,778058,0,541036,541480,650502,'F',TO_TIMESTAMP('2026-05-05 16:40:35.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sachkontenrahmen, der für die Bewegungsdaten verwendet wurde','Y','N','N','Y','N','N','N',0,'Sachkontenrahmen',40,0,0,TO_TIMESTAMP('2026-05-05 16:40:35.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 10 -> description.Sachkontenlänge
-- Column: DATEV_Export.ChartOfAccountsNumberLength
-- 2026-05-05T16:40:44.662Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,778059,0,541036,541480,650503,'F',TO_TIMESTAMP('2026-05-05 16:40:44.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nummernlänge der Sachkonten. Wert muss beim Import mit der Konfiguration des Mandats in der DATEV App übereinstimmen.','Y','N','N','Y','N','N','N',0,'Sachkontenlänge',50,0,0,TO_TIMESTAMP('2026-05-05 16:40:44.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;




-- Column: DATEV_Export_Config.ChartOfAccountsNumberLength
-- 2026-05-06T05:57:23.659Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0',Updated=TO_TIMESTAMP('2026-05-06 05:57:23.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592491
;

-- 2026-05-06T05:57:28.302Z
INSERT INTO t_alter_column values('datev_export_config','ChartOfAccountsNumberLength','NUMERIC(10)',null,'0')
;

-- 2026-05-06T05:57:28.342Z
UPDATE DATEV_Export_Config SET ChartOfAccountsNumberLength=0 WHERE ChartOfAccountsNumberLength IS NULL
;

-- Column: DATEV_Export.ChartOfAccountsNumberLength
-- 2026-05-06T05:58:08.908Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', FieldLength=1, IsMandatory='Y',Updated=TO_TIMESTAMP('2026-05-06 05:58:08.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592495
;

-- 2026-05-06T05:58:10.424Z
INSERT INTO t_alter_column values('datev_export','ChartOfAccountsNumberLength','NUMERIC(10)',null,'0')
;

-- 2026-05-06T05:58:10.441Z
UPDATE DATEV_Export SET ChartOfAccountsNumberLength=0 WHERE ChartOfAccountsNumberLength IS NULL
;

-- 2026-05-06T05:58:10.442Z
INSERT INTO t_alter_column values('datev_export','ChartOfAccountsNumberLength',null,'NOT NULL',null)
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 10 -> description.DATEV Export Config
-- Column: DATEV_Export.DATEV_Export_Config_ID
-- 2026-05-06T06:37:33.086Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,778056,0,541036,541480,650504,'F',TO_TIMESTAMP('2026-05-06 06:37:32.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'DATEV Export Config',60,0,0,TO_TIMESTAMP('2026-05-06 06:37:32.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 10 -> description.DATEV Export Config
-- Column: DATEV_Export.DATEV_Export_Config_ID
-- 2026-05-06T06:37:44.065Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2026-05-06 06:37:44.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=650504
;


-- 2026-05-06T06:33:02.012Z
/* DDL */ SELECT public.db_alter_table('DATEV_Export','ALTER TABLE public.DATEV_Export ADD COLUMN AdvisorNumber VARCHAR(10)')
;

-- 2026-05-06T06:34:48.144Z
/* DDL */ SELECT public.db_alter_table('DATEV_Export','ALTER TABLE public.DATEV_Export ADD COLUMN ClientNumber VARCHAR(10)')
;


-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 10 -> description.DATEV Export Config
-- Column: DATEV_Export.DATEV_Export_Config_ID
-- 2026-05-06T06:37:44.065Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2026-05-06 06:37:44.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=650504
;

-- Field: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Sachkontenrahmen
-- Column: DATEV_Export.ChartOfAccounts
-- 2026-05-06T07:37:04.187Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2026-05-06 07:37:04.187000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=778058
;

-- Field: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Sachkontenlänge
-- Column: DATEV_Export.ChartOfAccountsNumberLength
-- 2026-05-06T07:37:07.609Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2026-05-06 07:37:07.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=778059
;

-- Field: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Mandantennummer
-- Column: DATEV_Export.ClientNumber
-- 2026-05-06T07:37:10.998Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2026-05-06 07:37:10.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=778060
;

-- Field: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Beraternummer
-- Column: DATEV_Export.AdvisorNumber
-- 2026-05-06T07:37:19.572Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2026-05-06 07:37:19.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=778057
;

-- Field: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> DATEV Export Config
-- Column: DATEV_Export.DATEV_Export_Config_ID
-- 2026-05-06T07:37:37.948Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2026-05-06 07:37:37.948000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=778056
;

