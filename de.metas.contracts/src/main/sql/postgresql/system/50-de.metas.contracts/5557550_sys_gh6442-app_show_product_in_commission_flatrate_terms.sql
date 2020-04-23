-- 2020-04-21T04:33:10.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@Type_Conditions/''X''@=''Procuremnt'' | @Type_Conditions/''X''@=''Subscr'' | @Type_Conditions/''X''@=''Refund''',Updated=TO_TIMESTAMP('2020-04-21 06:33:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547283
;

-- 2020-04-21T04:34:11.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''X''@=''Subscr''|@Type_Conditions/''X''@=''QualityBsd''|@Type_Conditions/''X''@=''Procuremnt''|@Type_Conditions/''X''@=''Refund''|@Type_Conditions/''X''@=''Commission''',Updated=TO_TIMESTAMP('2020-04-21 06:34:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559779
;

