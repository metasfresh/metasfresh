-- Column: M_CostElement.CostingMethod
-- 2023-02-16T12:57:23.975Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-16 14:57:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=13463
;

-- 2023-02-16T12:57:24.820Z
INSERT INTO t_alter_column values('m_costelement','CostingMethod','CHAR(1)',null,null)
;

-- 2023-02-16T12:57:24.959Z
INSERT INTO t_alter_column values('m_costelement','CostingMethod',null,'NOT NULL',null)
;

