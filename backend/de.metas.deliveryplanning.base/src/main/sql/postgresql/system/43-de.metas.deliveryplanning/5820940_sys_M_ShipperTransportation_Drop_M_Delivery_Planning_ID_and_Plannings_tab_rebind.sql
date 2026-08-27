-- Drops M_ShipperTransportation.M_Delivery_Planning_ID, superseded by M_Delivery_Planning_Alloc, and
-- re-parents AD_Tab 546754 (the tab that used to bind to this column) directly onto the instruction.
-- Every SQL/AD consumer was re-pointed onto M_Delivery_Planning_Alloc by 5820910 / 5820920 / 5820930;
-- dependency sweep against the live DB (pg_views, pg_proc, AD_Val_Rule.Code, AD_Column.ColumnSQL,
-- EXP_FormatLine by AD_Column_ID) found no other consumer of this column. Applied, and the model
-- classes (I_M_ShipperTransportation, X_M_ShipperTransportation) regenerated, by 00e448d0841.
--
-- AD_Tab 546754 binds to its parent through this column (AD_Tab.Parent_Column_ID = 585609), so the FK
-- constraint parentcolumn_adtab refuses to let AD_Column 585609 be deleted while the tab still
-- references it. The tab is therefore re-bound to its NEW parent link (M_ShipperTransportation_ID,
-- 540426) FIRST, below, before the AD_Field/AD_Column cleanup and the physical column drop.

