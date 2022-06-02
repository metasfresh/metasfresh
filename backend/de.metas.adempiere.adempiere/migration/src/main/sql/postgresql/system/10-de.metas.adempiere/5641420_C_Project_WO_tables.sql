-- Table: C_Project_WO_Step
-- 2022-05-31T06:04:38.691Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542159,'N',TO_TIMESTAMP('2022-05-31 09:04:38','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','Y','N','N','N',0,'Project Step','NP','L','C_Project_WO_Step','DTI',TO_TIMESTAMP('2022-05-31 09:04:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T06:04:38.694Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542159 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-05-31T06:04:38.814Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555896,TO_TIMESTAMP('2022-05-31 09:04:38','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Project_WO_Step',1,'Y','N','Y','Y','C_Project_WO_Step','N',1000000,TO_TIMESTAMP('2022-05-31 09:04:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T06:04:38.825Z
CREATE SEQUENCE C_PROJECT_WO_STEP_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Project_WO_Step.AD_Client_ID
-- 2022-05-31T06:04:48.647Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583223,102,0,19,542159,'AD_Client_ID',TO_TIMESTAMP('2022-05-31 09:04:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-05-31 09:04:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:04:48.652Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583223 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:04:48.687Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Project_WO_Step.AD_Org_ID
-- 2022-05-31T06:04:50.256Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583224,113,0,30,542159,'AD_Org_ID',TO_TIMESTAMP('2022-05-31 09:04:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-05-31 09:04:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:04:50.258Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583224 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:04:50.262Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Project_WO_Step.Created
-- 2022-05-31T06:04:51.295Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583225,245,0,16,542159,'Created',TO_TIMESTAMP('2022-05-31 09:04:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-05-31 09:04:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:04:51.298Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583225 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:04:51.305Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Project_WO_Step.CreatedBy
-- 2022-05-31T06:04:52.237Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583226,246,0,18,110,542159,'CreatedBy',TO_TIMESTAMP('2022-05-31 09:04:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-05-31 09:04:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:04:52.239Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583226 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:04:52.242Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Project_WO_Step.IsActive
-- 2022-05-31T06:04:53.059Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583227,348,0,20,542159,'IsActive',TO_TIMESTAMP('2022-05-31 09:04:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-05-31 09:04:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:04:53.061Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583227 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:04:53.065Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Project_WO_Step.Updated
-- 2022-05-31T06:04:53.934Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583228,607,0,16,542159,'Updated',TO_TIMESTAMP('2022-05-31 09:04:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-05-31 09:04:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:04:53.937Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583228 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:04:53.940Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Project_WO_Step.UpdatedBy
-- 2022-05-31T06:04:54.639Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583229,608,0,18,110,542159,'UpdatedBy',TO_TIMESTAMP('2022-05-31 09:04:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-05-31 09:04:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:04:54.641Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583229 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:04:54.645Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-05-31T06:04:55.316Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580965,0,'C_Project_WO_Step_ID',TO_TIMESTAMP('2022-05-31 09:04:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Project Step','Project Step',TO_TIMESTAMP('2022-05-31 09:04:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T06:04:55.326Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580965 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_WO_Step.C_Project_WO_Step_ID
-- 2022-05-31T06:04:55.961Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583230,580965,0,13,542159,'C_Project_WO_Step_ID',TO_TIMESTAMP('2022-05-31 09:04:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Project Step',0,0,TO_TIMESTAMP('2022-05-31 09:04:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:04:55.964Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583230 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:04:55.967Z
/* DDL */  select update_Column_Translation_From_AD_Element(580965) 
;

-- Column: C_Project_WO_Step.C_Project_ID
-- 2022-05-31T06:05:20.087Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583231,208,0,30,542159,'C_Project_ID',TO_TIMESTAMP('2022-05-31 09:05:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Financial Project','D',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Projekt',0,0,TO_TIMESTAMP('2022-05-31 09:05:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:05:20.090Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583231 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:05:20.094Z
/* DDL */  select update_Column_Translation_From_AD_Element(208) 
;

-- Column: C_Project_WO_Step.SeqNo
-- 2022-05-31T06:07:29.428Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583232,566,0,11,542159,'SeqNo',TO_TIMESTAMP('2022-05-31 09:07:29','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D',0,10,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2022-05-31 09:07:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:07:29.431Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583232 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:07:29.435Z
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

-- Column: C_Project_WO_Step.Name
-- 2022-05-31T06:08:07.406Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583233,469,0,10,542159,'Name',TO_TIMESTAMP('2022-05-31 09:08:07','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,255,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2022-05-31 09:08:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:08:07.409Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583233 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:08:07.412Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Column: C_Project_WO_Step.Description
-- 2022-05-31T06:08:30.510Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583234,275,0,10,542159,'Description',TO_TIMESTAMP('2022-05-31 09:08:30','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2022-05-31 09:08:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:08:30.512Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583234 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:08:30.516Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: C_Project_WO_Step.Name
-- 2022-05-31T06:08:49.152Z
UPDATE AD_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2022-05-31 09:08:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583233
;

-- Column: C_Project_WO_Step.SeqNo
-- 2022-05-31T06:09:02.016Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-05-31 09:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583232
;

-- Table: C_Project_WO_Resource
-- 2022-05-31T06:25:47.525Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542161,'N',TO_TIMESTAMP('2022-05-31 09:25:47','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','Y','N','N','N',0,'Project Resource','NP','L','C_Project_WO_Resource','DTI',TO_TIMESTAMP('2022-05-31 09:25:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T06:25:47.530Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542161 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-05-31T06:25:47.634Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555897,TO_TIMESTAMP('2022-05-31 09:25:47','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Project_WO_Resource',1,'Y','N','Y','Y','C_Project_WO_Resource','N',1000000,TO_TIMESTAMP('2022-05-31 09:25:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T06:25:47.645Z
CREATE SEQUENCE C_PROJECT_WO_RESOURCE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Project_WO_Resource.AD_Client_ID
-- 2022-05-31T06:26:00.807Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583235,102,0,19,542161,'AD_Client_ID',TO_TIMESTAMP('2022-05-31 09:26:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-05-31 09:26:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:26:00.812Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583235 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:26:00.819Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Project_WO_Resource.AD_Org_ID
-- 2022-05-31T06:26:01.894Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583236,113,0,30,542161,'AD_Org_ID',TO_TIMESTAMP('2022-05-31 09:26:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-05-31 09:26:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:26:01.896Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583236 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:26:01.900Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Project_WO_Resource.Created
-- 2022-05-31T06:26:02.844Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583237,245,0,16,542161,'Created',TO_TIMESTAMP('2022-05-31 09:26:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-05-31 09:26:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:26:02.847Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583237 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:26:02.851Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Project_WO_Resource.CreatedBy
-- 2022-05-31T06:26:03.586Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583238,246,0,18,110,542161,'CreatedBy',TO_TIMESTAMP('2022-05-31 09:26:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-05-31 09:26:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:26:03.589Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583238 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:26:03.594Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Project_WO_Resource.IsActive
-- 2022-05-31T06:26:04.350Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583239,348,0,20,542161,'IsActive',TO_TIMESTAMP('2022-05-31 09:26:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-05-31 09:26:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:26:04.353Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583239 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:26:04.358Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Project_WO_Resource.Updated
-- 2022-05-31T06:26:05.114Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583240,607,0,16,542161,'Updated',TO_TIMESTAMP('2022-05-31 09:26:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-05-31 09:26:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:26:05.117Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583240 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:26:05.120Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Project_WO_Resource.UpdatedBy
-- 2022-05-31T06:26:05.915Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583241,608,0,18,110,542161,'UpdatedBy',TO_TIMESTAMP('2022-05-31 09:26:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-05-31 09:26:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:26:05.917Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583241 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:26:05.921Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-05-31T06:26:06.657Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580966,0,'C_Project_WO_Resource_ID',TO_TIMESTAMP('2022-05-31 09:26:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Project Resource','Project Resource',TO_TIMESTAMP('2022-05-31 09:26:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T06:26:06.660Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580966 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_WO_Resource.C_Project_WO_Resource_ID
-- 2022-05-31T06:26:07.228Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583242,580966,0,13,542161,'C_Project_WO_Resource_ID',TO_TIMESTAMP('2022-05-31 09:26:06','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Project Resource',0,0,TO_TIMESTAMP('2022-05-31 09:26:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:26:07.231Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583242 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:26:07.234Z
/* DDL */  select update_Column_Translation_From_AD_Element(580966) 
;

-- Column: C_Project_WO_Resource.C_Project_ID
-- 2022-05-31T06:26:29.541Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583243,208,0,30,542161,'C_Project_ID',TO_TIMESTAMP('2022-05-31 09:26:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Financial Project','D',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Projekt',0,0,TO_TIMESTAMP('2022-05-31 09:26:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:26:29.544Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583243 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:26:29.549Z
/* DDL */  select update_Column_Translation_From_AD_Element(208) 
;

-- Column: C_Project_WO_Resource.C_Project_WO_Step_ID
-- 2022-05-31T06:26:50.627Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583244,580965,0,30,542161,'C_Project_WO_Step_ID',TO_TIMESTAMP('2022-05-31 09:26:50','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Project Step',0,0,TO_TIMESTAMP('2022-05-31 09:26:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:26:50.630Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583244 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:26:50.634Z
/* DDL */  select update_Column_Translation_From_AD_Element(580965) 
;

-- Column: C_Project_WO_Resource.S_Resource_ID
-- 2022-05-31T06:27:10.963Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583245,1777,0,30,542161,'S_Resource_ID',TO_TIMESTAMP('2022-05-31 09:27:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Ressource','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Ressource',0,0,TO_TIMESTAMP('2022-05-31 09:27:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:27:10.966Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583245 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:27:10.970Z
/* DDL */  select update_Column_Translation_From_AD_Element(1777) 
;

-- Column: C_Project_WO_Resource.StartDate
-- 2022-05-31T06:27:34.719Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583246,574,0,16,542161,'StartDate',TO_TIMESTAMP('2022-05-31 09:27:34','YYYY-MM-DD HH24:MI:SS'),100,'N','First effective day (inclusive)','D',0,7,'The Start Date indicates the first or starting date','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Anfangsdatum',0,0,TO_TIMESTAMP('2022-05-31 09:27:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:27:34.721Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583246 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:27:34.725Z
/* DDL */  select update_Column_Translation_From_AD_Element(574) 
;

-- Column: C_Project_WO_Resource.StartDate
-- 2022-05-31T06:27:42.847Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-05-31 09:27:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583246
;

-- Column: C_Project_WO_Resource.AssignDateTo
-- 2022-05-31T06:34:59.062Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583247,1755,0,16,542161,'AssignDateTo',TO_TIMESTAMP('2022-05-31 09:34:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Ressource zuordnen bis','D',0,7,'Zuordnung endet','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Zuordnung bis',0,0,TO_TIMESTAMP('2022-05-31 09:34:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:34:59.066Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583247 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:34:59.075Z
/* DDL */  select update_Column_Translation_From_AD_Element(1755) 
;

-- Column: C_Project_WO_Resource.AssignDateFrom
-- 2022-05-31T06:35:21.349Z
UPDATE AD_Column SET AD_Element_ID=1754, ColumnName='AssignDateFrom', Description='Ressource zuordnen ab', Help='Beginn Zuordnung', Name='Zuordnung von',Updated=TO_TIMESTAMP('2022-05-31 09:35:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583246
;

-- 2022-05-31T06:35:21.353Z
UPDATE AD_Field SET Name='Zuordnung von', Description='Ressource zuordnen ab', Help='Beginn Zuordnung' WHERE AD_Column_ID=583246
;

-- 2022-05-31T06:35:21.356Z
/* DDL */  select update_Column_Translation_From_AD_Element(1754) 
;

-- Column: C_Project_WO_Resource.IsAllDay
-- 2022-05-31T06:35:45.954Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583248,580861,0,20,542161,'IsAllDay',TO_TIMESTAMP('2022-05-31 09:35:45','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'All day',0,0,TO_TIMESTAMP('2022-05-31 09:35:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:35:45.957Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583248 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:35:45.961Z
/* DDL */  select update_Column_Translation_From_AD_Element(580861) 
;

-- Column: C_Project_WO_Resource.Duration
-- 2022-05-31T06:36:24.073Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583249,2320,0,22,542161,'Duration',TO_TIMESTAMP('2022-05-31 09:36:23','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Normal Duration in Duration Unit','D',0,10,'Expected (normal) Length of time for the execution','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Duration',0,0,TO_TIMESTAMP('2022-05-31 09:36:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:36:24.077Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583249 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:36:24.082Z
/* DDL */  select update_Column_Translation_From_AD_Element(2320) 
;

-- Column: C_Project_WO_Resource.DurationUnit
-- 2022-05-31T06:37:50.050Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583250,2321,0,17,299,542161,'DurationUnit',TO_TIMESTAMP('2022-05-31 09:37:49','YYYY-MM-DD HH24:MI:SS'),100,'N','h','Unit of Duration','D',0,1,'Unit to define the length of time for the execution','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Duration Unit',0,0,TO_TIMESTAMP('2022-05-31 09:37:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:37:50.053Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583250 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:37:50.057Z
/* DDL */  select update_Column_Translation_From_AD_Element(2321) 
;

-- Column: C_Project_WO_Resource.Description
-- 2022-05-31T06:38:50.788Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583251,275,0,10,542161,'Description',TO_TIMESTAMP('2022-05-31 09:38:50','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2022-05-31 09:38:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:38:50.790Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583251 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:38:50.795Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: C_Project_Resource_Budget.S_Resource_Group_ID
-- 2022-05-31T06:41:15.809Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-05-31 09:41:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583211
;

-- Column: C_Project_Resource_Budget.S_Resource_ID
-- 2022-05-31T06:41:33.479Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-05-31 09:41:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583212
;

-- Column: C_Project_WO_Resource.C_Project_Resource_Budget_ID
-- 2022-05-31T06:41:40.918Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583252,580947,0,30,542161,'C_Project_Resource_Budget_ID',TO_TIMESTAMP('2022-05-31 09:41:40','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Project Resource Budget',0,0,TO_TIMESTAMP('2022-05-31 09:41:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:41:40.920Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583252 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:41:40.925Z
/* DDL */  select update_Column_Translation_From_AD_Element(580947) 
;

-- 2022-05-31T06:42:57.372Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580967,0,'Budget_Project_ID',TO_TIMESTAMP('2022-05-31 09:42:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Budget Project','Budget Project',TO_TIMESTAMP('2022-05-31 09:42:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T06:42:57.375Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580967 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_WO_Resource.Budget_Project_ID
-- 2022-05-31T06:43:33.170Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583253,580967,0,30,541136,542161,'Budget_Project_ID',TO_TIMESTAMP('2022-05-31 09:43:33','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Budget Project',0,0,TO_TIMESTAMP('2022-05-31 09:43:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:43:33.172Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583253 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:43:33.176Z
/* DDL */  select update_Column_Translation_From_AD_Element(580967) 
;

-- Column: C_Project_WO_Step.DateStart
-- 2022-05-31T06:55:46.296Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583254,53280,0,16,542159,'DateStart',TO_TIMESTAMP('2022-05-31 09:55:46','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Startdatum',0,0,TO_TIMESTAMP('2022-05-31 09:55:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:55:46.300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583254 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:55:46.305Z
/* DDL */  select update_Column_Translation_From_AD_Element(53280) 
;

-- Column: C_Project_WO_Step.DateEnd
-- 2022-05-31T06:56:15.887Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583255,579243,0,16,542159,'DateEnd',TO_TIMESTAMP('2022-05-31 09:56:15','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,' Endzeitpunkt',0,0,TO_TIMESTAMP('2022-05-31 09:56:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T06:56:15.891Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583255 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T06:56:15.895Z
/* DDL */  select update_Column_Translation_From_AD_Element(579243) 
;

-- 2022-05-31T06:56:44.169Z
/* DDL */ CREATE TABLE public.C_Project_WO_Step (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Project_ID NUMERIC(10) NOT NULL, C_Project_WO_Step_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DateEnd TIMESTAMP WITH TIME ZONE, DateStart TIMESTAMP WITH TIME ZONE, Description VARCHAR(2000), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(255) NOT NULL, SeqNo NUMERIC(10) DEFAULT 0 NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CProject_CProjectWOStep FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Project_WO_Step_Key PRIMARY KEY (C_Project_WO_Step_ID))
;

-- 2022-05-31T06:56:54.162Z
/* DDL */ CREATE TABLE public.C_Project_WO_Resource (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AssignDateFrom TIMESTAMP WITH TIME ZONE NOT NULL, AssignDateTo TIMESTAMP WITH TIME ZONE NOT NULL, Budget_Project_ID NUMERIC(10), C_Project_ID NUMERIC(10) NOT NULL, C_Project_Resource_Budget_ID NUMERIC(10), C_Project_WO_Resource_ID NUMERIC(10) NOT NULL, C_Project_WO_Step_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(2000), Duration NUMERIC DEFAULT 0 NOT NULL, DurationUnit CHAR(1) DEFAULT 'h' NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsAllDay CHAR(1) DEFAULT 'N' CHECK (IsAllDay IN ('Y','N')) NOT NULL, S_Resource_ID NUMERIC(10), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT BudgetProject_CProjectWOResource FOREIGN KEY (Budget_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CProject_CProjectWOResource FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CProjectResourceBudget_CProjectWOResource FOREIGN KEY (C_Project_Resource_Budget_ID) REFERENCES public.C_Project_Resource_Budget DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Project_WO_Resource_Key PRIMARY KEY (C_Project_WO_Resource_ID), CONSTRAINT CProjectWOStep_CProjectWOResource FOREIGN KEY (C_Project_WO_Step_ID) REFERENCES public.C_Project_WO_Step DEFERRABLE INITIALLY DEFERRED, CONSTRAINT SResource_CProjectWOResource FOREIGN KEY (S_Resource_ID) REFERENCES public.S_Resource DEFERRABLE INITIALLY DEFERRED)
;

-- Window: Budget Project, InternalName=null
-- 2022-05-31T07:20:51.572Z
UPDATE AD_Window SET AD_Element_ID=580967, Description=NULL, Help=NULL, Name='Budget Project',Updated=TO_TIMESTAMP('2022-05-31 10:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541506
;

-- Name: Budget Project
-- Window: Budget Project
-- 2022-05-31T07:20:51.592Z
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Budget Project',Updated=TO_TIMESTAMP('2022-05-31 10:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541957
;

-- 2022-05-31T07:20:51.629Z
/* DDL */  select update_window_translation_from_ad_element(580967) 
;

-- 2022-05-31T07:20:51.725Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541506
;

-- 2022-05-31T07:20:51.736Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541506)
;

-- Name: Budget Project
-- Window: Budget Project
-- 2022-05-31T07:21:30.964Z
UPDATE AD_Menu SET AD_Element_ID=580967, Description=NULL, InternalName='budgetProject', Name='Budget Project', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2022-05-31 10:21:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541957
;

-- 2022-05-31T07:21:30.966Z
/* DDL */  select update_menu_translation_from_ad_element(580967) 
;

-- Window: Budget Project, InternalName=budgetProject
-- 2022-05-31T07:21:48.635Z
UPDATE AD_Window SET InternalName='budgetProject',Updated=TO_TIMESTAMP('2022-05-31 10:21:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541506
;

-- 2022-05-31T07:22:32.670Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580968,0,'WorkOrder_Project_ID',TO_TIMESTAMP('2022-05-31 10:22:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Work Order Project','Work Order Project',TO_TIMESTAMP('2022-05-31 10:22:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:22:32.674Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580968 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Work Order Project, InternalName=null
-- 2022-05-31T07:22:43.751Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,580968,0,541512,TO_TIMESTAMP('2022-05-31 10:22:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Work Order Project','N',TO_TIMESTAMP('2022-05-31 10:22:43','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-05-31T07:22:43.755Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541512 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-05-31T07:22:43.759Z
/* DDL */  select update_window_translation_from_ad_element(580968) 
;

-- 2022-05-31T07:22:43.762Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541512
;

-- 2022-05-31T07:22:43.764Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541512)
;

-- Tab: Work Order Project -> Project Resource Budget
-- Table: C_Project_Resource_Budget
-- 2022-05-31T07:23:49.195Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583210,580947,0,546288,542157,541512,'Y',TO_TIMESTAMP('2022-05-31 10:23:48','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_Resource_Budget','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Project Resource Budget',1349,'N',20,1,TO_TIMESTAMP('2022-05-31 10:23:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:49.199Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546288 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-05-31T07:23:49.202Z
/* DDL */  select update_tab_translation_from_ad_element(580947) 
;

-- 2022-05-31T07:23:49.205Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546288)
;

-- 2022-05-31T07:23:49.211Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 546288
;

-- 2022-05-31T07:23:49.213Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 546288, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 546281
;

-- Field: Work Order Project -> Project Resource Budget -> Mandant
-- Column: C_Project_Resource_Budget.AD_Client_ID
-- 2022-05-31T07:23:49.346Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583202,697946,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',10,1,1,TO_TIMESTAMP('2022-05-31 10:23:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:49.350Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697946 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:49.357Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-05-31T07:23:50.596Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697946
;

-- 2022-05-31T07:23:50.597Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697946)
;

-- 2022-05-31T07:23:50.602Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697946
;

-- 2022-05-31T07:23:50.604Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697946, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697305
;

-- Field: Work Order Project -> Project Resource Budget -> Sektion
-- Column: C_Project_Resource_Budget.AD_Org_ID
-- 2022-05-31T07:23:50.707Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583203,697947,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:50','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','Sektion',20,1,1,TO_TIMESTAMP('2022-05-31 10:23:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:50.709Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697947 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:50.711Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-05-31T07:23:51.774Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697947
;

-- 2022-05-31T07:23:51.775Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697947)
;

-- 2022-05-31T07:23:51.779Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697947
;

-- 2022-05-31T07:23:51.780Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697947, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697306
;

-- Field: Work Order Project -> Project Resource Budget -> Aktiv
-- Column: C_Project_Resource_Budget.IsActive
-- 2022-05-31T07:23:51.911Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583206,697948,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:51','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',30,1,1,TO_TIMESTAMP('2022-05-31 10:23:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:51.913Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697948 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:51.916Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-05-31T07:23:52.485Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697948
;

-- 2022-05-31T07:23:52.486Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697948)
;

-- 2022-05-31T07:23:52.489Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697948
;

-- 2022-05-31T07:23:52.490Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697948, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697307
;

-- Field: Work Order Project -> Project Resource Budget -> Project Resource Budget
-- Column: C_Project_Resource_Budget.C_Project_Resource_Budget_ID
-- 2022-05-31T07:23:52.605Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583209,697949,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:52','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','Project Resource Budget',40,1,1,TO_TIMESTAMP('2022-05-31 10:23:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:52.607Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697949 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:52.609Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580947) 
;

-- 2022-05-31T07:23:52.612Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697949
;

-- 2022-05-31T07:23:52.613Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697949)
;

-- 2022-05-31T07:23:52.617Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697949
;

-- 2022-05-31T07:23:52.618Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697949, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697308
;

-- Field: Work Order Project -> Project Resource Budget -> Projekt
-- Column: C_Project_Resource_Budget.C_Project_ID
-- 2022-05-31T07:23:52.722Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583210,697950,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:52','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.',0,'Y','N','N','N','N','N','N','N','Projekt',50,1,1,TO_TIMESTAMP('2022-05-31 10:23:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:52.724Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697950 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:52.726Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-05-31T07:23:52.765Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697950
;

-- 2022-05-31T07:23:52.766Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697950)
;

-- 2022-05-31T07:23:52.769Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697950
;

-- 2022-05-31T07:23:52.770Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697950, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697309
;

-- Field: Work Order Project -> Project Resource Budget -> Resource Group
-- Column: C_Project_Resource_Budget.S_Resource_Group_ID
-- 2022-05-31T07:23:52.873Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583211,697951,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:52','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','Y','N','N','N','N','N','N','Resource Group',10,1,1,TO_TIMESTAMP('2022-05-31 10:23:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:52.875Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697951 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:52.877Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580932) 
;

-- 2022-05-31T07:23:52.881Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697951
;

-- 2022-05-31T07:23:52.882Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697951)
;

-- 2022-05-31T07:23:52.888Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697951
;

-- 2022-05-31T07:23:52.889Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697951, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697310
;

-- Field: Work Order Project -> Project Resource Budget -> Ressource
-- Column: C_Project_Resource_Budget.S_Resource_ID
-- 2022-05-31T07:23:52.980Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583212,697952,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:52','YYYY-MM-DD HH24:MI:SS'),100,'Ressource',10,'D',0,'Y','Y','N','N','N','N','N','N','Ressource',20,1,1,TO_TIMESTAMP('2022-05-31 10:23:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:52.982Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697952 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:52.983Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1777) 
;

-- 2022-05-31T07:23:52.993Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697952
;

-- 2022-05-31T07:23:52.994Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697952)
;

-- 2022-05-31T07:23:52.998Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697952
;

-- 2022-05-31T07:23:52.999Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697952, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697311
;

-- Field: Work Order Project -> Project Resource Budget -> VK Total
-- Column: C_Project_Resource_Budget.PlannedAmt
-- 2022-05-31T07:23:53.106Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583213,697953,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100,'VK Total',10,'D','The Planned Amount indicates the anticipated amount for this project or project line.',0,'Y','Y','N','N','N','N','N','N','VK Total',30,1,1,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:53.108Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697953 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:53.111Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1564) 
;

-- 2022-05-31T07:23:53.116Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697953
;

-- 2022-05-31T07:23:53.117Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697953)
;

-- 2022-05-31T07:23:53.121Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697953
;

-- 2022-05-31T07:23:53.122Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697953, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697312
;

-- Field: Work Order Project -> Project Resource Budget -> Maßeinheit für Zeit
-- Column: C_Project_Resource_Budget.C_UOM_Time_ID
-- 2022-05-31T07:23:53.227Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583214,697954,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100,'Standardmaßeinheit für Zeit',10,'D','"Maßeinheit für Zeit" bezeichnet die Standardmaßeinheit, die bei Produkten mit Zeitangabe auf Belegen verwendet wird.',0,'Y','Y','N','N','N','N','N','N','Maßeinheit für Zeit',50,1,1,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:53.229Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697954 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:53.231Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(219) 
;

-- 2022-05-31T07:23:53.235Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697954
;

-- 2022-05-31T07:23:53.237Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697954)
;

-- 2022-05-31T07:23:53.240Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697954
;

-- 2022-05-31T07:23:53.242Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697954, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697313
;

-- Field: Work Order Project -> Project Resource Budget -> Price / Time UOM
-- Column: C_Project_Resource_Budget.PricePerTimeUOM
-- 2022-05-31T07:23:53.343Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583215,697955,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','Y','N','N','N','N','N','N','Price / Time UOM',60,1,1,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:53.345Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697955 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:53.347Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580948) 
;

-- 2022-05-31T07:23:53.349Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697955
;

-- 2022-05-31T07:23:53.350Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697955)
;

-- 2022-05-31T07:23:53.353Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697955
;

-- 2022-05-31T07:23:53.355Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697955, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697314
;

-- Field: Work Order Project -> Project Resource Budget -> Planned Duration
-- Column: C_Project_Resource_Budget.PlannedDuration
-- 2022-05-31T07:23:53.461Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583216,697956,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','Y','N','N','N','N','N','N','Planned Duration',40,1,1,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:53.463Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697956 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:53.465Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580949) 
;

-- 2022-05-31T07:23:53.467Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697956
;

-- 2022-05-31T07:23:53.468Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697956)
;

-- 2022-05-31T07:23:53.472Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697956
;

-- 2022-05-31T07:23:53.473Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697956, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697315
;

-- Field: Work Order Project -> Project Resource Budget -> Start Plan
-- Column: C_Project_Resource_Budget.DateStartPlan
-- 2022-05-31T07:23:53.583Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583217,697957,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100,'Planned Start Date',7,'D','Date when you plan to start',0,'Y','Y','N','N','N','N','N','N','Start Plan',70,1,1,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:53.585Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697957 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:53.586Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2901) 
;

-- 2022-05-31T07:23:53.592Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697957
;

-- 2022-05-31T07:23:53.593Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697957)
;

-- 2022-05-31T07:23:53.596Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697957
;

-- 2022-05-31T07:23:53.598Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697957, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697316
;

-- Field: Work Order Project -> Project Resource Budget -> Finish Plan
-- Column: C_Project_Resource_Budget.DateFinishPlan
-- 2022-05-31T07:23:53.704Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583218,697958,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100,'Planned Finish Date',7,'D','Date when you plan to finish',0,'Y','Y','N','N','N','N','N','N','Finish Plan',80,1,1,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:53.706Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697958 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:53.709Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541244) 
;

-- 2022-05-31T07:23:53.712Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697958
;

-- 2022-05-31T07:23:53.714Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697958)
;

-- 2022-05-31T07:23:53.717Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697958
;

-- 2022-05-31T07:23:53.718Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697958, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697317
;

-- Field: Work Order Project -> Project Resource Budget -> Beschreibung
-- Column: C_Project_Resource_Budget.Description
-- 2022-05-31T07:23:53.822Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583219,697959,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100,2000,'D',0,'Y','Y','N','N','N','N','N','N','Beschreibung',90,1,1,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:53.824Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697959 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:53.826Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-05-31T07:23:53.916Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697959
;

-- 2022-05-31T07:23:53.917Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697959)
;

-- 2022-05-31T07:23:53.920Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697959
;

-- 2022-05-31T07:23:53.921Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697959, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697318
;

-- Field: Work Order Project -> Project Resource Budget -> Währung
-- Column: C_Project_Resource_Budget.C_Currency_ID
-- 2022-05-31T07:23:54.028Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583222,697960,0,546288,0,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',10,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','N','N','N','N','N','N','N','Währung',100,1,1,TO_TIMESTAMP('2022-05-31 10:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:54.030Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697960 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:23:54.032Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2022-05-31T07:23:54.072Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697960
;

-- 2022-05-31T07:23:54.074Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697960)
;

-- 2022-05-31T07:23:54.077Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697960
;

-- 2022-05-31T07:23:54.078Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697960, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 697943
;

-- 2022-05-31T07:23:54.203Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546288,544936,TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-05-31T07:23:54.207Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544936 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-05-31T07:23:54.214Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544936
;

-- 2022-05-31T07:23:54.218Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544936, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544927
;

-- 2022-05-31T07:23:54.330Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545968,544936,TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:54.453Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545968,549185,TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','resource',10,TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource Budget.Resource Group
-- Column: C_Project_Resource_Budget.S_Resource_Group_ID
-- 2022-05-31T07:23:54.613Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697951,0,546288,549185,608282,'F',TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Resource Group',10,0,0,TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource Budget.Ressource
-- Column: C_Project_Resource_Budget.S_Resource_ID
-- 2022-05-31T07:23:54.729Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697952,0,546288,549185,608283,'F',TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100,'Ressource','Y','N','N','Y','N','N','N','Ressource',20,0,0,TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:54.829Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545968,549186,TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','misc',40,TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource Budget.Beschreibung
-- Column: C_Project_Resource_Budget.Description
-- 2022-05-31T07:23:54.948Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697959,0,546288,549186,608284,'F',TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:55.046Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545969,544936,TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-05-31 10:23:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:55.148Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545969,549187,TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','amounts and duration',10,TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource Budget.Währung
-- Column: C_Project_Resource_Budget.C_Currency_ID
-- 2022-05-31T07:23:55.264Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697960,0,546288,549187,608285,'F',TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','N','N','Währung',5,0,0,TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource Budget.VK Total
-- Column: C_Project_Resource_Budget.PlannedAmt
-- 2022-05-31T07:23:55.376Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697953,0,546288,549187,608286,'F',TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100,'VK Total','The Planned Amount indicates the anticipated amount for this project or project line.','Y','N','N','Y','Y','N','N','VK Total',10,10,0,TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource Budget.Planned Duration
-- Column: C_Project_Resource_Budget.PlannedDuration
-- 2022-05-31T07:23:55.486Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697956,0,546288,549187,608287,'F',TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Planned Duration',20,20,0,TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource Budget.Price / Time UOM
-- Column: C_Project_Resource_Budget.PricePerTimeUOM
-- 2022-05-31T07:23:55.594Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697955,0,546288,549187,608288,'F',TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Price / Time UOM',30,0,0,TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource Budget.Maßeinheit für Zeit
-- Column: C_Project_Resource_Budget.C_UOM_Time_ID
-- 2022-05-31T07:23:55.703Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697954,0,546288,549187,608289,'F',TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100,'Standardmaßeinheit für Zeit','"Maßeinheit für Zeit" bezeichnet die Standardmaßeinheit, die bei Produkten mit Zeitangabe auf Belegen verwendet wird.','Y','N','N','Y','N','N','N','Maßeinheit für Zeit',40,0,0,TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:55.793Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545969,549188,TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',20,TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource Budget.Start Plan
-- Column: C_Project_Resource_Budget.DateStartPlan
-- 2022-05-31T07:23:55.894Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697957,0,546288,549188,608290,'F',TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100,'Planned Start Date','Date when you plan to start','Y','N','N','Y','Y','N','N','Start Plan',10,30,0,TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource Budget.Finish Plan
-- Column: C_Project_Resource_Budget.DateFinishPlan
-- 2022-05-31T07:23:56.005Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697958,0,546288,549188,608291,'F',TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100,'Planned Finish Date','Date when you plan to finish','Y','N','N','Y','Y','N','N','Finish Plan',20,40,0,TO_TIMESTAMP('2022-05-31 10:23:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:23:56.106Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545969,549189,TO_TIMESTAMP('2022-05-31 10:23:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','client and org',30,TO_TIMESTAMP('2022-05-31 10:23:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource Budget.Sektion
-- Column: C_Project_Resource_Budget.AD_Org_ID
-- 2022-05-31T07:23:56.219Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697947,0,546288,549189,608292,'F',TO_TIMESTAMP('2022-05-31 10:23:56','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N','Sektion',10,0,0,TO_TIMESTAMP('2022-05-31 10:23:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource Budget.Mandant
-- Column: C_Project_Resource_Budget.AD_Client_ID
-- 2022-05-31T07:23:56.324Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697946,0,546288,549189,608293,'F',TO_TIMESTAMP('2022-05-31 10:23:56','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',20,0,0,TO_TIMESTAMP('2022-05-31 10:23:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Work Order Project -> Project Resource Budget
-- Table: C_Project_Resource_Budget
-- UI Element: Work Order Project -> Project Resource Budget.Mandant
-- Column: C_Project_Resource_Budget.AD_Client_ID
-- 2022-05-31T07:24:18.849Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=608293
;

-- 2022-05-31T07:24:18.852Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697946
;

-- Field: Work Order Project -> Project Resource Budget -> Mandant
-- Column: C_Project_Resource_Budget.AD_Client_ID
-- 2022-05-31T07:24:18.856Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697946
;

-- 2022-05-31T07:24:18.862Z
DELETE FROM AD_Field WHERE AD_Field_ID=697946
;

-- UI Element: Work Order Project -> Project Resource Budget.Sektion
-- Column: C_Project_Resource_Budget.AD_Org_ID
-- 2022-05-31T07:24:18.883Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=608292
;

-- 2022-05-31T07:24:18.885Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697947
;

-- Field: Work Order Project -> Project Resource Budget -> Sektion
-- Column: C_Project_Resource_Budget.AD_Org_ID
-- 2022-05-31T07:24:18.888Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697947
;

-- 2022-05-31T07:24:18.894Z
DELETE FROM AD_Field WHERE AD_Field_ID=697947
;

-- 2022-05-31T07:24:18.902Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697948
;

-- Field: Work Order Project -> Project Resource Budget -> Aktiv
-- Column: C_Project_Resource_Budget.IsActive
-- 2022-05-31T07:24:18.906Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697948
;

-- 2022-05-31T07:24:18.911Z
DELETE FROM AD_Field WHERE AD_Field_ID=697948
;

-- 2022-05-31T07:24:18.919Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697949
;

-- Field: Work Order Project -> Project Resource Budget -> Project Resource Budget
-- Column: C_Project_Resource_Budget.C_Project_Resource_Budget_ID
-- 2022-05-31T07:24:18.923Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697949
;

-- 2022-05-31T07:24:18.929Z
DELETE FROM AD_Field WHERE AD_Field_ID=697949
;

-- 2022-05-31T07:24:18.936Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697950
;

-- Field: Work Order Project -> Project Resource Budget -> Projekt
-- Column: C_Project_Resource_Budget.C_Project_ID
-- 2022-05-31T07:24:18.940Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697950
;

-- 2022-05-31T07:24:18.945Z
DELETE FROM AD_Field WHERE AD_Field_ID=697950
;

-- UI Element: Work Order Project -> Project Resource Budget.Resource Group
-- Column: C_Project_Resource_Budget.S_Resource_Group_ID
-- 2022-05-31T07:24:18.964Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=608282
;

-- 2022-05-31T07:24:18.966Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697951
;

-- Field: Work Order Project -> Project Resource Budget -> Resource Group
-- Column: C_Project_Resource_Budget.S_Resource_Group_ID
-- 2022-05-31T07:24:18.969Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697951
;

-- 2022-05-31T07:24:18.974Z
DELETE FROM AD_Field WHERE AD_Field_ID=697951
;

-- UI Element: Work Order Project -> Project Resource Budget.Ressource
-- Column: C_Project_Resource_Budget.S_Resource_ID
-- 2022-05-31T07:24:18.993Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=608283
;

-- 2022-05-31T07:24:18.995Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697952
;

-- Field: Work Order Project -> Project Resource Budget -> Ressource
-- Column: C_Project_Resource_Budget.S_Resource_ID
-- 2022-05-31T07:24:18.998Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697952
;

-- 2022-05-31T07:24:19.004Z
DELETE FROM AD_Field WHERE AD_Field_ID=697952
;

-- UI Element: Work Order Project -> Project Resource Budget.VK Total
-- Column: C_Project_Resource_Budget.PlannedAmt
-- 2022-05-31T07:24:19.021Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=608286
;

-- 2022-05-31T07:24:19.024Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697953
;

-- Field: Work Order Project -> Project Resource Budget -> VK Total
-- Column: C_Project_Resource_Budget.PlannedAmt
-- 2022-05-31T07:24:19.027Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697953
;

-- 2022-05-31T07:24:19.032Z
DELETE FROM AD_Field WHERE AD_Field_ID=697953
;

-- UI Element: Work Order Project -> Project Resource Budget.Maßeinheit für Zeit
-- Column: C_Project_Resource_Budget.C_UOM_Time_ID
-- 2022-05-31T07:24:19.049Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=608289
;

-- 2022-05-31T07:24:19.051Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697954
;

-- Field: Work Order Project -> Project Resource Budget -> Maßeinheit für Zeit
-- Column: C_Project_Resource_Budget.C_UOM_Time_ID
-- 2022-05-31T07:24:19.054Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697954
;

-- 2022-05-31T07:24:19.059Z
DELETE FROM AD_Field WHERE AD_Field_ID=697954
;

-- UI Element: Work Order Project -> Project Resource Budget.Price / Time UOM
-- Column: C_Project_Resource_Budget.PricePerTimeUOM
-- 2022-05-31T07:24:19.076Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=608288
;

-- 2022-05-31T07:24:19.078Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697955
;

-- Field: Work Order Project -> Project Resource Budget -> Price / Time UOM
-- Column: C_Project_Resource_Budget.PricePerTimeUOM
-- 2022-05-31T07:24:19.081Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697955
;

-- 2022-05-31T07:24:19.086Z
DELETE FROM AD_Field WHERE AD_Field_ID=697955
;

-- UI Element: Work Order Project -> Project Resource Budget.Planned Duration
-- Column: C_Project_Resource_Budget.PlannedDuration
-- 2022-05-31T07:24:19.105Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=608287
;

-- 2022-05-31T07:24:19.107Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697956
;

-- Field: Work Order Project -> Project Resource Budget -> Planned Duration
-- Column: C_Project_Resource_Budget.PlannedDuration
-- 2022-05-31T07:24:19.110Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697956
;

-- 2022-05-31T07:24:19.115Z
DELETE FROM AD_Field WHERE AD_Field_ID=697956
;

-- UI Element: Work Order Project -> Project Resource Budget.Start Plan
-- Column: C_Project_Resource_Budget.DateStartPlan
-- 2022-05-31T07:24:19.133Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=608290
;

-- 2022-05-31T07:24:19.135Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697957
;

-- Field: Work Order Project -> Project Resource Budget -> Start Plan
-- Column: C_Project_Resource_Budget.DateStartPlan
-- 2022-05-31T07:24:19.137Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697957
;

-- 2022-05-31T07:24:19.142Z
DELETE FROM AD_Field WHERE AD_Field_ID=697957
;

-- UI Element: Work Order Project -> Project Resource Budget.Finish Plan
-- Column: C_Project_Resource_Budget.DateFinishPlan
-- 2022-05-31T07:24:19.160Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=608291
;

-- 2022-05-31T07:24:19.162Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697958
;

-- Field: Work Order Project -> Project Resource Budget -> Finish Plan
-- Column: C_Project_Resource_Budget.DateFinishPlan
-- 2022-05-31T07:24:19.165Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697958
;

-- 2022-05-31T07:24:19.169Z
DELETE FROM AD_Field WHERE AD_Field_ID=697958
;

-- UI Element: Work Order Project -> Project Resource Budget.Beschreibung
-- Column: C_Project_Resource_Budget.Description
-- 2022-05-31T07:24:19.186Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=608284
;

-- 2022-05-31T07:24:19.189Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697959
;

-- Field: Work Order Project -> Project Resource Budget -> Beschreibung
-- Column: C_Project_Resource_Budget.Description
-- 2022-05-31T07:24:19.191Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697959
;

-- 2022-05-31T07:24:19.196Z
DELETE FROM AD_Field WHERE AD_Field_ID=697959
;

-- UI Element: Work Order Project -> Project Resource Budget.Währung
-- Column: C_Project_Resource_Budget.C_Currency_ID
-- 2022-05-31T07:24:19.214Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=608285
;

-- 2022-05-31T07:24:19.216Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697960
;

-- Field: Work Order Project -> Project Resource Budget -> Währung
-- Column: C_Project_Resource_Budget.C_Currency_ID
-- 2022-05-31T07:24:19.219Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=697960
;

-- 2022-05-31T07:24:19.224Z
DELETE FROM AD_Field WHERE AD_Field_ID=697960
;

-- 2022-05-31T07:24:19.246Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=549189
;

-- 2022-05-31T07:24:19.257Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=549188
;

-- 2022-05-31T07:24:19.267Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=549187
;

-- 2022-05-31T07:24:19.274Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=545969
;

-- 2022-05-31T07:24:19.288Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=549186
;

-- 2022-05-31T07:24:19.297Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=549185
;

-- 2022-05-31T07:24:19.303Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=545968
;

-- 2022-05-31T07:24:19.344Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=544936
;

-- 2022-05-31T07:24:19.351Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=544936
;

-- 2022-05-31T07:24:19.353Z
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=546288
;

-- 2022-05-31T07:24:19.359Z
DELETE FROM AD_Tab WHERE AD_Tab_ID=546288
;

-- Tab: Budget Project -> Projekt
-- Table: C_Project
-- 2022-05-31T07:25:59.234Z
UPDATE AD_Tab SET InternalName='budgetProject',Updated=TO_TIMESTAMP('2022-05-31 10:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546269
;

-- Tab: Work Order Project -> Projekt
-- Table: C_Project
-- 2022-05-31T07:26:28.521Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,572687,0,546289,203,541512,'Y',TO_TIMESTAMP('2022-05-31 10:26:28','YYYY-MM-DD HH24:MI:SS'),100,'Define Project','D','N','The Project Tab is used to define the Value, Name and Description for each project.  It also is defines the tracks the amounts assigned to, committed to and used for this project. Note that when the project Type is changed, the Phases and Tasks are re-created.','A','budgetProject','Y','N','Y','Y','Y','N','N','Y','Y','N','N','N','Y','Y','Y','N','N','Projekt','N',10,0,TO_TIMESTAMP('2022-05-31 10:26:28','YYYY-MM-DD HH24:MI:SS'),100,'ProjectCategory=''B''')
;

-- 2022-05-31T07:26:28.523Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546289 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-05-31T07:26:28.527Z
/* DDL */  select update_tab_translation_from_ad_element(572687) 
;

-- 2022-05-31T07:26:28.535Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546289)
;

-- 2022-05-31T07:26:28.540Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 546289
;

-- 2022-05-31T07:26:28.541Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 546289, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 546269
;

-- Field: Work Order Project -> Projekt -> Zusage ist Obergrenze
-- Column: C_Project.IsCommitCeiling
-- 2022-05-31T07:26:28.674Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8978,697962,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:28','YYYY-MM-DD HH24:MI:SS'),100,'Betrag / Menge der Zusage ist die Obergrenze für die Abrechnung',1,'@IsSummary@=N & @IsCommitment@=Y','D','Zusage-Betrag und -Menge sind maximal abrechenbarer Betrag und Menge. Ignoriert, wenn Betrag oder Menge gleich 0.',0,'Y','N','N','N','N','N','N','Y','Zusage ist Obergrenze',20,0,1,1,TO_TIMESTAMP('2022-05-31 10:26:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:28.677Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697962 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:28.680Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2077) 
;

-- 2022-05-31T07:26:28.685Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697962
;

-- 2022-05-31T07:26:28.687Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697962)
;

-- 2022-05-31T07:26:28.692Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697962
;

-- 2022-05-31T07:26:28.693Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697962, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696859
;

-- Field: Work Order Project -> Projekt -> Verarbeitet
-- Column: C_Project.Processed
-- 2022-05-31T07:26:28.801Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5749,697963,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:28','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','N','N','Verarbeitet',30,0,1,1,TO_TIMESTAMP('2022-05-31 10:26:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:28.803Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697963 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:28.806Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2022-05-31T07:26:28.839Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697963
;

-- 2022-05-31T07:26:28.841Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697963)
;

-- 2022-05-31T07:26:28.846Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697963
;

-- 2022-05-31T07:26:28.847Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697963, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696860
;

-- Field: Work Order Project -> Projekt -> Project Category
-- Column: C_Project.ProjectCategory
-- 2022-05-31T07:26:28.954Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9856,697964,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:28','YYYY-MM-DD HH24:MI:SS'),100,'Project Category',14,'D','The Project Category determines the behavior of the project:
General - no special accounting, e.g. for Presales or general tracking
Service - no special accounting, e.g. for Service/Charge projects
Work Order - creates Project/Job WIP transactions - ability to issue material
Asset - create Project Asset transactions - ability to issue material
',0,'Y','N','N','N','N','N','N','N','Project Category',40,0,1,1,TO_TIMESTAMP('2022-05-31 10:26:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:28.956Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697964 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:28.959Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2179) 
;

-- 2022-05-31T07:26:28.964Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697964
;

-- 2022-05-31T07:26:28.965Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697964)
;

-- 2022-05-31T07:26:28.970Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697964
;

-- 2022-05-31T07:26:28.971Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697964, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696861
;

-- Field: Work Order Project -> Projekt -> Projekt
-- Column: C_Project.C_Project_ID
-- 2022-05-31T07:26:29.110Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1349,697965,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:28','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',14,'D','A Project allows you to track and control internal or external activities.',0,'Y','N','N','N','N','N','N','N','Projekt',50,0,1,1,TO_TIMESTAMP('2022-05-31 10:26:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:29.113Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697965 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:29.115Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-05-31T07:26:29.122Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697965
;

-- 2022-05-31T07:26:29.124Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697965)
;

-- 2022-05-31T07:26:29.128Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697965
;

-- 2022-05-31T07:26:29.130Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697965, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696862
;

-- Field: Work Order Project -> Projekt -> Commitment
-- Column: C_Project.IsCommitment
-- 2022-05-31T07:26:29.238Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3904,697966,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:29','YYYY-MM-DD HH24:MI:SS'),100,'Is this document a (legal) commitment?',1,'@IsSummary@=N','D','Commitment indicates if the document is legally binding.',0,'Y','N','N','N','N','N','N','N','Commitment',60,0,1,1,TO_TIMESTAMP('2022-05-31 10:26:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:29.241Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697966 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:29.243Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1101) 
;

-- 2022-05-31T07:26:29.247Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697966
;

-- 2022-05-31T07:26:29.248Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697966)
;

-- 2022-05-31T07:26:29.252Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697966
;

-- 2022-05-31T07:26:29.254Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697966, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696863
;

-- Field: Work Order Project -> Projekt -> Mandant
-- Column: C_Project.AD_Client_ID
-- 2022-05-31T07:26:29.373Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1351,697967,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:29','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2022-05-31 10:26:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:29.376Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697967 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:29.378Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-05-31T07:26:29.608Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697967
;

-- 2022-05-31T07:26:29.609Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697967)
;

-- 2022-05-31T07:26:29.613Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697967
;

-- 2022-05-31T07:26:29.615Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697967, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696864
;

-- Field: Work Order Project -> Projekt -> Sektion
-- Column: C_Project.AD_Org_ID
-- 2022-05-31T07:26:29.716Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1350,697968,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:29','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2022-05-31 10:26:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:29.718Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697968 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:29.720Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-05-31T07:26:29.947Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697968
;

-- 2022-05-31T07:26:29.948Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697968)
;

-- 2022-05-31T07:26:29.951Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697968
;

-- 2022-05-31T07:26:29.952Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697968, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696865
;

-- Field: Work Order Project -> Projekt -> Projekt Nummer
-- Column: C_Project.Value
-- 2022-05-31T07:26:30.058Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2010,697969,577009,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:29','YYYY-MM-DD HH24:MI:SS'),100,20,'D',0,'Y','Y','Y','N','N','N','N','N','Projekt Nummer',30,30,-1,1,1,TO_TIMESTAMP('2022-05-31 10:26:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:30.060Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697969 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:30.063Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577009) 
;

-- 2022-05-31T07:26:30.065Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697969
;

-- 2022-05-31T07:26:30.067Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697969)
;

-- 2022-05-31T07:26:30.071Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697969
;

-- 2022-05-31T07:26:30.073Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697969, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696866
;

-- Field: Work Order Project -> Projekt -> Kundenbetreuer
-- Column: C_Project.SalesRep_ID
-- 2022-05-31T07:26:30.178Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5752,697970,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:30','YYYY-MM-DD HH24:MI:SS'),100,'',14,'@IsSummary@=N','D','',0,'Y','Y','Y','Y','N','N','N','Y','Kundenbetreuer',40,40,1,1,TO_TIMESTAMP('2022-05-31 10:26:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:30.181Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697970 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:30.183Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1063) 
;

-- 2022-05-31T07:26:30.195Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697970
;

-- 2022-05-31T07:26:30.196Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697970)
;

-- 2022-05-31T07:26:30.200Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697970
;

-- 2022-05-31T07:26:30.202Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697970, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696867
;

-- Field: Work Order Project -> Projekt -> Name
-- Column: C_Project.Name
-- 2022-05-31T07:26:30.311Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1356,697971,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:30','YYYY-MM-DD HH24:MI:SS'),100,'',60,'D','',0,'Y','Y','Y','N','N','N','N','N','Name',50,50,999,1,TO_TIMESTAMP('2022-05-31 10:26:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:30.313Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697971 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:30.315Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2022-05-31T07:26:30.407Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697971
;

-- 2022-05-31T07:26:30.408Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697971)
;

-- 2022-05-31T07:26:30.412Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697971
;

-- 2022-05-31T07:26:30.413Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697971, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696868
;

-- Field: Work Order Project -> Projekt -> Beschreibung
-- Column: C_Project.Description
-- 2022-05-31T07:26:30.520Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1358,697972,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:30','YYYY-MM-DD HH24:MI:SS'),100,60,'D',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',60,60,999,1,TO_TIMESTAMP('2022-05-31 10:26:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:30.522Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697972 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:30.524Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-05-31T07:26:30.573Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697972
;

-- 2022-05-31T07:26:30.574Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697972)
;

-- 2022-05-31T07:26:30.577Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697972
;

-- 2022-05-31T07:26:30.579Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697972, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696869
;

-- Field: Work Order Project -> Projekt -> Aktiv
-- Column: C_Project.IsActive
-- 2022-05-31T07:26:30.686Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1352,697973,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:30','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',70,70,1,1,TO_TIMESTAMP('2022-05-31 10:26:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:30.689Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697973 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:30.691Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-05-31T07:26:30.884Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697973
;

-- 2022-05-31T07:26:30.885Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697973)
;

-- 2022-05-31T07:26:30.888Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697973
;

-- 2022-05-31T07:26:30.890Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697973, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696870
;

-- Field: Work Order Project -> Projekt -> Zusammenfassungseintrag
-- Column: C_Project.IsSummary
-- 2022-05-31T07:26:30.996Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1360,697974,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:30','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein Zusammenfassungseintrag',1,'D','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.',0,'Y','Y','Y','N','N','N','N','Y','Zusammenfassungseintrag',80,80,1,1,TO_TIMESTAMP('2022-05-31 10:26:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:30.998Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697974 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:31.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(416) 
;

-- 2022-05-31T07:26:31.009Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697974
;

-- 2022-05-31T07:26:31.010Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697974)
;

-- 2022-05-31T07:26:31.014Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697974
;

-- 2022-05-31T07:26:31.015Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697974, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696871
;

-- Field: Work Order Project -> Projekt -> Notiz
-- Column: C_Project.Note
-- 2022-05-31T07:26:31.124Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5750,697975,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information',60,'D','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben',0,'Y','Y','Y','N','N','N','N','N','Notiz',90,90,999,1,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:31.126Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697975 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:31.128Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2022-05-31T07:26:31.138Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697975
;

-- 2022-05-31T07:26:31.139Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697975)
;

-- 2022-05-31T07:26:31.143Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697975
;

-- 2022-05-31T07:26:31.145Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697975, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696872
;

-- Field: Work Order Project -> Projekt -> Line Level
-- Column: C_Project.ProjectLineLevel
-- 2022-05-31T07:26:31.253Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,15469,697976,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100,'Project Line Level',1,'@IsSummary@=N','D','Level on which Project Lines are maintained',0,'Y','Y','Y','N','N','N','N','N','Line Level',100,100,1,1,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:31.255Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697976 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:31.257Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3035) 
;

-- 2022-05-31T07:26:31.261Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697976
;

-- 2022-05-31T07:26:31.262Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697976)
;

-- 2022-05-31T07:26:31.268Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697976
;

-- 2022-05-31T07:26:31.269Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697976, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696873
;

-- Field: Work Order Project -> Projekt -> Projektart
-- Column: C_Project.C_ProjectType_ID
-- 2022-05-31T07:26:31.381Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_Val_Rule_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8757,697977,0,546289,540582,0,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100,'Type of the project',23,'','D','Type of the project with optional phases of the project with standard performance information',0,'Y','Y','Y','N','N','N','Y','N','N','Projektart',110,110,1,1,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:31.384Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697977 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:31.386Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2033) 
;

-- 2022-05-31T07:26:31.389Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697977
;

-- 2022-05-31T07:26:31.390Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697977)
;

-- 2022-05-31T07:26:31.393Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697977
;

-- 2022-05-31T07:26:31.395Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697977, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696874
;

-- Field: Work Order Project -> Projekt -> Standard-Phase
-- Column: C_Project.C_Phase_ID
-- 2022-05-31T07:26:31.497Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8755,697978,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100,'Status oder Phase dieser Projektart',14,'@IsSummary@=N','D','Phase of the project with standard performance information with standard work',0,'Y','Y','Y','N','N','N','N','Y','Standard-Phase',120,120,1,1,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:31.499Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697978 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:31.500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2032) 
;

-- 2022-05-31T07:26:31.503Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697978
;

-- 2022-05-31T07:26:31.504Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697978)
;

-- 2022-05-31T07:26:31.507Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697978
;

-- 2022-05-31T07:26:31.508Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697978, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696875
;

-- Field: Work Order Project -> Projekt -> Datum Auftragseingang
-- Column: C_Project.DateContract
-- 2022-05-31T07:26:31.614Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5745,697979,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftragseingangs',14,'@IsSummary@=N','D','The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.',0,'Y','Y','Y','N','N','N','N','N','Datum Auftragseingang',130,130,1,1,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:31.616Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697979 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:31.618Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1556) 
;

-- 2022-05-31T07:26:31.620Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697979
;

-- 2022-05-31T07:26:31.622Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697979)
;

-- 2022-05-31T07:26:31.625Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697979
;

-- 2022-05-31T07:26:31.627Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697979, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696876
;

-- Field: Work Order Project -> Projekt -> Projektabschluss
-- Column: C_Project.DateFinish
-- 2022-05-31T07:26:31.736Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5746,697980,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100,'Finish or (planned) completion date',14,'@IsSummary@=N','D','Dieses Datum gibt das erwartete oder tatsächliche Projektende an',0,'Y','Y','Y','N','N','N','N','Y','Projektabschluss',140,140,1,1,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:31.738Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697980 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:31.740Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1557) 
;

-- 2022-05-31T07:26:31.743Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697980
;

-- 2022-05-31T07:26:31.744Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697980)
;

-- 2022-05-31T07:26:31.748Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697980
;

-- 2022-05-31T07:26:31.749Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697980, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696877
;

-- Field: Work Order Project -> Projekt -> Geschäftspartner
-- Column: C_Project.C_BPartner_ID
-- 2022-05-31T07:26:31.845Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3902,104,697981,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',26,'@IsSummary@=N','D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner',150,150,1,1,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:31.848Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697981 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:31.850Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2022-05-31T07:26:31.875Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697981
;

-- 2022-05-31T07:26:31.876Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697981)
;

-- 2022-05-31T07:26:31.880Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697981
;

-- 2022-05-31T07:26:31.881Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697981, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696878
;

-- Field: Work Order Project -> Projekt -> BPartner (Agent)
-- Column: C_Project.C_BPartnerSR_ID
-- 2022-05-31T07:26:31.978Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14095,697982,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner (Agent or Sales Rep)',10,'@IsSummary@=N','D',0,'Y','Y','Y','N','N','N','N','Y','BPartner (Agent)',160,160,1,1,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:31.981Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697982 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:31.983Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2810) 
;

-- 2022-05-31T07:26:31.985Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697982
;

-- 2022-05-31T07:26:31.987Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697982)
;

-- 2022-05-31T07:26:31.990Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697982
;

-- 2022-05-31T07:26:31.992Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697982, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696879
;

-- Field: Work Order Project -> Projekt -> Standort
-- Column: C_Project.C_BPartner_Location_ID
-- 2022-05-31T07:26:32.084Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5798,697983,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',14,'@IsSummary@=N','D','Identifiziert die Adresse des Geschäftspartners',0,'Y','Y','Y','N','N','N','N','N','Standort',170,170,1,1,TO_TIMESTAMP('2022-05-31 10:26:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:32.087Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697983 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:32.089Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2022-05-31T07:26:32.097Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697983
;

-- 2022-05-31T07:26:32.098Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697983)
;

-- 2022-05-31T07:26:32.102Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697983
;

-- 2022-05-31T07:26:32.103Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697983, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696880
;

-- Field: Work Order Project -> Projekt -> Ansprechpartner
-- Column: C_Project.AD_User_ID
-- 2022-05-31T07:26:32.206Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5797,697984,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',14,'@IsSummary@=N','D','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','N','N','N','N','Y','Ansprechpartner',180,180,1,1,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:32.208Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697984 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:32.210Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2022-05-31T07:26:32.219Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697984
;

-- 2022-05-31T07:26:32.221Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697984)
;

-- 2022-05-31T07:26:32.225Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697984
;

-- 2022-05-31T07:26:32.226Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697984, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696881
;

-- Field: Work Order Project -> Projekt -> Zahlungsbedingung
-- Column: C_Project.C_PaymentTerm_ID
-- 2022-05-31T07:26:32.328Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5796,697985,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs',14,'@IsSummary@=N','D','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.',0,'Y','Y','Y','N','N','N','N','N','Zahlungsbedingung',190,190,1,1,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:32.331Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697985 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:32.333Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(204) 
;

-- 2022-05-31T07:26:32.341Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697985
;

-- 2022-05-31T07:26:32.342Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697985)
;

-- 2022-05-31T07:26:32.346Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697985
;

-- 2022-05-31T07:26:32.347Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697985, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696882
;

-- Field: Work Order Project -> Projekt -> Referenz
-- Column: C_Project.POReference
-- 2022-05-31T07:26:32.437Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5794,697986,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden',20,'@IsSummary@=N','D','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','Y','Y','N','N','N','N','Y','Referenz',200,200,1,1,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:32.439Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697986 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:32.441Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952) 
;

-- 2022-05-31T07:26:32.450Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697986
;

-- 2022-05-31T07:26:32.451Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697986)
;

-- 2022-05-31T07:26:32.454Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697986
;

-- 2022-05-31T07:26:32.456Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697986, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696883
;

-- Field: Work Order Project -> Projekt -> Lager
-- Column: C_Project.M_Warehouse_ID
-- 2022-05-31T07:26:32.563Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9637,697987,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung',14,'@IsSummary@=N','D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','Y','Y','N','N','N','N','N','Lager',210,210,1,1,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:32.565Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697987 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:32.567Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459) 
;

-- 2022-05-31T07:26:32.615Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697987
;

-- 2022-05-31T07:26:32.616Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697987)
;

-- 2022-05-31T07:26:32.619Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697987
;

-- 2022-05-31T07:26:32.620Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697987, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696884
;

-- Field: Work Order Project -> Projekt -> Werbemassnahme
-- Column: C_Project.C_Campaign_ID
-- 2022-05-31T07:26:32.727Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5795,697988,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign',14,'@IsSummary@=N & @$Element_MC@=Y','D','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.',0,'Y','Y','Y','N','N','N','N','Y','Werbemassnahme',220,220,1,1,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:32.730Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697988 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:32.732Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550) 
;

-- 2022-05-31T07:26:32.742Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697988
;

-- 2022-05-31T07:26:32.743Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697988)
;

-- 2022-05-31T07:26:32.746Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697988
;

-- 2022-05-31T07:26:32.747Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697988, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696885
;

-- Field: Work Order Project -> Projekt -> Version Preisliste
-- Column: C_Project.M_PriceList_Version_ID
-- 2022-05-31T07:26:32.855Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5753,697989,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine einzelne Version der Preisliste',14,'@IsSummary@=N','D','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ',0,'Y','Y','Y','N','N','N','N','N','Version Preisliste',230,230,1,1,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:32.857Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697989 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:32.860Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(450) 
;

-- 2022-05-31T07:26:32.864Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697989
;

-- 2022-05-31T07:26:32.866Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697989)
;

-- 2022-05-31T07:26:32.869Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697989
;

-- 2022-05-31T07:26:32.871Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697989, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696886
;

-- Field: Work Order Project -> Projekt -> Währung
-- Column: C_Project.C_Currency_ID
-- 2022-05-31T07:26:32.972Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3901,697990,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',14,'@IsSummary@=N','D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','Y','Y','N','N','N','Y','Y','Y','Währung',240,240,1,1,TO_TIMESTAMP('2022-05-31 10:26:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:32.974Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697990 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:32.977Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2022-05-31T07:26:32.993Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697990
;

-- 2022-05-31T07:26:32.995Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697990)
;

-- 2022-05-31T07:26:32.999Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697990
;

-- 2022-05-31T07:26:33Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697990, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696887
;

-- Field: Work Order Project -> Projekt -> VK Total
-- Column: C_Project.PlannedAmt
-- 2022-05-31T07:26:33.106Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5755,103,697991,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100,'VK Total',26,'@IsSummary@=N','D','The Planned Amount indicates the anticipated amount for this project or project line.',0,'Y','Y','Y','N','N','N','N','N','VK Total',250,250,1,1,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:33.108Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697991 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:33.111Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1564) 
;

-- 2022-05-31T07:26:33.114Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697991
;

-- 2022-05-31T07:26:33.115Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697991)
;

