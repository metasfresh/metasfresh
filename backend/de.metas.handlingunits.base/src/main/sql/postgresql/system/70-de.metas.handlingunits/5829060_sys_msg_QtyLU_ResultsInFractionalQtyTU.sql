-- Run mode: SWING_CLIENT
-- IDs allocated from idserver.metas.de on 2026-10-09:
--   AD_MigrationScript  582906 -> script prefix 5829060
--   AD_Message          545921  (de.metas.handlingunits.QtyLU_ResultsInFractionalQtyTU)

-- AD_Message: a TU quantity is always a whole number. Raised (as user validation error) when an LU quantity
-- is entered that results in a fractional TU quantity (e.g. 0.25 LU x 10 TU per LU = 2.5 TU), so that the
-- user is pointed at the LU quantity that caused it.
-- {0} = the entered LU quantity, {1} = TUs per LU, {2} = the resulting TU quantity
-- 2026-10-09T14:00:00Z
INSERT INTO AD_Message
    (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive,
     MsgText, MsgTip, MsgType, ErrorCode, Updated, UpdatedBy, Value)
VALUES
    (0, 545921 /*From ID Server*/, 0,
     TO_TIMESTAMP('2026-10-09 14:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
     100, 'de.metas.handlingunits', 'Y',
     'Die LU-Menge {0} ergibt bei {1} TU je LU {2} TU; die TU-Menge muss eine ganze Zahl sein. Bitte die LU-Menge anpassen.',
     NULL,
     'E',
     'QtyLU_ResultsInFractionalQtyTU',
     TO_TIMESTAMP('2026-10-09 14:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
     100,
     'de.metas.handlingunits.QtyLU_ResultsInFractionalQtyTU')
;

-- 2026-10-09T14:00:01Z
INSERT INTO AD_Message_Trl
    (AD_Language, AD_Message_ID, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     IsActive, IsTranslated, MsgText, MsgTip)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.AD_Client_ID,
       t.AD_Org_ID,
       TO_TIMESTAMP('2026-10-09 14:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100,
       TO_TIMESTAMP('2026-10-09 14:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100,
       'Y',
       'N',
       t.MsgText,
       t.MsgTip
FROM AD_Language l,
     AD_Message  t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Message_ID = 545921
  AND NOT EXISTS (SELECT 1
                  FROM AD_Message_Trl tt
                  WHERE tt.AD_Language   = l.AD_Language
                    AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- 2026-10-09T14:00:02Z
UPDATE AD_Message_Trl
SET    MsgText      = 'The LU quantity {0} at {1} TUs per LU results in {2} TUs; the TU quantity must be a whole number. Please adjust the LU quantity.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-10-09 14:00:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy    = 100
WHERE  AD_Language  = 'en_US'
  AND  AD_Message_ID = 545921
;

-- 2026-10-09T14:00:03Z
UPDATE AD_Message_Trl
SET    MsgText      = 'Die LU-Menge {0} ergibt bei {1} TU je LU {2} TU; die TU-Menge muss eine ganze Zahl sein. Bitte die LU-Menge anpassen.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-10-09 14:00:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy    = 100
WHERE  AD_Language  = 'de_DE'
  AND  AD_Message_ID = 545921
;

-- 2026-10-09T14:00:04Z
UPDATE AD_Message_Trl
SET    MsgText      = 'Die LU-Menge {0} ergibt bei {1} TU je LU {2} TU; die TU-Menge muss eine ganze Zahl sein. Bitte die LU-Menge anpassen.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-10-09 14:00:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy    = 100
WHERE  AD_Language  = 'de_CH'
  AND  AD_Message_ID = 545921
;
