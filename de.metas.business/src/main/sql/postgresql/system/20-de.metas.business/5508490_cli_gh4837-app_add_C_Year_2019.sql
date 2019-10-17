CREATE OR REPLACE FUNCTION public.add_year_2019_if_not_exists()
    RETURNS VOID
    LANGUAGE 'plpgsql'
    VOLATILE 
AS $BODY$
DECLARE
    v_year_exists BOOLEAN;
BEGIN

    select exists(select 1 from C_Year where FiscalYear='2019' and IsActive='Y' and C_Calendar_ID=1000000) INTO v_year_exists;
    IF (v_year_exists) THEN
        RAISE NOTICE 'There already exists a C_Year "2019" for C_Calendar_ID=1000000; Nothing to do';
        RETURN;
    END IF;

-- 2018-12-17T12:19:45.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Year (CreatedBy,FiscalYear,AD_Client_ID,IsActive,Created,C_Calendar_ID,Processing,UpdatedBy,Updated,C_Year_ID,AD_Org_ID) VALUES (100,'2019',1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:45','YYYY-MM-DD HH24:MI:SS'),1000000,'N',100,TO_TIMESTAMP('2018-12-17 12:19:45','YYYY-MM-DD HH24:MI:SS'),540006,0)
;

-- 2018-12-17T12:19:56.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (Processing,AD_Client_ID,IsActive,Created,CreatedBy,PeriodNo,PeriodType,C_Year_ID,Updated,UpdatedBy,EndDate,StartDate,C_Period_ID,AD_Org_ID,Name) VALUES ('N',1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),100,1,'S',540006,TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-01-31','YYYY-MM-DD'),TO_TIMESTAMP('2019-01-01','YYYY-MM-DD'),540038,0,'Jan-19')
;

-- 2018-12-17T12:19:56.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540038 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2018-12-17T12:19:56.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AVI',100,541190,540038,0)
;

-- 2018-12-17T12:19:56.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),'N','N','N','HRP',100,541191,540038,0)
;

-- 2018-12-17T12:19:56.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DOO',100,541192,540038,0)
;

-- 2018-12-17T12:19:56.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MCC',100,541193,540038,0)
;

-- 2018-12-17T12:19:56.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AEI',100,541194,540038,0)
;

-- 2018-12-17T12:19:56.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLJ',100,541195,540038,0)
;

-- 2018-12-17T12:19:56.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARC',100,541196,540038,0)
;

-- 2018-12-17T12:19:56.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXI',100,541197,540038,0)
;

-- 2018-12-17T12:19:56.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXP',100,541198,540038,0)
;

-- 2018-12-17T12:19:57.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:56','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POR',100,541199,540038,0)
;

-- 2018-12-17T12:19:57.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMB',100,541200,540038,0)
;

-- 2018-12-17T12:19:57.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMC',100,541201,540038,0)
;

-- 2018-12-17T12:19:57.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMM',100,541202,540038,0)
;

-- 2018-12-17T12:19:57.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMI',100,541203,540038,0)
;

-- 2018-12-17T12:19:57.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMP',100,541204,540038,0)
;

-- 2018-12-17T12:19:57.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),'N','N','N','PJI',100,541205,540038,0)
;

-- 2018-12-17T12:19:57.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLD',100,541206,540038,0)
;

-- 2018-12-17T12:19:57.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARF',100,541207,540038,0)
;

-- 2018-12-17T12:19:57.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APC',100,541208,540038,0)
;

-- 2018-12-17T12:19:57.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMA',100,541209,540038,0)
;

-- 2018-12-17T12:19:57.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMS',100,541210,540038,0)
;

-- 2018-12-17T12:19:57.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POO',100,541211,540038,0)
;

-- 2018-12-17T12:19:58.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:57','YYYY-MM-DD HH24:MI:SS'),'N','N','N','SOO',100,541212,540038,0)
;

-- 2018-12-17T12:19:58.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARI',100,541213,540038,0)
;

-- 2018-12-17T12:19:58.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMR',100,541214,540038,0)
;

-- 2018-12-17T12:19:58.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CON',100,541215,540038,0)
;

-- 2018-12-17T12:19:58.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MST',100,541216,540038,0)
;

-- 2018-12-17T12:19:58.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),'N','N','N','API',100,541217,540038,0)
;

-- 2018-12-17T12:19:58.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARR',100,541218,540038,0)
;

-- 2018-12-17T12:19:58.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APP',100,541219,540038,0)
;

-- 2018-12-17T12:19:58.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MQO',100,541220,540038,0)
;

-- 2018-12-17T12:19:58.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOP',100,541221,540038,0)
;

-- 2018-12-17T12:19:58.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOF',100,541222,540038,0)
;

-- 2018-12-17T12:19:58.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DUN',100,541223,540038,0)
;

-- 2018-12-17T12:19:58.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (Processing,AD_Client_ID,IsActive,Created,CreatedBy,PeriodNo,PeriodType,C_Year_ID,Updated,UpdatedBy,EndDate,StartDate,C_Period_ID,AD_Org_ID,Name) VALUES ('N',1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),100,2,'S',540006,TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-02-28','YYYY-MM-DD'),TO_TIMESTAMP('2019-02-01','YYYY-MM-DD'),540039,0,'Feb-19')
;

-- 2018-12-17T12:19:58.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540039 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2018-12-17T12:19:59.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:58','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AVI',100,541224,540039,0)
;

-- 2018-12-17T12:19:59.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','HRP',100,541225,540039,0)
;

