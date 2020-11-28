-- 2018-08-20T11:55:51.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540404,'(C_ProjectType.AD_Org_ID = @AD_Org_ID/-1@) or (C_ProjectType.AD_Org_ID = 0)',TO_TIMESTAMP('2018-08-20 11:55:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_ProjectType_ID from Org or all Orgs','S',TO_TIMESTAMP('2018-08-20 11:55:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-20T11:56:49.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540404,Updated=TO_TIMESTAMP('2018-08-20 11:56:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8757
;

