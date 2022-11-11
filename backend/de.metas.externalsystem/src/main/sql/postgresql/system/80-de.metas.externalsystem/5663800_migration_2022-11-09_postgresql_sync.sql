-- 2022-11-09T14:15:06.869Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Metasfresh','ALTER TABLE public.ExternalSystem_Config_Metasfresh ADD COLUMN HttpResponseAuthKey VARCHAR(255)')
;

-- 2022-11-09T14:15:15.800Z
INSERT INTO t_alter_column values('externalsystem_config_metasfresh','FeedbackURL','VARCHAR(255)',null,null)
;

