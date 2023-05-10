-- Column: C_PO_OrderLine_Alloc.C_OrderPO_ID
-- 2023-01-31T09:49:32.027Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-31 11:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585670
;

-- 2023-01-31T09:49:33.675Z
INSERT INTO t_alter_column values('c_po_orderline_alloc','C_OrderPO_ID','NUMERIC(10)',null,null)
;

-- Column: C_PO_OrderLine_Alloc.C_OrderSO_ID
-- 2023-01-31T09:50:12.752Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-31 11:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585671
;

-- 2023-01-31T09:50:14.143Z
INSERT INTO t_alter_column values('c_po_orderline_alloc','C_OrderSO_ID','NUMERIC(10)',null,null)
;

-- 2023-01-31T09:50:14.151Z
INSERT INTO t_alter_column values('c_po_orderline_alloc','C_OrderSO_ID',null,'NOT NULL',null)
;

-- 2023-01-31T09:50:35.626Z
INSERT INTO t_alter_column values('c_po_orderline_alloc','C_OrderPO_ID','NUMERIC(10)',null,null)
;