-- 2018-12-17T12:19:59.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DOO',100,541226,540039,0)
;

-- 2018-12-17T12:19:59.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MCC',100,541227,540039,0)
;

-- 2018-12-17T12:19:59.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AEI',100,541228,540039,0)
;

-- 2018-12-17T12:19:59.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLJ',100,541229,540039,0)
;

-- 2018-12-17T12:19:59.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARC',100,541230,540039,0)
;

-- 2018-12-17T12:19:59.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXI',100,541231,540039,0)
;

-- 2018-12-17T12:19:59.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXP',100,541232,540039,0)
;

-- 2018-12-17T12:19:59.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POR',100,541233,540039,0)
;

-- 2018-12-17T12:19:59.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMB',100,541234,540039,0)
;

-- 2018-12-17T12:19:59.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMC',100,541235,540039,0)
;

-- 2018-12-17T12:19:59.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMM',100,541236,540039,0)
;

-- 2018-12-17T12:19:59.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMI',100,541237,540039,0)
;

-- 2018-12-17T12:20:00.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:19:59','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMP',100,541238,540039,0)
;

-- 2018-12-17T12:20:00.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),'N','N','N','PJI',100,541239,540039,0)
;

-- 2018-12-17T12:20:00.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLD',100,541240,540039,0)
;

-- 2018-12-17T12:20:00.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARF',100,541241,540039,0)
;

-- 2018-12-17T12:20:00.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APC',100,541242,540039,0)
;

-- 2018-12-17T12:20:00.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMA',100,541243,540039,0)
;

-- 2018-12-17T12:20:00.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMS',100,541244,540039,0)
;

-- 2018-12-17T12:20:00.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POO',100,541245,540039,0)
;

-- 2018-12-17T12:20:00.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),'N','N','N','SOO',100,541246,540039,0)
;

-- 2018-12-17T12:20:00.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARI',100,541247,540039,0)
;

-- 2018-12-17T12:20:00.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMR',100,541248,540039,0)
;

-- 2018-12-17T12:20:00.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CON',100,541249,540039,0)
;

-- 2018-12-17T12:20:00.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MST',100,541250,540039,0)
;

-- 2018-12-17T12:20:01.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:00','YYYY-MM-DD HH24:MI:SS'),'N','N','N','API',100,541251,540039,0)
;

-- 2018-12-17T12:20:01.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARR',100,541252,540039,0)
;

-- 2018-12-17T12:20:01.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APP',100,541253,540039,0)
;

-- 2018-12-17T12:20:01.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MQO',100,541254,540039,0)
;

-- 2018-12-17T12:20:01.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOP',100,541255,540039,0)
;

-- 2018-12-17T12:20:01.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOF',100,541256,540039,0)
;

-- 2018-12-17T12:20:01.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DUN',100,541257,540039,0)
;

-- 2018-12-17T12:20:01.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (Processing,AD_Client_ID,IsActive,Created,CreatedBy,PeriodNo,PeriodType,C_Year_ID,Updated,UpdatedBy,EndDate,StartDate,C_Period_ID,AD_Org_ID,Name) VALUES ('N',1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),100,3,'S',540006,TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-03-31','YYYY-MM-DD'),TO_TIMESTAMP('2019-03-01','YYYY-MM-DD'),540040,0,'Mär-19')
;

-- 2018-12-17T12:20:01.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540040 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2018-12-17T12:20:01.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AVI',100,541258,540040,0)
;

-- 2018-12-17T12:20:01.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),'N','N','N','HRP',100,541259,540040,0)
;

-- 2018-12-17T12:20:01.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DOO',100,541260,540040,0)
;

-- 2018-12-17T12:20:01.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MCC',100,541261,540040,0)
;

-- 2018-12-17T12:20:02.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:01','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AEI',100,541262,540040,0)
;

-- 2018-12-17T12:20:02.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLJ',100,541263,540040,0)
;

-- 2018-12-17T12:20:02.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARC',100,541264,540040,0)
;

-- 2018-12-17T12:20:02.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXI',100,541265,540040,0)
;

-- 2018-12-17T12:20:02.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXP',100,541266,540040,0)
;

-- 2018-12-17T12:20:02.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POR',100,541267,540040,0)
;

-- 2018-12-17T12:20:02.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMB',100,541268,540040,0)
;

-- 2018-12-17T12:20:02.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMC',100,541269,540040,0)
;

-- 2018-12-17T12:20:02.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMM',100,541270,540040,0)
;

-- 2018-12-17T12:20:02.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMI',100,541271,540040,0)
;

-- 2018-12-17T12:20:02.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMP',100,541272,540040,0)
;

-- 2018-12-17T12:20:03.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:02','YYYY-MM-DD HH24:MI:SS'),'N','N','N','PJI',100,541273,540040,0)
;

-- 2018-12-17T12:20:03.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLD',100,541274,540040,0)
;

-- 2018-12-17T12:20:03.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARF',100,541275,540040,0)
;

-- 2018-12-17T12:20:03.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APC',100,541276,540040,0)
;

-- 2018-12-17T12:20:03.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMA',100,541277,540040,0)
;

-- 2018-12-17T12:20:03.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMS',100,541278,540040,0)
;

-- 2018-12-17T12:20:03.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POO',100,541279,540040,0)
;

-- 2018-12-17T12:20:03.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),'N','N','N','SOO',100,541280,540040,0)
;

-- 2018-12-17T12:20:03.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARI',100,541281,540040,0)
;

