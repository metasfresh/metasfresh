DO $$
BEGIN
--- YEARS


-- 2021-02-02T08:45:30.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Year (AD_Client_ID,AD_Org_ID,C_Calendar_ID,Created,CreatedBy,C_Year_ID,FiscalYear,IsActive,Processing,Updated,UpdatedBy) VALUES (1000000,0,1000000,TO_TIMESTAMP('2021-02-02 10:45:30','YYYY-MM-DD HH24:MI:SS'),100,540016,'2022','Y','N',TO_TIMESTAMP('2021-02-02 10:45:30','YYYY-MM-DD HH24:MI:SS'),100)
;

EXCEPTION WHEN unique_violation THEN

RAISE NOTICE 'year 2022 already exists';
end $$;




DO $$
BEGIN
-- 2021-02-02T08:45:41.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Year (AD_Client_ID,AD_Org_ID,C_Calendar_ID,Created,CreatedBy,C_Year_ID,FiscalYear,IsActive,Processing,Updated,UpdatedBy) VALUES (1000000,0,1000000,TO_TIMESTAMP('2021-02-02 10:45:41','YYYY-MM-DD HH24:MI:SS'),100,540017,'2023','Y','N',TO_TIMESTAMP('2021-02-02 10:45:41','YYYY-MM-DD HH24:MI:SS'),100)
;


EXCEPTION WHEN unique_violation THEN

RAISE NOTICE 'year 2023 already exists';

end $$;




DO $$
BEGIN
-- 2021-02-02T08:45:46.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Year (AD_Client_ID,AD_Org_ID,C_Calendar_ID,Created,CreatedBy,C_Year_ID,FiscalYear,IsActive,Processing,Updated,UpdatedBy) VALUES (1000000,0,1000000,TO_TIMESTAMP('2021-02-02 10:45:46','YYYY-MM-DD HH24:MI:SS'),100,540018,'2024','Y','N',TO_TIMESTAMP('2021-02-02 10:45:46','YYYY-MM-DD HH24:MI:SS'),100)
;


EXCEPTION WHEN unique_violation THEN
RAISE NOTICE 'year 2024 already exists';

end $$;






DO $$
BEGIN



--- PERIODS




