-- gh#28598: Register AD_Process for MD_Candidate_Rebuild
-- https://github.com/metasfresh/me03/issues/28598

-- AD_Process
INSERT INTO AD_Process (
    AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Value, Name, Description,
    Classname, EntityType, AccessLevel, Type,
    IsReport, IsDirectPrint, IsBetaFunctionality, IsServerProcess,
    ShowHelp, AllowProcessReRun, CopyFromProcess,
    IsApplySecuritySettings, IsOneInstanceOnly,
    RefreshAllAfterExecution, IsUseBPartnerLanguage,
    IsTranslateExcelHeaders, IsFormatExcelFile, SpreadsheetFormat,
    IsNotifyUserAfterExecution, PostgrestResponseFormat,
    LockWaitTimeout
)
VALUES (
    585595, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-11 16:00', 'YYYY-MM-DD HH24:MI'), 0,
    TO_TIMESTAMP('2026-03-11 16:00', 'YYYY-MM-DD HH24:MI'), 0,
    'MD_Candidate_Rebuild', 'MaterialDispo neu aufbauen', 'Baut alle MD_Candidate-Datensaetze aus Quelldokumenten (offene Auftraege, Bestellungen, Produktionsauftraege) neu auf.',
    'de.metas.material.dispo.commons.process.MD_Candidate_Rebuild',
    'de.metas.material.dispo', '7', 'Java',
    'N', 'N', 'N', 'N',
    'N', 'Y', 'N',
    'N', 'Y',
    'Y', 'Y',
    'Y', 'Y', 'xls',
    'Y', 'json',
    0
);

-- AD_Process_Trl
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 585595, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Process_ID = 585595
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID);

-- English translation
UPDATE AD_Process_Trl
SET Name = 'Rebuild MaterialDispo Candidates',
    Description = 'Rebuilds all MD_Candidate records from source documents (open orders, production orders, distribution orders).',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-03-11 16:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 0
WHERE AD_Process_ID = 585595 AND AD_Language = 'en_US';

-- AD_Process_Para: IsBackupBeforeDelete (Yes/No)
INSERT INTO AD_Process_Para (
    AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Process_ID, Name, Description,
    ColumnName, AD_Reference_ID,
    SeqNo, IsMandatory, IsRange, IsCentrallyMaintained,
    EntityType, FieldLength, DefaultValue
)
VALUES (
    543155, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-11 16:00', 'YYYY-MM-DD HH24:MI'), 0,
    TO_TIMESTAMP('2026-03-11 16:00', 'YYYY-MM-DD HH24:MI'), 0,
    585595, 'Backup vor Loeschung', 'Sichert die bestehenden MD_Candidate-Daten vor dem Neuaufbau.',
    'IsBackupBeforeDelete', 20,  -- 20 = Yes-No
    10, 'Y', 'N', 'Y',
    'de.metas.material.dispo', 0, 'Y'
);

-- AD_Process_Para_Trl
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 543155, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Process_Para_ID = 543155
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID);

-- English translation for parameter
UPDATE AD_Process_Para_Trl
SET Name = 'Backup Before Delete',
    Description = 'Creates backup of existing MD_Candidate data before rebuilding.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-03-11 16:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 0
WHERE AD_Process_Para_ID = 543155 AND AD_Language = 'en_US';

-- Link process to MD_Candidate table (AD_Table_ID=540808) for WebUI action
INSERT INTO AD_Table_Process (
    AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Process_ID, AD_Table_ID, EntityType,
    WEBUI_DocumentAction, WEBUI_IncludedTabTopAction,
    WEBUI_ViewAction, WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default
)
VALUES (
    541631, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-11 16:00', 'YYYY-MM-DD HH24:MI'), 0,
    TO_TIMESTAMP('2026-03-11 16:00', 'YYYY-MM-DD HH24:MI'), 0,
    585595, 540808, 'de.metas.material.dispo',
    'Y', 'N',
    'Y', 'N', 'N'
);
