-- gh#28967: Restore Package_Licensing_Product_Report (was dropped in 5793140)
-- Now implemented as product-level aggregation of Package_Licensing_InOut_Report.
-- Same parameters (DateFrom, DateTo, C_Country_ID, IsIncludeAllProducts) and same
-- columns as the detail report, minus per-InOut fields.

-- ==========================================================================
-- 1) SQL Function — aggregates the InOut Report to product level
-- ==========================================================================

DROP FUNCTION IF EXISTS report.Package_Licensing_Product_Report(numeric);
DROP FUNCTION IF EXISTS report.Package_Licensing_Product_Report(numeric, varchar);
DROP FUNCTION IF EXISTS report.Package_Licensing_Product_Report(timestamp with time zone, timestamp with time zone, numeric, varchar);

CREATE OR REPLACE FUNCTION report.Package_Licensing_Product_Report(
    p_DateFrom             timestamp with time zone,
    p_DateTo               timestamp with time zone,
    p_C_Country_ID         numeric,
    p_IsIncludeAllProducts varchar DEFAULT 'Y'
)
    RETURNS TABLE
            (
                ProductValue               varchar,
                ProductName                varchar,
                MovementQty                numeric,
                PurchaseQty                numeric,
                ForeignSalesQty            numeric,
                UOMSymbol                  varchar,
                Weight                     numeric,
                ProductGroup               varchar,
                MaterialType               varchar,
                SmallPackagingMaterial      varchar,
                SmallPackagingWeight        numeric,
                OuterPackagingMaterial      varchar,
                OuterPackagingWeight        numeric,
                PackagingInstructionFactor  numeric
            )
    LANGUAGE sql
    STABLE
AS
$$
SELECT r.ProductValue,
       r.ProductName,
       SUM(r.MovementQty)       AS MovementQty,
       SUM(r.PurchaseQty)       AS PurchaseQty,
       SUM(r.ForeignSalesQty)   AS ForeignSalesQty,
       r.UOMSymbol,
       r.Weight,
       r.ProductGroup,
       r.MaterialType,
       r.SmallPackagingMaterial,
       r.SmallPackagingWeight,
       r.OuterPackagingMaterial,
       r.OuterPackagingWeight,
       r.PackagingInstructionFactor
FROM report.Package_Licensing_InOut_Report(
             p_DateFrom := p_DateFrom,
             p_DateTo := p_DateTo,
             p_Country_id := p_C_Country_ID,
             p_IsIncludeAllProducts := p_IsIncludeAllProducts
     ) r
GROUP BY r.ProductValue,
         r.ProductName,
         r.UOMSymbol,
         r.Weight,
         r.ProductGroup,
         r.MaterialType,
         r.SmallPackagingMaterial,
         r.SmallPackagingWeight,
         r.OuterPackagingMaterial,
         r.OuterPackagingWeight,
         r.PackagingInstructionFactor
ORDER BY r.ProductValue, r.ProductName;
$$;

-- ==========================================================================
-- 2) Restore AD_Process 585578
-- ==========================================================================

INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, CopyFromProcess, Created, CreatedBy, EntityType, IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsLogWarning, IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders, IsUpdateExportDate, IsUseBPartnerLanguage, JasperReport, LockWaitTimeout, Name, PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat, SQLStatement, Type, Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585578, 'Y', 'de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess', 'N', '2026-03-21 16:00', 100, 'D', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'Y', '', 0, 'Produkt Verpackungslizenzierung Export', 'json', 'N', 'Y', 'xls',
        'SELECT * FROM report.Package_Licensing_Product_Report(@DateFrom/null@, @DateTo/null@, @C_Country_ID/null@, ''@IsIncludeAllProducts@'')',
        'Excel', '2026-03-21 16:00', 100, 'Package-Licensing-Product-Report')
;

INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585578
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

-- German translations
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Produkt Verpackungslizenzierung Export', Updated='2026-03-21 16:00', UpdatedBy=100
WHERE AD_Language = 'de_DE' AND AD_Process_ID = 585578;

UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Produkt Verpackungslizenzierung Export', Updated='2026-03-21 16:00', UpdatedBy=100
WHERE AD_Language = 'de_CH' AND AD_Process_ID = 585578;

-- ==========================================================================
-- 3) AD_Process_Para: DateFrom (ID=543161, mandatory)
-- ==========================================================================

INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID,
                             AD_Reference_ID, ColumnName, Created, CreatedBy, Description, EntityType, FieldLength,
                             Help, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange,
                             Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 1581, 0, 585578, 543161 /*From ID Server*/,
        15, 'DateFrom', '2026-03-21 16:00', 100, 'Startdatum eines Abschnittes', 'U', 0,
        'Datum von bezeichnet das Startdatum eines Abschnittes', 'Y', 'N', 'Y', 'N', 'Y', 'N',
        'Datum von', 10, '2026-03-21 16:00', 100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name,
                                 IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543161
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- ==========================================================================
-- 4) AD_Process_Para: DateTo (ID=543162, mandatory)
-- ==========================================================================

INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID,
                             AD_Reference_ID, ColumnName, Created, CreatedBy, Description, EntityType, FieldLength,
                             Help, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange,
                             Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 1582, 0, 585578, 543162 /*From ID Server*/,
        15, 'DateTo', '2026-03-21 16:00', 100, 'Enddatum eines Abschnittes', 'D', 0,
        'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)', 'Y', 'N', 'Y', 'N', 'Y', 'N',
        'Datum bis', 20, '2026-03-21 16:00', 100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name,
                                 IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543162
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- ==========================================================================
-- 5) AD_Process_Para: C_Country_ID (ID=543129, mandatory)
-- ==========================================================================

INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, AD_Val_Rule_ID, ColumnName, Created, CreatedBy, Description, EntityType, FieldLength, Help, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 192, 0, 585578, 543129, 19, 540744, 'C_Country_ID', '2026-03-21 16:00', 100, 'Land', 'U', 0, '"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.', 'Y', 'N', 'Y', 'N', 'Y', 'N', 'Land', 30, '2026-03-21 16:00', 100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543129
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- ==========================================================================
-- 6) AD_Process_Para: IsIncludeAllProducts (ID=543160, mandatory, default Y)
-- ==========================================================================

INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID,
                             AD_Reference_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength,
                             IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange,
                             Name, SeqNo, DefaultValue, Updated, UpdatedBy)
VALUES (0, 584691, 0, 585578, 543160 /*From ID Server*/,
        20, 'IsIncludeAllProducts', '2026-03-21 16:00', 100, 'D', 0,
        'Y', 'N', 'Y', 'N', 'Y', 'N',
        'Alle Produkte einbeziehen', 40, 'Y', '2026-03-21 16:00', 100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name,
                                 IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543160
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- Propagate element translations to process parameter labels
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'en_US');

-- ==========================================================================
-- 7) Restore AD_Element 584562 for menu entry (if not exists)
-- ==========================================================================

INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
SELECT 0, 584562, 0, '2026-03-21 16:00', 100, 'D', 'Y', 'Produkt Verpackungslizenzierung Export', 'Produkt Verpackungslizenzierung Export', '2026-03-21 16:00', 100
WHERE NOT EXISTS (SELECT 1 FROM AD_Element WHERE AD_Element_ID = 584562)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584562
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET IsTranslated='N', Updated='2026-03-21 16:00', UpdatedBy=100
WHERE AD_Element_ID = 584562 AND AD_Language = 'de_DE';
SELECT update_ad_element_on_ad_element_trl_update(584562, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584562, 'de_DE');

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated='2026-03-21 16:00', UpdatedBy=100
WHERE AD_Element_ID = 584562 AND AD_Language = 'de_CH';
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584562, 'de_CH');

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Package Licensing Product Export', PrintName='Package Licensing Product Export', Updated='2026-03-21 16:00', UpdatedBy=100
WHERE AD_Element_ID = 584562 AND AD_Language = 'en_US';
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584562, 'en_US');

-- ==========================================================================
-- 8) Restore AD_Menu 542300
-- ==========================================================================

INSERT INTO AD_Menu (Action, AD_Client_ID, AD_Element_ID, AD_Menu_ID, AD_Org_ID, AD_Process_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary, Name, Updated, UpdatedBy)
VALUES ('R', 0, 584562, 542300, 0, 585578, '2026-03-21 16:00', 100, 'D', 'Package-Licensing-Product-Report', 'Y', 'N', 'N', 'N', 'N', 'Produkt Verpackungslizenzierung Export', '2026-03-21 16:00', 100)
;

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description, t.Name, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Menu_ID = 542300
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID)
;

INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT t.AD_Client_ID, 0, 'Y', '2026-03-21 16:00', 100, '2026-03-21 16:00', 100, t.AD_Tree_ID, 542300, 0, 999
FROM AD_Tree t
WHERE t.AD_Client_ID = 0
  AND t.IsActive = 'Y'
  AND t.IsAllNodes = 'Y'
  AND t.AD_Table_ID = 116
  AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID = t.AD_Tree_ID AND Node_ID = 542300)
;

SELECT update_menu_translation_from_ad_element(584562);

-- ==========================================================================
-- 9) Reorder children of "Verpackungslizenzierung" (parent_id=542248)
-- ==========================================================================

UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=0, Updated='2026-03-21 16:00', UpdatedBy=100
WHERE Node_ID = 542252 AND AD_Tree_ID = 10;

UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=1, Updated='2026-03-21 16:00', UpdatedBy=100
WHERE Node_ID = 542253 AND AD_Tree_ID = 10;

UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=2, Updated='2026-03-21 16:00', UpdatedBy=100
WHERE Node_ID = 542300 AND AD_Tree_ID = 10;

UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=3, Updated='2026-03-21 16:00', UpdatedBy=100
WHERE Node_ID = 542250 AND AD_Tree_ID = 10;

UPDATE AD_TreeNodeMM SET Parent_ID=542248, SeqNo=4, Updated='2026-03-21 16:00', UpdatedBy=100
WHERE Node_ID = 542249 AND AD_Tree_ID = 10;
