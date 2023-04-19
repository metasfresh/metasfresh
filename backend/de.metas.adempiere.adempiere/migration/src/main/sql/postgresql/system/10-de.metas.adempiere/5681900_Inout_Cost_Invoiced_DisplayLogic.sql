-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Invoiced
-- Column: M_InOut_Cost.IsInvoiced
-- 2023-03-16T14:47:41.729Z
UPDATE AD_Field SET DisplayLogic='@IsSOTrx@=''N''',Updated=TO_TIMESTAMP('2023-03-16 16:47:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712605
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Cost Amount Invoiced
-- Column: M_InOut_Cost.CostAmountInvoiced
-- 2023-03-16T14:48:05.515Z
UPDATE AD_Field SET DisplayLogic='@IsSOTrx@=''N''',Updated=TO_TIMESTAMP('2023-03-16 16:48:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712604
;

