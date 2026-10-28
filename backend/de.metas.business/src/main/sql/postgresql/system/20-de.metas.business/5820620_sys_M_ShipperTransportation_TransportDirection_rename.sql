-- Repoint M_ShipperTransportation.M_Delivery_Planning_Type to AD_Element 585383
-- (TransportDirection) and rename the physical column to match.
--
-- AD_Field 783020 (AD_Window 540020 "Transport Auftrag" -> AD_Tab 540096) and 783021
-- (AD_Window 541657 "Lieferanweisungen" -> AD_Tab 546732) carry this column; neither has an
-- AD_Name_ID override, so both render whatever the column's element says -- which is why
-- repointing the column is the whole caption fix on this side.
--
-- No new AD row is created here, so no ID-server allocation beyond this script's own
-- AD_MigrationScript 5820620 (idserver.metas.de).

-- 1) repoint AD_Column to the new element and rename its ColumnName
UPDATE AD_Column SET AD_Element_ID=585383, ColumnName='TransportDirection', Updated=TO_TIMESTAMP('2026-08-27 11:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593410
;
/* DDL */ select update_column_translation_from_ad_element(585383)
;

-- 2) rename the physical column
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation', 'ALTER TABLE M_ShipperTransportation RENAME COLUMN M_Delivery_Planning_Type TO TransportDirection')
;

-- 3) restate each field's own base-language caption from the new element, then let the sync function
--    propagate Name/Description/Help into all four AD_Field_Trl rows (de_CH, de_DE, en_US, fr_CH).
--    Help is cleared explicitly because update_fieldtranslation_from_ad_name_element writes only
--    Name and Description onto the AD_Field base row.
--    AD_Name_ID is left alone: blanking it would discard a per-field caption override a customer
--    instance may legitimately hold.
UPDATE AD_Field SET Description=NULL, Help=NULL, Name='Richtung', Updated=TO_TIMESTAMP('2026-08-27 11:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID IN (783020, 783021)
;
/* DDL */ select update_fieldtranslation_from_ad_name_element(585383)
;

-- the helper recreates the links from each field's effective element, so the stale links pointing
-- at the column's previous element have to be deleted first
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (783020, 783021)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783020)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783021)
;
