-- me03#29584: Add IsDropShipWarehouse to M_Warehouse
-- Script 3 of 3: AD_Field + AD_Field_Trl + AD_UI_Element on main Lager tab (AD_Tab_ID=177, AD_Window_ID=139)
--
-- Placement: next to existing IsInTransit field
--   IsInTransit: AD_Field_ID=54334, SeqNo=100, SeqNoGrid=60
--                AD_UI_Element SeqNo=50, SeqNoGrid=100, AD_UI_ElementGroup_ID=540174
--   IsDropShipWarehouse goes right after IsInTransit:
--                SeqNo=105, SeqNoGrid=62 (AD_Field legacy), UI SeqNo=55, UI SeqNoGrid=105

-- =============================================================================
-- 1. AD_Field on main Lager tab (AD_Tab_ID=177)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
VALUES (779180 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:02:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
        177, 592508, NULL,
        'Streckengeschäft-Lager',
        'Wenn Ja, werden Verkaufsaufträge auf diesem Lager als Streckengeschäft (Dropship) abgewickelt. Bei Auftragsabschluss wird automatisch eine Bestellung an den Lieferanten erzeugt.',
        'Y', 'Y', 'N', 'N',
        105, 62, 0, 'D');

-- Skeleton Trl rows for AD_Field
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 779180
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- 2. AD_UI_Element (WebUI layout — required, AD_Field alone is not visible in WebUI)
-- Placed in same AD_UI_ElementGroup as IsInTransit (AD_UI_ElementGroup_ID=540174)
-- SeqNo=55 (between IsInTransit=50 and Ohne HU Bestand=60)
-- SeqNoGrid=105 (between IsInTransit=100 and Ohne HU Bestand=110)
-- =============================================================================
INSERT INTO AD_UI_Element (
    AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy
) VALUES (
    0, 779180, 0, 177,
    540174, 651164 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-05-11 14:02:01','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Streckengeschäft-Lager', 55, 105, 0,
    TO_TIMESTAMP('2026-05-11 14:02:01','YYYY-MM-DD HH24:MI:SS'), 100
);

-- =============================================================================
-- 3. Propagate translations from AD_Element to AD_Field_Trl (fills de_DE, de_CH, en_US)
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584854);

-- =============================================================================
-- 4. Explicit en_US translation on Trl tables
--    The propagation function above leaves the skeleton en_US row with IsTranslated='N'
--    and the German base name, so on an English UI the label renders as
--    'Streckengeschäft-Lager'. Set the English label + IsTranslated='Y' directly.
-- =============================================================================
UPDATE AD_Field_Trl
SET Name        = 'Dropship Warehouse',
    Description = 'If Yes, sales orders on this warehouse are handled as dropship. On sales-order completion a single purchase order is automatically created for the vendor.',
    Help        = 'Marks this warehouse as a dropship warehouse. On such warehouses, completing a sales order automatically creates exactly one purchase order per sales order — bypassing the normal material-disposition / purchase-candidate path. Per-line vendor selection is enforced more strictly for dropship warehouses.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-05-11 14:02:02','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Field_ID = 779180 AND AD_Language = 'en_US';

UPDATE AD_UI_Element_Trl
SET Name         = 'Dropship Warehouse',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-11 14:02:03','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_UI_Element_ID = 651164 AND AD_Language = 'en_US';

UPDATE AD_Column_Trl
SET Name         = 'Dropship Warehouse',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-11 14:02:04','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Column_ID = 592508 AND AD_Language = 'en_US';
