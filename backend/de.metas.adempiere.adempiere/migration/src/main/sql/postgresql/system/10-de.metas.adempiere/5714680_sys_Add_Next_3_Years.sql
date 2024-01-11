--- YEARS 2025, 2026, 2027

DO $$
BEGIN

-- 2024-01-02T11:01:52.326680700Z
INSERT INTO C_Year (AD_Client_ID,AD_Org_ID,C_Calendar_ID,Created,CreatedBy,C_Year_ID,FiscalYear,IsActive,Processing,Updated,UpdatedBy) VALUES (1000000,0,1000000,TO_TIMESTAMP('2024-01-02 12:01:52.319','YYYY-MM-DD HH24:MI:SS.US'),100,540029,'2025','Y','N',TO_TIMESTAMP('2024-01-02 12:01:52.319','YYYY-MM-DD HH24:MI:SS.US'),100)
;

EXCEPTION WHEN unique_violation THEN

RAISE NOTICE 'year 2025 already exists';
end $$;




DO $$
BEGIN
    
-- 2024-01-02T11:02:04.740211900Z
INSERT INTO C_Year (AD_Client_ID,AD_Org_ID,C_Calendar_ID,Created,CreatedBy,C_Year_ID,FiscalYear,IsActive,Processing,Updated,UpdatedBy) VALUES (1000000,0,1000000,TO_TIMESTAMP('2024-01-02 12:02:04.738','YYYY-MM-DD HH24:MI:SS.US'),100,540030,'2026','Y','N',TO_TIMESTAMP('2024-01-02 12:02:04.738','YYYY-MM-DD HH24:MI:SS.US'),100)
;

EXCEPTION WHEN unique_violation THEN

RAISE NOTICE 'year 2026 already exists';

end $$;




DO $$
BEGIN

-- 2024-01-02T11:02:10.664141700Z
INSERT INTO C_Year (AD_Client_ID,AD_Org_ID,C_Calendar_ID,Created,CreatedBy,C_Year_ID,FiscalYear,IsActive,Processing,Updated,UpdatedBy) VALUES (1000000,0,1000000,TO_TIMESTAMP('2024-01-02 12:02:10.661','YYYY-MM-DD HH24:MI:SS.US'),100,540031,'2027','Y','N',TO_TIMESTAMP('2024-01-02 12:02:10.661','YYYY-MM-DD HH24:MI:SS.US'),100)
;

EXCEPTION WHEN unique_violation THEN
RAISE NOTICE 'year 2027 already exists';

end $$;



--- PERIODS for 2025


DO $$
    DECLARE
        year_id numeric;
