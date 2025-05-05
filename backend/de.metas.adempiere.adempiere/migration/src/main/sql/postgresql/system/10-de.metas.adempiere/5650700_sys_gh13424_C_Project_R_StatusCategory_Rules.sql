

-- 2022-08-11T17:06:31.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540596,TO_TIMESTAMP('2022-08-11 20:06:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','R_Status_From_Category','S',TO_TIMESTAMP('2022-08-11 20:06:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-11T17:07:17.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='R_Status.R_StatusCategory_ID = @R_StatusCategory_ID/-1@',Updated=TO_TIMESTAMP('2022-08-11 20:07:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540596
;

-- 2022-08-11T17:08:04.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=345, AD_Val_Rule_ID=540596,Updated=TO_TIMESTAMP('2022-08-11 20:08:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559693
;

-- 2022-08-11T17:08:22.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_project','R_Project_Status_ID','NUMERIC(10)',null,null)
;

-- 2022-08-11T17:29:37.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541047,Updated=TO_TIMESTAMP('2022-08-11 20:29:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559693
;

-- 2022-08-11T17:29:39.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_project','R_Project_Status_ID','NUMERIC(10)',null,null)
;

