-- Drop M_Delivery_Planning.IsB2B: the B2B/dropship information now lives entirely in
-- M_Delivery_Planning_Type (added by 5820420), so the separate flag is redundant.
--
-- Conversion rule (asymmetric on purpose -- decided in AGGREGATION-PROPOSAL.md, section
-- "Consequence for section 4c.1's migration guard, now that A is chosen", under section 4c ---
-- shape A keeps a dropship's sales-side twin planning as a separate Outgoing record this
-- iteration; only its typing/flag handling changes here, the twin's suppression is deferred):
--   Incoming + IsB2B='Y'  -> retype to Dropship. This is the purchase-side leg of a dropship: the
--                            planning whose type now carries what the flag used to.
--   Outgoing + IsB2B='Y'  -> stays Outgoing, the flag is just dropped. This is the sales-side twin
--                            a dropship still produces today (consolidating the two legs into one
--                            planning is deferred to a follow-up). Its dropship-ness stays
--                            derivable through C_PO_OrderLine_Alloc (SO line -> PO line -> the
--                            Dropship planning), so nothing is lost by not storing it on this row.
-- Not made symmetric on purpose: retyping the Outgoing rows too would conflate the two legs of a
-- dropship into one type, which the deferred-consolidation decision explicitly avoided.
--
-- THIS SCRIPT MUST NEVER FAIL: IsB2B is NOT NULL CHAR(1) with only 'Y'/'N' ever written (verified
-- on deep_tundra_uat_2), so the UPDATE below is a total function of the two existing columns --
-- no branch, no fallback, no row can be left unclassified.
--
-- Live counts before this script (deep_tundra_uat_2, port 21632), by
-- (M_Delivery_Planning_Type, IsB2B): Incoming/Y=5 (-> retyped to Dropship), Outgoing/N=4
-- (untouched, flag just dropped). No Incoming/N or Outgoing/Y rows exist on this stack.
--
-- IDs: only AD_MigrationScript (5820510, from ID server) is needed -- this script only removes AD
-- rows, it creates none.
--
-- Dependency sweep of the column before dropping it (live DB, deep_tundra_uat_2):
--   AD_Column       585006  M_Delivery_Planning.IsB2B, AD_Element 581680
--   AD_Column_Trl   4 rows (de_CH / de_DE / en_US / fr_CH)
--   AD_Field        708077  tab 546674 (M_Delivery_Planning window 541632)
--   AD_Field_Trl    4 rows (de_CH / de_DE / en_US / fr_CH)
--   AD_Element_Link 1012714 (field 708077 <-> element 581680)
--   AD_UI_Element   613483 (field 708077); no AD_UI_ElementField rows reference it
--   AD_Field_ContextMenu / AD_UserDef_Field / AD_User_SortPref_Line / any
--     AD_UI_Element.Labels_Selector_Field_ID: 0 rows each
--   pg_views / pg_proc / AD_Val_Rule.code / AD_Column.ColumnSQL / pg_indexes / pg_matviews: no
--     hit for 'isb2b' anywhere in the live DB
--
-- Explicitly NOT touched -- AD_Element 581680 stays active (still referenced by AD_Process_Para
-- 542516), and 542516 itself stays active. It is a parameter of process 585192 "Generate Goods
-- Receipt" (M_Delivery_Planning_GenerateReceipt); its value is derived at runtime from
-- getB2BShipmentInfo().isPresent() (M_Delivery_Planning_GenerateReceipt.getParameterDefaultValue),
-- not read from this column, so dropping the column does not affect it. Live query against
-- ad_process_para for processes 585192/585194 found only 542516 referencing element 581680 -- a
-- second parameter (585194's "Generate Goods Issue") does not have one.
--
-- Java-side counterpart (not part of this script): DeliveryPlanningRepository's
-- setIsB2B(getDeliveryPlanningType().isDropship()) write and the cucumber assertion/table column
-- on isB2B() are removed in the same commit, and the generated model classes are regenerated
-- after this migration applies.

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
-- 3. Remove the AD rows that reference the column: the field, its
--    translations, its element link, its UI element -- then the column
--    itself. AD_Element 581680 and AD_Process_Para 542516 are left alone
--    (see header).
-- ===========================================================================
DELETE FROM AD_Field_Trl    WHERE AD_Field_ID = 708077;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 708077;
DELETE FROM AD_UI_Element   WHERE AD_Field_ID = 708077;
DELETE FROM AD_Field        WHERE AD_Field_ID = 708077;

DELETE FROM AD_Column_Trl WHERE AD_Column_ID = 585006;
DELETE FROM AD_Column     WHERE AD_Column_ID = 585006;

-- ===========================================================================
-- 4. Drop the physical column
-- ===========================================================================
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning', 'ALTER TABLE public.M_Delivery_Planning DROP COLUMN IF EXISTS IsB2B');

-- ===========================================================================
-- 5. Verification -- run by hand after applying
-- ===========================================================================
-- (a) SELECT count(*) FROM M_Delivery_Planning WHERE M_Delivery_Planning_Type='Dropship';
--     -- expect 5
-- (b) SELECT count(*) FROM M_Delivery_Planning WHERE IsB2B='Y';
--     -- must ERROR: column "isb2b" does not exist
-- (c) SELECT count(*) FROM AD_Field f JOIN AD_Column c ON c.AD_Column_ID=f.AD_Column_ID
--      WHERE c.AD_Column_ID=585006;
--     -- expect 0
-- (d) SELECT count(*) FROM AD_UI_Element WHERE AD_Field_ID=708077;
--     -- expect 0
-- (e) SELECT AD_Element_ID, IsActive FROM AD_Element WHERE AD_Element_ID=581680;
--     -- still present, IsActive='Y'
-- (f) SELECT AD_Process_Para_ID, IsActive FROM AD_Process_Para WHERE AD_Process_Para_ID=542516;
--     -- still present, IsActive='Y'
