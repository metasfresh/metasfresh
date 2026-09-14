-- Implemented by: backend/de.metas.fresh/de.metas.fresh.base/src/main/java/de/metas/factoring/process/Factoring_OP_Liste_Export.java

-- IDs allocated from idserver.metas.de on 2026-07-22:
--   AD_MigrationScript  5815540 (this file prefix)
--   AD_Element          585124 (process name element)
--   AD_Process          585642
--   AD_Process_Para     543261
--   AD_Menu             542349
--   AD_Message (5)      545780, 545781, 545782, 545783, 545784
-- Lookups:
--   C_Currency_ID AD_Element = 193
--   AD_TreeNodeMM parent (Berichte) = 541076

-- ============================================================================
-- 1. AD_Element for the process name (used for translations)
-- ============================================================================

INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Name, PrintName, Description, ColumnName, EntityType)
VALUES (585124, 0, 0, 'Y', TO_TIMESTAMP('2026-07-22 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-22 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        'Factoring OP-Liste Export', 'Factoring OP-Liste Export', 'Exportiert die offenen Rechnungen und Gutschriften der Factoring-Kunden als CSV-Datei.', 'Factoring_OP_Liste_Export', 'D')
/*From ID Server*/;

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            Name, PrintName, Description, IsTranslated)
VALUES (585124, 'en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-07-22 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-22 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        'Factoring OP-List Export', 'Factoring OP-List Export', 'Exports open invoices and credit notes of factoring customers as a CSV file.', 'Y')
/*From ID Server*/;

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            Name, PrintName, Description, IsTranslated)
VALUES (585124, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-07-22 00:00:01','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-22 00:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
        'Factoring OP-Liste Export', 'Factoring OP-Liste Export', 'Exportiert die offenen Rechnungen und Gutschriften der Factoring-Kunden als CSV-Datei.', 'Y')
/*From ID Server*/;

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            Name, PrintName, Description, IsTranslated)
VALUES (585124, 'de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-07-22 00:00:02','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-22 00:00:02','YYYY-MM-DD HH24:MI:SS'), 100,
        'Factoring OP-Liste Export', 'Factoring OP-Liste Export', 'Exportiert die offenen Rechnungen und Gutschriften der Factoring-Kunden als CSV-Datei.', 'Y')
/*From ID Server*/;

-- ============================================================================
-- 2. AD_Process
-- ============================================================================

