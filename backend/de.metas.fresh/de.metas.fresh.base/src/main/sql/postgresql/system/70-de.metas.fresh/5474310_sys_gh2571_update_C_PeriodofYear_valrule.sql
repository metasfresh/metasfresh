

-- 2017-10-12T18:30:19.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(@C_Year_ID/-1@ = -1 OR C_Period.C_Year_ID=@C_Year_ID/-1@)',Updated=TO_TIMESTAMP('2017-10-12 18:30:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=199
;

