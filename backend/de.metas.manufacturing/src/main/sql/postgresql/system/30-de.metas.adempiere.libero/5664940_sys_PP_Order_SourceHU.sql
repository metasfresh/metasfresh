-- Table: PP_Order_SourceHU
-- 2022-11-17T12:10:58.643Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542262,'N',TO_TIMESTAMP('2022-11-17 14:10:57','YYYY-MM-DD HH24:MI:SS'),100,'EE01','N','Y','N','N','Y','N','N','N','N','N',0,'Manufacturing Order Source HU','NP','L','PP_Order_SourceHU','DTI',TO_TIMESTAMP('2022-11-17 14:10:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T12:10:58.646Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542262 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-11-17T12:10:58.776Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556054,TO_TIMESTAMP('2022-11-17 14:10:58','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table PP_Order_SourceHU',1,'Y','N','Y','Y','PP_Order_SourceHU','N',1000000,TO_TIMESTAMP('2022-11-17 14:10:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:10:58.788Z
CREATE SEQUENCE PP_ORDER_SOURCEHU_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: PP_Order_SourceHU.AD_Client_ID
-- 2022-11-17T12:11:38.092Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585074,102,0,19,542262,'AD_Client_ID',TO_TIMESTAMP('2022-11-17 14:11:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','EE01',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-11-17 14:11:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T12:11:38.095Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585074 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T12:11:38.132Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: PP_Order_SourceHU.AD_Org_ID
-- 2022-11-17T12:11:39.349Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585075,113,0,30,542262,'AD_Org_ID',TO_TIMESTAMP('2022-11-17 14:11:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','EE01',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2022-11-17 14:11:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T12:11:39.350Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585075 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T12:11:39.352Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: PP_Order_SourceHU.Created
-- 2022-11-17T12:11:40.186Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585076,245,0,16,542262,'Created',TO_TIMESTAMP('2022-11-17 14:11:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','EE01',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-11-17 14:11:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T12:11:40.188Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585076 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T12:11:40.190Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: PP_Order_SourceHU.CreatedBy
-- 2022-11-17T12:11:40.975Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585077,246,0,18,110,542262,'CreatedBy',TO_TIMESTAMP('2022-11-17 14:11:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','EE01',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-11-17 14:11:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T12:11:40.977Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585077 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T12:11:40.978Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: PP_Order_SourceHU.IsActive
-- 2022-11-17T12:11:41.774Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585078,348,0,20,542262,'IsActive',TO_TIMESTAMP('2022-11-17 14:11:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','EE01',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-11-17 14:11:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T12:11:41.775Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585078 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T12:11:41.777Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: PP_Order_SourceHU.Updated
-- 2022-11-17T12:11:42.675Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585079,607,0,16,542262,'Updated',TO_TIMESTAMP('2022-11-17 14:11:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','EE01',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-11-17 14:11:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T12:11:42.676Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585079 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T12:11:42.678Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: PP_Order_SourceHU.UpdatedBy
-- 2022-11-17T12:11:43.489Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585080,608,0,18,110,542262,'UpdatedBy',TO_TIMESTAMP('2022-11-17 14:11:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','EE01',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-11-17 14:11:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T12:11:43.491Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585080 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T12:11:43.492Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-11-17T12:11:44.121Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581703,0,'PP_Order_SourceHU_ID',TO_TIMESTAMP('2022-11-17 14:11:44','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Manufacturing Order Source HU','Manufacturing Order Source HU',TO_TIMESTAMP('2022-11-17 14:11:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:11:44.124Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581703 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PP_Order_SourceHU.PP_Order_SourceHU_ID
-- 2022-11-17T12:11:44.698Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585081,581703,0,13,542262,'PP_Order_SourceHU_ID',TO_TIMESTAMP('2022-11-17 14:11:43','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Manufacturing Order Source HU',0,0,TO_TIMESTAMP('2022-11-17 14:11:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T12:11:44.699Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585081 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T12:11:44.701Z
/* DDL */  select update_Column_Translation_From_AD_Element(581703) 
;

-- Column: PP_Order_SourceHU.PP_Order_ID
-- 2022-11-17T12:12:29.108Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,
                       IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,585082,53276,0,30,542262,'PP_Order_ID',TO_TIMESTAMP('2022-11-17 14:12:28','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y',
        'N','N','N','N','N','N','N','N',0,'Produktionsauftrag',0,0,TO_TIMESTAMP('2022-11-17 14:12:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T12:12:29.109Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585082 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T12:12:29.111Z
/* DDL */  select update_Column_Translation_From_AD_Element(53276) 
;

-- Column: PP_Order_SourceHU.M_HU_ID
-- 2022-11-17T12:12:46.734Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,
                       IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,585083,542140,0,30,542262,'M_HU_ID',TO_TIMESTAMP('2022-11-17 14:12:46','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y',
        'N','N','N','N','N','N','N','N',0,'Handling Unit',0,0,TO_TIMESTAMP('2022-11-17 14:12:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T12:12:46.737Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585083 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T12:12:46.744Z
/* DDL */  select update_Column_Translation_From_AD_Element(542140) 
;

-- 2022-11-17T12:13:34.022Z
/* DDL */ CREATE TABLE public.PP_Order_SourceHU (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_HU_ID NUMERIC(10) NOT NULL, PP_Order_ID NUMERIC(10) NOT NULL, PP_Order_SourceHU_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MHU_PPOrderSourceHU FOREIGN KEY (M_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED, CONSTRAINT PPOrder_PPOrderSourceHU FOREIGN KEY (PP_Order_ID) REFERENCES public.PP_Order DEFERRABLE INITIALLY DEFERRED, CONSTRAINT PP_Order_SourceHU_Key PRIMARY KEY (PP_Order_SourceHU_ID))
;

-- Window: Manufacturing Order Source HU, InternalName=null
-- 2022-11-17T12:18:00.384Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority)
VALUES (0,581703,0,541633,TO_TIMESTAMP('2022-11-17 14:18:00','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','N','N','N','N','N','Y','Manufacturing Order Source HU','N',TO_TIMESTAMP('2022-11-17 14:18:00','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-11-17T12:18:00.395Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541633 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-11-17T12:18:00.407Z
/* DDL */  select update_window_translation_from_ad_element(581703) 
;

-- 2022-11-17T12:18:00.420Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541633
;

-- 2022-11-17T12:18:00.426Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541633)
;

-- Tab: Manufacturing Order Source HU(541633,EE01) -> Manufacturing Order Source HU
-- Table: PP_Order_SourceHU
-- 2022-11-17T12:20:42.035Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,
                    IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,
                    IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (0,581703,0,546678,542262,541633,'Y',TO_TIMESTAMP('2022-11-17 14:20:41','YYYY-MM-DD HH24:MI:SS'),100,'EE01','N','N','A','PP_Order_SourceHU','Y','N',
        'Y','N','N','N','N',
        'Y','N','N','N','Y','Y','N','N','N',0,'Manufacturing Order Source HU','N',10,0,TO_TIMESTAMP('2022-11-17 14:20:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:20:42.039Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546678 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-17T12:20:42.043Z
/* DDL */  select update_tab_translation_from_ad_element(581703) 
;

-- 2022-11-17T12:20:42.051Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546678)
;

-- Field: Manufacturing Order Source HU(541633,EE01) -> Manufacturing Order Source HU(546678,EE01) -> Mandant
-- Column: PP_Order_SourceHU.AD_Client_ID
-- 2022-11-17T12:20:55.782Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585074,708119,0,546678,TO_TIMESTAMP('2022-11-17 14:20:55','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-11-17 14:20:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:20:55.786Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708119 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-17T12:20:55.790Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-11-17T12:20:57.172Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708119
;

-- 2022-11-17T12:20:57.173Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708119)
;

-- Field: Manufacturing Order Source HU(541633,EE01) -> Manufacturing Order Source HU(546678,EE01) -> Organisation
-- Column: PP_Order_SourceHU.AD_Org_ID
-- 2022-11-17T12:20:57.290Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585075,708120,0,546678,TO_TIMESTAMP('2022-11-17 14:20:57','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-11-17 14:20:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:20:57.291Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708120 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-17T12:20:57.292Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-11-17T12:20:57.804Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708120
;

-- 2022-11-17T12:20:57.805Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708120)
;

-- Field: Manufacturing Order Source HU(541633,EE01) -> Manufacturing Order Source HU(546678,EE01) -> Aktiv
-- Column: PP_Order_SourceHU.IsActive
-- 2022-11-17T12:20:57.909Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585078,708121,0,546678,TO_TIMESTAMP('2022-11-17 14:20:57','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-11-17 14:20:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:20:57.910Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708121 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-17T12:20:57.911Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-11-17T12:20:58.857Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708121
;

-- 2022-11-17T12:20:58.857Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708121)
;

-- Field: Manufacturing Order Source HU(541633,EE01) -> Manufacturing Order Source HU(546678,EE01) -> Manufacturing Order Source HU
-- Column: PP_Order_SourceHU.PP_Order_SourceHU_ID
-- 2022-11-17T12:20:58.963Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585081,708122,0,546678,TO_TIMESTAMP('2022-11-17 14:20:58','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Manufacturing Order Source HU',TO_TIMESTAMP('2022-11-17 14:20:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:20:58.964Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708122 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-17T12:20:58.965Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581703) 
;

-- 2022-11-17T12:20:58.968Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708122
;

-- 2022-11-17T12:20:58.969Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708122)
;

-- Field: Manufacturing Order Source HU(541633,EE01) -> Manufacturing Order Source HU(546678,EE01) -> Produktionsauftrag
-- Column: PP_Order_SourceHU.PP_Order_ID
-- 2022-11-17T12:20:59.083Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585082,708123,0,546678,TO_TIMESTAMP('2022-11-17 14:20:58','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Produktionsauftrag',TO_TIMESTAMP('2022-11-17 14:20:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:20:59.084Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708123 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-17T12:20:59.085Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53276) 
;

-- 2022-11-17T12:20:59.098Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708123
;

-- 2022-11-17T12:20:59.098Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708123)
;

-- Field: Manufacturing Order Source HU(541633,EE01) -> Manufacturing Order Source HU(546678,EE01) -> Handling Unit
-- Column: PP_Order_SourceHU.M_HU_ID
-- 2022-11-17T12:20:59.188Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585083,708124,0,546678,TO_TIMESTAMP('2022-11-17 14:20:59','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Handling Unit',TO_TIMESTAMP('2022-11-17 14:20:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:20:59.188Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708124 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-17T12:20:59.189Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542140) 
;

-- 2022-11-17T12:20:59.209Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708124
;

-- 2022-11-17T12:20:59.209Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708124)
;

-- Tab: Manufacturing Order Source HU(541633,EE01) -> Manufacturing Order Source HU(546678,EE01)
-- UI Section: main
-- 2022-11-17T12:21:13.802Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546678,545301,TO_TIMESTAMP('2022-11-17 14:21:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-17 14:21:13','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-11-17T12:21:13.804Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545301 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Manufacturing Order Source HU(541633,EE01) -> Manufacturing Order Source HU(546678,EE01) -> main
-- UI Column: 10
-- 2022-11-17T12:21:13.963Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546456,545301,TO_TIMESTAMP('2022-11-17 14:21:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-17 14:21:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Manufacturing Order Source HU(541633,EE01) -> Manufacturing Order Source HU(546678,EE01) -> main
-- UI Column: 20
-- 2022-11-17T12:21:14.058Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546457,545301,TO_TIMESTAMP('2022-11-17 14:21:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-17 14:21:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Manufacturing Order Source HU(541633,EE01) -> Manufacturing Order Source HU(546678,EE01) -> main -> 10
-- UI Element Group: default
-- 2022-11-17T12:21:14.223Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546456,550034,TO_TIMESTAMP('2022-11-17 14:21:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-11-17 14:21:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Manufacturing Order Source HU(541633,EE01) -> Manufacturing Order Source HU(546678,EE01) -> main -> 10 -> default.Produktionsauftrag
-- Column: PP_Order_SourceHU.PP_Order_ID
-- 2022-11-17T12:21:44.957Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708123,0,546678,613521,550034,'F',TO_TIMESTAMP('2022-11-17 14:21:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produktionsauftrag',10,0,0,TO_TIMESTAMP('2022-11-17 14:21:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Manufacturing Order Source HU(541633,EE01) -> Manufacturing Order Source HU(546678,EE01) -> main -> 10 -> default.Handling Unit
-- Column: PP_Order_SourceHU.M_HU_ID
-- 2022-11-17T12:21:51.652Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708124,0,546678,613522,550034,'F',TO_TIMESTAMP('2022-11-17 14:21:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Handling Unit',20,0,0,TO_TIMESTAMP('2022-11-17 14:21:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: PP_Order_SourceHU.PP_Order_ID
-- 2022-11-17T12:31:51.026Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-11-17 14:31:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585082
;

-- Column: PP_Order_SourceHU.M_HU_ID
-- 2022-11-17T12:33:54.358Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-11-17 14:33:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585083
;


















-- Element: PP_Order_SourceHU_ID
-- 2022-11-17T13:38:46.499Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produktionsauftrag Quell-HU', PrintName='Produktionsauftrag Quell-HU',Updated=TO_TIMESTAMP('2022-11-17 15:38:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581703 AND AD_Language='de_CH'
;

-- 2022-11-17T13:38:46.528Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581703,'de_CH') 
;

-- Element: PP_Order_SourceHU_ID
-- 2022-11-17T13:38:51.309Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produktionsauftrag Quell-HU', PrintName='Produktionsauftrag Quell-HU',Updated=TO_TIMESTAMP('2022-11-17 15:38:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581703 AND AD_Language='de_DE'
;

-- 2022-11-17T13:38:51.310Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581703,'de_DE') 
;

-- 2022-11-17T13:38:51.320Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581703,'de_DE') 
;

-- 2022-11-17T13:38:51.321Z
UPDATE AD_Column SET ColumnName='PP_Order_SourceHU_ID', Name='Produktionsauftrag Quell-HU', Description=NULL, Help=NULL WHERE AD_Element_ID=581703
;

-- 2022-11-17T13:38:51.323Z
UPDATE AD_Process_Para SET ColumnName='PP_Order_SourceHU_ID', Name='Produktionsauftrag Quell-HU', Description=NULL, Help=NULL, AD_Element_ID=581703 WHERE UPPER(ColumnName)='PP_ORDER_SOURCEHU_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-17T13:38:51.324Z
UPDATE AD_Process_Para SET ColumnName='PP_Order_SourceHU_ID', Name='Produktionsauftrag Quell-HU', Description=NULL, Help=NULL WHERE AD_Element_ID=581703 AND IsCentrallyMaintained='Y'
;

-- 2022-11-17T13:38:51.325Z
UPDATE AD_Field SET Name='Produktionsauftrag Quell-HU', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581703) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581703)
;

-- 2022-11-17T13:38:51.338Z
UPDATE AD_PrintFormatItem pi SET PrintName='Produktionsauftrag Quell-HU', Name='Produktionsauftrag Quell-HU' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581703)
;

-- 2022-11-17T13:38:51.340Z
UPDATE AD_Tab SET Name='Produktionsauftrag Quell-HU', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581703
;

-- 2022-11-17T13:38:51.341Z
UPDATE AD_WINDOW SET Name='Produktionsauftrag Quell-HU', Description=NULL, Help=NULL WHERE AD_Element_ID = 581703
;

-- 2022-11-17T13:38:51.343Z
UPDATE AD_Menu SET   Name = 'Produktionsauftrag Quell-HU', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581703
;

-- Element: PP_Order_SourceHU_ID
-- 2022-11-17T13:38:53.215Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-17 15:38:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581703 AND AD_Language='en_US'
;

-- 2022-11-17T13:38:53.216Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581703,'en_US') 
;

-- Table: PP_Order_SourceHU
-- 2022-11-18T11:03:14.922Z
UPDATE AD_Table SET AD_Window_ID=541633,Updated=TO_TIMESTAMP('2022-11-18 13:03:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542262
;

-- Tab: Produktionsauftrag Quell-HU(541633,EE01) -> Produktionsauftrag Quell-HU
-- Table: PP_Order_SourceHU
-- 2022-11-18T11:03:41.031Z
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-18 13:03:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546678
;

-- Field: Produktionsauftrag Quell-HU(541633,EE01) -> Produktionsauftrag Quell-HU(546678,EE01) -> Organisation
-- Column: PP_Order_SourceHU.AD_Org_ID
-- 2022-11-18T15:40:27.859Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-18 17:40:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708120
;

-- Field: Produktionsauftrag Quell-HU(541633,EE01) -> Produktionsauftrag Quell-HU(546678,EE01) -> Aktiv
-- Column: PP_Order_SourceHU.IsActive
-- 2022-11-18T15:40:29.341Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-18 17:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708121
;

-- Field: Produktionsauftrag Quell-HU(541633,EE01) -> Produktionsauftrag Quell-HU(546678,EE01) -> Produktionsauftrag Quell-HU
-- Column: PP_Order_SourceHU.PP_Order_SourceHU_ID
-- 2022-11-18T15:40:30.483Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-18 17:40:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708122
;

-- Field: Produktionsauftrag Quell-HU(541633,EE01) -> Produktionsauftrag Quell-HU(546678,EE01) -> Produktionsauftrag
-- Column: PP_Order_SourceHU.PP_Order_ID
-- 2022-11-18T15:40:31.488Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-18 17:40:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708123
;

-- Field: Produktionsauftrag Quell-HU(541633,EE01) -> Produktionsauftrag Quell-HU(546678,EE01) -> Handling Unit
-- Column: PP_Order_SourceHU.M_HU_ID
-- 2022-11-18T15:40:34.581Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-18 17:40:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708124
;

-- Tab: Produktionsauftrag Quell-HU(541633,EE01) -> Produktionsauftrag Quell-HU
-- Table: PP_Order_SourceHU
-- 2022-11-18T15:40:42.584Z
UPDATE AD_Tab SET IsReadOnly='N',Updated=TO_TIMESTAMP('2022-11-18 17:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546678
;

