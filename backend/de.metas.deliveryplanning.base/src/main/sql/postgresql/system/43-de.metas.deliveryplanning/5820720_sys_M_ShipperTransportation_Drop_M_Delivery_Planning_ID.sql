-- NOT APPLIED YET on purpose (B6 task split): the Java readers/writers of
-- M_ShipperTransportation.M_Delivery_Planning_ID are being reworked in a parallel change; this
-- script must stay unapplied until that lands, then be applied and the model classes (I_M_ShipperTransportation,
-- X_M_ShipperTransportation) regenerated. Every SQL/AD consumer was already re-pointed onto
-- M_Delivery_Planning_Alloc by 5820690 / 5820700 / 5820710; dependency sweep against the live DB
-- (pg_views, pg_proc, AD_Val_Rule.Code, AD_Column.ColumnSQL, EXP_FormatLine by AD_Column_ID) found no
-- other consumer of this column.

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

-- AD_Tab 546754 "Lieferanweisungen fuer die Lieferplanung" binds to its parent through THIS column
-- (AD_Tab.Parent_Column_ID = 585609), so FK constraint parentcolumn_adtab refuses the DROP while it stands.
-- That tab asked "which earlier delivery instructions covered this instruction's planning?" - a question the
-- header can no longer answer, because after aggregation an instruction has N plannings and no single
-- M_Delivery_Planning_ID. So the tab is retired here rather than re-pointed.
--
-- Its replacement is NOT a re-pointed where clause: the re-booking trail is rebuilt on the INACTIVE
-- M_Delivery_Planning_Alloc rows (a real supersession link) instead of this tab's "Created < @Created@"
-- timestamp proxy, scoped per planning under the Plannings tab. Owner-approved 2026-08-27.
--
-- Parent_Column_ID must be NULLed, not just IsActive='N' - the FK fires regardless of IsActive.
-- DO NOT simply reactivate this tab: with Parent_Column_ID NULL the WebUI silently falls back to the
-- parent's key column (GridTabVOBasedDocumentEntityDescriptorFactory:1018), which would match rows on
-- M_ShipperTransportation_ID and display the wrong instructions with no error anywhere.
UPDATE AD_Tab SET IsActive='N', Parent_Column_ID=NULL, Updated=now(), UpdatedBy=100 WHERE AD_Tab_ID=546754
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
