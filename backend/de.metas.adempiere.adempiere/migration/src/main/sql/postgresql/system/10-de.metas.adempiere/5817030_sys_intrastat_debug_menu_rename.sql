-- Rename debug Intrastat menu caption to disambiguate against the new preview window.
-- The debug menu previously shared AD_Element 584668 ("Intrastat") with the debug window;
-- we allocate a dedicated element so the menu tree caption can differ from the window title
-- without disturbing the window itself or other references to 584668.

-- =====================================================================
-- 1. New AD_Element for the debug menu caption
-- =====================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType, Name, PrintName)
VALUES (585151 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-31 13:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-31 13:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
    'Intrastat_Debug_Menu', 'D', 'Intrastat - Detail (Debug)', 'Intrastat - Detail (Debug)');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy, IsTranslated,
    Name, PrintName)
VALUES (585151 /*From ID Server*/, 'en_US', 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-31 13:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-31 13:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
    'Intrastat - Detail (Debug)', 'Intrastat - Detail (Debug)');

-- =====================================================================
-- 2. Point the debug AD_Menu at the new element
-- =====================================================================
UPDATE AD_Menu SET
    AD_Element_ID = 585151,
    Name = 'Intrastat - Detail (Debug)',
    Updated = TO_TIMESTAMP('2026-07-31 13:00:01','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
 WHERE AD_Menu_ID = 542307;

-- =====================================================================
-- 3. Seed AD_Menu_Trl rows for active system languages (idempotent)
--    so the menu tree caption is consistent across languages.
-- =====================================================================
INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Name, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, m.AD_Menu_ID, m.Name, 'N',
    m.AD_Client_ID, m.AD_Org_ID, m.Created, m.Createdby, m.Updated, m.UpdatedBy, 'Y'
FROM AD_Language l, AD_Menu m
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND m.AD_Menu_ID = 542307
  AND NOT EXISTS (
    SELECT 1 FROM AD_Menu_Trl t
    WHERE t.AD_Menu_ID = m.AD_Menu_ID AND t.AD_Language = l.AD_Language);

-- German captions (base language de_DE, and de_CH inherits the same wording).
UPDATE AD_Menu_Trl SET
    Name = 'Intrastat - Detail (Debug)',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-31 13:00:02','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
 WHERE AD_Menu_ID = 542307 AND AD_Language IN ('de_DE', 'de_CH');

-- English override.
UPDATE AD_Menu_Trl SET
    Name = 'Intrastat - Detail (Debug)',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-31 13:00:03','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
 WHERE AD_Menu_ID = 542307 AND AD_Language = 'en_US';
