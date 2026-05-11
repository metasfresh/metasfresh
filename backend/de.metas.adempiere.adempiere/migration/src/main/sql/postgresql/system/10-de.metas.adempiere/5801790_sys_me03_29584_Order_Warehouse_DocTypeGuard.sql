-- me03#29584: Doc-subtype guard for dropship warehouse on C_Order (T4b)
--
-- DECISION: Field-level val-rule binding (NOT column-level).
-- Reason: The column-level rule (AD_Val_Rule_ID=540006, "M_Warehouse Org Order") already
-- applies globally to C_Order.M_Warehouse_ID for org/SO/PO filtering. Overriding it at
-- column level would affect ALL windows including the Purchase Order header (AD_Window 181,
-- AD_Field_ID=3451). Instead, we bind the new rule on the SO header fields only:
--   - AD_Field_ID=1114  (Auftrag, AD_Window 143, "Auftrag" tab)
--   - AD_Field_ID=544897 (B2B Auftrag, AD_Window 540072, "Auftrag" tab)
-- The Bestellung/PO field (AD_Field_ID=3451, AD_Window 181) is intentionally left unchanged.
--
-- LOGIC:
--   When DocSubType = 'SO' (Standardauftrag): all active warehouses are visible (no dropship restriction).
--   When DocSubType is anything else (quotation, prepay, RMA, etc.): dropship warehouses are hidden.
--
-- Context variable: @C_DocTypeTarget_ID@ is confirmed reachable on C_Order header
-- (see AD_Val_Rule 52030 "C_Charge by Doc Type Target" which uses the same variable).
--
-- Standard Sales Order DocSubType = 'SO' verified by:
--   SELECT DocBaseType, DocSubType, Name FROM C_DocType WHERE DocBasetype='SOO' ORDER BY DocSubType;
--   → DocSubType='SO', Name='Standardauftrag'

-- =============================================================================
-- 1. Insert new AD_Val_Rule
-- =============================================================================
INSERT INTO AD_Val_Rule
    (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, Type, Code, EntityType)
VALUES
    (540783 /*From ID Server*/, 0, 0, 'Y',
     '2026-05-11 12:00', 100, '2026-05-11 12:00', 100,
     'M_Warehouse for C_Order (no dropship unless Standard SO)',
     'S',
     'M_Warehouse.IsActive=''Y''
AND (
  -- Standard Sales Order (DocSubType=''SO''): all warehouses allowed
  EXISTS (
    SELECT 1 FROM C_DocType dt
    WHERE dt.C_DocType_ID = @C_DocTypeTarget_ID@
      AND dt.DocSubType   = ''SO''
  )
  OR
  -- Any other doc-type (quotation, prepay, RMA, …): hide dropship warehouses
  (
    COALESCE(M_Warehouse.IsDropShipWarehouse, ''N'') = ''N''
  )
)',
     'D');

-- =============================================================================
-- 2. Bind the new val-rule on the SO header field in "Auftrag" window (AD_Window 143)
--    AD_Field_ID = 1114 (Lager on Auftrag tab)
-- =============================================================================
UPDATE AD_Field
SET    AD_Val_Rule_ID = 540783 /*From ID Server*/,
       Updated        = '2026-05-11 12:00',
       UpdatedBy      = 100
WHERE  AD_Field_ID = 1114;

-- =============================================================================
-- 3. Bind the new val-rule on the SO header field in "B2B Auftrag" window (AD_Window 540072)
--    AD_Field_ID = 544897 (Lager on Auftrag tab)
-- =============================================================================
UPDATE AD_Field
SET    AD_Val_Rule_ID = 540783 /*From ID Server*/,
       Updated        = '2026-05-11 12:00',
       UpdatedBy      = 100
WHERE  AD_Field_ID = 544897;
