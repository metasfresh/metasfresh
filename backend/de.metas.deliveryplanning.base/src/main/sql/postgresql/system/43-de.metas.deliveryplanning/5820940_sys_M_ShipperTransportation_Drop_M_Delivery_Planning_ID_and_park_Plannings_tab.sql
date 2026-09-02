-- Drops M_ShipperTransportation.M_Delivery_Planning_ID, replaced by M_Delivery_Planning_Alloc, and
-- deactivates AD_Tab 546754, which bound to that column. Every SQL and AD consumer now resolves through
-- M_Delivery_Planning_Alloc; a dependency sweep over pg_views, pg_proc, AD_Val_Rule.Code,
-- AD_Column.ColumnSQL and EXP_FormatLine found no other consumer.
--
-- Do NOT rename this file once it has been applied anywhere: the runner's applied-check is keyed on the
-- script NAME with no checksum, so a rename makes it look unapplied and re-run, while whatever an edit
-- REMOVED stays applied and is undone by nothing.

-- Window 541657 keeps two tabs: lines (546736) plus a history tab over inactive
-- M_Delivery_Planning_Alloc rows. "Which plannings are on this instruction" is answered by Related
-- Documents (an AD_RelationType). AD_Tab 546754 is reserved for a future multi-leg display, so it
-- is deactivated rather than re-purposed; ad_tab.ad_window_id is NOT NULL, so IsActive='N' is the only
-- park mechanism the schema offers.
--
-- Parent_Column_ID must move even so, because it is FK-forced: 546754 bound to its parent through
-- Parent_Column_ID=585609, and constraint parentcolumn_adtab refuses to delete AD_Column 585609 while
-- the tab references it. It is re-pointed at 540426 (M_ShipperTransportation_ID, the own key of header
-- tab 546732), which is what extractChildParentLinkColumnNames() resolves against the parent tab.
-- AD_Column_ID and WhereClause are cleared because both name the retired 1:1 column.
UPDATE AD_Tab
   SET IsActive='N',
       AD_Column_ID=NULL,
       Parent_Column_ID=540426,
       WhereClause=NULL,
       Updated=TO_TIMESTAMP('2026-08-27 16:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Tab_ID=546754
;

-- FK-chain cleanup, anchored on AD_Column_ID=585609 (M_ShipperTransportation.M_Delivery_Planning_ID),
-- so it also covers any future custom-window AD_Field for this column, not just AD_Field 710779.
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585609)
;
DELETE FROM AD_UI_Element WHERE Labels_Selector_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585609)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585609)
;
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585609)
;
DELETE FROM AD_Field_ContextMenu WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585609)
;
DELETE FROM AD_UI_ElementField WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585609)
;
DELETE FROM AD_UserDef_Field WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585609)
;
DELETE FROM AD_User_SortPref_Line WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 585609)
;
DELETE FROM AD_Field WHERE AD_Column_ID = 585609
;

DELETE FROM AD_Column_Trl WHERE AD_Column_ID = 585609
;
DELETE FROM AD_Column WHERE AD_Column_ID = 585609
;

-- Backup before the physical DROP COLUMN.
SELECT backup_table('m_shippertransportation', '_drop_M_Delivery_Planning_ID');

/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation', 'ALTER TABLE public.M_ShipperTransportation DROP COLUMN IF EXISTS M_Delivery_Planning_ID')
;
