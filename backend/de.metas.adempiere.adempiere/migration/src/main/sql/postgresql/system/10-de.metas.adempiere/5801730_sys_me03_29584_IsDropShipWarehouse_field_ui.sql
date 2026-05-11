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
VALUES (779180 /*From ID Server*/, 0, 0, 'Y', '2026-05-11 09:00', 100, '2026-05-11 09:00', 100,
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
    '2026-05-11 09:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Streckengeschäft-Lager', 55, 105, 0,
    '2026-05-11 09:00', 100
);

-- =============================================================================
-- 3. Propagate translations from AD_Element to AD_Field_Trl (fills de_DE, de_CH, en_US)
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584854);
