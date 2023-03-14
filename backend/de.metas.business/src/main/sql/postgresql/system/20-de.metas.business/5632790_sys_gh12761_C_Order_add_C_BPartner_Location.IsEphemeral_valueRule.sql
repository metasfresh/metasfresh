-- 2022-04-01T07:48:19.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_BPartner_Location.C_BPartner_ID=@C_BPartner_ID@ AND C_BPartner_Location.IsActive=''Y'' AND C_BPartner_Location.IsEphemeral=''N'' AND (C_BPartner_Location.IsShipTo=''Y'' OR C_BPartner_Location.IsHandoverLocation=''Y'')',Updated=TO_TIMESTAMP('2022-04-01 10:48:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=167
;


-- 2022-04-04T13:37:38.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_BPartner_Location.C_BPartner_ID=COALESCE(@Bill_BPartner_ID@, @C_BPartner_ID@) AND C_BPartner_Location.IsBillTo=''Y'' AND C_BPartner_Location.IsActive=''Y'' AND C_BPartner_Location.IsEphemeral=''N''',Updated=TO_TIMESTAMP('2022-04-04 16:37:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540229
;