-- 2018-12-17T12:20:03.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMR',100,541282,540040,0)
;

-- 2018-12-17T12:20:03.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CON',100,541283,540040,0)
;

-- 2018-12-17T12:20:03.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MST',100,541284,540040,0)
;

-- 2018-12-17T12:20:03.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),'N','N','N','API',100,541285,540040,0)
;

-- 2018-12-17T12:20:04.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:03','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARR',100,541286,540040,0)
;

-- 2018-12-17T12:20:04.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APP',100,541287,540040,0)
;

-- 2018-12-17T12:20:04.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MQO',100,541288,540040,0)
;

-- 2018-12-17T12:20:04.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOP',100,541289,540040,0)
;

-- 2018-12-17T12:20:04.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOF',100,541290,540040,0)
;

-- 2018-12-17T12:20:04.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DUN',100,541291,540040,0)
;

-- 2018-12-17T12:20:04.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (Processing,AD_Client_ID,IsActive,Created,CreatedBy,PeriodNo,PeriodType,C_Year_ID,Updated,UpdatedBy,EndDate,StartDate,C_Period_ID,AD_Org_ID,Name) VALUES ('N',1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),100,4,'S',540006,TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-30','YYYY-MM-DD'),TO_TIMESTAMP('2019-04-01','YYYY-MM-DD'),540041,0,'Apr-19')
;

-- 2018-12-17T12:20:04.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540041 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2018-12-17T12:20:04.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AVI',100,541292,540041,0)
;

-- 2018-12-17T12:20:04.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),'N','N','N','HRP',100,541293,540041,0)
;

-- 2018-12-17T12:20:04.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DOO',100,541294,540041,0)
;

-- 2018-12-17T12:20:04.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MCC',100,541295,540041,0)
;

-- 2018-12-17T12:20:04.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AEI',100,541296,540041,0)
;

-- 2018-12-17T12:20:04.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLJ',100,541297,540041,0)
;

-- 2018-12-17T12:20:05.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:04','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARC',100,541298,540041,0)
;

-- 2018-12-17T12:20:05.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXI',100,541299,540041,0)
;

-- 2018-12-17T12:20:05.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXP',100,541300,540041,0)
;

-- 2018-12-17T12:20:05.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POR',100,541301,540041,0)
;

-- 2018-12-17T12:20:05.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMB',100,541302,540041,0)
;

-- 2018-12-17T12:20:05.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMC',100,541303,540041,0)
;

-- 2018-12-17T12:20:05.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMM',100,541304,540041,0)
;

-- 2018-12-17T12:20:05.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMI',100,541305,540041,0)
;

-- 2018-12-17T12:20:05.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMP',100,541306,540041,0)
;

-- 2018-12-17T12:20:05.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','PJI',100,541307,540041,0)
;

-- 2018-12-17T12:20:05.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLD',100,541308,540041,0)
;

-- 2018-12-17T12:20:05.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARF',100,541309,540041,0)
;

-- 2018-12-17T12:20:05.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APC',100,541310,540041,0)
;

-- 2018-12-17T12:20:05.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMA',100,541311,540041,0)
;

-- 2018-12-17T12:20:06.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:05','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMS',100,541312,540041,0)
;

-- 2018-12-17T12:20:06.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POO',100,541313,540041,0)
;

-- 2018-12-17T12:20:06.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),'N','N','N','SOO',100,541314,540041,0)
;

-- 2018-12-17T12:20:06.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARI',100,541315,540041,0)
;

-- 2018-12-17T12:20:06.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMR',100,541316,540041,0)
;

-- 2018-12-17T12:20:06.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CON',100,541317,540041,0)
;

-- 2018-12-17T12:20:06.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MST',100,541318,540041,0)
;

-- 2018-12-17T12:20:06.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),'N','N','N','API',100,541319,540041,0)
;

-- 2018-12-17T12:20:06.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARR',100,541320,540041,0)
;

-- 2018-12-17T12:20:06.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APP',100,541321,540041,0)
;

-- 2018-12-17T12:20:06.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MQO',100,541322,540041,0)
;

-- 2018-12-17T12:20:06.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOP',100,541323,540041,0)
;

-- 2018-12-17T12:20:06.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOF',100,541324,540041,0)
;

-- 2018-12-17T12:20:07.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:06','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DUN',100,541325,540041,0)
;

-- 2018-12-17T12:20:07.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (Processing,AD_Client_ID,IsActive,Created,CreatedBy,PeriodNo,PeriodType,C_Year_ID,Updated,UpdatedBy,EndDate,StartDate,C_Period_ID,AD_Org_ID,Name) VALUES ('N',1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),100,5,'S',540006,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-05-31','YYYY-MM-DD'),TO_TIMESTAMP('2019-05-01','YYYY-MM-DD'),540042,0,'Mai-19')
;

-- 2018-12-17T12:20:07.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540042 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2018-12-17T12:20:07.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AVI',100,541326,540042,0)
;

-- 2018-12-17T12:20:07.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),'N','N','N','HRP',100,541327,540042,0)
;

-- 2018-12-17T12:20:07.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DOO',100,541328,540042,0)
;

-- 2018-12-17T12:20:07.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MCC',100,541329,540042,0)
;

-- 2018-12-17T12:20:07.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AEI',100,541330,540042,0)
;

-- 2018-12-17T12:20:07.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLJ',100,541331,540042,0)
;

-- 2018-12-17T12:20:07.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARC',100,541332,540042,0)
;

