-- Repoint M_ShipperTransportation.M_Delivery_Planning_Type to the new AD_Element
-- 585383 (TransportDirection, created by 5820600) and rename the physical column to match.
--
-- Two AD_Field rows carry this column: 783020 (AD_Window 540020 "Transport Auftrag" -> AD_Tab
-- 540096) and 783021 (AD_Window 541657 "Lieferanweisungen" -> AD_Tab 546732). Both currently
-- override their caption via AD_Name_ID=540579 ("Richtung"/"Direction"), added by 5820440 because
-- the column's own element (581679) was misnamed "Lieferplanung Art"/"Type". Element 585383 already
-- carries that same caption (5820600), so both overrides are removed here.
--
-- Dependency sweep before renaming (case-insensitive, live DB, 2026-08-27): the only hit on
-- m_delivery_planning_type for THIS table is AD_Val_Rule 540796
-- (M_ShipperTransportation_DraftDI_ForDirection), whose Code references
-- "M_ShipperTransportation.M_Delivery_Planning_Type" and the parameter placeholder
-- "@M_Delivery_Planning_Type/-@" -- fixed together with the process parameter it pairs with in
-- 5820630 (de.metas.deliveryplanning.base), which owns the process (585654) both belong to. No
-- view, matview, function or AD_Column.ColumnSQL references it.
--
-- AD_Window 540020 IS overridden by two customer windows (Overrides_Window_ID=540020, each in its
-- own customer repository); 541657 is not. Established by an org-wide code search for
-- Overrides_Window_ID, NOT
-- from a local DB, which carries no Overrides_Window_ID rows at all and so always answers "no
-- override". This script still needs no companion of its own: the element repoint and the physical
-- rename sit on AD_Column 593410, which every window over the table shares, and the only per-window
-- rows it touches are the two AD_Name_ID stop-gaps 5820440 put on the core tabs -- the companion
-- scripts in those two repos own the equivalent for their own tabs.
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

-- 3) remove the AD_Field overrides -- the column's element now already says "Richtung"/"Direction"
UPDATE AD_Field SET AD_Name_ID=NULL, Description=NULL, Help=NULL, Name='Richtung', Updated=TO_TIMESTAMP('2026-08-27 11:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID IN (783020, 783021)
;
/* DDL */ select update_fieldtranslation_from_ad_name_element(585383)
;

-- the links followed AD_Name_ID (540579); they must follow the column's element (585383) now, and
-- the helper recreates them from each field's effective element -- so the DELETE has to come first
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (783020, 783021)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783020)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783021)
;
