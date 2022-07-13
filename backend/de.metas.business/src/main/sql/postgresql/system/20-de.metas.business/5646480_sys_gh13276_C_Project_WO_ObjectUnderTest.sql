-- Table: C_Project_WO_ObjectUnderTest
-- 2022-07-12T10:12:28.212Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('1',0,0,0,542184,'N',TO_TIMESTAMP('2022-07-12 12:12:28','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'C_Project_WO_ObjectUnderTest','NP','L','C_Project_WO_ObjectUnderTest','DTI',TO_TIMESTAMP('2022-07-12 12:12:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:12:28.214Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542184 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-07-12T10:12:28.323Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555974,TO_TIMESTAMP('2022-07-12 12:12:28','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Project_WO_ObjectUnderTest',1,'Y','N','Y','Y','C_Project_WO_ObjectUnderTest','N',1000000,TO_TIMESTAMP('2022-07-12 12:12:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:12:28.335Z
CREATE SEQUENCE C_PROJECT_WO_OBJECTUNDERTEST_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Project_WO_ObjectUnderTest.AD_Client_ID
-- 2022-07-12T10:12:51.439Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583619,102,0,19,542184,'AD_Client_ID',TO_TIMESTAMP('2022-07-12 12:12:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-07-12 12:12:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:12:51.440Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583619 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:12:51.445Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Project_WO_ObjectUnderTest.AD_Org_ID
-- 2022-07-12T10:12:52.070Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583620,113,0,30,542184,'AD_Org_ID',TO_TIMESTAMP('2022-07-12 12:12:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-07-12 12:12:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:12:52.073Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583620 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:12:52.077Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Project_WO_ObjectUnderTest.Created
-- 2022-07-12T10:12:52.695Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583621,245,0,16,542184,'Created',TO_TIMESTAMP('2022-07-12 12:12:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-07-12 12:12:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:12:52.697Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583621 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:12:52.701Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Project_WO_ObjectUnderTest.CreatedBy
-- 2022-07-12T10:12:53.266Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583622,246,0,18,110,542184,'CreatedBy',TO_TIMESTAMP('2022-07-12 12:12:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-07-12 12:12:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:12:53.268Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583622 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:12:53.273Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Project_WO_ObjectUnderTest.IsActive
-- 2022-07-12T10:12:53.870Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583623,348,0,20,542184,'IsActive',TO_TIMESTAMP('2022-07-12 12:12:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-07-12 12:12:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:12:53.872Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583623 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:12:53.876Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Project_WO_ObjectUnderTest.Updated
-- 2022-07-12T10:12:54.521Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583624,607,0,16,542184,'Updated',TO_TIMESTAMP('2022-07-12 12:12:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-07-12 12:12:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:12:54.523Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583624 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:12:54.527Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Project_WO_ObjectUnderTest.UpdatedBy
-- 2022-07-12T10:12:55.120Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583625,608,0,18,110,542184,'UpdatedBy',TO_TIMESTAMP('2022-07-12 12:12:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-07-12 12:12:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:12:55.122Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583625 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:12:55.126Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-07-12T10:12:55.688Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581101,0,'C_Project_WO_ObjectUnderTest_ID',TO_TIMESTAMP('2022-07-12 12:12:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_Project_WO_ObjectUnderTest','C_Project_WO_ObjectUnderTest',TO_TIMESTAMP('2022-07-12 12:12:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:12:55.690Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581101 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_WO_ObjectUnderTest.C_Project_WO_ObjectUnderTest_ID
-- 2022-07-12T10:12:56.159Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583626,581101,0,13,542184,'C_Project_WO_ObjectUnderTest_ID',TO_TIMESTAMP('2022-07-12 12:12:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','C_Project_WO_ObjectUnderTest',0,0,TO_TIMESTAMP('2022-07-12 12:12:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:12:56.161Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583626 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:12:56.165Z
/* DDL */  select update_Column_Translation_From_AD_Element(581101) 
;

-- Column: C_Project_WO_ObjectUnderTest.C_Project_ID
-- 2022-07-12T10:13:34.996Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583627,208,0,19,542184,'C_Project_ID',TO_TIMESTAMP('2022-07-12 12:13:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Financial Project','D',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N',0,'Projekt',0,0,TO_TIMESTAMP('2022-07-12 12:13:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:13:34.998Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583627 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:13:35.002Z
/* DDL */  select update_Column_Translation_From_AD_Element(208) 
;

-- Table: C_Project_WO_ObjectUnderTest
-- 2022-07-12T10:13:42.628Z
UPDATE AD_Table SET IsAutocomplete='Y', IsChangeLog='Y', IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2022-07-12 12:13:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542184
;

-- Column: C_Project_WO_ObjectUnderTest.C_Project_ID
-- 2022-07-12T10:13:54.550Z
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-07-12 12:13:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583627
;

-- 2022-07-12T10:13:57.312Z
/* DDL */ CREATE TABLE public.C_Project_WO_ObjectUnderTest (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Project_ID NUMERIC(10) NOT NULL, C_Project_WO_ObjectUnderTest_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CProject_CProjectWOObjectUnderTest FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Project_WO_ObjectUnderTest_Key PRIMARY KEY (C_Project_WO_ObjectUnderTest_ID))
;

-- 2022-07-12T10:14:43.602Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581102,0,'NumberOfObjectsUnderTest',TO_TIMESTAMP('2022-07-12 12:14:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Anzahl der Prüfgegenstände','Anzahl der Prüfgegenstände',TO_TIMESTAMP('2022-07-12 12:14:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:14:43.604Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581102 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:14:47.120Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:14:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581102 AND AD_Language='de_CH'
;

-- 2022-07-12T10:14:47.122Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581102,'de_CH') 
;

-- 2022-07-12T10:14:58.777Z
UPDATE AD_Element_Trl SET Description='Anzahl der zu prüfenden Objekte',Updated=TO_TIMESTAMP('2022-07-12 12:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581102 AND AD_Language='de_CH'
;

-- 2022-07-12T10:14:58.779Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581102,'de_CH') 
;

-- 2022-07-12T10:15:01.808Z
UPDATE AD_Element_Trl SET Description='Anzahl der zu prüfenden Objekte', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:15:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581102 AND AD_Language='de_DE'
;

-- 2022-07-12T10:15:01.810Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581102,'de_DE') 
;

-- 2022-07-12T10:15:01.817Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581102,'de_DE') 
;

-- 2022-07-12T10:15:01.819Z
UPDATE AD_Column SET ColumnName='NumberOfObjectsUnderTest', Name='Anzahl der Prüfgegenstände', Description='Anzahl der zu prüfenden Objekte', Help=NULL WHERE AD_Element_ID=581102
;

-- 2022-07-12T10:15:01.821Z
UPDATE AD_Process_Para SET ColumnName='NumberOfObjectsUnderTest', Name='Anzahl der Prüfgegenstände', Description='Anzahl der zu prüfenden Objekte', Help=NULL, AD_Element_ID=581102 WHERE UPPER(ColumnName)='NUMBEROFOBJECTSUNDERTEST' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-12T10:15:01.823Z
UPDATE AD_Process_Para SET ColumnName='NumberOfObjectsUnderTest', Name='Anzahl der Prüfgegenstände', Description='Anzahl der zu prüfenden Objekte', Help=NULL WHERE AD_Element_ID=581102 AND IsCentrallyMaintained='Y'
;

-- 2022-07-12T10:15:01.824Z
UPDATE AD_Field SET Name='Anzahl der Prüfgegenstände', Description='Anzahl der zu prüfenden Objekte', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581102) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581102)
;

-- 2022-07-12T10:15:01.837Z
UPDATE AD_Tab SET Name='Anzahl der Prüfgegenstände', Description='Anzahl der zu prüfenden Objekte', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581102
;

-- 2022-07-12T10:15:01.840Z
UPDATE AD_WINDOW SET Name='Anzahl der Prüfgegenstände', Description='Anzahl der zu prüfenden Objekte', Help=NULL WHERE AD_Element_ID = 581102
;

-- 2022-07-12T10:15:01.842Z
UPDATE AD_Menu SET   Name = 'Anzahl der Prüfgegenstände', Description = 'Anzahl der zu prüfenden Objekte', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581102
;

-- 2022-07-12T10:15:23.861Z
UPDATE AD_Element_Trl SET Description='Number of objects under test', IsTranslated='Y', Name='Number of test items', PrintName='Number of test items',Updated=TO_TIMESTAMP('2022-07-12 12:15:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581102 AND AD_Language='en_US'
;

-- 2022-07-12T10:15:23.863Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581102,'en_US') 
;

-- Column: C_Project_WO_ObjectUnderTest.NumberOfObjectsUnderTest
-- 2022-07-12T10:15:48.093Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583628,581102,0,11,542184,'NumberOfObjectsUnderTest',TO_TIMESTAMP('2022-07-12 12:15:47','YYYY-MM-DD HH24:MI:SS'),100,'N','1','Anzahl der zu prüfenden Objekte','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Anzahl der Prüfgegenstände',0,0,TO_TIMESTAMP('2022-07-12 12:15:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:15:48.096Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583628 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:15:48.100Z
/* DDL */  select update_Column_Translation_From_AD_Element(581102) 
;

-- 2022-07-12T10:15:49.388Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_ObjectUnderTest','ALTER TABLE public.C_Project_WO_ObjectUnderTest ADD COLUMN NumberOfObjectsUnderTest NUMERIC(10) DEFAULT 1 NOT NULL')
;

-- 2022-07-12T10:16:48.908Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581103,0,'WODeliveryNote',TO_TIMESTAMP('2022-07-12 12:16:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lieferhinweis','Lieferhinweis',TO_TIMESTAMP('2022-07-12 12:16:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:16:48.909Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581103 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:16:52.262Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:16:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581103 AND AD_Language='de_CH'
;

-- 2022-07-12T10:16:52.264Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581103,'de_CH') 
;

-- 2022-07-12T10:16:54.840Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:16:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581103 AND AD_Language='de_DE'
;

-- 2022-07-12T10:16:54.842Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581103,'de_DE') 
;

-- 2022-07-12T10:16:54.850Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581103,'de_DE') 
;

-- 2022-07-12T10:16:59.875Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Delivery note', PrintName='Delivery note',Updated=TO_TIMESTAMP('2022-07-12 12:16:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581103 AND AD_Language='en_US'
;

-- 2022-07-12T10:16:59.878Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581103,'en_US') 
;

-- Column: C_Project_WO_ObjectUnderTest.WODeliveryNote
-- 2022-07-12T10:17:13.630Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583629,581103,0,10,542184,'WODeliveryNote',TO_TIMESTAMP('2022-07-12 12:17:13','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferhinweis',0,0,TO_TIMESTAMP('2022-07-12 12:17:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:17:13.633Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583629 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:17:13.638Z
/* DDL */  select update_Column_Translation_From_AD_Element(581103) 
;

-- 2022-07-12T10:17:14.810Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_ObjectUnderTest','ALTER TABLE public.C_Project_WO_ObjectUnderTest ADD COLUMN WODeliveryNote VARCHAR(255)')
;

-- Column: C_Project_WO_ObjectUnderTest.Manufacturer
-- 2022-07-12T10:17:33.620Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583630,1915,0,10,542184,'Manufacturer',TO_TIMESTAMP('2022-07-12 12:17:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Hersteller des Produktes','D',0,255,'Der Hersteller des Produktes (genutzt, wenn abweichend von Geschäftspartner / Lieferant).','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Hersteller',0,0,TO_TIMESTAMP('2022-07-12 12:17:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:17:33.623Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583630 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:17:33.628Z
/* DDL */  select update_Column_Translation_From_AD_Element(1915) 
;

-- 2022-07-12T10:17:34.359Z
--/* DDL */ SELECT public.db_alter_table('C_Project_WO_ObjectUnderTest','ALTER TABLE public.C_Project_WO_ObjectUnderTest ADD COLUMN Manufacturer VARCHAR(255)')
--;

-- 2022-07-12T10:18:12.336Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581104,0,'WOManufacturer',TO_TIMESTAMP('2022-07-12 12:18:12','YYYY-MM-DD HH24:MI:SS'),100,'Hersteller des Prüfgegenstandes','D','','Y','Hersteller','Hersteller',TO_TIMESTAMP('2022-07-12 12:18:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:18:12.337Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581104 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:18:16.087Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:18:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581104 AND AD_Language='de_CH'
;

-- 2022-07-12T10:18:16.089Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581104,'de_CH') 
;

-- 2022-07-12T10:18:18.065Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:18:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581104 AND AD_Language='de_DE'
;

-- 2022-07-12T10:18:18.066Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581104,'de_DE') 
;

-- 2022-07-12T10:18:18.073Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581104,'de_DE') 
;

-- 2022-07-12T10:18:34.155Z
UPDATE AD_Element_Trl SET Description='Manufacturer of the object under test', IsTranslated='Y', Name='Manufacturer', PrintName='Manufacturer',Updated=TO_TIMESTAMP('2022-07-12 12:18:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581104 AND AD_Language='en_US'
;

-- 2022-07-12T10:18:34.157Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581104,'en_US') 
;

-- Column: C_Project_WO_ObjectUnderTest.WOManufacturer
-- 2022-07-12T10:19:21.009Z
UPDATE AD_Column SET AD_Element_ID=581104, ColumnName='WOManufacturer', Description='Hersteller des Prüfgegenstandes', Help='', Name='Hersteller',Updated=TO_TIMESTAMP('2022-07-12 12:19:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583630
;

-- 2022-07-12T10:19:21.012Z
UPDATE AD_Field SET Name='Hersteller', Description='Hersteller des Prüfgegenstandes', Help='' WHERE AD_Column_ID=583630
;

-- 2022-07-12T10:19:21.013Z
/* DDL */  select update_Column_Translation_From_AD_Element(581104) 
;

-- 2022-07-12T10:19:22.639Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_ObjectUnderTest','ALTER TABLE public.C_Project_WO_ObjectUnderTest ADD COLUMN WOManufacturer VARCHAR(255)')
;

-- 2022-07-12T10:20:21.416Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581105,0,'WOObjectType',TO_TIMESTAMP('2022-07-12 12:20:21','YYYY-MM-DD HH24:MI:SS'),100,'Art des Prüfgegenstandes','D','Y','Klasse','Klasse',TO_TIMESTAMP('2022-07-12 12:20:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:20:21.418Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581105 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:20:24.785Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581105 AND AD_Language='de_CH'
;

-- 2022-07-12T10:20:24.787Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581105,'de_CH') 
;

-- 2022-07-12T10:20:26.496Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:20:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581105 AND AD_Language='de_DE'
;

-- 2022-07-12T10:20:26.497Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581105,'de_DE') 
;

-- 2022-07-12T10:20:26.509Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581105,'de_DE') 
;

-- 2022-07-12T10:20:43.299Z
UPDATE AD_Element_Trl SET Description='Type of test item', IsTranslated='Y', Name='Class', PrintName='Class',Updated=TO_TIMESTAMP('2022-07-12 12:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581105 AND AD_Language='en_US'
;

-- 2022-07-12T10:20:43.301Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581105,'en_US') 
;

-- Column: C_Project_WO_ObjectUnderTest.WOObjectType
-- 2022-07-12T10:20:57.562Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583631,581105,0,10,542184,'WOObjectType',TO_TIMESTAMP('2022-07-12 12:20:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Art des Prüfgegenstandes','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Klasse',0,0,TO_TIMESTAMP('2022-07-12 12:20:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:20:57.564Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583631 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:20:57.568Z
/* DDL */  select update_Column_Translation_From_AD_Element(581105) 
;

-- 2022-07-12T10:20:58.289Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_ObjectUnderTest','ALTER TABLE public.C_Project_WO_ObjectUnderTest ADD COLUMN WOObjectType VARCHAR(255)')
;

-- 2022-07-12T10:21:37.741Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581106,0,'WOObjectName',TO_TIMESTAMP('2022-07-12 12:21:37','YYYY-MM-DD HH24:MI:SS'),100,'Name des Prüfgegenstandes (z.B. Typbezeichnung)','D','Y','Name','Name',TO_TIMESTAMP('2022-07-12 12:21:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:21:37.743Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581106 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:21:42.504Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:21:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581106 AND AD_Language='de_CH'
;

-- 2022-07-12T10:21:42.506Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581106,'de_CH') 
;

-- 2022-07-12T10:21:44.449Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:21:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581106 AND AD_Language='de_DE'
;

-- 2022-07-12T10:21:44.450Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581106,'de_DE') 
;

-- 2022-07-12T10:21:44.457Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581106,'de_DE') 
;

-- 2022-07-12T10:21:57.533Z
UPDATE AD_Element_Trl SET Description='Name of the test item (e.g. type designation)', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:21:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581106 AND AD_Language='en_US'
;

-- 2022-07-12T10:21:57.536Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581106,'en_US') 
;

-- Column: C_Project_WO_ObjectUnderTest.WOObjectName
-- 2022-07-12T10:22:14.622Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583632,581106,0,10,542184,'WOObjectName',TO_TIMESTAMP('2022-07-12 12:22:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Name des Prüfgegenstandes (z.B. Typbezeichnung)','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Name',0,0,TO_TIMESTAMP('2022-07-12 12:22:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:22:14.624Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583632 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:22:14.629Z
/* DDL */  select update_Column_Translation_From_AD_Element(581106) 
;

-- 2022-07-12T10:22:15.357Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_ObjectUnderTest','ALTER TABLE public.C_Project_WO_ObjectUnderTest ADD COLUMN WOObjectName VARCHAR(255)')
;

-- 2022-07-12T10:22:26.690Z
INSERT INTO t_alter_column values('c_project_wo_objectundertest','WOObjectName','VARCHAR(255)',null,null)
;

-- 2022-07-12T10:22:56.596Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581107,0,'WOObjectWhereabouts',TO_TIMESTAMP('2022-07-12 12:22:56','YYYY-MM-DD HH24:MI:SS'),100,'Verbleib des Prüfgegenstandes nach der Prüfung.','D','Y','Verbleib','Verbleib',TO_TIMESTAMP('2022-07-12 12:22:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:22:56.598Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581107 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:23:00.559Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:23:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581107 AND AD_Language='de_CH'
;

-- 2022-07-12T10:23:00.561Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581107,'de_CH') 
;

-- 2022-07-12T10:23:02.784Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581107 AND AD_Language='de_DE'
;

-- 2022-07-12T10:23:02.786Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581107,'de_DE') 
;

-- 2022-07-12T10:23:02.793Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581107,'de_DE') 
;

-- 2022-07-12T10:23:21.387Z
UPDATE AD_Element_Trl SET Description='Whereabouts of the test item after the test', IsTranslated='Y', Name='Whereabouts', PrintName='Whereabouts',Updated=TO_TIMESTAMP('2022-07-12 12:23:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581107 AND AD_Language='en_US'
;

-- 2022-07-12T10:23:21.389Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581107,'en_US') 
;

-- Column: C_Project_WO_ObjectUnderTest.WOObjectWhereabouts
-- 2022-07-12T10:23:34.521Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583633,581107,0,10,542184,'WOObjectWhereabouts',TO_TIMESTAMP('2022-07-12 12:23:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Verbleib des Prüfgegenstandes nach der Prüfung.','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verbleib',0,0,TO_TIMESTAMP('2022-07-12 12:23:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:23:34.524Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583633 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:23:34.528Z
/* DDL */  select update_Column_Translation_From_AD_Element(581107) 
;

-- 2022-07-12T10:23:35.257Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_ObjectUnderTest','ALTER TABLE public.C_Project_WO_ObjectUnderTest ADD COLUMN WOObjectWhereabouts VARCHAR(255)')
;

