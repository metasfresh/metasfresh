-- 2026-05-12
-- Tax Declaration Iter4 — AD_Process for C_TaxDeclaration_Build + AD_Message already-processed guard

-- INSERT AD_Process for C_TaxDeclaration_Build
INSERT INTO AD_Process
    (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID,
     AllowProcessReRun, Classname, CopyFromProcess,
     Created, CreatedBy, EntityType,
     IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint,
     IsOneInstanceOnly, IsReport, IsServerProcess, IsUseBPartnerLanguage,
     LockWaitTimeout, Name, RefreshAllAfterExecution, ShowHelp,
     Type, Updated, UpdatedBy, Value)
VALUES
    ('3', 0, 0, 585615 /*From ID Server*/,
     'Y', 'de.metas.acct.tax.C_TaxDeclaration_Build', 'N',
     TIMESTAMP '2026-05-19 00:00:00', 100, 'de.metas.acct',
     'Y', 'N', 'N', 'N',
     'N', 'N', 'Y', 'Y',
     0, 'Steuererklärung aufbauen', 'N', 'Y',
     'Java', TIMESTAMP '2026-05-19 00:00:00', 100, 'C_TaxDeclaration_Build')
;

-- Translation rows for AD_Process (base rows propagated by SELECT)
INSERT INTO AD_Process_Trl
    (AD_Language, AD_Process_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Process_ID = 585615
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

-- English translation
UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Name = 'Build Tax Declaration',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 585615;

-- INSERT AD_Message for the already-processed guard (base language = German)
INSERT INTO AD_Message
    (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy,
     EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES
    (0, 545682 /*From ID Server*/, 0, TIMESTAMP '2026-05-19 00:00:00', 100,
     'de.metas.acct', 'Y',
     'Steuererklärung wurde bereits verarbeitet und kann nicht neu erstellt werden.',
     'E', TIMESTAMP '2026-05-19 00:00:00', 100,
     'TaxDeclaration_AlreadyProcessed')
;

-- Set ErrorCode (required for MsgType='E')
UPDATE AD_Message SET ErrorCode = 'TAX_DECLARATION_ALREADY_PROCESSED' WHERE AD_Message_ID = 545682;

-- Translation rows for AD_Message (base rows propagated by SELECT)
INSERT INTO AD_Message_Trl
    (AD_Language, AD_Message_ID, MsgText, MsgTip,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545682
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- English translation
UPDATE AD_Message_Trl
SET IsTranslated = 'Y',
    MsgText = 'Tax Declaration is already processed and cannot be rebuilt.',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545682;

-- Wire the Processing button column (C_TaxDeclaration.Processing, col 14464) to the new process.
-- The old process AD_Process_ID=336 (C_TaxDeclaration_CreateLines) had its Java class deleted in Iter4.
UPDATE AD_Column
SET AD_Process_ID = 585615, Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Column_ID = 14464;

-- Inactivate the legacy C_TaxDeclaration_CreateLines process (AD_Process_ID=336).
-- Its Java class org.adempiere.acct.process.C_TaxDeclaration_CreateLines was removed in Iter4;
-- the health check fails when it tries to instantiate it.
UPDATE AD_Process
SET IsActive = 'N', Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Process_ID = 336;
