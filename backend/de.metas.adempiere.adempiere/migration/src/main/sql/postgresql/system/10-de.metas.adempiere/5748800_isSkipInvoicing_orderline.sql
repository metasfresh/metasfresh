-- Run mode: SWING_CLIENT

-- 2025-03-10T12:02:31.941Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583524,0,'IsSkipInvoicing',TO_TIMESTAMP('2025-03-10 12:02:31.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Rechnungsstellung überspringen','Rechnungsstellung überspringen',TO_TIMESTAMP('2025-03-10 12:02:31.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-10T12:02:31.950Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583524 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsSkipInvoicing
-- 2025-03-10T12:02:51.032Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Skip invoicing', PrintName='Skip invoicing',Updated=TO_TIMESTAMP('2025-03-10 12:02:51.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583524 AND AD_Language='en_US'
;

-- 2025-03-10T12:02:51.034Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-03-10T12:02:56.870Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583524,'en_US')
;

-- Column: C_OrderLine.IsSkipInvoicing
-- 2025-03-10T12:03:45.107Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589758,583524,0,20,260,'XX','IsSkipInvoicing',TO_TIMESTAMP('2025-03-10 12:03:44.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungsstellung überspringen',0,0,TO_TIMESTAMP('2025-03-10 12:03:44.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-03-10T12:03:45.109Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589758 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-03-10T12:03:45.113Z
/* DDL */  select update_Column_Translation_From_AD_Element(583524)
;

-- 2025-03-10T12:04:07.809Z
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN IsSkipInvoicing CHAR(1) DEFAULT ''N'' CHECK (IsSkipInvoicing IN (''Y'',''N'')) NOT NULL')
;

-- Column: C_CompensationGroup_Schema_TemplateLine.IsSkipInvoicing
-- 2025-03-10T12:06:03.674Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589759,583524,0,20,541679,'XX','IsSkipInvoicing',TO_TIMESTAMP('2025-03-10 12:06:03.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.order',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungsstellung überspringen',0,0,TO_TIMESTAMP('2025-03-10 12:06:03.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-03-10T12:06:03.676Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589759 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-03-10T12:06:03.776Z
/* DDL */  select update_Column_Translation_From_AD_Element(583524)
;

-- 2025-03-10T12:06:20.308Z
/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_Schema_TemplateLine','ALTER TABLE public.C_CompensationGroup_Schema_TemplateLine ADD COLUMN IsSkipInvoicing CHAR(1) DEFAULT ''N'' CHECK (IsSkipInvoicing IN (''Y'',''N'')) NOT NULL')
;

-- Field: Kompensationsgruppe Schema(540415,de.metas.order) -> Schema-Zeilen(544005,de.metas.order) -> Rechnungsstellung überspringen
-- Column: C_CompensationGroup_Schema_TemplateLine.IsSkipInvoicing
-- 2025-03-10T12:07:05.353Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589759,740367,0,544005,0,TO_TIMESTAMP('2025-03-10 12:07:05.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Rechnungsstellung überspringen',0,0,70,0,1,1,TO_TIMESTAMP('2025-03-10 12:07:05.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-10T12:07:05.357Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740367 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-10T12:07:05.359Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583524)
;

-- 2025-03-10T12:07:05.369Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740367
;

-- 2025-03-10T12:07:05.371Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740367)
;

-- Field: Kompensationsgruppe Schema(540415,de.metas.order) -> Schema-Zeilen(544005,de.metas.order) -> Rechnungsstellung überspringen
-- Column: C_CompensationGroup_Schema_TemplateLine.IsSkipInvoicing
-- 2025-03-10T12:08:01.223Z
UPDATE AD_Field SET DisplayLength=10, SeqNo=45, SeqNoGrid=45,Updated=TO_TIMESTAMP('2025-03-10 12:08:01.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740367
;

-- Field: Auftrag(143,D) -> Auftragsposition(187,D) -> Rechnungsstellung überspringen
-- Column: C_OrderLine.IsSkipInvoicing
-- 2025-03-10T13:58:25.772Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589758,740368,0,187,0,TO_TIMESTAMP('2025-03-10 13:58:25.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Rechnungsstellung überspringen',0,0,370,0,1,1,TO_TIMESTAMP('2025-03-10 13:58:25.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-10T13:58:25.775Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740368 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-10T13:58:25.777Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583524)
;

-- 2025-03-10T13:58:25.784Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740368
;

-- 2025-03-10T13:58:25.787Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740368)
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Rechnungsstellung überspringen
-- Column: C_OrderLine.IsSkipInvoicing
-- 2025-03-10T14:00:52.181Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,740368,0,187,1000005,630673,'F',TO_TIMESTAMP('2025-03-10 14:00:52.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Rechnungsstellung überspringen',440,0,0,TO_TIMESTAMP('2025-03-10 14:00:52.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_CompensationGroup_Schema_TemplateLine.IsHideWhenPrinting
-- 2025-03-11T12:33:31.780Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589770,579540,0,20,541679,'XX','IsHideWhenPrinting',TO_TIMESTAMP('2025-03-11 12:33:31.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Beim Drucken ausblenden',0,0,TO_TIMESTAMP('2025-03-11 12:33:31.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-03-11T12:33:31.783Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589770 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-03-11T12:33:31.811Z
/* DDL */  select update_Column_Translation_From_AD_Element(579540)
;

-- 2025-03-11T12:33:34.742Z
/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_Schema_TemplateLine','ALTER TABLE public.C_CompensationGroup_Schema_TemplateLine ADD COLUMN IsHideWhenPrinting CHAR(1) DEFAULT ''N'' CHECK (IsHideWhenPrinting IN (''Y'',''N'')) NOT NULL')
;

-- Field: Kompensationsgruppe Schema(540415,de.metas.order) -> Schema-Zeilen(544005,de.metas.order) -> Beim Drucken ausblenden
-- Column: C_CompensationGroup_Schema_TemplateLine.IsHideWhenPrinting
-- 2025-03-11T12:38:25.709Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589770,740369,0,544005,0,TO_TIMESTAMP('2025-03-11 12:38:25.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Beim Drucken ausblenden',0,48,48,0,1,1,TO_TIMESTAMP('2025-03-11 12:38:25.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-11T12:38:25.711Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740369 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-11T12:38:25.714Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579540)
;

-- 2025-03-11T12:38:25.725Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740369
;

-- 2025-03-11T12:38:25.728Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740369)
;

