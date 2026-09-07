-- Make the EPCIS-Exportstatus tab's IsActive switch editable in the GRID.
-- (ExternalSystem_ScriptedExportConversion_Status, AD_Tab 549295, on the Lieferung / M_InOut window 169)
--
-- Prior migrations delivered the escape-hatch (deactivate a stuck in-flight status row to release the
-- shipment) only partway for the WebUI grid:
--   * 5813870 added the editable IsActive AD_Field (781730) + AD_UI_Element (652656) and set the tab
--     IsReadOnly='N';
--   * 5814320 set AD_Column.IsAlwaysUpdateable='Y' so the field survives the processed-parent readonly
--     lock (DocumentReadonly) in the SINGLE-ROW form.
-- But the field stays non-editable in the GRID: LayoutFactory#computeViewEditorRenderMode defaults any
-- widget that is not Amount/CostPrice/Quantity to ViewEditorRenderMode.NEVER when the AD_UI_Element has
-- no ViewEditMode set. IsActive is a YesNo Switch, and 5813870 left ViewEditMode empty on 652656, so the
-- grid renders "viewEditorRenderMode":"never" and the switch cannot be toggled inline.
--
-- Fix: set ViewEditMode='D' (ON_DEMAND) on the IsActive element so the switch is editable on demand in
-- the grid; it composes with the existing IsAlwaysUpdateable='Y' so the backend PATCH accepts the toggle
-- on the processed-parent row. 'D' matches the existing grid-editable AD_UI_Element convention.
UPDATE AD_UI_Element
SET    ViewEditMode = 'D',
       Updated = TO_TIMESTAMP('2026-07-20 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_UI_Element_ID = 652656
  AND  COALESCE(ViewEditMode, '') <> 'D'
;
