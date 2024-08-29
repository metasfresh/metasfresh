-- Run mode: SWING_CLIENT

-- 2024-08-28T16:13:01.028Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583232,0,'IsMainArchive',TO_TIMESTAMP('2024-08-28 19:13:00.61','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Main Archive','Main Archive',TO_TIMESTAMP('2024-08-28 19:13:00.61','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-28T16:13:01.103Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583232 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-08-28T16:13:21.539Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583233,0,'IsMainLog',TO_TIMESTAMP('2024-08-28 19:13:21.211','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Main Log','Main Log',TO_TIMESTAMP('2024-08-28 19:13:21.211','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-28T16:13:21.562Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583233 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Archive.IsMainArchive
-- 2024-08-28T16:13:34.388Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588933,583232,0,20,754,'IsMainArchive',TO_TIMESTAMP('2024-08-28 19:13:34.011','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Main Archive',0,0,TO_TIMESTAMP('2024-08-28 19:13:34.011','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-28T16:13:34.413Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588933 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-28T16:13:34.483Z
/* DDL */  select update_Column_Translation_From_AD_Element(583232)
;

-- Column: AD_Archive.IsMainArchive
-- 2024-08-28T16:13:41.252Z
UPDATE AD_Column SET DefaultValue='Y	',Updated=TO_TIMESTAMP('2024-08-28 19:13:41.252','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588933
;

-- Column: AD_Archive.IsMainArchive
-- 2024-08-28T16:13:59.935Z
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2024-08-28 19:13:59.935','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588933
;

-- 2024-08-28T16:14:03.682Z
/* DDL */ SELECT public.db_alter_table('AD_Archive','ALTER TABLE public.AD_Archive ADD COLUMN IsMainArchive CHAR(1) DEFAULT ''Y'' CHECK (IsMainArchive IN (''Y'',''N'')) NOT NULL')
;

-- Column: C_Doc_Outbound_Log.IsMainLog
-- 2024-08-28T16:14:49.926Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588934,583233,0,20,540453,'IsMainLog',TO_TIMESTAMP('2024-08-28 19:14:49.507','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','de.metas.document.archive',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Main Log',0,0,TO_TIMESTAMP('2024-08-28 19:14:49.507','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-28T16:14:49.953Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588934 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-28T16:14:50.005Z
/* DDL */  select update_Column_Translation_From_AD_Element(583233)
;

-- 2024-08-28T16:14:55.060Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Log','ALTER TABLE public.C_Doc_Outbound_Log ADD COLUMN IsMainLog CHAR(1) DEFAULT ''Y'' CHECK (IsMainLog IN (''Y'',''N'')) NOT NULL')
;

-- Column: AD_Archive.IsMainArchive
-- 2024-08-28T16:18:28.071Z
UPDATE AD_Column SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2024-08-28 19:18:28.071','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588933
;

-- 2024-08-29T07:20:27.331Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Log','ALTER TABLE C_Doc_Outbound_Log DROP COLUMN IF EXISTS IsMainLog')
;

-- Column: C_Doc_Outbound_Log.IsMainLog
-- 2024-08-29T07:20:27.502Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588934
;

-- 2024-08-29T07:20:27.643Z
DELETE FROM AD_Column WHERE AD_Column_ID=588934
;

-- Column: C_Doc_Outbound_Log.AD_Archive_ID
-- 2024-08-29T07:21:26.295Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588935,2671,0,19,540453,'AD_Archive_ID',TO_TIMESTAMP('2024-08-29 10:21:25.737','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Archiv für Belege und Berichte','de.metas.document.archive',0,10,'In Abhängigkeit des Grades der Archivierungsautomatik des Mandanten werden Dokumente und Berichte archiviert und stehen zur Ansicht zur Verfügung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Archiv',0,0,TO_TIMESTAMP('2024-08-29 10:21:25.737','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-29T07:21:26.323Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588935 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-29T07:21:26.376Z
/* DDL */  select update_Column_Translation_From_AD_Element(2671)
;

-- 2024-08-29T07:21:30.514Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Log','ALTER TABLE public.C_Doc_Outbound_Log ADD COLUMN AD_Archive_ID NUMERIC(10)')
;

-- 2024-08-29T07:21:30.543Z
ALTER TABLE C_Doc_Outbound_Log ADD CONSTRAINT ADArchive_CDocOutboundLog FOREIGN KEY (AD_Archive_ID) REFERENCES public.AD_Archive DEFERRABLE INITIALLY DEFERRED
;


DROP index c_doc_outbound_log_uc;
create unique index c_doc_outbound_log_uc
    on public.c_doc_outbound_log (record_id, ad_table_id, ad_archive_id)
    where (isactive = 'Y'::bpchar);

