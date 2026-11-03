-- Make the transport order's B/L Date and ETA editable once the document is processed.
--
-- Note this covers every Processed-derived state, not only Completed: the read-only gate keys on
-- Processed, which Closed keeps set and Void also sets. Editing either date on a VOIDED transport
-- order therefore saves but propagates nothing, because the BL/ETA change handler only forwards to
-- the linked orders when the document is completed or closed. That is the same characteristic the
-- already-always-updateable sibling fields have had since they were introduced.
--
-- A completed M_ShipperTransportation is Processed='Y', and the WebUI renders every field of a
-- processed document read-only unless its AD_Column.IsAlwaysUpdateable='Y'. Both dates were left
-- at 'N', so they could not be entered or corrected once the transport order was completed -- even
-- though the bill of lading is issued by the carrier only AFTER loading, i.e. normally after the
-- transport order is done. The user's only recourse was to reactivate and re-complete the
-- document, which resets M_ShippingPackage.Processed and M_Package.ShipDate on every linked
-- package.
--
-- This also made the completed/closed branch of the transport order's BL/ETA change handler
-- unreachable from the WebUI: it recomputes the linked purchase orders' pay-schedule due dates
-- when either date is edited on an already-completed transport order, which the UI never allowed.
--
-- Every other logistics field on this window is already always-updateable -- among them
-- M_ShipperTransportation.ATA, M_ShipperTransportation.ETD and M_ShipperTransportation.IsBLReceived
-- ("B/L erhalten"), the companion flag of the B/L date -- so these two were the outliers.
--
-- Window 540020 "Transport Auftrag", tab 540096 "Speditionslieferung". Both fields already carry
-- AD_Field.IsReadOnly='N' with no read-only logic, so the column flag is the only thing gating them.

-- Column: M_ShipperTransportation.ETA
UPDATE AD_Column
SET IsAlwaysUpdateable='Y',
    Updated=TO_TIMESTAMP('2026-07-30 16:20:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Column_ID=591246;

-- Column: M_ShipperTransportation.BLDate
UPDATE AD_Column
SET IsAlwaysUpdateable='Y',
    Updated=TO_TIMESTAMP('2026-07-30 16:20:01','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Column_ID=591254;
