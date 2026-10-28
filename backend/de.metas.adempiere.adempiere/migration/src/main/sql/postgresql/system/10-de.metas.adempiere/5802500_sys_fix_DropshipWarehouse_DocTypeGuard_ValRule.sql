-- Fix syntax that broke the M_Warehouse dropdown lookup on the C_Order header.
--
-- The original AD_Val_Rule.Code (id=540783, set by script 5801790) used a multi-line
-- 'EXISTS (SELECT 1 FROM C_DocType dt WHERE ...)' structure with inline '--' comments.
-- The framework's lookup-SQL assembler walks the val-rule to resolve table aliases
-- for the security-record clause it appends, and the nested FROM/WHERE confuses it:
-- the security-record clause comes out as 'Record_ID=WHERE.M_Warehouse_ID' (literal
-- 'WHERE' substituted where the table alias should be), causing a PostgreSQL
-- syntax error when the dropdown loads.
--
-- Rewrite as a single-line boolean expression with the doc-type lookup expressed as
-- an IN-subquery (no nested EXISTS, no inline comments). Semantically equivalent.

UPDATE AD_Val_Rule
SET Code      = 'COALESCE(M_Warehouse.IsDropShipWarehouse,''N'')=''N'' OR @C_DocTypeTarget_ID@ IN (SELECT C_DocType_ID FROM C_DocType WHERE DocSubType=''SO'')',
    Updated   = TO_TIMESTAMP('2026-05-13 10:00:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Val_Rule_ID = 540783;
