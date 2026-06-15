-- 30241: Produktionsauftrag — IsFixedProductionDate flag makes "Eff. Prod. Datum" read-only
--
-- Adds a new Y/N flag PP_Order.IsFixedProductionDate (default 'N' = backward compatible).
-- When the flag = 'Y', the "Eff. Prod. Datum" field (PP_Order.DateDelivered,
-- AD_Field 54142, window 53009 / tab 53054) becomes read-only; when 'N' it stays editable.
--
-- IDs allocated from idserver.metas.de on 2026-06-15:
--   AD_Element     584991  (PP_Order.IsFixedProductionDate label/help)
--   AD_Column      592808  (PP_Order.IsFixedProductionDate)
--   AD_Field       781116  (checkbox on window 53009 / tab 53054)
--   AD_UI_Element  652262  (UI placement)
--
-- NOTE: AD_Table_ID (PP_Order), the UI element group and SeqNo are resolved via
--       sub-queries (relative to the existing "Eff. Prod. Datum" field 54142) instead of
--       hard-coded literals, because they were not read from a live DB. They resolve
--       correctly at apply time; replace with literals if you prefer the usual style.

-- =============================================================================
-- 1. AD_Element
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help)
VALUES (584991 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-15 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-15 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'IsFixedProductionDate',
        'Eff. Prod. Datum gesetzt',
        'Eff. Prod. Datum gesetzt',
        'Wenn aktiviert, ist das Feld „Eff. Prod. Datum" schreibgeschützt.',
        'Steuert, ob das effektive Produktionsdatum („Eff. Prod. Datum", PP_Order.DateDelivered) auf dem Produktionsauftrag bearbeitet werden kann.'
        || ' Bei „Ja" ist das Feld schreibgeschützt, bei „Nein" editierbar. Standard: „Nein".');

-- Skeleton Trl rows (copy the element's Created/Updated = 08:00:00)
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 584991
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- English translation (Updated strictly later than the element INSERT, so the propagation guard fires)
UPDATE AD_Element_Trl
SET Name        = 'Eff. Prod. Date Set',
    PrintName   = 'Eff. Prod. Date Set',
    Description = 'If set, the "Eff. Prod. Date" field is read-only.',
    Help        = 'Controls whether the effective production date ("Eff. Prod. Date", PP_Order.DateDelivered) on the production order can be edited.'
                  || ' When "Yes" the field is read-only; when "No" it is editable. Default: "No".',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-06-15 08:00:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584991 AND AD_Language = 'en_US';

-- German translation (base language)
UPDATE AD_Element_Trl
SET Name        = 'Eff. Prod. Datum gesetzt',
    PrintName   = 'Eff. Prod. Datum gesetzt',
    Description = 'Wenn aktiviert, ist das Feld „Eff. Prod. Datum" schreibgeschützt.',
    Help        = 'Steuert, ob das effektive Produktionsdatum („Eff. Prod. Datum", PP_Order.DateDelivered) auf dem Produktionsauftrag bearbeitet werden kann.'
                  || ' Bei „Ja" ist das Feld schreibgeschützt, bei „Nein" editierbar. Standard: „Nein".',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-06-15 08:00:18', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584991 AND AD_Language = 'de_DE';

-- de_CH = same as de_DE (no ß present)
UPDATE AD_Element_Trl
SET Name        = 'Eff. Prod. Datum gesetzt',
    PrintName   = 'Eff. Prod. Datum gesetzt',
    Description = 'Wenn aktiviert, ist das Feld „Eff. Prod. Datum" schreibgeschützt.',
    Help        = 'Steuert, ob das effektive Produktionsdatum („Eff. Prod. Datum", PP_Order.DateDelivered) auf dem Produktionsauftrag bearbeitet werden kann.'
                  || ' Bei „Ja" ist das Feld schreibgeschützt, bei „Nein" editierbar. Standard: „Nein".',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-06-15 08:00:24', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584991 AND AD_Language = 'de_CH';

-- =============================================================================
-- 2. AD_Column  (Yes-No = AD_Reference_ID 20)
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent, IsSelectionColumn,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       PersonalDataCategory)
VALUES (592808 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-15 08:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-15 08:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'PP_Order'), 584991, 20,
        'IsFixedProductionDate',
        'Eff. Prod. Datum gesetzt',
        'Wenn aktiviert, ist das Feld „Eff. Prod. Datum" schreibgeschützt.',
        NULL,
        1, 'Y', 'Y', 'N',
        'N', 'D', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y',
        'NP');

