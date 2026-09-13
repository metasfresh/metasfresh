-- Run mode: SWING_CLIENT

-- Column: Mobile_Application_Action.Name
-- 2026-03-31T14:03:06.622Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-03-31 14:03:06.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592329
;

-- 2026-03-31T14:03:07.359Z
INSERT INTO t_alter_column values('mobile_application_action','Name','VARCHAR(255)',null,null)
;

-- 2026-03-31T14:03:07.363Z
INSERT INTO t_alter_column values('mobile_application_action','Name',null,'NOT NULL',null)
;

