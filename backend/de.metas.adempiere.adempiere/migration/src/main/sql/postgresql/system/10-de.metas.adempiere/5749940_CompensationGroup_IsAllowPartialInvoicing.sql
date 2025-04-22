-- Run mode: SWING_CLIENT

-- 2025-03-25T06:49:44.984Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583545,0,'IsAllowSeparateInvoicing',TO_TIMESTAMP('2025-03-25 06:49:44.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Getrennt fakturieren','Getrennt fakturieren',TO_TIMESTAMP('2025-03-25 06:49:44.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-25T06:49:44.993Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583545 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-03-25T06:51:09.586Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740367
;

-- Field: Kompensationsgruppe Schema(540415,de.metas.order) -> Schema-Zeilen(544005,de.metas.order) -> Rechnungsstellung überspringen
-- Column: C_CompensationGroup_Schema_TemplateLine.IsSkipInvoicing
-- 2025-03-25T06:51:09.673Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=740367
;

-- 2025-03-25T06:51:09.683Z
DELETE FROM AD_Field WHERE AD_Field_ID=740367
;

-- 2025-03-25T06:51:09.852Z
/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_Schema_TemplateLine','ALTER TABLE C_CompensationGroup_Schema_TemplateLine DROP COLUMN IF EXISTS IsSkipInvoicing')
;

-- Column: C_CompensationGroup_Schema_TemplateLine.IsSkipInvoicing
-- 2025-03-25T06:51:09.911Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=589759
;

-- 2025-03-25T06:51:09.916Z
DELETE FROM AD_Column WHERE AD_Column_ID=589759
;

-- Column: C_CompensationGroup_Schema_TemplateLine.IsAllowSeparateInvoicing
-- 2025-03-25T06:52:44.822Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589790,583545,0,20,541679,'XX','IsAllowSeparateInvoicing',TO_TIMESTAMP('2025-03-25 06:52:44.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.order',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Getrennt fakturieren',0,0,TO_TIMESTAMP('2025-03-25 06:52:44.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-03-25T06:52:44.824Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589790 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-03-25T06:52:44.925Z
/* DDL */  select update_Column_Translation_From_AD_Element(583545)
;

-- 2025-03-25T06:53:11.224Z
/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_Schema_TemplateLine','ALTER TABLE public.C_CompensationGroup_Schema_TemplateLine ADD COLUMN IsAllowSeparateInvoicing CHAR(1) DEFAULT ''N'' CHECK (IsAllowSeparateInvoicing IN (''Y'',''N'')) NOT NULL')
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Rechnungsstellung überspringen
-- Column: C_OrderLine.IsSkipInvoicing
-- 2025-03-25T06:55:53.429Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=630673
;

-- 2025-03-25T06:55:53.431Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740368
;

-- Field: Auftrag(143,D) -> Auftragsposition(187,D) -> Rechnungsstellung überspringen
-- Column: C_OrderLine.IsSkipInvoicing
-- 2025-03-25T06:55:53.434Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=740368
;

-- 2025-03-25T06:55:53.439Z
DELETE FROM AD_Field WHERE AD_Field_ID=740368
;

-- 2025-03-25T06:55:53.441Z
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE C_OrderLine DROP COLUMN IF EXISTS IsSkipInvoicing')
;

-- Column: C_OrderLine.IsSkipInvoicing
-- 2025-03-25T06:55:54.398Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=589758
;

-- 2025-03-25T06:55:54.404Z
DELETE FROM AD_Column WHERE AD_Column_ID=589758
;

-- Column: C_OrderLine.IsAllowSeparateInvoicing
-- 2025-03-25T07:00:12.961Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589791,583545,0,20,260,'XX','IsAllowSeparateInvoicing',TO_TIMESTAMP('2025-03-25 07:00:12.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Getrennt fakturieren',0,0,TO_TIMESTAMP('2025-03-25 07:00:12.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-03-25T07:00:12.963Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589791 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-03-25T07:00:13.077Z
/* DDL */  select update_Column_Translation_From_AD_Element(583545)
;

-- 2025-03-25T07:00:41.209Z
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN IsAllowSeparateInvoicing CHAR(1) DEFAULT ''N'' CHECK (IsAllowSeparateInvoicing IN (''Y'',''N'')) NOT NULL')
;

-- Field: Kompensationsgruppe Schema(540415,de.metas.order) -> Schema-Zeilen(544005,de.metas.order) -> Getrennt fakturieren
-- Column: C_CompensationGroup_Schema_TemplateLine.IsAllowSeparateInvoicing
-- 2025-03-25T07:02:42.165Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589790,740710,0,544005,0,TO_TIMESTAMP('2025-03-25 07:02:41.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Getrennt fakturieren',0,0,70,0,1,1,TO_TIMESTAMP('2025-03-25 07:02:41.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-25T07:02:42.168Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740710 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-25T07:02:42.171Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583545)
;

-- 2025-03-25T07:02:42.174Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740710
;

-- 2025-03-25T07:02:42.175Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740710)
;

-- Field: Kompensationsgruppe Schema(540415,de.metas.order) -> Schema-Zeilen(544005,de.metas.order) -> Getrennt fakturieren
-- Column: C_CompensationGroup_Schema_TemplateLine.IsAllowSeparateInvoicing
-- 2025-03-25T07:03:16.503Z
UPDATE AD_Field SET DisplayLength=1, SeqNo=45, SeqNoGrid=45, SortNo=NULL,Updated=TO_TIMESTAMP('2025-03-25 07:03:16.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740710
;

-- Column: C_Invoice_Candidate.IsAllowSeparateInvoicing
-- 2025-03-25T07:33:51.305Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589792,583545,0,20,540270,'XX','IsAllowSeparateInvoicing',TO_TIMESTAMP('2025-03-25 07:33:51.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.invoicecandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Getrennt fakturieren',0,0,TO_TIMESTAMP('2025-03-25 07:33:51.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-03-25T07:33:51.307Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589792 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-03-25T07:33:51.310Z
/* DDL */  select update_Column_Translation_From_AD_Element(583545)
;

-- 2025-03-25T07:34:31.211Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN IsAllowSeparateInvoicing CHAR(1) DEFAULT ''N'' CHECK (IsAllowSeparateInvoicing IN (''Y'',''N'')) NOT NULL')
;

-- Element: IsAllowSeparateInvoicing
-- 2025-03-25T09:22:14.904Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Separate invoicing', PrintName='Separate invoicing',Updated=TO_TIMESTAMP('2025-03-25 09:22:14.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583545 AND AD_Language='en_US'
;

-- 2025-03-25T09:22:14.905Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-03-25T09:22:19.420Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583545,'en_US')
;

