-- DD_Order / DD_OrderLine.M_ShipmentSchedule_ID: make them valid zoom / related-document targets of M_ShipmentSchedule.
--
-- Why: the columns were created (5804730 / 5804740) with AD_Column.IsExcludeFromZoomTargets='Y'. The generic
-- related-documents view ad_table_related_windows_v filters with
--   WHERE COALESCE(NULLIF(field.IsExcludeFromZoomTargets,''), column.IsExcludeFromZoomTargets) = 'N'
-- The DD_Order header field (780487) has no field-level override, so it falls back to the column flag 'Y' →
-- the Distributionsauftrag window was excluded from the Lieferdisposition (M_ShipmentSchedule) "Related Documents"
-- panel, even though the reconcile DD_Order correctly carries M_ShipmentSchedule_ID. Flip the flag to 'N' so a
-- shipment schedule surfaces its reconcile DD_Order(s) as related documents (matching e.g. M_Picking_Candidate).

UPDATE AD_Column SET IsExcludeFromZoomTargets='N',
    Updated=TO_TIMESTAMP('2026-06-05 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=592625 /*DD_Order.M_ShipmentSchedule_ID*/ AND COALESCE(IsExcludeFromZoomTargets,'Y')<>'N';

UPDATE AD_Column SET IsExcludeFromZoomTargets='N',
    Updated=TO_TIMESTAMP('2026-06-05 12:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=592666 /*DD_OrderLine.M_ShipmentSchedule_ID*/ AND COALESCE(IsExcludeFromZoomTargets,'Y')<>'N';
