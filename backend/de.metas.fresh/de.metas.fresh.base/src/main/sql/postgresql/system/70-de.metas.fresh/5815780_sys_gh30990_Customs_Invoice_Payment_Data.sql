-- Run mode: SWING_CLIENT

-- Column: C_Customs_Invoice.C_PaymentTerm_ID
-- Column SQL (old): getC_PaymentTerm_ID_From_C_Customs_Invoice(C_Customs_Invoice.C_Customs_Invoice_ID)
-- 2026-07-23T09:56:07.811Z
UPDATE AD_Column SET ColumnSQL='SELECT C_PaymentTerm_ID from get_Customs_Invoice_Payment_Data(C_Customs_Invoice.C_Customs_Invoice_ID)', IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2026-07-23 09:56:07.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592975
;

-- Column: C_Customs_Invoice.C_Incoterms_ID
-- 2026-07-23T09:57:15.744Z
UPDATE AD_Column SET ColumnSQL='(SELECT C_Incoterms_ID from get_Customs_Invoice_Payment_Data(C_Customs_Invoice.C_Customs_Invoice_ID))', IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2026-07-23 09:57:15.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591811
;

