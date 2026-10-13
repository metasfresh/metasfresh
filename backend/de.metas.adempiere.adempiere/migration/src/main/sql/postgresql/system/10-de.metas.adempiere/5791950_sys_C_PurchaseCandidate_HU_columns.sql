-- Run mode: SWING_CLIENT

-- Column: C_PurchaseCandidate.M_HU_PI_Item_Product_ID
-- 2026-03-05T15:17:31.440Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, CloningStrategy, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterInactiveValues, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence,
                       MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592169, 542132, 0, 19, 540861, 'XX', 'M_HU_PI_Item_Product_ID', TO_TIMESTAMP('2026-03-05 15:17:31.169000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'N', 'de.metas.purchasecandidate', 0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0,
        'Packvorschrift', 0, 0, TO_TIMESTAMP('2026-03-05 15:17:31.169000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0)
;

-- 2026-03-05T15:17:31.446Z
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
  AND t.AD_Column_ID = 592169
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2026-03-05T15:17:31.470Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(542132)
;

-- 2026-03-05T15:18:28.366Z
/* DDL */

SELECT public.db_alter_table('C_PurchaseCandidate', 'ALTER TABLE public.C_PurchaseCandidate ADD COLUMN M_HU_PI_Item_Product_ID NUMERIC(10)')
;

-- 2026-03-05T15:18:28.481Z
ALTER TABLE C_PurchaseCandidate
    ADD CONSTRAINT MHUPIItemProduct_CPurchaseCandidate FOREIGN KEY (M_HU_PI_Item_Product_ID) REFERENCES public.M_HU_PI_Item_Product DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_PurchaseCandidate.QtyEnteredTU
-- 2026-03-05T15:19:09.629Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, CloningStrategy, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterInactiveValues, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence,
                       MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592170, 542397, 0, 29, 540861, 'XX', 'QtyEnteredTU', TO_TIMESTAMP('2026-03-05 15:19:09.484000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'N', 'de.metas.purchasecandidate', 0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Menge TU', 0,
        0, TO_TIMESTAMP('2026-03-05 15:19:09.484000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0)
;

-- 2026-03-05T15:19:09.631Z
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
  AND t.AD_Column_ID = 592170
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2026-03-05T15:19:09.723Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(542397)
;

-- 2026-03-05T15:19:11.572Z
/* DDL */

SELECT public.db_alter_table('C_PurchaseCandidate', 'ALTER TABLE public.C_PurchaseCandidate ADD COLUMN QtyEnteredTU NUMERIC')
;

-- Field: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> Packvorschrift
-- Column: C_PurchaseCandidate.M_HU_PI_Item_Product_ID
-- 2026-03-05T15:33:10.369Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, DisplayLength, EntityType, FacetFilterSeqNo, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsHideGridColumnIfEmpty, IsOverrideFilterDefaultValue, IsReadOnly, IsSameLine, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                      SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592169, 774827, 0, 540894, 0, TO_TIMESTAMP('2026-03-05 15:33:10.160000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0, 'U', 0, 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Packvorschrift', 0, 0, 380, 0, 1, 1, TO_TIMESTAMP('2026-03-05 15:33:10.160000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- 2026-03-05T15:33:10.375Z
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
  AND t.AD_Field_ID = 774827
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2026-03-05T15:33:10.400Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(542132)
;

-- 2026-03-05T15:33:10.412Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 774827
;

-- 2026-03-05T15:33:10.414Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(774827)
;

-- Field: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> Menge TU
-- Column: C_PurchaseCandidate.QtyEnteredTU
-- 2026-03-05T15:33:16.280Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, DisplayLength, EntityType, FacetFilterSeqNo, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsHideGridColumnIfEmpty, IsOverrideFilterDefaultValue, IsReadOnly, IsSameLine, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                      SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592170, 774828, 0, 540894, 0, TO_TIMESTAMP('2026-03-05 15:33:16.162000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0, 'U', 0, 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Menge TU', 0, 0, 390, 0, 1, 1, TO_TIMESTAMP('2026-03-05 15:33:16.162000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- 2026-03-05T15:33:16.282Z
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
  AND t.AD_Field_ID = 774828
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2026-03-05T15:33:16.283Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(542397)
;

-- 2026-03-05T15:33:16.286Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 774828
;

-- 2026-03-05T15:33:16.287Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(774828)
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> default.Abweichende Lieferadresse
-- Column: C_PurchaseCandidate.IsDropShip
-- 2026-03-05T15:34:05.914Z
UPDATE AD_UI_Element
SET SeqNo=70, Updated=TO_TIMESTAMP('2026-03-05 15:34:05.914000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_UI_Element_ID = 648472
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> default.Lieferempfänger
-- Column: C_PurchaseCandidate.DropShip_BPartner_ID
-- 2026-03-05T15:34:10.096Z
UPDATE AD_UI_Element
SET SeqNo=80, Updated=TO_TIMESTAMP('2026-03-05 15:34:10.096000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_UI_Element_ID = 648473
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> default.Menge TU
-- Column: C_PurchaseCandidate.QtyEnteredTU
-- 2026-03-05T15:34:31.407Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774828, 0, 540894, 541247, 648474, 'F', TO_TIMESTAMP('2026-03-05 15:34:31.270000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Menge TU', 90, 0, 0, TO_TIMESTAMP('2026-03-05 15:34:31.270000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> default.Packvorschrift
-- Column: C_PurchaseCandidate.M_HU_PI_Item_Product_ID
-- 2026-03-05T15:34:46.890Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774827, 0, 540894, 541247, 648475, 'F', TO_TIMESTAMP('2026-03-05 15:34:46.772000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Packvorschrift', 100, 0, 0, TO_TIMESTAMP('2026-03-05 15:34:46.772000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- Field: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> Menge TU
-- Column: C_PurchaseCandidate.QtyEnteredTU
-- 2026-03-05T15:35:33.536Z
UPDATE AD_Field
SET DisplayLogic='@QtyEnteredTU/0@>0', Updated=TO_TIMESTAMP('2026-03-05 15:35:33.536000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Field_ID = 774828
;

-- Field: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> Packvorschrift
-- Column: C_PurchaseCandidate.M_HU_PI_Item_Product_ID
-- 2026-03-05T15:35:59.799Z
UPDATE AD_Field
SET DisplayLogic='@M_HU_PI_Item_Product_ID/-1@>0', Updated=TO_TIMESTAMP('2026-03-05 15:35:59.799000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Field_ID = 774827
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> default.Lager
-- Column: C_PurchaseCandidate.M_WarehousePO_ID
-- 2026-03-05T15:36:45.134Z
UPDATE AD_UI_Element
SET SeqNo=60, Updated=TO_TIMESTAMP('2026-03-05 15:36:45.134000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_UI_Element_ID = 549340
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> default.Menge TU
-- Column: C_PurchaseCandidate.QtyEnteredTU
-- 2026-03-05T15:36:48.066Z
UPDATE AD_UI_Element
SET SeqNo=40, Updated=TO_TIMESTAMP('2026-03-05 15:36:48.066000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_UI_Element_ID = 648474
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> default.Packvorschrift
-- Column: C_PurchaseCandidate.M_HU_PI_Item_Product_ID
-- 2026-03-05T15:38:58.058Z
UPDATE AD_UI_Element
SET SeqNo=50, Updated=TO_TIMESTAMP('2026-03-05 15:38:58.057000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_UI_Element_ID = 648475
;

