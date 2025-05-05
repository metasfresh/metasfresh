-- Column: C_AcctSchema_Element.C_OrderSO_ID
-- 2023-03-02T14:23:05.728Z
UPDATE AD_Column SET AD_Element_ID=543479, AD_Reference_Value_ID=290, AD_Val_Rule_ID=540622, ColumnName='C_OrderSO_ID', Description='Order', Help='The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.', IsExcludeFromZoomTargets='Y', Name='Order',Updated=TO_TIMESTAMP('2023-03-02 16:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585405
;

-- 2023-03-02T14:23:05.730Z
UPDATE AD_Column_Trl trl SET Name='Order' WHERE AD_Column_ID=585405 AND AD_Language='en_US'
;

-- 2023-03-02T14:23:05.732Z
UPDATE AD_Field SET Name='Order', Description='Order', Help='The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.' WHERE AD_Column_ID=585405
;

-- 2023-03-02T14:23:05.737Z
/* DDL */  select update_Column_Translation_From_AD_Element(543479) 
;

-- Reference: C_AcctSchema ElementType
-- Value: OR
-- ValueName: Order
-- 2023-03-02T14:24:38.905Z
UPDATE AD_Ref_List SET Name='Sales Order',Updated=TO_TIMESTAMP('2023-03-02 16:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543364
;

-- 2023-03-02T14:24:38.906Z
UPDATE AD_Ref_List_Trl trl SET Name='Sales Order' WHERE AD_Ref_List_ID=543364 AND AD_Language='en_US'
;

-- Reference Item: C_AcctSchema ElementType -> OR_Order
-- 2023-03-02T14:24:51.316Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Auftag',Updated=TO_TIMESTAMP('2023-03-02 16:24:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543364
;

-- Reference Item: C_AcctSchema ElementType -> OR_Order
-- 2023-03-02T14:24:54.347Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Auftag',Updated=TO_TIMESTAMP('2023-03-02 16:24:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543364
;

-- Reference Item: C_AcctSchema ElementType -> OR_Order
-- 2023-03-02T14:25:01.063Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-02 16:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543364
;

-- Reference Item: C_AcctSchema ElementType -> OR_Order
-- 2023-03-02T14:25:08.811Z
UPDATE AD_Ref_List_Trl SET Name='Sales Order',Updated=TO_TIMESTAMP('2023-03-02 16:25:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543364
;

-- Reference Item: C_AcctSchema ElementType -> OR_Order
-- 2023-03-02T14:25:11.116Z
UPDATE AD_Ref_List_Trl SET Name='Sales Order',Updated=TO_TIMESTAMP('2023-03-02 16:25:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543364
;







update AD_Ref_List set ValueName='SalesOrder' where AD_Ref_List_ID=543364;

alter table c_acctschema_element rename column c_order_id to C_OrderSO_ID;

