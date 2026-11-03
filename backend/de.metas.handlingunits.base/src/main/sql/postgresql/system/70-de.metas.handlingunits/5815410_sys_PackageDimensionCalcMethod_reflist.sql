-- Adds the PackageDimensionCalcMethod reference list (AD_Reference, ValidationType='L')
-- with three entries: Strapping (S), Repacking (R), Nesting (N).
-- Used by the PackageDimensionCalcMethod column on M_HU_PI_Version (added in a later task)
-- to configure how multi-item TU parcel dimensions are calculated.
--
-- IDs allocated from idserver.metas.de on 2026-07-22:
--   AD_Reference  542122 /*From ID Server*/  (PackageDimensionCalcMethod list)
--   AD_Ref_List   544321 /*From ID Server*/  (S  = Strapping / Bändern)
--   AD_Ref_List   544322 /*From ID Server*/  (R  = Repacking / Umverpacken)
--   AD_Ref_List   544323 /*From ID Server*/  (N  = Nesting   / Verschachteln)

-- ============================================================
-- 1. AD_Reference (ValidationType='L')
-- ============================================================
INSERT INTO AD_Reference
  (AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy,
   AD_Reference_ID, ValidationType, Name, IsOrderByValue, EntityType)
VALUES
  (0, 0, 'Y',
   TO_TIMESTAMP('2026-07-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-07-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   542122 /*From ID Server*/, 'L', 'PackageDimensionCalcMethod', 'N', 'D');

-- ============================================================
-- 2. AD_Reference_Trl skeleton for all active system languages
-- ============================================================
INSERT INTO AD_Reference_Trl
  (AD_Language, AD_Reference_ID, Name, Help, Description,
   IsTranslated, AD_Client_ID, AD_Org_ID,
   Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, t.AD_Reference_ID, t.Name, t.Help, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID,
  t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Reference_ID = 542122
  AND NOT EXISTS (
    SELECT 1 FROM AD_Reference_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID
  );

-- Mark de_DE and de_CH as translated (German is the base name)
UPDATE AD_Reference_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-22 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Reference_ID = 542122
  AND AD_Language IN ('de_DE', 'de_CH');

-- Override en_US name to English
UPDATE AD_Reference_Trl
SET IsTranslated = 'Y',
    Name         = 'Package Dimension Calc Method',
    Updated      = TO_TIMESTAMP('2026-07-22 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Reference_ID = 542122
  AND AD_Language = 'en_US';

-- ============================================================
-- 3. AD_Ref_List — Strapping (Value='S')
-- ============================================================
INSERT INTO AD_Ref_List
  (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy,
   AD_Ref_List_ID, Value, ValueName, Name, Description, EntityType)
VALUES
  (542122, 0, 0, 'Y',
   TO_TIMESTAMP('2026-07-22 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-07-22 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
   544321 /*From ID Server*/, 'S', 'Strapping', 'Bändern', 'TU-Maße: Stapelachse = Summe(kleinste Kante x Menge); übrige zwei Kanten = Maximum je Achse.', 'D');

-- AD_Ref_List_Trl skeleton for Strapping
INSERT INTO AD_Ref_List_Trl
  (AD_Language, AD_Ref_List_ID, Name, Description,
   IsTranslated, AD_Client_ID, AD_Org_ID,
   Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID,
  t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Ref_List_ID = 544321
  AND NOT EXISTS (
    SELECT 1 FROM AD_Ref_List_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID
  );

UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-22 10:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 544321
  AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y',
    Name         = 'Strapping',
    Description  = 'TU dimensions: stacking axis = sum(smallest edge x qty); other two edges = max per axis.',
    Updated      = TO_TIMESTAMP('2026-07-22 10:00:05', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 544321
  AND AD_Language = 'en_US';

-- ============================================================
-- 4. AD_Ref_List — Repacking (Value='R')
-- ============================================================
INSERT INTO AD_Ref_List
  (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy,
   AD_Ref_List_ID, Value, ValueName, Name, Description, EntityType)
VALUES
  (542122, 0, 0, 'Y',
   TO_TIMESTAMP('2026-07-22 10:00:06', 'YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-07-22 10:00:06', 'YYYY-MM-DD HH24:MI:SS'), 100,
   544322 /*From ID Server*/, 'R', 'Repacking', 'Umverpacken', 'TU-Maße aus dem Gesamtvolumen (x 1,05) neu berechnet.', 'D');

-- AD_Ref_List_Trl skeleton for Repacking
INSERT INTO AD_Ref_List_Trl
  (AD_Language, AD_Ref_List_ID, Name, Description,
   IsTranslated, AD_Client_ID, AD_Org_ID,
   Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID,
  t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Ref_List_ID = 544322
  AND NOT EXISTS (
    SELECT 1 FROM AD_Ref_List_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID
  );

UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-22 10:00:07', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 544322
  AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y',
    Name         = 'Repacking',
    Description  = 'TU dimensions recomputed from total volume (x 1.05).',
    Updated      = TO_TIMESTAMP('2026-07-22 10:00:08', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 544322
  AND AD_Language = 'en_US';

-- ============================================================
-- 5. AD_Ref_List — Nesting (Value='N')
-- ============================================================
INSERT INTO AD_Ref_List
  (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy,
   AD_Ref_List_ID, Value, ValueName, Name, Description, EntityType)
VALUES
  (542122, 0, 0, 'Y',
   TO_TIMESTAMP('2026-07-22 10:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-07-22 10:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100,
   544323 /*From ID Server*/, 'N', 'Nesting', 'Verschachteln', 'TU übernimmt die Maße des Artikels mit der größten Einzelkante.', 'D');

-- AD_Ref_List_Trl skeleton for Nesting
INSERT INTO AD_Ref_List_Trl
  (AD_Language, AD_Ref_List_ID, Name, Description,
   IsTranslated, AD_Client_ID, AD_Org_ID,
   Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID,
  t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Ref_List_ID = 544323
  AND NOT EXISTS (
    SELECT 1 FROM AD_Ref_List_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID
  );

UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-22 10:00:10', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 544323
  AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y',
    Name         = 'Nesting',
    Description  = 'TU takes the dimensions of the item with the largest single edge.',
    Updated      = TO_TIMESTAMP('2026-07-22 10:00:11', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 544323
  AND AD_Language = 'en_US';