-- 2018-12-17T12:20:07.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXI',100,541333,540042,0)
;

-- 2018-12-17T12:20:07.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXP',100,541334,540042,0)
;

-- 2018-12-17T12:20:07.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POR',100,541335,540042,0)
;

-- 2018-12-17T12:20:07.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMB',100,541336,540042,0)
;

-- 2018-12-17T12:20:07.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMC',100,541337,540042,0)
;

-- 2018-12-17T12:20:08.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMM',100,541338,540042,0)
;

-- 2018-12-17T12:20:08.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMI',100,541339,540042,0)
;

-- 2018-12-17T12:20:08.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMP',100,541340,540042,0)
;

-- 2018-12-17T12:20:08.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','PJI',100,541341,540042,0)
;

-- 2018-12-17T12:20:08.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLD',100,541342,540042,0)
;

-- 2018-12-17T12:20:08.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARF',100,541343,540042,0)
;

-- 2018-12-17T12:20:08.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APC',100,541344,540042,0)
;

-- 2018-12-17T12:20:08.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMA',100,541345,540042,0)
;

-- 2018-12-17T12:20:08.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMS',100,541346,540042,0)
;

-- 2018-12-17T12:20:08.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POO',100,541347,540042,0)
;

-- 2018-12-17T12:20:08.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','SOO',100,541348,540042,0)
;

-- 2018-12-17T12:20:08.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARI',100,541349,540042,0)
;

-- 2018-12-17T12:20:08.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMR',100,541350,540042,0)
;

-- 2018-12-17T12:20:08.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CON',100,541351,540042,0)
;

-- 2018-12-17T12:20:09.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:08','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MST',100,541352,540042,0)
;

-- 2018-12-17T12:20:09.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),'N','N','N','API',100,541353,540042,0)
;

-- 2018-12-17T12:20:09.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARR',100,541354,540042,0)
;

-- 2018-12-17T12:20:09.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APP',100,541355,540042,0)
;

-- 2018-12-17T12:20:09.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MQO',100,541356,540042,0)
;

-- 2018-12-17T12:20:09.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOP',100,541357,540042,0)
;

-- 2018-12-17T12:20:09.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOF',100,541358,540042,0)
;

-- 2018-12-17T12:20:09.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DUN',100,541359,540042,0)
;

-- 2018-12-17T12:20:09.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (Processing,AD_Client_ID,IsActive,Created,CreatedBy,PeriodNo,PeriodType,C_Year_ID,Updated,UpdatedBy,EndDate,StartDate,C_Period_ID,AD_Org_ID,Name) VALUES ('N',1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),100,6,'S',540006,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-06-30','YYYY-MM-DD'),TO_TIMESTAMP('2019-06-01','YYYY-MM-DD'),540043,0,'Jun-19')
;

-- 2018-12-17T12:20:09.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540043 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2018-12-17T12:20:09.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AVI',100,541360,540043,0)
;

-- 2018-12-17T12:20:09.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),'N','N','N','HRP',100,541361,540043,0)
;

-- 2018-12-17T12:20:09.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DOO',100,541362,540043,0)
;

-- 2018-12-17T12:20:09.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MCC',100,541363,540043,0)
;

-- 2018-12-17T12:20:09.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AEI',100,541364,540043,0)
;

-- 2018-12-17T12:20:10.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:09','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLJ',100,541365,540043,0)
;

-- 2018-12-17T12:20:10.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARC',100,541366,540043,0)
;

-- 2018-12-17T12:20:10.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXI',100,541367,540043,0)
;

-- 2018-12-17T12:20:10.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXP',100,541368,540043,0)
;

-- 2018-12-17T12:20:10.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POR',100,541369,540043,0)
;

-- 2018-12-17T12:20:10.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMB',100,541370,540043,0)
;

-- 2018-12-17T12:20:10.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMC',100,541371,540043,0)
;

-- 2018-12-17T12:20:10.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMM',100,541372,540043,0)
;

-- 2018-12-17T12:20:10.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMI',100,541373,540043,0)
;

-- 2018-12-17T12:20:10.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMP',100,541374,540043,0)
;

-- 2018-12-17T12:20:10.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','PJI',100,541375,540043,0)
;

-- 2018-12-17T12:20:10.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLD',100,541376,540043,0)
;

-- 2018-12-17T12:20:10.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARF',100,541377,540043,0)
;

-- 2018-12-17T12:20:10.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APC',100,541378,540043,0)
;

-- 2018-12-17T12:20:11.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:10','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMA',100,541379,540043,0)
;

-- 2018-12-17T12:20:11.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMS',100,541380,540043,0)
;

-- 2018-12-17T12:20:11.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POO',100,541381,540043,0)
;

-- 2018-12-17T12:20:11.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),'N','N','N','SOO',100,541382,540043,0)
;

-- 2018-12-17T12:20:11.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARI',100,541383,540043,0)
;

-- 2018-12-17T12:20:11.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMR',100,541384,540043,0)
;

-- 2018-12-17T12:20:11.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CON',100,541385,540043,0)
;

-- 2018-12-17T12:20:11.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MST',100,541386,540043,0)
;

-- 2018-12-17T12:20:11.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),'N','N','N','API',100,541387,540043,0)
;

-- 2018-12-17T12:20:11.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARR',100,541388,540043,0)
;

-- 2018-12-17T12:20:11.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APP',100,541389,540043,0)
;

-- 2018-12-17T12:20:11.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MQO',100,541390,540043,0)
;

