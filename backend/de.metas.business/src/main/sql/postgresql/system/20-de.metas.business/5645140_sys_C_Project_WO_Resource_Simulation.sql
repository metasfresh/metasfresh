-- Table: C_Project_WO_Resource_Simulation
-- 2022-06-26T07:31:49.465758500Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542176,'N',TO_TIMESTAMP('2022-06-26 10:31:49','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'WO Project Resource Simulation','NP','L','C_Project_WO_Resource_Simulation','DTI',TO_TIMESTAMP('2022-06-26 10:31:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:31:49.472757Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542176 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-06-26T07:31:49.585719300Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555967,TO_TIMESTAMP('2022-06-26 10:31:49','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Project_WO_Resource_Simulation',1,'Y','N','Y','Y','C_Project_WO_Resource_Simulation','N',1000000,TO_TIMESTAMP('2022-06-26 10:31:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:31:49.593716300Z
CREATE SEQUENCE C_PROJECT_WO_RESOURCE_SIMULATION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: C_Project_WO_Resource_Simulation
-- 2022-06-26T07:31:53.953698300Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2022-06-26 10:31:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542176
;

-- Column: C_Project_WO_Resource_Simulation.AD_Client_ID
-- 2022-06-26T07:32:01.145147400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583512,102,0,19,542176,'AD_Client_ID',TO_TIMESTAMP('2022-06-26 10:32:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-06-26 10:32:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:32:01.148142300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583512 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:32:01.151143100Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Project_WO_Resource_Simulation.AD_Org_ID
-- 2022-06-26T07:32:01.916750500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583513,113,0,30,542176,'AD_Org_ID',TO_TIMESTAMP('2022-06-26 10:32:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-06-26 10:32:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:32:01.919750400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583513 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:32:01.921782Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Project_WO_Resource_Simulation.Created
-- 2022-06-26T07:32:02.591116700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583514,245,0,16,542176,'Created',TO_TIMESTAMP('2022-06-26 10:32:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-06-26 10:32:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:32:02.596153900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583514 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:32:02.602130800Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Project_WO_Resource_Simulation.CreatedBy
-- 2022-06-26T07:32:03.197259200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583515,246,0,18,110,542176,'CreatedBy',TO_TIMESTAMP('2022-06-26 10:32:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-06-26 10:32:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:32:03.205292200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583515 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:32:03.213310500Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Project_WO_Resource_Simulation.IsActive
-- 2022-06-26T07:32:03.836239200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583516,348,0,20,542176,'IsActive',TO_TIMESTAMP('2022-06-26 10:32:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-06-26 10:32:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:32:03.843237500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583516 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:32:03.850245200Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Project_WO_Resource_Simulation.Updated
-- 2022-06-26T07:32:04.399285200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583517,607,0,16,542176,'Updated',TO_TIMESTAMP('2022-06-26 10:32:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-06-26 10:32:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:32:04.406348600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583517 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:32:04.412282Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Project_WO_Resource_Simulation.UpdatedBy
-- 2022-06-26T07:32:04.925702400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583518,608,0,18,110,542176,'UpdatedBy',TO_TIMESTAMP('2022-06-26 10:32:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-06-26 10:32:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:32:04.929694800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583518 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:32:04.933695Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-06-26T07:32:05.435541300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581066,0,'C_Project_WO_Resource_Simulation_ID',TO_TIMESTAMP('2022-06-26 10:32:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','WO Project Resource Simulation','WO Project Resource Simulation',TO_TIMESTAMP('2022-06-26 10:32:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:32:05.442600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581066 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Resource_Simulation_ID
-- 2022-06-26T07:32:05.913110Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583519,581066,0,13,542176,'C_Project_WO_Resource_Simulation_ID',TO_TIMESTAMP('2022-06-26 10:32:05','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','WO Project Resource Simulation',0,0,TO_TIMESTAMP('2022-06-26 10:32:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:32:05.920102400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583519 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:32:05.926144800Z
/* DDL */  select update_Column_Translation_From_AD_Element(581066) 
;

-- Column: C_Project_WO_Resource_Simulation.C_Project_ID
-- 2022-06-26T07:32:34.089736900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583520,208,0,30,542176,'C_Project_ID',TO_TIMESTAMP('2022-06-26 10:32:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Financial Project','D',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Projekt',0,0,TO_TIMESTAMP('2022-06-26 10:32:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:32:34.096697200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583520 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:32:34.103709200Z
/* DDL */  select update_Column_Translation_From_AD_Element(208) 
;

-- Column: C_Project_WO_Resource_Simulation.C_Project_ID
-- 2022-06-26T07:32:38.582416800Z
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-06-26 10:32:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583520
;

-- Column: C_Project_WO_Resource.C_Project_ID
-- 2022-06-26T07:33:26.694176100Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2022-06-26 10:33:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583243
;

-- Column: C_Project_WO_Resource.S_Resource_ID
-- 2022-06-26T07:33:42.514803400Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-06-26 10:33:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583245
;

-- Column: C_Project_WO_Resource.S_Resource_ID
-- 2022-06-26T07:34:09.106811400Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-06-26 10:34:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583245
;

-- 2022-06-26T07:34:10.100844Z
INSERT INTO t_alter_column values('c_project_wo_resource','S_Resource_ID','NUMERIC(10)',null,null)
;

-- 2022-06-26T07:34:10.106838800Z
INSERT INTO t_alter_column values('c_project_wo_resource','S_Resource_ID',null,'NOT NULL',null)
;

-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Resource_ID
-- 2022-06-26T07:34:28.464161500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583521,580966,0,30,542176,'C_Project_WO_Resource_ID',TO_TIMESTAMP('2022-06-26 10:34:28','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N',0,'Project Resource',0,0,TO_TIMESTAMP('2022-06-26 10:34:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:34:28.471120Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583521 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:34:28.484121500Z
/* DDL */  select update_Column_Translation_From_AD_Element(580966) 
;

-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Step_ID
-- 2022-06-26T07:34:52.716169500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583522,580965,0,30,542176,'C_Project_WO_Step_ID',TO_TIMESTAMP('2022-06-26 10:34:52','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N',0,'Project Step',0,0,TO_TIMESTAMP('2022-06-26 10:34:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:34:52.723212100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583522 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:34:52.736205400Z
/* DDL */  select update_Column_Translation_From_AD_Element(580965) 
;

-- Column: C_Project_WO_Resource_Simulation.AssignDateFrom
-- 2022-06-26T07:36:02.812010700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583523,1754,0,16,542176,'AssignDateFrom',TO_TIMESTAMP('2022-06-26 10:36:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Ressource zuordnen ab','D',0,7,'Beginn Zuordnung','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Zuordnung von',0,0,TO_TIMESTAMP('2022-06-26 10:36:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:36:02.820021700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583523 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:36:02.824020800Z
/* DDL */  select update_Column_Translation_From_AD_Element(1754) 
;

-- Column: C_Project_WO_Resource_Simulation.AssignDateTo
-- 2022-06-26T07:36:17.984635Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583524,1755,0,16,542176,'AssignDateTo',TO_TIMESTAMP('2022-06-26 10:36:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Ressource zuordnen bis','D',0,7,'Zuordnung endet','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Zuordnung bis',0,0,TO_TIMESTAMP('2022-06-26 10:36:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:36:17.989637300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583524 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:36:17.993618Z
/* DDL */  select update_Column_Translation_From_AD_Element(1755) 
;

-- Column: C_Project_WO_Resource_Simulation.IsAllDay
-- 2022-06-26T07:37:42.559272800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583525,580861,0,20,542176,'IsAllDay',TO_TIMESTAMP('2022-06-26 10:37:42','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'All day',0,0,TO_TIMESTAMP('2022-06-26 10:37:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:37:42.561244900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583525 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:37:42.563211100Z
/* DDL */  select update_Column_Translation_From_AD_Element(580861) 
;

-- 2022-06-26T07:38:16.607721800Z
/* DDL */ CREATE TABLE public.C_Project_WO_Resource_Simulation (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AssignDateFrom TIMESTAMP WITH TIME ZONE NOT NULL, AssignDateTo TIMESTAMP WITH TIME ZONE NOT NULL, C_Project_ID NUMERIC(10) NOT NULL, C_Project_WO_Resource_ID NUMERIC(10) NOT NULL, C_Project_WO_Resource_Simulation_ID NUMERIC(10) NOT NULL, C_Project_WO_Step_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsAllDay CHAR(1) DEFAULT 'N' CHECK (IsAllDay IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CProject_CProjectWOResourceSimulation FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CProjectWOResource_CProjectWOResourceSimulation FOREIGN KEY (C_Project_WO_Resource_ID) REFERENCES public.C_Project_WO_Resource DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Project_WO_Resource_Simulation_Key PRIMARY KEY (C_Project_WO_Resource_Simulation_ID), CONSTRAINT CProjectWOStep_CProjectWOResourceSimulation FOREIGN KEY (C_Project_WO_Step_ID) REFERENCES public.C_Project_WO_Step DEFERRABLE INITIALLY DEFERRED)
;

-- Column: C_Project_WO_Resource_Simulation.C_SimulationPlan_ID
-- 2022-06-26T07:39:53.746447800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583526,581064,0,30,542176,'C_SimulationPlan_ID',TO_TIMESTAMP('2022-06-26 10:39:53','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Simulation Plan',0,0,TO_TIMESTAMP('2022-06-26 10:39:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:39:53.753489800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583526 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:39:53.759515200Z
/* DDL */  select update_Column_Translation_From_AD_Element(581064) 
;

-- 2022-06-26T07:39:55.504865900Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource_Simulation','ALTER TABLE public.C_Project_WO_Resource_Simulation ADD COLUMN C_SimulationPlan_ID NUMERIC(10) NOT NULL')
;

-- 2022-06-26T07:39:55.528869700Z
ALTER TABLE C_Project_WO_Resource_Simulation ADD CONSTRAINT CSimulationPlan_CProjectWOResourceSimulation FOREIGN KEY (C_SimulationPlan_ID) REFERENCES public.C_SimulationPlan DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Resource_ID
-- 2022-06-26T07:40:09.527179200Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-06-26 10:40:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583521
;

-- Tab: Simulation Plan -> WO Project Resource Simulation
-- Table: C_Project_WO_Resource_Simulation
-- 2022-06-26T07:40:25.960352900Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord
,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (0,583526,581066,0,546393,542176,541541,'Y',TO_TIMESTAMP('2022-06-26 10:40:25','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_WO_Resource_Simulation','Y','N','Y','Y','N','N','N','N'
,'Y','N','N','N','Y','Y','N','N','N',0,'WO Project Resource Simulation',583496,'N',30,1,TO_TIMESTAMP('2022-06-26 10:40:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:40:25.976403700Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546393 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-06-26T07:40:25.992404Z
/* DDL */  select update_tab_translation_from_ad_element(581066) 
;

-- 2022-06-26T07:40:26.000413300Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546393)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Mandant
-- Column: C_Project_WO_Resource_Simulation.AD_Client_ID
-- 2022-06-26T07:40:32.658472200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583512,700777,0,546393,TO_TIMESTAMP('2022-06-26 10:40:32','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-06-26 10:40:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:40:32.660476300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700777 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:40:32.667512100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-06-26T07:40:33.023470200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700777
;

-- 2022-06-26T07:40:33.024471900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700777)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Sektion
-- Column: C_Project_WO_Resource_Simulation.AD_Org_ID
-- 2022-06-26T07:40:33.136420400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583513,700778,0,546393,TO_TIMESTAMP('2022-06-26 10:40:33','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-06-26 10:40:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:40:33.142457500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700778 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:40:33.147417Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-06-26T07:40:33.445465600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700778
;

-- 2022-06-26T07:40:33.445465600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700778)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Aktiv
-- Column: C_Project_WO_Resource_Simulation.IsActive
-- 2022-06-26T07:40:33.542860900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583516,700779,0,546393,TO_TIMESTAMP('2022-06-26 10:40:33','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-06-26 10:40:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:40:33.544852Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700779 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:40:33.545848900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-06-26T07:40:33.936878800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700779
;

-- 2022-06-26T07:40:33.936878800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700779)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> WO Project Resource Simulation
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Resource_Simulation_ID
-- 2022-06-26T07:40:34.046975900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583519,700780,0,546393,TO_TIMESTAMP('2022-06-26 10:40:33','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','WO Project Resource Simulation',TO_TIMESTAMP('2022-06-26 10:40:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:40:34.049976500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700780 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:40:34.052974200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581066) 
;

-- 2022-06-26T07:40:34.053968700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700780
;

-- 2022-06-26T07:40:34.054972400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700780)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Projekt
-- Column: C_Project_WO_Resource_Simulation.C_Project_ID
-- 2022-06-26T07:40:34.158864500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583520,700781,0,546393,TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:40:34.163870Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700781 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:40:34.165862500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-06-26T07:40:34.182860700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700781
;

-- 2022-06-26T07:40:34.182860700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700781)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Project Resource
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Resource_ID
-- 2022-06-26T07:40:34.273858100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583521,700782,0,546393,TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Resource',TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:40:34.274859Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700782 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:40:34.276859100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580966) 
;

-- 2022-06-26T07:40:34.276859100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700782
;

-- 2022-06-26T07:40:34.277858200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700782)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Project Step
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Step_ID
-- 2022-06-26T07:40:34.378864100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583522,700783,0,546393,TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Step',TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:40:34.379895200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700783 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:40:34.381862100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580965) 
;

-- 2022-06-26T07:40:34.381862100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700783
;

-- 2022-06-26T07:40:34.382862300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700783)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Zuordnung von
-- Column: C_Project_WO_Resource_Simulation.AssignDateFrom
-- 2022-06-26T07:40:34.478006700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583523,700784,0,546393,TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab',7,'D','Beginn Zuordnung','Y','N','N','N','N','N','N','N','Zuordnung von',TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:40:34.482996900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700784 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:40:34.486992800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1754) 
;

-- 2022-06-26T07:40:34.490988800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700784
;

-- 2022-06-26T07:40:34.490988800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700784)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Zuordnung bis
-- Column: C_Project_WO_Resource_Simulation.AssignDateTo
-- 2022-06-26T07:40:34.630023700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583524,700785,0,546393,TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis',7,'D','Zuordnung endet','Y','N','N','N','N','N','N','N','Zuordnung bis',TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:40:34.636022400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700785 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:40:34.640976900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1755) 
;

-- 2022-06-26T07:40:34.646986400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700785
;

-- 2022-06-26T07:40:34.647979500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700785)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> All day
-- Column: C_Project_WO_Resource_Simulation.IsAllDay
-- 2022-06-26T07:40:34.775459700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583525,700786,0,546393,TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','All day',TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:40:34.779460500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700786 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:40:34.784507100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580861) 
;

-- 2022-06-26T07:40:34.786533800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700786
;

-- 2022-06-26T07:40:34.787517700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700786)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Simulation Plan
-- Column: C_Project_WO_Resource_Simulation.C_SimulationPlan_ID
-- 2022-06-26T07:40:34.891675Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583526,700787,0,546393,TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Simulation Plan',TO_TIMESTAMP('2022-06-26 10:40:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:40:34.894704800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700787 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:40:34.897704600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581064) 
;

-- 2022-06-26T07:40:34.899704400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700787
;

-- 2022-06-26T07:40:34.899704400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700787)
;

-- 2022-06-26T07:40:59.204060800Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546393,545033,TO_TIMESTAMP('2022-06-26 10:40:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-06-26 10:40:58','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-06-26T07:40:59.206055800Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545033 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-06-26T07:41:04.236784200Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546094,545033,TO_TIMESTAMP('2022-06-26 10:41:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-06-26 10:41:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:41:06.405072300Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546095,545033,TO_TIMESTAMP('2022-06-26 10:41:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-06-26 10:41:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:41:16.966686600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546094,549392,TO_TIMESTAMP('2022-06-26 10:41:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2022-06-26 10:41:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Projekt
-- Column: C_Project_WO_Resource_Simulation.C_Project_ID
-- 2022-06-26T07:41:32.206719300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700781,0,546393,609644,549392,'F',TO_TIMESTAMP('2022-06-26 10:41:32','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','Projekt',10,0,0,TO_TIMESTAMP('2022-06-26 10:41:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Project Step
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Step_ID
-- 2022-06-26T07:41:41.818001600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700783,0,546393,609645,549392,'F',TO_TIMESTAMP('2022-06-26 10:41:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Project Step',20,0,0,TO_TIMESTAMP('2022-06-26 10:41:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Project Resource
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Resource_ID
-- 2022-06-26T07:41:51.422621600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700782,0,546393,609646,549392,'F',TO_TIMESTAMP('2022-06-26 10:41:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Project Resource',30,0,0,TO_TIMESTAMP('2022-06-26 10:41:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:42:06.208805400Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546095,549393,TO_TIMESTAMP('2022-06-26 10:42:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',10,TO_TIMESTAMP('2022-06-26 10:42:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Zuordnung von
-- Column: C_Project_WO_Resource_Simulation.AssignDateFrom
-- 2022-06-26T07:42:23.163484300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700784,0,546393,609647,549393,'F',TO_TIMESTAMP('2022-06-26 10:42:23','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab','Beginn Zuordnung','Y','N','Y','N','N','Zuordnung von',10,0,0,TO_TIMESTAMP('2022-06-26 10:42:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Zuordnung bis
-- Column: C_Project_WO_Resource_Simulation.AssignDateTo
-- 2022-06-26T07:42:32.256776600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700785,0,546393,609648,549393,'F',TO_TIMESTAMP('2022-06-26 10:42:32','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis','Zuordnung endet','Y','N','Y','N','N','Zuordnung bis',20,0,0,TO_TIMESTAMP('2022-06-26 10:42:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.All day
-- Column: C_Project_WO_Resource_Simulation.IsAllDay
-- 2022-06-26T07:42:45.727887200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700786,0,546393,609649,549393,'F',TO_TIMESTAMP('2022-06-26 10:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','All day',30,0,0,TO_TIMESTAMP('2022-06-26 10:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Projekt
-- Column: C_Project_WO_Resource_Simulation.C_Project_ID
-- 2022-06-26T07:43:05.202055300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-06-26 10:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609644
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Project Step
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Step_ID
-- 2022-06-26T07:43:05.212045100Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-06-26 10:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609645
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Project Resource
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Resource_ID
-- 2022-06-26T07:43:05.217045900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-06-26 10:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609646
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Zuordnung von
-- Column: C_Project_WO_Resource_Simulation.AssignDateFrom
-- 2022-06-26T07:43:05.223043500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-06-26 10:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609647
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Zuordnung bis
-- Column: C_Project_WO_Resource_Simulation.AssignDateTo
-- 2022-06-26T07:43:05.228044500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-06-26 10:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609648
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.All day
-- Column: C_Project_WO_Resource_Simulation.IsAllDay
-- 2022-06-26T07:43:05.234044400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-06-26 10:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609649
;

-- Tab: Simulation Plan -> WO Project Resource Simulation
-- Table: C_Project_WO_Resource_Simulation
-- 2022-06-26T07:43:31.142658900Z
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-06-26 10:43:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546393
;

-- Field: Simulation Plan -> Simulation Plan -> Verarbeitet
-- Column: C_SimulationPlan.Processed
-- 2022-06-26T07:43:57.311743200Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-06-26 10:43:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700763
;

-- Tab: Simulation Plan -> Simulation Plan
-- Table: C_SimulationPlan
-- 2022-06-26T07:44:11.916200800Z
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-06-26 10:44:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546390
;

