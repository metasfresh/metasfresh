-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- 2023-10-23T09:00:37.380Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-10-23 12:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587601
;

-- 2023-10-23T09:00:38.128Z
INSERT INTO t_alter_column values('m_picking_job_line','C_OrderLine_ID','NUMERIC(10)',null,null)
;

-- 2023-10-23T09:00:38.131Z
INSERT INTO t_alter_column values('m_picking_job_line','C_OrderLine_ID',null,'NOT NULL',null)
;

-- Column: M_Picking_Job_Line.C_Order_ID
-- Column: M_Picking_Job_Line.C_Order_ID
-- 2023-10-23T09:00:42.789Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-10-23 12:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587600
;

-- 2023-10-23T09:00:43.393Z
INSERT INTO t_alter_column values('m_picking_job_line','C_Order_ID','NUMERIC(10)',null,null)
;

-- 2023-10-23T09:00:43.396Z
INSERT INTO t_alter_column values('m_picking_job_line','C_Order_ID',null,'NOT NULL',null)
;

