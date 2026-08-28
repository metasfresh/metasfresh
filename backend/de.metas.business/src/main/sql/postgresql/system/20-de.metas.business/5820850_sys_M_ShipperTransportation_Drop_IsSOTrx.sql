-- Drops M_ShipperTransportation.IsSOTrx, superseded by TransportDirection (5820430 / 5820620).
-- Its single reader -- the one that defaults a transport's dates from its first order -- was reworked
-- to gate on TransportDirection instead, and the four code paths that used to set it no longer do:
-- all four create Outgoing transports only, which is exactly the column's own physical DEFAULT
-- ('Outgoing', see 5820430), so dropping the writes changes nothing observable.
--
-- Dependency sweep against the live DB (pg_views, pg_proc, AD_Val_Rule.Code, AD_Column.ColumnSQL,
-- EXP_FormatLine by AD_Column_ID, AD_Tab.Parent_Column_ID -- all keyed on AD_Column_ID=590639) found no
-- consumer besides two AD_Field rows, on windows 540020 "Transport Auftrag" and 541657
-- "Lieferanweisungen", cleaned up below.
--
-- That sweep ran against a DB carrying no Overrides_Window_ID rows at all, so it could not see
-- customer override windows and its "only two AD_Field rows" answer is a floor, not a ceiling. A
-- targeted code search for Overrides_Window_ID=540020 returns two customer override windows; for
-- 541657 it returns none, which is likewise a floor -- neither a local DB nor a code search can
-- prove an override absent, only a customer-faithful DB can. It makes no difference to the cleanup:
-- whatever AD_Field rows any override window holds for this column are covered regardless, because
-- every DELETE below is anchored on the column, not on the two known AD_Field_IDs -- which is also
-- why no companion script is needed here.

-- FK-chain cleanup, anchored on AD_Column_ID=590639 (M_ShipperTransportation.IsSOTrx).
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590639)
;
DELETE FROM AD_UI_Element WHERE Labels_Selector_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590639)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590639)
;
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590639)
;
DELETE FROM AD_Field_ContextMenu WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590639)
;
DELETE FROM AD_UI_ElementField WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590639)
;
DELETE FROM AD_UserDef_Field WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590639)
;
DELETE FROM AD_User_SortPref_Line WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590639)
;
DELETE FROM AD_Field WHERE AD_Column_ID = 590639
;

DELETE FROM AD_Column_Trl WHERE AD_Column_ID = 590639
;
DELETE FROM AD_Column WHERE AD_Column_ID = 590639
;

-- Backup before the physical DROP COLUMN (cheap defensive backup per metasfresh-db skill).
SELECT backup_table('m_shippertransportation', '_drop_IsSOTrx');

-- db_alter_table's second argument is the COMPLETE statement, not a fragment -- it drops the dependent
-- views, executes the DDL verbatim, then recreates them (none exist for this column, per the sweep above).
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation', 'ALTER TABLE public.M_ShipperTransportation DROP COLUMN IF EXISTS IsSOTrx')
;