BEGIN

    year_id := (SELECT c_year_id from c_year where fiscalyear = '2025' and c_calendar_id = 1000000);
    -- 2024-01-02T11:03:33.859594700Z
    INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540295,TO_TIMESTAMP('2024-01-02 12:03:33.727','YYYY-MM-DD HH24:MI:SS.US'),100,year_id,TO_TIMESTAMP('2025-01-31','YYYY-MM-DD'),'Y','Jan.-25',1,'S','N',TO_TIMESTAMP('2025-01-01','YYYY-MM-DD'),TO_TIMESTAMP('2024-01-02 12:03:33.727','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:33.869191500Z
    INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540295 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
    ;

    -- 2024-01-02T11:03:34.034952800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550880,540295,TO_TIMESTAMP('2024-01-02 12:03:33.944','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:33.944','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:34.126927600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550881,540295,TO_TIMESTAMP('2024-01-02 12:03:34.04','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:34.04','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:34.209158900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550882,540295,TO_TIMESTAMP('2024-01-02 12:03:34.132','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:34.132','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:34.302268100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550883,540295,TO_TIMESTAMP('2024-01-02 12:03:34.215','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:34.215','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:34.395847900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550884,540295,TO_TIMESTAMP('2024-01-02 12:03:34.309','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:34.309','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:34.492963300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550885,540295,TO_TIMESTAMP('2024-01-02 12:03:34.4','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:34.4','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:34.583579100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550886,540295,TO_TIMESTAMP('2024-01-02 12:03:34.497','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:34.497','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:34.685146300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550887,540295,TO_TIMESTAMP('2024-01-02 12:03:34.589','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:34.589','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:34.776849200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550888,540295,TO_TIMESTAMP('2024-01-02 12:03:34.69','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:34.69','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:34.881991900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550889,540295,TO_TIMESTAMP('2024-01-02 12:03:34.782','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:34.782','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:34.966590400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550890,540295,TO_TIMESTAMP('2024-01-02 12:03:34.887','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:34.887','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:35.048180600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550891,540295,TO_TIMESTAMP('2024-01-02 12:03:34.972','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:34.972','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:35.138782700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550892,540295,TO_TIMESTAMP('2024-01-02 12:03:35.054','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:35.054','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:35.226566Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550893,540295,TO_TIMESTAMP('2024-01-02 12:03:35.144','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:35.144','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:35.320968300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550894,540295,TO_TIMESTAMP('2024-01-02 12:03:35.232','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:35.232','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:35.424925200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550895,540295,TO_TIMESTAMP('2024-01-02 12:03:35.327','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:35.327','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:35.516540500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550896,540295,TO_TIMESTAMP('2024-01-02 12:03:35.431','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:35.431','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:35.602989300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550897,540295,TO_TIMESTAMP('2024-01-02 12:03:35.521','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:35.521','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:35.693845900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550898,540295,TO_TIMESTAMP('2024-01-02 12:03:35.609','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:35.609','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:35.782789300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550899,540295,TO_TIMESTAMP('2024-01-02 12:03:35.699','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:35.699','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:35.873430700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550900,540295,TO_TIMESTAMP('2024-01-02 12:03:35.789','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:35.789','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:35.953107300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550901,540295,TO_TIMESTAMP('2024-01-02 12:03:35.878','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:35.878','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:36.042409400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550902,540295,TO_TIMESTAMP('2024-01-02 12:03:35.959','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:35.959','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:36.136239800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550903,540295,TO_TIMESTAMP('2024-01-02 12:03:36.047','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:36.047','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:36.230544200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550904,540295,TO_TIMESTAMP('2024-01-02 12:03:36.142','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:36.142','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:36.327768400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550905,540295,TO_TIMESTAMP('2024-01-02 12:03:36.236','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:36.236','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:36.414492700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550906,540295,TO_TIMESTAMP('2024-01-02 12:03:36.333','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:36.333','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:36.501713500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550907,540295,TO_TIMESTAMP('2024-01-02 12:03:36.42','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:36.42','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:36.599566Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550908,540295,TO_TIMESTAMP('2024-01-02 12:03:36.507','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:36.507','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:36.684171900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550909,540295,TO_TIMESTAMP('2024-01-02 12:03:36.606','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:36.606','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:36.772156Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550910,540295,TO_TIMESTAMP('2024-01-02 12:03:36.69','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:36.69','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:36.852729500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550911,540295,TO_TIMESTAMP('2024-01-02 12:03:36.778','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:36.778','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:36.944700100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550912,540295,TO_TIMESTAMP('2024-01-02 12:03:36.858','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:36.858','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:37.028796800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550913,540295,TO_TIMESTAMP('2024-01-02 12:03:36.95','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:36.95','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:37.109622600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550914,540295,TO_TIMESTAMP('2024-01-02 12:03:37.034','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:37.034','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:37.189100200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550915,540295,TO_TIMESTAMP('2024-01-02 12:03:37.115','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:37.115','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:37.277612600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550916,540295,TO_TIMESTAMP('2024-01-02 12:03:37.195','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:37.195','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:37.371981700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550917,540295,TO_TIMESTAMP('2024-01-02 12:03:37.283','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:37.283','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:37.458306100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550918,540295,TO_TIMESTAMP('2024-01-02 12:03:37.378','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:37.378','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:37.548850800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550919,540295,TO_TIMESTAMP('2024-01-02 12:03:37.463','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:37.463','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:37.648431Z
    INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540296,TO_TIMESTAMP('2024-01-02 12:03:37.559','YYYY-MM-DD HH24:MI:SS.US'),100,year_id,TO_TIMESTAMP('2025-02-28','YYYY-MM-DD'),'Y','Feb.-25',2,'S','N',TO_TIMESTAMP('2025-02-01','YYYY-MM-DD'),TO_TIMESTAMP('2024-01-02 12:03:37.559','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:37.652249400Z
    INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540296 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
    ;

    -- 2024-01-02T11:03:37.769215Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550920,540296,TO_TIMESTAMP('2024-01-02 12:03:37.692','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:37.692','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:37.856806900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550921,540296,TO_TIMESTAMP('2024-01-02 12:03:37.774','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:37.774','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:37.956619500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550922,540296,TO_TIMESTAMP('2024-01-02 12:03:37.862','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:37.862','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:38.057035500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550923,540296,TO_TIMESTAMP('2024-01-02 12:03:37.962','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:37.962','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:38.138000800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550924,540296,TO_TIMESTAMP('2024-01-02 12:03:38.062','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:38.062','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:38.230335400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550925,540296,TO_TIMESTAMP('2024-01-02 12:03:38.143','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:38.143','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:38.312608500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550926,540296,TO_TIMESTAMP('2024-01-02 12:03:38.236','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:38.236','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:38.402845200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550927,540296,TO_TIMESTAMP('2024-01-02 12:03:38.318','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:38.318','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:38.485802Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550928,540296,TO_TIMESTAMP('2024-01-02 12:03:38.409','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:38.409','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:38.580306700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550929,540296,TO_TIMESTAMP('2024-01-02 12:03:38.491','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:38.491','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:38.673041600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550930,540296,TO_TIMESTAMP('2024-01-02 12:03:38.585','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:38.585','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:38.768093200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550931,540296,TO_TIMESTAMP('2024-01-02 12:03:38.679','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:38.679','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:38.869168400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550932,540296,TO_TIMESTAMP('2024-01-02 12:03:38.775','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:38.775','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:38.991569600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550933,540296,TO_TIMESTAMP('2024-01-02 12:03:38.875','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:38.875','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:39.083882300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550934,540296,TO_TIMESTAMP('2024-01-02 12:03:38.997','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:38.997','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:39.171739100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550935,540296,TO_TIMESTAMP('2024-01-02 12:03:39.088','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:39.088','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:39.265912600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550936,540296,TO_TIMESTAMP('2024-01-02 12:03:39.177','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:39.177','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:39.368670Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550937,540296,TO_TIMESTAMP('2024-01-02 12:03:39.272','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:39.272','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:39.459218100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550938,540296,TO_TIMESTAMP('2024-01-02 12:03:39.374','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:39.374','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:39.555503400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550939,540296,TO_TIMESTAMP('2024-01-02 12:03:39.465','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:39.465','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:39.646981700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550940,540296,TO_TIMESTAMP('2024-01-02 12:03:39.56','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:39.56','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:39.738930Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550941,540296,TO_TIMESTAMP('2024-01-02 12:03:39.653','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:39.653','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:39.826020200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550942,540296,TO_TIMESTAMP('2024-01-02 12:03:39.744','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:39.744','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:39.921450300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550943,540296,TO_TIMESTAMP('2024-01-02 12:03:39.831','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:39.831','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:40.017882Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550944,540296,TO_TIMESTAMP('2024-01-02 12:03:39.927','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:39.927','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:40.117639500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550945,540296,TO_TIMESTAMP('2024-01-02 12:03:40.022','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:40.022','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:40.211599400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550946,540296,TO_TIMESTAMP('2024-01-02 12:03:40.123','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:40.123','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:40.303132800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550947,540296,TO_TIMESTAMP('2024-01-02 12:03:40.217','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:40.217','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:40.404445500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550948,540296,TO_TIMESTAMP('2024-01-02 12:03:40.308','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:40.308','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:40.499259900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550949,540296,TO_TIMESTAMP('2024-01-02 12:03:40.411','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:40.411','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:40.590538400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550950,540296,TO_TIMESTAMP('2024-01-02 12:03:40.504','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:40.504','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:40.685674Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550951,540296,TO_TIMESTAMP('2024-01-02 12:03:40.596','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:40.596','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:40.780155100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550952,540296,TO_TIMESTAMP('2024-01-02 12:03:40.691','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:40.691','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:40.884456700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550953,540296,TO_TIMESTAMP('2024-01-02 12:03:40.786','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:40.786','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:40.980733900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550954,540296,TO_TIMESTAMP('2024-01-02 12:03:40.89','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:40.89','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:41.082285700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550955,540296,TO_TIMESTAMP('2024-01-02 12:03:40.986','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:40.986','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:41.181975500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550956,540296,TO_TIMESTAMP('2024-01-02 12:03:41.087','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:41.087','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:41.272335300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550957,540296,TO_TIMESTAMP('2024-01-02 12:03:41.188','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:41.188','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:41.368550100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550958,540296,TO_TIMESTAMP('2024-01-02 12:03:41.278','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:41.278','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:41.457267800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550959,540296,TO_TIMESTAMP('2024-01-02 12:03:41.374','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:41.374','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:41.553130900Z
    INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540297,TO_TIMESTAMP('2024-01-02 12:03:41.468','YYYY-MM-DD HH24:MI:SS.US'),100,year_id,TO_TIMESTAMP('2025-03-31','YYYY-MM-DD'),'Y','März-25',3,'S','N',TO_TIMESTAMP('2025-03-01','YYYY-MM-DD'),TO_TIMESTAMP('2024-01-02 12:03:41.468','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:41.556973700Z
    INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540297 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
    ;

    -- 2024-01-02T11:03:41.678374900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550960,540297,TO_TIMESTAMP('2024-01-02 12:03:41.596','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:41.596','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:41.767507100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550961,540297,TO_TIMESTAMP('2024-01-02 12:03:41.684','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:41.684','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:41.851469300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550962,540297,TO_TIMESTAMP('2024-01-02 12:03:41.772','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:41.772','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:41.938597100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550963,540297,TO_TIMESTAMP('2024-01-02 12:03:41.855','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:41.855','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:42.030492400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550964,540297,TO_TIMESTAMP('2024-01-02 12:03:41.942','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:41.942','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:42.119459200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550965,540297,TO_TIMESTAMP('2024-01-02 12:03:42.037','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:42.037','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:42.212643700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550966,540297,TO_TIMESTAMP('2024-01-02 12:03:42.124','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:42.124','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:42.309244200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550967,540297,TO_TIMESTAMP('2024-01-02 12:03:42.217','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:42.217','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:42.405664400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550968,540297,TO_TIMESTAMP('2024-01-02 12:03:42.314','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:42.314','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:42.487499300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550969,540297,TO_TIMESTAMP('2024-01-02 12:03:42.411','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:42.411','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:42.583200100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550970,540297,TO_TIMESTAMP('2024-01-02 12:03:42.492','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:42.492','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:42.674623100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550971,540297,TO_TIMESTAMP('2024-01-02 12:03:42.588','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:42.588','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:42.757544300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550972,540297,TO_TIMESTAMP('2024-01-02 12:03:42.68','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:42.68','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:42.850899400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550973,540297,TO_TIMESTAMP('2024-01-02 12:03:42.763','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:42.763','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:42.945947300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550974,540297,TO_TIMESTAMP('2024-01-02 12:03:42.856','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:42.856','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:43.039800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550975,540297,TO_TIMESTAMP('2024-01-02 12:03:42.951','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:42.951','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:43.131743800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550976,540297,TO_TIMESTAMP('2024-01-02 12:03:43.045','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:43.045','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:43.251915Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550977,540297,TO_TIMESTAMP('2024-01-02 12:03:43.137','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:43.137','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:43.342851200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550978,540297,TO_TIMESTAMP('2024-01-02 12:03:43.256','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:43.256','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:43.435448900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550979,540297,TO_TIMESTAMP('2024-01-02 12:03:43.348','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:43.348','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:43.523297800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550980,540297,TO_TIMESTAMP('2024-01-02 12:03:43.441','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:43.441','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:43.613818900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550981,540297,TO_TIMESTAMP('2024-01-02 12:03:43.528','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:43.528','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:43.707027800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550982,540297,TO_TIMESTAMP('2024-01-02 12:03:43.618','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:43.618','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:43.795240700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550983,540297,TO_TIMESTAMP('2024-01-02 12:03:43.712','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:43.712','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:43.887634200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550984,540297,TO_TIMESTAMP('2024-01-02 12:03:43.801','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:43.801','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:43.983359800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550985,540297,TO_TIMESTAMP('2024-01-02 12:03:43.892','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:43.892','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:44.073088100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550986,540297,TO_TIMESTAMP('2024-01-02 12:03:43.988','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:43.988','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:44.154171800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550987,540297,TO_TIMESTAMP('2024-01-02 12:03:44.077','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:44.077','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:44.254447100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550988,540297,TO_TIMESTAMP('2024-01-02 12:03:44.159','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:44.159','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:44.333923500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550989,540297,TO_TIMESTAMP('2024-01-02 12:03:44.258','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:44.258','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:44.428746500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550990,540297,TO_TIMESTAMP('2024-01-02 12:03:44.339','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:44.339','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:44.526409500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550991,540297,TO_TIMESTAMP('2024-01-02 12:03:44.435','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:44.435','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:44.609906900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550992,540297,TO_TIMESTAMP('2024-01-02 12:03:44.531','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:44.531','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:44.693675200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550993,540297,TO_TIMESTAMP('2024-01-02 12:03:44.615','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:44.615','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:44.784170Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550994,540297,TO_TIMESTAMP('2024-01-02 12:03:44.698','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:44.698','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:44.872783600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550995,540297,TO_TIMESTAMP('2024-01-02 12:03:44.789','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:44.789','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:44.963468400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550996,540297,TO_TIMESTAMP('2024-01-02 12:03:44.878','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:44.878','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:45.048765400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550997,540297,TO_TIMESTAMP('2024-01-02 12:03:44.969','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:44.969','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:45.134645Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550998,540297,TO_TIMESTAMP('2024-01-02 12:03:45.053','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:45.053','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:45.210600900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,550999,540297,TO_TIMESTAMP('2024-01-02 12:03:45.14','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:45.14','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:45.313427200Z
    INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540298,TO_TIMESTAMP('2024-01-02 12:03:45.221','YYYY-MM-DD HH24:MI:SS.US'),100,year_id,TO_TIMESTAMP('2025-04-30','YYYY-MM-DD'),'Y','Apr.-25',4,'S','N',TO_TIMESTAMP('2025-04-01','YYYY-MM-DD'),TO_TIMESTAMP('2024-01-02 12:03:45.221','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:45.318378800Z
    INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540298 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
    ;

    -- 2024-01-02T11:03:45.441613300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551000,540298,TO_TIMESTAMP('2024-01-02 12:03:45.355','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:45.355','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:45.528459600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551001,540298,TO_TIMESTAMP('2024-01-02 12:03:45.446','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:45.446','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:45.620115Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551002,540298,TO_TIMESTAMP('2024-01-02 12:03:45.534','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:45.534','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:45.713168600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551003,540298,TO_TIMESTAMP('2024-01-02 12:03:45.624','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:45.624','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:45.803495100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551004,540298,TO_TIMESTAMP('2024-01-02 12:03:45.718','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:45.718','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:45.890199800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551005,540298,TO_TIMESTAMP('2024-01-02 12:03:45.808','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:45.808','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:45.983797800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551006,540298,TO_TIMESTAMP('2024-01-02 12:03:45.896','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:45.896','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:46.073585400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551007,540298,TO_TIMESTAMP('2024-01-02 12:03:45.989','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:45.989','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:46.162212700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551008,540298,TO_TIMESTAMP('2024-01-02 12:03:46.078','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:46.078','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:46.256571400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551009,540298,TO_TIMESTAMP('2024-01-02 12:03:46.167','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:46.167','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:46.345206200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551010,540298,TO_TIMESTAMP('2024-01-02 12:03:46.262','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:46.262','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:46.440685Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551011,540298,TO_TIMESTAMP('2024-01-02 12:03:46.35','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:46.35','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:46.522437400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551012,540298,TO_TIMESTAMP('2024-01-02 12:03:46.446','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:46.446','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:46.614362Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551013,540298,TO_TIMESTAMP('2024-01-02 12:03:46.527','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:46.527','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:46.702396100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551014,540298,TO_TIMESTAMP('2024-01-02 12:03:46.62','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:46.62','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:46.794768500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551015,540298,TO_TIMESTAMP('2024-01-02 12:03:46.707','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:46.707','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:46.886213100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551016,540298,TO_TIMESTAMP('2024-01-02 12:03:46.8','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:46.8','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:46.966950300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551017,540298,TO_TIMESTAMP('2024-01-02 12:03:46.891','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:46.891','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:47.062105900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551018,540298,TO_TIMESTAMP('2024-01-02 12:03:46.972','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:46.972','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:47.159975800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551019,540298,TO_TIMESTAMP('2024-01-02 12:03:47.067','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:47.067','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:47.249435300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551020,540298,TO_TIMESTAMP('2024-01-02 12:03:47.165','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:47.165','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:47.342049300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551021,540298,TO_TIMESTAMP('2024-01-02 12:03:47.254','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:47.254','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:47.422662100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551022,540298,TO_TIMESTAMP('2024-01-02 12:03:47.346','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:47.346','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:47.510013Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551023,540298,TO_TIMESTAMP('2024-01-02 12:03:47.427','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:47.427','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:47.595202100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551024,540298,TO_TIMESTAMP('2024-01-02 12:03:47.514','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:47.514','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:47.681607700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551025,540298,TO_TIMESTAMP('2024-01-02 12:03:47.599','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:47.599','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:47.769208500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551026,540298,TO_TIMESTAMP('2024-01-02 12:03:47.687','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:47.687','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:47.859848Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551027,540298,TO_TIMESTAMP('2024-01-02 12:03:47.774','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:47.774','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:47.950093900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551028,540298,TO_TIMESTAMP('2024-01-02 12:03:47.864','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:47.864','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:48.045838600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551029,540298,TO_TIMESTAMP('2024-01-02 12:03:47.957','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:47.957','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:48.134729300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551030,540298,TO_TIMESTAMP('2024-01-02 12:03:48.05','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:48.05','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:48.221697800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551031,540298,TO_TIMESTAMP('2024-01-02 12:03:48.139','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:48.139','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:48.312848800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551032,540298,TO_TIMESTAMP('2024-01-02 12:03:48.226','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:48.226','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:48.399045800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551033,540298,TO_TIMESTAMP('2024-01-02 12:03:48.318','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:48.318','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:48.496220700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551034,540298,TO_TIMESTAMP('2024-01-02 12:03:48.405','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:48.405','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:48.590827600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551035,540298,TO_TIMESTAMP('2024-01-02 12:03:48.501','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:48.501','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:48.676395500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551036,540298,TO_TIMESTAMP('2024-01-02 12:03:48.595','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:48.595','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:48.769427600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551037,540298,TO_TIMESTAMP('2024-01-02 12:03:48.681','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:48.681','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:48.857371100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551038,540298,TO_TIMESTAMP('2024-01-02 12:03:48.774','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:48.774','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:48.948469400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551039,540298,TO_TIMESTAMP('2024-01-02 12:03:48.862','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:48.862','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:49.050888200Z
    INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540299,TO_TIMESTAMP('2024-01-02 12:03:48.958','YYYY-MM-DD HH24:MI:SS.US'),100,year_id,TO_TIMESTAMP('2025-05-31','YYYY-MM-DD'),'Y','Mai-25',5,'S','N',TO_TIMESTAMP('2025-05-01','YYYY-MM-DD'),TO_TIMESTAMP('2024-01-02 12:03:48.958','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:49.054759Z
    INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540299 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
    ;

    -- 2024-01-02T11:03:49.185156Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551040,540299,TO_TIMESTAMP('2024-01-02 12:03:49.091','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:49.091','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:49.267862500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551041,540299,TO_TIMESTAMP('2024-01-02 12:03:49.19','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:49.19','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:49.351596500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551042,540299,TO_TIMESTAMP('2024-01-02 12:03:49.273','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:49.273','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:49.448624200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551043,540299,TO_TIMESTAMP('2024-01-02 12:03:49.356','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:49.356','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:49.537589700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551044,540299,TO_TIMESTAMP('2024-01-02 12:03:49.454','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:49.454','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:49.627857300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551045,540299,TO_TIMESTAMP('2024-01-02 12:03:49.544','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:49.544','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:49.711557200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551046,540299,TO_TIMESTAMP('2024-01-02 12:03:49.632','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:49.632','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:49.797692700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551047,540299,TO_TIMESTAMP('2024-01-02 12:03:49.715','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:49.715','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:49.885195400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551048,540299,TO_TIMESTAMP('2024-01-02 12:03:49.803','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:49.803','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:49.973870100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551049,540299,TO_TIMESTAMP('2024-01-02 12:03:49.891','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:49.891','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:50.062334800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551050,540299,TO_TIMESTAMP('2024-01-02 12:03:49.979','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:49.979','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:50.156224200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551051,540299,TO_TIMESTAMP('2024-01-02 12:03:50.067','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:50.067','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:50.245755800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551052,540299,TO_TIMESTAMP('2024-01-02 12:03:50.161','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:50.161','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:50.340707500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551053,540299,TO_TIMESTAMP('2024-01-02 12:03:50.251','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:50.251','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:50.423497300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551054,540299,TO_TIMESTAMP('2024-01-02 12:03:50.346','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:50.346','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:50.517479700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551055,540299,TO_TIMESTAMP('2024-01-02 12:03:50.428','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:50.428','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:50.615635300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551056,540299,TO_TIMESTAMP('2024-01-02 12:03:50.523','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:50.523','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:50.702163900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551057,540299,TO_TIMESTAMP('2024-01-02 12:03:50.621','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:50.621','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:50.792427900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551058,540299,TO_TIMESTAMP('2024-01-02 12:03:50.708','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:50.708','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:50.869179100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551059,540299,TO_TIMESTAMP('2024-01-02 12:03:50.797','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:50.797','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:50.959446700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551060,540299,TO_TIMESTAMP('2024-01-02 12:03:50.874','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:50.874','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:51.043517100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551061,540299,TO_TIMESTAMP('2024-01-02 12:03:50.964','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:50.964','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:51.135022400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551062,540299,TO_TIMESTAMP('2024-01-02 12:03:51.048','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:51.048','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:51.233972700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551063,540299,TO_TIMESTAMP('2024-01-02 12:03:51.14','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:51.14','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:51.316935500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551064,540299,TO_TIMESTAMP('2024-01-02 12:03:51.238','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:51.238','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:51.405279700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551065,540299,TO_TIMESTAMP('2024-01-02 12:03:51.322','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:51.322','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:51.498017900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551066,540299,TO_TIMESTAMP('2024-01-02 12:03:51.41','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:51.41','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:51.586559700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551067,540299,TO_TIMESTAMP('2024-01-02 12:03:51.503','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:51.503','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:51.682035800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551068,540299,TO_TIMESTAMP('2024-01-02 12:03:51.592','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:51.592','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:51.769314200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551069,540299,TO_TIMESTAMP('2024-01-02 12:03:51.688','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:51.688','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:51.849081300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551070,540299,TO_TIMESTAMP('2024-01-02 12:03:51.774','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:51.774','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:51.936210200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551071,540299,TO_TIMESTAMP('2024-01-02 12:03:51.852','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:51.852','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:52.028818900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551072,540299,TO_TIMESTAMP('2024-01-02 12:03:51.942','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:51.942','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:52.112050800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551073,540299,TO_TIMESTAMP('2024-01-02 12:03:52.033','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:52.033','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:52.193478200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551074,540299,TO_TIMESTAMP('2024-01-02 12:03:52.117','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:52.117','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:52.313188300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551075,540299,TO_TIMESTAMP('2024-01-02 12:03:52.199','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:52.199','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:52.400836400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551076,540299,TO_TIMESTAMP('2024-01-02 12:03:52.319','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:52.319','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:52.499881300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551077,540299,TO_TIMESTAMP('2024-01-02 12:03:52.406','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:52.406','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:52.589783Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551078,540299,TO_TIMESTAMP('2024-01-02 12:03:52.505','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:52.505','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:52.675966200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551079,540299,TO_TIMESTAMP('2024-01-02 12:03:52.595','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:52.595','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:52.782324100Z
    INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540300,TO_TIMESTAMP('2024-01-02 12:03:52.686','YYYY-MM-DD HH24:MI:SS.US'),100,year_id,TO_TIMESTAMP('2025-06-30','YYYY-MM-DD'),'Y','Juni-25',6,'S','N',TO_TIMESTAMP('2025-06-01','YYYY-MM-DD'),TO_TIMESTAMP('2024-01-02 12:03:52.686','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:52.786474800Z
    INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540300 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
    ;

    -- 2024-01-02T11:03:52.908510400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551080,540300,TO_TIMESTAMP('2024-01-02 12:03:52.823','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:52.823','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:53.006496500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551081,540300,TO_TIMESTAMP('2024-01-02 12:03:52.913','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:52.913','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:53.095095700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551082,540300,TO_TIMESTAMP('2024-01-02 12:03:53.012','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:53.012','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:53.182124900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551083,540300,TO_TIMESTAMP('2024-01-02 12:03:53.1','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:53.1','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:53.263157900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551084,540300,TO_TIMESTAMP('2024-01-02 12:03:53.187','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:53.187','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:53.353885Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551085,540300,TO_TIMESTAMP('2024-01-02 12:03:53.268','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:53.268','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:53.445019400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551086,540300,TO_TIMESTAMP('2024-01-02 12:03:53.359','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:53.359','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:53.539755900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551087,540300,TO_TIMESTAMP('2024-01-02 12:03:53.45','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:53.45','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:53.631102200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551088,540300,TO_TIMESTAMP('2024-01-02 12:03:53.544','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:53.544','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:53.729235300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551089,540300,TO_TIMESTAMP('2024-01-02 12:03:53.635','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:53.635','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:53.819152600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551090,540300,TO_TIMESTAMP('2024-01-02 12:03:53.734','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:53.734','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:53.908019300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551091,540300,TO_TIMESTAMP('2024-01-02 12:03:53.824','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:53.824','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:54.007316600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551092,540300,TO_TIMESTAMP('2024-01-02 12:03:53.912','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:53.912','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:54.092481600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551093,540300,TO_TIMESTAMP('2024-01-02 12:03:54.012','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:54.012','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:54.185459400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551094,540300,TO_TIMESTAMP('2024-01-02 12:03:54.097','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:54.097','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:54.272809400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551095,540300,TO_TIMESTAMP('2024-01-02 12:03:54.19','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:54.19','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:54.359101500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551096,540300,TO_TIMESTAMP('2024-01-02 12:03:54.277','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:54.277','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:54.447458900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551097,540300,TO_TIMESTAMP('2024-01-02 12:03:54.363','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:54.363','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:54.539789700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551098,540300,TO_TIMESTAMP('2024-01-02 12:03:54.452','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:54.452','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:54.634075400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551099,540300,TO_TIMESTAMP('2024-01-02 12:03:54.545','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:54.545','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:54.733266500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551100,540300,TO_TIMESTAMP('2024-01-02 12:03:54.64','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:54.64','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:54.824288700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551101,540300,TO_TIMESTAMP('2024-01-02 12:03:54.738','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:54.738','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:54.913070700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551102,540300,TO_TIMESTAMP('2024-01-02 12:03:54.829','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:54.829','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:55.005586900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551103,540300,TO_TIMESTAMP('2024-01-02 12:03:54.917','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:54.917','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:55.102190400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551104,540300,TO_TIMESTAMP('2024-01-02 12:03:55.011','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:55.011','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:55.187625900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551105,540300,TO_TIMESTAMP('2024-01-02 12:03:55.108','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:55.108','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:55.284141200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551106,540300,TO_TIMESTAMP('2024-01-02 12:03:55.193','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:55.193','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:55.376604900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551107,540300,TO_TIMESTAMP('2024-01-02 12:03:55.289','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:55.289','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:55.468988500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551108,540300,TO_TIMESTAMP('2024-01-02 12:03:55.382','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:55.382','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:55.559249900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551109,540300,TO_TIMESTAMP('2024-01-02 12:03:55.474','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:55.474','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:55.647302200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551110,540300,TO_TIMESTAMP('2024-01-02 12:03:55.564','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:55.564','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:55.729873100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551111,540300,TO_TIMESTAMP('2024-01-02 12:03:55.653','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:55.653','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:55.813602300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551112,540300,TO_TIMESTAMP('2024-01-02 12:03:55.734','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:55.734','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:55.905473900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551113,540300,TO_TIMESTAMP('2024-01-02 12:03:55.819','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:55.819','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:56.004738100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551114,540300,TO_TIMESTAMP('2024-01-02 12:03:55.911','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:55.911','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:56.086932600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551115,540300,TO_TIMESTAMP('2024-01-02 12:03:56.01','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:56.01','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:56.176292600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551116,540300,TO_TIMESTAMP('2024-01-02 12:03:56.092','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:56.092','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:56.260385900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551117,540300,TO_TIMESTAMP('2024-01-02 12:03:56.181','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:56.181','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:56.353219500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551118,540300,TO_TIMESTAMP('2024-01-02 12:03:56.265','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:56.265','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:56.441800400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551119,540300,TO_TIMESTAMP('2024-01-02 12:03:56.358','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:56.358','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:56.544628100Z
    INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540301,TO_TIMESTAMP('2024-01-02 12:03:56.453','YYYY-MM-DD HH24:MI:SS.US'),100,year_id,TO_TIMESTAMP('2025-07-31','YYYY-MM-DD'),'Y','Juli-25',7,'S','N',TO_TIMESTAMP('2025-07-01','YYYY-MM-DD'),TO_TIMESTAMP('2024-01-02 12:03:56.453','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:56.548402800Z
    INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540301 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
    ;

    -- 2024-01-02T11:03:56.674646100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551120,540301,TO_TIMESTAMP('2024-01-02 12:03:56.585','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:56.585','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:56.769577500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551121,540301,TO_TIMESTAMP('2024-01-02 12:03:56.68','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:56.68','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:56.857831100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551122,540301,TO_TIMESTAMP('2024-01-02 12:03:56.774','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:56.774','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:56.947761100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551123,540301,TO_TIMESTAMP('2024-01-02 12:03:56.862','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:56.862','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:57.038559500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551124,540301,TO_TIMESTAMP('2024-01-02 12:03:56.953','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:56.953','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:57.127646200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551125,540301,TO_TIMESTAMP('2024-01-02 12:03:57.044','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:57.044','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:57.211781200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551126,540301,TO_TIMESTAMP('2024-01-02 12:03:57.132','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:57.132','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:57.304122900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551127,540301,TO_TIMESTAMP('2024-01-02 12:03:57.217','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:57.217','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:57.396415300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551128,540301,TO_TIMESTAMP('2024-01-02 12:03:57.309','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:57.309','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:57.473726500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551129,540301,TO_TIMESTAMP('2024-01-02 12:03:57.4','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:57.4','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:57.558320200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551130,540301,TO_TIMESTAMP('2024-01-02 12:03:57.479','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:57.479','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:57.653776800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551131,540301,TO_TIMESTAMP('2024-01-02 12:03:57.564','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:57.564','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:57.738944500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551132,540301,TO_TIMESTAMP('2024-01-02 12:03:57.659','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:57.659','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:57.832988400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551133,540301,TO_TIMESTAMP('2024-01-02 12:03:57.743','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:57.743','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:57.921214300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551134,540301,TO_TIMESTAMP('2024-01-02 12:03:57.837','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:57.837','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:58.011771900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551135,540301,TO_TIMESTAMP('2024-01-02 12:03:57.927','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:57.927','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:58.106114500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551136,540301,TO_TIMESTAMP('2024-01-02 12:03:58.016','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:58.016','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:58.197769800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551137,540301,TO_TIMESTAMP('2024-01-02 12:03:58.111','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:58.111','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:58.298838Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551138,540301,TO_TIMESTAMP('2024-01-02 12:03:58.203','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:58.203','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:58.387140400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551139,540301,TO_TIMESTAMP('2024-01-02 12:03:58.303','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:58.303','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:58.465533500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551140,540301,TO_TIMESTAMP('2024-01-02 12:03:58.393','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:58.393','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:58.556911300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551141,540301,TO_TIMESTAMP('2024-01-02 12:03:58.471','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:58.471','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:58.641905600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551142,540301,TO_TIMESTAMP('2024-01-02 12:03:58.561','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:58.561','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:58.730060800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551143,540301,TO_TIMESTAMP('2024-01-02 12:03:58.646','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:58.646','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:58.820905900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551144,540301,TO_TIMESTAMP('2024-01-02 12:03:58.735','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:58.735','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:58.907978800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551145,540301,TO_TIMESTAMP('2024-01-02 12:03:58.827','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:58.827','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:59.014697100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551146,540301,TO_TIMESTAMP('2024-01-02 12:03:58.912','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:58.912','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:59.104797100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551147,540301,TO_TIMESTAMP('2024-01-02 12:03:59.019','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:59.019','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:59.197572100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551148,540301,TO_TIMESTAMP('2024-01-02 12:03:59.11','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:59.11','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:59.290362Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551149,540301,TO_TIMESTAMP('2024-01-02 12:03:59.203','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:59.203','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:59.385048900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551150,540301,TO_TIMESTAMP('2024-01-02 12:03:59.296','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:59.296','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:59.474656Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551151,540301,TO_TIMESTAMP('2024-01-02 12:03:59.389','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:59.389','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:59.563907100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551152,540301,TO_TIMESTAMP('2024-01-02 12:03:59.48','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:59.48','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:59.653563200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551153,540301,TO_TIMESTAMP('2024-01-02 12:03:59.568','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:59.568','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:59.749478600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551154,540301,TO_TIMESTAMP('2024-01-02 12:03:59.658','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:59.658','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:59.842340800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551155,540301,TO_TIMESTAMP('2024-01-02 12:03:59.753','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:59.753','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:03:59.934724200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551156,540301,TO_TIMESTAMP('2024-01-02 12:03:59.848','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:59.848','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:00.029645100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551157,540301,TO_TIMESTAMP('2024-01-02 12:03:59.94','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:03:59.94','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:00.123253600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551158,540301,TO_TIMESTAMP('2024-01-02 12:04:00.034','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:00.034','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:00.207329100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551159,540301,TO_TIMESTAMP('2024-01-02 12:04:00.129','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:00.129','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:00.293104600Z
    INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540302,TO_TIMESTAMP('2024-01-02 12:04:00.217','YYYY-MM-DD HH24:MI:SS.US'),100,year_id,TO_TIMESTAMP('2025-08-31','YYYY-MM-DD'),'Y','Aug.-25',8,'S','N',TO_TIMESTAMP('2025-08-01','YYYY-MM-DD'),TO_TIMESTAMP('2024-01-02 12:04:00.217','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:00.298144200Z
    INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540302 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
    ;

    -- 2024-01-02T11:04:00.417038400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551160,540302,TO_TIMESTAMP('2024-01-02 12:04:00.337','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:00.337','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:00.505780100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551161,540302,TO_TIMESTAMP('2024-01-02 12:04:00.422','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:00.422','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:00.601321900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551162,540302,TO_TIMESTAMP('2024-01-02 12:04:00.511','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:00.511','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:00.693102900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551163,540302,TO_TIMESTAMP('2024-01-02 12:04:00.607','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:00.607','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:00.783309Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551164,540302,TO_TIMESTAMP('2024-01-02 12:04:00.699','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:00.699','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:00.872367900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551165,540302,TO_TIMESTAMP('2024-01-02 12:04:00.787','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:00.787','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:00.962416200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551166,540302,TO_TIMESTAMP('2024-01-02 12:04:00.877','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:00.877','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:01.043118Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551167,540302,TO_TIMESTAMP('2024-01-02 12:04:00.968','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:00.968','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:01.126387600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551168,540302,TO_TIMESTAMP('2024-01-02 12:04:01.048','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:01.048','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:01.204262700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551169,540302,TO_TIMESTAMP('2024-01-02 12:04:01.131','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:01.131','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:01.292843400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551170,540302,TO_TIMESTAMP('2024-01-02 12:04:01.209','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:01.209','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:01.380424300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551171,540302,TO_TIMESTAMP('2024-01-02 12:04:01.299','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:01.299','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:01.486454200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551172,540302,TO_TIMESTAMP('2024-01-02 12:04:01.385','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:01.385','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:01.574228100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551173,540302,TO_TIMESTAMP('2024-01-02 12:04:01.491','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:01.491','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:01.663667Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551174,540302,TO_TIMESTAMP('2024-01-02 12:04:01.579','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:01.579','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:01.745384100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551175,540302,TO_TIMESTAMP('2024-01-02 12:04:01.669','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:01.669','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:01.834834800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551176,540302,TO_TIMESTAMP('2024-01-02 12:04:01.752','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:01.752','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:01.930887600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551177,540302,TO_TIMESTAMP('2024-01-02 12:04:01.842','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:01.842','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:02.017067400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551178,540302,TO_TIMESTAMP('2024-01-02 12:04:01.935','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:01.935','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:02.104901600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551179,540302,TO_TIMESTAMP('2024-01-02 12:04:02.021','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:02.021','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:02.194341100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551180,540302,TO_TIMESTAMP('2024-01-02 12:04:02.109','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:02.109','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:02.286470700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551181,540302,TO_TIMESTAMP('2024-01-02 12:04:02.2','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:02.2','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:02.381546300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551182,540302,TO_TIMESTAMP('2024-01-02 12:04:02.292','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:02.292','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:02.464691500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551183,540302,TO_TIMESTAMP('2024-01-02 12:04:02.386','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:02.386','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:02.557751800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551184,540302,TO_TIMESTAMP('2024-01-02 12:04:02.47','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:02.47','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:02.652100700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551185,540302,TO_TIMESTAMP('2024-01-02 12:04:02.563','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:02.563','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:02.747228700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551186,540302,TO_TIMESTAMP('2024-01-02 12:04:02.657','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:02.657','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:02.842859400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551187,540302,TO_TIMESTAMP('2024-01-02 12:04:02.752','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:02.752','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:02.957895900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551188,540302,TO_TIMESTAMP('2024-01-02 12:04:02.848','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:02.848','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:03.044482800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551189,540302,TO_TIMESTAMP('2024-01-02 12:04:02.963','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:02.963','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:03.137623300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551190,540302,TO_TIMESTAMP('2024-01-02 12:04:03.049','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:03.049','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:03.222646100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551191,540302,TO_TIMESTAMP('2024-01-02 12:04:03.143','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:03.143','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:03.299457500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551192,540302,TO_TIMESTAMP('2024-01-02 12:04:03.227','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:03.227','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:03.393649900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551193,540302,TO_TIMESTAMP('2024-01-02 12:04:03.304','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:03.304','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:03.483768700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551194,540302,TO_TIMESTAMP('2024-01-02 12:04:03.399','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:03.399','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:03.567461900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551195,540302,TO_TIMESTAMP('2024-01-02 12:04:03.489','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:03.489','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:03.678328Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551196,540302,TO_TIMESTAMP('2024-01-02 12:04:03.572','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:03.572','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:03.771206500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551197,540302,TO_TIMESTAMP('2024-01-02 12:04:03.683','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:03.683','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:03.861503400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551198,540302,TO_TIMESTAMP('2024-01-02 12:04:03.776','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:03.776','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:03.956173600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551199,540302,TO_TIMESTAMP('2024-01-02 12:04:03.867','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:03.867','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:04.059700500Z
    INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540303,TO_TIMESTAMP('2024-01-02 12:04:03.966','YYYY-MM-DD HH24:MI:SS.US'),100,year_id,TO_TIMESTAMP('2025-09-30','YYYY-MM-DD'),'Y','Sept.-25',9,'S','N',TO_TIMESTAMP('2025-09-01','YYYY-MM-DD'),TO_TIMESTAMP('2024-01-02 12:04:03.966','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:04.062986900Z
    INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540303 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
    ;

    -- 2024-01-02T11:04:04.182306100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551200,540303,TO_TIMESTAMP('2024-01-02 12:04:04.1','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:04.1','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:04.272991500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551201,540303,TO_TIMESTAMP('2024-01-02 12:04:04.187','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:04.187','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:04.362645800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551202,540303,TO_TIMESTAMP('2024-01-02 12:04:04.277','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:04.277','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:04.455627700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551203,540303,TO_TIMESTAMP('2024-01-02 12:04:04.367','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:04.367','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:04.547253400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551204,540303,TO_TIMESTAMP('2024-01-02 12:04:04.459','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:04.459','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:04.638640700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551205,540303,TO_TIMESTAMP('2024-01-02 12:04:04.553','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:04.553','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:04.733406300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551206,540303,TO_TIMESTAMP('2024-01-02 12:04:04.643','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:04.643','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:04.824161100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551207,540303,TO_TIMESTAMP('2024-01-02 12:04:04.738','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:04.738','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:04.922609300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551208,540303,TO_TIMESTAMP('2024-01-02 12:04:04.829','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:04.829','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:05.011688200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551209,540303,TO_TIMESTAMP('2024-01-02 12:04:04.927','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:04.927','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:05.104914Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551210,540303,TO_TIMESTAMP('2024-01-02 12:04:05.016','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:05.016','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:05.193267900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551211,540303,TO_TIMESTAMP('2024-01-02 12:04:05.109','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:05.109','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:05.284173700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551212,540303,TO_TIMESTAMP('2024-01-02 12:04:05.198','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:05.198','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:05.369307200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551213,540303,TO_TIMESTAMP('2024-01-02 12:04:05.288','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:05.288','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:05.460245700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551214,540303,TO_TIMESTAMP('2024-01-02 12:04:05.373','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:05.373','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:05.556861800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551215,540303,TO_TIMESTAMP('2024-01-02 12:04:05.466','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:05.466','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:05.648447200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551216,540303,TO_TIMESTAMP('2024-01-02 12:04:05.561','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:05.561','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:05.739554900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551217,540303,TO_TIMESTAMP('2024-01-02 12:04:05.654','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:05.654','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:05.837497100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551218,540303,TO_TIMESTAMP('2024-01-02 12:04:05.747','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:05.747','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:05.930762200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551219,540303,TO_TIMESTAMP('2024-01-02 12:04:05.842','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:05.842','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:06.026537800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551220,540303,TO_TIMESTAMP('2024-01-02 12:04:05.936','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:05.936','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:06.120319100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551221,540303,TO_TIMESTAMP('2024-01-02 12:04:06.031','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:06.031','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:06.206264100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551222,540303,TO_TIMESTAMP('2024-01-02 12:04:06.124','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:06.124','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:06.302712600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551223,540303,TO_TIMESTAMP('2024-01-02 12:04:06.211','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:06.211','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:06.392718400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551224,540303,TO_TIMESTAMP('2024-01-02 12:04:06.307','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:06.307','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:06.476039600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551225,540303,TO_TIMESTAMP('2024-01-02 12:04:06.397','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:06.397','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:06.569735300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551226,540303,TO_TIMESTAMP('2024-01-02 12:04:06.48','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:06.48','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:06.655748900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551227,540303,TO_TIMESTAMP('2024-01-02 12:04:06.573','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:06.573','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:06.757648Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551228,540303,TO_TIMESTAMP('2024-01-02 12:04:06.661','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:06.661','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:06.864670100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551229,540303,TO_TIMESTAMP('2024-01-02 12:04:06.763','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:06.763','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:06.953267Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551230,540303,TO_TIMESTAMP('2024-01-02 12:04:06.87','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:06.87','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:07.045102300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551231,540303,TO_TIMESTAMP('2024-01-02 12:04:06.958','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:06.958','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:07.127330200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551232,540303,TO_TIMESTAMP('2024-01-02 12:04:07.049','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:07.049','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:07.219496800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551233,540303,TO_TIMESTAMP('2024-01-02 12:04:07.132','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:07.132','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:07.313268600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551234,540303,TO_TIMESTAMP('2024-01-02 12:04:07.224','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:07.224','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:07.391835700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551235,540303,TO_TIMESTAMP('2024-01-02 12:04:07.319','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:07.319','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:07.485845100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551236,540303,TO_TIMESTAMP('2024-01-02 12:04:07.397','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:07.397','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:07.579003800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551237,540303,TO_TIMESTAMP('2024-01-02 12:04:07.492','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:07.492','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:07.668625500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551238,540303,TO_TIMESTAMP('2024-01-02 12:04:07.584','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:07.584','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:07.764671800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551239,540303,TO_TIMESTAMP('2024-01-02 12:04:07.673','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:07.673','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:07.856156200Z
    INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540304,TO_TIMESTAMP('2024-01-02 12:04:07.774','YYYY-MM-DD HH24:MI:SS.US'),100,year_id,TO_TIMESTAMP('2025-10-31','YYYY-MM-DD'),'Y','Okt.-25',10,'S','N',TO_TIMESTAMP('2025-10-01','YYYY-MM-DD'),TO_TIMESTAMP('2024-01-02 12:04:07.774','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:07.859962Z
    INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540304 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
    ;

    -- 2024-01-02T11:04:07.982033600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551240,540304,TO_TIMESTAMP('2024-01-02 12:04:07.896','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:07.896','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:08.074501Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551241,540304,TO_TIMESTAMP('2024-01-02 12:04:07.986','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:07.986','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:08.153199500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551242,540304,TO_TIMESTAMP('2024-01-02 12:04:08.079','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:08.079','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:08.232579800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551243,540304,TO_TIMESTAMP('2024-01-02 12:04:08.158','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:08.158','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:08.324768800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551244,540304,TO_TIMESTAMP('2024-01-02 12:04:08.237','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:08.237','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:08.405053900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551245,540304,TO_TIMESTAMP('2024-01-02 12:04:08.329','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:08.329','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:08.489580500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551246,540304,TO_TIMESTAMP('2024-01-02 12:04:08.41','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:08.41','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:08.576779700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551247,540304,TO_TIMESTAMP('2024-01-02 12:04:08.493','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:08.493','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:08.662543700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551248,540304,TO_TIMESTAMP('2024-01-02 12:04:08.582','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:08.582','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:08.753773800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551249,540304,TO_TIMESTAMP('2024-01-02 12:04:08.667','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:08.667','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:08.851255400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551250,540304,TO_TIMESTAMP('2024-01-02 12:04:08.759','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:08.759','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:08.942778100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551251,540304,TO_TIMESTAMP('2024-01-02 12:04:08.856','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:08.856','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:09.037892700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551252,540304,TO_TIMESTAMP('2024-01-02 12:04:08.949','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:08.949','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:09.136096200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551253,540304,TO_TIMESTAMP('2024-01-02 12:04:09.044','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:09.044','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:09.218338800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551254,540304,TO_TIMESTAMP('2024-01-02 12:04:09.142','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:09.142','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:09.311128300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551255,540304,TO_TIMESTAMP('2024-01-02 12:04:09.224','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:09.224','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:09.410699800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551256,540304,TO_TIMESTAMP('2024-01-02 12:04:09.316','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:09.316','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:09.505074200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551257,540304,TO_TIMESTAMP('2024-01-02 12:04:09.415','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:09.415','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:09.595526Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551258,540304,TO_TIMESTAMP('2024-01-02 12:04:09.51','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:09.51','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:09.688212800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551259,540304,TO_TIMESTAMP('2024-01-02 12:04:09.6','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:09.6','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:09.786561400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551260,540304,TO_TIMESTAMP('2024-01-02 12:04:09.693','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:09.693','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:09.879874300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551261,540304,TO_TIMESTAMP('2024-01-02 12:04:09.791','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:09.791','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:09.974021200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551262,540304,TO_TIMESTAMP('2024-01-02 12:04:09.885','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:09.885','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:10.056721500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551263,540304,TO_TIMESTAMP('2024-01-02 12:04:09.979','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:09.979','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:10.149231500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551264,540304,TO_TIMESTAMP('2024-01-02 12:04:10.062','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:10.062','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:10.250820Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551265,540304,TO_TIMESTAMP('2024-01-02 12:04:10.155','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:10.155','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:10.342256300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551266,540304,TO_TIMESTAMP('2024-01-02 12:04:10.256','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:10.256','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:10.438417800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551267,540304,TO_TIMESTAMP('2024-01-02 12:04:10.348','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:10.348','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:10.532192700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551268,540304,TO_TIMESTAMP('2024-01-02 12:04:10.444','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:10.444','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:10.624959300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551269,540304,TO_TIMESTAMP('2024-01-02 12:04:10.537','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:10.537','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:10.757725Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551270,540304,TO_TIMESTAMP('2024-01-02 12:04:10.63','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:10.63','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:10.849507200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551271,540304,TO_TIMESTAMP('2024-01-02 12:04:10.763','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:10.763','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:10.943636700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551272,540304,TO_TIMESTAMP('2024-01-02 12:04:10.854','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:10.854','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:11.035648900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551273,540304,TO_TIMESTAMP('2024-01-02 12:04:10.949','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:10.949','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:11.117004300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551274,540304,TO_TIMESTAMP('2024-01-02 12:04:11.04','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:11.04','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:11.210131800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551275,540304,TO_TIMESTAMP('2024-01-02 12:04:11.121','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:11.121','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:11.296210500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551276,540304,TO_TIMESTAMP('2024-01-02 12:04:11.215','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:11.215','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:11.382325600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551277,540304,TO_TIMESTAMP('2024-01-02 12:04:11.301','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:11.301','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:11.468030100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551278,540304,TO_TIMESTAMP('2024-01-02 12:04:11.387','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:11.387','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:11.560137600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551279,540304,TO_TIMESTAMP('2024-01-02 12:04:11.473','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:11.473','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:11.662965200Z
    INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540305,TO_TIMESTAMP('2024-01-02 12:04:11.571','YYYY-MM-DD HH24:MI:SS.US'),100,year_id,TO_TIMESTAMP('2025-11-30','YYYY-MM-DD'),'Y','Nov.-25',11,'S','N',TO_TIMESTAMP('2025-11-01','YYYY-MM-DD'),TO_TIMESTAMP('2024-01-02 12:04:11.571','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:11.666840Z
    INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540305 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
    ;

    -- 2024-01-02T11:04:11.792298600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551280,540305,TO_TIMESTAMP('2024-01-02 12:04:11.705','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:11.705','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:11.883269500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551281,540305,TO_TIMESTAMP('2024-01-02 12:04:11.797','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:11.797','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:11.973920400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551282,540305,TO_TIMESTAMP('2024-01-02 12:04:11.888','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:11.888','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:12.059226800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551283,540305,TO_TIMESTAMP('2024-01-02 12:04:11.978','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:11.978','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:12.149467300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551284,540305,TO_TIMESTAMP('2024-01-02 12:04:12.065','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:12.065','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:12.238602200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551285,540305,TO_TIMESTAMP('2024-01-02 12:04:12.154','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:12.154','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:12.314613700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551286,540305,TO_TIMESTAMP('2024-01-02 12:04:12.242','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:12.242','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:12.396287900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551287,540305,TO_TIMESTAMP('2024-01-02 12:04:12.32','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:12.32','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:12.486354500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551288,540305,TO_TIMESTAMP('2024-01-02 12:04:12.402','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:12.402','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:12.577045900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551289,540305,TO_TIMESTAMP('2024-01-02 12:04:12.491','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:12.491','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:12.721793700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551290,540305,TO_TIMESTAMP('2024-01-02 12:04:12.582','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:12.582','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:12.849541200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551291,540305,TO_TIMESTAMP('2024-01-02 12:04:12.729','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:12.729','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:13.002605700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551292,540305,TO_TIMESTAMP('2024-01-02 12:04:12.888','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:12.888','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:13.387496700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551293,540305,TO_TIMESTAMP('2024-01-02 12:04:13.023','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:13.023','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:13.496783900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551294,540305,TO_TIMESTAMP('2024-01-02 12:04:13.397','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:13.397','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:13.597673900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551295,540305,TO_TIMESTAMP('2024-01-02 12:04:13.503','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:13.503','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:13.693186100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551296,540305,TO_TIMESTAMP('2024-01-02 12:04:13.603','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:13.603','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:13.788985800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551297,540305,TO_TIMESTAMP('2024-01-02 12:04:13.699','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:13.699','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:13.881239500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551298,540305,TO_TIMESTAMP('2024-01-02 12:04:13.795','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:13.795','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:13.976307900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551299,540305,TO_TIMESTAMP('2024-01-02 12:04:13.886','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:13.886','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:14.062143300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551300,540305,TO_TIMESTAMP('2024-01-02 12:04:13.981','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:13.981','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:14.154860700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551301,540305,TO_TIMESTAMP('2024-01-02 12:04:14.066','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:14.066','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:14.242102900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551302,540305,TO_TIMESTAMP('2024-01-02 12:04:14.159','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:14.159','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:14.352134400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551303,540305,TO_TIMESTAMP('2024-01-02 12:04:14.25','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:14.25','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:14.451211600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551304,540305,TO_TIMESTAMP('2024-01-02 12:04:14.36','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:14.36','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:14.554034300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551305,540305,TO_TIMESTAMP('2024-01-02 12:04:14.461','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:14.461','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:14.657570600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551306,540305,TO_TIMESTAMP('2024-01-02 12:04:14.563','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:14.563','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:14.772406300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551307,540305,TO_TIMESTAMP('2024-01-02 12:04:14.667','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:14.667','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:14.874952400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551308,540305,TO_TIMESTAMP('2024-01-02 12:04:14.781','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:14.781','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:14.986743Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551309,540305,TO_TIMESTAMP('2024-01-02 12:04:14.884','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:14.884','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:15.131588100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551310,540305,TO_TIMESTAMP('2024-01-02 12:04:14.996','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:14.996','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:15.236391700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551311,540305,TO_TIMESTAMP('2024-01-02 12:04:15.142','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:15.142','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:15.345048300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551312,540305,TO_TIMESTAMP('2024-01-02 12:04:15.246','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:15.246','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:15.448318500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551313,540305,TO_TIMESTAMP('2024-01-02 12:04:15.355','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:15.355','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:15.572477600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551314,540305,TO_TIMESTAMP('2024-01-02 12:04:15.458','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:15.458','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:15.683570400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551315,540305,TO_TIMESTAMP('2024-01-02 12:04:15.583','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:15.583','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:15.796106100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551316,540305,TO_TIMESTAMP('2024-01-02 12:04:15.692','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:15.692','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:15.903292800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551317,540305,TO_TIMESTAMP('2024-01-02 12:04:15.805','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:15.805','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:15.996904400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551318,540305,TO_TIMESTAMP('2024-01-02 12:04:15.912','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:15.912','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:16.098105300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551319,540305,TO_TIMESTAMP('2024-01-02 12:04:16.006','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:16.006','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:16.220680Z
    INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540306,TO_TIMESTAMP('2024-01-02 12:04:16.116','YYYY-MM-DD HH24:MI:SS.US'),100,year_id,TO_TIMESTAMP('2025-12-31','YYYY-MM-DD'),'Y','Dez.-25',12,'S','N',TO_TIMESTAMP('2025-12-01','YYYY-MM-DD'),TO_TIMESTAMP('2024-01-02 12:04:16.116','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:16.228113800Z
    INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540306 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
    ;

    -- 2024-01-02T11:04:16.423933300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551320,540306,TO_TIMESTAMP('2024-01-02 12:04:16.331','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:16.331','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:16.527522300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551321,540306,TO_TIMESTAMP('2024-01-02 12:04:16.433','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:16.433','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:16.627328900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551322,540306,TO_TIMESTAMP('2024-01-02 12:04:16.537','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:16.537','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:16.733314800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551323,540306,TO_TIMESTAMP('2024-01-02 12:04:16.637','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:16.637','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:16.837608Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551324,540306,TO_TIMESTAMP('2024-01-02 12:04:16.742','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:16.742','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:16.941950600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551325,540306,TO_TIMESTAMP('2024-01-02 12:04:16.847','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:16.847','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:17.044924200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551326,540306,TO_TIMESTAMP('2024-01-02 12:04:16.951','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:16.951','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:17.143559900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551327,540306,TO_TIMESTAMP('2024-01-02 12:04:17.054','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:17.054','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:17.242682700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551328,540306,TO_TIMESTAMP('2024-01-02 12:04:17.153','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:17.153','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:17.353828200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551329,540306,TO_TIMESTAMP('2024-01-02 12:04:17.252','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:17.252','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:17.449936300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551330,540306,TO_TIMESTAMP('2024-01-02 12:04:17.362','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:17.362','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:17.546786100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551331,540306,TO_TIMESTAMP('2024-01-02 12:04:17.459','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:17.459','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:17.650148600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551332,540306,TO_TIMESTAMP('2024-01-02 12:04:17.556','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:17.556','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:17.748703500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551333,540306,TO_TIMESTAMP('2024-01-02 12:04:17.658','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:17.658','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:17.852653300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551334,540306,TO_TIMESTAMP('2024-01-02 12:04:17.758','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:17.758','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:17.963152400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551335,540306,TO_TIMESTAMP('2024-01-02 12:04:17.861','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:17.861','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:18.235131300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551336,540306,TO_TIMESTAMP('2024-01-02 12:04:17.972','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:17.972','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:18.339664300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551337,540306,TO_TIMESTAMP('2024-01-02 12:04:18.242','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:18.242','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:18.470377900Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551338,540306,TO_TIMESTAMP('2024-01-02 12:04:18.351','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:18.351','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:18.561544800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551339,540306,TO_TIMESTAMP('2024-01-02 12:04:18.476','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:18.476','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:18.660099100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551340,540306,TO_TIMESTAMP('2024-01-02 12:04:18.567','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:18.567','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:18.742345400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551341,540306,TO_TIMESTAMP('2024-01-02 12:04:18.665','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:18.665','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:18.842309300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551342,540306,TO_TIMESTAMP('2024-01-02 12:04:18.751','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:18.751','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:18.937260200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551343,540306,TO_TIMESTAMP('2024-01-02 12:04:18.848','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:18.848','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:19.065481400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551344,540306,TO_TIMESTAMP('2024-01-02 12:04:18.943','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:18.943','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:19.210623Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551345,540306,TO_TIMESTAMP('2024-01-02 12:04:19.086','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:19.086','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:19.363922700Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551346,540306,TO_TIMESTAMP('2024-01-02 12:04:19.219','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:19.219','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:19.625610100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551347,540306,TO_TIMESTAMP('2024-01-02 12:04:19.423','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:19.423','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:19.779672200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551348,540306,TO_TIMESTAMP('2024-01-02 12:04:19.649','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:19.649','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:20.015328400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551349,540306,TO_TIMESTAMP('2024-01-02 12:04:19.785','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:19.785','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:20.221313100Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551350,540306,TO_TIMESTAMP('2024-01-02 12:04:20.028','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:20.028','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:20.386783400Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551351,540306,TO_TIMESTAMP('2024-01-02 12:04:20.238','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:20.238','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:20.521259300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551352,540306,TO_TIMESTAMP('2024-01-02 12:04:20.392','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:20.392','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:20.630734600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551353,540306,TO_TIMESTAMP('2024-01-02 12:04:20.526','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:20.526','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:20.809983500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551354,540306,TO_TIMESTAMP('2024-01-02 12:04:20.646','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:20.646','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:20.911666600Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551355,540306,TO_TIMESTAMP('2024-01-02 12:04:20.819','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:20.819','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:21.017415200Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551356,540306,TO_TIMESTAMP('2024-01-02 12:04:20.92','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:20.92','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:21.150232800Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551357,540306,TO_TIMESTAMP('2024-01-02 12:04:21.024','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:21.024','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:21.267796300Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551358,540306,TO_TIMESTAMP('2024-01-02 12:04:21.159','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:21.159','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

    -- 2024-01-02T11:04:21.397736500Z
    INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,551359,540306,TO_TIMESTAMP('2024-01-02 12:04:21.275','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2024-01-02 12:04:21.275','YYYY-MM-DD HH24:MI:SS.US'),100)
    ;

EXCEPTION WHEN unique_violation THEN
RAISE NOTICE 'At least one period of 2025 already exists';

end $$;
