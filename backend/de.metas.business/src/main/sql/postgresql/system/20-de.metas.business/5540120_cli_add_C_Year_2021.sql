
CREATE OR REPLACE FUNCTION public.add_year_2021_if_not_exists()
    RETURNS VOID
    LANGUAGE 'plpgsql'
    VOLATILE 
AS $BODY$
DECLARE
    v_year_exists BOOLEAN;
BEGIN

    select exists(select 1 from C_Year where FiscalYear='2021' and IsActive='Y' and C_Calendar_ID=1000000) INTO v_year_exists;
    IF (v_year_exists) THEN
        RAISE NOTICE 'There already exists a C_Year "2021" for C_Calendar_ID=1000000; Nothing to do';
        RETURN;
    END IF;

-- 2020-01-01T18:58:28.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Year (AD_Client_ID,AD_Org_ID,C_Calendar_ID,Created,CreatedBy,C_Year_ID,FiscalYear,IsActive,Processing,Updated,UpdatedBy) VALUES (1000000,0,1000000,TO_TIMESTAMP('2020-01-01 19:58:28','YYYY-MM-DD HH24:MI:SS'),100,540015,'2021','Y','N',TO_TIMESTAMP('2020-01-01 19:58:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:31.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540146,TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100,540015,TO_TIMESTAMP('2021-01-31','YYYY-MM-DD'),'Y','Jan-21',1,'S','N',TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:31.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540146 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:58:31.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545138,540146,TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:31.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545139,540146,TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:31.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545140,540146,TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:31.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545141,540146,TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:31.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545142,540146,TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:31.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545143,540146,TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:31.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545144,540146,TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:31.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545145,540146,TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:31.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545146,540146,TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:31.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545147,540146,TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:32.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545148,540146,TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:32.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545149,540146,TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:32.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545150,540146,TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:32.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545151,540146,TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:32.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545152,540146,TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:32.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545153,540146,TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:32.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545154,540146,TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:32.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545155,540146,TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:32.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545156,540146,TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:32.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545157,540146,TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:32.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545158,540146,TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:33.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545159,540146,TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:33.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545160,540146,TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:33.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545161,540146,TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:33.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545162,540146,TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:33.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545163,540146,TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:33.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545164,540146,TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:33.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545165,540146,TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:33.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545166,540146,TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:33.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545167,540146,TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:33.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545168,540146,TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:33.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545169,540146,TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:33.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545170,540146,TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:33.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545171,540146,TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:34.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545172,540146,TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:34.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545173,540146,TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:34.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540147,TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100,540015,TO_TIMESTAMP('2021-02-28','YYYY-MM-DD'),'Y','Feb-21',2,'S','N',TO_TIMESTAMP('2021-02-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:34.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540147 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:58:34.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545174,540147,TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:34.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545175,540147,TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:34.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545176,540147,TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:34.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545177,540147,TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:34.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545178,540147,TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:34.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545179,540147,TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:34.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545180,540147,TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:34.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545181,540147,TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:34.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545182,540147,TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:34.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545183,540147,TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:35.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545184,540147,TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:35.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545185,540147,TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:35.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545186,540147,TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:35.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545187,540147,TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:35.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545188,540147,TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:35.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545189,540147,TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:35.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545190,540147,TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:35.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545191,540147,TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:35.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545192,540147,TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:35.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545193,540147,TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:35.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545194,540147,TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:35.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545195,540147,TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:36.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545196,540147,TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:36.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545197,540147,TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:36.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545198,540147,TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:36.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545199,540147,TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:36.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545200,540147,TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:36.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545201,540147,TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:36.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545202,540147,TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:36.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545203,540147,TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:36.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545204,540147,TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:36.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545205,540147,TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:36.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545206,540147,TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:36.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545207,540147,TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:37.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545208,540147,TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:37.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545209,540147,TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:37.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540148,TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100,540015,TO_TIMESTAMP('2021-03-31','YYYY-MM-DD'),'Y','Mär-21',3,'S','N',TO_TIMESTAMP('2021-03-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:37.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540148 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:58:37.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545210,540148,TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:37.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545211,540148,TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:37.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545212,540148,TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:37.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545213,540148,TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:37.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545214,540148,TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:37.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545215,540148,TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:37.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545216,540148,TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:37.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545217,540148,TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:37.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545218,540148,TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:38.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545219,540148,TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:38.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545220,540148,TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:38.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545221,540148,TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:38.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545222,540148,TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:38.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545223,540148,TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:38.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545224,540148,TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:38.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545225,540148,TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:38.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545226,540148,TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:38.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545227,540148,TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:38.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545228,540148,TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:38.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545229,540148,TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:38.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545230,540148,TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:39.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545231,540148,TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:39.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545232,540148,TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:39.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545233,540148,TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:39.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545234,540148,TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:39.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545235,540148,TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:39.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545236,540148,TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:39.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545237,540148,TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:39.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545238,540148,TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:39.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545239,540148,TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:39.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545240,540148,TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:39.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545241,540148,TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:39.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545242,540148,TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:40.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545243,540148,TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:40.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545244,540148,TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:40.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545245,540148,TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:40.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540149,TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100,540015,TO_TIMESTAMP('2021-04-30','YYYY-MM-DD'),'Y','Apr-21',4,'S','N',TO_TIMESTAMP('2021-04-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:40.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540149 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:58:40.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545246,540149,TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:40.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545247,540149,TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:40.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545248,540149,TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:40.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545249,540149,TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:40.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545250,540149,TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:40.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545251,540149,TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:40.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545252,540149,TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:40.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545253,540149,TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:41.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545254,540149,TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:41.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545255,540149,TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:41.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545256,540149,TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:41.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545257,540149,TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:41.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545258,540149,TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:41.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545259,540149,TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:41.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545260,540149,TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:41.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545261,540149,TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:41.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545262,540149,TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:41.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545263,540149,TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:41.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545264,540149,TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:42.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545265,540149,TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:42.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545266,540149,TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:42.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545267,540149,TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:42.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545268,540149,TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:42.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545269,540149,TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:42.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545270,540149,TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:42.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545271,540149,TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:42.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545272,540149,TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:42.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545273,540149,TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:42.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545274,540149,TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:42.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545275,540149,TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:42.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545276,540149,TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:42.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545277,540149,TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:43.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545278,540149,TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:43.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545279,540149,TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:43.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545280,540149,TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:43.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545281,540149,TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:43.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540150,TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100,540015,TO_TIMESTAMP('2021-05-31','YYYY-MM-DD'),'Y','Mai-21',5,'S','N',TO_TIMESTAMP('2021-05-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:43.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540150 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:58:43.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545282,540150,TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:43.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545283,540150,TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:43.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545284,540150,TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:43.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545285,540150,TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:43.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545286,540150,TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:43.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545287,540150,TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:43.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545288,540150,TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:44.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545289,540150,TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:44.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545290,540150,TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:44.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545291,540150,TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:44.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545292,540150,TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:44.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545293,540150,TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:44.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545294,540150,TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:44.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545295,540150,TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:44.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545296,540150,TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:44.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545297,540150,TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:44.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545298,540150,TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:44.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545299,540150,TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:44.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545300,540150,TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:45.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545301,540150,TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:45.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545302,540150,TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:45.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545303,540150,TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:45.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545304,540150,TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:45.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545305,540150,TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:45.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545306,540150,TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:45.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545307,540150,TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:45.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545308,540150,TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:45.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545309,540150,TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:45.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545310,540150,TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:45.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545311,540150,TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:45.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545312,540150,TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:45.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545313,540150,TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:46.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545314,540150,TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:46.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545315,540150,TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:46.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545316,540150,TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:46.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545317,540150,TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:46.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540151,TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100,540015,TO_TIMESTAMP('2021-06-30','YYYY-MM-DD'),'Y','Jun-21',6,'S','N',TO_TIMESTAMP('2021-06-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:46.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540151 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:58:46.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545318,540151,TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:46.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545319,540151,TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:46.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545320,540151,TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:46.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545321,540151,TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:46.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545322,540151,TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:46.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545323,540151,TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:46.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545324,540151,TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:47.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545325,540151,TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:47.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545326,540151,TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:47.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545327,540151,TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:47.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545328,540151,TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:47.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545329,540151,TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:47.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545330,540151,TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:47.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545331,540151,TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:47.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545332,540151,TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:47.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545333,540151,TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:47.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545334,540151,TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:47.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545335,540151,TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545336,540151,TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545337,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545338,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545339,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545340,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545341,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545342,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545343,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545344,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545345,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545346,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545347,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545348,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:48.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545349,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:49.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545350,540151,TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:49.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545351,540151,TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:49.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545352,540151,TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:49.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545353,540151,TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:49.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540152,TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100,540015,TO_TIMESTAMP('2021-07-31','YYYY-MM-DD'),'Y','Jul-21',7,'S','N',TO_TIMESTAMP('2021-07-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:49.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540152 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:58:49.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545354,540152,TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:49.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545355,540152,TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:49.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545356,540152,TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:49.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545357,540152,TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:49.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545358,540152,TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:49.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545359,540152,TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:49.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545360,540152,TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:50.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545361,540152,TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:50.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545362,540152,TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:50.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545363,540152,TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:50.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545364,540152,TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:50.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545365,540152,TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:50.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545366,540152,TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:50.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545367,540152,TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:50.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545368,540152,TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:50.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545369,540152,TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:50.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545370,540152,TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:50.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545371,540152,TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:50.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545372,540152,TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:50.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545373,540152,TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:51.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545374,540152,TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:51.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545375,540152,TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:51.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545376,540152,TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:51.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545377,540152,TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:51.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545378,540152,TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:51.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545379,540152,TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:51.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545380,540152,TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:51.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545381,540152,TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:51.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545382,540152,TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:51.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545383,540152,TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:51.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545384,540152,TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:51.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545385,540152,TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:52.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545386,540152,TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:52.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545387,540152,TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:52.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545388,540152,TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:52.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545389,540152,TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:52.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540153,TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100,540015,TO_TIMESTAMP('2021-08-31','YYYY-MM-DD'),'Y','Aug-21',8,'S','N',TO_TIMESTAMP('2021-08-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:52.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540153 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:58:52.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545390,540153,TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:52.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545391,540153,TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:52.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545392,540153,TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:52.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545393,540153,TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:52.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545394,540153,TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:52.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545395,540153,TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:52.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545396,540153,TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:52.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545397,540153,TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:53.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545398,540153,TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:53.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545399,540153,TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:53.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545400,540153,TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:53.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545401,540153,TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:53.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545402,540153,TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:53.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545403,540153,TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:53.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545404,540153,TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:53.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545405,540153,TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:53.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545406,540153,TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:53.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545407,540153,TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:53.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545408,540153,TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:53.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545409,540153,TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:54.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545410,540153,TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:54.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545411,540153,TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:54.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545412,540153,TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:54.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545413,540153,TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:54.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545414,540153,TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:54.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545415,540153,TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:54.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545416,540153,TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:54.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545417,540153,TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:54.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545418,540153,TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:54.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545419,540153,TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:54.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545420,540153,TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:54.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545421,540153,TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:55.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545422,540153,TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:55.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545423,540153,TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:55.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545424,540153,TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:55.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545425,540153,TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:55.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540154,TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100,540015,TO_TIMESTAMP('2021-09-30','YYYY-MM-DD'),'Y','Sep-21',9,'S','N',TO_TIMESTAMP('2021-09-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:55.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540154 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:58:55.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545426,540154,TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:55.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545427,540154,TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:55.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545428,540154,TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:55.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545429,540154,TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:55.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545430,540154,TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:55.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545431,540154,TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:55.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545432,540154,TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:56.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545433,540154,TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:56.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545434,540154,TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:56.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545435,540154,TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:56.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545436,540154,TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:56.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545437,540154,TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:56.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545438,540154,TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:56.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545439,540154,TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:56.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545440,540154,TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:56.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545441,540154,TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:56.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545442,540154,TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:56.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545443,540154,TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:56.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545444,540154,TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:57.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545445,540154,TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:57.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545446,540154,TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:57.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545447,540154,TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:57.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545448,540154,TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:57.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545449,540154,TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:57.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545450,540154,TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:57.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545451,540154,TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:57.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545452,540154,TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:57.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545453,540154,TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:57.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545454,540154,TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:57.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545455,540154,TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:58.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545456,540154,TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:58.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545457,540154,TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:58.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545458,540154,TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:58.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545459,540154,TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:58.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545460,540154,TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:58.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545461,540154,TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:58.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540155,TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,540015,TO_TIMESTAMP('2021-10-31','YYYY-MM-DD'),'Y','Okt-21',10,'S','N',TO_TIMESTAMP('2021-10-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:58.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540155 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:58:58.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545462,540155,TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:58.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545463,540155,TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:58.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545464,540155,TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:58.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545465,540155,TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:58.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545466,540155,TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:59.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545467,540155,TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:59.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545468,540155,TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:59.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545469,540155,TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:59.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545470,540155,TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:59.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545471,540155,TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:59.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545472,540155,TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:59.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545473,540155,TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:59.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545474,540155,TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:59.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545475,540155,TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:59.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545476,540155,TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:59.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545477,540155,TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:58:59.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545478,540155,TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:00.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545479,540155,TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:00.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545480,540155,TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:00.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545481,540155,TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:00.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545482,540155,TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:00.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545483,540155,TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:00.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545484,540155,TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:00.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545485,540155,TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:00.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545486,540155,TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:00.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545487,540155,TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:00.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545488,540155,TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:00.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545489,540155,TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:00.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545490,540155,TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:01.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545491,540155,TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:01.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545492,540155,TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:01.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545493,540155,TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:01.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545494,540155,TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:01.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545495,540155,TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:01.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545496,540155,TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:01.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545497,540155,TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:01.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540156,TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100,540015,TO_TIMESTAMP('2021-11-30','YYYY-MM-DD'),'Y','Nov-21',11,'S','N',TO_TIMESTAMP('2021-11-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:01.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540156 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:59:01.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545498,540156,TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:01.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545499,540156,TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:01.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545500,540156,TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:01.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545501,540156,TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:02.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545502,540156,TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:02.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545503,540156,TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:02.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545504,540156,TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:02.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545505,540156,TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:02.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545506,540156,TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:02.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545507,540156,TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:02.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545508,540156,TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:02.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545509,540156,TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:02.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545510,540156,TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:02.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545511,540156,TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:02.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545512,540156,TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:02.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545513,540156,TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:03.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545514,540156,TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:03.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545515,540156,TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:03.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545516,540156,TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:03.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545517,540156,TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:03.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545518,540156,TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:03.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545519,540156,TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:03.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545520,540156,TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:03.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545521,540156,TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:03.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545522,540156,TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:03.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545523,540156,TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:03.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545524,540156,TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:03.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545525,540156,TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:04.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545526,540156,TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:04.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545527,540156,TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:04.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545528,540156,TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:04.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545529,540156,TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:04.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545530,540156,TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:04.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545531,540156,TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:04.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545532,540156,TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:04.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545533,540156,TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:04.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540157,TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100,540015,TO_TIMESTAMP('2021-12-31','YYYY-MM-DD'),'Y','Dez-21',12,'S','N',TO_TIMESTAMP('2021-12-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:04.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540157 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:59:04.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545534,540157,TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:04.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545535,540157,TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:04.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545536,540157,TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:05.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545537,540157,TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:05.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545538,540157,TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:05.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545539,540157,TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:05.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545540,540157,TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:05.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545541,540157,TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:05.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545542,540157,TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:05.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545543,540157,TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:05.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545544,540157,TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:05.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545545,540157,TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:05.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545546,540157,TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:05.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545547,540157,TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:05.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545548,540157,TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:06.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545549,540157,TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:06.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545550,540157,TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:06.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545551,540157,TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:06.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545552,540157,TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:06.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545553,540157,TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:06.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545554,540157,TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:06.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545555,540157,TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:06.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545556,540157,TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:06.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545557,540157,TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:06.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545558,540157,TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:06.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545559,540157,TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:06.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545560,540157,TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:06.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545561,540157,TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:07.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545562,540157,TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:07.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545563,540157,TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:07.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545564,540157,TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:07.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545565,540157,TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:07.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545566,540157,TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:07.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545567,540157,TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:07.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545568,540157,TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:59:07.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545569,540157,TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:59:07','YYYY-MM-DD HH24:MI:SS'),100)
;

RAISE NOTICE 'successfully created C_Year "2021" for C_Calendar_ID=1000000, with C_Period and C_PeriodControl records';

END;
$BODY$;

select public.add_year_2021_if_not_exists();
