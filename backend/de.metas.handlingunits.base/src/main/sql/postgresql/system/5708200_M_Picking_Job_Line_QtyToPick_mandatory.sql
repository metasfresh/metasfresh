-- Column: M_Picking_Job_Line.QtyToPick
-- Column: M_Picking_Job_Line.QtyToPick
-- 2023-10-23T07:13:58.154Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-10-23 10:13:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587596
;

-- 2023-10-23T07:13:59.296Z
INSERT INTO t_alter_column values('m_picking_job_line','QtyToPick','NUMERIC',null,null)
;

-- 2023-10-23T07:13:59.302Z
INSERT INTO t_alter_column values('m_picking_job_line','QtyToPick',null,'NOT NULL',null)
;

-- Column: M_Picking_Job_Line.C_UOM_ID
-- Column: M_Picking_Job_Line.C_UOM_ID
-- 2023-10-23T07:14:10.448Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-10-23 10:14:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587597
;

-- 2023-10-23T07:14:11.528Z
INSERT INTO t_alter_column values('m_picking_job_line','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2023-10-23T07:14:11.534Z
INSERT INTO t_alter_column values('m_picking_job_line','C_UOM_ID',null,'NOT NULL',null)
;

