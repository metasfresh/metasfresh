-- The grid had no defined sort order (neither AD_Tab.AD_ColumnSortOrder_ID nor any AD_Field.SortNo was
-- set), so the WebUI fell back to sorting by the key column. Because an unplanned row keys at
-- 1,000,000,000 + M_ReceiptSchedule_ID (see RV_ReceiptDisposition_DeliveryPlanning.sql), that fallback buried every
-- unplanned row at the very bottom of the grid -- exactly the rows a dispatcher most needs to chase,
-- since nobody has planned them yet.
--
-- Fix: sort by ETA ascending (AD_Field.SortNo=1 -- positive sign = ascending, magnitude = priority).
-- ETA is a COALESCE across both branches and is never null in the data behind this view, so it never
-- reintroduces a "buried at the bottom" group. Chosen over the M_Delivery_Planning window's own
-- default (there is none configured on AD_Tab 546674/546737 either, so there is no house default to
-- inherit here) and over the generic "documents sort by date descending" convention (C_Order,
-- M_InOut, ...): those dates record when a document was ENTERED, so newest-first surfaces recent
-- activity. ETA is a forward-looking promise of when goods arrive, the same kind of value as
-- DatePromised on PP_Order_Candidate (AD_Field 5+, SortNo=20) and M_Forecast_ProductQty_V
-- (SortNo=1) -- both sort such promise dates ascending, soonest first, which is what a dispatcher
-- triaging arrivals needs: what is due soonest belongs on top, not what was most recently touched.
UPDATE AD_Field
SET SortNo   = 1,
    Updated   = TO_TIMESTAMP('2026-09-03 09:00:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_Field_ID = 784930 -- ETA field on the RV_ReceiptDisposition_DeliveryPlanning tab (549491)
;
