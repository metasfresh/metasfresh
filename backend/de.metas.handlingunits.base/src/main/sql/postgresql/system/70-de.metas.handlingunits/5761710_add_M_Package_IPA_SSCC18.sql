-- Run mode: SWING_CLIENT

-- Column: M_Package.IPA_SSCC18
-- 2025-07-30T19:43:19.427Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, CloningStrategy, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 590585, 542693, 0, 10, 664, 'XX', 'IPA_SSCC18', TO_TIMESTAMP('2025-07-30 19:43:19.171000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'N', 'de.metas.esb.edi', 0, 40, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'SSCC18', 0, 0,
        TO_TIMESTAMP('2025-07-30 19:43:19.171000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0)
;

-- 2025-07-30T19:43:19.430Z
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 590585
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2025-07-30T19:43:19.458Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(542693)
;

-- 2025-07-30T19:43:22.321Z
/* DDL */

SELECT public.db_alter_table('M_Package', 'ALTER TABLE public.M_Package ADD COLUMN IPA_SSCC18 VARCHAR(40)')
;

-- Field: Packstück(319,D) -> Packstück(626,D) -> SSCC18
-- Column: M_Package.IPA_SSCC18
-- 2025-07-30T19:47:27.990Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, DisplayLength, EntityType, FacetFilterSeqNo, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsHideGridColumnIfEmpty, IsOverrideFilterDefaultValue, IsReadOnly, IsSameLine, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                      SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 590585, 751747, 0, 626, 0, TO_TIMESTAMP('2025-07-30 19:47:27.803000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0, 'D', 0, 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'SSCC18', 0, 0, 180, 0, 1, 1, TO_TIMESTAMP('2025-07-30 19:47:27.803000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- 2025-07-30T19:47:27.992Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 751747
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2025-07-30T19:47:27.995Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(542693)
;

-- 2025-07-30T19:47:28.007Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 751747
;

-- 2025-07-30T19:47:28.009Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(751747)
;

-- UI Element: Packstück(319,D) -> Packstück(626,D) -> main -> 10 -> description.SSCC18
-- Column: M_Package.IPA_SSCC18
-- 2025-07-30T19:49:45.511Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 751747, 0, 626, 541523, 636084, 'F', TO_TIMESTAMP('2025-07-30 19:49:45.320000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'SSCC18', 20, 0, 0, TO_TIMESTAMP('2025-07-30 19:49:45.320000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