-- 2022-05-31T07:26:33.118Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697991
;

-- 2022-05-31T07:26:33.120Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697991, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696888
;

-- Field: Work Order Project -> Projekt -> Geplante Menge
-- Column: C_Project.PlannedQty
-- 2022-05-31T07:26:33.226Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5756,103,697992,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100,'',26,'@IsSummary@=N','D','',0,'Y','Y','Y','N','N','N','N','Y','Geplante Menge',260,260,1,1,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:33.228Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697992 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:33.231Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1568) 
;

-- 2022-05-31T07:26:33.233Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697992
;

-- 2022-05-31T07:26:33.234Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697992)
;

-- 2022-05-31T07:26:33.238Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697992
;

-- 2022-05-31T07:26:33.239Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697992, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696889
;

-- Field: Work Order Project -> Projekt -> DB1
-- Column: C_Project.PlannedMarginAmt
-- 2022-05-31T07:26:33.342Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5757,697993,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100,'Project''s planned margin amount',26,'@IsSummary@=N','D','The Planned Margin Amount indicates the anticipated margin amount for this project or project line.',0,'Y','Y','Y','N','N','N','N','N','DB1',270,270,1,1,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:33.345Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697993 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:33.347Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1566) 
;

-- 2022-05-31T07:26:33.349Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697993
;

-- 2022-05-31T07:26:33.350Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697993)
;

