
--
-- C_Order.Link_Order_ID: sales and purchase orders are linked via this column
--
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2016-12-09 14:45:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ColumnName='Link_Order_ID' and AD_Table_ID=get_table_id('C_Order');
;
