-- Table: C_Project_Resource_Budget_Simulation
-- 2022-06-26T10:47:05.773124800Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542177,'N',TO_TIMESTAMP('2022-06-26 13:47:05','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','Y','N','N','N',0,'Project Resource Budget Simulation','NP','L','C_Project_Resource_Budget_Simulation','DTI',TO_TIMESTAMP('2022-06-26 13:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T10:47:05.776125400Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542177 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-06-26T10:47:05.885412600Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555968,TO_TIMESTAMP('2022-06-26 13:47:05','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Project_Resource_Budget_Simulation',1,'Y','N','Y','Y','C_Project_Resource_Budget_Simulation','N',1000000,TO_TIMESTAMP('2022-06-26 13:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T10:47:05.901409600Z
CREATE SEQUENCE C_PROJECT_RESOURCE_BUDGET_SIMULATION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Project_Resource_Budget_Simulation.AD_Client_ID
-- 2022-06-26T10:47:11.305036800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583528,102,0,19,542177,'AD_Client_ID',TO_TIMESTAMP('2022-06-26 13:47:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-06-26 13:47:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T10:47:11.309032200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583528 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T10:47:11.344065700Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Project_Resource_Budget_Simulation.AD_Org_ID
-- 2022-06-26T10:47:12.550031500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583529,113,0,30,542177,'AD_Org_ID',TO_TIMESTAMP('2022-06-26 13:47:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-06-26 13:47:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T10:47:12.553032500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583529 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T10:47:12.556032400Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Project_Resource_Budget_Simulation.Created
-- 2022-06-26T10:47:13.268082Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583530,245,0,16,542177,'Created',TO_TIMESTAMP('2022-06-26 13:47:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-06-26 13:47:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T10:47:13.271082Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583530 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T10:47:13.275083800Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Project_Resource_Budget_Simulation.CreatedBy
-- 2022-06-26T10:47:14.149663800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583531,246,0,18,110,542177,'CreatedBy',TO_TIMESTAMP('2022-06-26 13:47:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-06-26 13:47:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T10:47:14.153663300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583531 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T10:47:14.157665300Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Project_Resource_Budget_Simulation.IsActive
-- 2022-06-26T10:47:16.060275500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583532,348,0,20,542177,'IsActive',TO_TIMESTAMP('2022-06-26 13:47:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-06-26 13:47:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T10:47:16.063274900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583532 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T10:47:16.068274800Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Project_Resource_Budget_Simulation.Updated
-- 2022-06-26T10:47:18.121867200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583533,607,0,16,542177,'Updated',TO_TIMESTAMP('2022-06-26 13:47:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-06-26 13:47:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T10:47:18.124866900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583533 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T10:47:18.128868300Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Project_Resource_Budget_Simulation.UpdatedBy
-- 2022-06-26T10:47:19.219865300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583534,608,0,18,110,542177,'UpdatedBy',TO_TIMESTAMP('2022-06-26 13:47:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-06-26 13:47:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T10:47:19.224864700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583534 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T10:47:19.235866600Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-06-26T10:47:20.330099Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581067,0,'C_Project_Resource_Budget_Simulation_ID',TO_TIMESTAMP('2022-06-26 13:47:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Project Resource Budget Simulation','Project Resource Budget Simulation',TO_TIMESTAMP('2022-06-26 13:47:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T10:47:20.335095800Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581067 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_Resource_Budget_Simulation.C_Project_Resource_Budget_Simulation_ID
-- 2022-06-26T10:47:20.989096100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583535,581067,0,13,542177,'C_Project_Resource_Budget_Simulation_ID',TO_TIMESTAMP('2022-06-26 13:47:20','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Project Resource Budget Simulation',0,0,TO_TIMESTAMP('2022-06-26 13:47:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T10:47:20.992095600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583535 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T10:47:20.996095300Z
/* DDL */  select update_Column_Translation_From_AD_Element(581067) 
;

-- Column: C_Project_Resource_Budget_Simulation.DateStartPlan
-- 2022-06-26T10:52:49.049995200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583536,2901,0,16,542177,'DateStartPlan',TO_TIMESTAMP('2022-06-26 13:52:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Planned Start Date','D',0,7,'Date when you plan to start','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Start Plan',0,0,TO_TIMESTAMP('2022-06-26 13:52:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T10:52:49.052993700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583536 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T10:52:49.058996200Z
/* DDL */  select update_Column_Translation_From_AD_Element(2901) 
;

-- Column: C_Project_Resource_Budget_Simulation.DateFinishPlan
-- 2022-06-26T10:53:02.449563Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583537,541244,0,16,542177,'DateFinishPlan',TO_TIMESTAMP('2022-06-26 13:53:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Planned Finish Date','de.metas.swat',0,7,'Date when you plan to finish','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Finish Plan',0,0,TO_TIMESTAMP('2022-06-26 13:53:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T10:53:02.452563100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583537 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T10:53:02.456562300Z
/* DDL */  select update_Column_Translation_From_AD_Element(541244) 
;

-- Table: C_Project_Resource_Budget_Simulation
-- 2022-06-26T10:54:15.975725300Z
UPDATE AD_Table SET AD_Window_ID=541541,Updated=TO_TIMESTAMP('2022-06-26 13:54:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542177
;

-- Column: C_Project_Resource_Budget_Simulation.C_SimulationPlan_ID
-- 2022-06-26T10:55:54.798507400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583538,581064,0,30,542177,'C_SimulationPlan_ID',TO_TIMESTAMP('2022-06-26 13:55:54','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Simulation Plan',0,0,TO_TIMESTAMP('2022-06-26 13:55:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T10:55:54.801505500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583538 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T10:55:54.805505600Z
/* DDL */  select update_Column_Translation_From_AD_Element(581064) 
;

-- Column: C_Project_Resource_Budget_Simulation.C_Project_ID
-- 2022-06-26T10:56:08.578317600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583539,208,0,30,542177,'C_Project_ID',TO_TIMESTAMP('2022-06-26 13:56:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Financial Project','D',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N',0,'Projekt',0,0,TO_TIMESTAMP('2022-06-26 13:56:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T10:56:08.580317900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583539 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T10:56:08.584318800Z
/* DDL */  select update_Column_Translation_From_AD_Element(208) 
;

-- Column: C_Project_Resource_Budget_Simulation.C_Project_Resource_Budget_ID
-- 2022-06-26T10:56:26.023499300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583540,580947,0,30,542177,'C_Project_Resource_Budget_ID',TO_TIMESTAMP('2022-06-26 13:56:25','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N',0,'Project Resource Budget',0,0,TO_TIMESTAMP('2022-06-26 13:56:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T10:56:26.026498700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583540 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T10:56:26.029536Z
/* DDL */  select update_Column_Translation_From_AD_Element(580947) 
;

-- 2022-06-26T11:01:33.494495800Z
/* DDL */ CREATE TABLE public.C_Project_Resource_Budget_Simulation (
	  AD_Client_ID NUMERIC(10) NOT NULL
	, AD_Org_ID NUMERIC(10) NOT NULL
	, C_Project_ID NUMERIC(10) NOT NULL
	, C_Project_Resource_Budget_ID NUMERIC(10) NOT NULL
	, C_Project_Resource_Budget_Simulation_ID NUMERIC(10) NOT NULL
	, C_SimulationPlan_ID NUMERIC(10) NOT NULL
	, Created TIMESTAMP WITH TIME ZONE NOT NULL
	, CreatedBy NUMERIC(10) NOT NULL
	, DateFinishPlan TIMESTAMP WITH TIME ZONE NOT NULL
	, DateStartPlan TIMESTAMP WITH TIME ZONE NOT NULL
	, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL
	, Updated TIMESTAMP WITH TIME ZONE NOT NULL
	, UpdatedBy NUMERIC(10) NOT NULL
	, CONSTRAINT CProject_CProjectResourceBudgetSimulation FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED
	, CONSTRAINT CProjectResourceBudget_CProjectResourceBudgetSimulation FOREIGN KEY (C_Project_Resource_Budget_ID) REFERENCES public.C_Project_Resource_Budget DEFERRABLE INITIALLY DEFERRED
	, CONSTRAINT C_Project_Resource_Budget_Simulation_Key PRIMARY KEY (C_Project_Resource_Budget_Simulation_ID)
	, CONSTRAINT CSimulationPlan_CProjectResourceBudgetSimulation FOREIGN KEY (C_SimulationPlan_ID) REFERENCES public.C_SimulationPlan DEFERRABLE INITIALLY DEFERRED
)
;

-- Tab: Simulation Plan -> Project Resource Budget Simulation
-- Table: C_Project_Resource_Budget_Simulation
-- 2022-06-26T11:03:45.616668700Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord
,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (0,583538,581067,0,546394,542177,541541,'Y',TO_TIMESTAMP('2022-06-26 14:03:45','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_Resource_Budget_Simulation','Y','N','Y','Y','N','N','N','N'
,'Y','Y','N','N','Y','Y','N','N','N',0,'Project Resource Budget Simulation',583496,'N',40,1,TO_TIMESTAMP('2022-06-26 14:03:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T11:03:45.620707100Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546394 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-06-26T11:03:45.623667400Z
/* DDL */  select update_tab_translation_from_ad_element(581067) 
;

-- 2022-06-26T11:03:45.637666600Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546394)
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Mandant
-- Column: C_Project_Resource_Budget_Simulation.AD_Client_ID
-- 2022-06-26T11:03:48.348471700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583528,700789,0,546394,TO_TIMESTAMP('2022-06-26 14:03:48','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-06-26 14:03:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T11:03:48.351474400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700789 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T11:03:48.356505300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-06-26T11:03:49.177522100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700789
;

-- 2022-06-26T11:03:49.180522100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700789)
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Sektion
-- Column: C_Project_Resource_Budget_Simulation.AD_Org_ID
-- 2022-06-26T11:03:49.287523900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583529,700790,0,546394,TO_TIMESTAMP('2022-06-26 14:03:49','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-06-26 14:03:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T11:03:49.289523300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700790 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T11:03:49.290523800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-06-26T11:03:49.808000500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700790
;

-- 2022-06-26T11:03:49.809002700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700790)
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Aktiv
-- Column: C_Project_Resource_Budget_Simulation.IsActive
-- 2022-06-26T11:03:49.915492800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583532,700791,0,546394,TO_TIMESTAMP('2022-06-26 14:03:49','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-06-26 14:03:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T11:03:49.916489Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700791 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T11:03:49.918490Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-06-26T11:03:50.467534200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700791
;

-- 2022-06-26T11:03:50.467534200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700791)
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Project Resource Budget Simulation
-- Column: C_Project_Resource_Budget_Simulation.C_Project_Resource_Budget_Simulation_ID
-- 2022-06-26T11:03:50.562684100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583535,700792,0,546394,TO_TIMESTAMP('2022-06-26 14:03:50','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Resource Budget Simulation',TO_TIMESTAMP('2022-06-26 14:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T11:03:50.565688800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700792 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T11:03:50.567687500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581067) 
;

-- 2022-06-26T11:03:50.569721500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700792
;

-- 2022-06-26T11:03:50.570686Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700792)
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Start Plan
-- Column: C_Project_Resource_Budget_Simulation.DateStartPlan
-- 2022-06-26T11:03:50.677576400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583536,700793,0,546394,TO_TIMESTAMP('2022-06-26 14:03:50','YYYY-MM-DD HH24:MI:SS'),100,'Planned Start Date',7,'D','Date when you plan to start','Y','N','N','N','N','N','N','N','Start Plan',TO_TIMESTAMP('2022-06-26 14:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T11:03:50.679601400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700793 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T11:03:50.681569100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2901) 
;

-- 2022-06-26T11:03:50.690584300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700793
;

-- 2022-06-26T11:03:50.690584300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700793)
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Finish Plan
-- Column: C_Project_Resource_Budget_Simulation.DateFinishPlan
-- 2022-06-26T11:03:50.787159800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583537,700794,0,546394,TO_TIMESTAMP('2022-06-26 14:03:50','YYYY-MM-DD HH24:MI:SS'),100,'Planned Finish Date',7,'D','Date when you plan to finish','Y','N','N','N','N','N','N','N','Finish Plan',TO_TIMESTAMP('2022-06-26 14:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T11:03:50.789159800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700794 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T11:03:50.791157500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541244) 
;

-- 2022-06-26T11:03:50.794193900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700794
;

-- 2022-06-26T11:03:50.794193900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700794)
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Simulation Plan
-- Column: C_Project_Resource_Budget_Simulation.C_SimulationPlan_ID
-- 2022-06-26T11:03:50.890228Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583538,700795,0,546394,TO_TIMESTAMP('2022-06-26 14:03:50','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Simulation Plan',TO_TIMESTAMP('2022-06-26 14:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T11:03:50.892221400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700795 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T11:03:50.894258600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581064) 
;

-- 2022-06-26T11:03:50.896258900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700795
;

-- 2022-06-26T11:03:50.896258900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700795)
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Projekt
-- Column: C_Project_Resource_Budget_Simulation.C_Project_ID
-- 2022-06-26T11:03:50.995012100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583539,700796,0,546394,TO_TIMESTAMP('2022-06-26 14:03:50','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2022-06-26 14:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T11:03:50.998010300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700796 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T11:03:50.999011Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-06-26T11:03:51.044004700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700796
;

-- 2022-06-26T11:03:51.044004700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700796)
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Project Resource Budget
-- Column: C_Project_Resource_Budget_Simulation.C_Project_Resource_Budget_ID
-- 2022-06-26T11:03:51.144323500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583540,700797,0,546394,TO_TIMESTAMP('2022-06-26 14:03:51','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Resource Budget',TO_TIMESTAMP('2022-06-26 14:03:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T11:03:51.146295800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700797 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T11:03:51.147295600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580947) 
;

-- 2022-06-26T11:03:51.149307400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700797
;

-- 2022-06-26T11:03:51.150315900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700797)
;

-- 2022-06-26T11:04:01.092747Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546394,545034,TO_TIMESTAMP('2022-06-26 14:04:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-06-26 14:04:00','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-06-26T11:04:01.095784700Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545034 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-06-26T11:04:06.159995300Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546096,545034,TO_TIMESTAMP('2022-06-26 14:04:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-06-26 14:04:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T11:04:07.915335400Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546097,545034,TO_TIMESTAMP('2022-06-26 14:04:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-06-26 14:04:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T11:04:17.490296500Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546096,549394,TO_TIMESTAMP('2022-06-26 14:04:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2022-06-26 14:04:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Project Resource Budget Simulation.Projekt
-- Column: C_Project_Resource_Budget_Simulation.C_Project_ID
-- 2022-06-26T11:04:37.315345400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700796,0,546394,609651,549394,'F',TO_TIMESTAMP('2022-06-26 14:04:37','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','Projekt',10,0,0,TO_TIMESTAMP('2022-06-26 14:04:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Project Resource Budget Simulation.Project Resource Budget
-- Column: C_Project_Resource_Budget_Simulation.C_Project_Resource_Budget_ID
-- 2022-06-26T11:04:47.841463300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700797,0,546394,609652,549394,'F',TO_TIMESTAMP('2022-06-26 14:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Project Resource Budget',20,0,0,TO_TIMESTAMP('2022-06-26 14:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T11:04:56.907864500Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546097,549395,TO_TIMESTAMP('2022-06-26 14:04:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',10,TO_TIMESTAMP('2022-06-26 14:04:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Project Resource Budget Simulation.Start Plan
-- Column: C_Project_Resource_Budget_Simulation.DateStartPlan
-- 2022-06-26T11:05:11.575300500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700793,0,546394,609653,549395,'F',TO_TIMESTAMP('2022-06-26 14:05:11','YYYY-MM-DD HH24:MI:SS'),100,'Planned Start Date','Date when you plan to start','Y','N','Y','N','N','Start Plan',10,0,0,TO_TIMESTAMP('2022-06-26 14:05:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Project Resource Budget Simulation.Finish Plan
-- Column: C_Project_Resource_Budget_Simulation.DateFinishPlan
-- 2022-06-26T11:05:18.077214Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700794,0,546394,609654,549395,'F',TO_TIMESTAMP('2022-06-26 14:05:17','YYYY-MM-DD HH24:MI:SS'),100,'Planned Finish Date','Date when you plan to finish','Y','N','Y','N','N','Finish Plan',20,0,0,TO_TIMESTAMP('2022-06-26 14:05:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Project Resource Budget Simulation.Projekt
-- Column: C_Project_Resource_Budget_Simulation.C_Project_ID
-- 2022-06-26T11:05:32.618636800Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-06-26 14:05:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609651
;

-- UI Element: Simulation Plan -> Project Resource Budget Simulation.Project Resource Budget
-- Column: C_Project_Resource_Budget_Simulation.C_Project_Resource_Budget_ID
-- 2022-06-26T11:05:32.623667400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-06-26 14:05:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609652
;

-- UI Element: Simulation Plan -> Project Resource Budget Simulation.Start Plan
-- Column: C_Project_Resource_Budget_Simulation.DateStartPlan
-- 2022-06-26T11:05:32.628635200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-06-26 14:05:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609653
;

-- UI Element: Simulation Plan -> Project Resource Budget Simulation.Finish Plan
-- Column: C_Project_Resource_Budget_Simulation.DateFinishPlan
-- 2022-06-26T11:05:32.633633600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-06-26 14:05:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609654
;

-- Column: C_Project_Resource_Budget_Simulation.DateFinishPlan
-- 2022-06-26T11:25:03.319692Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-06-26 14:25:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583537
;

