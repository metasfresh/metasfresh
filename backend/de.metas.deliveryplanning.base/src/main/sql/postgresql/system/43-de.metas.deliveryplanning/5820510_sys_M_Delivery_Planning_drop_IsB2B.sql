-- Drop M_Delivery_Planning.IsB2B: the B2B/dropship information now lives entirely in
-- M_Delivery_Planning_Type, so the separate flag is redundant.
--
-- Conversion rule, asymmetric on purpose:
--   Incoming + IsB2B='Y'  -> retype to Dropship (the purchase-side leg of a dropship).
--   Outgoing + IsB2B='Y'  -> stays Outgoing, the flag is just dropped. This is the sales-side twin a
--                            dropship still produces; its dropship-ness stays derivable through
--                            C_PO_OrderLine_Alloc. Retyping it too would conflate the two legs.
--
-- THIS SCRIPT MUST NEVER FAIL: IsB2B is NOT NULL CHAR(1) carrying only 'Y'/'N', so the UPDATE below is
-- a total function of the two existing columns -- no branch, no fallback, no unclassified row.
--
-- AD_Element 581680 stays active: it is still referenced by AD_Process_Para 542516, a parameter of
-- process 585192 "Generate Goods Receipt" whose value is derived at runtime from
-- getB2BShipmentInfo().isPresent(), not read from this column.
-- ===========================================================================
-- 1. Back up the whole table BEFORE anything is written
-- ===========================================================================
SELECT backup_table('m_delivery_planning', '_isb2b_drop');

-- ===========================================================================
-- 2. Retype the purchase-side dropship legs (see the conversion rule above).
--    IsB2B is dropped from every row in step 4 regardless of direction.
-- ===========================================================================
UPDATE M_Delivery_Planning
   SET M_Delivery_Planning_Type = 'Dropship',
       Updated   = TO_TIMESTAMP('2026-08-27 09:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 99
 WHERE M_Delivery_Planning_Type = 'Incoming'
   AND IsB2B = 'Y';

-- ===========================================================================
-- 3. Remove the AD rows that reference the column, then the column itself.
--    Anchored on AD_Column_ID rather than on a literal AD_Field_ID: six of the eight FKs into
--    AD_Field are NO ACTION, so one unreached row -- an override window's own AD_Field, a user's
--    saved sort -- makes the DELETE FROM AD_Column violate ad_column_field and aborts the whole
--    migration run. This narrows the exposure to the AD_Field side; it does not close it, since
--    38 of the 44 FKs into AD_Column are NO ACTION too.
-- ===========================================================================
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585006)
;
DELETE FROM AD_UI_Element WHERE Labels_Selector_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585006)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585006)
;
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585006)
;
DELETE FROM AD_Field_ContextMenu WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585006)
;
DELETE FROM AD_UI_ElementField WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585006)
;
DELETE FROM AD_UserDef_Field WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585006)
;
DELETE FROM AD_User_SortPref_Line WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585006)
;
DELETE FROM AD_Field WHERE AD_Column_ID = 585006
;

DELETE FROM AD_Column_Trl WHERE AD_Column_ID = 585006
;
DELETE FROM AD_Column WHERE AD_Column_ID = 585006
;

-- ===========================================================================
-- 4. Drop the physical column
-- ===========================================================================
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning', 'ALTER TABLE public.M_Delivery_Planning DROP COLUMN IF EXISTS IsB2B');
