-- Revert 5814640: unset ViewEditMode on the EPCIS-Exportstatus tab's IsActive AD_UI_Element (652656).
--
-- 5814640 set ViewEditMode='D' hoping to make the IsActive switch inline-editable in the GRID. That is
-- INERT: WebUI enables inline grid editing only for the MAIN record view (mainTable=true, set only in
-- DocumentList.js); an included/detail tab renders with mainTable falsy, so TableRow.js never makes a
-- field inline-editable there regardless of the backend viewEditorRenderMode. Changing a shipment's
-- EPCIS export status is instead done via the new "Change EPCIS Export Status" M_InOut process
-- (AD_Process 585645/585646). Restore the element to its original no-ViewEditMode state so the tab does
-- not carry misleading dead config; the single-row-form IsActive escape-hatch (5813870 + 5814320,
-- IsAlwaysUpdateable='Y') is unaffected.
UPDATE AD_UI_Element
SET    ViewEditMode = NULL,
       Updated = TO_TIMESTAMP('2026-07-21 09:01:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_UI_Element_ID = 652656
  AND  ViewEditMode = 'D'
;
