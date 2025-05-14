-- Run mode: SWING_CLIENT

-- Table: ModCntr_BaseModuleConfig
-- 2025-05-12T13:53:32.292Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542477,'N',TO_TIMESTAMP('2025-05-12 15:53:32.082','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','N','Y','N','N','Y','N','N','N','N','N',0,'Basisbaustein Konfig','NP','L','ModCntr_BaseModuleConfig','DTI',TO_TIMESTAMP('2025-05-12 15:53:32.082','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-12T13:53:32.300Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542477 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2025-05-12T13:53:32.411Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556482,TO_TIMESTAMP('2025-05-12 15:53:32.317','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table ModCntr_BaseModuleConfig',1,'Y','N','Y','Y','ModCntr_BaseModuleConfig','N',1000000,TO_TIMESTAMP('2025-05-12 15:53:32.317','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-12T13:53:32.426Z
CREATE SEQUENCE MODCNTR_BASEMODULECONFIG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2025-05-12T13:54:23.342Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Base Module Config',Updated=TO_TIMESTAMP('2025-05-12 15:54:23.338','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542477
;

-- 2025-05-12T13:54:25.236Z
UPDATE AD_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-12 15:54:25.233','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542477
;

-- 2025-05-12T13:54:27.702Z
UPDATE AD_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-12 15:54:27.7','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542477
;

-- Column: ModCntr_BaseModuleConfig.AD_Client_ID
-- 2025-05-12T13:54:39.958Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589959,102,0,19,542477,'AD_Client_ID',TO_TIMESTAMP('2025-05-12 15:54:39.818','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','de.metas.contracts',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2025-05-12 15:54:39.818','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-12T13:54:39.962Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589959 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-12T13:54:39.984Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: ModCntr_BaseModuleConfig.AD_Org_ID
-- 2025-05-12T13:54:41.546Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589960,113,0,30,542477,'AD_Org_ID',TO_TIMESTAMP('2025-05-12 15:54:41.297','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','de.metas.contracts',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2025-05-12 15:54:41.297','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-12T13:54:41.547Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589960 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-12T13:54:41.549Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: ModCntr_BaseModuleConfig.Created
-- 2025-05-12T13:54:42.036Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589961,245,0,16,542477,'Created',TO_TIMESTAMP('2025-05-12 15:54:41.842','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.contracts',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2025-05-12 15:54:41.842','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-12T13:54:42.039Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589961 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-12T13:54:42.042Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: ModCntr_BaseModuleConfig.CreatedBy
-- 2025-05-12T13:54:42.533Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589962,246,0,18,110,542477,'CreatedBy',TO_TIMESTAMP('2025-05-12 15:54:42.344','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.contracts',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2025-05-12 15:54:42.344','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-12T13:54:42.536Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589962 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-12T13:54:42.538Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: ModCntr_BaseModuleConfig.IsActive
-- 2025-05-12T13:54:43.018Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589963,348,0,20,542477,'IsActive',TO_TIMESTAMP('2025-05-12 15:54:42.826','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','de.metas.contracts',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2025-05-12 15:54:42.826','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-12T13:54:43.020Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589963 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-12T13:54:43.024Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: ModCntr_BaseModuleConfig.Updated
-- 2025-05-12T13:54:43.634Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589964,607,0,16,542477,'Updated',TO_TIMESTAMP('2025-05-12 15:54:43.31','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.contracts',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2025-05-12 15:54:43.31','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-12T13:54:43.637Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589964 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-12T13:54:43.643Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: ModCntr_BaseModuleConfig.UpdatedBy
-- 2025-05-12T13:54:44.258Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589965,608,0,18,110,542477,'UpdatedBy',TO_TIMESTAMP('2025-05-12 15:54:44.016','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.contracts',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2025-05-12 15:54:44.016','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-12T13:54:44.261Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589965 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-12T13:54:44.264Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2025-05-12T13:54:44.741Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583621,0,'ModCntr_BaseModuleConfig_ID',TO_TIMESTAMP('2025-05-12 15:54:44.653','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Basisbaustein Konfig','Basisbaustein Konfig',TO_TIMESTAMP('2025-05-12 15:54:44.653','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-12T13:54:44.745Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583621 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ModCntr_BaseModuleConfig.ModCntr_BaseModuleConfig_ID
-- 2025-05-12T13:54:45.269Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589966,583621,0,13,542477,'ModCntr_BaseModuleConfig_ID',TO_TIMESTAMP('2025-05-12 15:54:44.649','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Basisbaustein Konfig',0,0,TO_TIMESTAMP('2025-05-12 15:54:44.649','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-12T13:54:45.270Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589966 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-12T13:54:45.273Z
/* DDL */  select update_Column_Translation_From_AD_Element(583621)
;

-- Column: ModCntr_BaseModuleConfig.Name
-- 2025-05-12T14:01:22.471Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589967,469,0,10,542477,'Name',TO_TIMESTAMP('2025-05-12 16:01:22.325','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','de.metas.contracts',0,40,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2025-05-12 16:01:22.325','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-12T14:01:22.474Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589967 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-12T14:01:22.479Z
/* DDL */  select update_Column_Translation_From_AD_Element(469)
;

-- 2025-05-12T14:18:41.305Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583622,0,'ModCntr_BaseModule_ID',TO_TIMESTAMP('2025-05-12 16:18:41.167','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Basisbausteine','Basisbausteine',TO_TIMESTAMP('2025-05-12 16:18:41.167','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-12T14:18:41.308Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583622 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ModCntr_BaseModule_ID
-- 2025-05-12T14:19:14.804Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Base Modules', PrintName='Base Modules',Updated=TO_TIMESTAMP('2025-05-12 16:19:14.804','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583622 AND AD_Language='en_US'
;

-- 2025-05-12T14:19:14.806Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583622,'en_US')
;

-- Element: ModCntr_BaseModule_ID
-- 2025-05-12T14:19:16.465Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-12 16:19:16.465','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583622 AND AD_Language='de_CH'
;

-- 2025-05-12T14:19:16.467Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583622,'de_CH')
;

-- Element: ModCntr_BaseModule_ID
-- 2025-05-12T14:19:17.866Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-12 16:19:17.866','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583622 AND AD_Language='de_DE'
;

-- 2025-05-12T14:19:17.868Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583622,'de_DE')
;

-- 2025-05-12T14:19:17.869Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583622,'de_DE')
;

-- Column: ModCntr_BaseModuleConfig.ModCntr_Module_ID
-- 2025-05-12T14:19:44.070Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589968,582426,0,19,542477,'ModCntr_Module_ID',TO_TIMESTAMP('2025-05-12 16:19:43.877','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Bausteine',0,0,TO_TIMESTAMP('2025-05-12 16:19:43.877','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-12T14:19:44.071Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589968 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-12T14:19:44.073Z
/* DDL */  select update_Column_Translation_From_AD_Element(582426)
;

-- Column: ModCntr_BaseModuleConfig.ModCntr_BaseModule_ID
-- 2025-05-12T14:21:06.911Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589969,583622,0,18,541830,542477,'ModCntr_BaseModule_ID',TO_TIMESTAMP('2025-05-12 16:21:06.76','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Basisbausteine',0,0,TO_TIMESTAMP('2025-05-12 16:21:06.76','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-12T14:21:06.912Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589969 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-12T14:21:06.915Z
/* DDL */  select update_Column_Translation_From_AD_Element(583622)
;

-- 2025-05-12T14:21:17.383Z
/* DDL */ CREATE TABLE public.ModCntr_BaseModuleConfig (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, ModCntr_BaseModuleConfig_ID NUMERIC(10) NOT NULL, ModCntr_BaseModule_ID NUMERIC(10) NOT NULL, ModCntr_Module_ID NUMERIC(10) NOT NULL, Name VARCHAR(40) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ModCntr_BaseModuleConfig_Key PRIMARY KEY (ModCntr_BaseModuleConfig_ID), CONSTRAINT ModCntrBaseModule_ModCntrBaseModuleConfig FOREIGN KEY (ModCntr_BaseModule_ID) REFERENCES public.ModCntr_Module DEFERRABLE INITIALLY DEFERRED, CONSTRAINT ModCntrModule_ModCntrBaseModuleConfig FOREIGN KEY (ModCntr_Module_ID) REFERENCES public.ModCntr_Module DEFERRABLE INITIALLY DEFERRED)
;

-- Column: ModCntr_BaseModuleConfig.ModCntr_Settings_ID
-- 2025-05-12T14:36:36.474Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589970,582425,0,19,542477,'ModCntr_Settings_ID',TO_TIMESTAMP('2025-05-12 16:36:36.327','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Einstellungen für modulare Verträge',0,0,TO_TIMESTAMP('2025-05-12 16:36:36.327','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-12T14:36:36.476Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589970 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-12T14:36:36.478Z
/* DDL */  select update_Column_Translation_From_AD_Element(582425)
;

-- 2025-05-12T14:36:38.032Z
/* DDL */ SELECT public.db_alter_table('ModCntr_BaseModuleConfig','ALTER TABLE public.ModCntr_BaseModuleConfig ADD COLUMN ModCntr_Settings_ID NUMERIC(10) NOT NULL')
;

-- 2025-05-12T14:36:38.039Z
ALTER TABLE ModCntr_BaseModuleConfig ADD CONSTRAINT ModCntrSettings_ModCntrBaseModuleConfig FOREIGN KEY (ModCntr_Settings_ID) REFERENCES public.ModCntr_Settings DEFERRABLE INITIALLY DEFERRED
;

-- Name: BaseModuleConfig_Modules
-- 2025-05-13T06:18:43.173Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540726,'ModCntr_Module.ModCntr_Module_ID IN (SELECT ModCntr_Module_ID FROM ModCntr_Module m  INNER JOIN ModCntr_Type t ON m.modcntr_type_id = t.modcntr_type_id AND modularcontracthandlertype = ''SalesInterest'' WHERE m.ModCntr_Settings_ID = @ModCntr_Settings_ID/-1@)',TO_TIMESTAMP('2025-05-13 08:18:42.965','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','BaseModuleConfig_Modules','S',TO_TIMESTAMP('2025-05-13 08:18:42.965','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: BaseModuleConfig_BaseModules
-- 2025-05-13T06:28:10.719Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540727,'ModCntr_Module.ModCntr_Module_ID IN (SELECT ModCntr_Module_ID FROM ModCntr_Module m  INNER JOIN ModCntr_Type t ON m.modcntr_type_id = t.modcntr_type_id AND modularcontracthandlertype IN (''SalesAV'', ''SalesAverageAVOnShippedQty'', ''Sales'') WHERE m.ModCntr_Settings_ID = @ModCntr_Settings_ID/-1@)',TO_TIMESTAMP('2025-05-13 08:28:10.547','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','BaseModuleConfig_BaseModules','S',TO_TIMESTAMP('2025-05-13 08:28:10.547','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: ModCntr_BaseModuleConfig.ModCntr_Module_ID
-- 2025-05-13T06:29:15.226Z
UPDATE AD_Column SET AD_Val_Rule_ID=540726,Updated=TO_TIMESTAMP('2025-05-13 08:29:15.226','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589968
;

-- Column: ModCntr_BaseModuleConfig.ModCntr_BaseModule_ID
-- 2025-05-13T06:29:28.053Z
UPDATE AD_Column SET AD_Val_Rule_ID=540727,Updated=TO_TIMESTAMP('2025-05-13 08:29:28.053','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589969
;

-- 2025-05-14T06:07:18.275Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540816,0,542477,TO_TIMESTAMP('2025-05-14 08:07:18.155','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Basisbaustein Konfiguration muss für aktive Einträge einzigartige Kombinationen von Bausteinen enthalten.','Y','Y','modcntr_basemoduleconfig_settings_uq','N',TO_TIMESTAMP('2025-05-14 08:07:18.155','YYYY-MM-DD HH24:MI:SS.US'),100,'isActive = ''Y''')
;

-- 2025-05-14T06:07:18.285Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540816 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-05-14T06:07:47.467Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,589970,541447,540816,0,TO_TIMESTAMP('2025-05-14 08:07:47.35','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',10,TO_TIMESTAMP('2025-05-14 08:07:47.35','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-14T06:08:14.167Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,589968,541448,540816,0,TO_TIMESTAMP('2025-05-14 08:08:14.041','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',20,TO_TIMESTAMP('2025-05-14 08:08:14.041','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-14T06:08:57.911Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,589969,541449,540816,0,TO_TIMESTAMP('2025-05-14 08:08:57.782','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',30,TO_TIMESTAMP('2025-05-14 08:08:57.782','YYYY-MM-DD HH24:MI:SS.US'),100)
;