-- 2022-05-31T07:26:33.353Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697993
;

-- 2022-05-31T07:26:33.355Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697993, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696890
;

-- Field: Work Order Project -> Projekt -> Rechnungsstellung
-- Column: C_Project.ProjInvoiceRule
-- 2022-05-31T07:26:33.448Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,15449,697994,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Rule for the project',1,'@IsSummary@=N','D','The Invoice Rule for the project determines how orders (and consequently invoices) are created.  The selection on project level can be overwritten on Phase or Task',0,'Y','Y','Y','N','N','N','N','Y','Rechnungsstellung',280,280,1,1,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:33.450Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697994 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:33.452Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3031) 
;

-- 2022-05-31T07:26:33.454Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697994
;

-- 2022-05-31T07:26:33.456Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697994)
;

-- 2022-05-31T07:26:33.459Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697994
;

-- 2022-05-31T07:26:33.461Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697994, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696891
;

-- Field: Work Order Project -> Projekt -> Committed Amount
-- Column: C_Project.CommittedAmt
-- 2022-05-31T07:26:33.570Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3907,697995,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100,'The (legal) commitment amount',26,'@IsSummary@=N','D','The commitment amount is independent from the planned amount. You would use the planned amount for your realistic estimation, which might be higher or lower than the commitment amount.',0,'Y','Y','Y','N','N','N','N','N','Committed Amount',290,290,1,1,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:33.572Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697995 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:33.574Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1081) 
;

