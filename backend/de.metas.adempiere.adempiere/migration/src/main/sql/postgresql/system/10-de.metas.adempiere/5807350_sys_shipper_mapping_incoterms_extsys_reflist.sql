-- nShift: new mapping attribute source values IncotermsValue / ExternalSystemValue / TopLevelType
-- Added to AD_Reference 542001 ("Mapping Attribut Wert")

-- AD_Ref_List: IncotermsValue
-- 2026-06-03T10:00:51.000Z
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Ref_List_ID, AD_Reference_ID,
                         Created, CreatedBy, Description, EntityType, IsActive, Name,
                         Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 544241 /*From ID Server*/, 542001,
        TO_TIMESTAMP('2026-06-03 10:00:51', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        NULL, 'D', 'Y', 'Incoterms (Auftrag)',
        TO_TIMESTAMP('2026-06-03 10:00:51', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'IncotermsValue', 'IncotermsValue');

-- AD_Ref_List_Trl skeleton for IncotermsValue
-- 2026-06-03T10:00:52.000Z
INSERT INTO AD_Ref_List_Trl (AD_Client_ID, AD_Org_ID, AD_Language, AD_Ref_List_ID,
                              Created, CreatedBy, Description, IsActive, IsTranslated, Name,
                              Updated, UpdatedBy)
SELECT 0, 0, l.AD_Language, 544241 /*From ID Server*/,
       TO_TIMESTAMP('2026-06-03 10:00:52', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
       NULL, 'Y', 'N', 'Incoterms (Auftrag)',
       TO_TIMESTAMP('2026-06-03 10:00:52', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100
FROM AD_Language l WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Ref_List_ID = 544241 AND tt.AD_Language = l.AD_Language);

-- English translation for IncotermsValue
-- 2026-06-03T10:00:53.000Z
UPDATE AD_Ref_List_Trl SET Name = 'Incoterms (Order)', IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-06-03 10:00:53', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
WHERE AD_Ref_List_ID = 544241 AND AD_Language = 'en_US';

-- mark German translations as actively translated (same text as the German base)
-- 2026-06-03T10:00:57.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-06-03 10:00:57', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
WHERE AD_Ref_List_ID = 544241 AND AD_Language IN ('de_DE', 'de_CH');

-- AD_Ref_List: ExternalSystemValue
-- 2026-06-03T10:00:54.000Z
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Ref_List_ID, AD_Reference_ID,
                         Created, CreatedBy, Description, EntityType, IsActive, Name,
                         Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 544242 /*From ID Server*/, 542001,
        TO_TIMESTAMP('2026-06-03 10:00:54', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        NULL, 'D', 'Y', 'Externes System (Auftrag)',
        TO_TIMESTAMP('2026-06-03 10:00:54', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'ExternalSystemValue', 'ExternalSystemValue');

-- AD_Ref_List_Trl skeleton for ExternalSystemValue
-- 2026-06-03T10:00:55.000Z
INSERT INTO AD_Ref_List_Trl (AD_Client_ID, AD_Org_ID, AD_Language, AD_Ref_List_ID,
                              Created, CreatedBy, Description, IsActive, IsTranslated, Name,
                              Updated, UpdatedBy)
SELECT 0, 0, l.AD_Language, 544242 /*From ID Server*/,
       TO_TIMESTAMP('2026-06-03 10:00:55', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
       NULL, 'Y', 'N', 'Externes System (Auftrag)',
       TO_TIMESTAMP('2026-06-03 10:00:55', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100
FROM AD_Language l WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Ref_List_ID = 544242 AND tt.AD_Language = l.AD_Language);

-- English translation for ExternalSystemValue
-- 2026-06-03T10:00:56.000Z
UPDATE AD_Ref_List_Trl SET Name = 'External System (Order)', IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-06-03 10:00:56', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
WHERE AD_Ref_List_ID = 544242 AND AD_Language = 'en_US';

-- mark German translations as actively translated (same text as the German base)
-- 2026-06-03T10:00:58.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-06-03 10:00:58', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
WHERE AD_Ref_List_ID = 544242 AND AD_Language IN ('de_DE', 'de_CH');

-- AD_Ref_List: TopLevelType (HU unit type LU/TU/CU) — emitted on ship AND all advises
-- 2026-06-03T10:00:59.000Z
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Ref_List_ID, AD_Reference_ID,
                         Created, CreatedBy, Description, EntityType, IsActive, Name,
                         Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 544263 /*From ID Server*/, 542001,
        TO_TIMESTAMP('2026-06-03 10:00:59', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        NULL, 'D', 'Y', 'HU-Typ',
        TO_TIMESTAMP('2026-06-03 10:00:59', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'TopLevelType', 'TopLevelType');

-- AD_Ref_List_Trl skeleton for TopLevelType
-- 2026-06-03T10:01:00.000Z
INSERT INTO AD_Ref_List_Trl (AD_Client_ID, AD_Org_ID, AD_Language, AD_Ref_List_ID,
                              Created, CreatedBy, Description, IsActive, IsTranslated, Name,
                              Updated, UpdatedBy)
SELECT 0, 0, l.AD_Language, 544263 /*From ID Server*/,
       TO_TIMESTAMP('2026-06-03 10:01:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
       NULL, 'Y', 'N', 'HU-Typ',
       TO_TIMESTAMP('2026-06-03 10:01:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100
FROM AD_Language l WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Ref_List_ID = 544263 AND tt.AD_Language = l.AD_Language);

-- English translation for TopLevelType
-- 2026-06-03T10:01:01.000Z
UPDATE AD_Ref_List_Trl SET Name = 'HU Type', IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-06-03 10:01:01', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
WHERE AD_Ref_List_ID = 544263 AND AD_Language = 'en_US';

-- mark German translations as actively translated (same text as the German base)
-- 2026-06-03T10:01:02.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-06-03 10:01:02', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
WHERE AD_Ref_List_ID = 544263 AND AD_Language IN ('de_DE', 'de_CH');
