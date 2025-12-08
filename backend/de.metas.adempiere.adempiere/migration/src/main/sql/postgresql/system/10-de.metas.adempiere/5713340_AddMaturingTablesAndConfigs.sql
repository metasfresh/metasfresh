-- Table: M_Maturing_Configuration
-- Table: M_Maturing_Configuration
-- 2023-12-13T08:36:22.583Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('4',0,0,0,542383,'N',TO_TIMESTAMP('2023-12-13 10:36:22','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Maturing Configuration ','NP','L','M_Maturing_Configuration','DTI',TO_TIMESTAMP('2023-12-13 10:36:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:36:22.586Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542383 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-12-13T08:36:22.686Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556320,TO_TIMESTAMP('2023-12-13 10:36:22','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_Maturing_Configuration',1,'Y','N','Y','Y','M_Maturing_Configuration','N',1000000,TO_TIMESTAMP('2023-12-13 10:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T08:36:22.694Z
CREATE SEQUENCE M_MATURING_CONFIGURATION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: M_Maturing_Configuration.AD_Client_ID
-- Column: M_Maturing_Configuration.AD_Client_ID
-- 2023-12-13T08:36:50.513Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587730,102,0,19,542383,'AD_Client_ID',TO_TIMESTAMP('2023-12-13 10:36:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-12-13 10:36:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:36:50.515Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587730 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:36:50.540Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_Maturing_Configuration.AD_Org_ID
-- Column: M_Maturing_Configuration.AD_Org_ID
-- 2023-12-13T08:36:51.687Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587731,113,0,30,542383,'AD_Org_ID',TO_TIMESTAMP('2023-12-13 10:36:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2023-12-13 10:36:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:36:51.689Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587731 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:36:51.694Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_Maturing_Configuration.Created
-- Column: M_Maturing_Configuration.Created
-- 2023-12-13T08:36:52.246Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587732,245,0,16,542383,'Created',TO_TIMESTAMP('2023-12-13 10:36:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-12-13 10:36:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:36:52.248Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587732 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:36:52.250Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_Maturing_Configuration.CreatedBy
-- Column: M_Maturing_Configuration.CreatedBy
-- 2023-12-13T08:36:52.758Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587733,246,0,18,110,542383,'CreatedBy',TO_TIMESTAMP('2023-12-13 10:36:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-12-13 10:36:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:36:52.760Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587733 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:36:52.763Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_Maturing_Configuration.IsActive
-- Column: M_Maturing_Configuration.IsActive
-- 2023-12-13T08:36:53.270Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587734,348,0,20,542383,'IsActive',TO_TIMESTAMP('2023-12-13 10:36:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-12-13 10:36:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:36:53.274Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587734 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:36:53.283Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_Maturing_Configuration.Updated
-- Column: M_Maturing_Configuration.Updated
-- 2023-12-13T08:36:53.817Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587735,607,0,16,542383,'Updated',TO_TIMESTAMP('2023-12-13 10:36:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-12-13 10:36:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:36:53.821Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587735 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:36:53.829Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_Maturing_Configuration.UpdatedBy
-- Column: M_Maturing_Configuration.UpdatedBy
-- 2023-12-13T08:36:54.552Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587736,608,0,18,110,542383,'UpdatedBy',TO_TIMESTAMP('2023-12-13 10:36:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-12-13 10:36:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:36:54.556Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587736 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:36:54.561Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-12-13T08:36:54.973Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582856,0,'M_Maturing_Configuration_ID',TO_TIMESTAMP('2023-12-13 10:36:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Maturing Configuration ','Maturing Configuration ',TO_TIMESTAMP('2023-12-13 10:36:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T08:36:54.975Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582856 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Maturing_Configuration.M_Maturing_Configuration_ID
-- Column: M_Maturing_Configuration.M_Maturing_Configuration_ID
-- 2023-12-13T08:36:55.440Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587737,582856,0,13,542383,'M_Maturing_Configuration_ID',TO_TIMESTAMP('2023-12-13 10:36:54','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Maturing Configuration ',0,0,TO_TIMESTAMP('2023-12-13 10:36:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:36:55.441Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587737 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:36:55.443Z
/* DDL */  select update_Column_Translation_From_AD_Element(582856) 
;

-- Column: M_Maturing_Configuration.Name
-- Column: M_Maturing_Configuration.Name
-- 2023-12-13T08:39:44.515Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587738,469,0,10,542383,'Name',TO_TIMESTAMP('2023-12-13 10:39:44','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,60,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2023-12-13 10:39:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:39:44.516Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587738 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:39:44.520Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Column: M_Maturing_Configuration.Description
-- Column: M_Maturing_Configuration.Description
-- 2023-12-13T08:39:55.827Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587739,275,0,10,542383,'Description',TO_TIMESTAMP('2023-12-13 10:39:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2023-12-13 10:39:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:39:55.828Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587739 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:39:55.832Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: M_Maturing_Configuration.Name
-- Column: M_Maturing_Configuration.Name
-- 2023-12-13T08:40:06.692Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-12-13 10:40:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587738
;

-- 2023-12-13T08:40:22.773Z
/* DDL */ CREATE TABLE public.M_Maturing_Configuration (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Maturing_Configuration_ID NUMERIC(10) NOT NULL, Name VARCHAR(60) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT M_Maturing_Configuration_Key PRIMARY KEY (M_Maturing_Configuration_ID))
;

-- Table: M_Maturing_Configuration_Line
-- Table: M_Maturing_Configuration_Line
-- 2023-12-13T08:44:50.681Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('4',0,0,0,542384,'N',TO_TIMESTAMP('2023-12-13 10:44:50','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Maturing Configuration Line','NP','L','M_Maturing_Configuration_Line','DTI',TO_TIMESTAMP('2023-12-13 10:44:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:44:50.682Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542384 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-12-13T08:44:50.763Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556321,TO_TIMESTAMP('2023-12-13 10:44:50','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_Maturing_Configuration_Line',1,'Y','N','Y','Y','M_Maturing_Configuration_Line','N',1000000,TO_TIMESTAMP('2023-12-13 10:44:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T08:44:50.769Z
CREATE SEQUENCE M_MATURING_CONFIGURATION_LINE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: M_Maturing_Configuration_Line.AD_Client_ID
-- Column: M_Maturing_Configuration_Line.AD_Client_ID
-- 2023-12-13T08:44:58.603Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587740,102,0,19,542384,'AD_Client_ID',TO_TIMESTAMP('2023-12-13 10:44:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-12-13 10:44:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:44:58.607Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587740 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:44:58.611Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_Maturing_Configuration_Line.AD_Org_ID
-- Column: M_Maturing_Configuration_Line.AD_Org_ID
-- 2023-12-13T08:44:59.333Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587741,113,0,30,542384,'AD_Org_ID',TO_TIMESTAMP('2023-12-13 10:44:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2023-12-13 10:44:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:44:59.335Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587741 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:44:59.337Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_Maturing_Configuration_Line.Created
-- Column: M_Maturing_Configuration_Line.Created
-- 2023-12-13T08:44:59.849Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587742,245,0,16,542384,'Created',TO_TIMESTAMP('2023-12-13 10:44:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-12-13 10:44:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:44:59.850Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587742 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:44:59.853Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_Maturing_Configuration_Line.CreatedBy
-- Column: M_Maturing_Configuration_Line.CreatedBy
-- 2023-12-13T08:45:00.370Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587743,246,0,18,110,542384,'CreatedBy',TO_TIMESTAMP('2023-12-13 10:45:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-12-13 10:45:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:45:00.371Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587743 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:45:00.374Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_Maturing_Configuration_Line.IsActive
-- Column: M_Maturing_Configuration_Line.IsActive
-- 2023-12-13T08:45:00.890Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587744,348,0,20,542384,'IsActive',TO_TIMESTAMP('2023-12-13 10:45:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-12-13 10:45:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:45:00.893Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587744 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:45:00.899Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_Maturing_Configuration_Line.Updated
-- Column: M_Maturing_Configuration_Line.Updated
-- 2023-12-13T08:45:01.444Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587745,607,0,16,542384,'Updated',TO_TIMESTAMP('2023-12-13 10:45:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-12-13 10:45:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:45:01.446Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587745 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:45:01.449Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_Maturing_Configuration_Line.UpdatedBy
-- Column: M_Maturing_Configuration_Line.UpdatedBy
-- 2023-12-13T08:45:01.998Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587746,608,0,18,110,542384,'UpdatedBy',TO_TIMESTAMP('2023-12-13 10:45:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-12-13 10:45:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:45:01.999Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587746 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:45:02.001Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-12-13T08:45:02.393Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582857,0,'M_Maturing_Configuration_Line_ID',TO_TIMESTAMP('2023-12-13 10:45:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Maturing Configuration Line','Maturing Configuration Line',TO_TIMESTAMP('2023-12-13 10:45:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T08:45:02.397Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582857 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Maturing_Configuration_Line.M_Maturing_Configuration_Line_ID
-- Column: M_Maturing_Configuration_Line.M_Maturing_Configuration_Line_ID
-- 2023-12-13T08:45:02.908Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587747,582857,0,13,542384,'M_Maturing_Configuration_Line_ID',TO_TIMESTAMP('2023-12-13 10:45:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Maturing Configuration Line',0,0,TO_TIMESTAMP('2023-12-13 10:45:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:45:02.911Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587747 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:45:02.915Z
/* DDL */  select update_Column_Translation_From_AD_Element(582857) 
;

-- Column: M_Maturing_Configuration_Line.M_Maturing_Configuration_ID
-- Column: M_Maturing_Configuration_Line.M_Maturing_Configuration_ID
-- 2023-12-13T08:45:35.711Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587748,582856,0,19,542384,'M_Maturing_Configuration_ID',TO_TIMESTAMP('2023-12-13 10:45:35','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Maturing Configuration ',0,0,TO_TIMESTAMP('2023-12-13 10:45:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:45:35.713Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587748 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:45:35.715Z
/* DDL */  select update_Column_Translation_From_AD_Element(582856) 
;

-- 2023-12-13T08:46:41.507Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582858,0,'MaturityAge',TO_TIMESTAMP('2023-12-13 10:46:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Maturity Age','Maturity Age',TO_TIMESTAMP('2023-12-13 10:46:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T08:46:41.509Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582858 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Maturing_Configuration_Line.MaturityAge
-- Column: M_Maturing_Configuration_Line.MaturityAge
-- 2023-12-13T08:46:59.781Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587749,582858,0,22,542384,'MaturityAge',TO_TIMESTAMP('2023-12-13 10:46:59','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Maturity Age',0,0,TO_TIMESTAMP('2023-12-13 10:46:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:46:59.783Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587749 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:46:59.786Z
/* DDL */  select update_Column_Translation_From_AD_Element(582858) 
;

-- 2023-12-13T08:48:15.785Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582859,0,'Matured_Product_ID',TO_TIMESTAMP('2023-12-13 10:48:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Matured Product','Matured Product',TO_TIMESTAMP('2023-12-13 10:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T08:48:15.787Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582859 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Maturing_Configuration_Line.Matured_Product_ID
-- Column: M_Maturing_Configuration_Line.Matured_Product_ID
-- 2023-12-13T08:48:56.486Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587750,582859,0,30,540272,542384,540377,'Matured_Product_ID',TO_TIMESTAMP('2023-12-13 10:48:56','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Matured Product',0,0,TO_TIMESTAMP('2023-12-13 10:48:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:48:56.488Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587750 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:48:56.491Z
/* DDL */  select update_Column_Translation_From_AD_Element(582859) 
;

-- 2023-12-13T08:49:32.453Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582860,0,'From_Product_ID',TO_TIMESTAMP('2023-12-13 10:49:32','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','From Product','From Product',TO_TIMESTAMP('2023-12-13 10:49:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T08:49:32.456Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582860 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- 2023-12-13T08:49:55.668Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587751,582860,0,30,540272,542384,540377,'From_Product_ID',TO_TIMESTAMP('2023-12-13 10:49:55','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'From Product',0,0,TO_TIMESTAMP('2023-12-13 10:49:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T08:49:55.670Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587751 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T08:49:55.674Z
/* DDL */  select update_Column_Translation_From_AD_Element(582860) 
;

-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- 2023-12-13T08:50:03.458Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-12-13 10:50:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587751
;

-- Column: M_Maturing_Configuration_Line.Matured_Product_ID
-- Column: M_Maturing_Configuration_Line.Matured_Product_ID
-- 2023-12-13T08:50:11.996Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-12-13 10:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587750
;

-- 2023-12-13T08:50:14.903Z
/* DDL */ CREATE TABLE public.M_Maturing_Configuration_Line (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, From_Product_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Matured_Product_ID NUMERIC(10) NOT NULL, MaturityAge NUMERIC DEFAULT 0 NOT NULL, M_Maturing_Configuration_ID NUMERIC(10) NOT NULL, M_Maturing_Configuration_Line_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT FromProduct_MMaturingConfigurationLine FOREIGN KEY (From_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MaturedProduct_MMaturingConfigurationLine FOREIGN KEY (Matured_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MMaturingConfiguration_MMaturingConfigurationLine FOREIGN KEY (M_Maturing_Configuration_ID) REFERENCES public.M_Maturing_Configuration DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_Maturing_Configuration_Line_Key PRIMARY KEY (M_Maturing_Configuration_Line_ID))
;

-- 2023-12-13T09:13:50.425Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540781,0,542384,TO_TIMESTAMP('2023-12-13 11:13:50','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','Matured Product Unique','N',TO_TIMESTAMP('2023-12-13 11:13:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:13:50.426Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540781 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-12-13T09:13:53.483Z
UPDATE AD_Index_Table SET EntityType='D',Updated=TO_TIMESTAMP('2023-12-13 11:13:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540781
;

-- 2023-12-13T09:14:24.105Z
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y''',Updated=TO_TIMESTAMP('2023-12-13 11:14:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540781
;

-- 2023-12-13T09:14:36.614Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587750,541384,540781,0,TO_TIMESTAMP('2023-12-13 11:14:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2023-12-13 11:14:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:15:11.912Z
UPDATE AD_Index_Table SET Name='Matured_Product_Unique',Updated=TO_TIMESTAMP('2023-12-13 11:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540781
;

-- 2023-12-13T09:15:13.450Z
CREATE INDEX Matured_Product_Unique ON M_Maturing_Configuration_Line (Matured_Product_ID) WHERE IsActive='Y'
;

-- 2023-12-13T09:15:46.266Z
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2023-12-13 11:15:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540781
;

-- 2023-12-13T09:15:47.541Z
DROP INDEX IF EXISTS matured_product_unique
;

-- 2023-12-13T09:15:47.544Z
CREATE UNIQUE INDEX Matured_Product_Unique ON M_Maturing_Configuration_Line (Matured_Product_ID) WHERE IsActive='Y'
;

-- 2023-12-13T09:17:51.710Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582861,0,TO_TIMESTAMP('2023-12-13 11:17:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Maturing Configuration','Maturing Configuration',TO_TIMESTAMP('2023-12-13 11:17:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:17:51.712Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582861 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-12-13T09:17:59.612Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reife Konfiguration', PrintName='Reife Konfiguration',Updated=TO_TIMESTAMP('2023-12-13 11:17:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582861 AND AD_Language='de_CH'
;

-- 2023-12-13T09:17:59.615Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582861,'de_CH') 
;

-- Element: null
-- 2023-12-13T09:18:04.393Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reife Konfiguration', PrintName='Reife Konfiguration',Updated=TO_TIMESTAMP('2023-12-13 11:18:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582861 AND AD_Language='de_DE'
;

-- 2023-12-13T09:18:04.394Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582861,'de_DE') 
;

-- 2023-12-13T09:18:04.396Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582861,'de_DE') 
;

-- Window: Reife Konfiguration, InternalName=null
-- Window: Reife Konfiguration, InternalName=null
-- 2023-12-13T09:18:16.623Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582861,0,541755,TO_TIMESTAMP('2023-12-13 11:18:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Reife Konfiguration','N',TO_TIMESTAMP('2023-12-13 11:18:16','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-12-13T09:18:16.624Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541755 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-12-13T09:18:16.627Z
/* DDL */  select update_window_translation_from_ad_element(582861) 
;

-- 2023-12-13T09:18:16.636Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541755
;

-- 2023-12-13T09:18:16.639Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541755)
;

-- Tab: Reife Konfiguration -> Maturing Configuration
-- Table: M_Maturing_Configuration
-- Tab: Reife Konfiguration(541755,D) -> Maturing Configuration
-- Table: M_Maturing_Configuration
-- 2023-12-13T09:18:27.842Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582856,0,547343,542383,541755,'Y',TO_TIMESTAMP('2023-12-13 11:18:27','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_Maturing_Configuration','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Maturing Configuration ','N',10,0,TO_TIMESTAMP('2023-12-13 11:18:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:18:27.844Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547343 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-12-13T09:18:27.846Z
/* DDL */  select update_tab_translation_from_ad_element(582856) 
;

-- 2023-12-13T09:18:27.850Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547343)
;

-- Field: Reife Konfiguration -> Maturing Configuration -> Mandant
-- Column: M_Maturing_Configuration.AD_Client_ID
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> Mandant
-- Column: M_Maturing_Configuration.AD_Client_ID
-- 2023-12-13T09:18:34.557Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587730,723202,0,547343,TO_TIMESTAMP('2023-12-13 11:18:34','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-12-13 11:18:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:18:34.559Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723202 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:18:34.561Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-12-13T09:18:34.924Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723202
;

-- 2023-12-13T09:18:34.925Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723202)
;

-- Field: Reife Konfiguration -> Maturing Configuration -> Sektion
-- Column: M_Maturing_Configuration.AD_Org_ID
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> Sektion
-- Column: M_Maturing_Configuration.AD_Org_ID
-- 2023-12-13T09:18:35.027Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587731,723203,0,547343,TO_TIMESTAMP('2023-12-13 11:18:34','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-12-13 11:18:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:18:35.028Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723203 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:18:35.029Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-12-13T09:18:35.162Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723203
;

-- 2023-12-13T09:18:35.164Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723203)
;

-- Field: Reife Konfiguration -> Maturing Configuration -> Aktiv
-- Column: M_Maturing_Configuration.IsActive
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> Aktiv
-- Column: M_Maturing_Configuration.IsActive
-- 2023-12-13T09:18:35.274Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587734,723204,0,547343,TO_TIMESTAMP('2023-12-13 11:18:35','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-12-13 11:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:18:35.278Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723204 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:18:35.283Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-12-13T09:18:35.452Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723204
;

-- 2023-12-13T09:18:35.454Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723204)
;

-- Field: Reife Konfiguration -> Maturing Configuration -> Maturing Configuration
-- Column: M_Maturing_Configuration.M_Maturing_Configuration_ID
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> Maturing Configuration
-- Column: M_Maturing_Configuration.M_Maturing_Configuration_ID
-- 2023-12-13T09:18:35.552Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587737,723205,0,547343,TO_TIMESTAMP('2023-12-13 11:18:35','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Maturing Configuration ',TO_TIMESTAMP('2023-12-13 11:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:18:35.553Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723205 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:18:35.554Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582856) 
;

-- 2023-12-13T09:18:35.556Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723205
;

-- 2023-12-13T09:18:35.557Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723205)
;

-- Field: Reife Konfiguration -> Maturing Configuration -> Name
-- Column: M_Maturing_Configuration.Name
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> Name
-- Column: M_Maturing_Configuration.Name
-- 2023-12-13T09:18:35.647Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587738,723206,0,547343,TO_TIMESTAMP('2023-12-13 11:18:35','YYYY-MM-DD HH24:MI:SS'),100,'',60,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-12-13 11:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:18:35.649Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723206 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:18:35.650Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2023-12-13T09:18:35.700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723206
;

-- 2023-12-13T09:18:35.702Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723206)
;

-- Field: Reife Konfiguration -> Maturing Configuration -> Beschreibung
-- Column: M_Maturing_Configuration.Description
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> Beschreibung
-- Column: M_Maturing_Configuration.Description
-- 2023-12-13T09:18:35.821Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587739,723207,0,547343,TO_TIMESTAMP('2023-12-13 11:18:35','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2023-12-13 11:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:18:35.823Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723207 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:18:35.824Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2023-12-13T09:18:35.875Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723207
;

-- 2023-12-13T09:18:35.877Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723207)
;

-- Tab: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D)
-- UI Section: main
-- 2023-12-13T09:18:47.378Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547343,545927,TO_TIMESTAMP('2023-12-13 11:18:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-12-13 11:18:47','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-12-13T09:18:47.379Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545927 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> main
-- UI Column: 10
-- 2023-12-13T09:18:55.260Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547225,545927,TO_TIMESTAMP('2023-12-13 11:18:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-12-13 11:18:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> main -> 10
-- UI Element Group: maine
-- 2023-12-13T09:19:03.431Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547225,551400,TO_TIMESTAMP('2023-12-13 11:19:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','maine',10,TO_TIMESTAMP('2023-12-13 11:19:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reife Konfiguration -> Maturing Configuration.Name
-- Column: M_Maturing_Configuration.Name
-- UI Element: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> main -> 10 -> maine.Name
-- Column: M_Maturing_Configuration.Name
-- 2023-12-13T09:19:20.060Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723206,0,547343,551400,621982,'F',TO_TIMESTAMP('2023-12-13 11:19:19','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2023-12-13 11:19:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reife Konfiguration -> Maturing Configuration.Beschreibung
-- Column: M_Maturing_Configuration.Description
-- UI Element: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> main -> 10 -> maine.Beschreibung
-- Column: M_Maturing_Configuration.Description
-- 2023-12-13T09:19:34.454Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723207,0,547343,551400,621983,'F',TO_TIMESTAMP('2023-12-13 11:19:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',20,0,0,TO_TIMESTAMP('2023-12-13 11:19:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> main
-- UI Column: 20
-- 2023-12-13T09:19:40.442Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547226,545927,TO_TIMESTAMP('2023-12-13 11:19:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-12-13 11:19:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> main -> 20
-- UI Element Group: second
-- 2023-12-13T09:19:47.404Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547226,551401,TO_TIMESTAMP('2023-12-13 11:19:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','second',10,TO_TIMESTAMP('2023-12-13 11:19:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reife Konfiguration -> Maturing Configuration.Sektion
-- Column: M_Maturing_Configuration.AD_Org_ID
-- UI Element: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> main -> 20 -> second.Sektion
-- Column: M_Maturing_Configuration.AD_Org_ID
-- 2023-12-13T09:20:12.853Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723203,0,547343,551401,621984,'F',TO_TIMESTAMP('2023-12-13 11:20:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2023-12-13 11:20:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reife Konfiguration -> Maturing Configuration.Mandant
-- Column: M_Maturing_Configuration.AD_Client_ID
-- UI Element: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> main -> 20 -> second.Mandant
-- Column: M_Maturing_Configuration.AD_Client_ID
-- 2023-12-13T09:20:21.359Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723202,0,547343,551401,621985,'F',TO_TIMESTAMP('2023-12-13 11:20:21','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2023-12-13 11:20:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reife Konfiguration -> Maturing Configuration.Name
-- Column: M_Maturing_Configuration.Name
-- UI Element: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> main -> 10 -> maine.Name
-- Column: M_Maturing_Configuration.Name
-- 2023-12-13T09:20:36.619Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-12-13 11:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621982
;

-- UI Element: Reife Konfiguration -> Maturing Configuration.Beschreibung
-- Column: M_Maturing_Configuration.Description
-- UI Element: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> main -> 10 -> maine.Beschreibung
-- Column: M_Maturing_Configuration.Description
-- 2023-12-13T09:20:36.627Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-12-13 11:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621983
;

-- UI Element: Reife Konfiguration -> Maturing Configuration.Sektion
-- Column: M_Maturing_Configuration.AD_Org_ID
-- UI Element: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> main -> 20 -> second.Sektion
-- Column: M_Maturing_Configuration.AD_Org_ID
-- 2023-12-13T09:20:36.634Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-12-13 11:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621984
;

-- UI Element: Reife Konfiguration -> Maturing Configuration.Mandant
-- Column: M_Maturing_Configuration.AD_Client_ID
-- UI Element: Reife Konfiguration(541755,D) -> Maturing Configuration (547343,D) -> main -> 20 -> second.Mandant
-- Column: M_Maturing_Configuration.AD_Client_ID
-- 2023-12-13T09:20:36.642Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-12-13 11:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621985
;

-- 2023-12-13T09:21:03.388Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582862,0,TO_TIMESTAMP('2023-12-13 11:21:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Maturing Configuration Line','Maturing Configuration Line',TO_TIMESTAMP('2023-12-13 11:21:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:21:03.390Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582862 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-12-13T09:21:13.128Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reife Konfigurationslinie', PrintName='Reife Konfigurationslinie',Updated=TO_TIMESTAMP('2023-12-13 11:21:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582862 AND AD_Language='de_CH'
;

-- 2023-12-13T09:21:13.130Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582862,'de_CH') 
;

-- Element: null
-- 2023-12-13T09:21:16.921Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reife Konfigurationslinie', PrintName='Reife Konfigurationslinie',Updated=TO_TIMESTAMP('2023-12-13 11:21:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582862 AND AD_Language='de_DE'
;

-- 2023-12-13T09:21:16.923Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582862,'de_DE') 
;

-- 2023-12-13T09:21:16.924Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582862,'de_DE') 
;

-- Tab: Reife Konfiguration -> Maturing Configuration Line
-- Table: M_Maturing_Configuration_Line
-- Tab: Reife Konfiguration(541755,D) -> Maturing Configuration Line
-- Table: M_Maturing_Configuration_Line
-- 2023-12-13T09:21:30.950Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582857,0,547344,542384,541755,'Y',TO_TIMESTAMP('2023-12-13 11:21:30','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_Maturing_Configuration_Line','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Maturing Configuration Line','N',20,0,TO_TIMESTAMP('2023-12-13 11:21:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:21:30.951Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547344 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-12-13T09:21:30.953Z
/* DDL */  select update_tab_translation_from_ad_element(582857) 
;

-- 2023-12-13T09:21:30.956Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547344)
;

-- Field: Reife Konfiguration -> Maturing Configuration Line -> Mandant
-- Column: M_Maturing_Configuration_Line.AD_Client_ID
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration Line(547344,D) -> Mandant
-- Column: M_Maturing_Configuration_Line.AD_Client_ID
-- 2023-12-13T09:21:35.138Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587740,723208,0,547344,TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:21:35.140Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723208 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:21:35.141Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-12-13T09:21:35.199Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723208
;

-- 2023-12-13T09:21:35.201Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723208)
;

-- Field: Reife Konfiguration -> Maturing Configuration Line -> Sektion
-- Column: M_Maturing_Configuration_Line.AD_Org_ID
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration Line(547344,D) -> Sektion
-- Column: M_Maturing_Configuration_Line.AD_Org_ID
-- 2023-12-13T09:21:35.302Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587741,723209,0,547344,TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:21:35.303Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723209 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:21:35.304Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-12-13T09:21:35.357Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723209
;

-- 2023-12-13T09:21:35.358Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723209)
;

-- Field: Reife Konfiguration -> Maturing Configuration Line -> Aktiv
-- Column: M_Maturing_Configuration_Line.IsActive
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration Line(547344,D) -> Aktiv
-- Column: M_Maturing_Configuration_Line.IsActive
-- 2023-12-13T09:21:35.455Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587744,723210,0,547344,TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:21:35.456Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723210 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:21:35.458Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-12-13T09:21:35.511Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723210
;

-- 2023-12-13T09:21:35.513Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723210)
;

-- Field: Reife Konfiguration -> Maturing Configuration Line -> Maturing Configuration Line
-- Column: M_Maturing_Configuration_Line.M_Maturing_Configuration_Line_ID
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration Line(547344,D) -> Maturing Configuration Line
-- Column: M_Maturing_Configuration_Line.M_Maturing_Configuration_Line_ID
-- 2023-12-13T09:21:35.619Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587747,723211,0,547344,TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Maturing Configuration Line',TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:21:35.622Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723211 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:21:35.626Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582857) 
;

-- 2023-12-13T09:21:35.634Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723211
;

-- 2023-12-13T09:21:35.637Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723211)
;

-- Field: Reife Konfiguration -> Maturing Configuration Line -> Maturing Configuration
-- Column: M_Maturing_Configuration_Line.M_Maturing_Configuration_ID
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration Line(547344,D) -> Maturing Configuration
-- Column: M_Maturing_Configuration_Line.M_Maturing_Configuration_ID
-- 2023-12-13T09:21:35.744Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587748,723212,0,547344,TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Maturing Configuration ',TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:21:35.746Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723212 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:21:35.749Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582856) 
;

-- 2023-12-13T09:21:35.753Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723212
;

-- 2023-12-13T09:21:35.754Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723212)
;

-- Field: Reife Konfiguration -> Maturing Configuration Line -> Maturity Age
-- Column: M_Maturing_Configuration_Line.MaturityAge
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration Line(547344,D) -> Maturity Age
-- Column: M_Maturing_Configuration_Line.MaturityAge
-- 2023-12-13T09:21:35.854Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587749,723213,0,547344,TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100,2,'D','Y','N','N','N','N','N','N','N','Maturity Age',TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:21:35.856Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723213 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:21:35.859Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582858) 
;

-- 2023-12-13T09:21:35.866Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723213
;

-- 2023-12-13T09:21:35.868Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723213)
;

-- Field: Reife Konfiguration -> Maturing Configuration Line -> Matured Product
-- Column: M_Maturing_Configuration_Line.Matured_Product_ID
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration Line(547344,D) -> Matured Product
-- Column: M_Maturing_Configuration_Line.Matured_Product_ID
-- 2023-12-13T09:21:35.990Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587750,723214,0,547344,TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Matured Product',TO_TIMESTAMP('2023-12-13 11:21:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:21:35.996Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723214 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:21:36.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582859) 
;

-- 2023-12-13T09:21:36.007Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723214
;

-- 2023-12-13T09:21:36.009Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723214)
;

-- Field: Reife Konfiguration -> Maturing Configuration Line -> From Product
-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- Field: Reife Konfiguration(541755,D) -> Maturing Configuration Line(547344,D) -> From Product
-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- 2023-12-13T09:21:36.130Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587751,723215,0,547344,TO_TIMESTAMP('2023-12-13 11:21:36','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','Y','N','N','N','N','N','N','N','From Product',TO_TIMESTAMP('2023-12-13 11:21:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T09:21:36.131Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723215 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T09:21:36.133Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582860) 
;

-- 2023-12-13T09:21:36.138Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723215
;

-- 2023-12-13T09:21:36.139Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723215)
;

-- Column: PP_Product_Planning.M_Maturing_Configuration_ID
-- Column: PP_Product_Planning.M_Maturing_Configuration_ID
-- 2023-12-13T11:13:31.305Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587752,582856,0,19,53020,'M_Maturing_Configuration_ID',TO_TIMESTAMP('2023-12-13 13:13:31','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maturing Configuration ',0,0,TO_TIMESTAMP('2023-12-13 13:13:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T11:13:31.309Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587752 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T11:13:31.315Z
/* DDL */  select update_Column_Translation_From_AD_Element(582856) 
;

-- 2023-12-13T11:13:33.194Z
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN M_Maturing_Configuration_ID NUMERIC(10)')
;

-- 2023-12-13T11:13:33.308Z
ALTER TABLE PP_Product_Planning ADD CONSTRAINT MMaturingConfiguration_PPProductPlanning FOREIGN KEY (M_Maturing_Configuration_ID) REFERENCES public.M_Maturing_Configuration DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Maturing_Configuration_Line.M_Maturing_Configuration_ID
-- Column: M_Maturing_Configuration_Line.M_Maturing_Configuration_ID
-- 2023-12-13T11:14:39.648Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-12-13 13:14:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587748
;

-- 2023-12-13T11:14:41.215Z
INSERT INTO t_alter_column values('m_maturing_configuration_line','M_Maturing_Configuration_ID','NUMERIC(10)',null,null)
;

-- Table: M_Maturing_Configuration_Line
-- Table: M_Maturing_Configuration_Line
-- 2023-12-13T11:14:52.732Z
UPDATE AD_Table SET AD_Window_ID=541755,Updated=TO_TIMESTAMP('2023-12-13 13:14:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542384
;

-- Table: M_Maturing_Configuration
-- Table: M_Maturing_Configuration
-- 2023-12-13T11:15:19.919Z
UPDATE AD_Table SET AD_Window_ID=541755,Updated=TO_TIMESTAMP('2023-12-13 13:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542383
;

-- Tab: Reife Konfiguration -> Maturing Configuration Line
-- Table: M_Maturing_Configuration_Line
-- Tab: Reife Konfiguration(541755,D) -> Maturing Configuration Line
-- Table: M_Maturing_Configuration_Line
-- 2023-12-13T11:16:59.675Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2023-12-13 13:16:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547344
;

-- Tab: Reife Konfiguration -> Maturing Configuration Line
-- Table: M_Maturing_Configuration_Line
-- Tab: Reife Konfiguration(541755,D) -> Maturing Configuration Line
-- Table: M_Maturing_Configuration_Line
-- 2023-12-13T11:17:05.578Z
UPDATE AD_Tab SET AD_Column_ID=587748,Updated=TO_TIMESTAMP('2023-12-13 13:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547344
;

-- Tab: Reife Konfiguration -> Maturing Configuration Line
-- Table: M_Maturing_Configuration_Line
-- Tab: Reife Konfiguration(541755,D) -> Maturing Configuration Line
-- Table: M_Maturing_Configuration_Line
-- 2023-12-13T11:17:09.003Z
UPDATE AD_Tab SET Parent_Column_ID=587737,Updated=TO_TIMESTAMP('2023-12-13 13:17:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547344
;

-- Column: M_Maturing_Configuration_Line.Matured_Product_ID
-- Column: M_Maturing_Configuration_Line.Matured_Product_ID
-- 2023-12-13T11:18:45.603Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2023-12-13 13:18:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587750
;

-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- 2023-12-13T11:18:58.258Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=2,Updated=TO_TIMESTAMP('2023-12-13 13:18:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587751
;

-- Column: PP_Product_Planning.M_Maturing_Configuration_Line_ID
-- Column: PP_Product_Planning.M_Maturing_Configuration_Line_ID
-- 2023-12-13T11:19:02.652Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587753,582857,0,19,53020,'M_Maturing_Configuration_Line_ID',TO_TIMESTAMP('2023-12-13 13:19:02','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maturing Configuration Line',0,0,TO_TIMESTAMP('2023-12-13 13:19:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T11:19:02.654Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587753 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T11:19:02.657Z
/* DDL */  select update_Column_Translation_From_AD_Element(582857) 
;

-- 2023-12-13T11:19:05.634Z
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN M_Maturing_Configuration_Line_ID NUMERIC(10)')
;

-- 2023-12-13T11:19:05.709Z
ALTER TABLE PP_Product_Planning ADD CONSTRAINT MMaturingConfigurationLine_PPProductPlanning FOREIGN KEY (M_Maturing_Configuration_Line_ID) REFERENCES public.M_Maturing_Configuration_Line DEFERRABLE INITIALLY DEFERRED
;

-- 2023-12-13T11:19:31.577Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582864,0,'IsMatured',TO_TIMESTAMP('2023-12-13 13:19:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Matured','Matured',TO_TIMESTAMP('2023-12-13 13:19:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:19:31.578Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582864 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsMatured
-- 2023-12-13T11:19:45.222Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gereift', PrintName='Gereift',Updated=TO_TIMESTAMP('2023-12-13 13:19:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582864 AND AD_Language='de_CH'
;

-- 2023-12-13T11:19:45.224Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582864,'de_CH') 
;

-- Element: IsMatured
-- 2023-12-13T11:19:48.438Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gereift', PrintName='Gereift',Updated=TO_TIMESTAMP('2023-12-13 13:19:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582864 AND AD_Language='de_DE'
;

-- 2023-12-13T11:19:48.441Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582864,'de_DE') 
;

-- 2023-12-13T11:19:48.443Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582864,'de_DE') 
;

-- Column: PP_Product_Planning.IsMatured
-- Column: PP_Product_Planning.IsMatured
-- 2023-12-13T11:19:58.572Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587754,582864,0,20,53020,'IsMatured',TO_TIMESTAMP('2023-12-13 13:19:58','YYYY-MM-DD HH24:MI:SS'),100,'N','N','EE01',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Gereift',0,0,TO_TIMESTAMP('2023-12-13 13:19:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T11:19:58.574Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587754 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T11:19:58.577Z
/* DDL */  select update_Column_Translation_From_AD_Element(582864) 
;

-- 2023-12-13T11:20:02.653Z
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN IsMatured CHAR(1) DEFAULT ''N'' CHECK (IsMatured IN (''Y'',''N'')) NOT NULL')
;

-- Field: Produkt Plandaten -> Product Planning -> Maturing Configuration
-- Column: PP_Product_Planning.M_Maturing_Configuration_ID
-- Field: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> Maturing Configuration
-- Column: PP_Product_Planning.M_Maturing_Configuration_ID
-- 2023-12-13T11:22:13.275Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587752,723216,0,542102,TO_TIMESTAMP('2023-12-13 13:22:13','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Maturing Configuration ',TO_TIMESTAMP('2023-12-13 13:22:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:22:13.277Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723216 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:22:13.279Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582856) 
;

-- 2023-12-13T11:22:13.282Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723216
;

-- 2023-12-13T11:22:13.284Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723216)
;

-- Field: Produkt Plandaten -> Product Planning -> Maturing Configuration Line
-- Column: PP_Product_Planning.M_Maturing_Configuration_Line_ID
-- Field: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> Maturing Configuration Line
-- Column: PP_Product_Planning.M_Maturing_Configuration_Line_ID
-- 2023-12-13T11:22:13.393Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587753,723217,0,542102,TO_TIMESTAMP('2023-12-13 13:22:13','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Maturing Configuration Line',TO_TIMESTAMP('2023-12-13 13:22:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:22:13.394Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723217 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:22:13.396Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582857) 
;

-- 2023-12-13T11:22:13.398Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723217
;

-- 2023-12-13T11:22:13.399Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723217)
;

-- Field: Produkt Plandaten -> Product Planning -> Gereift
-- Column: PP_Product_Planning.IsMatured
-- Field: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> Gereift
-- Column: PP_Product_Planning.IsMatured
-- 2023-12-13T11:22:13.494Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587754,723218,0,542102,TO_TIMESTAMP('2023-12-13 13:22:13','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Gereift',TO_TIMESTAMP('2023-12-13 13:22:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:22:13.495Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723218 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:22:13.496Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582864) 
;

-- 2023-12-13T11:22:13.499Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723218
;

-- 2023-12-13T11:22:13.500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723218)
;

-- UI Element: Produkt Plandaten -> Product Planning.Gereift
-- Column: PP_Product_Planning.IsMatured
-- UI Element: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> main -> 20 -> flags.Gereift
-- Column: PP_Product_Planning.IsMatured
-- 2023-12-13T11:22:55.297Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723218,0,542102,543143,621986,'F',TO_TIMESTAMP('2023-12-13 13:22:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Gereift',130,0,0,TO_TIMESTAMP('2023-12-13 13:22:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:23:29.794Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582865,0,'IsMaturing',TO_TIMESTAMP('2023-12-13 13:23:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Maturing','Maturing',TO_TIMESTAMP('2023-12-13 13:23:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:23:29.796Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582865 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsMaturing
-- 2023-12-13T11:23:41.358Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reifung', PrintName='Reifung',Updated=TO_TIMESTAMP('2023-12-13 13:23:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582865 AND AD_Language='de_CH'
;

-- 2023-12-13T11:23:41.360Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582865,'de_CH') 
;

-- Element: IsMaturing
-- 2023-12-13T11:23:44.224Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reifung', PrintName='Reifung',Updated=TO_TIMESTAMP('2023-12-13 13:23:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582865 AND AD_Language='de_DE'
;

-- 2023-12-13T11:23:44.226Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582865,'de_DE') 
;

-- 2023-12-13T11:23:44.227Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582865,'de_DE') 
;

-- Column: PP_Order_Candidate.IsMaturing
-- Column: PP_Order_Candidate.IsMaturing
-- 2023-12-13T11:23:53.521Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587755,582865,0,20,541913,'IsMaturing',TO_TIMESTAMP('2023-12-13 13:23:53','YYYY-MM-DD HH24:MI:SS'),100,'N','N','EE01',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reifung',0,0,TO_TIMESTAMP('2023-12-13 13:23:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T11:23:53.523Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587755 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T11:23:53.525Z
/* DDL */  select update_Column_Translation_From_AD_Element(582865) 
;

-- 2023-12-13T11:24:01.418Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Candidate','ALTER TABLE public.PP_Order_Candidate ADD COLUMN IsMaturing CHAR(1) DEFAULT ''N'' CHECK (IsMaturing IN (''Y'',''N'')) NOT NULL')
;

-- UI Element: Produkt Plandaten -> Product Planning.Maturing Configuration
-- Column: PP_Product_Planning.M_Maturing_Configuration_ID
-- UI Element: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> main -> 10 -> default.Maturing Configuration
-- Column: PP_Product_Planning.M_Maturing_Configuration_ID
-- 2023-12-13T11:35:56.700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723216,0,542102,543142,621987,'F',TO_TIMESTAMP('2023-12-13 13:35:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Maturing Configuration ',180,0,0,TO_TIMESTAMP('2023-12-13 13:35:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt Plandaten -> Product Planning.Maturing Configuration Line
-- Column: PP_Product_Planning.M_Maturing_Configuration_Line_ID
-- UI Element: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> main -> 10 -> default.Maturing Configuration Line
-- Column: PP_Product_Planning.M_Maturing_Configuration_Line_ID
-- 2023-12-13T11:36:07.597Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723217,0,542102,543142,621988,'F',TO_TIMESTAMP('2023-12-13 13:36:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Maturing Configuration Line',190,0,0,TO_TIMESTAMP('2023-12-13 13:36:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Produkt Plandaten -> Product Planning -> Maturing Configuration
-- Column: PP_Product_Planning.M_Maturing_Configuration_ID
-- Field: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> Maturing Configuration
-- Column: PP_Product_Planning.M_Maturing_Configuration_ID
-- 2023-12-13T11:36:47.469Z
UPDATE AD_Field SET DisplayLogic='@IsMatured@=''''Y',Updated=TO_TIMESTAMP('2023-12-13 13:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=723216
;

-- Field: Produkt Plandaten -> Product Planning -> Maturing Configuration
-- Column: PP_Product_Planning.M_Maturing_Configuration_ID
-- Field: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> Maturing Configuration
-- Column: PP_Product_Planning.M_Maturing_Configuration_ID
-- 2023-12-13T11:37:22.654Z
UPDATE AD_Field SET DisplayLogic='@IsMatured/''N''@=''Y'' ',Updated=TO_TIMESTAMP('2023-12-13 13:37:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=723216
;

-- Field: Produkt Plandaten -> Product Planning -> Maturing Configuration Line
-- Column: PP_Product_Planning.M_Maturing_Configuration_Line_ID
-- Field: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> Maturing Configuration Line
-- Column: PP_Product_Planning.M_Maturing_Configuration_Line_ID
-- 2023-12-13T11:37:27.517Z
UPDATE AD_Field SET DisplayLogic='@IsMatured/''N''@=''Y'' ',Updated=TO_TIMESTAMP('2023-12-13 13:37:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=723217
;

-- Column: PP_Product_Planning.PP_Product_BOMVersions_ID
-- Column: PP_Product_Planning.PP_Product_BOMVersions_ID
-- 2023-12-13T11:40:21.715Z
UPDATE AD_Column SET MandatoryLogic='@IsManufactured@=''Y'' | @IsMatured/''N''@=''Y'' ',Updated=TO_TIMESTAMP('2023-12-13 13:40:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53397
;

-- Column: PP_Product_Planning.M_Warehouse_ID
-- Column: PP_Product_Planning.M_Warehouse_ID
-- 2023-12-13T11:40:38.331Z
UPDATE AD_Column SET MandatoryLogic='@IsMatured/''N''@=''Y'' ',Updated=TO_TIMESTAMP('2023-12-13 13:40:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53390
;

-- Tab: Produktionsdisposition -> Produktionsdisposition
-- Table: PP_Order_Candidate
-- Tab: Produktionsdisposition(541316,EE01) -> Produktionsdisposition
-- Table: PP_Order_Candidate
-- 2023-12-13T11:42:03.169Z
UPDATE AD_Tab SET WhereClause='PP_Order_Candidate.IsSimulated=''N'' AND PP_Order_Candidate.IsMaturing = ''N''',Updated=TO_TIMESTAMP('2023-12-13 13:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544794
;

-- 2023-12-13T11:42:53.018Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582866,0,TO_TIMESTAMP('2023-12-13 13:42:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Maturing candidate','Maturing candidate',TO_TIMESTAMP('2023-12-13 13:42:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:42:53.020Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582866 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-12-13T11:43:28.823Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reiferdisposition', PrintName='Reiferdisposition',Updated=TO_TIMESTAMP('2023-12-13 13:43:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582866 AND AD_Language='de_CH'
;

-- 2023-12-13T11:43:28.824Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582866,'de_CH') 
;

-- Element: null
-- 2023-12-13T11:43:33.039Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reiferdisposition', PrintName='Reiferdisposition',Updated=TO_TIMESTAMP('2023-12-13 13:43:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582866 AND AD_Language='de_DE'
;

-- 2023-12-13T11:43:33.041Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582866,'de_DE') 
;

-- 2023-12-13T11:43:33.042Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582866,'de_DE') 
;

-- Window: Reiferdisposition, InternalName=null
-- Window: Reiferdisposition, InternalName=null
-- 2023-12-13T11:43:45.705Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582866,0,541756,TO_TIMESTAMP('2023-12-13 13:43:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Reiferdisposition','N',TO_TIMESTAMP('2023-12-13 13:43:45','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-12-13T11:43:45.707Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541756 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-12-13T11:43:45.708Z
/* DDL */  select update_window_translation_from_ad_element(582866) 
;

-- 2023-12-13T11:43:45.710Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541756
;

-- 2023-12-13T11:43:45.711Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541756)
;

-- Window: Reiferdisposition, InternalName=null
-- Window: Reiferdisposition, InternalName=null
-- 2023-12-13T11:44:52.247Z
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='EE01', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='N', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='Y', WindowType='M', WinHeight=0, WinWidth=0, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2023-12-13 13:44:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541756
;

-- 2023-12-13T11:44:52.254Z
DELETE FROM AD_Window_Trl WHERE AD_Window_ID = 541756
;

-- 2023-12-13T11:44:52.255Z
INSERT INTO AD_Window_Trl (AD_Window_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541756, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Window_Trl WHERE AD_Window_ID = 541316
;

-- Tab: Reiferdisposition -> Produktionsdisposition
-- Table: PP_Order_Candidate
-- Tab: Reiferdisposition(541756,EE01) -> Produktionsdisposition
-- Table: PP_Order_Candidate
-- 2023-12-13T11:44:52.382Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,580109,0,547345,541913,541756,'Y',TO_TIMESTAMP('2023-12-13 13:44:52','YYYY-MM-DD HH24:MI:SS'),100,'EE01','N','N','A','PP_Order_Candidate','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','Y','N','N',0,'Produktionsdisposition','N',10,0,TO_TIMESTAMP('2023-12-13 13:44:52','YYYY-MM-DD HH24:MI:SS'),100,'PP_Order_Candidate.IsSimulated=''N'' AND PP_Order_Candidate.IsMaturing = ''N''')
;

-- 2023-12-13T11:44:52.383Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547345 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-12-13T11:44:52.385Z
/* DDL */  select update_tab_translation_from_ad_element(580109) 
;

-- 2023-12-13T11:44:52.389Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547345)
;

-- 2023-12-13T11:44:52.391Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 547345
;

-- 2023-12-13T11:44:52.392Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 547345, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 544794
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Mandant
-- Column: PP_Order_Candidate.AD_Client_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Mandant
-- Column: PP_Order_Candidate.AD_Client_ID
-- 2023-12-13T11:44:52.490Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577868,723219,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:52','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',10,1,1,TO_TIMESTAMP('2023-12-13 13:44:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:52.492Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723219 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:52.493Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-12-13T11:44:52.815Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723219
;

-- 2023-12-13T11:44:52.817Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723219)
;

-- 2023-12-13T11:44:52.819Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723219
;

-- 2023-12-13T11:44:52.820Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723219, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667472
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Sektion
-- Column: PP_Order_Candidate.AD_Org_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Sektion
-- Column: PP_Order_Candidate.AD_Org_ID
-- 2023-12-13T11:44:52.910Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577869,723220,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:52','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','Y','N','Sektion',20,1,1,TO_TIMESTAMP('2023-12-13 13:44:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:52.911Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723220 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:52.912Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-12-13T11:44:53.006Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723220
;

-- 2023-12-13T11:44:53.007Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723220)
;

-- 2023-12-13T11:44:53.010Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723220
;

-- 2023-12-13T11:44:53.010Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723220, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667473
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Aktiv
-- Column: PP_Order_Candidate.IsActive
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Aktiv
-- Column: PP_Order_Candidate.IsActive
-- 2023-12-13T11:44:53.094Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577872,723221,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',30,1,1,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:53.095Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723221 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:53.096Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-12-13T11:44:53.204Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723221
;

-- 2023-12-13T11:44:53.205Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723221)
;

-- 2023-12-13T11:44:53.208Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723221
;

-- 2023-12-13T11:44:53.209Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723221, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667474
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Produktionsdisposition
-- Column: PP_Order_Candidate.PP_Order_Candidate_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Produktionsdisposition
-- Column: PP_Order_Candidate.PP_Order_Candidate_ID
-- 2023-12-13T11:44:53.292Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577875,723222,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01',0,'Y','N','N','N','N','N','N','N','Produktionsdisposition',40,1,1,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:53.293Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723222 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:53.294Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580109) 
;

-- 2023-12-13T11:44:53.297Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723222
;

-- 2023-12-13T11:44:53.298Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723222)
;

-- 2023-12-13T11:44:53.299Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723222
;

-- 2023-12-13T11:44:53.300Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723222, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667475
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Lager
-- Column: PP_Order_Candidate.M_Warehouse_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Lager
-- Column: PP_Order_Candidate.M_Warehouse_ID
-- 2023-12-13T11:44:53.393Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577876,723223,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung',22,'EE01','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','N','N','N','N','N','Y','N','Lager',50,1,1,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:53.397Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723223 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:53.401Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459) 
;

-- 2023-12-13T11:44:53.433Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723223
;

-- 2023-12-13T11:44:53.435Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723223)
;

-- 2023-12-13T11:44:53.439Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723223
;

-- 2023-12-13T11:44:53.440Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723223, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667476
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Ressource
-- Column: PP_Order_Candidate.S_Resource_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Ressource
-- Column: PP_Order_Candidate.S_Resource_ID
-- 2023-12-13T11:44:53.536Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577877,723224,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100,'Ressource',22,'EE01',0,'Y','N','N','N','N','N','N','N','Ressource',60,1,1,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:53.537Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723224 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:53.538Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1777) 
;

-- 2023-12-13T11:44:53.543Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723224
;

-- 2023-12-13T11:44:53.543Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723224)
;

-- 2023-12-13T11:44:53.546Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723224
;

-- 2023-12-13T11:44:53.547Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723224, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667477
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Product Planning
-- Column: PP_Order_Candidate.PP_Product_Planning_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Product Planning
-- Column: PP_Order_Candidate.PP_Product_Planning_ID
-- 2023-12-13T11:44:53.637Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577878,723225,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01',0,'Y','N','N','N','N','N','Y','N','Product Planning',70,1,1,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:53.639Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723225 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:53.640Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53268) 
;

-- 2023-12-13T11:44:53.642Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723225
;

-- 2023-12-13T11:44:53.643Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723225)
;

-- 2023-12-13T11:44:53.645Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723225
;

-- 2023-12-13T11:44:53.645Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723225, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667478
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Stücklistenversion
-- Column: PP_Order_Candidate.PP_Product_BOM_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Stücklistenversion
-- Column: PP_Order_Candidate.PP_Product_BOM_ID
-- 2023-12-13T11:44:53.724Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577879,723226,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100,'Stücklistenversion',22,'EE01',0,'Y','N','N','N','N','N','Y','N','Stücklistenversion',80,1,1,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:53.725Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723226 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:53.726Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53245) 
;

-- 2023-12-13T11:44:53.729Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723226
;

-- 2023-12-13T11:44:53.730Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723226)
;

-- 2023-12-13T11:44:53.732Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723226
;

-- 2023-12-13T11:44:53.732Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723226, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667479
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Produkt
-- Column: PP_Order_Candidate.M_Product_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Produkt
-- Column: PP_Order_Candidate.M_Product_ID
-- 2023-12-13T11:44:53.821Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577880,723227,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',22,'EE01','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','N','N','N','N','N','Y','N','Produkt',90,1,1,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:53.822Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723227 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:53.823Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2023-12-13T11:44:53.842Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723227
;

-- 2023-12-13T11:44:53.843Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723227)
;

-- 2023-12-13T11:44:53.846Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723227
;

-- 2023-12-13T11:44:53.846Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723227, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667480
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Merkmale
-- Column: PP_Order_Candidate.M_AttributeSetInstance_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Merkmale
-- Column: PP_Order_Candidate.M_AttributeSetInstance_ID
-- 2023-12-13T11:44:53.933Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577881,723228,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt',22,'EE01','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','N','N','N','N','N','Y','N','Merkmale',100,1,1,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:53.934Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723228 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:53.936Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019) 
;

-- 2023-12-13T11:44:53.943Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723228
;

-- 2023-12-13T11:44:53.944Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723228)
;

-- 2023-12-13T11:44:53.946Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723228
;

-- 2023-12-13T11:44:53.946Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723228, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667481
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Auftragsposition
-- Column: PP_Order_Candidate.C_OrderLine_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Auftragsposition
-- Column: PP_Order_Candidate.C_OrderLine_ID
-- 2023-12-13T11:44:54.036Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577882,723229,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition',10,'EE01','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.',0,'Y','N','N','N','N','N','Y','N','Auftragsposition',110,1,1,TO_TIMESTAMP('2023-12-13 13:44:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:54.037Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723229 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:54.038Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561) 
;

-- 2023-12-13T11:44:54.046Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723229
;

-- 2023-12-13T11:44:54.047Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723229)
;

-- 2023-12-13T11:44:54.048Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723229
;

-- 2023-12-13T11:44:54.049Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723229, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667482
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Zugesagter Termin
-- Column: PP_Order_Candidate.DatePromised
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Zugesagter Termin
-- Column: PP_Order_Candidate.DatePromised
-- 2023-12-13T11:44:54.136Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577883,723230,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag',7,'EE01','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.',0,'Y','N','N','N','N','N','Y','N','Zugesagter Termin',120,1,1,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:54.137Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723230 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:54.138Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(269) 
;

-- 2023-12-13T11:44:54.142Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723230
;

-- 2023-12-13T11:44:54.143Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723230)
;

-- 2023-12-13T11:44:54.145Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723230
;

-- 2023-12-13T11:44:54.145Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723230, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667483
;

-- Field: Reiferdisposition -> Produktionsdisposition -> geplanter Beginn
-- Column: PP_Order_Candidate.DateStartSchedule
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> geplanter Beginn
-- Column: PP_Order_Candidate.DateStartSchedule
-- 2023-12-13T11:44:54.232Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577884,723231,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100,7,'EE01',0,'Y','N','N','N','N','N','N','N','geplanter Beginn',130,1,1,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:54.233Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723231 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:54.234Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53281) 
;

-- 2023-12-13T11:44:54.237Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723231
;

-- 2023-12-13T11:44:54.238Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723231)
;

-- 2023-12-13T11:44:54.240Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723231
;

-- 2023-12-13T11:44:54.240Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723231, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667484
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Menge
-- Column: PP_Order_Candidate.QtyEntered
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Menge
-- Column: PP_Order_Candidate.QtyEntered
-- 2023-12-13T11:44:54.326Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577885,723232,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit',10,'EE01','Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt',0,'Y','N','N','N','N','N','N','N','Menge',140,1,1,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:54.327Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723232 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:54.328Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2589) 
;

-- 2023-12-13T11:44:54.332Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723232
;

-- 2023-12-13T11:44:54.333Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723232)
;

-- 2023-12-13T11:44:54.335Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723232
;

-- 2023-12-13T11:44:54.336Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723232, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667485
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Maßeinheit
-- Column: PP_Order_Candidate.C_UOM_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Maßeinheit
-- Column: PP_Order_Candidate.C_UOM_ID
-- 2023-12-13T11:44:54.417Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577886,723233,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',22,'EE01','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','N','N','N','N','N','Y','N','Maßeinheit',150,1,1,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:54.418Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723233 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:54.419Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2023-12-13T11:44:54.441Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723233
;

-- 2023-12-13T11:44:54.442Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723233)
;

-- 2023-12-13T11:44:54.444Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723233
;

-- 2023-12-13T11:44:54.445Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723233, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667486
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Verarbeitet
-- Column: PP_Order_Candidate.Processed
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Verarbeitet
-- Column: PP_Order_Candidate.Processed
-- 2023-12-13T11:44:54.518Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577887,723234,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'EE01','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','Y','N','Verarbeitet',160,1,1,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:54.519Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723234 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:54.520Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2023-12-13T11:44:54.528Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723234
;

-- 2023-12-13T11:44:54.528Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723234)
;

-- 2023-12-13T11:44:54.531Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723234
;

-- 2023-12-13T11:44:54.531Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723234, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667487
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Geschlossen
-- Column: PP_Order_Candidate.IsClosed
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Geschlossen
-- Column: PP_Order_Candidate.IsClosed
-- 2023-12-13T11:44:54.622Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577888,723235,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100,'',1,'EE01','',0,'Y','N','N','N','N','N','Y','N','Geschlossen',170,1,1,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:54.623Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723235 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:54.625Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2723) 
;

-- 2023-12-13T11:44:54.628Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723235
;

-- 2023-12-13T11:44:54.629Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723235)
;

-- 2023-12-13T11:44:54.631Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723235
;

-- 2023-12-13T11:44:54.631Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723235, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667488
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Produzierte Menge
-- Column: PP_Order_Candidate.QtyProcessed
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Produzierte Menge
-- Column: PP_Order_Candidate.QtyProcessed
-- 2023-12-13T11:44:54.709Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,578414,723236,580292,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100,22,'EE01',0,'Y','N','N','N','N','N','Y','N','Produzierte Menge',180,1,1,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:54.710Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723236 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:54.711Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580292) 
;

-- 2023-12-13T11:44:54.713Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723236
;

-- 2023-12-13T11:44:54.714Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723236)
;

-- 2023-12-13T11:44:54.715Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723236
;

-- 2023-12-13T11:44:54.716Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723236, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 669111
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Offene Menge
-- Column: PP_Order_Candidate.QtyToProcess
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Offene Menge
-- Column: PP_Order_Candidate.QtyToProcess
-- 2023-12-13T11:44:54.802Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,578415,723237,580291,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100,22,'EE01',0,'Y','N','N','N','N','N','N','N','Offene Menge',190,1,1,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:54.803Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723237 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:54.804Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580291) 
;

-- 2023-12-13T11:44:54.807Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723237
;

-- 2023-12-13T11:44:54.807Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723237)
;

-- 2023-12-13T11:44:54.809Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723237
;

-- 2023-12-13T11:44:54.810Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723237, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 669112
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Lieferdisposition
-- Column: PP_Order_Candidate.M_ShipmentSchedule_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Lieferdisposition
-- Column: PP_Order_Candidate.M_ShipmentSchedule_ID
-- 2023-12-13T11:44:54.897Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,578711,723238,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100,22,'EE01',0,'Y','N','N','N','N','N','Y','N','Lieferdisposition',200,1,1,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:54.898Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723238 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:54.899Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(500221) 
;

-- 2023-12-13T11:44:54.903Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723238
;

-- 2023-12-13T11:44:54.904Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723238)
;

-- 2023-12-13T11:44:54.906Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723238
;

-- 2023-12-13T11:44:54.906Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723238, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 672876
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Sequenz Nr.
-- Column: PP_Order_Candidate.SeqNo
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Sequenz Nr.
-- Column: PP_Order_Candidate.SeqNo
-- 2023-12-13T11:44:55.004Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583339,723239,581031,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100,0,'EE01',0,'Y','Y','Y','N','N','N','N','N','Sequenz Nr.',210,10,0,1,1,TO_TIMESTAMP('2023-12-13 13:44:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:55.005Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723239 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:55.006Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581031) 
;

-- 2023-12-13T11:44:55.008Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723239
;

-- 2023-12-13T11:44:55.009Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723239)
;

-- 2023-12-13T11:44:55.010Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723239
;

-- 2023-12-13T11:44:55.011Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723239, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 700645
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Packvorschrift
-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Packvorschrift
-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2023-12-13T11:44:55.101Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583382,723240,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01',0,'Y','N','N','N','N','N','N','N','Packvorschrift',220,1,1,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:55.102Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723240 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:55.103Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542132) 
;

-- 2023-12-13T11:44:55.106Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723240
;

-- 2023-12-13T11:44:55.107Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723240)
;

-- 2023-12-13T11:44:55.108Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723240
;

-- 2023-12-13T11:44:55.109Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723240, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 700726
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Anzahl der Maschinen
-- Column: PP_Order_Candidate.NumberOfResources_ToProcess
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Anzahl der Maschinen
-- Column: PP_Order_Candidate.NumberOfResources_ToProcess
-- 2023-12-13T11:44:55.206Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584864,723241,0,547345,0,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100,'',14,'EE01',0,'Y','N','N','N','N','N','N','N','Anzahl der Maschinen',230,1,1,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:55.207Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723241 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:55.208Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581637) 
;

-- 2023-12-13T11:44:55.211Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723241
;

-- 2023-12-13T11:44:55.211Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723241)
;

-- 2023-12-13T11:44:55.213Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723241
;

-- 2023-12-13T11:44:55.214Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723241, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 707969
;

-- Tab: Reiferdisposition -> Produktionsdispozeile
-- Table: PP_OrderLine_Candidate
-- Tab: Reiferdisposition(541756,EE01) -> Produktionsdispozeile
-- Table: PP_OrderLine_Candidate
-- 2023-12-13T11:44:55.292Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,577897,580294,0,547346,541914,541756,'Y',TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100,'EE01','N','N','A','PP_OrderLine_Candidate','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','Y','N','N',0,'Produktionsdispozeile','N',20,1,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:55.294Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547346 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-12-13T11:44:55.295Z
/* DDL */  select update_tab_translation_from_ad_element(580294) 
;

-- 2023-12-13T11:44:55.299Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547346)
;

-- 2023-12-13T11:44:55.302Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 547346
;

-- 2023-12-13T11:44:55.303Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 547346, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 544808
;

-- Field: Reiferdisposition -> Produktionsdispozeile -> Mandant
-- Column: PP_OrderLine_Candidate.AD_Client_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> Mandant
-- Column: PP_OrderLine_Candidate.AD_Client_ID
-- 2023-12-13T11:44:55.402Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577889,723242,0,547346,0,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',10,1,1,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:55.403Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723242 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:55.404Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-12-13T11:44:55.458Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723242
;

-- 2023-12-13T11:44:55.461Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723242)
;

-- 2023-12-13T11:44:55.463Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723242
;

-- 2023-12-13T11:44:55.463Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723242, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667597
;

-- Field: Reiferdisposition -> Produktionsdispozeile -> Sektion
-- Column: PP_OrderLine_Candidate.AD_Org_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> Sektion
-- Column: PP_OrderLine_Candidate.AD_Org_ID
-- 2023-12-13T11:44:55.543Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577890,723243,0,547346,0,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','Sektion',20,1,1,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:55.545Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723243 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:55.546Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-12-13T11:44:55.599Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723243
;

-- 2023-12-13T11:44:55.600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723243)
;

-- 2023-12-13T11:44:55.602Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723243
;

-- 2023-12-13T11:44:55.603Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723243, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667598
;

-- Field: Reiferdisposition -> Produktionsdispozeile -> Aktiv
-- Column: PP_OrderLine_Candidate.IsActive
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> Aktiv
-- Column: PP_OrderLine_Candidate.IsActive
-- 2023-12-13T11:44:55.682Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577893,723244,0,547346,0,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',30,1,1,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:55.684Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723244 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:55.685Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-12-13T11:44:55.742Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723244
;

-- 2023-12-13T11:44:55.743Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723244)
;

-- 2023-12-13T11:44:55.745Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723244
;

-- 2023-12-13T11:44:55.746Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723244, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667599
;

-- Field: Reiferdisposition -> Produktionsdispozeile -> Manufacturing Order Line Candidate
-- Column: PP_OrderLine_Candidate.PP_OrderLine_Candidate_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> Manufacturing Order Line Candidate
-- Column: PP_OrderLine_Candidate.PP_OrderLine_Candidate_ID
-- 2023-12-13T11:44:55.833Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577896,723245,0,547346,0,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01',0,'Y','N','N','N','N','N','N','N','Manufacturing Order Line Candidate',40,1,1,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:55.835Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723245 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:55.836Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580110) 
;

-- 2023-12-13T11:44:55.838Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723245
;

-- 2023-12-13T11:44:55.839Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723245)
;

-- 2023-12-13T11:44:55.841Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723245
;

-- 2023-12-13T11:44:55.842Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723245, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667600
;

-- Field: Reiferdisposition -> Produktionsdispozeile -> Produktionsdisposition
-- Column: PP_OrderLine_Candidate.PP_Order_Candidate_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> Produktionsdisposition
-- Column: PP_OrderLine_Candidate.PP_Order_Candidate_ID
-- 2023-12-13T11:44:55.929Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577897,723246,0,547346,0,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100,22,'EE01',0,'Y','N','N','N','N','N','N','N','Produktionsdisposition',50,1,1,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:55.930Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723246 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:55.931Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580109) 
;

-- 2023-12-13T11:44:55.933Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723246
;

-- 2023-12-13T11:44:55.934Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723246)
;

-- 2023-12-13T11:44:55.935Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723246
;

-- 2023-12-13T11:44:55.936Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723246, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667601
;

-- Field: Reiferdisposition -> Produktionsdispozeile -> Beschreibung
-- Column: PP_OrderLine_Candidate.Description
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> Beschreibung
-- Column: PP_OrderLine_Candidate.Description
-- 2023-12-13T11:44:56.023Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577898,723247,0,547346,0,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100,510,'EE01',0,'Y','N','N','N','N','N','N','N','Beschreibung',60,1,1,TO_TIMESTAMP('2023-12-13 13:44:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:56.024Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723247 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:56.026Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2023-12-13T11:44:56.070Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723247
;

-- 2023-12-13T11:44:56.071Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723247)
;

-- 2023-12-13T11:44:56.074Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723247
;

-- 2023-12-13T11:44:56.074Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723247, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667602
;

-- Field: Reiferdisposition -> Produktionsdispozeile -> BOM Line
-- Column: PP_OrderLine_Candidate.PP_Product_BOMLine_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> BOM Line
-- Column: PP_OrderLine_Candidate.PP_Product_BOMLine_ID
-- 2023-12-13T11:44:56.161Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577899,723248,0,547346,0,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100,'BOM Line',22,'EE01','The BOM Line is a unique identifier for a BOM line in an BOM.',0,'Y','N','N','N','N','N','N','N','BOM Line',70,1,1,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:56.162Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723248 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:56.163Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53254) 
;

-- 2023-12-13T11:44:56.165Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723248
;

-- 2023-12-13T11:44:56.166Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723248)
;

-- 2023-12-13T11:44:56.169Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723248
;

-- 2023-12-13T11:44:56.170Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723248, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667603
;

-- Field: Reiferdisposition -> Produktionsdispozeile -> Component Type
-- Column: PP_OrderLine_Candidate.ComponentType
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> Component Type
-- Column: PP_OrderLine_Candidate.ComponentType
-- 2023-12-13T11:44:56.253Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577900,723249,0,547346,0,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Component Type for a Bill of Material or Formula',2,'EE01','The Component Type can be:

1.- By Product: Define a By Product as Component into BOM
2.- Component: Define a normal Component into BOM 
3.- Option: Define an Option for Product Configure BOM
4.- Phantom: Define a Phantom as Component into BOM
5.- Packing: Define a Packing as Component into BOM
6.- Planning : Define Planning as Component into BOM
7.- Tools: Define Tools as Component into BOM
8.- Variant: Define Variant  for Product Configure BOM
',0,'Y','N','N','N','N','N','N','N','Component Type',80,1,1,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:56.255Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723249 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:56.256Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53249) 
;

-- 2023-12-13T11:44:56.258Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723249
;

-- 2023-12-13T11:44:56.259Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723249)
;

-- 2023-12-13T11:44:56.261Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723249
;

-- 2023-12-13T11:44:56.261Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723249, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667604
;

-- Field: Reiferdisposition -> Produktionsdispozeile -> Produkt
-- Column: PP_OrderLine_Candidate.M_Product_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> Produkt
-- Column: PP_OrderLine_Candidate.M_Product_ID
-- 2023-12-13T11:44:56.345Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577901,723250,0,547346,0,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',22,'EE01','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','N','N','N','N','N','N','N','Produkt',90,1,1,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:56.347Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723250 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:56.348Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2023-12-13T11:44:56.358Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723250
;

-- 2023-12-13T11:44:56.359Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723250)
;

-- 2023-12-13T11:44:56.361Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723250
;

-- 2023-12-13T11:44:56.362Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723250, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667605
;

-- Field: Reiferdisposition -> Produktionsdispozeile -> Merkmale
-- Column: PP_OrderLine_Candidate.M_AttributeSetInstance_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> Merkmale
-- Column: PP_OrderLine_Candidate.M_AttributeSetInstance_ID
-- 2023-12-13T11:44:56.448Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577902,723251,0,547346,0,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt',22,'EE01','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','N','N','N','N','N','N','N','Merkmale',100,1,1,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:56.449Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723251 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:56.450Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019) 
;

-- 2023-12-13T11:44:56.456Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723251
;

-- 2023-12-13T11:44:56.457Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723251)
;

-- 2023-12-13T11:44:56.458Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723251
;

-- 2023-12-13T11:44:56.459Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723251, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667606
;

-- Field: Reiferdisposition -> Produktionsdispozeile -> Menge
-- Column: PP_OrderLine_Candidate.QtyEntered
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> Menge
-- Column: PP_OrderLine_Candidate.QtyEntered
-- 2023-12-13T11:44:56.544Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577903,723252,0,547346,0,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit',10,'EE01','Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt',0,'Y','N','N','N','N','N','N','N','Menge',110,1,1,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:56.545Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723252 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:56.548Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2589) 
;

-- 2023-12-13T11:44:56.551Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723252
;

-- 2023-12-13T11:44:56.552Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723252)
;

-- 2023-12-13T11:44:56.554Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723252
;

-- 2023-12-13T11:44:56.554Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723252, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667607
;

-- Field: Reiferdisposition -> Produktionsdispozeile -> Maßeinheit
-- Column: PP_OrderLine_Candidate.C_UOM_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> Maßeinheit
-- Column: PP_OrderLine_Candidate.C_UOM_ID
-- 2023-12-13T11:44:56.641Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577904,723253,0,547346,0,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',10,'EE01','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','N','N','N','N','N','N','N','Maßeinheit',120,1,1,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:44:56.643Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723253 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:44:56.644Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2023-12-13T11:44:56.650Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723253
;

-- 2023-12-13T11:44:56.651Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723253)
;

-- 2023-12-13T11:44:56.654Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 723253
;

-- 2023-12-13T11:44:56.655Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 723253, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 667608
;

-- Tab: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01)
-- UI Section: main
-- 2023-12-13T11:44:56.738Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547345,545928,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-12-13T11:44:56.739Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545928 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2023-12-13T11:44:56.742Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545928
;

-- 2023-12-13T11:44:56.743Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545928, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 543800
;

-- UI Section: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main
-- UI Column: 10
-- 2023-12-13T11:44:56.824Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547227,545928,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10
-- UI Element Group: main
-- 2023-12-13T11:44:56.911Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547227,551402,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Produkt
-- Column: PP_Order_Candidate.M_Product_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> main.Produkt
-- Column: PP_Order_Candidate.M_Product_ID
-- 2023-12-13T11:44:56.992Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723227,0,547345,551402,621989,'F',TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','Y','N','N',0,'Produkt',10,20,0,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Merkmale
-- Column: PP_Order_Candidate.M_AttributeSetInstance_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> main.Merkmale
-- Column: PP_Order_Candidate.M_AttributeSetInstance_ID
-- 2023-12-13T11:44:57.088Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723228,0,547345,551402,621990,'F',TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','Y','N','N',0,'Merkmale',20,30,0,TO_TIMESTAMP('2023-12-13 13:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Product Planning
-- Column: PP_Order_Candidate.PP_Product_Planning_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> main.Product Planning
-- Column: PP_Order_Candidate.PP_Product_Planning_ID
-- 2023-12-13T11:44:57.185Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723225,0,547345,551402,621991,'F',TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Product Planning',25,40,0,TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Stückliste
-- Column: PP_Order_Candidate.PP_Product_BOM_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> main.Stückliste
-- Column: PP_Order_Candidate.PP_Product_BOM_ID
-- 2023-12-13T11:44:57.290Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723226,0,547345,551402,621992,'F',TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','N','Y','Y','N','N',0,'Stückliste',30,50,0,TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Produktionsressource
-- Column: PP_Order_Candidate.S_Resource_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> main.Produktionsressource
-- Column: PP_Order_Candidate.S_Resource_ID
-- 2023-12-13T11:44:57.383Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723224,0,547345,551402,621993,'F',TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','N','Y','Y','N','N',0,'Produktionsressource',40,60,0,TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Lager
-- Column: PP_Order_Candidate.M_Warehouse_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> main.Lager
-- Column: PP_Order_Candidate.M_Warehouse_ID
-- 2023-12-13T11:44:57.464Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723223,0,547345,551402,621994,'F',TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','Y','Y','N','N',0,'Lager',50,70,0,TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10
-- UI Element Group: menge
-- 2023-12-13T11:44:57.545Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547227,551403,TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','menge',20,TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Menge
-- Column: PP_Order_Candidate.QtyEntered
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> menge.Menge
-- Column: PP_Order_Candidate.QtyEntered
-- 2023-12-13T11:44:57.653Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,723232,0,547345,551403,621995,'F',TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','Y','Y','N','N',0,'Menge',10,80,0,TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Quantity To Process
-- Column: PP_Order_Candidate.QtyToProcess
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> menge.Quantity To Process
-- Column: PP_Order_Candidate.QtyToProcess
-- 2023-12-13T11:44:57.746Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,723237,0,547345,551403,621996,'F',TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Quantity To Process',11,90,0,TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Quantity Processed
-- Column: PP_Order_Candidate.QtyProcessed
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> menge.Quantity Processed
-- Column: PP_Order_Candidate.QtyProcessed
-- 2023-12-13T11:44:57.841Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,723236,0,547345,551403,621997,'F',TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Quantity Processed',15,100,0,TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Maßeinheit
-- Column: PP_Order_Candidate.C_UOM_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> menge.Maßeinheit
-- Column: PP_Order_Candidate.C_UOM_ID
-- 2023-12-13T11:44:57.933Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,723233,0,547345,551403,621998,'F',TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','Y','N','N',0,'Maßeinheit',20,110,0,TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Sequenz Nr.
-- Column: PP_Order_Candidate.SeqNo
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> menge.Sequenz Nr.
-- Column: PP_Order_Candidate.SeqNo
-- 2023-12-13T11:44:58.020Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723239,0,547345,551403,621999,'F',TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Sequenz Nr.',30,10,0,TO_TIMESTAMP('2023-12-13 13:44:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Packvorschrift
-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> menge.Packvorschrift
-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2023-12-13T11:44:58.114Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723240,0,547345,551403,622000,'F',TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Packvorschrift',30,120,0,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Number of Resources to Process
-- Column: PP_Order_Candidate.NumberOfResources_ToProcess
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> menge.Number of Resources to Process
-- Column: PP_Order_Candidate.NumberOfResources_ToProcess
-- 2023-12-13T11:44:58.219Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723241,0,547345,551403,622001,'F',TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100,'Required number of resources to process the the open qty','Y','N','N','Y','Y','N','N','Number of Resources to Process',40,100,0,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main
-- UI Column: 20
-- 2023-12-13T11:44:58.305Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547228,545928,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 20
-- UI Element Group: dates
-- 2023-12-13T11:44:58.394Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547228,551404,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',10,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Zugesagter Termin
-- Column: PP_Order_Candidate.DatePromised
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 20 -> dates.Zugesagter Termin
-- Column: PP_Order_Candidate.DatePromised
-- 2023-12-13T11:44:58.481Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723230,0,547345,551404,622002,'F',TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','N','Y','Y','N','N',0,'Zugesagter Termin',10,130,0,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.geplanter Beginn
-- Column: PP_Order_Candidate.DateStartSchedule
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 20 -> dates.geplanter Beginn
-- Column: PP_Order_Candidate.DateStartSchedule
-- 2023-12-13T11:44:58.566Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723231,0,547345,551404,622003,'F',TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'geplanter Beginn',20,140,0,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 20
-- UI Element Group: bp
-- 2023-12-13T11:44:58.642Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547228,551405,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','bp',20,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Verarbeitet
-- Column: PP_Order_Candidate.Processed
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 20 -> bp.Verarbeitet
-- Column: PP_Order_Candidate.Processed
-- 2023-12-13T11:44:58.735Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723234,0,547345,551405,622004,'F',TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','Y','N','N',0,'Verarbeitet',20,150,0,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Geschlossen
-- Column: PP_Order_Candidate.IsClosed
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 20 -> bp.Geschlossen
-- Column: PP_Order_Candidate.IsClosed
-- 2023-12-13T11:44:58.820Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723235,0,547345,551405,622005,'F',TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Geschlossen',30,160,0,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 20
-- UI Element Group: org
-- 2023-12-13T11:44:58.908Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547228,551406,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Sektion
-- Column: PP_Order_Candidate.AD_Org_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 20 -> org.Sektion
-- Column: PP_Order_Candidate.AD_Org_ID
-- 2023-12-13T11:44:59.003Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723220,0,547345,551406,622006,'F',TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N',0,'Sektion',10,170,0,TO_TIMESTAMP('2023-12-13 13:44:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Mandant
-- Column: PP_Order_Candidate.AD_Client_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 20 -> org.Mandant
-- Column: PP_Order_Candidate.AD_Client_ID
-- 2023-12-13T11:44:59.095Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723219,0,547345,551406,622007,'F',TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','Y','N','N',0,'Mandant',20,180,0,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01)
-- UI Section: advanced edit
-- 2023-12-13T11:44:59.187Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547345,545929,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2023-12-13T11:44:59.192Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545929 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2023-12-13T11:44:59.200Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545929
;

-- 2023-12-13T11:44:59.206Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545929, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544046
;

-- UI Section: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> advanced edit
-- UI Column: 10
-- 2023-12-13T11:44:59.299Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547229,545929,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> advanced edit -> 10
-- UI Element Group: primary
-- 2023-12-13T11:44:59.388Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547229,551407,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','primary',10,'primary',TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Auftragsposition
-- Column: PP_Order_Candidate.C_OrderLine_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> advanced edit -> 10 -> primary.Auftragsposition
-- Column: PP_Order_Candidate.C_OrderLine_ID
-- 2023-12-13T11:44:59.507Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723229,0,547345,551407,622008,'F',TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','Y','N','Y','N','N','N',0,'Auftragsposition',10,0,0,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Lieferdisposition
-- Column: PP_Order_Candidate.M_ShipmentSchedule_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> advanced edit -> 10 -> primary.Lieferdisposition
-- Column: PP_Order_Candidate.M_ShipmentSchedule_ID
-- 2023-12-13T11:44:59.598Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723238,0,547345,551407,622009,'F',TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Lieferdisposition',20,0,0,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01)
-- UI Section: main
-- 2023-12-13T11:44:59.690Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547346,545930,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-12-13T11:44:59.691Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545930 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2023-12-13T11:44:59.693Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545930
;

-- 2023-12-13T11:44:59.694Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545930, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 543814
;

-- UI Section: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> main
-- UI Column: 10
-- 2023-12-13T11:44:59.775Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547230,545930,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> main -> 10
-- UI Element Group: default
-- 2023-12-13T11:44:59.850Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547230,551408,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdispozeile.Produkt
-- Column: PP_OrderLine_Candidate.M_Product_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> main -> 10 -> default.Produkt
-- Column: PP_OrderLine_Candidate.M_Product_ID
-- 2023-12-13T11:44:59.935Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723250,0,547346,551408,622010,'F',TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',10,0,0,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdispozeile.Merkmale
-- Column: PP_OrderLine_Candidate.M_AttributeSetInstance_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> main -> 10 -> default.Merkmale
-- Column: PP_OrderLine_Candidate.M_AttributeSetInstance_ID
-- 2023-12-13T11:45:00.029Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723251,0,547346,551408,622011,'F',TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','N','N','N',0,'Merkmale',20,0,0,TO_TIMESTAMP('2023-12-13 13:44:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdispozeile.Menge
-- Column: PP_OrderLine_Candidate.QtyEntered
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> main -> 10 -> default.Menge
-- Column: PP_OrderLine_Candidate.QtyEntered
-- 2023-12-13T11:45:00.125Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723252,0,547346,551408,622012,'F',TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit','Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt','Y','N','N','Y','N','N','N',0,'Menge',30,0,0,TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdispozeile.Maßeinheit
-- Column: PP_OrderLine_Candidate.C_UOM_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> main -> 10 -> default.Maßeinheit
-- Column: PP_OrderLine_Candidate.C_UOM_ID
-- 2023-12-13T11:45:00.220Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723253,0,547346,551408,622013,'F',TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',40,0,0,TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdispozeile.Component Type
-- Column: PP_OrderLine_Candidate.ComponentType
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> main -> 10 -> default.Component Type
-- Column: PP_OrderLine_Candidate.ComponentType
-- 2023-12-13T11:45:00.310Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723249,0,547346,551408,622014,'F',TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100,'Component Type for a Bill of Material or Formula','The Component Type can be:

1.- By Product: Define a By Product as Component into BOM
2.- Component: Define a normal Component into BOM 
3.- Option: Define an Option for Product Configure BOM
4.- Phantom: Define a Phantom as Component into BOM
5.- Packing: Define a Packing as Component into BOM
6.- Planning : Define Planning as Component into BOM
7.- Tools: Define Tools as Component into BOM
8.- Variant: Define Variant  for Product Configure BOM
','Y','N','N','Y','N','N','N',0,'Component Type',50,0,0,TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdispozeile.BOM Line
-- Column: PP_OrderLine_Candidate.PP_Product_BOMLine_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> main -> 10 -> default.BOM Line
-- Column: PP_OrderLine_Candidate.PP_Product_BOMLine_ID
-- 2023-12-13T11:45:00.432Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723248,0,547346,551408,622015,'F',TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100,'BOM Line','The BOM Line is a unique identifier for a BOM line in an BOM.','Y','N','N','Y','N','N','N',0,'BOM Line',60,0,0,TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdispozeile.Beschreibung
-- Column: PP_OrderLine_Candidate.Description
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> main -> 10 -> default.Beschreibung
-- Column: PP_OrderLine_Candidate.Description
-- 2023-12-13T11:45:00.540Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723247,0,547346,551408,622016,'F',TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',70,0,0,TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdispozeile.Aktiv
-- Column: PP_OrderLine_Candidate.IsActive
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> main -> 10 -> default.Aktiv
-- Column: PP_OrderLine_Candidate.IsActive
-- 2023-12-13T11:45:00.623Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723244,0,547346,551408,622017,'F',TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',80,0,0,TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdispozeile.Sektion
-- Column: PP_OrderLine_Candidate.AD_Org_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> main -> 10 -> default.Sektion
-- Column: PP_OrderLine_Candidate.AD_Org_ID
-- 2023-12-13T11:45:00.713Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723243,0,547346,551408,622018,'F',TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',90,0,0,TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdispozeile.Mandant
-- Column: PP_OrderLine_Candidate.AD_Client_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdispozeile(547346,EE01) -> main -> 10 -> default.Mandant
-- Column: PP_OrderLine_Candidate.AD_Client_ID
-- 2023-12-13T11:45:00.795Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723242,0,547346,551408,622019,'F',TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',100,0,0,TO_TIMESTAMP('2023-12-13 13:45:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Reiferdisposition -> Produktionsdisposition
-- Table: PP_Order_Candidate
-- Tab: Reiferdisposition(541756,EE01) -> Produktionsdisposition
-- Table: PP_Order_Candidate
-- 2023-12-13T11:45:20.260Z
UPDATE AD_Tab SET WhereClause='PP_Order_Candidate.IsSimulated=''N'' AND PP_Order_Candidate.IsMaturing = ''Y''',Updated=TO_TIMESTAMP('2023-12-13 13:45:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547345
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Simulated
-- Column: PP_Order_Candidate.IsSimulated
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Simulated
-- Column: PP_Order_Candidate.IsSimulated
-- 2023-12-13T11:45:38.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579340,723254,0,547345,TO_TIMESTAMP('2023-12-13 13:45:38','YYYY-MM-DD HH24:MI:SS'),100,1,'EE01','Y','N','N','N','N','N','N','N','Simulated',TO_TIMESTAMP('2023-12-13 13:45:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:45:38.271Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723254 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:45:38.272Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580611) 
;

-- 2023-12-13T11:45:38.276Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723254
;

-- 2023-12-13T11:45:38.278Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723254)
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Reifung
-- Column: PP_Order_Candidate.IsMaturing
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Reifung
-- Column: PP_Order_Candidate.IsMaturing
-- 2023-12-13T11:45:38.372Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587755,723255,0,547345,TO_TIMESTAMP('2023-12-13 13:45:38','YYYY-MM-DD HH24:MI:SS'),100,1,'EE01','Y','N','N','N','N','N','N','N','Reifung',TO_TIMESTAMP('2023-12-13 13:45:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:45:38.373Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723255 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:45:38.375Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582865) 
;

-- 2023-12-13T11:45:38.377Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723255
;

-- 2023-12-13T11:45:38.377Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723255)
;

-- Column: PP_Order_Candidate.M_Maturing_Configuration_ID
-- Column: PP_Order_Candidate.M_Maturing_Configuration_ID
-- 2023-12-13T11:49:18.339Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587756,582856,0,19,541913,'M_Maturing_Configuration_ID',TO_TIMESTAMP('2023-12-13 13:49:18','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maturing Configuration ',0,0,TO_TIMESTAMP('2023-12-13 13:49:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T11:49:18.340Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587756 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T11:49:18.342Z
/* DDL */  select update_Column_Translation_From_AD_Element(582856) 
;

-- 2023-12-13T11:49:19.385Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Candidate','ALTER TABLE public.PP_Order_Candidate ADD COLUMN M_Maturing_Configuration_ID NUMERIC(10)')
;

-- 2023-12-13T11:49:19.390Z
ALTER TABLE PP_Order_Candidate ADD CONSTRAINT MMaturingConfiguration_PPOrderCandidate FOREIGN KEY (M_Maturing_Configuration_ID) REFERENCES public.M_Maturing_Configuration DEFERRABLE INITIALLY DEFERRED
;

-- Column: PP_Order_Candidate.M_Maturing_Configuration_Line_ID
-- Column: PP_Order_Candidate.M_Maturing_Configuration_Line_ID
-- 2023-12-13T11:49:31.062Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587757,582857,0,19,541913,'M_Maturing_Configuration_Line_ID',TO_TIMESTAMP('2023-12-13 13:49:30','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maturing Configuration Line',0,0,TO_TIMESTAMP('2023-12-13 13:49:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T11:49:31.064Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587757 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T11:49:31.066Z
/* DDL */  select update_Column_Translation_From_AD_Element(582857) 
;

-- 2023-12-13T11:49:33.722Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Candidate','ALTER TABLE public.PP_Order_Candidate ADD COLUMN M_Maturing_Configuration_Line_ID NUMERIC(10)')
;

-- 2023-12-13T11:49:33.729Z
ALTER TABLE PP_Order_Candidate ADD CONSTRAINT MMaturingConfigurationLine_PPOrderCandidate FOREIGN KEY (M_Maturing_Configuration_Line_ID) REFERENCES public.M_Maturing_Configuration_Line DEFERRABLE INITIALLY DEFERRED
;

-- 2023-12-13T11:50:04.410Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582867,0,'Issue_HU_ID',TO_TIMESTAMP('2023-12-13 13:50:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Issue HU','Issue HU',TO_TIMESTAMP('2023-12-13 13:50:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:50:04.411Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582867 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Issue_HU_ID
-- 2023-12-13T11:52:42.282Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='HU', PrintName='HU',Updated=TO_TIMESTAMP('2023-12-13 13:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582867 AND AD_Language='de_CH'
;

-- 2023-12-13T11:52:42.285Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582867,'de_CH') 
;

-- Element: Issue_HU_ID
-- 2023-12-13T11:52:48.616Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='HU', PrintName='HU',Updated=TO_TIMESTAMP('2023-12-13 13:52:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582867 AND AD_Language='de_DE'
;

-- 2023-12-13T11:52:48.618Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582867,'de_DE') 
;

-- 2023-12-13T11:52:48.619Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582867,'de_DE') 
;

-- Column: PP_Order_Candidate.Issue_HU_ID
-- Column: PP_Order_Candidate.Issue_HU_ID
-- 2023-12-13T11:53:57.765Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587758,582867,0,30,540499,541913,'Issue_HU_ID',TO_TIMESTAMP('2023-12-13 13:53:57','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'HU',0,0,TO_TIMESTAMP('2023-12-13 13:53:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-13T11:53:57.766Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587758 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-13T11:53:57.769Z
/* DDL */  select update_Column_Translation_From_AD_Element(582867) 
;

-- 2023-12-13T11:54:08.722Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Candidate','ALTER TABLE public.PP_Order_Candidate ADD COLUMN Issue_HU_ID NUMERIC(10)')
;

-- 2023-12-13T11:54:08.728Z
ALTER TABLE PP_Order_Candidate ADD CONSTRAINT IssueHU_PPOrderCandidate FOREIGN KEY (Issue_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;

-- 2023-12-13T11:54:29.895Z
INSERT INTO t_alter_column values('pp_order_candidate','M_Maturing_Configuration_ID','NUMERIC(10)',null,null)
;

-- 2023-12-13T11:54:34.944Z
INSERT INTO t_alter_column values('pp_order_candidate','M_Maturing_Configuration_Line_ID','NUMERIC(10)',null,null)
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Maturing Configuration
-- Column: PP_Order_Candidate.M_Maturing_Configuration_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Maturing Configuration
-- Column: PP_Order_Candidate.M_Maturing_Configuration_ID
-- 2023-12-13T11:55:26.353Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587756,723256,0,547345,TO_TIMESTAMP('2023-12-13 13:55:26','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Maturing Configuration ',TO_TIMESTAMP('2023-12-13 13:55:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:55:26.355Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723256 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:55:26.356Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582856) 
;

-- 2023-12-13T11:55:26.360Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723256
;

-- 2023-12-13T11:55:26.363Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723256)
;

-- Field: Reiferdisposition -> Produktionsdisposition -> Maturing Configuration Line
-- Column: PP_Order_Candidate.M_Maturing_Configuration_Line_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Maturing Configuration Line
-- Column: PP_Order_Candidate.M_Maturing_Configuration_Line_ID
-- 2023-12-13T11:55:26.459Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587757,723257,0,547345,TO_TIMESTAMP('2023-12-13 13:55:26','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Maturing Configuration Line',TO_TIMESTAMP('2023-12-13 13:55:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:55:26.460Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723257 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:55:26.462Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582857) 
;

-- 2023-12-13T11:55:26.464Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723257
;

-- 2023-12-13T11:55:26.465Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723257)
;

-- Field: Reiferdisposition -> Produktionsdisposition -> HU
-- Column: PP_Order_Candidate.Issue_HU_ID
-- Field: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> HU
-- Column: PP_Order_Candidate.Issue_HU_ID
-- 2023-12-13T11:55:26.548Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587758,723258,0,547345,TO_TIMESTAMP('2023-12-13 13:55:26','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','HU',TO_TIMESTAMP('2023-12-13 13:55:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T11:55:26.549Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723258 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-13T11:55:26.550Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582867) 
;

-- 2023-12-13T11:55:26.552Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723258
;

-- 2023-12-13T11:55:26.553Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723258)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Reifung
-- Column: PP_Order_Candidate.IsMaturing
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 20 -> bp.Reifung
-- Column: PP_Order_Candidate.IsMaturing
-- 2023-12-13T12:03:58.979Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723255,0,547345,551405,622020,'F',TO_TIMESTAMP('2023-12-13 14:03:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Reifung',40,0,0,TO_TIMESTAMP('2023-12-13 14:03:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Maturing Configuration
-- Column: PP_Order_Candidate.M_Maturing_Configuration_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> main.Maturing Configuration
-- Column: PP_Order_Candidate.M_Maturing_Configuration_ID
-- 2023-12-13T12:04:16.351Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723256,0,547345,551402,622021,'F',TO_TIMESTAMP('2023-12-13 14:04:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Maturing Configuration ',60,0,0,TO_TIMESTAMP('2023-12-13 14:04:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.Maturing Configuration Line
-- Column: PP_Order_Candidate.M_Maturing_Configuration_Line_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> main.Maturing Configuration Line
-- Column: PP_Order_Candidate.M_Maturing_Configuration_Line_ID
-- 2023-12-13T12:04:22.407Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723257,0,547345,551402,622022,'F',TO_TIMESTAMP('2023-12-13 14:04:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Maturing Configuration Line',70,0,0,TO_TIMESTAMP('2023-12-13 14:04:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reiferdisposition -> Produktionsdisposition.HU
-- Column: PP_Order_Candidate.Issue_HU_ID
-- UI Element: Reiferdisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> main -> 10 -> main.HU
-- Column: PP_Order_Candidate.Issue_HU_ID
-- 2023-12-13T12:04:58.590Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723258,0,547345,551402,622023,'F',TO_TIMESTAMP('2023-12-13 14:04:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'HU',80,0,0,TO_TIMESTAMP('2023-12-13 14:04:58','YYYY-MM-DD HH24:MI:SS'),100)
;



-- Column: M_Maturing_Configuration_Line.MaturityAge
-- Column: M_Maturing_Configuration_Line.MaturityAge
-- 2023-12-13T14:32:04.271Z
UPDATE AD_Column SET AD_Reference_ID=11,Updated=TO_TIMESTAMP('2023-12-13 16:32:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587749
;

-- 2023-12-13T14:32:05.792Z
INSERT INTO t_alter_column values('m_maturing_configuration_line','MaturityAge','NUMERIC(10)',null,'0')
;

-- 2023-12-13T14:32:05.822Z
UPDATE M_Maturing_Configuration_Line SET MaturityAge=0 WHERE MaturityAge IS NULL
;




----------------------------------------------------------------------------------------- AgeOffset Attribute -----------------------------------------------------------

-- 2023-12-13T15:05:16.910Z
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,C_UOM_ID,DescriptionPattern,IsActive,IsAlwaysUpdateable,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsPrintedInDocument,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,'N',TO_TIMESTAMP('2023-12-13 17:05:16','YYYY-MM-DD HH24:MI:SS'),100,100,'','Y','N','N','N','Y','N','N','N','N','Y','N',540128,'Altersversatz',TO_TIMESTAMP('2023-12-13 17:05:16','YYYY-MM-DD HH24:MI:SS'),100,'Age offset')
;


-- 2023-12-13T15:05:43.690Z
UPDATE M_Attribute SET IsStorageRelevant='N',Updated=TO_TIMESTAMP('2023-12-13 17:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540128
;

-- 2023-12-13T15:06:11.370Z
UPDATE M_Attribute SET C_UOM_ID=NULL, IsAttrDocumentRelevant='Y', IsPricingRelevant='Y',Updated=TO_TIMESTAMP('2023-12-13 17:06:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540128
;

-- 2023-12-13T15:08:19.723Z
UPDATE M_Attribute SET Value='AgeOffset',Updated=TO_TIMESTAMP('2023-12-13 17:08:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540128
;

-----------



-- Name: Reife Konfiguration
-- Action Type: W
-- Window: Reife Konfiguration(541755,D)
-- 2023-12-13T16:19:03.087Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582861,542133,0,541755,TO_TIMESTAMP('2023-12-13 18:19:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Maturing Config','Y','N','N','Y','N','Reife Konfiguration',TO_TIMESTAMP('2023-12-13 18:19:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T16:19:03.090Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542133 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-12-13T16:19:03.094Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542133, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542133)
;

-- 2023-12-13T16:19:03.106Z
/* DDL */  select update_menu_translation_from_ad_element(582861) 
;

-- Reordering children of `Settings`
-- Node name: `Product Planning (PP_Product_Planning)`
-- 2023-12-13T16:19:11.378Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541392 AND AD_Tree_ID=10
;

-- Node name: `Product Planning Schema (M_Product_PlanningSchema)`
-- 2023-12-13T16:19:11.381Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541035 AND AD_Tree_ID=10
;

-- Node name: `Product Planning (M_Product)`
-- 2023-12-13T16:19:11.383Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540820 AND AD_Tree_ID=10
;

-- Node name: `Create Default Product Planning Data (de.metas.product.process.M_ProductPlanning_Create_Default_ProductPlanningData)`
-- 2023-12-13T16:19:11.385Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541030 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflows (AD_Workflow)`
-- 2023-12-13T16:19:11.386Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540822 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Standard Activity`
-- 2023-12-13T16:19:11.387Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541409 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Transitions (AD_WF_NodeNext)`
-- 2023-12-13T16:19:11.388Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540823 AND AD_Tree_ID=10
;

-- Node name: `Resource (S_Resource)`
-- 2023-12-13T16:19:11.389Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540818 AND AD_Tree_ID=10
;

-- Node name: `Resource Type (S_ResourceType)`
-- 2023-12-13T16:19:11.390Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540819 AND AD_Tree_ID=10
;

-- Node name: `Component Generator (PP_ComponentGenerator)`
-- 2023-12-13T16:19:11.390Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541571 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Activity (AD_WF_Node)`
-- 2023-12-13T16:19:11.391Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541609 AND AD_Tree_ID=10
;

-- Node name: `Reife Konfiguration`
-- 2023-12-13T16:19:11.392Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542133 AND AD_Tree_ID=10
;

-- Element: null
-- 2023-12-13T16:20:02.912Z
UPDATE AD_Element_Trl SET Name='Reifung Konfiguration', PrintName='Reifung Konfiguration',Updated=TO_TIMESTAMP('2023-12-13 18:20:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582861 AND AD_Language='de_CH'
;

-- 2023-12-13T16:20:02.914Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582861,'de_CH') 
;

-- Element: null
-- 2023-12-13T16:20:35.222Z
UPDATE AD_Element_Trl SET Name='Reifung Konfiguration', PrintName='Reifung Konfiguration',Updated=TO_TIMESTAMP('2023-12-13 18:20:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582861 AND AD_Language='de_DE'
;

-- 2023-12-13T16:20:35.224Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582861,'de_DE') 
;

-- 2023-12-13T16:20:35.225Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582861,'de_DE') 
;

-- Element: From_Product_ID
-- 2023-12-13T16:22:06.590Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rohprodukt', PrintName='Rohprodukt',Updated=TO_TIMESTAMP('2023-12-13 18:22:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582860 AND AD_Language='de_CH'
;

-- 2023-12-13T16:22:06.592Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582860,'de_CH') 
;

-- Element: From_Product_ID
-- 2023-12-13T16:22:11.295Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rohprodukt', PrintName='Rohprodukt',Updated=TO_TIMESTAMP('2023-12-13 18:22:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582860 AND AD_Language='de_DE'
;

-- 2023-12-13T16:22:11.297Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582860,'de_DE') 
;

-- 2023-12-13T16:22:11.298Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582860,'de_DE') 
;

-- Element: From_Product_ID
-- 2023-12-13T16:22:31.019Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Raw Product', PrintName='Raw Product',Updated=TO_TIMESTAMP('2023-12-13 18:22:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582860 AND AD_Language='en_US'
;

-- 2023-12-13T16:22:31.021Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582860,'en_US') 
;

-- Element: IsMatured
-- 2023-12-13T16:23:31.165Z
UPDATE AD_Element_Trl SET Name='Fertig gereift', PrintName='Fertig gereift',Updated=TO_TIMESTAMP('2023-12-13 18:23:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582864 AND AD_Language='de_CH'
;

-- 2023-12-13T16:23:31.168Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582864,'de_CH') 
;

-- Element: IsMatured
-- 2023-12-13T16:23:34.022Z
UPDATE AD_Element_Trl SET Name='Fertig gereift', PrintName='Fertig gereift',Updated=TO_TIMESTAMP('2023-12-13 18:23:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582864 AND AD_Language='de_DE'
;

-- 2023-12-13T16:23:34.024Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582864,'de_DE') 
;

-- 2023-12-13T16:23:34.025Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582864,'de_DE') 
;

-- Element: IsMatured
-- 2023-12-13T16:23:47.742Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Fully matured', PrintName='Fully matured',Updated=TO_TIMESTAMP('2023-12-13 18:23:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582864 AND AD_Language='en_US'
;

-- 2023-12-13T16:23:47.744Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582864,'en_US') 
;

-- Element: IsMaturing
-- 2023-12-13T16:24:53.789Z
UPDATE AD_Element_Trl SET Name='Reifung läuft', PrintName='Reifung läuft',Updated=TO_TIMESTAMP('2023-12-13 18:24:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582865 AND AD_Language='de_CH'
;

-- 2023-12-13T16:24:53.791Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582865,'de_CH') 
;

-- Element: IsMaturing
-- 2023-12-13T16:24:56.172Z
UPDATE AD_Element_Trl SET Name='Reifung läuft', PrintName='Reifung läuft',Updated=TO_TIMESTAMP('2023-12-13 18:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582865 AND AD_Language='de_DE'
;

-- 2023-12-13T16:24:56.174Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582865,'de_DE') 
;

-- 2023-12-13T16:24:56.175Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582865,'de_DE') 
;

-- Element: M_Maturing_Configuration_Line_ID
-- 2023-12-13T16:25:15.655Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zuordnung Reifeprodukte', PrintName='Zuordnung Reifeprodukte',Updated=TO_TIMESTAMP('2023-12-13 18:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582857 AND AD_Language='de_CH'
;

-- 2023-12-13T16:25:15.658Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582857,'de_CH') 
;

-- Element: M_Maturing_Configuration_Line_ID
-- 2023-12-13T16:25:18.474Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zuordnung Reifeprodukte', PrintName='Zuordnung Reifeprodukte',Updated=TO_TIMESTAMP('2023-12-13 18:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582857 AND AD_Language='de_DE'
;

-- 2023-12-13T16:25:18.478Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582857,'de_DE') 
;

-- 2023-12-13T16:25:18.481Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582857,'de_DE') 
;

-- Element: M_Maturing_Configuration_Line_ID
-- 2023-12-13T16:25:26.533Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Maturing Products Allocation', PrintName='Maturing Products Allocation',Updated=TO_TIMESTAMP('2023-12-13 18:25:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582857 AND AD_Language='en_US'
;

-- 2023-12-13T16:25:26.535Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582857,'en_US') 
;

-- 2023-12-13T16:27:18.738Z
UPDATE AD_Window_Trl SET Name='Reiferdisposition',Updated=TO_TIMESTAMP('2023-12-13 18:27:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Window_ID=541756
;

-- 2023-12-13T16:27:21.012Z
UPDATE AD_Window_Trl SET Name='Reiferdisposition',Updated=TO_TIMESTAMP('2023-12-13 18:27:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Window_ID=541756
;

-- 2023-12-13T16:27:29.870Z
UPDATE AD_Window_Trl SET Name='Maturing candidate',Updated=TO_TIMESTAMP('2023-12-13 18:27:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Window_ID=541756
;

-- Name: Reiferdisposition
-- Action Type: W
-- Window: Reiferdisposition(541756,EE01)
-- 2023-12-13T16:27:42.148Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582866,542134,0,541756,TO_TIMESTAMP('2023-12-13 18:27:42','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Maturing candidate','Y','N','N','N','N','Reiferdisposition',TO_TIMESTAMP('2023-12-13 18:27:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-13T16:27:42.149Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542134 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-12-13T16:27:42.150Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542134, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542134)
;

-- 2023-12-13T16:27:42.152Z
/* DDL */  select update_menu_translation_from_ad_element(582866) 
;

-- Reordering children of `Settings`
-- Node name: `Product Planning (PP_Product_Planning)`
-- 2023-12-13T16:27:50.429Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541392 AND AD_Tree_ID=10
;

-- Node name: `Product Planning Schema (M_Product_PlanningSchema)`
-- 2023-12-13T16:27:50.432Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541035 AND AD_Tree_ID=10
;

-- Node name: `Product Planning (M_Product)`
-- 2023-12-13T16:27:50.434Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540820 AND AD_Tree_ID=10
;

-- Node name: `Create Default Product Planning Data (de.metas.product.process.M_ProductPlanning_Create_Default_ProductPlanningData)`
-- 2023-12-13T16:27:50.436Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541030 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflows (AD_Workflow)`
-- 2023-12-13T16:27:50.438Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540822 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Standard Activity`
-- 2023-12-13T16:27:50.439Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541409 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Transitions (AD_WF_NodeNext)`
-- 2023-12-13T16:27:50.440Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540823 AND AD_Tree_ID=10
;

-- Node name: `Resource (S_Resource)`
-- 2023-12-13T16:27:50.442Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540818 AND AD_Tree_ID=10
;

-- Node name: `Resource Type (S_ResourceType)`
-- 2023-12-13T16:27:50.443Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540819 AND AD_Tree_ID=10
;

-- Node name: `Component Generator (PP_ComponentGenerator)`
-- 2023-12-13T16:27:50.444Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541571 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Activity (AD_WF_Node)`
-- 2023-12-13T16:27:50.445Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541609 AND AD_Tree_ID=10
;

-- Node name: `Reife Konfiguration`
-- 2023-12-13T16:27:50.446Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542133 AND AD_Tree_ID=10
;

-- Node name: `Reiferdisposition`
-- 2023-12-13T16:27:50.447Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542134 AND AD_Tree_ID=10
;



-- Tab: Reifung Konfiguration -> Maturing Configuration
-- Table: M_Maturing_Configuration
-- Tab: Reifung Konfiguration(541755,D) -> Maturing Configuration
-- Table: M_Maturing_Configuration
-- 2023-12-13T16:34:29.600Z
UPDATE AD_Tab SET IsSingleRow='Y',Updated=TO_TIMESTAMP('2023-12-13 18:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547343
;

-- Tab: Reifung Konfiguration -> Zuordnung Reifeprodukte
-- Table: M_Maturing_Configuration_Line
-- Tab: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte
-- Table: M_Maturing_Configuration_Line
-- 2023-12-13T16:34:41.825Z
UPDATE AD_Tab SET IsSingleRow='Y',Updated=TO_TIMESTAMP('2023-12-13 18:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547344
;


-- Table: M_Maturing_Configuration
-- Table: M_Maturing_Configuration
-- 2023-12-13T16:36:14.183Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2023-12-13 18:36:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542383
;

-- Table: M_Maturing_Configuration_Line
-- Table: M_Maturing_Configuration_Line
-- 2023-12-13T16:36:23.889Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2023-12-13 18:36:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542384
;





-- Tab: Reifung Konfiguration -> Maturing Configuration
-- Table: M_Maturing_Configuration
-- Tab: Reifung Konfiguration(541755,D) -> Maturing Configuration
-- Table: M_Maturing_Configuration
-- 2023-12-13T16:34:29.600Z
UPDATE AD_Tab SET IsSingleRow='Y',Updated=TO_TIMESTAMP('2023-12-13 18:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547343
;

-- Tab: Reifung Konfiguration -> Zuordnung Reifeprodukte
-- Table: M_Maturing_Configuration_Line
-- Tab: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte
-- Table: M_Maturing_Configuration_Line
-- 2023-12-13T16:34:41.825Z
UPDATE AD_Tab SET IsSingleRow='Y',Updated=TO_TIMESTAMP('2023-12-13 18:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547344
;

-- Table: M_Maturing_Configuration
-- Table: M_Maturing_Configuration
-- 2023-12-13T16:36:14.183Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2023-12-13 18:36:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542383
;

-- Table: M_Maturing_Configuration_Line
-- Table: M_Maturing_Configuration_Line
-- 2023-12-13T16:36:23.889Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2023-12-13 18:36:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542384
;

-- Tab: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D)
-- UI Section: main
-- 2023-12-13T16:39:40.440Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547344,545931,TO_TIMESTAMP('2023-12-13 18:39:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-12-13 18:39:40','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-12-13T16:39:40.441Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545931 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D) -> main
-- UI Column: 10
-- 2023-12-13T16:39:45.338Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547231,545931,TO_TIMESTAMP('2023-12-13 18:39:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-12-13 18:39:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D) -> main
-- UI Column: 20
-- 2023-12-13T16:39:47.038Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547232,545931,TO_TIMESTAMP('2023-12-13 18:39:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-12-13 18:39:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D) -> main -> 10
-- UI Element Group: main
-- 2023-12-13T16:39:54.705Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547231,551409,TO_TIMESTAMP('2023-12-13 18:39:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-12-13 18:39:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reifung Konfiguration -> Zuordnung Reifeprodukte.Rohprodukt
-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- UI Element: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D) -> main -> 10 -> main.Rohprodukt
-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- 2023-12-13T16:40:10.932Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723215,0,547344,551409,622024,'F',TO_TIMESTAMP('2023-12-13 18:40:10','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','N','N','Rohprodukt',10,0,0,TO_TIMESTAMP('2023-12-13 18:40:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reifung Konfiguration -> Zuordnung Reifeprodukte.Rohprodukt
-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- UI Element: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D) -> main -> 10 -> main.Rohprodukt
-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- 2023-12-13T16:40:16.647Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551409, IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2023-12-13 18:40:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=622024
;

-- UI Element: Reifung Konfiguration -> Zuordnung Reifeprodukte.Maturity Age
-- Column: M_Maturing_Configuration_Line.MaturityAge
-- UI Element: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D) -> main -> 10 -> main.Maturity Age
-- Column: M_Maturing_Configuration_Line.MaturityAge
-- 2023-12-13T16:40:25.448Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723213,0,547344,551409,622025,'F',TO_TIMESTAMP('2023-12-13 18:40:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Maturity Age',30,0,0,TO_TIMESTAMP('2023-12-13 18:40:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D) -> main -> 20
-- UI Element Group: flag
-- 2023-12-13T16:40:42.215Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547232,551410,TO_TIMESTAMP('2023-12-13 18:40:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','flag',10,TO_TIMESTAMP('2023-12-13 18:40:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D) -> main -> 20
-- UI Element Group: org
-- 2023-12-13T16:40:46.021Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547232,551411,TO_TIMESTAMP('2023-12-13 18:40:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2023-12-13 18:40:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reifung Konfiguration -> Zuordnung Reifeprodukte.Aktiv
-- Column: M_Maturing_Configuration_Line.IsActive
-- UI Element: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D) -> main -> 20 -> flag.Aktiv
-- Column: M_Maturing_Configuration_Line.IsActive
-- 2023-12-13T16:40:54.042Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723210,0,547344,551410,622026,'F',TO_TIMESTAMP('2023-12-13 18:40:53','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2023-12-13 18:40:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reifung Konfiguration -> Zuordnung Reifeprodukte.Sektion
-- Column: M_Maturing_Configuration_Line.AD_Org_ID
-- UI Element: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D) -> main -> 20 -> org.Sektion
-- Column: M_Maturing_Configuration_Line.AD_Org_ID
-- 2023-12-13T16:41:02.087Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723209,0,547344,551411,622027,'F',TO_TIMESTAMP('2023-12-13 18:41:01','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2023-12-13 18:41:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reifung Konfiguration -> Zuordnung Reifeprodukte.Mandant
-- Column: M_Maturing_Configuration_Line.AD_Client_ID
-- UI Element: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D) -> main -> 20 -> org.Mandant
-- Column: M_Maturing_Configuration_Line.AD_Client_ID
-- 2023-12-13T16:41:08.525Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723208,0,547344,551411,622028,'F',TO_TIMESTAMP('2023-12-13 18:41:08','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2023-12-13 18:41:08','YYYY-MM-DD HH24:MI:SS'),100)
;



-- UI Element: Reifung Konfiguration -> Zuordnung Reifeprodukte.Rohprodukt
-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- UI Element: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D) -> main -> 10 -> main.Rohprodukt
-- Column: M_Maturing_Configuration_Line.From_Product_ID
-- 2023-12-13T16:42:27.778Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2023-12-13 18:42:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=622024
;

-- UI Element: Reifung Konfiguration -> Zuordnung Reifeprodukte.Matured Product
-- Column: M_Maturing_Configuration_Line.Matured_Product_ID
-- UI Element: Reifung Konfiguration(541755,D) -> Zuordnung Reifeprodukte(547344,D) -> main -> 10 -> main.Matured Product
-- Column: M_Maturing_Configuration_Line.Matured_Product_ID
-- 2023-12-13T16:42:36.377Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723214,0,547344,551409,622029,'F',TO_TIMESTAMP('2023-12-13 18:42:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Matured Product',20,0,0,TO_TIMESTAMP('2023-12-13 18:42:36','YYYY-MM-DD HH24:MI:SS'),100)
;



-- Element: Matured_Product_ID
-- 2023-12-13T16:45:59.942Z
UPDATE AD_Element_Trl SET IsTranslated='N', Name='Gereiftes Produkts', PrintName='Gereiftes Produkts',Updated=TO_TIMESTAMP('2023-12-13 18:45:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582859 AND AD_Language='de_DE'
;

-- 2023-12-13T16:45:59.944Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582859,'de_DE') 
;

-- 2023-12-13T16:45:59.945Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582859,'de_DE') 
;

-- Element: Matured_Product_ID
-- 2023-12-13T16:46:03.677Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gereiftes Produkt', PrintName='Gereiftes Produkt',Updated=TO_TIMESTAMP('2023-12-13 18:46:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582859 AND AD_Language='de_DE'
;

-- 2023-12-13T16:46:03.680Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582859,'de_DE') 
;

-- 2023-12-13T16:46:03.681Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582859,'de_DE') 
;

-- Element: Matured_Product_ID
-- 2023-12-13T16:46:08.870Z
UPDATE AD_Element_Trl SET IsTranslated='N', Name='Gereiftes Produk', PrintName='Gereiftes Produk',Updated=TO_TIMESTAMP('2023-12-13 18:46:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582859 AND AD_Language='de_CH'
;

-- 2023-12-13T16:46:08.872Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582859,'de_CH') 
;

-- Element: Matured_Product_ID
-- 2023-12-13T16:46:12.236Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gereiftes Produkt', PrintName='Gereiftes Produkt',Updated=TO_TIMESTAMP('2023-12-13 18:46:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582859 AND AD_Language='de_CH'
;

-- 2023-12-13T16:46:12.238Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582859,'de_CH') 
;

-- Element: MaturityAge
-- 2023-12-13T16:46:30.754Z
UPDATE AD_Element_Trl SET IsTranslated='N', Name='Reifealters',Updated=TO_TIMESTAMP('2023-12-13 18:46:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582858 AND AD_Language='de_CH'
;

-- 2023-12-13T16:46:30.756Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582858,'de_CH') 
;

-- Element: MaturityAge
-- 2023-12-13T16:46:32.484Z
UPDATE AD_Element_Trl SET PrintName='Reifealters',Updated=TO_TIMESTAMP('2023-12-13 18:46:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582858 AND AD_Language='de_CH'
;

-- 2023-12-13T16:46:32.486Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582858,'de_CH') 
;

-- Element: MaturityAge
-- 2023-12-13T16:46:36.087Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reifealter', PrintName='Reifealter',Updated=TO_TIMESTAMP('2023-12-13 18:46:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582858 AND AD_Language='de_CH'
;

-- 2023-12-13T16:46:36.089Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582858,'de_CH') 
;

-- Element: MaturityAge
-- 2023-12-13T16:46:40.157Z
UPDATE AD_Element_Trl SET IsTranslated='N', Name='Reifealte', PrintName='Reifealte',Updated=TO_TIMESTAMP('2023-12-13 18:46:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582858 AND AD_Language='de_DE'
;

-- 2023-12-13T16:46:40.159Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582858,'de_DE') 
;

-- 2023-12-13T16:46:40.159Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582858,'de_DE') 
;

-- Element: MaturityAge
-- 2023-12-13T16:46:47.814Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-13 18:46:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582858 AND AD_Language='de_DE'
;

-- 2023-12-13T16:46:47.817Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582858,'de_DE') 
;

-- 2023-12-13T16:46:47.818Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582858,'de_DE') 
;

-- Element: MaturityAge
-- 2023-12-13T16:47:05.757Z
UPDATE AD_Element_Trl SET Name='Reifealter', PrintName='Reifealter',Updated=TO_TIMESTAMP('2023-12-13 18:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582858 AND AD_Language='de_DE'
;

-- 2023-12-13T16:47:05.759Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582858,'de_DE') 
;

-- 2023-12-13T16:47:05.759Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582858,'de_DE') 
;




---------------


-- 2023-12-18T10:18:18.146Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540782,0,542384,TO_TIMESTAMP('2023-12-18 12:18:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Raw_product_unique','N',TO_TIMESTAMP('2023-12-18 12:18:17','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2023-12-18T10:18:18.154Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540782 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-12-18T10:18:24.921Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587751,541385,540782,0,TO_TIMESTAMP('2023-12-18 12:18:24','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',10,TO_TIMESTAMP('2023-12-18 12:18:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-18T10:18:33.568Z
CREATE UNIQUE INDEX Raw_product_unique ON M_Maturing_Configuration_Line (From_Product_ID) WHERE IsActive='Y'
;

----

-- 2023-12-18T10:50:02.606Z
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (0,0,TO_TIMESTAMP('2023-12-18 12:50:02','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','Y','N','N','N',540128,540117,101,'NONE',130,TO_TIMESTAMP('2023-12-18 12:50:02','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2023-12-18T10:52:16.507Z
UPDATE M_HU_PI_Attribute SET PropagationType='TOPD',Updated=TO_TIMESTAMP('2023-12-18 12:52:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540117
;

-- 2023-12-18T10:55:20.439Z
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (0,0,TO_TIMESTAMP('2023-12-18 12:55:20','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','Y','N','N','N',540128,540118,100,'TOPD',9120,TO_TIMESTAMP('2023-12-18 12:55:20','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

