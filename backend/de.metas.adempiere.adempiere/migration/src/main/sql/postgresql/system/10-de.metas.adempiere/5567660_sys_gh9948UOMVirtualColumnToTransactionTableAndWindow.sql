-- 2020-09-15T15:58:27.454Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRangeFilter, IsSelectionColumn, IsShowFilterIncrementButtons, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 571510, 215, 0, 19, 329, 'C_UOM_ID', TO_TIMESTAMP('2020-09-15 18:58:25', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'Maßeinheit', 'D', 0, 10, 'Eine eindeutige (nicht monetäre) Maßeinheit', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Maßeinheit', 0, 0,
        TO_TIMESTAMP('2020-09-15 18:58:25', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

-- 2020-09-15T15:58:27.529Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Column_ID = 571510
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2020-09-15T15:58:27.628Z
-- URL zum Konzept
/* DDL */

SELECT update_Column_Translation_From_AD_Element(215)
;

-- 2020-09-15T16:00:11.163Z
-- URL zum Konzept
UPDATE AD_Column
SET ColumnSQL='(select C_UOM_ID from m_product p where p.M_Product_ID = M_Transaction.M_Product_ID)', IsUpdateable='N', Updated=TO_TIMESTAMP('2020-09-15 19:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 571510
;

-- 2020-09-15T16:04:52.915Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 571510, 617511, 0, 384, 0, TO_TIMESTAMP('2020-09-15 19:04:51', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Maßeinheit', 0, 'D', 'Eine eindeutige (nicht monetäre) Maßeinheit', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Maßeinheit', 160, 160, 0, 1, 1, TO_TIMESTAMP('2020-09-15 19:04:51', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-09-15T16:04:52.989Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
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
       t.UpdatedBy
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Field_ID = 617511
  AND NOT EXISTS(SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2020-09-15T16:04:53.059Z
-- URL zum Konzept
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2020-09-15T16:04:53.302Z
-- URL zum Konzept
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 617511
;

-- 2020-09-15T16:04:53.390Z
-- URL zum Konzept
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(617511)
;

-- 2020-09-15T16:08:05.956Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, Description, Help, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 617511, 0, 384, 540638, 571407, 'F', TO_TIMESTAMP('2020-09-15 19:08:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Maßeinheit', 'Eine eindeutige (nicht monetäre) Maßeinheit', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Maßeinheit', 35, 0, 0, TO_TIMESTAMP('2020-09-15 19:08:05', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-09-15T16:08:20.175Z
-- URL zum Konzept
UPDATE AD_UI_Element
SET WidgetSize='M', Updated=TO_TIMESTAMP('2020-09-15 19:08:19', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 571407
;

-- 2020-09-15T16:09:00.127Z
-- URL zum Konzept
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=40, Updated=TO_TIMESTAMP('2020-09-15 19:09:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 571407
;

-- 2020-09-15T16:09:00.386Z
-- URL zum Konzept
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=50, Updated=TO_TIMESTAMP('2020-09-15 19:09:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 545581
;

-- 2020-09-15T16:09:00.641Z
-- URL zum Konzept
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=60, Updated=TO_TIMESTAMP('2020-09-15 19:09:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 545588
;

-- 2020-09-15T16:09:00.910Z
-- URL zum Konzept
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=70, Updated=TO_TIMESTAMP('2020-09-15 19:09:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 545583
;

-- 2020-09-15T16:09:01.171Z
-- URL zum Konzept
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=80, Updated=TO_TIMESTAMP('2020-09-15 19:09:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 545587
;

-- 2020-09-15T16:09:01.425Z
-- URL zum Konzept
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=90, Updated=TO_TIMESTAMP('2020-09-15 19:09:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 545586
;

-- 2020-09-15T16:09:01.679Z
-- URL zum Konzept
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=100, Updated=TO_TIMESTAMP('2020-09-15 19:09:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 545584
;

-- 2020-09-15T16:09:01.924Z
-- URL zum Konzept
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=130, Updated=TO_TIMESTAMP('2020-09-15 19:09:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 545592
;

-- 2020-09-15T16:09:02.182Z
-- URL zum Konzept
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=140, Updated=TO_TIMESTAMP('2020-09-15 19:09:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 545593
;

-- 2020-09-15T16:09:02.439Z
-- URL zum Konzept
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=150, Updated=TO_TIMESTAMP('2020-09-15 19:09:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 545594
;
