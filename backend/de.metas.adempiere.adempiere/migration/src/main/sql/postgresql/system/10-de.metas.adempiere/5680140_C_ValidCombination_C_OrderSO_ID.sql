-- Column: C_ValidCombination.C_OrderSO_ID
-- 2023-03-02T14:09:36.764Z
UPDATE AD_Column SET AD_Element_ID=543479, AD_Reference_Value_ID=290, AD_Val_Rule_ID=540622, ColumnName='C_OrderSO_ID', Description='Order', Help='The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.', IsExcludeFromZoomTargets='Y', Name='Order',Updated=TO_TIMESTAMP('2023-03-02 16:09:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585403
;

-- 2023-03-02T14:09:36.767Z
UPDATE AD_Column_Trl trl SET Name='Order' WHERE AD_Column_ID=585403 AND AD_Language='en_US'
;

-- 2023-03-02T14:09:36.769Z
UPDATE AD_Field SET Name='Order', Description='Order', Help='The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.' WHERE AD_Column_ID=585403
;

-- 2023-03-02T14:09:36.772Z
/* DDL */  select update_Column_Translation_From_AD_Element(543479) 
;

alter table C_ValidCombination rename column c_order_id to C_OrderSO_ID;

