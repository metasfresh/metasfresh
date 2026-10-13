-- Boni refund engine: category-based refund configs have no product, so their lookup label (M_Product_ID, RefundPercent,
-- C_InvoiceSchedule_ID) could be identical. Add the product category to the record's display string (after the product).
UPDATE AD_Column
SET IsIdentifier = 'Y', SeqNo = 15, Updated = TO_TIMESTAMP('2026-10-05 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593707
;
