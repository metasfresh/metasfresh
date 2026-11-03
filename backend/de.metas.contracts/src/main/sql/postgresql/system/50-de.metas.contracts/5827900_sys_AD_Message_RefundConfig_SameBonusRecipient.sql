-- Error message: all refund configs of one contract condition must have the same bonus recipient.
-- IDs allocated from the central ID server on 2026-10-05: AD_Message 545895
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545895 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-05 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'Y',
        'Alle Rückvergütungskonfigurationen einer Vertragsbedingung müssen denselben Bonusempfänger haben.', 'E',
        TO_TIMESTAMP('2026-10-05 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.constracts.refund.C_Flatrate_RefundConfig_SameBonusRecipient')
;

UPDATE AD_Message
SET ErrorCode='REFUND_CONFIG_SAME_BONUS_RECIPIENT', Updated=TO_TIMESTAMP('2026-10-05 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545895
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545895
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='All refund configurations of one contract condition must have the same bonus recipient.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-05 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545895
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-05 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545895
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-05 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545895
;
