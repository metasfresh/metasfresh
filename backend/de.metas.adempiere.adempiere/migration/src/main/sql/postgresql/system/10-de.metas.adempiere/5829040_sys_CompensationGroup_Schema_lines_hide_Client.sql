-- Window "Kompensationsgruppe Schema" (540415), tab "Kompensationszeilen" (541042): the client is not needed on the
-- sub-tab (like the organisation, hidden there by 5828930); it stays on the header tab.
-- Hide the AD_Client_ID UI element 551004 in form and grid. (Tab 543968 has no client UI element.)

UPDATE AD_UI_Element
SET IsDisplayed = 'N', IsDisplayedGrid = 'N', Updated = TO_TIMESTAMP('2026-10-09 19:20:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 551004
;
