-- #28782 BPartner Balance and Account Hierarchy views
-- 2026-03-16

-- =======================
-- AD_Element: BPartnerBalance
-- =======================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (584681 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        'BPartnerBalance', 'D', 'Geschäftspartner-Kontensaldo', 'Geschäftspartner-Kontensaldo', 'Zeigt alle Buchungen eines Geschäftspartners mit laufendem Saldo');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            Name, PrintName, Description, IsTranslated)
SELECT l.AD_Language, 584681, 0, 0, 'Y', TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100,
       CASE WHEN l.AD_Language = 'en_US' THEN 'BPartner Account Balance'
            ELSE 'Geschäftspartner-Kontensaldo' END,
       CASE WHEN l.AD_Language = 'en_US' THEN 'BPartner Account Balance'
            ELSE 'Geschäftspartner-Kontensaldo' END,
       CASE WHEN l.AD_Language = 'en_US' THEN 'Shows all postings for a business partner with running balance'
            ELSE 'Zeigt alle Buchungen eines Geschäftspartners mit laufendem Saldo' END,
       CASE WHEN l.AD_Language = 'en_US' THEN 'Y' ELSE 'N' END
FROM AD_Language l
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 584681);

-- =======================
-- AD_Element: AccountHierarchy
-- =======================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (584682 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        'AccountHierarchy', 'D', 'Kontenhierarchie', 'Kontenhierarchie', 'Kontenplan als Baumstruktur mit aggregierten Salden');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            Name, PrintName, Description, IsTranslated)
SELECT l.AD_Language, 584682, 0, 0, 'Y', TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100,
       CASE WHEN l.AD_Language = 'en_US' THEN 'Account Hierarchy'
            ELSE 'Kontenhierarchie' END,
       CASE WHEN l.AD_Language = 'en_US' THEN 'Account Hierarchy'
            ELSE 'Kontenhierarchie' END,
       CASE WHEN l.AD_Language = 'en_US' THEN 'Chart of accounts tree with aggregated balances'
            ELSE 'Kontenplan als Baumstruktur mit aggregierten Salden' END,
       CASE WHEN l.AD_Language = 'en_US' THEN 'Y' ELSE 'N' END
FROM AD_Language l
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 584682);

-- =======================
-- AD_Process: BPartnerBalance_Launcher
-- =======================
INSERT INTO AD_Process (AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, Name, Classname, AccessLevel, EntityType, Type,
                        IsReport, IsDirectPrint, IsOneInstanceOnly, IsBetaFunctionality, IsApplySecuritySettings,
                        ShowHelp, AllowProcessReRun, CopyFromProcess, RefreshAllAfterExecution,
                        IsFormatExcelFile, IsTranslateExcelHeaders, IsNotifyUserAfterExecution, IsUpdateExportDate,
                        IsUseBPartnerLanguage, LockWaitTimeout, PostgrestResponseFormat, SpreadsheetFormat)
VALUES (585597 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        'BPartnerBalance_Launcher', 'Geschäftspartner-Kontensaldo',
        'de.metas.acct.account_info.bpartner_balance.BPartnerBalance_Launcher',
        '3', 'D', 'Java',
        'N', 'N', 'N', 'N', 'N',
        'N', 'Y', 'N', 'N',
        'Y', 'Y', 'N', 'N',
        'Y', 0, 'json', 'xls');

INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585597
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID);

-- =======================
-- AD_Process: AccountHierarchy_Launcher
-- =======================
INSERT INTO AD_Process (AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, Name, Classname, AccessLevel, EntityType, Type,
                        IsReport, IsDirectPrint, IsOneInstanceOnly, IsBetaFunctionality, IsApplySecuritySettings,
                        ShowHelp, AllowProcessReRun, CopyFromProcess, RefreshAllAfterExecution,
                        IsFormatExcelFile, IsTranslateExcelHeaders, IsNotifyUserAfterExecution, IsUpdateExportDate,
                        IsUseBPartnerLanguage, LockWaitTimeout, PostgrestResponseFormat, SpreadsheetFormat)
VALUES (585598 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        'AccountHierarchy_Launcher', 'Kontenhierarchie',
        'de.metas.acct.account_info.hierarchy.AccountHierarchy_Launcher',
        '3', 'D', 'Java',
        'N', 'N', 'N', 'N', 'N',
        'N', 'Y', 'N', 'N',
        'Y', 'Y', 'N', 'N',
        'Y', 0, 'json', 'xls');

INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585598
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID);

