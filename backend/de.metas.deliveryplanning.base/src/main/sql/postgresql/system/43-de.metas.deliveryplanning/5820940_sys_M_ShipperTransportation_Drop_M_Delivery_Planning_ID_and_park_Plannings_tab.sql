-- Drops M_ShipperTransportation.M_Delivery_Planning_ID, superseded by M_Delivery_Planning_Alloc, and
-- parks AD_Tab 546754 (the tab that used to bind to this column).
-- Every SQL/AD consumer was re-pointed onto M_Delivery_Planning_Alloc by 5820910 / 5820920 / 5820930;
-- dependency sweep against the live DB (pg_views, pg_proc, AD_Val_Rule.Code, AD_Column.ColumnSQL,
-- EXP_FormatLine by AD_Column_ID) found no other consumer of this column. Applied, and the model
-- classes (I_M_ShipperTransportation, X_M_ShipperTransportation) regenerated, by 00e448d0841.
--
-- THIS SCRIPT WAS EDITED IN PLACE AND RENAMED (was
-- ..._Drop_M_Delivery_Planning_ID_and_Plannings_tab_rebind.sql). It is legal because neither this file nor
-- 5820860 has ever reached a base branch (`git log origin/deep_tundra_release -- <path>` is empty for
-- both), and migration-script immutability binds on INTEGRATION, not on local application.
--
-- The hazard that carries, named so the next reader can re-check it rather than trust this comment: the
-- runner's applied-check is `SELECT COUNT(1) FROM AD_MigrationScript WHERE ProjectName=? AND Name=?`
-- (SQLDatabaseScriptsRegistry.dbIsApplied, de.metas.migration.base .../impl/SQLDatabaseScriptsRegistry.java:106)
-- -- keyed on the NAME, with no checksum. So on any environment that already ran the OLD file, the rename
-- makes this script look unapplied and re-run, while the statements the edit REMOVED (the AD_Element 581962
-- re-caption and the AD_UI_Element grid columns on tab 546754) stay applied and are not undone by anything.
--
-- Checked, not assumed, on 2026-08-28: every local stack on this box was queried with
--   select name from ad_migrationscript where name like '%5820940%' or name like '%5820980%';
-- across ports 21632, 21432, 22432, 21732, 21832, 21941. Only 21632 had ever recorded either script, and it
-- now records only the new name (its old rows were dropped and the edited scripts re-applied, then the
-- removed statements' effects reverted by hand). The branch has never been merged to a base branch and has
-- never been rolled out to any instance, so the set of environments carrying un-reverted effects is EMPTY --
-- and a compensating migration for an empty set is a guard with no failure scenario it prevents. If this
-- branch is ever applied somewhere else before it merges, that query is what catches it.

-- Park AD_Tab 546754 instead of re-purposing it.
--
-- Owner decision 2026-08-28: window 541657 keeps the pre-branch TWO-tab shape -- lines (546736
-- "Versandpaket", untouched) plus a HISTORY tab over INACTIVE M_Delivery_Planning_Alloc rows. The
-- forward "which plannings are on this instruction" question is answered by Related Documents (the
-- AD_RelationType added in 5821190), not by a third tab. 546754 is reserved for the future
-- multi-leg / N:N display, so it is taken OFF the window here and left in the AD untouched
-- otherwise: its AD_Element 581962 keeps its pre-branch caption and no grid columns are wired.
--
-- Only the parent binding has to move, and only because it is FK-forced: 546754 bound to its parent
-- through AD_Tab.Parent_Column_ID = 585609 (M_ShipperTransportation.M_Delivery_Planning_ID), and the
-- FK constraint parentcolumn_adtab refuses to let AD_Column 585609 be deleted while the tab still
-- references it. So the tab is re-pointed at 540426 -- M_ShipperTransportation.M_ShipperTransportation_ID,
-- the OWN key column of the window's header tab (546732, table 540030), which is what
-- GridTabVOBasedDocumentEntityDescriptorFactory.extractChildParentLinkColumnNames() resolves against
-- the PARENT tab -- and AD_Column_ID (was 585629) plus WhereClause (named @M_Delivery_Planning_ID@, a
-- context variable the aggregated header no longer supplies) are cleared, because both name the
-- retired 1:1 column. IsActive='N' parks it.
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

-- Backup before the physical DROP COLUMN, even though the current data is already migrated
-- to M_Delivery_Planning_Alloc by 5820530 (cheap defensive backup per metasfresh-db skill).
SELECT backup_table('m_shippertransportation', '_drop_M_Delivery_Planning_ID');

-- db_alter_table's second argument is the COMPLETE statement, not a fragment - it drops the dependent
-- views, executes the DDL verbatim, then recreates them. Same form as this branch's own IsB2B drop (5820510).
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation', 'ALTER TABLE public.M_ShipperTransportation DROP COLUMN IF EXISTS M_Delivery_Planning_ID')
;
