-- 2022-10-18T13:06:03.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540606,'IsSystemUser=''Y'' OR EXISTS (SELECT * from C_BPartner bp where AD_User.C_BPartner_ID=bp.C_BPartner_ID AND bp.IsEmployee=''Y'')',TO_TIMESTAMP('2022-10-18 16:06:03','YYYY-MM-DD HH24:MI:SS'),100,'Employees (nw:excluded Sales Reps)','D','Y','AD_User - Employee or Login user','S',TO_TIMESTAMP('2022-10-18 16:06:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-18T13:06:18.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540606,Updated=TO_TIMESTAMP('2022-10-18 16:06:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8839
;

-- 2022-10-18T13:23:42.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2022-10-18 16:23:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8839
;

