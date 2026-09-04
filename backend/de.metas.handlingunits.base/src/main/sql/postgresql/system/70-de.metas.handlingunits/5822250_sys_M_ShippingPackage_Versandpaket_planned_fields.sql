-- Task Q14 (delivery planning quantities): place the two new derived columns (PlannedLoadedQuantity,
-- PlannedDischargeQuantity - migration 5822240) on the Versandpaket tab (AD_Tab 546736, window
-- Lieferanweisungen 541657), mirroring the planning tab's own order (planned before actual, per pair).
-- Also flips IsReadOnly='Y' on all four quantity fields (the two existing ones and the two new ones) - the
-- instruction line only shows the planning's figures now, it never edits them.
--
-- Grid order stays: ProductValue(10) ProductName(20) M_Locator_ID(30) Batch(40)
--   PlannedLoadedQuantity(45, new) ActualLoadQty(50) PlannedDischargeQuantity(55, new)
--   ActualDischargeQuantity(60) C_UOM_ID(70)
--
-- No AD_Name_ID override on either new field: the point of reusing the planning's own AD_Element
-- (581794/581795) is identical wording by construction - a field-level override is precisely the defect
-- Task Q15 exists to retire on the two existing fields.

-- Existing fields become read-only: the mirror has no logic the user could edit into.
UPDATE AD_Field
SET IsReadOnly = 'Y',
    Updated    = TO_TIMESTAMP('2026-09-03 09:00:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy  = 100
WHERE AD_Field_ID IN (710204, 710205);

-- New field: PlannedLoadedQuantity (AD_Column 593470)
INSERT INTO AD_Field (
    AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, IsReadOnly, SeqNo, IsSameLine, IsHeading, IsFieldOnly,
    IsEncrypted, EntityType, IsMandatory, SeqNoGrid, IsDisplayedGrid, SpanX, SpanY,
    IsExcludeFromZoomTargets, IsAlwaysUpdateable, IsFilterField, IsShowFilterInline,
    IsOverrideFilterDefaultValue, IsFacetFilter, IsHideGridColumnIfEmpty, IsShowFilterInactiveValues)
VALUES (
    784915 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-03 09:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Geplante Verlademenge', 546736, 593470, 'Y', 'Y', 10, 'N', 'N', 'N',
    'N', 'D', 'N', 45, 'Y', 1, 1,
    'N', 'N', 'N', 'N',
    'N', 'N', 'N', 'N');

-- New field: PlannedDischargeQuantity (AD_Column 593471)
INSERT INTO AD_Field (
    AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, IsReadOnly, SeqNo, IsSameLine, IsHeading, IsFieldOnly,
    IsEncrypted, EntityType, IsMandatory, SeqNoGrid, IsDisplayedGrid, SpanX, SpanY,
    IsExcludeFromZoomTargets, IsAlwaysUpdateable, IsFilterField, IsShowFilterInline,
    IsOverrideFilterDefaultValue, IsFacetFilter, IsHideGridColumnIfEmpty, IsShowFilterInactiveValues)
VALUES (
    784916 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-03 09:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Geplante Liefermenge', 546736, 593471, 'Y', 'Y', 10, 'N', 'N', 'N',
    'N', 'D', 'N', 55, 'Y', 1, 1,
    'N', 'N', 'N', 'N',
    'N', 'N', 'N', 'N');

-- AD_Field_Trl: seed every active language from the field's base Name, then override with the same
-- per-language text the two source AD_Elements already carry (581794 / 581795) - identical wording by
-- construction, since both fields reuse those elements at the column level too.
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, f.AD_Field_ID, f.Name, 'N', f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND f.AD_Field_ID IN (784915, 784916)
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID);

UPDATE AD_Field_Trl SET Name = 'Plan Load Qty',    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-03 09:00:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Field_ID = 784915 AND AD_Language = 'en_US';
UPDATE AD_Field_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-03 09:00:16', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Field_ID = 784915 AND AD_Language IN ('de_DE', 'de_CH');
UPDATE AD_Field_Trl SET Name = 'Plan Delivered Qty', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-03 09:00:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Field_ID = 784916 AND AD_Language = 'en_US';
UPDATE AD_Field_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-03 09:00:18', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Field_ID = 784916 AND AD_Language IN ('de_DE', 'de_CH');

-- AD_UI_Element: every renderable AD_Field needs one, or the WebUI silently ignores it.
INSERT INTO AD_UI_Element (
    AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_UI_ElementGroup_ID, Name, SeqNo, SeqNoGrid, IsDisplayed, IsDisplayedGrid,
    AD_Tab_ID, AD_UI_ElementType, IsAdvancedField, IsAllowFiltering, IsMultiLine)
VALUES (
    654685 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:19', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-03 09:00:19', 'YYYY-MM-DD HH24:MI:SS'), 100,
    784915, 550219, 'Geplante Verlademenge', 45, 45, 'Y', 'Y',
    546736, 'F', 'N', 'N', 'N');

INSERT INTO AD_UI_Element (
    AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_UI_ElementGroup_ID, Name, SeqNo, SeqNoGrid, IsDisplayed, IsDisplayedGrid,
    AD_Tab_ID, AD_UI_ElementType, IsAdvancedField, IsAllowFiltering, IsMultiLine)
VALUES (
    654686 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-03 09:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    784916, 550219, 'Geplante Liefermenge', 55, 55, 'Y', 'Y',
    546736, 'F', 'N', 'N', 'N');
