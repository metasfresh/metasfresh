-- Table: C_Project_WO_Step_Simulation
-- 2022-06-26T07:23:25.205532400Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542175,'N',TO_TIMESTAMP('2022-06-26 10:23:25','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','Y','N','N','N',0,'WO Project Step Simulation','NP','L','C_Project_WO_Step_Simulation','DTI',TO_TIMESTAMP('2022-06-26 10:23:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:23:25.214520100Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542175 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-06-26T07:23:25.330060800Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555966,TO_TIMESTAMP('2022-06-26 10:23:25','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Project_WO_Step_Simulation',1,'Y','N','Y','Y','C_Project_WO_Step_Simulation','N',1000000,TO_TIMESTAMP('2022-06-26 10:23:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:23:25.351059400Z
CREATE SEQUENCE C_PROJECT_WO_STEP_SIMULATION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Project_WO_Step_Simulation.AD_Client_ID
-- 2022-06-26T07:23:38.995072700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583499,102,0,19,542175,'AD_Client_ID',TO_TIMESTAMP('2022-06-26 10:23:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-06-26 10:23:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:23:39.002098900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583499 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:23:39.010104500Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Project_WO_Step_Simulation.AD_Org_ID
-- 2022-06-26T07:23:39.839823300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583500,113,0,30,542175,'AD_Org_ID',TO_TIMESTAMP('2022-06-26 10:23:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-06-26 10:23:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:23:39.844825500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583500 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:23:39.849850200Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Project_WO_Step_Simulation.Created
-- 2022-06-26T07:23:40.474856100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583501,245,0,16,542175,'Created',TO_TIMESTAMP('2022-06-26 10:23:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-06-26 10:23:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:23:40.477855200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583501 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:23:40.479858400Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Project_WO_Step_Simulation.CreatedBy
-- 2022-06-26T07:23:41.062403900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583502,246,0,18,110,542175,'CreatedBy',TO_TIMESTAMP('2022-06-26 10:23:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-06-26 10:23:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:23:41.065380200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583502 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:23:41.068366200Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Project_WO_Step_Simulation.IsActive
-- 2022-06-26T07:23:41.673597600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583503,348,0,20,542175,'IsActive',TO_TIMESTAMP('2022-06-26 10:23:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-06-26 10:23:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:23:41.680558200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583503 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:23:41.688625300Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Project_WO_Step_Simulation.Updated
-- 2022-06-26T07:23:42.290451700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583504,607,0,16,542175,'Updated',TO_TIMESTAMP('2022-06-26 10:23:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-06-26 10:23:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:23:42.292447500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583504 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:23:42.295448300Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Project_WO_Step_Simulation.UpdatedBy
-- 2022-06-26T07:23:42.805265200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583505,608,0,18,110,542175,'UpdatedBy',TO_TIMESTAMP('2022-06-26 10:23:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-06-26 10:23:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:23:42.809316700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583505 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:23:42.813303300Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-06-26T07:23:43.295258Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581065,0,'C_Project_WO_Step_Simulation_ID',TO_TIMESTAMP('2022-06-26 10:23:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','WO Project Step Simulation','WO Project Step Simulation',TO_TIMESTAMP('2022-06-26 10:23:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:23:43.298287800Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581065 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_WO_Step_Simulation.C_Project_WO_Step_Simulation_ID
-- 2022-06-26T07:23:43.730922800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583506,581065,0,13,542175,'C_Project_WO_Step_Simulation_ID',TO_TIMESTAMP('2022-06-26 10:23:43','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','WO Project Step Simulation',0,0,TO_TIMESTAMP('2022-06-26 10:23:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:23:43.733925700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583506 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:23:43.737961100Z
/* DDL */  select update_Column_Translation_From_AD_Element(581065) 
;

-- Column: C_Project_WO_Step_Simulation.C_Project_WO_Step_ID
-- 2022-06-26T07:24:13.254702300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583507,580965,0,30,542175,'C_Project_WO_Step_ID',TO_TIMESTAMP('2022-06-26 10:24:13','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Project Step',0,0,TO_TIMESTAMP('2022-06-26 10:24:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:24:13.256723900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583507 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:24:13.259703Z
/* DDL */  select update_Column_Translation_From_AD_Element(580965) 
;

-- Column: C_Project_WO_Step_Simulation.C_Project_ID
-- 2022-06-26T07:24:31.286228700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583508,208,0,30,542175,'C_Project_ID',TO_TIMESTAMP('2022-06-26 10:24:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Financial Project','D',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N',0,'Projekt',0,0,TO_TIMESTAMP('2022-06-26 10:24:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:24:31.295262400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583508 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:24:31.309273Z
/* DDL */  select update_Column_Translation_From_AD_Element(208) 
;

-- Column: C_Project_WO_Step_Simulation.C_SimulationPlan_ID
-- 2022-06-26T07:24:46.649436200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583509,581064,0,30,542175,'C_SimulationPlan_ID',TO_TIMESTAMP('2022-06-26 10:24:46','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Simulation Plan',0,0,TO_TIMESTAMP('2022-06-26 10:24:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:24:46.656429100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583509 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:24:46.670389Z
/* DDL */  select update_Column_Translation_From_AD_Element(581064) 
;

-- Column: C_Project_WO_Step_Simulation.DateStart
-- 2022-06-26T07:26:03.921575400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583510,53280,0,16,542175,'DateStart',TO_TIMESTAMP('2022-06-26 10:26:03','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Startdatum',0,0,TO_TIMESTAMP('2022-06-26 10:26:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:26:03.928580800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583510 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:26:03.940616800Z
/* DDL */  select update_Column_Translation_From_AD_Element(53280) 
;

-- Column: C_Project_WO_Step_Simulation.DateEnd
-- 2022-06-26T07:26:27.770516200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583511,579243,0,16,542175,'DateEnd',TO_TIMESTAMP('2022-06-26 10:26:27','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,' Endzeitpunkt',0,0,TO_TIMESTAMP('2022-06-26 10:26:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:26:27.778519100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583511 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:26:27.787517200Z
/* DDL */  select update_Column_Translation_From_AD_Element(579243) 
;

-- 2022-06-26T07:27:02.401102400Z
/* DDL */ CREATE TABLE public.C_Project_WO_Step_Simulation (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Project_ID NUMERIC(10) NOT NULL, C_Project_WO_Step_ID NUMERIC(10) NOT NULL, C_Project_WO_Step_Simulation_ID NUMERIC(10) NOT NULL, C_SimulationPlan_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DateEnd TIMESTAMP WITH TIME ZONE NOT NULL, DateStart TIMESTAMP WITH TIME ZONE NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CProject_CProjectWOStepSimulation FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CProjectWOStep_CProjectWOStepSimulation FOREIGN KEY (C_Project_WO_Step_ID) REFERENCES public.C_Project_WO_Step DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Project_WO_Step_Simulation_Key PRIMARY KEY (C_Project_WO_Step_Simulation_ID), CONSTRAINT CSimulationPlan_CProjectWOStepSimulation FOREIGN KEY (C_SimulationPlan_ID) REFERENCES public.C_SimulationPlan DEFERRABLE INITIALLY DEFERRED)
;

-- Tab: Simulation Plan -> WO Project Step Simulation
-- Table: C_Project_WO_Step_Simulation
-- 2022-06-26T07:28:11.786320500Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord
,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (0,583509,581065,0,546391,542175,541541,'Y',TO_TIMESTAMP('2022-06-26 10:28:11','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_WO_Step_Simulation','Y','N','Y','Y','N','N','N','N'
,'Y','Y','N','N','Y','Y','N','N','N',0,'WO Project Step Simulation',583496,'N',20,1,TO_TIMESTAMP('2022-06-26 10:28:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:28:11.789319300Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546391 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-06-26T07:28:11.790317900Z
/* DDL */  select update_tab_translation_from_ad_element(581065) 
;

-- 2022-06-26T07:28:11.794319100Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546391)
;

-- Field: Simulation Plan -> WO Project Step Simulation -> Mandant
-- Column: C_Project_WO_Step_Simulation.AD_Client_ID
-- 2022-06-26T07:28:16.503055500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583499,700768,0,546391,TO_TIMESTAMP('2022-06-26 10:28:16','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-06-26 10:28:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:28:16.507015300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700768 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:28:16.510014800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-06-26T07:28:16.870062300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700768
;

-- 2022-06-26T07:28:16.874063200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700768)
;

-- Field: Simulation Plan -> WO Project Step Simulation -> Sektion
-- Column: C_Project_WO_Step_Simulation.AD_Org_ID
-- 2022-06-26T07:28:16.971061400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583500,700769,0,546391,TO_TIMESTAMP('2022-06-26 10:28:16','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-06-26 10:28:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:28:16.973062500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700769 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:28:16.974063200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-06-26T07:28:17.223062200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700769
;

-- 2022-06-26T07:28:17.223062200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700769)
;

-- Field: Simulation Plan -> WO Project Step Simulation -> Aktiv
-- Column: C_Project_WO_Step_Simulation.IsActive
-- 2022-06-26T07:28:17.322077700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583503,700770,0,546391,TO_TIMESTAMP('2022-06-26 10:28:17','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-06-26 10:28:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:28:17.324062200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700770 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:28:17.326077300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-06-26T07:28:17.777361600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700770
;

-- 2022-06-26T07:28:17.778325200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700770)
;

-- Field: Simulation Plan -> WO Project Step Simulation -> WO Project Step Simulation
-- Column: C_Project_WO_Step_Simulation.C_Project_WO_Step_Simulation_ID
-- 2022-06-26T07:28:17.896383900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583506,700771,0,546391,TO_TIMESTAMP('2022-06-26 10:28:17','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','WO Project Step Simulation',TO_TIMESTAMP('2022-06-26 10:28:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:28:17.903405300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700771 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:28:17.907415200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581065) 
;

-- 2022-06-26T07:28:17.915411300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700771
;

-- 2022-06-26T07:28:17.916373100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700771)
;

-- Field: Simulation Plan -> WO Project Step Simulation -> Project Step
-- Column: C_Project_WO_Step_Simulation.C_Project_WO_Step_ID
-- 2022-06-26T07:28:18.033621300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583507,700772,0,546391,TO_TIMESTAMP('2022-06-26 10:28:17','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Step',TO_TIMESTAMP('2022-06-26 10:28:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:28:18.039668100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700772 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:28:18.044667900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580965) 
;

-- 2022-06-26T07:28:18.053617100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700772
;

-- 2022-06-26T07:28:18.054620900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700772)
;

-- Field: Simulation Plan -> WO Project Step Simulation -> Projekt
-- Column: C_Project_WO_Step_Simulation.C_Project_ID
-- 2022-06-26T07:28:18.173145400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583508,700773,0,546391,TO_TIMESTAMP('2022-06-26 10:28:18','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2022-06-26 10:28:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:28:18.179199900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700773 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:28:18.186144Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-06-26T07:28:18.265257200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700773
;

-- 2022-06-26T07:28:18.265257200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700773)
;

-- Field: Simulation Plan -> WO Project Step Simulation -> Simulation Plan
-- Column: C_Project_WO_Step_Simulation.C_SimulationPlan_ID
-- 2022-06-26T07:28:18.372836100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583509,700774,0,546391,TO_TIMESTAMP('2022-06-26 10:28:18','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Simulation Plan',TO_TIMESTAMP('2022-06-26 10:28:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:28:18.374837300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700774 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:28:18.376833600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581064) 
;

-- 2022-06-26T07:28:18.377834Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700774
;

-- 2022-06-26T07:28:18.377834Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700774)
;

-- Field: Simulation Plan -> WO Project Step Simulation -> Startdatum
-- Column: C_Project_WO_Step_Simulation.DateStart
-- 2022-06-26T07:28:18.482408600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583510,700775,0,546391,TO_TIMESTAMP('2022-06-26 10:28:18','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Startdatum',TO_TIMESTAMP('2022-06-26 10:28:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:28:18.488408300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700775 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:28:18.493446500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53280) 
;

-- 2022-06-26T07:28:18.498558700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700775
;

-- 2022-06-26T07:28:18.499509800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700775)
;

-- Field: Simulation Plan -> WO Project Step Simulation ->  Endzeitpunkt
-- Column: C_Project_WO_Step_Simulation.DateEnd
-- 2022-06-26T07:28:18.614597500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583511,700776,0,546391,TO_TIMESTAMP('2022-06-26 10:28:18','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N',' Endzeitpunkt',TO_TIMESTAMP('2022-06-26 10:28:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:28:18.620591300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700776 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:28:18.625594700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579243) 
;

-- 2022-06-26T07:28:18.628596Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700776
;

-- 2022-06-26T07:28:18.629648700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700776)
;

-- 2022-06-26T07:28:49.218846400Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546391,545032,TO_TIMESTAMP('2022-06-26 10:28:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-06-26 10:28:49','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-06-26T07:28:49.224846800Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545032 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-06-26T07:28:55.098029700Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546092,545032,TO_TIMESTAMP('2022-06-26 10:28:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-06-26 10:28:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:28:57.030751Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546093,545032,TO_TIMESTAMP('2022-06-26 10:28:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-06-26 10:28:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:29:04.619296300Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546092,549390,TO_TIMESTAMP('2022-06-26 10:29:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2022-06-26 10:29:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:29:10.051788400Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2022-06-26 10:29:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549390
;

-- UI Element: Simulation Plan -> WO Project Step Simulation.Projekt
-- Column: C_Project_WO_Step_Simulation.C_Project_ID
-- 2022-06-26T07:29:28.845770300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700773,0,546391,609640,549390,'F',TO_TIMESTAMP('2022-06-26 10:29:28','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','Projekt',10,0,0,TO_TIMESTAMP('2022-06-26 10:29:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Step Simulation.Project Step
-- Column: C_Project_WO_Step_Simulation.C_Project_WO_Step_ID
-- 2022-06-26T07:29:36.490864Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700772,0,546391,609641,549390,'F',TO_TIMESTAMP('2022-06-26 10:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Project Step',20,0,0,TO_TIMESTAMP('2022-06-26 10:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:30:02.651944800Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546093,549391,TO_TIMESTAMP('2022-06-26 10:30:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','date start/end',10,TO_TIMESTAMP('2022-06-26 10:30:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Step Simulation.Startdatum
-- Column: C_Project_WO_Step_Simulation.DateStart
-- 2022-06-26T07:30:12.838867Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700775,0,546391,609642,549391,'F',TO_TIMESTAMP('2022-06-26 10:30:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Startdatum',10,0,0,TO_TIMESTAMP('2022-06-26 10:30:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Step Simulation. Endzeitpunkt
-- Column: C_Project_WO_Step_Simulation.DateEnd
-- 2022-06-26T07:30:19.766637700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700776,0,546391,609643,549391,'F',TO_TIMESTAMP('2022-06-26 10:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N',' Endzeitpunkt',20,0,0,TO_TIMESTAMP('2022-06-26 10:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Step Simulation.Projekt
-- Column: C_Project_WO_Step_Simulation.C_Project_ID
-- 2022-06-26T07:30:38.811805Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-06-26 10:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609640
;

-- UI Element: Simulation Plan -> WO Project Step Simulation.Project Step
-- Column: C_Project_WO_Step_Simulation.C_Project_WO_Step_ID
-- 2022-06-26T07:30:38.817802400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-06-26 10:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609641
;

-- UI Element: Simulation Plan -> WO Project Step Simulation.Startdatum
-- Column: C_Project_WO_Step_Simulation.DateStart
-- 2022-06-26T07:30:38.823882500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-06-26 10:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609642
;

-- UI Element: Simulation Plan -> WO Project Step Simulation. Endzeitpunkt
-- Column: C_Project_WO_Step_Simulation.DateEnd
-- 2022-06-26T07:30:38.831917Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-06-26 10:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609643
;

