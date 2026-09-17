-- Run mode: SWING_CLIENT

-- Column: M_Picking_Job_Line.C_BPartner_ID
-- 2025-02-27T07:30:38.724Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-02-27 07:30:38.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589741
;

-- 2025-02-27T07:30:39.507Z
INSERT INTO t_alter_column values('m_picking_job_line','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2025-02-27T07:30:39.512Z
INSERT INTO t_alter_column values('m_picking_job_line','C_BPartner_ID',null,'NOT NULL',null)
;

-- Column: M_Picking_Job_Line.C_BPartner_Location_ID
-- 2025-02-27T07:30:43.853Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-02-27 07:30:43.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589742
;

-- 2025-02-27T07:30:44.378Z
INSERT INTO t_alter_column values('m_picking_job_line','C_BPartner_Location_ID','NUMERIC(10)',null,null)
;

-- 2025-02-27T07:30:44.381Z
INSERT INTO t_alter_column values('m_picking_job_line','C_BPartner_Location_ID',null,'NOT NULL',null)
;

