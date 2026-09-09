-- Drops M_InOut.M_Delivery_Planning_ID (AD_Column 585626, added by 5673610), replaced by
-- M_InOutLine.M_Delivery_Planning_ID (AD_Column 593535, added by 5823550). The header holds ONE id while a
-- receipt or shipment legitimately aggregates lines of several plannings, so a column that looks authoritative
-- was at the wrong grain and invited the mistake this change set fixes.
--
-- Dependency sweep before dropping - pg_views / pg_matviews, pg_proc, AD_Val_Rule.Code, AD_Column.ColumnSQL,
-- EXP_FormatLine and AD_Field: NO consumer of M_InOut.M_Delivery_Planning_ID anywhere. Confirmed at attnum
-- level with pg_depend over pg_rewrite (the only check that distinguishes "selects this column" from "mentions
-- a column of that name"): zero dependent views or rules. The column is displayed in no window - it never had
-- an AD_Field - so no AD_Field / AD_UI_Element / AD_UserDef_Field chain has to be unwound, unlike
-- 5820940 which retired the same FK from M_ShipperTransportation. Its Java consumers (the two HU producers and
-- de.metas.deliveryplanning's interceptor/M_InOut) all moved to the line in this same change set.
--
-- The reverse link M_Delivery_Planning.M_InOut_ID stays: a planning is still received into, or shipped out of,
-- exactly ONE document, so no information is lost by dropping this side.
--
-- Do NOT rename this file once it has been applied anywhere: the runner's applied-check is keyed on the
-- script NAME with no checksum, so a rename makes it look unapplied and re-run, while whatever an edit
-- REMOVED stays applied and is undone by nothing.

-- Carry the historic links down to the grain they belong at, so a document completed before this change set
-- still says which planning it belongs to. Every such document was produced by a single-planning path (the
-- generate-receipt / generate-shipment processes, and the multi-row receive back when it grouped per
-- planning), so all of its material lines belong to that one planning. Restricted to lines that carry an
-- order line: a packing-material line has none, and belongs to no planning.
UPDATE M_InOutLine iol
   SET M_Delivery_Planning_ID = io.M_Delivery_Planning_ID,
       Updated = TO_TIMESTAMP('2026-09-09 00:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
  FROM M_InOut io
 WHERE io.M_InOut_ID = iol.M_InOut_ID
   AND io.M_Delivery_Planning_ID IS NOT NULL
   AND iol.C_OrderLine_ID IS NOT NULL
   AND iol.M_Delivery_Planning_ID IS NULL
;

DELETE FROM AD_Column_Trl WHERE AD_Column_ID = 585626
;
DELETE FROM AD_Column WHERE AD_Column_ID = 585626
;

-- Backup before the physical DROP COLUMN.
SELECT backup_table('m_inout', '_drop_M_Delivery_Planning_ID');

ALTER TABLE M_InOut DROP CONSTRAINT IF EXISTS MDeliveryPlanning_MInOut
;

/* DDL */ SELECT public.db_alter_table('M_InOut', 'ALTER TABLE public.M_InOut DROP COLUMN IF EXISTS M_Delivery_Planning_ID')
;
