-- Fix-ups for AD_Process C_Order_Split (registered in 5804910 + 5804940).
-- Two corrections raised in code review of commit 0e781f9:
--   1. AD_Process.AccessLevel '7' (System+Org+Client) is wider than needed for a
--      user-facing transactional process; '3' (Org+Client) is the standard.
--   2. AD_Table_Process.AD_Window_ID was NULL → the action appeared on every C_Order
--      window including the purchase-order window (AD_Window_ID 181 "Bestellung").
--      Bind it explicitly to the sales-order window (AD_Window_ID 143 "Auftrag")
--      so it only shows up on sales orders.

UPDATE AD_Process
   SET AccessLevel = '3',
       Updated = NOW(),
       UpdatedBy = 100
 WHERE Value = 'C_Order_Split';

UPDATE AD_Table_Process
   SET AD_Window_ID = 143 /*From ID Server*/,
       Updated = NOW(),
       UpdatedBy = 100
 WHERE AD_Process_ID = 585625 /*From ID Server*/;
