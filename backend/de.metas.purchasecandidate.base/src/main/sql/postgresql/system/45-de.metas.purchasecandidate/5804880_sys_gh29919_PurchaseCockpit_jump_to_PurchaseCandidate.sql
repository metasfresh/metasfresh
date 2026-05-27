-- Migration: 5804880
-- Issue: https://github.com/metasfresh/me03/issues/29919
-- Purpose: Add AD_RelationType + AD_Process for "Jump to Purchase Candidates" quick-action on the Purchase Cockpit
--          Consuming the OpenTarget framework from PR-A

-- ============================================================================
-- BLOCK A: Source AD_Reference + AD_Ref_Table (RV_PurchaseCockpit)
-- ============================================================================

INSERT INTO AD_Reference
  (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
   Name, Description, Help, ValidationType, VFormat, EntityType, IsOrderByValue)
VALUES
  (542097, 0, 0, 'Y', NOW(), 100, NOW(), 100,
   'RV_PurchaseCockpit (Purchase Cockpit jump source)', 'Einkaufsdisposition', NULL, 'T', NULL, 'D', 'N')
ON CONFLICT (AD_Reference_ID) DO NOTHING;

INSERT INTO AD_Ref_Table
  (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
   AD_Table_ID, AD_Key, AD_Display, IsValueDisplayed, WhereClause, OrderByClause, EntityType, AD_Window_ID, ShowInactiveValues)
VALUES
  (542097, 0, 0, 'Y', NOW(), 100, NOW(), 100,
   542581, 592051, 592051, 'N', NULL, NULL, 'D', NULL, 'N')
ON CONFLICT (AD_Reference_ID) DO NOTHING;

-- ============================================================================
-- BLOCK B: Target AD_Reference + AD_Ref_Table (C_PurchaseCandidate) WITH EXISTS WhereClause
-- ============================================================================

INSERT INTO AD_Reference
  (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
   Name, Description, Help, ValidationType, VFormat, EntityType, IsOrderByValue)
VALUES
  (542098, 0, 0, 'Y', NOW(), 100, NOW(), 100,
   'C_PurchaseCandidate (Purchase Cockpit jump target)', 'Bestelldisposition', NULL, 'T', NULL, 'D', 'N')
ON CONFLICT (AD_Reference_ID) DO NOTHING;

INSERT INTO AD_Ref_Table
  (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
   AD_Table_ID, AD_Key, AD_Display, IsValueDisplayed, WhereClause, OrderByClause, EntityType, AD_Window_ID, ShowInactiveValues)
VALUES
  (542098, 0, 0, 'Y', NOW(), 100, NOW(), 100,
   540861, 557857, 557857, 'N',
   $WC$EXISTS (
     SELECT 1 FROM RV_PurchaseCockpit rv
     WHERE rv.RV_PurchaseCockpit_ID = @RV_PurchaseCockpit_ID/-1@
       AND rv.AD_Org_ID      = C_PurchaseCandidate.AD_Org_ID
       AND rv.M_Product_ID   = C_PurchaseCandidate.M_Product_ID
       AND rv.M_Warehouse_ID = C_PurchaseCandidate.M_WarehousePO_ID
       AND COALESCE(rv.AttributesKey, '') = COALESCE(generateasistorageattributeskey(C_PurchaseCandidate.M_AttributeSetInstance_ID), '')
       AND C_PurchaseCandidate.QtyToPurchase > 0
   )$WC$,
   NULL, 'D', NULL, 'N')
ON CONFLICT (AD_Reference_ID) DO NOTHING;

-- ============================================================================
-- BLOCK C: AD_RelationType
-- ============================================================================

INSERT INTO AD_RelationType
  (AD_RelationType_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
   Name, Description, IsDirected, IsExplicit, Type, InternalName, Role_Source, Role_Target,
   AD_Reference_Source_ID, AD_Reference_Target_ID, EntityType, IsTableRecordIDTarget)
