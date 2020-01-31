CREATE OR REPLACE FUNCTION public.add_year_2020_if_not_exists()
    RETURNS VOID
    LANGUAGE 'plpgsql'
    VOLATILE 
AS $BODY$
DECLARE
    v_year_exists BOOLEAN;
BEGIN

    select exists(select 1 from C_Year where FiscalYear='2020' and IsActive='Y' and C_Calendar_ID=1000000) INTO v_year_exists;
    IF (v_year_exists) THEN
        RAISE NOTICE 'There already exists a C_Year "2020" for C_Calendar_ID=1000000; Nothing to do';
        RETURN;
    END IF;

-- 2020-01-01T18:54:40.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Year (AD_Client_ID,AD_Org_ID,C_Calendar_ID,Created,CreatedBy,C_Year_ID,FiscalYear,IsActive,Processing,Updated,UpdatedBy) VALUES (1000000,0,1000000,TO_TIMESTAMP('2020-01-01 19:54:39','YYYY-MM-DD HH24:MI:SS'),100,540014,'2020','Y','N',TO_TIMESTAMP('2020-01-01 19:54:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:45.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540134,TO_TIMESTAMP('2020-01-01 19:54:45','YYYY-MM-DD HH24:MI:SS'),100,540014,TO_TIMESTAMP('2020-01-31','YYYY-MM-DD'),'Y','Jan-20',1,'S','N',TO_TIMESTAMP('2020-01-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:54:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:45.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540134 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:54:45.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544706,540134,TO_TIMESTAMP('2020-01-01 19:54:45','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:46.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544707,540134,TO_TIMESTAMP('2020-01-01 19:54:45','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:46.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544708,540134,TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:46.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544709,540134,TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:46.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544710,540134,TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:46.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544711,540134,TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:46.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544712,540134,TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:46.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544713,540134,TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:46.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544714,540134,TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:46.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544715,540134,TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:46.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544716,540134,TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:46.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544717,540134,TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:46.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544718,540134,TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:47.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544719,540134,TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:47.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544720,540134,TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:47.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544721,540134,TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:47.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544722,540134,TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:47.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544723,540134,TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:47.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544724,540134,TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:47.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544725,540134,TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:47.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544726,540134,TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:47.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544727,540134,TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:47.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544728,540134,TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:47.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544729,540134,TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:48.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544730,540134,TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:48.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544731,540134,TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:48.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544732,540134,TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:48.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544733,540134,TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:48.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544734,540134,TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:48.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544735,540134,TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:48.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544736,540134,TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:48.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544737,540134,TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:48.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544738,540134,TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:48.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544739,540134,TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:48.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544740,540134,TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:48.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544741,540134,TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:49.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540135,TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100,540014,TO_TIMESTAMP('2020-02-29','YYYY-MM-DD'),'Y','Feb-20',2,'S','N',TO_TIMESTAMP('2020-02-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:49.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540135 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:54:49.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544742,540135,TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:49.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544743,540135,TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:49.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544744,540135,TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:49.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544745,540135,TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:49.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544746,540135,TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:49.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544747,540135,TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:49.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544748,540135,TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:49.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544749,540135,TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:49.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544750,540135,TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:49.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544751,540135,TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:50.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544752,540135,TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:50.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544753,540135,TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:50.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544754,540135,TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:50.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544755,540135,TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:50.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544756,540135,TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:50.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544757,540135,TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:50.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544758,540135,TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:50.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544759,540135,TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:50.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544760,540135,TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:50.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544761,540135,TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:50.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544762,540135,TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:50.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544763,540135,TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:50.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544764,540135,TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:51.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544765,540135,TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:51.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544766,540135,TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:51.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544767,540135,TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:51.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544768,540135,TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:51.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544769,540135,TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:51.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544770,540135,TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:51.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544771,540135,TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:51.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544772,540135,TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:51.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544773,540135,TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:51.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544774,540135,TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:52.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544775,540135,TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:52.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544776,540135,TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:52.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544777,540135,TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:52.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540136,TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100,540014,TO_TIMESTAMP('2020-03-31','YYYY-MM-DD'),'Y','Mär-20',3,'S','N',TO_TIMESTAMP('2020-03-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:52.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540136 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:54:52.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544778,540136,TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:52.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544779,540136,TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:52.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544780,540136,TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:52.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544781,540136,TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:52.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544782,540136,TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:52.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544783,540136,TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:52.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544784,540136,TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:52.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544785,540136,TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:53.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544786,540136,TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:53.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544787,540136,TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:53.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544788,540136,TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:53.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544789,540136,TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:53.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544790,540136,TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:53.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544791,540136,TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:53.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544792,540136,TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:53.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544793,540136,TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:53.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544794,540136,TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:53.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544795,540136,TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:53.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544796,540136,TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:53.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544797,540136,TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:53.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544798,540136,TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:54.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544799,540136,TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:54.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544800,540136,TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:54.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544801,540136,TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:54.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544802,540136,TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:54.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544803,540136,TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:54.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544804,540136,TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:54.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544805,540136,TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:54.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544806,540136,TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:54.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544807,540136,TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:54.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544808,540136,TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:54.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544809,540136,TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:54.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544810,540136,TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:55.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544811,540136,TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:55.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544812,540136,TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:55.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544813,540136,TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:55.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540137,TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100,540014,TO_TIMESTAMP('2020-04-30','YYYY-MM-DD'),'Y','Apr-20',4,'S','N',TO_TIMESTAMP('2020-04-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:55.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540137 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:54:55.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544814,540137,TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:55.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544815,540137,TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:55.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544816,540137,TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:55.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544817,540137,TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:55.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544818,540137,TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:55.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544819,540137,TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:55.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544820,540137,TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:55.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544821,540137,TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:56.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544822,540137,TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:56.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544823,540137,TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:56.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544824,540137,TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:56.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544825,540137,TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:56.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544826,540137,TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:56.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544827,540137,TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:56.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544828,540137,TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:56.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544829,540137,TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:56.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544830,540137,TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:56.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544831,540137,TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:56.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544832,540137,TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:56.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544833,540137,TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:57.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544834,540137,TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:57.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544835,540137,TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:57.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544836,540137,TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:57.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544837,540137,TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:57.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544838,540137,TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:57.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544839,540137,TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:57.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544840,540137,TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:57.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544841,540137,TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:57.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544842,540137,TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:57.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544843,540137,TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:57.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544844,540137,TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:57.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544845,540137,TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:57.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544846,540137,TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:58.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544847,540137,TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:58.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544848,540137,TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:58.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544849,540137,TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:58.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540138,TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100,540014,TO_TIMESTAMP('2020-05-31','YYYY-MM-DD'),'Y','Mai-20',5,'S','N',TO_TIMESTAMP('2020-05-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:58.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540138 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:54:58.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544850,540138,TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:58.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544851,540138,TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:58.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544852,540138,TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:58.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544853,540138,TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:58.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544854,540138,TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:58.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544855,540138,TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:59.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544856,540138,TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:59.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544857,540138,TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:59.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544858,540138,TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:59.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544859,540138,TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:59.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544860,540138,TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:59.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544861,540138,TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:59.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544862,540138,TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:59.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544863,540138,TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:59.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544864,540138,TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:59.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544865,540138,TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:59.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544866,540138,TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:54:59.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544867,540138,TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:00.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544868,540138,TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:00.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544869,540138,TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:00.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544870,540138,TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:00.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544871,540138,TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:00.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544872,540138,TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:00.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544873,540138,TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:00.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544874,540138,TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:00.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544875,540138,TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:00.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544876,540138,TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:00.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544877,540138,TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:00.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544878,540138,TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:01.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544879,540138,TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:01.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544880,540138,TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:01.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544881,540138,TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:01.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544882,540138,TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:01.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544883,540138,TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:01.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544884,540138,TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:01.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544885,540138,TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:01.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540139,TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100,540014,TO_TIMESTAMP('2020-06-30','YYYY-MM-DD'),'Y','Jun-20',6,'S','N',TO_TIMESTAMP('2020-06-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:01.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540139 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:55:01.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544886,540139,TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:01.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544887,540139,TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:01.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544888,540139,TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:01.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544889,540139,TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:01.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544890,540139,TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:02.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544891,540139,TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:02.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544892,540139,TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:02.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544893,540139,TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:02.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544894,540139,TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:02.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544895,540139,TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:02.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544896,540139,TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:02.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544897,540139,TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:02.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544898,540139,TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:02.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544899,540139,TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:02.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544900,540139,TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:02.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544901,540139,TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:03.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544902,540139,TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:03.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544903,540139,TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:03.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544904,540139,TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:03.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544905,540139,TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:03.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544906,540139,TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:03.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544907,540139,TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:03.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544908,540139,TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:03.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544909,540139,TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:03.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544910,540139,TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:03.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544911,540139,TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:03.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544912,540139,TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:03.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544913,540139,TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:04.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544914,540139,TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:04.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544915,540139,TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:04.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544916,540139,TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:04.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544917,540139,TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:04.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544918,540139,TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:04.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544919,540139,TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:04.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544920,540139,TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:04.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544921,540139,TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:04.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540140,TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100,540014,TO_TIMESTAMP('2020-07-31','YYYY-MM-DD'),'Y','Jul-20',7,'S','N',TO_TIMESTAMP('2020-07-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:04.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540140 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:55:04.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544922,540140,TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:04.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544923,540140,TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:04.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544924,540140,TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:04.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544925,540140,TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:05.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544926,540140,TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:05.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544927,540140,TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:05.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544928,540140,TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:05.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544929,540140,TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:05.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544930,540140,TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:05.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544931,540140,TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:05.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544932,540140,TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:05.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544933,540140,TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:05.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544934,540140,TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:05.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544935,540140,TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:05.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544936,540140,TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:05.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544937,540140,TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:05.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544938,540140,TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:06.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544939,540140,TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:06.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544940,540140,TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:06.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544941,540140,TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:06.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544942,540140,TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:06.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544943,540140,TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:06.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544944,540140,TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:06.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544945,540140,TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:06.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544946,540140,TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:06.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544947,540140,TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:06.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544948,540140,TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:06.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544949,540140,TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:07.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544950,540140,TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:07.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544951,540140,TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:07.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544952,540140,TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:07.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544953,540140,TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:07.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544954,540140,TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:07.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544955,540140,TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:07.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544956,540140,TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:07.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544957,540140,TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:07.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540141,TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100,540014,TO_TIMESTAMP('2020-08-31','YYYY-MM-DD'),'Y','Aug-20',8,'S','N',TO_TIMESTAMP('2020-08-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:07.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540141 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:55:07.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544958,540141,TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:07.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544959,540141,TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:07.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544960,540141,TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:08.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544961,540141,TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:08.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544962,540141,TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:08.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544963,540141,TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:08.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544964,540141,TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:08.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544965,540141,TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:08.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544966,540141,TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:08.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544967,540141,TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:08.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544968,540141,TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:08.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544969,540141,TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:08.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544970,540141,TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:08.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544971,540141,TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:08.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544972,540141,TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:09.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544973,540141,TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:09.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544974,540141,TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:09.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544975,540141,TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:09.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544976,540141,TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:09.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544977,540141,TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:09.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544978,540141,TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:09.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544979,540141,TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:09.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544980,540141,TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:09.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544981,540141,TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:09.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544982,540141,TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:09.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544983,540141,TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:09.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544984,540141,TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:10.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544985,540141,TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:10.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544986,540141,TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:10.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544987,540141,TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:10.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544988,540141,TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:10.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544989,540141,TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:10.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544990,540141,TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:10.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544991,540141,TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:10.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544992,540141,TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:10.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544993,540141,TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:10.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540142,TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100,540014,TO_TIMESTAMP('2020-09-30','YYYY-MM-DD'),'Y','Sep-20',9,'S','N',TO_TIMESTAMP('2020-09-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:10.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540142 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:55:10.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544994,540142,TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:10.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544995,540142,TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:11.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544996,540142,TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:11.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544997,540142,TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:11.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544998,540142,TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:11.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,544999,540142,TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:11.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545000,540142,TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:11.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545001,540142,TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:11.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545002,540142,TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:11.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545003,540142,TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:11.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545004,540142,TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:11.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545005,540142,TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:11.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545006,540142,TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:11.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545007,540142,TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:11.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545008,540142,TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:12.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545009,540142,TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:12.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545010,540142,TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:12.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545011,540142,TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:12.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545012,540142,TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:12.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545013,540142,TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:12.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545014,540142,TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:12.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545015,540142,TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:12.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545016,540142,TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:12.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545017,540142,TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:12.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545018,540142,TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:13.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545019,540142,TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:13.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545020,540142,TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:13.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545021,540142,TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:13.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545022,540142,TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:13.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545023,540142,TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:13.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545024,540142,TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:13.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545025,540142,TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:13.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545026,540142,TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:13.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545027,540142,TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:13.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545028,540142,TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:14.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545029,540142,TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:14.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540143,TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100,540014,TO_TIMESTAMP('2020-10-31','YYYY-MM-DD'),'Y','Okt-20',10,'S','N',TO_TIMESTAMP('2020-10-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:14.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540143 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:55:14.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545030,540143,TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:14.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545031,540143,TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:14.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545032,540143,TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:14.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545033,540143,TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:14.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545034,540143,TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:14.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545035,540143,TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:14.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545036,540143,TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:14.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545037,540143,TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:14.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545038,540143,TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:14.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545039,540143,TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:15.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545040,540143,TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:15.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545041,540143,TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:15.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545042,540143,TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:15.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545043,540143,TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:15.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545044,540143,TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:15.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545045,540143,TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:15.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545046,540143,TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:15.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545047,540143,TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:15.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545048,540143,TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:15.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545049,540143,TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:15.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545050,540143,TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:15.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545051,540143,TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:15.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545052,540143,TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:16.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545053,540143,TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:16.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545054,540143,TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:16.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545055,540143,TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:16.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545056,540143,TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:16.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545057,540143,TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:16.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545058,540143,TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:16.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545059,540143,TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:16.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545060,540143,TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:16.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545061,540143,TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:16.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545062,540143,TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:16.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545063,540143,TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:16.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545064,540143,TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:17.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545065,540143,TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:17.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540144,TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100,540014,TO_TIMESTAMP('2020-11-30','YYYY-MM-DD'),'Y','Nov-20',11,'S','N',TO_TIMESTAMP('2020-11-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:17.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540144 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:55:17.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545066,540144,TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:17.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545067,540144,TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:17.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545068,540144,TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:17.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545069,540144,TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:17.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545070,540144,TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:17.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545071,540144,TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:17.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545072,540144,TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:17.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545073,540144,TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:17.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545074,540144,TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:18.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545075,540144,TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:18.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545076,540144,TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:18.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545077,540144,TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:18.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545078,540144,TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:18.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545079,540144,TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:18.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545080,540144,TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:18.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545081,540144,TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:18.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545082,540144,TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:18.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545083,540144,TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:18.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545084,540144,TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:18.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545085,540144,TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:18.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545086,540144,TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:18.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545087,540144,TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:19.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545088,540144,TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:19.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545089,540144,TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:19.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545090,540144,TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:19.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545091,540144,TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:19.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545092,540144,TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:19.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545093,540144,TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:19.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545094,540144,TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:19.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545095,540144,TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:19.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545096,540144,TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:19.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545097,540144,TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:19.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545098,540144,TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:19.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545099,540144,TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:20.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545100,540144,TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:20.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545101,540144,TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:20.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540145,TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100,540014,TO_TIMESTAMP('2020-12-31','YYYY-MM-DD'),'Y','Dez-20',12,'S','N',TO_TIMESTAMP('2020-12-01','YYYY-MM-DD'),TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:20.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540145 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2020-01-01T18:55:20.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545102,540145,TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:20.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545103,540145,TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:20.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545104,540145,TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:20.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545105,540145,TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:20.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545106,540145,TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:20.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545107,540145,TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:20.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545108,540145,TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:20.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545109,540145,TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:21.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545110,540145,TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:21.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545111,540145,TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:21.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545112,540145,TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:21.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545113,540145,TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:21.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545114,540145,TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:21.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545115,540145,TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:21.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545116,540145,TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:21.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545117,540145,TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:21.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545118,540145,TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:21.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545119,540145,TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:21.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545120,540145,TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:21.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545121,540145,TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:22.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545122,540145,TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:22.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545123,540145,TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:22.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545124,540145,TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:22.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545125,540145,TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:22.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545126,540145,TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:22.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545127,540145,TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:22.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545128,540145,TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:22.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545129,540145,TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:22.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545130,540145,TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:22.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545131,540145,TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:22.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545132,540145,TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:22.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545133,540145,TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:23.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545134,540145,TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:23.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545135,540145,TO_TIMESTAMP('2020-01-01 19:55:23','YYYY-MM-DD HH24:MI:SS'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:23.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545136,540145,TO_TIMESTAMP('2020-01-01 19:55:23','YYYY-MM-DD HH24:MI:SS'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-01T18:55:23.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,545137,540145,TO_TIMESTAMP('2020-01-01 19:55:23','YYYY-MM-DD HH24:MI:SS'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2020-01-01 19:55:23','YYYY-MM-DD HH24:MI:SS'),100)
;

RAISE NOTICE 'successfully created C_Year "2020" for C_Calendar_ID=1000000, with C_Period and C_PeriodControl records';

END;
$BODY$;

select public.add_year_2020_if_not_exists();