-- 2018-12-17T12:20:11.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOP',100,541391,540043,0)
;

-- 2018-12-17T12:20:12.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:11','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOF',100,541392,540043,0)
;

-- 2018-12-17T12:20:12.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DUN',100,541393,540043,0)
;

-- 2018-12-17T12:20:12.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (Processing,AD_Client_ID,IsActive,Created,CreatedBy,PeriodNo,PeriodType,C_Year_ID,Updated,UpdatedBy,EndDate,StartDate,C_Period_ID,AD_Org_ID,Name) VALUES ('N',1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),100,7,'S',540006,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-07-31','YYYY-MM-DD'),TO_TIMESTAMP('2019-07-01','YYYY-MM-DD'),540044,0,'Jul-19')
;

-- 2018-12-17T12:20:12.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540044 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2018-12-17T12:20:12.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AVI',100,541394,540044,0)
;

-- 2018-12-17T12:20:12.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),'N','N','N','HRP',100,541395,540044,0)
;

-- 2018-12-17T12:20:12.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DOO',100,541396,540044,0)
;

-- 2018-12-17T12:20:12.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MCC',100,541397,540044,0)
;

-- 2018-12-17T12:20:12.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AEI',100,541398,540044,0)
;

-- 2018-12-17T12:20:12.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLJ',100,541399,540044,0)
;

-- 2018-12-17T12:20:12.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARC',100,541400,540044,0)
;

-- 2018-12-17T12:20:12.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXI',100,541401,540044,0)
;

-- 2018-12-17T12:20:12.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXP',100,541402,540044,0)
;

-- 2018-12-17T12:20:12.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POR',100,541403,540044,0)
;

-- 2018-12-17T12:20:12.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMB',100,541404,540044,0)
;

-- 2018-12-17T12:20:13.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:12','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMC',100,541405,540044,0)
;

-- 2018-12-17T12:20:13.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMM',100,541406,540044,0)
;

-- 2018-12-17T12:20:13.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMI',100,541407,540044,0)
;

-- 2018-12-17T12:20:13.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMP',100,541408,540044,0)
;

-- 2018-12-17T12:20:13.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),'N','N','N','PJI',100,541409,540044,0)
;

-- 2018-12-17T12:20:13.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLD',100,541410,540044,0)
;

-- 2018-12-17T12:20:13.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARF',100,541411,540044,0)
;

-- 2018-12-17T12:20:13.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APC',100,541412,540044,0)
;

-- 2018-12-17T12:20:13.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMA',100,541413,540044,0)
;

-- 2018-12-17T12:20:13.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMS',100,541414,540044,0)
;

-- 2018-12-17T12:20:13.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POO',100,541415,540044,0)
;

-- 2018-12-17T12:20:13.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),'N','N','N','SOO',100,541416,540044,0)
;

-- 2018-12-17T12:20:13.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARI',100,541417,540044,0)
;

-- 2018-12-17T12:20:13.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:13','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMR',100,541418,540044,0)
;

-- 2018-12-17T12:20:14.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CON',100,541419,540044,0)
;

-- 2018-12-17T12:20:14.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MST',100,541420,540044,0)
;

-- 2018-12-17T12:20:14.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),'N','N','N','API',100,541421,540044,0)
;

-- 2018-12-17T12:20:14.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARR',100,541422,540044,0)
;

-- 2018-12-17T12:20:14.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APP',100,541423,540044,0)
;

-- 2018-12-17T12:20:14.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MQO',100,541424,540044,0)
;

-- 2018-12-17T12:20:14.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOP',100,541425,540044,0)
;

-- 2018-12-17T12:20:14.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOF',100,541426,540044,0)
;

-- 2018-12-17T12:20:14.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DUN',100,541427,540044,0)
;

-- 2018-12-17T12:20:14.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (Processing,AD_Client_ID,IsActive,Created,CreatedBy,PeriodNo,PeriodType,C_Year_ID,Updated,UpdatedBy,EndDate,StartDate,C_Period_ID,AD_Org_ID,Name) VALUES ('N',1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),100,8,'S',540006,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-08-31','YYYY-MM-DD'),TO_TIMESTAMP('2019-08-01','YYYY-MM-DD'),540045,0,'Aug-19')
;

-- 2018-12-17T12:20:14.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540045 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2018-12-17T12:20:14.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AVI',100,541428,540045,0)
;

-- 2018-12-17T12:20:14.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),'N','N','N','HRP',100,541429,540045,0)
;

-- 2018-12-17T12:20:14.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DOO',100,541430,540045,0)
;

-- 2018-12-17T12:20:15.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:14','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MCC',100,541431,540045,0)
;

-- 2018-12-17T12:20:15.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AEI',100,541432,540045,0)
;

-- 2018-12-17T12:20:15.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLJ',100,541433,540045,0)
;

-- 2018-12-17T12:20:15.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARC',100,541434,540045,0)
;

-- 2018-12-17T12:20:15.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXI',100,541435,540045,0)
;

-- 2018-12-17T12:20:15.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXP',100,541436,540045,0)
;

-- 2018-12-17T12:20:15.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POR',100,541437,540045,0)
;

-- 2018-12-17T12:20:15.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMB',100,541438,540045,0)
;

-- 2018-12-17T12:20:15.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMC',100,541439,540045,0)
;

-- 2018-12-17T12:20:15.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMM',100,541440,540045,0)
;

-- 2018-12-17T12:20:15.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMI',100,541441,540045,0)
;

