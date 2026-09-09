-- 2026-05-25
-- Tax Declaration — AD_Process for C_TaxDeclaration_CreateCorrection + AD_Table_Process wiring
-- Iter 7 of me03 epic 28717. See https://github.com/metasfresh/me03/issues/29631
--
-- NOTE: Task spec requested AD_Menu + AD_Menu_Trl + AD_TreeNodeMM, but the discovery query
-- returned 0 rows for existing C_TaxDeclaration_Build/Reactivate menus. In practice, these
-- processes are wired via AD_Table_Process (not AD_Menu) as per 5803190 pattern.
-- Following the working implementation: AD_Table_Process for WebUI document button.

-- INSERT AD_Process for C_TaxDeclaration_CreateCorrection
INSERT INTO AD_Process
    (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID,
     AllowProcessReRun, Classname, CopyFromProcess,
     Created, CreatedBy, EntityType,
     IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint,
     IsOneInstanceOnly, IsReport, IsServerProcess, IsUseBPartnerLanguage,
     LockWaitTimeout, Name, RefreshAllAfterExecution, ShowHelp,
     Type, Updated, UpdatedBy, Value)
VALUES
    ('7', 0, 0, 585620 /*From ID Server*/,
     'Y', 'de.metas.acct.tax.C_TaxDeclaration_CreateCorrection', 'N',
     TIMESTAMP '2026-05-25 00:00:00', 100, 'de.metas.acct',
     'Y', 'N', 'N', 'N',
     'N', 'N', 'Y', 'Y',
     0, 'Korrektur-Berichtigung erstellen', 'N', 'N',
     'Java', TIMESTAMP '2026-05-25 00:00:00', 100, 'C_TaxDeclaration_CreateCorrection')
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
  AND t.AD_Process_ID = 585620
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

-- English translation
UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Name = 'Create Correction',
    Description = 'Spawn a Correction declaration anchored to this locked Original.',
    Updated = TIMESTAMP '2026-05-25 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 585620;

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
    541871 /*From ID Server*/, 0, 0, 'Y',
    TIMESTAMP '2026-05-25 00:00:00', 100, TIMESTAMP '2026-05-25 00:00:00', 100,
    818, 585620,
    'Y', 'N', 'N', 'N', 'N',
    'de.metas.acct'
);
