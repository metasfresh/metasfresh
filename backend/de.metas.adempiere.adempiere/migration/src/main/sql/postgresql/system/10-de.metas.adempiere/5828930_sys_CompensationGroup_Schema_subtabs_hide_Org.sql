-- Window "Kompensationsgruppe Schema" (540415): the organisation is not needed on the sub-tabs; it stays on the
-- header tab. Hide the AD_Org_ID UI element in form and grid of
--   tab 541042 "Kompensationszeilen"   (AD_UI_Element 551003)
--   tab 543968 "Produkte ohne Vertrag" (AD_UI_Element 585391)

UPDATE AD_UI_Element
SET IsDisplayed = 'N', IsDisplayedGrid = 'N', Updated = TO_TIMESTAMP('2026-10-09 17:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID IN (551003, 585391)
;
