-- Run mode: SWING_CLIENT

-- 2025-12-04T14:55:23.923Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584329,0,'IsAutoSendDocument',TO_TIMESTAMP('2025-12-04 14:55:23.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.document.archive','Y','Beleg autom. versenden','Beleg autom. versenden',TO_TIMESTAMP('2025-12-04 14:55:23.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-04T14:55:23.949Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584329 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsAutoSendDocument
-- 2025-12-04T14:56:23.885Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Send document automatically',Updated=TO_TIMESTAMP('2025-12-04 14:56:23.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584329 AND AD_Language='en_US'
;

-- 2025-12-04T14:56:23.887Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-04T14:56:24.078Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584329,'en_US')
;

-- Element: IsAutoSendDocument
-- 2025-12-04T14:56:24.732Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-04 14:56:24.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584329 AND AD_Language='de_CH'
;

-- 2025-12-04T14:56:24.734Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584329,'de_CH')
;

-- Element: IsAutoSendDocument
-- 2025-12-04T14:56:25.965Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-04 14:56:25.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584329 AND AD_Language='de_DE'
;

-- 2025-12-04T14:56:25.968Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584329,'de_DE')
;

-- 2025-12-04T14:56:25.977Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584329,'de_DE')
;

-- Column: C_Doc_Outbound_Config.IsDirectEnqueue
-- 2025-12-04T14:57:21.973Z
UPDATE AD_Column SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2025-12-04 14:57:21.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549041
;

-- Column: C_Doc_Outbound_Config.IsDirectProcessQueueItem
-- 2025-12-04T14:57:41.458Z
UPDATE AD_Column SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2025-12-04 14:57:41.458000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=552771
;

-- Column: C_Doc_Outbound_Config.IsAutoSendDocument
-- 2025-12-04T14:58:00.200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591669,584329,0,20,540434,'XX','IsAutoSendDocument',TO_TIMESTAMP('2025-12-04 14:58:00.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.document.archive',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Beleg autom. versenden',0,0,TO_TIMESTAMP('2025-12-04 14:58:00.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-04T14:58:00.204Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591669 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-04T14:58:00.328Z
/* DDL */  select update_Column_Translation_From_AD_Element(584329)
;

-- 2025-12-04T14:58:01.439Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Config','ALTER TABLE public.C_Doc_Outbound_Config ADD COLUMN IsAutoSendDocument CHAR(1) DEFAULT ''N'' CHECK (IsAutoSendDocument IN (''Y'',''N'')) NOT NULL')
;

CREATE UNIQUE INDEX IF NOT EXISTS c_doc_outbound_config_ad_table_id_docbasetype_ad_org_id_uq
    ON C_Doc_Outbound_Config (AD_Table_ID, AD_Org_ID, DocBaseType) WHERE IsActive='Y';

CREATE UNIQUE INDEX IF NOT EXISTS c_doc_outbound_config_ad_table_id_ad_org_id_uq
    ON C_Doc_Outbound_Config (AD_Table_ID, AD_Org_ID) WHERE IsActive='Y' AND DocBaseType IS NULL;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig(540477,de.metas.document.archive) -> Beleg autom. versenden
-- Column: C_Doc_Outbound_Config.IsAutoSendDocument
-- 2025-12-04T15:24:13.732Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591669,758541,0,540477,TO_TIMESTAMP('2025-12-04 15:24:13.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.document.archive','Y','N','N','N','N','N','N','N','Beleg autom. versenden',TO_TIMESTAMP('2025-12-04 15:24:13.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-04T15:24:13.738Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758541 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-04T15:24:13.745Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584329)
;

-- 2025-12-04T15:24:13.759Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758541
;

-- 2025-12-04T15:24:13.768Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758541)
;

-- UI Element: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig(540477,de.metas.document.archive) -> main -> 20 -> flags.Beleg autom. versenden
-- Column: C_Doc_Outbound_Config.IsAutoSendDocument
-- 2025-12-04T15:24:58.198Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758541,0,540477,540349,639736,'F',TO_TIMESTAMP('2025-12-04 15:24:58.068000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Beleg autom. versenden',40,0,0,TO_TIMESTAMP('2025-12-04 15:24:58.068000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig(540477,de.metas.document.archive) -> main -> 20 -> flags.Beleg autom. versenden
-- Column: C_Doc_Outbound_Config.IsAutoSendDocument
-- 2025-12-04T15:25:09.513Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-12-04 15:25:09.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=639736
;

-- UI Element: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig(540477,de.metas.document.archive) -> main -> 10 -> doctype.CC Pfad
-- Column: C_Doc_Outbound_Config.CCPath
-- 2025-12-04T15:25:09.519Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-12-04 15:25:09.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543680
;

-- UI Element: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig(540477,de.metas.document.archive) -> main -> 20 -> org.Sektion
-- Column: C_Doc_Outbound_Config.AD_Org_ID
-- 2025-12-04T15:25:09.525Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-12-04 15:25:09.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543684
;
