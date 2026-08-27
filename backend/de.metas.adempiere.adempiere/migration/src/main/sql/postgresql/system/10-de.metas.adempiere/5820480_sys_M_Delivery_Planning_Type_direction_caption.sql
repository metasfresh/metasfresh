-- Label the transport direction "Richtung" / "Direction" on the Delivery Planning window
-- too, so ONE column no longer carries TWO names across the three windows a planner works in.
--
-- AD_Field 708076 (AD_Window 541632 "Lieferplanung" -> AD_Tab 546674, over AD_Column 585005
-- M_Delivery_Planning.M_Delivery_Planning_Type) has AD_Name_ID NULL, so it inherits the label of
-- the column's shared AD_Element 581679 and renders "Lieferplanung Art" (de_DE / de_CH) / "Type"
-- (en_US / fr_CH). The very same three-valued direction is labelled via AD_Element 540579
-- ("Richtung" / "Direction") on both windows over M_ShipperTransportation.M_Delivery_Planning_Type
-- since 5820440 - AD_Window 540020 (AD_Field 783020) and AD_Window 541657 (AD_Field 783021).
--
-- Why it matters beyond consistency: the Combine-into-ONE-delivery-instruction rejection added by
-- 5820450 names every field a selection disagrees on, and names this one via AD_Message 545800
-- ("Richtung" / "Direction"). Combine is launched from AD_Window 541632, so a planner who selected
-- two plannings differing only in direction was told they differ in a word that appears nowhere on
-- the screen in front of them - that screen said "Lieferplanung Art". Unifying the caption is what
-- makes the message correct on every window it can be raised from; rewording the message instead
-- would have taught it the second name rather than removing it.
--
-- Element 581679's own German ("Lieferplanung Art") names the *delivery planning* record type and
-- is what makes it wrong wherever the column describes transport direction - which is why 5820440
-- already forked to 540579 rather than reusing it.
--
-- Fork via AD_Field.AD_Name_ID, never a mutation of the element: AD_Element 581679 is SHARED - it
-- is the element of AD_Column 585005 (M_Delivery_Planning.M_Delivery_Planning_Type) AND of
-- AD_Column 593410 (M_ShipperTransportation.M_Delivery_Planning_Type), and one AD_Process_Para
-- reads it as well. Renaming it would reach all of them. The per-field override is the same lever
-- 5820440 used, and AD_Field 708076 is the only AD_Field over column 585005, so this one row is the
-- whole caption on the delivery-planning side.
--
-- Element 540579 is a fully translated core element: de_DE / de_CH "Richtung" and en_GB / en_US
-- "Direction" carry IsTranslated='Y'; fr_CH holds "Direction" with IsTranslated='N', which is
-- exactly what AD_Fields 783020 / 783021 render today, so all three windows stay in step per
-- language. AD_Field_Trl 708076 has rows for de_CH, de_DE, en_US and fr_CH - the four active system
-- languages - and update_FieldTranslation_From_AD_Name_Element refreshes all of them plus the
-- base-language AD_Field.Name from the element.
--
-- Additive and position-preserving: no AD_Field and no AD_UI_Element is created, deleted,
-- renumbered or repositioned. AD_Field 708076 keeps its tab, and AD_UI_Element 613482 keeps its
-- element group (550029), its SeqNo (50) and its SeqNoGrid (30), so a later change that adds a tab
-- to AD_Window 541632 cannot collide with this one.
--
-- AD_Window 541632 has no override window (no AD_Window row with Overrides_Window_ID=541632), so
-- there is no companion customer-repo script.
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_MigrationScript 5820480 (this file). No new AD row is created, so no other ID is needed:
--   AD_Element 540579 and AD_Field 708076 both already exist, and the AD_Element_Link row is
--   (re)created by AD_Element_Link_Create_Missing_Field, which allocates its own id.

-- ============================================================================
-- AD_Window 541632 / AD_Tab 546674 "Lieferplanung" - AD_Field 708076
-- ============================================================================
-- Column: M_Delivery_Planning.M_Delivery_Planning_Type
UPDATE AD_Field SET AD_Name_ID=540579, Description=NULL, Help=NULL, Name='Richtung',Updated=TO_TIMESTAMP('2026-08-27 01:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708076
;

/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540579)
;

-- the link followed the column's element 581679; it has to follow AD_Name_ID now, and the helper
-- recreates it from the field's effective element - so the DELETE has to come first
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708076
;

/* DDL */  select AD_Element_Link_Create_Missing_Field(708076)
;
