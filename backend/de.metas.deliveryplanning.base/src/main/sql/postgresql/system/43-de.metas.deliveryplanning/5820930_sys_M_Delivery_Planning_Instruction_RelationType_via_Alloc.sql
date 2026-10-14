-- Re-point AD_Ref_Table 541708 (target reference of AD_RelationType 540381, "M_Delivery_Planning ->
-- M_Delivery_Instruction" zoom) onto M_Delivery_Planning_Alloc, which is where an instruction's
-- plannings are recorded.
UPDATE AD_Ref_Table
SET WhereClause = 'EXISTS (SELECT 1 FROM M_Delivery_Planning_Alloc dpa WHERE dpa.M_Delivery_Planning_ID = @M_Delivery_Planning_ID / -1@ AND dpa.IsActive=''Y'' AND M_ShipperTransportation.M_ShipperTransportation_ID = dpa.M_ShipperTransportation_ID)',
    Updated = TO_TIMESTAMP('2026-08-27 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Reference_ID = 541708
;

-- Deactivate AD_Field 710779 ("Lieferplanung", tab 546732 "Lieferanweisungen" on window 541657) --
-- the link it displays does not live on M_ShipperTransportation.M_Delivery_Planning_ID.
UPDATE AD_Field
SET IsActive = 'N',
    IsDisplayed = 'N',
    Updated = TO_TIMESTAMP('2026-08-27 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Field_ID = 710779
;

-- ... and the paired AD_UI_Element: the WebUI layout reads AD_UI_Element, not AD_Field, so the two
-- must always be deactivated together.
UPDATE AD_UI_Element
SET IsActive = 'N',
    IsDisplayed = 'N',
    IsDisplayedGrid = 'N',
    Updated = TO_TIMESTAMP('2026-08-27 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 614920
;
