-- Column: AD_WF_Node.Value
-- 2023-08-10T15:10:40.263Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2023-08-10 18:10:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=11739
;

-- Column: AD_WF_Node.Name
-- 2023-08-10T15:10:56.881Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2023-08-10 18:10:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=294
;

-- Column: AD_Workflow_Trl.Name
-- 2023-08-10T15:36:02.221Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2023-08-10 18:36:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=314
;

-- Column: AD_WF_Node_Trl.Name
-- 2023-08-10T15:37:08.841Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2023-08-10 18:37:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=299
;

-- 2023-08-10T15:39:53.103Z
INSERT INTO t_alter_column values('ad_wf_node_trl','Name','VARCHAR(255)',null,null)
;

-- 2023-08-10T15:40:13.958Z
INSERT INTO t_alter_column values('ad_wf_node','Name','VARCHAR(255)',null,null)
;

-- 2023-08-10T15:40:28.865Z
INSERT INTO t_alter_column values('ad_wf_node','Value','VARCHAR(255)',null,null)
;

-- 2023-08-10T15:41:13.147Z
INSERT INTO t_alter_column values('ad_workflow_trl','Name','VARCHAR(255)',null,null)
;

-- 2023-08-10T15:43:54.357Z
INSERT INTO t_alter_column values('ad_workflow','Name','VARCHAR(255)',null,null)
;

-- 2023-08-10T15:44:03.080Z
INSERT INTO t_alter_column values('ad_workflow','Value','VARCHAR(255)',null,null)
;