-- Repoint M_ShipperTransportation.M_Delivery_Planning_Type to the new AD_Element
-- 585383 (TransportDirection, created by 5820600) and rename the physical column to match.
--
-- Two AD_Field rows carry this column: 783020 (AD_Window 540020 "Transport Auftrag" -> AD_Tab
-- 540096) and 783021 (AD_Window 541657 "Lieferanweisungen" -> AD_Tab 546732). Neither carries an
-- AD_Name_ID override, so both render whatever the column's element says -- until now 581679
-- "Lieferplanung Art"/"Type", the name of the delivery-planning record type rather than of the
-- direction the column holds. Repointing the column to 585383 ("Richtung"/"Direction") is therefore
-- the whole caption fix on this side, exactly as 5820610 is on the Delivery Planning side.
--
-- Dependency sweep before renaming (case-insensitive, live DB, 2026-08-27): the only hit on
-- m_delivery_planning_type for THIS table is AD_Val_Rule 540796
-- (M_ShipperTransportation_DraftDI_ForDirection), whose Code references
-- "M_ShipperTransportation.M_Delivery_Planning_Type" and the parameter placeholder
-- "@M_Delivery_Planning_Type/-@" -- fixed together with the process parameter it pairs with in
-- 5820630 (de.metas.deliveryplanning.base), which owns the process (585654) both belong to. No
-- view, matview, function or AD_Column.ColumnSQL references it.
--
-- AD_Window 540020 IS overridden: a targeted code search for Overrides_Window_ID=540020 returns two
-- customer override windows, each in its own repository. No override was found for 541657, but that
-- is a floor, not a ceiling -- the search sees checked-in SQL only, truncates silently, and a
-- custom window may predate the Overrides_Window_ID column; only a faithful customer DB settles it.
-- This script still needs no companion of its own: the element repoint and the physical rename sit
-- on AD_Column 593410, which every window over the table shares, and the only per-window rows it
-- touches are the two core-tab AD_Field rows 5820440 created -- the companion scripts in those
-- repositories own the equivalent for their own tabs.
--
-- No new AD row is created here, so no ID-server allocation beyond this script's own
-- AD_MigrationScript 5820620 (idserver.metas.de, 2026-08-27).

-- 1) repoint AD_Column to the new element and rename its ColumnName
UPDATE AD_Column SET AD_Element_ID=585383, ColumnName='TransportDirection', Updated=TO_TIMESTAMP('2026-08-27 11:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593410
;
/* DDL */ select update_column_translation_from_ad_element(585383)
;

-- 2) rename the physical column (db_alter_table drops/recreates dependent views around the ALTER)
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation', 'ALTER TABLE M_ShipperTransportation RENAME COLUMN M_Delivery_Planning_Type TO TransportDirection')
;

-- 3) restate each field's own base-language caption from the new element, then let the sync function
--    propagate Name/Description/Help into all four AD_Field_Trl rows (de_CH, de_DE, en_US, fr_CH).
--    Help is cleared explicitly because update_fieldtranslation_from_ad_name_element writes only
--    Name and Description onto the AD_Field base row (it does write Help into the _Trl rows).
--    AD_Name_ID is deliberately NOT written: it is already NULL on both fields, and blanking it
--    would silently discard a per-field caption override a customer instance may legitimately hold.
UPDATE AD_Field SET Description=NULL, Help=NULL, Name='Richtung', Updated=TO_TIMESTAMP('2026-08-27 11:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID IN (783020, 783021)
;
/* DDL */ select update_fieldtranslation_from_ad_name_element(585383)
;

-- the links followed the column's OLD element (581679); they must follow 585383 now, and the helper
-- recreates them from each field's effective element -- so the DELETE has to come first
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (783020, 783021)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783020)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783021)
;
