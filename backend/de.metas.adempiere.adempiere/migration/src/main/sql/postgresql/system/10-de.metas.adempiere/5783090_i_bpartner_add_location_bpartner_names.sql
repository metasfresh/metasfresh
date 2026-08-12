-- Run mode: SWING_CLIENT

-- Column: I_BPartner.BPartnerName
-- 2026-01-08T07:59:36.958Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591824,543350,0,10,533,'XX','BPartnerName',TO_TIMESTAMP('2026-01-08 07:59:36.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Name Geschäftspartner',0,0,TO_TIMESTAMP('2026-01-08 07:59:36.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-01-08T07:59:36.970Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591824 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-01-08T07:59:36.992Z
/* DDL */  select update_Column_Translation_From_AD_Element(543350)
;

-- 2026-01-08T07:59:39.670Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN BPartnerName VARCHAR(255)')
;

-- Column: I_BPartner.BPartnerName2
-- 2026-01-08T07:59:55.024Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591825,584387,0,10,533,'XX','BPartnerName2',TO_TIMESTAMP('2026-01-08 07:59:54.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Abw. Firmenname 2',0,0,TO_TIMESTAMP('2026-01-08 07:59:54.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-01-08T07:59:55.027Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591825 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-01-08T07:59:55.106Z
/* DDL */  select update_Column_Translation_From_AD_Element(584387)
;

-- 2026-01-08T07:59:57.318Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN BPartnerName2 VARCHAR(255)')
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Name Geschäftspartner
-- Column: I_BPartner.BPartnerName
-- 2026-01-08T08:00:47.546Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591824,760976,0,441,TO_TIMESTAMP('2026-01-08 08:00:47.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Name Geschäftspartner',TO_TIMESTAMP('2026-01-08 08:00:47.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-08T08:00:47.551Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760976 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-08T08:00:47.558Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543350)
;

-- 2026-01-08T08:00:47.591Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760976
;

-- 2026-01-08T08:00:47.600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760976)
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Abw. Firmenname 2
-- Column: I_BPartner.BPartnerName2
-- 2026-01-08T08:00:58.139Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591825,760977,0,441,TO_TIMESTAMP('2026-01-08 08:00:57.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Abw. Firmenname 2',TO_TIMESTAMP('2026-01-08 08:00:57.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-08T08:00:58.144Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760977 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-08T08:00:58.152Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584387)
;

-- 2026-01-08T08:00:58.159Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760977
;

-- 2026-01-08T08:00:58.165Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760977)
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Abw. Firmenname
-- Column: I_BPartner.BPartnerName
-- 2026-01-08T08:01:35.908Z
UPDATE AD_Field SET AD_Name_ID=577370, Description=NULL, Help=NULL, Name='Abw. Firmenname',Updated=TO_TIMESTAMP('2026-01-08 08:01:35.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=760976
;

-- 2026-01-08T08:01:35.909Z
UPDATE AD_Field_Trl trl SET Name='Abw. Firmenname' WHERE AD_Field_ID=760976 AND AD_Language='de_DE'
;

-- 2026-01-08T08:01:35.911Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577370)
;

-- 2026-01-08T08:01:35.921Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760976
;

-- 2026-01-08T08:01:35.922Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760976)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> main -> 10 -> address.Abw. Firmenname
-- Column: I_BPartner.BPartnerName
-- 2026-01-08T08:02:55.752Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760976,0,441,541264,641309,'F',TO_TIMESTAMP('2026-01-08 08:02:55.596000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Abw. Firmenname',130,0,0,TO_TIMESTAMP('2026-01-08 08:02:55.596000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> main -> 10 -> address.Abw. Firmenname 2
-- Column: I_BPartner.BPartnerName2
-- 2026-01-08T08:03:09.728Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760977,0,441,541264,641310,'F',TO_TIMESTAMP('2026-01-08 08:03:09.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Abw. Firmenname 2',140,0,0,TO_TIMESTAMP('2026-01-08 08:03:09.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