-- =======================
-- AD_Process: AccountHierarchy_OpenBPartnerBalance (quick action)
-- =======================
INSERT INTO AD_Process (AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, Name, Classname, AccessLevel, EntityType, Type,
                        IsReport, IsDirectPrint, IsOneInstanceOnly, IsBetaFunctionality, IsApplySecuritySettings,
                        ShowHelp, AllowProcessReRun, CopyFromProcess, RefreshAllAfterExecution,
                        IsFormatExcelFile, IsTranslateExcelHeaders, IsNotifyUserAfterExecution, IsUpdateExportDate,
                        IsUseBPartnerLanguage, LockWaitTimeout, PostgrestResponseFormat, SpreadsheetFormat)
VALUES (585599 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        'AccountHierarchy_OpenBPartnerBalance', 'Kontensaldo öffnen',
        'de.metas.acct.account_info.hierarchy.AccountHierarchy_OpenBPartnerBalance',
        '3', 'D', 'Java',
        'N', 'N', 'N', 'N', 'N',
        'N', 'Y', 'N', 'N',
        'Y', 'Y', 'N', 'N',
        'Y', 0, 'json', 'xls');

INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585599
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID);

-- =======================
-- AD_Menu: BPartnerBalance
-- =======================
INSERT INTO AD_Menu (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                     Name, Action, AD_Process_ID, IsSummary, EntityType, IsSOTrx, IsReadOnly,
                     AD_Element_ID, InternalName, WEBUI_NameBrowse)
VALUES (542308 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        'Geschäftspartner-Kontensaldo', 'P', 585597, 'N', 'D', 'N', 'N',
        584681, 'BPartnerBalance', 'Geschäftspartner-Kontensaldo');

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description, t.Name, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Menu_ID = 542308
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID);

SELECT update_menu_translation_from_ad_element(542308);

-- =======================
-- AD_Menu: AccountHierarchy
-- =======================
INSERT INTO AD_Menu (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                     Name, Action, AD_Process_ID, IsSummary, EntityType, IsSOTrx, IsReadOnly,
                     AD_Element_ID, InternalName, WEBUI_NameBrowse)
VALUES (542309 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        'Kontenhierarchie', 'P', 585598, 'N', 'D', 'N', 'N',
        584682, 'AccountHierarchy', 'Kontenhierarchie');

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description, t.Name, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Menu_ID = 542309
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID);

SELECT update_menu_translation_from_ad_element(542309);

-- =======================
-- AD_TreeNodeMM: Place both menus under "Buchhaltung" (Accounting)
-- Find the tree ID and the Buchhaltung folder dynamically
-- =======================
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, AD_Tree_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           Node_ID, Parent_ID, SeqNo)
SELECT 0, 0,
       (SELECT MIN(AD_Tree_ID) FROM AD_Tree WHERE AD_Client_ID = 0 AND IsActive = 'Y' AND IsAllNodes = 'Y' AND AD_Table_ID = 116),
       'Y', TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100,
       542308,
       -- Place under the "Buchhaltung" folder. Find it by name.
       COALESCE(
               (SELECT m.AD_Menu_ID FROM AD_Menu m WHERE m.Name = 'Buchhaltung' AND m.IsSummary = 'Y' AND m.IsActive = 'Y' LIMIT 1),
               0),
       900
WHERE NOT EXISTS (SELECT 1 FROM AD_TreeNodeMM tn WHERE tn.Node_ID = 542308 AND tn.AD_Tree_ID = (SELECT MIN(AD_Tree_ID) FROM AD_Tree WHERE AD_Client_ID = 0 AND IsActive = 'Y' AND IsAllNodes = 'Y' AND AD_Table_ID = 116));

INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, AD_Tree_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           Node_ID, Parent_ID, SeqNo)
SELECT 0, 0,
       (SELECT MIN(AD_Tree_ID) FROM AD_Tree WHERE AD_Client_ID = 0 AND IsActive = 'Y' AND IsAllNodes = 'Y' AND AD_Table_ID = 116),
       'Y', TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100,
       542309,
       COALESCE(
               (SELECT m.AD_Menu_ID FROM AD_Menu m WHERE m.Name = 'Buchhaltung' AND m.IsSummary = 'Y' AND m.IsActive = 'Y' LIMIT 1),
               0),
       910
WHERE NOT EXISTS (SELECT 1 FROM AD_TreeNodeMM tn WHERE tn.Node_ID = 542309 AND tn.AD_Tree_ID = (SELECT MIN(AD_Tree_ID) FROM AD_Tree WHERE AD_Client_ID = 0 AND IsActive = 'Y' AND IsAllNodes = 'Y' AND AD_Table_ID = 116));

-- =======================
-- AD_Table_Process: Register BPartnerBalance_Launcher on C_BPartner (quick action)
-- =======================
INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Process_ID, AD_Table_ID, EntityType,
                              WEBUI_DocumentAction, WEBUI_IncludedTabTopAction, WEBUI_ViewAction, WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
VALUES (541633 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-16 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        585597,
        291, -- C_BPartner table
        'D',
        'N', 'N', 'N', 'Y', 'N');
