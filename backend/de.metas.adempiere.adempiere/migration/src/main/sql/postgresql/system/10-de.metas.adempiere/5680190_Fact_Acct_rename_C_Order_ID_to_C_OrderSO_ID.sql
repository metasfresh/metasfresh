-- Column: Fact_Acct.C_OrderSO_ID
-- 2023-03-02T14:48:56.287Z
UPDATE AD_Column SET AD_Element_ID=543479, AD_Reference_Value_ID=290, AD_Val_Rule_ID=540622, ColumnName='C_OrderSO_ID', Description='Order', Help='The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.', IsExcludeFromZoomTargets='Y', Name='Order',Updated=TO_TIMESTAMP('2023-03-02 16:48:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585373
;

-- 2023-03-02T14:48:56.291Z
UPDATE AD_Column_Trl trl SET Name='Order' WHERE AD_Column_ID=585373 AND AD_Language='en_US'
;

-- 2023-03-02T14:48:56.292Z
UPDATE AD_Field SET Name='Order', Description='Order', Help='The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.' WHERE AD_Column_ID=585373
;

-- 2023-03-02T14:48:56.295Z
/* DDL */  select update_Column_Translation_From_AD_Element(543479) 
;













-- Column: Fact_Acct_Transactions_View.C_OrderSO_ID
-- 2023-03-02T14:52:40.833Z
UPDATE AD_Column SET AD_Element_ID=543479, AD_Reference_Value_ID=290, AD_Val_Rule_ID=540622, ColumnName='C_OrderSO_ID', Description='Order', Help='The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.', Name='Order',Updated=TO_TIMESTAMP('2023-03-02 16:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585382
;

-- 2023-03-02T14:52:40.836Z
UPDATE AD_Column_Trl trl SET Name='Order' WHERE AD_Column_ID=585382 AND AD_Language='en_US'
;

-- 2023-03-02T14:52:40.837Z
UPDATE AD_Field SET Name='Order', Description='Order', Help='The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.' WHERE AD_Column_ID=585382
;

-- 2023-03-02T14:52:40.841Z
/* DDL */  select update_Column_Translation_From_AD_Element(543479) 
;

