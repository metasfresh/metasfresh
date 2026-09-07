-- Drops M_ShipperTransportation.IsSOTrx; TransportDirection carries the direction instead.
--
-- A new writer of this table must set TransportDirection itself: the column is mandatory and
-- carries no default at either layer, so a creation path that omits it fails loudly on NOT NULL
-- instead of silently writing 'Outgoing'.

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

SELECT backup_table('m_shippertransportation', '_drop_IsSOTrx');

/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation', 'ALTER TABLE public.M_ShipperTransportation DROP COLUMN IF EXISTS IsSOTrx')
;
