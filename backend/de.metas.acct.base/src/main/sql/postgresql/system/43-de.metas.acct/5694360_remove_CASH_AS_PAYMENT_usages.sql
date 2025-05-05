/*
 SELECT t.tablename,
       c.columnname,
       c.readonlylogic,
       c.mandatorylogic,
       f.displaylogic,
       f.ad_field_id,
       tt.name AS tabname,
       tt.ad_window_id
FROM ad_table t
         INNER JOIN ad_column c ON c.ad_table_id = t.ad_table_id
         LEFT OUTER JOIN ad_field f ON f.ad_column_id = c.ad_column_id
         LEFT OUTER JOIN ad_tab tt ON tt.ad_table_id = f.ad_tab_id
WHERE f.displaylogic LIKE '%CashAsPayment%'
   or c.readonlylogic LIKE '%CashAsPayment%'
   or c.mandatorylogic  LIKE '%CashAsPayment%'
 */

-- Field: Payment(195,D) -> Payment(330,D) -> Partner Bank Account
-- Column: C_Payment.C_BP_BankAccount_ID
-- 2023-07-04T10:43:37.718Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2023-07-04 13:43:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4128
;

-- SysConfig Name: CASH_AS_PAYMENT
-- SysConfig Value: Y
-- 2023-07-04T10:56:07.227Z
DELETE FROM AD_SysConfig WHERE AD_SysConfig_ID=50028
;

