-- 2026-04-29
-- AD_Message — Order_Reactivate_Blocked_By_PaySchedule_Activity
-- iter-3 https://github.com/metasfresh/me03/issues/29369 (TODO-1 — C_Order_PayScheduleReactivateGuard interceptor)

-- AD_Message
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y',
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        'E',
        'Order_Reactivate_Blocked_By_PaySchedule_Activity',
        'Cannot reactivate the order because at least one payment-schedule row is no longer Pending. Please reverse the receipts / invoices / payments first.',
        'D',
        545676 /*From ID Server*/);

-- Set ErrorCode (required for MsgType='E') — must fit AD_Message.ErrorCode varchar(40)
UPDATE AD_Message
SET ErrorCode = 'ORDER_REACTIVATE_BLOCKED_BY_PAYSCHED'
WHERE AD_Message_ID = 545676;

-- AD_Message_Trl: de_DE
INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y',
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        545676 /*From ID Server*/,
        'Beleg kann nicht reaktiviert werden, da mindestens eine Zeile im Zahlungsplan nicht mehr Pending ist. Bitte zuerst die Wareneingänge / Rechnungen / Zahlungen stornieren.',
        NULL, 'N');

-- AD_Message_Trl: de_CH
INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y',
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        545676 /*From ID Server*/,
        'Beleg kann nicht reaktiviert werden, da mindestens eine Zeile im Zahlungsplan nicht mehr Pending ist. Bitte zuerst die Wareneingänge / Rechnungen / Zahlungen stornieren.',
        NULL, 'N');

-- AD_Message_Trl: en_US
INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y',
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        545676 /*From ID Server*/,
        'Cannot reactivate the order because at least one payment-schedule row is no longer Pending. Please reverse the receipts / invoices / payments first.',
        NULL, 'Y');
