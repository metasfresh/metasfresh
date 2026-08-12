-- 2026-05-30
-- Tax Declaration — correction-workflow AD_Messages. me03 epic 28717 (issue 29632). See https://github.com/metasfresh/me03/issues/29632

-- MSG1: TaxDeclaration_CheckCorrectionNeed_NotLatest
INSERT INTO AD_Message (
    AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Value, MsgText, MsgType, EntityType, ErrorCode
) VALUES (
    545736 /*From ID Server*/, 0, 0, 'Y',
    TIMESTAMP '2026-05-30 00:00:01', 100, TIMESTAMP '2026-05-30 00:00:01', 100,
    'TaxDeclaration_CheckCorrectionNeed_NotLatest',
    'Es existiert bereits eine neuere Berichtigung — bitte diese prüfen.',
    'E', 'de.metas.acct', 'TAXDECLARATION_CORRECTION_NOT_LATEST'
);

-- en_US Trl
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
VALUES
    ('en_US', 545736, 'A newer correction exists — check that one instead.',
     NULL, 'Y', 0, 0, TIMESTAMP '2026-05-30 00:00:02', 100, TIMESTAMP '2026-05-30 00:00:02', 100, 'Y');

-- Other active system languages — base-language fallback
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, m.AD_Message_ID, m.MsgText, m.MsgTip, 'N',
    m.AD_Client_ID, m.AD_Org_ID, m.Created, m.CreatedBy, m.Updated, m.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Message m
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND m.AD_Message_ID = 545736
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = m.AD_Message_ID);

-- MSG2: TaxDeclaration_CreateCorrection_DraftExists
INSERT INTO AD_Message (
    AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Value, MsgText, MsgType, EntityType, ErrorCode
) VALUES (
    545737 /*From ID Server*/, 0, 0, 'Y',
    TIMESTAMP '2026-05-30 00:00:03', 100, TIMESTAMP '2026-05-30 00:00:03', 100,
    'TaxDeclaration_CreateCorrection_DraftExists',
    'Bitte zuerst die vorhandene Berichtigung im Entwurf abschließen oder löschen.',
    'E', 'de.metas.acct', 'TAXDECLARATION_CORRECTION_DRAFT_EXISTS'
);

-- en_US Trl
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
VALUES
    ('en_US', 545737, 'Complete or delete the existing draft correction first.',
     NULL, 'Y', 0, 0, TIMESTAMP '2026-05-30 00:00:04', 100, TIMESTAMP '2026-05-30 00:00:04', 100, 'Y');

-- Other active system languages — base-language fallback
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, m.AD_Message_ID, m.MsgText, m.MsgTip, 'N',
    m.AD_Client_ID, m.AD_Org_ID, m.Created, m.CreatedBy, m.Updated, m.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Message m
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND m.AD_Message_ID = 545737
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = m.AD_Message_ID);

-- MSG3: TaxDeclaration_CreateCorrection_NoCorrectionNeeded
INSERT INTO AD_Message (
    AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Value, MsgText, MsgType, EntityType, ErrorCode
) VALUES (
    545738 /*From ID Server*/, 0, 0, 'Y',
    TIMESTAMP '2026-05-30 00:00:05', 100, TIMESTAMP '2026-05-30 00:00:05', 100,
    'TaxDeclaration_CreateCorrection_NoCorrectionNeeded',
    'Keine Berichtigung erforderlich — die Voranmeldung ist weiterhin korrekt.',
    'E', 'de.metas.acct', 'TAXDECLARATION_NO_CORRECTION_NEEDED'
);

-- en_US Trl
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
VALUES
    ('en_US', 545738, 'No correction needed — the declaration is still accurate.',
     NULL, 'Y', 0, 0, TIMESTAMP '2026-05-30 00:00:06', 100, TIMESTAMP '2026-05-30 00:00:06', 100, 'Y');

-- Other active system languages — base-language fallback
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, m.AD_Message_ID, m.MsgText, m.MsgTip, 'N',
    m.AD_Client_ID, m.AD_Org_ID, m.Created, m.CreatedBy, m.Updated, m.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Message m
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND m.AD_Message_ID = 545738
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = m.AD_Message_ID);
