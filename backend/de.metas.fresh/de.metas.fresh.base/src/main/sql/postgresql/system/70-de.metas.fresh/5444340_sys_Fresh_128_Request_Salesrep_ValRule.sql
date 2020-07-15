-- 08.04.2016 11:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2016-04-08 11:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=5432
;

-- 08.04.2016 11:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540328,'AD_User.IsSystemUser = ''Y''',TO_TIMESTAMP('2016-04-08 11:16:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','AS_User_IsSystemUser','S',TO_TIMESTAMP('2016-04-08 11:16:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.04.2016 11:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540328,Updated=TO_TIMESTAMP('2016-04-08 11:16:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=5432
;

