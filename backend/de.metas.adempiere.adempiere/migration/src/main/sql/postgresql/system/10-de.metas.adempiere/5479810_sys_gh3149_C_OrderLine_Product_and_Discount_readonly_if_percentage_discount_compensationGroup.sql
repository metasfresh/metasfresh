-- 2017-12-07T15:04:41.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@S_ResourceAssignment_ID@>0 | @C_Charge_ID@>0 | @IsGroupCompensationLine/N@=Y',Updated=TO_TIMESTAMP('2017-12-07 15:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2221
;

-- 2017-12-07T15:06:14.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='(@IsGroupCompensationLine/N@=Y & @GroupCompensationType/-@=D & @GroupCompensationAmtType/-@=P)',Updated=TO_TIMESTAMP('2017-12-07 15:06:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4031
;