-- Skeleton Trl rows for AD_Column (copy the column's Created/Updated = 08:01:00)
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592808
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 3. DDL — physical column (via db_alter_table: handles dependent-view drop/recreate)
-- =============================================================================
SELECT db_alter_table('PP_Order', 'ALTER TABLE public.PP_Order ADD COLUMN IF NOT EXISTS IsFixedProductionDate CHAR(1) DEFAULT ''N''');
UPDATE PP_Order SET IsFixedProductionDate = 'N' WHERE IsFixedProductionDate IS NULL;
SELECT db_alter_table('PP_Order', 'ALTER TABLE public.PP_Order ALTER COLUMN IsFixedProductionDate SET NOT NULL');

-- =============================================================================
-- 4. AD_Field on window 53009 / tab 53054
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
VALUES (781116 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-15 08:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-15 08:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        53054, 592808, NULL,
        'Eff. Prod. Datum gesetzt',
        'Wenn aktiviert, ist das Feld „Eff. Prod. Datum" schreibgeschützt.',
        'Y', 'N', 'N', 'N',
        0, 0, 0, 'D');

-- Skeleton Trl rows for AD_Field (copy the field's Created/Updated = 08:02:00)
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 781116
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- Field-level translation propagation + element link (mandatory after a new AD_Field)
SELECT update_FieldTranslation_From_AD_Name_Element(584991);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781116;
SELECT AD_Element_Link_Create_Missing_Field(781116);

-- =============================================================================
-- 5. AD_UI_Element — checkbox placed in the same UI group as "Eff. Prod. Datum" (field 54142)
-- =============================================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781116, 0, 53054,
        (SELECT AD_UI_ElementGroup_ID FROM AD_UI_Element WHERE AD_Field_ID = 54142 AND AD_Tab_ID = 53054 ORDER BY SeqNo LIMIT 1),
        652262 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-06-15 08:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N',
        'Eff. Prod. Datum gesetzt',
        (SELECT SeqNo + 1 FROM AD_UI_Element WHERE AD_Field_ID = 54142 AND AD_Tab_ID = 53054 ORDER BY SeqNo LIMIT 1),
        0, 0,
        TO_TIMESTAMP('2026-06-15 08:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- =============================================================================
-- 6. Propagate element translations to dependent column/field records
--    (works because AD_Field_Trl/AD_Column_Trl.Updated differ from AD_Element_Trl.Updated)
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584991);

-- =============================================================================
-- 7. ReadOnlyLogic at AD_Column level on PP_Order.DateDelivered ("Eff. Prod. Datum").
--    Set on the column (not field 54142) so it applies wherever DateDelivered is shown
--    on PP_Order windows. @IsFixedProductionDate@ resolves from the same record's flag.
-- =============================================================================
UPDATE AD_Column
SET ReadOnlyLogic = '@IsFixedProductionDate@=Y',
    Updated       = TO_TIMESTAMP('2026-06-15 08:03:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy     = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'PP_Order')
  AND ColumnName  = 'DateDelivered';

-- =============================================================================
-- 8. Make "Eff. Prod. Datum" EDITABLE when the flag is off.
--    DateDelivered is read-only by default (hard read-only at column/field level), so it
--    still looks read-only with the flag = N — the conditional ReadOnlyLogic never applies.
--    Clear the hard read-only so ReadOnlyLogic alone governs: flag = Y -> read-only,
--    flag = N -> editable.
-- =============================================================================
UPDATE AD_Column
SET IsUpdateable = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-15 08:03:10', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'PP_Order')
  AND ColumnName  = 'DateDelivered';

UPDATE AD_Field
SET IsReadOnly = 'N',
    Updated    = TO_TIMESTAMP('2026-06-15 08:03:20', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy  = 100
WHERE AD_Field_ID = 54142;