-- 2018-12-17T12:20:15.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMP',100,541442,540045,0)
;

-- 2018-12-17T12:20:15.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','PJI',100,541443,540045,0)
;

-- 2018-12-17T12:20:15.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLD',100,541444,540045,0)
;

-- 2018-12-17T12:20:16.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:15','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARF',100,541445,540045,0)
;

-- 2018-12-17T12:20:16.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APC',100,541446,540045,0)
;

-- 2018-12-17T12:20:16.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMA',100,541447,540045,0)
;

-- 2018-12-17T12:20:16.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMS',100,541448,540045,0)
;

-- 2018-12-17T12:20:16.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POO',100,541449,540045,0)
;

-- 2018-12-17T12:20:16.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),'N','N','N','SOO',100,541450,540045,0)
;

-- 2018-12-17T12:20:16.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARI',100,541451,540045,0)
;

-- 2018-12-17T12:20:16.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMR',100,541452,540045,0)
;

-- 2018-12-17T12:20:16.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CON',100,541453,540045,0)
;

-- 2018-12-17T12:20:16.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MST',100,541454,540045,0)
;

-- 2018-12-17T12:20:16.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),'N','N','N','API',100,541455,540045,0)
;

-- 2018-12-17T12:20:16.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARR',100,541456,540045,0)
;

-- 2018-12-17T12:20:16.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APP',100,541457,540045,0)
;

-- 2018-12-17T12:20:17.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:16','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MQO',100,541458,540045,0)
;

-- 2018-12-17T12:20:17.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOP',100,541459,540045,0)
;

-- 2018-12-17T12:20:17.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOF',100,541460,540045,0)
;

-- 2018-12-17T12:20:17.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DUN',100,541461,540045,0)
;

-- 2018-12-17T12:20:17.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (Processing,AD_Client_ID,IsActive,Created,CreatedBy,PeriodNo,PeriodType,C_Year_ID,Updated,UpdatedBy,EndDate,StartDate,C_Period_ID,AD_Org_ID,Name) VALUES ('N',1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),100,9,'S',540006,TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-09-30','YYYY-MM-DD'),TO_TIMESTAMP('2019-09-01','YYYY-MM-DD'),540046,0,'Sep-19')
;

-- 2018-12-17T12:20:17.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540046 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2018-12-17T12:20:17.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AVI',100,541462,540046,0)
;

-- 2018-12-17T12:20:17.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),'N','N','N','HRP',100,541463,540046,0)
;

-- 2018-12-17T12:20:17.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DOO',100,541464,540046,0)
;

-- 2018-12-17T12:20:17.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MCC',100,541465,540046,0)
;

-- 2018-12-17T12:20:17.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AEI',100,541466,540046,0)
;

-- 2018-12-17T12:20:17.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLJ',100,541467,540046,0)
;

-- 2018-12-17T12:20:17.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARC',100,541468,540046,0)
;

-- 2018-12-17T12:20:17.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXI',100,541469,540046,0)
;

-- 2018-12-17T12:20:18.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:17','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXP',100,541470,540046,0)
;

-- 2018-12-17T12:20:18.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POR',100,541471,540046,0)
;

-- 2018-12-17T12:20:18.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMB',100,541472,540046,0)
;

-- 2018-12-17T12:20:18.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMC',100,541473,540046,0)
;

-- 2018-12-17T12:20:18.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMM',100,541474,540046,0)
;

-- 2018-12-17T12:20:18.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMI',100,541475,540046,0)
;

-- 2018-12-17T12:20:18.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMP',100,541476,540046,0)
;

-- 2018-12-17T12:20:18.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),'N','N','N','PJI',100,541477,540046,0)
;

-- 2018-12-17T12:20:18.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLD',100,541478,540046,0)
;

-- 2018-12-17T12:20:18.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARF',100,541479,540046,0)
;

-- 2018-12-17T12:20:18.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APC',100,541480,540046,0)
;

-- 2018-12-17T12:20:18.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMA',100,541481,540046,0)
;

-- 2018-12-17T12:20:18.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMS',100,541482,540046,0)
;

-- 2018-12-17T12:20:19.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:18','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POO',100,541483,540046,0)
;

-- 2018-12-17T12:20:19.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),'N','N','N','SOO',100,541484,540046,0)
;

-- 2018-12-17T12:20:19.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARI',100,541485,540046,0)
;

-- 2018-12-17T12:20:19.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMR',100,541486,540046,0)
;

-- 2018-12-17T12:20:19.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CON',100,541487,540046,0)
;

-- 2018-12-17T12:20:19.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MST',100,541488,540046,0)
;

-- 2018-12-17T12:20:19.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),'N','N','N','API',100,541489,540046,0)
;

-- 2018-12-17T12:20:19.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARR',100,541490,540046,0)
;

-- 2018-12-17T12:20:19.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APP',100,541491,540046,0)
;

-- 2018-12-17T12:20:19.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MQO',100,541492,540046,0)
;

-- 2018-12-17T12:20:19.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOP',100,541493,540046,0)
;

-- 2018-12-17T12:20:19.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOF',100,541494,540046,0)
;

-- 2018-12-17T12:20:19.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DUN',100,541495,540046,0)
;

-- 2018-12-17T12:20:20.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (Processing,AD_Client_ID,IsActive,Created,CreatedBy,PeriodNo,PeriodType,C_Year_ID,Updated,UpdatedBy,EndDate,StartDate,C_Period_ID,AD_Org_ID,Name) VALUES ('N',1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),100,10,'S',540006,TO_TIMESTAMP('2018-12-17 12:20:19','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-10-31','YYYY-MM-DD'),TO_TIMESTAMP('2019-10-01','YYYY-MM-DD'),540047,0,'Okt-19')
;

