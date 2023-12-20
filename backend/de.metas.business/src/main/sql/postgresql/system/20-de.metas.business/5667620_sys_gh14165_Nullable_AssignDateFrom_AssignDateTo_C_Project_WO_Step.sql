
-- Column: C_Project_WO_Step.DateStart
-- 2022-12-08T11:25:26.580Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-12-08 13:25:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583254
;

-- 2022-12-08T11:25:28.543Z
INSERT INTO t_alter_column values('c_project_wo_step','DateStart','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-12-08T11:25:28.548Z
INSERT INTO t_alter_column values('c_project_wo_step','DateStart',null,'NULL',null)
;

-- Column: C_Project_WO_Step.DateEnd
-- 2022-12-08T11:25:37.324Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-12-08 13:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583255
;

-- 2022-12-08T11:25:38.619Z
INSERT INTO t_alter_column values('c_project_wo_step','DateEnd','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-12-08T11:25:38.624Z
INSERT INTO t_alter_column values('c_project_wo_step','DateEnd',null,'NULL',null)
;

