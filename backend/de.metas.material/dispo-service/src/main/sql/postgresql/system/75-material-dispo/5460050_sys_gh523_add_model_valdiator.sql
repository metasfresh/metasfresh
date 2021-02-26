-- 2017-04-12T14:53:11.471
-- URL zum Konzept
INSERT INTO AD_ModelValidator (AD_Client_ID,AD_ModelValidator_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,ModelValidationClass,Name,SeqNo,Updated,UpdatedBy) VALUES (0,540115,0,TO_TIMESTAMP('2017-04-12 14:53:11','YYYY-MM-DD HH24:MI:SS'),100,'This model validator is about firing events (and thus invoking the manufacturing-dispo service) from the metasfresh-backend','de.metas.material.dispo','Y','de.metas.manufacturing.model.interceptor.Main','manufacturing-dispo_Backend-EventDispatchers',0,TO_TIMESTAMP('2017-04-12 14:53:11','YYYY-MM-DD HH24:MI:SS'),100)
;

UPDATE AD_ModelValidator
SET ModelValidationClass='de.metas.material.model.interceptor.Main'
WHERE ModelValidationClass='de.metas.manufacturing.model.interceptor.Main'
;