-- 2018-12-17T12:20:20.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540047 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2018-12-17T12:20:20.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AVI',100,541496,540047,0)
;

-- 2018-12-17T12:20:20.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),'N','N','N','HRP',100,541497,540047,0)
;

-- 2018-12-17T12:20:20.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DOO',100,541498,540047,0)
;

-- 2018-12-17T12:20:20.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MCC',100,541499,540047,0)
;

-- 2018-12-17T12:20:20.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AEI',100,541500,540047,0)
;

-- 2018-12-17T12:20:20.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLJ',100,541501,540047,0)
;

-- 2018-12-17T12:20:20.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARC',100,541502,540047,0)
;

-- 2018-12-17T12:20:20.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXI',100,541503,540047,0)
;

-- 2018-12-17T12:20:20.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXP',100,541504,540047,0)
;

-- 2018-12-17T12:20:20.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POR',100,541505,540047,0)
;

-- 2018-12-17T12:20:20.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMB',100,541506,540047,0)
;

-- 2018-12-17T12:20:20.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMC',100,541507,540047,0)
;

-- 2018-12-17T12:20:21.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:20','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMM',100,541508,540047,0)
;

-- 2018-12-17T12:20:21.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMI',100,541509,540047,0)
;

-- 2018-12-17T12:20:21.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMP',100,541510,540047,0)
;

-- 2018-12-17T12:20:21.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','PJI',100,541511,540047,0)
;

-- 2018-12-17T12:20:21.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLD',100,541512,540047,0)
;

-- 2018-12-17T12:20:21.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARF',100,541513,540047,0)
;

-- 2018-12-17T12:20:21.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APC',100,541514,540047,0)
;

-- 2018-12-17T12:20:21.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMA',100,541515,540047,0)
;

-- 2018-12-17T12:20:21.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMS',100,541516,540047,0)
;

-- 2018-12-17T12:20:21.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POO',100,541517,540047,0)
;

-- 2018-12-17T12:20:21.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','SOO',100,541518,540047,0)
;

-- 2018-12-17T12:20:21.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARI',100,541519,540047,0)
;

-- 2018-12-17T12:20:21.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMR',100,541520,540047,0)
;

-- 2018-12-17T12:20:21.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CON',100,541521,540047,0)
;

-- 2018-12-17T12:20:22.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:21','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MST',100,541522,540047,0)
;

-- 2018-12-17T12:20:22.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),'N','N','N','API',100,541523,540047,0)
;

-- 2018-12-17T12:20:22.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARR',100,541524,540047,0)
;

-- 2018-12-17T12:20:22.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APP',100,541525,540047,0)
;

-- 2018-12-17T12:20:22.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MQO',100,541526,540047,0)
;

-- 2018-12-17T12:20:22.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOP',100,541527,540047,0)
;

-- 2018-12-17T12:20:22.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOF',100,541528,540047,0)
;

-- 2018-12-17T12:20:22.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DUN',100,541529,540047,0)
;

-- 2018-12-17T12:20:22.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (Processing,AD_Client_ID,IsActive,Created,CreatedBy,PeriodNo,PeriodType,C_Year_ID,Updated,UpdatedBy,EndDate,StartDate,C_Period_ID,AD_Org_ID,Name) VALUES ('N',1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),100,11,'S',540006,TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-11-30','YYYY-MM-DD'),TO_TIMESTAMP('2019-11-01','YYYY-MM-DD'),540048,0,'Nov-19')
;

-- 2018-12-17T12:20:22.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540048 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2018-12-17T12:20:22.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AVI',100,541530,540048,0)
;

-- 2018-12-17T12:20:22.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),'N','N','N','HRP',100,541531,540048,0)
;

-- 2018-12-17T12:20:22.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DOO',100,541532,540048,0)
;

-- 2018-12-17T12:20:22.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:22','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MCC',100,541533,540048,0)
;

-- 2018-12-17T12:20:23.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AEI',100,541534,540048,0)
;

-- 2018-12-17T12:20:23.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLJ',100,541535,540048,0)
;

-- 2018-12-17T12:20:23.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARC',100,541536,540048,0)
;

-- 2018-12-17T12:20:23.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXI',100,541537,540048,0)
;

-- 2018-12-17T12:20:23.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXP',100,541538,540048,0)
;

-- 2018-12-17T12:20:23.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POR',100,541539,540048,0)
;

-- 2018-12-17T12:20:23.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMB',100,541540,540048,0)
;

-- 2018-12-17T12:20:23.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMC',100,541541,540048,0)
;

-- 2018-12-17T12:20:23.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMM',100,541542,540048,0)
;

-- 2018-12-17T12:20:23.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMI',100,541543,540048,0)
;

-- 2018-12-17T12:20:23.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMP',100,541544,540048,0)
;

-- 2018-12-17T12:20:23.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','PJI',100,541545,540048,0)
;

-- 2018-12-17T12:20:23.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLD',100,541546,540048,0)
;

-- 2018-12-17T12:20:24.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:23','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARF',100,541547,540048,0)
;

-- 2018-12-17T12:20:24.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APC',100,541548,540048,0)
;

-- 2018-12-17T12:20:24.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMA',100,541549,540048,0)
;