VALUES
  (540498, 0, 0, 'Y', NOW(), 100, NOW(), 100,
   'RV_PurchaseCockpit → C_PurchaseCandidate', NULL, 'Y', 'N', 'I', 'RV_PurchaseCockpit_C_PurchaseCandidate', NULL, NULL,
   542097, 542098, 'D', 'N')
ON CONFLICT (AD_RelationType_ID) DO NOTHING;

-- ============================================================================
-- BLOCK D: AD_Process + AD_Process_Trl
-- ============================================================================

INSERT INTO AD_Process
  (AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
   Value, Name, Description, Help, AccessLevel, EntityType, ProcedureName, IsReport, IsDirectPrint,
   ClassNAME, AD_ReportView_ID, AD_PrintFormat_ID, WorkflowValue, AD_Workflow_ID, IsBetaFunctionality,
   IsServerProcess, ShowHelp, JasperReport, AD_Form_ID, CopyFromProcess, LockWaitTimeout, RefreshAllAfterExecution,
   JasperReport_Tabular, AllowProcessRerun, IsUseBPartnerLanguage, IsApplySecuritySettings, TechnicalNote,
   IsTranslateExcelHeaders, JSONPath, IsNotifyUserAfterExecution, PostgRESTResponseFormat, IsFormatExcelFile,
   SpreadsheetFormat, CSVFieldDelimiter, IsUpdateExportDate, IsLogWarning, CSVFieldQuote,
   AD_RelationType_ID, IsIncludeCSVHeaderRow, FileNamePattern, OpenTarget, Type)
VALUES
  (585624, 0, 0, 'Y', NOW(), 100, NOW(), 100,
   'gh29919_JumpToPurchaseCandidate', 'Sprung zu Bestellvorschlägen', NULL, NULL, '3', 'D', NULL, 'N', 'N',
   'de.metas.ui.web.view.process.RelationTypeInOverlayProcess', NULL, NULL, NULL, NULL, 'N',
   'N', 'N', NULL, NULL, 'N', 0, 'N',
   NULL, 'Y', 'Y', 'N', NULL,
   'Y', NULL, 'N', 'json', 'Y',
   'xls', NULL, 'N', 'N', '"',
   540498, 'Y', NULL, 'N', 'RelationTypeInOverlay')
ON CONFLICT (AD_Process_ID) DO NOTHING;

-- Copy translations from base language
INSERT INTO AD_Process_Trl
  (AD_Process_ID, AD_Client_ID, AD_Org_ID, AD_Language, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, Name, Description, Help)
SELECT p.AD_Process_ID, p.AD_Client_ID, p.AD_Org_ID, l.AD_Language, p.IsActive, NOW(), 100, NOW(), 100,
       CASE WHEN l.AD_Language IN ('de_DE', 'en_US') THEN 'Y' ELSE 'N' END,
       CASE WHEN l.AD_Language = 'de_DE' THEN 'Sprung zu Bestellvorschlägen'
            WHEN l.AD_Language = 'en_US' THEN 'Jump to Purchase Candidates'
            ELSE p.Name
       END,
       NULL, NULL
FROM AD_Process p
CROSS JOIN AD_Language l
WHERE p.AD_Process_ID = 585624
  AND l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (
    SELECT 1 FROM AD_Process_Trl pt
    WHERE pt.AD_Process_ID = p.AD_Process_ID
      AND pt.AD_Language = l.AD_Language
  )
ON CONFLICT (AD_Process_ID, AD_Language) DO NOTHING;

-- ============================================================================
-- BLOCK E: AD_Table_Process
-- ============================================================================

INSERT INTO AD_Table_Process
  (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
   AD_Table_ID, AD_Process_ID, WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default, WEBUI_ViewAction,
   WEBUI_IncludedTabTopAction, EntityType)
VALUES
  (541644, 0, 0, 'Y', NOW(), 100, NOW(), 100,
   542581, 585624, 'Y', 'N', 'Y',
   'N', 'D')
ON CONFLICT (AD_Table_Process_ID) DO NOTHING;
