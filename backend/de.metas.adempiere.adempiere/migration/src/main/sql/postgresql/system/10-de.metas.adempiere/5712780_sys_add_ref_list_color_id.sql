-- Column: AD_Ref_List.AD_Color_ID
-- 2023-12-06T10:16:02.597Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587688, 1636, 0, 19, 104, 'AD_Color_ID', TO_TIMESTAMP('2023-12-06 11:16:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'Color for backgrounds or indicators', 'D', 0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'System-Farbe', 0, 0,
        TO_TIMESTAMP('2023-12-06 11:16:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

-- 2023-12-06T10:16:02.602Z
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
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Column_ID = 587688
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-12-06T10:16:02.627Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(1636)
;

-- 2023-12-06T10:16:18.280Z
/* DDL */

SELECT public.db_alter_table('AD_Ref_List', 'ALTER TABLE public.AD_Ref_List ADD COLUMN AD_Color_ID NUMERIC(10)')
;

-- 2023-12-06T10:16:18.953Z
ALTER TABLE AD_Ref_List
    ADD CONSTRAINT ADColor_ADRefList FOREIGN KEY (AD_Color_ID) REFERENCES public.AD_Color DEFERRABLE INITIALLY DEFERRED
;

-- Field: Referenz(101,D) -> Listenvalidierung(104,D) -> System-Farbe
-- Column: AD_Ref_List.AD_Color_ID
-- 2023-12-06T10:19:11.341Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, Description, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 587688, 723102, 0, 104, 0, TO_TIMESTAMP('2023-12-06 11:19:11', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Color for backgrounds or indicators', 0, 'U', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'System-Farbe', 0, 120, 0, 1, 1, TO_TIMESTAMP('2023-12-06 11:19:11', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2023-12-06T10:19:11.346Z
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
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Field_ID = 723102
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-12-06T10:19:11.353Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(1636)
;

-- 2023-12-06T10:19:11.377Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 723102
;

-- 2023-12-06T10:19:11.389Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(723102)
;

-- UI Element: Referenz(101,D) -> Listenvalidierung(104,D) -> main -> 10 -> default.System-Farbe
-- Column: AD_Ref_List.AD_Color_ID
-- 2023-12-06T10:21:04.115Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, Description, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 723102, 0, 104, 541381, 621956, 'F', TO_TIMESTAMP('2023-12-06 11:21:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Color for backgrounds or indicators', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'System-Farbe', 65, 0, 0, TO_TIMESTAMP('2023-12-06 11:21:03', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- Field: Referenz(101,D) -> Listenvalidierung(104,D) -> System-Farbe
-- Column: AD_Ref_List.AD_Color_ID
-- 2023-12-06T10:33:28.640Z
UPDATE AD_Field
SET EntityType='D', Updated=TO_TIMESTAMP('2023-12-06 11:33:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 723102
;

-- UI Element: Referenz(101,D) -> Listenvalidierung(104,D) -> main -> 10 -> default.System-Farbe
-- Column: AD_Ref_List.AD_Color_ID
-- 2023-12-06T10:34:20.254Z
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=60, Updated=TO_TIMESTAMP('2023-12-06 11:34:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 621956
;

-- UI Element: Referenz(101,D) -> Listenvalidierung(104,D) -> main -> 10 -> default.Aktiv
-- Column: AD_Ref_List.IsActive
-- 2023-12-06T10:34:20.261Z
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=70, Updated=TO_TIMESTAMP('2023-12-06 11:34:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 550261
;

-- UI Element: Referenz(101,D) -> Listenvalidierung(104,D) -> main -> 10 -> default.Gültig ab
-- Column: AD_Ref_List.ValidFrom
-- 2023-12-06T10:34:20.268Z
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=80, Updated=TO_TIMESTAMP('2023-12-06 11:34:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 550263
;

-- UI Element: Referenz(101,D) -> Listenvalidierung(104,D) -> main -> 10 -> default.Gültig bis
-- Column: AD_Ref_List.ValidTo
-- 2023-12-06T10:34:20.274Z
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=90, Updated=TO_TIMESTAMP('2023-12-06 11:34:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 550264
;

-- UI Element: Referenz(101,D) -> Listenvalidierung(104,D) -> main -> 10 -> default.Sektion
-- Column: AD_Ref_List.AD_Org_ID
-- 2023-12-06T10:34:20.281Z
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=100, Updated=TO_TIMESTAMP('2023-12-06 11:34:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 550255
;

-- Field: Referenz(101,D) -> Listenvalidierung(104,D) -> System-Farbe
-- Column: AD_Ref_List.AD_Color_ID
-- 2023-12-06T10:44:50.519Z
UPDATE AD_Field
SET SeqNo=75, Updated=TO_TIMESTAMP('2023-12-06 11:44:50', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 723102
;

-- Field: Referenz(101,D) -> Listenvalidierung(104,D) -> System-Farbe
-- Column: AD_Ref_List.AD_Color_ID
-- 2023-12-06T10:45:17.930Z
UPDATE AD_Field
SET DisplayLogic='@ValidationType@=L', Updated=TO_TIMESTAMP('2023-12-06 11:45:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 723102
;

-- Field: Referenz(101,D) -> Listenvalidierung(104,D) -> System-Farbe
-- Column: AD_Ref_List.AD_Color_ID
-- 2023-12-06T10:45:46.118Z
UPDATE AD_Field
SET DisplayLength=14, Updated=TO_TIMESTAMP('2023-12-06 11:45:46', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 723102
;