-- 2018-12-17T12:20:24.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMS',100,541550,540048,0)
;

-- 2018-12-17T12:20:24.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POO',100,541551,540048,0)
;

-- 2018-12-17T12:20:24.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),'N','N','N','SOO',100,541552,540048,0)
;

-- 2018-12-17T12:20:24.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARI',100,541553,540048,0)
;

-- 2018-12-17T12:20:24.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMR',100,541554,540048,0)
;

-- 2018-12-17T12:20:24.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CON',100,541555,540048,0)
;

-- 2018-12-17T12:20:24.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MST',100,541556,540048,0)
;

-- 2018-12-17T12:20:24.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),'N','N','N','API',100,541557,540048,0)
;

-- 2018-12-17T12:20:24.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARR',100,541558,540048,0)
;

-- 2018-12-17T12:20:24.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:24','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APP',100,541559,540048,0)
;

-- 2018-12-17T12:20:25.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MQO',100,541560,540048,0)
;

-- 2018-12-17T12:20:25.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOP',100,541561,540048,0)
;

-- 2018-12-17T12:20:25.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOF',100,541562,540048,0)
;

-- 2018-12-17T12:20:25.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DUN',100,541563,540048,0)
;

-- 2018-12-17T12:20:25.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period (Processing,AD_Client_ID,IsActive,Created,CreatedBy,PeriodNo,PeriodType,C_Year_ID,Updated,UpdatedBy,EndDate,StartDate,C_Period_ID,AD_Org_ID,Name) VALUES ('N',1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),100,12,'S',540006,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-12-31','YYYY-MM-DD'),TO_TIMESTAMP('2019-12-01','YYYY-MM-DD'),540049,0,'Dez-19')
;

-- 2018-12-17T12:20:25.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_Period_ID=540049 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2018-12-17T12:20:25.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AVI',100,541564,540049,0)
;

-- 2018-12-17T12:20:25.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),'N','N','N','HRP',100,541565,540049,0)
;

-- 2018-12-17T12:20:25.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DOO',100,541566,540049,0)
;

-- 2018-12-17T12:20:25.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MCC',100,541567,540049,0)
;

-- 2018-12-17T12:20:25.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),'N','N','N','AEI',100,541568,540049,0)
;

-- 2018-12-17T12:20:25.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLJ',100,541569,540049,0)
;

-- 2018-12-17T12:20:25.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARC',100,541570,540049,0)
;

-- 2018-12-17T12:20:25.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXI',100,541571,540049,0)
;

-- 2018-12-17T12:20:26.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:25','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MXP',100,541572,540049,0)
;

-- 2018-12-17T12:20:26.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POR',100,541573,540049,0)
;

-- 2018-12-17T12:20:26.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMB',100,541574,540049,0)
;

-- 2018-12-17T12:20:26.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMC',100,541575,540049,0)
;

-- 2018-12-17T12:20:26.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMM',100,541576,540049,0)
;

-- 2018-12-17T12:20:26.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMI',100,541577,540049,0)
;

-- 2018-12-17T12:20:26.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMP',100,541578,540049,0)
;

-- 2018-12-17T12:20:26.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','PJI',100,541579,540049,0)
;

-- 2018-12-17T12:20:26.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','GLD',100,541580,540049,0)
;

-- 2018-12-17T12:20:26.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARF',100,541581,540049,0)
;

-- 2018-12-17T12:20:26.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APC',100,541582,540049,0)
;

-- 2018-12-17T12:20:26.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CMA',100,541583,540049,0)
;

-- 2018-12-17T12:20:26.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMS',100,541584,540049,0)
;

-- 2018-12-17T12:20:26.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','POO',100,541585,540049,0)
;

-- 2018-12-17T12:20:27.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:26','YYYY-MM-DD HH24:MI:SS'),'N','N','N','SOO',100,541586,540049,0)
;

-- 2018-12-17T12:20:27.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARI',100,541587,540049,0)
;

-- 2018-12-17T12:20:27.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MMR',100,541588,540049,0)
;

-- 2018-12-17T12:20:27.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),'N','N','N','CON',100,541589,540049,0)
;

-- 2018-12-17T12:20:27.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MST',100,541590,540049,0)
;

-- 2018-12-17T12:20:27.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),'N','N','N','API',100,541591,540049,0)
;

-- 2018-12-17T12:20:27.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),'N','N','N','ARR',100,541592,540049,0)
;

-- 2018-12-17T12:20:27.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),'N','N','N','APP',100,541593,540049,0)
;

-- 2018-12-17T12:20:27.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MQO',100,541594,540049,0)
;

-- 2018-12-17T12:20:27.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOP',100,541595,540049,0)
;

-- 2018-12-17T12:20:27.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),'N','N','N','MOF',100,541596,540049,0)
;

-- 2018-12-17T12:20:27.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PeriodControl (CreatedBy,Updated,AD_Client_ID,IsActive,Created,PeriodStatus,Processing,PeriodAction,DocBaseType,UpdatedBy,C_PeriodControl_ID,C_Period_ID,AD_Org_ID) VALUES (100,TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),1000000,'Y',TO_TIMESTAMP('2018-12-17 12:20:27','YYYY-MM-DD HH24:MI:SS'),'N','N','N','DUN',100,541597,540049,0)
;

RAISE NOTICE 'successfully created C_Year "2019" for C_Calendar_ID=1000000, with C_Period and C_PeriodControl records';

END;
$BODY$;

select public.add_year_2019_if_not_exists();
