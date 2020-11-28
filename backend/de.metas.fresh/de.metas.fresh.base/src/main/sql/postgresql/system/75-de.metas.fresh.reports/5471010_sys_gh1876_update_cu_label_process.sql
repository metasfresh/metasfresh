-- 2017-09-06T15:30:48.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/docs/label/cu_manufacturing/report_cu.jasper',Updated=TO_TIMESTAMP('2017-09-06 15:30:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540802
;

-- 2017-09-06T15:30:54.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=541202
;

-- 2017-09-06T15:30:54.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=541202
;

