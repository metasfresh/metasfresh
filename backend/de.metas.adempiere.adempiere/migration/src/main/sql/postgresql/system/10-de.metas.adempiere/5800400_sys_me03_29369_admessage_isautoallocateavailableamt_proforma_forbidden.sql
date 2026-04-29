-- 2026-04-29
-- AD_Message — IsAutoAllocateAvailableAmt_ProformaPrepayment_Forbidden
-- iter-3 https://github.com/metasfresh/me03/issues/29369 (Phase 7 Task 42 — C_Payment_AutoAllocateGuard interceptor)

-- AD_Message
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y',
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        'E',
        'IsAutoAllocateAvailableAmt_ProformaPrepayment_Forbidden',
        'IsAutoAllocateAvailableAmt cannot be enabled on a proforma-prepayment payment.',
        'D',
        545675 /*From ID Server*/);

-- Set ErrorCode (required for MsgType='E')
UPDATE AD_Message
SET ErrorCode = 'AUTO_ALLOC_FORBIDDEN_PROFORMA_PREPAYMENT'
WHERE AD_Message_ID = 545675;

-- AD_Message_Trl: de_DE
INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y',
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        545675 /*From ID Server*/,
        'IsAutoAllocateAvailableAmt darf für eine Proforma-Anzahlung nicht aktiviert werden.',
        NULL, 'N');

-- AD_Message_Trl: de_CH
INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y',
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        545675 /*From ID Server*/,
        'IsAutoAllocateAvailableAmt darf für eine Proforma-Anzahlung nicht aktiviert werden.',
        NULL, 'N');

-- AD_Message_Trl: en_US
INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y',
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-04-29 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        545675 /*From ID Server*/,
        'IsAutoAllocateAvailableAmt cannot be enabled on a proforma-prepayment payment.',
        NULL, 'Y');
