-- Table: C_Import_Camt53_File
-- 2022-10-17T13:52:58.592Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542246,'N',TO_TIMESTAMP('2022-10-17 16:52:58','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Camt53 import file','NP','L','C_Import_Camt53_File','DTI',TO_TIMESTAMP('2022-10-17 16:52:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T13:52:58.597Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542246 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-10-17T13:52:58.724Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556036,TO_TIMESTAMP('2022-10-17 16:52:58','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Import_Camt53_File',1,'Y','N','Y','Y','C_Import_Camt53_File','N',1000000,TO_TIMESTAMP('2022-10-17 16:52:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T13:52:58.751Z
CREATE SEQUENCE C_IMPORT_CAMT53_FILE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: C_Import_Camt53_File
-- 2022-10-17T13:53:16.788Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2022-10-17 16:53:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542246
;

-- Table: C_Import_Camt53_File
-- 2022-10-17T13:53:18.692Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='N',Updated=TO_TIMESTAMP('2022-10-17 16:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542246
;

-- Column: C_Import_Camt53_File.AD_Client_ID
-- 2022-10-17T13:53:25.108Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584717,102,0,19,542246,'AD_Client_ID',TO_TIMESTAMP('2022-10-17 16:53:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-10-17 16:53:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-17T13:53:25.112Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584717 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-17T13:53:25.157Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Import_Camt53_File.AD_Org_ID
-- 2022-10-17T13:53:27.053Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584718,113,0,30,542246,'AD_Org_ID',TO_TIMESTAMP('2022-10-17 16:53:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-10-17 16:53:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-17T13:53:27.054Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584718 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-17T13:53:27.056Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Import_Camt53_File.Created
-- 2022-10-17T13:53:28.343Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584719,245,0,16,542246,'Created',TO_TIMESTAMP('2022-10-17 16:53:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-10-17 16:53:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-17T13:53:28.345Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584719 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-17T13:53:28.347Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Import_Camt53_File.CreatedBy
-- 2022-10-17T13:53:29.352Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584720,246,0,18,110,542246,'CreatedBy',TO_TIMESTAMP('2022-10-17 16:53:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-10-17 16:53:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-17T13:53:29.353Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584720 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-17T13:53:29.355Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Import_Camt53_File.IsActive
-- 2022-10-17T13:53:30.247Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584721,348,0,20,542246,'IsActive',TO_TIMESTAMP('2022-10-17 16:53:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-10-17 16:53:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-17T13:53:30.248Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584721 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-17T13:53:30.250Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Import_Camt53_File.Updated
-- 2022-10-17T13:53:31.305Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584722,607,0,16,542246,'Updated',TO_TIMESTAMP('2022-10-17 16:53:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-10-17 16:53:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-17T13:53:31.307Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584722 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-17T13:53:31.309Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Import_Camt53_File.UpdatedBy
-- 2022-10-17T13:53:32.086Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584723,608,0,18,110,542246,'UpdatedBy',TO_TIMESTAMP('2022-10-17 16:53:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-10-17 16:53:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-17T13:53:32.087Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584723 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-17T13:53:32.089Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-10-17T13:53:32.689Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581559,0,'C_Import_Camt53_File_ID',TO_TIMESTAMP('2022-10-17 16:53:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Camt53 import file','Camt53 import file',TO_TIMESTAMP('2022-10-17 16:53:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T13:53:32.692Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581559 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Import_Camt53_File.C_Import_Camt53_File_ID
-- 2022-10-17T13:53:33.331Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584724,581559,0,13,542246,'C_Import_Camt53_File_ID',TO_TIMESTAMP('2022-10-17 16:53:32','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Camt53 import file',0,0,TO_TIMESTAMP('2022-10-17 16:53:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-17T13:53:33.333Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584724 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-17T13:53:33.336Z
/* DDL */  select update_Column_Translation_From_AD_Element(581559) 
;

-- 2022-10-17T13:53:33.838Z
/* DDL */ CREATE TABLE public.C_Import_Camt53_File (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Import_Camt53_File_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_Import_Camt53_File_Key PRIMARY KEY (C_Import_Camt53_File_ID))
;

-- 2022-10-17T13:53:33.877Z
INSERT INTO t_alter_column values('c_import_camt53_file','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2022-10-17T13:53:33.898Z
INSERT INTO t_alter_column values('c_import_camt53_file','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-10-17T13:53:33.907Z
INSERT INTO t_alter_column values('c_import_camt53_file','CreatedBy','NUMERIC(10)',null,null)
;

-- 2022-10-17T13:53:33.918Z
INSERT INTO t_alter_column values('c_import_camt53_file','IsActive','CHAR(1)',null,null)
;

-- 2022-10-17T13:53:33.944Z
INSERT INTO t_alter_column values('c_import_camt53_file','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-10-17T13:53:33.955Z
INSERT INTO t_alter_column values('c_import_camt53_file','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2022-10-17T13:53:33.966Z
INSERT INTO t_alter_column values('c_import_camt53_file','C_Import_Camt53_File_ID','NUMERIC(10)',null,null)
;

-- Column: C_Import_Camt53_File.FileName
-- 2022-10-17T13:55:09.991Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584725,2295,0,10,542246,'FileName',TO_TIMESTAMP('2022-10-17 16:55:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Name of the local file or URL','D',0,255,'Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Dateiname',0,0,TO_TIMESTAMP('2022-10-17 16:55:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-17T13:55:09.993Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584725 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-17T13:55:09.996Z
/* DDL */  select update_Column_Translation_From_AD_Element(2295) 
;

-- 2022-10-17T13:55:11.620Z
/* DDL */ SELECT public.db_alter_table('C_Import_Camt53_File','ALTER TABLE public.C_Import_Camt53_File ADD COLUMN FileName VARCHAR(255) NOT NULL')
;

-- 2022-10-17T13:59:30.736Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581560,0,'Imported',TO_TIMESTAMP('2022-10-17 16:59:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Imported timestamp','Imported timestamp',TO_TIMESTAMP('2022-10-17 16:59:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T13:59:30.743Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581560 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Import_Camt53_File.Imported
-- 2022-10-17T14:00:21.630Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584726,581560,0,16,542246,'Imported',TO_TIMESTAMP('2022-10-17 17:00:21','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Imported timestamp',0,0,TO_TIMESTAMP('2022-10-17 17:00:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-17T14:00:21.632Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584726 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-17T14:00:21.635Z
/* DDL */  select update_Column_Translation_From_AD_Element(581560) 
;

-- 2022-10-17T14:00:24.077Z
/* DDL */ SELECT public.db_alter_table('C_Import_Camt53_File','ALTER TABLE public.C_Import_Camt53_File ADD COLUMN Imported TIMESTAMP WITH TIME ZONE')
;

-- 2022-10-17T14:00:28.892Z
INSERT INTO t_alter_column values('c_import_camt53_file','FileName','VARCHAR(255)',null,null)
;

-- Column: C_Import_Camt53_File.Processed
-- 2022-10-17T14:02:11.169Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584727,1047,0,20,542246,'Processed',TO_TIMESTAMP('2022-10-17 17:02:11','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','D',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2022-10-17 17:02:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-17T14:02:11.175Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584727 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-17T14:02:11.183Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;

-- 2022-10-17T14:02:12.378Z
/* DDL */ SELECT public.db_alter_table('C_Import_Camt53_File','ALTER TABLE public.C_Import_Camt53_File ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N''))')
;

-- Window: Camt53 import file, InternalName=null
-- 2022-10-17T14:03:31.366Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581559,0,541623,TO_TIMESTAMP('2022-10-17 17:03:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Camt53 import file','N',TO_TIMESTAMP('2022-10-17 17:03:31','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-10-17T14:03:31.371Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541623 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-10-17T14:03:31.381Z
/* DDL */  select update_window_translation_from_ad_element(581559) 
;

-- 2022-10-17T14:03:31.404Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541623
;

-- 2022-10-17T14:03:31.409Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541623)
;

-- Tab: Camt53 import file(541623,D) -> Camt53 import file
-- Table: C_Import_Camt53_File
-- 2022-10-17T14:03:59.507Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581559,0,546658,542246,541623,'Y',TO_TIMESTAMP('2022-10-17 17:03:59','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Import_Camt53_File','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Camt53 import file','N',10,0,TO_TIMESTAMP('2022-10-17 17:03:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T14:03:59.512Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546658 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-10-17T14:03:59.519Z
/* DDL */  select update_tab_translation_from_ad_element(581559) 
;

-- 2022-10-17T14:03:59.529Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546658)
;

-- Field: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> Mandant
-- Column: C_Import_Camt53_File.AD_Client_ID
-- 2022-10-17T14:04:11.643Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584717,707791,0,546658,TO_TIMESTAMP('2022-10-17 17:04:11','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-10-17 17:04:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T14:04:11.645Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707791 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-17T14:04:11.647Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-10-17T14:04:11.953Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707791
;

-- 2022-10-17T14:04:11.953Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707791)
;

-- Field: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> Sektion
-- Column: C_Import_Camt53_File.AD_Org_ID
-- 2022-10-17T14:04:12.060Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584718,707792,0,546658,TO_TIMESTAMP('2022-10-17 17:04:11','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-10-17 17:04:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T14:04:12.063Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707792 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-17T14:04:12.066Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-10-17T14:04:12.417Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707792
;

-- 2022-10-17T14:04:12.417Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707792)
;

-- Field: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> Aktiv
-- Column: C_Import_Camt53_File.IsActive
-- 2022-10-17T14:04:12.715Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584721,707793,0,546658,TO_TIMESTAMP('2022-10-17 17:04:12','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-10-17 17:04:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T14:04:12.719Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707793 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-17T14:04:12.722Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-10-17T14:04:14.219Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707793
;

-- 2022-10-17T14:04:14.219Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707793)
;

-- Field: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> Camt53 import file
-- Column: C_Import_Camt53_File.C_Import_Camt53_File_ID
-- 2022-10-17T14:04:14.420Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584724,707794,0,546658,TO_TIMESTAMP('2022-10-17 17:04:14','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Camt53 import file',TO_TIMESTAMP('2022-10-17 17:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T14:04:14.423Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707794 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-17T14:04:14.428Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581559) 
;

-- 2022-10-17T14:04:14.435Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707794
;

-- 2022-10-17T14:04:14.435Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707794)
;

-- Field: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> Dateiname
-- Column: C_Import_Camt53_File.FileName
-- 2022-10-17T14:04:14.537Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584725,707795,0,546658,TO_TIMESTAMP('2022-10-17 17:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Name of the local file or URL',255,'D','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','N','N','N','N','N','N','N','Dateiname',TO_TIMESTAMP('2022-10-17 17:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T14:04:14.540Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707795 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-17T14:04:14.544Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2295) 
;

-- 2022-10-17T14:04:14.563Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707795
;

-- 2022-10-17T14:04:14.564Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707795)
;

-- Field: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> Imported timestamp
-- Column: C_Import_Camt53_File.Imported
-- 2022-10-17T14:04:14.674Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584726,707796,0,546658,TO_TIMESTAMP('2022-10-17 17:04:14','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Imported timestamp',TO_TIMESTAMP('2022-10-17 17:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T14:04:14.677Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707796 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-17T14:04:14.681Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581560) 
;

-- 2022-10-17T14:04:14.702Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707796
;

-- 2022-10-17T14:04:14.703Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707796)
;

-- Field: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> Verarbeitet
-- Column: C_Import_Camt53_File.Processed
-- 2022-10-17T14:04:14.806Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584727,707797,0,546658,TO_TIMESTAMP('2022-10-17 17:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2022-10-17 17:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T14:04:14.809Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707797 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-17T14:04:14.813Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2022-10-17T14:04:14.824Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707797
;

-- 2022-10-17T14:04:14.825Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707797)
;

-- Tab: Camt53 import file(541623,D) -> Camt53 import file(546658,D)
-- UI Section: main
-- 2022-10-17T14:04:31.295Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546658,545282,TO_TIMESTAMP('2022-10-17 17:04:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-10-17 17:04:31','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-10-17T14:04:31.299Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545282 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> main
-- UI Column: 10
-- 2022-10-17T14:05:04.596Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546425,545282,TO_TIMESTAMP('2022-10-17 17:05:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-10-17 17:05:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> main -> 10
-- UI Element Group: main
-- 2022-10-17T14:05:18.044Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546425,549978,TO_TIMESTAMP('2022-10-17 17:05:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2022-10-17 17:05:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> main -> 10 -> main.Dateiname
-- Column: C_Import_Camt53_File.FileName
-- 2022-10-17T14:05:38.131Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707795,0,546658,613268,549978,'F',TO_TIMESTAMP('2022-10-17 17:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Name of the local file or URL','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','N','N','Y','N','N','N',0,'Dateiname',10,0,0,TO_TIMESTAMP('2022-10-17 17:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> main -> 10 -> main.Imported timestamp
-- Column: C_Import_Camt53_File.Imported
-- 2022-10-17T14:06:06.053Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707796,0,546658,613269,549978,'F',TO_TIMESTAMP('2022-10-17 17:06:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Imported timestamp',20,0,0,TO_TIMESTAMP('2022-10-17 17:06:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> main -> 10 -> main.Verarbeitet
-- Column: C_Import_Camt53_File.Processed
-- 2022-10-17T14:06:30.970Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707797,0,546658,613270,549978,'F',TO_TIMESTAMP('2022-10-17 17:06:30','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',30,0,0,TO_TIMESTAMP('2022-10-17 17:06:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> main
-- UI Column: 20
-- 2022-10-17T14:06:46.865Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546426,545282,TO_TIMESTAMP('2022-10-17 17:06:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-10-17 17:06:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> main -> 20
-- UI Element Group: Org
-- 2022-10-17T14:06:54.745Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546426,549979,TO_TIMESTAMP('2022-10-17 17:06:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','Org',10,TO_TIMESTAMP('2022-10-17 17:06:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> main -> 20 -> Org.Sektion
-- Column: C_Import_Camt53_File.AD_Org_ID
-- 2022-10-17T14:07:07.268Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707792,0,546658,613271,549979,'F',TO_TIMESTAMP('2022-10-17 17:07:07','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2022-10-17 17:07:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> main -> 20 -> Org.Mandant
-- Column: C_Import_Camt53_File.AD_Client_ID
-- 2022-10-17T14:07:15.207Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707791,0,546658,613272,549979,'F',TO_TIMESTAMP('2022-10-17 17:07:15','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2022-10-17 17:07:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: Camt53 import file
-- Action Type: null
-- 2022-10-17T14:09:28.358Z
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,581559,542014,0,TO_TIMESTAMP('2022-10-17 17:09:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Camt53 import file','Y','N','N','N','N','Camt53 import file',TO_TIMESTAMP('2022-10-17 17:09:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T14:09:28.361Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542014 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-10-17T14:09:28.364Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542014, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542014)
;

-- 2022-10-17T14:09:28.384Z
/* DDL */  select update_menu_translation_from_ad_element(581559) 
;

-- Reordering children of `Finance`
-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2022-10-17T14:09:29.153Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2022-10-17T14:09:29.154Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2022-10-17T14:09:29.155Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2022-10-17T14:09:29.156Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2022-10-17T14:09:29.157Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2022-10-17T14:09:29.158Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2022-10-17T14:09:29.159Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2022-10-17T14:09:29.159Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2022-10-17T14:09:29.160Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2022-10-17T14:09:29.161Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2022-10-17T14:09:29.162Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2022-10-17T14:09:29.162Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2022-10-17T14:09:29.163Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2022-10-17T14:09:29.164Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2022-10-17T14:09:29.165Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2022-10-17T14:09:29.165Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2022-10-17T14:09:29.166Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2022-10-17T14:09:29.167Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2022-10-17T14:09:29.167Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2022-10-17T14:09:29.168Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2022-10-17T14:09:29.169Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-10-17T14:09:29.169Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-10-17T14:09:29.170Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2022-10-17T14:09:29.171Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2022-10-17T14:09:29.172Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2022-10-17T14:09:29.173Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2022-10-17T14:09:29.173Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2022-10-17T14:09:29.174Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2022-10-17T14:09:29.174Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2022-10-17T14:09:29.175Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2022-10-17T14:09:29.175Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2022-10-17T14:09:29.176Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2022-10-17T14:09:29.176Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-10-17T14:09:29.177Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2022-10-17T14:09:29.178Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-10-17T14:09:29.178Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-10-17T14:09:29.179Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Camt53 import file`
-- 2022-10-17T14:09:29.179Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Name: Camt53 import file
-- Action Type: W
-- Window: Camt53 import file(541623,D)
-- 2022-10-17T14:09:57.691Z
UPDATE AD_Menu SET Action='W', AD_Window_ID=541623, InternalName='541623',Updated=TO_TIMESTAMP('2022-10-17 17:09:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542014
;

-- Process: C_BankStatement_Camt53_Import_LoadFromFile(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile)
-- Table: C_BankStatement
-- EntityType: D
-- 2022-10-17T14:15:18.040Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541293
;

-- Process: C_BankStatement_Camt53_Import_LoadFromFile(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile)
-- Table: C_Import_Camt53_File
-- EntityType: D
-- 2022-10-17T14:15:39.952Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585128,542246,541294,TO_TIMESTAMP('2022-10-17 17:15:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2022-10-17 17:15:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Process: C_BankStatement_Camt53_Import_LoadFromFile(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile)
-- ParameterName: FileName
-- 2022-10-17T14:17:59.845Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542320
;

-- 2022-10-17T14:17:59.854Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542320
;

-- Process: C_BankStatement_Camt53_Import_LoadFromFile(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile)
-- ParameterName: AD_AttachmentEntry_ID
-- 2022-10-17T14:19:09.065Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543390,0,585128,542327,18,540479,'AD_AttachmentEntry_ID',TO_TIMESTAMP('2022-10-17 17:19:08','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Anhang',10,TO_TIMESTAMP('2022-10-17 17:19:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T14:19:09.067Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542327 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_BankStatement_Camt53_Import_LoadFromFile(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile)
-- Table: C_Import_Camt53_File
-- EntityType: D
-- 2022-10-17T14:24:14.012Z
UPDATE AD_Table_Process SET WEBUI_ViewAction='N',Updated=TO_TIMESTAMP('2022-10-17 17:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541294
;

-- Value: C_BankStatement_Camt53_ImportAttachment
-- Classname: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile
-- 2022-10-17T14:26:16.282Z
UPDATE AD_Process SET Value='C_BankStatement_Camt53_ImportAttachment',Updated=TO_TIMESTAMP('2022-10-17 17:26:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585128
;

-- Value: C_BankStatement_Camt53_ImportAttachment
-- Classname: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment
-- 2022-10-17T14:26:29.723Z
UPDATE AD_Process SET Classname='de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment',Updated=TO_TIMESTAMP('2022-10-17 17:26:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585128
;

-- Process: C_BankStatement_Camt53_ImportAttachment(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment)
-- Table: C_Import_Camt53_File
-- EntityType: D
-- 2022-10-17T14:28:26.891Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541294
;

-- Value: C_BankStatement_Camt53_ImportAttachment
-- Classname: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment
-- 2022-10-17T14:28:30.387Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=585128
;

-- 2022-10-17T14:28:30.389Z
DELETE FROM AD_Process WHERE AD_Process_ID=585128
;

-- Value: C_BankStatement_Camt53_ImportAttachment
-- Classname: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment
-- 2022-10-17T14:29:08.976Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585129,'Y','de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment','N',TO_TIMESTAMP('2022-10-17 17:29:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Kontoauszugsdatei importieren (camt53)','json','N','N','xls','Java',TO_TIMESTAMP('2022-10-17 17:29:08','YYYY-MM-DD HH24:MI:SS'),100,'C_BankStatement_Camt53_ImportAttachment')
;

-- 2022-10-17T14:29:08.982Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585129 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_BankStatement_Camt53_ImportAttachment(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment)
-- 2022-10-17T14:29:15.865Z
UPDATE AD_Process_Trl SET Name='Import Bank Statement File (camt53)',Updated=TO_TIMESTAMP('2022-10-17 17:29:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585129
;

-- Process: C_BankStatement_Camt53_ImportAttachment(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment)
-- ParameterName: AD_AttachmentEntry_ID
-- 2022-10-17T14:29:49.288Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543390,0,585129,542328,18,540479,'AD_AttachmentEntry_ID',TO_TIMESTAMP('2022-10-17 17:29:49','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Anhang',10,TO_TIMESTAMP('2022-10-17 17:29:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-17T14:29:49.295Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542328 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_BankStatement_Camt53_ImportAttachment(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment)
-- Table: C_Import_Camt53_File
-- EntityType: D
-- 2022-10-17T14:30:07.396Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585129,542246,541295,TO_TIMESTAMP('2022-10-17 17:30:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2022-10-17 17:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

-- Value: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment.NoStatementImported
-- 2022-10-17T14:31:44.381Z
UPDATE AD_Message SET Value='de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment.NoStatementImported',Updated=TO_TIMESTAMP('2022-10-17 17:31:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545163
;

-- Value: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment.NoCamt5FileAttached
-- 2022-10-17T14:33:48.345Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545167,0,TO_TIMESTAMP('2022-10-17 17:33:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Keine Camt5-Datei angehängt!','E',TO_TIMESTAMP('2022-10-17 17:33:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment.NoCamt5FileAttached')
;

-- 2022-10-17T14:33:48.348Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545167 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment.NoCamt5FileAttached
-- 2022-10-17T14:33:53.986Z
UPDATE AD_Message_Trl SET MsgText='No Camt5 file attached!',Updated=TO_TIMESTAMP('2022-10-17 17:33:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545167
;

-- Name: AD_AttachmentEntry_for_C_Import_Camt53_File
-- 2022-10-17T14:53:34.956Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540605,'AD_AttachmentEntry_ID IN ( select r.AD_AttachmentEntry_ID from AD_Attachment_MultiRef r where r.AD_Table_ID=get_Table_ID(''C_Import_Camt53_File'') and r.Record_ID=@C_Import_Camt53_File_ID@ )',TO_TIMESTAMP('2022-10-17 17:53:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_AttachmentEntry_for_C_Import_Camt53_File','S',TO_TIMESTAMP('2022-10-17 17:53:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Process: C_BankStatement_Camt53_ImportAttachment(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment)
-- ParameterName: AD_AttachmentEntry_ID
-- 2022-10-17T14:54:22.099Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540605,Updated=TO_TIMESTAMP('2022-10-17 17:54:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542328
;

-- Field: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> Imported timestamp
-- Column: C_Import_Camt53_File.Imported
-- 2022-10-17T14:55:45.422Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-10-17 17:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707796
;

-- Field: Camt53 import file(541623,D) -> Camt53 import file(546658,D) -> Verarbeitet
-- Column: C_Import_Camt53_File.Processed
-- 2022-10-17T14:56:03.720Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-10-17 17:56:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707797
;

-- Tab: Camt53 import file(541623,D) -> Camt53 import file
-- Table: C_Import_Camt53_File
-- 2022-10-17T14:56:41.067Z
UPDATE AD_Tab SET ReadOnlyLogic='Processed=''Y''',Updated=TO_TIMESTAMP('2022-10-17 17:56:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546658
;

-- Value: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment.SelectedRecordIsProcessed
-- 2022-10-17T15:33:14.912Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545168,0,TO_TIMESTAMP('2022-10-17 18:33:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ausgewählter Datensatz wird bereits bearbeitet!','I',TO_TIMESTAMP('2022-10-17 18:33:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment.SelectedRecordIsProcessed')
;

-- 2022-10-17T15:33:14.918Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545168 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_ImportAttachment.SelectedRecordIsProcessed
-- 2022-10-17T15:33:23.228Z
UPDATE AD_Message_Trl SET MsgText='Selected record is already processed!',Updated=TO_TIMESTAMP('2022-10-17 18:33:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545168
;

-- Table: C_BankStatement_Import_File
-- 2022-10-17T16:41:59.405Z
UPDATE AD_Table SET Name='Bank Statement Import-File', TableName='C_BankStatement_Import_File',Updated=TO_TIMESTAMP('2022-10-17 19:41:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542246
;

-- 2022-10-17T16:41:59.420Z
UPDATE AD_Sequence SET Name='C_BankStatement_Import_File',Updated=TO_TIMESTAMP('2022-10-17 19:41:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=556036
;

-- 2022-10-17T16:41:59.428Z
ALTER SEQUENCE C_Import_Camt53_File_SEQ RENAME TO C_BankStatement_Import_File_SEQ
;

alter table c_import_camt53_file rename to C_BankStatement_Import_File
;-- 2022-10-17T16:44:37.005Z
UPDATE AD_Element SET ColumnName='C_BankStatement_Import_File_ID',Updated=TO_TIMESTAMP('2022-10-17 19:44:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581559
;

-- 2022-10-17T16:44:37.007Z
UPDATE AD_Column SET ColumnName='C_BankStatement_Import_File_ID' WHERE AD_Element_ID=581559
;

-- 2022-10-17T16:44:37.010Z
UPDATE AD_Process_Para SET ColumnName='C_BankStatement_Import_File_ID' WHERE AD_Element_ID=581559
;

-- 2022-10-17T16:44:37.015Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581559,'de_DE') 
;

-- Element: C_BankStatement_Import_File_ID
-- 2022-10-17T16:45:06.435Z
UPDATE AD_Element_Trl SET Name='Bank Statement Import-File', PrintName='Bank Statement Import-File',Updated=TO_TIMESTAMP('2022-10-17 19:45:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581559 AND AD_Language='de_CH'
;

-- 2022-10-17T16:45:06.437Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581559,'de_CH') 
;

-- Element: C_BankStatement_Import_File_ID
-- 2022-10-17T16:45:13.272Z
UPDATE AD_Element_Trl SET Name='Bank Statement Import-File', PrintName='Bank Statement Import-File',Updated=TO_TIMESTAMP('2022-10-17 19:45:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581559 AND AD_Language='de_DE'
;

-- 2022-10-17T16:45:13.274Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581559,'de_DE') 
;

-- 2022-10-17T16:45:13.278Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581559,'de_DE') 
;

-- Element: C_BankStatement_Import_File_ID
-- 2022-10-17T16:45:15.921Z
UPDATE AD_Element_Trl SET Name='Bank Statement Import-File', PrintName='Bank Statement Import-File',Updated=TO_TIMESTAMP('2022-10-17 19:45:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581559 AND AD_Language='en_US'
;

-- 2022-10-17T16:45:15.922Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581559,'en_US') 
;

-- Element: C_BankStatement_Import_File_ID
-- 2022-10-17T16:45:20.306Z
UPDATE AD_Element_Trl SET Name='Bank Statement Import-File', PrintName='Bank Statement Import-File',Updated=TO_TIMESTAMP('2022-10-17 19:45:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581559 AND AD_Language='nl_NL'
;

-- 2022-10-17T16:45:20.307Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581559,'nl_NL') 
;


alter table C_BankStatement_Import_File rename c_import_camt53_file_id to C_BankStatement_Import_File_ID
;

-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.NoStatementImported
-- 2022-10-17T17:48:56.131Z
UPDATE AD_Message SET Value='de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.NoStatementImported',Updated=TO_TIMESTAMP('2022-10-17 20:48:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545163
;

-- Value: dede.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.NoCamt5FileAttached
-- 2022-10-17T17:49:21.651Z
UPDATE AD_Message SET Value='dede.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.NoCamt5FileAttached',Updated=TO_TIMESTAMP('2022-10-17 20:49:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545167
;

-- Value: dede.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.NoCamt5FileAttached
-- 2022-10-17T17:51:37.260Z
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=545167
;

-- 2022-10-17T17:51:37.272Z
DELETE FROM AD_Message WHERE AD_Message_ID=545167
;

-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.SelectedRecordIsProcessed
-- 2022-10-17T17:52:26.055Z
UPDATE AD_Message SET Value='de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.SelectedRecordIsProcessed',Updated=TO_TIMESTAMP('2022-10-17 20:52:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545168
;

-- Value: de.metas.banking.camt53.wrapper.NoBatchBankToCustomerStatementV02Wrapper.BatchTransactionsNotSupported
-- 2022-10-17T17:53:36.651Z
UPDATE AD_Message SET Value='de.metas.banking.camt53.wrapper.NoBatchBankToCustomerStatementV02Wrapper.BatchTransactionsNotSupported',Updated=TO_TIMESTAMP('2022-10-17 20:53:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545164
;

-- Value: C_BankStatement_Camt53_ImportAttachment
-- Classname: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment
-- 2022-10-17T18:43:10.885Z
UPDATE AD_Process SET Classname='de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment',Updated=TO_TIMESTAMP('2022-10-17 21:43:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585129
;
