-- Run mode: SWING_CLIENT

-- Value: de.metas.ui.web.bpartner.quickinput.MSG_NO_BUSINESS_PARTNER_SELECTED
-- 2026-03-05T12:46:05.584Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545639, 0, TO_TIMESTAMP('2026-03-05 12:46:05.301000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'D', 'Y', 'Kein Geschäftspartner ausgewählt.', 'E', TO_TIMESTAMP('2026-03-05 12:46:05.301000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'de.metas.ui.web.bpartner.quickinput.MSG_NO_BUSINESS_PARTNER_SELECTED')
;

-- 2026-03-05T12:46:05.592Z
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.MsgText,
       t.MsgTip,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Message_ID = 545639
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.ui.web.bpartner.quickinput.MSG_NO_BUSINESS_PARTNER_SELECTED
-- 2026-03-05T12:46:21.086Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', MsgText='No business partner selected.', Updated=TO_TIMESTAMP('2026-03-05 12:46:21.085000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545639
;

-- 2026-03-05T12:46:21.087Z
UPDATE AD_Message base
SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Message_Trl trl
WHERE trl.AD_Message_ID = base.AD_Message_ID
  AND trl.AD_Language = 'en_US'
  AND trl.AD_Language = getBaseLanguage()
;

