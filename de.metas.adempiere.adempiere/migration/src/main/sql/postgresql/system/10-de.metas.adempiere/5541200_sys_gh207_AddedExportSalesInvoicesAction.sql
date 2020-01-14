DROP FUNCTION IF EXISTS exportSalesInvoicesInDateRange(TIMESTAMP With Time Zone, TIMESTAMP With Time Zone, numeric);

CREATE OR REPLACE FUNCTION exportSalesInvoicesInDateRange(IN p_C_Invoice_dateinvoiced_start TIMESTAMP With Time Zone,
                                                          IN p_C_Invoice_dateinvoiced_end TIMESTAMP With Time Zone,
                                                          IN p_AD_Org_ID numeric)

    RETURNS TABLE
            (
                AD_Org_ID        character varying,
                C_Bpartner_ID    character varying,
                C_Invoices_Count bigint
            )
AS

$BODY$

SELECT DISTINCT org.name as AD_Org_ID,
                bp.name  as C_Bpartner_ID,
                COUNT(*) AS C_Invoices_Count

FROM C_Invoice inv
         LEFT OUTER JOIN ad_org org on org.ad_org_id = inv.ad_org_id
         LEFT OUTER JOIN ad_orginfo orginfo on orginfo.ad_org_id = inv.ad_org_id
         LEFT OUTER JOIN c_bpartner bp on bp.c_bpartner_id = orginfo.org_bpartner_id
WHERE inv.dateinvoiced >= p_C_Invoice_dateinvoiced_start
  AND inv.dateinvoiced <= p_C_Invoice_dateinvoiced_end
  AND CASE WHEN p_AD_Org_ID > 0 THEN inv.ad_org_id = p_AD_Org_ID ELSE 1 = 1 END
  AND inv.ad_client_id = 1000000
  AND inv.isactive = 'Y'
  AND inv.docstatus IN ('CO', 'CL')
GROUP BY org.name, bp.name
$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000;

