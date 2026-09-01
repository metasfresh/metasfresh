-- M_Delivery_Planning: move ShipToLocation_Name out of the grid into advanced edit on
-- AD_Tab 546674 (AD_Window 541632).
--
-- ShipToLocation_Name (AD_Column 585014) carries no information the grid does not already show.
-- It is a virtual column projecting C_BPartner_Location.Name of the stored FK
-- M_Delivery_Planning.C_BPartner_Location_ID; the neighbouring ShipTo_Location_ID (AD_Column
-- 593415) is a reference-159 lookup on C_BPartner_Location whose AD_Display is that same Name.
-- The stored FK is written once at planning generation from exactly the source ShipTo_Location_ID
-- reads live -- M_ShipmentSchedule.C_BPartner_Location_ID for Outgoing, M_Warehouse.
-- C_BPartner_Location_ID for Incoming and Dropship -- and nothing rewrites it afterwards. On all
-- 1392 active plannings, across all three transport directions, the two resolve to the identical
-- location id AND render the identical text: 1392 of 1392 equal, zero divergences. The older
-- column is the weaker of the two, because the reference lookup also zooms to the location record
-- and the plain string does not.
--
-- Both also carry the caption 'Lieferadresse' in de_DE (AD_Element 581681 and 585386), so the
-- planner currently sees two adjacent grid columns under the same header, at SeqNoGrid 110 and
-- 114, holding byte-identical text. The grid header comes from the AD_Field caption, not from
-- AD_UI_Element.Name, so this element's cosmetic Name 'Ship-to location' does not prevent it.
--
-- Neither element is renamed: 'Lieferadresse' is the established term for the delivery address and
-- is what the delivery instruction calls its C_BPartner_Location_Delivery_ID, the column this
-- field's value propagates to. Taking the redundant column out of the grid removes the duplicate
-- header and the duplicate column in one step, and is reversible.
--
-- IsAdvancedField='Y' with IsDisplayed='Y' keeps the field reachable in advanced edit (Alt+E),
-- which is the only place the snapshot-vs-live distinction could ever become visible. The element
-- stays in the existing 'default' element group 550028: IsAdvancedField is a per-element flag that
-- the WebUI applies when serialising the layout (the advanced request returns every element, the
-- normal one drops the advanced ones), so no dedicated advanced-edit container is needed, and the
-- group already sits in an active AD_UI_Section -> AD_UI_Column -> AD_UI_ElementGroup chain.
-- Keeping it there also keeps AD_Org_ID and AD_Client_ID (element group 550031, last group of the
-- right UI column) the last two fields of advanced edit; a trailing advanced-edit section would
-- have been rendered after them.
--
-- Nothing else changes: no AD_Column, no AD_Element, no AD_Field, no translation, no Java. The
-- column stays selectable via the AD_Field and readable through the API.
--
-- IDs allocated from idserver.metas.de on 2026-09-01:
--   AD_MigrationScript 5821480 (this file)

-- ============================================================================
-- AD_UI_Element 613490 (AD_Tab 546674, AD_Field 708085, AD_Column 585014)
-- ============================================================================
UPDATE AD_UI_Element
   SET IsDisplayedGrid='N',
       SeqNoGrid=0,
       IsAdvancedField='Y',
       Updated=TO_TIMESTAMP('2026-09-01 11:30:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
 WHERE AD_UI_Element_ID=613490
;
