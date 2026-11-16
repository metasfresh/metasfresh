-- Run mode: SWING_CLIENT
-- IDs allocated from idserver.metas.de on 2026-06-07:
--   AD_Message  545747  (de.metas.handlingunits.picking.massprinting.MassPrintingNotEnabled)

-- AD_Message: MassPrinting guard — fired when the picker's picking profile does not have
-- mass-printing enabled (IsMassPrinting=N). The server rejects any scan attempt for
-- callers whose profile disables the feature, regardless of how the request arrived.
-- MsgType=E because this is a user-visible error that must surface in the mobile UI.
-- 2026-06-07T12:00:00Z
INSERT INTO AD_Message
    (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive,
     MsgText, MsgTip, MsgType, ErrorCode, Updated, UpdatedBy, Value)
VALUES
    (0, 545747 /*From ID Server*/, 0,
     TO_TIMESTAMP('2026-06-07 12:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
     100, 'D', 'Y',
     'Massendruck ist in Ihrem Kommissionierprofil nicht aktiviert.',
     NULL,
     'E',
     'MassPrintingNotEnabled',
     TO_TIMESTAMP('2026-06-07 12:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
     100,
     'de.metas.handlingunits.picking.massprinting.MassPrintingNotEnabled')
;

-- Seed translation rows for all active system languages (copies DE base text)
-- 2026-06-07T12:00:01Z
INSERT INTO AD_Message_Trl
    (AD_Language, AD_Message_ID, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     IsActive, IsTranslated, MsgText, MsgTip)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.AD_Client_ID,
       t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-07 12:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100,
       TO_TIMESTAMP('2026-06-07 12:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100,
       'Y',
       'N',
       t.MsgText,
       t.MsgTip
FROM AD_Language l,
     AD_Message  t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Message_ID = 545747
  AND NOT EXISTS (SELECT 1
                  FROM AD_Message_Trl tt
                  WHERE tt.AD_Language    = l.AD_Language
                    AND tt.AD_Message_ID  = t.AD_Message_ID)
;

-- Override en_US with English translation
-- 2026-06-07T12:00:02Z
UPDATE AD_Message_Trl
SET    MsgText        = 'Mass printing is not enabled in your picking profile.',
       IsTranslated   = 'Y',
       Updated        = TO_TIMESTAMP('2026-06-07 12:00:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy      = 100
WHERE  AD_Language    = 'en_US'
  AND  AD_Message_ID  = 545747
;

-- Mark de_DE as actively translated (same text as base)
-- 2026-06-07T12:00:03Z
UPDATE AD_Message_Trl
SET    IsTranslated   = 'Y',
       Updated        = TO_TIMESTAMP('2026-06-07 12:00:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy      = 100
WHERE  AD_Language    = 'de_DE'
  AND  AD_Message_ID  = 545747
;

-- Mark de_CH as actively translated (same text as base)
-- 2026-06-07T12:00:04Z
UPDATE AD_Message_Trl
SET    IsTranslated   = 'Y',
       Updated        = TO_TIMESTAMP('2026-06-07 12:00:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy      = 100
WHERE  AD_Language    = 'de_CH'
  AND  AD_Message_ID  = 545747
;
