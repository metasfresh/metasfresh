-- 2020-01-21T13:32:16.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='yearFrom',Updated=TO_TIMESTAMP('2020-01-21 15:32:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577465
;

-- 2020-01-21T13:32:16.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='yearFrom', Name='Jahr Von', Description=NULL, Help=NULL WHERE AD_Element_ID=577465
;

-- 2020-01-21T13:32:16.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='yearFrom', Name='Jahr Von', Description=NULL, Help=NULL, AD_Element_ID=577465 WHERE UPPER(ColumnName)='YEARFROM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-21T13:32:16.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='yearFrom', Name='Jahr Von', Description=NULL, Help=NULL WHERE AD_Element_ID=577465 AND IsCentrallyMaintained='Y'
;

-- 2020-01-21T13:32:38.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='yearTo',Updated=TO_TIMESTAMP('2020-01-21 15:32:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577464
;

-- 2020-01-21T13:32:38.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='yearTo', Name='Jahr bis', Description=NULL, Help=NULL WHERE AD_Element_ID=577464
;

-- 2020-01-21T13:32:38.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='yearTo', Name='Jahr bis', Description=NULL, Help=NULL, AD_Element_ID=577464 WHERE UPPER(ColumnName)='YEARTO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-21T13:32:38.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='yearTo', Name='Jahr bis', Description=NULL, Help=NULL WHERE AD_Element_ID=577464 AND IsCentrallyMaintained='Y'
;

-- 2020-01-21T13:32:51.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='compYearFrom',Updated=TO_TIMESTAMP('2020-01-21 15:32:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577469
;

-- 2020-01-21T13:32:51.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='compYearFrom', Name='Vergleich Jahr von', Description=NULL, Help=NULL WHERE AD_Element_ID=577469
;

-- 2020-01-21T13:32:51.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='compYearFrom', Name='Vergleich Jahr von', Description=NULL, Help=NULL, AD_Element_ID=577469 WHERE UPPER(ColumnName)='COMPYEARFROM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-21T13:32:51.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='compYearFrom', Name='Vergleich Jahr von', Description=NULL, Help=NULL WHERE AD_Element_ID=577469 AND IsCentrallyMaintained='Y'
;

-- 2020-01-21T13:33:04.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='compYearTo',Updated=TO_TIMESTAMP('2020-01-21 15:33:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577470
;

-- 2020-01-21T13:33:04.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='compYearTo', Name='Vergleich Jahr bis', Description=NULL, Help=NULL WHERE AD_Element_ID=577470
;

-- 2020-01-21T13:33:04.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='compYearTo', Name='Vergleich Jahr bis', Description=NULL, Help=NULL, AD_Element_ID=577470 WHERE UPPER(ColumnName)='COMPYEARTO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-21T13:33:04.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='compYearTo', Name='Vergleich Jahr bis', Description=NULL, Help=NULL WHERE AD_Element_ID=577470 AND IsCentrallyMaintained='Y'
;

-- 2020-01-21T13:33:46.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=NULL,Updated=TO_TIMESTAMP('2020-01-21 15:33:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540939
;

-- 2020-01-21T13:33:51.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=NULL,Updated=TO_TIMESTAMP('2020-01-21 15:33:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540942
;

-- 2020-01-21T13:33:55.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=NULL,Updated=TO_TIMESTAMP('2020-01-21 15:33:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540950
;

-- 2020-01-21T13:33:58.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=NULL,Updated=TO_TIMESTAMP('2020-01-21 15:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540951
;

-- 2020-01-21T13:34:23.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=577465, EntityType='U',Updated=TO_TIMESTAMP('2020-01-21 15:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540939
;

-- 2020-01-21T13:34:46.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=577464, ColumnName='Base_Year_End', EntityType='U',Updated=TO_TIMESTAMP('2020-01-21 15:34:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540942
;

-- 2020-01-21T13:34:56.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='yearTo',Updated=TO_TIMESTAMP('2020-01-21 15:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540942
;

-- 2020-01-21T13:35:05.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=577469,Updated=TO_TIMESTAMP('2020-01-21 15:35:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540950
;

-- 2020-01-21T13:35:12.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=577470,Updated=TO_TIMESTAMP('2020-01-21 15:35:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540951
;

-- 2020-01-21T13:35:55.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2020-01-21 15:35:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541065
;

-- 2020-01-21T13:36:03.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2020-01-21 15:36:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541066
;

-- 2020-01-21T13:36:12.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2020-01-21 15:36:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541063
;

-- 2020-01-21T13:36:25.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2020-01-21 15:36:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540939
;

-- 2020-01-21T13:36:34.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2020-01-21 15:36:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540942
;

-- 2020-01-21T13:37:46.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=541452
;

-- 2020-01-21T13:37:46.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=541452
;

-- 2020-01-21T13:37:49.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=541453
;

-- 2020-01-21T13:37:49.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=541453
;

-- 2020-01-21T13:37:57.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=541454
;

-- 2020-01-21T13:37:57.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=541454
;

-- 2020-01-21T13:38:00.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=541455
;

-- 2020-01-21T13:38:00.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=541455
;

-- 2020-01-21T13:38:33.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=NULL,Updated=TO_TIMESTAMP('2020-01-21 15:38:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540942
;

-- 2020-01-21T13:38:57.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=540753, ColumnName='year', EntityType='de.metas.commission_legacy', Name='year',Updated=TO_TIMESTAMP('2020-01-21 15:38:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540939
;

-- 2020-01-21T13:39:03.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=577465, ColumnName='yearFrom', EntityType='U', Name='Jahr Von',Updated=TO_TIMESTAMP('2020-01-21 15:39:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540939
;

-- 2020-01-21T13:52:31.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=577464,Updated=TO_TIMESTAMP('2020-01-21 15:52:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540942
;

-- 2020-01-21T13:52:36.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2020-01-21 15:52:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540939
;

drop table if exists c_year_from;
drop table if exists c_year_to;
drop table if exists c_comp_year_from;
drop table if exists c_comp_year_to;
