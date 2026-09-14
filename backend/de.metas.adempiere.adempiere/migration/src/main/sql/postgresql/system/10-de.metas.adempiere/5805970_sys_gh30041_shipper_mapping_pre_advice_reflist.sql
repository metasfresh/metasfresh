-- nShift: new mapping attribute source value IsPreAdviceRequired
-- Added to AD_Reference 542001 ("Mapping Attribut Wert")

-- AD_Ref_List: IsPreAdviceRequired
-- 2026-06-03T10:00:57.000Z
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Ref_List_ID, AD_Reference_ID,
                         Created, CreatedBy, Description, EntityType, IsActive, Name,
                         Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 544243 /*From ID Server*/, 542001,
        TO_TIMESTAMP('2026-06-03 10:00:57', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        NULL, 'D', 'Y', 'Voranmeldung erforderlich (Auftrag)',
        TO_TIMESTAMP('2026-06-03 10:00:57', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'IsPreAdviceRequired', 'IsPreAdviceRequired');

-- AD_Ref_List_Trl skeleton for IsPreAdviceRequired
-- 2026-06-03T10:00:58.000Z
INSERT INTO AD_Ref_List_Trl (AD_Client_ID, AD_Org_ID, AD_Language, AD_Ref_List_ID,
                              Created, CreatedBy, Description, IsActive, IsTranslated, Name,
                              Updated, UpdatedBy)
SELECT 0, 0, l.AD_Language, 544243 /*From ID Server*/,
       TO_TIMESTAMP('2026-06-03 10:00:58', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
       NULL, 'Y', 'N', 'Voranmeldung erforderlich (Auftrag)',
       TO_TIMESTAMP('2026-06-03 10:00:58', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100
FROM AD_Language l WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Ref_List_ID = 544243 AND tt.AD_Language = l.AD_Language);

-- English translation for IsPreAdviceRequired
-- 2026-06-03T10:00:59.000Z
UPDATE AD_Ref_List_Trl SET Name = 'Pre-Advice Required (Order)', IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-06-03 10:00:59', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
WHERE AD_Ref_List_ID = 544243 AND AD_Language = 'en_US';

-- German languages inherit base Name; mark as translated
-- 2026-06-03T10:01:00.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-06-03 10:01:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
WHERE AD_Ref_List_ID = 544243 AND AD_Language IN ('de_DE', 'de_CH');