-- 2022-05-31T07:26:33.579Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697995
;

-- 2022-05-31T07:26:33.580Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697995)
;

-- 2022-05-31T07:26:33.584Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697995
;

-- 2022-05-31T07:26:33.586Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697995, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696892
;

-- Field: Work Order Project -> Projekt -> Committed Quantity
-- Column: C_Project.CommittedQty
-- 2022-05-31T07:26:33.694Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8759,697996,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100,'The (legal) commitment Quantity',26,'@IsSummary@=N','D','The commitment amount is independent from the planned amount. You would use the planned amount for your realistic estimation, which might be higher or lower than the commitment amount.',0,'Y','Y','Y','N','N','N','N','Y','Committed Quantity',300,300,1,1,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:33.696Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697996 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:33.698Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2036) 
;

-- 2022-05-31T07:26:33.701Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697996
;

-- 2022-05-31T07:26:33.702Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697996)
;

-- 2022-05-31T07:26:33.706Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697996
;

-- 2022-05-31T07:26:33.708Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697996, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696893
;

-- Field: Work Order Project -> Projekt -> Invoiced Amount
-- Column: C_Project.InvoicedAmt
-- 2022-05-31T07:26:33.824Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8753,105,697997,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100,'The amount invoiced',26,'@IsSummary@=N','D','The amount invoiced',0,'Y','Y','Y','N','N','N','Y','N','Invoiced Amount',310,310,1,1,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:33.827Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697997 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:33.830Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2044) 
;

