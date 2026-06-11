-- Tax Declaration: AD_Message for createCorrection guard (Iter 7).
-- Iter 7 of EPIC https://github.com/metasfresh/me03/issues/28717 — Corrections lifecycle.

INSERT INTO AD_Message (
    AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Value, MsgText, MsgType, EntityType, ErrorCode
) VALUES (
    545717, 0, 0, 'Y',
    TIMESTAMP '2026-05-25 00:00:00', 100, TIMESTAMP '2026-05-25 00:00:00', 100,
    'TaxDeclaration_CreateCorrection_OriginalNotLocked',
    'Eine Korrektur kann nur für eine gesperrte Original-Berichtigung erstellt werden.',
    'E', 'de.metas.acct', 'TAXDECLARATION_CORRECTION_NOT_LOCKED'
);

-- en_US Trl
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
VALUES
    ('en_US', 545717, 'A Correction can only be created from a locked Original declaration.',
     NULL, 'Y', 0, 0, TIMESTAMP '2026-05-25 00:00:00', 100, TIMESTAMP '2026-05-25 00:00:00', 100, 'Y');

-- Other active system languages — base-language fallback
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, m.AD_Message_ID, m.MsgText, m.MsgTip, 'N',
    m.AD_Client_ID, m.AD_Org_ID, m.Created, m.CreatedBy, m.Updated, m.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Message m
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND m.AD_Message_ID = 545717
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = m.AD_Message_ID);
