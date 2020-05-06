
-- in the IT-branche's DB we have the MV, but no migration script for it. Therefore, we delete it first and then recreate it.
DELETE FROM AD_ModelValidator WHERE ModelValidationClass='de.metas.fresh.model.validator.Main';

-- 12.10.2015 08:19:19
-- URL zum Konzept
INSERT INTO AD_ModelValidator (AD_Client_ID,AD_ModelValidator_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,ModelValidationClass,Name,SeqNo,Updated,UpdatedBy) VALUES (0,540104,0,TO_TIMESTAMP('2015-10-12 08:19:18','YYYY-MM-DD HH24:MI:SS'),100,'Registers all the fresh-specific model validators.','de.metas.fresh','Y','de.metas.fresh.model.validator.Main','fresh main model validator',99999,TO_TIMESTAMP('2015-10-12 08:19:18','YYYY-MM-DD HH24:MI:SS'),100)
;

