-- 27.08.2015 11:17:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Y/N configuration to decide if the preparation date will have a fallback on datePromised, in case it can''t be computed from the delivery days. If it is on ''Y'' then the preparationdate will be the promised date, else it will be left null. Default:Y', Value='Y',Updated=TO_TIMESTAMP('2015-08-27 11:17:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540874
;

