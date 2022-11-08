-- Table: C_BankStatement_Import_File_Log
-- 2022-10-28T11:00:42.084Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542251,'N',TO_TIMESTAMP('2022-10-28 14:00:40','YYYY-MM-DD HH24:MI:SS'),100,'U','N','Y','N','N','N','N','N','N','N','N',0,'C_BankStatement_Import_File_Log','NP','L','C_BankStatement_Import_File_Log','DTI',TO_TIMESTAMP('2022-10-28 14:00:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-28T11:00:42.099Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542251 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-10-28T11:00:42.235Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556043,TO_TIMESTAMP('2022-10-28 14:00:42','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_BankStatement_Import_File_Log',1,'Y','N','Y','Y','C_BankStatement_Import_File_Log','N',1000000,TO_TIMESTAMP('2022-10-28 14:00:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-28T11:00:42.308Z
CREATE SEQUENCE C_BANKSTATEMENT_IMPORT_FILE_LOG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_BankStatement_Import_File_Log.AD_Client_ID
-- 2022-10-28T11:00:53.040Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584835,102,0,19,542251,'AD_Client_ID',TO_TIMESTAMP('2022-10-28 14:00:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','U',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-10-28 14:00:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-28T11:00:53.049Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584835 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-28T11:00:53.101Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: C_BankStatement_Import_File_Log.AD_Org_ID
-- 2022-10-28T11:00:56.038Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584836,113,0,30,542251,'AD_Org_ID',TO_TIMESTAMP('2022-10-28 14:00:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','U',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-10-28 14:00:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-28T11:00:56.041Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584836 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-28T11:00:56.044Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: C_BankStatement_Import_File_Log.Created
-- 2022-10-28T11:00:57.026Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584837,245,0,16,542251,'Created',TO_TIMESTAMP('2022-10-28 14:00:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','U',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-10-28 14:00:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-28T11:00:57.028Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584837 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-28T11:00:57.030Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: C_BankStatement_Import_File_Log.CreatedBy
-- 2022-10-28T11:00:58.100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584838,246,0,18,110,542251,'CreatedBy',TO_TIMESTAMP('2022-10-28 14:00:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','U',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-10-28 14:00:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-28T11:00:58.103Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584838 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-28T11:00:58.107Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: C_BankStatement_Import_File_Log.IsActive
-- 2022-10-28T11:00:59.042Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584839,348,0,20,542251,'IsActive',TO_TIMESTAMP('2022-10-28 14:00:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','U',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-10-28 14:00:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-28T11:00:59.061Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584839 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-28T11:00:59.066Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: C_BankStatement_Import_File_Log.Updated
-- 2022-10-28T11:01:00.235Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584840,607,0,16,542251,'Updated',TO_TIMESTAMP('2022-10-28 14:00:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','U',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-10-28 14:00:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-28T11:01:00.237Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584840 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-28T11:01:00.239Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: C_BankStatement_Import_File_Log.UpdatedBy
-- 2022-10-28T11:01:01.299Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584841,608,0,18,110,542251,'UpdatedBy',TO_TIMESTAMP('2022-10-28 14:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','U',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-10-28 14:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-28T11:01:01.301Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584841 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-28T11:01:01.303Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2022-10-28T11:01:02.082Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581627,0,'C_BankStatement_Import_File_Log_ID',TO_TIMESTAMP('2022-10-28 14:01:01','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','C_BankStatement_Import_File_Log','C_BankStatement_Import_File_Log',TO_TIMESTAMP('2022-10-28 14:01:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-28T11:01:02.087Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581627 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_BankStatement_Import_File_Log.C_BankStatement_Import_File_Log_ID
-- 2022-10-28T11:01:02.886Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584842,581627,0,13,542251,'C_BankStatement_Import_File_Log_ID',TO_TIMESTAMP('2022-10-28 14:01:01','YYYY-MM-DD HH24:MI:SS'),100,'N','U',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','C_BankStatement_Import_File_Log',0,0,TO_TIMESTAMP('2022-10-28 14:01:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-28T11:01:02.888Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584842 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-28T11:01:02.891Z
/* DDL */  select update_Column_Translation_From_AD_Element(581627)
;

-- Column: C_BankStatement_Import_File_Log.C_BankStatement_Import_File_ID
-- 2022-10-28T11:01:45.790Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584843,581559,0,19,542251,'C_BankStatement_Import_File_ID',TO_TIMESTAMP('2022-10-28 14:01:45','YYYY-MM-DD HH24:MI:SS'),100,'N','U',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Kontoauszug Import-Datei',0,0,TO_TIMESTAMP('2022-10-28 14:01:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-28T11:01:45.798Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584843 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-28T11:01:45.804Z
/* DDL */  select update_Column_Translation_From_AD_Element(581559)
;

-- Column: C_BankStatement_Import_File_Log.AD_Issue_ID
-- 2022-10-28T11:02:12.648Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584844,2887,0,19,542251,'AD_Issue_ID',TO_TIMESTAMP('2022-10-28 14:02:12','YYYY-MM-DD HH24:MI:SS'),100,'N','','U',0,10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Probleme',0,0,TO_TIMESTAMP('2022-10-28 14:02:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-28T11:02:12.653Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584844 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-28T11:02:12.657Z
/* DDL */  select update_Column_Translation_From_AD_Element(2887)
;

-- Column: C_BankStatement_Import_File_Log.Logmessage
-- 2022-10-28T11:02:57.925Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584845,579125,0,10,542251,'Logmessage',TO_TIMESTAMP('2022-10-28 14:02:57','YYYY-MM-DD HH24:MI:SS'),100,'N','U',0,1000000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Log-Nachricht',0,0,TO_TIMESTAMP('2022-10-28 14:02:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-28T11:02:57.929Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584845 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-28T11:02:57.933Z
/* DDL */  select update_Column_Translation_From_AD_Element(579125)
;

-- 2022-10-28T11:05:41.567Z
/* DDL */ CREATE TABLE public.C_BankStatement_Import_File_Log (AD_Client_ID NUMERIC(10) NOT NULL, AD_Issue_ID NUMERIC(10), AD_Org_ID NUMERIC(10) NOT NULL, C_BankStatement_Import_File_ID NUMERIC(10) NOT NULL, C_BankStatement_Import_File_Log_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Logmessage VARCHAR(1000000), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADIssue_CBankStatementImportFileLog FOREIGN KEY (AD_Issue_ID) REFERENCES public.AD_Issue DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CBankStatementImportFile_CBankStatementImportFileLog FOREIGN KEY (C_BankStatement_Import_File_ID) REFERENCES public.C_BankStatement_Import_File DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_BankStatement_Import_File_Log_Key PRIMARY KEY (C_BankStatement_Import_File_Log_ID))
;

-- Table: C_BankStatement_Import_File_Log
-- 2022-10-28T11:08:14.889Z
UPDATE AD_Table SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2022-10-28 14:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542251
;



-- Tab: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log
-- Table: C_BankStatement_Import_File_Log
-- 2022-10-28T14:32:53.090Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584843,581627,0,546662,542251,541623,'Y',TO_TIMESTAMP('2022-10-28 17:32:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','N','N','A','C_BankStatement_Import_File_Log','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'C_BankStatement_Import_File_Log',584724,'N',20,1,TO_TIMESTAMP('2022-10-28 17:32:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-28T14:32:53.105Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546662 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-10-28T14:32:53.105Z
/* DDL */  select update_tab_translation_from_ad_element(581627)
;

-- 2022-10-28T14:32:53.199Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546662)
;

-- Field: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking) -> Mandant
-- Column: C_BankStatement_Import_File_Log.AD_Client_ID
-- 2022-10-28T14:48:28.031Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584835,707909,0,546662,TO_TIMESTAMP('2022-10-28 17:48:27','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.banking','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-10-28 17:48:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-28T14:48:28.040Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707909 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-28T14:48:28.049Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2022-10-28T14:48:29.966Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707909
;

-- 2022-10-28T14:48:29.975Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707909)
;

-- Field: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking) -> Sektion
-- Column: C_BankStatement_Import_File_Log.AD_Org_ID
-- 2022-10-28T14:48:30.093Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584836,707910,0,546662,TO_TIMESTAMP('2022-10-28 17:48:29','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.banking','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-10-28 17:48:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-28T14:48:30.095Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707910 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-28T14:48:30.097Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2022-10-28T14:48:30.659Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707910
;

-- 2022-10-28T14:48:30.660Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707910)
;

-- Field: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking) -> Aktiv
-- Column: C_BankStatement_Import_File_Log.IsActive
-- 2022-10-28T14:48:30.759Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584839,707911,0,546662,TO_TIMESTAMP('2022-10-28 17:48:30','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.banking','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-10-28 17:48:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-28T14:48:30.761Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707911 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-28T14:48:30.762Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2022-10-28T14:48:32.281Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707911
;

-- 2022-10-28T14:48:32.282Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707911)
;

-- Field: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking) -> C_BankStatement_Import_File_Log
-- Column: C_BankStatement_Import_File_Log.C_BankStatement_Import_File_Log_ID
-- 2022-10-28T14:48:32.380Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584842,707912,0,546662,TO_TIMESTAMP('2022-10-28 17:48:32','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.banking','Y','N','N','N','N','N','N','N','C_BankStatement_Import_File_Log',TO_TIMESTAMP('2022-10-28 17:48:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-28T14:48:32.383Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707912 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-28T14:48:32.384Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581627)
;

-- 2022-10-28T14:48:32.455Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707912
;

-- 2022-10-28T14:48:32.456Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707912)
;

-- Field: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking) -> Kontoauszug Import-Datei
-- Column: C_BankStatement_Import_File_Log.C_BankStatement_Import_File_ID
-- 2022-10-28T14:48:32.543Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584843,707913,0,546662,TO_TIMESTAMP('2022-10-28 17:48:32','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.banking','Y','N','N','N','N','N','N','N','Kontoauszug Import-Datei',TO_TIMESTAMP('2022-10-28 17:48:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-28T14:48:32.546Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707913 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-28T14:48:32.547Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581559)
;

-- 2022-10-28T14:48:32.623Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707913
;

-- 2022-10-28T14:48:32.624Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707913)
;

-- Field: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking) -> Probleme
-- Column: C_BankStatement_Import_File_Log.AD_Issue_ID
-- 2022-10-28T14:48:32.727Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584844,707914,0,546662,TO_TIMESTAMP('2022-10-28 17:48:32','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.banking','','Y','N','N','N','N','N','N','N','Probleme',TO_TIMESTAMP('2022-10-28 17:48:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-28T14:48:32.728Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707914 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-28T14:48:32.729Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887)
;

-- 2022-10-28T14:48:33.915Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707914
;

-- 2022-10-28T14:48:33.915Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707914)
;

-- Field: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking) -> Log-Nachricht
-- Column: C_BankStatement_Import_File_Log.Logmessage
-- 2022-10-28T14:48:34.005Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584845,707915,0,546662,TO_TIMESTAMP('2022-10-28 17:48:33','YYYY-MM-DD HH24:MI:SS'),100,1000000,'de.metas.banking','Y','N','N','N','N','N','N','N','Log-Nachricht',TO_TIMESTAMP('2022-10-28 17:48:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-28T14:48:34.006Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707915 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-28T14:48:34.007Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579125)
;

-- 2022-10-28T14:48:34.213Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707915
;

-- 2022-10-28T14:48:34.213Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707915)
;

-- 2022-10-28T14:48:44.370Z
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- Tab: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking)
-- UI Section: main
-- 2022-10-28T14:48:53.027Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546662,545286,TO_TIMESTAMP('2022-10-28 17:48:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-10-28 17:48:52','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-10-28T14:48:53.029Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545286 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking) -> main
-- UI Column: 10
-- 2022-10-28T14:48:53.136Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546431,545286,TO_TIMESTAMP('2022-10-28 17:48:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-10-28 17:48:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking) -> main -> 10
-- UI Element Group: default
-- 2022-10-28T14:48:53.245Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546431,549988,TO_TIMESTAMP('2022-10-28 17:48:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-10-28 17:48:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking) -> main -> 10 -> default.C_BankStatement_Import_File_Log
-- Column: C_BankStatement_Import_File_Log.C_BankStatement_Import_File_Log_ID
-- 2022-10-28T14:49:17.179Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707912,0,546662,613346,549988,'F',TO_TIMESTAMP('2022-10-28 17:49:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_BankStatement_Import_File_Log',10,0,0,TO_TIMESTAMP('2022-10-28 17:49:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking) -> main -> 10 -> default.Log-Nachricht
-- Column: C_BankStatement_Import_File_Log.Logmessage
-- 2022-10-28T14:49:32.425Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707915,0,546662,613347,549988,'F',TO_TIMESTAMP('2022-10-28 17:49:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Log-Nachricht',20,0,0,TO_TIMESTAMP('2022-10-28 17:49:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking) -> main -> 10 -> default.Sektion
-- Column: C_BankStatement_Import_File_Log.AD_Org_ID
-- 2022-10-28T14:49:46.994Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707910,0,546662,613348,549988,'F',TO_TIMESTAMP('2022-10-28 17:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',30,0,0,TO_TIMESTAMP('2022-10-28 17:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Kontoauszug Import-Datei(541623,D) -> C_BankStatement_Import_File_Log(546662,de.metas.banking) -> main -> 10 -> default.Mandant
-- Column: C_BankStatement_Import_File_Log.AD_Client_ID
-- 2022-10-28T14:49:52.471Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707909,0,546662,613349,549988,'F',TO_TIMESTAMP('2022-10-28 17:49:52','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',40,0,0,TO_TIMESTAMP('2022-10-28 17:49:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Element: C_BankStatement_Import_File_Log_ID
-- 2022-10-28T14:51:24.336Z
UPDATE AD_Element_Trl SET Name='Log', PrintName='Log',Updated=TO_TIMESTAMP('2022-10-28 17:51:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581627 AND AD_Language='de_CH'
;

-- 2022-10-28T14:51:24.342Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581627,'de_CH')
;

-- Element: C_BankStatement_Import_File_Log_ID
-- 2022-10-28T14:51:28.356Z
UPDATE AD_Element_Trl SET Name='Log', PrintName='Log',Updated=TO_TIMESTAMP('2022-10-28 17:51:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581627 AND AD_Language='nl_NL'
;

-- 2022-10-28T14:51:28.358Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581627,'nl_NL')
;

-- Element: C_BankStatement_Import_File_Log_ID
-- 2022-10-28T14:51:30.798Z
UPDATE AD_Element_Trl SET Name='Log', PrintName='Log',Updated=TO_TIMESTAMP('2022-10-28 17:51:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581627 AND AD_Language='en_US'
;

-- 2022-10-28T14:51:30.799Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581627,'en_US')
;

-- Element: C_BankStatement_Import_File_Log_ID
-- 2022-10-28T14:51:33.590Z
UPDATE AD_Element_Trl SET Name='Log', PrintName='Log',Updated=TO_TIMESTAMP('2022-10-28 17:51:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581627 AND AD_Language='de_DE'
;

-- 2022-10-28T14:51:33.592Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581627,'de_DE')
;

-- 2022-10-28T14:51:33.595Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581627,'de_DE')
;


