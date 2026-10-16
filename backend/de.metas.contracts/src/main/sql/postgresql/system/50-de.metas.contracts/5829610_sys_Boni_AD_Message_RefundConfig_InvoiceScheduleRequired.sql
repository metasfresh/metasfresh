-- Error message: a refund config that is NOT deducted at payment needs an invoice schedule.
-- The C_InvoiceSchedule_ID column is no longer physically NOT NULL (it is optional for a config deducted
-- at payment), so this model-level validation replaces the dropped DB constraint for every other config.
-- IDs allocated from the central ID server on 2026-10-16: AD_Message 545953
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545953 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-16 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'Y',
        'Ein Rechnungs-Terminplan ist erforderlich, es sei denn, der Bonus wird bei Zahlung abgezogen.', 'E',
        TO_TIMESTAMP('2026-10-16 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.contracts.refund.C_Flatrate_RefundConfig_InvoiceScheduleRequired')
;

UPDATE AD_Message
SET ErrorCode='REFUND_CONFIG_INVOICE_SCHEDULE_REQUIRED', Updated=TO_TIMESTAMP('2026-10-16 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545953
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545953
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='An invoice schedule is required unless the bonus is deducted at payment.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-16 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545953
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545953
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545953
;
