-- The ETA default sort on the receipt-disposition grid (AD_Field 784930, tab 549491) was set ascending
-- by 5822470_sys_RV_ReceiptDisposition_DeliveryPlanning_default_sort.sql. That puts the OLDEST ETA on
-- top, which is not what the dispatcher wants to see first.
--
-- The sign is the direction: SqlDocumentFieldDataBindingDescriptor.Builder#setDefaultOrderBy(priority)
-- reads `priority >= 0` as ASCENDING and a negative value as DESCENDING, taking the magnitude as the
-- sort priority. So -1 keeps the same priority (first sort column) and flips the direction only.
--
-- 5822470's mechanism note ("positive sign = ascending") was right; its CHOICE of ascending was not, and
-- it is corrected here rather than edited, because that script is already merged onto deep_tundra_release
-- and therefore may already be applied.

UPDATE AD_Field
SET SortNo    = -1,
    Updated   = TO_TIMESTAMP('2026-09-13 18:30:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_Field_ID = 784930 -- ETA field on the RV_ReceiptDisposition_DeliveryPlanning tab (549491)
;
