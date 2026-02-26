-- Run mode: SWING_CLIENT

-- gh#28225: Package Licensing Product Export
-- AD_Process using ExportToSpreadsheetProcess with the new SQL function

-- ==========================================================================
-- AD_Process (ID=585578)
-- ==========================================================================

-- Value: Package-Licensing-Product-Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, CopyFromProcess, Created, CreatedBy, EntityType, IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsLogWarning, IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders, IsUpdateExportDate, IsUseBPartnerLanguage, JasperReport, LockWaitTimeout, Name, PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat, SQLStatement, Type, Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585578, 'Y', 'de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess', 'N', TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'), 100, 'D', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'Y', '', 0, 'Package Licensing Product Export', 'json', 'N', 'Y', 'xls',
        'SELECT * FROM report.Package_Licensing_Product_Report(@C_Country_ID/null@)',
        'Excel', TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'), 100, 'Package-Licensing-Product-Report');

INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585578
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID);

-- German translation
UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Name='Produkt Verpackungslizenzierung Export',
    Updated=TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'),
    UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Process_ID = 585578;

UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Name='Produkt Verpackungslizenzierung Export',
    Updated=TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'),
    UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Process_ID = 585578;

UPDATE AD_Process base
SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Process_Trl trl
WHERE trl.AD_Process_ID = base.AD_Process_ID
  AND trl.AD_Language = 'de_CH'
  AND trl.AD_Language = getBaseLanguage();

-- ==========================================================================
-- AD_Process_Para: C_Country_ID (ID=543129, optional)
-- ==========================================================================

INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, AD_Val_Rule_ID, ColumnName, Created, CreatedBy, Description, EntityType, FieldLength, Help, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 192, 0, 585578, 543129, 19, 540744, 'C_Country_ID', TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'), 100, 'Land', 'U', 0, '"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.', 'Y', 'N', 'Y', 'N', 'N', 'N', 'Land', 10, TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'), 100);

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543129
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID);

-- ==========================================================================
-- AD_Element (ID=584562) for menu entry
-- ==========================================================================

INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 584562, 0, TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'), 100, 'D', 'Y', 'Produkt Verpackungslizenzierung Export', 'Produkt Verpackungslizenzierung Export', TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'), 100);

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584562
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- German translations
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'),
    UpdatedBy=100
WHERE AD_Element_ID = 584562
  AND AD_Language = 'de_CH';

/* DDL */
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584562, 'de_CH');

UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'),
    UpdatedBy=100
WHERE AD_Element_ID = 584562
  AND AD_Language = 'de_DE';

/* DDL */
SELECT update_ad_element_on_ad_element_trl_update(584562, 'de_DE');

/* DDL */
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584562, 'de_DE');

-- English translation
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Name='Package Licensing Product Export',
    PrintName='Package Licensing Product Export',
    Updated=TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'),
    UpdatedBy=100
WHERE AD_Element_ID = 584562
  AND AD_Language = 'en_US';

UPDATE AD_Element base
SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Element_Trl trl
WHERE trl.AD_Element_ID = base.AD_Element_ID
  AND trl.AD_Language = 'en_US'
  AND trl.AD_Language = getBaseLanguage();

/* DDL */
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584562, 'en_US');

-- ==========================================================================
-- AD_Menu (ID=542300)
-- ==========================================================================

INSERT INTO AD_Menu (Action, AD_Client_ID, AD_Element_ID, AD_Menu_ID, AD_Org_ID, AD_Process_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary, Name, Updated, UpdatedBy)
VALUES ('R', 0, 584562, 542300, 0, 585578, TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'), 100, 'D', 'Package-Licensing-Product-Report', 'Y', 'N', 'N', 'N', 'N', 'Produkt Verpackungslizenzierung Export', TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'), 100);

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description, t.Name, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Menu t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Menu_ID = 542300
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID);

-- Insert tree node (initially at root level)
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT t.AD_Client_ID, 0, 'Y', TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'), 100, TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'), 100, t.AD_Tree_ID, 542300, 0, 999
FROM AD_Tree t
WHERE t.AD_Client_ID = 0
  AND t.IsActive = 'Y'
  AND t.IsAllNodes = 'Y'
  AND t.AD_Table_ID = 116
  AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID = t.AD_Tree_ID AND Node_ID = 542300);

/* DDL */
SELECT update_menu_translation_from_ad_element(584562);

-- ==========================================================================
-- Reorder children of "Verpackungslizenzierung" (parent_id=542248)
-- Insert new node at SeqNo=2, after the two existing reports
-- ==========================================================================

-- Node name: Verpackungslizenzierungs-Bewegungsdetails
UPDATE AD_TreeNodeMM
SET Parent_ID=542248,
    SeqNo=0,
    Updated=TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'),
    UpdatedBy=100
WHERE Node_ID = 542252
  AND AD_Tree_ID = 10;

-- Node name: Zusammenfassung Verpackungslizenzbewegungen
UPDATE AD_TreeNodeMM
SET Parent_ID=542248,
    SeqNo=1,
    Updated=TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'),
    UpdatedBy=100
WHERE Node_ID = 542253
  AND AD_Tree_ID = 10;

-- Node name: Package Licensing Product Export (NEW)
UPDATE AD_TreeNodeMM
SET Parent_ID=542248,
    SeqNo=2,
    Updated=TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'),
    UpdatedBy=100
WHERE Node_ID = 542300
  AND AD_Tree_ID = 10;

-- Node name: Product group (M_PackageLicensing_ProductGroup)
UPDATE AD_TreeNodeMM
SET Parent_ID=542248,
    SeqNo=3,
    Updated=TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'),
    UpdatedBy=100
WHERE Node_ID = 542250
  AND AD_Tree_ID = 10;

-- Node name: Material group (M_PackageLicensing_MaterialGroup)
UPDATE AD_TreeNodeMM
SET Parent_ID=542248,
    SeqNo=4,
    Updated=TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'),
    UpdatedBy=100
WHERE Node_ID = 542249
  AND AD_Tree_ID = 10;