-- 2020-01-12T18:01:43.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, CopyFromProcess, Created, CreatedBy, EntityType, IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint, IsOneInstanceOnly, IsReport, IsServerProcess, IsTranslateExcelHeaders, IsUseBPartnerLanguage, LockWaitTimeout, Name, RefreshAllAfterExecution, ShowHelp, SQLStatement, Type,
                        Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 541243, 'Y', 'de.metas.impexp.excel.process.ExportToExcelProcess', 'N', TO_TIMESTAMP('2020-01-12 20:01:43', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', 0, 'Verrechnung Billcare', 'Y', 'N', '', 'Excel', TO_TIMESTAMP('2020-01-12 20:01:43', 'YYYY-MM-DD HH24:MI:SS'), 100, 'exportSalesInvoicesInDateRange')
;

-- 2020-01-12T18:01:43.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Process_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Process_ID = 541243
  AND NOT EXISTS(SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

-- 2020-01-12T18:02:56.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName, Created, CreatedBy, DefaultValue, Description, EntityType, FieldLength, Help, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 1581, 0, 541243, 541664, 16, 'DateFrom', TO_TIMESTAMP('2020-01-12 20:02:56', 'YYYY-MM-DD HH24:MI:SS'), 100, '@#Date@', 'Startdatum eines Abschnittes', 'U', 0, 'Datum von bezeichnet das Startdatum eines Abschnittes', 'Y', 'N', 'Y', 'N', 'N', 'N', 'Datum von', 10, TO_TIMESTAMP('2020-01-12 20:02:56', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-01-12T18:02:57.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Process_Para_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Process_Para_ID = 541664
  AND NOT EXISTS(SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- 2020-01-12T18:03:19.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET EntityType='D',
    Updated=TO_TIMESTAMP('2020-01-12 20:03:19', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_Para_ID = 541664
;

-- 2020-01-12T18:03:54.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName, Created, CreatedBy, DefaultValue, Description, EntityType, FieldLength, Help, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 1582, 0, 541243, 541665, 16, 'DateTo', TO_TIMESTAMP('2020-01-12 20:03:54', 'YYYY-MM-DD HH24:MI:SS'), 100, '@#Date@', 'Enddatum eines Abschnittes', 'D', 0, 'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)', 'Y', 'N', 'Y', 'N', 'N', 'N', 'Datum bis', 20, TO_TIMESTAMP('2020-01-12 20:03:54', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-01-12T18:03:54.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Process_Para_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Process_Para_ID = 541665
  AND NOT EXISTS(SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- 2020-01-12T18:04:59.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName, Created, CreatedBy, DefaultValue, Description, EntityType, FieldLength, Help, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 113, 0, 541243, 541666, 19, 'AD_Org_ID', TO_TIMESTAMP('2020-01-12 20:04:59', 'YYYY-MM-DD HH24:MI:SS'), 100, '@AD_Org_ID@', 'Organisatorische Einheit des Mandanten', 'D', 0, 'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie kÃƒÂ¶nnen Daten ÃƒÂ¼ber Organisationen hinweg gemeinsam verwenden.', 'Y', 'N', 'Y', 'N', 'N', 'N', 'Sektion', 30,
        TO_TIMESTAMP('2020-01-12 20:04:59', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-01-12T18:04:59.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Process_Para_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Process_Para_ID = 541666
  AND NOT EXISTS(SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- 2020-01-12T18:06:01.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET SQLStatement='select * from exportSalesInvoicesInDateRange(''@DateFrom@'', ''@DateTo@'', @AD_Org_ID@)',
    Updated=TO_TIMESTAMP('2020-01-12 20:06:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID = 541243
;

-- 2020-01-12T18:07:37.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Table_ID, AD_Table_Process_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, Updated, UpdatedBy, WEBUI_DocumentAction, WEBUI_IncludedTabTopAction, WEBUI_ViewAction, WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
VALUES (0, 0, 541243, 318, 540776, 167, TO_TIMESTAMP('2020-01-12 20:07:37', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', TO_TIMESTAMP('2020-01-12 20:07:37', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'Y', 'Y', 'N', 'N')
;

-- 2020-01-12T18:09:24.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 577458, 0, 'C_Invoices_Count', TO_TIMESTAMP('2020-01-12 20:09:24', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Anzahl Dokumente erstellt (abzgl. Storno)', 'Anzahl Dokumente erstellt (abzgl. Storno)', TO_TIMESTAMP('2020-01-12 20:09:24', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-01-12T18:09:24.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 577458
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 2020-01-12T18:09:38.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Name='Invoices Count',
    PrintName='Invoices Count',
    Updated=TO_TIMESTAMP('2020-01-12 20:09:38', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577458
  AND AD_Language = 'en_US'
;

-- 2020-01-12T18:09:38.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577458, 'en_US')
;

-- 2020-01-12T18:10:37.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Name='Export Invoices',
    Updated=TO_TIMESTAMP('2020-01-12 20:10:37', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Process_ID = 541243
;

-- 2020-01-13T16:16:30.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 577459, 0, TO_TIMESTAMP('2020-01-13 18:16:30', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Verrechnung Billcare', 'Verrechnung Billcare', TO_TIMESTAMP('2020-01-13 18:16:30', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-01-13T16:16:30.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 577459
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 2020-01-13T16:16:59.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Name='Export Invoices',
    PrintName='Export Invoices',
    Updated=TO_TIMESTAMP('2020-01-13 18:16:59', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577459
  AND AD_Language = 'en_US'
;

-- 2020-01-13T16:16:59.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577459, 'en_US')
;

-- 2020-01-13T16:17:49.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action, AD_Client_ID, AD_Element_ID, AD_Menu_ID, AD_Org_ID, AD_Process_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary, Name, Updated, UpdatedBy)
VALUES ('P', 0, 577459, 541419, 0, 541243, TO_TIMESTAMP('2020-01-13 18:17:49', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'exportSalesInvoicesInDateRange', 'Y', 'N', 'N', 'N', 'N', 'Verrechnung Billcare', TO_TIMESTAMP('2020-01-13 18:17:49', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-01-13T16:17:49.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Menu_ID,
       t.Description,
       t.Name,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Menu t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Menu_ID = 541419
  AND NOT EXISTS(SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID)
;

-- 2020-01-13T16:17:49.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT t.AD_Client_ID,
       0,
       'Y',
       now(),
       100,
       now(),
       100,
       t.AD_Tree_ID,
       541419,
       0,
       999
FROM AD_Tree t
WHERE t.AD_Client_ID = 0
  AND t.IsActive = 'Y'
  AND t.IsAllNodes = 'Y'
  AND t.AD_Table_ID = 116
  AND NOT EXISTS(SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID = t.AD_Tree_ID AND Node_ID = 541419)
;

-- 2020-01-13T16:17:49.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_menu_translation_from_ad_element(577459)
;

-- 2020-01-13T16:17:50.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM
SET Parent_ID=1000050,
    SeqNo=0,
    Updated=now(),
    UpdatedBy=100
WHERE Node_ID = 541232
  AND AD_Tree_ID = 10
;

-- 2020-01-13T16:17:50.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM
SET Parent_ID=1000050,
    SeqNo=1,
    Updated=now(),
    UpdatedBy=100
WHERE Node_ID = 541419
  AND AD_Tree_ID = 10
;

-- 2020-01-13T16:18:02.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM
SET Parent_ID=1000077,
    SeqNo=0,
    Updated=now(),
    UpdatedBy=100
WHERE Node_ID = 541419
  AND AD_Tree_ID = 10
;

-- 2020-01-13T16:18:02.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM
SET Parent_ID=1000077,
    SeqNo=1,
    Updated=now(),
    UpdatedBy=100
WHERE Node_ID = 540998
  AND AD_Tree_ID = 10
;

-- 2020-01-13T16:18:02.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM
SET Parent_ID=1000077,
    SeqNo=2,
    Updated=now(),
    UpdatedBy=100
WHERE Node_ID = 540825
  AND AD_Tree_ID = 10
;

-- 2020-01-13T16:22:09.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process
SET WEBUI_IncludedTabTopAction='N',
    WEBUI_ViewAction='N',
    Updated=TO_TIMESTAMP('2020-01-13 18:22:09', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Table_Process_ID = 540776
;

