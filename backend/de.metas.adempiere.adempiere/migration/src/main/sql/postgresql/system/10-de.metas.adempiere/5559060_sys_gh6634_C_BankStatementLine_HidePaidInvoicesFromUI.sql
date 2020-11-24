-- 2020-05-12T05:46:30.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (Code,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Val_Rule_ID,Type,Name,AD_Org_ID,EntityType) VALUES ('(
  (C_Invoice.IsPaid = ''N'')
  AND
  (C_Invoice.C_BPartner_ID=@C_BPartner_ID/-1@ OR @C_BPartner_ID/-1@<=0) AND C_Invoice.AD_Org_ID IN (@AD_Org_ID/-1@, 0)
)',0,'Y',TO_TIMESTAMP('2020-05-12 08:46:30','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-05-12 08:46:30','YYYY-MM-DD HH24:MI:SS'),100,540500,'S','C_Invoice Not Paid and of (C_BPartner, null) and Org',0,'D')
;

-- 2020-05-12T05:47:34.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(
  (C_Invoice.IsPaid != ''Y'')
  AND
  (C_Invoice.C_BPartner_ID=@C_BPartner_ID/-1@ OR @C_BPartner_ID/-1@<=0) AND C_Invoice.AD_Org_ID IN (@AD_Org_ID/-1@, 0)
)',Updated=TO_TIMESTAMP('2020-05-12 08:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540500
;

-- 2020-05-12T05:50:46.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540500,Updated=TO_TIMESTAMP('2020-05-12 08:50:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=10779
;

