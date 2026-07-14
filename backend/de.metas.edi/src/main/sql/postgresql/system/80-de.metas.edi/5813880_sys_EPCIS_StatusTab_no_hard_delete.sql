-- Prevent HARD-DELETE of ExternalSystem_ScriptedExportConversion_Status rows.
--
-- WHY: migration 5813870 made the EPCIS-Exportstatus tab (AD_Tab 549295) editable so support can
-- DEACTIVATE a stuck in-flight status row (IsActive='N') to release a shipment the reverse guard is
-- blocking. But this table was created (5806850) with IsDeleteable='Y', and a tab whose
-- IsReadOnly='N' exposes a row-DELETE action (WebUI: allowDelete AND NOT readonlyLogic). A hard
-- delete is unsafe here: if the shipment's EPCIS export was actually dispatched to the receiver and
-- the /ok callback merely late, deleting the status row means the callback finds no row, the
-- transmission ledger is never written, and the reverse guard no longer blocks — re-completion would
-- re-transmit the same physical SSCC (the duplicate-transmission bug this whole change prevents).
--
-- The sanctioned release action is DEACTIVATE (IsActive='N', which the guard's active-only lookup
-- honours), never delete. Set IsDeleteable='N' so the tab exposes only deactivate/reactivate —
-- matching the sibling ledger table EDI_EPCIS_Transmitted_SSCC (also IsDeleteable='N').

UPDATE AD_Table
SET IsDeleteable = 'N',
    Updated = TO_TIMESTAMP('2026-07-14 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542617;
