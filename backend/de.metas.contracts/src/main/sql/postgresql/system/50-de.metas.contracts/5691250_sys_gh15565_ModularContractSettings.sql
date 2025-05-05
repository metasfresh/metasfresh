-- Table: C_ModularContractSettings
-- 2023-06-13T05:29:36.157221900Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542339,'N',TO_TIMESTAMP('2023-06-13 08:29:35.046','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','N','Y','N','N','Y','N','N','N','N','N',0,'Modular Contract Settings','NP','L','C_ModularContractSettings','DTI',TO_TIMESTAMP('2023-06-13 08:29:35.046','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T05:29:36.164324700Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542339 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-06-13T05:29:36.634578900Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556275,TO_TIMESTAMP('2023-06-13 08:29:36.535','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table C_ModularContractSettings',1,'Y','N','Y','Y','C_ModularContractSettings','N',1000000,TO_TIMESTAMP('2023-06-13 08:29:36.535','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T05:29:36.649578200Z
CREATE SEQUENCE C_MODULARCONTRACTSETTINGS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_ModularContractSettings.AD_Client_ID
-- 2023-06-13T05:29:47.005912400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586782,102,0,19,542339,'AD_Client_ID',TO_TIMESTAMP('2023-06-13 08:29:46.839','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','de.metas.contracts',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-06-13 08:29:46.839','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T05:29:47.010948200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586782 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T05:29:47.481035900Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_ModularContractSettings.AD_Org_ID
-- 2023-06-13T05:29:48.146554800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586783,113,0,30,542339,'AD_Org_ID',TO_TIMESTAMP('2023-06-13 08:29:47.885','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','de.metas.contracts',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-06-13 08:29:47.885','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T05:29:48.149641600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586783 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T05:29:48.629967300Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_ModularContractSettings.Created
-- 2023-06-13T05:29:49.246308300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586784,245,0,16,542339,'Created',TO_TIMESTAMP('2023-06-13 08:29:49.039','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.contracts',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-06-13 08:29:49.039','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T05:29:49.249309800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586784 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T05:29:49.719111Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_ModularContractSettings.CreatedBy
-- 2023-06-13T05:29:50.364349600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586785,246,0,18,110,542339,'CreatedBy',TO_TIMESTAMP('2023-06-13 08:29:50.08','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.contracts',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-06-13 08:29:50.08','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T05:29:50.367344500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586785 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T05:29:50.834019500Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_ModularContractSettings.IsActive
-- 2023-06-13T05:29:51.478890100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586786,348,0,20,542339,'IsActive',TO_TIMESTAMP('2023-06-13 08:29:51.223','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','de.metas.contracts',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-06-13 08:29:51.223','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T05:29:51.481744200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586786 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T05:29:51.926798200Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_ModularContractSettings.Updated
-- 2023-06-13T05:29:52.498633700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586787,607,0,16,542339,'Updated',TO_TIMESTAMP('2023-06-13 08:29:52.255','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.contracts',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-06-13 08:29:52.255','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T05:29:52.501627200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586787 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T05:29:52.989957200Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_ModularContractSettings.UpdatedBy
-- 2023-06-13T05:29:53.806904Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586788,608,0,18,110,542339,'UpdatedBy',TO_TIMESTAMP('2023-06-13 08:29:53.538','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.contracts',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-06-13 08:29:53.538','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T05:29:53.807893800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586788 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T05:29:54.268490500Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-06-13T05:29:54.949787Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582425,0,'C_ModularContractSettings_ID',TO_TIMESTAMP('2023-06-13 08:29:54.836','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Modular Contract Settings','Modular Contract Settings',TO_TIMESTAMP('2023-06-13 08:29:54.836','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T05:29:54.953552700Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582425 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_ModularContractSettings.C_ModularContractSettings_ID
-- 2023-06-13T05:29:55.623831100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586789,582425,0,13,542339,'C_ModularContractSettings_ID',TO_TIMESTAMP('2023-06-13 08:29:54.834','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Modular Contract Settings',0,0,TO_TIMESTAMP('2023-06-13 08:29:54.834','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T05:29:55.625917800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586789 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T05:29:56.066617800Z
/* DDL */  select update_Column_Translation_From_AD_Element(582425) 
;

-- Column: C_ModularContractSettings.M_Product_ID
-- 2023-06-13T06:29:57.225292300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586790,454,0,30,540272,542339,'M_Product_ID',TO_TIMESTAMP('2023-06-13 09:29:56.991','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Produkt, Leistung, Artikel','de.metas.contracts',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2023-06-13 09:29:56.991','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:29:57.235344100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586790 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:29:57.676884900Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2023-06-13T06:30:11.191938700Z
/* DDL */ CREATE TABLE public.C_ModularContractSettings (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_ModularContractSettings_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_ModularContractSettings_Key PRIMARY KEY (C_ModularContractSettings_ID), CONSTRAINT MProduct_CModularContractSettings FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED)
;

-- Column: C_ModularContractSettings.C_Calendar_ID
-- 2023-06-13T06:31:33.575744900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586791,190,0,30,540260,542339,'C_Calendar_ID',TO_TIMESTAMP('2023-06-13 09:31:33.369','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Bezeichnung des Buchführungs-Kalenders','de.metas.contracts',0,10,'E','"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Kalender',0,0,TO_TIMESTAMP('2023-06-13 09:31:33.369','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:31:33.585906300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586791 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:31:34.004895200Z
/* DDL */  select update_Column_Translation_From_AD_Element(190) 
;

-- 2023-06-13T06:31:37.575404100Z
/* DDL */ SELECT public.db_alter_table('C_ModularContractSettings','ALTER TABLE public.C_ModularContractSettings ADD COLUMN C_Calendar_ID NUMERIC(10) NOT NULL')
;

-- 2023-06-13T06:31:37.589465Z
ALTER TABLE C_ModularContractSettings ADD CONSTRAINT CCalendar_CModularContractSettings FOREIGN KEY (C_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_ModularContractSettings.C_Year_ID
-- 2023-06-13T06:32:07.452000100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586792,223,0,30,540133,542339,'C_Year_ID',TO_TIMESTAMP('2023-06-13 09:32:07.27','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Kalenderjahr','de.metas.contracts',0,10,'"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Jahr',0,0,TO_TIMESTAMP('2023-06-13 09:32:07.27','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:32:07.454999900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586792 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:32:07.885269900Z
/* DDL */  select update_Column_Translation_From_AD_Element(223) 
;

-- 2023-06-13T06:32:10.075358900Z
/* DDL */ SELECT public.db_alter_table('C_ModularContractSettings','ALTER TABLE public.C_ModularContractSettings ADD COLUMN C_Year_ID NUMERIC(10) NOT NULL')
;

-- 2023-06-13T06:32:10.082761200Z
ALTER TABLE C_ModularContractSettings ADD CONSTRAINT CYear_CModularContractSettings FOREIGN KEY (C_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_ModularContractSettings.C_Year_ID
-- 2023-06-13T06:32:18.489951700Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2023-06-13 09:32:18.489','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586792
;

-- Column: C_ModularContractSettings.C_Year_ID
-- 2023-06-13T06:32:31.184987900Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-06-13 09:32:31.184','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586792
;

-- Column: C_ModularContractSettings.Name
-- 2023-06-13T06:33:05.639898Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586793,469,0,10,542339,'Name',TO_TIMESTAMP('2023-06-13 09:33:05.478','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','de.metas.contracts',0,40,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2023-06-13 09:33:05.478','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:33:05.641983600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586793 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:33:06.096775300Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2023-06-13T06:33:07.619791600Z
/* DDL */ SELECT public.db_alter_table('C_ModularContractSettings','ALTER TABLE public.C_ModularContractSettings ADD COLUMN Name VARCHAR(40) NOT NULL')
;

-- Column: C_ModularContractSettings.Name
-- 2023-06-13T06:33:14.319738500Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2023-06-13 09:33:14.319','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586793
;

-- 2023-06-13T06:33:15.047293800Z
INSERT INTO t_alter_column values('c_modularcontractsettings','Name','VARCHAR(255)',null,null)
;

-- Column: C_ModularContractSettings.M_PricingSystem_ID
-- 2023-06-13T06:34:32.299074200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586794,505135,0,30,505274,542339,'M_PricingSystem_ID',TO_TIMESTAMP('2023-06-13 09:34:32.148','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','de.metas.contracts',0,10,'Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Preissystem',0,0,TO_TIMESTAMP('2023-06-13 09:34:32.148','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:34:32.303156Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586794 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:34:32.726147200Z
/* DDL */  select update_Column_Translation_From_AD_Element(505135) 
;

-- 2023-06-13T06:34:34.866496500Z
/* DDL */ SELECT public.db_alter_table('C_ModularContractSettings','ALTER TABLE public.C_ModularContractSettings ADD COLUMN M_PricingSystem_ID NUMERIC(10)')
;

-- 2023-06-13T06:34:34.872491100Z
ALTER TABLE C_ModularContractSettings ADD CONSTRAINT MPricingSystem_CModularContractSettings FOREIGN KEY (M_PricingSystem_ID) REFERENCES public.M_PricingSystem DEFERRABLE INITIALLY DEFERRED
;

-- Table: C_ModuleSettings
-- 2023-06-13T06:35:40.881824700Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542340,'N',TO_TIMESTAMP('2023-06-13 09:35:40.631','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','N','Y','N','N','Y','N','N','N','N','N',0,'Module Settings','NP','L','C_ModuleSettings','DTI',TO_TIMESTAMP('2023-06-13 09:35:40.631','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:35:40.887845400Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542340 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-06-13T06:35:41.313312600Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556276,TO_TIMESTAMP('2023-06-13 09:35:41.194','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table C_ModuleSettings',1,'Y','N','Y','Y','C_ModuleSettings','N',1000000,TO_TIMESTAMP('2023-06-13 09:35:41.194','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:35:41.327347900Z
CREATE SEQUENCE C_MODULESETTINGS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2023-06-13T06:37:12.548491400Z
UPDATE AD_Table_Trl SET Name='Einstellungen Vertragsbausteine',Updated=TO_TIMESTAMP('2023-06-13 09:37:12.547','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542339
;

-- 2023-06-13T06:37:17.138675700Z
UPDATE AD_Table_Trl SET Name='Einstellungen Vertragsbausteine',Updated=TO_TIMESTAMP('2023-06-13 09:37:17.137','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542339
;

-- 2023-06-13T06:37:17.139675400Z
UPDATE AD_Table SET Name='Einstellungen Vertragsbausteine' WHERE AD_Table_ID=542339
;

-- 2023-06-13T06:37:21.456420500Z
UPDATE AD_Table_Trl SET Name='Einstellungen Vertragsbausteine',Updated=TO_TIMESTAMP('2023-06-13 09:37:21.455','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Table_ID=542339
;

-- Element: C_ModularContractSettings_ID
-- 2023-06-13T06:37:47.045521Z
UPDATE AD_Element_Trl SET Name='Einstellungen Vertragsbausteine', PrintName='Einstellungen Vertragsbausteine',Updated=TO_TIMESTAMP('2023-06-13 09:37:47.045','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582425 AND AD_Language='de_CH'
;

-- 2023-06-13T06:37:47.048607500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582425,'de_CH') 
;

-- Element: C_ModularContractSettings_ID
-- 2023-06-13T06:37:51.102806Z
UPDATE AD_Element_Trl SET Name='Einstellungen Vertragsbausteine', PrintName='Einstellungen Vertragsbausteine',Updated=TO_TIMESTAMP('2023-06-13 09:37:51.102','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582425 AND AD_Language='de_DE'
;

-- 2023-06-13T06:37:51.103805800Z
UPDATE AD_Element SET Name='Einstellungen Vertragsbausteine', PrintName='Einstellungen Vertragsbausteine' WHERE AD_Element_ID=582425
;

-- 2023-06-13T06:37:51.589075800Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582425,'de_DE') 
;

-- 2023-06-13T06:37:51.590076600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582425,'de_DE') 
;

-- Element: C_ModularContractSettings_ID
-- 2023-06-13T06:38:09.932593700Z
UPDATE AD_Element_Trl SET Name='Einstellungen Vertragsbausteine', PrintName='Einstellungen Vertragsbausteine',Updated=TO_TIMESTAMP('2023-06-13 09:38:09.932','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582425 AND AD_Language='fr_CH'
;

-- 2023-06-13T06:38:09.933827700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582425,'fr_CH') 
;

-- 2023-06-13T06:39:21.124497100Z
UPDATE AD_Table_Trl SET Name='Bausteine',Updated=TO_TIMESTAMP('2023-06-13 09:39:21.123','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Table_ID=542340
;

-- 2023-06-13T06:39:24.812193600Z
UPDATE AD_Table_Trl SET Name='Bausteine',Updated=TO_TIMESTAMP('2023-06-13 09:39:24.811','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542340
;

-- 2023-06-13T06:39:24.812193600Z
UPDATE AD_Table SET Name='Bausteine' WHERE AD_Table_ID=542340
;

-- 2023-06-13T06:39:27.538518200Z
UPDATE AD_Table_Trl SET Name='Bausteine',Updated=TO_TIMESTAMP('2023-06-13 09:39:27.538','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542340
;

-- Column: C_ModuleSettings.AD_Client_ID
-- 2023-06-13T06:39:53.128733900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586795,102,0,19,542340,'AD_Client_ID',TO_TIMESTAMP('2023-06-13 09:39:52.922','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','de.metas.contracts',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-06-13 09:39:52.922','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:39:53.130744800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586795 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:39:53.568628500Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_ModuleSettings.AD_Org_ID
-- 2023-06-13T06:39:54.214906700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586796,113,0,30,542340,'AD_Org_ID',TO_TIMESTAMP('2023-06-13 09:39:53.906','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','de.metas.contracts',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-06-13 09:39:53.906','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:39:54.215914900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586796 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:39:54.658606500Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_ModuleSettings.Created
-- 2023-06-13T06:39:55.322175500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586797,245,0,16,542340,'Created',TO_TIMESTAMP('2023-06-13 09:39:55.087','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.contracts',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-06-13 09:39:55.087','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:39:55.324204200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586797 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:39:55.817551800Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_ModuleSettings.CreatedBy
-- 2023-06-13T06:39:56.483357700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586798,246,0,18,110,542340,'CreatedBy',TO_TIMESTAMP('2023-06-13 09:39:56.243','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.contracts',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-06-13 09:39:56.243','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:39:56.485372500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586798 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:39:56.960058600Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_ModuleSettings.IsActive
-- 2023-06-13T06:39:57.603154200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586799,348,0,20,542340,'IsActive',TO_TIMESTAMP('2023-06-13 09:39:57.358','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','de.metas.contracts',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-06-13 09:39:57.358','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:39:57.605177300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586799 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:39:58.061444400Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_ModuleSettings.Updated
-- 2023-06-13T06:39:58.733685900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586800,607,0,16,542340,'Updated',TO_TIMESTAMP('2023-06-13 09:39:58.52','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.contracts',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-06-13 09:39:58.52','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:39:58.736671800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586800 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:39:59.169604700Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_ModuleSettings.UpdatedBy
-- 2023-06-13T06:39:59.812801500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586801,608,0,18,110,542340,'UpdatedBy',TO_TIMESTAMP('2023-06-13 09:39:59.539','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.contracts',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-06-13 09:39:59.539','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:39:59.814847200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586801 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:40:00.285796800Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-06-13T06:40:00.784288400Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582426,0,'C_ModuleSettings_ID',TO_TIMESTAMP('2023-06-13 09:40:00.679','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Bausteine','Bausteine',TO_TIMESTAMP('2023-06-13 09:40:00.679','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:40:00.788105500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582426 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_ModuleSettings.C_ModuleSettings_ID
-- 2023-06-13T06:40:01.389651700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586802,582426,0,13,542340,'C_ModuleSettings_ID',TO_TIMESTAMP('2023-06-13 09:40:00.678','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Bausteine',0,0,TO_TIMESTAMP('2023-06-13 09:40:00.678','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:40:01.391729800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586802 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:40:01.817753800Z
/* DDL */  select update_Column_Translation_From_AD_Element(582426) 
;

-- Column: C_ModuleSettings.SeqNo
-- 2023-06-13T06:41:14.315512800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586803,566,0,11,542340,'SeqNo',TO_TIMESTAMP('2023-06-13 09:41:14.175','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','de.metas.contracts',0,22,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2023-06-13 09:41:14.175','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:41:14.318533900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586803 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:41:14.849494600Z
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

-- Column: C_ModuleSettings.SeqNo
-- 2023-06-13T06:42:54.963884Z
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM C_ModuleSettings WHERE C_ModularContractSettings_ID=@C_ModularContractSettings_ID@',Updated=TO_TIMESTAMP('2023-06-13 09:42:54.963','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586803
;

-- 2023-06-13T06:42:58.073672300Z
/* DDL */ CREATE TABLE public.C_ModuleSettings (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_ModuleSettings_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, SeqNo NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_ModuleSettings_Key PRIMARY KEY (C_ModuleSettings_ID))
;

-- Column: C_ModuleSettings.C_ModularContractSettings_ID
-- 2023-06-13T06:43:51.922000800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586804,582425,0,19,542340,'C_ModularContractSettings_ID',TO_TIMESTAMP('2023-06-13 09:43:51.751','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Einstellungen Vertragsbausteine',0,0,TO_TIMESTAMP('2023-06-13 09:43:51.751','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:43:51.924038700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586804 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:43:52.564293200Z
/* DDL */  select update_Column_Translation_From_AD_Element(582425) 
;

-- Column: C_ModuleSettings.Name
-- 2023-06-13T06:44:43.068784200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586805,469,0,10,542340,'Name',TO_TIMESTAMP('2023-06-13 09:44:42.908','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','de.metas.contracts',0,40,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2023-06-13 09:44:42.908','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:44:43.073724Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586805 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:44:43.559170700Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Column: C_ModuleSettings.Name
-- 2023-06-13T06:44:50.673676300Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2023-06-13 09:44:50.673','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586805
;

-- 2023-06-13T06:44:53.944910100Z
/* DDL */ SELECT public.db_alter_table('C_ModuleSettings','ALTER TABLE public.C_ModuleSettings ADD COLUMN Name VARCHAR(40) NOT NULL')
;

-- Column: C_ModuleSettings.Name
-- 2023-06-13T06:44:58.927114500Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2023-06-13 09:44:58.927','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586805
;

-- 2023-06-13T06:44:59.707230600Z
INSERT INTO t_alter_column values('c_modulesettings','Name','VARCHAR(255)',null,null)
;

-- Column: C_ModuleSettings.M_Product_ID
-- 2023-06-13T06:45:34.723604700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586806,454,0,19,542340,'M_Product_ID',TO_TIMESTAMP('2023-06-13 09:45:34.562','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Produkt, Leistung, Artikel','de.metas.contracts',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2023-06-13 09:45:34.562','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:45:34.725662500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586806 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:45:35.140798200Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- Column: C_ModuleSettings.M_Product_ID
-- 2023-06-13T06:45:45.890435800Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=540272, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-06-13 09:45:45.89','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586806
;

-- 2023-06-13T06:45:47.049731Z
/* DDL */ SELECT public.db_alter_table('C_ModuleSettings','ALTER TABLE public.C_ModuleSettings ADD COLUMN M_Product_ID NUMERIC(10) NOT NULL')
;

-- 2023-06-13T06:45:47.054731800Z
ALTER TABLE C_ModuleSettings ADD CONSTRAINT MProduct_CModuleSettings FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2023-06-13T06:46:57.504973800Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582427,0,'InvoicingGroup',TO_TIMESTAMP('2023-06-13 09:46:57.323','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Invoicing group','Invoicing group',TO_TIMESTAMP('2023-06-13 09:46:57.323','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:46:57.509862600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582427 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: InvoicingGroup
-- 2023-06-13T06:47:28.817408900Z
UPDATE AD_Element_Trl SET Name='Abrechnungsgruppe', PrintName='Abrechnungsgruppe',Updated=TO_TIMESTAMP('2023-06-13 09:47:28.817','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582427 AND AD_Language='de_CH'
;

-- 2023-06-13T06:47:28.818387300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582427,'de_CH') 
;

-- Element: InvoicingGroup
-- 2023-06-13T06:47:34.537050700Z
UPDATE AD_Element_Trl SET Name='Abrechnungsgruppe', PrintName='Abrechnungsgruppe',Updated=TO_TIMESTAMP('2023-06-13 09:47:34.537','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582427 AND AD_Language='de_DE'
;

-- 2023-06-13T06:47:34.538161600Z
UPDATE AD_Element SET Name='Abrechnungsgruppe', PrintName='Abrechnungsgruppe' WHERE AD_Element_ID=582427
;

-- 2023-06-13T06:47:34.924528900Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582427,'de_DE') 
;

-- 2023-06-13T06:47:34.925528100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582427,'de_DE') 
;

-- Element: InvoicingGroup
-- 2023-06-13T06:47:58.730867100Z
UPDATE AD_Element_Trl SET PrintName='Abrechnungsgruppe',Updated=TO_TIMESTAMP('2023-06-13 09:47:58.73','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582427 AND AD_Language='fr_CH'
;

-- 2023-06-13T06:47:58.731872100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582427,'fr_CH') 
;

-- Element: InvoicingGroup
-- 2023-06-13T06:48:23.453311600Z
UPDATE AD_Element_Trl SET Name='Abrechnungsgruppe',Updated=TO_TIMESTAMP('2023-06-13 09:48:23.453','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582427 AND AD_Language='fr_CH'
;

-- 2023-06-13T06:48:23.455389800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582427,'fr_CH') 
;

-- Name: InvoicingGroup
-- 2023-06-13T06:49:29.281578100Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541742,TO_TIMESTAMP('2023-06-13 09:49:29.142','YYYY-MM-DD HH24:MI:SS.US'),100,'','de.metas.contracts','Y','N','InvoicingGroup',TO_TIMESTAMP('2023-06-13 09:49:29.142','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2023-06-13T06:49:29.287574100Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541742 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: InvoicingGroup
-- Value: Other
-- ValueName: Other
-- 2023-06-13T06:49:45.670576700Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543484,541742,TO_TIMESTAMP('2023-06-13 09:49:45.512','YYYY-MM-DD HH24:MI:SS.US'),100,'Other','de.metas.contracts','Y','Other',TO_TIMESTAMP('2023-06-13 09:49:45.512','YYYY-MM-DD HH24:MI:SS.US'),100,'Other','Other')
;

-- 2023-06-13T06:49:45.674561500Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543484 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_ModuleSettings.InvoicingGroup
-- 2023-06-13T06:50:07.537779100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586807,582427,0,10,542340,'InvoicingGroup',TO_TIMESTAMP('2023-06-13 09:50:07.388','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Abrechnungsgruppe',0,0,TO_TIMESTAMP('2023-06-13 09:50:07.388','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T06:50:07.539839400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586807 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T06:50:08.039278700Z
/* DDL */  select update_Column_Translation_From_AD_Element(582427) 
;

-- Column: C_ModuleSettings.InvoicingGroup
-- 2023-06-13T06:50:16.419245700Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541742,Updated=TO_TIMESTAMP('2023-06-13 09:50:16.419','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586807
;

-- 2023-06-13T06:50:18.226613100Z
/* DDL */ SELECT public.db_alter_table('C_ModuleSettings','ALTER TABLE public.C_ModuleSettings ADD COLUMN InvoicingGroup VARCHAR(255)')
;

-- Column: C_ModuleSettings.InvoicingGroup
-- 2023-06-13T06:50:24.109796300Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-06-13 09:50:24.109','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586807
;

-- Column: C_ModuleSettings.InvoicingGroup
-- 2023-06-13T06:50:42.194366300Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=2,Updated=TO_TIMESTAMP('2023-06-13 09:50:42.194','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586807
;

-- 2023-06-13T06:51:04.722698500Z
/* DDL */ SELECT public.db_alter_table('C_ModuleSettings','ALTER TABLE public.C_ModuleSettings ADD COLUMN C_ModularContractSettings_ID NUMERIC(10) NOT NULL')
;

-- 2023-06-13T06:51:04.728698900Z
ALTER TABLE C_ModuleSettings ADD CONSTRAINT CModularContractSettings_CModuleSettings FOREIGN KEY (C_ModularContractSettings_ID) REFERENCES public.C_ModularContractSettings DEFERRABLE INITIALLY DEFERRED
;

-- 2023-06-13T06:51:15.680451400Z
INSERT INTO t_alter_column values('c_modulesettings','InvoicingGroup','VARCHAR(255)',null,null)
;

-- 2023-06-13T06:51:15.681492900Z
INSERT INTO t_alter_column values('c_modulesettings','InvoicingGroup',null,'NOT NULL',null)
;

-- 2023-06-13T06:51:21.788206700Z
INSERT INTO t_alter_column values('c_modulesettings','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2023-06-13T06:51:26.490328200Z
INSERT INTO t_alter_column values('c_modulesettings','Name','VARCHAR(255)',null,null)
;

-- 2023-06-13T06:51:32.586346800Z
INSERT INTO t_alter_column values('c_modulesettings','SeqNo','NUMERIC(10)',null,null)
;

-- Window: Einstellungen Vertragsbausteine, InternalName=null
-- 2023-06-13T06:52:37.575169600Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582425,0,541712,TO_TIMESTAMP('2023-06-13 09:52:37.368','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','Y','N','N','N','N','Einstellungen Vertragsbausteine','N',TO_TIMESTAMP('2023-06-13 09:52:37.368','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-06-13T06:52:37.582123300Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541712 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-06-13T06:52:37.589234300Z
/* DDL */  select update_window_translation_from_ad_element(582425) 
;

-- 2023-06-13T06:52:37.612260100Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541712
;

-- 2023-06-13T06:52:37.622194100Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541712)
;

-- Tab: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine
-- Table: C_ModularContractSettings
-- 2023-06-13T06:53:14.397085Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582425,0,547013,542339,541712,'Y',TO_TIMESTAMP('2023-06-13 09:53:14.209','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','N','N','A','C_ModularContractSettings','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Einstellungen Vertragsbausteine','N',10,0,TO_TIMESTAMP('2023-06-13 09:53:14.209','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:53:14.402302900Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547013 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-06-13T06:53:14.407202100Z
/* DDL */  select update_tab_translation_from_ad_element(582425) 
;

-- 2023-06-13T06:53:14.410221100Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547013)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> Mandant
-- Column: C_ModularContractSettings.AD_Client_ID
-- 2023-06-13T06:53:20.445706300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586782,716337,0,547013,TO_TIMESTAMP('2023-06-13 09:53:20.231','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.contracts','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-06-13 09:53:20.231','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:53:20.453579700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716337 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:53:20.459589100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-06-13T06:53:20.804052200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716337
;

-- 2023-06-13T06:53:20.805052700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716337)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> Organisation
-- Column: C_ModularContractSettings.AD_Org_ID
-- 2023-06-13T06:53:20.933807200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586783,716338,0,547013,TO_TIMESTAMP('2023-06-13 09:53:20.807','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.contracts','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-06-13 09:53:20.807','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:53:20.934816300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716338 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:53:20.936813300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-06-13T06:53:21.117011Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716338
;

-- 2023-06-13T06:53:21.118518100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716338)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> Aktiv
-- Column: C_ModularContractSettings.IsActive
-- 2023-06-13T06:53:21.238057300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586786,716339,0,547013,TO_TIMESTAMP('2023-06-13 09:53:21.119','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.contracts','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-06-13 09:53:21.119','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:53:21.240148900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716339 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:53:21.241056900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-06-13T06:53:21.519373200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716339
;

-- 2023-06-13T06:53:21.520358500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716339)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> Einstellungen Vertragsbausteine
-- Column: C_ModularContractSettings.C_ModularContractSettings_ID
-- 2023-06-13T06:53:21.650435100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586789,716340,0,547013,TO_TIMESTAMP('2023-06-13 09:53:21.522','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Einstellungen Vertragsbausteine',TO_TIMESTAMP('2023-06-13 09:53:21.522','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:53:21.652541600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716340 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:53:21.654539Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582425) 
;

-- 2023-06-13T06:53:21.660454500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716340
;

-- 2023-06-13T06:53:21.661456100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716340)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> Produkt
-- Column: C_ModularContractSettings.M_Product_ID
-- 2023-06-13T06:53:21.784481200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586790,716341,0,547013,TO_TIMESTAMP('2023-06-13 09:53:21.663','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel',10,'de.metas.contracts','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2023-06-13 09:53:21.663','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:53:21.785563100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716341 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:53:21.788485800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2023-06-13T06:53:21.839514100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716341
;

-- 2023-06-13T06:53:21.839514100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716341)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> Kalender
-- Column: C_ModularContractSettings.C_Calendar_ID
-- 2023-06-13T06:53:21.937607500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586791,716342,0,547013,TO_TIMESTAMP('2023-06-13 09:53:21.841','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnung des Buchführungs-Kalenders',10,'de.metas.contracts','"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.','Y','N','N','N','N','N','N','N','Kalender',TO_TIMESTAMP('2023-06-13 09:53:21.841','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:53:21.939543300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716342 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:53:21.941542900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(190) 
;

-- 2023-06-13T06:53:21.964089500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716342
;

-- 2023-06-13T06:53:21.964089500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716342)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> Jahr
-- Column: C_ModularContractSettings.C_Year_ID
-- 2023-06-13T06:53:22.073780700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586792,716343,0,547013,TO_TIMESTAMP('2023-06-13 09:53:21.968','YYYY-MM-DD HH24:MI:SS.US'),100,'Kalenderjahr',10,'de.metas.contracts','"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','N','N','N','N','N','N','Jahr',TO_TIMESTAMP('2023-06-13 09:53:21.968','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:53:22.074813200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716343 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:53:22.076863500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(223) 
;

-- 2023-06-13T06:53:22.089976300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716343
;

-- 2023-06-13T06:53:22.091066700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716343)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> Name
-- Column: C_ModularContractSettings.Name
-- 2023-06-13T06:53:22.210488100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586793,716344,0,547013,TO_TIMESTAMP('2023-06-13 09:53:22.092','YYYY-MM-DD HH24:MI:SS.US'),100,'',255,'de.metas.contracts','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-06-13 09:53:22.092','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:53:22.212524800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716344 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:53:22.214448800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2023-06-13T06:53:22.299634Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716344
;

-- 2023-06-13T06:53:22.299634Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716344)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> Preissystem
-- Column: C_ModularContractSettings.M_PricingSystem_ID
-- 2023-06-13T06:53:22.411682900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586794,716345,0,547013,TO_TIMESTAMP('2023-06-13 09:53:22.301','YYYY-MM-DD HH24:MI:SS.US'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.',10,'de.metas.contracts','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','N','N','N','N','N','N','Preissystem',TO_TIMESTAMP('2023-06-13 09:53:22.301','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:53:22.413729300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716345 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:53:22.415738200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505135) 
;

-- 2023-06-13T06:53:22.443515100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716345
;

-- 2023-06-13T06:53:22.444474100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716345)
;

-- Tab: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine
-- Table: C_ModuleSettings
-- 2023-06-13T06:53:49.116137700Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582426,0,547014,542340,541712,'Y',TO_TIMESTAMP('2023-06-13 09:53:48.958','YYYY-MM-DD HH24:MI:SS.US'),100,'U','N','N','A','C_ModuleSettings','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Bausteine','N',20,0,TO_TIMESTAMP('2023-06-13 09:53:48.958','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:53:49.120129800Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547014 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-06-13T06:53:49.124094600Z
/* DDL */  select update_tab_translation_from_ad_element(582426) 
;

-- 2023-06-13T06:53:49.131363100Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547014)
;

-- Tab: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine
-- Table: C_ModuleSettings
-- 2023-06-13T06:53:56.701119200Z
UPDATE AD_Tab SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2023-06-13 09:53:56.701','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547014
;

-- Tab: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine
-- Table: C_ModuleSettings
-- 2023-06-13T06:54:05.993434Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2023-06-13 09:54:05.993','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547014
;

-- Tab: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine
-- Table: C_ModuleSettings
-- 2023-06-13T06:54:18.865225Z
UPDATE AD_Tab SET AllowQuickInput='N',Updated=TO_TIMESTAMP('2023-06-13 09:54:18.865','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547014
;

-- Tab: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine
-- Table: C_ModuleSettings
-- 2023-06-13T06:54:28.361985500Z
UPDATE AD_Tab SET AD_Column_ID=586804, Parent_Column_ID=586789,Updated=TO_TIMESTAMP('2023-06-13 09:54:28.361','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547014
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Mandant
-- Column: C_ModuleSettings.AD_Client_ID
-- 2023-06-13T06:54:35.531466500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586795,716346,0,547014,TO_TIMESTAMP('2023-06-13 09:54:35.381','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.contracts','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-06-13 09:54:35.381','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:54:35.536567500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716346 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:54:35.541558800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-06-13T06:54:35.636475200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716346
;

-- 2023-06-13T06:54:35.637305100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716346)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Organisation
-- Column: C_ModuleSettings.AD_Org_ID
-- 2023-06-13T06:54:35.751911800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586796,716347,0,547014,TO_TIMESTAMP('2023-06-13 09:54:35.64','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.contracts','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-06-13 09:54:35.64','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:54:35.753079500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716347 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:54:35.755183500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-06-13T06:54:35.821201300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716347
;

-- 2023-06-13T06:54:35.821939500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716347)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Aktiv
-- Column: C_ModuleSettings.IsActive
-- 2023-06-13T06:54:35.923843600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586799,716348,0,547014,TO_TIMESTAMP('2023-06-13 09:54:35.823','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.contracts','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-06-13 09:54:35.823','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:54:35.925893Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716348 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:54:35.928055200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-06-13T06:54:36.010231100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716348
;

-- 2023-06-13T06:54:36.010978300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716348)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Bausteine
-- Column: C_ModuleSettings.C_ModuleSettings_ID
-- 2023-06-13T06:54:36.101810600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586802,716349,0,547014,TO_TIMESTAMP('2023-06-13 09:54:36.012','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Bausteine',TO_TIMESTAMP('2023-06-13 09:54:36.012','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:54:36.102895800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716349 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:54:36.104892700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582426) 
;

-- 2023-06-13T06:54:36.109905800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716349
;

-- 2023-06-13T06:54:36.110855200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716349)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Reihenfolge
-- Column: C_ModuleSettings.SeqNo
-- 2023-06-13T06:54:36.201492900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586803,716350,0,547014,TO_TIMESTAMP('2023-06-13 09:54:36.112','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',22,'de.metas.contracts','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2023-06-13 09:54:36.112','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:54:36.202583400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716350 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:54:36.205495Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2023-06-13T06:54:36.243749900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716350
;

-- 2023-06-13T06:54:36.243749900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716350)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Einstellungen Vertragsbausteine
-- Column: C_ModuleSettings.C_ModularContractSettings_ID
-- 2023-06-13T06:54:36.342388700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586804,716351,0,547014,TO_TIMESTAMP('2023-06-13 09:54:36.245','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Einstellungen Vertragsbausteine',TO_TIMESTAMP('2023-06-13 09:54:36.245','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:54:36.344476700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716351 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:54:36.346396900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582425) 
;

-- 2023-06-13T06:54:36.356470300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716351
;

-- 2023-06-13T06:54:36.357468200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716351)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Name
-- Column: C_ModuleSettings.Name
-- 2023-06-13T06:54:36.478973300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586805,716352,0,547014,TO_TIMESTAMP('2023-06-13 09:54:36.359','YYYY-MM-DD HH24:MI:SS.US'),100,'',255,'de.metas.contracts','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-06-13 09:54:36.359','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:54:36.480821700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716352 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:54:36.481953400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2023-06-13T06:54:36.524812600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716352
;

-- 2023-06-13T06:54:36.525761200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716352)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Produkt
-- Column: C_ModuleSettings.M_Product_ID
-- 2023-06-13T06:54:36.626615700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586806,716353,0,547014,TO_TIMESTAMP('2023-06-13 09:54:36.526','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel',10,'de.metas.contracts','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2023-06-13 09:54:36.526','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:54:36.626615700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716353 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:54:36.627640200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2023-06-13T06:54:36.655700400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716353
;

-- 2023-06-13T06:54:36.656395400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716353)
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Abrechnungsgruppe
-- Column: C_ModuleSettings.InvoicingGroup
-- 2023-06-13T06:54:36.746797200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586807,716354,0,547014,TO_TIMESTAMP('2023-06-13 09:54:36.657','YYYY-MM-DD HH24:MI:SS.US'),100,255,'de.metas.contracts','Y','N','N','N','N','N','N','N','Abrechnungsgruppe',TO_TIMESTAMP('2023-06-13 09:54:36.657','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T06:54:36.747793500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716354 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T06:54:36.749877700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582427) 
;

-- 2023-06-13T06:54:36.754155100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716354
;

-- 2023-06-13T06:54:36.754691200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716354)
;

-- Tab: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts)
-- UI Section: main
-- 2023-06-13T06:55:06.697222700Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547013,545621,TO_TIMESTAMP('2023-06-13 09:55:06.558','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-06-13 09:55:06.558','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-06-13T06:55:06.702264100Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545621 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> main
-- UI Column: 10
-- 2023-06-13T06:55:06.877251Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546865,545621,TO_TIMESTAMP('2023-06-13 09:55:06.741','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-06-13 09:55:06.741','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> main
-- UI Column: 20
-- 2023-06-13T06:55:06.993824700Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546866,545621,TO_TIMESTAMP('2023-06-13 09:55:06.892','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-06-13 09:55:06.892','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> main -> 10
-- UI Element Group: default
-- 2023-06-13T06:55:07.179027700Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546865,550782,TO_TIMESTAMP('2023-06-13 09:55:07.024','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-06-13 09:55:07.024','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Tab: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts)
-- UI Section: main
-- 2023-06-13T06:55:07.303238800Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547014,545622,TO_TIMESTAMP('2023-06-13 09:55:07.194','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-06-13 09:55:07.194','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-06-13T06:55:07.305258600Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545622 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main
-- UI Column: 10
-- 2023-06-13T06:55:07.419923900Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546867,545622,TO_TIMESTAMP('2023-06-13 09:55:07.308','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-06-13 09:55:07.308','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main -> 10
-- UI Element Group: default
-- 2023-06-13T06:55:07.539551600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546867,550783,TO_TIMESTAMP('2023-06-13 09:55:07.423','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-06-13 09:55:07.423','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> main -> 10 -> default.Name
-- Column: C_ModularContractSettings.Name
-- 2023-06-13T07:33:44.847533500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716344,0,547013,617984,550782,'F',TO_TIMESTAMP('2023-06-13 10:33:44.446','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2023-06-13 10:33:44.446','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> main -> 10 -> default.Produkt
-- Column: C_ModularContractSettings.M_Product_ID
-- 2023-06-13T07:34:03.551228300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716341,0,547013,617985,550782,'F',TO_TIMESTAMP('2023-06-13 10:34:03.351','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',20,0,0,TO_TIMESTAMP('2023-06-13 10:34:03.351','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> main -> 10 -> default.Kalender
-- Column: C_ModularContractSettings.C_Calendar_ID
-- 2023-06-13T07:34:17.409485800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716342,0,547013,617986,550782,'F',TO_TIMESTAMP('2023-06-13 10:34:17.189','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnung des Buchführungs-Kalenders','"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.','Y','N','N','Y','N','N','N',0,'Kalender',30,0,0,TO_TIMESTAMP('2023-06-13 10:34:17.189','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> main -> 10 -> default.Jahr
-- Column: C_ModularContractSettings.C_Year_ID
-- 2023-06-13T07:34:48.400020Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716343,0,547013,617987,550782,'F',TO_TIMESTAMP('2023-06-13 10:34:48.228','YYYY-MM-DD HH24:MI:SS.US'),100,'Kalenderjahr','"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','N','Y','N','N','N',0,'Jahr',40,0,0,TO_TIMESTAMP('2023-06-13 10:34:48.228','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> main -> 10
-- UI Element Group: pricing
-- 2023-06-13T07:35:12.114277500Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546865,550784,TO_TIMESTAMP('2023-06-13 10:35:11.969','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','pricing',20,TO_TIMESTAMP('2023-06-13 10:35:11.969','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> main -> 10 -> pricing.Preissystem
-- Column: C_ModularContractSettings.M_PricingSystem_ID
-- 2023-06-13T07:35:27.545299700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716345,0,547013,617988,550784,'F',TO_TIMESTAMP('2023-06-13 10:35:27.348','YYYY-MM-DD HH24:MI:SS.US'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','N','Y','N','N','N',0,'Preissystem',10,0,0,TO_TIMESTAMP('2023-06-13 10:35:27.348','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> main -> 20
-- UI Element Group: org+client
-- 2023-06-13T07:37:39.104180200Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546866,550785,TO_TIMESTAMP('2023-06-13 10:37:38.903','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org+client',10,TO_TIMESTAMP('2023-06-13 10:37:38.903','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> main -> 20 -> org+client.Organisation
-- Column: C_ModularContractSettings.AD_Org_ID
-- 2023-06-13T07:37:52.133667800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716338,0,547013,617989,550785,'F',TO_TIMESTAMP('2023-06-13 10:37:51.971','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-06-13 10:37:51.971','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> main -> 20 -> org+client.Mandant
-- Column: C_ModularContractSettings.AD_Client_ID
-- 2023-06-13T07:38:14.169164200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716337,0,547013,617990,550785,'F',TO_TIMESTAMP('2023-06-13 10:38:13.973','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-06-13 10:38:13.973','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main -> 10 -> default.Reihenfolge
-- Column: C_ModuleSettings.SeqNo
-- 2023-06-13T07:41:29.438026700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716350,0,547014,617991,550783,'F',TO_TIMESTAMP('2023-06-13 10:41:29.257','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',10,0,0,TO_TIMESTAMP('2023-06-13 10:41:29.257','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main -> 10 -> default.Name
-- Column: C_ModuleSettings.Name
-- 2023-06-13T08:02:11.080840500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716352,0,547014,617992,550783,'F',TO_TIMESTAMP('2023-06-13 11:02:10.928','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2023-06-13 11:02:10.928','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main -> 10 -> default.Abrechnungsgruppe
-- Column: C_ModuleSettings.InvoicingGroup
-- 2023-06-13T08:02:26.251651100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716354,0,547014,617993,550783,'F',TO_TIMESTAMP('2023-06-13 11:02:26.064','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Abrechnungsgruppe',30,0,0,TO_TIMESTAMP('2023-06-13 11:02:26.064','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main -> 10 -> default.Produkt
-- Column: C_ModuleSettings.M_Product_ID
-- 2023-06-13T08:02:34.742146700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716353,0,547014,617994,550783,'F',TO_TIMESTAMP('2023-06-13 11:02:34.429','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',40,0,0,TO_TIMESTAMP('2023-06-13 11:02:34.429','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main -> 10 -> default.Reihenfolge
-- Column: C_ModuleSettings.SeqNo
-- 2023-06-13T08:02:53.015503400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-06-13 11:02:53.015','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617991
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main -> 10 -> default.Name
-- Column: C_ModuleSettings.Name
-- 2023-06-13T08:02:53.026269700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-06-13 11:02:53.025','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617992
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main -> 10 -> default.Abrechnungsgruppe
-- Column: C_ModuleSettings.InvoicingGroup
-- 2023-06-13T08:02:53.033864500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-06-13 11:02:53.033','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617993
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main -> 10 -> default.Produkt
-- Column: C_ModuleSettings.M_Product_ID
-- 2023-06-13T08:02:53.041030300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-06-13 11:02:53.041','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617994
;

-- Name: Einstellungen Vertragsbausteine
-- Action Type: W
-- Window: Einstellungen Vertragsbausteine(541712,de.metas.contracts)
-- 2023-06-13T08:34:39.038671500Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582425,542088,0,541712,TO_TIMESTAMP('2023-06-13 11:34:38.677','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Modular Contract Settings_541712','Y','N','N','N','N','Einstellungen Vertragsbausteine',TO_TIMESTAMP('2023-06-13 11:34:38.677','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T08:34:39.047214500Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542088 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-06-13T08:34:39.057249400Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542088, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542088)
;

-- 2023-06-13T08:34:39.094504400Z
/* DDL */  select update_menu_translation_from_ad_element(582425) 
;

-- Reordering children of `Contract Management`
-- Node name: `Contractpartner (C_Flatrate_Data)`
-- 2023-06-13T08:34:47.400757300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Term)`
-- 2023-06-13T08:34:47.401758700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540951 AND AD_Tree_ID=10
;

-- Node name: `Contract Invoicecandidates (C_Invoice_Clearing_Alloc)`
-- 2023-06-13T08:34:47.402757700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540952 AND AD_Tree_ID=10
;

-- Node name: `Subscription History (C_SubscriptionProgress)`
-- 2023-06-13T08:34:47.402757700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540953 AND AD_Tree_ID=10
;

-- Node name: `Contract Terms (C_Flatrate_Conditions)`
-- 2023-06-13T08:34:47.404173100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Transition)`
-- 2023-06-13T08:34:47.404173100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- Node name: `Subscriptions import (I_Flatrate_Term)`
-- 2023-06-13T08:34:47.405173400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540920 AND AD_Tree_ID=10
;

-- Node name: `Membership Month (C_MembershipMonth)`
-- 2023-06-13T08:34:47.405173400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541740 AND AD_Tree_ID=10
;

-- Node name: `C_SubscrDiscount (C_SubscrDiscount)`
-- 2023-06-13T08:34:47.406173100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541766 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Flatrate Term (C_InterimInvoice_FlatrateTerm)`
-- 2023-06-13T08:34:47.406173100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541979 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-06-13T08:34:47.407173300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Settings (C_Interim_Invoice_Settings)`
-- 2023-06-13T08:34:47.407173300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541974 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-06-13T08:34:47.408175300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- Node name: `Type specific settings`
-- 2023-06-13T08:34:47.409175700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- Node name: `Create/Update Customer Retentions (de.metas.contracts.process.C_Customer_Retention_CreateUpdate)`
-- 2023-06-13T08:34:47.410176400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541170 AND AD_Tree_ID=10
;

-- Node name: `Call-off order overview (C_CallOrderSummary)`
-- 2023-06-13T08:34:47.411176800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541909 AND AD_Tree_ID=10
;

-- Node name: `Einstellungen Vertragsbausteine`
-- 2023-06-13T08:34:47.411176800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542088 AND AD_Tree_ID=10
;

-- 2023-06-13T08:38:12.214793Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582430,0,TO_TIMESTAMP('2023-06-13 11:38:12.069','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Erntekalender','Erntekalender',TO_TIMESTAMP('2023-06-13 11:38:12.069','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T08:38:13.063190400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582430 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-06-13T08:38:56.811274200Z
UPDATE AD_Element_Trl SET Name='Harvest calendar', PrintName='Harvest calendar',Updated=TO_TIMESTAMP('2023-06-13 11:38:56.811','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582430 AND AD_Language='en_US'
;

-- 2023-06-13T08:38:56.815830300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582430,'en_US') 
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Einstellungen Vertragsbausteine(547013,de.metas.contracts) -> Erntekalender
-- Column: C_ModularContractSettings.C_Calendar_ID
-- 2023-06-13T08:39:24.015603300Z
UPDATE AD_Field SET AD_Name_ID=582430, Description=NULL, Help=NULL, Name='Erntekalender',Updated=TO_TIMESTAMP('2023-06-13 11:39:24.014','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716342
;

-- 2023-06-13T08:39:24.018602700Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Erntekalender' WHERE AD_Field_ID=716342 AND AD_Language='de_DE'
;

-- 2023-06-13T08:39:24.019601300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582430) 
;

-- 2023-06-13T08:39:24.029820800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716342
;

-- 2023-06-13T08:39:24.040112400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716342)
;

-- 2023-06-13T08:39:44.576886700Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582431,0,TO_TIMESTAMP('2023-06-13 11:39:44.401','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Sequenz','Sequenz',TO_TIMESTAMP('2023-06-13 11:39:44.401','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T08:39:44.581859Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582431 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-06-13T08:39:51.828416100Z
UPDATE AD_Element SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2023-06-13 11:39:51.827','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582431
;

-- 2023-06-13T08:39:51.832790500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582431,'de_DE') 
;

-- Element: null
-- 2023-06-13T08:40:19.917634500Z
UPDATE AD_Element_Trl SET Name='Sequence', PrintName='Sequence',Updated=TO_TIMESTAMP('2023-06-13 11:40:19.917','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582431 AND AD_Language='en_US'
;

-- 2023-06-13T08:40:19.920704Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582431,'en_US') 
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Sequenz
-- Column: C_ModuleSettings.SeqNo
-- 2023-06-13T08:40:53.757758400Z
UPDATE AD_Field SET AD_Name_ID=582431, Description=NULL, Help=NULL, Name='Sequenz',Updated=TO_TIMESTAMP('2023-06-13 11:40:53.757','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716350
;

-- 2023-06-13T08:40:53.758763500Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Sequenz' WHERE AD_Field_ID=716350 AND AD_Language='de_DE'
;

-- 2023-06-13T08:40:53.761575700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582431) 
;

-- 2023-06-13T08:40:53.769448700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716350
;

-- 2023-06-13T08:40:53.771046300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716350)
;

