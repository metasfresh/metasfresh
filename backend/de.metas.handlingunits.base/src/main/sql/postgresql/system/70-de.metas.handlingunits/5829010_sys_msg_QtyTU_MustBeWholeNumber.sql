-- Run mode: SWING_CLIENT
-- IDs allocated from idserver.metas.de on 2026-10-09:
--   AD_Message  545920  (de.metas.handlingunits.QtyTU_MustBeWholeNumber)

-- AD_Message: a TU quantity is always a whole number. Raised (as user validation error) by HUPackingAwareBL
-- when a fractional TU quantity (e.g. 1.5) would otherwise be silently truncated while computing the CU quantity.
-- {0} = the entered TU quantity
-- 2026-10-09T10:00:00Z
INSERT INTO AD_Message
    (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive,
     MsgText, MsgTip, MsgType, ErrorCode, Updated, UpdatedBy, Value)
VALUES
    (0, 545920 /*From ID Server*/, 0,
     TO_TIMESTAMP('2026-10-09 10:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
     100, 'de.metas.handlingunits', 'Y',
     'Die TU-Menge muss eine ganze Zahl sein (Wert: {0}).',
     NULL,
     'E',
     'QtyTU_MustBeWholeNumber',
     TO_TIMESTAMP('2026-10-09 10:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
     100,
     'de.metas.handlingunits.QtyTU_MustBeWholeNumber')
;

-- 2026-10-09T10:00:01Z
INSERT INTO AD_Message_Trl
    (AD_Language, AD_Message_ID, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     IsActive, IsTranslated, MsgText, MsgTip)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.AD_Client_ID,
       t.AD_Org_ID,
       TO_TIMESTAMP('2026-10-09 10:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100,
       TO_TIMESTAMP('2026-10-09 10:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100,
       'Y',
       'N',
       t.MsgText,
       t.MsgTip
FROM AD_Language l,
     AD_Message  t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Message_ID = 545920
  AND NOT EXISTS (SELECT 1
                  FROM AD_Message_Trl tt
                  WHERE tt.AD_Language   = l.AD_Language
                    AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- 2026-10-09T10:00:02Z
UPDATE AD_Message_Trl
SET    MsgText      = 'The TU quantity must be a whole number (value: {0}).',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-10-09 10:00:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy    = 100
WHERE  AD_Language  = 'en_US'
  AND  AD_Message_ID = 545920
;

-- 2026-10-09T10:00:03Z
UPDATE AD_Message_Trl
SET    MsgText      = 'Die TU-Menge muss eine ganze Zahl sein (Wert: {0}).',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-10-09 10:00:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy    = 100
WHERE  AD_Language  = 'de_DE'
  AND  AD_Message_ID = 545920
;

-- 2026-10-09T10:00:04Z
UPDATE AD_Message_Trl
SET    MsgText      = 'Die TU-Menge muss eine ganze Zahl sein (Wert: {0}).',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-10-09 10:00:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy    = 100
WHERE  AD_Language  = 'de_CH'
  AND  AD_Message_ID = 545920
;
