-- MobileUI Picking: refusal message when the picked quantity would exceed the ordered quantity of the picking line.
-- Creates AD_Message de.metas.handlingunits.picking.job.PICK_OVERDELIVERY_BLOCKED_ERROR_MSG (ErrorCode PICK_OVERDELIVERY_BLOCKED), used by the picking job command.

-- Value: de.metas.handlingunits.picking.job.PICK_OVERDELIVERY_BLOCKED_ERROR_MSG
-- 2026-10-29 12:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545973 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-29 12:00','YYYY-MM-DD HH24:MI'),100,'D','Y','Es kann nicht mehr als die beauftragte Menge kommissioniert werden.','E','PICK_OVERDELIVERY_BLOCKED',TO_TIMESTAMP('2026-10-29 12:00','YYYY-MM-DD HH24:MI'),100,'de.metas.handlingunits.picking.job.PICK_OVERDELIVERY_BLOCKED_ERROR_MSG')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545973
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Cannot pick more than the ordered quantity for this line.',Updated=TO_TIMESTAMP('2026-10-29 12:00','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545973
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-29 12:00','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545973
;
