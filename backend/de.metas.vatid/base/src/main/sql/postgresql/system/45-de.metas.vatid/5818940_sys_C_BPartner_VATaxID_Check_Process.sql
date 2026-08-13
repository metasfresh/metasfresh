-- VAT-ID online check: the manual/scheduled check process.
--
-- Available on the Business Partner window (table 291, C_BPartner), runnable on a single partner or on a
-- selection. MaxChecksPerRun throttles a selection run: empty or <= 0 means no limit (see the parameter's
-- own Description, which is the user-facing statement of that rule and must keep saying exactly that).
--
-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript 5818940 (this file's prefix)
--   AD_Process          585650
--   AD_Element          585297 (MaxChecksPerRun parameter label/description)
--   AD_Process_Para      543273
--   AD_Table_Process     541663 (exposes the process on C_BPartner, table 291)

-- 1. AD_Process
INSERT INTO AD_Process (
    AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, CopyFromProcess,
    Created, CreatedBy, Description, EntityType, Help, IsActive, IsApplySecuritySettings,
    IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsNotifyUserAfterExecution, IsOneInstanceOnly,
    IsReport, IsTranslateExcelHeaders, IsUpdateExportDate, IsUseBPartnerLanguage, LockWaitTimeout, Name,
    PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat, Type, Updated,
    UpdatedBy, Value)
VALUES (
    '3', 0, 0, 585650 /*From ID Server*/, 'Y', 'de.metas.vatid.process.C_BPartner_VATaxID_Check', 'N',
    TO_TIMESTAMP('2026-08-13 17:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Prüft die USt-IdNr. der ausgewählten Geschäftspartner online (VIES) und speichert das Ergebnis.', 'D',
    NULL, 'Y', 'Y', 'N', 'N', 'N', 'Y', 'N',
    'N', 'N', 'N', 'Y', 0, 'USt-IdNr. prüfen',
    'json', 'N', 'Y', 'xls', 'Java', TO_TIMESTAMP('2026-08-13 17:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    100, 'C_BPartner_VATaxID_Check')
;

-- 2. AD_Process_Trl skeleton for every active system language
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID,
                            AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Process_ID = 585650
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

-- 3. English override
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name         = 'Check VAT-ID',
    Description  = 'Checks the VAT-ID of the selected business partners online (VIES) and records the result.',
    Updated      = TO_TIMESTAMP('2026-08-13 17:10:10', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 585650
;

-- 4. AD_Element for the MaxChecksPerRun parameter -- its Description is the user-facing statement of the
--    empty-or-<=0-means-no-limit rule (AD_Process_Para.Description renders as the field's tooltip).
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description,
                         EntityType, Help, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (
    585297 /*From ID Server*/, 0, 0, 'MaxChecksPerRun',
    TO_TIMESTAMP('2026-08-13 17:11:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Maximale Anzahl der USt-IdNr.-Prüfungen in einem Lauf. Leer oder kleiner/gleich 0 bedeutet: kein Limit.',
    'D',
    'Maximale Anzahl der USt-IdNr.-Prüfungen in einem Lauf. Leer oder kleiner/gleich 0 bedeutet: kein Limit.',
    'Y', 'Max. Prüfungen pro Lauf', 'Max. Prüfungen pro Lauf',
    TO_TIMESTAMP('2026-08-13 17:11:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 5. AD_Element_Trl skeleton
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Description, Help, Name, PrintName, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PrintName, 'N', t.AD_Client_ID,
       t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585297
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 6. English override
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Name         = 'Max checks per run',
    PrintName    = 'Max checks per run',
    Description  = 'Maximum number of VAT-ID checks performed in one run. Empty or less than/equal to 0 means no limit.',
    Help         = 'Maximum number of VAT-ID checks performed in one run. Empty or less than/equal to 0 means no limit.',
    Updated      = TO_TIMESTAMP('2026-08-13 17:11:10', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585297
;

-- de_DE / de_CH already carry the base (German) text from the skeleton insert -- just flip IsTranslated.
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-08-13 17:11:11', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'de_DE' AND AD_Element_ID = 585297
;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-08-13 17:11:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'de_CH' AND AD_Element_ID = 585297
;

-- 7. AD_Process_Para -- default 500; the Name/Description below are immediately overwritten by the
--    propagation call in step 9, but are filled in now so the row is never inconsistent even transiently.
INSERT INTO AD_Process_Para (
    AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName,
    Created, CreatedBy, DefaultValue, Description, EntityType, FieldLength, IsActive, IsAutocomplete,
    IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (
    0, 585297 /*From ID Server*/, 0, 585650, 543273 /*From ID Server*/, 11, 'MaxChecksPerRun',
    TO_TIMESTAMP('2026-08-13 17:12:00', 'YYYY-MM-DD HH24:MI:SS'), 100, '500',
    'Maximale Anzahl der USt-IdNr.-Prüfungen in einem Lauf. Leer oder kleiner/gleich 0 bedeutet: kein Limit.',
    'D', 22, 'Y', 'N',
    'Y', 'N', 'N', 'N', 'Max. Prüfungen pro Lauf', 10,
    TO_TIMESTAMP('2026-08-13 17:12:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 8. AD_Process_Para_Trl skeleton
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated,
                                  AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Process_Para_ID = 543273
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- 9. Propagate the element's translations (base + en_US) onto the parameter -- Para.Updated (17:12:00) is
--    later than every AD_Element_Trl row touched above, so the function's `<> ` guard fires for all of them.
SELECT update_process_para_translation_from_ad_element(585297);

-- 10. AD_Table_Process -- exposes the process on the C_BPartner window/grid (table 291), same shape as the
--     single-vs-selection template (C_Doc_Outbound_Log_SendPDFMails): available as a view action on a
--     single record or on a selection, not as a document action or an included-tab top action.
INSERT INTO AD_Table_Process (AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Table_ID, AD_Table_Process_ID,
                               Created, CreatedBy, EntityType, IsActive, Updated, UpdatedBy,
                               WEBUI_DocumentAction, WEBUI_IncludedTabTopAction, WEBUI_ViewAction,
                               WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
VALUES (
    0, 0, 585650, 291, 541663 /*From ID Server*/,
    TO_TIMESTAMP('2026-08-13 17:13:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
    TO_TIMESTAMP('2026-08-13 17:13:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'N', 'N', 'Y', 'N', 'N')
;
