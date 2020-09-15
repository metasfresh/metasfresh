-- 15.10.2015 19:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@EDI_ExportStatus@!P & @EDI_ExportStatus@!E & @EDI_ExportStatus@!I  | @EDI_DESADV_SumPercentage@ < @EDI_DESADV_MinimumSumPercentage@',Updated=TO_TIMESTAMP('2015-10-15 19:14:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551732
;