-- 2022-05-31T07:26:33.833Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697997
;

-- 2022-05-31T07:26:33.834Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697997)
;

-- 2022-05-31T07:26:33.837Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697997
;

-- 2022-05-31T07:26:33.839Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697997, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696894
;

-- Field: Work Order Project -> Projekt -> Berechnete Menge
-- Column: C_Project.InvoicedQty
-- 2022-05-31T07:26:33.944Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8756,697998,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100,'The quantity invoiced',26,'@IsSummary@=N','D',0,'Y','Y','Y','N','N','N','Y','Y','Berechnete Menge',320,320,1,1,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:33.946Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697998 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:33.948Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2045) 
;

-- 2022-05-31T07:26:33.951Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697998
;

-- 2022-05-31T07:26:33.952Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697998)
;

-- 2022-05-31T07:26:33.955Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697998
;

-- 2022-05-31T07:26:33.957Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697998, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696895
;

-- Field: Work Order Project -> Projekt -> Project Balance
-- Column: C_Project.ProjectBalanceAmt
-- 2022-05-31T07:26:34.060Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8758,697999,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100,'Total Project Balance',26,'@IsSummary@=N','D','The project balance is the sum of all invoices and payments',0,'Y','Y','Y','N','N','N','Y','N','Project Balance',330,330,1,1,TO_TIMESTAMP('2022-05-31 10:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:34.062Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697999 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:34.064Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2054) 
;

-- 2022-05-31T07:26:34.067Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697999
;

-- 2022-05-31T07:26:34.068Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(697999)
;

-- 2022-05-31T07:26:34.071Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 697999
;

-- 2022-05-31T07:26:34.073Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 697999, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696896
;

-- Field: Work Order Project -> Projekt -> Process Now
-- Column: C_Project.Processing
-- 2022-05-31T07:26:34.179Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9861,698000,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100,23,'@IsSummary@=N','D',0,'Y','Y','Y','N','N','N','N','N','Process Now',350,350,1,1,TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:34.181Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698000 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:34.184Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2022-05-31T07:26:34.209Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698000
;

-- 2022-05-31T07:26:34.211Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698000)
;

