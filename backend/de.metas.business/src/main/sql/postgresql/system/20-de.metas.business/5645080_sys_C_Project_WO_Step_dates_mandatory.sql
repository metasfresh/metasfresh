-- Column: C_Project_WO_Step.DateStart
-- 2022-06-24T13:21:06.783726100Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-06-24 16:21:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583254
;

-- 2022-06-24T13:21:08.005640300Z
INSERT INTO t_alter_column values('c_project_wo_step','DateStart','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-06-24T13:21:08.015638900Z
INSERT INTO t_alter_column values('c_project_wo_step','DateStart',null,'NOT NULL',null)
;

-- Column: C_Project_WO_Step.DateEnd
-- 2022-06-24T13:21:18.451880300Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-06-24 16:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583255
;

-- 2022-06-24T13:21:19.202711400Z
INSERT INTO t_alter_column values('c_project_wo_step','DateEnd','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-06-24T13:21:19.203714Z
INSERT INTO t_alter_column values('c_project_wo_step','DateEnd',null,'NOT NULL',null)
;

