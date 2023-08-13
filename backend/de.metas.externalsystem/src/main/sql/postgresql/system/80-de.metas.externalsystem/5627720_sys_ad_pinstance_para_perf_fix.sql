-- fix index introduced by 5580210_sys_gh10691_CreateIndexAd_pinstance_log.sql
-- CREATE INDEX ad_pinstance_para_perf ON ad_pinstance_para (ad_pinstance_id, parametername, p_string);

DROP INDEX IF EXISTS ad_pinstance_para_perf
;

CREATE INDEX ad_pinstance_para_perf ON ad_pinstance_para (ad_pinstance_id, parametername, p_string)
    WHERE p_string IS NOT NULL AND LENGTH(p_string) <= 2000
;

COMMENT ON INDEX ad_pinstance_para_perf IS 'Helps to speed up the view ExternalSystem_Config_PInstance_Log_v. Avoid indexing large text fields because we might get errors like `error index row size exceeds maximum for index`.'
;


