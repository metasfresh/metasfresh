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

DELETE FROM AD_Column_Trl WHERE AD_Column_ID = 585609
;
DELETE FROM AD_Column WHERE AD_Column_ID = 585609
;

-- Backup before the physical DROP COLUMN, even though the current data is already migrated
-- to M_Delivery_Planning_Alloc by 5820530 (cheap defensive backup per metasfresh-db skill).
SELECT backup_table('m_shippertransportation', '_drop_M_Delivery_Planning_ID');

SELECT db_alter_table('M_ShipperTransportation', 'DROP COLUMN IF EXISTS M_Delivery_Planning_ID')
;
