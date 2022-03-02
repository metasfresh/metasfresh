-- 2022-03-02T19:56:29.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_User.C_BPartner_ID=@C_BPartner_ID@  AND (CASE  WHEN ''@IsSOTrx@''=''Y'' THEN  AD_User.IsSalesContact=''Y''   ELSE  AD_User.IsPurchaseContact=''Y'' END)',Updated=TO_TIMESTAMP('2022-03-02 20:56:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=123
;

-- 2022-03-02T19:59:32.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_User.C_BPartner_ID=@Bill_BPartner_ID@ AND (CASE  WHEN ''@IsSOTrx@''=''Y'' THEN  AD_User.IsSalesContact=''Y''   ELSE  AD_User.IsPurchaseContact=''Y'' END)',Updated=TO_TIMESTAMP('2022-03-02 20:59:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=191
;

