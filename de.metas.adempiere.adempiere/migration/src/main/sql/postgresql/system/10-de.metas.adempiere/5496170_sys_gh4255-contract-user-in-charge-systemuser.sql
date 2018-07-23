-- 2018-06-20T10:18:15.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=105,Updated=TO_TIMESTAMP('2018-06-20 10:18:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546058
;

-- 2018-06-20T10:19:30.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540314,Updated=TO_TIMESTAMP('2018-06-20 10:19:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546058
;

-- 2018-06-20T10:20:32.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=164,Updated=TO_TIMESTAMP('2018-06-20 10:20:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546058
;

-- 2018-06-20T10:25:36.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540400,'EXISTS (SELECT * FROM AD_User u WHERE u.isSystemUser = ''Y'')',TO_TIMESTAMP('2018-06-20 10:25:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_User Systemuser','S',TO_TIMESTAMP('2018-06-20 10:25:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-20T10:25:54.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540400,Updated=TO_TIMESTAMP('2018-06-20 10:25:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546058
;


-- 2018-06-20T11:50:40.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_User.isSystemUser = ''Y''',Updated=TO_TIMESTAMP('2018-06-20 11:50:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540400
;

