-- Run mode: WEBUI

-- 2025-10-16T15:01:06.789Z
INSERT INTO ExternalSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-10-16 15:01:06.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540058,'Y','Scripted Import Conversion',TO_TIMESTAMP('2025-10-16 15:01:06.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ScriptedImportConversion')
;

-- 2025-10-16T15:02:29.274Z
INSERT INTO ExternalSystem_Config (AD_Client_ID,AD_Org_ID,AuditFileFolder,Created,CreatedBy,ExternalSystem_Config_ID,ExternalSystem_ID,IsActive,Name,Updated,UpdatedBy,WriteAudit) VALUES (1000000,1000000,'/app/data/audit',TO_TIMESTAMP('2025-10-16 15:02:29.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540020,540058,'Y','Scripted Import Conversion',TO_TIMESTAMP('2025-10-16 15:02:29.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N')
;

-- 2025-10-16T15:02:35.557Z
UPDATE ExternalSystem_Config SET IsActive='N',Updated=TO_TIMESTAMP('2025-10-16 15:02:35.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE ExternalSystem_Config_ID=540020
;

-- Run mode: SWING_CLIENT

-- Table: ExternalSystem_Config_ScriptedImportConversion
-- 2025-10-16T16:52:09.185Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542546,'A','N',TO_TIMESTAMP('2025-10-16 16:52:08.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','de.metas.externalsystem','N','Y','N','Y','Y','N','N','N','N','N',0,'ExternalSystem_Config_ScriptedImportConversion','NP','L','ExternalSystem_Config_ScriptedImportConversion','DTI',TO_TIMESTAMP('2025-10-16 16:52:08.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'A')
;

-- 2025-10-16T16:52:09.201Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542546 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2025-10-16T16:52:09.324Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556553,TO_TIMESTAMP('2025-10-16 16:52:09.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table ExternalSystem_Config_ScriptedImportConversion',1,'Y','N','Y','Y','ExternalSystem_Config_ScriptedImportConversion',1000000,TO_TIMESTAMP('2025-10-16 16:52:09.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T16:52:09.340Z
CREATE SEQUENCE EXTERNALSYSTEM_CONFIG_SCRIPTEDIMPORTCONVERSION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_Client_ID
-- 2025-10-16T16:52:19.181Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591354,102,0,19,542546,'AD_Client_ID',TO_TIMESTAMP('2025-10-16 16:52:18.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mandant für diese Installation.','de.metas.externalsystem',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2025-10-16 16:52:18.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T16:52:19.187Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591354 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T16:52:19.238Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_Org_ID
-- 2025-10-16T16:52:21.072Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591355,113,0,30,542546,'AD_Org_ID',TO_TIMESTAMP('2025-10-16 16:52:20.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Organisatorische Einheit des Mandanten','de.metas.externalsystem',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2025-10-16 16:52:20.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T16:52:21.074Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591355 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T16:52:21.076Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.Created
-- 2025-10-16T16:52:22.501Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591356,245,0,16,542546,'Created',TO_TIMESTAMP('2025-10-16 16:52:22.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.externalsystem',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2025-10-16 16:52:22.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T16:52:22.503Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591356 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T16:52:22.505Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.CreatedBy
-- 2025-10-16T16:52:23.694Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591357,246,0,18,110,542546,'CreatedBy',TO_TIMESTAMP('2025-10-16 16:52:23.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.externalsystem',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2025-10-16 16:52:23.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T16:52:23.695Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591357 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T16:52:23.698Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.IsActive
-- 2025-10-16T16:52:24.935Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591358,348,0,20,542546,'IsActive',TO_TIMESTAMP('2025-10-16 16:52:24.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Der Eintrag ist im System aktiv','de.metas.externalsystem',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2025-10-16 16:52:24.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T16:52:24.936Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591358 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T16:52:24.939Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.Updated
-- 2025-10-16T16:52:26.214Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591359,607,0,16,542546,'Updated',TO_TIMESTAMP('2025-10-16 16:52:25.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.externalsystem',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2025-10-16 16:52:25.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T16:52:26.221Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591359 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T16:52:26.223Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.UpdatedBy
-- 2025-10-16T16:52:28.484Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591360,608,0,18,110,542546,'UpdatedBy',TO_TIMESTAMP('2025-10-16 16:52:28.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.externalsystem',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2025-10-16 16:52:28.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T16:52:28.486Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591360 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T16:52:28.488Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2025-10-16T16:52:29.515Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584117,0,'ExternalSystem_Config_ScriptedImportConversion_ID',TO_TIMESTAMP('2025-10-16 16:52:29.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','ExternalSystem_Config_ScriptedImportConversion','ExternalSystem_Config_ScriptedImportConversion',TO_TIMESTAMP('2025-10-16 16:52:29.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T16:52:29.520Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584117 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.ExternalSystem_Config_ScriptedImportConversion_ID
-- 2025-10-16T16:52:30.688Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591361,584117,0,13,542546,'ExternalSystem_Config_ScriptedImportConversion_ID',TO_TIMESTAMP('2025-10-16 16:52:29.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','ExternalSystem_Config_ScriptedImportConversion',0,0,TO_TIMESTAMP('2025-10-16 16:52:29.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T16:52:30.690Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591361 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T16:52:30.692Z
/* DDL */  select update_Column_Translation_From_AD_Element(584117)
;

-- 2025-10-16T16:52:31.598Z
/* DDL */ CREATE TABLE public.ExternalSystem_Config_ScriptedImportConversion (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExternalSystem_Config_ScriptedImportConversion_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ExternalSystem_Config_ScriptedImportConversion_Key PRIMARY KEY (ExternalSystem_Config_ScriptedImportConversion_ID))
;

-- 2025-10-16T16:52:31.671Z
INSERT INTO t_alter_column values('externalsystem_config_scriptedimportconversion','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2025-10-16T16:52:31.695Z
INSERT INTO t_alter_column values('externalsystem_config_scriptedimportconversion','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2025-10-16T16:52:31.708Z
INSERT INTO t_alter_column values('externalsystem_config_scriptedimportconversion','CreatedBy','NUMERIC(10)',null,null)
;

-- 2025-10-16T16:52:31.718Z
INSERT INTO t_alter_column values('externalsystem_config_scriptedimportconversion','IsActive','CHAR(1)',null,null)
;

-- 2025-10-16T16:52:31.834Z
INSERT INTO t_alter_column values('externalsystem_config_scriptedimportconversion','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2025-10-16T16:52:31.852Z
INSERT INTO t_alter_column values('externalsystem_config_scriptedimportconversion','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2025-10-16T16:52:31.868Z
INSERT INTO t_alter_column values('externalsystem_config_scriptedimportconversion','ExternalSystem_Config_ScriptedImportConversion_ID','NUMERIC(10)',null,null)
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.ExternalSystem_Config_ID
-- 2025-10-16T16:53:07.311Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591362,578728,0,19,542546,'XX','ExternalSystem_Config_ID',TO_TIMESTAMP('2025-10-16 16:53:07.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'External System Config',0,0,TO_TIMESTAMP('2025-10-16 16:53:07.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T16:53:07.317Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591362 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T16:53:07.321Z
/* DDL */  select update_Column_Translation_From_AD_Element(578728)
;

-- 2025-10-16T16:53:08.883Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ScriptedImportConversion','ALTER TABLE public.ExternalSystem_Config_ScriptedImportConversion ADD COLUMN ExternalSystem_Config_ID NUMERIC(10) NOT NULL')
;

-- 2025-10-16T16:53:08.904Z
ALTER TABLE ExternalSystem_Config_ScriptedImportConversion ADD CONSTRAINT ExternalSystemConfig_ExternalSystemConfigScriptedImportConversion FOREIGN KEY (ExternalSystem_Config_ID) REFERENCES public.ExternalSystem_Config DEFERRABLE INITIALLY DEFERRED
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.Description
-- 2025-10-16T16:53:49.391Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591363,275,0,10,542546,'XX','Description',TO_TIMESTAMP('2025-10-16 16:53:49.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2025-10-16 16:53:49.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T16:53:49.394Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591363 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T16:53:49.547Z
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- 2025-10-16T16:53:50.572Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ScriptedImportConversion','ALTER TABLE public.ExternalSystem_Config_ScriptedImportConversion ADD COLUMN Description VARCHAR(255)')
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.ScriptIdentifier
-- 2025-10-16T16:55:04.804Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591364,584103,0,10,542546,'XX','ScriptIdentifier',TO_TIMESTAMP('2025-10-16 16:55:04.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Name der JavaScript-Datei, die von externen Systemen ausgeführt werden soll','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Skript-Kennung',0,0,TO_TIMESTAMP('2025-10-16 16:55:04.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T16:55:04.804Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591364 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T16:55:04.998Z
/* DDL */  select update_Column_Translation_From_AD_Element(584103)
;

-- 2025-10-16T16:55:05.906Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ScriptedImportConversion','ALTER TABLE public.ExternalSystem_Config_ScriptedImportConversion ADD COLUMN ScriptIdentifier VARCHAR(255) NOT NULL')
;

-- 2025-10-16T16:56:32.066Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584118,0,'EndpointName',TO_TIMESTAMP('2025-10-16 16:56:31.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Endpunktname','Endpunktname',TO_TIMESTAMP('2025-10-16 16:56:31.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T16:56:32.072Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584118 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: EndpointName
-- 2025-10-16T16:56:42.539Z
UPDATE AD_Element_Trl SET Name='Endpoint Name', PrintName='Endpoint Name',Updated=TO_TIMESTAMP('2025-10-16 16:56:42.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584118 AND AD_Language='en_US'
;

-- 2025-10-16T16:56:42.540Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-16T16:56:43.310Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584118,'en_US')
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.EndpointName
-- 2025-10-16T16:57:10.683Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591365,584118,0,10,542546,'XX','EndpointName',TO_TIMESTAMP('2025-10-16 16:57:10.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Endpunktname',0,0,TO_TIMESTAMP('2025-10-16 16:57:10.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T16:57:10.685Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591365 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T16:57:10.688Z
/* DDL */  select update_Column_Translation_From_AD_Element(584118)
;

-- 2025-10-16T16:57:11.596Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ScriptedImportConversion','ALTER TABLE public.ExternalSystem_Config_ScriptedImportConversion ADD COLUMN EndpointName VARCHAR(255) NOT NULL')
;

-- 2025-10-16T16:57:54.666Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584119,0,'AD_User_Import_ID',TO_TIMESTAMP('2025-10-16 16:57:54.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Benutzerimport','Benutzerimport',TO_TIMESTAMP('2025-10-16 16:57:54.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T16:57:54.674Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584119 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: AD_User_Import_ID
-- 2025-10-16T16:58:04.739Z
UPDATE AD_Element_Trl SET Name='User Import', PrintName='User Import',Updated=TO_TIMESTAMP('2025-10-16 16:58:04.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584119 AND AD_Language='en_US'
;

-- 2025-10-16T16:58:04.739Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-16T16:58:05.356Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584119,'en_US')
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_User_Import_ID
-- 2025-10-16T17:01:24.329Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591366,584119,0,30,540401,542546,'XX','AD_User_Import_ID',TO_TIMESTAMP('2025-10-16 17:01:24.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Benutzerimport',0,0,TO_TIMESTAMP('2025-10-16 17:01:24.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T17:01:24.330Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591366 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T17:01:24.333Z
/* DDL */  select update_Column_Translation_From_AD_Element(584119)
;

-- 2025-10-16T17:01:25.653Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ScriptedImportConversion','ALTER TABLE public.ExternalSystem_Config_ScriptedImportConversion ADD COLUMN AD_User_Import_ID NUMERIC(10) NOT NULL')
;

-- 2025-10-16T17:01:25.664Z
ALTER TABLE ExternalSystem_Config_ScriptedImportConversion ADD CONSTRAINT ADUserImport_ExternalSystemConfigScriptedImportConversion FOREIGN KEY (AD_User_Import_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Run mode: SWING_CLIENT

-- Tab: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion
-- Table: ExternalSystem_Config_ScriptedImportConversion
-- 2025-10-16T17:03:06.917Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,591362,584117,0,548472,542546,541024,'Y',TO_TIMESTAMP('2025-10-16 17:03:06.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_ScriptedImportConversion','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'ExternalSystem_Config_ScriptedImportConversion','N',110,1,TO_TIMESTAMP('2025-10-16 17:03:06.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:03:06.926Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548472 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-10-16T17:03:06.930Z
/* DDL */  select update_tab_translation_from_ad_element(584117)
;

-- 2025-10-16T17:03:06.948Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548472)
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_Client_ID
-- 2025-10-16T17:03:19.625Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591354,754997,0,548472,TO_TIMESTAMP('2025-10-16 17:03:19.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-10-16 17:03:19.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:03:19.631Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754997 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:03:19.634Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-10-16T17:03:20.090Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754997
;

-- 2025-10-16T17:03:20.095Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754997)
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> Sektion
-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_Org_ID
-- 2025-10-16T17:03:20.213Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591355,754998,0,548472,TO_TIMESTAMP('2025-10-16 17:03:20.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-10-16 17:03:20.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:03:20.214Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754998 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:03:20.215Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-10-16T17:03:20.440Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754998
;

-- 2025-10-16T17:03:20.440Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754998)
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> Aktiv
-- Column: ExternalSystem_Config_ScriptedImportConversion.IsActive
-- 2025-10-16T17:03:20.556Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591358,754999,0,548472,TO_TIMESTAMP('2025-10-16 17:03:20.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-10-16 17:03:20.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:03:20.557Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754999 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:03:20.559Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-10-16T17:03:20.920Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754999
;

-- 2025-10-16T17:03:20.921Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754999)
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion
-- Column: ExternalSystem_Config_ScriptedImportConversion.ExternalSystem_Config_ScriptedImportConversion_ID
-- 2025-10-16T17:03:21.037Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591361,755000,0,548472,TO_TIMESTAMP('2025-10-16 17:03:20.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','ExternalSystem_Config_ScriptedImportConversion',TO_TIMESTAMP('2025-10-16 17:03:20.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:03:21.038Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755000 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:03:21.043Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584117)
;

-- 2025-10-16T17:03:21.046Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755000
;

-- 2025-10-16T17:03:21.046Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755000)
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> External System Config
-- Column: ExternalSystem_Config_ScriptedImportConversion.ExternalSystem_Config_ID
-- 2025-10-16T17:03:21.150Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591362,755001,0,548472,TO_TIMESTAMP('2025-10-16 17:03:21.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2025-10-16 17:03:21.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:03:21.151Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755001 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:03:21.152Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728)
;

-- 2025-10-16T17:03:21.163Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755001
;

-- 2025-10-16T17:03:21.164Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755001)
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> Beschreibung
-- Column: ExternalSystem_Config_ScriptedImportConversion.Description
-- 2025-10-16T17:03:21.272Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591363,755002,0,548472,TO_TIMESTAMP('2025-10-16 17:03:21.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2025-10-16 17:03:21.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:03:21.277Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755002 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:03:21.278Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2025-10-16T17:03:21.498Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755002
;

-- 2025-10-16T17:03:21.498Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755002)
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> Skript-Kennung
-- Column: ExternalSystem_Config_ScriptedImportConversion.ScriptIdentifier
-- 2025-10-16T17:03:21.621Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591364,755003,0,548472,TO_TIMESTAMP('2025-10-16 17:03:21.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Name der JavaScript-Datei, die von externen Systemen ausgeführt werden soll',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Skript-Kennung',TO_TIMESTAMP('2025-10-16 17:03:21.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:03:21.622Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755003 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:03:21.623Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584103)
;

-- 2025-10-16T17:03:21.630Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755003
;

-- 2025-10-16T17:03:21.630Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755003)
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> Endpunktname
-- Column: ExternalSystem_Config_ScriptedImportConversion.EndpointName
-- 2025-10-16T17:03:21.739Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591365,755004,0,548472,TO_TIMESTAMP('2025-10-16 17:03:21.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Endpunktname',TO_TIMESTAMP('2025-10-16 17:03:21.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:03:21.740Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755004 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:03:21.741Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584118)
;

-- 2025-10-16T17:03:21.744Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755004
;

-- 2025-10-16T17:03:21.744Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755004)
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> Benutzerimport
-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_User_Import_ID
-- 2025-10-16T17:03:21.856Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591366,755005,0,548472,TO_TIMESTAMP('2025-10-16 17:03:21.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Benutzerimport',TO_TIMESTAMP('2025-10-16 17:03:21.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:03:21.857Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755005 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:03:21.858Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584119)
;

-- 2025-10-16T17:03:21.862Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755005
;

-- 2025-10-16T17:03:21.862Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755005)
;

-- Tab: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem)
-- UI Section: main
-- 2025-10-16T17:03:35.609Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548472,546999,TO_TIMESTAMP('2025-10-16 17:03:35.451000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-16 17:03:35.451000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-10-16T17:03:35.614Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546999 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2025-10-16T17:03:40.758Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548531,546999,TO_TIMESTAMP('2025-10-16 17:03:40.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-16 17:03:40.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> main -> 10
-- UI Element Group: main
-- 2025-10-16T17:03:53.645Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548531,553640,TO_TIMESTAMP('2025-10-16 17:03:53.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,'primary',TO_TIMESTAMP('2025-10-16 17:03:53.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.ExternalSystemValue
-- 2025-10-16T17:04:55.565Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591367,578788,0,10,542546,'XX','ExternalSystemValue',TO_TIMESTAMP('2025-10-16 17:04:55.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Suchschlüssel','NP',0,10,TO_TIMESTAMP('2025-10-16 17:04:55.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T17:04:55.567Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591367 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T17:04:55.570Z
/* DDL */  select update_Column_Translation_From_AD_Element(578788)
;

-- 2025-10-16T17:04:56.871Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ScriptedImportConversion','ALTER TABLE public.ExternalSystem_Config_ScriptedImportConversion ADD COLUMN ExternalSystemValue VARCHAR(255) NOT NULL')
;

-- Column: ExternalSystem_Config_ScriptedImportConversion.ExternalSystem_Config_ScriptedImportConversion_ID
-- 2025-10-16T17:05:29.593Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-10-16 17:05:29.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591361
;

-- 2025-10-16T17:05:31.190Z
INSERT INTO t_alter_column values('externalsystem_config_scriptedimportconversion','ExternalSystem_Config_ScriptedImportConversion_ID','NUMERIC(10)',null,null)
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> Suchschlüssel
-- Column: ExternalSystem_Config_ScriptedImportConversion.ExternalSystemValue
-- 2025-10-16T17:06:18.191Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591367,755006,0,548472,TO_TIMESTAMP('2025-10-16 17:06:18.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2025-10-16 17:06:18.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:06:18.192Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755006 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:06:18.194Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578788)
;

-- 2025-10-16T17:06:18.198Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755006
;

-- 2025-10-16T17:06:18.198Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755006)
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> main -> 10 -> main.Suchschlüssel
-- Column: ExternalSystem_Config_ScriptedImportConversion.ExternalSystemValue
-- 2025-10-16T17:06:32.974Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755006,0,548472,637872,553640,'F',TO_TIMESTAMP('2025-10-16 17:06:32.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Suchschlüssel',10,0,0,TO_TIMESTAMP('2025-10-16 17:06:32.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> main -> 10 -> main.Endpunktname
-- Column: ExternalSystem_Config_ScriptedImportConversion.EndpointName
-- 2025-10-16T17:07:16.876Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755004,0,548472,637873,553640,'F',TO_TIMESTAMP('2025-10-16 17:07:16.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Endpunktname',20,0,0,TO_TIMESTAMP('2025-10-16 17:07:16.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> main -> 10 -> main.Skript-Kennung
-- Column: ExternalSystem_Config_ScriptedImportConversion.ScriptIdentifier
-- 2025-10-16T17:07:26.125Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755003,0,548472,637874,553640,'F',TO_TIMESTAMP('2025-10-16 17:07:25.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Name der JavaScript-Datei, die von externen Systemen ausgeführt werden soll','Y','N','N','Y','N','N','N',0,'Skript-Kennung',30,0,0,TO_TIMESTAMP('2025-10-16 17:07:25.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> main -> 10 -> main.Benutzerimport
-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_User_Import_ID
-- 2025-10-16T17:07:38.421Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755005,0,548472,637875,553640,'F',TO_TIMESTAMP('2025-10-16 17:07:38.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Benutzerimport',40,0,0,TO_TIMESTAMP('2025-10-16 17:07:38.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> main -> 10 -> main.Beschreibung
-- Column: ExternalSystem_Config_ScriptedImportConversion.Description
-- 2025-10-16T17:07:48.645Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755002,0,548472,637876,553640,'F',TO_TIMESTAMP('2025-10-16 17:07:48.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Beschreibung',50,0,0,TO_TIMESTAMP('2025-10-16 17:07:48.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> main
-- UI Column: 20
-- 2025-10-16T17:07:54.986Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548532,546999,TO_TIMESTAMP('2025-10-16 17:07:54.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-10-16 17:07:54.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> main -> 20
-- UI Element Group: flags
-- 2025-10-16T17:08:12.541Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548532,553641,TO_TIMESTAMP('2025-10-16 17:08:12.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-10-16 17:08:12.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> main -> 20 -> flags.Aktiv
-- Column: ExternalSystem_Config_ScriptedImportConversion.IsActive
-- 2025-10-16T17:08:21.585Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,754999,0,548472,637877,553641,'F',TO_TIMESTAMP('2025-10-16 17:08:21.446000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-10-16 17:08:21.446000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> main -> 20
-- UI Element Group: org
-- 2025-10-16T17:08:29.987Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548532,553642,TO_TIMESTAMP('2025-10-16 17:08:29.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-10-16 17:08:29.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> main -> 20 -> org.Mandant
-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_Client_ID
-- 2025-10-16T17:08:43.180Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,754997,0,548472,637878,553642,'F',TO_TIMESTAMP('2025-10-16 17:08:43.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2025-10-16 17:08:43.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548472,de.metas.externalsystem) -> main -> 20 -> org.Sektion
-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_Org_ID
-- 2025-10-16T17:08:51.458Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,754998,0,548472,637879,553642,'F',TO_TIMESTAMP('2025-10-16 17:08:51.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2025-10-16 17:08:51.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Run mode: SWING_CLIENT

-- 2025-10-16T17:17:58.591Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584120,0,TO_TIMESTAMP('2025-10-16 17:17:58.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','Externe System Konfig Skriptbasierte Importkonvertierung','Externe System Konfig Skriptbasierte Importkonvertierung',TO_TIMESTAMP('2025-10-16 17:17:58.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:17:58.598Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584120 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-10-16T17:18:46.900Z
UPDATE AD_Element_Trl SET Name='External system config Scripted Import Conversion', PrintName='External system config Scripted Import Conversion',Updated=TO_TIMESTAMP('2025-10-16 17:18:46.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584120 AND AD_Language='en_US'
;

-- 2025-10-16T17:18:46.901Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-16T17:18:48.099Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584120,'en_US')
;

-- Window: Externe System Konfig Skriptbasierte Importkonvertierung, InternalName=null
-- 2025-10-16T17:19:42.787Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584120,0,541962,TO_TIMESTAMP('2025-10-16 17:19:42.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','N','N','N','N','N','N','Y','Externe System Konfig Skriptbasierte Importkonvertierung','N',TO_TIMESTAMP('2025-10-16 17:19:42.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-10-16T17:19:42.793Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541962 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-10-16T17:19:42.797Z
/* DDL */  select update_window_translation_from_ad_element(584120)
;

-- 2025-10-16T17:19:42.800Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541962
;

-- 2025-10-16T17:19:42.800Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541962)
;

-- Tab: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion
-- Table: ExternalSystem_Config_ScriptedImportConversion
-- 2025-10-16T17:20:40.492Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584117,0,548473,542546,541962,'Y',TO_TIMESTAMP('2025-10-16 17:20:40.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_ScriptedImportConversion','Y','N','Y','Y','N','N','N','N','Y','N','N','N','Y','Y','N','N','N',0,'ExternalSystem_Config_ScriptedImportConversion','N',10,0,TO_TIMESTAMP('2025-10-16 17:20:40.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:20:40.498Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548473 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-10-16T17:20:40.500Z
/* DDL */  select update_tab_translation_from_ad_element(584117)
;

-- 2025-10-16T17:20:40.506Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548473)
;

-- Field: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_Client_ID
-- 2025-10-16T17:20:43.796Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591354,755007,0,548473,TO_TIMESTAMP('2025-10-16 17:20:43.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-10-16 17:20:43.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:20:43.798Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755007 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:20:43.801Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-10-16T17:20:43.923Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755007
;

-- 2025-10-16T17:20:43.927Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755007)
;

-- Field: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> Sektion
-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_Org_ID
-- 2025-10-16T17:20:44.039Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591355,755008,0,548473,TO_TIMESTAMP('2025-10-16 17:20:43.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-10-16 17:20:43.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:20:44.039Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755008 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:20:44.041Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-10-16T17:20:44.140Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755008
;

-- 2025-10-16T17:20:44.141Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755008)
;

-- Field: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> Aktiv
-- Column: ExternalSystem_Config_ScriptedImportConversion.IsActive
-- 2025-10-16T17:20:44.258Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591358,755009,0,548473,TO_TIMESTAMP('2025-10-16 17:20:44.143000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-10-16 17:20:44.143000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:20:44.259Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755009 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:20:44.261Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-10-16T17:20:44.367Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755009
;

-- 2025-10-16T17:20:44.368Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755009)
;

-- Field: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion
-- Column: ExternalSystem_Config_ScriptedImportConversion.ExternalSystem_Config_ScriptedImportConversion_ID
-- 2025-10-16T17:20:44.469Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591361,755010,0,548473,TO_TIMESTAMP('2025-10-16 17:20:44.370000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','ExternalSystem_Config_ScriptedImportConversion',TO_TIMESTAMP('2025-10-16 17:20:44.370000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:20:44.470Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755010 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:20:44.471Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584117)
;

-- 2025-10-16T17:20:44.474Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755010
;

-- 2025-10-16T17:20:44.474Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755010)
;

-- Field: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> External System Config
-- Column: ExternalSystem_Config_ScriptedImportConversion.ExternalSystem_Config_ID
-- 2025-10-16T17:20:44.582Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591362,755011,0,548473,TO_TIMESTAMP('2025-10-16 17:20:44.476000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2025-10-16 17:20:44.476000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:20:44.583Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755011 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:20:44.584Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728)
;

-- 2025-10-16T17:20:44.587Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755011
;

-- 2025-10-16T17:20:44.588Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755011)
;

-- Field: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> Beschreibung
-- Column: ExternalSystem_Config_ScriptedImportConversion.Description
-- 2025-10-16T17:20:44.710Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591363,755012,0,548473,TO_TIMESTAMP('2025-10-16 17:20:44.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2025-10-16 17:20:44.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:20:44.711Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755012 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:20:44.712Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2025-10-16T17:20:44.777Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755012
;

-- 2025-10-16T17:20:44.777Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755012)
;

-- Field: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> Skript-Kennung
-- Column: ExternalSystem_Config_ScriptedImportConversion.ScriptIdentifier
-- 2025-10-16T17:20:44.889Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591364,755013,0,548473,TO_TIMESTAMP('2025-10-16 17:20:44.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Name der JavaScript-Datei, die von externen Systemen ausgeführt werden soll',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Skript-Kennung',TO_TIMESTAMP('2025-10-16 17:20:44.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:20:44.890Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755013 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:20:44.891Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584103)
;

-- 2025-10-16T17:20:44.894Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755013
;

-- 2025-10-16T17:20:44.894Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755013)
;

-- Field: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> Endpunktname
-- Column: ExternalSystem_Config_ScriptedImportConversion.EndpointName
-- 2025-10-16T17:20:45.007Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591365,755014,0,548473,TO_TIMESTAMP('2025-10-16 17:20:44.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Endpunktname',TO_TIMESTAMP('2025-10-16 17:20:44.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:20:45.007Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755014 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:20:45.008Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584118)
;

-- 2025-10-16T17:20:45.011Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755014
;

-- 2025-10-16T17:20:45.011Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755014)
;

-- Field: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> Benutzerimport
-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_User_Import_ID
-- 2025-10-16T17:20:45.122Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591366,755015,0,548473,TO_TIMESTAMP('2025-10-16 17:20:45.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Benutzerimport',TO_TIMESTAMP('2025-10-16 17:20:45.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:20:45.123Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755015 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:20:45.124Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584119)
;

-- 2025-10-16T17:20:45.127Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755015
;

-- 2025-10-16T17:20:45.128Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755015)
;

-- Field: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> Suchschlüssel
-- Column: ExternalSystem_Config_ScriptedImportConversion.ExternalSystemValue
-- 2025-10-16T17:20:45.246Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591367,755016,0,548473,TO_TIMESTAMP('2025-10-16 17:20:45.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2025-10-16 17:20:45.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:20:45.247Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755016 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T17:20:45.248Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578788)
;

-- 2025-10-16T17:20:45.252Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755016
;

-- 2025-10-16T17:20:45.252Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755016)
;

-- Tab: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem)
-- UI Section: main
-- 2025-10-16T17:21:02.404Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548473,547000,TO_TIMESTAMP('2025-10-16 17:21:02.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-16 17:21:02.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-10-16T17:21:02.406Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547000 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2025-10-16T17:21:07.109Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548533,547000,TO_TIMESTAMP('2025-10-16 17:21:07.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-16 17:21:07.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main -> 10
-- UI Element Group: default
-- 2025-10-16T17:21:20.240Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548533,553643,TO_TIMESTAMP('2025-10-16 17:21:20.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2025-10-16 17:21:20.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main -> 10 -> default.External System Config
-- Column: ExternalSystem_Config_ScriptedImportConversion.ExternalSystem_Config_ID
-- 2025-10-16T17:21:40.512Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755011,0,548473,637880,553643,'F',TO_TIMESTAMP('2025-10-16 17:21:40.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'External System Config',10,0,0,TO_TIMESTAMP('2025-10-16 17:21:40.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion
-- Table: ExternalSystem_Config_ScriptedImportConversion
-- 2025-10-16T17:22:06.736Z
UPDATE AD_Tab SET IsInsertRecord='Y',Updated=TO_TIMESTAMP('2025-10-16 17:22:06.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548473
;

-- UI Element: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main -> 10 -> default.Suchschlüssel
-- Column: ExternalSystem_Config_ScriptedImportConversion.ExternalSystemValue
-- 2025-10-16T17:22:22.284Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755016,0,548473,637881,553643,'F',TO_TIMESTAMP('2025-10-16 17:22:22.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Suchschlüssel',20,0,0,TO_TIMESTAMP('2025-10-16 17:22:22.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main -> 10 -> default.Endpunktname
-- Column: ExternalSystem_Config_ScriptedImportConversion.EndpointName
-- 2025-10-16T17:22:39.158Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755014,0,548473,637882,553643,'F',TO_TIMESTAMP('2025-10-16 17:22:38.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Endpunktname',30,0,0,TO_TIMESTAMP('2025-10-16 17:22:38.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main -> 10 -> default.Skript-Kennung
-- Column: ExternalSystem_Config_ScriptedImportConversion.ScriptIdentifier
-- 2025-10-16T17:22:57.426Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755013,0,548473,637883,553643,'F',TO_TIMESTAMP('2025-10-16 17:22:57.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Name der JavaScript-Datei, die von externen Systemen ausgeführt werden soll','Y','N','N','Y','N','N','N',0,'Skript-Kennung',40,0,0,TO_TIMESTAMP('2025-10-16 17:22:57.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main -> 10 -> default.Benutzerimport
-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_User_Import_ID
-- 2025-10-16T17:23:03.666Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755015,0,548473,637884,553643,'F',TO_TIMESTAMP('2025-10-16 17:23:03.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Benutzerimport',50,0,0,TO_TIMESTAMP('2025-10-16 17:23:03.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main -> 10 -> default.Beschreibung
-- Column: ExternalSystem_Config_ScriptedImportConversion.Description
-- 2025-10-16T17:23:11.045Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755012,0,548473,637885,553643,'F',TO_TIMESTAMP('2025-10-16 17:23:10.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Beschreibung',60,0,0,TO_TIMESTAMP('2025-10-16 17:23:10.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main
-- UI Column: 20
-- 2025-10-16T17:23:26.418Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548534,547000,TO_TIMESTAMP('2025-10-16 17:23:26.277000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-10-16 17:23:26.277000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main -> 20
-- UI Element Group: flags
-- 2025-10-16T17:23:34.625Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548534,553644,TO_TIMESTAMP('2025-10-16 17:23:34.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-10-16 17:23:34.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main -> 20 -> flags.Aktiv
-- Column: ExternalSystem_Config_ScriptedImportConversion.IsActive
-- 2025-10-16T17:23:43.744Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755009,0,548473,637886,553644,'F',TO_TIMESTAMP('2025-10-16 17:23:43.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-10-16 17:23:43.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main -> 20
-- UI Element Group: org
-- 2025-10-16T17:23:53.679Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548534,553645,TO_TIMESTAMP('2025-10-16 17:23:53.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-10-16 17:23:53.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main -> 20 -> org.Mandant
-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_Client_ID
-- 2025-10-16T17:24:06.554Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755007,0,548473,637887,553645,'F',TO_TIMESTAMP('2025-10-16 17:24:06.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2025-10-16 17:24:06.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedImportConversion(548473,de.metas.externalsystem) -> main -> 20 -> org.Sektion
-- Column: ExternalSystem_Config_ScriptedImportConversion.AD_Org_ID
-- 2025-10-16T17:24:11.783Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755008,0,548473,637888,553645,'F',TO_TIMESTAMP('2025-10-16 17:24:11.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2025-10-16 17:24:11.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Run mode: SWING_CLIENT

-- Name: Externe System Konfig Skriptbasierte Importkonvertierung
-- Action Type: W
-- Window: Externe System Konfig Skriptbasierte Importkonvertierung(541962,de.metas.externalsystem)
-- 2025-10-16T17:28:04.848Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584120,542263,0,541962,TO_TIMESTAMP('2025-10-16 17:28:04.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','541962','Y','N','N','N','N','Externe System Konfig Skriptbasierte Importkonvertierung',TO_TIMESTAMP('2025-10-16 17:28:04.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T17:28:04.856Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542263 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-10-16T17:28:04.861Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542263, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542263)
;

-- 2025-10-16T17:28:04.883Z
/* DDL */  select update_menu_translation_from_ad_element(584120)
;

-- Reordering children of `System`
-- Node name: `Change Log Config (AD_ChangeLog_Config)`
-- 2025-10-16T17:28:05.579Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542199 AND AD_Tree_ID=10
;

-- Node name: `Warnings (AD_Record_Warning)`
-- 2025-10-16T17:28:05.581Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542223 AND AD_Tree_ID=10
;

-- Node name: `Externe Systeme authentifizieren (de.metas.externalsystem.externalservice.authorization.SendAuthTokenProcess)`
-- 2025-10-16T17:28:05.582Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541993 AND AD_Tree_ID=10
;

-- Node name: `External system config PCM (ExternalSystem_Config_ProCareManagement)`
-- 2025-10-16T17:28:05.582Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542142 AND AD_Tree_ID=10
;

-- Node name: `Costing (Freight etc)`
-- 2025-10-16T17:28:05.582Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542051 AND AD_Tree_ID=10
;

-- Node name: `External system config Leich + Mehl (ExternalSystem_Config_LeichMehl)`
-- 2025-10-16T17:28:05.583Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541966 AND AD_Tree_ID=10
;

-- Node name: `API Audit`
-- 2025-10-16T17:28:05.583Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2025-10-16T17:28:05.587Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `External System Configuration (ExternalSystem_Config)`
-- 2025-10-16T17:28:05.588Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2025-10-16T17:28:05.589Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `External system config Scripted Export Conversion (ExternalSystem_Config_ScriptedExportConversion)`
-- 2025-10-16T17:28:05.589Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542262 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2025-10-16T17:28:05.589Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2025-10-16T17:28:05.591Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2025-10-16T17:28:05.591Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2025-10-16T17:28:05.592Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2025-10-16T17:28:05.593Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchronous processing`
-- 2025-10-16T17:28:05.593Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2025-10-16T17:28:05.594Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Async workpackage queue (C_Queue_WorkPackage)`
-- 2025-10-16T17:28:05.594Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Scheduler (AD_Scheduler)`
-- 2025-10-16T17:28:05.595Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2025-10-16T17:28:05.596Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Role (AD_Role)`
-- 2025-10-16T17:28:05.596Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2025-10-16T17:28:05.597Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2025-10-16T17:28:05.597Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Counter Document (C_DocTypeCounter)`
-- 2025-10-16T17:28:05.597Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Users Group (AD_UserGroup)`
-- 2025-10-16T17:28:05.598Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2025-10-16T17:28:05.598Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Language (AD_Language)`
-- 2025-10-16T17:28:05.599Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menu (AD_Menu)`
-- 2025-10-16T17:28:05.599Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WEBUI_Dashboard)`
-- 2025-10-16T17:28:05.600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2025-10-16T17:28:05.600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Document Type (C_DocType)`
-- 2025-10-16T17:28:05.600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Boiler Plate (AD_BoilerPlate)`
-- 2025-10-16T17:28:05.601Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Boilerplate Translation (AD_BoilerPlate_Trl)`
-- 2025-10-16T17:28:05.602Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2025-10-16T17:28:05.602Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Text Variable (AD_BoilerPlate_Var)`
-- 2025-10-16T17:28:05.603Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Print Format (AD_PrintFormat)`
-- 2025-10-16T17:28:05.603Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `Zebra Configuration (AD_Zebra_Config)`
-- 2025-10-16T17:28:05.603Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Document Sequence (AD_Sequence)`
-- 2025-10-16T17:28:05.604Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `My Profile (AD_User)`
-- 2025-10-16T17:28:05.604Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Printing Queue (C_Printing_Queue)`
-- 2025-10-16T17:28:05.605Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2025-10-16T17:28:05.605Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2025-10-16T17:28:05.606Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Printer (AD_PrinterHW)`
-- 2025-10-16T17:28:05.606Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Printer Mapping (AD_Printer_Config)`
-- 2025-10-16T17:28:05.606Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Printer-Tray-Mapping (AD_Printer_Matching)`
-- 2025-10-16T17:28:05.607Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `RV Missing Counter Documents (RV_Missing_Counter_Documents)`
-- 2025-10-16T17:28:05.607Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System Configuration (AD_SysConfig)`
-- 2025-10-16T17:28:05.608Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2025-10-16T17:28:05.608Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Session Audit (AD_Session)`
-- 2025-10-16T17:28:05.608Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2025-10-16T17:28:05.609Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Change Log (AD_ChangeLog)`
-- 2025-10-16T17:28:05.610Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner (I_BPartner)`
-- 2025-10-16T17:28:05.610Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2025-10-16T17:28:05.611Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Product (I_Product)`
-- 2025-10-16T17:28:05.611Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2025-10-16T17:28:05.612Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventory (I_Inventory)`
-- 2025-10-16T17:28:05.612Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2025-10-16T17:28:05.612Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import Account (I_ElementValue)`
-- 2025-10-16T17:28:05.613Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import GL Journal (I_GLJournal)`
-- 2025-10-16T17:28:05.614Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542247 AND AD_Tree_ID=10
;

-- Node name: `Import Format (AD_ImpFormat)`
-- 2025-10-16T17:28:05.614Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Import Forecast (I_Forecast)`
-- 2025-10-16T17:28:05.614Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542243 AND AD_Tree_ID=10
;

-- Node name: `Data import (C_DataImport)`
-- 2025-10-16T17:28:05.615Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2025-10-16T17:28:05.615Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2025-10-16T17:28:05.615Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Processor (IMP_Processor)`
-- 2025-10-16T17:28:05.616Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Processor Log (IMP_ProcessorLog)`
-- 2025-10-16T17:28:05.616Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2025-10-16T17:28:05.616Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export Format (DATEV_ExportFormat)`
-- 2025-10-16T17:28:05.617Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2025-10-16T17:28:05.618Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Event Log (AD_EventLog)`
-- 2025-10-16T17:28:05.618Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2025-10-16T17:28:05.618Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-10-16T17:28:05.619Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-10-16T17:28:05.619Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-10-16T17:28:05.620Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Extended Windows`
-- 2025-10-16T17:28:05.620Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Attachment changelog (AD_Attachment_Log)`
-- 2025-10-16T17:28:05.621Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (de.metas.process.ExecuteUpdateSQL)`
-- 2025-10-16T17:28:05.621Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Role Access Update (org.compiere.process.RoleAccessUpdate)`
-- 2025-10-16T17:28:05.622Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `User Record Access Update from BPartner Hierachy (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2025-10-16T17:28:05.622Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2025-10-16T17:28:05.622Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Configuration (GeocodingConfig)`
-- 2025-10-16T17:28:05.623Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exported Data Audit (Data_Export_Audit)`
-- 2025-10-16T17:28:05.623Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `Analysis Report (QM_Analysis_Report)`
-- 2025-10-16T17:28:05.624Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542215 AND AD_Tree_ID=10
;

-- Node name: `Rebuild FactAcct Summary (de.metas.acct.process.Rebuild_FactAcctSummary)`
-- 2025-10-16T17:28:05.624Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542089 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2025-10-16T17:28:05.624Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2025-10-16T17:28:05.625Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Update purchase order highest price cache (de.metas.process.ExecuteUpdateSQL)`
-- 2025-10-16T17:28:05.625Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542158 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2025-10-16T17:28:05.626Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;

-- Node name: `RabbitMQ Message Audit (RabbitMQ_Message_Audit)`
-- 2025-10-16T17:28:05.626Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541910 AND AD_Tree_ID=10
;

-- Node name: `Letters (C_Letter)`
-- 2025-10-16T17:28:05.626Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540403 AND AD_Tree_ID=10
;

-- Node name: `Issues (AD_Issue)`
-- 2025-10-16T17:28:05.627Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542151 AND AD_Tree_ID=10
;

-- Node name: `Mobile`
-- 2025-10-16T17:28:05.627Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542179 AND AD_Tree_ID=10
;

-- Node name: `Business Rules`
-- 2025-10-16T17:28:05.628Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542187 AND AD_Tree_ID=10
;

-- Node name: `UI Tracing`
-- 2025-10-16T17:28:05.628Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542190 AND AD_Tree_ID=10
;

-- Node name: `Notification`
-- 2025-10-16T17:28:05.628Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542210 AND AD_Tree_ID=10
;

-- Node name: `External System (ExternalSystem)`
-- 2025-10-16T17:28:05.629Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542246 AND AD_Tree_ID=10
;

-- Node name: `Externe System Konfig Skriptbasierte Importkonvertierung`
-- 2025-10-16T17:28:05.629Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542263 AND AD_Tree_ID=10
;

-- Reordering children of `System`
-- Node name: `Change Log Config (AD_ChangeLog_Config)`
-- 2025-10-16T17:28:24.455Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542199 AND AD_Tree_ID=10
;

-- Node name: `Warnings (AD_Record_Warning)`
-- 2025-10-16T17:28:24.456Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542223 AND AD_Tree_ID=10
;

-- Node name: `Externe Systeme authentifizieren (de.metas.externalsystem.externalservice.authorization.SendAuthTokenProcess)`
-- 2025-10-16T17:28:24.456Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541993 AND AD_Tree_ID=10
;

-- Node name: `External system config PCM (ExternalSystem_Config_ProCareManagement)`
-- 2025-10-16T17:28:24.457Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542142 AND AD_Tree_ID=10
;

-- Node name: `Costing (Freight etc)`
-- 2025-10-16T17:28:24.457Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542051 AND AD_Tree_ID=10
;

-- Node name: `External system config Leich + Mehl (ExternalSystem_Config_LeichMehl)`
-- 2025-10-16T17:28:24.467Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541966 AND AD_Tree_ID=10
;

-- Node name: `API Audit`
-- 2025-10-16T17:28:24.467Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2025-10-16T17:28:24.467Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `External System Configuration (ExternalSystem_Config)`
-- 2025-10-16T17:28:24.468Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2025-10-16T17:28:24.468Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `External system config Scripted Export Conversion (ExternalSystem_Config_ScriptedExportConversion)`
-- 2025-10-16T17:28:24.469Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542262 AND AD_Tree_ID=10
;

-- Node name: `Externe System Konfig Skriptbasierte Importkonvertierung`
-- 2025-10-16T17:28:24.469Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542263 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2025-10-16T17:28:24.470Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2025-10-16T17:28:24.470Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2025-10-16T17:28:24.471Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2025-10-16T17:28:24.471Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2025-10-16T17:28:24.471Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchronous processing`
-- 2025-10-16T17:28:24.472Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2025-10-16T17:28:24.472Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Async workpackage queue (C_Queue_WorkPackage)`
-- 2025-10-16T17:28:24.473Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Scheduler (AD_Scheduler)`
-- 2025-10-16T17:28:24.473Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2025-10-16T17:28:24.473Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Role (AD_Role)`
-- 2025-10-16T17:28:24.474Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2025-10-16T17:28:24.474Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2025-10-16T17:28:24.474Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Counter Document (C_DocTypeCounter)`
-- 2025-10-16T17:28:24.475Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Users Group (AD_UserGroup)`
-- 2025-10-16T17:28:24.475Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2025-10-16T17:28:24.476Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Language (AD_Language)`
-- 2025-10-16T17:28:24.476Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menu (AD_Menu)`
-- 2025-10-16T17:28:24.477Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WEBUI_Dashboard)`
-- 2025-10-16T17:28:24.477Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2025-10-16T17:28:24.478Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Document Type (C_DocType)`
-- 2025-10-16T17:28:24.478Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Boiler Plate (AD_BoilerPlate)`
-- 2025-10-16T17:28:24.479Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Boilerplate Translation (AD_BoilerPlate_Trl)`
-- 2025-10-16T17:28:24.479Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2025-10-16T17:28:24.479Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Text Variable (AD_BoilerPlate_Var)`
-- 2025-10-16T17:28:24.480Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Print Format (AD_PrintFormat)`
-- 2025-10-16T17:28:24.480Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `Zebra Configuration (AD_Zebra_Config)`
-- 2025-10-16T17:28:24.481Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Document Sequence (AD_Sequence)`
-- 2025-10-16T17:28:24.481Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `My Profile (AD_User)`
-- 2025-10-16T17:28:24.482Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Printing Queue (C_Printing_Queue)`
-- 2025-10-16T17:28:24.482Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2025-10-16T17:28:24.482Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2025-10-16T17:28:24.483Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Printer (AD_PrinterHW)`
-- 2025-10-16T17:28:24.483Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Printer Mapping (AD_Printer_Config)`
-- 2025-10-16T17:28:24.484Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Printer-Tray-Mapping (AD_Printer_Matching)`
-- 2025-10-16T17:28:24.484Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `RV Missing Counter Documents (RV_Missing_Counter_Documents)`
-- 2025-10-16T17:28:24.484Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System Configuration (AD_SysConfig)`
-- 2025-10-16T17:28:24.484Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2025-10-16T17:28:24.485Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Session Audit (AD_Session)`
-- 2025-10-16T17:28:24.485Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2025-10-16T17:28:24.486Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Change Log (AD_ChangeLog)`
-- 2025-10-16T17:28:24.486Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner (I_BPartner)`
-- 2025-10-16T17:28:24.486Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2025-10-16T17:28:24.487Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Product (I_Product)`
-- 2025-10-16T17:28:24.487Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2025-10-16T17:28:24.488Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventory (I_Inventory)`
-- 2025-10-16T17:28:24.488Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2025-10-16T17:28:24.489Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import Account (I_ElementValue)`
-- 2025-10-16T17:28:24.489Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import GL Journal (I_GLJournal)`
-- 2025-10-16T17:28:24.489Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542247 AND AD_Tree_ID=10
;

-- Node name: `Import Format (AD_ImpFormat)`
-- 2025-10-16T17:28:24.490Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Import Forecast (I_Forecast)`
-- 2025-10-16T17:28:24.490Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542243 AND AD_Tree_ID=10
;

-- Node name: `Data import (C_DataImport)`
-- 2025-10-16T17:28:24.491Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2025-10-16T17:28:24.491Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2025-10-16T17:28:24.491Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Processor (IMP_Processor)`
-- 2025-10-16T17:28:24.492Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Processor Log (IMP_ProcessorLog)`
-- 2025-10-16T17:28:24.492Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2025-10-16T17:28:24.493Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export Format (DATEV_ExportFormat)`
-- 2025-10-16T17:28:24.493Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2025-10-16T17:28:24.493Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Event Log (AD_EventLog)`
-- 2025-10-16T17:28:24.494Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2025-10-16T17:28:24.494Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-10-16T17:28:24.495Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-10-16T17:28:24.495Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-10-16T17:28:24.495Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Extended Windows`
-- 2025-10-16T17:28:24.496Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Attachment changelog (AD_Attachment_Log)`
-- 2025-10-16T17:28:24.496Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (de.metas.process.ExecuteUpdateSQL)`
-- 2025-10-16T17:28:24.497Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Role Access Update (org.compiere.process.RoleAccessUpdate)`
-- 2025-10-16T17:28:24.497Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `User Record Access Update from BPartner Hierachy (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2025-10-16T17:28:24.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2025-10-16T17:28:24.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Configuration (GeocodingConfig)`
-- 2025-10-16T17:28:24.499Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exported Data Audit (Data_Export_Audit)`
-- 2025-10-16T17:28:24.499Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `Analysis Report (QM_Analysis_Report)`
-- 2025-10-16T17:28:24.500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542215 AND AD_Tree_ID=10
;

-- Node name: `Rebuild FactAcct Summary (de.metas.acct.process.Rebuild_FactAcctSummary)`
-- 2025-10-16T17:28:24.500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542089 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2025-10-16T17:28:24.500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2025-10-16T17:28:24.501Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Update purchase order highest price cache (de.metas.process.ExecuteUpdateSQL)`
-- 2025-10-16T17:28:24.501Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542158 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2025-10-16T17:28:24.502Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;

-- Node name: `RabbitMQ Message Audit (RabbitMQ_Message_Audit)`
-- 2025-10-16T17:28:24.502Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541910 AND AD_Tree_ID=10
;

-- Node name: `Letters (C_Letter)`
-- 2025-10-16T17:28:24.503Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540403 AND AD_Tree_ID=10
;

-- Node name: `Issues (AD_Issue)`
-- 2025-10-16T17:28:24.503Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542151 AND AD_Tree_ID=10
;

-- Node name: `Mobile`
-- 2025-10-16T17:28:24.504Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542179 AND AD_Tree_ID=10
;

-- Node name: `Business Rules`
-- 2025-10-16T17:28:24.504Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542187 AND AD_Tree_ID=10
;

-- Node name: `UI Tracing`
-- 2025-10-16T17:28:24.505Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542190 AND AD_Tree_ID=10
;

-- Node name: `Notification`
-- 2025-10-16T17:28:24.505Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542210 AND AD_Tree_ID=10
;

-- Node name: `External System (ExternalSystem)`
-- 2025-10-16T17:28:24.505Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542246 AND AD_Tree_ID=10
;

-- Run mode: SWING_CLIENT

-- Value: Call_external_system_ScriptedImportConversion
-- Classname: de.metas.externalsystem.process.InvokeScriptedImportConversionAction
-- 2025-10-17T12:46:07.759Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585512,'Y','de.metas.externalsystem.process.InvokeScriptedImportConversionAction','N',TO_TIMESTAMP('2025-10-17 12:46:07.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Importkonvertierung mit Skript Aufrufen','json','N','N','xls','Java',TO_TIMESTAMP('2025-10-17 12:46:07.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Call_external_system_ScriptedImportConversion')
;

-- 2025-10-17T12:46:07.783Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585512 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Call_external_system_ScriptedImportConversion(de.metas.externalsystem.process.InvokeScriptedImportConversionAction)
-- 2025-10-17T12:46:24.536Z
UPDATE AD_Process_Trl SET Name='Call Scripted Import Conversion',Updated=TO_TIMESTAMP('2025-10-17 12:46:24.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585512
;

-- 2025-10-17T12:46:24.537Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Name: External Request ScriptedExportConversion
-- 2025-10-17T12:47:52.964Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541998,TO_TIMESTAMP('2025-10-17 12:47:52.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','N','External Request ScriptedExportConversion',TO_TIMESTAMP('2025-10-17 12:47:52.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-10-17T12:47:52.970Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541998 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: External Request ScriptedExportConversion
-- Value: enableRestAPI
-- ValueName: Start API
-- 2025-10-17T12:49:20.276Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544006,541998,TO_TIMESTAMP('2025-10-17 12:49:20.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','API Starten',TO_TIMESTAMP('2025-10-17 12:49:20.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'enableRestAPI','Start API')
;

-- 2025-10-17T12:49:20.280Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544006 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: External Request ScriptedExportConversion
-- Value: disableRestAPI
-- ValueName: Stop API
-- 2025-10-17T12:49:40.726Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544007,541998,TO_TIMESTAMP('2025-10-17 12:49:40.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','API Stoppen',TO_TIMESTAMP('2025-10-17 12:49:40.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'disableRestAPI','Stop API')
;

-- 2025-10-17T12:49:40.743Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544007 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: External Request ScriptedExportConversion -> disableRestAPI_Stop API
-- 2025-10-17T12:49:49.356Z
UPDATE AD_Ref_List_Trl SET Name='Stop API',Updated=TO_TIMESTAMP('2025-10-17 12:49:49.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544007
;

-- 2025-10-17T12:49:49.357Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Name: External Request ScriptedImportConversion
-- 2025-10-17T12:52:02.205Z
UPDATE AD_Reference SET Name='External Request ScriptedImportConversion',Updated=TO_TIMESTAMP('2025-10-17 12:52:02.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541998
;

-- 2025-10-17T12:52:02.206Z
UPDATE AD_Reference_Trl trl SET Name='External Request ScriptedImportConversion' WHERE AD_Reference_ID=541998 AND AD_Language='de_DE'
;

-- 2025-10-17T12:52:20.030Z
UPDATE AD_Reference_Trl SET Name='External Request ScriptedImportConversion',Updated=TO_TIMESTAMP('2025-10-17 12:52:20.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541998
;

-- 2025-10-17T12:52:20.031Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-17T12:52:23.587Z
UPDATE AD_Reference_Trl SET Name='External Request ScriptedImportConversion',Updated=TO_TIMESTAMP('2025-10-17 12:52:23.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=541998
;

-- 2025-10-17T12:52:23.588Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-17T12:52:25.247Z
UPDATE AD_Reference_Trl SET Name='External Request ScriptedImportConversion',Updated=TO_TIMESTAMP('2025-10-17 12:52:25.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541998
;

-- 2025-10-17T12:52:25.248Z
UPDATE AD_Reference base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Reference_Trl trl  WHERE trl.AD_Reference_ID=base.AD_Reference_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Call_external_system_ScriptedImportConversion(de.metas.externalsystem.process.InvokeScriptedImportConversionAction)
-- ParameterName: External_Request
-- 2025-10-17T12:53:24.809Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,578757,0,585512,543013,17,541998,'External_Request',TO_TIMESTAMP('2025-10-17 12:53:24.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalreference',30,'Y','N','Y','N','Y','N','Befehl',10,TO_TIMESTAMP('2025-10-17 12:53:24.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-17T12:53:24.817Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543013 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Call_external_system_ScriptedImportConversion(de.metas.externalsystem.process.InvokeScriptedImportConversionAction)
-- Table: ExternalSystem_Config
-- EntityType: de.metas.externalsystem
-- 2025-10-17T12:54:24.955Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585512,541576,541574,TO_TIMESTAMP('2025-10-17 12:54:24.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y',TO_TIMESTAMP('2025-10-17 12:54:24.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Process: Call_external_system_ScriptedImportConversion(de.metas.externalsystem.process.InvokeScriptedImportConversionAction)
-- Table: ExternalSystem_Config_ScriptedImportConversion
-- EntityType: de.metas.externalsystem
-- 2025-10-17T12:54:56.779Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585512,542546,541575,TO_TIMESTAMP('2025-10-17 12:54:56.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y',TO_TIMESTAMP('2025-10-17 12:54:56.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- 2025-10-17T13:10:15.158Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540829,0,542546,TO_TIMESTAMP('2025-10-17 13:10:14.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Unique constraint on parent config id','de.metas.externalsystem','Y','Y','IDX_S_ExtSystemScriptedImportConversion_unique_parent_id','N',TO_TIMESTAMP('2025-10-17 13:10:14.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-17T13:10:15.168Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540829 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-10-17T13:10:37.011Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591362,541474,540829,0,TO_TIMESTAMP('2025-10-17 13:10:36.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y',10,TO_TIMESTAMP('2025-10-17 13:10:36.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-17T13:10:42.547Z
CREATE UNIQUE INDEX IDX_S_ExtSystemScriptedImportConversion_unique_parent_id ON ExternalSystem_Config_ScriptedImportConversion (ExternalSystem_Config_ID)
;

-- 2025-10-17T13:11:33.561Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540830,0,542546,TO_TIMESTAMP('2025-10-17 13:11:33.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Unique index external system scriptedimportconversion value','de.metas.externalsystem','Y','Y','IDX_S_ExternalSystemScriptedImportConversion_unique_value','N',TO_TIMESTAMP('2025-10-17 13:11:33.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-17T13:11:33.563Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540830 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-10-17T13:11:55.920Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591367,541475,540830,0,TO_TIMESTAMP('2025-10-17 13:11:54.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y',10,TO_TIMESTAMP('2025-10-17 13:11:54.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2025-10-17T13:12:01.105Z
CREATE UNIQUE INDEX IDX_S_ExternalSystemScriptedImportConversion_unique_value ON ExternalSystem_Config_ScriptedImportConversion (ExternalSystemValue)
;

-- 2025-10-17T13:12:41.760Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540831,0,542546,TO_TIMESTAMP('2025-10-17 13:12:41.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Unique index external system scriptedimportconversion endpoint','de.metas.externalsystem','Y','Y','IDX_S_ExternalSystemScriptedImportConversion_unique_endpoint','N',TO_TIMESTAMP('2025-10-17 13:12:41.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-17T13:12:41.762Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540831 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-10-17T13:12:55.981Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591365,541476,540831,0,TO_TIMESTAMP('2025-10-17 13:12:55.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y',10,TO_TIMESTAMP('2025-10-17 13:12:55.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-17T13:12:59.395Z
CREATE UNIQUE INDEX IDX_S_ExternalSystemScriptedImportConversion_unique_endpoint ON ExternalSystem_Config_ScriptedImportConversion (EndpointName)
;


-- 2025-10-17T13:20:29.369Z
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,ExternalSystem_ID,ExternalSystem_Service_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-17 13:20:29.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'/scriptedimportconversion',540058,540017,'Y','REST API',TO_TIMESTAMP('2025-10-17 13:20:29.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'defaultRestAPIScriptedImportConversion')
;


-- 2025-10-17T15:20:20.553Z
UPDATE ExternalSystem_Service SET EnableCommand='enableRestAPI',Updated=TO_TIMESTAMP('2025-10-17 15:20:20.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE ExternalSystem_Service_ID=540017
;

-- 2025-10-17T15:20:27.202Z
UPDATE ExternalSystem_Service SET DisableCommand='disableRestAPI',Updated=TO_TIMESTAMP('2025-10-17 15:20:27.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE ExternalSystem_Service_ID=540017
;

