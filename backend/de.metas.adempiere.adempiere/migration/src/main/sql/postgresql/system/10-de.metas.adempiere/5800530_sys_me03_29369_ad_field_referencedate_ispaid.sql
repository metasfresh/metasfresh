-- 2026-04-30 https://github.com/metasfresh/me03/issues/29369
-- AD_Field rows for ReferenceDate and IsPaid on tabs 548449 (window 181 "Bestellung_OLD")
-- and 548450 (window 541889 "Bestellung").
--
-- Tab 548450 exists only on dev DBs — no committed metasfresh migration creates it,
-- so the CI seed lacks it (same pattern noted in 5800240 for tabs 549067/548567).
-- Use SELECT FROM AD_Tab WHERE AD_Tab_ID = X — when the tab is missing, the SELECT
-- returns 0 rows and the INSERT is a graceful no-op. The bulk VALUES form would
-- fail with FK constraint violation on CI.

-- ReferenceDate on tab 548449 (Bestellung_OLD)
INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Name_ID, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, SeqNo, SeqNoGrid, SortNo, EntityType)
SELECT 777476, t.AD_Tab_ID, 592457, 0, 0, 'Y', '2026-04-30 12:00', 0, '2026-04-30 12:00', 0,
       NULL, 'Y', 'Y', 'Y', 'N', 30, 30, 0, 'D'
FROM AD_Tab t
WHERE t.AD_Tab_ID = 548449
  AND NOT EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 777476);

-- ReferenceDate on tab 548450 (Bestellung)
INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Name_ID, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, SeqNo, SeqNoGrid, SortNo, EntityType)
SELECT 777477, t.AD_Tab_ID, 592457, 0, 0, 'Y', '2026-04-30 12:00', 0, '2026-04-30 12:00', 0,
       NULL, 'Y', 'Y', 'Y', 'N', 30, 30, 0, 'D'
FROM AD_Tab t
WHERE t.AD_Tab_ID = 548450
  AND NOT EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 777477);

-- IsPaid on tab 548449 (Bestellung_OLD)
INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Name_ID, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, SeqNo, SeqNoGrid, SortNo, EntityType)
SELECT 777478, t.AD_Tab_ID, 592458, 0, 0, 'Y', '2026-04-30 12:00', 0, '2026-04-30 12:00', 0,
       NULL, 'Y', 'Y', 'Y', 'N', 35, 35, 0, 'D'
FROM AD_Tab t
WHERE t.AD_Tab_ID = 548449
  AND NOT EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 777478);

-- IsPaid on tab 548450 (Bestellung)
INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Name_ID, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, SeqNo, SeqNoGrid, SortNo, EntityType)
SELECT 777479, t.AD_Tab_ID, 592458, 0, 0, 'Y', '2026-04-30 12:00', 0, '2026-04-30 12:00', 0,
       NULL, 'Y', 'Y', 'Y', 'N', 35, 35, 0, 'D'
FROM AD_Tab t
WHERE t.AD_Tab_ID = 548450
  AND NOT EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 777479);

-- Trl skeleton seeds for any field rows that actually got inserted above.
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Description, Help, IsTranslated)
  SELECT f.AD_Field_ID, l.AD_Language, f.AD_Client_ID, f.AD_Org_ID, f.IsActive, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy,
         c.Name, c.Description, NULL, 'N'
    FROM AD_Field f
    JOIN AD_Column c ON f.AD_Column_ID = c.AD_Column_ID
    JOIN AD_Language l ON l.IsSystemLanguage = 'Y'
   WHERE f.AD_Field_ID IN (777476, 777477, 777478, 777479)
     AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl WHERE AD_Field_ID = f.AD_Field_ID AND AD_Language = l.AD_Language);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584813);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1402); -- existing IsPaid element
