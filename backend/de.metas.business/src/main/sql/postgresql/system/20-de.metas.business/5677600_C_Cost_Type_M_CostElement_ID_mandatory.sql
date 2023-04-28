-- Column: C_Cost_Type.M_CostElement_ID
-- 2023-02-16T12:30:25.596Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-16 14:30:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586067
;

-- 2023-02-16T12:30:26.844Z
INSERT INTO t_alter_column values('c_cost_type','M_CostElement_ID','NUMERIC(10)',null,null)
;

-- 2023-02-16T12:30:26.847Z
INSERT INTO t_alter_column values('c_cost_type','M_CostElement_ID',null,'NOT NULL',null)
;

