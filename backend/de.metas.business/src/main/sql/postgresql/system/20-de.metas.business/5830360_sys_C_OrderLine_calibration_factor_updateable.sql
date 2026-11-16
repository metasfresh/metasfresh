-- The order line calibration factor (C_OrderLine.GroupCompensationCalibrationFactor) becomes editable on a calibrated line, in the advanced edit of window 143 (and in any window that shows it),
-- kept in sync with QtyEntered by the callout de.metas.order.compensationGroup.callout.C_OrderLine.
-- 1. The column is updateable, so that the field's ReadOnlyLogic is honoured (a not updateable column makes the WebUI ignore it). IsAlwaysUpdateable stays 'N': a completed order stays locked.
--    Side effect (accepted by the product owner): REST, imports and processes can now change the factor too; the callout runs on window level only.
-- 2. The factor field is read-only unless the line is calibrated, i.e. has a calibration rule or a stored uncalibrated qty. It stays in the advanced edit, not in the grid.

-- Column: C_OrderLine.GroupCompensationCalibrationFactor
UPDATE AD_Column SET IsUpdateable = 'Y', Updated = TO_TIMESTAMP('2026-10-20 19:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Column_ID = 593730
;

-- Field: Auftrag(143,D) -> Auftragsposition(187,D) -> Kalibrierfaktor
UPDATE AD_Field SET IsReadOnly = 'N', ReadOnlyLogic = '@C_CompensationGroup_CalibrationRule_ID/0@=0 & @GroupCompensationQtyEnteredUncalibrated/0@=0', Updated = TO_TIMESTAMP('2026-10-20 19:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Field_ID = 785624
;
