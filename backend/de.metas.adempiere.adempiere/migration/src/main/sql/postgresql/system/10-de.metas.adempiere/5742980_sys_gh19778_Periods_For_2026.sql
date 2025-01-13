--- Jan.-26

DO
$$
    DECLARE
        year_id numeric;
BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2026' and c_calendar_id = 1000000);
        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2026-01-01', 'YYYY-MM-DD') and enddate = TO_TIMESTAMP('2026-01-31', 'YYYY-MM-DD')) THEN

            -- 2025-01-13T09:33:55.785Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540343,year_id,TO_TIMESTAMP('2025-01-13 09:33:55.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-01-31','YYYY-MM-DD'),'Y','Jan.-26',1,'S','N',TO_TIMESTAMP('2026-01-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:33:55.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:55.839Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540343 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:33:56.026Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552872,TO_TIMESTAMP('2025-01-13 09:33:55.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:55.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:56.118Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552873,TO_TIMESTAMP('2025-01-13 09:33:56.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:56.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:56.210Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552874,TO_TIMESTAMP('2025-01-13 09:33:56.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:56.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:56.292Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552875,TO_TIMESTAMP('2025-01-13 09:33:56.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:56.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:56.383Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552876,TO_TIMESTAMP('2025-01-13 09:33:56.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:56.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:56.476Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552877,TO_TIMESTAMP('2025-01-13 09:33:56.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:56.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:56.557Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552878,TO_TIMESTAMP('2025-01-13 09:33:56.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:56.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:56.640Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552879,TO_TIMESTAMP('2025-01-13 09:33:56.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:56.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:56.731Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552880,TO_TIMESTAMP('2025-01-13 09:33:56.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:56.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:56.821Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552881,TO_TIMESTAMP('2025-01-13 09:33:56.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:56.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:56.901Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552882,TO_TIMESTAMP('2025-01-13 09:33:56.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:56.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:56.984Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552883,TO_TIMESTAMP('2025-01-13 09:33:56.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:56.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:57.073Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552884,TO_TIMESTAMP('2025-01-13 09:33:56.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:56.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:57.157Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552885,TO_TIMESTAMP('2025-01-13 09:33:57.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:57.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:57.247Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552886,TO_TIMESTAMP('2025-01-13 09:33:57.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:57.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:57.338Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552887,TO_TIMESTAMP('2025-01-13 09:33:57.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:57.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:57.428Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552888,TO_TIMESTAMP('2025-01-13 09:33:57.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:57.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:57.512Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552889,TO_TIMESTAMP('2025-01-13 09:33:57.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:57.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:57.598Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552890,TO_TIMESTAMP('2025-01-13 09:33:57.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:57.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:57.692Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552891,TO_TIMESTAMP('2025-01-13 09:33:57.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:57.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:57.778Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552892,TO_TIMESTAMP('2025-01-13 09:33:57.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:57.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:57.864Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552893,TO_TIMESTAMP('2025-01-13 09:33:57.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:57.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:57.954Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552894,TO_TIMESTAMP('2025-01-13 09:33:57.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:57.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:58.040Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552895,TO_TIMESTAMP('2025-01-13 09:33:57.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:57.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:58.123Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552896,TO_TIMESTAMP('2025-01-13 09:33:58.046000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:58.046000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:58.208Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552897,TO_TIMESTAMP('2025-01-13 09:33:58.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:58.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:58.290Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552898,TO_TIMESTAMP('2025-01-13 09:33:58.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:58.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:58.487Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552899,TO_TIMESTAMP('2025-01-13 09:33:58.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:58.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:58.578Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552900,TO_TIMESTAMP('2025-01-13 09:33:58.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:58.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:58.663Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552901,TO_TIMESTAMP('2025-01-13 09:33:58.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:58.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:58.756Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552902,TO_TIMESTAMP('2025-01-13 09:33:58.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:58.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:58.843Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552903,TO_TIMESTAMP('2025-01-13 09:33:58.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:58.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:58.928Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552904,TO_TIMESTAMP('2025-01-13 09:33:58.848000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:58.848000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:59.023Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552905,TO_TIMESTAMP('2025-01-13 09:33:58.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:58.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:59.111Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552906,TO_TIMESTAMP('2025-01-13 09:33:59.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:59.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:59.195Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552907,TO_TIMESTAMP('2025-01-13 09:33:59.113000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:59.113000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:59.292Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552908,TO_TIMESTAMP('2025-01-13 09:33:59.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:59.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:59.390Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552909,TO_TIMESTAMP('2025-01-13 09:33:59.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:59.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:59.489Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552910,TO_TIMESTAMP('2025-01-13 09:33:59.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:59.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:33:59.576Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540343,552911,TO_TIMESTAMP('2025-01-13 09:33:59.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:33:59.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
END
$$;

--- Feb.-26

DO
$$
    DECLARE
        year_id numeric;
    
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2026' and c_calendar_id = 1000000);
        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2026-02-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2026-02-28','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:34:49.591Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540344,year_id,TO_TIMESTAMP('2025-01-13 09:34:49.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-02-28','YYYY-MM-DD'),'Y','Feb.-26',2,'S','N',TO_TIMESTAMP('2026-02-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:34:49.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:49.596Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540344 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:34:49.764Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552912,TO_TIMESTAMP('2025-01-13 09:34:49.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:49.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:49.861Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552913,TO_TIMESTAMP('2025-01-13 09:34:49.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:49.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:49.964Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552914,TO_TIMESTAMP('2025-01-13 09:34:49.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:49.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:50.063Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552915,TO_TIMESTAMP('2025-01-13 09:34:49.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:49.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:50.161Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552916,TO_TIMESTAMP('2025-01-13 09:34:50.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:50.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:50.254Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552917,TO_TIMESTAMP('2025-01-13 09:34:50.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:50.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:50.346Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552918,TO_TIMESTAMP('2025-01-13 09:34:50.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:50.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:50.446Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552919,TO_TIMESTAMP('2025-01-13 09:34:50.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:50.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:50.546Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552920,TO_TIMESTAMP('2025-01-13 09:34:50.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:50.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:50.646Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552921,TO_TIMESTAMP('2025-01-13 09:34:50.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:50.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:50.742Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552922,TO_TIMESTAMP('2025-01-13 09:34:50.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:50.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:50.839Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552923,TO_TIMESTAMP('2025-01-13 09:34:50.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:50.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:50.932Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552924,TO_TIMESTAMP('2025-01-13 09:34:50.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:50.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:51.028Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552925,TO_TIMESTAMP('2025-01-13 09:34:50.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:50.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:51.129Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552926,TO_TIMESTAMP('2025-01-13 09:34:51.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:51.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:51.228Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552927,TO_TIMESTAMP('2025-01-13 09:34:51.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:51.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:51.317Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552928,TO_TIMESTAMP('2025-01-13 09:34:51.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:51.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:51.416Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552929,TO_TIMESTAMP('2025-01-13 09:34:51.321000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:51.321000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:51.509Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552930,TO_TIMESTAMP('2025-01-13 09:34:51.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:51.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:51.614Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552931,TO_TIMESTAMP('2025-01-13 09:34:51.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:51.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:51.710Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552932,TO_TIMESTAMP('2025-01-13 09:34:51.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:51.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:51.811Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552933,TO_TIMESTAMP('2025-01-13 09:34:51.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:51.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:51.906Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552934,TO_TIMESTAMP('2025-01-13 09:34:51.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:51.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:52.006Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552935,TO_TIMESTAMP('2025-01-13 09:34:51.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:51.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:52.097Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552936,TO_TIMESTAMP('2025-01-13 09:34:52.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:52.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:52.186Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552937,TO_TIMESTAMP('2025-01-13 09:34:52.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:52.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:52.280Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552938,TO_TIMESTAMP('2025-01-13 09:34:52.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:52.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:52.382Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552939,TO_TIMESTAMP('2025-01-13 09:34:52.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:52.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:52.474Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552940,TO_TIMESTAMP('2025-01-13 09:34:52.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:52.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:52.573Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552941,TO_TIMESTAMP('2025-01-13 09:34:52.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:52.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:52.673Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552942,TO_TIMESTAMP('2025-01-13 09:34:52.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:52.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:52.769Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552943,TO_TIMESTAMP('2025-01-13 09:34:52.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:52.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:52.867Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552944,TO_TIMESTAMP('2025-01-13 09:34:52.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:52.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:52.964Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552945,TO_TIMESTAMP('2025-01-13 09:34:52.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:52.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:53.068Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552946,TO_TIMESTAMP('2025-01-13 09:34:52.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:52.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:53.169Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552947,TO_TIMESTAMP('2025-01-13 09:34:53.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:53.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:53.264Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552948,TO_TIMESTAMP('2025-01-13 09:34:53.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:53.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:53.354Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552949,TO_TIMESTAMP('2025-01-13 09:34:53.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:53.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:53.451Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552950,TO_TIMESTAMP('2025-01-13 09:34:53.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:53.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:34:53.546Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540344,552951,TO_TIMESTAMP('2025-01-13 09:34:53.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:34:53.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:35:17.082Z
            UPDATE C_Period SET EndDate=TO_TIMESTAMP('2026-02-28','YYYY-MM-DD'), Name='Feb.-26', StartDate=TO_TIMESTAMP('2026-02-01','YYYY-MM-DD'),Updated=TO_TIMESTAMP('2025-01-13 09:35:17.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Period_ID=540344
            ;

            -- 2025-01-13T09:35:17.091Z
            UPDATE C_Period_Trl trl SET Name='Feb.-26' WHERE C_Period_ID=540344 AND (IsTranslated='N' OR AD_Language='de_DE')
            ;

            -- 2025-01-13T09:35:30.004Z
            UPDATE C_Period SET EndDate=TO_TIMESTAMP('2026-02-28','YYYY-MM-DD'), Name='Feb.-26', StartDate=TO_TIMESTAMP('2026-02-01','YYYY-MM-DD'),Updated=TO_TIMESTAMP('2025-01-13 09:35:30.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Period_ID=540344
            ;

            -- 2025-01-13T09:35:30.006Z
            UPDATE C_Period_Trl trl SET Name='Feb.-26' WHERE C_Period_ID=540344 AND (IsTranslated='N' OR AD_Language='de_DE')
            ;

        END IF;
    END
$$;

--- März-26

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2026' and c_calendar_id = 1000000);
        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2026-03-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2026-03-31','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:36:35.402Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540345,year_id,TO_TIMESTAMP('2025-01-13 09:36:35.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-03-31','YYYY-MM-DD'),'Y','März-26',3,'S','N',TO_TIMESTAMP('2026-03-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:36:35.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:35.405Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540345 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:36:35.554Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552952,TO_TIMESTAMP('2025-01-13 09:36:35.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:35.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:35.641Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552953,TO_TIMESTAMP('2025-01-13 09:36:35.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:35.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:35.729Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552954,TO_TIMESTAMP('2025-01-13 09:36:35.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:35.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:35.821Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552955,TO_TIMESTAMP('2025-01-13 09:36:35.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:35.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:35.898Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552956,TO_TIMESTAMP('2025-01-13 09:36:35.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:35.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:35.981Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552957,TO_TIMESTAMP('2025-01-13 09:36:35.901000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:35.901000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:36.064Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552958,TO_TIMESTAMP('2025-01-13 09:36:35.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:35.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:36.149Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552959,TO_TIMESTAMP('2025-01-13 09:36:36.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:36.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:36.237Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552960,TO_TIMESTAMP('2025-01-13 09:36:36.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:36.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:36.321Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552961,TO_TIMESTAMP('2025-01-13 09:36:36.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:36.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:36.406Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552962,TO_TIMESTAMP('2025-01-13 09:36:36.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:36.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:36.484Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552963,TO_TIMESTAMP('2025-01-13 09:36:36.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:36.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:36.573Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552964,TO_TIMESTAMP('2025-01-13 09:36:36.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:36.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:36.665Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552965,TO_TIMESTAMP('2025-01-13 09:36:36.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:36.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:36.753Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552966,TO_TIMESTAMP('2025-01-13 09:36:36.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:36.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:36.836Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552967,TO_TIMESTAMP('2025-01-13 09:36:36.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:36.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:36.917Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552968,TO_TIMESTAMP('2025-01-13 09:36:36.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:36.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:37.011Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552969,TO_TIMESTAMP('2025-01-13 09:36:36.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:36.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:37.104Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552970,TO_TIMESTAMP('2025-01-13 09:36:37.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:37.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:37.191Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552971,TO_TIMESTAMP('2025-01-13 09:36:37.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:37.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:37.285Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552972,TO_TIMESTAMP('2025-01-13 09:36:37.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:37.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:37.367Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552973,TO_TIMESTAMP('2025-01-13 09:36:37.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:37.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:37.448Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552974,TO_TIMESTAMP('2025-01-13 09:36:37.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:37.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:37.527Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552975,TO_TIMESTAMP('2025-01-13 09:36:37.451000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:37.451000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:37.604Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552976,TO_TIMESTAMP('2025-01-13 09:36:37.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:37.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:37.696Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552977,TO_TIMESTAMP('2025-01-13 09:36:37.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:37.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:37.787Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552978,TO_TIMESTAMP('2025-01-13 09:36:37.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:37.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:37.881Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552979,TO_TIMESTAMP('2025-01-13 09:36:37.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:37.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:37.979Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552980,TO_TIMESTAMP('2025-01-13 09:36:37.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:37.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:38.060Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552981,TO_TIMESTAMP('2025-01-13 09:36:37.981000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:37.981000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:38.149Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552982,TO_TIMESTAMP('2025-01-13 09:36:38.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:38.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:38.239Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552983,TO_TIMESTAMP('2025-01-13 09:36:38.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:38.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:38.326Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552984,TO_TIMESTAMP('2025-01-13 09:36:38.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:38.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:38.409Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552985,TO_TIMESTAMP('2025-01-13 09:36:38.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:38.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:38.492Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552986,TO_TIMESTAMP('2025-01-13 09:36:38.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:38.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:38.571Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552987,TO_TIMESTAMP('2025-01-13 09:36:38.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:38.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:38.662Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552988,TO_TIMESTAMP('2025-01-13 09:36:38.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:38.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:38.750Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552989,TO_TIMESTAMP('2025-01-13 09:36:38.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:38.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:38.836Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552990,TO_TIMESTAMP('2025-01-13 09:36:38.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:38.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:36:38.917Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540345,552991,TO_TIMESTAMP('2025-01-13 09:36:38.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:36:38.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Apr.-26

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2026' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2026-04-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2026-04-30','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:37:24.631Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540346,year_id,TO_TIMESTAMP('2025-01-13 09:37:24.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-04-30','YYYY-MM-DD'),'Y','Apr.-26',4,'S','N',TO_TIMESTAMP('2026-04-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:37:24.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:24.634Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540346 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:37:24.799Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,552992,TO_TIMESTAMP('2025-01-13 09:37:24.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:24.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:24.899Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,552993,TO_TIMESTAMP('2025-01-13 09:37:24.801000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:24.801000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:25.009Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,552994,TO_TIMESTAMP('2025-01-13 09:37:24.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:24.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:25.118Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,552995,TO_TIMESTAMP('2025-01-13 09:37:25.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:25.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:25.218Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,552996,TO_TIMESTAMP('2025-01-13 09:37:25.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:25.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:25.318Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,552997,TO_TIMESTAMP('2025-01-13 09:37:25.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:25.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:25.417Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,552998,TO_TIMESTAMP('2025-01-13 09:37:25.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:25.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:25.531Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,552999,TO_TIMESTAMP('2025-01-13 09:37:25.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:25.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:25.629Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553000,TO_TIMESTAMP('2025-01-13 09:37:25.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:25.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:25.730Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553001,TO_TIMESTAMP('2025-01-13 09:37:25.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:25.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:25.835Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553002,TO_TIMESTAMP('2025-01-13 09:37:25.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:25.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:25.949Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553003,TO_TIMESTAMP('2025-01-13 09:37:25.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:25.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:26.054Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553004,TO_TIMESTAMP('2025-01-13 09:37:25.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:25.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:26.162Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553005,TO_TIMESTAMP('2025-01-13 09:37:26.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:26.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:26.264Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553006,TO_TIMESTAMP('2025-01-13 09:37:26.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:26.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:26.360Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553007,TO_TIMESTAMP('2025-01-13 09:37:26.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:26.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:26.464Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553008,TO_TIMESTAMP('2025-01-13 09:37:26.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:26.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:26.560Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553009,TO_TIMESTAMP('2025-01-13 09:37:26.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:26.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:26.659Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553010,TO_TIMESTAMP('2025-01-13 09:37:26.565000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:26.565000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:26.772Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553011,TO_TIMESTAMP('2025-01-13 09:37:26.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:26.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:26.870Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553012,TO_TIMESTAMP('2025-01-13 09:37:26.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:26.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:26.964Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553013,TO_TIMESTAMP('2025-01-13 09:37:26.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:26.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:27.064Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553014,TO_TIMESTAMP('2025-01-13 09:37:26.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:26.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:27.168Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553015,TO_TIMESTAMP('2025-01-13 09:37:27.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:27.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:27.272Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553016,TO_TIMESTAMP('2025-01-13 09:37:27.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:27.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:27.368Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553017,TO_TIMESTAMP('2025-01-13 09:37:27.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:27.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:27.464Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553018,TO_TIMESTAMP('2025-01-13 09:37:27.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:27.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:27.569Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553019,TO_TIMESTAMP('2025-01-13 09:37:27.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:27.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:27.659Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553020,TO_TIMESTAMP('2025-01-13 09:37:27.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:27.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:27.755Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553021,TO_TIMESTAMP('2025-01-13 09:37:27.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:27.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:27.846Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553022,TO_TIMESTAMP('2025-01-13 09:37:27.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:27.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:27.939Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553023,TO_TIMESTAMP('2025-01-13 09:37:27.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:27.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:28.026Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553024,TO_TIMESTAMP('2025-01-13 09:37:27.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:27.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:28.114Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553025,TO_TIMESTAMP('2025-01-13 09:37:28.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:28.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:28.205Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553026,TO_TIMESTAMP('2025-01-13 09:37:28.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:28.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:28.297Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553027,TO_TIMESTAMP('2025-01-13 09:37:28.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:28.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:28.385Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553028,TO_TIMESTAMP('2025-01-13 09:37:28.301000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:28.301000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:28.475Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553029,TO_TIMESTAMP('2025-01-13 09:37:28.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:28.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:28.572Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553030,TO_TIMESTAMP('2025-01-13 09:37:28.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:28.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:37:28.673Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540346,553031,TO_TIMESTAMP('2025-01-13 09:37:28.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:37:28.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Mai-26

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2026' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2026-05-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2026-05-31','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:38:06.488Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540347,year_id,TO_TIMESTAMP('2025-01-13 09:38:06.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-05-31','YYYY-MM-DD'),'Y','Mai-26',5,'S','N',TO_TIMESTAMP('2026-05-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:38:06.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:06.492Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540347 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:38:06.624Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553032,TO_TIMESTAMP('2025-01-13 09:38:06.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:06.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:06.714Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553033,TO_TIMESTAMP('2025-01-13 09:38:06.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:06.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:06.800Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553034,TO_TIMESTAMP('2025-01-13 09:38:06.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:06.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:06.887Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553035,TO_TIMESTAMP('2025-01-13 09:38:06.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:06.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:06.972Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553036,TO_TIMESTAMP('2025-01-13 09:38:06.889000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:06.889000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:07.055Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553037,TO_TIMESTAMP('2025-01-13 09:38:06.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:06.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:07.141Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553038,TO_TIMESTAMP('2025-01-13 09:38:07.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:07.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:07.231Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553039,TO_TIMESTAMP('2025-01-13 09:38:07.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:07.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:07.319Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553040,TO_TIMESTAMP('2025-01-13 09:38:07.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:07.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:07.412Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553041,TO_TIMESTAMP('2025-01-13 09:38:07.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:07.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:07.497Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553042,TO_TIMESTAMP('2025-01-13 09:38:07.416000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:07.416000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:07.585Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553043,TO_TIMESTAMP('2025-01-13 09:38:07.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:07.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:07.672Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553044,TO_TIMESTAMP('2025-01-13 09:38:07.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:07.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:07.758Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553045,TO_TIMESTAMP('2025-01-13 09:38:07.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:07.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:07.843Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553046,TO_TIMESTAMP('2025-01-13 09:38:07.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:07.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:07.923Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553047,TO_TIMESTAMP('2025-01-13 09:38:07.848000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:07.848000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:08.009Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553048,TO_TIMESTAMP('2025-01-13 09:38:07.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:07.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:08.094Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553049,TO_TIMESTAMP('2025-01-13 09:38:08.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:08.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:08.180Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553050,TO_TIMESTAMP('2025-01-13 09:38:08.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:08.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:08.381Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553051,TO_TIMESTAMP('2025-01-13 09:38:08.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:08.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:08.467Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553052,TO_TIMESTAMP('2025-01-13 09:38:08.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:08.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:08.560Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553053,TO_TIMESTAMP('2025-01-13 09:38:08.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:08.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:08.648Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553054,TO_TIMESTAMP('2025-01-13 09:38:08.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:08.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:08.746Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553055,TO_TIMESTAMP('2025-01-13 09:38:08.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:08.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:08.833Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553056,TO_TIMESTAMP('2025-01-13 09:38:08.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:08.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:08.915Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553057,TO_TIMESTAMP('2025-01-13 09:38:08.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:08.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:09.001Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553058,TO_TIMESTAMP('2025-01-13 09:38:08.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:08.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:09.082Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553059,TO_TIMESTAMP('2025-01-13 09:38:09.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:09.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:09.162Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553060,TO_TIMESTAMP('2025-01-13 09:38:09.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:09.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:09.268Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553061,TO_TIMESTAMP('2025-01-13 09:38:09.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:09.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:09.354Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553062,TO_TIMESTAMP('2025-01-13 09:38:09.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:09.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:09.444Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553063,TO_TIMESTAMP('2025-01-13 09:38:09.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:09.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:09.532Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553064,TO_TIMESTAMP('2025-01-13 09:38:09.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:09.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:09.617Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553065,TO_TIMESTAMP('2025-01-13 09:38:09.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:09.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:09.700Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553066,TO_TIMESTAMP('2025-01-13 09:38:09.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:09.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:09.787Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553067,TO_TIMESTAMP('2025-01-13 09:38:09.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:09.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:09.869Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553068,TO_TIMESTAMP('2025-01-13 09:38:09.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:09.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:09.951Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553069,TO_TIMESTAMP('2025-01-13 09:38:09.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:09.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:10.035Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553070,TO_TIMESTAMP('2025-01-13 09:38:09.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:09.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:10.117Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540347,553071,TO_TIMESTAMP('2025-01-13 09:38:10.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:10.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Juni-26

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2026' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2026-06-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2026-06-30','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:38:47.139Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540348,year_id,TO_TIMESTAMP('2025-01-13 09:38:47.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-06-30','YYYY-MM-DD'),'Y','Juni-26',6,'S','N',TO_TIMESTAMP('2026-06-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:38:47.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:47.142Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540348 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:38:47.309Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553072,TO_TIMESTAMP('2025-01-13 09:38:47.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:47.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:47.395Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553073,TO_TIMESTAMP('2025-01-13 09:38:47.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:47.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:47.478Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553074,TO_TIMESTAMP('2025-01-13 09:38:47.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:47.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:47.556Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553075,TO_TIMESTAMP('2025-01-13 09:38:47.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:47.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:47.641Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553076,TO_TIMESTAMP('2025-01-13 09:38:47.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:47.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:47.722Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553077,TO_TIMESTAMP('2025-01-13 09:38:47.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:47.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:47.809Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553078,TO_TIMESTAMP('2025-01-13 09:38:47.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:47.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:47.892Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553079,TO_TIMESTAMP('2025-01-13 09:38:47.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:47.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:47.988Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553080,TO_TIMESTAMP('2025-01-13 09:38:47.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:47.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:48.074Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553081,TO_TIMESTAMP('2025-01-13 09:38:47.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:47.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:48.160Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553082,TO_TIMESTAMP('2025-01-13 09:38:48.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:48.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:48.244Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553083,TO_TIMESTAMP('2025-01-13 09:38:48.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:48.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:48.332Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553084,TO_TIMESTAMP('2025-01-13 09:38:48.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:48.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:48.422Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553085,TO_TIMESTAMP('2025-01-13 09:38:48.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:48.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:48.507Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553086,TO_TIMESTAMP('2025-01-13 09:38:48.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:48.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:48.584Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553087,TO_TIMESTAMP('2025-01-13 09:38:48.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:48.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:48.675Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553088,TO_TIMESTAMP('2025-01-13 09:38:48.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:48.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:48.757Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553089,TO_TIMESTAMP('2025-01-13 09:38:48.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:48.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:48.866Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553090,TO_TIMESTAMP('2025-01-13 09:38:48.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:48.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:48.946Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553091,TO_TIMESTAMP('2025-01-13 09:38:48.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:48.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:49.028Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553092,TO_TIMESTAMP('2025-01-13 09:38:48.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:48.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:49.115Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553093,TO_TIMESTAMP('2025-01-13 09:38:49.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:49.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:49.200Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553094,TO_TIMESTAMP('2025-01-13 09:38:49.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:49.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:49.289Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553095,TO_TIMESTAMP('2025-01-13 09:38:49.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:49.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:49.371Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553096,TO_TIMESTAMP('2025-01-13 09:38:49.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:49.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:49.453Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553097,TO_TIMESTAMP('2025-01-13 09:38:49.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:49.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:49.547Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553098,TO_TIMESTAMP('2025-01-13 09:38:49.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:49.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:49.632Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553099,TO_TIMESTAMP('2025-01-13 09:38:49.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:49.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:49.720Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553100,TO_TIMESTAMP('2025-01-13 09:38:49.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:49.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:49.807Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553101,TO_TIMESTAMP('2025-01-13 09:38:49.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:49.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:49.896Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553102,TO_TIMESTAMP('2025-01-13 09:38:49.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:49.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:49.983Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553103,TO_TIMESTAMP('2025-01-13 09:38:49.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:49.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:50.066Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553104,TO_TIMESTAMP('2025-01-13 09:38:49.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:49.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:50.163Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553105,TO_TIMESTAMP('2025-01-13 09:38:50.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:50.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:50.259Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553106,TO_TIMESTAMP('2025-01-13 09:38:50.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:50.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:50.347Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553107,TO_TIMESTAMP('2025-01-13 09:38:50.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:50.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:50.451Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553108,TO_TIMESTAMP('2025-01-13 09:38:50.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:50.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:50.535Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553109,TO_TIMESTAMP('2025-01-13 09:38:50.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:50.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:50.618Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553110,TO_TIMESTAMP('2025-01-13 09:38:50.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:50.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:38:50.706Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540348,553111,TO_TIMESTAMP('2025-01-13 09:38:50.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:38:50.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Juli-26

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2026' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2026-07-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2026-07-31','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:39:26.416Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540349,year_id,TO_TIMESTAMP('2025-01-13 09:39:26.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-07-31','YYYY-MM-DD'),'Y','Juli-26',7,'S','N',TO_TIMESTAMP('2026-07-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:39:26.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:26.422Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540349 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:39:26.580Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553112,TO_TIMESTAMP('2025-01-13 09:39:26.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:26.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:26.666Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553113,TO_TIMESTAMP('2025-01-13 09:39:26.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:26.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:26.750Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553114,TO_TIMESTAMP('2025-01-13 09:39:26.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:26.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:26.834Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553115,TO_TIMESTAMP('2025-01-13 09:39:26.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:26.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:26.916Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553116,TO_TIMESTAMP('2025-01-13 09:39:26.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:26.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:26.994Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553117,TO_TIMESTAMP('2025-01-13 09:39:26.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:26.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:27.071Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553118,TO_TIMESTAMP('2025-01-13 09:39:26.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:26.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:27.148Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553119,TO_TIMESTAMP('2025-01-13 09:39:27.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:27.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:27.224Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553120,TO_TIMESTAMP('2025-01-13 09:39:27.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:27.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:27.302Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553121,TO_TIMESTAMP('2025-01-13 09:39:27.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:27.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:27.392Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553122,TO_TIMESTAMP('2025-01-13 09:39:27.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:27.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:27.480Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553123,TO_TIMESTAMP('2025-01-13 09:39:27.394000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:27.394000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:27.563Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553124,TO_TIMESTAMP('2025-01-13 09:39:27.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:27.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:27.648Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553125,TO_TIMESTAMP('2025-01-13 09:39:27.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:27.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:27.734Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553126,TO_TIMESTAMP('2025-01-13 09:39:27.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:27.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:27.823Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553127,TO_TIMESTAMP('2025-01-13 09:39:27.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:27.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:27.908Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553128,TO_TIMESTAMP('2025-01-13 09:39:27.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:27.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:28.007Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553129,TO_TIMESTAMP('2025-01-13 09:39:27.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:27.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:28.089Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553130,TO_TIMESTAMP('2025-01-13 09:39:28.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:28.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:28.188Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553131,TO_TIMESTAMP('2025-01-13 09:39:28.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:28.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:28.281Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553132,TO_TIMESTAMP('2025-01-13 09:39:28.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:28.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:28.397Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553133,TO_TIMESTAMP('2025-01-13 09:39:28.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:28.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:28.476Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553134,TO_TIMESTAMP('2025-01-13 09:39:28.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:28.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:28.556Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553135,TO_TIMESTAMP('2025-01-13 09:39:28.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:28.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:28.640Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553136,TO_TIMESTAMP('2025-01-13 09:39:28.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:28.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:28.726Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553137,TO_TIMESTAMP('2025-01-13 09:39:28.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:28.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:28.822Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553138,TO_TIMESTAMP('2025-01-13 09:39:28.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:28.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:28.909Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553139,TO_TIMESTAMP('2025-01-13 09:39:28.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:28.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:29.002Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553140,TO_TIMESTAMP('2025-01-13 09:39:28.914000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:28.914000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:29.081Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553141,TO_TIMESTAMP('2025-01-13 09:39:29.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:29.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:29.160Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553142,TO_TIMESTAMP('2025-01-13 09:39:29.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:29.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:29.241Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553143,TO_TIMESTAMP('2025-01-13 09:39:29.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:29.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:29.326Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553144,TO_TIMESTAMP('2025-01-13 09:39:29.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:29.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:29.409Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553145,TO_TIMESTAMP('2025-01-13 09:39:29.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:29.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:29.489Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553146,TO_TIMESTAMP('2025-01-13 09:39:29.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:29.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:29.572Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553147,TO_TIMESTAMP('2025-01-13 09:39:29.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:29.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:29.648Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553148,TO_TIMESTAMP('2025-01-13 09:39:29.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:29.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:29.727Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553149,TO_TIMESTAMP('2025-01-13 09:39:29.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:29.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:29.809Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553150,TO_TIMESTAMP('2025-01-13 09:39:29.729000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:29.729000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:39:29.889Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540349,553151,TO_TIMESTAMP('2025-01-13 09:39:29.812000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:39:29.812000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Aug.-26

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2026' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2026-08-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2026-08-31','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:40:03.568Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540350,year_id,TO_TIMESTAMP('2025-01-13 09:40:03.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-08-31','YYYY-MM-DD'),'Y','Aug.-26',8,'S','N',TO_TIMESTAMP('2026-08-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:40:03.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:03.572Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540350 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:40:03.745Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553152,TO_TIMESTAMP('2025-01-13 09:40:03.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:03.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:03.843Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553153,TO_TIMESTAMP('2025-01-13 09:40:03.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:03.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:03.940Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553154,TO_TIMESTAMP('2025-01-13 09:40:03.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:03.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:04.043Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553155,TO_TIMESTAMP('2025-01-13 09:40:03.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:03.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:04.145Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553156,TO_TIMESTAMP('2025-01-13 09:40:04.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:04.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:04.270Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553157,TO_TIMESTAMP('2025-01-13 09:40:04.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:04.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:04.369Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553158,TO_TIMESTAMP('2025-01-13 09:40:04.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:04.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:04.467Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553159,TO_TIMESTAMP('2025-01-13 09:40:04.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:04.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:04.588Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553160,TO_TIMESTAMP('2025-01-13 09:40:04.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:04.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:04.681Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553161,TO_TIMESTAMP('2025-01-13 09:40:04.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:04.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:04.779Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553162,TO_TIMESTAMP('2025-01-13 09:40:04.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:04.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:04.878Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553163,TO_TIMESTAMP('2025-01-13 09:40:04.784000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:04.784000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:04.976Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553164,TO_TIMESTAMP('2025-01-13 09:40:04.881000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:04.881000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:05.075Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553165,TO_TIMESTAMP('2025-01-13 09:40:04.980000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:04.980000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:05.180Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553166,TO_TIMESTAMP('2025-01-13 09:40:05.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:05.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:05.281Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553167,TO_TIMESTAMP('2025-01-13 09:40:05.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:05.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:05.385Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553168,TO_TIMESTAMP('2025-01-13 09:40:05.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:05.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:05.486Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553169,TO_TIMESTAMP('2025-01-13 09:40:05.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:05.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:05.586Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553170,TO_TIMESTAMP('2025-01-13 09:40:05.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:05.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:05.685Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553171,TO_TIMESTAMP('2025-01-13 09:40:05.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:05.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:05.789Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553172,TO_TIMESTAMP('2025-01-13 09:40:05.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:05.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:05.903Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553173,TO_TIMESTAMP('2025-01-13 09:40:05.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:05.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:06.011Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553174,TO_TIMESTAMP('2025-01-13 09:40:05.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:05.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:06.113Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553175,TO_TIMESTAMP('2025-01-13 09:40:06.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:06.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:06.209Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553176,TO_TIMESTAMP('2025-01-13 09:40:06.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:06.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:06.301Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553177,TO_TIMESTAMP('2025-01-13 09:40:06.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:06.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:06.404Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553178,TO_TIMESTAMP('2025-01-13 09:40:06.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:06.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:06.503Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553179,TO_TIMESTAMP('2025-01-13 09:40:06.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:06.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:06.604Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553180,TO_TIMESTAMP('2025-01-13 09:40:06.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:06.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:06.704Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553181,TO_TIMESTAMP('2025-01-13 09:40:06.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:06.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:06.802Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553182,TO_TIMESTAMP('2025-01-13 09:40:06.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:06.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:06.901Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553183,TO_TIMESTAMP('2025-01-13 09:40:06.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:06.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:07Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553184,TO_TIMESTAMP('2025-01-13 09:40:06.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:06.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:07.108Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553185,TO_TIMESTAMP('2025-01-13 09:40:07.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:07.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:07.209Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553186,TO_TIMESTAMP('2025-01-13 09:40:07.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:07.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:07.314Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553187,TO_TIMESTAMP('2025-01-13 09:40:07.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:07.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:07.414Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553188,TO_TIMESTAMP('2025-01-13 09:40:07.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:07.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:07.510Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553189,TO_TIMESTAMP('2025-01-13 09:40:07.418000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:07.418000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:07.607Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553190,TO_TIMESTAMP('2025-01-13 09:40:07.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:07.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:07.710Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540350,553191,TO_TIMESTAMP('2025-01-13 09:40:07.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:07.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Sept.-26

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2026' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2026-09-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2026-09-30','YYYY-MM-DD')) THEN


            -- 2025-01-13T09:40:46.048Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540351,year_id,TO_TIMESTAMP('2025-01-13 09:40:46.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-09-30','YYYY-MM-DD'),'Y','Sept.-26',9,'S','N',TO_TIMESTAMP('2026-09-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:40:46.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:46.050Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540351 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:40:46.186Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553192,TO_TIMESTAMP('2025-01-13 09:40:46.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:46.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:46.276Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553193,TO_TIMESTAMP('2025-01-13 09:40:46.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:46.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:46.362Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553194,TO_TIMESTAMP('2025-01-13 09:40:46.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:46.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:46.454Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553195,TO_TIMESTAMP('2025-01-13 09:40:46.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:46.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:46.546Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553196,TO_TIMESTAMP('2025-01-13 09:40:46.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:46.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:46.633Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553197,TO_TIMESTAMP('2025-01-13 09:40:46.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:46.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:46.724Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553198,TO_TIMESTAMP('2025-01-13 09:40:46.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:46.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:46.807Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553199,TO_TIMESTAMP('2025-01-13 09:40:46.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:46.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:46.896Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553200,TO_TIMESTAMP('2025-01-13 09:40:46.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:46.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:46.985Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553201,TO_TIMESTAMP('2025-01-13 09:40:46.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:46.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:47.066Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553202,TO_TIMESTAMP('2025-01-13 09:40:46.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:46.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:47.149Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553203,TO_TIMESTAMP('2025-01-13 09:40:47.068000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:47.068000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:47.246Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553204,TO_TIMESTAMP('2025-01-13 09:40:47.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:47.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:47.338Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553205,TO_TIMESTAMP('2025-01-13 09:40:47.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:47.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:47.424Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553206,TO_TIMESTAMP('2025-01-13 09:40:47.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:47.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:47.520Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553207,TO_TIMESTAMP('2025-01-13 09:40:47.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:47.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:47.614Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553208,TO_TIMESTAMP('2025-01-13 09:40:47.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:47.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:47.706Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553209,TO_TIMESTAMP('2025-01-13 09:40:47.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:47.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:47.795Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553210,TO_TIMESTAMP('2025-01-13 09:40:47.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:47.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:47.876Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553211,TO_TIMESTAMP('2025-01-13 09:40:47.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:47.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:47.963Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553212,TO_TIMESTAMP('2025-01-13 09:40:47.881000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:47.881000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:48.042Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553213,TO_TIMESTAMP('2025-01-13 09:40:47.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:47.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:48.139Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553214,TO_TIMESTAMP('2025-01-13 09:40:48.046000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:48.046000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:48.235Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553215,TO_TIMESTAMP('2025-01-13 09:40:48.143000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:48.143000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:48.319Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553216,TO_TIMESTAMP('2025-01-13 09:40:48.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:48.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:48.407Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553217,TO_TIMESTAMP('2025-01-13 09:40:48.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:48.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:48.499Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553218,TO_TIMESTAMP('2025-01-13 09:40:48.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:48.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:48.588Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553219,TO_TIMESTAMP('2025-01-13 09:40:48.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:48.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:48.671Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553220,TO_TIMESTAMP('2025-01-13 09:40:48.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:48.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:48.764Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553221,TO_TIMESTAMP('2025-01-13 09:40:48.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:48.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:48.853Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553222,TO_TIMESTAMP('2025-01-13 09:40:48.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:48.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:48.942Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553223,TO_TIMESTAMP('2025-01-13 09:40:48.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:48.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:49.032Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553224,TO_TIMESTAMP('2025-01-13 09:40:48.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:48.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:49.119Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553225,TO_TIMESTAMP('2025-01-13 09:40:49.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:49.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:49.217Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553226,TO_TIMESTAMP('2025-01-13 09:40:49.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:49.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:49.299Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553227,TO_TIMESTAMP('2025-01-13 09:40:49.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:49.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:49.388Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553228,TO_TIMESTAMP('2025-01-13 09:40:49.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:49.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:49.475Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553229,TO_TIMESTAMP('2025-01-13 09:40:49.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:49.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:49.562Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553230,TO_TIMESTAMP('2025-01-13 09:40:49.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:49.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:40:49.649Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540351,553231,TO_TIMESTAMP('2025-01-13 09:40:49.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:40:49.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Okt.-26

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2026' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2026-10-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2026-10-31','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:41:32.432Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540352,year_id,TO_TIMESTAMP('2025-01-13 09:41:32.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-10-31','YYYY-MM-DD'),'Y','Okt.-26',10,'S','N',TO_TIMESTAMP('2026-10-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:41:32.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:32.435Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540352 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:41:32.546Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553232,TO_TIMESTAMP('2025-01-13 09:41:32.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:32.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:32.646Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553233,TO_TIMESTAMP('2025-01-13 09:41:32.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:32.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:32.732Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553234,TO_TIMESTAMP('2025-01-13 09:41:32.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:32.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:32.825Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553235,TO_TIMESTAMP('2025-01-13 09:41:32.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:32.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:32.905Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553236,TO_TIMESTAMP('2025-01-13 09:41:32.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:32.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:32.996Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553237,TO_TIMESTAMP('2025-01-13 09:41:32.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:32.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:33.082Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553238,TO_TIMESTAMP('2025-01-13 09:41:33.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:33.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:33.172Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553239,TO_TIMESTAMP('2025-01-13 09:41:33.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:33.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:33.252Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553240,TO_TIMESTAMP('2025-01-13 09:41:33.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:33.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:33.329Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553241,TO_TIMESTAMP('2025-01-13 09:41:33.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:33.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:33.410Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553242,TO_TIMESTAMP('2025-01-13 09:41:33.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:33.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:33.494Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553243,TO_TIMESTAMP('2025-01-13 09:41:33.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:33.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:33.576Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553244,TO_TIMESTAMP('2025-01-13 09:41:33.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:33.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:33.663Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553245,TO_TIMESTAMP('2025-01-13 09:41:33.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:33.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:33.746Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553246,TO_TIMESTAMP('2025-01-13 09:41:33.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:33.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:33.841Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553247,TO_TIMESTAMP('2025-01-13 09:41:33.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:33.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:33.920Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553248,TO_TIMESTAMP('2025-01-13 09:41:33.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:33.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:34Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553249,TO_TIMESTAMP('2025-01-13 09:41:33.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:33.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:34.086Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553250,TO_TIMESTAMP('2025-01-13 09:41:34.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:34.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:34.170Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553251,TO_TIMESTAMP('2025-01-13 09:41:34.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:34.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:34.258Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553252,TO_TIMESTAMP('2025-01-13 09:41:34.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:34.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:34.346Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553253,TO_TIMESTAMP('2025-01-13 09:41:34.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:34.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:34.434Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553254,TO_TIMESTAMP('2025-01-13 09:41:34.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:34.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:34.515Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553255,TO_TIMESTAMP('2025-01-13 09:41:34.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:34.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:34.599Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553256,TO_TIMESTAMP('2025-01-13 09:41:34.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:34.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:34.688Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553257,TO_TIMESTAMP('2025-01-13 09:41:34.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:34.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:34.772Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553258,TO_TIMESTAMP('2025-01-13 09:41:34.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:34.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:34.861Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553259,TO_TIMESTAMP('2025-01-13 09:41:34.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:34.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:34.938Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553260,TO_TIMESTAMP('2025-01-13 09:41:34.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:34.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:35.024Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553261,TO_TIMESTAMP('2025-01-13 09:41:34.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:34.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:35.105Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553262,TO_TIMESTAMP('2025-01-13 09:41:35.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:35.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:35.209Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553263,TO_TIMESTAMP('2025-01-13 09:41:35.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:35.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:35.293Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553264,TO_TIMESTAMP('2025-01-13 09:41:35.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:35.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:35.377Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553265,TO_TIMESTAMP('2025-01-13 09:41:35.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:35.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:35.458Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553266,TO_TIMESTAMP('2025-01-13 09:41:35.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:35.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:35.540Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553267,TO_TIMESTAMP('2025-01-13 09:41:35.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:35.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:35.623Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553268,TO_TIMESTAMP('2025-01-13 09:41:35.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:35.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:35.705Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553269,TO_TIMESTAMP('2025-01-13 09:41:35.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:35.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:35.789Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553270,TO_TIMESTAMP('2025-01-13 09:41:35.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:35.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:41:35.886Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540352,553271,TO_TIMESTAMP('2025-01-13 09:41:35.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:41:35.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Nov.-26

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2026' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2026-11-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2026-11-30','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:42:05.287Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540353,year_id,TO_TIMESTAMP('2025-01-13 09:42:05.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-11-30','YYYY-MM-DD'),'Y','Nov.-26',11,'S','N',TO_TIMESTAMP('2026-11-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:42:05.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:05.290Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540353 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:42:05.439Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553272,TO_TIMESTAMP('2025-01-13 09:42:05.318000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:05.318000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:05.545Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553273,TO_TIMESTAMP('2025-01-13 09:42:05.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:05.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:05.642Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553274,TO_TIMESTAMP('2025-01-13 09:42:05.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:05.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:05.736Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553275,TO_TIMESTAMP('2025-01-13 09:42:05.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:05.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:05.831Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553276,TO_TIMESTAMP('2025-01-13 09:42:05.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:05.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:05.926Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553277,TO_TIMESTAMP('2025-01-13 09:42:05.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:05.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:06.014Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553278,TO_TIMESTAMP('2025-01-13 09:42:05.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:05.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:06.131Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553279,TO_TIMESTAMP('2025-01-13 09:42:06.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:06.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:06.223Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553280,TO_TIMESTAMP('2025-01-13 09:42:06.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:06.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:06.317Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553281,TO_TIMESTAMP('2025-01-13 09:42:06.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:06.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:06.404Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553282,TO_TIMESTAMP('2025-01-13 09:42:06.321000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:06.321000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:06.505Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553283,TO_TIMESTAMP('2025-01-13 09:42:06.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:06.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:06.596Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553284,TO_TIMESTAMP('2025-01-13 09:42:06.508000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:06.508000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:06.688Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553285,TO_TIMESTAMP('2025-01-13 09:42:06.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:06.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:06.780Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553286,TO_TIMESTAMP('2025-01-13 09:42:06.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:06.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:06.868Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553287,TO_TIMESTAMP('2025-01-13 09:42:06.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:06.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:06.959Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553288,TO_TIMESTAMP('2025-01-13 09:42:06.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:06.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:07.049Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553289,TO_TIMESTAMP('2025-01-13 09:42:06.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:06.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:07.138Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553290,TO_TIMESTAMP('2025-01-13 09:42:07.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:07.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:07.223Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553291,TO_TIMESTAMP('2025-01-13 09:42:07.142000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:07.142000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:07.319Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553292,TO_TIMESTAMP('2025-01-13 09:42:07.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:07.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:07.421Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553293,TO_TIMESTAMP('2025-01-13 09:42:07.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:07.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:07.513Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553294,TO_TIMESTAMP('2025-01-13 09:42:07.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:07.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:07.606Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553295,TO_TIMESTAMP('2025-01-13 09:42:07.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:07.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:07.703Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553296,TO_TIMESTAMP('2025-01-13 09:42:07.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:07.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:07.796Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553297,TO_TIMESTAMP('2025-01-13 09:42:07.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:07.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:07.881Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553298,TO_TIMESTAMP('2025-01-13 09:42:07.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:07.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:07.965Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553299,TO_TIMESTAMP('2025-01-13 09:42:07.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:07.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:08.052Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553300,TO_TIMESTAMP('2025-01-13 09:42:07.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:07.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:08.142Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553301,TO_TIMESTAMP('2025-01-13 09:42:08.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:08.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:08.233Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553302,TO_TIMESTAMP('2025-01-13 09:42:08.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:08.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:08.332Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553303,TO_TIMESTAMP('2025-01-13 09:42:08.237000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:08.237000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:08.422Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553304,TO_TIMESTAMP('2025-01-13 09:42:08.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:08.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:08.518Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553305,TO_TIMESTAMP('2025-01-13 09:42:08.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:08.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:08.608Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553306,TO_TIMESTAMP('2025-01-13 09:42:08.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:08.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:08.695Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553307,TO_TIMESTAMP('2025-01-13 09:42:08.612000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:08.612000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:08.786Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553308,TO_TIMESTAMP('2025-01-13 09:42:08.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:08.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:08.874Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553309,TO_TIMESTAMP('2025-01-13 09:42:08.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:08.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:08.965Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553310,TO_TIMESTAMP('2025-01-13 09:42:08.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:08.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:09.064Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540353,553311,TO_TIMESTAMP('2025-01-13 09:42:08.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:08.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Dez.-26

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2026' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2026-12-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2026-12-31','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:42:39.239Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540354,year_id,TO_TIMESTAMP('2025-01-13 09:42:39.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-12-31','YYYY-MM-DD'),'Y','Dez.-26',12,'S','N',TO_TIMESTAMP('2026-12-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:42:39.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:39.241Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540354 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:42:39.375Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553312,TO_TIMESTAMP('2025-01-13 09:42:39.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:39.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:39.459Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553313,TO_TIMESTAMP('2025-01-13 09:42:39.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:39.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:39.549Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553314,TO_TIMESTAMP('2025-01-13 09:42:39.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:39.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:39.635Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553315,TO_TIMESTAMP('2025-01-13 09:42:39.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:39.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:39.730Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553316,TO_TIMESTAMP('2025-01-13 09:42:39.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:39.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:39.816Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553317,TO_TIMESTAMP('2025-01-13 09:42:39.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:39.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:39.908Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553318,TO_TIMESTAMP('2025-01-13 09:42:39.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:39.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:39.993Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553319,TO_TIMESTAMP('2025-01-13 09:42:39.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:39.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:40.078Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553320,TO_TIMESTAMP('2025-01-13 09:42:39.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:39.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:40.164Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553321,TO_TIMESTAMP('2025-01-13 09:42:40.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:40.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:40.249Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553322,TO_TIMESTAMP('2025-01-13 09:42:40.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:40.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:40.339Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553323,TO_TIMESTAMP('2025-01-13 09:42:40.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:40.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:40.428Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553324,TO_TIMESTAMP('2025-01-13 09:42:40.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:40.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:40.513Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553325,TO_TIMESTAMP('2025-01-13 09:42:40.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:40.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:40.600Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553326,TO_TIMESTAMP('2025-01-13 09:42:40.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:40.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:40.692Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553327,TO_TIMESTAMP('2025-01-13 09:42:40.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:40.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:40.781Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553328,TO_TIMESTAMP('2025-01-13 09:42:40.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:40.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:40.867Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553329,TO_TIMESTAMP('2025-01-13 09:42:40.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:40.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:40.951Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553330,TO_TIMESTAMP('2025-01-13 09:42:40.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:40.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:41.032Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553331,TO_TIMESTAMP('2025-01-13 09:42:40.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:40.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:41.118Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553332,TO_TIMESTAMP('2025-01-13 09:42:41.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:41.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:41.202Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553333,TO_TIMESTAMP('2025-01-13 09:42:41.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:41.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:41.281Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553334,TO_TIMESTAMP('2025-01-13 09:42:41.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:41.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:41.369Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553335,TO_TIMESTAMP('2025-01-13 09:42:41.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:41.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:41.450Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553336,TO_TIMESTAMP('2025-01-13 09:42:41.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:41.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:41.541Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553337,TO_TIMESTAMP('2025-01-13 09:42:41.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:41.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:41.623Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553338,TO_TIMESTAMP('2025-01-13 09:42:41.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:41.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:41.704Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553339,TO_TIMESTAMP('2025-01-13 09:42:41.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:41.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:41.789Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553340,TO_TIMESTAMP('2025-01-13 09:42:41.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:41.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:41.871Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553341,TO_TIMESTAMP('2025-01-13 09:42:41.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:41.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:41.953Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553342,TO_TIMESTAMP('2025-01-13 09:42:41.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:41.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:42.032Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553343,TO_TIMESTAMP('2025-01-13 09:42:41.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:41.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:42.110Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553344,TO_TIMESTAMP('2025-01-13 09:42:42.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:42.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:42.199Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553345,TO_TIMESTAMP('2025-01-13 09:42:42.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:42.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:42.279Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553346,TO_TIMESTAMP('2025-01-13 09:42:42.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:42.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:42.365Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553347,TO_TIMESTAMP('2025-01-13 09:42:42.283000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:42.283000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:42.447Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553348,TO_TIMESTAMP('2025-01-13 09:42:42.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:42.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:42.532Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553349,TO_TIMESTAMP('2025-01-13 09:42:42.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:42.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:42.617Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553350,TO_TIMESTAMP('2025-01-13 09:42:42.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:42.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:42:42.701Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540354,553351,TO_TIMESTAMP('2025-01-13 09:42:42.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:42:42.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;
