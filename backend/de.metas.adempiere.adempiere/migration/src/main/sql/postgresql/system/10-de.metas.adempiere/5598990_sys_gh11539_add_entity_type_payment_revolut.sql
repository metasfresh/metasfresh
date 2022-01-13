
-- 2021-07-19T07:41:24.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,Name,Processing,Updated,UpdatedBy) VALUES (0,540270,0,TO_TIMESTAMP('2021-07-19 10:41:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.revolut','Y','Y','de.metas.payment.revolut','N',TO_TIMESTAMP('2021-07-19 10:41:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-19T07:41:29.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_EntityType SET ModelPackage='de.metas.payment.revolut.model',Updated=TO_TIMESTAMP('2021-07-19 10:41:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_EntityType_ID=540270
;