-- 2022-05-31T07:26:34.214Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 698000
;

-- 2022-05-31T07:26:34.215Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 698000, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696898
;

-- Field: Work Order Project -> Projekt -> Externe Projektreferenz
-- Column: C_Project.C_Project_Reference_Ext
-- 2022-05-31T07:26:34.323Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582993,698001,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Externe Projektreferenz',370,360,0,1,1,TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:34.325Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698001 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:34.327Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580859) 
;

-- 2022-05-31T07:26:34.329Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698001
;

-- 2022-05-31T07:26:34.330Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698001)
;

-- 2022-05-31T07:26:34.333Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 698001
;

-- 2022-05-31T07:26:34.335Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 698001, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696900
;

-- Field: Work Order Project -> Projekt -> Elternprojekt
-- Column: C_Project.C_Project_Parent_ID
-- 2022-05-31T07:26:34.431Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582994,698002,0,546289,0,TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Elternprojekt',380,370,0,1,1,TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:34.433Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698002 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:26:34.435Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580860) 
;

-- 2022-05-31T07:26:34.437Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698002
;

-- 2022-05-31T07:26:34.438Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698002)
;

-- 2022-05-31T07:26:34.441Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 698002
;

-- 2022-05-31T07:26:34.443Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 698002, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 696901
;

-- 2022-05-31T07:26:34.541Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546289,544937,TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100,'1000028')
;

-- 2022-05-31T07:26:34.543Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544937 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-05-31T07:26:34.547Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544937
;

-- 2022-05-31T07:26:34.549Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 544937, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 544914
;

