-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- 2023-11-15T11:53:04.664Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-11-15 12:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583485
;

-- 2023-11-15T11:53:25.418Z
INSERT INTO t_alter_column values('externalsystem_config_leichmehl','TCP_Host','VARCHAR(255)',null,'127.0.0.1')
;

-- 2023-11-15T11:53:25.489Z
UPDATE ExternalSystem_Config_LeichMehl SET TCP_Host='127.0.0.1' WHERE TCP_Host IS NULL
;

-- 2023-11-15T11:53:25.492Z
INSERT INTO t_alter_column values('externalsystem_config_leichmehl','TCP_Host',null,'NOT NULL',null)
;

