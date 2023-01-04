-- 2022-08-10T17:38:16.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DefaultValue='@SQL=select C_BPartner_ID from C_Project where C_Project_ID = @C_Project_ID/-1@',Updated=TO_TIMESTAMP('2022-08-10 20:38:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582333
;