-- 2021-02-02T08:54:17.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540158,TO_TIMESTAMP('2021-02-02 10:54:17','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2022'),TO_TIMESTAMP('2022-01-31','YYYY-MM-DD'),'Y','Jan-22',1,'S','N',TO_TIMESTAMP('2022-01-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:54:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:17.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540158 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:54:17.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545570,540158,TO_TIMESTAMP('2021-02-02 10:54:17','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:17.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545571,540158,TO_TIMESTAMP('2021-02-02 10:54:17','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:17.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545572,540158,TO_TIMESTAMP('2021-02-02 10:54:17','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:17.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545573,540158,TO_TIMESTAMP('2021-02-02 10:54:17','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:18.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545574,540158,TO_TIMESTAMP('2021-02-02 10:54:17','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:18.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545575,540158,TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:18.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545576,540158,TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:18.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545577,540158,TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:18.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545578,540158,TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:18.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545579,540158,TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:18.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545580,540158,TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:18.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545581,540158,TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:19.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545582,540158,TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:19.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545583,540158,TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:19.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545584,540158,TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:19.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545585,540158,TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:19.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545586,540158,TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:19.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545587,540158,TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:19.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545588,540158,TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:19.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545589,540158,TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:19.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545590,540158,TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:20.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545591,540158,TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:20.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545592,540158,TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:20.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545593,540158,TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:20.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545594,540158,TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:20.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545595,540158,TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:20.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545596,540158,TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:20.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545597,540158,TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:20.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545598,540158,TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:21.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545599,540158,TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:21.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545600,540158,TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:21.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545601,540158,TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:21.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545602,540158,TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:21.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545603,540158,TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:21.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545604,540158,TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:21.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545605,540158,TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:21.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545606,540158,TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:22.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540159,TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2022'),TO_TIMESTAMP('2022-02-28','YYYY-MM-DD'),'Y','Feb-22',2,'S','N',TO_TIMESTAMP('2022-02-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:54:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:22.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540159 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:54:22.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545607,540159,TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:22.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545608,540159,TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:22.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545609,540159,TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:22.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545610,540159,TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:22.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545611,540159,TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:22.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545612,540159,TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:22.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545613,540159,TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:23.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545614,540159,TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:23.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545615,540159,TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:23.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545616,540159,TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:23.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545617,540159,TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:23.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545618,540159,TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:23.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545619,540159,TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:23.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545620,540159,TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:23.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545621,540159,TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:23.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545622,540159,TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:24.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545623,540159,TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:24.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545624,540159,TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:24.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545625,540159,TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:24.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545626,540159,TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:24.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545627,540159,TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:24.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545628,540159,TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:24.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545629,540159,TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:24.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545630,540159,TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:25.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545631,540159,TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:25.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545632,540159,TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:25.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545633,540159,TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:25.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545634,540159,TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:25.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545635,540159,TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:25.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545636,540159,TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:25.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545637,540159,TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:25.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545638,540159,TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:26.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545639,540159,TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:26.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545640,540159,TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:26.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545641,540159,TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:26.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545642,540159,TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:26.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545643,540159,TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:26.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540160,TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2022'),TO_TIMESTAMP('2022-03-31','YYYY-MM-DD'),'Y','Mär-22',3,'S','N',TO_TIMESTAMP('2022-03-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:26.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540160 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:54:26.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545644,540160,TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:26.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545645,540160,TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:26.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545646,540160,TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:27.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545647,540160,TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:27.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545648,540160,TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:27.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545649,540160,TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:27.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545650,540160,TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:27.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545651,540160,TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:27.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545652,540160,TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:27.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545653,540160,TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:28.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545654,540160,TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:28.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545655,540160,TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:28.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545656,540160,TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:28.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545657,540160,TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:28.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545658,540160,TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:28.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545659,540160,TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:28.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545660,540160,TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:28.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545661,540160,TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:29.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545662,540160,TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:29.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545663,540160,TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:29.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545664,540160,TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:29.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545665,540160,TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:29.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545666,540160,TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:29.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545667,540160,TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:29.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545668,540160,TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:29.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545669,540160,TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:30.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545670,540160,TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:30.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545671,540160,TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:30.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545672,540160,TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:30.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545673,540160,TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:30.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545674,540160,TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:30.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545675,540160,TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:30.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545676,540160,TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:30.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545677,540160,TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:30.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545678,540160,TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:31.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545679,540160,TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:31.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545680,540160,TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:31.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540161,TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2022'),TO_TIMESTAMP('2022-04-30','YYYY-MM-DD'),'Y','Apr-22',4,'S','N',TO_TIMESTAMP('2022-04-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:31.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540161 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:54:31.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545681,540161,TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:31.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545682,540161,TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:31.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545683,540161,TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:31.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545684,540161,TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:32.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545685,540161,TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:32.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545686,540161,TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:32.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545687,540161,TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:32.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545688,540161,TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:32.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545689,540161,TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:32.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545690,540161,TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:32.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545691,540161,TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:32.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545692,540161,TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:32.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545693,540161,TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:33.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545694,540161,TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:33.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545695,540161,TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:33.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545696,540161,TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:33.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545697,540161,TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:33.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545698,540161,TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:33.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545699,540161,TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:33.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545700,540161,TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:33.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545701,540161,TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:34.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545702,540161,TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:34.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545703,540161,TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:34.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545704,540161,TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:34.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545705,540161,TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:34.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545706,540161,TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:34.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545707,540161,TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:34.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545708,540161,TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:34.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545709,540161,TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:34.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545710,540161,TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:35.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545711,540161,TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:35.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545712,540161,TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:35.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545713,540161,TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:35.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545714,540161,TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:35.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545715,540161,TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:35.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545716,540161,TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:35.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545717,540161,TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:36.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540162,TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2022'),TO_TIMESTAMP('2022-05-31','YYYY-MM-DD'),'Y','Mai-22',5,'S','N',TO_TIMESTAMP('2022-05-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:54:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:36.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540162 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:54:36.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545718,540162,TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:36.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545719,540162,TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:36.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545720,540162,TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:36.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545721,540162,TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:36.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545722,540162,TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:36.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545723,540162,TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:36.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545724,540162,TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:36.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545725,540162,TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:37.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545726,540162,TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:37.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545727,540162,TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:37.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545728,540162,TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:37.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545729,540162,TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:37.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545730,540162,TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:37.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545731,540162,TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:37.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545732,540162,TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:37.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545733,540162,TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:38.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545734,540162,TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:38.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545735,540162,TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:38.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545736,540162,TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:38.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545737,540162,TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:38.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545738,540162,TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:38.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545739,540162,TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:38.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545740,540162,TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:38.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545741,540162,TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:38.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545742,540162,TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:39.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545743,540162,TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:39.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545744,540162,TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:39.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545745,540162,TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:39.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545746,540162,TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:39.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545747,540162,TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:39.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545748,540162,TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:39.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545749,540162,TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:39.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545750,540162,TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:40.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545751,540162,TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:40.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545752,540162,TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:40.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545753,540162,TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:40.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545754,540162,TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:40.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540163,TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2022'),TO_TIMESTAMP('2022-06-30','YYYY-MM-DD'),'Y','Jun-22',6,'S','N',TO_TIMESTAMP('2022-06-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:40.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540163 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:54:40.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545755,540163,TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:40.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545756,540163,TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:40.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545757,540163,TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:41.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545758,540163,TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:41.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545759,540163,TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:41.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545760,540163,TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:41.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545761,540163,TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:41.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545762,540163,TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:41.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545763,540163,TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:41.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545764,540163,TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:41.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545765,540163,TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:42.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545766,540163,TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:42.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545767,540163,TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:42.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545768,540163,TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:42.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545769,540163,TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:42.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545770,540163,TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:42.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545771,540163,TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:42.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545772,540163,TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:42.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545773,540163,TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:42.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545774,540163,TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:43.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545775,540163,TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:43.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545776,540163,TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:43.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545777,540163,TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:43.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545778,540163,TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:43.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545779,540163,TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:43.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545780,540163,TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:43.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545781,540163,TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:43.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545782,540163,TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:44.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545783,540163,TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:44.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545784,540163,TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:44.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545785,540163,TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:44.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545786,540163,TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:44.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545787,540163,TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:44.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545788,540163,TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:44.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545789,540163,TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:44.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545790,540163,TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:45.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545791,540163,TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:45.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540164,TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2022'),TO_TIMESTAMP('2022-07-31','YYYY-MM-DD'),'Y','Jul-22',7,'S','N',TO_TIMESTAMP('2022-07-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:45.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540164 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:54:45.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545792,540164,TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:45.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545793,540164,TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:45.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545794,540164,TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:45.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545795,540164,TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:45.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545796,540164,TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:45.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545797,540164,TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:46.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545798,540164,TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:46.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545799,540164,TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:46.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545800,540164,TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:46.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545801,540164,TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:46.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545802,540164,TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:46.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545803,540164,TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:46.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545804,540164,TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:46.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545805,540164,TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:47.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545806,540164,TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:47.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545807,540164,TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:47.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545808,540164,TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:47.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545809,540164,TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:47.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545810,540164,TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:47.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545811,540164,TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:47.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545812,540164,TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:47.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545813,540164,TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:47.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545814,540164,TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:47.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545815,540164,TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:48.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545816,540164,TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:48.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545817,540164,TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:48.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545818,540164,TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:48.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545819,540164,TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:48.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545820,540164,TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:48.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545821,540164,TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:48.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545822,540164,TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:48.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545823,540164,TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:48.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545824,540164,TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:49.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545825,540164,TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:49.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545826,540164,TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:49.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545827,540164,TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:49.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545828,540164,TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:49.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540165,TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2022'),TO_TIMESTAMP('2022-08-31','YYYY-MM-DD'),'Y','Aug-22',8,'S','N',TO_TIMESTAMP('2022-08-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:49.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540165 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:54:49.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545829,540165,TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:49.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545830,540165,TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:49.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545831,540165,TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:49.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545832,540165,TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:50.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545833,540165,TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:50.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545834,540165,TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:50.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545835,540165,TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:50.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545836,540165,TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:50.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545837,540165,TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:50.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545838,540165,TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:50.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545839,540165,TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:50.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545840,540165,TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:51.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545841,540165,TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:51.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545842,540165,TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:51.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545843,540165,TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:51.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545844,540165,TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:51.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545845,540165,TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:51.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545846,540165,TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:51.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545847,540165,TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:51.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545848,540165,TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:51.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545849,540165,TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:52.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545850,540165,TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:52.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545851,540165,TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:52.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545852,540165,TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:52.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545853,540165,TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:52.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545854,540165,TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:52.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545855,540165,TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:52.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545856,540165,TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:52.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545857,540165,TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:52.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545858,540165,TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:53.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545859,540165,TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:53.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545860,540165,TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:53.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545861,540165,TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:53.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545862,540165,TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:53.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545863,540165,TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:53.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545864,540165,TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:53.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545865,540165,TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:53.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540166,TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2022'),TO_TIMESTAMP('2022-09-30','YYYY-MM-DD'),'Y','Sep-22',9,'S','N',TO_TIMESTAMP('2022-09-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:53.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540166 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:54:53.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545866,540166,TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:54.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545867,540166,TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:54.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545868,540166,TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:54.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545869,540166,TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:54.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545870,540166,TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:54.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545871,540166,TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:54.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545872,540166,TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:54.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545873,540166,TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:54.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545874,540166,TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:55.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545875,540166,TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:55.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545876,540166,TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:55.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545877,540166,TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:55.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545878,540166,TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:55.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545879,540166,TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:55.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545880,540166,TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:55.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545881,540166,TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:55.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545882,540166,TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:55.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545883,540166,TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:56.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545884,540166,TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:56.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545885,540166,TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:56.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545886,540166,TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:56.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545887,540166,TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:56.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545888,540166,TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:56.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545889,540166,TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:56.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545890,540166,TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:56.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545891,540166,TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:56.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545892,540166,TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:57.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545893,540166,TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:57.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545894,540166,TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:57.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545895,540166,TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:57.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545896,540166,TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:57.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545897,540166,TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:57.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545898,540166,TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:57.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545899,540166,TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:57.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545900,540166,TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:57.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545901,540166,TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:57.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545902,540166,TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:58.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540167,TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2022'),TO_TIMESTAMP('2022-10-31','YYYY-MM-DD'),'Y','Okt-22',10,'S','N',TO_TIMESTAMP('2022-10-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:58.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540167 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:54:58.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545903,540167,TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:58.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545904,540167,TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:58.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545905,540167,TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:58.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545906,540167,TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:58.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545907,540167,TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:58.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545908,540167,TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:58.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545909,540167,TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:58.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545910,540167,TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:59.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545911,540167,TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:59.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545912,540167,TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:59.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545913,540167,TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:59.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545914,540167,TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:59.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545915,540167,TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:59.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545916,540167,TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:59.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545917,540167,TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:59.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545918,540167,TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:54:59.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545919,540167,TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:00.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545920,540167,TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:00.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545921,540167,TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:00.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545922,540167,TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:00.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545923,540167,TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:00.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545924,540167,TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:00.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545925,540167,TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:00.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545926,540167,TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:00.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545927,540167,TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:01.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545928,540167,TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:01.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545929,540167,TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:01.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545930,540167,TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:01.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545931,540167,TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:01.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545932,540167,TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:01.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545933,540167,TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:01.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545934,540167,TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:01.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545935,540167,TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:01.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545936,540167,TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:02.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545937,540167,TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:02.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545938,540167,TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:02.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545939,540167,TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:02.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540168,TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2022'),TO_TIMESTAMP('2022-11-30','YYYY-MM-DD'),'Y','Nov-22',11,'S','N',TO_TIMESTAMP('2022-11-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:02.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540168 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:55:02.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545940,540168,TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:02.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545941,540168,TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:02.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545942,540168,TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:03.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545943,540168,TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:03.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545944,540168,TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:03.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545945,540168,TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:03.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545946,540168,TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:03.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545947,540168,TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:03.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545948,540168,TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:03.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545949,540168,TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:03.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545950,540168,TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:03.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545951,540168,TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:04.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545952,540168,TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:04.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545953,540168,TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:04.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545954,540168,TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:04.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545955,540168,TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:04.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545956,540168,TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:04.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545957,540168,TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:04.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545958,540168,TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:04.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545959,540168,TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:04.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545960,540168,TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:05.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545961,540168,TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:05.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545962,540168,TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:05.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545963,540168,TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:05.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545964,540168,TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:05.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545965,540168,TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:05.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545966,540168,TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:05.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545967,540168,TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:05.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545968,540168,TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:06.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545969,540168,TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:06.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545970,540168,TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:06.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545971,540168,TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:06.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545972,540168,TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:06.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545973,540168,TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:06.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545974,540168,TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:06.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545975,540168,TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:06.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545976,540168,TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:06.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540169,TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2022'),TO_TIMESTAMP('2022-12-31','YYYY-MM-DD'),'Y','Dez-22',12,'S','N',TO_TIMESTAMP('2022-12-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:06.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540169 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:55:07.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545977,540169,TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:07.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545978,540169,TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:07.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545979,540169,TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:07.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545980,540169,TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:07.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545981,540169,TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:07.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545982,540169,TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:07.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545983,540169,TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:07.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545984,540169,TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:07.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545985,540169,TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:08.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545986,540169,TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:08.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545987,540169,TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:08.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545988,540169,TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:08.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545989,540169,TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:08.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545990,540169,TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:08.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545991,540169,TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:08.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545992,540169,TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:08.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545993,540169,TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:08.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545994,540169,TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:09.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545995,540169,TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:09.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545996,540169,TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:09.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545997,540169,TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:09.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545998,540169,TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:09.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545999,540169,TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:09.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546000,540169,TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:09.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546001,540169,TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:09.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546002,540169,TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:09.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546003,540169,TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:10.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546004,540169,TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:10.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546005,540169,TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:10.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546006,540169,TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:10.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546007,540169,TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:10.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546008,540169,TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:10.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546009,540169,TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:10.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546010,540169,TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:10.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546011,540169,TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:10.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546012,540169,TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:55:11.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546013,540169,TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;





EXCEPTION WHEN unique_violation THEN

RAISE NOTICE 'At least one period of 2022 already exists';

end $$;

-------------------








do $$

begin



-- 2021-02-02T08:58:07.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540170,TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2023'),TO_TIMESTAMP('2023-01-31','YYYY-MM-DD'),'Y','Jan-23',1,'S','N',TO_TIMESTAMP('2023-01-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:07.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540170 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:58:07.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546014,540170,TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:07.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546015,540170,TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:07.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546016,540170,TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:07.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546017,540170,TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:07.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546018,540170,TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:07.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546019,540170,TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:08.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546020,540170,TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:08.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546021,540170,TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:08.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546022,540170,TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:08.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546023,540170,TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:08.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546024,540170,TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:08.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546025,540170,TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:08.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546026,540170,TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:08.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546027,540170,TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:08.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546028,540170,TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:09.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546029,540170,TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:09.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546030,540170,TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:09.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546031,540170,TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:09.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546032,540170,TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:09.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546033,540170,TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:09.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546034,540170,TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:09.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546035,540170,TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:09.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546036,540170,TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:10.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546037,540170,TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:10.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546038,540170,TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:10.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546039,540170,TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:10.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546040,540170,TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:10.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546041,540170,TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:10.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546042,540170,TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:10.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546043,540170,TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:10.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546044,540170,TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:10.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546045,540170,TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:11.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546046,540170,TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:11.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546047,540170,TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:11.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546048,540170,TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:11.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546049,540170,TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:11.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546050,540170,TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:11.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540171,TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2023'),TO_TIMESTAMP('2023-02-28','YYYY-MM-DD'),'Y','Feb-23',2,'S','N',TO_TIMESTAMP('2023-02-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:11.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540171 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:58:11.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546051,540171,TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:11.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546052,540171,TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:12.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546053,540171,TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:12.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546054,540171,TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:12.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546055,540171,TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:12.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546056,540171,TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:12.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546057,540171,TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:12.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546058,540171,TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:12.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546059,540171,TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:12.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546060,540171,TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:12.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546061,540171,TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:13.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546062,540171,TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:13.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546063,540171,TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:13.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546064,540171,TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:13.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546065,540171,TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:13.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546066,540171,TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:13.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546067,540171,TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:13.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546068,540171,TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:13.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546069,540171,TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:13.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546070,540171,TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:14.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546071,540171,TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:14.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546072,540171,TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:14.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546073,540171,TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:14.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546074,540171,TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:14.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546075,540171,TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:14.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546076,540171,TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:14.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546077,540171,TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:14.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546078,540171,TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:14.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546079,540171,TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:15.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546080,540171,TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:15.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546081,540171,TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:15.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546082,540171,TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:15.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546083,540171,TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:15.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546084,540171,TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:15.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546085,540171,TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:15.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546086,540171,TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:15.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546087,540171,TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:16.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540172,TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2023'),TO_TIMESTAMP('2023-03-31','YYYY-MM-DD'),'Y','Mär-23',3,'S','N',TO_TIMESTAMP('2023-03-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:58:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:16.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540172 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:58:16.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546088,540172,TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:16.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546089,540172,TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:16.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546090,540172,TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:16.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546091,540172,TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:16.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546092,540172,TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:16.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546093,540172,TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:16.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546094,540172,TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:16.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546095,540172,TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:17.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546096,540172,TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:17.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546097,540172,TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:17.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546098,540172,TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:17.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546099,540172,TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:17.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546100,540172,TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:17.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546101,540172,TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:17.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546102,540172,TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:17.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546103,540172,TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:17.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546104,540172,TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:18.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546105,540172,TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:18.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546106,540172,TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:18.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546107,540172,TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:18.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546108,540172,TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:18.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546109,540172,TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:18.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546110,540172,TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:18.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546111,540172,TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:19.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546112,540172,TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:19.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546113,540172,TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:19.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546114,540172,TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:19.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546115,540172,TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:19.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546116,540172,TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:19.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546117,540172,TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:19.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546118,540172,TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:19.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546119,540172,TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:19.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546120,540172,TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:19.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546121,540172,TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:20.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546122,540172,TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:20.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546123,540172,TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:20.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546124,540172,TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:20.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540173,TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2023'),TO_TIMESTAMP('2023-04-30','YYYY-MM-DD'),'Y','Apr-23',4,'S','N',TO_TIMESTAMP('2023-04-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:20.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540173 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:58:20.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546125,540173,TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:20.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546126,540173,TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:20.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546127,540173,TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:20.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546128,540173,TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:21.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546129,540173,TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:21.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546130,540173,TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:21.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546131,540173,TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:21.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546132,540173,TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:21.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546133,540173,TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:21.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546134,540173,TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:21.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546135,540173,TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:21.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546136,540173,TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:22.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546137,540173,TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:22.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546138,540173,TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:22.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546139,540173,TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:22.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546140,540173,TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:22.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546141,540173,TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:22.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546142,540173,TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:22.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546143,540173,TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:22.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546144,540173,TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:23.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546145,540173,TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:23.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546146,540173,TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:23.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546147,540173,TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:23.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546148,540173,TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:23.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546149,540173,TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:23.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546150,540173,TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:23.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546151,540173,TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:23.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546152,540173,TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:23.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546153,540173,TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:24.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546154,540173,TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:24.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546155,540173,TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:24.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546156,540173,TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:24.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546157,540173,TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:24.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546158,540173,TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:24.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546159,540173,TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:24.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546160,540173,TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:24.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546161,540173,TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:25.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540174,TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2023'),TO_TIMESTAMP('2023-05-31','YYYY-MM-DD'),'Y','Mai-23',5,'S','N',TO_TIMESTAMP('2023-05-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:25.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540174 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:58:25.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546162,540174,TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:25.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546163,540174,TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:25.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546164,540174,TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:25.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546165,540174,TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:25.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546166,540174,TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:25.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546167,540174,TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:25.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546168,540174,TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:26.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546169,540174,TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:26.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546170,540174,TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:26.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546171,540174,TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:26.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546172,540174,TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:26.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546173,540174,TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:26.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546174,540174,TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:26.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546175,540174,TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:26.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546176,540174,TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:27.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546177,540174,TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:27.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546178,540174,TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:27.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546179,540174,TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:27.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546180,540174,TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:27.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546181,540174,TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:27.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546182,540174,TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:27.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546183,540174,TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:27.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546184,540174,TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:27.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546185,540174,TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:28.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546186,540174,TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:28.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546187,540174,TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:28.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546188,540174,TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:28.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546189,540174,TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:28.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546190,540174,TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:28.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546191,540174,TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:28.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546192,540174,TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:28.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546193,540174,TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:29.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546194,540174,TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:29.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546195,540174,TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:29.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546196,540174,TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:29.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546197,540174,TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:29.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546198,540174,TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:29.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540175,TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2023'),TO_TIMESTAMP('2023-06-30','YYYY-MM-DD'),'Y','Jun-23',6,'S','N',TO_TIMESTAMP('2023-06-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:29.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540175 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:58:29.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546199,540175,TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:29.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546200,540175,TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:29.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546201,540175,TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:30.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546202,540175,TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:30.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546203,540175,TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:30.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546204,540175,TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:30.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546205,540175,TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:30.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546206,540175,TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:30.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546207,540175,TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:30.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546208,540175,TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:30.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546209,540175,TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:31.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546210,540175,TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:31.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546211,540175,TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:31.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546212,540175,TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:31.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546213,540175,TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:31.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546214,540175,TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:31.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546215,540175,TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:31.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546216,540175,TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:31.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546217,540175,TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:31.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546218,540175,TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:31.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546219,540175,TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:32.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546220,540175,TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:32.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546221,540175,TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:32.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546222,540175,TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:32.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546223,540175,TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:32.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546224,540175,TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:32.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546225,540175,TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:32.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546226,540175,TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:32.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546227,540175,TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:33.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546228,540175,TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:33.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546229,540175,TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:33.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546230,540175,TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:33.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546231,540175,TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:33.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546232,540175,TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:33.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546233,540175,TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:33.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546234,540175,TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:33.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546235,540175,TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:33.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540176,TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2023'),TO_TIMESTAMP('2023-07-31','YYYY-MM-DD'),'Y','Jul-23',7,'S','N',TO_TIMESTAMP('2023-07-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:33.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540176 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:58:34.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546236,540176,TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:34.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546237,540176,TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:34.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546238,540176,TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:34.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546239,540176,TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:34.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546240,540176,TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:34.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546241,540176,TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:34.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546242,540176,TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:34.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546243,540176,TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:34.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546244,540176,TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:35.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546245,540176,TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:35.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546246,540176,TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:35.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546247,540176,TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:35.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546248,540176,TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:35.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546249,540176,TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:35.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546250,540176,TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:35.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546251,540176,TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:35.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546252,540176,TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:35.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546253,540176,TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:36.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546254,540176,TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:36.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546255,540176,TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:36.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546256,540176,TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:36.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546257,540176,TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:36.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546258,540176,TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:36.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546259,540176,TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:36.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546260,540176,TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:36.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546261,540176,TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:36.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546262,540176,TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:36.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546263,540176,TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:37.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546264,540176,TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:37.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546265,540176,TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:37.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546266,540176,TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:37.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546267,540176,TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:37.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546268,540176,TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:37.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546269,540176,TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:37.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546270,540176,TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:37.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546271,540176,TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:38.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546272,540176,TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:38.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540177,TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2023'),TO_TIMESTAMP('2023-08-31','YYYY-MM-DD'),'Y','Aug-23',8,'S','N',TO_TIMESTAMP('2023-08-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:38.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540177 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:58:38.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546273,540177,TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:38.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546274,540177,TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:38.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546275,540177,TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:38.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546276,540177,TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:38.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546277,540177,TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:38.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546278,540177,TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:39.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546279,540177,TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:39.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546280,540177,TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:39.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546281,540177,TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:39.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546282,540177,TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:39.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546283,540177,TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:39.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546284,540177,TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:39.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546285,540177,TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:39.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546286,540177,TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:39.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546287,540177,TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:40.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546288,540177,TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:40.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546289,540177,TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:40.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546290,540177,TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:40.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546291,540177,TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:40.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546292,540177,TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:40.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546293,540177,TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:40.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546294,540177,TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:40.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546295,540177,TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:40.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546296,540177,TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:40.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546297,540177,TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:41.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546298,540177,TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:41.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546299,540177,TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:41.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546300,540177,TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:41.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546301,540177,TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:41.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546302,540177,TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:41.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546303,540177,TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:41.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546304,540177,TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:41.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546305,540177,TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:41.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546306,540177,TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:41.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546307,540177,TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:42.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546308,540177,TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:42.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546309,540177,TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:42.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540178,TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2023'),TO_TIMESTAMP('2023-09-30','YYYY-MM-DD'),'Y','Sep-23',9,'S','N',TO_TIMESTAMP('2023-09-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:42.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540178 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:58:42.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546310,540178,TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:42.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546311,540178,TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:42.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546312,540178,TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:42.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546313,540178,TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:42.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546314,540178,TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:43.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546315,540178,TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:43.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546316,540178,TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:43.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546317,540178,TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:43.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546318,540178,TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:43.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546319,540178,TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:43.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546320,540178,TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:43.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546321,540178,TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:43.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546322,540178,TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:43.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546323,540178,TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:43.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546324,540178,TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:44.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546325,540178,TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:44.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546326,540178,TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:44.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546327,540178,TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:44.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546328,540178,TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:44.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546329,540178,TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:44.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546330,540178,TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:44.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546331,540178,TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:44.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546332,540178,TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:44.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546333,540178,TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:44.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546334,540178,TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:45.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546335,540178,TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:45.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546336,540178,TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:45.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546337,540178,TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:45.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546338,540178,TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:45.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546339,540178,TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:45.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546340,540178,TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:45.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546341,540178,TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:45.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546342,540178,TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:45.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546343,540178,TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:46.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546344,540178,TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:46.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546345,540178,TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:46.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546346,540178,TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:46.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540179,TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2023'),TO_TIMESTAMP('2023-10-31','YYYY-MM-DD'),'Y','Okt-23',10,'S','N',TO_TIMESTAMP('2023-10-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:46.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540179 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:58:46.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546347,540179,TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:46.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546348,540179,TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:46.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546349,540179,TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:46.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546350,540179,TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:46.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546351,540179,TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:47.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546352,540179,TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:47.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546353,540179,TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:47.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546354,540179,TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:47.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546355,540179,TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:47.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546356,540179,TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:47.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546357,540179,TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:47.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546358,540179,TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:47.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546359,540179,TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:47.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546360,540179,TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:48.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546361,540179,TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:48.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546362,540179,TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:48.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546363,540179,TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:48.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546364,540179,TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:48.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546365,540179,TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:48.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546366,540179,TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:48.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546367,540179,TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:48.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546368,540179,TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:49.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546369,540179,TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:49.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546370,540179,TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:49.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546371,540179,TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:49.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546372,540179,TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:49.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546373,540179,TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:49.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546374,540179,TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:49.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546375,540179,TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:49.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546376,540179,TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:49.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546377,540179,TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:49.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546378,540179,TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:50.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546379,540179,TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:50.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546380,540179,TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:50.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546381,540179,TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:50.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546382,540179,TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:50.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546383,540179,TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:50.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540180,TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2023'),TO_TIMESTAMP('2023-11-30','YYYY-MM-DD'),'Y','Nov-23',11,'S','N',TO_TIMESTAMP('2023-11-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:50.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540180 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:58:50.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546384,540180,TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:50.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546385,540180,TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:51.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546386,540180,TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:51.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546387,540180,TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:51.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546388,540180,TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:51.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546389,540180,TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:51.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546390,540180,TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:51.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546391,540180,TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:51.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546392,540180,TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:51.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546393,540180,TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:51.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546394,540180,TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:52.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546395,540180,TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:52.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546396,540180,TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:52.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546397,540180,TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:52.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546398,540180,TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:52.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546399,540180,TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:52.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546400,540180,TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:52.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546401,540180,TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:52.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546402,540180,TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:52.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546403,540180,TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:53.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546404,540180,TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:53.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546405,540180,TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:53.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546406,540180,TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:53.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546407,540180,TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:53.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546408,540180,TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:53.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546409,540180,TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:53.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546410,540180,TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:53.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546411,540180,TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:54.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546412,540180,TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:54.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546413,540180,TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:54.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546414,540180,TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:54.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546415,540180,TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:54.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546416,540180,TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:54.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546417,540180,TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:54.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546418,540180,TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:54.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546419,540180,TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:54.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546420,540180,TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:54.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540181,TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2023'),TO_TIMESTAMP('2023-12-31','YYYY-MM-DD'),'Y','Dez-23',12,'S','N',TO_TIMESTAMP('2023-12-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:54.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540181 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:58:55.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546421,540181,TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:55.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546422,540181,TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:55.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546423,540181,TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:55.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546424,540181,TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:55.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546425,540181,TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:55.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546426,540181,TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:55.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546427,540181,TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:55.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546428,540181,TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:55.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546429,540181,TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:55.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546430,540181,TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:56.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546431,540181,TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:56.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546432,540181,TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:56.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546433,540181,TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:56.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546434,540181,TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:56.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546435,540181,TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:56.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546436,540181,TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:56.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546437,540181,TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:56.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546438,540181,TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:56.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546439,540181,TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:57.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546440,540181,TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:57.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546441,540181,TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:57.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546442,540181,TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:57.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546443,540181,TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:57.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546444,540181,TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:57.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546445,540181,TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:57.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546446,540181,TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:57.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546447,540181,TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:57.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546448,540181,TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:57.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546449,540181,TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:58.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546450,540181,TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:58.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546451,540181,TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:58.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546452,540181,TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:58.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546453,540181,TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:58.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546454,540181,TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:58.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546455,540181,TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:58.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546456,540181,TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:58:58.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546457,540181,TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;



EXCEPTION WHEN unique_violation THEN
RAISE NOTICE 'At least one period of 2023 already exists';

end $$;

-------------------


DO $$
BEGIN


-- 2021-02-02T08:59:21.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540182,TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2024'),TO_TIMESTAMP('2024-01-31','YYYY-MM-DD'),'Y','Jan-24',1,'S','N',TO_TIMESTAMP('2024-01-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:21.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540182 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:59:21.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546458,540182,TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:21.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546459,540182,TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:21.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546460,540182,TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:21.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546461,540182,TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:21.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546462,540182,TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:22.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546463,540182,TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:22.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546464,540182,TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:22.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546465,540182,TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:22.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546466,540182,TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:22.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546467,540182,TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:22.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546468,540182,TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:22.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546469,540182,TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:22.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546470,540182,TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:22.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546471,540182,TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:23.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546472,540182,TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:23.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546473,540182,TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:23.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546474,540182,TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:23.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546475,540182,TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:23.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546476,540182,TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:23.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546477,540182,TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:23.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546478,540182,TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:23.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546479,540182,TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:23.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546480,540182,TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:24.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546481,540182,TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:24.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546482,540182,TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:24.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546483,540182,TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:24.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546484,540182,TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:24.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546485,540182,TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:24.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546486,540182,TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:24.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546487,540182,TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:24.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546488,540182,TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:24.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546489,540182,TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:25.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546490,540182,TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:25.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546491,540182,TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:25.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546492,540182,TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:25.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546493,540182,TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:25.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546494,540182,TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:25.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540183,TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2024'),TO_TIMESTAMP('2024-02-29','YYYY-MM-DD'),'Y','Feb-24',2,'S','N',TO_TIMESTAMP('2024-02-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:25.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540183 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:59:25.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546495,540183,TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:25.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546496,540183,TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:26.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546497,540183,TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:26.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546498,540183,TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:26.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546499,540183,TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:26.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546500,540183,TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:26.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546501,540183,TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:26.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546502,540183,TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:26.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546503,540183,TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:26.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546504,540183,TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:26.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546505,540183,TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:26.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546506,540183,TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:27.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546507,540183,TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:27.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546508,540183,TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:27.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546509,540183,TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:27.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546510,540183,TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:27.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546511,540183,TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:27.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546512,540183,TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:27.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546513,540183,TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:27.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546514,540183,TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:27.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546515,540183,TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:28.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546516,540183,TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:28.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546517,540183,TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:28.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546518,540183,TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:28.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546519,540183,TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:28.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546520,540183,TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:28.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546521,540183,TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:28.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546522,540183,TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:28.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546523,540183,TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:28.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546524,540183,TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:29.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546525,540183,TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:29.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546526,540183,TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:29.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546527,540183,TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:29.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546528,540183,TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:29.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546529,540183,TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:29.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546530,540183,TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:29.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546531,540183,TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:29.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540184,TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2024'),TO_TIMESTAMP('2024-03-31','YYYY-MM-DD'),'Y','Mär-24',3,'S','N',TO_TIMESTAMP('2024-03-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:29.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540184 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:59:30.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546532,540184,TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:30.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546533,540184,TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:30.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546534,540184,TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:30.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546535,540184,TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:30.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546536,540184,TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:30.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546537,540184,TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:30.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546538,540184,TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:30.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546539,540184,TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:30.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546540,540184,TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:30.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546541,540184,TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:31.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546542,540184,TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:31.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546543,540184,TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:31.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546544,540184,TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:31.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546545,540184,TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:31.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546546,540184,TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:31.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546547,540184,TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:31.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546548,540184,TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:31.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546549,540184,TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:31.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546550,540184,TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:31.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546551,540184,TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:32.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546552,540184,TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:32.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546553,540184,TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:32.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546554,540184,TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:32.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546555,540184,TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:32.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546556,540184,TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:32.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546557,540184,TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:32.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546558,540184,TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:32.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546559,540184,TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:32.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546560,540184,TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:33.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546561,540184,TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:33.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546562,540184,TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:33.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546563,540184,TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:33.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546564,540184,TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:33.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546565,540184,TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:33.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546566,540184,TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:33.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546567,540184,TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:33.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546568,540184,TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:34.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540185,TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2024'),TO_TIMESTAMP('2024-04-30','YYYY-MM-DD'),'Y','Apr-24',4,'S','N',TO_TIMESTAMP('2024-04-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:59:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:34.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540185 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:59:34.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546569,540185,TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:34.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546570,540185,TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:34.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546571,540185,TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:34.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546572,540185,TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:34.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546573,540185,TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:34.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546574,540185,TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:34.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546575,540185,TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:34.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546576,540185,TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:35.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546577,540185,TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:35.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546578,540185,TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:35.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546579,540185,TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:35.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546580,540185,TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:35.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546581,540185,TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:35.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546582,540185,TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:35.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546583,540185,TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:35.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546584,540185,TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:35.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546585,540185,TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:36.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546586,540185,TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:36.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546587,540185,TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:36.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546588,540185,TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:36.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546589,540185,TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:36.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546590,540185,TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:36.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546591,540185,TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:36.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546592,540185,TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:36.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546593,540185,TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:37.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546594,540185,TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:37.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546595,540185,TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:37.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546596,540185,TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:37.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546597,540185,TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:37.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546598,540185,TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:37.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546599,540185,TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:37.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546600,540185,TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:37.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546601,540185,TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:37.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546602,540185,TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:37.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546603,540185,TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:38.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546604,540185,TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:38.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546605,540185,TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:38.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540186,TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2024'),TO_TIMESTAMP('2024-05-31','YYYY-MM-DD'),'Y','Mai-24',5,'S','N',TO_TIMESTAMP('2024-05-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:38.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540186 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:59:38.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546606,540186,TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:38.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546607,540186,TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:38.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546608,540186,TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:38.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546609,540186,TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:38.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546610,540186,TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:38.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546611,540186,TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:39.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546612,540186,TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:39.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546613,540186,TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:39.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546614,540186,TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:39.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546615,540186,TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:39.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546616,540186,TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:39.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546617,540186,TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:39.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546618,540186,TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:39.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546619,540186,TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:39.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546620,540186,TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:40.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546621,540186,TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:40.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546622,540186,TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:40.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546623,540186,TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:40.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546624,540186,TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:40.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546625,540186,TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:40.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546626,540186,TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:40.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546627,540186,TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:40.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546628,540186,TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:40.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546629,540186,TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:41.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546630,540186,TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:41.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546631,540186,TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:41.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546632,540186,TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:41.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546633,540186,TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:41.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546634,540186,TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:41.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546635,540186,TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:41.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546636,540186,TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:41.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546637,540186,TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:42.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546638,540186,TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:42.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546639,540186,TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:42.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546640,540186,TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:42.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546641,540186,TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:42.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546642,540186,TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:42.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540187,TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2024'),TO_TIMESTAMP('2024-06-30','YYYY-MM-DD'),'Y','Jun-24',6,'S','N',TO_TIMESTAMP('2024-06-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:42.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540187 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:59:42.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546643,540187,TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:42.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546644,540187,TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:42.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546645,540187,TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:43.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546646,540187,TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:43.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546647,540187,TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:43.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546648,540187,TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:43.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546649,540187,TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:43.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546650,540187,TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:43.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546651,540187,TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:43.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546652,540187,TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:43.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546653,540187,TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:44.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546654,540187,TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:44.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546655,540187,TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:44.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546656,540187,TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:44.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546657,540187,TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:44.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546658,540187,TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:44.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546659,540187,TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:44.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546660,540187,TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:44.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546661,540187,TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:44.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546662,540187,TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:45.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546663,540187,TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:45.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546664,540187,TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:45.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546665,540187,TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:45.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546666,540187,TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:45.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546667,540187,TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:45.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546668,540187,TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:45.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546669,540187,TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:45.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546670,540187,TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:45.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546671,540187,TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:46.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546672,540187,TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:46.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546673,540187,TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:46.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546674,540187,TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:46.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546675,540187,TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:46.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546676,540187,TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:46.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546677,540187,TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:46.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546678,540187,TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:46.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546679,540187,TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:46.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540188,TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2024'),TO_TIMESTAMP('2024-07-31','YYYY-MM-DD'),'Y','Jul-24',7,'S','N',TO_TIMESTAMP('2024-07-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:46.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540188 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:59:47.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546680,540188,TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:47.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546681,540188,TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:47.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546682,540188,TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:47.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546683,540188,TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:47.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546684,540188,TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:47.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546685,540188,TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:47.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546686,540188,TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:47.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546687,540188,TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:47.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546688,540188,TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:48.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546689,540188,TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:48.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546690,540188,TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:48.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546691,540188,TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:48.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546692,540188,TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:48.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546693,540188,TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:48.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546694,540188,TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:48.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546695,540188,TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:48.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546696,540188,TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:48.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546697,540188,TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:48.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546698,540188,TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:49.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546699,540188,TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:49.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546700,540188,TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:49.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546701,540188,TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:49.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546702,540188,TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:49.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546703,540188,TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:49.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546704,540188,TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:49.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546705,540188,TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:49.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546706,540188,TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:49.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546707,540188,TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:49.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546708,540188,TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:50.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546709,540188,TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:50.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546710,540188,TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:50.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546711,540188,TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:50.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546712,540188,TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:50.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546713,540188,TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:50.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546714,540188,TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:50.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546715,540188,TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:50.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546716,540188,TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:50.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540189,TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2024'),TO_TIMESTAMP('2024-08-31','YYYY-MM-DD'),'Y','Aug-24',8,'S','N',TO_TIMESTAMP('2024-08-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:59:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:50.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540189 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:59:51.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546717,540189,TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:51.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546718,540189,TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:51.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546719,540189,TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:51.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546720,540189,TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:51.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546721,540189,TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:51.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546722,540189,TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:51.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546723,540189,TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:51.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546724,540189,TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:52Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546725,540189,TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:52.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546726,540189,TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:52.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546727,540189,TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:52.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546728,540189,TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:52.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546729,540189,TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:52.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546730,540189,TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:52.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546731,540189,TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:52.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546732,540189,TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:52.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546733,540189,TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:52.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546734,540189,TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:53.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546735,540189,TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:53.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546736,540189,TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:53.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546737,540189,TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:53.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546738,540189,TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:53.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546739,540189,TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:53.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546740,540189,TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:53.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546741,540189,TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:53.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546742,540189,TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:53.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546743,540189,TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:54.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546744,540189,TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:54.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546745,540189,TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:54.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546746,540189,TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:54.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546747,540189,TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:54.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546748,540189,TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:54.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546749,540189,TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:54.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546750,540189,TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:54.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546751,540189,TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:55.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546752,540189,TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:55.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546753,540189,TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:55.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540190,TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2024'),TO_TIMESTAMP('2024-09-30','YYYY-MM-DD'),'Y','Sep-24',9,'S','N',TO_TIMESTAMP('2024-09-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:55.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540190 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:59:55.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546754,540190,TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:55.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546755,540190,TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:55.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546756,540190,TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:55.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546757,540190,TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:55.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546758,540190,TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:56.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546759,540190,TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:56.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546760,540190,TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:56.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546761,540190,TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:56.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546762,540190,TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:56.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546763,540190,TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:56.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546764,540190,TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:56.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546765,540190,TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:56.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546766,540190,TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:56.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546767,540190,TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:56.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546768,540190,TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:57.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546769,540190,TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:57.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546770,540190,TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:57.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546771,540190,TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:57.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546772,540190,TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:57.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546773,540190,TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:57.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546774,540190,TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:57.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546775,540190,TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:57.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546776,540190,TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:58.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546777,540190,TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:58.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546778,540190,TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:58.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546779,540190,TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:58.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546780,540190,TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:58.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546781,540190,TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:58.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546782,540190,TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:58.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546783,540190,TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:58.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546784,540190,TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:58.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546785,540190,TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:59.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546786,540190,TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:59.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546787,540190,TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:59.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546788,540190,TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:59.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546789,540190,TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:59.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546790,540190,TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:59.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540191,TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2024'),TO_TIMESTAMP('2024-10-31','YYYY-MM-DD'),'Y','Okt-24',10,'S','N',TO_TIMESTAMP('2024-10-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:59.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540191 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T08:59:59.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546791,540191,TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T08:59:59.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546792,540191,TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:00.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546793,540191,TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 10:59:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:00.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546794,540191,TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:00.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546795,540191,TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:00.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546796,540191,TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:00.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546797,540191,TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:00.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546798,540191,TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:00.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546799,540191,TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:00.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546800,540191,TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:00.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546801,540191,TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:01.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546802,540191,TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:01.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546803,540191,TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:01.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546804,540191,TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:01.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546805,540191,TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:01.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546806,540191,TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:01.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546807,540191,TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:01.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546808,540191,TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:01.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546809,540191,TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:02.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546810,540191,TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:02.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546811,540191,TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:02.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546812,540191,TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:02.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546813,540191,TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:02.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546814,540191,TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:02.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546815,540191,TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:02.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546816,540191,TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:02.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546817,540191,TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:02.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546818,540191,TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:03.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546819,540191,TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:03.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546820,540191,TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:03.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546821,540191,TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:03.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546822,540191,TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:03.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546823,540191,TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:03.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546824,540191,TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:03.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546825,540191,TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:03.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546826,540191,TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:03.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546827,540191,TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:04.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540192,TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2024'),TO_TIMESTAMP('2024-11-30','YYYY-MM-DD'),'Y','Nov-24',11,'S','N',TO_TIMESTAMP('2024-11-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 11:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:04.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540192 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T09:00:04.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546828,540192,TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:04.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546829,540192,TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:04.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546830,540192,TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:04.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546831,540192,TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:04.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546832,540192,TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:04.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546833,540192,TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:04.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546834,540192,TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:04.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546835,540192,TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:05.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546836,540192,TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:05.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546837,540192,TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:05.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546838,540192,TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:05.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546839,540192,TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:05.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546840,540192,TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:05.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546841,540192,TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:05.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546842,540192,TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:05.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546843,540192,TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:05.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546844,540192,TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:06.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546845,540192,TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:06.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546846,540192,TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:06.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546847,540192,TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:06.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546848,540192,TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:06.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546849,540192,TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:06.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546850,540192,TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:06.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546851,540192,TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:06.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546852,540192,TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:07.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546853,540192,TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:07.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546854,540192,TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:07.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546855,540192,TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:07.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546856,540192,TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:07.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546857,540192,TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:07.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546858,540192,TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:07.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546859,540192,TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:07.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546860,540192,TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:07.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546861,540192,TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:08.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546862,540192,TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:08.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546863,540192,TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:08.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546864,540192,TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:08.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540193,TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100,(select c_year_id from c_year where FiscalYear='2024'),TO_TIMESTAMP('2024-12-31','YYYY-MM-DD'),'Y','Dez-24',12,'S','N',TO_TIMESTAMP('2024-12-01','YYYY-MM-DD'),TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:08.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Period_ID=540193 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2021-02-02T09:00:08.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546865,540193,TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:08.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546866,540193,TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:08.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546867,540193,TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:08.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546868,540193,TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:09.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546869,540193,TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:09.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546870,540193,TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:09.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546871,540193,TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:09.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546872,540193,TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:09.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546873,540193,TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:09.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546874,540193,TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:09.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546875,540193,TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:09.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546876,540193,TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:09.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546877,540193,TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:09.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546878,540193,TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:10.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546879,540193,TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:10.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546880,540193,TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:10.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546881,540193,TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:10.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546882,540193,TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:10.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546883,540193,TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:10.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546884,540193,TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:10.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546885,540193,TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:10.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546886,540193,TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:10.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546887,540193,TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:11.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546888,540193,TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:11.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546889,540193,TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:11.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546890,540193,TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:11.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546891,540193,TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:11.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546892,540193,TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:11.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546893,540193,TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:11.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546894,540193,TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:11.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546895,540193,TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:11.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546896,540193,TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:11.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546897,540193,TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:12.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546898,540193,TO_TIMESTAMP('2021-02-02 11:00:12','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:12.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546899,540193,TO_TIMESTAMP('2021-02-02 11:00:12','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:12.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546900,540193,TO_TIMESTAMP('2021-02-02 11:00:12','YYYY-MM-DD HH24:MI:SS'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-02T09:00:12.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,546901,540193,TO_TIMESTAMP('2021-02-02 11:00:12','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2021-02-02 11:00:12','YYYY-MM-DD HH24:MI:SS'),100)
;

EXCEPTION WHEN unique_violation THEN
RAISE NOTICE 'At least one period of 2024 already exists';

end $$;
