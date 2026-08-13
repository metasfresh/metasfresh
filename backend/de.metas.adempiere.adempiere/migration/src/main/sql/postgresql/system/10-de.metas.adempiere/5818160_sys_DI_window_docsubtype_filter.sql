-- Migration: Add DI DocSubType filter to Lieferanweisungen window tab
-- AC-14: Filter Delivery Instructions (DI) DocSubType in window 541657
-- 2026-08-13

INSERT INTO AD_MigrationScript(AD_MigrationScript_ID, Name, Description, Script_Type, IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES(5818160, '5818160_sys_DI_window_docsubtype_filter', 'Add DI DocSubType filter to Lieferanweisungen (Delivery Instructions) window tab 546732', 'SQL', 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100);

-- Set WhereClause on AD_Tab 546732 (Lieferanweisungen tab, window 541657)
-- to filter for DI (Delivery Instructions) DocSubType only
UPDATE AD_Tab
SET WhereClause = 'exists(select 1 from c_doctype dt where M_ShipperTransportation.c_doctype_id = dt.c_doctype_id and dt.docsubtype = ''DI'')',
    Updated = CURRENT_TIMESTAMP,
    UpdatedBy = 100
WHERE AD_Tab_ID = 546732;
