update fact_acct fa set POReference=(select POReference from C_Invoice d where d.c_invoice_id=fa.record_id) where fa.ad_table_id=get_table_id('C_Invoice');
update fact_acct fa set POReference=(select POReference from C_Order d where d.c_order_id=fa.record_id) where fa.ad_table_id=get_table_id('C_Order');
update fact_acct fa set POReference=(select POReference from DD_Order d where d.DD_Order_ID=fa.record_id) where fa.ad_table_id=get_table_id('DD_Order');
update fact_acct fa set POReference=(select POReference from M_InOut d where d.M_InOut_ID=fa.record_id) where fa.ad_table_id=get_table_id('M_InOut');
update fact_acct fa set POReference=(select POReference from M_Inventory d where d.M_Inventory_ID=fa.record_id) where fa.ad_table_id=get_table_id('M_Inventory');
update fact_acct fa set POReference=(select POReference from M_Movement d where d.M_Movement_ID=fa.record_id) where fa.ad_table_id=get_table_id('M_Movement');


/*
 SELECT t.tablename, t.ad_table_id
FROM ad_column c
         INNER JOIN ad_table t ON t.ad_table_id = c.ad_table_id
WHERE TRUE
  AND t.isView = 'N'
  AND c.columnname = 'POReference'
  AND EXISTS (SELECT 1 FROM ad_column z WHERE z.ad_table_id = t.ad_table_id AND z.columnname = 'Posted')
ORDER BY t.tablename;
 */