-- Re-parent AD_Tab 546754 on window 541657 (Lieferanweisungen) directly onto the instruction, instead
-- of onto the (now dropped) M_ShipperTransportation.M_Delivery_Planning_ID column:
--
-- New semantics (owner-approved 2026-08-27): "the delivery plannings on THIS instruction" -- i.e.
-- re-parent on the instruction itself, not on a planning. Two changes, both required together:
--
-- 1) AD_Column_ID is cleared (was 585629, the child view's own "M_Delivery_Planning_ID" column). With
--    AD_Column_ID set, GridTabVO.buildLinkColumnNames() takes its single-column branch verbatim (the
--    field named by that AD_Column_ID) and ignores IsParent entirely -- leaving it set would link the
--    tab on M_Delivery_Planning_ID again (wrong: the header no longer has a single planning to compare
--    against). Clearing it makes buildLinkColumnNames() fall back to every AD_Column.IsParent='Y'
--    field, which now (5820860) includes M_ShipperTransportation_ID (585628 on the view) -- matching
--    the parent's own key column name, verified via
--    GridTabVOBasedDocumentEntityDescriptorFactory.extractChildParentLinkColumnNames()'s
--    childLinkColumnNames.contains(parentLinkColumnName) branch.
-- 2) Parent_Column_ID is set to 540426 -- M_ShipperTransportation.M_ShipperTransportation_ID, the OWN
--    key column of the window's header tab (546732, table 540030) -- per
--    GridTabVOBasedDocumentEntityDescriptorFactory.extractChildParentLinkColumnNames():
--    Parent_Column_ID must resolve (parentTabVO.getColumnNameByAD_Column_ID(...)) to a column
--    belonging to the PARENT tab's own table, not the child view's. The view (AD_Table 542287) exposes
--    a same-named M_ShipperTransportation_ID column (585628), so the two sides join by name.
--
-- WhereClause is cleared (old text named @M_Delivery_Planning_ID@, a context variable the header no
-- longer supplies). No replacement filter is needed: the view's own join
-- (M_Delivery_Planning_Alloc.IsActive='Y', per 5820920) already scopes every row to the instruction's
-- CURRENT active allocations -- exactly "the plannings on this instruction". Superseded/historical
-- allocations are intentionally NOT shown here; that capability is a separate, owner-approved rebuild
-- (plan task E5) sourced from INACTIVE M_Delivery_Planning_Alloc rows, not from this tab.
UPDATE AD_Tab
   SET AD_Column_ID=NULL,
       Parent_Column_ID=540426,
       WhereClause=NULL,
       Updated=TO_TIMESTAMP('2026-08-27 16:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Tab_ID=546754
;

-- Rename + set final PrintName: AD_Tab 546754 and AD_Tab 546737 previously carried IDENTICAL de_DE/
-- de_CH captions ("Lieferanweisungen fuer die Lieferplanung") on two DISTINCT (non-shared) AD_Elements
-- (581962 / 581926 respectively) -- a copy-paste that already caused a wrong analysis. Element 581962
-- is used ONLY by AD_Tab 546754 (verified: no AD_Column/AD_Field/AD_Window/AD_Menu/AD_Process_Para/
-- AD_UI_Element usage), so it is safe to rename outright without forking. AD_Tab itself has no
-- PrintName column (update_Tab_Translation_From_AD_Element only ever syncs Name/Description/Help/
-- CommitWarning), so PrintName is pure AD_Element data hygiene -- no AD_PrintFormatItem references
-- this element (verified: zero rows), but leaving stale text there would be a latent trap for the
-- next reader, so it is set to the same final text as Name/Description here, together.
UPDATE AD_Element
   SET Name='Lieferplanungen der Lieferanweisung', Description='Lieferplanungen der Lieferanweisung',
       PrintName='Lieferplanungen der Lieferanweisung',
       Updated=TO_TIMESTAMP('2026-08-27 16:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581962
;

UPDATE AD_Element_Trl
   SET Name='Lieferplanungen der Lieferanweisung', Description='Lieferplanungen der Lieferanweisung',
       PrintName='Lieferplanungen der Lieferanweisung', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-27 16:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581962 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl
   SET Name='Lieferplanungen der Lieferanweisung', Description='Lieferplanungen der Lieferanweisung',
       PrintName='Lieferplanungen der Lieferanweisung', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-27 16:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581962 AND AD_Language='de_CH'
;

UPDATE AD_Element_Trl
   SET Name='Delivery Plannings for this Instruction', Description='Delivery Plannings for this Instruction',
       PrintName='Delivery Plannings for this Instruction', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-27 16:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581962 AND AD_Language='en_US'
;

-- Propagate the base language first (also mirrors AD_Tab.Name -- update_Tab_Translation_From_AD_Element
-- writes AD_Tab itself when isBaseAD_Language, per metasfresh-application-dictionary), then the overrides.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581962, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581962, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581962, 'en_US');

-- Grid columns for the reactivated Plannings tab (window-designer validation finding): every
-- AD_UI_Element on 546754 defaults to IsDisplayedGrid='N'/SeqNoGrid=0 -- since the tab is
-- section-backed, AD_UI_Element (not AD_Field.IsDisplayedGrid) governs, so without this the tab's
-- grid would show zero columns: rows indistinguishable in the list, only readable by opening each one
-- individually. Column set matches the style of the sibling child tab on the same window (546736
-- "Versandpaket": Product/Locator/Batch/Qty columns wired via AD_UI_Element.SeqNoGrid).
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10, Updated=TO_TIMESTAMP('2026-08-27 16:00:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614861; -- DocumentNo
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20, Updated=TO_TIMESTAMP('2026-08-27 16:00:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614863; -- DocStatus
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30, Updated=TO_TIMESTAMP('2026-08-27 16:00:22','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614868; -- M_Product_ID
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40, Updated=TO_TIMESTAMP('2026-08-27 16:00:23','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614871; -- M_Locator_ID
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50, Updated=TO_TIMESTAMP('2026-08-27 16:00:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614865; -- ETD
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60, Updated=TO_TIMESTAMP('2026-08-27 16:00:25','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614867; -- ETA
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70, Updated=TO_TIMESTAMP('2026-08-27 16:00:26','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614869; -- PlannedLoadedQuantity
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80, Updated=TO_TIMESTAMP('2026-08-27 16:00:27','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614870; -- PlannedDischargeQuantity
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
