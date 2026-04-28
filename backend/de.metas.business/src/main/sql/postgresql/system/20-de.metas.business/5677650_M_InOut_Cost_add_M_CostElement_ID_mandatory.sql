-- Column: M_InOut_Cost.M_CostElement_ID
-- 2023-02-16T13:53:53.741Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-16 15:53:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586103
;

-- 2023-02-16T13:53:54.594Z
INSERT INTO t_alter_column values('m_inout_cost','M_CostElement_ID','NUMERIC(10)',null,null)
;

-- 2023-02-16T13:53:54.597Z
INSERT INTO t_alter_column values('m_inout_cost','M_CostElement_ID',null,'NOT NULL',null)
;

-- Column: C_Order_Cost.M_CostElement_ID
-- 2023-02-16T13:54:20.592Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-16 15:54:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586104
;

-- 2023-02-16T13:54:21.247Z
INSERT INTO t_alter_column values('c_order_cost','M_CostElement_ID','NUMERIC(10)',null,null)
;

-- 2023-02-16T13:54:21.269Z
INSERT INTO t_alter_column values('c_order_cost','M_CostElement_ID',null,'NOT NULL',null)
;