-- Note: AD_Process has NO ad_element_id column in the current schema (verified against the
-- deep_tundra_release preloaded DB — the metasfresh-db skill rule "AD_Process.Name comes
-- from AD_Element_ID" is stale for AD_Process specifically). Name / Description sit directly
-- on AD_Process; translation propagation for AD_Process_Trl is not element-driven here.
INSERT INTO AD_Process (ad_process_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                        value, name, description, classname, procedurename, isreport, showhelp, accesslevel,
                        entitytype, isbetafunctionality, isdirectprint, type)
VALUES (585642 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-22 00:00:03','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-22 00:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
        'Factoring_OP_Liste_Export', 'Factoring OP-Liste Export', 'Exportiert die offenen Rechnungen und Gutschriften der Factoring-Kunden als CSV-Datei.', 'de.metas.factoring.process.Factoring_OP_Liste_Export', NULL, 'N', 'N', '3',
        'D', 'N', 'N', 'Java');

-- ============================================================================
-- 3. AD_Process_Para for C_Currency_ID
-- ============================================================================

INSERT INTO AD_Process_Para (ad_process_para_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                             ad_process_id, ad_element_id, columnname, ad_reference_id, ismandatory, defaultvalue,
                             defaultvalue2, seqno, fieldlength, isrange, iscentrallymaintained, name)
VALUES (543261, 0, 0, 'Y', TO_TIMESTAMP('2026-07-22 00:00:04','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-22 00:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
        585642, 193, 'C_Currency_ID', 19, 'Y', NULL, '@#C_Currency_ID@', 10, 10, 'N', 'Y', 'Währung')
/*From ID Server*/;

-- ============================================================================
-- 4. AD_Menu entry
-- ============================================================================

INSERT INTO AD_Menu (ad_menu_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                     name, ad_element_id, action, ad_process_id, issummary, entitytype, internalname)
VALUES (542349, 0, 0, 'Y', TO_TIMESTAMP('2026-07-22 00:00:05','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-22 00:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
        'Factoring OP-Liste Export', 585124, 'P', 585642, 'N', 'D', 'Factoring_OP_Liste_Export')
/*From ID Server*/;

-- ============================================================================
-- 5. AD_TreeNodeMM placement under Berichte
-- ============================================================================

INSERT INTO AD_TreeNodeMM (ad_client_id, ad_org_id, ad_tree_id, parent_id, node_id, seqno, isactive, created, createdby, updated, updatedby)
VALUES (0, 0, 10, 541076, 542349, 10, 'Y', TO_TIMESTAMP('2026-07-22 00:01:20','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-22 00:01:20','YYYY-MM-DD HH24:MI:SS'), 100);

-- ============================================================================
-- 6. AD_Message entries for error paths
-- ============================================================================

-- Message 1: Factoring_OP_Liste_EXT_RoleScopeAllOrgs
INSERT INTO AD_Message (ad_message_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                        value, msgtype, msgtext, entitytype)
VALUES (545780, 0, 0, 'Y', TO_TIMESTAMP('2026-07-22 00:00:06','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-22 00:00:06','YYYY-MM-DD HH24:MI:SS'), 100,
        'Factoring_OP_Liste_EXT_RoleScopeAllOrgs', 'E',
        'Bitte wählen Sie eine spezifische Organisation aus, bevor Sie den Factoring-OP-Liste-Export starten. Die Rollenrichtweite "*" (alle Organisationen) wird nicht unterstützt.',
        'D')
/*From ID Server*/;

UPDATE AD_Message SET ErrorCode = 'FACTORING_OP_ROLE_SCOPE_ALL_ORGS',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545780;

INSERT INTO AD_Message_Trl (ad_message_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                            msgtext, istranslated)
SELECT t.AD_Message_ID, l.AD_Language,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-22 00:00:17','YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-22 00:00:17','YYYY-MM-DD HH24:MI:SS'), 100,
       t.MsgText, 'N'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545780
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl e
                  WHERE e.AD_Language = l.AD_Language AND e.AD_Message_ID = t.AD_Message_ID);

UPDATE AD_Message_Trl
SET MsgText = 'Please select a specific organisation before running the Factoring OP-Liste Export. The all-organisations scope (''*'') is not supported.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545780 AND AD_Language = 'en_US';

UPDATE AD_Message_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545780 AND AD_Language IN ('de_DE', 'de_CH');

-- Message 2: Factoring_OP_Liste_EXT_NoFactorer
INSERT INTO AD_Message (ad_message_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                        value, msgtype, msgtext, entitytype)
VALUES (545781, 0, 0, 'Y', TO_TIMESTAMP('2026-07-22 00:00:08','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-22 00:00:08','YYYY-MM-DD HH24:MI:SS'), 100,
        'Factoring_OP_Liste_EXT_NoFactorer', 'E',
        'Kein Factoring-Geschäftspartner (IsFactorer=''Y'') für die Organisation gefunden. Bitte richten Sie einen Geschäftspartner mit IsFactorer=''Y'' für diese Organisation ein.',
        'D')
/*From ID Server*/;

UPDATE AD_Message SET ErrorCode = 'FACTORING_OP_NO_FACTORER',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:19','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545781;

INSERT INTO AD_Message_Trl (ad_message_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                            msgtext, istranslated)
SELECT t.AD_Message_ID, l.AD_Language,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-22 00:00:20','YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-22 00:00:20','YYYY-MM-DD HH24:MI:SS'), 100,
       t.MsgText, 'N'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545781
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl e
                  WHERE e.AD_Language = l.AD_Language AND e.AD_Message_ID = t.AD_Message_ID);

UPDATE AD_Message_Trl
SET MsgText = 'No factorer BPartner (IsFactorer=''Y'') found for this organisation. Please configure a BPartner with IsFactorer=''Y'' for this organisation.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545781 AND AD_Language = 'en_US';

UPDATE AD_Message_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545781 AND AD_Language IN ('de_DE', 'de_CH');

-- Message 3: Factoring_OP_Liste_EXT_MultipleFactorers
INSERT INTO AD_Message (ad_message_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                        value, msgtype, msgtext, entitytype)
VALUES (545782, 0, 0, 'Y', TO_TIMESTAMP('2026-07-22 00:00:10','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-22 00:00:10','YYYY-MM-DD HH24:MI:SS'), 100,
        'Factoring_OP_Liste_EXT_MultipleFactorers', 'E',
        'Mehrere Factoring-Geschäftspartner (IsFactorer=''Y'') für die Organisation gefunden. Es ist genau ein Factoring-Geschäftspartner pro Organisation erforderlich.',
        'D')
/*From ID Server*/;

UPDATE AD_Message SET ErrorCode = 'FACTORING_OP_MULTIPLE_FACTORERS',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:22','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545782;

INSERT INTO AD_Message_Trl (ad_message_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                            msgtext, istranslated)
SELECT t.AD_Message_ID, l.AD_Language,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-22 00:00:23','YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-22 00:00:23','YYYY-MM-DD HH24:MI:SS'), 100,
       t.MsgText, 'N'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545782
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl e
                  WHERE e.AD_Language = l.AD_Language AND e.AD_Message_ID = t.AD_Message_ID);

