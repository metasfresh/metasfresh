-- Repoint M_Delivery_Planning.M_Delivery_Planning_Type to the new AD_Element
-- 585383 (TransportDirection, created by 5820600) and rename the physical column to match.
--
-- This is also what fixes the caption on the Delivery Planning window. AD_Field 708076 (AD_Window
-- 541632 "Lieferplanung" -> AD_Tab 546674) has AD_Name_ID NULL, so it renders whatever its column's
-- element says -- until now 581679 "Lieferplanung Art"/"Type", which names the *delivery planning
-- record type*, not the transport direction the column holds. Repointing the column to 585383
-- ("Richtung"/"Direction") is therefore the whole caption fix on this side: no per-field AD_Name_ID
-- override is needed or added, and 708076 ends up rendering exactly what AD_Fields 783020 / 783021
-- render on the two M_ShipperTransportation windows. ONE column, ONE name, all three windows.
--
-- Why it matters beyond consistency: the Combine-into-ONE-delivery-instruction rejection added by
-- 5820450 names every field a selection disagrees on, and names this one via AD_Message 545800
-- ("Richtung" / "Direction"). Combine is launched from AD_Window 541632, so before this script a
-- planner who selected two plannings differing only in direction was told they differ in a word
-- that appeared nowhere on the screen in front of them - that screen said "Lieferplanung Art".
--
-- The forked element (5820600) rather than a rename of 581679 in place: 581679 is SHARED - besides
-- AD_Column 585005 it also backs AD_Column 593410 (M_ShipperTransportation) and one
-- AD_Process_Para. 5820600 explains the fork; this script only consumes it.
--
-- Dependency sweep before renaming (case-insensitive, live DB, 2026-08-27): no view, matview,
-- function, AD_Val_Rule.Code or AD_Column.ColumnSQL references m_delivery_planning_type on
-- M_Delivery_Planning (the one hit, AD_Val_Rule 540796, is scoped to M_ShipperTransportation and
-- is handled in 5820620). No override window was found for AD_Window 541632, so no companion
-- customer-repo script is added here -- a floor, not a ceiling: neither a local DB (which holds no
-- Overrides_Window_ID rows at all) nor a code search can prove an override absent; only a
-- customer-faithful DB can.
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

-- 3) restate the field's own base-language caption from the new element, then let the sync function
--    propagate Name/Description/Help into all four AD_Field_Trl rows (de_CH, de_DE, en_US, fr_CH).
--    AD_Name_ID is deliberately NOT written here: it is already NULL on this field, and blanking it
--    would silently discard a per-field caption override a customer instance may legitimately hold.
UPDATE AD_Field SET Description=NULL, Help=NULL, Name='Richtung', Updated=TO_TIMESTAMP('2026-08-27 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=708076
;
/* DDL */ select update_fieldtranslation_from_ad_name_element(585383)
;

-- the link followed the column's OLD element (581679); it must follow 585383 now, and the helper
-- recreates it from the field's effective element -- so the DELETE has to come first
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708076
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(708076)
;
