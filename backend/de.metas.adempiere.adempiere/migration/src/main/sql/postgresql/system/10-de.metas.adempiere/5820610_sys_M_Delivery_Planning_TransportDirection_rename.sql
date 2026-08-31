-- Repoint M_Delivery_Planning.M_Delivery_Planning_Type to AD_Element 585383 (TransportDirection)
-- and rename the physical column to match.
--
-- This is also the whole caption fix on the Delivery Planning window: AD_Field 708076 (AD_Tab
-- 546674, AD_Window 541632) has AD_Name_ID NULL, so it renders whatever its column's element says
-- and reads "Richtung"/"Direction" rather than "Lieferplanung Art" -- the same wording AD_Message
-- 545800 uses when the combine-into-ONE-delivery-instruction check reports a direction mismatch, so
-- the rejection names a caption the planner can actually see on that screen.
--
-- AD_Name_ID is not written here: it is already NULL on this field, and blanking it would discard a
-- per-field caption override a customer instance may legitimately hold.
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_MigrationScript 5820610 (this file)

-- 1) repoint AD_Column to the new element and rename its ColumnName
UPDATE AD_Column SET AD_Element_ID=585383, ColumnName='TransportDirection', Updated=TO_TIMESTAMP('2026-08-27 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=585005
;
/* DDL */ select update_column_translation_from_ad_element(585383)
;

-- 2) rename the physical column
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning', 'ALTER TABLE M_Delivery_Planning RENAME COLUMN M_Delivery_Planning_Type TO TransportDirection')
;

-- 3) restate the field's own base-language caption from the new element, then let the sync function
--    propagate Name/Description/Help into all four AD_Field_Trl rows.
UPDATE AD_Field SET Description=NULL, Help=NULL, Name='Richtung', Updated=TO_TIMESTAMP('2026-08-27 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=708076
;
/* DDL */ select update_fieldtranslation_from_ad_name_element(585383)
;

-- the link still follows the column's OLD element, and the helper recreates it from the field's
-- effective element -- so the DELETE has to come first
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708076
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(708076)
;
