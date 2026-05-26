-- DD_Order picking reconcile — expose IsPackingWarehouse + DD_NetworkDistribution_ID on Warehouse window (AD_Window 139)
-- IDs allocated from idserver.metas.de on 2026-05-27:
--   AD_MigrationScript   5804820 (migration script prefix)
--   AD_Field             780493  (IsPackingWarehouse)
--   AD_Field             780494  (DD_NetworkDistribution_ID)
--   AD_UI_Element        651850  (IsPackingWarehouse)
--   AD_UI_Element        651851  (DD_NetworkDistribution_ID)
--
-- Placement on main Lager tab (AD_Tab_ID=177, AD_Window_ID=139):
--   IsDropShipWarehouse: SeqNo=105, SeqNoGrid=62, UI SeqNo=55, UI SeqNoGrid=105, AD_UI_ElementGroup_ID=540174
--   IsPackingWarehouse:  SeqNo=110, SeqNoGrid=64, UI SeqNo=60, UI SeqNoGrid=110, AD_UI_ElementGroup_ID=540174 (right after IsDropShipWarehouse)
--   DD_NetworkDistribution_ID: SeqNo=115, SeqNoGrid=66, UI SeqNo=65, UI SeqNoGrid=115, AD_UI_ElementGroup_ID=540174 (right after IsPackingWarehouse)

-- =============================================================================
-- 1. AD_Field for IsPackingWarehouse
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
VALUES (780493 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-27 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-27 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        177, (SELECT AD_Column_ID FROM AD_Column WHERE ColumnName='IsPackingWarehouse' AND AD_Table_ID=190), NULL,
        'Kommissionierungslager',
        'Wenn Ja, betreibt dieses Lager den eigenständigen DD_Order-Abgleich für die Kommissionierung — anstelle der allgemeinen Materialdisposition.',
        'Y', 'Y', 'N', 'N',
        110, 64, 0, 'D');

-- Skeleton Trl rows for IsPackingWarehouse AD_Field
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 780493
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- 2. AD_Field for DD_NetworkDistribution_ID
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
VALUES (780494 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-27 14:00:01','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-27 14:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
        177, (SELECT AD_Column_ID FROM AD_Column WHERE ColumnName='DD_NetworkDistribution_ID' AND AD_Table_ID=190), NULL,
        'Verteilungsnetz',
        'Verteilungsnetz, das für den DD_Order-Abgleich auf diesem Kommissionierungslager verwendet wird.',
        'Y', 'Y', 'N', 'N',
        115, 66, 0, 'D');

-- Skeleton Trl rows for DD_NetworkDistribution_ID AD_Field
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 780494
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- 3. AD_UI_Element for IsPackingWarehouse
-- =============================================================================
INSERT INTO AD_UI_Element (
    AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy
) VALUES (
    0, 780493, 0, 177,
    540174, 651850 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-05-27 14:00:02','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Kommissionierungslager', 60, 110, 0,
    TO_TIMESTAMP('2026-05-27 14:00:02','YYYY-MM-DD HH24:MI:SS'), 100
);

-- =============================================================================
-- 4. AD_UI_Element for DD_NetworkDistribution_ID (with DisplayLogic: only show when IsPackingWarehouse='Y')
-- =============================================================================
INSERT INTO AD_UI_Element (
    AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy
) VALUES (
    0, 780494, 0, 177,
    540174, 651851 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-05-27 14:00:03','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Verteilungsnetz', 65, 115, 0,
    TO_TIMESTAMP('2026-05-27 14:00:03','YYYY-MM-DD HH24:MI:SS'), 100
);

-- =============================================================================
-- 5. Propagate translations from AD_Element to AD_Column_Trl / AD_Field_Trl / AD_UI_Element_Trl
--    Timestamps are deliberately set later than the skeleton inserts above (14:00:02, 14:00:03)
--    so the propagation function fires and IsTranslated flows down correctly.
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(
  (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'IsPackingWarehouse')
);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(
  (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'DD_NetworkDistribution_ID')
);
