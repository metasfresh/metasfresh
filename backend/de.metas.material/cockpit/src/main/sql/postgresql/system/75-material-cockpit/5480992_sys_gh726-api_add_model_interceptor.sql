

-- 2017-12-20T07:46:46.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ModelValidator SET Name='material-planning-EventListeners',Updated=TO_TIMESTAMP('2017-12-20 07:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ModelValidator_ID=540116
;

-- 2017-12-20T07:47:07.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ModelValidator SET Name='material-dispo_Backend-EventDispatchers',Updated=TO_TIMESTAMP('2017-12-20 07:47:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ModelValidator_ID=540115
;

-- 2017-12-20T07:47:16.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ModelValidator SET Description='This model validator is about firing events (and thus invoking the material-dispo service) from the metasfresh-backend',Updated=TO_TIMESTAMP('2017-12-20 07:47:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ModelValidator_ID=540115
;

-- 2017-12-20T07:48:28.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ModelValidator (AD_Client_ID,AD_ModelValidator_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,ModelValidationClass,Name,SeqNo,Updated,UpdatedBy) VALUES (0,540118,0,TO_TIMESTAMP('2017-12-20 07:48:27','YYYY-MM-DD HH24:MI:SS'),100,'This insterceptor is about setting up the code in charge of keeping the MD_Cockpit table uiup to date','de.metas.material.cockpit','Y','de.metas.material.cockpit.interceptor.Main','material-cockpit',0,TO_TIMESTAMP('2017-12-20 07:48:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-20T07:48:40.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ModelValidator SET Description='This insterceptor is about setting up the code in charge of keeping the MD_Cockpit table up to date',Updated=TO_TIMESTAMP('2017-12-20 07:48:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ModelValidator_ID=540118
;

