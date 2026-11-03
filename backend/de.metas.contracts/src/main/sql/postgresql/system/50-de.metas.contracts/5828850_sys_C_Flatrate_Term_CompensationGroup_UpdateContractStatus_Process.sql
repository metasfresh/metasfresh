-- Process that keeps the contract status of completed compensation-group contracts up to date:
-- "not yet started" -> "running" once the start date is reached, "running" -> "contract end" once the end date
-- has passed without an extension. Run daily by the scheduler of the next migration script.
--
-- IDs allocated from idserver.metas.de:
--   AD_Process 585687

INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, CopyFromProcess, Created, CreatedBy,
                        Description, EntityType, Help, IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile,
                        IsLogWarning, IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders, IsUpdateExportDate,
                        IsUseBPartnerLanguage, LockWaitTimeout, Name, PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, Type,
                        Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585687 /*From ID Server*/, 'Y',
        'de.metas.contracts.compensationGroup.contract.process.C_Flatrate_Term_CompensationGroup_UpdateContractStatus', 'N',
        TO_TIMESTAMP('2026-10-09 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Setzt den Vertragsstatus fertiggestellter Kompensationsgruppen-Verträge: "Laufend" ab dem Vertragsbeginn, "Vertragsende" nach dem Vertragsende ohne Verlängerung.',
        'de.metas.contracts',
        'Wird täglich vom Ablaufplan ausgeführt. Die Status "Gekündigt", "Storniert" und "Vertragsende" werden nicht verändert.',
        'Y', 'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N', 'N', 'N',
        'Y', 0, 'Kompensationsgruppen-Verträge: Vertragsstatus aktualisieren', 'json', 'N', 'Y', 'Java',
        TO_TIMESTAMP('2026-10-09 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'C_Flatrate_Term_CompensationGroup_UpdateContractStatus')
;

INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585687
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name         = 'Compensation group contracts: update contract status',
    Description  = 'Sets the contract status of completed compensation group contracts: "running" from the start date, "contract end" after the end date without an extension.',
    Help         = 'Run daily by the scheduler. The statuses "quit", "voided" and "contract end" are not changed.',
    Updated      = TO_TIMESTAMP('2026-10-09 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US'
  AND AD_Process_ID = 585687
;

UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language IN ('de_DE', 'de_CH')
  AND AD_Process_ID = 585687
;
