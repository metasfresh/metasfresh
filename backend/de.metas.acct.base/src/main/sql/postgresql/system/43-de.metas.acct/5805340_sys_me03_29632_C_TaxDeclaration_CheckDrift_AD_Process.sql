-- 2026-05-29
-- Tax Declaration — AD_Process for C_TaxDeclaration_CheckDrift + AD_Table_Process wiring
-- Iter 8 of me03 epic 28717. See https://github.com/metasfresh/me03/issues/29632
--
-- IDs allocated from idserver.metas.de on 2026-05-29:
--   AD_MigrationScript  5805340 (sequential filename prefix)
--   AD_Process          585627  (new C_TaxDeclaration_CheckDrift process)
--   AD_Table_Process    541646  (wiring to C_TaxDeclaration table for WebUI button)

-- INSERT AD_Process for C_TaxDeclaration_CheckDrift
INSERT INTO AD_Process
    (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID,
     AllowProcessReRun, Classname, CopyFromProcess,
     Created, CreatedBy, EntityType,
     IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint,
     IsOneInstanceOnly, IsReport, IsServerProcess, IsUseBPartnerLanguage,
     LockWaitTimeout, Name, RefreshAllAfterExecution, ShowHelp,
     Type, Updated, UpdatedBy, Value)
VALUES
    ('7', 0, 0, 585627 /*From ID Server*/,
     'Y', 'de.metas.acct.tax.C_TaxDeclaration_CheckDrift', 'N',
     TIMESTAMP '2026-05-29 00:00:00', 100, 'de.metas.acct',
     'Y', 'N', 'N', 'N',
     'N', 'N', 'Y', 'Y',
     0, 'Abweichung prüfen', 'N', 'N',
     'Java', TIMESTAMP '2026-05-29 00:00:00', 100, 'C_TaxDeclaration_CheckDrift')
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
  AND t.AD_Process_ID = 585627
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

-- English translation
UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Name = 'Check Drift',
    Description = 'Check whether the Fact_Acct snapshot has drifted from live accounting data.',
    Updated = TIMESTAMP '2026-05-29 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 585627;

-- Wire the process to the C_TaxDeclaration table for WebUI document action button
-- (Following the pattern used for C_TaxDeclaration_Build via 5803190_sys_me03_29628_C_TaxDeclaration_Build_DocumentAction.sql)
INSERT INTO AD_Table_Process (
    AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Process_ID,
    WEBUI_DocumentAction, WEBUI_ViewAction, WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default, WEBUI_IncludedTabTopAction,
    EntityType
)
VALUES (
    541646 /*From ID Server*/, 0, 0, 'Y',
    TIMESTAMP '2026-05-29 00:00:00', 100, TIMESTAMP '2026-05-29 00:00:00', 100,
    818, 585627,
    'Y', 'N', 'N', 'N', 'N',
    'de.metas.acct'
);
