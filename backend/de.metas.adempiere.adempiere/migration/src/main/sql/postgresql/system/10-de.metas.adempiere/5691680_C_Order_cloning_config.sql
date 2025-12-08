-- Column: C_Order.PreparationDate
-- 2023-06-14T09:45:50.573Z
UPDATE AD_Column SET CloningStrategy='DC',Updated=TO_TIMESTAMP('2023-06-14 12:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550247
;

-- Column: C_Order.FreightAmt
-- 2023-06-14T09:46:52.763Z
UPDATE AD_Column SET CloningStrategy='DC',Updated=TO_TIMESTAMP('2023-06-14 12:46:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2195
;

-- Table: C_Order
-- 2023-06-14T09:47:49.223Z
UPDATE AD_Table SET CloningEnabled='E', DownlineCloningStrategy='I',Updated=TO_TIMESTAMP('2023-06-14 12:47:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=259
;

-- Table: C_Order_Cost
-- 2023-06-14T09:48:03.064Z
UPDATE AD_Table SET WhenChildCloningStrategy='I',Updated=TO_TIMESTAMP('2023-06-14 12:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542296
;

-- Table: C_OrderLine
-- 2023-06-14T09:48:18.428Z
UPDATE AD_Table SET DownlineCloningStrategy='S', WhenChildCloningStrategy='I',Updated=TO_TIMESTAMP('2023-06-14 12:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=260
;

-- Table: C_Order_Cost_Detail
-- 2023-06-14T09:48:30.633Z
UPDATE AD_Table SET CloningEnabled='D',Updated=TO_TIMESTAMP('2023-06-14 12:48:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542297
;

