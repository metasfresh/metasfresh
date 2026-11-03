-- Error message: refund periods are calendar periods, so a monthly invoice schedule of a refund line needs a distance that divides the year.
-- IDs allocated from the central ID server on 2026-10-07: AD_Message 545911
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545911 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-07 09:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'Y',
        'Rückvergütungsperioden sind Kalenderperioden: Der Abstand eines monatlichen Rechnungsterminplans muss 1, 2, 3, 4, 6 oder 12 Monate betragen.', 'E',
        TO_TIMESTAMP('2026-10-07 09:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.constracts.refund.C_Flatrate_RefundConfig_CalendarInvoiceDistance')
;

UPDATE AD_Message
SET ErrorCode='REFUND_CONFIG_CALENDAR_INVOICE_DISTANCE', Updated=TO_TIMESTAMP('2026-10-07 09:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545911
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545911
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='Refund periods are calendar periods: the distance of a monthly invoice schedule must be 1, 2, 3, 4, 6 or 12 months.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-07 09:10:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545911
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 09:10:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545911
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 09:10:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545911
;
