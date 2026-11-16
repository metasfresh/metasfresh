-- Add IsDropShipWarehouse to M_Warehouse
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
-- 3. Propagate translations from AD_Element to AD_Column_Trl / AD_Field_Trl / AD_UI_Element_Trl
--    AD_Element_Trl is the only source of truth for translations and IsTranslated. The
--    AD_Element_Trl UPDATEs in script 5801710 use timestamps (14:03:00 - 14:03:02) deliberately
--    later than every downstream skeleton-insert timestamp here in 5801730 (≤ 14:02:01), so the
--    function's f_trl.updated <> e_trl.updated guard fires and IsTranslated='Y' flows down to en_US.
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584854);
