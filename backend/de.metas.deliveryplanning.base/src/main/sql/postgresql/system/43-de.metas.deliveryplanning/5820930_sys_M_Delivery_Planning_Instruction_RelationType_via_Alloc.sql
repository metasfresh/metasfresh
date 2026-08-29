-- Re-point AD_Ref_Table 541708 (target reference of AD_RelationType 540381, "M_Delivery_Planning ->
-- M_Delivery_Instruction" zoom) off M_ShipperTransportation.M_Delivery_Planning_ID (being dropped) and
-- onto M_Delivery_Planning_Alloc. The @M_Delivery_Planning_ID / -1@ context-variable form is kept intact.
UPDATE AD_Ref_Table
SET WhereClause = 'EXISTS (SELECT 1 FROM M_Delivery_Planning_Alloc dpa WHERE dpa.M_Delivery_Planning_ID = @M_Delivery_Planning_ID / -1@ AND dpa.IsActive=''Y'' AND M_ShipperTransportation.M_ShipperTransportation_ID = dpa.M_ShipperTransportation_ID)',
    Updated = TO_TIMESTAMP('2026-08-27 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Reference_ID = 541708
;

-- Deactivate AD_Field 710779 ("Lieferplanung", tab 546732 "Lieferanweisungen" on window 541657) --
-- its backing column M_ShipperTransportation.M_Delivery_Planning_ID is going away.
--
-- These two UPDATEs are ALWAYS superseded within the same branch: 5820940 ships alongside this
-- script and DELETEs exactly these rows (its FK-chain cleanup is anchored on AD_Column 585609, so
-- it takes AD_Field 710779 and AD_UI_Element 614920 with it). They are kept only so that this
-- script leaves the window in a coherent state on its own, for the window of one script between
-- the two -- an earlier draft of this header claimed the column drop was "a separate, deliberately
-- not-yet-applied script", which is no longer true and must not be read as a reason to skip 5820940.
UPDATE AD_Field
SET IsActive = 'N',
    IsDisplayed = 'N',
    Updated = TO_TIMESTAMP('2026-08-27 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Field_ID = 710779
;

-- Deactivate the paired AD_UI_Element -- WebUI layout reads AD_UI_Element, not AD_Field, so this
-- must move together with the AD_Field deactivation above.
UPDATE AD_UI_Element
SET IsActive = 'N',
    IsDisplayed = 'N',
    IsDisplayedGrid = 'N',
    Updated = TO_TIMESTAMP('2026-08-27 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 614920
;
