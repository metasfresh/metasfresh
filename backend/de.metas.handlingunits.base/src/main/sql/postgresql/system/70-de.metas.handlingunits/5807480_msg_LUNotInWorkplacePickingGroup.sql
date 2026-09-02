-- Run mode: SWING_CLIENT
-- IDs allocated from idserver.metas.de on 2026-06-12:
--   AD_Message  545753  (de.metas.handlingunits.picking.massprinting.LUNotInWorkplacePickingGroup)

-- AD_Message: MassPrinting / picking guard — fired when a scanned LU's locator is not in the
-- picker's workplace picking warehouse group. The server rejects any scan of an LU that does
-- not belong to the workplace's picking group, regardless of how the request arrived.
-- MsgType=E because this is a user-visible error that must surface in the mobile UI.
-- 2026-06-12T12:00:00Z
INSERT INTO AD_Message
    (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive,
     MsgText, MsgTip, MsgType, ErrorCode, Updated, UpdatedBy, Value)
VALUES
    (0, 545753 /*From ID Server*/, 0,
     TO_TIMESTAMP('2026-06-12 12:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
     100, 'D', 'Y',
     'Die gescannte Ladeeinheit gehört nicht zur Kommissionier-Lagergruppe des Arbeitsplatzes.',
     NULL,
     'E',
     'LUNotInWorkplacePickingGroup',
     TO_TIMESTAMP('2026-06-12 12:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
     100,
     'de.metas.handlingunits.picking.massprinting.LUNotInWorkplacePickingGroup')
;

-- Seed translation rows for all active system languages (copies DE base text)
-- 2026-06-12T12:00:01Z
INSERT INTO AD_Message_Trl
    (AD_Language, AD_Message_ID, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     IsActive, IsTranslated, MsgText, MsgTip)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.AD_Client_ID,
       t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-12 12:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100,
       TO_TIMESTAMP('2026-06-12 12:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100,
       'Y',
       'N',
       t.MsgText,
       t.MsgTip
FROM AD_Language l,
     AD_Message  t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Message_ID = 545753
  AND NOT EXISTS (SELECT 1
                  FROM AD_Message_Trl tt
                  WHERE tt.AD_Language    = l.AD_Language
                    AND tt.AD_Message_ID  = t.AD_Message_ID)
;

-- Override en_US with English translation
-- 2026-06-12T12:00:02Z
UPDATE AD_Message_Trl
SET    MsgText        = 'The scanned LU does not belong to the workplace''s picking warehouse group.',
       IsTranslated   = 'Y',
       Updated        = TO_TIMESTAMP('2026-06-12 12:00:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy      = 100
WHERE  AD_Language    = 'en_US'
  AND  AD_Message_ID  = 545753
;

-- Mark de_DE as actively translated (same text as base)
-- 2026-06-12T12:00:03Z
UPDATE AD_Message_Trl
SET    IsTranslated   = 'Y',
       Updated        = TO_TIMESTAMP('2026-06-12 12:00:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy      = 100
WHERE  AD_Language    = 'de_DE'
  AND  AD_Message_ID  = 545753
;

-- Mark de_CH as actively translated (same text as base)
-- 2026-06-12T12:00:04Z
UPDATE AD_Message_Trl
SET    IsTranslated   = 'Y',
       Updated        = TO_TIMESTAMP('2026-06-12 12:00:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy      = 100
WHERE  AD_Language    = 'de_CH'
  AND  AD_Message_ID  = 545753
;
