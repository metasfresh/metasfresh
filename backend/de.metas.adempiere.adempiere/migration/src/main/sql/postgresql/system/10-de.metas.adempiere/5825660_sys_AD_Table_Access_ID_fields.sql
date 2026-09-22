-- Create the AD_Field rows for the surrogate key AD_Table_Access.AD_Table_Access_ID (added by
-- 5825620), mirroring the AD_Window_Access precedent 5721770.
--
-- The WebUI decides whether a tab has a key column from the tab's GridFieldVO set, which is loaded
-- from AD_Field_v / AD_Field_vt -- i.e. only from columns that own an AD_Field row on that tab. With
-- no key field in the tab, GridTabVOBasedDocumentEntityDescriptorFactory falls back to treating every
-- parent-link column as a key, which renders AD_Table_ID read-only on a new row. So the surrogate
-- column alone does not reach the window; the field rows below are what make it take effect.
--
-- Tabs: 549493 (Rolle - Verwaltung, window 111) and 482 (Rolle - Datenzugriff - LEGACY, window 268).
-- The field is technical, hence IsDisplayed='N' / IsDisplayedGrid='N' -- exactly as 5721770 does.
--
-- IDs allocated from idserver.metas.de on 2026-09-22:
--   AD_MigrationScript 5825660
--   AD_Field           785056 (tab 549493)
--   AD_Field           785057 (tab 482)

DO
$$
    BEGIN
        -- 5825620 returns early when an instance already carries an AD_Table_Access_ID column under a
        -- different AD_Column_ID; on such an instance AD_Column 593638 does not exist and the inserts
        -- below would violate the FK. Skip instead.
        IF NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_Column_ID = 593638) THEN
            RAISE NOTICE 'AD_Column 593638 (AD_Table_Access.AD_Table_Access_ID) not found. Do nothing';
            RETURN;
        END IF;

        -- Field: Rolle - Verwaltung(111,D) -> Tabellen-Zugriff(549493,D) -> AD_Table_Access
        INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
        VALUES (0, 593638, 785056 /*From ID Server*/, 0, 549493, TO_TIMESTAMP('2026-09-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'AD_Table_Access', TO_TIMESTAMP('2026-09-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
        ON CONFLICT (AD_Field_ID) DO NOTHING;

        INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
        SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
        FROM AD_Language l, AD_Field t
        WHERE l.IsActive = 'Y'
          AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
          AND t.AD_Field_ID = 785056
          AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

        /* DDL */
        PERFORM update_FieldTranslation_From_AD_Name_Element(585481);

        DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785056;

        /* DDL */ PERFORM AD_Element_Link_Create_Missing_Field(785056);

        -- Field: Rolle - Datenzugriff - LEGACY(268,D) -> Tabellen-Zugriff(482,D) -> AD_Table_Access
        INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
        VALUES (0, 593638, 785057 /*From ID Server*/, 0, 482, TO_TIMESTAMP('2026-09-22 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'AD_Table_Access', TO_TIMESTAMP('2026-09-22 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
        ON CONFLICT (AD_Field_ID) DO NOTHING;

        INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
        SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
        FROM AD_Language l, AD_Field t
        WHERE l.IsActive = 'Y'
          AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
          AND t.AD_Field_ID = 785057
          AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

        /* DDL */
        PERFORM update_FieldTranslation_From_AD_Name_Element(585481);

        DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785057;

        /* DDL */ PERFORM AD_Element_Link_Create_Missing_Field(785057);
    END
$$
;
