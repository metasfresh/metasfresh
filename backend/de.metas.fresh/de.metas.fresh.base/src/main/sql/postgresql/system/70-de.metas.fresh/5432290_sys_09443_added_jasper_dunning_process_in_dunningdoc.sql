-- 05.11.2015 16:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,1000000,540401,TO_TIMESTAMP('2015-11-05 16:52:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y',TO_TIMESTAMP('2015-11-05 16:52:48','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 05.11.2015 16:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET ShowHelp='S',Updated=TO_TIMESTAMP('2015-11-05 16:56:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=1000000
;