-- 2022-05-31T07:26:34.644Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545970,544937,TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:34.748Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,545970,549191,TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,'primary',TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Projektart
-- Column: C_Project.C_ProjectType_ID
-- 2022-05-31T07:26:34.872Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697977,0,546289,549191,608294,'F',TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektart',5,0,0,TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Projektnummer
-- Column: C_Project.Value
-- 2022-05-31T07:26:34.996Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697969,0,546289,549191,608295,'F',TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Projektnummer',10,60,0,TO_TIMESTAMP('2022-05-31 10:26:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Name
-- Column: C_Project.Name
-- 2022-05-31T07:26:35.128Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697971,0,546289,549191,608296,'F',TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Name',20,50,0,TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Externe Projektreferenz
-- Column: C_Project.C_Project_Reference_Ext
-- 2022-05-31T07:26:35.261Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698001,0,546289,549191,608297,'F',TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Externe Projektreferenz',35,70,0,TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Elternprojekt
-- Column: C_Project.C_Project_Parent_ID
-- 2022-05-31T07:26:35.378Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698002,0,546289,549191,608298,'F',TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Elternprojekt',45,0,0,TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:35.469Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545970,549192,TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Beschreibung
-- Column: C_Project.Description
-- 2022-05-31T07:26:35.606Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697972,0,546289,549192,608299,'F',TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Beschreibung',10,30,0,TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:35.703Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545971,544937,TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:35.812Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545971,549193,TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','active',5,TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Aktiv
-- Column: C_Project.IsActive
-- 2022-05-31T07:26:35.931Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697973,0,546289,549193,608300,'F',TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Aktiv',10,10,0,TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:36.031Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545971,549194,TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','partner',10,TO_TIMESTAMP('2022-05-31 10:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Kunde
-- Column: C_Project.C_BPartner_ID
-- 2022-05-31T07:26:36.159Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697981,0,546289,549194,608301,'F',TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Kunde',10,40,0,TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Aussendienst
-- Column: C_Project.SalesRep_ID
-- 2022-05-31T07:26:36.280Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697970,0,546289,549194,608302,'F',TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Aussendienst',30,20,0,TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Datum AE
-- Column: C_Project.DateContract
-- 2022-05-31T07:26:36.406Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697979,0,546289,549194,608303,'F',TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Datum AE',40,0,0,TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Projektabschluss
-- Column: C_Project.DateFinish
-- 2022-05-31T07:26:36.528Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697980,0,546289,549194,608304,'F',TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektabschluss',50,0,0,TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:36.626Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545971,549195,TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','pricing',15,TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Version Preisliste
-- Column: C_Project.M_PriceList_Version_ID
-- 2022-05-31T07:26:36.752Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697989,0,546289,549195,608305,'F',TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine einzelne Version der Preisliste','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','N','Y','N','N','N','Version Preisliste',10,0,0,TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Währung
-- Column: C_Project.C_Currency_ID
-- 2022-05-31T07:26:36.866Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697990,0,546289,549195,608306,'F',TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','N','N','Währung',20,0,0,TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:26:36.965Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545971,549196,TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Mandant
-- Column: C_Project.AD_Client_ID
-- 2022-05-31T07:26:37.074Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697967,0,546289,549196,608307,'F',TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2022-05-31 10:26:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Projekt.Sektion
-- Column: C_Project.AD_Org_ID
-- 2022-05-31T07:26:37.176Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,697968,0,546289,549196,608308,'F',TO_TIMESTAMP('2022-05-31 10:26:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2022-05-31 10:26:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Work Order Project -> Projekt
-- Table: C_Project
-- 2022-05-31T07:26:59.899Z
UPDATE AD_Tab SET WhereClause='ProjectCategory=''W''',Updated=TO_TIMESTAMP('2022-05-31 10:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546289
;

-- Name: C_ProjectType_ID with ProjectCategory=WorkOrder
-- 2022-05-31T07:28:27.851Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540583,'C_ProjectType.ProjectCategory=''W''',TO_TIMESTAMP('2022-05-31 10:28:27','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','C_ProjectType_ID with ProjectCategory=WorkOrder','S',TO_TIMESTAMP('2022-05-31 10:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Work Order Project -> Projekt -> Projektart
-- Column: C_Project.C_ProjectType_ID
-- 2022-05-31T07:28:45.521Z
UPDATE AD_Field SET AD_Reference_ID=18, AD_Val_Rule_ID=540583,Updated=TO_TIMESTAMP('2022-05-31 10:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=697977
;

-- Window: Work Order Project, InternalName=workOrderProject
-- 2022-05-31T07:33:22.158Z
UPDATE AD_Window SET InternalName='workOrderProject',Updated=TO_TIMESTAMP('2022-05-31 10:33:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541512
;

-- Tab: Work Order Project -> Projekt
-- Table: C_Project
-- 2022-05-31T07:33:31.697Z
UPDATE AD_Tab SET InternalName='workOrderProject',Updated=TO_TIMESTAMP('2022-05-31 10:33:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546289
;

-- Tab: Work Order Project -> Project Step
-- Table: C_Project_WO_Step
-- 2022-05-31T07:34:11.120Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,580965,0,546290,542159,541512,'Y',TO_TIMESTAMP('2022-05-31 10:34:10','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_WO_Step','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Project Step','N',20,0,TO_TIMESTAMP('2022-05-31 10:34:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:34:11.122Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546290 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-05-31T07:34:11.125Z
/* DDL */  select update_tab_translation_from_ad_element(580965) 
;

-- 2022-05-31T07:34:11.130Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546290)
;

-- Field: Work Order Project -> Project Step -> Mandant
-- Column: C_Project_WO_Step.AD_Client_ID
-- 2022-05-31T07:34:21.480Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583223,698003,0,546290,TO_TIMESTAMP('2022-05-31 10:34:21','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-05-31 10:34:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:34:21.483Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698003 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:34:21.490Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-05-31T07:34:21.718Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698003
;

-- 2022-05-31T07:34:21.720Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698003)
;

-- Field: Work Order Project -> Project Step -> Sektion
-- Column: C_Project_WO_Step.AD_Org_ID
-- 2022-05-31T07:34:21.847Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583224,698004,0,546290,TO_TIMESTAMP('2022-05-31 10:34:21','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-05-31 10:34:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:34:21.850Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698004 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:34:21.852Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-05-31T07:34:22.009Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698004
;

-- 2022-05-31T07:34:22.010Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698004)
;

-- Field: Work Order Project -> Project Step -> Aktiv
-- Column: C_Project_WO_Step.IsActive
-- 2022-05-31T07:34:22.132Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583227,698005,0,546290,TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:34:22.134Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698005 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:34:22.136Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-05-31T07:34:22.295Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698005
;

-- 2022-05-31T07:34:22.296Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698005)
;

-- Field: Work Order Project -> Project Step -> Project Step
-- Column: C_Project_WO_Step.C_Project_WO_Step_ID
-- 2022-05-31T07:34:22.408Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583230,698006,0,546290,TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Step',TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:34:22.410Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698006 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:34:22.412Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580965) 
;

-- 2022-05-31T07:34:22.416Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698006
;

-- 2022-05-31T07:34:22.417Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698006)
;

-- Field: Work Order Project -> Project Step -> Projekt
-- Column: C_Project_WO_Step.C_Project_ID
-- 2022-05-31T07:34:22.522Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583231,698007,0,546290,TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:34:22.525Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698007 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:34:22.526Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-05-31T07:34:22.539Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698007
;

-- 2022-05-31T07:34:22.541Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698007)
;

-- Field: Work Order Project -> Project Step -> Reihenfolge
-- Column: C_Project_WO_Step.SeqNo
-- 2022-05-31T07:34:22.645Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583232,698008,0,546290,TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:34:22.648Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698008 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:34:22.650Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2022-05-31T07:34:22.667Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698008
;

-- 2022-05-31T07:34:22.668Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698008)
;

-- Field: Work Order Project -> Project Step -> Name
-- Column: C_Project_WO_Step.Name
-- 2022-05-31T07:34:22.776Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583233,698009,0,546290,TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:34:22.780Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698009 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:34:22.782Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2022-05-31T07:34:22.826Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698009
;

-- 2022-05-31T07:34:22.827Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698009)
;

-- Field: Work Order Project -> Project Step -> Beschreibung
-- Column: C_Project_WO_Step.Description
-- 2022-05-31T07:34:22.929Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583234,698010,0,546290,TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:34:22.931Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698010 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:34:22.933Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-05-31T07:34:22.979Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698010
;

-- 2022-05-31T07:34:22.980Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698010)
;

-- Field: Work Order Project -> Project Step -> Startdatum
-- Column: C_Project_WO_Step.DateStart
-- 2022-05-31T07:34:23.088Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583254,698011,0,546290,TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Startdatum',TO_TIMESTAMP('2022-05-31 10:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:34:23.090Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698011 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:34:23.092Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53280) 
;

-- 2022-05-31T07:34:23.095Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698011
;

-- 2022-05-31T07:34:23.096Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698011)
;

-- Field: Work Order Project -> Project Step ->  Endzeitpunkt
-- Column: C_Project_WO_Step.DateEnd
-- 2022-05-31T07:34:23.198Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583255,698012,0,546290,TO_TIMESTAMP('2022-05-31 10:34:23','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N',' Endzeitpunkt',TO_TIMESTAMP('2022-05-31 10:34:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:34:23.200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698012 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:34:23.202Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579243) 
;

-- 2022-05-31T07:34:23.204Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698012
;

-- 2022-05-31T07:34:23.205Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698012)
;

-- 2022-05-31T07:34:58.024Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546290,544938,TO_TIMESTAMP('2022-05-31 10:34:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-31 10:34:57','YYYY-MM-DD HH24:MI:SS'),100,'default')
;

-- 2022-05-31T07:34:58.026Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544938 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-05-31T07:35:05.633Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545972,544938,TO_TIMESTAMP('2022-05-31 10:35:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-31 10:35:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:35:08.290Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545973,544938,TO_TIMESTAMP('2022-05-31 10:35:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-05-31 10:35:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:35:17.324Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545972,549197,TO_TIMESTAMP('2022-05-31 10:35:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2022-05-31 10:35:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Step.Reihenfolge
-- Column: C_Project_WO_Step.SeqNo
-- 2022-05-31T07:35:42.180Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698008,0,546290,549197,608310,'F',TO_TIMESTAMP('2022-05-31 10:35:42','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','Reihenfolge',10,0,0,TO_TIMESTAMP('2022-05-31 10:35:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Step.Name
-- Column: C_Project_WO_Step.Name
-- 2022-05-31T07:35:52.692Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698009,0,546290,549197,608311,'F',TO_TIMESTAMP('2022-05-31 10:35:52','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Name',20,0,0,TO_TIMESTAMP('2022-05-31 10:35:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Step.Beschreibung
-- Column: C_Project_WO_Step.Description
-- 2022-05-31T07:36:02.570Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698010,0,546290,549197,608312,'F',TO_TIMESTAMP('2022-05-31 10:36:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',30,0,0,TO_TIMESTAMP('2022-05-31 10:36:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:36:13.863Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545972,549198,TO_TIMESTAMP('2022-05-31 10:36:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',20,TO_TIMESTAMP('2022-05-31 10:36:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:36:20.499Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2022-05-31 10:36:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549197
;

-- UI Element: Work Order Project -> Project Step.Startdatum
-- Column: C_Project_WO_Step.DateStart
-- 2022-05-31T07:36:37.779Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698011,0,546290,549198,608313,'F',TO_TIMESTAMP('2022-05-31 10:36:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Startdatum',10,0,0,TO_TIMESTAMP('2022-05-31 10:36:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Step. Endzeitpunkt
-- Column: C_Project_WO_Step.DateEnd
-- 2022-05-31T07:36:51.459Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698012,0,546290,549198,608314,'F',TO_TIMESTAMP('2022-05-31 10:36:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N',' Endzeitpunkt',20,0,0,TO_TIMESTAMP('2022-05-31 10:36:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:37:01.334Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545972,549199,TO_TIMESTAMP('2022-05-31 10:37:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',30,TO_TIMESTAMP('2022-05-31 10:37:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Step.Sektion
-- Column: C_Project_WO_Step.AD_Org_ID
-- 2022-05-31T07:37:24.448Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698004,0,546290,549199,608315,'F',TO_TIMESTAMP('2022-05-31 10:37:24','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2022-05-31 10:37:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Step.Mandant
-- Column: C_Project_WO_Step.AD_Client_ID
-- 2022-05-31T07:37:31.906Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698003,0,546290,549199,608316,'F',TO_TIMESTAMP('2022-05-31 10:37:31','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2022-05-31 10:37:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:37:46.367Z
UPDATE AD_UI_ElementGroup SET Name='client&org',Updated=TO_TIMESTAMP('2022-05-31 10:37:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549199
;

-- 2022-05-31T07:37:59.973Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=545973, SeqNo=10,Updated=TO_TIMESTAMP('2022-05-31 10:37:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549198
;

-- 2022-05-31T07:38:09.915Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=545973, SeqNo=20,Updated=TO_TIMESTAMP('2022-05-31 10:38:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549199
;

-- UI Element: Work Order Project -> Project Step.Reihenfolge
-- Column: C_Project_WO_Step.SeqNo
-- 2022-05-31T07:38:27.328Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-05-31 10:38:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608310
;

-- UI Element: Work Order Project -> Project Step.Name
-- Column: C_Project_WO_Step.Name
-- 2022-05-31T07:38:27.339Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-05-31 10:38:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608311
;

-- UI Element: Work Order Project -> Project Step.Startdatum
-- Column: C_Project_WO_Step.DateStart
-- 2022-05-31T07:38:27.348Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-05-31 10:38:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608313
;

-- UI Element: Work Order Project -> Project Step. Endzeitpunkt
-- Column: C_Project_WO_Step.DateEnd
-- 2022-05-31T07:38:27.357Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-05-31 10:38:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608314
;

-- Tab: Work Order Project -> Project Resource
-- Table: C_Project_WO_Resource
-- 2022-05-31T07:39:13.433Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583243,580966,0,546291,542161,541512,'Y',TO_TIMESTAMP('2022-05-31 10:39:13','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Project_WO_Resource','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Project Resource',583231,'N',30,1,TO_TIMESTAMP('2022-05-31 10:39:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:13.437Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546291 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-05-31T07:39:13.439Z
/* DDL */  select update_tab_translation_from_ad_element(580966) 
;

-- 2022-05-31T07:39:13.442Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546291)
;

-- Tab: Work Order Project -> Project Step
-- Table: C_Project_WO_Step
-- 2022-05-31T07:39:25.543Z
UPDATE AD_Tab SET AD_Column_ID=583231, Parent_Column_ID=1349, TabLevel=1,Updated=TO_TIMESTAMP('2022-05-31 10:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546290
;

-- Field: Work Order Project -> Project Resource -> Mandant
-- Column: C_Project_WO_Resource.AD_Client_ID
-- 2022-05-31T07:39:33.694Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583235,698013,0,546291,TO_TIMESTAMP('2022-05-31 10:39:33','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-05-31 10:39:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:33.696Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698013 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:33.699Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-05-31T07:39:33.851Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698013
;

-- 2022-05-31T07:39:33.853Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698013)
;

-- Field: Work Order Project -> Project Resource -> Sektion
-- Column: C_Project_WO_Resource.AD_Org_ID
-- 2022-05-31T07:39:33.967Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583236,698014,0,546291,TO_TIMESTAMP('2022-05-31 10:39:33','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-05-31 10:39:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:33.970Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698014 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:33.972Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-05-31T07:39:34.130Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698014
;

-- 2022-05-31T07:39:34.131Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698014)
;

-- Field: Work Order Project -> Project Resource -> Aktiv
-- Column: C_Project_WO_Resource.IsActive
-- 2022-05-31T07:39:34.239Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583239,698015,0,546291,TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:34.242Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698015 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:34.245Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-05-31T07:39:34.387Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698015
;

-- 2022-05-31T07:39:34.389Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698015)
;

-- Field: Work Order Project -> Project Resource -> Project Resource
-- Column: C_Project_WO_Resource.C_Project_WO_Resource_ID
-- 2022-05-31T07:39:34.480Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583242,698016,0,546291,TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Resource',TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:34.482Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698016 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:34.484Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580966) 
;

-- 2022-05-31T07:39:34.488Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698016
;

-- 2022-05-31T07:39:34.489Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698016)
;

-- Field: Work Order Project -> Project Resource -> Projekt
-- Column: C_Project_WO_Resource.C_Project_ID
-- 2022-05-31T07:39:34.598Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583243,698017,0,546291,TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:34.601Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698017 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:34.603Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-05-31T07:39:34.614Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698017
;

-- 2022-05-31T07:39:34.616Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698017)
;

-- Field: Work Order Project -> Project Resource -> Project Step
-- Column: C_Project_WO_Resource.C_Project_WO_Step_ID
-- 2022-05-31T07:39:34.711Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583244,698018,0,546291,TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Step',TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:34.713Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698018 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:34.715Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580965) 
;

-- 2022-05-31T07:39:34.718Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698018
;

-- 2022-05-31T07:39:34.720Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698018)
;

-- Field: Work Order Project -> Project Resource -> Ressource
-- Column: C_Project_WO_Resource.S_Resource_ID
-- 2022-05-31T07:39:34.827Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583245,698019,0,546291,TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100,'Ressource',10,'D','Y','N','N','N','N','N','N','N','Ressource',TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:34.830Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698019 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:34.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1777) 
;

-- 2022-05-31T07:39:34.836Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698019
;

-- 2022-05-31T07:39:34.838Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698019)
;

-- Field: Work Order Project -> Project Resource -> Zuordnung von
-- Column: C_Project_WO_Resource.AssignDateFrom
-- 2022-05-31T07:39:34.946Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583246,698020,0,546291,TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab',7,'D','Beginn Zuordnung','Y','N','N','N','N','N','N','N','Zuordnung von',TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:34.949Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698020 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:34.951Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1754) 
;

-- 2022-05-31T07:39:34.953Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698020
;

-- 2022-05-31T07:39:34.954Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698020)
;

-- Field: Work Order Project -> Project Resource -> Zuordnung bis
-- Column: C_Project_WO_Resource.AssignDateTo
-- 2022-05-31T07:39:35.060Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583247,698021,0,546291,TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis',7,'D','Zuordnung endet','Y','N','N','N','N','N','N','N','Zuordnung bis',TO_TIMESTAMP('2022-05-31 10:39:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:35.062Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698021 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:35.064Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1755) 
;

-- 2022-05-31T07:39:35.066Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698021
;

-- 2022-05-31T07:39:35.067Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698021)
;

-- Field: Work Order Project -> Project Resource -> All day
-- Column: C_Project_WO_Resource.IsAllDay
-- 2022-05-31T07:39:35.174Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583248,698022,0,546291,TO_TIMESTAMP('2022-05-31 10:39:35','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','All day',TO_TIMESTAMP('2022-05-31 10:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:35.177Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698022 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:35.179Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580861) 
;

-- 2022-05-31T07:39:35.181Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698022
;

-- 2022-05-31T07:39:35.182Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698022)
;

-- Field: Work Order Project -> Project Resource -> Duration
-- Column: C_Project_WO_Resource.Duration
-- 2022-05-31T07:39:35.278Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583249,698023,0,546291,TO_TIMESTAMP('2022-05-31 10:39:35','YYYY-MM-DD HH24:MI:SS'),100,'Normal Duration in Duration Unit',10,'D','Expected (normal) Length of time for the execution','Y','N','N','N','N','N','N','N','Duration',TO_TIMESTAMP('2022-05-31 10:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:35.281Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698023 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:35.283Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2320) 
;

-- 2022-05-31T07:39:35.286Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698023
;

-- 2022-05-31T07:39:35.287Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698023)
;

-- Field: Work Order Project -> Project Resource -> Duration Unit
-- Column: C_Project_WO_Resource.DurationUnit
-- 2022-05-31T07:39:35.391Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583250,698024,0,546291,TO_TIMESTAMP('2022-05-31 10:39:35','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Duration',1,'D','Unit to define the length of time for the execution','Y','N','N','N','N','N','N','N','Duration Unit',TO_TIMESTAMP('2022-05-31 10:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:35.393Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698024 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:35.395Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2321) 
;

-- 2022-05-31T07:39:35.398Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698024
;

-- 2022-05-31T07:39:35.399Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698024)
;

-- Field: Work Order Project -> Project Resource -> Beschreibung
-- Column: C_Project_WO_Resource.Description
-- 2022-05-31T07:39:35.510Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583251,698025,0,546291,TO_TIMESTAMP('2022-05-31 10:39:35','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2022-05-31 10:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:35.512Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698025 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:35.514Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-05-31T07:39:35.558Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698025
;

-- 2022-05-31T07:39:35.559Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698025)
;

-- Field: Work Order Project -> Project Resource -> Project Resource Budget
-- Column: C_Project_WO_Resource.C_Project_Resource_Budget_ID
-- 2022-05-31T07:39:35.665Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583252,698026,0,546291,TO_TIMESTAMP('2022-05-31 10:39:35','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Resource Budget',TO_TIMESTAMP('2022-05-31 10:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:35.668Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698026 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:35.670Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580947) 
;

-- 2022-05-31T07:39:35.672Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698026
;

-- 2022-05-31T07:39:35.673Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698026)
;

-- Field: Work Order Project -> Project Resource -> Budget Project
-- Column: C_Project_WO_Resource.Budget_Project_ID
-- 2022-05-31T07:39:35.773Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583253,698027,0,546291,TO_TIMESTAMP('2022-05-31 10:39:35','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Budget Project',TO_TIMESTAMP('2022-05-31 10:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:39:35.776Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698027 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:39:35.777Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580967) 
;

-- 2022-05-31T07:39:35.779Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698027
;

-- 2022-05-31T07:39:35.781Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698027)
;

-- 2022-05-31T07:40:09.065Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546291,544939,TO_TIMESTAMP('2022-05-31 10:40:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-31 10:40:08','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-05-31T07:40:09.067Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544939 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-05-31T07:40:14.564Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545974,544939,TO_TIMESTAMP('2022-05-31 10:40:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-31 10:40:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:40:16.597Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545975,544939,TO_TIMESTAMP('2022-05-31 10:40:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-05-31 10:40:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:41:48.982Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545974,549200,TO_TIMESTAMP('2022-05-31 10:41:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','parent',10,TO_TIMESTAMP('2022-05-31 10:41:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource.Project Step
-- Column: C_Project_WO_Resource.C_Project_WO_Step_ID
-- 2022-05-31T07:42:09.560Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698018,0,546291,549200,608317,'F',TO_TIMESTAMP('2022-05-31 10:42:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Project Step',10,0,0,TO_TIMESTAMP('2022-05-31 10:42:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:42:20.709Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545974,549201,TO_TIMESTAMP('2022-05-31 10:42:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','resource',20,TO_TIMESTAMP('2022-05-31 10:42:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource.Ressource
-- Column: C_Project_WO_Resource.S_Resource_ID
-- 2022-05-31T07:42:42.245Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698019,0,546291,549201,608318,'F',TO_TIMESTAMP('2022-05-31 10:42:42','YYYY-MM-DD HH24:MI:SS'),100,'Ressource','Y','N','Y','N','N','Ressource',10,0,0,TO_TIMESTAMP('2022-05-31 10:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:43:10.314Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545974,549202,TO_TIMESTAMP('2022-05-31 10:43:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates & duration',30,TO_TIMESTAMP('2022-05-31 10:43:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource.Zuordnung von
-- Column: C_Project_WO_Resource.AssignDateFrom
-- 2022-05-31T07:43:27.344Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698020,0,546291,549202,608319,'F',TO_TIMESTAMP('2022-05-31 10:43:27','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab','Beginn Zuordnung','Y','N','Y','N','N','Zuordnung von',10,0,0,TO_TIMESTAMP('2022-05-31 10:43:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource.Zuordnung bis
-- Column: C_Project_WO_Resource.AssignDateTo
-- 2022-05-31T07:43:35.085Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698021,0,546291,549202,608320,'F',TO_TIMESTAMP('2022-05-31 10:43:34','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis','Zuordnung endet','Y','N','Y','N','N','Zuordnung bis',20,0,0,TO_TIMESTAMP('2022-05-31 10:43:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource.All day
-- Column: C_Project_WO_Resource.IsAllDay
-- 2022-05-31T07:43:42.875Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698022,0,546291,549202,608321,'F',TO_TIMESTAMP('2022-05-31 10:43:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','All day',30,0,0,TO_TIMESTAMP('2022-05-31 10:43:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource.Duration Unit
-- Column: C_Project_WO_Resource.DurationUnit
-- 2022-05-31T07:43:50.734Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698024,0,546291,549202,608322,'F',TO_TIMESTAMP('2022-05-31 10:43:50','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Duration','Unit to define the length of time for the execution','Y','N','Y','N','N','Duration Unit',40,0,0,TO_TIMESTAMP('2022-05-31 10:43:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource.Duration
-- Column: C_Project_WO_Resource.Duration
-- 2022-05-31T07:44:04.872Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698023,0,546291,549202,608323,'F',TO_TIMESTAMP('2022-05-31 10:44:04','YYYY-MM-DD HH24:MI:SS'),100,'Normal Duration in Duration Unit','Expected (normal) Length of time for the execution','Y','N','Y','N','N','Duration',50,0,0,TO_TIMESTAMP('2022-05-31 10:44:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:44:14.970Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545974,549203,TO_TIMESTAMP('2022-05-31 10:44:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','client & org',40,TO_TIMESTAMP('2022-05-31 10:44:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource.Sektion
-- Column: C_Project_WO_Resource.AD_Org_ID
-- 2022-05-31T07:44:26.890Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698014,0,546291,549203,608324,'F',TO_TIMESTAMP('2022-05-31 10:44:26','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2022-05-31 10:44:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource.Mandant
-- Column: C_Project_WO_Resource.AD_Client_ID
-- 2022-05-31T07:44:33.655Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698013,0,546291,549203,608325,'F',TO_TIMESTAMP('2022-05-31 10:44:33','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2022-05-31 10:44:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:45:18.946Z
UPDATE AD_UI_ElementGroup SET Name='main',Updated=TO_TIMESTAMP('2022-05-31 10:45:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549200
;

-- UI Element: Work Order Project -> Project Resource.Beschreibung
-- Column: C_Project_WO_Resource.Description
-- 2022-05-31T07:45:34.586Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698025,0,546291,549200,608326,'F',TO_TIMESTAMP('2022-05-31 10:45:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',20,0,0,TO_TIMESTAMP('2022-05-31 10:45:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:46:08.163Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545974,549204,TO_TIMESTAMP('2022-05-31 10:46:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','budget',50,TO_TIMESTAMP('2022-05-31 10:46:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource.Budget Project
-- Column: C_Project_WO_Resource.Budget_Project_ID
-- 2022-05-31T07:46:19.592Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698027,0,546291,549204,608327,'F',TO_TIMESTAMP('2022-05-31 10:46:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Budget Project',10,0,0,TO_TIMESTAMP('2022-05-31 10:46:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Work Order Project -> Project Resource.Project Resource Budget
-- Column: C_Project_WO_Resource.C_Project_Resource_Budget_ID
-- 2022-05-31T07:46:30.327Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698026,0,546291,549204,608328,'F',TO_TIMESTAMP('2022-05-31 10:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Project Resource Budget',20,0,0,TO_TIMESTAMP('2022-05-31 10:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:46:53.897Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=545975, SeqNo=10,Updated=TO_TIMESTAMP('2022-05-31 10:46:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549204
;

-- 2022-05-31T07:47:01.967Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=545975, SeqNo=20,Updated=TO_TIMESTAMP('2022-05-31 10:47:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549203
;

-- UI Element: Work Order Project -> Project Resource.Project Step
-- Column: C_Project_WO_Resource.C_Project_WO_Step_ID
-- 2022-05-31T07:47:33.814Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-05-31 10:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608317
;

-- UI Element: Work Order Project -> Project Resource.Ressource
-- Column: C_Project_WO_Resource.S_Resource_ID
-- 2022-05-31T07:47:33.824Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-05-31 10:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608318
;

-- UI Element: Work Order Project -> Project Resource.Duration
-- Column: C_Project_WO_Resource.Duration
-- 2022-05-31T07:47:33.832Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-05-31 10:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608323
;

-- UI Element: Work Order Project -> Project Resource.Zuordnung von
-- Column: C_Project_WO_Resource.AssignDateFrom
-- 2022-05-31T07:47:33.840Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-05-31 10:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608319
;

-- UI Element: Work Order Project -> Project Resource.Zuordnung bis
-- Column: C_Project_WO_Resource.AssignDateTo
-- 2022-05-31T07:47:33.848Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-05-31 10:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608320
;

-- Name: Work Order Project
-- Window: Work Order Project
-- 2022-05-31T07:48:18.705Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,580968,541961,0,541512,TO_TIMESTAMP('2022-05-31 10:48:18','YYYY-MM-DD HH24:MI:SS'),100,'D','workOrderProject','Y','N','N','N','N','Work Order Project',TO_TIMESTAMP('2022-05-31 10:48:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:48:18.709Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541961 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-05-31T07:48:18.713Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541961, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541961)
;

-- 2022-05-31T07:48:18.727Z
/* DDL */  select update_menu_translation_from_ad_element(580968) 
;

-- Reordering children of `Depreciation Processing`
-- Node name: `Build Depreciation Forecast (A_Depreciation_Forecast)`
-- 2022-05-31T07:48:19.340Z
UPDATE AD_TreeNodeMM SET Parent_ID=53149, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53154 AND AD_Tree_ID=10
;

-- Node name: `Asset Depreciation Forecast`
-- 2022-05-31T07:48:19.343Z
UPDATE AD_TreeNodeMM SET Parent_ID=53149, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53153 AND AD_Tree_ID=10
;

-- Node name: `Build Depreciation Workfile (A_Depreciation_Build)`
-- 2022-05-31T07:48:19.344Z
UPDATE AD_TreeNodeMM SET Parent_ID=53149, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53151 AND AD_Tree_ID=10
;

-- Node name: `Depreciation Expense Entry`
-- 2022-05-31T07:48:19.346Z
UPDATE AD_TreeNodeMM SET Parent_ID=53149, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53150 AND AD_Tree_ID=10
;

-- Node name: `Post Depreciation Entry (A_Depreciation_Entry)`
-- 2022-05-31T07:48:19.347Z
UPDATE AD_TreeNodeMM SET Parent_ID=53149, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53152 AND AD_Tree_ID=10
;

-- Node name: `Work Order Project`
-- 2022-05-31T07:48:19.349Z
UPDATE AD_TreeNodeMM SET Parent_ID=53149, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541961 AND AD_Tree_ID=10
;

-- Reordering children of `webUI`
-- Node name: `CRM`
-- 2022-05-31T07:48:27.939Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- Node name: `Marketing`
-- 2022-05-31T07:48:27.941Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- Node name: `Product Management`
-- 2022-05-31T07:48:27.942Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- Node name: `Sales`
-- 2022-05-31T07:48:27.944Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- Node name: `Purchase`
-- 2022-05-31T07:48:27.945Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- Node name: `Pricing`
-- 2022-05-31T07:48:27.946Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Management`
-- 2022-05-31T07:48:27.948Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2022-05-31T07:48:27.949Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing`
-- 2022-05-31T07:48:27.951Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt`
-- 2022-05-31T07:48:27.952Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- Node name: `Billing`
-- 2022-05-31T07:48:27.953Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- Node name: `Finance`
-- 2022-05-31T07:48:27.954Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- Node name: `Logistics`
-- 2022-05-31T07:48:27.956Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- Node name: `Shipment`
-- 2022-05-31T07:48:27.957Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- Node name: `Pharma`
-- 2022-05-31T07:48:27.958Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2022-05-31T07:48:27.960Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- Node name: `Work Order Project`
-- 2022-05-31T07:48:27.961Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541961 AND AD_Tree_ID=10
;

-- Node name: `Seminar-Verwaltung`
-- 2022-05-31T07:48:27.962Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- Node name: `Forum Datenaustausch`
-- 2022-05-31T07:48:27.964Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- Node name: `Client/ Organisation`
-- 2022-05-31T07:48:27.969Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- Node name: `System`
-- 2022-05-31T07:48:27.971Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- Node name: `Service delivery`
-- 2022-05-31T07:48:27.973Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541428 AND AD_Tree_ID=10
;

-- Node name: `Calendar`
-- 2022-05-31T07:48:27.974Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541955 AND AD_Tree_ID=10
;

-- Reordering children of `Project Management`
-- Node name: `Project Type (C_ProjectType)`
-- 2022-05-31T07:48:31.427Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541330 AND AD_Tree_ID=10
;

-- Node name: `Project (C_Project)`
-- 2022-05-31T07:48:31.428Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Project (C_Project)`
-- 2022-05-31T07:48:31.430Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541577 AND AD_Tree_ID=10
;

-- Node name: `Work Order Project`
-- 2022-05-31T07:48:31.431Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541961 AND AD_Tree_ID=10
;

-- Node name: `Budget Project (C_Project)`
-- 2022-05-31T07:48:31.432Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541957 AND AD_Tree_ID=10
;

-- Reordering children of `Project Management`
-- Node name: `Project Type (C_ProjectType)`
-- 2022-05-31T07:48:34.822Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541330 AND AD_Tree_ID=10
;

-- Node name: `Project (C_Project)`
-- 2022-05-31T07:48:34.824Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Project (C_Project)`
-- 2022-05-31T07:48:34.825Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541577 AND AD_Tree_ID=10
;

-- Node name: `Work Order Project`
-- 2022-05-31T07:48:34.827Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541961 AND AD_Tree_ID=10
;

-- Node name: `Budget Project (C_Project)`
-- 2022-05-31T07:48:34.828Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541957 AND AD_Tree_ID=10
;

-- Reordering children of `Project Management`
-- Node name: `Project Type (C_ProjectType)`
-- 2022-05-31T07:48:37.083Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541330 AND AD_Tree_ID=10
;

-- Node name: `Project (C_Project)`
-- 2022-05-31T07:48:37.085Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Project (C_Project)`
-- 2022-05-31T07:48:37.086Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541577 AND AD_Tree_ID=10
;

-- Node name: `Budget Project (C_Project)`
-- 2022-05-31T07:48:37.087Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541957 AND AD_Tree_ID=10
;

-- Node name: `Work Order Project`
-- 2022-05-31T07:48:37.089Z
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541961 AND AD_Tree_ID=10
;

