create index if not exists ad_pinstance_log_ad_pinstance_id_p_date on ad_pinstance_log (ad_pinstance_id, p_date);
comment on index ad_pinstance_log_ad_pinstance_id_p_date is 'helps to speed up displaying the logs of one AD_PInstance in their chronological order';

create index if not exists ad_pinstance_para_perf on ad_pinstance_para (ad_pinstance_id, parametername, p_string);
comment on index ad_pinstance_para_perf is 'helps to speed up the view ExternalSystem_Config_PInstance_Log_v';