UPDATE AD_Message_Trl
SET MsgText = 'Multiple factorer BPartners (IsFactorer=''Y'') found for this organisation. Exactly one factorer BPartner per organisation is required.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545782 AND AD_Language = 'en_US';

UPDATE AD_Message_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545782 AND AD_Language IN ('de_DE', 'de_CH');

-- Message 4: Factoring_OP_Liste_EXT_MissingContractNo
INSERT INTO AD_Message (ad_message_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                        value, msgtype, msgtext, entitytype)
VALUES (545783, 0, 0, 'Y', TO_TIMESTAMP('2026-07-22 00:00:12','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-22 00:00:12','YYYY-MM-DD HH24:MI:SS'), 100,
        'Factoring_OP_Liste_EXT_MissingContractNo', 'E',
        'Der Factoring-Geschäftspartner hat keine Vertragsnummer (FactoringContractNo) hinterlegt. Dies wird für den OP-Listen-Export benötigt.',
        'D')
/*From ID Server*/;

UPDATE AD_Message SET ErrorCode = 'FACTORING_OP_MISSING_CONTRACT_NO',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:25','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545783;

INSERT INTO AD_Message_Trl (ad_message_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                            msgtext, istranslated)
SELECT t.AD_Message_ID, l.AD_Language,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-22 00:00:26','YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-22 00:00:26','YYYY-MM-DD HH24:MI:SS'), 100,
       t.MsgText, 'N'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545783
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl e
                  WHERE e.AD_Language = l.AD_Language AND e.AD_Message_ID = t.AD_Message_ID);

UPDATE AD_Message_Trl
SET MsgText = 'The factorer BPartner has no FactoringContractNo set — required for the OP-Liste export.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:27','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545783 AND AD_Language = 'en_US';

UPDATE AD_Message_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:27','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545783 AND AD_Language IN ('de_DE', 'de_CH');

-- Message 5: Factoring_OP_Liste_EXT_MissingClientAccountId
INSERT INTO AD_Message (ad_message_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                        value, msgtype, msgtext, entitytype)
VALUES (545784, 0, 0, 'Y', TO_TIMESTAMP('2026-07-22 00:00:14','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-22 00:00:14','YYYY-MM-DD HH24:MI:SS'), 100,
        'Factoring_OP_Liste_EXT_MissingClientAccountId', 'E',
        'Der Factoring-Geschäftspartner hat keine Kundenkontonummer (FactoringClientAccountId) hinterlegt. Dies wird für den OP-Listen-Export benötigt.',
        'D')
/*From ID Server*/;

UPDATE AD_Message SET ErrorCode = 'FACTORING_OP_MISSING_CLIENT_ACCOUNT_ID',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:28','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545784;

INSERT INTO AD_Message_Trl (ad_message_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                            msgtext, istranslated)
SELECT t.AD_Message_ID, l.AD_Language,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-22 00:00:29','YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-22 00:00:29','YYYY-MM-DD HH24:MI:SS'), 100,
       t.MsgText, 'N'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545784
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl e
                  WHERE e.AD_Language = l.AD_Language AND e.AD_Message_ID = t.AD_Message_ID);

UPDATE AD_Message_Trl
SET MsgText = 'The factorer BPartner has no FactoringClientAccountId set — required for the OP-Liste export.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:30','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545784 AND AD_Language = 'en_US';

UPDATE AD_Message_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-22 00:00:30','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545784 AND AD_Language IN ('de_DE', 'de_CH');

-- ============================================================================
-- Translation propagation and element updates
-- ============================================================================

SELECT add_missing_translations();
SELECT update_ad_element_on_ad_element_trl_update(585124, NULL);
