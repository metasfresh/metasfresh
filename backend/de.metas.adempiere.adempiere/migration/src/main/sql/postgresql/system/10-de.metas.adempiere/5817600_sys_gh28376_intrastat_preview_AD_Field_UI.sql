-- Intrastat_Preview_V window: AD_Field + AD_UI_Element for the three new ID columns
-- (M_Product_ID / C_UOM_ID / C_Currency_ID), and deactivate the GoodsDescription
-- AD_Field + its AD_UI_Element (that column no longer exists on the view after migration
-- 5817570 and its AD_Column is deactivated by migration 5817590).
--
-- Grid placement:
--   Grid col 2:  M_Product_ID       (SeqNoGrid=20 — replaces GoodsDescription's slot)
--   Grid col 7b: C_UOM_ID           (SeqNoGrid=75 — between SupplementaryUnits and InvoiceValue)
--   Grid col 9b: C_Currency_ID      (SeqNoGrid=95 — between StatisticalValue and RecipientVATNo)
--
-- Form SeqNo values pick unused slots (25 / 145 / 155) so form order stays coherent even
-- though the tab is grid-only (IsSingleRow='N').

-- =====================================================================
-- 1. Deactivate old GoodsDescription AD_Field + AD_UI_Element
-- =====================================================================
UPDATE AD_Field
   SET IsActive        = 'N',
       IsDisplayed     = 'N',
       IsDisplayedGrid = 'N',
       Updated         = TO_TIMESTAMP('2026-08-05 10:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy       = 100
 WHERE AD_Field_ID = 781874; -- GoodsDescription field

UPDATE AD_UI_Element
   SET IsActive        = 'N',
       IsDisplayed     = 'N',
       IsDisplayedGrid = 'N',
       Updated         = TO_TIMESTAMP('2026-08-05 10:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy       = 100
 WHERE AD_UI_Element_ID = 652789; -- GoodsDescription UI element

-- =====================================================================
-- 2. New AD_Field: M_Product_ID (AD_Column 593074)
-- =====================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, IsReadOnly, IsEncrypted)
VALUES (781883 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-08-05 10:00:01','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-05 10:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
    'Produkt', 593074, 549359, 'D',
    'Y', 'Y', 25, 20, 'Y', 'N');

-- =====================================================================
-- 3. New AD_Field: C_UOM_ID (AD_Column 593075)
-- =====================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, IsReadOnly, IsEncrypted)
VALUES (781884 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-08-05 10:00:02','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-05 10:00:02','YYYY-MM-DD HH24:MI:SS'), 100,
    'Maßeinheit', 593075, 549359, 'D',
    'Y', 'Y', 145, 75, 'Y', 'N');

-- =====================================================================
-- 4. New AD_Field: C_Currency_ID (AD_Column 593076)
-- =====================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, IsReadOnly, IsEncrypted)
VALUES (781885 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-08-05 10:00:03','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-05 10:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
    'Währung', 593076, 549359, 'D',
    'Y', 'Y', 155, 95, 'Y', 'N');

-- =====================================================================
-- 5. AD_UI_Element: M_Product_ID (grid col 2 slot, SeqNoGrid=20)
-- =====================================================================
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652801 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-08-05 10:00:04','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-05 10:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
    781883, 549359, 555533, 'F',
    'Produkt', 25, 20, 0,
    'N', 'Y', 'N', 'N');

-- =====================================================================
-- 6. AD_UI_Element: C_UOM_ID (SeqNoGrid=75)
-- =====================================================================
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652802 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-08-05 10:00:05','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-05 10:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
    781884, 549359, 555533, 'F',
    'Maßeinheit', 145, 75, 0,
    'N', 'Y', 'N', 'N');

-- =====================================================================
-- 7. AD_UI_Element: C_Currency_ID (SeqNoGrid=95)
-- =====================================================================
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652803 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-08-05 10:00:06','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-05 10:00:06','YYYY-MM-DD HH24:MI:SS'), 100,
    781885, 549359, 555533, 'F',
    'Währung', 155, 95, 0,
    'N', 'Y', 'N', 'N');

-- =====================================================================
-- 8. Seed AD_Field_Trl skeleton rows + propagate AD_Element translations to the new fields.
-- =====================================================================
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Field_ID IN (781883, 781884, 781885)
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(454); -- M_Product_ID → propagate to AD_Field_Trl
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(215); -- C_UOM_ID
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(193); -- C_Currency_ID
