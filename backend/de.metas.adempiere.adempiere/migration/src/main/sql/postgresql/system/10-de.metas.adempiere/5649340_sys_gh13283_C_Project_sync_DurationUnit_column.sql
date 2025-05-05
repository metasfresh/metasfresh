-- 2022-08-04T06:07:35.106Z
INSERT INTO t_alter_column values('c_project_wo_resource','DurationUnit','CHAR(1)',null,'h')
;

-- 2022-08-04T06:07:35.153Z
UPDATE C_Project_WO_Resource SET DurationUnit='h' WHERE DurationUnit IS NULL
;
