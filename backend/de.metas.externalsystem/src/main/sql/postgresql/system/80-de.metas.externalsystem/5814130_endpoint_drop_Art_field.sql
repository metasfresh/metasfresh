-- #############################################################################
-- Migration: remove the redundant "Art" (Type) field from the endpoint window
-- (AD_Window 541967). "Transportart" (TransportType) is the single transport field;
-- the legacy "Art" (ExternalSystem_Endpoint.Type, column 591477, element 600) is
-- dead weight in the UI.
--
-- Dependency check (recorded per the task): ExternalSystem_Endpoint.Type has NO
-- runtime/production reader — the only references are a cucumber step-def that sets
-- it via the model (setType(TYPE_HTTP)) and the generated model accessors. It is not
-- mandatory (IsMandatory='N'). We therefore remove only the UI placement (deactivate
-- the AD_Field + AD_UI_Element on 541967); the physical column and generated model
-- are LEFT intact so the cucumber step-def still compiles and no model regen is needed.
-- #############################################################################

-- Remove the "Art" UI element from the endpoint window form.
UPDATE AD_UI_Element
SET IsActive = 'N',
    Updated = TO_TIMESTAMP('2026-07-16 09:30:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638544;

-- Deactivate the underlying AD_Field.
UPDATE AD_Field
SET IsActive = 'N',
    Updated = TO_TIMESTAMP('2026-07-16 09:30:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 755939;
