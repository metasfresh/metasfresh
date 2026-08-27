-- me03 #31608 -- repoint M_Delivery_Planning.M_Delivery_Planning_Type to the new AD_Element
-- 585383 (TransportDirection, created by 5820600) and rename the physical column to match.
--
-- AD_Field 708076 (AD_Window 541632 "Lieferplanung" -> AD_Tab 546674) currently overrides its
-- caption via AD_Name_ID=540579 ("Richtung"/"Direction") because the column's own element (581679)
-- was misnamed "Lieferplanung Art"/"Type". Element 585383 already carries that same caption
-- (5820600), so the override is no longer needed and is removed here -- the element finally says
-- what it means.
--
-- Dependency sweep before renaming (case-insensitive, live DB, 2026-08-27): no view, matview,
-- function, AD_Val_Rule.Code or AD_Column.ColumnSQL references m_delivery_planning_type on
-- M_Delivery_Planning (the one hit, AD_Val_Rule 540796, is scoped to M_ShipperTransportation and
-- is handled in 5820620). No AD_Window overrides AD_Window 541632, so no companion customer-repo
-- script is needed.
--
-- No new AD row is created here, so no ID-server allocation beyond this script's own
-- AD_MigrationScript 5820610 (idserver.metas.de, 2026-08-27).

-- 1) repoint AD_Column to the new element and rename its ColumnName
UPDATE AD_Column SET AD_Element_ID=585383, ColumnName='TransportDirection', Updated=TO_TIMESTAMP('2026-08-27 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=585005
;
/* DDL */ select update_column_translation_from_ad_element(585383)
;

-- 2) rename the physical column (db_alter_table drops/recreates dependent views around the ALTER)
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning', 'ALTER TABLE M_Delivery_Planning RENAME COLUMN M_Delivery_Planning_Type TO TransportDirection')
;

-- 3) remove the AD_Field override -- the column's element now already says "Richtung"/"Direction"
UPDATE AD_Field SET AD_Name_ID=NULL, Description=NULL, Help=NULL, Name='Richtung', Updated=TO_TIMESTAMP('2026-08-27 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=708076
;
/* DDL */ select update_fieldtranslation_from_ad_name_element(585383)
;

-- the link followed AD_Name_ID (540579); it must follow the column's element (585383) now, and the
-- helper recreates it from the field's effective element -- so the DELETE has to come first
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708076
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(708076)
;
