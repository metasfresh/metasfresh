-- Run mode: SWING_CLIENT
-- IDs allocated from idserver.metas.de on 2026-07-23:
--   AD_Message  545785  (M_HU_PI_Version_CalcMethodOnlyOnTU)

-- AD_Message: guard fired when PackageDimensionCalcMethod is set on a non-TU
-- packing instruction version (LU, VHU, or null HU unit type). Only Transport
-- Unit (TU) versions may carry a dimension calculation method.
-- MsgType=E because this is a user-validation error surfaced by the model interceptor.
-- EntityType=de.metas.handlingunits (module-owned message, not a core dictionary entry).
-- 2026-07-23T12:00:00Z
INSERT INTO AD_Message
    (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive,
     MsgText, MsgTip, MsgType, ErrorCode, Updated, UpdatedBy, Value)
VALUES
    (0, 545785 /*From ID Server*/, 0,
     TO_TIMESTAMP('2026-07-23 12:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
     100, 'de.metas.handlingunits', 'Y',
     'Die Maßberechnungsmethode kann nur auf Transporteinheit-(TU-)Packvorschriftsversionen gesetzt werden.',
     NULL,
     'E',
     'M_HU_PI_Version_CalcMethodOnlyOnTU',
     TO_TIMESTAMP('2026-07-23 12:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
     100,
     'M_HU_PI_Version_CalcMethodOnlyOnTU')
;

-- Seed translation rows for all active system languages (copies DE base text)
-- 2026-07-23T12:00:01Z
INSERT INTO AD_Message_Trl
    (AD_Language, AD_Message_ID, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     IsActive, IsTranslated, MsgText, MsgTip)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.AD_Client_ID,
       t.AD_Org_ID,
       TO_TIMESTAMP('2026-07-23 12:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100,
       TO_TIMESTAMP('2026-07-23 12:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100,
       'Y',
       'N',
       t.MsgText,
       t.MsgTip
FROM AD_Language l,
     AD_Message  t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Message_ID = 545785
  AND NOT EXISTS (SELECT 1
                  FROM AD_Message_Trl tt
                  WHERE tt.AD_Language   = l.AD_Language
                    AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Override en_US with English translation
-- 2026-07-23T12:00:02Z
UPDATE AD_Message_Trl
SET    MsgText      = 'The package dimension calculation method can only be set on Transport Unit (TU) packing instruction versions.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-07-23 12:00:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy    = 100
WHERE  AD_Language  = 'en_US'
  AND  AD_Message_ID = 545785
;

-- Mark de_DE as actively translated (same text as base)
-- 2026-07-23T12:00:03Z
UPDATE AD_Message_Trl
SET    IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-07-23 12:00:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy    = 100
WHERE  AD_Language  = 'de_DE'
  AND  AD_Message_ID = 545785
;

-- Mark de_CH as actively translated (same text as base)
-- 2026-07-23T12:00:04Z
UPDATE AD_Message_Trl
SET    IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-07-23 12:00:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy    = 100
WHERE  AD_Language  = 'de_CH'
  AND  AD_Message_ID = 545785
;
