-- Table: PickingProfile_Filter
-- Table: PickingProfile_Filter
-- 2024-01-26T10:16:42.204Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542389,'N',TO_TIMESTAMP('2024-01-26 12:16:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','N','Y','N','N','Y','N','N','N','N','N',0,'Picking Filter','NP','L','PickingProfile_Filter','DTI',TO_TIMESTAMP('2024-01-26 12:16:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:16:42.208Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542389 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-01-26T10:16:42.331Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556325,TO_TIMESTAMP('2024-01-26 12:16:42','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table PickingProfile_Filter',1,'Y','N','Y','Y','PickingProfile_Filter','N',1000000,TO_TIMESTAMP('2024-01-26 12:16:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T10:16:42.344Z
CREATE SEQUENCE PICKINGPROFILE_FILTER_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: PickingProfile_Filter.AD_Client_ID
-- Column: PickingProfile_Filter.AD_Client_ID
-- 2024-01-26T10:17:05.883Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587856,102,0,19,542389,'AD_Client_ID',TO_TIMESTAMP('2024-01-26 12:17:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.picking',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-01-26 12:17:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:17:05.888Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587856 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:17:05.926Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: PickingProfile_Filter.AD_Org_ID
-- Column: PickingProfile_Filter.AD_Org_ID
-- 2024-01-26T10:17:07.240Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587857,113,0,30,542389,'AD_Org_ID',TO_TIMESTAMP('2024-01-26 12:17:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.picking',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-01-26 12:17:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:17:07.247Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587857 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:17:07.249Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: PickingProfile_Filter.Created
-- Column: PickingProfile_Filter.Created
-- 2024-01-26T10:17:08.190Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587858,245,0,16,542389,'Created',TO_TIMESTAMP('2024-01-26 12:17:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.picking',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-01-26 12:17:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:17:08.192Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587858 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:17:08.194Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: PickingProfile_Filter.CreatedBy
-- Column: PickingProfile_Filter.CreatedBy
-- 2024-01-26T10:17:09.145Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587859,246,0,18,110,542389,'CreatedBy',TO_TIMESTAMP('2024-01-26 12:17:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.picking',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-01-26 12:17:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:17:09.146Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587859 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:17:09.148Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: PickingProfile_Filter.IsActive
-- Column: PickingProfile_Filter.IsActive
-- 2024-01-26T10:17:10.144Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587860,348,0,20,542389,'IsActive',TO_TIMESTAMP('2024-01-26 12:17:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.picking',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-01-26 12:17:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:17:10.146Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587860 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:17:10.148Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: PickingProfile_Filter.Updated
-- Column: PickingProfile_Filter.Updated
-- 2024-01-26T10:17:11.429Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587861,607,0,16,542389,'Updated',TO_TIMESTAMP('2024-01-26 12:17:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.picking',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-01-26 12:17:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:17:11.431Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587861 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:17:11.433Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: PickingProfile_Filter.UpdatedBy
-- Column: PickingProfile_Filter.UpdatedBy
-- 2024-01-26T10:17:12.382Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587862,608,0,18,110,542389,'UpdatedBy',TO_TIMESTAMP('2024-01-26 12:17:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.picking',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-01-26 12:17:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:17:12.384Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587862 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:17:12.385Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-01-26T10:17:13.046Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582926,0,'PickingProfile_Filter_ID',TO_TIMESTAMP('2024-01-26 12:17:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Picking Filter','Picking Filter',TO_TIMESTAMP('2024-01-26 12:17:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T10:17:13.052Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582926 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PickingProfile_Filter.PickingProfile_Filter_ID
-- Column: PickingProfile_Filter.PickingProfile_Filter_ID
-- 2024-01-26T10:17:13.892Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587863,582926,0,13,542389,'PickingProfile_Filter_ID',TO_TIMESTAMP('2024-01-26 12:17:12','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.picking',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Picking Filter',0,0,TO_TIMESTAMP('2024-01-26 12:17:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:17:13.894Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587863 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:17:13.896Z
/* DDL */  select update_Column_Translation_From_AD_Element(582926) 
;

-- Column: PickingProfile_Filter.MobileUI_UserProfile_Picking_ID
-- Column: PickingProfile_Filter.MobileUI_UserProfile_Picking_ID
-- 2024-01-26T10:18:08.712Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587864,582765,0,19,542389,'MobileUI_UserProfile_Picking_ID',TO_TIMESTAMP('2024-01-26 12:18:08','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.picking',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Mobile UI Kommissionierprofil',0,0,TO_TIMESTAMP('2024-01-26 12:18:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:18:08.713Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587864 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:18:08.718Z
/* DDL */  select update_Column_Translation_From_AD_Element(582765) 
;

-- 2024-01-26T10:18:55.470Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582927,0,'FilterType',TO_TIMESTAMP('2024-01-26 12:18:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Filter Type','Filter Type',TO_TIMESTAMP('2024-01-26 12:18:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T10:18:55.473Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582927 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: FilterType
-- 2024-01-26T10:19:27.109Z
UPDATE AD_Element_Trl SET Name='Filter Typ', PrintName='Filter Typ',Updated=TO_TIMESTAMP('2024-01-26 12:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582927 AND AD_Language='de_DE'
;

-- 2024-01-26T10:19:27.110Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582927,'de_DE') 
;

-- 2024-01-26T10:19:27.111Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582927,'de_DE') 
;

-- Element: FilterType
-- 2024-01-26T10:19:41.559Z
UPDATE AD_Element_Trl SET Name='Filter Typ', PrintName='Filter Typ',Updated=TO_TIMESTAMP('2024-01-26 12:19:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582927 AND AD_Language='fr_CH'
;

-- 2024-01-26T10:19:41.561Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582927,'fr_CH') 
;

-- Name: PickingFilter_Options
-- 2024-01-26T10:20:26.678Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541849,TO_TIMESTAMP('2024-01-26 12:20:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','N','PickingFilter_Options',TO_TIMESTAMP('2024-01-26 12:20:26','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-01-26T10:20:26.683Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541849 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PickingFilter_Options
-- Value: Customer
-- ValueName: Customer
-- 2024-01-26T10:20:47.591Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543617,541849,TO_TIMESTAMP('2024-01-26 12:20:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Customer',TO_TIMESTAMP('2024-01-26 12:20:47','YYYY-MM-DD HH24:MI:SS'),100,'Customer','Customer')
;

-- 2024-01-26T10:20:47.593Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543617 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PickingFilter_Options
-- Value: DeliveryDate
-- ValueName: DeliveryDate
-- 2024-01-26T10:21:13.881Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543618,541849,TO_TIMESTAMP('2024-01-26 12:21:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','DeliveryDate',TO_TIMESTAMP('2024-01-26 12:21:13','YYYY-MM-DD HH24:MI:SS'),100,'DeliveryDate','DeliveryDate')
;

-- 2024-01-26T10:21:13.883Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543618 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PickingFilter_Options
-- Value: HandoverLocation
-- ValueName: HandoverLocation
-- 2024-01-26T10:21:35.700Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543619,541849,TO_TIMESTAMP('2024-01-26 12:21:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','HandoverLocation',TO_TIMESTAMP('2024-01-26 12:21:35','YYYY-MM-DD HH24:MI:SS'),100,'HandoverLocation','HandoverLocation')
;

-- 2024-01-26T10:21:35.701Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543619 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PickingFilter_Options
-- Value: HandoverLocation
-- ValueName: HandoverLocation
-- 2024-01-26T10:21:39.277Z
UPDATE AD_Ref_List SET Name='Handover Location',Updated=TO_TIMESTAMP('2024-01-26 12:21:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543619
;

-- Reference: PickingFilter_Options
-- Value: DeliveryDate
-- ValueName: DeliveryDate
-- 2024-01-26T10:21:45.229Z
UPDATE AD_Ref_List SET Name='Delivery Date',Updated=TO_TIMESTAMP('2024-01-26 12:21:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543618
;

-- Reference Item: PickingFilter_Options -> Customer_Customer
-- 2024-01-26T10:21:58.028Z
UPDATE AD_Ref_List_Trl SET Name='Kunde',Updated=TO_TIMESTAMP('2024-01-26 12:21:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543617
;

-- Reference Item: PickingFilter_Options -> Customer_Customer
-- 2024-01-26T10:22:00.777Z
UPDATE AD_Ref_List_Trl SET Name='Kunde',Updated=TO_TIMESTAMP('2024-01-26 12:22:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543617
;

-- Reference Item: PickingFilter_Options -> Customer_Customer
-- 2024-01-26T10:22:06.851Z
UPDATE AD_Ref_List_Trl SET Name='Kunde',Updated=TO_TIMESTAMP('2024-01-26 12:22:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543617
;

-- Reference Item: PickingFilter_Options -> DeliveryDate_DeliveryDate
-- 2024-01-26T10:22:22.598Z
UPDATE AD_Ref_List_Trl SET Name='Lieferdatum',Updated=TO_TIMESTAMP('2024-01-26 12:22:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543618
;

-- Reference Item: PickingFilter_Options -> DeliveryDate_DeliveryDate
-- 2024-01-26T10:22:25.052Z
UPDATE AD_Ref_List_Trl SET Name='Lieferdatum',Updated=TO_TIMESTAMP('2024-01-26 12:22:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543618
;

-- Reference Item: PickingFilter_Options -> DeliveryDate_DeliveryDate
-- 2024-01-26T10:22:31.743Z
UPDATE AD_Ref_List_Trl SET Name='Lieferdatum',Updated=TO_TIMESTAMP('2024-01-26 12:22:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543618
;

-- Reference: PickingFilter_Options
-- Value: HandoverLocation
-- ValueName: HandoverLocation
-- 2024-01-26T10:24:14.259Z
UPDATE AD_Ref_List SET Name='Address',Updated=TO_TIMESTAMP('2024-01-26 12:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543619
;

-- Reference Item: PickingFilter_Options -> HandoverLocation_HandoverLocation
-- 2024-01-26T10:24:20.207Z
UPDATE AD_Ref_List_Trl SET Name='Adresse',Updated=TO_TIMESTAMP('2024-01-26 12:24:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543619
;

-- Reference Item: PickingFilter_Options -> HandoverLocation_HandoverLocation
-- 2024-01-26T10:24:22.600Z
UPDATE AD_Ref_List_Trl SET Name='Adresse',Updated=TO_TIMESTAMP('2024-01-26 12:24:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543619
;

-- Reference Item: PickingFilter_Options -> HandoverLocation_HandoverLocation
-- 2024-01-26T10:24:27.861Z
UPDATE AD_Ref_List_Trl SET Name='Adresse',Updated=TO_TIMESTAMP('2024-01-26 12:24:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543619
;

-- Reference Item: PickingFilter_Options -> HandoverLocation_HandoverLocation
-- 2024-01-26T10:24:33.151Z
UPDATE AD_Ref_List_Trl SET Name='Address',Updated=TO_TIMESTAMP('2024-01-26 12:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543619
;

-- Reference Item: PickingFilter_Options -> DeliveryDate_DeliveryDate
-- 2024-01-26T10:24:44.237Z
UPDATE AD_Ref_List_Trl SET Name='Delivery Date',Updated=TO_TIMESTAMP('2024-01-26 12:24:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543618
;

-- Column: PickingProfile_Filter.FilterType
-- Column: PickingProfile_Filter.FilterType
-- 2024-01-26T10:25:15.926Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587865,582927,0,17,541849,542389,'FilterType',TO_TIMESTAMP('2024-01-26 12:25:15','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.picking',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Filter Typ',0,0,TO_TIMESTAMP('2024-01-26 12:25:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:25:15.927Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587865 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:25:15.931Z
/* DDL */  select update_Column_Translation_From_AD_Element(582927) 
;

-- 2024-01-26T10:26:27.680Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540786,0,542389,TO_TIMESTAMP('2024-01-26 12:26:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','N','unq_idx_profile_filter','N',TO_TIMESTAMP('2024-01-26 12:26:27','YYYY-MM-DD HH24:MI:SS'),100,'isActive=''Y''')
;

-- 2024-01-26T10:26:27.682Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540786 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-01-26T10:26:32.324Z
/* DDL */ CREATE TABLE public.PickingProfile_Filter (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, FilterType VARCHAR(255) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MobileUI_UserProfile_Picking_ID NUMERIC(10) NOT NULL, PickingProfile_Filter_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MobileUIUserProfilePicking_PickingProfileFilter FOREIGN KEY (MobileUI_UserProfile_Picking_ID) REFERENCES public.MobileUI_UserProfile_Picking DEFERRABLE INITIALLY DEFERRED, CONSTRAINT PickingProfile_Filter_Key PRIMARY KEY (PickingProfile_Filter_ID))
;

-- 2024-01-26T10:26:44.644Z
INSERT INTO t_alter_column values('pickingprofile_filter','MobileUI_UserProfile_Picking_ID','NUMERIC(10)',null,null)
;

-- 2024-01-26T10:27:05.276Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587864,541391,540786,0,TO_TIMESTAMP('2024-01-26 12:27:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y',10,TO_TIMESTAMP('2024-01-26 12:27:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T10:27:14.614Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587865,541392,540786,0,TO_TIMESTAMP('2024-01-26 12:27:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y',20,TO_TIMESTAMP('2024-01-26 12:27:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T10:27:18.596Z
CREATE INDEX unq_idx_profile_filter ON PickingProfile_Filter (MobileUI_UserProfile_Picking_ID,FilterType) WHERE isActive='Y'
;

-- Table: PickingProfile_PickingJobConfig
-- Table: PickingProfile_PickingJobConfig
-- 2024-01-26T10:30:22.903Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542390,'N',TO_TIMESTAMP('2024-01-26 12:30:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','N','Y','N','N','Y','N','N','N','N','N',0,'Picking Job Config','NP','L','PickingProfile_PickingJobConfig','DTI',TO_TIMESTAMP('2024-01-26 12:30:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:30:22.906Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542390 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-01-26T10:30:23.007Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556326,TO_TIMESTAMP('2024-01-26 12:30:22','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table PickingProfile_PickingJobConfig',1,'Y','N','Y','Y','PickingProfile_PickingJobConfig','N',1000000,TO_TIMESTAMP('2024-01-26 12:30:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T10:30:23.012Z
CREATE SEQUENCE PICKINGPROFILE_PICKINGJOBCONFIG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: PickingProfile_PickingJobConfig.AD_Client_ID
-- Column: PickingProfile_PickingJobConfig.AD_Client_ID
-- 2024-01-26T10:30:30.477Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587866,102,0,19,542390,'AD_Client_ID',TO_TIMESTAMP('2024-01-26 12:30:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.picking',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-01-26 12:30:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:30:30.480Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587866 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:30:30.481Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: PickingProfile_PickingJobConfig.AD_Org_ID
-- Column: PickingProfile_PickingJobConfig.AD_Org_ID
-- 2024-01-26T10:30:31.341Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587867,113,0,30,542390,'AD_Org_ID',TO_TIMESTAMP('2024-01-26 12:30:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.picking',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-01-26 12:30:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:30:31.343Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587867 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:30:31.345Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: PickingProfile_PickingJobConfig.Created
-- Column: PickingProfile_PickingJobConfig.Created
-- 2024-01-26T10:30:32.193Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587868,245,0,16,542390,'Created',TO_TIMESTAMP('2024-01-26 12:30:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.picking',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-01-26 12:30:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:30:32.194Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587868 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:30:32.196Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: PickingProfile_PickingJobConfig.CreatedBy
-- Column: PickingProfile_PickingJobConfig.CreatedBy
-- 2024-01-26T10:30:33.028Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587869,246,0,18,110,542390,'CreatedBy',TO_TIMESTAMP('2024-01-26 12:30:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.picking',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-01-26 12:30:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:30:33.028Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587869 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:30:33.030Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: PickingProfile_PickingJobConfig.IsActive
-- Column: PickingProfile_PickingJobConfig.IsActive
-- 2024-01-26T10:30:33.860Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587870,348,0,20,542390,'IsActive',TO_TIMESTAMP('2024-01-26 12:30:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.picking',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-01-26 12:30:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:30:33.861Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587870 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:30:33.863Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: PickingProfile_PickingJobConfig.Updated
-- Column: PickingProfile_PickingJobConfig.Updated
-- 2024-01-26T10:30:34.708Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587871,607,0,16,542390,'Updated',TO_TIMESTAMP('2024-01-26 12:30:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.picking',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-01-26 12:30:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:30:34.709Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587871 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:30:34.712Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: PickingProfile_PickingJobConfig.UpdatedBy
-- Column: PickingProfile_PickingJobConfig.UpdatedBy
-- 2024-01-26T10:30:35.573Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587872,608,0,18,110,542390,'UpdatedBy',TO_TIMESTAMP('2024-01-26 12:30:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.picking',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-01-26 12:30:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:30:35.574Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587872 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:30:35.576Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-01-26T10:30:36.239Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582928,0,'PickingProfile_PickingJobConfig_ID',TO_TIMESTAMP('2024-01-26 12:30:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Picking Job Config','Picking Job Config',TO_TIMESTAMP('2024-01-26 12:30:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T10:30:36.240Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582928 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PickingProfile_PickingJobConfig.PickingProfile_PickingJobConfig_ID
-- Column: PickingProfile_PickingJobConfig.PickingProfile_PickingJobConfig_ID
-- 2024-01-26T10:30:37.014Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587873,582928,0,13,542390,'PickingProfile_PickingJobConfig_ID',TO_TIMESTAMP('2024-01-26 12:30:36','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.picking',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Picking Job Config',0,0,TO_TIMESTAMP('2024-01-26 12:30:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:30:37.015Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587873 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:30:37.017Z
/* DDL */  select update_Column_Translation_From_AD_Element(582928) 
;

-- Column: PickingProfile_PickingJobConfig.MobileUI_UserProfile_Picking_ID
-- Column: PickingProfile_PickingJobConfig.MobileUI_UserProfile_Picking_ID
-- 2024-01-26T10:31:02.510Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587874,582765,0,19,542390,'MobileUI_UserProfile_Picking_ID',TO_TIMESTAMP('2024-01-26 12:31:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.picking',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Mobile UI Kommissionierprofil',0,0,TO_TIMESTAMP('2024-01-26 12:31:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:31:02.510Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587874 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:31:02.513Z
/* DDL */  select update_Column_Translation_From_AD_Element(582765) 
;

-- 2024-01-26T10:31:32.510Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582929,0,'PickingJobField',TO_TIMESTAMP('2024-01-26 12:31:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Field','Field',TO_TIMESTAMP('2024-01-26 12:31:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T10:31:32.511Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582929 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PickingJobField
-- 2024-01-26T10:32:30.425Z
UPDATE AD_Element_Trl SET Name='Eintragsfeld', PrintName='Eintragsfeld',Updated=TO_TIMESTAMP('2024-01-26 12:32:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582929 AND AD_Language='de_CH'
;

-- 2024-01-26T10:32:30.427Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582929,'de_CH') 
;

-- Element: PickingJobField
-- 2024-01-26T10:32:35.443Z
UPDATE AD_Element_Trl SET Name='Eintragsfeld', PrintName='Eintragsfeld',Updated=TO_TIMESTAMP('2024-01-26 12:32:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582929 AND AD_Language='de_DE'
;

-- 2024-01-26T10:32:35.445Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582929,'de_DE') 
;

-- 2024-01-26T10:32:35.453Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582929,'de_DE') 
;

-- Element: PickingJobField
-- 2024-01-26T10:32:42.742Z
UPDATE AD_Element_Trl SET Name='Eintragsfeld', PrintName='Eintragsfeld',Updated=TO_TIMESTAMP('2024-01-26 12:32:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582929 AND AD_Language='fr_CH'
;

-- 2024-01-26T10:32:42.743Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582929,'fr_CH') 
;

-- Name: PickingJobField_Options
-- 2024-01-26T10:33:34.843Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541850,TO_TIMESTAMP('2024-01-26 12:33:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','N','PickingJobField_Options',TO_TIMESTAMP('2024-01-26 12:33:34','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-01-26T10:33:34.845Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541850 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PickingJobField_Options
-- Value: DocumentNo
-- ValueName: DocumentNo
-- 2024-01-26T10:33:59.568Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543620,541850,TO_TIMESTAMP('2024-01-26 12:33:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','DocumentNo',TO_TIMESTAMP('2024-01-26 12:33:59','YYYY-MM-DD HH24:MI:SS'),100,'DocumentNo','DocumentNo')
;

-- 2024-01-26T10:33:59.570Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543620 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: PickingJobField_Options -> DocumentNo_DocumentNo
-- 2024-01-26T10:34:29.360Z
UPDATE AD_Ref_List_Trl SET Name='Doku Nummer',Updated=TO_TIMESTAMP('2024-01-26 12:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543620
;

-- Reference Item: PickingJobField_Options -> DocumentNo_DocumentNo
-- 2024-01-26T10:34:32.352Z
UPDATE AD_Ref_List_Trl SET Name='Doku Nummer',Updated=TO_TIMESTAMP('2024-01-26 12:34:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543620
;

-- Reference Item: PickingJobField_Options -> DocumentNo_DocumentNo
-- 2024-01-26T10:34:36.834Z
UPDATE AD_Ref_List_Trl SET Name='Doku Nummer',Updated=TO_TIMESTAMP('2024-01-26 12:34:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543620
;

-- Reference Item: PickingJobField_Options -> DocumentNo_DocumentNo
-- 2024-01-26T10:34:54.246Z
UPDATE AD_Ref_List_Trl SET Name='Document No',Updated=TO_TIMESTAMP('2024-01-26 12:34:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543620
;

-- Reference: PickingJobField_Options
-- Value: Customer
-- ValueName: Customer
-- 2024-01-26T10:35:17.335Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543621,541850,TO_TIMESTAMP('2024-01-26 12:35:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Customer',TO_TIMESTAMP('2024-01-26 12:35:17','YYYY-MM-DD HH24:MI:SS'),100,'Customer','Customer')
;

-- 2024-01-26T10:35:17.336Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543621 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PickingJobField_Options
-- Value: ShipToLocation
-- ValueName: Delivery Address
-- 2024-01-26T10:35:49.910Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543622,541850,TO_TIMESTAMP('2024-01-26 12:35:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Delivery Address',TO_TIMESTAMP('2024-01-26 12:35:49','YYYY-MM-DD HH24:MI:SS'),100,'ShipToLocation','Delivery Address')
;

-- 2024-01-26T10:35:49.911Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543622 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PickingJobField_Options
-- Value: PreparationDate
-- ValueName: Date Ready
-- 2024-01-26T10:38:46.769Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543623,541850,TO_TIMESTAMP('2024-01-26 12:38:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Date Ready',TO_TIMESTAMP('2024-01-26 12:38:46','YYYY-MM-DD HH24:MI:SS'),100,'PreparationDate','Date Ready')
;

-- 2024-01-26T10:38:46.770Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543623 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PickingJobField_Options
-- Value: HandoverLocation
-- ValueName: Handover Location
-- 2024-01-26T10:39:18.248Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543624,541850,TO_TIMESTAMP('2024-01-26 12:39:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Handover Location',TO_TIMESTAMP('2024-01-26 12:39:18','YYYY-MM-DD HH24:MI:SS'),100,'HandoverLocation','Handover Location')
;

-- 2024-01-26T10:39:18.249Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543624 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PickingJobField_Options
-- Value: Setup_Place_No
-- ValueName: Rüstplatz Nr.
-- 2024-01-26T10:39:49.286Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543625,541850,TO_TIMESTAMP('2024-01-26 12:39:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Rüstplatz Nr.',TO_TIMESTAMP('2024-01-26 12:39:49','YYYY-MM-DD HH24:MI:SS'),100,'Setup_Place_No','Rüstplatz Nr.')
;

-- 2024-01-26T10:39:49.287Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543625 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: PickingProfile_PickingJobConfig.PickingJobField
-- Column: PickingProfile_PickingJobConfig.PickingJobField
-- 2024-01-26T10:40:07.695Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587875,582929,0,17,541850,542390,'PickingJobField',TO_TIMESTAMP('2024-01-26 12:40:07','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.picking',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Eintragsfeld',0,0,TO_TIMESTAMP('2024-01-26 12:40:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T10:40:07.698Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587875 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T10:40:07.701Z
/* DDL */  select update_Column_Translation_From_AD_Element(582929) 
;

-- 2024-01-26T11:02:39.066Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582930,0,'IsDisplayInSummary',TO_TIMESTAMP('2024-01-26 13:02:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Display in summary','Display in summary',TO_TIMESTAMP('2024-01-26 13:02:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:02:39.070Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582930 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-01-26T11:03:17.138Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582931,0,'IsDisplayInDetailed',TO_TIMESTAMP('2024-01-26 13:03:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Display in detailed','Display in detailed',TO_TIMESTAMP('2024-01-26 13:03:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:03:17.139Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582931 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PickingProfile_PickingJobConfig.IsDisplayInSummary
-- Column: PickingProfile_PickingJobConfig.IsDisplayInSummary
-- 2024-01-26T11:03:56.375Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587876,582930,0,20,542390,'IsDisplayInSummary',TO_TIMESTAMP('2024-01-26 13:03:56','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.picking',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Display in summary',0,0,TO_TIMESTAMP('2024-01-26 13:03:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T11:03:56.376Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587876 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T11:03:56.378Z
/* DDL */  select update_Column_Translation_From_AD_Element(582930) 
;

-- Column: PickingProfile_PickingJobConfig.IsDisplayInDetailed
-- Column: PickingProfile_PickingJobConfig.IsDisplayInDetailed
-- 2024-01-26T11:04:14.400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587877,582931,0,20,542390,'IsDisplayInDetailed',TO_TIMESTAMP('2024-01-26 13:04:14','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.picking',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Display in detailed',0,0,TO_TIMESTAMP('2024-01-26 13:04:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-26T11:04:14.402Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587877 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-26T11:04:14.404Z
/* DDL */  select update_Column_Translation_From_AD_Element(582931) 
;

-- 2024-01-26T11:04:16.385Z
/* DDL */ CREATE TABLE public.PickingProfile_PickingJobConfig (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsDisplayInDetailed CHAR(1) DEFAULT 'N' CHECK (IsDisplayInDetailed IN ('Y','N')) NOT NULL, IsDisplayInSummary CHAR(1) DEFAULT 'N' CHECK (IsDisplayInSummary IN ('Y','N')) NOT NULL, MobileUI_UserProfile_Picking_ID NUMERIC(10) NOT NULL, PickingJobField VARCHAR(255) NOT NULL, PickingProfile_PickingJobConfig_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MobileUIUserProfilePicking_PickingProfilePickingJobConfig FOREIGN KEY (MobileUI_UserProfile_Picking_ID) REFERENCES public.MobileUI_UserProfile_Picking DEFERRABLE INITIALLY DEFERRED, CONSTRAINT PickingProfile_PickingJobConfig_Key PRIMARY KEY (PickingProfile_PickingJobConfig_ID))
;

-- Tab: Mobile UI Kommissionierprofil -> Picking Filter
-- Table: PickingProfile_Filter
-- Tab: Mobile UI Kommissionierprofil(541743,D) -> Picking Filter
-- Table: PickingProfile_Filter
-- 2024-01-26T11:11:01.025Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,587864,582926,0,547359,542389,541743,'Y',TO_TIMESTAMP('2024-01-26 13:11:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','N','N','A','PickingProfile_Filter','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Picking Filter',587561,'N',30,1,TO_TIMESTAMP('2024-01-26 13:11:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:11:01.030Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547359 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-01-26T11:11:01.037Z
/* DDL */  select update_tab_translation_from_ad_element(582926) 
;

-- 2024-01-26T11:11:01.071Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547359)
;

-- 2024-01-26T11:11:13.407Z
INSERT INTO t_alter_column values('pickingprofile_filter','MobileUI_UserProfile_Picking_ID','NUMERIC(10)',null,null)
;

-- Tab: Mobile UI Kommissionierprofil -> Picking Job Config
-- Table: PickingProfile_PickingJobConfig
-- Tab: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config
-- Table: PickingProfile_PickingJobConfig
-- 2024-01-26T11:13:10.227Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,587874,582928,0,547360,542390,541743,'Y',TO_TIMESTAMP('2024-01-26 13:13:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','N','N','A','PickingProfile_PickingJobConfig','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Picking Job Config',587561,'N',40,1,TO_TIMESTAMP('2024-01-26 13:13:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:13:10.228Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547360 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-01-26T11:13:10.229Z
/* DDL */  select update_tab_translation_from_ad_element(582928) 
;

-- 2024-01-26T11:13:10.233Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547360)
;

-- 2024-01-26T11:13:17.613Z
INSERT INTO t_alter_column values('pickingprofile_pickingjobconfig','MobileUI_UserProfile_Picking_ID','NUMERIC(10)',null,null)
;

-- Column: PickingProfile_PickingJobConfig.PickingJobField
-- Column: PickingProfile_PickingJobConfig.PickingJobField
-- 2024-01-26T11:13:41.441Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2024-01-26 13:13:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587875
;

-- 2024-01-26T11:14:37.548Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540787,0,542390,TO_TIMESTAMP('2024-01-26 13:14:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','N','unq_idx_profile_field','N',TO_TIMESTAMP('2024-01-26 13:14:37','YYYY-MM-DD HH24:MI:SS'),100,'isActive=''Y''')
;

-- 2024-01-26T11:14:37.550Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540787 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-01-26T11:14:57.885Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587874,541393,540787,0,TO_TIMESTAMP('2024-01-26 13:14:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y',10,TO_TIMESTAMP('2024-01-26 13:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:15:11.676Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587875,541394,540787,0,TO_TIMESTAMP('2024-01-26 13:15:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y',20,TO_TIMESTAMP('2024-01-26 13:15:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:16:04.647Z
CREATE INDEX unq_idx_profile_field ON PickingProfile_PickingJobConfig (MobileUI_UserProfile_Picking_ID,PickingJobField) WHERE isActive='Y'
;

-- Field: Mobile UI Kommissionierprofil -> Picking Job Config -> Mandant
-- Column: PickingProfile_PickingJobConfig.AD_Client_ID
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> Mandant
-- Column: PickingProfile_PickingJobConfig.AD_Client_ID
-- 2024-01-26T11:16:24.875Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587866,723850,0,547360,TO_TIMESTAMP('2024-01-26 13:16:24','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.picking','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-01-26 13:16:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:16:24.879Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723850 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:16:24.883Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-01-26T11:16:26.033Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723850
;

-- 2024-01-26T11:16:26.034Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723850)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Job Config -> Sektion
-- Column: PickingProfile_PickingJobConfig.AD_Org_ID
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> Sektion
-- Column: PickingProfile_PickingJobConfig.AD_Org_ID
-- 2024-01-26T11:16:26.118Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587867,723851,0,547360,TO_TIMESTAMP('2024-01-26 13:16:26','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.picking','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-01-26 13:16:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:16:26.119Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723851 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:16:26.120Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-01-26T11:16:26.446Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723851
;

-- 2024-01-26T11:16:26.446Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723851)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Job Config -> Aktiv
-- Column: PickingProfile_PickingJobConfig.IsActive
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> Aktiv
-- Column: PickingProfile_PickingJobConfig.IsActive
-- 2024-01-26T11:16:26.536Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587870,723852,0,547360,TO_TIMESTAMP('2024-01-26 13:16:26','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.picking','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-01-26 13:16:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:16:26.537Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723852 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:16:26.539Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-01-26T11:16:26.948Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723852
;

-- 2024-01-26T11:16:26.949Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723852)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Job Config -> Picking Job Config
-- Column: PickingProfile_PickingJobConfig.PickingProfile_PickingJobConfig_ID
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> Picking Job Config
-- Column: PickingProfile_PickingJobConfig.PickingProfile_PickingJobConfig_ID
-- 2024-01-26T11:16:27.060Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587873,723853,0,547360,TO_TIMESTAMP('2024-01-26 13:16:26','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.picking','Y','N','N','N','N','N','N','N','Picking Job Config',TO_TIMESTAMP('2024-01-26 13:16:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:16:27.061Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723853 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:16:27.062Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582928) 
;

-- 2024-01-26T11:16:27.065Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723853
;

-- 2024-01-26T11:16:27.065Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723853)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Job Config -> Mobile UI Kommissionierprofil
-- Column: PickingProfile_PickingJobConfig.MobileUI_UserProfile_Picking_ID
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> Mobile UI Kommissionierprofil
-- Column: PickingProfile_PickingJobConfig.MobileUI_UserProfile_Picking_ID
-- 2024-01-26T11:16:27.154Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587874,723854,0,547360,TO_TIMESTAMP('2024-01-26 13:16:27','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.picking','Y','N','N','N','N','N','N','N','Mobile UI Kommissionierprofil',TO_TIMESTAMP('2024-01-26 13:16:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:16:27.155Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723854 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:16:27.156Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582765) 
;

-- 2024-01-26T11:16:27.159Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723854
;

-- 2024-01-26T11:16:27.160Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723854)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Job Config -> Eintragsfeld
-- Column: PickingProfile_PickingJobConfig.PickingJobField
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> Eintragsfeld
-- Column: PickingProfile_PickingJobConfig.PickingJobField
-- 2024-01-26T11:16:27.251Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587875,723855,0,547360,TO_TIMESTAMP('2024-01-26 13:16:27','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.picking','Y','N','N','N','N','N','N','N','Eintragsfeld',TO_TIMESTAMP('2024-01-26 13:16:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:16:27.252Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723855 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:16:27.253Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582929) 
;

-- 2024-01-26T11:16:27.262Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723855
;

-- 2024-01-26T11:16:27.262Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723855)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Job Config -> Display in summary
-- Column: PickingProfile_PickingJobConfig.IsDisplayInSummary
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> Display in summary
-- Column: PickingProfile_PickingJobConfig.IsDisplayInSummary
-- 2024-01-26T11:16:27.348Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587876,723856,0,547360,TO_TIMESTAMP('2024-01-26 13:16:27','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.picking','Y','N','N','N','N','N','N','N','Display in summary',TO_TIMESTAMP('2024-01-26 13:16:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:16:27.348Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723856 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:16:27.349Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582930) 
;

-- 2024-01-26T11:16:27.352Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723856
;

-- 2024-01-26T11:16:27.353Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723856)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Job Config -> Display in detailed
-- Column: PickingProfile_PickingJobConfig.IsDisplayInDetailed
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> Display in detailed
-- Column: PickingProfile_PickingJobConfig.IsDisplayInDetailed
-- 2024-01-26T11:16:27.425Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587877,723857,0,547360,TO_TIMESTAMP('2024-01-26 13:16:27','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.picking','Y','N','N','N','N','N','N','N','Display in detailed',TO_TIMESTAMP('2024-01-26 13:16:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:16:27.425Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723857 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:16:27.426Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582931) 
;

-- 2024-01-26T11:16:27.429Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723857
;

-- 2024-01-26T11:16:27.430Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723857)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Filter -> Mandant
-- Column: PickingProfile_Filter.AD_Client_ID
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Filter(547359,de.metas.picking) -> Mandant
-- Column: PickingProfile_Filter.AD_Client_ID
-- 2024-01-26T11:18:24.645Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587856,723858,0,547359,TO_TIMESTAMP('2024-01-26 13:18:24','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.picking','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-01-26 13:18:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:18:24.648Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723858 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:18:24.651Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-01-26T11:18:24.757Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723858
;

-- 2024-01-26T11:18:24.764Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723858)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Filter -> Sektion
-- Column: PickingProfile_Filter.AD_Org_ID
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Filter(547359,de.metas.picking) -> Sektion
-- Column: PickingProfile_Filter.AD_Org_ID
-- 2024-01-26T11:18:24.870Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587857,723859,0,547359,TO_TIMESTAMP('2024-01-26 13:18:24','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.picking','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-01-26 13:18:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:18:24.871Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723859 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:18:24.872Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-01-26T11:18:24.952Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723859
;

-- 2024-01-26T11:18:24.953Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723859)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Filter -> Aktiv
-- Column: PickingProfile_Filter.IsActive
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Filter(547359,de.metas.picking) -> Aktiv
-- Column: PickingProfile_Filter.IsActive
-- 2024-01-26T11:18:25.077Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587860,723860,0,547359,TO_TIMESTAMP('2024-01-26 13:18:24','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.picking','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-01-26 13:18:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:18:25.077Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723860 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:18:25.079Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-01-26T11:18:25.173Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723860
;

-- 2024-01-26T11:18:25.174Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723860)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Filter -> Picking Filter
-- Column: PickingProfile_Filter.PickingProfile_Filter_ID
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Filter(547359,de.metas.picking) -> Picking Filter
-- Column: PickingProfile_Filter.PickingProfile_Filter_ID
-- 2024-01-26T11:18:25.269Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587863,723861,0,547359,TO_TIMESTAMP('2024-01-26 13:18:25','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.picking','Y','N','N','N','N','N','N','N','Picking Filter',TO_TIMESTAMP('2024-01-26 13:18:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:18:25.270Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723861 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:18:25.271Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582926) 
;

-- 2024-01-26T11:18:25.274Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723861
;

-- 2024-01-26T11:18:25.275Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723861)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Filter -> Mobile UI Kommissionierprofil
-- Column: PickingProfile_Filter.MobileUI_UserProfile_Picking_ID
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Filter(547359,de.metas.picking) -> Mobile UI Kommissionierprofil
-- Column: PickingProfile_Filter.MobileUI_UserProfile_Picking_ID
-- 2024-01-26T11:18:25.370Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587864,723862,0,547359,TO_TIMESTAMP('2024-01-26 13:18:25','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.picking','Y','N','N','N','N','N','N','N','Mobile UI Kommissionierprofil',TO_TIMESTAMP('2024-01-26 13:18:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:18:25.371Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723862 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:18:25.372Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582765) 
;

-- 2024-01-26T11:18:25.375Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723862
;

-- 2024-01-26T11:18:25.376Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723862)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Filter -> Filter Typ
-- Column: PickingProfile_Filter.FilterType
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Filter(547359,de.metas.picking) -> Filter Typ
-- Column: PickingProfile_Filter.FilterType
-- 2024-01-26T11:18:25.460Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587865,723863,0,547359,TO_TIMESTAMP('2024-01-26 13:18:25','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.picking','Y','N','N','N','N','N','N','N','Filter Typ',TO_TIMESTAMP('2024-01-26 13:18:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T11:18:25.460Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723863 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-26T11:18:25.461Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582927) 
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2024 metas GmbH
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

-- 2024-01-26T11:18:25.469Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723863
;

-- 2024-01-26T11:18:25.469Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723863)
;

