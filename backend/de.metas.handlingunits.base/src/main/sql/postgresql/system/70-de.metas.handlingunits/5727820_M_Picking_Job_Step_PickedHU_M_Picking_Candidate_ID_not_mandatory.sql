-- Column: M_Picking_Job_Step_PickedHU.M_Picking_Candidate_ID
-- Column: M_Picking_Job_Step_PickedHU.M_Picking_Candidate_ID
-- 2024-07-04T07:55:42.766Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-07-04 10:55:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578409
;

-- 2024-07-04T07:55:44.517Z
INSERT INTO t_alter_column values('m_picking_job_step_pickedhu','M_Picking_Candidate_ID','NUMERIC(10)',null,null)
;

-- 2024-07-04T07:55:44.524Z
INSERT INTO t_alter_column values('m_picking_job_step_pickedhu','M_Picking_Candidate_ID',null,'NULL',null)
;

