-- Reference: C_Order_POTrx_Target_For_C_Order_SOTrx
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2023-01-31T10:13:41.223Z
UPDATE AD_Ref_Table SET WhereClause='C_Order.C_Order_ID IN (select alloc.C_OrderPO_ID from c_po_orderline_alloc alloc where alloc.C_OrderSO_ID=@C_Order_ID/1@)',Updated=TO_TIMESTAMP('2023-01-31 12:13:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540682
;

-- Name: C_Order_POTrx_Target_For_C_Order_SOTrx
-- 2023-01-31T10:21:03.316Z
UPDATE AD_Reference SET Description='Gets Purchase Orders for selected Sales Order',Updated=TO_TIMESTAMP('2023-01-31 12:21:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540682
;

-- 2023-01-31T10:21:03.319Z
UPDATE AD_Reference_Trl trl SET Description='Gets Purchase Orders for selected Sales Order' WHERE AD_Reference_ID=540682 AND AD_Language='en_US'
;

