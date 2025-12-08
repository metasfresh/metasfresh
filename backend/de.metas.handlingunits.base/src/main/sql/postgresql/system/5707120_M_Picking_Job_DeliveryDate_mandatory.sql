-- Column: M_Picking_Job.DeliveryDate
-- Column: M_Picking_Job.DeliveryDate
-- 2023-10-16T10:47:12.618Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-10-16 13:47:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587553
;

-- 2023-10-16T10:47:13.334Z
INSERT INTO t_alter_column values('m_picking_job','DeliveryDate','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-10-16T10:47:13.340Z
INSERT INTO t_alter_column values('m_picking_job','DeliveryDate',null,'NOT NULL',null)
;

